package cn.jeffey.jwt_authority.config;

import cn.jeffey.jwt_authority.authenization.AbstractAuthenization;
import cn.jeffey.jwt_authority.authenization.AuthenizationOrder;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AuthenizationConfig {
    private final PriorityQueue<AuthenizationOrder> authenizationOrders;
    private boolean useAnnoation;
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
    public void addAuthenization(AbstractAuthenization authenization,Integer order){
        authenizationOrders.add(new AuthenizationOrder(authenization,order));
    }
    public void addAuthenization(AuthenizationOrder authenizationOrder){
        authenizationOrders.add(authenizationOrder);
    }
    public AbstractAuthenization startAuthenization(){
        AbstractAuthenization startAuthenization = authenizationOrders.poll().getAbstractAuthenization();
        AbstractAuthenization curAuthenization = startAuthenization;
        while (!authenizationOrders.isEmpty()){
            AbstractAuthenization tempAuthenization = authenizationOrders.poll().getAbstractAuthenization();
            curAuthenization.setNextAuthenizate(tempAuthenization);
            curAuthenization = tempAuthenization;
        }
        return startAuthenization;
    }

}
