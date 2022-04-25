package cn.jeffey.jwt_authority.annoation;

import cn.jeffey.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
import cn.jeffey.jwt_authority.authentization_strategy.AuthentizationStrategy;
import cn.jeffey.jwt_authority.bean.AnnoationPermissionUrl;
import cn.jeffey.jwt_authority.bean.PermissionLevel;
import cn.jeffey.jwt_authority.bean.UrlPermission;
import cn.jeffey.jwt_authority.utils.CommonUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 12493
 */
@Component
public class AnnoationHandler implements EmbeddedValueResolverAware {
    private StringValueResolver stringValueResolver;
    private final AnnoationPermissionUrl annoationPermissionUrl = new AnnoationPermissionUrl();
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }
    @Autowired
    ApplicationContext applicationContext;

    public void readAnnoation(){

        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        //这个验证是基于接口的可访问，所以我们只需要拦截springboot controller

        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Class<?> aClass = AopUtils.getTargetClass(entry.getValue());
            readAnnoationFromClass(aClass);
        }
    }
    public void readAnnoationFromClass(Class<?> readClass){
        String[] prePerifx = {""};
        if(readClass.isAnnotationPresent(RequestMapping.class)){
            RequestMapping requestMapping = readClass.getAnnotation(RequestMapping.class);
            prePerifx = requestMapping.value();
            cleanMappingValues(prePerifx);

        }
        Method[] methods = readClass.getMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(NeedAuthorize.class)){
                NeedAuthorize needAuthorize = method.getAnnotation(NeedAuthorize.class);
                readGetMapping(prePerifx,method,needAuthorize);
                readPostMapping(prePerifx,method,needAuthorize);
                readPutMapping(prePerifx,method,needAuthorize);
                readDeleteMapping(prePerifx,method,needAuthorize);
                readRequestMapping(prePerifx,method,needAuthorize);
            }
        }

    }
    public void readGetMapping(String[] prePerifx,Method method,NeedAuthorize needAuthorize){
        if(method.isAnnotationPresent(GetMapping.class)){
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            String[] values = getMapping.value();
            putValueInAnnoationPermissionUrl(prePerifx,values, annoationPermissionUrl.getGetMap(), needAuthorize);
        }
    }
    public void readPutMapping(String[] prePerifx,Method method,NeedAuthorize needAuthorize){
        if(method.isAnnotationPresent(PutMapping.class)){
            PutMapping getMapping = method.getAnnotation(PutMapping.class);
            String[] values = getMapping.value();
            putValueInAnnoationPermissionUrl(prePerifx,values, annoationPermissionUrl.getPutMap(), needAuthorize);
        }
    }
    public void readRequestMapping(String[] prePerifx,Method method,NeedAuthorize needAuthorize){
        if(method.isAnnotationPresent(RequestMapping.class)){
            RequestMapping getMapping = method.getAnnotation(RequestMapping.class);
            String[] values = getMapping.value();
            if(values.length==0){
                values = new String[]{""};
            }
            putValueInAnnoationPermissionUrl(prePerifx,values, annoationPermissionUrl.getRequestMap(), needAuthorize);
        }
    }
    public void readPostMapping(String[] prePerifx,Method method,NeedAuthorize needAuthorize){
        if(method.isAnnotationPresent(PostMapping.class)){
            PostMapping getMapping = method.getAnnotation(PostMapping.class);
            String[] values = getMapping.value();
            putValueInAnnoationPermissionUrl(prePerifx,values, annoationPermissionUrl.getPostMap(), needAuthorize);
        }
    }
    public void readDeleteMapping(String[] prePerifx,Method method,NeedAuthorize needAuthorize){
        if(method.isAnnotationPresent(DeleteMapping.class)){
            DeleteMapping getMapping = method.getAnnotation(DeleteMapping.class);
            String[] values = getMapping.value();
            putValueInAnnoationPermissionUrl(prePerifx,values, annoationPermissionUrl.getDeleteMap(), needAuthorize);
        }
    }
    public void putValueInAnnoationPermissionUrl(String[] preValues,String[] values,Map<String, UrlPermission> map,NeedAuthorize needAuthorize){

        PermissionLevel permissionLevel = needAuthorize.authorizeLevel();
        List<String> authorities = Arrays.asList(needAuthorize.authorties());
        AuthentizationStrategy strategy = AuthenizationStrategyManger.getAuthenizationByPermissionLevel(permissionLevel);
        cleanMappingValues(values);
        for(String preValueItem:preValues){
            for(String valueItem:values){
                String url = preValueItem+valueItem;
                UrlPermission urlPermission= new UrlPermission(url,authorities,permissionLevel,strategy);
                map.put(url,urlPermission);
            }
        }
    }
    public void cleanMappingValues(String[] values){

        for(int i=0;i<values.length;i++){
            String readValue = stringValueResolver.resolveStringValue(values[i]);
            if(readValue==null|| "".equals(readValue.trim())){
                values[i]="";
                continue;
            }
            values[i] = CommonUtils.urlStandard(readValue);
        }
    }

    public AnnoationPermissionUrl getAnnoationPermissionUrl() {
        return annoationPermissionUrl;
    }
}
