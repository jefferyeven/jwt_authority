
package io.github.jefferyeven.jwt_authority.exception;

public enum JwtResponseMag {
    NoFindAuthenizationUrl("601","没有发现匹配的url"),
    DenyRequestError("602","不允许访问该链接"),
    NoTokenError("603","没有token"),
    TokenError("604","token验证出错"),
    TokenReadError("605","token读取出错"),
    NoAuthorityError("606","没有该权限名"),
    ;
   private JwtResponseMag(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;

	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

}

