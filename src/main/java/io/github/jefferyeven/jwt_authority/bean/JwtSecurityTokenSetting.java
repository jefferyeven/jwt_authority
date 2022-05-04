package io.github.jefferyeven.jwt_authority.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtSecurityTokenSetting {
    @Value("${jwt_token.expire_time:604800000}")
    private long expireTime;
    @Value("${jwt_token.isser:jwt_security}")
    private String isser;
    @Value("${jwt_token.token_secret:token123}")
    private String tokenSecret;
}
