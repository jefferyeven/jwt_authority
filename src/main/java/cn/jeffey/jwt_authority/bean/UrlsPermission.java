package cn.jeffey.jwt_authority.bean;

import cn.jeffey.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
import cn.jeffey.jwt_authority.authentization_strategy.AuthentizationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UrlsPermission {
    private final List<String> urls;
    private List<String> authorities;
    private PermissionLevel permissionLevel;
    private AuthentizationStrategy authenizationStrategy;

    public UrlsPermission(){
        urls = new ArrayList<>();
        authorities = new ArrayList<>();
        anonymous();
        authenizationStrategy = AuthenizationStrategyManger.getAuthentizationStrategyHaveToken();

    }
    public UrlsPermission(Collection<String> urls){
        this.urls = new ArrayList<>(urls);
        authorities = new ArrayList<>();
        anonymous();
    }

    public void anonymous(){
        this.permissionLevel = PermissionLevel.HAVE_TOKEN;
    }

    public void permitAll(){
        this.permissionLevel = PermissionLevel.PERMISSION_All;
        setAuthenizationStrategy(AuthenizationStrategyManger.getAuthentizationStrategyPermissionAll());
    }
    public void denyAll(){
        this.permissionLevel = PermissionLevel.DENY_All;
        setAuthenizationStrategy(AuthenizationStrategyManger.getAuthentizationStrategyDenyAll());
    }
    public void haveToken(){
        this.permissionLevel = PermissionLevel.HAVE_TOKEN;
        setAuthenizationStrategy(AuthenizationStrategyManger.getAuthentizationStrategyHaveToken());
    }
    public void haveAnyAuthority(String ... authorities){
        this.permissionLevel = PermissionLevel.HAVE_ANY_AUTHORITY;
        this.authorities = new ArrayList<>(Arrays.asList(authorities));
        setAuthenizationStrategy(AuthenizationStrategyManger.getAuthenizationStrategyHaveAnyAuthority());
    }

    public void haveAllAuthority(String ... authorities){
        this.permissionLevel = PermissionLevel.HAVE_ALL_AUTHORITY;
        this.authorities = new ArrayList<>(Arrays.asList(authorities));
        setAuthenizationStrategy(AuthenizationStrategyManger.getAuthenizationStrategyHaveAllAuthority());
    }
    public void setAuthenizationStrategy (AuthentizationStrategy authenizationStrategy){
        this.authenizationStrategy = authenizationStrategy;
    }

    public List<String> getUrls() {
        return urls;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public AuthentizationStrategy getAuthenizationStrategy() {
        return authenizationStrategy;
    }
}
