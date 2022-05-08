package io.github.jefferyeven.jwt_authority.filter;

import io.github.jefferyeven.jwt_authority.authenization.AbstractAuthenization;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUrlsPermissionFilter implements Filter {

   private AbstractAuthenization startAuthenization;
    public JwtUrlsPermissionFilter(){

    }

    private JwtUrlsPermissionFailureHandler jwtUrlsPermissionFailureHandler = new JwtUrlsPermissionFailureHandler() {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException{
            e.printStackTrace();
            String res = "{/“code/”:500,/“msg/”," + e.getMessage() + "}";
            response.getWriter().write(res);
        }
    };

    public JwtUrlsPermissionFailureHandler getJwtUrlsPermissionFailureHandler() {
        return jwtUrlsPermissionFailureHandler;
    }

    public void setJwtUrlsPermissionFailureHandler(JwtUrlsPermissionFailureHandler jwtUrlsPermissionFailureHandler) {
        this.jwtUrlsPermissionFailureHandler = jwtUrlsPermissionFailureHandler;
    }
    public AbstractAuthenization getStartAuthenization() {
        return startAuthenization;
    }

    public void setStartAuthenization(AbstractAuthenization startAuthenization) {
        this.startAuthenization = startAuthenization;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean passAuthenizate;
        try {
            passAuthenizate = startAuthenization.doAuthenizate(request,response);
        } catch (Exception e) {
            throwExceptionUrl(request,response,e);
            return;
        }
        if(!passAuthenizate){
            throwExceptionUrl(request,response,new JwtSecurityException(JwtResponseMag.NoAuthorityError));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void throwExceptionUrl(HttpServletRequest request, HttpServletResponse response,Exception e) throws ServletException, IOException {
        jwtUrlsPermissionFailureHandler.commence(request,response,e);
    }



}
