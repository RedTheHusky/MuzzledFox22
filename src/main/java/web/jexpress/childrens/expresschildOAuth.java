package web.jexpress.childrens;

import express.DynExpress;
import express.http.Cookie;
import express.http.RequestMethod;
import express.http.request.Request;
import express.http.response.Response;
import io.mokulu.discord.oauth.DiscordAPI;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.Permission;
import io.mokulu.discord.oauth.model.TokensResponse;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import web.jexpress.models.collectdata.*;
import web.jexpress.models.expStats;
import web.jexpress.jeAuth;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class expresschildOAuth {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;DiscordOAuth gOauthHandler,gOauthHandler2;
    jeAuth auth=null;
    public expresschildOAuth(lcGlobalHelper global, DiscordOAuth oauthHandler,DiscordOAuth oauthHandler2,Request req, Response res,String command) {
        String fName="build";
        try {
            gGlobal=global;gOauthHandler=oauthHandler;gOauthHandler2=oauthHandler2;
            Runnable r = new runLocal(req,res,command);
            new Thread(r).start();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
     
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        String gCommand="";Request gReq; Response gRes;
        CollectData collectData=new CollectData();
        DataSetDasboardCommon dataSetDasboardCommon=new DataSetDasboardCommon();
        DataSetDiscord dataSetDiscord =new DataSetDiscord();
        public runLocal(Request req, Response res,String command) {
            String fName="runLocal";
            try {
                logger.info(".run build");
                gCommand=command;
                logger.info(".gCommand="+gCommand);
                gReq=req;gRes=res;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        @Override
        public void run() {
            String fName="run";
            try {
                collectData.set(gReq);
                dataSetDasboardCommon.set(collectData);
                dataSetDiscord.set(collectData,gGlobal);
                switch (gCommand){
                    case "getUrl":
                        getUrl();
                        break;
                    case "getToken":
                        getToken();
                        break;
                    case "getRefresh":
                        getRefresh();
                        break;
                    case "getUser":
                        getUser();
                        break;
                    case "getGuilds":
                        getGuilds();
                        break;
                    case "postAuthLogin":
                        postAuthLogin();
                        break;
                    case "getAuthResume":
                        getAuthResume();
                        break;
                    case "getAuthLogOut":
                        getAuthLogOut();
                        break;
                    case "getGuildsUserIsPresent4Bot":
                        getGuildsUserIsPresent4Bot();
                        break;
                    default:
                        logger.warn("invalid command="+gCommand);

                }


            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }

        public String scope[]={"identify","guilds"};
        String getUrlSimple() {
            String fName="[getUrlSimple]";
            try {
                logger.info(fName+"init");
                String authURL = gOauthHandler.getAuthorizationURL(null);
                logger.info(fName+"authURL="+authURL);
                return  authURL;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }

        public JSONObject authorizingCode(String code) {
            String fName="[authorizingCode]";
            try {
                logger.info(fName+"code="+code);
                TokensResponse tokens=null;String accessToken="",refreshToken="";
                try {
                    tokens = gOauthHandler.getTokens(code);
                    accessToken = tokens.getAccessToken();
                    refreshToken = tokens.getRefreshToken();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    try {
                        tokens = gOauthHandler.getTokens(code);
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
                jsonObject.put(iAuth.Keys.accessToken,accessToken);
                jsonObject.put(iAuth.Keys.refreshToken,refreshToken);
                jsonObject.put(iAuth.Keys.code,code);
                return  jsonObject;
            }catch (Exception e){
                return new JSONObject();
            }
        }
        public JSONObject refreshingAccessToken(String refresh_token) {
            String fName="[refreshingAccessToken]";
            try {
                logger.info(fName+"refresh_token="+refresh_token);
                TokensResponse tokens = gOauthHandler.refreshTokens(refresh_token);
                String accessToken = tokens.getAccessToken();
                String refreshToken = tokens.getRefreshToken();
                logger.info(fName+"accessToken="+accessToken);
                logger.info(fName+"refreshToken="+refreshToken);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(iAuth.Keys.accessToken,accessToken);
                jsonObject.put(iAuth.Keys.refreshToken,refreshToken);
                return  jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }


        @DynExpress(context = "/ouath/url")
        public void getUrl() {
            String fName="[getUrl]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                logger.info(fName+"create response");
                String authURL = gOauthHandler.getAuthorizationURL(null);
                logger.info(fName+"authURL="+authURL);
                jsonResponse.put("url",authURL);
                jsonResponse.put("url_dec", URLDecoder.decode(authURL, StandardCharsets.UTF_8.toString()));
                jsonResponse.put("url_enc", URLEncoder.encode(authURL, StandardCharsets.UTF_8.toString()));
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/ouath/validate")
        public void getToken() {
            String fName="[getToken]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                logger.info(fName+"userCode="+ dataSetDasboardCommon.sessionCode);
                TokensResponse tokens=null;String accessToken="",refreshToken="";
                try {
                    tokens = gOauthHandler.getTokens(dataSetDasboardCommon.sessionCode);
                    accessToken = tokens.getAccessToken();
                    refreshToken = tokens.getRefreshToken();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    try {
                        tokens = gOauthHandler2.getTokens(dataSetDasboardCommon.sessionCode);
                        accessToken = tokens.getAccessToken();
                        refreshToken = tokens.getRefreshToken();
                    }catch (Exception e2){
                        logger.error(fName + ".exception=" + e2);
                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        expStats.respondInternalError(gRes);
                        return;
                    }
                }
                logger.info(fName+"accessToken="+accessToken);
                logger.info(fName+"refreshToken="+refreshToken);
                logger.info(fName+"create response");
                jsonResponse.put(iAuth.Keys.accessToken,accessToken);
                jsonResponse.put(iAuth.Keys.refreshToken,refreshToken);
                jsonResponse.put(iAuth.Keys.code, dataSetDasboardCommon.sessionCode);
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/ouath/refresh")
        public void getRefresh() {
            String fName="[getRefresh]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                TokensResponse tokens=null;String accessToken="",refreshToken="";
                try {
                    tokens = gOauthHandler.refreshTokens(dataSetDasboardCommon.sessionRefresToken);
                    accessToken = tokens.getAccessToken();
                    refreshToken = tokens.getRefreshToken();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    try {
                        tokens = gOauthHandler2.refreshTokens(dataSetDasboardCommon.sessionRefresToken);
                        accessToken = tokens.getAccessToken();
                        refreshToken = tokens.getRefreshToken();
                    }catch (Exception e2){
                        logger.error(fName + ".exception=" + e2);
                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        expStats.respondInternalError(gRes);
                        return;
                    }
                }
                logger.info(fName+"accessToken="+accessToken);
                logger.info(fName+"refreshToken="+refreshToken);
                logger.info(fName+"create response");
                jsonResponse.put(iAuth.Keys.accessToken,accessToken);
                jsonResponse.put(iAuth.Keys.refreshToken,refreshToken);
                jsonResponse.put(iAuth.Keys.code, dataSetDasboardCommon.sessionCode);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/ouath/user")
        public void getUser() {
            String fName="[getUser]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                logger.info(fName+"access_token="+ dataSetDasboardCommon.sessionAccessToken);
                DiscordAPI api = new DiscordAPI(dataSetDasboardCommon.sessionAccessToken);
                io.mokulu.discord.oauth.model.User user = api.fetchUser();
                jsonResponse.put(llCommonKeys.UserStructure.keyId,user.getId());
                jsonResponse.put(llCommonKeys.keyAvatarUrl,user.getAvatar());
                jsonResponse.put(llCommonKeys.UserStructure.keyBot,user.getBot());
                jsonResponse.put(llCommonKeys.UserStructure.keyDiscriminator,user.getDiscriminator());
                jsonResponse.put(llCommonKeys.UserStructure.keyEmail,user.getEmail());
                jsonResponse.put(llCommonKeys.UserStructure.keyUsername,user.getUsername());
                //jsonResponse.put(llCommonKeys.keyFullName,user.getFullUsername());
                jsonResponse.put(llCommonKeys.UserStructure.keyVerified,user.getVerified());
                jsonResponse.put(llCommonKeys.UserStructure.keyFlags,user.getFlags());
                jsonResponse.put(llCommonKeys.UserStructure.keyPremiumType,user.getPremiumType());

                if(dataSetDiscord.inputFlagIncludeGuilds){
                    List<io.mokulu.discord.oauth.model.Guild> guilds =  api.fetchGuilds();
                    JSONArray jsonGuilds=new JSONArray();
                    for(int i=0;i<guilds.size();i++){
                        try {
                            JSONObject jsonGuild=new JSONObject();
                            io.mokulu.discord.oauth.model.Guild guild=guilds.get(i);
                            jsonGuild.put(llCommonKeys.GuildStructure.keyId, guild.getId());
                            jsonGuild.put(llCommonKeys.GuildStructure.keyIcon,guild.getIcon());
                            jsonGuild.put(llCommonKeys.GuildStructure.keyName, guild.getName());
                            jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyOwner, guild.isOwner());
                            try {
                                List<Permission>permissions=guild.getPermissionList();
                                JSONArray jsonPermissions=new JSONArray();
                                for(int j=0;j<permissions.size();j++){
                                    jsonPermissions.put(permissions.get(j).name());
                                }
                                jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyPermissions,jsonPermissions);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            try {
                                List<String>features=guild.getFeatures();
                                JSONArray jsonFeatures=new JSONArray();
                                for(int j=0;j<features.size();j++){
                                    jsonFeatures.put(features.get(j));
                                }
                                jsonGuild.put(llCommonKeys.GuildStructure.keyFeatures,jsonFeatures);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            jsonGuilds.put(jsonGuild);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }

                    }
                    jsonResponse.put(llCommonKeys.keyGuilds,jsonGuilds);
                }
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/ouath/guilds")
        public void getGuilds() {
            String fName="[getGuilds]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                logger.info(fName+"access_token="+ dataSetDasboardCommon.sessionAccessToken);
                DiscordAPI api = new DiscordAPI(dataSetDasboardCommon.sessionAccessToken);
                List<io.mokulu.discord.oauth.model.Guild> guilds =  api.fetchGuilds();
                JSONArray jsonGuilds=new JSONArray();
                for(int i=0;i<guilds.size();i++){
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        io.mokulu.discord.oauth.model.Guild guild=guilds.get(i);
                        jsonGuild.put(llCommonKeys.GuildStructure.keyId, guild.getId());
                        jsonGuild.put(llCommonKeys.GuildStructure.keyIcon,guild.getIcon());
                        jsonGuild.put(llCommonKeys.GuildStructure.keyName, guild.getName());
                        jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyOwner, guild.isOwner());
                        try {
                            List<Permission>permissions=guild.getPermissionList();
                            JSONArray jsonPermissions=new JSONArray();
                            for(int j=0;j<permissions.size();j++){
                                jsonPermissions.put(permissions.get(j).name());
                            }
                            jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyPermissions,jsonPermissions);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        try {
                            List<String>features=guild.getFeatures();
                            JSONArray jsonFeatures=new JSONArray();
                            for(int j=0;j<features.size();j++){
                                jsonFeatures.put(features.get(j));
                            }
                            jsonGuild.put(llCommonKeys.GuildStructure.keyFeatures,jsonFeatures);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        jsonGuilds.put(jsonGuild);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                jsonResponse.put(llCommonKeys.keyGuilds,jsonGuilds);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/auth/login")
        public void authLogin_Get() {
            String fName="[authResume]";
            try {
                postAuthLogin();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/auth/login",method = RequestMethod.POST)
        public void postAuthLogin() {
            String fName="[authLogin]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject(),jsonData=new JSONObject();
                logger.info(fName+"init");
                if(dataSetDasboardCommon.sessionAccessToken.isBlank()&& dataSetDasboardCommon.sessionCode.isBlank()){
                    respond_NoCodeProvide();
                    return;
                }
                logger.info(fName+"userCode="+ dataSetDasboardCommon.sessionCode);
                TokensResponse tokens=null;String accessToken="",refreshToken="";
                if(!dataSetDasboardCommon.sessionCode.isBlank()){
                    try {
                        tokens = gOauthHandler.getTokens(dataSetDasboardCommon.sessionCode);
                        accessToken = tokens.getAccessToken();
                        refreshToken = tokens.getRefreshToken();
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        try {
                            tokens = gOauthHandler2.getTokens(dataSetDasboardCommon.sessionCode);
                            accessToken = tokens.getAccessToken();
                            refreshToken = tokens.getRefreshToken();
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            respond_InvalidCodeProvide();
                            return;
                        }
                    }
                }
                if(!dataSetDasboardCommon.sessionAccessToken.isBlank())accessToken= dataSetDasboardCommon.sessionAccessToken;
                if(!dataSetDasboardCommon.sessionRefresToken.isBlank())refreshToken= dataSetDasboardCommon.sessionRefresToken;
                logger.info(fName+"accessToken="+accessToken);
                logger.info(fName+"refreshToken="+refreshToken);
                jsonData.put(iAuth.Keys.code, dataSetDasboardCommon.sessionCode);
                jsonData.put(iAuth.Keys.accessToken,accessToken);
                jsonData.put(iAuth.Keys.refreshToken,refreshToken );
                DiscordAPI api = new DiscordAPI(accessToken);
                String userId="",userName="";
                try {
                    io.mokulu.discord.oauth.model.User user = api.fetchUser();
                    userId=user.getId();
                    userName=user.getFullUsername();
                    logger.info(fName+"userId="+userId);
                    logger.info(fName+"userName="+userName);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    respond_MissingScope_Identify();
                    return;
                }
                JSONArray jsonGuilds=new JSONArray();
                try {
                    logger.info(fName+"getting guilds");
                    List<io.mokulu.discord.oauth.model.Guild> guilds =  api.fetchGuilds();
                    logger.info(fName+"got guilds");
                    for(int i=0;i<guilds.size();i++){
                        try {
                            JSONObject jsonGuild=new JSONObject();
                            io.mokulu.discord.oauth.model.Guild guild=guilds.get(i);
                            jsonGuild.put(llCommonKeys.GuildStructure.keyId, guild.getId());
                            jsonGuild.put(llCommonKeys.GuildStructure.keyIcon,guild.getIcon());
                            jsonGuild.put(llCommonKeys.GuildStructure.keyName, guild.getName());
                            jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyOwner, guild.isOwner());
                            try {
                                List<Permission>permissions=guild.getPermissionList();
                                JSONArray jsonPermissions=new JSONArray();
                                for(int j=0;j<permissions.size();j++){
                                    jsonPermissions.put(permissions.get(j).name());
                                }
                                jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyPermissions,jsonPermissions);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            jsonGuilds.put(jsonGuild);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }

                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    respond_MissingScope_Guilds();
                    return;
                }
                jsonData.put(llCommonKeys.keyGuilds,jsonGuilds);
                JSONObject jsonUser=new JSONObject();
                jsonUser.put(llCommonKeys.UserStructure.keyId,userId);
                jsonUser.put(llCommonKeys.UserStructure.keyUsername,userName);
                jsonData.put(llCommonKeys.keyUser,jsonUser);
                jsonData.put(llCommonKeys.keyUser_id,userId);
                auth=new jeAuth(gGlobal);
                String token=auth.createToken(jsonData);
                if(token==null||token.isBlank()){
                    respond_Failed2LogIn();
                    return;
                }
                logger.info(fName+"token="+token);
            /*Cookie ctoken=new Cookie();
            ctoken.setName(expStats.keySessionToken);
            ctoken.setValue(token);
            res.setCookie(ctoken);*/
                jsonResponse.put(iAuth.Keys.token,token);
                jsonResponse.put(iAuth.Keys.userId,userId);
                jsonResponse.put("login","success");
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        boolean respond_NoCodeProvide() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[NoCodeProvide]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"ouath2.code:missing");
                jsonStatus.put(expStats.keyMessage,"No code provided from Discord OAuth2.");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean respond_InvalidCodeProvide() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_InvalidCodeProvide]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"ouath2.code:invalid");
                jsonStatus.put(expStats.keyMessage,"Invalid code provided from Discord OAuth2.");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean respond_MissingScope_Identify() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_MissingScope_Identify]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"scope.identify:missing");
                jsonStatus.put(expStats.keyMessage,"Scope identify is missing.");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean respond_MissingScope_Guilds() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_MissingScope_Guilds]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"scope.guilds:missing");
                jsonStatus.put(expStats.keyMessage,"Scope guilds is missing.");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean respond_Failed2LogIn() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_Failed2LogIn]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"login:failed");
                jsonStatus.put(expStats.keyMessage,"Failed to create token");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        @DynExpress(context = "/auth/resume")
        public void getAuthResume() {
            String fName="[authResume]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                if(dataSetDasboardCommon.sessionToken ==null|| dataSetDasboardCommon.sessionToken.isBlank()){
                    Cookie ctoken=gReq.getCookie(iAuth.Cookies.token);
                    if(ctoken!=null){
                        dataSetDasboardCommon.sessionToken =ctoken.getValue();
                    }

                }
                if(dataSetDasboardCommon.sessionToken ==null|| dataSetDasboardCommon.sessionToken.isBlank()){
                    expStats.respond_NoTokenFoundInCookie(gRes);
                    return;
                }
                logger.info(fName+"token="+ dataSetDasboardCommon.sessionToken);
                if(dataSetDasboardCommon.sessionAuthId ==null|| dataSetDasboardCommon.sessionAuthId.isBlank()){
                    Cookie cuserId=gReq.getCookie(iAuth.Cookies.user);
                    if(cuserId!=null){
                        dataSetDasboardCommon.sessionAuthId =cuserId.getValue();
                    }
                }
                if(dataSetDasboardCommon.sessionAuthId ==null|| dataSetDasboardCommon.sessionAuthId.isBlank()){
                    expStats.respond_NoUserIdFoundInCookie(gRes);
                    return;
                }
                logger.info(fName+"userId="+ dataSetDasboardCommon.sessionAuthId);
                auth=new jeAuth(gGlobal, dataSetDasboardCommon.sessionAuthId, dataSetDasboardCommon.sessionToken);
                if(!auth.isValid(gOauthHandler,gOauthHandler2)){
                    logger.warn(fName+"could not validate");
                    respond_Failed2Resume();
                    return;
                }
                if(!auth.resume()){
                    logger.warn(fName+"could not update it");
                    respond_Failed2Resume();
                    return;
                }
                jsonResponse.put(iAuth.Keys.token, dataSetDasboardCommon.sessionToken);
                jsonResponse.put(iAuth.Keys.userId, dataSetDasboardCommon.sessionAuthId);
                jsonResponse.put("resume","success");
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        boolean respond_Failed2Resume() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_Failed2Resume]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"resume:failed");
                jsonStatus.put(expStats.keyMessage,"Failed to resume.");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        @DynExpress(context = "/auth/logout")
        public void getAuthLogOut() {
            String fName="[authLogOut]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");
                if(dataSetDasboardCommon.sessionToken ==null|| dataSetDasboardCommon.sessionToken.isBlank()){
                    Cookie ctoken=gReq.getCookie(iAuth.Cookies.token);
                    if(ctoken!=null){
                        dataSetDasboardCommon.sessionToken =ctoken.getValue();
                    }

                }
                if(dataSetDasboardCommon.sessionToken ==null|| dataSetDasboardCommon.sessionToken.isBlank()){
                    expStats.respond_NoTokenFoundInCookie(gRes);
                    return;
                }
                logger.info(fName+"token="+ dataSetDasboardCommon.sessionToken);
                if(dataSetDasboardCommon.sessionAuthId ==null|| dataSetDasboardCommon.sessionAuthId.isBlank()){
                    Cookie cuserId=gReq.getCookie(iAuth.Cookies.user);
                    if(cuserId!=null){
                        dataSetDasboardCommon.sessionAuthId =cuserId.getValue();
                    }
                }
                if(dataSetDasboardCommon.sessionAuthId ==null|| dataSetDasboardCommon.sessionAuthId.isBlank()){
                    expStats.respond_NoUserIdFoundInCookie(gRes);
                    return;
                }
                logger.info(fName+"userId="+ dataSetDasboardCommon.sessionAuthId);
                auth=new jeAuth(gGlobal, dataSetDasboardCommon.sessionAuthId, dataSetDasboardCommon.sessionToken);
                if(!auth.invalidateToken()){
                    logger.warn(fName+"could not update it");
                    respond_Failed2LogOut();
                    return;
                }
                jsonResponse.put(iAuth.Keys.token, dataSetDasboardCommon.sessionToken);
                jsonResponse.put(iAuth.Keys.userId, dataSetDasboardCommon.sessionAuthId);
                jsonResponse.put("logout","success");
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        boolean respond_InvalidToken() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_InvalidToken]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"invalid_token");
                jsonStatus.put(expStats.keyMessage,"Invalid token");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean respond_Failed2LogOut() {
            Logger logger = Logger.getLogger(expresschildOAuth.class);String fName="[respond_Failed2LogOut]";
            try {
                logger.info(fName+"entered"); JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonStatus.put(expStats.keyCode,expStats.CODE_NOTFOUND);
                jsonStatus.put(expStats.keyLable,"logout:failed");
                jsonStatus.put(expStats.keyMessage,"Invalid token");
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes.setHeader("Content-Type", "application/json");
                gRes.setHeader("Access-Control-Allow-Origin", "*");
                //res.setStatus(STATUS_NOTFOUND);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        @DynExpress(context = "/auth/guilds")
        public void getGuildsUserIsPresent4Bot() {
            String fName="[getGuildsUserIsPresent4Bot]";
            try {
                JSONObject jsonResponse=new JSONObject(),jsonStatus=new JSONObject();
                logger.info(fName+"init");

                if(dataSetDasboardCommon.sessionToken ==null|| dataSetDasboardCommon.sessionToken.isBlank()){
                    Cookie ctoken=gReq.getCookie(iAuth.Cookies.token);
                    if(ctoken!=null){
                        dataSetDasboardCommon.sessionToken =ctoken.getValue();
                    }

                }
                if(dataSetDasboardCommon.sessionToken ==null|| dataSetDasboardCommon.sessionToken.isBlank()){
                    expStats.respond_NoTokenFoundInCookie(gRes);
                    return;
                }
                logger.info(fName+"token="+ dataSetDasboardCommon.sessionToken);
                if(dataSetDasboardCommon.sessionAuthId ==null|| dataSetDasboardCommon.sessionAuthId.isBlank()){
                    Cookie cuserId=gReq.getCookie(iAuth.Cookies.user);
                    if(cuserId!=null){
                        dataSetDasboardCommon.sessionAuthId =cuserId.getValue();
                    }
                }
                if(dataSetDasboardCommon.sessionAuthId ==null|| dataSetDasboardCommon.sessionAuthId.isBlank()){
                    expStats.respond_NoUserIdFoundInCookie(gRes);
                    return;
                }
                logger.info(fName+"userId="+ dataSetDasboardCommon.sessionAuthId);
                auth=new jeAuth(gGlobal, dataSetDasboardCommon.sessionAuthId, dataSetDasboardCommon.sessionToken);
                if(!auth.isValid(gOauthHandler,gOauthHandler2)){
                    logger.warn(fName+"could not validate");
                    respond_Failed2Resume();
                    return;
                }
                DiscordAPI api = new DiscordAPI(dataSetDasboardCommon.sessionAccessToken);
                List<io.mokulu.discord.oauth.model.Guild> guilds =  api.fetchGuilds();
                JSONArray jsonGuilds=new JSONArray();
                for(int i=0;i<guilds.size();i++){
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        io.mokulu.discord.oauth.model.Guild guild=guilds.get(i);
                        String guildId=guild.getId();
                        Guild guild1=gGlobal.getGuild(guildId);
                        if(guild1!=null){
                            jsonGuild.put(llCommonKeys.GuildStructure.keyId, guild.getId());
                            jsonGuild.put(llCommonKeys.GuildStructure.keyIcon,guild.getIcon());
                            jsonGuild.put(llCommonKeys.GuildStructure.keyName, guild.getName());
                            jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyOwner, guild.isOwner());
                            try {
                                List<Permission>permissions=guild.getPermissionList();
                                JSONArray jsonPermissions=new JSONArray();
                                for(int j=0;j<permissions.size();j++){
                                    jsonPermissions.put(permissions.get(j).name());
                                }
                                jsonGuild.put(llCommonKeys.GuildStructure.forUser.keyPermissions,jsonPermissions);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            try {
                                List<String>features=guild.getFeatures();
                                JSONArray jsonFeatures=new JSONArray();
                                for(int j=0;j<features.size();j++){
                                    jsonFeatures.put(features.get(j));
                                }
                                jsonGuild.put(llCommonKeys.GuildStructure.keyFeatures,jsonFeatures);
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                            jsonGuilds.put(jsonGuild);
                        }

                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }
                jsonResponse.put(llCommonKeys.keyGuilds,jsonGuilds);

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                logger.info(fName+"finalizing response");
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
                logger.info(fName+"return response");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }

    }


}
