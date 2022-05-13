package io.github.jefferyeven.jwt_authority.authorization;

import io.github.jefferyeven.jwt_authority.bean.AuthorizationState;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAuthorization {

    AbstractAuthorization nextAuthenizate;

    public void setNextAuthenizate(AbstractAuthorization nextAuthenizate) {
        this.nextAuthenizate = nextAuthenizate;
    }

    public boolean doAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {


        AuthorizationState passAuthorization = passAuthenizate(request,response);
        if(passAuthorization!=AuthorizationState.UnAuthenizateState){
           if(passAuthorization==AuthorizationState.PassState){
               return true;
           }
           if(passAuthorization==AuthorizationState.NoPassState){
               return false;
           }
        }
        if(nextAuthenizate==null){
            throw new JwtSecurityException(JwtResponseMag.NoFindAuthorizationUrl);
        }
        return nextAuthenizate.doAuthenizate(request,response);
    }

    public abstract AuthorizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
