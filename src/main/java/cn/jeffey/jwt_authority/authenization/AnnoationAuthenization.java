package cn.jeffey.jwt_authority.authenization;

import cn.jeffey.jwt_authority.authentization_strategy.AuthentizationStrategy;
import cn.jeffey.jwt_authority.bean.AnnoationPermissionUrl;
import cn.jeffey.jwt_authority.bean.AuthenizationState;
import cn.jeffey.jwt_authority.bean.UrlPermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AnnoationAuthenization extends AbstractAuthenization {
    private AnnoationPermissionUrl annoationPermissionUrl;
    public AnnoationAuthenization(AnnoationPermissionUrl annoationPermissionUrl){
        this.annoationPermissionUrl = annoationPermissionUrl;
    }
    @Override
    public AuthenizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        String url = request.getRequestURI();
        switch (method){
            case "PUT":
                return doAuthenizate(request,response,url, annoationPermissionUrl.getPutMap(), annoationPermissionUrl.getRequestMap());
            case "DELETE":
                return doAuthenizate(request,response,url, annoationPermissionUrl.getDeleteMap(), annoationPermissionUrl.getRequestMap());
            case "GET":
                return doAuthenizate(request,response,url, annoationPermissionUrl.getGetMap(), annoationPermissionUrl.getRequestMap());
            case "POST":
                return doAuthenizate(request,response,url, annoationPermissionUrl.getPostMap(), annoationPermissionUrl.getRequestMap());
            default:
                return doAuthenizate(request,response,url,  annoationPermissionUrl.getRequestMap(),null);

        }

    }
    public AuthenizationState doAuthenizate(HttpServletRequest request, HttpServletResponse response,String url,Map<String, UrlPermission> map,Map<String, UrlPermission> nextMap) throws Exception {
        if(map.containsKey(url)){
            UrlPermission urlPermission = map.get(url);
            AuthentizationStrategy authentizationStrategy = urlPermission.getAuthenizationStrategy();
            boolean passAuthizate = authentizationStrategy.passAuthentization(request,response,urlPermission);
            if(nextMap == null||passAuthizate){
                return passAuthizate?AuthenizationState.PassState:AuthenizationState.NoPassState;
            }
            return doAuthenizate(request,response,url,nextMap,null);
        }
        return AuthenizationState.UnAuthenizateState;
    }
}
