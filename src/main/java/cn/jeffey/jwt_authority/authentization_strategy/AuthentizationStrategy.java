package cn.jeffey.jwt_authority.authentization_strategy;

import cn.jeffey.jwt_authority.bean.UrlPermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface AuthentizationStrategy {
    boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission)throws Exception;
}
