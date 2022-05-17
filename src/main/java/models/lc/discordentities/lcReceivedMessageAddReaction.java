package models.lc.discordentities;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetGuildEmojisUrl;

public class lcReceivedMessageAddReaction {
    //https://discord.com/developers/docs/resources/emoji#emoji-object
    Logger logger = Logger.getLogger(getClass());
    protected lcReceivedMessageAddReaction(){

    }
    public lcReceivedMessageAddReaction(RawGatewayEvent event){
        setByRawGateway(event);
    }
    private lcReceivedMessageAddReaction clear() {
        String fName="[clear]";

        logger.info(fName + ".cleared");
        return this;
    }
    public JSONObject getJson() {
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject();
            logger.info(fName + ".value="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    JDA jda;
    private lcReceivedMessageAddReaction setByRawGateway(RawGatewayEvent event) {
        String fName="[setByRawGateway]";
        try {
            jda=event.getJDA();
            setByPayload(new JSONObject(event.getPayload().toString()));
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  interface  KEYS{
        String keyEmoji=llCommonKeys.keyEmoji,
        keyName=llCommonKeys.keyName,
        keyId=llCommonKeys.keyId,
        keyUserId=llCommonKeys.keyUser_id,
        keyMessageId=llCommonKeys.keyMessage_id,
        keyChannelId=llCommonKeys.keyChannel_id;
    }
    String  emojiName="",emojiId="";
    String user_id="",message_id="",channel_id="";
    private lcReceivedMessageAddReaction setByPayload(JSONObject jsonObject) {
        String fName="[setByPayload]";
        try {
            logger.info(fName+".Payload="+jsonObject.toString());
            /*example
            {
                "emoji": {
                    "name": "✅",
                    "id": null
                },
                "user_id": "249197641181822977",
                "message_id": "860386781021863946",
                "channel_id": "389519587181592576"
            }
             */
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }




}
