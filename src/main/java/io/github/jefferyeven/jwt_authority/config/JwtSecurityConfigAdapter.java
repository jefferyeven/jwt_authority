package io.github.jefferyeven.jwt_authority.config;

import io.github.jefferyeven.jwt_authority.annoation.AnnoationHandler;
import io.github.jefferyeven.jwt_authority.authenization.AnnoationAuthenization;
import io.github.jefferyeven.jwt_authority.authenization.AuthenizationOrder;
import io.github.jefferyeven.jwt_authority.authenization.BasicAuthenization;
import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
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
    private final AuthenizationConfig authenizationConfig;
    @Autowired
    private JwtSecurityTokenSetting jwtSecurityTokenSetting;
    public JwtSecurityConfigAdapter(){
       authenizationConfig = new AuthenizationConfig();
        httpConfig = new HttpConfig();
    }

    /**
     * 配置url
     * @param httpConfig 配置url
     */
    public abstract void config(HttpConfig httpConfig);

    /**
     * 配置验证逻辑
     * @param authenizationConfig 配置验证逻辑
     */
    public void config(AuthenizationConfig authenizationConfig){
    }
    @Autowired
    private AnnoationHandler annoationHandler;

    public void initAuthenization(){
        if(authenizationConfig.getUseAnnoation()){
            annoationHandler.readAnnoation();
            AnnoationAuthenization annoationAuthenization = new AnnoationAuthenization(annoationHandler.getAnnoationPermissionUrl());
            AuthenizationOrder authenizationOrder = new AuthenizationOrder(annoationAuthenization,900);
            authenizationConfig.addAuthenization(authenizationOrder);
        }
        BasicAuthenization basicAuthenization = new BasicAuthenization(httpConfig.getUrlsPermissionList());
        authenizationConfig.addAuthenization(new AuthenizationOrder(basicAuthenization, 1000));
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public AuthenizationConfig getAuthenizationConfig() {
        return authenizationConfig;
    }

    @Bean
    public FilterRegistrationBean registrationBean() throws Exception {
        // 设置验证filter
        initAuthenization();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authenizationConfig.getJwtUrlsPermissionFilter());
        registration.addUrlPatterns("/*");
        registration.setName("JWTFilter");
        return registration;
    }

    @PostConstruct
    private void postConfig(){
        JwtSecurityTokenUtil.updateJwtSecurityTokenUtil(jwtSecurityTokenSetting);
        //设置默认的token verifyer
        AuthenizationStrategyManger.setStrategyTokenVerifyer(new JwtSecurityTokenUtil());
        // 先构建认证的相关配置
        config(authenizationConfig);
        // 根据验证规则初始化
        httpConfig.initConfig();
        // 再构建需要认证的url
        config(httpConfig);

    }

}
