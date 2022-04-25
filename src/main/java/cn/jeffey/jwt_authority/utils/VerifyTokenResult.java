package cn.jeffey.jwt_authority.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VerifyTokenResult {
    private boolean passVerify;
    private List<String> authorities;
}
