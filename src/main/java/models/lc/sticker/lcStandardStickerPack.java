package models.lc.sticker;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.ll.llCommonKeys;
import models.ls.lsDiscordApi;
import models.ls.lsStringUsefullFunctions;
import net.dv8tion.jda.api.entities.MessageSticker;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class lcStandardStickerPack {
    Logger logger = Logger.getLogger(getClass());
    List<lcStandardSticker> stickers=new ArrayList<>();
    String name="",sku_id="",cover_sticker_id="",description="",banner_asset_id="",id="";
    private lcStandardStickerPacks stickerPacks=null;
    public lcStandardStickerPack(JSONObject jsonObject) {
        String fName = "build";
        try {
            setPack(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcStandardStickerPack(JSONObject jsonObject, lcStandardStickerPacks stickerPacks) {
        String fName = "build";
        try {
            setPack(jsonObject);
            this.stickerPacks=stickerPacks;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    public lcStandardStickerPack() {

    }

    public lcStandardStickerPacks getStickerPacks() {
        String fName = "[getStickerPacks]";
        try {
            return stickerPacks;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private boolean setPack(JSONObject jsonObject) {
        String fName = "[set]";
        try {
            logger.info(fName+"jsonObject="+jsonObject.toString());
            Iterator<String> keys=jsonObject.keys();
            while(keys.hasNext()){
                try {
                    String key=keys.next();
                    switch (key){
                        case llCommonKeys.StickerPackObject.Stickers:
                            setStickers(jsonObject.getJSONArray(key));
                            break;
                        case llCommonKeys.StickerPackObject.Id:
                            id=jsonObject.getString(key);
                            break;
                        case llCommonKeys.StickerPackObject.Name:
                            name=jsonObject.getString(key);
                            break;
                        case llCommonKeys.StickerPackObject.SkuId:
                            sku_id=jsonObject.getString(key);
                            break;
                        case llCommonKeys.StickerPackObject.Description:
                            description=jsonObject.getString(key);
                            break;
                        case llCommonKeys.StickerPackObject.BannerAssetId:
                            banner_asset_id=jsonObject.getString(key);
                            break;
                        case llCommonKeys.StickerPackObject.CoverStickerId:
                            cover_sticker_id=jsonObject.getString(key);
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
    private boolean setStickers(JSONArray jsonArray) {
        String fName = "[setStickers]";
        try {
            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                lcStandardSticker sticker=new lcStandardSticker(jsonArray.optJSONObject(i));
                stickers.add(sticker);
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<lcStandardSticker> getStickers() {
        String fName = "[getStickers]";
        try {
            logger.info(fName+"all");
            return stickers;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcStandardSticker getSticker(int index) {
        String fName = "[getSticker]";
        try {
            logger.info(fName+"index="+index);
            return stickers.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getId() {
        String fName = "[getId]";
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
    public String getDescription() {
        String fName = "[getDescription]";
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
    public String getName() {
        String fName = "[getName]";
        try {
            logger.info(fName+"value="+name);
            if(name==null)return "";
            return name;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getCoverStickerId() {
        String fName = "[getCoverStickerId]";
        try {
            logger.info(fName+"value="+cover_sticker_id);
            if(cover_sticker_id==null)return "";
            return cover_sticker_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getBannerAssetId() {
        String fName = "[getBannerAssetId]";
        try {
            logger.info(fName+"value="+banner_asset_id);
            if(banner_asset_id==null)return "";
            return banner_asset_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getBannerUrl() {
        String fName = "[getBannerUrl]";
        try {
            String url= lsDiscordApi.liStickerBannerUrl.replaceAll("!PACKBANNERASSETID", getBannerAssetId());
            logger.info(fName+"url="+url);
            return url;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getCoverStickerUrl() {
        String fName = "[getCoverStickerUrl]";
        try {
            String url= lsDiscordApi.liStickerMediaUrl.replaceAll("!STICKER", getCoverStickerId());
            logger.info(fName+"url="+url);
            return url;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public lcStandardSticker getCoverSticker() {
        String fName = "[getCoverSticker]";
        try {
            for(lcStandardSticker sticker:stickers){
                if(sticker.getIdAsLong()==getCoverStickerIdAsLong()){
                    logger.info(fName+"found");
                    return sticker;
                }
            }
            logger.info(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getSkuId() {
        String fName = "[getSkuId]";
        try {
            logger.info(fName+"value="+sku_id);
            if(sku_id==null)return "";
            return sku_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getIdAsLong() {
        String fName = "[getIdAsLong]";
        try {
            logger.info(fName+"value="+id);
            return lsStringUsefullFunctions.String2Long(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getCoverStickerIdAsLong() {
        String fName = "[getCoverStickerIdAsLong]";
        try {
            logger.info(fName+"value="+cover_sticker_id);
            return lsStringUsefullFunctions.String2Long(cover_sticker_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getBannerAssetIdAsLong() {
        String fName = "[getBannerAssetIdAsLong]";
        try {
            logger.info(fName+"value="+banner_asset_id);
            return lsStringUsefullFunctions.String2Long(banner_asset_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getSkuIdAsLong() {
        String fName = "[getSkuIdAsLong]";
        try {
            logger.info(fName+"value="+sku_id);
            return lsStringUsefullFunctions.String2Long(sku_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONObject getJson() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.StickerPackObject.Id,getId());
            jsonObject.put(llCommonKeys.StickerPackObject.Name,getName());
            jsonObject.put(llCommonKeys.StickerPackObject.Description,getDescription());
            jsonObject.put(llCommonKeys.StickerPackObject.CoverStickerId,getCoverStickerId());
            jsonObject.put(llCommonKeys.StickerPackObject.BannerAssetId,getBannerAssetId());
            JSONArray jsonArray=new JSONArray();
            for(int i=0;i<stickers.size();i++){
                try {
                    lcStandardSticker sticker= stickers.get(i);
                    logger.info(fName+"stickers["+i+"]:id="+sticker.getId());
                    jsonArray.put(sticker.getJSON());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            jsonObject.put(llCommonKeys.StickerPackObject.Stickers,jsonArray);
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
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.StickerPackObject.Id,getId());
                jsonObject.put(llCommonKeys.StickerPackObject.Name,getName());
                jsonObject.put(llCommonKeys.StickerPackObject.Description,getDescription());
                jsonObject.put(llCommonKeys.StickerPackObject.CoverStickerId,getCoverStickerId());
                jsonObject.put(llCommonKeys.StickerPackObject.BannerAssetId,getBannerAssetId());
                logger.info(fName+"pack="+jsonObject.toString());
                zipFile.addEntity("pack.json",jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(int i=0;i<stickers.size();i++){
                try {
                    lcStandardSticker sticker= stickers.get(i);
                    logger.info(fName+"stickers["+i+"]:id="+sticker.getId());
                    zipFile.addEntity(sticker.getName()+".json",sticker.getJSON());
                    InputStream inputStream=sticker.getStream();
                    if(inputStream!=null){
                        if(sticker.isImg()){
                            zipFile.addEntity(name+".png",inputStream);
                        }else{
                            zipFile.addEntity(name+".lottie",inputStream);
                        }
                        inputStream.close();
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
    public lcTempZipFile getZip() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            lcTempZipFile zipFile=new lcTempZipFile();
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(llCommonKeys.StickerPackObject.Id,getId());
                jsonObject.put(llCommonKeys.StickerPackObject.Name,getName());
                jsonObject.put(llCommonKeys.StickerPackObject.Description,getDescription());
                jsonObject.put(llCommonKeys.StickerPackObject.CoverStickerId,getCoverStickerId());
                jsonObject.put(llCommonKeys.StickerPackObject.BannerAssetId,getBannerAssetId());
                logger.info(fName+"pack="+jsonObject.toString());
                zipFile.addEntity("pack.json",jsonObject);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            for(int i=0;i<stickers.size();i++){
                try {
                    lcStandardSticker sticker= stickers.get(i);
                    logger.info(fName+"stickers["+i+"]:id="+sticker.getId());
                    zipFile.addEntity(sticker.getName()+".json",sticker.getJSON());
                    switch (sticker.getFormatType()){
                        case LOTTIE:
                            InputStream inputStream_Lottie=sticker.getLottieStream();
                            if(inputStream_Lottie!=null){
                                zipFile.addEntity(id+".lottie",inputStream_Lottie);
                                inputStream_Lottie.close();
                            }
                            break;
                        case PNG: case APNG:
                            String firstFileName="";
                            InputStream inputStream32=sticker.getMediaStream32();
                            InputStream inputStream64=sticker.getMediaStream64();
                            InputStream inputStream128=sticker.getMediaStream128();
                            InputStream inputStream256=sticker.getMediaStream256();
                            InputStream inputStream512=sticker.getMediaStream512();
                            InputStream inputStream1024=sticker.getMediaStream1024();
                            if(sticker.getFormatType()== MessageSticker.StickerFormat.APNG){
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

    public int size() {
        String fName = "[size]";
        try {
            int size=stickers.size();
            logger.info(fName+"value="+size);
            return size;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmpty() {
        String fName = "[isEmpty]";
        try {
            boolean result=stickers.isEmpty();
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public int countOfPng() {
        String fName = "[countOfPng]";
        try {
            int c=0;
            for(lcStandardSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.PNG)c++;
            }
            logger.info(fName + ".c=" + c);
            return c;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int countOfAPng() {
        String fName = "[countOfPng]";
        try {
            int c=0;
            for(lcStandardSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.APNG)c++;
            }
            logger.info(fName + ".c=" + c);
            return c;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int countOfLottie() {
        String fName = "[countOfPng]";
        try {
            int c=0;
            for(lcStandardSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.LOTTIE)c++;
            }
            logger.info(fName + ".c=" + c);
            return c;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
}
