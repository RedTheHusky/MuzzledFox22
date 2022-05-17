package models.lc.threads;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcGetMessageJson;
import models.lc.lcTempZipFile;
import models.lc.sticker.lcGuildStickerPack;
import models.ll.llCommonKeys;
import models.ls.lsStreamHelper;
import models.ls.lsStringUsefullFunctions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.*;

public class lcGuildThread implements  llCommonKeys.ChannelStructure{
    Logger logger = Logger.getLogger(getClass());
    protected String id="", guild_id="", owner_id ="",parent_id="",name="";
    protected  int type=0,message_count=0,member_count=0,auto_archive_duration=0;
    String last_message_id="",archive_timestamp="";
    boolean archived=false,locked=false;

    public lcGuildThread() {

    }



    Guild guild=null;Member owner=null;
    TextChannel textChannel=null;Message message=null;
    public lcGuildThread(Message message) {
        String fName = "build";
        try {
            if(!message.isFromGuild()){
                return;
            }
            if(!getThreadHttp(message.getIdLong())){
                return;
            }
            this.guild=message.getGuild();
            this.textChannel=message.getTextChannel();
            this.owner=message.getMember();
            this.message=message;

        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private boolean setThread(JSONObject jsonObject) {
        String fName = "[setThread]";
        try {

            logger.info(fName+"jsonObject="+jsonObject.toString());
            Iterator<String> keys=jsonObject.keys();
            while(keys.hasNext()){
                try {
                    String key=keys.next();
                    switch (key){
                        case llCommonKeys.ChannelStructure.keyId:
                            id=jsonObject.optString(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyGuildId:
                            guild_id=jsonObject.optString(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyParentId:
                            parent_id=jsonObject.optString(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyOwnerId:
                            owner_id=jsonObject.optString(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyType:
                            type=jsonObject.optInt(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyName:
                            name=jsonObject.optString(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyLastMessageId:
                            last_message_id=jsonObject.optString(key);
                            break;
                        case llCommonKeys.ChannelStructure.keyThreadMetadata:
                            JSONObject jsonObject1=jsonObject.optJSONObject(key);
                            Iterator<String> keys2=jsonObject1.keys();
                            while(keys2.hasNext()){
                                try {
                                    String key1=keys.next();
                                    switch (key1) {
                                        case ThreadMetadata.keyArchived:
                                            archived = jsonObject1.optBoolean(key1);
                                            break;
                                        case ThreadMetadata.keyArchiveTimestamp:
                                            archive_timestamp = jsonObject1.optString(key1);
                                            break;
                                        case ThreadMetadata.keyAutoArchiveDuration:
                                            auto_archive_duration = jsonObject1.optInt(key1);
                                            break;
                                        case ThreadMetadata.keyLocked:
                                            locked = jsonObject1.optBoolean(key1);
                                            break;
                                    }
                                } catch (Exception e) {
                                    logger.error(fName + ".exception=" + e);
                                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                }

                            }
                            break;
                        case keyMessageCount:
                            message_count=jsonObject.optInt(key);
                            break;
                        case keyMemberCount:
                            member_count=jsonObject.optInt(key);
                            break;


                    }
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return id!=null&&!id.isBlank();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getThreadHttp(long id) {
        String fName = "[getThreadHttp]";
        try {
            logger.info(fName+"id="+id);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetChannelUrl;
            url=url.replaceAll("!CHANNEL", String.valueOf(id));
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            if(response.getStatus()>299){
                logger.warn("invalid status="+response.getStatus());
                return false;
            }
            JSONObject jsonObject=response.getBody().getObject();
            logger.info(fName+"response.body"+jsonObject.toString());
            return  setThread(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setUser() {
        String fName = "[setUser]";
        try {
            logger.info(fName+"owner_id="+owner_id);
            this.owner=guild.getMemberById(owner_id);
            if(owner!=null){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setTextChannel() {
        String fName = "[setChannel]";
        try {
            logger.info(fName+"parent_id="+parent_id);
            textChannel=guild.getTextChannelById(parent_id);
            if(textChannel!=null){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setMessage() {
        String fName = "[setMessage]";
        try {
            logger.info(fName+"id="+id);
            message=textChannel.retrieveMessageById(id).complete();
            if(message!=null){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public int getType() {
        String fName = "getType";
        try {
            logger.info(fName+"value="+type);
            return type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isInvalid() {
        String fName = "[isInvalid]";
        try {
            return id==null||id.isBlank();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public String getName() {
        String fName = "getName";
        try {
            logger.info(fName+"value="+name);
            if(name==null)return "";
            return  name;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    /*public boolean getAvailable() {
        String fName = "[getAvailable]";
        try {
            logger.info(fName+"value="+available);
            return available;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }*/

    public String getId() {
        String fName = "getId";
        try {
            logger.info(fName+"value="+id);
            if(id==null)return "";
            return id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getIdAsLong() {
        String fName = "getIdAsLong";
        try {
            logger.info(fName+"value="+id);
            return lsStringUsefullFunctions.String2Long(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            logger.info(fName+"value="+guild.getId());
            return guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getGuildId() {
        String fName = "getGuildId";
        try {
            logger.info(fName+"value="+guild_id);
            if(guild_id==null)return "";
            return guild_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getGuildIdAsLong() {
        String fName = "getGuildIdAsLong";
        try {
            logger.info(fName+"value="+guild_id);
            return lsStringUsefullFunctions.String2Long(guild_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Member getAuthor() {
        String fName = "[getAuthor]";
        try {
            logger.info(fName+"value="+owner.getId());
            return owner;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getAuthorId() {
        String fName = "[getAuthorId]";
        try {
            logger.info(fName+"value="+ owner_id);
            if(owner_id ==null)return "";
            return owner_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getAuthorIdAsLong() {
        String fName = "getAuthorIdAsLong";
        try {
            logger.info(fName+"value="+ owner_id);
            return lsStringUsefullFunctions.String2Long(owner_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public TextChannel getChannel() {
        String fName = "[getChannel]";
        try {
            logger.info(fName+"value="+textChannel.getId());
            return textChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public TextChannel getParent() {
        String fName = "[getParent]";
        try {
            logger.info(fName+"value="+textChannel.getId());
            return textChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getChannelId() {
        String fName = "[getChannelId]";
        try {
            logger.info(fName+"value="+ parent_id);
            if(parent_id ==null)return "";
            return parent_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getChannelIdAsLong() {
        String fName = "getChannelIdAsLong";
        try {
            logger.info(fName+"value="+ parent_id);
            return lsStringUsefullFunctions.String2Long(parent_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Message getMessage() {
        String fName = "[getMessage]";
        try {
            logger.info(fName+"value="+message.getId());
            return message;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getMessageId() {
        String fName = "[getMessageId]";
        try {
            logger.info(fName+"value="+ id);
            if(id ==null)return "";
            return id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getMessageIdAsLong() {
        String fName = "getMessageIdAsLong";
        try {
            logger.info(fName+"value="+ id);
            return lsStringUsefullFunctions.String2Long(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getLastMessageId() {
        String fName = "[getLastMessageId]";
        try {
            logger.info(fName+"value="+ id);
            if(id ==null)return "";
            return id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getLastMessageIdAsLong() {
        String fName = "[getLastMessageIdAsLong]";
        try {
            logger.info(fName+"value="+ last_message_id);
            return lsStringUsefullFunctions.String2Long(last_message_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

    public JSONObject getJSON() {
        String fName = "[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyId,getId());
            jsonObject.put(keyName,name);
            jsonObject.put(keyParentId,getChannelId());
            jsonObject.put(keyGuildId,getGuildId());
            jsonObject.put(keyType,getType());
            jsonObject.put(keyOwnerId,getAuthorId());
            jsonObject.put(keyLastMessageId,getLastMessageId());
            jsonObject.put(keyMemberCount,message_count);
            JSONObject jsonObject1=new JSONObject();
            jsonObject.put(keyThreadMetadata,jsonObject1);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return  jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }


}
