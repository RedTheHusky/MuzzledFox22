package models.lc.discordentities.basicobjects;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ls.lsDiscordApi;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.*;

public class lcUserObject implements llCommonKeys.UserStructure {
    Logger logger = Logger.getLogger(getClass());
    protected JDA jda;protected List<JDA> jdas=new ArrayList<>();
    public lcUserObject(){
        String fName="build";
        logger.info(fName+".blank");
    }
    public lcUserObject(RawGatewayEvent event){
        String fName="build";
        try {
            set(event);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcUserObject(JDA jda,JSONObject jsonObject){
        String fName="build";
        try {
            if(jda!=null){
                this.jda=jda;
            }
            setJson(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected boolean set(RawGatewayEvent event){
        String fName="set";
        try {
            logger.info(fName+".creating");
            JSONObject jsonPayload=new JSONObject(),jsonMember=new JSONObject(),jsonUser=new JSONObject();
            jda=event.getJDA();
            String strPayload=event.getPayload().toString();
            jsonPayload=new JSONObject(strPayload);
            try {
                if(jsonPayload.has(llCommonKeys.keyMember)){
                    jsonMember=jsonPayload.getJSONObject(llCommonKeys.keyMember);
                    logger.info(fName+".jsonMember="+jsonMember.toString());
                    if(jsonMember.has(llCommonKeys.keyUser))jsonUser=jsonMember.getJSONObject(llCommonKeys.keyUser);
                    logger.info(fName+".jsonUser="+jsonUser.toString());
                }
                if(jsonPayload.has(llCommonKeys.keyUser)){
                    if(jsonMember.has(llCommonKeys.keyUser))jsonUser=jsonPayload.getJSONObject(llCommonKeys.keyUser);
                    logger.info(fName+".jsonUser="+jsonUser.toString());
                }
                return setJson(jsonUser);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected boolean setJson(JSONObject jsonObject){
        String fName="[setJson]";
        try {
            logger.info(fName+".creating");
            String key="";
            key=keyId;if(jsonObject.has(key))id=jsonObject.optString(key);
            key=keyUsername;if(jsonObject.has(key))username=jsonObject.optString(key);
            key=keyDiscriminator;if(jsonObject.has(key))discriminator=jsonObject.optString(key);
            key=keyAvatar;if(jsonObject.has(key))avatar=jsonObject.optString(key);
            key=keyBot;if(jsonObject.has(key))bot=jsonObject.optBoolean(key);
            key=keySystem;if(jsonObject.has(key))system=jsonObject.optBoolean(key);
            key=keyMfaEnabled;if(jsonObject.has(key))mfa_enabled=jsonObject.optBoolean(key);
            key=keyVerified;if(jsonObject.has(key))verified=jsonObject.optBoolean(key);
            key=keyLocale;if(jsonObject.has(key))locale=jsonObject.optString(key);
            key=keyEmail;if(jsonObject.has(key))email=jsonObject.optString(key);
            key=keyFlags;if(jsonObject.has(key))flags=jsonObject.optInt(key);
            key=keyPremiumType;if(jsonObject.has(key))premium_type=jsonObject.optInt(key);
            key=keyPublicFlags;if(jsonObject.has(key))public_flags=jsonObject.optInt(key);
            key=keyBanner;if(jsonObject.has(key))banner=jsonObject.optString(key);
            key=keyAccentColor;if(jsonObject.has(key))accent_color=jsonObject.optInt(key);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcUserObject(List<JDA>jdas, long id){
        String fName="[build]";
        try {
            if(jdas!=null){
                this.jdas=jdas;
            }
            set(id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcUserObject(JDA jda, long id){
        String fName="[build]";
        try {
            if(jda!=null){
                this.jda=jda;
            }
           set(id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcUserObject(User user){
        String fName="[build]";
        try {
            if(user!=null){
              this.jda=user.getJDA();
              set(user.getIdLong());
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected boolean set(long id){
        String fName="[set]";
        try {
            logger.info(fName);
            return setJson(getHttpBody(id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    protected JSONObject getHttpBody( long user){
        String fName="[getHttpBody]";
        try {
            HttpResponse<JsonNode> jsonResponse=getHttp(user);
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299||jsonResponse.getStatus()<200){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return new JSONObject();
            }else{
                return body;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    protected HttpResponse<JsonNode> getHttp( long user){
        String fName="[getHttp]";
        try {
            logger.info(fName+"user="+user);
            String url = liGetUserUrl;
            url=url.replaceAll("!USER", String.valueOf(user));
            JSONObject jsonObject=getJson();
            logger.info(fName+".json="+jsonObject.toString());
            logger.info(fName+".url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            return  jsonResponse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    protected String id=null;
    protected String username=null;
    protected String discriminator=null;
    protected String avatar=null;
    protected boolean bot=false;
    protected boolean system=false;
    protected boolean mfa_enabled=false;
    protected String banner=null;
    protected int accent_color=0;
    protected String locale=null;
    protected boolean verified=false;
    protected String email=null;
    protected int flags=0;
    protected int premium_type=0;
    protected int public_flags=0;
    protected String bio=null;
    public JSONObject getJson(){
        String fName="[getJson]";
        try {
            logger.info(fName+".creating");
            JSONObject jsonObject=new JSONObject();
            String key="";
            key=keyId;if(id!=null&&!id.isBlank())jsonObject.put(key,id);
            key=keyUsername;if(username!=null&&!username.isBlank())jsonObject.put(key,username);
            key=keyDiscriminator;if(discriminator!=null&&!discriminator.isBlank())jsonObject.put(key,discriminator);
            key=keyAvatar;if(avatar!=null&&!avatar.isBlank())jsonObject.put(key,avatar);
            key=keyBot;if(bot)jsonObject.put(key,true);
            key=keySystem;if(system)jsonObject.put(key,true);
            key=keyMfaEnabled;if(mfa_enabled)jsonObject.put(key,true);
            key=keyVerified;if(verified)jsonObject.put(key,true);
            key=keyLocale;if(locale!=null&&!locale.isBlank())jsonObject.put(key,locale);
            key=keyEmail;if(email!=null&&!email.isBlank())jsonObject.put(key,email);
            key=keyFlags;if(flags!=0)jsonObject.put(key,flags);
            key=keyPremiumType;if(premium_type!=0)jsonObject.put(key,premium_type);
            key=keyPublicFlags;if(public_flags!=0)jsonObject.put(key,public_flags);
            key=keyBanner;if(banner!=null&&!banner.isBlank())jsonObject.put(key,banner);
            key=keyBio;if(bio!=null&&!bio.isBlank())jsonObject.put(key,bio);
            logger.info(fName+".jsonObject="+ jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getId(){
        String fName="[getId]";
        try {
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getIdAsLong(){
        String fName="[getIDAsLong]";
        try {
            return Long.parseLong(getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            return username;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getDiscriminator(){
        String fName="[getdiscriminator]";
        try {
            return discriminator;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAvatar(){
        String fName="[getavatar]";
        try {
            return avatar;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean isAvatarAnimated(){
        String fName="[isAvatarAnimate]";
        try {
            String id=getAvatar();
            logger.info("id="+id);
            if(lsDiscordApi.isIdAnimated(id)){
                return  true;
            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getAvatarUrl(){
        String fName="[getAvatarUrl]";
        try {
            if(getAvatar()==null||getAvatar().isBlank()){
                logger.info(fName+"no avatar id");
                return "";
            }
            String str= lsUserHelper.userAvatarUrl(getId(),getAvatar(), ImageSize.int512);
            logger.info(fName+"str="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAvatarStaticUrl(){
        String fName="[getAvatarStaticUrl]";
        try {
            if(getAvatar()==null||getAvatar().isBlank()){
                logger.info(fName+"no avatar id");
                return "";
            }
            return liUserBannerUrl.replaceAll("!USER",getId()).replaceAll("!EXT","png").replaceAll("!AVATAR",getBanner())+"?"+ImageSize.size512;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAvatarAnimatedUrl(){
        String fName="[getAvatarAnimatedUrl]";
        try {
            if(getAvatar()==null||getAvatar().isBlank()){
                logger.info(fName+"no avatar id");
                return "";
            }
            return liUserBannerUrl.replaceAll("!USER",getId()).replaceAll("!EXT","gif").replaceAll("!AVATAR",getBanner())+"?"+ImageSize.size512;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getLocale(){
        String fName="[getlocale]";
        try {
            return locale;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getEmail(){
        String fName="[getemail]";
        try {
            return email;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getFlags(){
        String fName="[getFlags]";
        try {
            return flags;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getPremiumType(){
        String fName="[getpremium_type]";
        try {
            return premium_type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getPublicFlags(){
        String fName="[getpublic_flags]";
        try {
            return public_flags;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isBot(){
        String fName="[isbot]";
        try {
            return bot;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isSystem(){
        String fName="[issystem]";
        try {
            return system;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isMfaEnabled(){
        String fName="[ismfa_enabled]";
        try {
            return mfa_enabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isVerified(){
        String fName="[isverified]";
        try {
            return verified;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public User getUser(){
        String fName="[getUser]";
        try {
            if(jda!=null){
                return jda.getUserById(getId());
            }else{
                for(JDA jda:jdas){
                    try {
                       User user=jda.getUserById(getId());
                       if(user!=null){
                           this.jda=jda;
                           return user;
                       }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return null;
                    }
                }
            }
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getBanner(){
        String fName="[getBanner]";
        try {
            return banner;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean isBannerAnimated(){
        String fName="[isBannerAnimate]";
        try {
            String id=getBanner();
            logger.info("id="+id);
            if(lsDiscordApi.isIdAnimated(id)){
                return  true;
            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getBannerUrl(){
        String fName="[getBannerUrl]";
        try {
            if(getBanner()==null||getBanner().isBlank()){
                logger.info(fName+"no banner id");
                return "";
            }
            String str=lsUserHelper.userBannerUrl(getId(),getBanner(), ImageSize.int512);
            logger.info(fName+"str="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getBannerStaticUrl(){
        String fName="[getBannerStaticUrl]";
        try {
            if(getBanner()==null||getBanner().isBlank()){
                logger.info(fName+"no banner id");
                return "";
            }
            return liUserBannerUrl.replaceAll("!USER",getId()).replaceAll("!EXT","png").replaceAll("!BANNER",getBanner())+"?"+ImageSize.size512;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getBannerAnimatedUrl(){
        String fName="[getBannerAnimatedUrl]";
        try {
            if(getBanner()==null||getBanner().isBlank()){
                logger.info(fName+"no banner id");
                return "";
            }
            return liUserBannerUrl.replaceAll("!USER",getId()).replaceAll("!EXT","gif").replaceAll("!BANNER",getBanner())+"?"+ImageSize.size512;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getBio(){
        String fName="[getBio]";
        try {
            return bio;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getAccentColorRaw(){
        String fName="[getAccentColorRaw]";
        try {
            return accent_color;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Color getAccentColor(){
        String fName="[getAccentColor]";
        try {
            return new Color(getAccentColorRaw());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return Color.BLACK;
        }
    }

}
