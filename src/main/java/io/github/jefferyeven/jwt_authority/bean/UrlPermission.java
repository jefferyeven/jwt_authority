package io.github.jefferyeven.jwt_authority.bean;

import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthentizationStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
