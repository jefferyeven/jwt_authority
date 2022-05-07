package io.github.jefferyeven.jwt_authority.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtSecurityTokenSetting {
    @Value("${jwt_token.expire_time:604800000}")
    private long expireTime;
    @Value("${jwt_token.isser:jwt_security}")
    private String isser;
    @Value("${jwt_token.token_secret:token123}")
    private String tokenSecret;

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getIsser() {
        return isser;
    }

    public void setIsser(String isser) {
        this.isser = isser;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
