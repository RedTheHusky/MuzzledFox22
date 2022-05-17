package models.lc.discordentities.test.guild;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.lc.sticker.lcGuildSticker;
import models.ll.llCommonKeys;
import models.ls.lsStreamHelper;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.requests.Route;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetGuildStickers;

public class lcGuildEmotesPack {
    protected Logger logger = Logger.getLogger(getClass());
    protected Guild guild=null; String guild_id="";
    protected List<Emote> emotes=new ArrayList<>();
    public lcGuildEmotesPack() {

    }
    public lcGuildEmotesPack(Guild guild) {
        String fName = "build";
        try {
            if(setGuild(guild)) {
                setPack(this.guild);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildEmotesPack(long guild, List<JDA>jdas) {
        String fName = "build";
        try {
            if(setGuild(guild,jdas)){
                setPack(this.guild);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private boolean setGuild(Guild guild) {
        String fName = "[setGuild]";
        try {
            logger.info(fName+"guild="+guild.getId());
            this.guild=guild;
            this.guild_id=guild.getId();
            return  true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setGuild(long id,List<JDA>jdas) {
        String fName = "[setGuild]";
        try {
            logger.info(fName+"id="+id);
            for(JDA jda:jdas){
                try{
                    Guild guild=jda.getGuildById(id);
                    if(guild!=null){
                        this.guild=guild;
                        this.guild_id=this.guild.getId();
                        return true;
                    }
                }catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setPack(Guild guild) {
        String fName = "[setPack]";
        try {
            logger.info(fName+"id="+guild.getId());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            this.emotes=this.guild.getEmotes();
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean isEmpty() {
        String fName = "[isEmpty]";
        try {
            boolean result=emotes.isEmpty();
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int size() {
        String fName = "[size]";
        try {
            int i=emotes.size();
            logger.info(fName+"size="+i);
            return i;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<Emote> getEmotes() {
        String fName = "[getEmotes]";
        try {
            logger.info(fName+"all");
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return emotes;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Emote getEmote(long id) {
        String fName = "[getEmote]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            for(Emote emote:emotes){
                if(emote.getIdLong()==id){
                    logger.info(fName+"found");
                    return emote;
                }
            }
            logger.warn(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getEmotesIndex(long id) {
        String fName = "[getEmotesIndex]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return -1;
            }
            for(int i=0;i<emotes.size();i++){
                Emote emote=emotes.get(i);
                if(emote.getIdLong()==id){
                    logger.info(fName+"found at="+i);
                    return i;
                }
            }
            logger.warn(fName+"not found");
            return -1;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public Emote getEmote(int index) {
        String fName = "[getEmote]";
        try {
            logger.info(fName+"index="+index);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return emotes.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lcGuildEmote> getEmotesAslcEmote() {
        String fName = "[getEmotesAslcEmote]";
        try {
            logger.info(fName+"all");
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            List<lcGuildEmote>list=new ArrayList<>();
            for(Emote emote:emotes){
                list.add(new lcGuildEmote(emote));
            }
            return list;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildEmote getStickerAslcEmote(long id) {
        String fName = "[getEmoteAslcEmote]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            for(Emote emote:emotes){
                if(emote.getIdLong()==id){
                    logger.info(fName+"found");
                    return new lcGuildEmote(emote);
                }
            }
            logger.warn(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildEmote getEmoteAslcEmote(int index) {
        String fName = "[getEmoteAslcEmote]";
        try {
            logger.info(fName+"index="+index);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return new lcGuildEmote(emotes.get(index));
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public String getGuildId() {
        String fName = "[getGuildId]";
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
    public long getGuildIdLong() {
        String fName = "[getGuildIdLong]";
        try {
            logger.info(fName+"value="+guild_id);
            return Long.parseLong(guild_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            return guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasGuild() {
        String fName = "[hasGuild]";
        try {
            return guild!=null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcTempZipFile getZip() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            lcTempZipFile zipFile=new lcTempZipFile();
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.keyGuild_Id,getGuildId());
                jsonObject.put(llCommonKeys.keyCount,emotes.size());

                logger.info(fName+"pack="+jsonObject.toString());
                zipFile.addEntity("pack.json",jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(int i=0;i<emotes.size();i++){
                try {
                    Emote emote=emotes.get(i);
                    lcGuildEmote lcemote=new lcGuildEmote(emote);
                    logger.info(fName+"emote["+i+"].id="+emote.getId());
                    JSONObject jsonObject=lcemote.getJson();
                    logger.info(fName+"emote["+i+"].json="+jsonObject);
                    zipFile.addEntity(emote.getId()+".json",lcemote.getJson());
                    if(emote.isAnimated()){
                        zipFile.addEntity(emote.getId()+".gif",lcemote.getStream());
                    }else{
                        zipFile.addEntity(emote.getId()+".png",lcemote.getStream());
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return zipFile;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getZipAsInputStream() {
        String fName = "[getZipAsInputStream]";
        try {
            logger.info(fName);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            lcTempZipFile zipFile=getZip();
            InputStream targetStream = zipFile.getInputStream();
            return targetStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public class lcGuildEmote {
        protected Logger logger = Logger.getLogger(getClass());
        protected Emote emote=null;
        public lcGuildEmote() {

        }
        public lcGuildEmote(Emote emote) {
            String fName = "build";
            try {
                setEmote(emote);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean setEmote(Emote emote) {
            String fName = "[setGuild]";
            try {
                logger.info(fName+"emote="+emote.getId());
                this.emote=emote;
                return  true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public boolean isEmpty() {
            String fName = "[isEmpty]";
            try {
                if(emote!=null){
                    return false;
                }
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public Emote getEmote() {
            String fName = "[getEmote]";
            try {
                logger.info(fName+"all");
                if(isEmpty()){
                    logger.warn(fName+"is empty");
                    return null;
                }
                return emote;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getJson() {
            String fName = "[getJson]";
            try {
                if(isEmpty()){
                    logger.warn(fName+"is empty");
                    return new JSONObject();
                }
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.keyId,emote.getIdLong());
                jsonObject.put(llCommonKeys.keyName,emote.getName());
                jsonObject.put(llCommonKeys.keyGuild_Id,emote.getGuild().getId());
                jsonObject.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyAnimated,emote.isAnimated());
                jsonObject.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyManaged,emote.isManaged());
                jsonObject.put(llCommonKeys.lsGetEmojiObjectJsonKeys.keyAvailable,emote.isAvailable());
                jsonObject.put(llCommonKeys.keyTimeCreated,emote.getTimeCreated().toEpochSecond());
                List<Role>roles=emote.getRoles();
                JSONArray jsonArray=new JSONArray();
                for(Role role:roles){
                    jsonArray.put(role.getIdLong());
                }
                jsonObject.put(llCommonKeys.keyRoles,jsonArray);
                jsonObject.put(llCommonKeys.keyImageUrl,emote.getImageUrl());
                logger.info(fName+"jsonObject="+jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public InputStream getStream() {
            String fName = "[getStream]";
            try {
                InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(this.emote.getImageUrl());
                return  inputStream;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcTempZipFile getZip() {
            String fName = "[getZip]";
            try {
                logger.info(fName);
                if(isEmpty()){
                    logger.warn(fName+"is empty");
                    return null;
                }
                lcTempZipFile zipFile=new lcTempZipFile();
                try {
                    zipFile.addEntity(this.emote.getId()+".json",getJson());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    if(this.emote.isAnimated()){
                        zipFile.addEntity(this.emote.getId()+".gif",getStream());
                    }else{
                        zipFile.addEntity(this.emote.getId()+".png",getStream());
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                return zipFile;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public InputStream getZipAsInputStream() {
            String fName = "[getZipAsInputStream]";
            try {
                logger.info(fName);
                if(isEmpty()){
                    logger.warn(fName+"is empty");
                    return null;
                }
                lcTempZipFile zipFile=getZip();
                InputStream targetStream = zipFile.getInputStream();
                return targetStream;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }



    }



}
