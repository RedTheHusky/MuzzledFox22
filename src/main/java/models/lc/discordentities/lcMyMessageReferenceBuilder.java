package models.lc.discordentities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.internal.utils.Helpers;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class lcMyMessageReferenceBuilder {
    //https://discord.com/developers/docs/resources/channel#allowed-mentions-object-allowed-mention-types
    Logger logger = Logger.getLogger(getClass());

    public lcMyMessageReferenceBuilder(){
        String fName="build";
        try {

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageReferenceBuilder(JSONObject jsonObject){
        String fName="build";
        try {
            setJson(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMyMessageReferenceBuilder clear() {
        String fName="[clear]";
        try {
            message_id="";channel_id="";guild_id="";
            fail_if_not_exists=false;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    String message_id="",channel_id="",guild_id="";
    boolean fail_if_not_exists=false;
    public static  final String keymessage_id="message_id",keychannel_id="channel_id",keyguild_id="guild_id",keyfail_if_not_exists="fail_if_not_exists";
    public lcMyMessageReferenceBuilder setJson(JSONObject jsonObject) {
        String fName="[setJson]";
        try {
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            if(jsonObject.has(keymessage_id))message_id=jsonObject.getString(keymessage_id);
            if(jsonObject.has(keychannel_id))channel_id=jsonObject.getString(keychannel_id);
            if(jsonObject.has(keyguild_id))guild_id=jsonObject.getString(guild_id);
            if(jsonObject.has(keyfail_if_not_exists))fail_if_not_exists=jsonObject.getBoolean(keyfail_if_not_exists);
            return  this;
        }catch (Exception e){
            return null;
        }
    }
    public JSONObject getJson() {
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            if(message_id!=null&&!message_id.isBlank())jsonObject.put(keymessage_id,message_id);
            if(channel_id!=null&&!channel_id.isBlank())jsonObject.put(keychannel_id,channel_id);
            if(guild_id!=null&&!guild_id.isBlank())jsonObject.put(keyguild_id,guild_id);
            if(fail_if_not_exists==true)jsonObject.put(keyfail_if_not_exists,true);
            logger.info(fName + ".jsonObject="+jsonObject.toString());
            return  jsonObject;
        }catch (Exception e){
            return new JSONObject();
        }
    }

    public lcMyMessageReferenceBuilder setFailIfNotExists(boolean fail_if_not_exists) {
        String fName="[setFailIfNotExists]";
        try {
            logger.info(fName + ".value="+fail_if_not_exists);
            this.fail_if_not_exists= fail_if_not_exists;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public boolean getFailIfNotExists() {
        String fName="[getFailIfNotExists]";
        try {
            logger.info(fName + ".value="+this.fail_if_not_exists);
            return this.fail_if_not_exists;
        }catch (Exception e){
            return false;
        }
    }
    public lcMyMessageReferenceBuilder setMessageId(@Nullable String message_id) {
        String fName="[setMessageId]";
        try {
            logger.info(fName + ".value="+message_id);
            this.message_id=message_id;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public String getMessageId() {
        String fName="[getMessageId]";
        try {
            logger.info(fName + ".message_id="+message_id);
            return message_id;
        }catch (Exception e){
            return "";
        }
    }
    public lcMyMessageReferenceBuilder setMessageId( long message_id) {
        String fName="[setMessageId]";
        try {
            logger.info(fName + ".value="+message_id);
            this.message_id=String.valueOf(message_id);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public long getMessageIdAsLong() {
        String fName="[getMessageIdAsLong]";
        try {
            Long l=Long.parseLong(message_id);
            logger.info(fName + ".long="+l);
            return l;
        }catch (Exception e){
            return 0L;
        }
    }

    public lcMyMessageReferenceBuilder setChannelId(@Nullable String channel_id) {
        String fName="[setChannelId]";
        try {
            logger.info(fName + ".value="+channel_id);
            this.channel_id=channel_id;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public String getChannelId() {
        String fName="[getChannelId]";
        try {
            logger.info(fName + ".channel_id="+channel_id);
            return channel_id;
        }catch (Exception e){
            return "";
        }
    }
    public lcMyMessageReferenceBuilder setChannelId(long channel_id) {
        String fName="[setChannelId]";
        try {
            logger.info(fName + ".value="+channel_id);
            this.channel_id=String.valueOf(channel_id);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public long getChannelIdAsLong() {
        String fName="[getChannelIdAsLong]";
        try {
            Long l=Long.parseLong(channel_id);
            logger.info(fName + ".long="+l);
            return l;
        }catch (Exception e){
            return 0L;
        }
    }

    public lcMyMessageReferenceBuilder setGuildId(@Nullable String guild_id) {
        String fName="[setGuildId]";
        try {
            logger.info(fName + ".value="+guild_id);
            this.guild_id=guild_id;
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public String getGuildId() {
        String fName="[getGuildId]";
        try {
            logger.info(fName + ".guild_id="+guild_id);
            return guild_id;
        }catch (Exception e){
            return "";
        }
    }
    public lcMyMessageReferenceBuilder setGuildId(long guild_id) {
        String fName="[setGuildId]";
        try {
            logger.info(fName + ".value="+guild_id);
            this.guild_id=String.valueOf(guild_id);
            return this;
        }catch (Exception e){
            return null;
        }
    }
    public long getGuildIdAsLong() {
        String fName="[getGuildIdAsLong]";
        try {
            Long l=Long.parseLong(guild_id);
            logger.info(fName + ".long="+l);
            return l;
        }catch (Exception e){
            return 0L;
        }
    }

}
