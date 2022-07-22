package web.spring.discord.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import web.spring.discord.iDiscord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DashUserSession {
    Logger logger = Logger.getLogger(getClass());
    private String random_token;
    private String id;
    private String name;
    private String access_token;
    private String refresh_token;
    private String scope;
    private  int expires;
    private String token_type;
    public DashUserSession(){}
    public DashUserSession(String random_token,String id,String name,String access_token,String refresh_token,String token_type,String scope,int expires) throws Exception {
       setRandomToken(random_token).setId(id).setAccessToken(access_token).setRefreshToken(refresh_token).setExpires(expires).setScope(scope).setName(name).setTokenType(token_type);
    }

    public String getRandomToken() {
        return random_token;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExpires() {
        return expires;
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getScope() {
        return scope;
    }
    public DashUserSession setRandomToken(String random_token) throws Exception {
        String fName="[setRandomToken]";
        this.random_token = random_token;
        return this;
    }
    public DashUserSession setId(String id) throws Exception {
        String fName="[setId]";
        if(id==null||id.isBlank()){
            throw  new Exception("input can't be null or blank");
        }
        this.id = id;
        return this;
    }

    public DashUserSession setName(String name) {
        String fName="[setName]";
        this.name = name;
        return this;
    }

    public DashUserSession setAccessToken(String access_token) throws Exception {
        String fName="[setAccessToken]";
        if(id==null||id.isBlank()){
            throw  new Exception("input can't be null or blank");
        }
        this.access_token = access_token;
        return this;
    }

    public DashUserSession setExpires(int expires) {
        String fName="[setExpires]";
        this.expires = expires;
        return this;
    }

    public DashUserSession setRefreshToken(String refresh_token) {
        String fName="[setRefreshToken]";
        this.refresh_token = refresh_token;
        return this;
    }

    public DashUserSession setScope(String scope) {
        String fName="[setScope]";
        this.scope = scope;
        return this;
    }

    public DashUserSession setTokenType(String token_type) {
        String fName="[setTokenType]";
        this.token_type = token_type;
        return this;
    }
    public JSONObject getAsJson(){
        String fName="[getAsJson]";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(iDiscord.KEYS.random_token,random_token).put(iDiscord.KEYS.id,id).put(iDiscord.KEYS.name,name)
                .put(iDiscord.KEYS.access_token,access_token).put(iDiscord.KEYS.refresh_token,refresh_token)
                .put(iDiscord.KEYS.expires,expires).put(iDiscord.KEYS.scope,scope).put(iDiscord.KEYS.token_type,token_type);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "DashUserSession{" +
                "random_token='" + random_token + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", expires=" + expires +
                ", token_type='" + token_type + '\'' +
                '}';
    }
    public Map<String,Object> getAsMap() {
        Map<String,Object> map=Map.of(iDiscord.KEYS.random_token,random_token,iDiscord.KEYS.id,id,iDiscord.KEYS.name,name,
                iDiscord.KEYS.access_token,access_token,iDiscord.KEYS.refresh_token,refresh_token,
                iDiscord.KEYS.expires,expires,iDiscord.KEYS.scope,scope,iDiscord.KEYS.token_type,token_type);
        return map;
    }

}
