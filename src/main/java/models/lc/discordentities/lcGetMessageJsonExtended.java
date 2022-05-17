package models.lc.discordentities;

import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class lcGetMessageJsonExtended extends lcGetMessageJson{
    //https://discord.com/developers/docs/resources/channel#message-object
    Logger logger = Logger.getLogger(getClass());

    protected lcGetMessageJsonExtended(){
    }
    public lcGetMessageJsonExtended(JSONObject jsonObject){
        set(jsonObject);

    }
    public lcGetMessageJsonExtended(Message message){
        set(message);
    }
    public lcGetMessageJsonExtended(TextChannel textChannel, long message_id){
        set(textChannel,message_id);
    }
    public lcGetMessageJsonExtended(PrivateChannel privateChannel, long message_id){
        set(privateChannel,message_id);String fName="build";
    }
    public lcGetMessageJsonExtended(List<JDA>jdas,long channel_id, long message_id){
        set(jdas,channel_id,message_id);String fName="build";
    }
    private Message message=null;
    private TextChannel textChannel=null;
    private PrivateChannel privateChannel=null;
    private lcGetMessageJsonExtended clear() {
        String fName="[clear]";
        jsonMessage=new JSONObject();
        message=null;
        textChannel=null;
        privateChannel=null;
        logger.info(fName + ".cleared");
        return this;
    }
    private lcGetMessageJsonExtended set(JSONObject jsonObject) {
        String fName="[setJson]";
        try {
            clear();
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonMessage=new JSONObject(jsonObject.toString());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetMessageJsonExtended set(Message message) {
        String fName="[setMessage]";
        try {
            clear();
            logger.info(fName + ".message="+message.getId());
            JSONObject jsonObject=getHttpBody(message.getChannel().getIdLong(),message.getIdLong());
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonMessage=new JSONObject(jsonObject.toString());
            this.message=message;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetMessageJsonExtended set(TextChannel textChannel, long message_id) {
        String fName="[setMessage4TextChannel]";
        try {
            clear();
            logger.info(fName + ".textChannel="+textChannel.getId()+", message_id="+message_id);
            JSONObject jsonObject=getHttpBody(textChannel.getIdLong(),message_id);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonMessage=new JSONObject(jsonObject.toString());
            this.textChannel=textChannel;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetMessageJsonExtended set(PrivateChannel privateChannel, long message_id) {
        String fName="[setMessage4PrivateChannel]";
        try {
            clear();
            logger.info(fName + ".privateChannel="+privateChannel.getId()+", message_id="+message_id);
            JSONObject jsonObject=getHttpBody(privateChannel.getIdLong(),message_id);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonMessage=new JSONObject(jsonObject.toString());
            this.privateChannel=privateChannel;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetMessageJsonExtended set(List<JDA> jdas, long channel_id, long message_id) {
        String fName="[setMessage4JDA]";
        try {
            clear();
            logger.info(fName + ".channel_id="+channel_id+", message_id="+message_id);
            JSONObject jsonObject=getHttpBody(channel_id,message_id);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonMessage=new JSONObject(jsonObject.toString());
            for(JDA jda:jdas){
                try {
                    TextChannel textChannel=jda.getTextChannelById(channel_id);
                    if(textChannel!=null){
                        this.textChannel=textChannel;
                        logger.info(fName + ".found channel at guild="+textChannel.getGuild().getId());
                        break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    PrivateChannel privateChannel=jda.getPrivateChannelById(channel_id);
                    if(privateChannel!=null){
                        this.privateChannel=privateChannel;
                        logger.info(fName + ".found private channel for ="+privateChannel.getUser().getId());
                        break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Message getMessage() {
        String fName="[getMessage]";
        try {
            if(this.message!=null){
                logger.info(fName + ".global message is not null>return");
                return  this.message;
            }
            Message message=null;
            if(this.textChannel!=null){
                logger.info(fName + ".textchannel mode");
                message=this.textChannel.retrieveMessageById(getId()).complete();
            }
            if(this.privateChannel!=null){
                logger.info(fName + ".privatechannel mode");
                message=this.privateChannel.retrieveMessageById(getId()).complete();
            }
            if(message!=null){
                logger.info(fName + ".message is not null");
                this.message=message;
            }
            return message;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel getTextChannel() {
        String fName="[getTextChannel]";
        try {
            if(this.message!=null){
                logger.info(fName + ".global message is not null>return");
                return  this.message.getTextChannel();
            }
            if(this.textChannel!=null){
                logger.info(fName + ".textchannel");
                return this.textChannel;
            }
            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PrivateChannel getPrivateChannel() {
        String fName="[getPrivateChannel]";
        try {
            if(this.message!=null){
                logger.info(fName + ".global message is not null>return");
                return  this.message.getPrivateChannel();
            }
            Message message=null;
            if(this.privateChannel!=null){
                logger.info(fName + ".privatechannel ");
                return privateChannel;
            }

            return null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
