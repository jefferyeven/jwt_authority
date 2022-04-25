package cn.jeffey.jwt_authority.authentization_strategy;

import cn.jeffey.jwt_authority.bean.UrlPermission;
import cn.jeffey.jwt_authority.exception.JwtResponseMag;
import cn.jeffey.jwt_authority.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthentizationStrategyDenyAll implements AuthentizationStrategy{
    @Override
    public boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission){
        throw new JwtSecurityException(JwtResponseMag.DenyRequestError);
    }
}
