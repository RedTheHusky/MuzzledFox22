package nsfw.diaper.modules.entities;

import kong.unirest.json.JSONObject;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import nsfw.diaper.modules.interfaces.iDiaperInteractive.DIAPERTYPE;

public class entityDiaper {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityDiaper]";
    /*gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyType,typeDiaper_White);
            gUserProfile.safetyPutFieldEntry(field, keyMaxLevel,6);*/
    protected final String fieldDiaper= iDiaperInteractive.fieldDiaper,keyEnabled=iDiaperInteractive.keyEnabled,keyType=iDiaperInteractive.keyType,keyMaxLevel=iDiaperInteractive.keyMaxLevel;
    protected boolean gEnabled=false;
    protected String gType="";
    protected int gMaxLevel=0;

    public entityDiaper(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityDiaper(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityDiaper clear(){
        String fName="[clear]";
        try {
            gEnabled=false;
            gType=DIAPERTYPE.White.getString();
            gMaxLevel=6;

            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            if(jsonObject.has(fieldDiaper)){
                logger.info(fName+"has key fieldDiaper");
                jsonObject=jsonObject.getJSONObject(fieldDiaper);
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(!jsonObject.isEmpty()){
                Iterator<String> keys=jsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    put(key,jsonObject);
                }
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyEnabled,gEnabled);
            jsonObject.put(keyType,gType);
            jsonObject.put(keyMaxLevel,gMaxLevel);

            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isEnabled(){
        String fName="[isEnabled]";
        try {
            logger.info(fName+"value="+gEnabled);
            return gEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getTypeAsString(){
        String fName="[getTypeAsString]";
        try {
            logger.info(fName+"value="+gType);
            return gType;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public DIAPERTYPE getType(){
        String fName="[getTypeAsString]";
        try {
            logger.info(fName+"value="+gType);
            DIAPERTYPE type=DIAPERTYPE.valueByString(gType);
            if(type==null)type=DIAPERTYPE.INVALID;
            return type;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return DIAPERTYPE.INVALID;
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            logger.info(fName+"type="+gType);
            DIAPERTYPE diapertype= DIAPERTYPE.valueByString(gType);
            if(diapertype!=null){
                return diapertype.getName();
            }
            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getMaxLevel(){
        String fName="[getMaxLevel]";
        try {
            logger.info(fName+"value="+gMaxLevel);
            return gMaxLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public entityDiaper setEnabled(boolean input){
        String fName="[setEnabled]";
        try {
            logger.info(fName+"input="+input);
            gEnabled=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityDiaper setType(String input){
        String fName="[setType]";
        try {
            logger.info(fName+"input="+input);
            if(input==null)input="";
            gType=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityDiaper setType(DIAPERTYPE input){
        String fName="[setType]";
        try {
            if(input==null){return null;}
            logger.info(fName+"input="+input);
            setType(input.getString());
            setMaxWetness(input.getWetness());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityDiaper setMaxWetness(int input){
        String fName="[setMaxLevel]";
        try {
            logger.info(fName+"input="+input);
            gMaxLevel=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean put(String key,Object value){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyEnabled:
                    gEnabled= (boolean) value;
                    break;
                case keyType:
                    gType = (String) value;
                    break;
                case keyMaxLevel:
                    gMaxLevel = (int) value;
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case keyEnabled:
                    gEnabled= jsonObject.optBoolean(key);
                    break;
                case keyType:
                    gType = jsonObject.optString(key);
                    break;
                case keyMaxLevel:
                    gMaxLevel = jsonObject.optInt(key);
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public entityDiaper setOff() {
        String fName="[setOff]";
        try {
           setEnabled(false);setType("");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
