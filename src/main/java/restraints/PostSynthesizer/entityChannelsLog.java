package restraints.PostSynthesizer;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class entityChannelsLog {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityChannelsLog]";
    private long id=0;
    private String name="";
    private boolean nsfw=false;
    private Guild.NSFWLevel nsfwLevel= Guild.NSFWLevel.UNKNOWN;
    private List<entityCHANNEL>channels=new ArrayList();

    public entityChannelsLog(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public boolean clear(){
        String fName="[clear]";
        try {

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityChannelsLog set(Guild guild){
        String fName="[set]";
        try {
            if(guild==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            logger.info(fName+"guild="+guild.getId());
            id=guild.getIdLong();
            name=guild.getName();
            nsfwLevel=guild.getNSFWLevel();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChannelsLog set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            String key="";
            key= llCommonKeys.keyChannels;
            if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                JSONArray jsonArray=jsonObject.optJSONArray(key);
                for(int i=0;i<jsonArray.length();i++){
                    channels.add(new entityCHANNEL(jsonArray.getJSONObject(i)));
                }
            }
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isNsfw(){
        String fName="[isNsfw]";
        try {
            logger.info(fName+"value="+nsfw);
            return nsfw;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Guild.NSFWLevel nsfwLevel(){
        String fName="[nsfwLevel]";
        try {
            logger.info(fName+"value="+nsfwLevel.name());
            return nsfwLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return Guild.NSFWLevel.UNKNOWN;
        }
    }
    public int nsfwLevelInt(){
        String fName="[nsfwLevelInt]";
        try {
            int i= nsfwLevel.getKey();
            logger.info(fName+"value="+i);
            return i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public String nsfwLevelName(){
        String fName="[nsfwLevelName]";
        try {
            String i= nsfwLevel.name();
            logger.info(fName+"value="+i);
            return i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String name(){
        String fName="[name]";
        try {
            logger.info(fName+"value="+name);
            return name;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String id(){
        String fName="[id]";
        try {
            String text=String.valueOf(idLong());
            logger.info(fName+"value="+text);
            return text;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long idLong(){
        String fName="[idLong]";
        try {
            logger.info(fName+"value="+id);
            return id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONArray getChannelsJSON(){
        String fName="[getChannelsJSON]";
        try {
            JSONArray jsonArray=new JSONArray();
            for(entityCHANNEL channel:channels){
                jsonArray.put(channel.getJSON());
            }
            logger.info("jsonArray="+jsonArray.toString());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.keyId,idLong());
            jsonObject.put(llCommonKeys.keyName,name());
            jsonObject.put(llCommonKeys.keyChannels,getChannelsJSON());
            jsonObject.put(llCommonKeys.keyNsfwLevel,nsfwLevelName());
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isChannelsEmptye(){
        String fName="[isChannelsEmptye]";
        try {
            boolean i=channels.isEmpty();
            logger.info("size="+i);
            return i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public int channelsSize(){
        String fName="[channelsSize]";
        try {
            int i=channels.size();
            logger.info("size="+i);
            return i;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<entityCHANNEL> channels(){
        String fName="[channelsSize]";
        try {
            return channels;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public entityCHANNEL getChannelAt(int index){
        String fName="[getChannelAt]";
        try {
            logger.info(fName+"index="+index);
            return channels.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCHANNEL getChannelById(long id){
        String fName="[getChannelById]";
        try {
            logger.info(fName+"id="+id);
            for(entityCHANNEL channel:channels){
                if(channel.idLong()==id){
                    logger.info(fName+"found");
                    return channel;
                }
            }
            logger.info(fName+"not found");
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChannelsLog setChannelAt(int index, entityCHANNEL channel){
        String fName="[setChannelAt]";
        try {
            logger.info(fName+"index="+index);
            channels.set(index,channel);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChannelsLog setChannelById(long id, entityCHANNEL channel){
        String fName="[setChannelById]";
        try {
            logger.info(fName+"id="+id);
            for(int i=0;i<channels.size();i++){
                entityCHANNEL channels1=channels.get(i);
                if(channels1.idLong()==id){
                    logger.info(fName+"found");
                    channels.set(i,channel);
                    return this;
                }
            }
            logger.info(fName+"not found");
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasChannelById(long id){
        String fName="[hasChannelById]";
        try {
            logger.info(fName+"id="+id);
            for(int i=0;i<channels.size();i++){
                entityCHANNEL channels1=channels.get(i);
                if(channels1.idLong()==id){
                    logger.info(fName+"found");
                    return true;
                }
            }
            logger.info(fName+"not found");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityChannelsLog setChannel(entityCHANNEL channel){
        String fName="[setChannel]";
        try {
            long id=channel.idLong();
            logger.info(fName+"id="+id);
            for(int i=0;i<channels.size();i++){
                entityCHANNEL channels1=channels.get(i);
                if(channels1.idLong()==id){
                    logger.info(fName+"found");
                    channels.set(i,channel);
                    return this;
                }
            }
            logger.info(fName+"not found");
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChannelsLog addChannelById(entityCHANNEL channel){
        String fName="[addChannel]";
        try {
            if(channel==null){
                logger.warn(fName+"inpuit cant be null");
            }
            channels.add(channel);
            logger.info(fName+"added");
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCHANNEL putChannel(TextChannel textChannel){
        String fName="[putChannel]";
        try {
            if(textChannel==null){
                logger.warn(fName+"input cant be null");
            }
            long id=textChannel.getIdLong();
            logger.info(fName+"textChannel.id="+id);
            entityCHANNEL channel=null;
            if(isChannelsEmptye()){
                channel=new entityCHANNEL(textChannel);
                channels.add(channel);

            }else
            if(!hasChannelById(id)){
                channel=new entityCHANNEL(textChannel);
                channels.add(channel);
            }else{
                channel=getChannelById(id);
                channel.set(textChannel);
            }
            return channel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public  class entityCHANNEL {
        String cName="[entityCHANNELS]";
        public entityCHANNEL(){
            String fName="[constructor]";
            logger.info(fName);
        }
        public entityCHANNEL(TextChannel textChannel){
            String fName="[constructor]";
            logger.info(fName);
            set(textChannel);
        }
        public entityCHANNEL(JSONObject jsonObject){
            String fName="[constructor]";
            logger.info(fName);
            set(jsonObject);
        }
        private  long id=0;
        private  String name="";
        private  boolean nsfw=false;
        public entityCHANNEL set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(fName+"jsonObject is null");
                    return null;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                String key="";
                key= llCommonKeys.keyId;
                if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    id=jsonObject.optLong(key,0);
                }
                key=llCommonKeys.keyName;
                if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    name=jsonObject.optString(key,"");
                }
                key=llCommonKeys.keyNsfw;
                if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    nsfw=jsonObject.optBoolean(key,false);
                }
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public entityCHANNEL set(TextChannel textChannel){
            String fName="[set]";
            try {
                if(textChannel==null){
                    logger.info(fName+"textChannel is null");
                    return null;
                }
                logger.info(fName+"textChannel="+textChannel.getId());
                id=textChannel.getIdLong();
                name=textChannel.getName();
                nsfw=textChannel.isNSFW();
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isNsfw(){
            String fName="[isNsfw]";
            try {
                logger.info(fName+"value="+nsfw);
                return nsfw;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String name(){
            String fName="[name]";
            try {
                logger.info(fName+"value="+name);
                return name;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String id(){
            String fName="[id]";
            try {
                String text=String.valueOf(idLong());
                logger.info(fName+"value="+text);
                return text;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public long idLong(){
            String fName="[idLong]";
            try {
                logger.info(fName+"value="+id);
                return id;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public JSONObject getJSON(){
            String fName="[getJSON]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.keyId,id);
                jsonObject.put(llCommonKeys.keyName,name);
                jsonObject.put(llCommonKeys.keyNsfw,nsfw);
                logger.info("jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
    }



}
