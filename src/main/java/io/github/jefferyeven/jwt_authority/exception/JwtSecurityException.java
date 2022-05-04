package io.github.jefferyeven.jwt_authority.exception;

public class JwtSecurityException extends RuntimeException{
    //自定义错误码
    private String code;
    //自定义构造器，必须输入错误码及内容
    public JwtSecurityException(String code, String msg) {
        super(msg);
        this.code = code;
    }
    public JwtSecurityException(JwtResponseMag jwtResponseMag){
        super(jwtResponseMag.getMsg());
        code = jwtResponseMag.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


