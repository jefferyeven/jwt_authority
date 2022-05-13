package com.example.advanceuse.security;

import com.alibaba.fastjson.JSON;
import com.example.advanceuse.bean.entity.UrlAuthorities;
import com.example.advanceuse.mapper.UrlAuthoritiesMapper;
import io.github.jefferyeven.jwt_authority.authorization.AbstractAuthorization;
import io.github.jefferyeven.jwt_authority.bean.AuthorizationState;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
@Component
public class DynamicAuthorization extends AbstractAuthorization {
    @Autowired
    UrlAuthoritiesMapper urlAuthoritiesMapper;
    @Autowired
    TokenUtil tokenUtil;
    @Override
    public AuthorizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println(request.getRequestURI());
        UrlAuthorities urlAuthorities = urlAuthoritiesMapper.getUrlAuthoritiesFromUrl(request.getRequestURI());
        if(urlAuthorities==null){
            return AuthorizationState.UnAuthenizateState;
        }
        VerifyTokenResult verifyTokenResult = tokenUtil.verifyToken(request,response);
        if(!verifyTokenResult.isPassVerify()){
            throw new JwtSecurityException(JwtResponseMag.TokenError);
        }
        List<String> authortities = JSON.parseObject(urlAuthorities.getAuthorities(),List.class);
        for(String s:authortities){
            if(verifyTokenResult.getAuthorities().contains(s)){
                return AuthorizationState.PassState;
            }
        }
        return AuthorizationState.UnAuthenizateState;
    }
}
