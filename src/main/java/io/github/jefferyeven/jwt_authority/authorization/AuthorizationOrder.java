package io.github.jefferyeven.jwt_authority.authorization;

public class AuthorizationOrder {
    private AbstractAuthorization abstractAuthorization;
    private Integer order;

    public AuthorizationOrder(AbstractAuthorization abstractAuthorization, Integer order) {
        this.abstractAuthorization = abstractAuthorization;
        this.order = order;
    }
    public AuthorizationOrder(){
    }

    public AbstractAuthorization getAbstractAuthorization() {
        return abstractAuthorization;
    }

    public void setAbstractAuthorization(AbstractAuthorization abstractAuthorization) {
        this.abstractAuthorization = abstractAuthorization;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
