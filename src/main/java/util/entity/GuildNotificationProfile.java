package util.entity;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Arrays;

public class GuildNotificationProfile {
    Logger logger = Logger.getLogger(getClass());

    public lcJSONGuildProfile gGuildProfile=null;
    lcGlobalHelper gGlobal;
    Guild gGuild;TextChannel gTextChannel;
    public boolean flagGet4Cache=true,flagPut2Cache=true;
    public GuildNotificationProfile(lcGlobalHelper global, Guild guild){
        String fName="[constructor]";
        try {
            setGlobal(global);setGuild(guild);
            init();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public GuildNotificationProfile(lcGlobalHelper global, Guild guild, boolean doInit){
        String fName="[constructor]";
        try {
            setGlobal(global);setGuild(guild);
            if(doInit)init();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public GuildNotificationProfile(lcGlobalHelper global, TextChannel textChannel){
        String fName="[constructor]";
        try {
            setGlobal(global);setTextChannel(textChannel);
            init();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean init(){
        String fName="[init]";
        try {
            logger.info(fName);
            if(gGlobal==null)throw new Exception("global is null!");
            if(gGuild==null)throw new Exception("guild is null!");
            if(!getProfile()){
                logger.warn("failed to get profile>new profile");
                if(!newProfile()){
                    logger.warn("failed to create new profile");
                    return false;
                }
            }else{
                logger.info("got profile");
            }
            if(setDefaultProfileVariables()){
                logger.info("setDefaultProfileVariables updated>save");
                if(!saveProfile()){
                    logger.warn("failed to save>ignore");
                }
            }
            if(flagPut2Cache){
                if(putProfile()){
                    logger.info("successfully put profile to cache");
                }else{
                    logger.warn("failed put profile to cache");
                }
            }else{
                logger.info("disabled to put profile to cache");
            }
            logger.info("gGuildProfile.json="+gGuildProfile.jsonObject.toString());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(fName);
            if(flagGet4Cache)getCacheProfile();
            if(gGuildProfile!=null&&gGuildProfile.isProfile()){
                logger.info(fName + ".is locally cached");
                return true;
            }else{
                logger.info(fName + ".need to get or create");
                return getSQLProfile();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(){
        String fName="[getCacheProfile]";
        try {
            logger.info(fName);
            gGuildProfile=gGlobal.getGuildSettings(gGuild,profileName);
            if(gGuildProfile!=null&&gGuildProfile.isProfile()){
                logger.info(fName + ".is locally cached");
                return true;
            }else{
                logger.info(fName + ".not cached");
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(){
        String fName="[getSQLProfile]";
        try {
            logger.info(fName);
            gGuildProfile=new lcJSONGuildProfile(gGlobal,profileName,gGuild);
            if(gGuildProfile.getProfile()){
                logger.info(fName + ".has sql entry");
                return true;
            }else{
                logger.info(fName + ".no sql entry");
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            if(global==null){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            gGlobal=global;
            logger.info(fName+"set");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            if(guild==null){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            gGuild=guild;
            logger.info(fName+"guild="+guild.getId());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTextChannel(TextChannel textChannel){
        String fName="[setTextChannel]";
        try {
            if(textChannel==null){
                logger.info(fName+"textchannel can't be null>false");
                return false;
            }
            gTextChannel=textChannel;
            logger.info(fName+"textchannel="+textChannel.getId());
            setGuild(textChannel.getGuild());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDefaultProfileVariables(){
        String fName="[setDefaultProfileVariables]";
        try {
            logger.info(fName);
            gGuildProfile.safetyPutFieldEntry(keyConfig,new JSONObject());
            gGuildProfile.safetyPutFieldEntry(keySpamNewsGroups,new JSONObject());

            if(!gGuildProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return false;
            }
            logger.info(fName + ".updated");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean newProfile(){
        String fName="[newProfile]";
        try {
            logger.info(fName);
            gGuildProfile=new lcJSONGuildProfile(gGlobal,profileName,gGuild);
            setDefaultProfileVariables();
            logger.info(fName + ".created");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean resetProfile(){
        String fName="[resetProfile]";
        try {
            logger.info(fName);
            gGuildProfile.jsonObject =new JSONObject();
            setDefaultProfileVariables();
            logger.info(fName + ".reseted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    String profileName="notification";
    public boolean saveProfile(){
        String fName="[saveProfile]";
        try {
            logger.info(fName);
            if(flagPut2Cache)putProfile();
            if(gGuildProfile.saveProfile()){
                logger.info(fName+ ".success");return  true;
            }
            logger.warn(fName+ ".failed");return false;
        }catch (Exception e){
            logger.error(fName+ ".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putProfile(){
        String fName="[putProfile]";
        try {
            logger.info(fName);
            if(gGlobal.putGuildSettings(gGuildProfile)){
                logger.info(fName + ".success");return true;
            }
            logger.info(fName + ".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            if(gGuild==null){
                logger.info(fName+"isNull");
                return  null;
            }
            logger.info(fName+"guild="+gGuild.getId());
            return  gGuild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    final String keySpamNewsGroups ="spamnewsgroup";
    public class _POSTDEF {
        final String keyName="name",keyTimeStamp="timestamp",keyPosts="posts";
        protected JSONObject jsonObject=null;
        protected  String name=null;
        public _POSTDEF(){

        }
        public _POSTDEF(String name, JSONObject jsonObject){
            set(name,jsonObject);
        }
        public _POSTDEF set(String name, JSONObject jsonObject){
            String fName="[set]";
            try {
                if(setName(name)==null){
                    throw  new Exception("name invalid");
                }
                if(set(jsonObject)==null){
                    throw  new Exception("json invalid");
                }
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _POSTDEF set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                this.jsonObject=jsonObject;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private _POSTDEF prepareJson(){
            String fName="[prepareJson]";
            try {
                if(jsonObject==null){
                    logger.warn(fName +"json is null!");
                    jsonObject=new JSONObject();
                }
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _POSTDEF setName(String name){
            String fName="[setName]";
            try {
                prepareJson();
                if(name==null)throw  new Exception("input is null!");
                if(name.isBlank())throw  new Exception("input is blank!");
                logger.info(fName + ".name=" + name);
                this.name=name;
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
                logger.info(fName + ".result=" + this.name);
                return this.name;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _POSTDEF setTimeStamp(long timeStamp){
            String fName="[setTimeStamp]";
            try {
                prepareJson();
                logger.info(fName + ".timeStamp=" + timeStamp);
                jsonObject.put(keyTimeStamp,timeStamp);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getTimeStamp(){
            String fName="[getTimeStamp]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                long result=jsonObject.getLong(keyTimeStamp);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public JSONArray getPostsAsJson(){
            String fName="[getPostsAsJson]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                JSONArray result=jsonObject.getJSONArray(keyPosts);
                logger.info(fName + ".result=" + result.toString());
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getPostsAsJson(int index){
            String fName="[getPostsAsJson]";
            try {
                JSONArray jsonArray=getPostsAsJson();
                if(jsonArray==null){
                    throw  new Exception("json is null!");
                }
                if(jsonArray.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                if(index<0||jsonArray.length()<=index){
                    throw  new Exception("invalid index!");
                }
                JSONObject result=jsonArray.getJSONObject(index);
                logger.info(fName + ".result=" + result.toString());
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _POSTDEF addPost(Message message){
            String fName="[addPost]";
            try {
                if(message==null){
                    throw  new Exception("message is null!");
                }
                _POST post=new _POST(message);
                JSONObject jsonPostObject=post.getJson();
                if(jsonPostObject==null)throw  new Exception("post_json is null!");
                if(jsonObject==null){
                    throw  new Exception("main_json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("main_json is empty!");
                }
                if(!jsonObject.has(keyPosts)||jsonObject.isNull(keyPosts)){
                    jsonObject.put(keyPosts,new JSONArray());
                }
                jsonObject.getJSONArray(keyPosts).put(jsonPostObject);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {

                logger.info(fName + ".result=" + jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

    }
    protected class _POST{
        final String keyMessageId="message_id",keyTimeStamp="timestamp",keyChannelId="channel_id",keyChannelIsNSFW="channel_nsfw";
        protected JSONObject jsonObject=null;
        public _POST(){

        }
        public _POST(JSONObject jsonObject){
            set(jsonObject);
        }
        public _POST(Message message){
            set(message);
        }
        public _POST set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                this.jsonObject=jsonObject;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _POST set(Message message){
            String fName="[set]";
            try {
                long message_id=message.getIdLong(), channel_id=message.getTextChannel().getIdLong();
                boolean isNsfw=message.getTextChannel().isNSFW();
                long timestamp=message.getTimeCreated().toEpochSecond();
                logger.info(fName + ".message_id=" + message_id+", channel_id="+channel_id+", timestamp="+timestamp+", isNSFW="+isNsfw);
                jsonObject=new JSONObject();
                jsonObject.put(keyMessageId,message_id).put(keyChannelId,channel_id).put(keyTimeStamp,timestamp).put(keyChannelIsNSFW,isNsfw);
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            return jsonObject;
        }
        public long getTimeStamp(){
            String fName="[getTimeStamp]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                if(jsonObject.isEmpty()){
                    throw  new Exception("json is empty!");
                }
                long result=jsonObject.getLong(keyTimeStamp);
                logger.info(fName + ".result=" + result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }


    }
    public _POSTDEF getSpamNewsGroup(String name) {
        String fName = "[getSpamNewsGroup]";
        try {
            if(name==null)throw  new Exception("input is null!");
            if(name.isBlank())throw  new Exception("input is blank!");
            logger.info(fName + ".name=" + name);
            JSONObject jsonObject=gGuildProfile.jsonObject.getJSONObject(keySpamNewsGroups).getJSONObject(name);
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            return  new _POSTDEF(name,jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public GuildNotificationProfile setSpamNewsGroup(_POSTDEF generic) {
        String fName = "[setSpamNewsGroup]";
        try {
            if(generic==null)throw  new Exception("input is null!");
            String name=generic.getName();
            if(name==null)throw  new Exception("name is null!");
            if(name.isBlank())throw  new Exception("name is blank!");
            logger.info(fName + ".name=" +generic.getName());
            JSONObject jsonObject=gGuildProfile.jsonObject.getJSONObject(keySpamNewsGroups).put(name,generic.getJson());
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            return  this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int checkSpam(TextChannel textChannel) {
        String fName = "[checkSpam]";
        try {
            if(!textChannel.canTalk()){
                logger.warn(fName + ".can't talk in this channel:"+textChannel.getId());
                return 3;
            }
            GuildNotificationReader._NEWS spamnews= gGlobal.generalGuildNotification.getSpamNews();
            String name=spamnews.getName();
            logger.info(fName+" json="+spamnews.get().toString());
            if(name==null)throw  new Exception("name is null!");
            if(name.isBlank())throw  new Exception("name is blank!");
            logger.info(fName + ".name=" + name);
            if(!spamnews.isEnabled()){
                logger.info(fName + ".not enabled");
                return 4;
            }
            Timestamp timestamp_current0 = new Timestamp(System.currentTimeMillis());
            long timestamp_current =timestamp_current0.getTime();
            _POSTDEF notification= getSpamNewsGroup(name);
            Message message=null;
            if(notification==null){
                logger.info(fName + ".does not exist>create first time");
                notification =new _POSTDEF();
                notification.setName(name).setTimeStamp(timestamp_current);
                logger.info(fName + ".preparing to send");
                message=spamnews.send(textChannel);
                if(message!=null){
                    logger.info(fName + ".sent");
                    notification.setTimeStamp(timestamp_current).addPost(message);
                }else{
                    throw  new Exception("Failed to send message");
                }
            }else{
                logger.info(fName + ".does exist>check");
                long timestamp_last=notification.getTimeStamp();
                long timeout=spamnews.getTimeout();
                long diff=timestamp_current-timestamp_last;
                logger.info(fName+"last="+timestamp_last+", current="+timestamp_current+", duration="+timeout+", diff="+diff);
                if(diff>timeout){
                    logger.info(fName + ".do create, preparing to send");
                    message=spamnews.send(textChannel);
                    if(message!=null){
                        logger.info(fName + ".sent");
                        notification.setTimeStamp(timestamp_current).addPost(message);
                    }else{
                        throw  new Exception("Failed to send message");
                    }
                }else{
                    logger.info(fName + ".not the time yet>abort");
                    return  0;
                }
            }
            logger.info(fName + ".saving...");
            setSpamNewsGroup(notification);
            saveProfile();
            return  1;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    final String keyConfig="config";
    public _CONFIG getConfig() {
        String fName = "[getConfig]";
        try {
            String key=keyConfig;
            if(!gGuildProfile.jsonObject.has(key)){
                logger.warn(fName+"jsonObject["+key+"] not exists>create");
                gGuildProfile.jsonObject.put(key,new JSONObject());
            }
            if(gGuildProfile.jsonObject.isNull(key)){
                logger.warn(fName+"jsonObject["+key+"] is null>create");
                gGuildProfile.jsonObject.put(key,new JSONObject());
            }
            JSONObject jsonObject=gGuildProfile.jsonObject.getJSONObject(key);
            if(jsonObject==null){
                logger.warn(fName+"jsonObject["+key+"] is null 2>create");
                gGuildProfile.jsonObject.put(key,new JSONObject());
                jsonObject=gGuildProfile.jsonObject.getJSONObject(key);
            }
            logger.info(fName + ".jsonObject["+key+"]=" + jsonObject.toString());

            return  new _CONFIG(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public class _CONFIG{
        final String keyGeneralNews ="general_news", keySpamNews ="spam_news", keyTriggeredNews ="triggered_news";
        protected JSONObject jsonObject=null;
        public _CONFIG(){

        }
        public _CONFIG(JSONObject jsonObject){
            set(jsonObject);
        }
        public _CONFIG set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    throw  new Exception("json is null!");
                }
                this.jsonObject=jsonObject;
                if(this.jsonObject.isEmpty()){
                    logger.warn(fName+"json is empty>create");
                    this.jsonObject.put(keyGeneralNews,new JSONObject());
                    this.jsonObject.put(keySpamNews,new JSONObject());
                }
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson(){
            String fName="[getJson]";
            try {

                logger.info(fName + ".result=" + jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _NEWS getGeneralNewsConfig() {
            String fName = "[getGeneralNewsConfig]";
            try {
                String key= keyGeneralNews;
                if(!jsonObject.has(key)){
                    logger.warn(fName+"jsonObject["+key+"] not exists");
                    jsonObject.put(key,new JSONObject());
                }
                if(jsonObject.isNull(key)){
                    logger.warn(fName+"jsonObject["+key+"] is null");
                    jsonObject.put(key,new JSONObject());
                }
                JSONObject jsonObject1=jsonObject.getJSONObject(key);
                if(jsonObject1==null){
                    logger.warn(fName+"jsonObject["+key+"] is null 2");
                    jsonObject.put(key,new JSONObject());
                    jsonObject1=jsonObject.getJSONObject(key);
                }
                logger.info(fName + ".jsonObject["+key+"]=" + jsonObject1.toString());
                return  new _NEWS(jsonObject1);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _NEWS getSpamNewsConfig() {
            String fName = "[getSpamNewsConfig]";
            try {
                String key= keyGeneralNews;
                if(!jsonObject.has(key)){
                    logger.warn(fName+"jsonObject["+key+"] not exists");
                    jsonObject.put(key,new JSONObject());
                }
                if(jsonObject.isNull(key)){
                    logger.warn(fName+"jsonObject["+key+"] is null");
                    jsonObject.put(key,new JSONObject());
                }
                JSONObject jsonObject1=jsonObject.getJSONObject(key);
                if(jsonObject1==null){
                    logger.warn(fName+"jsonObject["+key+"] is null 2");
                    jsonObject.put(key,new JSONObject());
                    jsonObject1=jsonObject.getJSONObject(key);
                }
                logger.info(fName + ".jsonObject["+key+"]=" + jsonObject1.toString());
                return  new _NEWS(jsonObject1);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public _NEWS getTrigeredNewsConfig() {
            String fName = "[getTrigeredNewsConfig]";
            try {
                String key= keyTriggeredNews;
                if(!jsonObject.has(key)){
                    logger.warn(fName+"jsonObject["+key+"] not exists");
                    jsonObject.put(key,new JSONObject());
                }
                if(jsonObject.isNull(key)){
                    logger.warn(fName+"jsonObject["+key+"] is null");
                    jsonObject.put(key,new JSONObject());
                }
                JSONObject jsonObject1=jsonObject.getJSONObject(key);
                if(jsonObject1==null){
                    logger.warn(fName+"jsonObject["+key+"] is null 2");
                    jsonObject.put(key,new JSONObject());
                    jsonObject1=jsonObject.getJSONObject(key);
                }
                logger.info(fName + ".jsonObject["+key+"]=" + jsonObject1.toString());
                return  new _NEWS(jsonObject1);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public class _NEWS{
            final String keyDisabled="disabled",keyAutoPostDisabled="auto_disabled",keyChannel="channel_id";
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
                    this.jsonObject=jsonObject;
                    if(this.jsonObject.isEmpty()){
                        logger.warn(fName+"json is empty>create");
                        this.jsonObject.put(keyDisabled,false);
                        this.jsonObject.put(keyAutoPostDisabled,false);
                        this.jsonObject.put(keyChannel,0L);
                    }
                    logger.info(fName + ".jsonObject=" + jsonObject.toString());
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public JSONObject getJson(){
                String fName="[getJson]";
                try {

                    logger.info(fName + ".result=" + jsonObject.toString());
                    return jsonObject;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _NEWS setDisabled(boolean disabled){
                String fName="[setDisabled]";
                try {
                    logger.info(fName + ".disabled=" + disabled);
                    this.jsonObject.put(keyDisabled,disabled);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isDisabled(){
                String fName="[isDisabled]";
                try {
                    boolean result=this.jsonObject.getBoolean(keyDisabled);
                    logger.info(fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean hasDisabled(){
                String fName="[hasDisabled]";
                try {
                    if(jsonObject==null){
                        logger.warn(fName+"json is null>false");
                        return false;
                    }
                    if(jsonObject.isEmpty()){
                        logger.warn(fName+"json isEmpty>false");
                        return false;
                    }
                    if(!jsonObject.has(keyDisabled)){
                        logger.warn(fName+"json[disabled] does not exists>false");
                        return false;
                    }
                    if(jsonObject.isNull(keyDisabled)){
                        logger.warn(fName+"json[disabled] is null>false");
                        return false;
                    }
                    logger.info(fName+"none above>true");
                    return true;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public _NEWS setAutoDisabled(boolean disabled){
                String fName="[seAutotDisabled]";
                try {
                    logger.info(fName + ".disabled=" + disabled);
                    this.jsonObject.put(keyAutoPostDisabled,disabled);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isAutoDisabled(){
                String fName="[isAutoDisabled]";
                try {
                    boolean result=this.jsonObject.getBoolean(keyAutoPostDisabled);
                    logger.info(fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean hasAutoDisabled(){
                String fName="[hasAutoDisabled]";
                try {
                    if(jsonObject==null){
                        logger.warn(fName+"json is null>false");
                        return false;
                    }
                    if(jsonObject.isEmpty()){
                        logger.warn(fName+"json isEmpty>false");
                        return false;
                    }
                    if(!jsonObject.has(keyAutoPostDisabled)){
                        logger.warn(fName+"json[disabled] does not exists>false");
                        return false;
                    }
                    if(jsonObject.isNull(keyAutoPostDisabled)){
                        logger.warn(fName+"json[disabled] is null>false");
                        return false;
                    }
                    logger.info(fName+"none above>true");
                    return true;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public _NEWS setChannel(long channel_id){
                String fName="[setChannel]";
                try {
                    logger.info(fName + ".channel_id=" + channel_id);
                    this.jsonObject.put(keyChannel,channel_id);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public _NEWS clearChannel(){
                String fName="[clearChannel]";
                try {
                    logger.info(fName + ".channel_id= to clear");
                    this.jsonObject.put(keyChannel,0);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean hasChannel(){
                String fName="[hasChannel]";
                try {
                    if(jsonObject==null){
                        logger.warn(fName+"json is null>false");
                        return false;
                    }
                    if(jsonObject.isEmpty()){
                        logger.warn(fName+"json isEmpty>false");
                        return false;
                    }
                    if(!jsonObject.has(keyChannel)){
                        logger.warn(fName+"json[channel_id] does not exists>false");
                        return false;
                    }
                    if(jsonObject.isNull(keyChannel)){
                        logger.warn(fName+"json[channel_id] is null>false");
                        return false;
                    }
                    long id=jsonObject.getLong(keyChannel);
                    if(id<=0){
                        logger.warn(fName+"json[channel_id]="+id+" is invalid>false");
                        return false;
                    }
                    logger.info(fName+"none above>true");
                    return true;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public String getChannelId(){
                String fName="[getChannelId]";
                try {
                    long resultL=this.jsonObject.getLong(keyChannel);
                    String result=String.valueOf(resultL);
                    logger.info(fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public long getChannelIdAsLong(){
                String fName="[getChannelIdAsLong]";
                try {
                    long result=this.jsonObject.getLong(keyChannel);
                    logger.info(fName + ".result=" + result);
                    return result;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0L;
                }
            }
        }
    }

}
