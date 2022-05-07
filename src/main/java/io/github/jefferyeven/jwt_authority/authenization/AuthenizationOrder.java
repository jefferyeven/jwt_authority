package io.github.jefferyeven.jwt_authority.authenization;

public class AuthenizationOrder {
    private AbstractAuthenization abstractAuthenization;
    private Integer order;

    public AuthenizationOrder(AbstractAuthenization abstractAuthenization, Integer order) {
        this.abstractAuthenization = abstractAuthenization;
        this.order = order;
    }
    public AuthenizationOrder(){
    }

    public AbstractAuthenization getAbstractAuthenization() {
        return abstractAuthenization;
    }

    public void setAbstractAuthenization(AbstractAuthenization abstractAuthenization) {
        this.abstractAuthenization = abstractAuthenization;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
