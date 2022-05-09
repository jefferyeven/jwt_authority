package io.github.jefferyeven.jwt_authority.authentization_strategy;

import io.github.jefferyeven.jwt_authority.bean.UrlPermission;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthenizationStrategyHaveAllAuthority implements AuthentizationStrategy{
    private final TokenVerifyer tokenVerifyer;


    public AuthenizationStrategyHaveAllAuthority(TokenVerifyer tokenVerifyer) {
        this.tokenVerifyer = tokenVerifyer;
    }

    @Override
    public boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission) throws Exception {

        VerifyTokenResult verifyTokenResult = tokenVerifyer.verifyToken(request,response);
        if(!verifyTokenResult.isPassVerify()){
            throw new JwtSecurityException(JwtResponseMag.TokenReadError);
        }
        List<String> authorities = verifyTokenResult.getAuthorities();
        Set<String> authoritiesSet = new HashSet<>(authorities);
        List<String> needAuthorities = urlPermission.getAuthorities();
        for(String needAuthority:needAuthorities){
            if(!authoritiesSet.contains(needAuthority)){
               return false;
            }
        }
        return true;
    }
}
