package io.github.jefferyeven.jwt_authority.config;

import io.github.jefferyeven.jwt_authority.authenization.AbstractAuthenization;
import io.github.jefferyeven.jwt_authority.authenization.AuthenizationOrder;
import io.github.jefferyeven.jwt_authority.authentization_strategy.AuthenizationStrategyManger;
import io.github.jefferyeven.jwt_authority.filter.JwtUrlsPermissionFailureHandler;
import io.github.jefferyeven.jwt_authority.filter.JwtUrlsPermissionFilter;
import io.github.jefferyeven.jwt_authority.utils.TokenVerifyer;

import javax.servlet.Filter;
import java.util.PriorityQueue;

public class AuthenizationConfig {
    private final PriorityQueue<AuthenizationOrder> authenizationOrders;
    private boolean useAnnoation;
    private JwtUrlsPermissionFilter jwtUrlsPermissionFilter = new JwtUrlsPermissionFilter();
    public AuthenizationConfig(){
        authenizationOrders = new PriorityQueue<>((o1, o2) -> {
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
        AuthenizationStrategyManger.setStrategyTokenVerifyer(tokenVerifyer);
    }
    public void addAuthenization(AbstractAuthenization authenization,Integer order){
        authenizationOrders.add(new AuthenizationOrder(authenization,order));
    }
    public void addAuthenization(AuthenizationOrder authenizationOrder){
        authenizationOrders.add(authenizationOrder);
    }

    public AbstractAuthenization getStartAuthenization(){
        AbstractAuthenization startAuthenization = authenizationOrders.poll().getAbstractAuthenization();
        AbstractAuthenization curAuthenization = startAuthenization;
        while (!authenizationOrders.isEmpty()){
            AbstractAuthenization tempAuthenization = authenizationOrders.poll().getAbstractAuthenization();
            curAuthenization.setNextAuthenizate(tempAuthenization);
            curAuthenization = tempAuthenization;
        }
        return startAuthenization;
    }

    public void setJwtUrlsPermissionFailureHandler(JwtUrlsPermissionFailureHandler failureHandler){
        jwtUrlsPermissionFilter.setJwtUrlsPermissionFailureHandler(failureHandler);
    }
    public Filter getJwtUrlsPermissionFilter() {
        jwtUrlsPermissionFilter.setStartAuthenization(getStartAuthenization());
        return jwtUrlsPermissionFilter;
    }

    public void setJwtUrlsPermissionFilter(JwtUrlsPermissionFilter jwtUrlsPermissionFilter) {
        this.jwtUrlsPermissionFilter = jwtUrlsPermissionFilter;
    }
}
