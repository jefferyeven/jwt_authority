package io.github.jefferyeven.jwt_authority.authentization_strategy;

import io.github.jefferyeven.jwt_authority.bean.UrlPermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface AuthentizationStrategy {
    boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission) throws Exception;
}
