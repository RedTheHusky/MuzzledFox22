package models.lc.sticker;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcGetMessageJson;
import models.lc.lcTempZipFile;
import models.ll.llCommonKeys;
import models.ls.lsStreamHelper;
import models.ls.lsStringUsefullFunctions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.*;

public class lcGuildSticker extends  lcSticker{
    Logger logger = Logger.getLogger(getClass());
    protected String guild_id="", author_id ="",tag="";
    protected Guild guild=null;
    protected User author=null;
    protected boolean available=false;

    public lcGuildSticker() {

    }

    public  interface KEYS{
        String keyId= llCommonKeys.keyId,
                keyName=llCommonKeys.keyName,
                keyTags="tags",
                keyType=llCommonKeys.keyType,
                keyFormateType="format_type",
                keyDescription=llCommonKeys.keyDescription,
                keyAsset="asset",
                keyAvailable="available",
                keyGuildId=llCommonKeys.keyGuild_Id,
                keyuser=llCommonKeys.keyUser;
    }

    protected String tmpName="", tmpTag="", tmpDesc ="";
    public lcGuildSticker(JSONObject jsonObject,Guild guild) {
        String fName = "build";
        try {
            if(setSticker(jsonObject)){
              if(setGuild(guild)){
                  setUser(getAuthorIdAsLong(), guild.getJDA());
              }
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(JSONObject jsonObject, Guild guild, User author) {
        String fName = "build";
        try {
            if(setSticker(jsonObject)){
                setGuild(guild);
                setUser(author);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(JSONObject jsonObject) {
        String fName = "build";
        try {
            setSticker(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(JSONObject jsonObject,List<JDA>jdas) {
        String fName = "build";
        try {
            if(setSticker(jsonObject)){
                setGuild(getGuildIdAsLong(),jdas);
                setUser(getAuthorIdAsLong(),jdas);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(JSONObject jsonObject,JDA jda) {
        String fName = "build";
        try {
            this.jda=jda;
            if(setSticker(jsonObject)){
                setGuild(getGuildIdAsLong(),jda);
                setUser(getAuthorIdAsLong(),jda);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(long id,List<JDA>jdas) {
        String fName = "build";
        try {
            if(getStickerHttp(id)){
                setGuild(getGuildIdAsLong(),jdas);
                setUser(getAuthorIdAsLong(),jdas);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(long id) {
        String fName = "build";
        try {
            getStickerHttp(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(Message message, List<JDA>jdas) {
        String fName = "build";
        try {
           if(set4Message(message)){
               setGuild(getGuildIdAsLong(),jdas);
               setUser(getAuthorIdAsLong(),jdas);
           }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildSticker(Message message) {
        String fName = "build";
        try {
            if(set4Message(message)){
                setGuild(getGuildIdAsLong(),message.getJDA());
                setUser(getAuthorIdAsLong(),message.getJDA());
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
            guild_id=guild.getId();
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setUser(User author) {
        String fName = "[setUser]";
        try {

            logger.info(fName+"author="+author.getId());
            this.author=author;
            author_id=author.getId();
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setSticker(JSONObject jsonObject) {
        String fName = "[set]";
        try {

            logger.info(fName+"jsonObject="+jsonObject.toString());
            Iterator<String> keys=jsonObject.keys();
            while(keys.hasNext()){
                try {
                    String key=keys.next();
                    switch (key){
                        case KEYS.keyId:
                            id=jsonObject.optString(key);
                            break;
                        case KEYS.keyName:
                            name=jsonObject.optString(key);
                            break;
                        case KEYS.keyDescription:
                            description=jsonObject.optString(key);
                            break;
                        case KEYS.keyGuildId:
                            guild_id=jsonObject.optString(key);
                            break;
                        case KEYS.keyType:
                            type=jsonObject.optInt(key);
                            break;
                        case KEYS.keyFormateType:
                            format_type=jsonObject.optInt(key);
                            break;
                        case KEYS.keyTags:
                            setTags(jsonObject.optString(llCommonKeys.StickerStructure.Tags));
                            break;
                        case KEYS.keyAvailable:
                            available=jsonObject.optBoolean(key);
                            break;
                        case KEYS.keyuser:
                            JSONObject jsonObject1=jsonObject.optJSONObject(key);
                            author_id =jsonObject1.optString(llCommonKeys.keyId);
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
    protected boolean setTags(String source) {
        String fName = "[setTags]";
        try {
            logger.info(fName+"source="+ source);
            source=source.replaceAll(" ","");
            String []items = source.split(",");
            for(String item:items){
                tags.add(item);
            }
            if(!tags.isEmpty())tag=tags.get(0);
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getStickerHttp(long id) {
        String fName = "[getStickerHttp]";
        try {
            logger.info(fName+"id="+id);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetSticker;
            url=url.replaceAll("!STICKER", String.valueOf(id));
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
            return  setSticker(jsonObject);
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
    private boolean setGuild(long id,JDA jda) {
        String fName = "[setGuild]";
        try {
            logger.info(fName+"id="+id);
            Guild guild=jda.getGuildById(id);
            if(guild!=null){
                this.guild=guild;
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setUser(long id,List<JDA>jdas) {
        String fName = "[setUser]";
        try {
            logger.info(fName+"id="+id);
            for(JDA jda:jdas){
                try{
                    User user=jda.getUserById(id);
                    if(user!=null){
                        this.author=user;
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
    private boolean setUser(long id,JDA jda) {
        String fName = "[setUser]";
        try {
            logger.info(fName+"id="+id);
            User user=jda.getUserById(id);
            if(user!=null){
                this.author=user;
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean set4Message(Message message) {
        String fName = "[set4Message]";
        try {
            logger.info("message="+message.getId());
            lcGetMessageJson messageJson=new lcGetMessageJson(message);
            if(messageJson.isStickerItemsEmpty()||messageJson.getStickerItemsJson().isEmpty()){
                logger.warn("no sticker items");
                return false;
            }
            JSONObject jsonObject= messageJson.getStickerItemsJson().getJSONObject(0);
            logger.info("jsonObject="+jsonObject.toString());
            if(!jsonObject.has(llCommonKeys.keyId)){
                logger.warn("no sticker id");
                return false;
            }
            String idStr=jsonObject.optString(llCommonKeys.keyId);
            if(idStr==null||idStr.isBlank()){
                logger.warn("no id");
                return false;
            }
            long idLong=Long.parseLong(idStr);
            if(!getStickerHttp(idLong)){
                logger.warn("failed to set sticker");
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }



    public boolean isImg() {
        String fName = "[isImg]";
        try {
            logger.info(fName+"value="+format_type);
            return format_type == 1 || format_type == 2;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getFormatTypeAsInt() {
        String fName = "[getFormatTypeAsInt]";
        try {
            logger.info(fName+"result="+format_type);
            return  format_type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public MessageSticker.StickerFormat getFormatType() {
        String fName = "[getFormat]";
        try {
            MessageSticker.StickerFormat result=MessageSticker.StickerFormat.UNKNOWN;
            switch (getFormatTypeAsInt()){
                case 1:result= MessageSticker.StickerFormat.PNG;break;
                case 2:result= MessageSticker.StickerFormat.APNG;break;
                case 3:result= MessageSticker.StickerFormat.LOTTIE;break;
            }
            logger.info(fName+"result="+result);
            return  result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return MessageSticker.StickerFormat.UNKNOWN;
        }
    }
    public String getName() {
        String fName = "getName";
        try {
            logger.info(fName+"value="+name);
            if(tmpName!=null&&!tmpName.isBlank()){
                logger.info("name is edited");
                return tmpName;
            }
            if(name==null)return "";
            return  name;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getDescription() {
        String fName = "getDescription";
        try {
            logger.info(fName+"value="+description);
            if(tmpDesc!=null&&!tmpDesc.isBlank()){
                logger.info("desc is edited");
                return tmpDesc;
            }
            if(description==null)return "";
            return description;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getTag() {
        String fName = "[getTag]";
        try {
            logger.info(fName+"value="+tags);
            if(tmpTag!=null&&!tmpTag.isBlank()){
                logger.info("tag is edited");
                return tmpTag;
            }
            if(tag==null) return "";
            return  tag;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    public JDA getJDA() {
        String fName = "getJDA";
        try {
            return jda;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isAvailable() {
        String fName = "[isAvailable]";
        try {
            logger.info(fName+"value="+available);
            return available;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
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
    public User getAuthor() {
        String fName = "[getAuthor]";
        try {
            logger.info(fName+"value="+author.getId());
            return author;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getAuthorId() {
        String fName = "[getAuthorId]";
        try {
            logger.info(fName+"value="+author_id);
            if(author_id==null)return "";
            return author_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean hasAuthor() {
        String fName = "[getAuthorId]";
        try {
            if(getAuthor()!=null){
                logger.info(fName + "has user>true");
                return true;
            }
            String tmp=getAuthorId();
            if(tmp!=null||!tmp.isBlank()){
                logger.info(fName + "has id>true");
                return true;
            }
            logger.info(fName + "false");
           return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected JSONObject getAuthorJson() {
        String fName = "getAuthorJson";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.keyId,getAuthorId());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public long getAuthorIdAsLong() {
        String fName = "getAuthorIdAsLong";
        try {
            logger.info(fName+"value="+author_id);
            return lsStringUsefullFunctions.String2Long(author_id);
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
            jsonObject.put(KEYS.keyId,getId());
            jsonObject.put(KEYS.keyName,name);
            jsonObject.put(KEYS.keyDescription,getDescription());
            jsonObject.put(KEYS.keyGuildId,getGuildId());
            jsonObject.put(KEYS.keyType,getType());
            jsonObject.put(KEYS.keyType+"Name",getStickerType().getName());
            jsonObject.put(KEYS.keyFormateType,getFormatTypeAsInt());
            jsonObject.put(KEYS.keyFormateType+"Name",getFormatType());
            jsonObject.put(KEYS.keyTags,getTagsAsJson());
            jsonObject.put(KEYS.keyuser,getAuthorJson());
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return  jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public lcTempZipFile getZip_oldV1() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            lcTempZipFile zipFile=new lcTempZipFile();
            try {
                zipFile.addEntity("sticker.json",getJSON());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                switch (getFormatType()){
                    case LOTTIE:
                        InputStream inputStream_Lottie=getLottieStream();
                        if(inputStream_Lottie!=null){
                            zipFile.addEntity(id+".lottie",inputStream_Lottie);
                            inputStream_Lottie.close();
                        }
                        break;
                    case PNG: case APNG:
                        InputStream inputStream32=getMediaStream32();
                        InputStream inputStream64=getMediaStream64();
                        InputStream inputStream128=getMediaStream128();
                        InputStream inputStream256=getMediaStream256();
                        InputStream inputStream512=getMediaStream512();
                        InputStream inputStream1024=getMediaStream1024();
                        String ext=getExtension();
                        if(inputStream32!=null){
                            zipFile.addEntity(id+"_32."+ext,inputStream32);
                        }
                        if(inputStream64!=null){
                            zipFile.addEntity(id+"_64."+ext,inputStream64);
                        }
                        if(inputStream128!=null){
                            zipFile.addEntity(id+"_128."+ext,inputStream128);
                        }
                        if(inputStream256!=null){
                            zipFile.addEntity(id+"_256."+ext,inputStream256);
                        }
                        if(inputStream512!=null){
                            zipFile.addEntity(id+"_512."+ext,inputStream512);
                        }
                        if(inputStream1024!=null){
                            zipFile.addEntity(id+"_1024."+ext,inputStream1024);
                        }
                        inputStream32.close(); inputStream64.close(); inputStream128.close();
                        inputStream256.close(); inputStream512.close(); inputStream1024.close();
                        break;
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
    public lcTempZipFile getZip() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            lcTempZipFile zipFile=new lcTempZipFile();
            try {
                zipFile.addEntity("sticker.json",getJSON());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                switch (getFormatType()){
                    case LOTTIE:
                        InputStream inputStream_Lottie=getLottieStream();
                        if(inputStream_Lottie!=null){
                            zipFile.addEntity(id+".lottie",inputStream_Lottie);
                            inputStream_Lottie.close();
                        }
                        break;
                    case PNG: case APNG:
                        String firstFileName="";
                        InputStream inputStream32=getMediaStream32();
                        InputStream inputStream64=getMediaStream64();
                        InputStream inputStream128=getMediaStream128();
                        InputStream inputStream256=getMediaStream256();
                        InputStream inputStream512=getMediaStream512();
                        InputStream inputStream1024=getMediaStream1024();
                        if(getFormatType()== MessageSticker.StickerFormat.APNG){
                            firstFileName="a_";
                        }
                        String ext="png";
                        if(inputStream32!=null){
                            zipFile.addEntity(firstFileName+id+"_32."+ext,inputStream32);
                        }
                        if(inputStream64!=null){
                            zipFile.addEntity(firstFileName+id+"_64."+ext,inputStream64);
                        }
                        if(inputStream128!=null){
                            zipFile.addEntity(firstFileName+id+"_128."+ext,inputStream128);
                        }
                        if(inputStream256!=null){
                            zipFile.addEntity(firstFileName+id+"_256."+ext,inputStream256);
                        }
                        if(inputStream512!=null){
                            zipFile.addEntity(firstFileName+id+"_512."+ext,inputStream512);
                        }
                        if(inputStream1024!=null){
                            zipFile.addEntity(firstFileName+id+"_1024."+ext,inputStream1024);
                        }
                        inputStream32.close(); inputStream64.close(); inputStream128.close();
                        inputStream256.close(); inputStream512.close(); inputStream1024.close();
                        break;
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
            lcTempZipFile zipFile=getZip();
            InputStream targetStream = zipFile.getInputStream();
            return targetStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    lcGuildStickerPack stickerPack=null;
    public lcGuildStickerPack getStickerPack() {
        String fName = "[getStickerPack]";
        try {
            if(guild!=null&&stickerPack==null){
                stickerPack=new lcGuildStickerPack(guild);
            }
            return stickerPack;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isEdited() {
        String fName = "[isEdited]";
        try {
            if(tmpDesc!=null&&!tmpDesc.isBlank()){
                logger.info("desc is edited");
                return true;
            }
            if(tmpName!=null&&!tmpName.isBlank()){
                logger.info("name is edited");
                return true;
            }
            if(tmpTag!=null&&!tmpTag.isBlank()){
                logger.info("tag is edited");
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public lcGuildSticker clear() {
        String fName = "[clear]";
        try {
            tmpDesc="";tmpName="";tmpTag="";
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static int LowCharLimit=2,MaxCharLimitName=30,MaxCharLimitDes=100,MaxCharLimitTag=200;
    public lcGuildSticker setName(String str) {
        String fName = "[setName]";
        try {
            if(str==null){
                logger.warn("this cant be null");
                return null;
            }
            str=str.replaceAll(" ","");
            if(str.isBlank()){
                logger.warn("this cant be blank");
                return null;
            }
            if(str.length()<LowCharLimit){
                logger.warn("string too small");
                return null;
            }
            if(str.length()>MaxCharLimitName){
                logger.warn("string too big");
                return null;
            }
            tmpName=str;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildSticker setDescription(String str) {
        String fName = "[setDescription]";
        try {
            if(str==null){
                logger.warn("this cant be null");
                return null;
            }
            str=str.replaceAll(" ","");
            if(str.length()<LowCharLimit){
                logger.warn("string too small");
                return null;
            }
            if(str.length()>MaxCharLimitDes){
                logger.warn("string too big");
                return null;
            }
            tmpDesc=str;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildSticker setTag(String str) {
        String fName = "[setTag]";
        try {
            if(str==null){
                logger.warn("this cant be null");
                return null;
            }
            str=str.replaceAll(" ","");
            if(str.length()<LowCharLimit){
                logger.warn("string too small");
                return null;
            }
            if(str.length()>MaxCharLimitTag){
                logger.warn("string too big");
                return null;
            }
            tmpTag=str;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public HttpResponse<JsonNode> update() {
        String fName = "[update]";
        try {
            if(!isEdited()){
                logger.warn("not edited");
                return new FailedResponse<>(new Exception("Not edited"));
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(KEYS.keyName,getName());
            jsonObject.put(KEYS.keyDescription,getDescription());
            jsonObject.put(KEYS.keyTags,getTag());
            if(tags.isEmpty()){
                tags.add(getTag());
            }else{
                tags.set(0,getTag());
            }
            logger.info("jsonObject="+jsonObject.toString());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetGuildSticker;
            url=url.replaceAll("!GUILD", getGuildId()).replaceAll("!STICKER", getId());
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.patch(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .body(jsonObject)
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            if(response.getStatus()>299){
                logger.warn("invalid status="+response.getStatus());
                return response;
            }
            logger.warn("success update");
            clear();
            /*name=jsonObject.optString(KEYS.keyName);
            description=jsonObject.optString(KEYS.keyDescription);
            tags=jsonObject.optString(KEYS.keyTags);*/
            setSticker(jsonObject);
            return response;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }
    public HttpResponse<JsonNode> delete() {
        String fName = "[delete]";
        try {
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetGuildSticker;
            url=url.replaceAll("!GUILD", getGuildId()).replaceAll("!STICKER", getId());
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.delete(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            if(response.getStatus()>299){
                logger.warn("invalid status="+response.getStatus());
                return response;
            }
            logger.warn("success delete");
            this.id=""; this.guild_id=""; this.author_id ="";
            this.guild=null;
            this.author=null;
            this.name="";this.description="";this.tags=new ArrayList<>();
            this.type=0;this.format_type=0;
            this.available=false;this.empty=false;
            return response;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new FailedResponse<>(e);
        }
    }

}
