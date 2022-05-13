package com.example.advanceuse.security;

import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategy;
import io.github.jefferyeven.jwt_authority.bean.UrlPermission;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class NewAuthorizationStrategy implements AuthorizationStrategy {
    @Autowired
    TokenUtil tokenUtil;

    @Override
    public boolean passAuthorization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission) throws Exception {
        // 从header读取token
        VerifyTokenResult verifyTokenResult = tokenUtil.verifyToken(request,response);
        if(!verifyTokenResult.isPassVerify()){
            return false;
        }
        // 这里的验证规则是有admin就直接通过
        return verifyTokenResult.getAuthorities().contains("vip");
    }
}
