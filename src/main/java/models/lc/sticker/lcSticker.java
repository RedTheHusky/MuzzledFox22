package models.lc.sticker;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.ll.llCommonKeys;
import models.ls.lsStreamHelper;
import models.ls.lsStringUsefullFunctions;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageSticker;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.*;

public class lcSticker {
    Logger logger = Logger.getLogger(getClass());
    protected String id="";
    protected String name="",description="";
    protected List<String>tags=new ArrayList<>();
    protected int type=0,format_type=0,sort_value=0;
    protected boolean  empty=false;
    protected JSONObject jsonUser=new JSONObject();
    protected  boolean available=false;
    protected  JDA jda;
    protected  List<JDA>jdas;
    protected  Guild guild;
    public lcSticker() {

    }
    public lcSticker(long id,JDA jda) {
        String fName = "build";
        try {
            this.jda=jda;
            getStickerHttp(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSticker(String id,JDA jda) {
        String fName = "build";
        try {
            this.jda=jda;
            getStickerHttp(Long.parseLong(id));
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSticker(JSONObject jsonObject, JDA jda) {
        String fName = "build";
        try {
            this.jda=jda;
            setSticker(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSticker(long id,List<JDA>jdas) {
        String fName = "build";
        try {
            this.jdas=jdas;
            getStickerHttp(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSticker(String id,List<JDA>jdas) {
        String fName = "build";
        try {
            this.jdas=jdas;
            getStickerHttp(Long.parseLong(id));
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSticker(JSONObject jsonObject, List<JDA>jdas) {
        String fName = "build";
        try {
            this.jdas=jdas;
            setSticker(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    private boolean setSticker(JSONObject jsonObject) {
        String fName = "[setSticker]";
        try {

            logger.info(fName+"jsonObject="+jsonObject.toString());
            Iterator<String> keys=jsonObject.keys();
            while(keys.hasNext()){
                try {
                    String key=keys.next();
                    switch (key){
                        case llCommonKeys.StickerStructure.Id:
                            id=jsonObject.optString(key);
                            break;
                        case llCommonKeys.StickerStructure.Name:
                            name=jsonObject.optString(key);
                            break;
                        case llCommonKeys.StickerStructure.Description:
                            description=jsonObject.optString(key);
                            break;
                        case llCommonKeys.StickerStructure.Type:
                            type=jsonObject.optInt(key);
                            break;
                        case llCommonKeys.StickerStructure.FormatType:
                            format_type=jsonObject.optInt(key);
                            break;
                        case llCommonKeys.StickerStructure.Tags:
                            setTags(jsonObject.optString(llCommonKeys.StickerStructure.Tags));
                            break;
                        case llCommonKeys.StickerStructure.Available:
                            available=jsonObject.optBoolean(key);
                            break;
                        case llCommonKeys.StickerStructure.User:
                            jsonUser=jsonObject.optJSONObject(key);
                            break;
                        case llCommonKeys.StickerStructure.SortValue:
                            sort_value=jsonObject.optInt(key);
                            break;
                    }
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            if(type==1){
                standardSticker=new lcStandardSticker(jsonObject);
            }
            if(type==2){
                if(jdas!=null)guildSticker=new lcGuildSticker(jsonObject,jdas);
                else if(jda!=null)guildSticker=new lcGuildSticker(jsonObject,jda.getShardManager().getShards());
                else guildSticker=new lcGuildSticker(jsonObject);
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
                logger.warn("invalid status="+response.getStatusText());
                logger.warn("body="+response.getBody().getObject().toString());
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
    public boolean isTypeStandard() {
        String fName = "[isTypeStandard]";
        try {
            boolean result=getType()==1;
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isTypeGuild() {
        String fName = "[isTypeGuild]";
        try {
            boolean result=getType()==2;
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isFromGuild() {
        String fName = "[isFromGuild]";
        try {
            boolean result=getType()==2;
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public StickerTypes getStickerType() {
        String fName = "[getStickerType]";
        try {
            StickerTypes type=StickerTypes.valueByCode(getType());
            if(type==null)type=StickerTypes.Invalid;
            logger.info(fName+"type="+type.getName());
            return type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return StickerTypes.Invalid;
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
            if(description==null)return "";
            return description;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getLottieUrl() {
        String fName = "[getLottieUrl]";
        try {
            logger.info(fName+"sticker="+id);
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
            logger.info(fName+"sticker="+id);
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
    public String getMediaUrl4096() {
        String fName = "[getMediaUrl4096]";
        try {
            return getMediaUrl(4096);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl2048() {
        String fName = "[getMediaUrl2048]";
        try {
            return getMediaUrl(2048);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl1024() {
        String fName = "[getMediaUrl1024]";
        try {
            return getMediaUrl(1024);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl512() {
        String fName = "[getMediaUrl512]";
        try {
            return getMediaUrl(512);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl256() {
        String fName = "[getMediaUrl256]";
        try {
            return getMediaUrl(256);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl128() {
        String fName = "[getMediaUrl128]";
        try {
            return getMediaUrl(128);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl64() {
        String fName = "[getMediaUrl64]";
        try {
            return getMediaUrl(64);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl32() {
        String fName = "[getMediaUrl32]";
        try {
            return getMediaUrl(32);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getMediaUrl(int size) {
        String fName = "[getMediaUrl]";
        try {
            logger.info(fName+"sticker="+id+", size="+size);
            String url="";
            if(isImg()){
                url= liStickerMediaUrl.replaceAll("!STICKER",id)+"?size="+size;
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
    public InputStream getLottieStream() {
        String fName = "[getLottieStream]";
        try {
            String url=getLottieUrl();
            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
            return  inputStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream() {
        String fName = "[getMediaStream]";
        try {
            String url=getMediaUrl();
            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
            return  inputStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream32() {
        String fName = "[getMediaStream32]";
        try {
            return  getMediaStream(32);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream64() {
        String fName = "[getMediaStream32]";
        try {
            return  getMediaStream(64);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream128() {
        String fName = "[getMediaStream128]";
        try {
            return  getMediaStream(128);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream256() {
        String fName = "[getMediaStream256]";
        try {
            return  getMediaStream(256);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream512() {
        String fName = "[getMediaStream512]";
        try {
            return  getMediaStream(512);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream1024() {
        String fName = "[getMediaStream1024]";
        try {
            return  getMediaStream(1024);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getMediaStream(int size) {
        String fName = "[getMediaStream]";
        try {
            String url=getMediaUrl(size);
            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
            return  inputStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
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
            logger.info(fName+"value="+tags.size());
            return tags;
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
    public String getExtension() {
        String fName = "[getExtension]";
        try {
            String result="";
            switch (getFormatType()){
                case LOTTIE:
                    result="lottie";
                case PNG:
                    result="png";
                    break;
                case APNG:
                    result="apng";
                    break;
            }
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    public JSONObject getJSON() {
        String fName = "[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(lcGuildSticker.KEYS.keyId,getId());
            jsonObject.put(lcGuildSticker.KEYS.keyName,name);
            jsonObject.put(lcGuildSticker.KEYS.keyDescription,getDescription());
            jsonObject.put(lcGuildSticker.KEYS.keyType,getType());
            jsonObject.put(lcGuildSticker.KEYS.keyType+"Name",getStickerType().getName());
            jsonObject.put(lcGuildSticker.KEYS.keyFormateType,getFormatTypeAsInt());
            jsonObject.put(lcGuildSticker.KEYS.keyFormateType+"Name",getFormatType());
            jsonObject.put(lcGuildSticker.KEYS.keyTags,getTagsAsJson());
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

    lcGuildSticker guildSticker=null;
    lcStandardSticker standardSticker=null;
    public lcStandardSticker getNitroSticker() {
        String fName = "[getNitroSticker]";
        try {
            return standardSticker;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildSticker getGuildSticker() {
        String fName = "[getGuildSticker]";
        try {
            return guildSticker;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }



}
