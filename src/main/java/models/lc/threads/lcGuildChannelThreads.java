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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.*;

public class lcGuildChannelThreads {
    Logger logger = Logger.getLogger(getClass());
    protected String id="", guild_id="", author_id ="";
    protected Guild guild=null;
    protected User author=null;
    protected String name="",description="",asset="",tags="";
    protected int type=0,format_type=0;
    protected boolean available=false, empty=false;

    public lcGuildChannelThreads() {

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
    public lcGuildChannelThreads(JSONObject jsonObject, Guild guild) {
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
    public lcGuildChannelThreads(JSONObject jsonObject, Guild guild, User author) {
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
    public lcGuildChannelThreads(JSONObject jsonObject) {
        String fName = "build";
        try {
            setSticker(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildChannelThreads(JSONObject jsonObject, List<JDA>jdas) {
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
    public lcGuildChannelThreads(long id, List<JDA>jdas) {
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
    public lcGuildChannelThreads(long id) {
        String fName = "build";
        try {
            getStickerHttp(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildChannelThreads(Message message, List<JDA>jdas) {
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
    public lcGuildChannelThreads(Message message) {
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
                        case KEYS.keyAsset:
                            asset=jsonObject.optString(key);
                            break;
                        case KEYS.keyType:
                            type=jsonObject.optInt(key);
                            break;
                        case KEYS.keyFormateType:
                            format_type=jsonObject.optInt(key);
                            break;
                        case KEYS.keyTags:
                            tags=jsonObject.optString(key);
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
    public boolean isEmpty() {
        String fName = "[isEmpty]";
        try {
            return id==null||id.isBlank();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
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
    public String getAsset() {
        String fName = "getAsset";
        try {
            logger.info(fName+"value="+asset);
            if(asset==null)return "";
            return  asset;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getLottieUrl() {
        String fName = "[getLottieUrl]";
        try {
            logger.info(fName+"asset="+asset+", sticker="+id);
            String url="";
            if(!isImg()){
                url=liStickerLottieUrl.replaceAll("!STICKER",id);
            }
            logger.info(fName+"url="+url);
            return  url;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl() {
        String fName = "[getMediaUrl]";
        try {
            logger.info(fName+"asset="+asset+", sticker="+id);
            String url="";
            if(isImg()){
                url= liStickerMediaUrl.replaceAll("!STICKER",id);
            }
            logger.info(fName+"url="+url);
            return  url;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public InputStream getStream() {
        String fName = "[getStream]";
        try {
            String url="";
            if(isImg()){
                url=getMediaUrl();
            }else{
                url=getLottieUrl();
            }
            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
            return  inputStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
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
            if(tags==null) return "";
            return  tags;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public JSONArray getTagsAsJson() {
        String fName = "[getTagsAsJson]";
        try {
            JSONArray jsonArray=new JSONArray();
            logger.info(fName+"value="+tags);
            jsonArray.put(tags);
           return  jsonArray;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public List<String>getTagsAsList() {
        String fName = "[getTags]";
        try {
            List<String>list=new ArrayList<>();
            logger.info(fName+"value="+tags);
            list.add(tags);
            return list;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public Set<String>getTagsAsSet() {
        String fName = "[getTagsAsSet]";
        try {
            logger.info(fName+"value="+tags);
            Set<String> set = new HashSet<>(getTagsAsList());
            return set;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new HashSet<>();
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
            jsonObject.put(KEYS.keyAsset,getAsset());
            jsonObject.put(KEYS.keyType,getType());
            jsonObject.put(KEYS.keyFormateType,getFormatType());
            jsonObject.put(KEYS.keyTags,getTag());
            jsonObject.put(KEYS.keyuser,getAuthorJson());
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return  jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
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
                String url="",ext="";
                if(getType()==1||getType()==2){
                    url=getMediaUrl();ext="png";
                }else{
                    url=getLottieUrl();ext="lottie";
                }
                String name=getName();
                InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
                zipFile.addEntity(name+"."+ext,inputStream);
                inputStream.close();
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
    public lcGuildStickerPack getStickerPack() {
        String fName = "[getStickerPack]";
        try {
            lcGuildStickerPack stickerPack=null;

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
    public lcGuildChannelThreads clear() {
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
    public lcGuildChannelThreads setName(String str) {
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
    public lcGuildChannelThreads setDescription(String str) {
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
    public lcGuildChannelThreads setTag(String str) {
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
            this.name="";this.description="";this.asset="";this.tags="";
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
