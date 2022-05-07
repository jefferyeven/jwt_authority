package io.github.jefferyeven.jwt_authority.bean;

import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthentizationStrategy;

import java.util.List;

public class UrlPermission {
    private String url;
    private List<String> authorities;
    private PermissionLevel permissionLevel;
    private AuthentizationStrategy authenizationStrategy;
    public UrlPermission(){
    }

    public UrlPermission(String url, List<String> authorities, PermissionLevel permissionLevel, AuthentizationStrategy authenizationStrategy) {
        this.url = url;
        this.authorities = authorities;
        this.permissionLevel = permissionLevel;
        this.authenizationStrategy = authenizationStrategy;
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

    public AuthentizationStrategy getAuthenizationStrategy() {
        return authenizationStrategy;
    }

    public void setAuthenizationStrategy(AuthentizationStrategy authenizationStrategy) {
        this.authenizationStrategy = authenizationStrategy;
    }
}
