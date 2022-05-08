package io.github.jefferyeven.jwt_authority.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface JwtUrlsPermissionFailureHandler {
    void commence(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException;

}
