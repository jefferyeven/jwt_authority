package io.github.jefferyeven.jwt_authority;

import io.github.jefferyeven.jwt_authority.config.AuthenizationConfig;
import io.github.jefferyeven.jwt_authority.config.HttpConfig;
import io.github.jefferyeven.jwt_authority.config.JwtSecurityConfigAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecurityConfig extends JwtSecurityConfigAdapter {
    public  boolean haveSqlUpdate = true;

    public final String[] excludeUrls = {"/login","/register","/doLogin","/test/hello"};

    @Autowired
    DynamicAuthenization dynamicAuthenization;
    @Autowired
    MyTokenUtil myTokenUtil;
    /**
     * 因为我们的验证是交与jwt来做，所有我们只需要鉴权
     */
    @Override
    public void config(HttpConfig httpConfig){
        //更改默认的鉴权策略
//        httpConfig.getDefaultUrlConfig().haveToken();
        // 配置基础需要鉴权的url
        // 所有允许的url
        httpConfig.addConfigUrlsPermission(excludeUrls).permitAll();
        httpConfig.addConfigUrlsPermission("/user/*").haveAnyAuthority("user","admin");
        httpConfig.addConfigUrlsPermission("/admin/*").haveAnyAuthority("admin");
        httpConfig.setHeaderParameterTokenName("token");
    }

    @Override
    public void config(AuthenizationConfig authenizationConfig){
        // 添加自定义authenization
        authenizationConfig.addAuthenization(dynamicAuthenization,1);
        // 开启注解鉴权
        authenizationConfig.setUseAnnoation(true);
        authenizationConfig.setStrategyTokenVerifyer(myTokenUtil);
        System.out.println(myTokenUtil);
    }
}
