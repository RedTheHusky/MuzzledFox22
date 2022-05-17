package nsfw.diaper;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;


public class entityTimeLock {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityDiaper]";
    /*gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyType,typeDiaper_White);
            gUserProfile.safetyPutFieldEntry(field, keyMaxLevel,6);*/
    protected final String fieldTimeLock=iDiaperInteractive.fieldTimeLock,keyEnabled=iDiaperInteractive.keyEnabled,keyDuration=iDiaperInteractive.keyDuration,keyMaxDuration=iDiaperInteractive.keyMaxDuration,
            keyMinDuration=iDiaperInteractive.keyMinDuration,keyStartDuration=iDiaperInteractive.keyStartDuration,keyTimestamp=iDiaperInteractive.keyTimestamp;
    protected boolean gEnabled=false;
    protected long gDuration=0,gMaxDuration=0,gMinDuration=0,gStartDuration=60000*15,gTimestamp=0;
    protected entityDiaperUserProfile gNewProfile=null;

    public entityTimeLock(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityTimeLock(entityDiaperUserProfile profile){
        String fName="[constructor]";
        gNewProfile=profile;
        logger.info(fName);
    }
    public entityTimeLock(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityTimeLock(JSONObject jsonObject, entityDiaperUserProfile profile){
        String fName="[constructor]";
        try {
            set(jsonObject);
            gNewProfile=profile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            gEnabled=true;
            gDuration=0;
            gMaxDuration=0;
            gMinDuration=0;
            gStartDuration=60000*15;
            gTimestamp=0;

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            if(jsonObject.has(fieldTimeLock)){
                logger.info(fName+"has key fieldTimeLock");
                jsonObject=jsonObject.getJSONObject(fieldTimeLock);
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
            jsonObject.put(keyDuration,gDuration);
            jsonObject.put(keyMaxDuration,gMaxDuration);
            jsonObject.put(keyMinDuration,gMinDuration);
            jsonObject.put(keyStartDuration,gStartDuration);
            jsonObject.put(keyTimestamp,gTimestamp);

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
    public long getDuration(){
        String fName="[getDuration]";
        try {
            logger.info(fName+"value="+gDuration);
            return gDuration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getStartDuration(){
        String fName="[getStartDuration]";
        try {
            logger.info(fName+"value="+gStartDuration);
            return gStartDuration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getMaxDuration(){
        String fName="[getMaxDuration]";
        try {
            logger.info(fName+"value="+gMaxDuration);
            return gMaxDuration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getMinDuration(){
        String fName="[getMinDuration]";
        try {
            logger.info(fName+"value="+gMinDuration);
            return gMinDuration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getTimestamp(){
        String fName="[getDuration]";
        try {
            logger.info(fName+"value="+gTimestamp);
            return gTimestamp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public entityTimeLock setEnabled(boolean input){
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
    public entityTimeLock setDuration(long input){
        String fName="[setDuration]";
        try {
            logger.info(fName+"input="+input);
            gDuration=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setStartDuration(long input){
        String fName="[setStartDuration]";
        try {
            logger.info(fName+"input="+input);
            gStartDuration=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setMaxDuration(long input){
        String fName="[setMaxDuration]";
        try {
            logger.info(fName+"input="+input);
            gMaxDuration=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setMinDuration(long input){
        String fName="[setMinDuration]";
        try {
            logger.info(fName+"input="+input);
            gMinDuration=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean setTimestamp(long input){
        String fName="[setTimestamp]";
        try {
            logger.info(fName+"input="+input);
            gTimestamp=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
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
                case keyDuration:
                    gDuration = (long) value;
                    break;
                case keyStartDuration:
                    gStartDuration = (long) value;
                    break;
                case keyMaxDuration:
                    gMaxDuration = (long) value;
                    break;
                case keyMinDuration:
                    gMinDuration = (long) value;
                    break;
                case keyTimestamp:
                    gTimestamp = (long) value;
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
                case keyDuration:
                    gDuration = jsonObject.optLong(key);
                    break;
                case keyStartDuration:
                    gStartDuration = jsonObject.optLong(key);
                    break;
                case keyMaxDuration:
                    gMaxDuration = jsonObject.optLong(key);
                    break;
                case keyMinDuration:
                    gMinDuration = jsonObject.optLong(key);
                    break;
                case keyTimestamp:
                    gTimestamp =jsonObject.optLong(key);
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTimeLocked(){
        String fName="[isTimeLocked]";
        try {
            //return false;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            long startime=getTimestamp();
            logger.info(fName+".startime="+startime);
            long diff=timestamp.getTime()-startime;
            logger.info(fName+".diff="+diff);
            long duration=getDuration();
            timestampEnd.setTime(startime+duration);
            long remaning=timestampEnd.getTime()-timestamp.getTime();
            logger.info(fName+".remaning="+remaning);
            if(isEnabled()){
                logger.info(fName+".timelock enabled");
                if(remaning<0){
                    setEnabled(false);
                    logger.info(fName+".unlock");
                    gNewProfile.saveProfile();
                    return false;
                }else{
                    logger.info(fName+".remain locked");
                    if(!gNewProfile.cProfile.isLocked()){
                        logger.info(fName+".apply lock");
                        gNewProfile.cProfile.setLock(true);
                        gNewProfile.cProfile.setLockedBy(gNewProfile.gUserProfile.getUser().getIdLong());
                        gNewProfile.saveProfile();
                    }
                    return  true;
                }
            }
            logger.info(fName+".default");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public  long getRemaining(){
        String fName="[getRemaining]";
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            long startime=getTimestamp();
            logger.info(fName+".startime="+startime);
            long diff=timestamp.getTime()-startime;
            logger.info(fName+".diff="+diff);
            long duration=getDuration();
            timestampEnd.setTime(startime+duration);
            long remaning=timestampEnd.getTime()-timestamp.getTime();
            logger.info(fName+".remaning="+remaning);
            return  remaning;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }

    public entityTimeLock reset() {
        String fName="[reset]";
        try {
            setEnabled(false);
            setDuration(0);
            setMinDuration(0);
            setMaxDuration(0);
            setStartDuration(60000*15);
            setTimestamp(0);
            return  this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
