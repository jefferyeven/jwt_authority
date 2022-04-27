package cn.jeffey.jwt_authority.config;

import cn.jeffey.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
import cn.jeffey.jwt_authority.bean.UrlsPermission;
import cn.jeffey.jwt_authority.utils.TokenUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HttpConfig {
    private final List<UrlsPermission> urlsPermissionList;

    public HttpConfig(){
        AuthenizationStrategyManger.setStrategyTokenVerifyer(new TokenUtil());
        String headerParameterTokenName = "JwtToken";
        AuthenizationStrategyManger.setHeaderParameterTokenName(headerParameterTokenName);
        urlsPermissionList = new ArrayList<>();
        defaultUrlConfig = addConfigUrlsPermission("/*");
        defaultUrlConfig.haveToken();
        urlsPermissionList.add(defaultUrlConfig);
    }


    /**
     * 得到所有权限列表
     * @return list
     */
    public List<UrlsPermission> getUrlsPermissionList() {
        return urlsPermissionList;
    }

    /**
     * 设置多个url的权限
     * @param urls 集合类型
     * @return UrlsPermission
     */
    public UrlsPermission addConfigUrlsPermission(Collection<String> urls){
        UrlsPermission urlsPermission = new UrlsPermission();
        urlsPermissionList.add(urlsPermission);
        return urlsPermission;
    }

    /**
     * 设置多个url的权限
     * @param urls 多个参数类型
     * @return UrlsPermission
     */
    public UrlsPermission addConfigUrlsPermission(String ...urls){
        UrlsPermission urlsPermission = new UrlsPermission(Arrays.asList(urls));
        urlsPermissionList.add(urlsPermission);
        return urlsPermission;
    }
    public UrlsPermission addConfigUrlsPermission(UrlsPermission urlsPermission){
        urlsPermissionList.add(urlsPermission);
        return urlsPermission;
    }
    private final UrlsPermission defaultUrlConfig;
    public UrlsPermission getDefaultUrlConfig(){
        return defaultUrlConfig;
    }

    public void setHeaderParameterTokenName(String headerParameterTokenName) {
        AuthenizationStrategyManger.setHeaderParameterTokenName(headerParameterTokenName);
    }
}
