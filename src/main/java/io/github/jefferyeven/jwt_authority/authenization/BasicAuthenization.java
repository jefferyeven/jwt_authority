package io.github.jefferyeven.jwt_authority.authenization;

import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthentizationStrategy;
import io.github.jefferyeven.jwt_authority.bean.AuthenizationState;
import io.github.jefferyeven.jwt_authority.bean.UrlPermission;
import io.github.jefferyeven.jwt_authority.bean.UrlsPermission;
import io.github.jefferyeven.jwt_authority.utils.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicAuthenization extends AbstractAuthenization{
    // 对urlPermission进行规范化
    private final Map<String, UrlPermission> urlPermissionMap = new HashMap<>();
    private final String noMatch = "don't find match url";
    public BasicAuthenization(List<UrlsPermission> urlsPermissionList){
        for (UrlsPermission urlsPermission : urlsPermissionList) {
            for (String s : urlsPermission.getUrls()) {
                s = CommonUtils.urlStandard(s);
                UrlPermission urlPermission = new UrlPermission(s,urlsPermission.getAuthorities(),urlsPermission.getPermissionLevel(),urlsPermission.getAuthenizationStrategy());
                urlPermissionMap.put(s, urlPermission);
            }
        }
    }
    @Override
    public AuthenizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String findUrl = findMathUrl(request.getRequestURI());
        // 没有发现匹配的url
        if (noMatch.equals(findUrl)) {
            return AuthenizationState.UnAuthenizateState;
        }
        UrlPermission urlPermission = urlPermissionMap.get(findUrl);
        // 得到鉴权的策略
        AuthentizationStrategy authentizationStrategy = urlPermission.getAuthenizationStrategy();
        boolean passAuthentization = authentizationStrategy.passAuthentization(request,response,urlPermission);
        return  passAuthentization?AuthenizationState.PassState:AuthenizationState.NoPassState;
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
