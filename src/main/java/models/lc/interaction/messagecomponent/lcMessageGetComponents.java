package models.lc.interaction.messagecomponent;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ls.lsMessageHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetChannelMessageUrl;

public class lcMessageGetComponents {
    Logger logger = Logger.getLogger(getClass());
    protected JDA jda;
    public lcMessageGetComponents(){
        String fName="build";
        logger.info(fName+".blank");
    }
    public lcMessageGetComponents(Message message){
        String fName="build";
        try {
            logger.info(fName+".creating");
            set(message);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageGetComponents(TextChannel textChannel, long message){
        String fName="build";
        try {
            logger.info(fName+".creating");
            set(lsMessageHelper.lsGetMessageById(textChannel,message));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageGetComponents(Guild guild,long textchannel_id, long message){
        String fName="build";
        try {
            logger.info(fName+".creating");
            set(lsMessageHelper.lsGetMessageById(guild,textchannel_id,message));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageGetComponents(PrivateChannel privateChannel, long message){
        String fName="build";
        try {
            logger.info(fName+".creating");
            set(lsMessageHelper.lsGetMessageById(privateChannel,message));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageGetComponents(List<JDA>jdas,long channel, long message){
        String fName="build";
        try {
            logger.info(fName+".creating");
            set(jdas,channel,message);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcMessageGetComponents(List<JDA>jdas,long guild,long channel, long message){
        String fName="build";
        try {
            logger.info(fName+".creating");
            set(jdas,guild,channel,message);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private boolean set( List<JDA>jdas,long guild_id,long textchannel_id, long message_id){
        String fName="[set]";
        try {
            logger.info(fName+"guild_id="+guild_id+"textchannel_id="+textchannel_id+", message_id="+message_id);
            Guild guild=null;TextChannel textChannel=null;Message message=null;
            for(JDA jda:jdas){
                try {
                    guild=jda.getGuildById(guild_id);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(guild!=null){
                    logger.info(fName+"found text channel");
                    textChannel=guild.getTextChannelById(textchannel_id);
                    break;
                }
            }
            if(textChannel==null){
                logger.info(fName+"textChannel is null");
                return false;
            }
            message=textChannel.retrieveMessageById(message_id).complete();
            if(message==null){
                logger.info(fName+"message is null");
                return false;
            }
            return  set(message);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean set( List<JDA>jdas,long channel_id, long message_id){
        String fName="[set]";
        try {
            logger.info(fName+"channel_id="+channel_id+", message_id="+message_id);
            TextChannel textChannel=null;PrivateChannel privateChannel=null; Message message=null;
            for(JDA jda:jdas){
                try {
                    textChannel=jda.getTextChannelById(channel_id);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    privateChannel=jda.getPrivateChannelById(channel_id);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(textChannel!=null){
                    logger.info(fName+"found text channel");
                    message=textChannel.retrieveMessageById(message_id).complete();
                    break;
                }else
                if(privateChannel!=null){
                    logger.info(fName+"found private channel");
                    message=privateChannel.retrieveMessageById(message_id).complete();
                    break;
                }

            }
            if(message==null){
                logger.info(fName+"message is null");
                return false;
            }
            return  set(message);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean set( Message message){
        String fName="[set]";
        try {
            logger.info(fName+".message.id="+message.getId());
            this.message=message;
            jda=message.getJDA();
            channelType=message.getChannelType();
            switch (channelType){
                case TEXT:textChannel=message.getTextChannel();
                    guild=message.getGuild();
                    set(getHttpResponse(textChannel.getIdLong(),message.getIdLong()));
                    break;
                case PRIVATE:privateChannel=message.getPrivateChannel();
                    set(getHttpResponse(privateChannel.getIdLong(),message.getIdLong()));
                    break;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    Guild guild;
    ChannelType channelType;TextChannel textChannel; PrivateChannel privateChannel;
    Message message;
    private void clear(){
        String fName="clear";
        try {
            guild=null;
            channelType=null;textChannel=null; privateChannel=null;
            message=null;
            components=new JSONArray();
            logger.info(fName+".cleared");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private JSONObject getHttpResponse( long channel,long message){
        String fName="getHttpResponse";
        try {
            logger.info(fName+".channel="+channel+", message="+message);
            String url = liGetChannelMessageUrl;
            url=url.replaceAll("!CHANNEL", String.valueOf(channel)).replaceAll("!MESSAGE",String.valueOf(message));
            logger.info(fName+".url="+url); 
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
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
    JSONArray components=new JSONArray();
    private boolean set( JSONObject jsonObject){
        String fName="set";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            if(jsonObject.has(llCommonKeys.lsMessageJsonKeys.keyComponents)){
                components=jsonObject.getJSONArray(llCommonKeys.lsMessageJsonKeys.keyComponents);
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONArray getComponentsAsJson(){
        String fName="getComponentsAsJson";
        try {
            return components;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public List<lcMessageComponent> getComponents(){
        String fName="[getComponents]";
        try {
            logger.info(fName+".array="+components.toString());
            List<lcMessageComponent>list=new ArrayList<>();
            if(message!=null){
                for(int i=0;i<components.length();i++){
                    lcMessageComponent component=new lcMessageComponent(components.getJSONObject(i),message);
                    list.add(component);
                }
            }else{
                for(int i=0;i<components.length();i++){
                    lcMessageComponent component=new lcMessageComponent(components.getJSONObject(i));
                    list.add(component);
                }
            }

            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
}
