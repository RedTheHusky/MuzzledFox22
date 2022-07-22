package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityTimeLock {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityTimeLock]";
    /*gUserProfile.safetyCreateFieldEntry(nTimeLock);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nOn,false);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockDuration,0);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockMax,0);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockMin,0);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockStart,60000*15);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockTimestamp,0);*/
   final String nTimeLock=iRestraints.nTimeLock,nOn=iRestraints.nOn,
           nTimeLockDuration=iRestraints.nTimeLockDuration,
           nTimeLockMax=iRestraints.nTimeLockMax,
           nTimeLockMin=iRestraints.nTimeLockMin,
           nTimeLockStart=iRestraints.nTimeLockStart,
          nTimeLockTimestamp=iRestraints.nTimeLockTimestamp;
   boolean gnOn=false;
   long gnTimeLockDuration=0,
        gnTimeLockMax=0,
        gnTimeLockMin=0,
        gnTimeLockStart=60000*15,
        gnTimeLockTimestamp=0;
    public JSONObject extraJsonObject=new JSONObject();

    public entityTimeLock(){
        String fName="[constructor]";
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
    public boolean clear(){
        String fName="[clear]";
        try {
            gnOn=false;
            gnTimeLockDuration=0;
            gnTimeLockMax=0;
            gnTimeLockMin=0;
            gnTimeLockStart=60000*15;
            gnTimeLockTimestamp=0;
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityTimeLock set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nTimeLock)){
                logger.info(fName+"has key nTimeLock");
                jsonObject=jsonObject.getJSONObject(nTimeLock);
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(!jsonObject.isEmpty()){
                Iterator<String> keys=jsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    put(key,jsonObject);
                }
            }
            jsonObject=null;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nOn,gnOn);
            jsonObject.put(nTimeLockDuration,gnTimeLockDuration);
            jsonObject.put(nTimeLockMax,gnTimeLockMax);
            jsonObject.put(nTimeLockMin,gnTimeLockMin);
            jsonObject.put(nTimeLockStart,gnTimeLockStart);
            jsonObject.put(nTimeLockTimestamp,gnTimeLockTimestamp);
            if(!extraJsonObject.isEmpty()){
                logger.info("extraJsonObject="+extraJsonObject.toString());
                Iterator<String>keys=extraJsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    jsonObject.put(key,extraJsonObject.get(key));
                }
            }
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isOn(){
        String fName="[isOn]";
        try {
            logger.info(fName+"value="+gnOn);
            return gnOn;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public long getTimeLockDuration(){
        String fName="[getTimeLockDuration]";
        try {
            logger.info(fName+"value="+gnTimeLockDuration);
            return gnTimeLockDuration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getTimeLockMax(){
        String fName="[getTimeLockMax]";
        try {
            logger.info(fName+"value="+gnTimeLockMax);
            return gnTimeLockMax;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getTimeLockMin(){
        String fName="[getTimeLockMin]";
        try {
            logger.info(fName+"value="+gnTimeLockMin);
            return gnTimeLockMin;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getTimeLockStart(){
        String fName="[getTimeLockStart]";
        try {
            logger.info(fName+"value="+gnTimeLockStart);
            return gnTimeLockStart;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getTimeLockTimestamp(){
        String fName="[getTimeLockTimestamp]";
        try {
            logger.info(fName+"value="+gnTimeLockTimestamp);
            return gnTimeLockTimestamp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public entityTimeLock setOn(boolean input){
        String fName="[setOn]";
        try {
            logger.info(fName+"input="+input);
            gnOn=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setTimeLockDuration(long input){
        String fName="[setTimeLockDuration]";
        try {
            logger.info(fName+"input="+input);
            gnTimeLockDuration=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setTimeLockMax(long input){
        String fName="[setTimeLockMax]";
        try {
            logger.info(fName+"input="+input);
            gnTimeLockMax=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setTimeLockMin(long input){
        String fName="[setTimeLockMin]";
        try {
            logger.info(fName+"input="+input);
            gnTimeLockMin=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setTimeLockStart(long input){
        String fName="[setTimeLockStart]";
        try {
            logger.info(fName+"input="+input);
            gnTimeLockStart=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeLock setTimeLockTimestamp(long input){
        String fName="[setTimeLockTimestamp]";
        try {
            logger.info(fName+"input="+input);
            gnTimeLockTimestamp=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case nOn:
                    gnOn= jsonObject.getBoolean(key);
                    break;
                case nTimeLockDuration:
                    gnTimeLockDuration= jsonObject.getLong(key);
                    break;
                case nTimeLockMax:
                    gnTimeLockMax= jsonObject.getLong(key);
                    break;
                case nTimeLockMin:
                    gnTimeLockMin= jsonObject.getLong(key);
                    break;
                case nTimeLockStart:
                    gnTimeLockStart= jsonObject.getLong(key);
                    break;
                case nTimeLockTimestamp:
                    gnTimeLockTimestamp= jsonObject.getLong(key);
                    break;
                default:
                    extraJsonObject.put(key,jsonObject.get(key));
                    break;
            }
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
                case nOn:
                    gnOn= (boolean) value;
                    break;
                case nTimeLockDuration:
                    gnTimeLockDuration= (long) value;
                    break;
                case nTimeLockMax:
                    gnTimeLockMax= (long) value;
                    break;
                case nTimeLockMin:
                    gnTimeLockMin= (long) value;
                    break;
                case nTimeLockStart:
                    gnTimeLockStart= (long) value;
                    break;
                case nTimeLockTimestamp:
                    gnTimeLockTimestamp= (long) value;
                    break;
                default:
                    extraJsonObject.put(key,value);
                    break;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
