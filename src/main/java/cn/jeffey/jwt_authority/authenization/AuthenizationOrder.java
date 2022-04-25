package cn.jeffey.jwt_authority.authenization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenizationOrder {
    private AbstractAuthenization abstractAuthenization;
    private Integer order;

    public AuthenizationOrder(AbstractAuthenization abstractAuthenization, Integer order) {
        this.abstractAuthenization = abstractAuthenization;
        this.order = order;
    }
    public AuthenizationOrder(){
    }
}
