package io.github.jefferyeven.jwt_authority.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.jefferyeven.jwt_authority.bean.JwtSecurityTokenSetting;

import java.util.Date;
import java.util.List;

public class JwtSecurityTokenUtil implements TokenVerifyer {

    // 过期时间是七天
    private static long expireTime = 1000*60*60*24*7;
    private static String isser = "jwt_security";
    private static String tokenSecret = "token123";;  //密钥盐
    private static JWTVerifier verifier;
    public static void updateJwtSecurityTokenUtil(JwtSecurityTokenSetting jwtSecurityTokenSetting){
        expireTime = jwtSecurityTokenSetting.getExpireTime();
        tokenSecret = jwtSecurityTokenSetting.getTokenSecret();
        isser = jwtSecurityTokenSetting.getIsser();
        verifier = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer(isser).build();
    }

    /**
     * 签名生成
     * @return
     */
    public static String sign(String name, List<String> authorities){

        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + expireTime);
            token = JWT.create()
                    .withIssuer(isser)
                    .withClaim("username", name)
                    .withClaim("authorities", JSON.toJSONString(authorities))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;

    }


    @Override
    public VerifyTokenResult verifyToken(String token) {
        VerifyTokenResult verifyTokenResult = new VerifyTokenResult();
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
}
