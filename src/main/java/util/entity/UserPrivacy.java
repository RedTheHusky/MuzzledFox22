package util.entity;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonVariables;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.apache.log4j.Logger;
import restraints.models.*;

import java.awt.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class UserPrivacy {
    Logger logger = Logger.getLogger(getClass());

    public lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
    lcGlobalHelper gGlobal;
    Member gMember;
    Guild gGuild; lcBDSMGuildProfiles gBDSMCommands;
    public boolean flagGet4Cache=true,flagPut2Cache=true;
    public UserPrivacy(){
       
    }
    public UserPrivacy(lcGlobalHelper global, Member member){
        String fName="[constructor]";
        try {
            build(global, member);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public UserPrivacy(lcGlobalHelper global, GuildMessageReceivedEvent event){
        String fName="[constructor]";
        try {
            build(global,event.getMember());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean build(lcGlobalHelper global, Member member){
        String fName="[build]";
        try {
            logger.info(fName);
            gGlobal=global;
            gMember=member;
            gGuild=gMember.getGuild();
            if(!getProfile()){
                logger.warn("failed to get profile>new profile");
                if(!newProfile()){
                    logger.warn("failed to create new profile");
                    return false;
                }
            }else{
                logger.info("got profile");
            }
            if(setDefaultProfileVariables()){
                logger.info("setDefaultProfileVariables updated>save");
                if(!saveProfile()){
                    logger.warn("failed to save>ignore");
                }
            }
            if(flagPut2Cache){
                if(putProfile()){
                    logger.info("successfully put profile to cache");
                }else{
                    logger.warn("failed put profile to cache");
                }
            }else{
                logger.info("disabled to put profile to cache");
            }
            logger.info("gUserProfile.json="+gUserProfile.jsonObject.toString());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(fName);
            if(flagGet4Cache)getCacheProfile();
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
                return true;
            }else{
                logger.info(fName + ".need to get or create");
                return getSQLProfile();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(){
        String fName="[getCacheProfile]";
        try {
            logger.info(fName);
            gUserProfile=gGlobal.getUserProfile(profileName,gMember,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
                return true;
            }else{
                logger.info(fName + ".not cached");
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(){
        String fName="[getSQLProfile]";
        try {
            logger.info(fName);
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild);
            gUserProfile.setTable(llGlobalHelper.llMemberProfile).setName(profileName);
            if(gUserProfile.getProfile()){
                logger.info(fName + ".has sql entry");
                return true;
            }else{
                logger.info(fName + ".no sql entry");
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            if(global==null){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            gGlobal=global;
            logger.info(fName+"set");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMember(Member member){
        String fName="[setMember]";
        try {
            if(member==null){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            gMember=member;
            logger.info(fName+"member="+member.getId());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            if(guild==null){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            gGuild=guild;
            logger.info(fName+"guild="+guild.getId());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(lcGlobalHelper global,Member member){
        String fName="[getProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            if(!getProfile()){
                logger.info(fName+"failed to get>false");
                return false;
            }
            logger.info(fName+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(lcGlobalHelper global,Member member){
        String fName="[getCacheProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            if(!getCacheProfile()){
                logger.info(fName+"failed to get>false");
                return false;
            }
            logger.info(fName+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(lcGlobalHelper global,Member member){
        String fName="[getSQLProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            if(!getSQLProfile()){
                logger.info(fName+"failed to get>false");
                return false;
            }
            logger.info(fName+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDefaultProfileVariables(){
        String fName="[setDefaultProfileVariables]";
        try {
            logger.info(fName);
            gUserProfile.safetyPutFieldEntry(KEYS.guild,new JSONObject());
            gUserProfile.safetyPutFieldEntry(KEYS.guild,KEYS.read_messages,true);
            gUserProfile.safetyPutFieldEntry(KEYS.updateNotification,new JSONObject());
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return false;
            }
            logger.info(fName + ".updated");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean newProfile(){
        String fName="[newProfile]";
        try {
            logger.info(fName);
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild);
            gUserProfile.setTable(llGlobalHelper.llMemberProfile).setName(profileName);
            setDefaultProfileVariables();
            logger.info(fName + ".created");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean resetProfile(){
        String fName="[resetProfile]";
        try {
            logger.info(fName);
            gUserProfile.jsonObject =new JSONObject();
            setDefaultProfileVariables();
            logger.info(fName + ".reseted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    String profileName="privacy";
    public boolean saveProfile(){
        String fName="[saveProfile]";
        try {
            logger.info(fName);
            if(flagPut2Cache)putProfile();
            gUserProfile.setName(profileName).setTable(llGlobalHelper.llMemberProfile);
            if(gUserProfile.saveProfile()){
                logger.info(fName+ ".success");return  true;
            }
            logger.warn(fName+ ".failed");return false;
        }catch (Exception e){
            logger.error(fName+ ".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putProfile(){
        String fName="[putProfile]";
        try {
            logger.info(fName);
            if(gGlobal.putUserProfile(gUserProfile,profileName)){
                logger.info(fName + ".success");return true;
            }
            logger.info(fName + ".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Member getMember() {
        String fName = "[getMember]";
        try {
            if(gMember==null){
                logger.info(fName+"isNull");
                return  null;
            }
            logger.info(fName+"member="+gMember.getId());
            return  gMember;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            if(gGuild==null){
                logger.info(fName+"isNull");
                return  null;
            }
            logger.info(fName+"guild="+gGuild.getId());
            return  gGuild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasGuildMessageRead() {
        String fName = "[hasGuildMessageRead]";
        try {
            if(gUserProfile.jsonObject ==null)throw  new Exception("json is NULL!");
            if(gUserProfile.jsonObject.isEmpty())throw  new Exception("json is Empty!");
            if(!gUserProfile.jsonObject.has(KEYS.guild))throw  new Exception("json does not have key 'guild'!");
            if(!gUserProfile.jsonObject.getJSONObject(KEYS.guild).has(KEYS.read_messages))throw  new Exception("json does not have key 'read_messages'!");
            logger.info(fName + ".has key");
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isGuildMessageRead() {
        String fName = "[isGuildMessageRead]";
        try {
            if(gUserProfile.jsonObject ==null)throw  new Exception("json is NULL!");
            if(gUserProfile.jsonObject.isEmpty())throw  new Exception("json is Empty!");
            if(!gUserProfile.jsonObject.has(KEYS.guild))throw  new Exception("json does not have key 'guild'!");
            if(!gUserProfile.jsonObject.getJSONObject(KEYS.guild).has(KEYS.read_messages))throw  new Exception("json does not have key 'read_messages'!");
            boolean value=gUserProfile.jsonObject.getJSONObject(KEYS.guild).getBoolean(KEYS.read_messages);
            logger.info(fName + ".value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public UserPrivacy setGuildMessageRead(boolean value) {
        String fName = "[setGuildMessageRead]";
        try {
            if(gUserProfile.jsonObject ==null)throw  new Exception("json is NULL!");
            if(gUserProfile.jsonObject.isEmpty()){logger.warn("json is Empty>new");gUserProfile.jsonObject =new JSONObject(); }
            if(!gUserProfile.jsonObject.has(KEYS.guild)){logger.warn("json does not have key 'guild'>put");gUserProfile.jsonObject.put(KEYS.guild,new JSONObject());}
            logger.info(fName + ".value=" + value);
            gUserProfile.jsonObject.getJSONObject(KEYS.guild).put(KEYS.read_messages, value);
            logger.info(fName + ".done");
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public interface KEYS{
        String guild="guild";
        String read_messages="read_messages";
        String updateNotification="update_notification";
    }
    public interface VALUES{
        public interface UPDATE_NOTIFICATION{
            public interface U0415a{
                String key="u0415a",
                        timestamp="timestamp";
                public interface Embed{
                    String description="Prefix '!>' will be removed by the end of April. From that point on use the bot mentioning as prefix or slash commands.";
                    Color color= llColors.llColorBlue3;
                }
            }

        }
    }
    public JSONObject getUpdateNotificationJson(String key){
        String fName="[getUpdateNotificationJson]";
        try {
            logger.info(fName);
            JSONObject jsonObject=gUserProfile.jsonObject;
            if(!jsonObject.has(KEYS.updateNotification)||jsonObject.isNull(KEYS.updateNotification)){
                jsonObject.put(KEYS.updateNotification, new JSONObject());
            }
            jsonObject=jsonObject.getJSONObject(KEYS.updateNotification);
            if(!jsonObject.has(key)||jsonObject.isNull(key)){
                jsonObject.put(key, new JSONObject());
            }
            jsonObject=jsonObject.getJSONObject(VALUES.UPDATE_NOTIFICATION.U0415a.key);
            return  jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int doU0415a(TextChannel textChannel){
        String fName="[doU0415a]";
        try {
            logger.info(fName);
            JSONObject jsonObject=getUpdateNotificationJson(VALUES.UPDATE_NOTIFICATION.U0415a.key);
            if(jsonObject==null)throw  new Exception("jsonObject is NULL!");
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setDescription(VALUES.UPDATE_NOTIFICATION.U0415a.Embed.description);
            embedBuilder.setColor(VALUES.UPDATE_NOTIFICATION.U0415a.Embed.color);
            long duration=llCommonVariables.milliseconds_day;
            Timestamp timestamp_current = new Timestamp(System.currentTimeMillis());
            long current=timestamp_current.getTime(), last=0;
            if(jsonObject.has(VALUES.UPDATE_NOTIFICATION.U0415a.timestamp)&&!jsonObject.isNull(VALUES.UPDATE_NOTIFICATION.U0415a.timestamp)){
                last=jsonObject.getLong(VALUES.UPDATE_NOTIFICATION.U0415a.timestamp);
            }
            long diff=current-last;
            logger.info(fName+"last="+last+", current="+current+", duration="+duration+", diff="+diff);
            if(diff>duration){
                jsonObject.put(VALUES.UPDATE_NOTIFICATION.U0415a.timestamp,current);
                Message message= lsMessageHelper.lsSendMessageResponse(textChannel,embedBuilder.build());
                if(message==null){
                    logger.info(fName+"return -1");
                    return -1;
                }
                lsMessageHelper.lsAddDeleteReactionAndAutoDelete(gGlobal,message,TimeUnit.MINUTES,15);
                logger.info(fName+"return 1");
                return 1;
            }
            logger.info(fName+"return 0");
            return  0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -4;
        }
    }
    private void event4MessageDelete(Message message){
        String fName="[event4MessageDelete]";
        try {
            logger.info(fName+"message.id="+message.getIdLong());
           gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                   e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()),
                   e -> {
                       try {
                           String name=e.getReactionEmote().getName();
                           if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                               lsMessageHelper.lsMessageDelete(message);
                           }
                       }catch (Exception e2){
                           logger.info(fName + ".exception=" + e2);
                           logger.info(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                           event4MessageDelete(message);
                       }
                   },2, TimeUnit.MINUTES, () -> {
                       lsMessageHelper.lsMessageDelete(message);
                   });

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
}
