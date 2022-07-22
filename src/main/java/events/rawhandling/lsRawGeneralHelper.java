package events.rawhandling;

import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

public interface lsRawGeneralHelper
{
    static String getType(RawGatewayEvent event){
        String fName="[getType]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            return event.getType();
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return "";
        }
    }
    static JSONObject getPayloadAsJson(RawGatewayEvent event){
        String fName="[getPayloadAsJson]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            JSONObject jsonObject=new JSONObject(event.getPayload().toString());
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return new JSONObject();
        }
    }
    static String getUserId(RawGatewayEvent event){
        String fName="[getUserId]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            JSONObject jsonObject=getPayloadAsJson(event);
            String id="";
            if(jsonObject.has(llCommonKeys.keyUser_id)){
                id=jsonObject.optString(llCommonKeys.keyUser_id);
            }
            if(jsonObject.has(llCommonKeys.keyUser)){
                try{
                    if(jsonObject.optJSONObject(llCommonKeys.keyUser).has(llCommonKeys.keyId)){
                        id=jsonObject.optJSONObject(llCommonKeys.keyUser).optString(llCommonKeys.keyId);
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                }
            }
            if(jsonObject.has(llCommonKeys.keyMember)){
                try{
                    if(jsonObject.optJSONObject(llCommonKeys.keyMember).has(llCommonKeys.keyId)){
                        id=jsonObject.optJSONObject(llCommonKeys.keyMember).optString(llCommonKeys.keyId);
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                }
                try{
                    if(jsonObject.optJSONObject(llCommonKeys.keyMember).has(llCommonKeys.keyUser)){
                        if(jsonObject.optJSONObject(llCommonKeys.keyMember).optJSONObject(llCommonKeys.keyUser).has(llCommonKeys.keyId)){
                            id=jsonObject.optJSONObject(llCommonKeys.keyMember).optJSONObject(llCommonKeys.keyUser).optString(llCommonKeys.keyId);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                }
            }
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return "";
        }
    }
    static String getChannelId(RawGatewayEvent event){
        String fName="[getChannelId]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            JSONObject jsonObject=getPayloadAsJson(event);
            String id="";
            if(jsonObject.has(llCommonKeys.keyChannel_id)){
                id=jsonObject.optString(llCommonKeys.keyChannel_id);
            }
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return "";
        }
    }
    static String getMessageId(RawGatewayEvent event){
        String fName="[getMessageId]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            JSONObject jsonObject=getPayloadAsJson(event);
            String id="";
            if(jsonObject.has(llCommonKeys.keyMessage_id)){
                id=jsonObject.optString(llCommonKeys.keyMessage_id);
            }
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return "";
        }
    }
    static String getMemberId(RawGatewayEvent event){
        String fName="[getMemberId]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            JSONObject jsonObject=getPayloadAsJson(event);
            String id="";
            if(jsonObject.has(llCommonKeys.keyMember)){
                try{
                    if(jsonObject.optJSONObject(llCommonKeys.keyMember).has(llCommonKeys.keyId)){
                        id=jsonObject.optJSONObject(llCommonKeys.keyMember).optString(llCommonKeys.keyId);
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                }
                try{
                    if(jsonObject.optJSONObject(llCommonKeys.keyMember).has(llCommonKeys.keyUser)){
                        if(jsonObject.optJSONObject(llCommonKeys.keyMember).optJSONObject(llCommonKeys.keyUser).has(llCommonKeys.keyId)){
                            id=jsonObject.optJSONObject(llCommonKeys.keyMember).optJSONObject(llCommonKeys.keyUser).optString(llCommonKeys.keyId);
                        }
                    }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                }
            }
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return "";
        }
    }
    static String getGuildId(RawGatewayEvent event){
        String fName="[getGuildId]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            JSONObject jsonObject=getPayloadAsJson(event);
            String id="";
            if(jsonObject.has(llCommonKeys.keyGuild_Id)){
                id=jsonObject.optString(llCommonKeys.keyGuild_Id);
            }
            logger.info(fName+"id="+id);
            return id;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return "";
        }
    }

    static long getUserIdLong(RawGatewayEvent event){
        String fName="[getUserIdLong]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
           return Long.parseLong(getUserId(event));
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return 0;
        }
    }
    static long getChannelIdLong(RawGatewayEvent event){
        String fName="[getChannelIdLong]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            return Long.parseLong(getChannelId(event));
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return 0;
        }
    }
    static long getMessageIdLong(RawGatewayEvent event){
        String fName="[getMessageIdLong]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            return Long.parseLong(getMessageId(event));
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return 0;
        }
    }
    static long getMemberIdLong(RawGatewayEvent event){
        String fName="[getMemberIdLong]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            return Long.parseLong(getMemberId(event));
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return 0;
        }
    }
    static long getGuildIdLong(RawGatewayEvent event){
        String fName="[getGuildIdLong]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            return Long.parseLong(getGuildId(event));
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return 0;
        }
    }

    static User getUser(RawGatewayEvent event){
        String fName="[getUser]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            User user=event.getJDA().getUserById(getUserId(event));
            return user;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static TextChannel getTextChannel(RawGatewayEvent event){
        String fName="[getTextChannel]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            TextChannel channel=event.getJDA().getTextChannelById(getChannelId(event));
            return channel;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static VoiceChannel getVoiceChannel(RawGatewayEvent event){
        String fName="[getVoiceChannel]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            VoiceChannel channel=event.getJDA().getVoiceChannelById(getChannelId(event));
            return channel;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static PrivateChannel getPrivateChannel(RawGatewayEvent event){
        String fName="[getPrivateChannel]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            PrivateChannel channel=event.getJDA().getPrivateChannelById(getChannelId(event));
            return channel;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Message getMessage(RawGatewayEvent event){
        String fName="[getMessageId]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            Message message=null;
            TextChannel textChannel=null;
            PrivateChannel privateChannel=getPrivateChannel(event);
            if(privateChannel!=null){
                message=privateChannel.retrieveMessageById(getMessageId(event)).complete();
            }else{
                textChannel=getTextChannel(event);
                if(textChannel!=null){
                    message=textChannel.retrieveMessageById(getMessageId(event)).complete();
                }
            }
            return message;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Guild getGuild(RawGatewayEvent event){
        String fName="[getGuild]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            Guild guild=null;
            String guild_id=getGuildId(event);
            if(guild_id!=null&&!guild_id.isBlank()){
                try{
                    guild=event.getJDA().getGuildById(getGuildId(event));
                    return guild;
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);

                }
            }
            TextChannel textChannel=getTextChannel(event);
            if(textChannel!=null){
                try{
                    guild=textChannel.getGuild();
                    return guild;
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);

                }
            }
            VoiceChannel voiceChannel=getVoiceChannel(event);
            if(voiceChannel!=null){
                try{
                    guild=voiceChannel.getGuild();
                    return guild;
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);

                }
            }
            return null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    static Member getMember(RawGatewayEvent event){
        String fName="[getMember]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            Guild guild=getGuild(event);
            Member member=guild.getMemberById(getMemberId(event));
            return member;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return null;
        }
    }
    public class  RawEmoji{
        Logger logger = Logger.getLogger(getClass());
        String name="", id="";JDA jda=null;
        Emote emote=null;
        public RawEmoji(){
        }
        public RawEmoji(String name, String id, JDA jda){
            String fName="[build]";
            this.name=name;
            this.id=id;
            this.jda=jda;
            logger.info(fName+".name="+this.name+", id="+this.id);
            setUpEmote();
        }
        public RawEmoji(JSONObject jsonObject, JDA jda){
            String fName="[build]";
            this.jda=jda;
            logger.info(fName+".jsonObject="+jsonObject.toString());
            if(jsonObject.has(llCommonKeys.keyEmoji)){
                if(jsonObject.optJSONObject(llCommonKeys.keyEmoji).has(llCommonKeys.keyId)){
                    id=jsonObject.optJSONObject(llCommonKeys.keyEmoji).optString(llCommonKeys.keyId);
                }
                if(jsonObject.optJSONObject(llCommonKeys.keyEmoji).has(llCommonKeys.keyName)){
                   name=jsonObject.optJSONObject(llCommonKeys.keyEmoji).optString(llCommonKeys.keyName);
                }
            }else{
                if(jsonObject.has(llCommonKeys.keyId)){
                    id=jsonObject.optString(llCommonKeys.keyId);
                }
                if(jsonObject.has(llCommonKeys.keyName)){
                    name=jsonObject.optString(llCommonKeys.keyName);
                }
            }
            logger.info(fName+".name="+this.name+", id="+this.id);
            setUpEmote();
        }
        public String getId(){
            return id;
        }
        public String getName(){
            return name;
        }
        public long getIdLong(){
            String fName="[getIdLong]";
            try{
                return Long.parseLong(id);
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                return 0;
            }
        }
        public long getNameLong(){
            String fName="[getNameLong]";
            try{
                return Long.parseLong(name);
            }catch (Exception e){
                logger.error(fName+".exception=" + e);
                return 0;
            }
        }
        private void setUpEmote(){
            String fName="[setUpEmote]";
            try{
                emote=jda.getEmoteById(id);
            }catch (Exception e){
                logger.error(fName+".exception=" + e);

            }
        }
        public Emote toEmote(){
            return  emote;
        }
    }
    static boolean isType_MessageReactionAdd(RawGatewayEvent event){
        String fName="[getType]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            if(event.getType().equalsIgnoreCase("MESSAGE_REACTION_ADD")){
                logger.info(fName+"true");
                return true;
            }
            logger.info(fName+"false");
            return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }

    static lsRawGeneralHelper.RawEmoji getEmoji(RawGatewayEvent event){
        String fName="[getEmoji]";
        Logger logger = Logger.getLogger(lsRawGeneralHelper.class);
        logger.info(fName+".init");
        try{
            lsRawGeneralHelper.RawEmoji emoji=new lsRawGeneralHelper.RawEmoji(lsRawGeneralHelper.getPayloadAsJson(event),event.getJDA());
            if(emoji!=null&&!emoji.getId().isBlank())logger.info(fName+"emoji retrieved");
            else  logger.info(fName+"failed to retrieve emoji");
            return emoji;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            return new lsRawGeneralHelper.RawEmoji();
        }
    }
}