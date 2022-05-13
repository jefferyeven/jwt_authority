package io.github.jefferyeven.jwt_authority.authorization;

import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategy;
import io.github.jefferyeven.jwt_authority.bean.AuthorizationState;
import io.github.jefferyeven.jwt_authority.bean.UrlPermission;
import io.github.jefferyeven.jwt_authority.bean.UrlsPermission;
import io.github.jefferyeven.jwt_authority.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicAuthorization extends AbstractAuthorization{
    // 对urlPermission进行规范化
    private final Map<String, UrlPermission> urlPermissionMap = new HashMap<>();
    private final String noMatch = "don't find match url";
    public BasicAuthorization(List<UrlsPermission> urlsPermissionList){
        for (UrlsPermission urlsPermission : urlsPermissionList) {
            for (String s : urlsPermission.getUrls()) {
                s = CommonUtils.urlStandard(s);
                UrlPermission urlPermission = new UrlPermission(s,urlsPermission.getAuthorities(),urlsPermission.getPermissionLevel(),urlsPermission.getAuthorizationStrategy());
                urlPermissionMap.put(s, urlPermission);
            }
        }
    }
    @Override
    public AuthorizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String findUrl = findMathUrl(request.getRequestURI());
        // 没有发现匹配的url
        if (noMatch.equals(findUrl)) {
            return AuthorizationState.UnAuthenizateState;
        }
        UrlPermission urlPermission = urlPermissionMap.get(findUrl);
        // 得到鉴权的策略
        AuthorizationStrategy authorizationStrategy = urlPermission.getAuthorizationStrategy();
        boolean passAuthorization = authorizationStrategy.passAuthorization(request,response,urlPermission);
        return  passAuthorization?AuthorizationState.PassState:AuthorizationState.NoPassState;
    }

    private String findMathUrl(String url) {
        if (urlPermissionMap.containsKey(url)) {
            return url;
        }
        String[] mathes = url.split("/");
        for (int i = mathes.length - 1; i >= 0; i--) {
            StringBuilder res = new StringBuilder();
            for (int j = 0; j < i; j++) {
                res.append(mathes[j]).append("/");
            }
            res.append("*");
            if (urlPermissionMap.containsKey(res.toString())) {
                return res.toString();
            }
        }
        return noMatch;
    }
}
