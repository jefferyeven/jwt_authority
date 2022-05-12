package com.example.recommendeduse.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;
import io.github.jefferyeven.jwt_authority.utils.VerifyTokenResult;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
@Component
public class TokenUtil implements TokenVerifyer {
    public String salt = "fdelsfjdo.243";
    public long expireTime = 100000000;
    String isser = "test";
    private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(salt)).withIssuer(isser).build();
    @Override
    public VerifyTokenResult verifyToken(HttpServletRequest request, HttpServletResponse response) {
        VerifyTokenResult verifyTokenResult = new VerifyTokenResult();
        // 从head读取token
        String tokenName = "token";
        String token = request.getHeader(tokenName);
//        // 从session读取token
//        HttpSession httpSession = request.getSession();
//        token = (String) httpSession.getAttribute(tokenName);
//        // 从cookie读取token
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie:cookies){
//            System.out.println(cookie.getName());
//            if(cookie.getName().equals(tokenName)){
//                token = cookie.getValue();
//                System.out.println(token);
//                break;
//            }
//        }
//        // 从request读取token
//        token = request.getHeader(tokenName);

        if(token==null){
            throw new JwtSecurityException(JwtResponseMag.NoTokenError);
        }
        try {
            DecodedJWT jwt = verifier.verify(token);
            verifyTokenResult.setPassVerify(true);
            Claim authoritiesClaim =  jwt.getClaims().get("authorities");
            if(authoritiesClaim==null){
                return verifyTokenResult;
            }
            List<String> authorities = JSONObject.parseArray(authoritiesClaim.asString(),String.class);
            verifyTokenResult.setAuthorities(authorities);
        } catch (Exception e){
            verifyTokenResult.setPassVerify(false);
        }
        return verifyTokenResult;
    }
    public String sign(String userid, String name, List<String> authorities){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + expireTime);
            token = JWT.create()
                    .withIssuer(isser)
                    .withClaim("userid",userid)
                    .withClaim("username", name)
                    .withClaim("authorities", JSON.toJSONString(authorities))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(salt));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
}
