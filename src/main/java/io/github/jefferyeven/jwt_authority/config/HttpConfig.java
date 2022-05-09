package io.github.jefferyeven.jwt_authority.config;

import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
import io.github.jefferyeven.jwt_authority.bean.UrlsPermission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HttpConfig {
    private final List<UrlsPermission> urlsPermissionList;

    public HttpConfig(){
        urlsPermissionList = new ArrayList<>();
    }
    public void initConfig(){
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
    private UrlsPermission defaultUrlConfig;
    public UrlsPermission getDefaultUrlConfig(){
        return defaultUrlConfig;
    }

    public void setHeaderParameterTokenName(String headerParameterTokenName) {
        AuthenizationStrategyManger.setHeaderParameterTokenName(headerParameterTokenName);
    }
}
