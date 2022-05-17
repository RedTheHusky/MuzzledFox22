package models.lc.rawgate;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.sticker.lcStandardSticker;

import models.lc.sticker.lcSticker;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.ls.lsDiscordApi.liGetChannelMessageUrl;
import static models.llGlobalHelper.llBotToken;

public class lcGetChannelMessage {
    Logger logger = Logger.getLogger(getClass());
    public JSONObject jsonPayload=new JSONObject();
    boolean status=false;
    String failedMessage="";int code=0;
    protected JDA jda;
    public lcGetChannelMessage(PrivateChannel channel, String messageID) {
        String fName = "build";
        try {
            jda=channel.getJDA();
            set(channel.getId(),messageID);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGetChannelMessage(TextChannel channel, String messageID) {
        String fName = "build";
        try {
            jda=channel.getJDA();
            set(channel.getId(),messageID);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public boolean set(String channelId,String messageID) {
        String fName = "[set]";
        try {
            jsonPayload=new JSONObject();
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetChannelMessageUrl.replaceAll("!CHANNEL",channelId).replaceAll("!MESSAGE",messageID);
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            if(response.getStatus()<200||response.getStatus()>299){
                logger.warn(fName+"invalid status");
                status=false;
                JSONObject body=response.getBody().getObject();
                logger.warn(fName+"body="+body.toString());
                code=body.getInt("code");
                failedMessage=body.getString("message");
                return false;
            }
            status=true;
            jsonPayload=response.getBody().getObject();
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public List<lcSticker> getStickers() {
        String fName = "[getStickers]";
        try {
            String keyStickers="stickers";
            if(!jsonPayload.has(keyStickers)){
                logger.info(fName+"no such key");
                return new ArrayList<>();
            }
            if(jsonPayload.isNull(keyStickers)){
                logger.info(fName+"is null");
                return new ArrayList<>();
            }
            List<lcSticker>stickers=new ArrayList<>();
            JSONArray jsonArray=jsonPayload.optJSONArray(keyStickers);
            for(int i=0;i<jsonArray.length();i++){
                stickers.add(new lcSticker(jsonArray.getJSONObject(i).getString(llCommonKeys.StickerStructure.Id),jda));
            }
            return stickers;

        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }

}
