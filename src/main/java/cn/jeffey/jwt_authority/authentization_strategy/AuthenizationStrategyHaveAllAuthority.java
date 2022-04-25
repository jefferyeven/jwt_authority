package cn.jeffey.jwt_authority.authentization_strategy;

import cn.jeffey.jwt_authority.bean.UrlPermission;
import cn.jeffey.jwt_authority.exception.JwtResponseMag;
import cn.jeffey.jwt_authority.exception.JwtSecurityException;
import cn.jeffey.jwt_authority.utils.TokenVerifyer;
import cn.jeffey.jwt_authority.utils.VerifyTokenResult;

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
    public boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission){
        String token = request.getHeader(AuthenizationStrategyManger.getHeaderParameterTokenName());
        if(token==null){
            throw new JwtSecurityException(JwtResponseMag.NoTokenError);
        }
        VerifyTokenResult verifyTokenResult = tokenVerifyer.verifyToken(token);
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
