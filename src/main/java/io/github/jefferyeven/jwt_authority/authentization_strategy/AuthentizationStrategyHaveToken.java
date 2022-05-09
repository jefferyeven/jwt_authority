package io.github.jefferyeven.jwt_authority.authentization_strategy;

import io.github.jefferyeven.jwt_authority.bean.UrlPermission;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthentizationStrategyHaveToken implements AuthentizationStrategy{
    private final TokenVerifyer tokenVerifyer;

    public AuthentizationStrategyHaveToken(TokenVerifyer tokenVerifyer) {
        this.tokenVerifyer = tokenVerifyer;
    }

    @Override
    public boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission) throws Exception {
        boolean res = tokenVerifyer.verifyToken(request,response).isPassVerify();
        if(!res){
            throw new JwtSecurityException(JwtResponseMag.TokenError);
        }
        return res;
    }
}
