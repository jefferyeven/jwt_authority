package io.github.jefferyeven.jwt_authority.utils;

public interface TokenVerifyer {
    VerifyTokenResult verifyToken(String token);
}
