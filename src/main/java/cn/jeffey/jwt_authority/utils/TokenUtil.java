package cn.jeffey.jwt_authority.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;

public class TokenUtil implements TokenVerifyer {

    // 过期时间是七天
    private static final long EXPIRE_TIME= 1000*60*60*24*7;
    private static final String ISSER = "auth0";
    private static final String TOKEN_SECRET="token123";  //密钥盐
    public final static JWTVerifier VERIFIER = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer(ISSER).build();
    /**
     * 签名生成
     * @return
     */
    public static String sign(String name, List<String> authorities){

        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer(ISSER)
                    .withClaim("username", name)
                    .withClaim("authorities", JSON.toJSONString(authorities))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;

    }


    @Override
    public VerifyTokenResult verifyToken(String token) {
        VerifyTokenResult verifyTokenResult = new VerifyTokenResult();
        try {
            DecodedJWT jwt = VERIFIER.verify(token);
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
