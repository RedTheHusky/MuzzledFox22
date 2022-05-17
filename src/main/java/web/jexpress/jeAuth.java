package web.jexpress;

import io.mokulu.discord.oauth.DiscordAPI;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class jeAuth {
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper gGlobal;
    String gUserId="",gToken="";
    public jeAuth(lcGlobalHelper global,String userId,String token)  {
        String fName="build";
        gGlobal=global;gUserId=userId;gToken=token;
        try {
            logger.info(fName+".userId="+gUserId+", token="+gToken);
            loadedProfile(gUserId);
            logger.info(fName+".loaded user profile or created");

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public jeAuth(lcGlobalHelper global)  {
        gGlobal=global;
    }
    public lcJSONUserProfile gUserProfile;
    String profileName="dasboardUser",table="dasboardUser";
    public String getAccessToken(String userId,String token){
        String fName="[getAccessToken]";
        try {
            logger.info(fName+".userId="+userId+", token="+token);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(token)){
                logger.warn(fName+".invalid token");
                return "";
            }
            return gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(token).getString(expressOAuth.keyAccessToken);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String getAccessToken(){
        String fName="[getAccessToken]";
        try {
            logger.info(fName+".userId="+gUserId+", token="+gToken);
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.warn(fName+".invalid token");
                return "";
            }
            return gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken).getString(expressOAuth.keyAccessToken);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean setAccessToken(String userId,String token,String newValue){
        String fName="[setAccessToken]";
        try {
            logger.info(fName+".userId="+userId+", token="+token+", newValue="+newValue);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(token)){
                logger.warn(fName+".invalid token");
                return false;
            }
            gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(token).put(expressOAuth.keyAccessToken,newValue);
            return saveProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean setAccessToken(String newValue){
        String fName="[setAccessToken]";
        try {
            logger.info(fName+".userId="+gUserId+", token="+gToken+", newValue="+newValue);
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.warn(fName+".invalid token");
                return false;
            }
            gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken).put(expressOAuth.keyAccessToken,newValue);
            return saveProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getRefreshToken(String userId, String token){
        String fName="[getRefreshToken]";
        try {
            logger.info(fName+".userId="+userId+", token="+token);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(token)){
                logger.warn(fName+".invalid token");
                return "";
            }
            return gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(token).getString(expressOAuth.keyRefreshToken);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String getRefreshToken(){
        String fName="[getRefreshToken]";
        try {
            logger.info(fName+".userId="+gUserId+", token="+gToken);
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.warn(fName+".invalid token");
                return "";
            }
            return gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken).getString(expressOAuth.keyRefreshToken);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean setRefreshToken(String userId,String token,String newValue){
        String fName="[setRefreshToken]";
        try {
            logger.info(fName+".userId="+userId+", token="+token+", newValue="+newValue);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(token)){
                logger.warn(fName+".invalid token");
                return false;
            }
            gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(token).put(expressOAuth.keyRefreshToken,newValue);
            return saveProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean setRefreshToken(String newValue){
        String fName="[setRefreshToken]";
        try {
            logger.info(fName+".userId="+gUserId+", token="+gToken+", newValue="+newValue);
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.warn(fName+".invalid token");
                return false;
            }
            gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken).put(expressOAuth.keyRefreshToken,newValue);
            return saveProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String createToken(JSONObject jsonData){
        String fName="[createToken]";
        try {
            logger.info(fName+".jsonData="+jsonData.toString());
            loadedProfile(jsonData.getString(llCommonKeys.keyUser_id));
            logger.info(fName+".loaded user profile or created");

            JSONObject token=new JSONObject();
            String id=webHelper1.getAlphaNumericString();
            token.put(keyID,id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            token.put(keyCode,jsonData.getString("code"));
            token.put(expressOAuth.keyAccessToken,jsonData.getString(expressOAuth.keyAccessToken));
            token.put(expressOAuth.keyRefreshToken,jsonData.getString(expressOAuth.keyRefreshToken));
            token.put(keyTimeCreated,timestamp.getTime());
            //token.put(keyTimeAccessed,0);
            //token.put(keyTimeRead,0);
            //token.put(keyTimeWrite,0);
            //token.put(keyTimeRemoved,0);
            gUserProfile.jsonObject.getJSONObject(keyTokens).put(id,token);
            gUserProfile.jsonObject.put(llCommonKeys.keyUser,jsonData.getJSONObject(llCommonKeys.keyUser));
            gUserProfile.jsonObject.put(llCommonKeys.keyGuilds,jsonData.getJSONArray(llCommonKeys.keyGuilds));
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  "";
            }
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String createToken(String code,String accessToken,String refreshtoken,JSONObject jsonUser, JSONArray jsonGuilds){
        String fName="[createToken]";
        try {
            logger.info(fName+".code="+code+", accessToken="+accessToken+", refreshtoken="+refreshtoken+" ,jsonUser="+jsonUser.toString()+", jsonGuilds="+jsonGuilds.toString());
            JSONObject token=new JSONObject();
            String id=webHelper1.getAlphaNumericString();
            token.put(keyID,id);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            token.put(keyCode,code);
            token.put(expressOAuth.keyAccessToken,accessToken);
            token.put(expressOAuth.keyRefreshToken,refreshtoken);
            token.put(keyTimeCreated,timestamp.getTime());
            //token.put(keyTimeAccessed,0);
            //token.put(keyTimeRead,0);
            //token.put(keyTimeWrite,0);
            //token.put(keyTimeRemoved,0);
            gUserProfile.jsonObject.getJSONObject(keyTokens).put(id,token);
            gUserProfile.jsonObject.put(llCommonKeys.keyUser,jsonUser);
            gUserProfile.jsonObject.put(llCommonKeys.keyGuilds,jsonGuilds);
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  "";
            }
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public boolean invalidateToken(String userId, String tokenId){
        String fName="[invalidateToken]";
        try {
            logger.info(fName+"userId="+userId+", tokenId="+tokenId);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");

            JSONObject token=gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(tokenId);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            token.put(keyTimeRemoved,timestamp.getTime());
            gUserProfile.jsonObject.getJSONArray(keyTokensArchive).put(token);
            gUserProfile.jsonObject.getJSONObject(keyTokens).remove(tokenId);
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean invalidateToken(){
        String fName="[invalidateToken]";
        try {
            logger.info(fName+"userId="+gUserId+", token="+gToken);

            JSONObject token=gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            token.put(keyTimeRemoved,timestamp.getTime());
            gUserProfile.jsonObject.getJSONArray(keyTokensArchive).put(token);
            gUserProfile.jsonObject.getJSONObject(keyTokens).remove(gToken);
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    long experiationOffset=604800000;
    public boolean isAvailable(String userId, String tokenId){
        String fName="[isAvailable]";
        try {
            logger.info(fName+"userId="+userId+", tokenId="+tokenId);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");

            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(tokenId)){
                logger.info(fName+".has no token>need to create one");
                return  false;
            }
            JSONObject token=gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(tokenId);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            long timeCreated=token.getLong(keyTimeCreated);
            logger.info(fName+".timeCreated="+timeCreated);
            logger.info(fName+".diff="+(timestamp.getTime()-timeCreated));
            if((timestamp.getTime()-timeCreated)>experiationOffset){
                logger.info(fName+".is expired");
                token.put(keyTimeRemoved,timestamp.getTime());
                gUserProfile.jsonObject.getJSONArray(keyTokensArchive).put(token);
                gUserProfile.jsonObject.getJSONObject(keyToken).remove(tokenId);
                if(!saveProfile()){
                    logger.warn(fName + ".could not save");
                }
                return false;
            }
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isAvailable(){
        String fName="[isAvailable]";
        try {
            logger.info(fName+"userId="+gUserId+", tokenId="+gToken);

            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.info(fName+".has no token>need to create one");
                return  false;
            }
            JSONObject token=gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            long timeCreated=token.getLong(keyTimeCreated);
            logger.info(fName+".timeCreated="+timeCreated);
            logger.info(fName+".diff="+(timestamp.getTime()-timeCreated));
            if((timestamp.getTime()-timeCreated)>experiationOffset){
                logger.info(fName+".is expired");
                token.put(keyTimeRemoved,timestamp.getTime());
                gUserProfile.jsonObject.getJSONArray(keyTokensArchive).put(token);
                gUserProfile.jsonObject.getJSONObject(keyToken).remove(gToken);
                if(!saveProfile()){
                    logger.warn(fName + ".could not save");
                }
                return false;
            }
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    public boolean isValid(DiscordOAuth oauthHandler,DiscordOAuth oauthHandler2){
        String fName="[isValid]";
        try {
            logger.info(fName+"userId="+gUserId+", tokenId="+gToken);
            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.info(fName+".has no token>need to create one");
                return  false;
            }
            JSONObject token=gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken);
            String accessToken=token.getString(expressOAuth.keyAccessToken);
            DiscordAPI api = new DiscordAPI(accessToken);
            boolean tryRefresh=false;
            try {
                io.mokulu.discord.oauth.model.User user = api.fetchUser();
                if(!gUserId.equalsIgnoreCase(user.getId())){
                    return false;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                tryRefresh=true;
            }
            if(tryRefresh){
                String refreshToken=token.getString(expressOAuth.keyAccessToken);
                TokensResponse tokens=null;
                try {
                    tokens = oauthHandler.refreshTokens(refreshToken);
                    accessToken = tokens.getAccessToken();
                    refreshToken = tokens.getRefreshToken();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    try {
                        tokens = oauthHandler2.refreshTokens(refreshToken);
                        accessToken = tokens.getAccessToken();
                        refreshToken = tokens.getRefreshToken();
                    }catch (Exception e2){
                        logger.error(fName + ".exception=" + e2);
                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        return false;
                    }
                }
                logger.info(fName+"accessToken="+accessToken);
                logger.info(fName+"refreshToken="+refreshToken);
                if(!setAccessToken(accessToken)){
                    return false;
                }
                try {
                    DiscordAPI api2 = new DiscordAPI( accessToken);
                    io.mokulu.discord.oauth.model.User user = api2.fetchUser();
                    if(!gUserId.equalsIgnoreCase(user.getId())){
                        return false;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean resume(String userId, String tokenId){
        String fName="[resume]";
        try {
            logger.info(fName);
            logger.info(fName+"userId="+userId+", tokenId="+tokenId);
            loadedProfile(userId);
            logger.info(fName+".loaded user profile or created");

            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(tokenId)){
                logger.info(fName+".has no token>need to create one");
                return  false;
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(tokenId).put(keyTimeAccessed,timestamp.getTime());
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean resume(){
        String fName="[resume]";
        try {
            logger.info(fName);
            logger.info(fName+"userId="+gUserId+", tokenId="+gToken);

            if(!gUserProfile.jsonObject.getJSONObject(keyTokens).has(gToken)){
                logger.info(fName+".has no token>need to create one");
                return  false;
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            gUserProfile.jsonObject.getJSONObject(keyTokens).getJSONObject(gToken).put(keyTimeAccessed,timestamp.getTime());
            if(!saveProfile()){
                logger.warn(fName + ".could not save");
                return  false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean saveProfile(){
        String fName="[saveProfile]";
        try {
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean loadedProfile(String id){
        String fName="[loadedProfile]";
        logger.info(fName);
        try {
            List<JDA>jdas=gGlobal.getJDAList();
            for(int i=0;i<jdas.size();i++){
                try {
                    JDA jda=gGlobal.getJDAList().get(0);
                    User user=jda.getUserById(id);
                    if(user!=null){
                        if(loadedProfile(user)){
                            logger.info(fName + "received user");
                            logger.info(fName + "received json="+gUserProfile.jsonObject.toString());
                            return true;
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.error(fName + "failed to get user");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private Boolean loadedProfile(User user){
        String fName="[loadedProfile]";
        try {
            logger.info(fName);
            logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
            gUserProfile=gGlobal.getUserProfile(profileName,user);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,user);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            gUserProfile=safetyUserProfileEntry(gUserProfile);gGlobal.putUserProfile(gUserProfile,profileName);
            logger.info(fName + ".isUpdated="+gUserProfile.isUpdated);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
            logger.info(fName + ".is updated");
            if(!saveProfile()){ logger.error(fName+".failed to write in Db");
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public static final String
            keyCode="code",
            keyToken = "token",
            keyID = "id",
            keyTimeCreated="timeCreated",
            keyTimeAccessed="timeAccessed",
            keyTimeRead="timeread",
            keyTimeWrite="timeWrite",
            keyTimeRemoved="timeRemoved";
    public static final String
            keyTokens = "tokens",
            keyTokensArchive = "tokensArchive";
    lcJSONUserProfile safetyUserProfileEntry(lcJSONUserProfile gUserProfile){
        String fName="[safetyUserProfileEntry]";
        try{
            logger.info(fName+".safety check");
            gUserProfile.safetyPutFieldEntry(keyTokens,new JSONObject());
            gUserProfile.safetyPutFieldEntry(keyTokensArchive,new JSONArray());
            return  gUserProfile;
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return  gUserProfile;
        }
    }

}
