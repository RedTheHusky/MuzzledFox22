package util.entity;

import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcEmbedBuilder;
import models.lc.json.lcText2Json;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static models.lsGlobalHelper.lsGeneralGuildNotificationPath;

public class GuildNotificationReader {
    Logger logger = Logger.getLogger(getClass());

    public lcJSONGuildProfile gGuildProfile=null;
    lcGlobalHelper gGlobal;
    Member gMember;
    Guild gGuild;
    public boolean flagGet4Cache=true,flagPut2Cache=true;
    public GuildNotificationReader(lcGlobalHelper global){
        String fName="[constructor]";
        try {
           gGlobal=global;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    lcText2Json text2Json=new lcText2Json();
    //String configFilePath="resources/json/guilds/notification.json";
    private boolean readFile(String path){
        String fName="[readFile]";
        try {
            File file1;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+path);
                file1=new File(path);
                if(file1.exists()){
                    logger.info(fName+".file1 exists");
                    fileStream = new FileInputStream(file1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            if(fileStream!=null){
                if(!text2Json.isInputStream2JsonObject(fileStream)){
                    logger.warn(fName+".failed to load");
                    fileStream.close();
                    logger.info(fName+".fileStream.close");
                    return false;
                }else{
                    logger.info(fName+".loaded from file");
                    fileStream.close();
                    logger.info(fName+".fileStream.close");
                }
            }else{
                logger.warn(fName+".no input stream");
            }
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty");return false;
            }
            logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
            return  true;

        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean init(){
        String fName="[init]";
        try {
            return init(lsGeneralGuildNotificationPath);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean init(String path){
        String fName="[init]";
        try {
            text2Json.jsonObject=null;
            if(!readFile(path)){
                logger.error(fName + ".failed to read file");
                return false;
            }
            if(!getProperties()){
                logger.error(fName + ".failed to get properties");
                return false;
            }
            logger.info(fName + ".done");
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProperties(){
        String fName="[getProperties]";
        try {
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty");return false;
            }
            logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
            String key="";JSONObject jsonObject=text2Json.jsonObject;
            key="spam_news";if( jsonObject.has(key)&&!jsonObject.isNull(key)) {
                spamNews.set(text2Json.jsonObject.optJSONObject(key));
            }
            key="trigger_news";if( jsonObject.has(key)&&!jsonObject.isNull(key)) {
                triggerNews.set(text2Json.jsonObject.optJSONObject(key));
            }
            key="general_news";if( jsonObject.has(key)&&!jsonObject.isNull(key)) {
                generalNews.set(text2Json.jsonObject.optJSONObject(key));
            }

            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private _NEWS generalNews =new _NEWS(),spamNews =new _NEWS(),triggerNews =new _NEWS();
    public class _NEWS {
        final String keyName="name",keyEnable="enable",keyAutoPost="auto_post",keyContent="content",keyEmbed="embed",keyTimeout="timeout",keyDeleteOption="delete_option",keyAutoDelete="auto_delete",keyGuilds="guilds", keyBlocked ="blocked", keyAllowed ="allowed";
        protected JSONObject jsonObject=null;
        public _NEWS(){

        }
        public _NEWS(JSONObject jsonObject){
            set(jsonObject);
        }
        public _NEWS set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                logger.info(fName + ".jsonObject=" + jsonObject);
                this.jsonObject=jsonObject;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject get(){
            String fName="[get]";
            try {
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _NEWS print(JSONObject jsonObject){
            String fName="[print]";
            try {
                logger.info(fName + ".jsonObject=" + jsonObject);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName(){
            String fName="[getName]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                String result=jsonObject.getString(keyName);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isEnabled(){
            String fName="[isEnabled]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                if(!jsonObject.has(keyEnable)){
                    throw  new Exception("json key invalid!");
                }
                if(jsonObject.isNull(keyEnable)){
                    throw  new Exception("json key is null!");
                }
                boolean result=jsonObject.getBoolean(keyEnable);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isAutoPost(){
            String fName="[isAutoPost]";
            try {
                String key=keyAutoPost;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return false;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return false;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return false;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return false;
                }
                boolean result=jsonObject.getBoolean(key);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasContent(){
            String fName="[hasContent]";
            try {
                String key=keyContent;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return false;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return false;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return false;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return false;
                }
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getContent(){
            String fName="[getContent]";
            try {
                if(!hasContent()){
                   logger.info(fName+"invalid!");
                    return null;
                }
                String result=jsonObject.getString(keyContent);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getTimeout(){
            String fName="[getTimeout]";
            try {
                String key=keyTimeout;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return 0;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return 0;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return 0;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return 0;
                }
                long result=jsonObject.getLong(key);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        private JSONObject getGuildsJson(){
            String fName="[getGuildsJson]";
            try {
                String key= keyGuilds;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return null;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return null;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return null;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return null;
                }
                JSONObject result=jsonObject.getJSONObject(key);
                logger.info(fName + ".result=" + result.toString());
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean hasBlockedGuilds(){
            String fName="[hasBlockedGuilds]";
            try {
                JSONObject jsonObject= getGuildsJson();
                String key= keyBlocked;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return false;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return false;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return false;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return false;
                }
                if(jsonObject.getJSONObject(key).isEmpty()){
                    logger.info(fName+"json key is empty!");return false;
                }
                logger.info(fName+"has>true");
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getBlockedGuilds(){
            String fName="[getBlockedGuilds]";
            try {
                if(!hasBlockedGuilds()){
                    logger.info(fName+"invalid!");
                    return new JSONObject();
                }
                JSONObject result=jsonObject.getJSONObject(keyGuilds).getJSONObject(keyBlocked);
                logger.info(fName + ".result=" + result.toString());
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isGuildBlocked(Guild guild){
            String fName="[isGuildBlocked_Guild]";
            try {
                if(guild==null){
                    logger.info(fName+"guild is null!");
                    return false;
                }
                logger.info(fName+"guild="+guild.getId());
                return isGuildBlocked(guild.getId());
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isGuildBlocked(long guild_id){
            String fName="[isGuildBlocked_Long]";
            try {
                if(guild_id<=0){
                    logger.info(fName+"invalid guild_id!");
                    return false;
                }
                logger.info(fName+"guild_id="+guild_id);
                return isGuildBlocked(String.valueOf(guild_id));
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isGuildBlocked(String guild_id){
            String fName="[isGuildBlocked_String]";
            try {
                if(guild_id==null||guild_id.isEmpty()){
                    logger.info(fName+"invalid guild_id!");
                    return false;
                }
                logger.info(fName+"guild_id="+guild_id);
                JSONObject jsonObject= getBlockedGuilds();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.info(fName+"invalid jsonObject!");
                    return false;
                }
                if(jsonObject.has(guild_id)){
                    logger.info(fName+"key found>true");
                    return true;
                }
                logger.info(fName+"key not found>false");
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasAllowedGuilds(){
            String fName="[hasAllowedGuilds]";
            try {
                String key= keyAllowed;
                JSONObject jsonObject= getGuildsJson();
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return false;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return false;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return false;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return false;
                }
                if(jsonObject.getJSONObject(key).isEmpty()){
                    logger.info(fName+"json key is empty!");return false;
                }
                logger.info(fName+"has>true");
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getAllowedGuilds(){
            String fName="[getAllowedGuilds]";
            try {
                if(!hasAllowedGuilds()){
                    logger.info(fName+"invalid!");
                    return new JSONObject();
                }
                JSONObject result=jsonObject.getJSONObject(keyGuilds).getJSONObject(keyAllowed);
                logger.info(fName + ".result=" + result.toString());
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isGuildAllowed(Guild guild){
            String fName="[isGuildAllowed_Guild]";
            try {
                if(guild==null){
                    logger.info(fName+"guild is null!");
                    return false;
                }
                logger.info(fName+"guild="+guild.getId());
                return isGuildAllowed(guild.getId());
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isGuildAllowed(long guild_id){
            String fName="[isGuildAllowed_Long]";
            try {
                if(guild_id<=0){
                    logger.info(fName+"invalid guild_id!");
                    return false;
                }
                logger.info(fName+"guild_id="+guild_id);
                return isGuildAllowed(String.valueOf(guild_id));
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isGuildAllowed(String guild_id){
            String fName="[isGuildAllowed_String]";
            try {
                if(guild_id==null||guild_id.isEmpty()){
                    logger.info(fName+"invalid guild_id!");
                    return false;
                }
                logger.info(fName+"guild_id="+guild_id);
                JSONObject jsonObject= getAllowedGuilds();
                if(jsonObject==null||jsonObject.isEmpty()){
                    logger.info(fName+"invalid jsonObject!");
                    return false;
                }
                if(jsonObject.has(guild_id)){
                    logger.info(fName+"key found>true");
                    return true;
                }
                logger.info(fName+"key not found>false");
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasEmbedJson(){
            String fName="[hasEmbedJson]";
            try {
                String key=keyEmbed;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return false;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return false;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return false;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return false;
                }
                if(jsonObject.getJSONObject(key).isEmpty()){
                    logger.info(fName+"json key is empty!");return false;
                }
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getEmbedJson(){
            String fName="[getEmbedJson]";
            try {
                if(!hasEmbedJson()){
                    logger.info(fName+"invalid");
                    return new JSONObject();
                }
                JSONObject result=jsonObject.getJSONObject(keyEmbed);
                logger.info(fName + ".result=" + result.toString());
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public EmbedBuilder getEmbedBuilder(){
            String fName="[getEmbedBuilder]";
            try {
                JSONObject jsonObject=getEmbedJson();
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                lcEmbedBuilder embedBuilder1=new lcEmbedBuilder();
                embedBuilder1.set(jsonObject);
                EmbedBuilder embedBuilder=embedBuilder1.embed();
                return embedBuilder;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public MessageEmbed getEmbedMessage(){
            String fName="[getEmbedMessage]";
            try {
                EmbedBuilder embedBuilder=getEmbedBuilder();
                if(embedBuilder==null){
                    throw  new Exception("embedBuilder is null!");
                }
                MessageEmbed messageEmbed=embedBuilder.build();
                return messageEmbed;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isDeleteOption(){
            String fName="[isDeleteOption]";
            try {
                String key=keyDeleteOption;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return false;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return false;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return false;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return false;
                }
                boolean result=jsonObject.getBoolean(key);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public long getAutoDelete(){
            String fName="[getAutoDelete]";
            try {
                String key=keyAutoDelete;
                if(jsonObject==null){
                    logger.info(fName+"json is null!");return 0;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"json is empty!");return 0;
                }
                if(!jsonObject.has(key)){
                    logger.info(fName+"json key invalid!");return 0;
                }
                if(jsonObject.isNull(key)){
                    logger.info(fName+"json key is null!");return 0;
                }
                long result=jsonObject.getLong(key);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        private Message buildMessage(){
            String fName="[buildMessage]";
            try {
                MessageBuilder messageBuilder=new MessageBuilder();
                Message message2Send=null;
                if(hasContent()){
                    String content=null;
                    content=getContent();
                    if(content!=null&&!content.isEmpty()){
                        logger.info(fName + ".added content");
                        messageBuilder.setContent(content);
                    }
                }
                if(hasEmbedJson()){
                    MessageEmbed embedMessage=null;
                    Collection<MessageEmbed> embeds=new ArrayList<>();
                    embedMessage=getEmbedMessage();
                    embeds.add(embedMessage);
                    if(embedMessage!=null){
                        logger.info(fName + ".added embed");
                        messageBuilder.setEmbeds(embeds);
                    }else{
                        throw  new Exception("Failed to add embed");
                    }
                }
                message2Send=messageBuilder.build();
                if(message2Send==null){
                    throw  new Exception("Failed to build message");
                }
                return  message2Send;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Message send(TextChannel textChannel){
            String fName="[send]";
            try {
                if(!textChannel.canTalk()){
                    logger.warn(fName + ".can't talk in this channel:"+textChannel.getId());
                    return null;
                }
                Message message2Send=buildMessage();
                Message messageReceived= lsMessageHelper.lsSendMessageResponse(textChannel,message2Send);
                if(messageReceived!=null){
                    logger.info(fName + ".sent");
                    if(isDeleteOption()){
                        logger.info(fName + ".add delete option");
                        lsMessageHelper.lsMessageAddReactions(messageReceived,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
                    }
                    long autoDelete=getAutoDelete();
                    if(autoDelete>0){
                        logger.info(fName + ".autoDelete="+autoDelete);
                        lsMessageHelper.lsSetAutoDelete(gGlobal,messageReceived,TimeUnit.SECONDS,autoDelete);
                    }
                    return messageReceived;
                }else{
                    throw  new Exception("Failed to send message");
                }

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Message send(User user){
            String fName="[send]";
            try {
                Message message2Send=buildMessage();
                Message messageReceived= lsMessageHelper.lsSendMessageResponse(user,message2Send);
                if(messageReceived!=null){
                    logger.info(fName + ".sent");
                    long autoDelete=getAutoDelete();
                    if(autoDelete>0){
                        logger.info(fName + ".autoDelete="+autoDelete);
                        lsMessageHelper.lsSetAutoDelete(gGlobal,messageReceived,TimeUnit.SECONDS,autoDelete);
                    }
                    return messageReceived;
                }else{
                    throw  new Exception("Failed to send message");
                }

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Message send(InteractionHook interactionHook){
            String fName="[send]";
            try {
                Message message2Send=buildMessage();
                Message messageReceived=interactionHook.editOriginal(message2Send).complete();
                if(messageReceived!=null){
                    logger.info(fName + ".sent");
                    return messageReceived;
                }else{
                    throw  new Exception("Failed to send message");
                }

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public  _NEWS getGeneralNews() {
        String fName = "[getGeneralNews]";
        try {
            if(generalNews==null)generalNews=new _NEWS();
            return generalNews;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  _NEWS getSpamNews() {
        String fName = "[getSpamNews]";
        try {
            if(spamNews==null)spamNews=new _NEWS();
            return spamNews;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  _NEWS getTriggerNews() {
        String fName = "[getTriggerNews]";
        try {
            if(triggerNews==null)triggerNews=new _NEWS();
            return triggerNews;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
