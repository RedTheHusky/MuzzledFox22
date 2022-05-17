package web.jexpress;

import express.http.request.Request;
import express.http.response.Response;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ls.lsRoleHelper;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;

public class OAUTH2HANDLING {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public OAUTH2HANDLING()  {

    }
    public OAUTH2HANDLING(lcGlobalHelper global)  {
        gGlobal=global;

    }
    DiscordOAuth oauthHandler;
    String clientID="389512707973316608";
    String clientSecret="WKXr7Ub1yzDDK3KVg7M5oXkedyAqX3Jt";
    String redirectUri="http://localhost:8789/OAuth2";
    public String scope[]={"identify"};
    public void start() {
        String fName="[start]";
        try {
            logger.info(fName+"init");
            oauthHandler = new DiscordOAuth(clientID, clientSecret, redirectUri, scope);
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
            TokensResponse tokens = oauthHandler.getTokens(code);
            String accessToken = tokens.getAccessToken();
            String refreshToken = tokens.getRefreshToken();
            logger.info(fName+"accessToken="+accessToken);
            logger.info(fName+"refreshToken="+refreshToken);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyAccessToken,accessToken);
            jsonObject.put(keyRefreshToken,refreshToken);
            jsonObject.put(keyCode,code);
            return  jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
}
