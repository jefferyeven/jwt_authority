package io.github.jefferyeven.jwt_authority.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TokenVerifyer {
    VerifyTokenResult verifyToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
