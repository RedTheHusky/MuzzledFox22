package models.lc.sticker;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import org.apache.log4j.Logger;

import java.util.*;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.*;

public class lcStandardStickerPacks {
    Logger logger = Logger.getLogger(getClass());


    List<lcStandardStickerPack> packs=new ArrayList<>();
    public lcStandardStickerPacks(boolean get) {
        String fName = "build";
        try {
            if(get)getHttps();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcStandardStickerPacks() {
        String fName = "build";
        try {
            getHttps();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean getHttps() {
        String fName = "[getHttps]";
        try {
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetStickerPacks;
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            if(response.getStatus()<200||response.getStatus()>299){
                logger.warn(fName+"response.statustext="+response.getStatusText());
                logger.warn(fName+"response.body="+response.getBody().getObject());
                return false;
            }
            return set(response.getBody().getObject());
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcStandardStickerPacks(JSONObject jsonObject) {
        String fName = "build";
        try {
            set(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private boolean set(JSONObject jsonObject) {
        String fName = "[set]";
        try {
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(jsonObject.has(llCommonKeys.NitroStickerPacks.StickerPacks)){
                setPackages(jsonObject.getJSONArray(llCommonKeys.NitroStickerPacks.StickerPacks));
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setPackages(JSONArray jsonArray) {
        String fName = "[setPackages]";
        try {
            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                packs.add(new lcStandardStickerPack(jsonArray.getJSONObject(i),this));
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcStandardStickerPack getStickerPackAt(int index) {
        String fName = "[getStickerPackAt]";
        try {
            logger.info(fName+"index="+index);
            return packs.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcStandardStickerPack getStickerPack(long id) {
        String fName = "[getStickerPack]";
        try {
            logger.info(fName+"id="+id);
            int i=0;
            for(lcStandardStickerPack pack:packs){
                logger.info(fName+"pack["+i+"].id="+pack.getIdAsLong());
                //logger.info(fName+"pack["+i+"].json="+pack.getJson());
                if(pack.getIdAsLong()==id){
                    logger.info(fName+"found");
                    return pack;
                }
                i++;
            }
            logger.warn(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcStandardStickerPack getStickerPack(String id) {
        String fName = "[getStickerPack]";
        try {
            if(id==null)id="";
            logger.info(fName+"id="+id);
            for(lcStandardStickerPack pack:packs){
                if(id.equalsIgnoreCase(pack.getId())){
                    logger.info(fName+"found");
                    return pack;
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
    public List<lcStandardStickerPack> getStickerPacks() {
        String fName = "[getStickerPacks]";
        try {
            return packs;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int size() {
        String fName = "[size]";
        try {
            return packs.size();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmpty() {
        String fName = "[isEmpty]";
        try {
            return packs.isEmpty();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }


}
