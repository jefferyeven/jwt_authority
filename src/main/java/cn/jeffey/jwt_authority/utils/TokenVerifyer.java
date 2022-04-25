package cn.jeffey.jwt_authority.utils;

public interface TokenVerifyer {
    VerifyTokenResult verifyToken(String token);
}
