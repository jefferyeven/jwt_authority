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
            return passAuthizate?AuthenizationState.PassState:AuthenizationState.NoPassState;
        }
        if(nextMap!=null) {
            return doAuthenizate(request,response,url,nextMap,null);
        }
        // 代表是reques,由于其可以是加在类上。
        String findUrl = findMathUrl(url,map);
        // 没有发现匹配的url
        if (noMatch.equals(findUrl)) {
            return AuthenizationState.UnAuthenizateState;
        }
        UrlPermission urlPermission = map.get(findUrl);
        AuthentizationStrategy authentizationStrategy = urlPermission.getAuthenizationStrategy();
        boolean passAuthizate = authentizationStrategy.passAuthentization(request,response,urlPermission);
        return passAuthizate?AuthenizationState.PassState:AuthenizationState.NoPassState;
    }
    private final String noMatch = "don't find match url";
    private String findMathUrl(String url,Map<String, UrlPermission> map) {
        String[] mathes = url.split("/");
        for (int i = mathes.length - 1; i >= 0; i--) {
            StringBuilder res = new StringBuilder();
            for (int j = 0; j < i; j++) {
                res.append(mathes[j]).append("/");
            }
            res.append("*");
            if (map.containsKey(res.toString())) {
                return res.toString();
            }
        }
        return noMatch;
    }
}
