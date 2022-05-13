package com.example.advanceuse.security;

import io.github.jefferyeven.jwt_authority.filter.JwtUrlsPermissionFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UrlFailureHandler implements JwtUrlsPermissionFailureHandler {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        System.out.println("出现了错误");
        e.printStackTrace();
        request.setAttribute("exception",e);
        // 跳转到处理错误的页面
        request.getRequestDispatcher("/index/error").forward(request,response);
    }
}
