package io.github.jefferyeven.jwt_authority.config;

import io.github.jefferyeven.jwt_authority.annoation.AnnoationHandler;
import io.github.jefferyeven.jwt_authority.authorization.AnnoationAuthorization;
import io.github.jefferyeven.jwt_authority.authorization.AuthorizationOrder;
import io.github.jefferyeven.jwt_authority.authorization.BasicAuthorization;
import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategyManger;
import io.github.jefferyeven.jwt_authority.bean.JwtSecurityTokenSetting;
import io.github.jefferyeven.jwt_authority.utils.JwtSecurityTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Order(100)
@ComponentScan(value = "io.github.jefferyeven.jwt_authority")
public abstract class JwtSecurityConfigAdapter {
    private final HttpConfig httpConfig;
    private final AuthorizationConfig authenicationConfig;
    @Autowired
    private JwtSecurityTokenSetting jwtSecurityTokenSetting;
    public JwtSecurityConfigAdapter(){
       authenicationConfig = new AuthorizationConfig();
        httpConfig = new HttpConfig();
    }

    /**
     * 配置url
     * @param httpConfig 配置url
     */
    public abstract void config(HttpConfig httpConfig);

    /**
     * 配置验证逻辑
     * @param authenicationConfig 配置验证逻辑
     */
    public void config(AuthorizationConfig authenicationConfig){
    }
    @Autowired
    private AnnoationHandler annoationHandler;

    public void initAuthorization(){
        if(authenicationConfig.getUseAnnoation()){
            annoationHandler.readAnnoation();
            AnnoationAuthorization annoationAuthorization = new AnnoationAuthorization(annoationHandler.getAnnoationPermissionUrl());
            AuthorizationOrder authenicationOrder = new AuthorizationOrder(annoationAuthorization,900);
            authenicationConfig.addAuthorization(authenicationOrder);
        }
        BasicAuthorization basicAuthorization = new BasicAuthorization(httpConfig.getUrlsPermissionList());
        authenicationConfig.addAuthorization(new AuthorizationOrder(basicAuthorization, 1000));
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public AuthorizationConfig getAuthorizationConfig() {
        return authenicationConfig;
    }

    @Bean
    public FilterRegistrationBean registrationBean() throws Exception {
        // 设置验证filter
        initAuthorization();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authenicationConfig.getJwtUrlsPermissionFilter());
        registration.addUrlPatterns("/*");
        registration.setName("JWTFilter");
        return registration;
    }

    @PostConstruct
    private void postConfig(){
        JwtSecurityTokenUtil.updateJwtSecurityTokenUtil(jwtSecurityTokenSetting);
        //设置默认的token verifyer
        AuthorizationStrategyManger.setStrategyTokenVerifyer(new JwtSecurityTokenUtil());
        // 先构建认证的相关配置
        config(authenicationConfig);
        // 根据验证规则初始化
        httpConfig.initConfig();
        // 再构建需要认证的url
        config(httpConfig);

    }

}
