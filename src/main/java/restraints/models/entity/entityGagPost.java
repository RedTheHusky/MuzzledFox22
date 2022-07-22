package restraints.models.entity;

import kong.unirest.json.JSONObject;
import models.lc.lcSqlConnEntity;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import restraints.in.iUtility;
import restraints.models.enums.GAGLEVELS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class entityGagPost {
    Logger logger = Logger.getLogger(getClass());
    private String content,gaglevel;private long user_id,guild_id,channel_id,org_message_id,new_message_id;
    public entityGagPost(){}
    public entityGagPost(JSONObject jsonObject){set(jsonObject);}
    private entityGagPost clear(){
        String fName = "[clear]";
        try {
            content=null;gaglevel=null;user_id=0;guild_id=0;channel_id=0;org_message_id=0;new_message_id=0;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGagPost set(JSONObject jsonObject){
        String fName = "[set]";
        try {
            if(jsonObject==null)throw new Exception("Json is null!");
            if(jsonObject.isEmpty())throw new Exception("Json is empty!");
            if(jsonObject.has(iUtility.KEYS.content))content=jsonObject.getString(iUtility.KEYS.content);
            if(jsonObject.has(iUtility.KEYS.gaglevel))gaglevel=jsonObject.getString(iUtility.KEYS.gaglevel);
            if(jsonObject.has(iUtility.KEYS.user_id))user_id=jsonObject.getLong(iUtility.KEYS.user_id);
            if(jsonObject.has(iUtility.KEYS.guild_id))guild_id=jsonObject.getLong(iUtility.KEYS.guild_id);
            if(jsonObject.has(iUtility.KEYS.channel_id))channel_id=jsonObject.getLong(iUtility.KEYS.channel_id);
            if(jsonObject.has(iUtility.KEYS.org_message_id))org_message_id=jsonObject.getLong(iUtility.KEYS.org_message_id);
            if(jsonObject.has(iUtility.KEYS.new_message_id))new_message_id=jsonObject.getLong(iUtility.KEYS.new_message_id);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJson(){
        String fName = "[getJson]";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(iUtility.KEYS.user_id, user_id).put(iUtility.KEYS.guild_id, guild_id).put(iUtility.KEYS.channel_id, channel_id);
            jsonObject.put(iUtility.KEYS.org_message_id, org_message_id).put(iUtility.KEYS.new_message_id, new_message_id);
            jsonObject.put(iUtility.KEYS.content, content).put(iUtility.KEYS.gaglevel, gaglevel);
            logger.info(fName+"json="+jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public String getContent() {
        return content;
    }

    public String getGaglevelAsString() {
        return gaglevel;
    }
    public GAGLEVELS getGaglevel() {
        String fName = "[getGaglevel]";
        try {
            GAGLEVELS g=GAGLEVELS.valueByName(gaglevel);
            return g;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public long getUserId() {
        return user_id;
    }
    public User getUser() {
        String fName = "[getUser]";
        try {
            return lsUserHelper.lsGetUserById(lsGlobalHelper.sGetGlobal(),user_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember() {
        String fName = "[getUser]";
        try {
            return lsMemberHelper.lsGetMember(getGuild(),user_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public long getGuildId() {
        return guild_id;
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            return lsGuildHelper.getGuild(lsGlobalHelper.sGetGlobal().getJDAList(),guild_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public long getChannelId() {
        return channel_id;
    }
    public TextChannel getChannel() {
        String fName = "[getChannel]";
        try {
            return lsChannelHelper.lsGetTextChannelById(lsGlobalHelper.sGetGlobal().getJDAList(),channel_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public long getOrgMessageId() {
        return org_message_id;
    }
    public long getNewMessageId() {
        return new_message_id;
    }
    public Message getMessage() {
        String fName = "[getMessage]";
        try {
            return lsMessageHelper.lsGetMessageById(getGuild(),new_message_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e+", StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
