package cn.jeffey.jwt_authority.bean;

public enum AuthenizationState {
    PassState(1),
    NoPassState(-1),
    UnAuthenizateState(0)
    ;
    AuthenizationState(int state){
        this.state = state;
    }
    private final int state;
    public int getState() {
        return state;
    }
}
