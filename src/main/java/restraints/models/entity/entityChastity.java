package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.CHASTITYLEVELS;

import java.util.Arrays;
import java.util.Iterator;


public class entityChastity  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityChastity]";
    /*  gUserProfile.safetyCreateFieldEntry(nChastity);
            gUserProfile.safetyPutFieldEntry(nChastity,nOn,false);
            gUserProfile.safetyPutFieldEntry(nChastity,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nChastity,nShock,false);*/
    final String nOn=iRestraints.nOn,
            nLevel =iRestraints.nLevel,
            nNone=iRestraints.nNone,
            nChastity=iRestraints.nChastity,nShock=iRestraints.nShock;
    boolean gnOn=false;
    String gnLevel=nNone;
    boolean gnShock=false;
    public JSONObject extraJsonObject=new JSONObject();

    public entityChastity(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityChastity(JSONObject jsonObject){
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
            gnLevel=nNone;
            gnShock=false;
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityChastity set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nChastity)){
                logger.info(fName+"has key nChastity");
                jsonObject=jsonObject.getJSONObject(nChastity);
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
            jsonObject.put(nLevel,gnLevel);
            jsonObject.put(nShock,gnShock);
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
    public boolean isShockEnabled(){
        String fName="[isShockEnabled]";
        try {
            logger.info(fName+"value="+gnShock);
            return gnShock;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isShockAvailable(){
        String fName="[isShockAvailable]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(!isShockEnabled()){
                logger.info(fName+"shock not enabled>false");
                return false;
            }
            logger.info(fName+"dafault>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public CHASTITYLEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+gnLevel);
            return CHASTITYLEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return CHASTITYLEVELS.INVALID;
        }
    }
    public String getLevelAsString(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+gnLevel);
           return gnLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityChastity setOn(boolean input){
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
    public entityChastity setShockEnabled(boolean input){
        String fName="[setShockEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnShock=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChastity setLevel(String input){
        String fName="[setLevel]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnLevel=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChastity setLevel(CHASTITYLEVELS input){
        String fName="[setLevel]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.getName());
            gnLevel=input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityChastity release(){
        String fName="[release]";
        try {
            setOn(false);
            setShockEnabled(false);
            setLevel(CHASTITYLEVELS.None);
            logger.info(fName+"default >true");
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
                case nOn:
                    gnOn= (boolean) value;
                    break;
                case nShock:
                    gnShock= (boolean) value;
                    break;
                case nLevel:
                    gnLevel= (String) value;
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
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case nOn:
                    gnOn= jsonObject.getBoolean(key);
                    break;
                case nShock:
                    gnShock= jsonObject.getBoolean(key);
                    break;
                case nLevel:
                    gnLevel= jsonObject.getString(key);
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
}
