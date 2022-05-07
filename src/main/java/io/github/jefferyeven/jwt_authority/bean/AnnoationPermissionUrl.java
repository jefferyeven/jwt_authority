package io.github.jefferyeven.jwt_authority.bean;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, UrlPermission> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, UrlPermission> requestMap) {
        this.requestMap = requestMap;
    }

    public Map<String, UrlPermission> getGetMap() {
        return getMap;
    }

    public void setGetMap(Map<String, UrlPermission> getMap) {
        this.getMap = getMap;
    }

    public Map<String, UrlPermission> getPostMap() {
        return postMap;
    }

    public void setPostMap(Map<String, UrlPermission> postMap) {
        this.postMap = postMap;
    }

    public Map<String, UrlPermission> getPutMap() {
        return putMap;
    }

    public void setPutMap(Map<String, UrlPermission> putMap) {
        this.putMap = putMap;
    }

    public Map<String, UrlPermission> getDeleteMap() {
        return deleteMap;
    }

    public void setDeleteMap(Map<String, UrlPermission> deleteMap) {
        this.deleteMap = deleteMap;
    }
}
