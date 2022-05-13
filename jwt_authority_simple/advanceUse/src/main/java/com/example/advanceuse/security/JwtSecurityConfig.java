package com.example.advanceuse.security;

import io.github.jefferyeven.jwt_authority.config.AuthorizationConfig;
import io.github.jefferyeven.jwt_authority.config.HttpConfig;
import io.github.jefferyeven.jwt_authority.config.JwtSecurityConfigAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecurityConfig extends JwtSecurityConfigAdapter {
    @Autowired
    DynamicAuthorization dynamicAuthorization;
    @Autowired
    NewAuthorizationStrategy newAuthorizationStrategy;
    @Override
    public void config(HttpConfig httpConfig) {
        httpConfig.addConfigUrlsPermission("/admin/*").haveAnyAuthority("admin");
        httpConfig.addConfigUrlsPermission("/user/*").haveAnyAuthority("user","admin");
        httpConfig.addConfigUrlsPermission("index/*").permitAll();
        httpConfig.addConfigUrlsPermission("home/*").setAuthorizationStrategy(newAuthorizationStrategy);
        // 如果没有设置我们就默认允许
        httpConfig.getDefaultUrlConfig().permitAll();
    }

    @Override
    public void config(AuthorizationConfig authorizationConfig){
        authorizationConfig.setStrategyTokenVerifyer(new TokenUtil());
        // 开启注解
        authorizationConfig.setUseAnnoation(true);
        // 设置失败处理器
        authorizationConfig.setJwtUrlsPermissionFailureHandler(new UrlFailureHandler());
        // 设置自定义的鉴权器
        authorizationConfig.addAuthorization(dynamicAuthorization,1);
    }

}
