package io.github.jefferyeven.jwt_authority.bean;

import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategy;

import java.util.List;

public class UrlPermission {
    private String url;
    private List<String> authorities;
    private PermissionLevel permissionLevel;
    private AuthorizationStrategy authorizationStrategy;
    public UrlPermission(){
    }

    public UrlPermission(String url, List<String> authorities, PermissionLevel permissionLevel, AuthorizationStrategy authorizationStrategy) {
        this.url = url;
        this.authorities = authorities;
        this.permissionLevel = permissionLevel;
        this.authorizationStrategy = authorizationStrategy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public AuthorizationStrategy getAuthorizationStrategy() {
        return authorizationStrategy;
    }

    public void setAuthorizationStrategy(AuthorizationStrategy authorizationStrategy) {
        this.authorizationStrategy = authorizationStrategy;
    }
}
