package models.lc.discordentities;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface liCommonFunctions {
    default int getInt(JSONObject jsonObject,String key) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[getInt]";
        try {
            logger.info(fName + ".key="+key);
            if(!jsonObject.has(key)){
                logger.warn(fName + ".has no "+key);
                return  -1;
            }
            int value=jsonObject.getInt(key);
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    default String getString(JSONObject jsonObject,String key) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[getString]";
        try {
            logger.info(fName + ".key="+key);
            if(!jsonObject.has(key)){
                logger.warn(fName + ".has no "+key);
                return  null;
            }
            String value=jsonObject.getString(key);
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    default JSONObject getObjectJson(JSONObject jsonObject,String key) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[getObjectJson]";
        try {
            if(!jsonObject.has(key)){
                logger.warn(fName + ".has no "+key);
                return new JSONObject();
            }
            return jsonObject.getJSONObject(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    default JSONArray getArrayJson(JSONObject jsonObject,String key) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[getArrayJson]";
        try {
            if(!jsonObject.has(key)){
                logger.warn(fName + ".has no "+key);
                return new JSONArray();
            }
            return jsonObject.getJSONArray(key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    default int arrayLength(JSONArray jsonArray) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[arrayLength]";
        try {
            if(jsonArray==null){
                logger.warn(fName + "jsonArray is null>0");
                return 0;
            }
            if(jsonArray.isEmpty()){
                logger.warn(fName + "jsonArray is empty>0");
                return 0;
            }
            int l=jsonArray.length();
            logger.error(fName + "default>"+l);
            return l;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    default boolean getBoolean(JSONObject jsonObject,String key) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[getBoolean]";
        try {
            logger.info(fName + ".key-"+key);
            if(!jsonObject.has(key)){
                logger.warn(fName + ".has no "+key);
                return false;
            }
            boolean value=jsonObject.getBoolean(key);
            logger.info(fName + ".value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default List<String> jsonArray2ListString(JSONArray jsonArray) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[jsonArray2ListString]";
        try {
            List<String>list=new ArrayList<>();
            if(jsonArray==null){
                logger.warn(fName + "jsonArray is null>0");
                return list;
            }
            if(jsonArray.isEmpty()){
                logger.warn(fName + "jsonArray is empty>0");
                return list;
            }
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    default boolean isArrayEmpty(JSONArray jsonArray) {
        Logger logger = Logger.getLogger(getClass());
        String fName="[isArrayEmpty]";
        try {
            if(jsonArray==null){
                logger.error(fName + "jsonArray is null>true");
                return true;
            }
            if(jsonArray.isEmpty()){
                logger.error(fName + "jsonArray is empty>true");
                return true;
            }
            logger.error(fName + "default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
}
