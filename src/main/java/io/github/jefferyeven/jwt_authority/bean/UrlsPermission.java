package io.github.jefferyeven.jwt_authority.bean;

import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategyManger;
import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UrlsPermission {
    private final List<String> urls;
    private List<String> authorities;
    private PermissionLevel permissionLevel;
    private AuthorizationStrategy authorizationStrategy;

    public UrlsPermission(){
        urls = new ArrayList<>();
        authorities = new ArrayList<>();
        anonymous();

    }
    public UrlsPermission(Collection<String> urls){
        this.urls = new ArrayList<>(urls);
        authorities = new ArrayList<>();
        anonymous();
    }

    public void anonymous(){
        this.permissionLevel = PermissionLevel.HAVE_TOKEN;
        setAuthorizationStrategy(AuthorizationStrategyManger.getAuthorizationStrategyHaveToken());
    }

    public void permitAll(){
        this.permissionLevel = PermissionLevel.PERMISSION_All;
        setAuthorizationStrategy(AuthorizationStrategyManger.getAuthorizationStrategyPermissionAll());
    }
    public void denyAll(){
        this.permissionLevel = PermissionLevel.DENY_All;
        setAuthorizationStrategy(AuthorizationStrategyManger.getAuthorizationStrategyDenyAll());
    }
    public void haveToken(){
        this.permissionLevel = PermissionLevel.HAVE_TOKEN;
        setAuthorizationStrategy(AuthorizationStrategyManger.getAuthorizationStrategyHaveToken());
    }
    public void haveAnyAuthority(String ... authorities){
        this.permissionLevel = PermissionLevel.HAVE_ANY_AUTHORITY;
        this.authorities = new ArrayList<>(Arrays.asList(authorities));
        setAuthorizationStrategy(AuthorizationStrategyManger.getAuthorizationStrategyHaveAnyAuthority());
    }

    public void haveAllAuthority(String ... authorities){
        this.permissionLevel = PermissionLevel.HAVE_ALL_AUTHORITY;
        this.authorities = new ArrayList<>(Arrays.asList(authorities));
        setAuthorizationStrategy(AuthorizationStrategyManger.getAuthorizationStrategyHaveAllAuthority());
    }
    public void setAuthorizationStrategy (AuthorizationStrategy authorizationStrategy){
        this.authorizationStrategy = authorizationStrategy;
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

    public AuthorizationStrategy getAuthorizationStrategy() {
        return authorizationStrategy;
    }
}
