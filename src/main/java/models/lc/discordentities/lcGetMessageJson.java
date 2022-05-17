package models.lc.discordentities;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Message;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetChannelMessageReactionUrl;
import static models.ls.lsDiscordApi.liGetChannelMessageUrl;

public class lcGetMessageJson implements liCommonFunctions{
    //https://discord.com/developers/docs/resources/channel#message-object
    Logger logger = Logger.getLogger(getClass());

    protected lcGetMessageJson(){
    }
    public lcGetMessageJson(JSONObject jsonObject){
        set(jsonObject);
    }
    public lcGetMessageJson(Message message){
        set(message);
    }
    public lcGetMessageJson(long channel_id, long message_id){
        set(channel_id,message_id);
    }
    protected JSONObject jsonMessage=new JSONObject();
    private lcGetMessageJson clear() {
        String fName="[clear]";
        jsonMessage=new JSONObject();
        logger.info(fName + ".cleared");
        return this;
    }
    public JSONObject getJson() {
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject(jsonMessage.toString());
            logger.info(fName + ".value="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetMessageJson set(JSONObject jsonObject) {
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
    private lcGetMessageJson set(Message message) {
        String fName="[setMessage]";
        try {
            clear();
            JSONObject jsonObject=getHttpBody(message.getChannel().getIdLong(),message.getIdLong());
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
    private lcGetMessageJson set(long channel_id, long message_id) {
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
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected JSONObject getHttpBody( long channel,long message){
        String fName="[getHttpBody]";
        try {
            HttpResponse<JsonNode> jsonResponse=getHttp(channel,message);
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
    protected HttpResponse<JsonNode> getHttp( long channel,long message){
        String fName="[getHttp]";
        try {
            logger.info(fName+".channel="+channel+", message="+message);
            String url = liGetChannelMessageUrl;
            url=url.replaceAll("!CHANNEL", String.valueOf(channel)).replaceAll("!MESSAGE", String.valueOf(message));
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

    public String getId() {
        String fName="[getId]";
        try {
            return getString(jsonMessage, llCommonKeys.MessageStructureGet.keyId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getChannelId() {
        String fName="[getChannelId]";
        try {
            return getString(jsonMessage, llCommonKeys.MessageStructureGet.keyChannelId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getIdAsLong() {
        String fName="[getIdAsLong]";
        try {
            return  Long.parseLong(getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getChannelIdAsLong() {
        String fName="[getChannelIdAsLong]";
        try {
           return  Long.parseLong(getChannelId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getTimestamp() {
        String fName="[getTimestamp]";
        try {
            return getString(jsonMessage, llCommonKeys.MessageStructureGet.keyTimestamp);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getEditedTimestamp() {
        String fName="[getEditedTimestamp]";
        try {
            logger.info(fName);
            return getString(jsonMessage, llCommonKeys.MessageStructureGet.keyEditedTimestamp);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getContent() {
        String fName="[getContent]";
        try {
            logger.info(fName);
            return getString(jsonMessage, llCommonKeys.MessageStructureGet.keyContent);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getType() {
        String fName="[getType]";
        try {
            logger.info(fName);
            return getInt(jsonMessage, llCommonKeys.MessageStructureGet.keyType);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public int getFlags() {
        String fName="[getFlags]";
        try {
            logger.info(fName);
            return getInt(jsonMessage, llCommonKeys.MessageStructureGet.keyFlags);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public JSONObject getAuthorJson() {
        String fName="[getAuthorJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyAuthor);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getAuthorId() {
        String fName="[getAuthorId]";
        try {
            logger.info(fName);
            JSONObject author=getAuthorJson();
            String key= llCommonKeys.keyId;
            if(!author.has(key)){
                logger.warn(fName + ".has no "+key);
                return  null;
            }
            return author.getString(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getAuthorIdAsLong() {
        String fName="[getAuthorIdAsLong]";
        try {
            logger.info(fName);
            return  Long.parseLong(getAuthorId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

    public boolean getPinned() {
        String fName="[getPinned]";
        try {
            logger.info(fName);
           return  getBoolean(jsonMessage, llCommonKeys.MessageStructureGet.keyPinned);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getMentionEveryone() {
        String fName="[getMentionEveryone]";
        try {
            logger.info(fName);
            return  getBoolean(jsonMessage, llCommonKeys.MessageStructureGet.keyMentionEveryone);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getTts() {
        String fName="[getTts]";
        try {
            logger.info(fName);
            return  getBoolean(jsonMessage, llCommonKeys.MessageStructureGet.keyTts);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


    public JSONArray getAttachmentsJson() {
        String fName="[getAttachmentsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyAttachments);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getEmbedsJson() {
        String fName="[getEmbedsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyEmbeds);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getMentionsJson() {
        String fName="[getMentionsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyMentions);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getMentionRolesJson() {
        String fName="[getMentionRolesJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyMentionRoles);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getComponentsJson() {
        String fName="[getComponentsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyComponents);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getMentionChannelsJson() {
        String fName="[getMentionRChannelsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyMentionChannels);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getReactionsJson() {
        String fName="[getReactionsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyReactions);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getStickersJson() {
        String fName="[getStickersJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyStickers);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getStickerItemsJson() {
        String fName="[geStickerItemsJson]";
        try {
            logger.info(fName);
            return  getArrayJson(jsonMessage, llCommonKeys.MessageStructureGet.keyStickerItems);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }

    public boolean isAttachmentsEmpty() {
        String fName="[isAttachmentsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getAttachmentsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isEmbedsEmpty() {
        String fName="[isEmbedsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getEmbedsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isMentionsEmpty() {
        String fName="[isMentionsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getMentionsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isMentionRolesEmpty() {
        String fName="[isMentionRolesEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getMentionRolesJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isComponentsJson() {
        String fName="[isComponentsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getComponentsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isMentionChannelsEmpty() {
        String fName="[isMentionChannelsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getMentionChannelsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isReactionsEmpty() {
        String fName="[isReactionsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getReactionsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isStickersEmpty() {
        String fName="[isStickersEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getStickersJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isStickerItemsEmpty() {
        String fName="[isStickerItemsEmpty]";
        try {
            logger.info(fName);
            return  isArrayEmpty(getStickerItemsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }


    public int getAttachmentsLength() {
        String fName="[getAttachmentsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getAttachmentsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getEmbedsLength() {
        String fName="[getEmbedsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getEmbedsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getMentionsLength() {
        String fName="[getMentionsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getMentionsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getMentionRolesLength() {
        String fName="[getMentionRolesLength]";
        try {
            logger.info(fName);
            return  arrayLength(getMentionRolesJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getComponentsLength() {
        String fName="[getComponentsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getComponentsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getMentionChannelsLength() {
        String fName="[getMentionChannelsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getMentionChannelsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getReactionsLength() {
        String fName="[getReactionsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getReactionsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getStickersLength() {
        String fName="[getStickersLength]";
        try {
            logger.info(fName);
            return  arrayLength(getStickersJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getStickerItemsLength() {
        String fName="[getStickerItemsLength]";
        try {
            logger.info(fName);
            return  arrayLength(getStickerItemsJson());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }



    public String getGuildId_() {
        String fName="[getGuildId_]";
        try {
            logger.info(fName);
            return  getString(jsonMessage, llCommonKeys.MessageStructureGet.keyGuildId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getWebhookId() {
        String fName="[getWebhookId]";
        try {
            logger.info(fName);
            return  getString(jsonMessage, llCommonKeys.MessageStructureGet.keyWebhookId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getApplicationId() {
        String fName="[getApplicationId]";
        try {
            logger.info(fName);
            return  getString(jsonMessage, llCommonKeys.MessageStructureGet.keyApplicationId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getGuildIdAsLong_() {
        String fName="[getGuildIdAsLong_]";
        try {
            logger.info(fName);
            return  Long.parseLong(getGuildId_());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getWebhookIdAsLong() {
        String fName="[getWebhookIdAsLong]";
        try {
            logger.info(fName);
            return  Long.parseLong(getWebhookId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getApplicationIdAsLong() {
        String fName="[getApplicationIdAsLong]";
        try {
            logger.info(fName);
            return  Long.parseLong(getApplicationId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONObject getMemberJson_() {
        String fName="[getMemberJson_]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyMember);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getActivityJson() {
        String fName="[geActivityJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyActivity);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getApplicationJson() {
        String fName="[getApplicationJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyApplication);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getMessageReferenceJson() {
        String fName="[getMessageReferenceJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyMessageReference);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getReferencedMessageJson() {
        String fName="[getReferencedMessageJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyReferencedMessage);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getInteractionJson() {
        String fName="[getInteractionJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyInteraction);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getThreadJson() {
        String fName="[getThreadJson]";
        try {
            logger.info(fName);
            return  getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyThread);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }

    public incUser getAuthor() {
        String fName="[getAuthor]";
        try {
            logger.info(fName);
            JSONObject author=getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyAuthor);
            return new incUser(author);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new incUser();
        }
    }
    public incInteraction getInteraction() {
        String fName="[getInteraction]";
        try {
            logger.info(fName);
            JSONObject interaction=getObjectJson(jsonMessage, llCommonKeys.MessageStructureGet.keyInteraction);
            return new incInteraction(interaction);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new incInteraction();
        }
    }
    public List<incReaction> getReactions() {
        String fName="[getReactions]";
        try {
            logger.info(fName);
            List<incReaction>list=new ArrayList<>();
            JSONArray array=getReactionsJson();
            for(int i=0;i<array.length();i++){
                list.add(new incReaction(array.getJSONObject(i)));
            }
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<incAttachment> getAttachments() {
        String fName="[getAttachments]";
        try {
            logger.info(fName);
            List<incAttachment>list=new ArrayList<>();
            JSONArray array=getAttachmentsJson();
            for(int i=0;i<array.length();i++){
                list.add(new incAttachment(array.getJSONObject(i)));
            }
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }

    public class incInteraction {
        protected incInteraction(){

        }
        public incInteraction(JSONObject jsonObject){
            set(jsonObject);
        }
        private JSONObject jsonInteraction=new JSONObject();

        public incInteraction clear() {
            String fName="[Interaction.clear]";
            try {
                jsonInteraction=new JSONObject();
                logger.info(fName+"cleared");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public incInteraction set(JSONObject jsonObject) {
            String fName="[Interaction.setJson]";
            try {
                logger.info(fName);
                clear();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.error(fName + ".json is null or empty");
                    return  null;
                }
                logger.info(fName + ".value="+jsonObject.toString());
                jsonInteraction=new JSONObject(jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName="[Interaction.getJson]";
            try {
                logger.info(fName);
                JSONObject jsonObject=new JSONObject(jsonInteraction.toString());
                logger.info(fName + ".value="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName="[Interaction.getId]";
            try {
                logger.info(fName);
                return getString(jsonInteraction,llCommonKeys.lsGetMessageInteractionObjectJsonKeys.keyId);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName() {
            String fName="[Interaction.getName]";
            try {
                logger.info(fName);
                return getString(jsonInteraction,llCommonKeys.lsGetMessageInteractionObjectJsonKeys.keyName);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong() {
            String fName="[Interaction.getIdAsLong]";
            try {
                logger.info(fName);
                return  Long.parseLong(getId());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getType() {
            String fName="[Interaction.getType]";
            try {
                logger.info(fName);
                return getInt(jsonInteraction,llCommonKeys.lsGetMessageInteractionObjectJsonKeys.keyType);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public JSONObject getUserJson() {
            String fName="[Interaction.getUserJson]";
            try {
                logger.info(fName);
                return getObjectJson(jsonInteraction,llCommonKeys.lsGetMessageInteractionObjectJsonKeys.keyUser);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public incUser getUser() {
            String fName="[Interaction.getUser]";
            try {
                logger.info(fName);
                return new incUser(getUserJson());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new incUser();
            }
        }
    }
    public class incUser {
        protected incUser(){

        }
        public incUser(JSONObject jsonObject){
            set(jsonObject);
        }
        private JSONObject jsonUser =new JSONObject();

        public incUser clear() {
            String fName="[User.clear]";
            try {
                jsonUser =new JSONObject();
                logger.info(fName+"cleared");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public incUser set(JSONObject jsonObject) {
            String fName="[User.setJson]";
            try {
                logger.info(fName);
                clear();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.error(fName + ".json is null or empty");
                    return  null;
                }
                logger.info(fName + ".value="+jsonObject.toString());
                jsonUser =new JSONObject(jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName="[User.getJson]";
            try {
                logger.info(fName);
                JSONObject jsonObject=new JSONObject(jsonUser.toString());
                logger.info(fName + ".value="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName="[User.getId]";
            try {
                logger.info(fName);
                return getString(jsonUser, llCommonKeys.keyId);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName() {
            String fName="[User.getName]";
            try {
                logger.info(fName);
                return getString(jsonUser, llCommonKeys.UserStructure.keyUsername);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getAvatar() {
            String fName="[User.getAvatar]";
            try {
                logger.info(fName);
                return getString(jsonUser, llCommonKeys.UserStructure.keyAvatar);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong() {
            String fName="[User.getIdAsLong]";
            try {
                logger.info(fName);
                return  Long.parseLong(getId());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getDiscriminator() {
            String fName="[User.getDiscriminator]";
            try {
                logger.info(fName);
                return getInt(jsonUser, llCommonKeys.UserStructure.keyDiscriminator);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getPublicFlags() {
            String fName="[User.getPublicFlags]";
            try {
                logger.info(fName);
                return getInt(jsonUser, llCommonKeys.UserStructure.keyPublicFlags);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
    }
    public class incReaction {
        protected incReaction(){

        }
        public incReaction(JSONObject jsonObject){
            set(jsonObject);

        }
        public incReaction(JSONObject jsonObject,long channel_id,long message_id){
            set(jsonObject,channel_id,message_id);
        }
        private JSONObject jsonReaction =new JSONObject();
        private JSONArray jsonUsers=new JSONArray();
        public incReaction clear() {
            String fName="[Reaction.clear]";
            try {
                jsonReaction =new JSONObject();
                jsonUsers=new JSONArray();
                logger.info(fName+"cleared");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public incReaction set(JSONObject jsonObject) {
            String fName="[Reaction.setJson]";
            try {
                logger.info(fName);
                clear();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.error(fName + ".json is null or empty");
                    return  null;
                }
                logger.info(fName + ".value="+jsonObject.toString());
                jsonReaction =new JSONObject(jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public incReaction set(JSONObject jsonObject,long channel_id,long message_id) {
            String fName="[Reaction.setJson]";
            try {
                logger.info(fName);
                clear();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.error(fName + ".json is null or empty");
                    return  null;
                }
                logger.info(fName + ".value="+jsonObject.toString());
                jsonReaction =new JSONObject(jsonObject.toString());
                JSONArray jsonArray=getHttpBody(channel_id,message_id);
                if(jsonArray!=null&&!jsonArray.isEmpty()){
                    jsonUsers=jsonArray;
                }
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName="[Reaction.getJson]";
            try {
                logger.info(fName);
                JSONObject jsonObject=new JSONObject(jsonReaction.toString());
                logger.info(fName + ".value="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        /*
        "reactions": [
            {
                "emoji": {
                    "id": "817034229812559912",
                    "name": "mToy"
                },
                "count": 1,
                "me": false
            }
        ]
        */
        public JSONObject getEmoji() {
            String fName="[Reaction.getEmoji]";
            try {
                logger.info(fName);
                return getObjectJson(jsonReaction,"emoji");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public String getId() {
            String fName="[Reaction.getId]";
            try {
                logger.info(fName);
                return getString(getEmoji(),"id");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName() {
            String fName="[Reaction.getName]";
            try {
                logger.info(fName);
                return getString(getEmoji(),"name");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong() {
            String fName="[Reaction.getIdAsLong]";
            try {
                logger.info(fName);
                return  Long.parseLong(getId());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getCount() {
            String fName="[Reaction.getCount]";
            try {
                logger.info(fName);
                return getInt(jsonReaction,"count");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        //https://discord.com/developers/docs/resources/channel#get-reactions
        private JSONArray getHttpBody( long channel,long message){
            String fName="[Reaction.getHttpBody]";
            try {
                HttpResponse<JsonNode> jsonResponse=getHttp(channel,message);
                logger.info(fName+".status ="+jsonResponse.getStatus());
                JSONArray body=new JSONArray();
                try {
                    body=jsonResponse.getBody().getArray();
                    logger.info(fName+".body ="+body.toString());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(jsonResponse.getStatus()>299||jsonResponse.getStatus()<200){
                    logger.warn(fName+".invalid status");
                    logger.error(fName+".body ="+body.toString());
                    return new JSONArray();
                }else{
                    return body;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
            }
        }
        private HttpResponse<JsonNode> getHttp( long channel,long message){
            String fName="[Reaction.getHttp]";
            try {
                logger.info(fName+".channel="+channel+", message="+message);
                String url = liGetChannelMessageReactionUrl;
                url=url.replaceAll("!CHANNEL", String.valueOf(channel)).replaceAll("!MESSAGE", String.valueOf(message)).replaceAll("!EMOJI", getName()+":"+getId());
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
        public JSONArray getUsersJson() {
            String fName="[Reaction.getUsersJson]";
            try {
                return jsonUsers;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONArray();
            }
        }
        public List<incUser> getUsers() {
            String fName="[Reaction.getUsers]";
            try {
                List<incUser>list=new ArrayList<>();
                JSONArray array=getUsersJson();
                for(int i=0;i<array.length();i++){
                    list.add(new incUser(array.getJSONObject(i)));
                }
                return list;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new ArrayList<>();
            }
        }
    }
    public class incAttachment {
        public incAttachment(){

        }
        public incAttachment(JSONObject jsonObject){
            set(jsonObject);
        }
        private JSONObject jsonAttachment =new JSONObject();

        public incAttachment clear() {
            String fName="[Attachment.clear]";
            try {
                jsonAttachment =new JSONObject();
                logger.info(fName+"cleared");
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public incAttachment set(JSONObject jsonObject) {
            String fName="[Attachment.setJson]";
            try {
                clear();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.error(fName + ".json is null or empty");
                    return  null;
                }
                logger.info(fName + ".value="+jsonObject.toString());
                jsonAttachment =new JSONObject(jsonObject.toString());
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName="[Attachment.getJson]";
            try {
                JSONObject jsonObject=new JSONObject(jsonAttachment.toString());
                logger.info(fName + ".value="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName="[Attachment.getId]";
            try {
                return getString(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyId);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getFileName() {
            String fName="[Attachment.getFIleName]";
            try {
                return getString(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyFilename);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getContentType() {
            String fName="[Attachment.getContentType]";
            try {
                return getString(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyContentType);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getUrl() {
            String fName="[Attachment.getUrl]";
            try {
                return getString(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyUrl);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getProxyUrl() {
            String fName="[Attachment.getProxyUrl]";
            try {
                return getString(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyProxyUrl);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong() {
            String fName="[Attachment.getIdAsLong]";
            try {
                return  Long.parseLong(getId());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getSize() {
            String fName="[Attachment.getSize]";
            try {
                return getInt(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keySize);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getHeight() {
            String fName="[Attachment.getHeight]";
            try {
                return getInt(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyHeight);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getWidth() {
            String fName="[Attachment.getWidth]";
            try {
                return getInt(jsonAttachment,llCommonKeys.lsAttachmentObjectJsonKeys.keyWidth);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
    }
}
