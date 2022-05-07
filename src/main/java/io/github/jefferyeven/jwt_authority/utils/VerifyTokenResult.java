package io.github.jefferyeven.jwt_authority.utils;

import java.util.List;

public class VerifyTokenResult {
    private boolean passVerify;
    private List<String> authorities;

    public boolean isPassVerify() {
        return passVerify;
    }

    public void setPassVerify(boolean passVerify) {
        this.passVerify = passVerify;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
