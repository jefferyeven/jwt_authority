package cn.jeffey.jwt_authority.filter;

import cn.jeffey.jwt_authority.authenization.AbstractAuthenization;
import cn.jeffey.jwt_authority.exception.JwtResponseMag;
import cn.jeffey.jwt_authority.exception.JwtSecurityException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUrlsPermissionFilter implements Filter {

    AbstractAuthenization startAuthenization;
    public JwtUrlsPermissionFilter(AbstractAuthenization startAuthenization){
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
            throwExceptionUrl(servletRequest,servletResponse,e);
            return;
        }
        if(!passAuthenizate){
            throwExceptionUrl(servletRequest,servletResponse,new JwtSecurityException(JwtResponseMag.NoAuthorityError));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void throwExceptionUrl(ServletRequest servletRequest, ServletResponse servletResponse,Exception e) throws ServletException, IOException {
        e.printStackTrace();
        // 异常捕获、发送到UnsupportedJwtException
        servletRequest.setAttribute("Exception", e);
        // 将异常分发到UnsupportedJwtException控制器
        servletRequest.getRequestDispatcher("/Exception").forward(servletRequest, servletResponse);
    }



}
