package cn.jeffey.jwt_authority.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AnnoationPermissionUrl {
    private Map<String,UrlPermission> requestMap;
    private Map<String,UrlPermission> getMap;
    private Map<String,UrlPermission> postMap;
    private Map<String,UrlPermission> putMap;
    private Map<String,UrlPermission> deleteMap;
    public AnnoationPermissionUrl(){
        requestMap = new HashMap<>();
        getMap = new HashMap<>();
        postMap = new HashMap<>();
        deleteMap = new HashMap<>();
        putMap = new HashMap<>();
    }
}
