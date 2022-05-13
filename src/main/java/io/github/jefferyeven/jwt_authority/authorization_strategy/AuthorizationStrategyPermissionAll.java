package io.github.jefferyeven.jwt_authority.authorization_strategy;

import io.github.jefferyeven.jwt_authority.bean.UrlPermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationStrategyPermissionAll implements AuthorizationStrategy {
    @Override
    public boolean passAuthorization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission){
        String url = request.getRequestURI();
        return true;
    }
}
