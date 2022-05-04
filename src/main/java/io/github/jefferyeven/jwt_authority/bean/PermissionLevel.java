package io.github.jefferyeven.jwt_authority.bean;


public enum PermissionLevel {
    PERMISSION_All("允许所有url"),
    DENY_All("拒绝所有url"),
    HAVE_TOKEN("url必须携带token"),
    HAVE_ANY_AUTHORITY("指定任一权限访问"),
    HAVE_ALL_AUTHORITY("指定所有权限访问");
    private final String descripe;

    PermissionLevel(String descripe){
        this.descripe = descripe;
    }
    public String getDescripe() {
        return descripe;
    }
}
