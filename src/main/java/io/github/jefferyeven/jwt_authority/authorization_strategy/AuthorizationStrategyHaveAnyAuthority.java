package io.github.jefferyeven.jwt_authority.authorization_strategy;

import io.github.jefferyeven.jwt_authority.bean.UrlPermission;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthorizationStrategyHaveAnyAuthority implements AuthorizationStrategy{
    private final TokenVerifyer tokenVerifyer;

    public AuthorizationStrategyHaveAnyAuthority(TokenVerifyer tokenVerifyer) {
        this.tokenVerifyer = tokenVerifyer;
    }

    @Override
    public boolean passAuthorization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission) throws Exception {
        VerifyTokenResult verifyTokenResult = tokenVerifyer.verifyToken(request,response);
        if(!verifyTokenResult.isPassVerify()){
            throw new JwtSecurityException(JwtResponseMag.TokenError);
        }
        List<String> authorities = verifyTokenResult.getAuthorities();
        List<String> needAuthorities = urlPermission.getAuthorities();
        for(String authority:authorities){
            if(needAuthorities.contains(authority)){
                return true;
            }
        }
        return false;
    }
}
