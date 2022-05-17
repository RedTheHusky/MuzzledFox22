package models.lc.sticker;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetChannelMessageUrl;
import static models.ls.lsDiscordApi.liGetStickerPacks;

public class lcMessageSticker {
    Logger logger = Logger.getLogger(getClass());
    //src="https://media.discordapp.net/stickers/785645938890637352/4102d5e48a644afa9d15ef09963ac18d.png?size=256&passthrough=false"

    private JSONObject jsonPayload = new JSONObject();
    private JDA jda;
    private String id="",channel_id="";
    private Message message=null;
    private TextChannel textChannel=null;
    private  PrivateChannel privateChannel=null;
    private  boolean isMessage4Guild=false;
    public lcMessageSticker(RawGatewayEvent event) {
        String fName = "build";
        try {
            jda = event.getJDA();
            if (!event.getType().equalsIgnoreCase("MESSAGE_CREATE")) {
                logger.warn(fName + ".invaliod type");
                return;
            }
            String strPayload = event.getPayload().toString();
            jsonPayload = new JSONObject(strPayload);
            logger.info(fName+"jsonPayload="+jsonPayload.toString());
            setMessage(jsonPayload);
            setChannel();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected boolean setMessage(JSONObject jsonObject) {
        String fName = "[setMessage]";
        try {
            logger.info(fName+"jsonObject="+jsonObject.toString());
            String key="";
            key= llCommonKeys.MessageStructureGet.keyId; if(jsonObject.has(key))id=jsonObject.optString(key);
            key= llCommonKeys.MessageStructureGet.keyChannelId; if(jsonObject.has(key))channel_id=jsonObject.optString(key);
            key=llCommonKeys.MessageStructureGet.keyStickerItems; if(jsonObject.has(key))setStickers(jsonObject.optJSONArray(key));
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected boolean setChannel() {
        String fName = "[setChannel]";
        try {
            logger.info(fName+"channel_id="+channel_id);
            try {
                textChannel=jda.getTextChannelById(channel_id);
                if(textChannel!=null){
                    logger.info(fName+"found as textchannel");
                    return true;
                }

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                privateChannel=jda.getPrivateChannelById(channel_id);
                if(privateChannel!=null){
                    logger.info(fName+"found as privatechannel");
                    return true;
                }

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName+"not found as textchannel or privatechannel");
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcMessageSticker(Message message) {
        String fName = "build";
        try {
           jda=message.getJDA();
           id=message.getId();
           channel_id=message.getChannel().getId();
           if(message.isFromGuild()){
               isMessage4Guild=true;
               textChannel=message.getTextChannel();
           }else{
               privateChannel=message.getPrivateChannel();
           }
           getHttps();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private List<lcSticker> stickers=new ArrayList<>();
    private boolean getHttps() {
        String fName = "[getHttps]";
        try {
            logger.info(fName+"channelId="+channel_id+", id="+id);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetChannelMessageUrl;
            url=url.replaceAll("!CHANNEL",channel_id).replaceAll("!MESSAGE",id);
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            if(response.getStatus()<200||response.getStatus()>299){
                logger.warn(fName+"response.statustext="+response.getStatusText());
                logger.warn(fName+"response.body="+response.getBody().getObject());
                return false;
            }
            jsonPayload=response.getBody().getObject();
            String key="";
            key=llCommonKeys.MessageStructureGet.keyStickerItems; if(jsonPayload.has(key))setStickers(jsonPayload.optJSONArray(key));
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setStickers(JSONArray jsonArray) {
        String fName = "[setStickers]";
        try {
            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                lcSticker sticker=new lcSticker(jsonArray.optJSONObject(i).getString(llCommonKeys.StickerStructure.Id),jda);
                stickers.add(sticker);
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getMessageId() {
        String fName = "[getMessageId]";
        try {
            return id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getMessageIdAsLong() {
        String fName = "[getMessageId]";
        try {
            return Long.parseLong(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public Message getMessage() {
        String fName = "[getMessage]";
        try {
            return message;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getChannelId() {
        String fName = "[getChannelId]";
        try {
            return channel_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getChannelIdAsLong() {
        String fName = "[getChannelId=";
        try {
            return Long.parseLong(channel_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public boolean isFromGuild() {
        String fName = "[isFromGuild]";
        try {
            return isMessage4Guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public TextChannel getTextChannel() {
        String fName = "[getTextChannel]";
        try {
            return textChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PrivateChannel getPrivateChannel() {
        String fName = "[getPrivateChannel]";
        try {
            return privateChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcSticker> getStickers() {
        String fName = "[getStickers]";
        try {
            logger.info(fName+"all");
            return stickers;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcSticker getSticker(int index) {
        String fName = "[getSticker]";
        try {
            logger.info(fName+"index="+index);
            return stickers.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
