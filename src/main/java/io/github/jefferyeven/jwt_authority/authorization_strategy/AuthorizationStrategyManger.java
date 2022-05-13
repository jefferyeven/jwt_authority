package io.github.jefferyeven.jwt_authority.authorization_strategy;

import io.github.jefferyeven.jwt_authority.bean.PermissionLevel;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationStrategyManger {
    private static AuthorizationStrategy authorizationStrategyDenyAll;
    private static AuthorizationStrategy authorizationStrategyHaveToken;
    private static AuthorizationStrategy authorizationStrategyPermissionAll;
    private static AuthorizationStrategy authorizationStrategyHaveAnyAuthority;
    private static AuthorizationStrategy authorizationStrategyHaveAllAuthority;

    private static TokenVerifyer strategyTokenVerifyer;
    private static String headerParameterTokenName;

    public static AuthorizationStrategy getAuthorizationByPermissionLevel(PermissionLevel permissionLevel){
        AuthorizationStrategy authorizationStrategy;
        switch (permissionLevel){
            case PERMISSION_All:
                return getAuthorizationStrategyPermissionAll();
            case DENY_All:
                return getAuthorizationStrategyDenyAll();
            case HAVE_TOKEN:
                return getAuthorizationStrategyHaveToken();
            case HAVE_ANY_AUTHORITY:
                return getAuthorizationStrategyHaveAnyAuthority();
            case HAVE_ALL_AUTHORITY:
                return getAuthorizationStrategyHaveAllAuthority();
            default:
                return getAuthorizationStrategyHaveToken();
        }
    }
    public static AuthorizationStrategy getAuthorizationStrategyDenyAll() {
        if(authorizationStrategyDenyAll==null){
            authorizationStrategyDenyAll = new AuthorizationStrategyDenyAll();
        }
        return authorizationStrategyDenyAll;
    }

    public static AuthorizationStrategy getAuthorizationStrategyHaveToken() {
        if(authorizationStrategyHaveToken==null){
            authorizationStrategyHaveToken = new AuthorizationStrategyHaveToken(strategyTokenVerifyer);
        }
        return authorizationStrategyHaveToken;
    }

    public static AuthorizationStrategy getAuthorizationStrategyPermissionAll() {
        if(authorizationStrategyPermissionAll==null){
            authorizationStrategyPermissionAll = new AuthorizationStrategyPermissionAll();
        }
        return authorizationStrategyPermissionAll;
    }
    public static AuthorizationStrategy getAuthorizationStrategyHaveAnyAuthority(){
        if(authorizationStrategyHaveAnyAuthority==null){
            authorizationStrategyHaveAnyAuthority = new AuthorizationStrategyHaveAnyAuthority(strategyTokenVerifyer);
        }
        return authorizationStrategyHaveAnyAuthority;
    }
    public static AuthorizationStrategy getAuthorizationStrategyHaveAllAuthority(){
        if(authorizationStrategyHaveAllAuthority==null){
            authorizationStrategyHaveAllAuthority = new AuthorizationStrategyHaveAllAuthority(strategyTokenVerifyer);
        }
        return authorizationStrategyHaveAllAuthority;
    }

    public static TokenVerifyer getStrategyTokenVerifyer() {
        return strategyTokenVerifyer;
    }

    public static void setStrategyTokenVerifyer(TokenVerifyer strategyTokenVerifyer) {
        AuthorizationStrategyManger.strategyTokenVerifyer = strategyTokenVerifyer;
    }

    public static String getHeaderParameterTokenName() {
        return headerParameterTokenName;
    }

    public static void setHeaderParameterTokenName(String headerParameterTokenName) {
        AuthorizationStrategyManger.headerParameterTokenName = headerParameterTokenName;
    }
}
