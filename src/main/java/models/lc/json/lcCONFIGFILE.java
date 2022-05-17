package models.lc.json;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class lcCONFIGFILE {
    Logger logger = Logger.getLogger(getClass());
    public lcCONFIGFILE(){}
    lcText2Json text2Json=new lcText2Json();
    String configFilePath="resources/json/config.json";
    public boolean initConfig(){
        String fName="[initConfig]";
        try {
            if(!readFile()){
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
    public JSONObject getJsonObject(){
        String fName="[getJsonObject]";
        try {
           return  text2Json.jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJsonObjectAsJsonObject(String key){
        String fName="[getJsonObjectAsJsonObject]";
        try {
            logger.info(fName+".key="+key);
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty"); return null;
            }
            if(text2Json.jsonObject.has(key)){
                logger.warn(fName+".has not"); return null;
            }
            if(text2Json.jsonObject.isNull(key)){
                logger.warn(fName+".isNull"); return null;
            }
            JSONObject jsonObject=text2Json.jsonObject.getJSONObject(key);
            logger.info(fName+".jsonObject="+jsonObject.toString());
            return  jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean readFile(){
        String fName="[readFile]";
        try {
            File file1;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+configFilePath);
                file1=new File(configFilePath);
                if(file1.exists()){
                    logger.info(fName+".file1 exists");
                    fileStream = new FileInputStream(file1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            if(fileStream!=null){
                if(!text2Json.isInputStream2Json(fileStream)){
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
    public boolean getProperties(){
        String fName="[getProperties]";
        try {
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty");return false;
            }
            logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
            String key="";
            key="bot";if( text2Json.jsonObject.has(key)&&!text2Json.jsonObject.isNull(key)){
                JSONObject jsonBot=text2Json.jsonObject.optJSONObject(key);
                //key= llCommonKeys.BotConfig.token;if( jsonBot.has(key)&&!jsonBot.isNull(key))gBotToken=jsonBot.optString(key,"");
                //key= llCommonKeys.BotConfig.id;if( jsonBot.has(key)&&!jsonBot.isNull(key))gBotId=jsonBot.optString(key,"");
                logger.info(fName + ".return true");
                return  true;
            }
            logger.info(fName + ".return false");
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
