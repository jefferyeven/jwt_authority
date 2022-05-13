package io.github.jefferyeven.jwt_authority.authorization_strategy;

import io.github.jefferyeven.jwt_authority.bean.UrlPermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface AuthorizationStrategy {
    boolean passAuthorization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission) throws Exception;
}
