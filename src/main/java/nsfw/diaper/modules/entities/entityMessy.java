package nsfw.diaper.modules.entities;

import kong.unirest.json.JSONObject;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;


public class entityMessy {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityWet]";
    /* field=fieldWet;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyChance,4);
            gUserProfile.safetyPutFieldEntry(field, keyLevel,0);*/
    protected final String fieldMessy = iDiaperInteractive.fieldMessy,keyEnabled=iDiaperInteractive.keyEnabled, keyChance =iDiaperInteractive.keyChance, keyLevel =iDiaperInteractive.keyLevel;
    protected boolean gEnabled=false;
    protected int gChance=0, gLevel =0;


    public entityMessy(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityMessy(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityMessy clear(){
        String fName="[clear]";
        try {
            gEnabled=false;
            gChance=4;
            gLevel =0;

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
            if(jsonObject.has(fieldMessy)){
                logger.info(fName+"has key fieldDiaper");
                jsonObject=jsonObject.getJSONObject(fieldMessy);
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
            jsonObject.put(keyChance,gChance);
            jsonObject.put(keyLevel, gLevel);

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
    public int getChance(){
        String fName="[getChance]";
        try {
            logger.info(fName+"value="+gChance);
            return gChance;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+ gLevel);
            return gLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public entityMessy setEnabled(boolean input){
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
    public entityMessy setChance(int input){
        String fName="[setChance]";
        try {
            logger.info(fName+"input="+input);
            gChance=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityMessy setLevel(int input){
        String fName="[setLevel]";
        try {
            logger.info(fName+"input="+input);
            gLevel =input;
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
                case keyChance:
                    gChance = (int) value;
                    break;
                case keyLevel:
                    gLevel = (int) value;
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
                case keyChance:
                    gChance = jsonObject.optInt(key);
                    break;
                case keyLevel:
                    gLevel = jsonObject.optInt(key);
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public entityMessy runaway() {
        String fName="[runaway]";
        try {
            setEnabled(false);setChance(4);setLevel(0);
            logger.info(fName+"done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
