package models.lc.discordentities;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Emote;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetGuildEmojisUrl;

public class lcGetGuildEmojiJson implements  liCommonFunctions{
    //https://discord.com/developers/docs/resources/emoji#emoji-object
    Logger logger = Logger.getLogger(getClass());
    protected lcGetGuildEmojiJson(){

    }
    public lcGetGuildEmojiJson(JSONObject jsonObject){
        set(jsonObject);
    }
    public lcGetGuildEmojiJson(Emote emoji){
        set(emoji);
    }
    public lcGetGuildEmojiJson(long channel_id, long message_id){
        set(channel_id,message_id);
    }
    JSONObject jsonEmoji =new JSONObject();
    long guildId=0;
    private lcGetGuildEmojiJson clear() {
        String fName="[clear]";
        jsonEmoji =new JSONObject();
        guildId=0;
        logger.info(fName + ".cleared");
        return this;
    }
    public JSONObject getJson() {
        String fName="[getJson]";
        try {
            JSONObject jsonObject=new JSONObject(jsonEmoji.toString());
            logger.info(fName + ".value="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetGuildEmojiJson set(JSONObject jsonObject) {
        String fName="[setJson]";
        try {
            clear();
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji =new JSONObject(jsonObject.toString());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetGuildEmojiJson set(Emote emoji) {
        String fName="[setEmote]";
        try {
            clear();
            JSONObject jsonObject=getHttpBody(emoji.getGuild().getIdLong(),emoji.getIdLong());
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji =new JSONObject(jsonObject.toString());
            guildId=emoji.getGuild().getIdLong();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private lcGetGuildEmojiJson set(long guild_id, long emoji_id) {
        String fName="[setEmote]";
        try {
            clear();
            logger.info(fName + ".guild_id="+guild_id+", emoji_id="+emoji_id);
            JSONObject jsonObject=getHttpBody(guild_id,emoji_id);
            if(jsonObject==null||jsonObject.isEmpty()){
                logger.error(fName + ".json is null or empty");
                return  null;
            }
            logger.info(fName + ".value="+jsonObject.toString());
            jsonEmoji =new JSONObject(jsonObject.toString());
            guildId=guild_id;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected JSONObject getHttpBody( long guild,long emoji){
        String fName="[getHttpBody]";
        try {
            HttpResponse<JsonNode> jsonResponse=getHttp(guild,emoji);
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
    protected HttpResponse<JsonNode> getHttp( long guild,long emoji){
        String fName="[getHttp]";
        try {
            logger.info(fName+".guild="+guild+", emoji="+emoji);
            String url = liGetGuildEmojisUrl;
            url=url.replaceAll("!GUILD", String.valueOf(guild)).replaceAll("!EMOJI", String.valueOf(emoji));
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
            return getString(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyId);
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
    public JSONObject getAuthorJson() {
        String fName="[getAuthorJson]";
        logger.info(fName);
        return getObjectJson(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyUser);
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

    public long getGuildIdAsLong() {
        String fName="[getGuildIdAsLong]";
        logger.info(fName);
        return  guildId;
    }

    public boolean getManaged() {
        String fName="[getManaged]";
        logger.info(fName);
        return getBoolean(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyManaged);
    }
    public boolean getAnimated() {
        String fName="[getAnimated]";
        logger.info(fName);
        return getBoolean(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyAnimated);
    }
    public boolean getAvailable() {
        String fName="[getAvailable]";
        logger.info(fName);
        return getBoolean(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyAvailable);
    }
    public boolean getRequireColons() {
        String fName="[getRequireColons]";
        logger.info(fName);
        return getBoolean(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyRequireColons);
    }

    public JSONArray getRolesJson() {
        String fName="[getRolesJson]";
        logger.info(fName);
        return  getArrayJson(jsonEmoji,llCommonKeys.lsGetEmojiObjectJsonKeys.keyRoles);
    }

    public boolean isRolesEmpty() {
        String fName="[isRolesEmpty]";
        logger.info(fName);
        return  isArrayEmpty(getRolesJson());
    }
    public int getRolesLength() {
        String fName="[getRolesLength]";
        return  arrayLength(getRolesJson());
    }
    public List<String> getRoles() {
        String fName="[getRoles]";
        logger.info(fName);
        return  jsonArray2ListString(getRolesJson());
    }



}
