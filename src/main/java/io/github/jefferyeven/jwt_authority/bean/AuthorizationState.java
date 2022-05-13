package io.github.jefferyeven.jwt_authority.bean;

public enum AuthorizationState {
    PassState(1),
    NoPassState(-1),
    UnAuthenizateState(0)
    ;
    AuthorizationState(int state){
        this.state = state;
    }
    private final int state;
    public int getState() {
        return state;
    }
}
