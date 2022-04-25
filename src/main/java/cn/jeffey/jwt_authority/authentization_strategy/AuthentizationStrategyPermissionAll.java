package cn.jeffey.jwt_authority.authentization_strategy;

import cn.jeffey.jwt_authority.bean.UrlPermission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthentizationStrategyPermissionAll implements AuthentizationStrategy {
    @Override
    public boolean passAuthentization(HttpServletRequest request, HttpServletResponse response, UrlPermission urlPermission){
        String url = request.getRequestURI();
        System.out.println(url);
        return true;
    }
}
