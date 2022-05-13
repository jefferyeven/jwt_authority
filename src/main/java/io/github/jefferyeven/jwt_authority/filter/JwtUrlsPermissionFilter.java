package io.github.jefferyeven.jwt_authority.filter;

import io.github.jefferyeven.jwt_authority.authorization.AbstractAuthorization;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUrlsPermissionFilter implements Filter {

   private AbstractAuthorization startAuthorization;
    public JwtUrlsPermissionFilter(){

    }

    private JwtUrlsPermissionFailureHandler jwtUrlsPermissionFailureHandler = new JwtUrlsPermissionFailureHandler() {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException{
            e.printStackTrace();
            response.setContentType("text/plain;charset=UTF-8");
            String res = "{\"code\":500,\"msg\",\""+e.getMessage()+"\"}";
            response.getWriter().write(res);
        }
    };

    public JwtUrlsPermissionFailureHandler getJwtUrlsPermissionFailureHandler() {
        return jwtUrlsPermissionFailureHandler;
    }

    public void setJwtUrlsPermissionFailureHandler(JwtUrlsPermissionFailureHandler jwtUrlsPermissionFailureHandler) {
        this.jwtUrlsPermissionFailureHandler = jwtUrlsPermissionFailureHandler;
    }
    public AbstractAuthorization getStartAuthorization() {
        return startAuthorization;
    }

    public void setStartAuthorization(AbstractAuthorization startAuthorization) {
        this.startAuthorization = startAuthorization;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean passAuthenizate;
        try {
            passAuthenizate = startAuthorization.doAuthenizate(request,response);
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
