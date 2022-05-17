package models.lc.sticker;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.ll.llCommonKeys;
import models.ls.lsStreamHelper;
import models.ls.lsStringUsefullFunctions;
import net.dv8tion.jda.api.entities.MessageSticker;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.*;

import static models.ls.lsDiscordApi.*;

public class lcStandardSticker extends lcSticker{
    Logger logger = Logger.getLogger(getClass());
    protected int sort_value=0;
    protected String pack_id="";
    public lcStandardSticker() {

    }
    public lcStandardSticker(JSONObject jsonObject) {
        String fName = "build";
        try {
            set(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcStandardSticker(MessageSticker sticker) {
        String fName = "build";
        try {
            set(sticker);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public lcStandardSticker(lcSticker sticker) {
        String fName = "build";
        try {
            set(sticker.getJSON());
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    protected boolean set(JSONObject jsonObject) {
        String fName = "[set]";
        try {

            logger.info(fName+"jsonObject="+jsonObject.toString());
            Iterator<String> keys=jsonObject.keys();
            while(keys.hasNext()){
                try {
                    String key=keys.next();
                    switch (key){
                        case llCommonKeys.StickerStructure.Id:
                            id=jsonObject.optString(key,"");
                            break;
                        case llCommonKeys.StickerStructure.Name:
                            name=jsonObject.optString(key,"");
                            break;
                        case llCommonKeys.StickerStructure.Description:
                            description=jsonObject.optString(key,"");
                            break;
                        case llCommonKeys.StickerStructure.PackId:
                            pack_id=jsonObject.optString(key,"");
                            break;
                        case llCommonKeys.StickerStructure.SortValue:
                            sort_value=jsonObject.optInt(key,-1);
                            break;
                        case llCommonKeys.StickerStructure.FormatType:
                            format_type=jsonObject.optInt(key,0);
                            break;
                        case llCommonKeys.StickerStructure.Tags:
                            setTags(jsonObject.optString(llCommonKeys.StickerStructure.Tags,"[]"));
                            break;
                    }
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected boolean set(MessageSticker sticker) {
        String fName = "[set]";
        try {
            id=sticker.getId();
            pack_id=sticker.getPackId();
            description=sticker.getDescription();
            name=sticker.getName();
            switch (sticker.getFormatType()){
                case PNG:format_type=1;
                case APNG:format_type=2;
                case LOTTIE:format_type=3;
            }
            Iterator<String>iterator=sticker.getTags().iterator();
            while (iterator.hasNext()){tags.add(iterator.next());}
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }



    public String getPackId() {
        String fName = "getPackId";
        try {
            logger.info(fName+"value="+pack_id);
            if(pack_id==null)return "";
            return pack_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getPackIdAsLong() {
        String fName = "getPackIdAsLong";
        try {
            logger.info(fName+"value="+pack_id);
            return lsStringUsefullFunctions.String2Long(pack_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONObject getJSON() {
        String fName = "getJSON";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.StickerStructure.Id,id);
            jsonObject.put(llCommonKeys.StickerStructure.Name,name);
            jsonObject.put(llCommonKeys.StickerStructure.Description,description);
            jsonObject.put(llCommonKeys.StickerStructure.PackId,pack_id);
            jsonObject.put(llCommonKeys.StickerStructure.FormatType,format_type);
            jsonObject.put(llCommonKeys.StickerStructure.Tags,getTagsAsJson());
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
                JSONObject jsonObject=getJSON();
                logger.info(fName+"sticker="+jsonObject.toString());
                zipFile.addEntity("sticker.json",jsonObject);
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
    public lcTempZipFile getZip() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            lcTempZipFile zipFile=new lcTempZipFile();
            try {
                JSONObject jsonObject=getJSON();
                logger.info(fName+"sticker="+jsonObject.toString());
                zipFile.addEntity("sticker.json",jsonObject);
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
    public lcStandardStickerPack getStickerPack() {
        String fName = "[getStickerPack]";
        try {
            lcStandardStickerPacks stickerPacks=new lcStandardStickerPacks();
            lcStandardStickerPack stickerPack=stickerPacks.getStickerPack(getPackIdAsLong());
            return stickerPack;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public MessageSticker getMessageSticker() {
        String fName = "[getMessageSticker]";
        try {
            /*
            removed since JDA.version 4.3.0+
            MessageSticker sticker=new MessageSticker(getIdAsLong(),getName(),getDescription(),getPackIdAsLong(),getAssetHash(),"",getFormat(),getTagsAsSet());
             */
            MessageSticker sticker=new MessageSticker(getIdAsLong(),getName(),getDescription(),getPackIdAsLong(),"",getFormatType(),getTagsAsSet());
            return sticker;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
