package io.github.jefferyeven.jwt_authority.config;

import io.github.jefferyeven.jwt_authority.authorization.AbstractAuthorization;
import io.github.jefferyeven.jwt_authority.authorization.AuthorizationOrder;
import io.github.jefferyeven.jwt_authority.authorization_strategy.AuthorizationStrategyManger;
import io.github.jefferyeven.jwt_authority.filter.JwtUrlsPermissionFailureHandler;
import io.github.jefferyeven.jwt_authority.filter.JwtUrlsPermissionFilter;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;

import javax.servlet.Filter;
import java.util.PriorityQueue;

public class AuthorizationConfig {
    private final PriorityQueue<AuthorizationOrder> authenicationOrders;
    private boolean useAnnoation;
    private JwtUrlsPermissionFilter jwtUrlsPermissionFilter = new JwtUrlsPermissionFilter();
    public AuthorizationConfig(){
        authenicationOrders = new PriorityQueue<>((o1, o2) -> {
            if(o1.getOrder()==null){
                return 1;
            }
            if(o2.getOrder()==null){
                return -1;
            }
            return o1.getOrder()-o2.getOrder();
        });
    }
    public void setUseAnnoation(boolean useAnnoation) {
        this.useAnnoation = useAnnoation;
    }
    public boolean getUseAnnoation(){
        return useAnnoation;
    }
    public void setStrategyTokenVerifyer(TokenVerifyer tokenVerifyer){
        AuthorizationStrategyManger.setStrategyTokenVerifyer(tokenVerifyer);
    }
    public void addAuthorization(AbstractAuthorization authenication,Integer order){
        authenicationOrders.add(new AuthorizationOrder(authenication,order));
    }
    public void addAuthorization(AuthorizationOrder authenicationOrder){
        authenicationOrders.add(authenicationOrder);
    }

    public AbstractAuthorization getStartAuthorization(){
        AbstractAuthorization startAuthorization = authenicationOrders.poll().getAbstractAuthorization();
        AbstractAuthorization curAuthorization = startAuthorization;
        while (!authenicationOrders.isEmpty()){
            AbstractAuthorization tempAuthorization = authenicationOrders.poll().getAbstractAuthorization();
            curAuthorization.setNextAuthenizate(tempAuthorization);
            curAuthorization = tempAuthorization;
        }
        return startAuthorization;
    }

    public void setJwtUrlsPermissionFailureHandler(JwtUrlsPermissionFailureHandler failureHandler){
        jwtUrlsPermissionFilter.setJwtUrlsPermissionFailureHandler(failureHandler);
    }
    public Filter getJwtUrlsPermissionFilter() {
        jwtUrlsPermissionFilter.setStartAuthorization(getStartAuthorization());
        return jwtUrlsPermissionFilter;
    }

    public void setJwtUrlsPermissionFilter(JwtUrlsPermissionFilter jwtUrlsPermissionFilter) {
        this.jwtUrlsPermissionFilter = jwtUrlsPermissionFilter;
    }
}
