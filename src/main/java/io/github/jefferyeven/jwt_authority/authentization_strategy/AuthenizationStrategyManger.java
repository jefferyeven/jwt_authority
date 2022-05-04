package io.github.jefferyeven.jwt_authority.authentization_strategy;

import io.github.jefferyeven.jwt_authority.bean.PermissionLevel;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;
import org.springframework.stereotype.Component;

@Component
public class AuthenizationStrategyManger {
    private static AuthentizationStrategy authentizationStrategyDenyAll;
    private static AuthentizationStrategy authentizationStrategyHaveToken;
    private static AuthentizationStrategy authentizationStrategyPermissionAll;
    private static AuthentizationStrategy authenizationStrategyHaveAnyAuthority;
    private static AuthentizationStrategy authenizationStrategyHaveAllAuthority;

    private static TokenVerifyer strategyTokenVerifyer;
    private static String headerParameterTokenName;

    public static AuthentizationStrategy getAuthenizationByPermissionLevel(PermissionLevel permissionLevel){
        AuthentizationStrategy authentizationStrategy;
        switch (permissionLevel){
            case PERMISSION_All:
                return getAuthentizationStrategyPermissionAll();
            case DENY_All:
                return getAuthentizationStrategyDenyAll();
            case HAVE_TOKEN:
                return getAuthentizationStrategyHaveToken();
            case HAVE_ANY_AUTHORITY:
                return getAuthenizationStrategyHaveAnyAuthority();
            case HAVE_ALL_AUTHORITY:
                return getAuthenizationStrategyHaveAllAuthority();
            default:
                return getAuthentizationStrategyHaveToken();
        }
    }
    public static AuthentizationStrategy getAuthentizationStrategyDenyAll() {
        if(authentizationStrategyDenyAll==null){
            authentizationStrategyDenyAll = new AuthentizationStrategyDenyAll();
        }
        return authentizationStrategyDenyAll;
    }

    public static AuthentizationStrategy getAuthentizationStrategyHaveToken() {
        if(authentizationStrategyHaveToken==null){
            authentizationStrategyHaveToken = new AuthentizationStrategyHaveToken(strategyTokenVerifyer);
        }
        return authentizationStrategyHaveToken;
    }

    public static AuthentizationStrategy getAuthentizationStrategyPermissionAll() {
        if(authentizationStrategyPermissionAll==null){
            authentizationStrategyPermissionAll = new AuthentizationStrategyPermissionAll();
        }
        return authentizationStrategyPermissionAll;
    }
    public static AuthentizationStrategy getAuthenizationStrategyHaveAnyAuthority(){
        if(authenizationStrategyHaveAnyAuthority==null){
            authenizationStrategyHaveAnyAuthority = new AuthenizationStrategyHaveAnyAuthority(strategyTokenVerifyer);
        }
        return authenizationStrategyHaveAnyAuthority;
    }
    public static AuthentizationStrategy getAuthenizationStrategyHaveAllAuthority(){
        if(authenizationStrategyHaveAllAuthority==null){
            authenizationStrategyHaveAllAuthority = new AuthenizationStrategyHaveAllAuthority(strategyTokenVerifyer);
        }
        return authenizationStrategyHaveAllAuthority;
    }

    public static TokenVerifyer getStrategyTokenVerifyer() {
        return strategyTokenVerifyer;
    }

    public static void setStrategyTokenVerifyer(TokenVerifyer strategyTokenVerifyer) {
        AuthenizationStrategyManger.strategyTokenVerifyer = strategyTokenVerifyer;
    }

    public static String getHeaderParameterTokenName() {
        return headerParameterTokenName;
    }

    public static void setHeaderParameterTokenName(String headerParameterTokenName) {
        AuthenizationStrategyManger.headerParameterTokenName = headerParameterTokenName;
    }
}
