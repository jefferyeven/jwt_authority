package io.github.jefferyeven.jwt_authority.authenization;

import io.github.jefferyeven.jwt_authority.bean.AuthenizationState;
import io.github.jefferyeven.jwt_authority.exception.JwtResponseMag;
import io.github.jefferyeven.jwt_authority.exception.JwtSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAuthenization {

    AbstractAuthenization nextAuthenizate;

    public void setNextAuthenizate(AbstractAuthenization nextAuthenizate) {
        this.nextAuthenizate = nextAuthenizate;
    }

    public boolean doAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception {


        AuthenizationState passAuthenization = passAuthenizate(request,response);
        if(passAuthenization!=AuthenizationState.UnAuthenizateState){
           if(passAuthenization==AuthenizationState.PassState){
               return true;
           }
           if(passAuthenization==AuthenizationState.NoPassState){
               return false;
           }
        }
        if(nextAuthenizate==null){
            throw new JwtSecurityException(JwtResponseMag.NoFindAuthenizationUrl);
        }
        return nextAuthenizate.doAuthenizate(request,response);
    }

    public abstract AuthenizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
