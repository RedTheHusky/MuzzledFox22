package models.lc.discordentities.basicobjects;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ls.lsDiscordApi;
import models.ls.lsMemberHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetGuildMemberUrl;

public class lcMemberObject implements llCommonKeys.MemberStructure {
    Logger logger = Logger.getLogger(getClass());
    protected JDA jda;protected List<JDA> jdas=new ArrayList<>();
    public lcMemberObject(){
        String fName="build";
        logger.info(fName+".blank");
    }
    public lcMemberObject(RawGatewayEvent event){
        String fName="build";
        try {
            set(event);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMemberObject(JDA jda,JSONObject jsonObject){
        String fName="build";
        try {
            this.jda=jda;
            setMember(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMemberObject(JDA jda, long guild,long member){
        String fName="build";
        try {
            this.jda=jda;
            setMember(getHttpBody(guild,member));
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
            if(!event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                logger.warn(fName+".invaliod type");
                return false;
            }
            String strPayload=event.getPayload().toString();
            jsonPayload=new JSONObject(strPayload);
            try {
                if(jsonPayload.has(llCommonKeys.keyMember)){
                    jsonMember=jsonPayload.getJSONObject(llCommonKeys.keyMember);
                    logger.info(fName+".jsonMember="+jsonMember.toString());
                    if(jsonMember.has(llCommonKeys.keyUser))jsonUser=jsonMember.getJSONObject(llCommonKeys.keyUser);
                    logger.info(fName+".jsonUser="+jsonUser.toString());
                    guild_id=jsonPayload.getString("guild_id");

                }
                if(jsonPayload.has(llCommonKeys.keyUser)){
                    if(jsonMember.has(llCommonKeys.keyUser))jsonUser=jsonPayload.getJSONObject(llCommonKeys.keyUser);
                    logger.info(fName+".jsonUser="+jsonUser.toString());
                    setUser(jsonUser);
                }
                return setMember(jsonMember);
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
    protected String nick=null;
    protected List<String>roles=new ArrayList<>();
    protected String joined_at=null;
    protected String premium_since=null;
    protected boolean deaf=false;
    protected boolean mute=false;
    protected boolean pending=false;
    protected String permissions=null;
    protected String guild_id=null;
    protected String avatar=null;
    protected JSONObject getHttpBody( long guild,long member){
        String fName="[getHttpBody]";
        try {
            HttpResponse<JsonNode> jsonResponse=getHttp(guild,member);
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
                guild_id=String.valueOf(guild);
                return body;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    protected HttpResponse<JsonNode> getHttp( long guild,long member){
        String fName="[getHttp]";
        try {
            logger.info(fName+".guild="+guild+", member="+member);
            String url = liGetGuildMemberUrl;
            url=url.replaceAll("!GUILD", String.valueOf(guild)).replaceAll("!MEMBER", String.valueOf(member));
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
    protected boolean setMember(JSONObject jsonObject){
        String fName="[setMember]";
        try {
            logger.info(fName+".creating");
            String key="";
            key=keyNick;if(jsonObject.has(key))nick=jsonObject.optString(key);
            key=keyJoinedAt;if(jsonObject.has(key))joined_at=jsonObject.optString(key);
            key=keyPremiumSince;if(jsonObject.has(key))premium_since=jsonObject.optString(key);
            key=keyDeaf;if(jsonObject.has(key))deaf=jsonObject.optBoolean(key);
            key=keyMute;if(jsonObject.has(key))mute=jsonObject.optBoolean(key);
            key=keyPending;if(jsonObject.has(key))pending=jsonObject.optBoolean(key);
            key=keyPermissions;if(jsonObject.has(key))permissions=jsonObject.optString(key);
            key= llCommonKeys.MemberStructure.keyAvatar;if(jsonObject.has(key))avatar=jsonObject.optString(key);
            key=keyRoles;if(jsonObject.has(key)) {
                JSONArray jsonArray=jsonObject.getJSONArray(key);
                for(int i=0;i<jsonArray.length();i++){
                    roles.add(jsonArray.getString(i));
                }
            }
            if(jsonObject.has(llCommonKeys.keyUser))setUser(jsonObject.getJSONObject(llCommonKeys.keyUser));

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    lcUserObject userObject=new lcUserObject();
    protected boolean setUser(JSONObject jsonObject){
        String fName="[setUser]";
        try {
            logger.info(fName+".creating");
            return userObject.setJson(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJson(){
        String fName="[getJson]";
        try {
            logger.info(fName+".creating");
            JSONObject jsonObject=new JSONObject();
            String key="";
            jsonObject.put(llCommonKeys.keyUser,userObject.getJson());
            key=keyNick;if(nick!=null&&!nick.isBlank())jsonObject.put(key,nick);
            key=keyJoinedAt;if(joined_at!=null&&!joined_at.isBlank())jsonObject.put(key,joined_at);
            key=keyPremiumSince;if(premium_since!=null&&!premium_since.isBlank())jsonObject.put(key,premium_since);
            key=keyDeaf;if(deaf)jsonObject.put(key,true);
            key=keyMute;if(mute)jsonObject.put(key,true);
            key=keyPending;if(pending)jsonObject.put(key,true);
            key=keyPermissions;if(permissions!=null&&!permissions.isBlank())jsonObject.put(key,permissions);
            logger.info(fName+".jsonObject="+ jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public List<String> getRoles(){
        String fName="[getRolesAsJson]";
        try {
            return roles;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public JSONArray getRolesAsJson(){
        String fName="[getRolesAsJson]";
        try {
            JSONArray jsonArray=new JSONArray();
            for(String role:roles){
                jsonArray.put(role);
            }
            logger.info(fName + ".jsonArray=" + jsonArray.toString());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public Member getMember(){
        String fName="[getMember]";
        try {
            if(jda!=null){
                return jda.getGuildById(getGuildId()).getMemberById(getId());
            }else{
                for(JDA jda:jdas){
                    try {
                        Member member=jda.getGuildById(getGuildId()).getMemberById(getId());
                        if(member!=null){
                            this.jda=jda;
                            return member;
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
    public User getUser(){
        String fName="[getUser]";
        try {
            return userObject.getUser();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcUserObject getUserObject(){
        String fName="[getUser]";
        try {
            return userObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getGuildId(){
        String fName="getGuildId";
        try {
            return guild_id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getGuildIDAsLong(){
        String fName="getGuildIDAsLong";
        try {
            return Long.parseLong(getGuildId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getNick(){
        String fName="[getnick]";
        try {
            return nick;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getJoinedAt(){
        String fName="[getjoined_at]";
        try {
            return joined_at;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getPremiumSince(){
        String fName="[getpremium_since]";
        try {
            return premium_since;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getPermissions(){
        String fName="[getpermissions]";
        try {
            return permissions;
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
    public String getAvatar(){
        String fName="[getAvatar]";
        try {
            return avatar;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAvatarUrl(){
        String fName="[getAvatar]";
        try {
            if(getAvatar()==null||getAvatar().isBlank()){
                logger.info(fName+"no avatar id");
                return "";
            }
            String str= lsMemberHelper.guildMemberAvatar(getGuildId(),getId(),getAvatar(), lsDiscordApi.ImageSize.int512);
            logger.info(fName+"str="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean isDeaf(){
        String fName="[isdeaf]";
        try {
            return deaf;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isMute(){
        String fName="[ismute]";
        try {
            return mute;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPending(){
        String fName="[ispending]";
        try {
            return pending;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getId(){
        String fName="[getId]";
        try {
            return userObject.getId();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getIdAsLong(){
        String fName="[getIDAsLong]";
        try {
            return userObject.getIdAsLong();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
}
