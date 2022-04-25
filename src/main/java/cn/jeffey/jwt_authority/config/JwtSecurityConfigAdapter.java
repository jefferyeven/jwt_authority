package cn.jeffey.jwt_authority.config;

import cn.jeffey.jwt_authority.annoation.AnnoationHandler;
import cn.jeffey.jwt_authority.authenization.AnnoationAuthenization;
import cn.jeffey.jwt_authority.authenization.AuthenizationOrder;
import cn.jeffey.jwt_authority.authenization.BasicAuthenization;
import cn.jeffey.jwt_authority.filter.JwtUrlsPermissionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Order(100)
public abstract class JwtSecurityConfigAdapter {
    private final HttpConfig httpConfig;
    private final AuthenizationConfig authenizationConfig;

    public JwtSecurityConfigAdapter(){
       httpConfig = new HttpConfig();
       authenizationConfig = new AuthenizationConfig();
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



    @Bean
    public FilterRegistrationBean registrationBean() throws Exception {
        // 设置验证filter
        initAuthenization();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new JwtUrlsPermissionFilter(authenizationConfig.startAuthenization()));
        registration.addUrlPatterns("/*");
        registration.setName("JWTFilter");
        return registration;
    }

    @PostConstruct
    private void postConfig(){
        config(httpConfig);
        config(authenizationConfig);
    }

}
