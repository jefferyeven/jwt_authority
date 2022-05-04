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
        if(passAuthenization==AuthenizationState.PassState){
            return true;
        }
        if(nextAuthenizate==null){
            if(passAuthenization==AuthenizationState.UnAuthenizateState){
                throw new JwtSecurityException(JwtResponseMag.NoFindAuthenizationUrl);
            }
            return false;
        }
        return nextAuthenizate.doAuthenizate(request,response);
    }

    public abstract AuthenizationState passAuthenizate(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
