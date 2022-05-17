package web.jexpress;

import express.DynExpress;
import express.http.RequestMethod;
import express.http.request.Request;
import express.http.response.Response;
import io.mokulu.discord.oauth.DiscordOAuth;

import io.mokulu.discord.oauth.model.TokensResponse;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import web.jexpress.childrens.expresschildOAuth;
import web.jexpress.models.expStats;

import java.util.Arrays;

public class expressOAuth {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    jeAuth auth=null;
    public expressOAuth(lcGlobalHelper global, boolean start) {
        gGlobal=global;
        if(start)start();
    }


    DiscordOAuth oauthHandler,oauthHandler2;
    String clientID="389512707973316608";
    String clientSecret="WKXr7Ub1yzDDK3KVg7M5oXkedyAqX3Jt";
    String redirectUri="http://localhost:8789/ouath/validate";
    String redirectUriPHP="http://localhost/Discord/Dasboard/backend/login";
    public String scope[]={"identify","guilds"};

    public void start() {
        String fName="[start]";
        try {
            logger.info(fName+"init");
            oauthHandler = new DiscordOAuth(clientID, clientSecret, redirectUri, scope);
            oauthHandler2 = new DiscordOAuth(clientID, clientSecret, redirectUriPHP, scope);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public String getUrl() {
        String fName="[getUrl]";
        try {
            logger.info(fName+"init");
            String authURL = oauthHandler.getAuthorizationURL(null);
            logger.info(fName+"authURL="+authURL);
            return  authURL;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public static final String
            keyAccessToken="accessToken",
            keyRefreshToken="refreshToken",
            keyCode="code";
    public JSONObject authorizingCode(String code) {
        String fName="[authorizingCode]";
        try {
            logger.info(fName+"code="+code);
            TokensResponse tokens=null;String accessToken="",refreshToken="";
            try {
                tokens = oauthHandler.getTokens(code);
                accessToken = tokens.getAccessToken();
                refreshToken = tokens.getRefreshToken();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                try {
                    tokens = oauthHandler.getTokens(code);
                    accessToken = tokens.getAccessToken();
                    refreshToken = tokens.getRefreshToken();
                    return new JSONObject();
                }catch (Exception e2){
                    logger.error(fName + ".exception=" + e2);
                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                }
            }
            logger.info(fName+"accessToken="+accessToken);
            logger.info(fName+"refreshToken="+refreshToken);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyAccessToken,accessToken);
            jsonObject.put(keyRefreshToken,refreshToken);
            jsonObject.put(keyCode,code);
            return  jsonObject;
        }catch (Exception e){
            return new JSONObject();
        }
    }
    public JSONObject refreshingAccessToken(String refresh_token) {
        String fName="[refreshingAccessToken]";
        try {
            logger.info(fName+"refresh_token="+refresh_token);
            TokensResponse tokens = oauthHandler.refreshTokens(refresh_token);
            String accessToken = tokens.getAccessToken();
            String refreshToken = tokens.getRefreshToken();
            logger.info(fName+"accessToken="+accessToken);
            logger.info(fName+"refreshToken="+refreshToken);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyAccessToken,accessToken);
            jsonObject.put(keyRefreshToken,refreshToken);
            return  jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    

    @DynExpress(context = "/ouath/url")
    public void getUrl(Request req, Response res) {
        String fName="[getUrl]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getUrl");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/ouath/validate")
    public void getToken(Request req, Response res) {
        String fName="[getToken]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getToken");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/ouath/refresh")
    public void getRefresh(Request req, Response res) {
        String fName="[getRefresh]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getRefresh");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/ouath/user")
    public void getUser(Request req, Response res) {
        String fName="[getUser]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getUser");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/ouath/guilds")
    public void getGuilds(Request req, Response res) {
        String fName="[getGuilds]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getGuilds");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/auth/login")
    public void getAuthLogin_Get(Request req, Response res) {
        String fName="[getAuthResume]";
        try {
            postAuthLogin(req,res);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }
    @DynExpress(context = "/auth/login",method = RequestMethod.POST)
    public void postAuthLogin(Request req, Response res) {
        String fName="[postAuthLogin]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"postAuthLogin");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }


    @DynExpress(context = "/auth/resume")
    public void getAuthResume(Request req, Response res) {
        String fName="[authResume]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getAuthResume");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }



    @DynExpress(context = "/auth/logout")
    public void getAuthLogOut(Request req, Response res) {
        String fName="[authLogOut]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getAuthLogOut");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }


    @DynExpress(context = "/auth/guilds")
    public void getGuildsUserIsPresent4Bot(Request req, Response res) {
        String fName="[getGuildsUserIsPresent4Bot]";
        try {
            new expresschildOAuth(gGlobal,oauthHandler,oauthHandler2,req,res,"getGuildsUserIsPresent4Bot");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            expStats.respondInternalError(res);
        }
    }

}
