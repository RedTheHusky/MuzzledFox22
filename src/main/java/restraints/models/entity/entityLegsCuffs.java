package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.CUFFSLEGSLEVELS;

import java.util.Arrays;
import java.util.Iterator;


public class entityLegsCuffs  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLegsCuffs]";
    /* gUserProfile.safetyCreateFieldEntry(nLegsCuffs);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nOn,false);
            //gUserProfile.safetyPutFieldEntry(nLegsCuffs,nAnkles,false);
            //gUserProfile.safetyPutFieldEntry(nLegsCuffs,nKnees,false);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nChannelRestrainEnabled,false);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nChannelRestrainId,0);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs, nChannelRestrainException2UseUserGag,true);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs, nChannelRestrainException2UseGuildGag,true);
            //gUserProfile.safetyPutFieldEntry(nLegsCuffs,nTightness,0);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs, nImageUrlOn,"");gUserProfile.safetyPutFieldEntry(nLegsCuffs, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nLegsCuffs, nImageUrlStruggle,"");
            */

    final String nOn=iRestraints.nOn,
            nLevel =iRestraints.nLevel,
            nImageUrlOn=iRestraints.nImageUrlOn,nImageUrlOff=iRestraints.nImageUrlOff,nImageUrlSecure=iRestraints.nImageUrlSecure,nImageUrlUnSecure=iRestraints.nImageUrlUnSecure,nImageUrlStruggle=iRestraints.nImageUrlStruggle,
            nNone=iRestraints.nNone, nLegsCuffs=iRestraints.nLegsCuffs,
            nChannelRestrainEnabled=iRestraints.nChannelRestrainEnabled,
            nChannelRestrainException2UseUserGag=iRestraints.nChannelRestrainException2UseUserGag,
            nChannelRestrainException2UseGuildGag=iRestraints.nChannelRestrainException2UseGuildGag,
            nChannelRestrainId=iRestraints.nChannelRestrainId;
    boolean gnOn=false,
                    gnChannelRestrainEnabled =false,
                    gnChannelRestrainException2UseUserGag =false,
                    gnChannelRestrainException2UseGuildGag =false;
    String gnLevel =nNone;
    String gnImageUrlOn="",gnImageUrlOff="",gnImageUrlSecure="",gnImageUrlUnSecure="",gnImageUrlStruggle="";
    long gnChannelRestrainId =0L;
    public JSONObject extraJsonObject=new JSONObject();

    public entityLegsCuffs(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLegsCuffs(JSONObject jsonObject){
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
            gnChannelRestrainEnabled =false;
            gnChannelRestrainException2UseUserGag =false;
            gnChannelRestrainException2UseGuildGag =false;
            gnLevel =nNone;
            gnImageUrlOn="";gnImageUrlOff="";gnImageUrlSecure="";gnImageUrlUnSecure="";gnImageUrlStruggle="";
            gnChannelRestrainId =0L;
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityLegsCuffs set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nLegsCuffs)){
                logger.info(fName+"has key nStraitjacket");
                jsonObject=jsonObject.getJSONObject(nLegsCuffs);
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
            jsonObject.put(nChannelRestrainEnabled, gnChannelRestrainEnabled);
            jsonObject.put(nChannelRestrainException2UseUserGag, gnChannelRestrainException2UseUserGag);
            jsonObject.put(nChannelRestrainException2UseGuildGag, gnChannelRestrainException2UseGuildGag);
            jsonObject.put(nLevel, gnLevel);
            jsonObject.put(nChannelRestrainId, gnChannelRestrainId);
            jsonObject.put(nImageUrlOn,gnImageUrlOn);
            jsonObject.put(nImageUrlOff,gnImageUrlOff);
            jsonObject.put(nImageUrlSecure,gnImageUrlSecure);
            jsonObject.put(nImageUrlUnSecure,gnImageUrlUnSecure);
            jsonObject.put(nImageUrlStruggle,gnImageUrlStruggle);
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
    public boolean isChannelRestrainEnabled(){
        String fName="[isChannelRestrainEnabled]";
        try {
            logger.info(fName+"value="+ gnChannelRestrainEnabled);
            return gnChannelRestrainEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isChannelRestrainException2UseUserGag(){
        String fName="[isChannelRestrainException2UseUserGag]";
        try {
            logger.info(fName+"value="+ gnChannelRestrainException2UseUserGag);
            return gnChannelRestrainException2UseUserGag;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isChannelRestrainException2UseGuildGag(){
        String fName="[isChannelRestrainException2UseGuildGag]";
        try {
            logger.info(fName+"value="+ gnChannelRestrainException2UseGuildGag);
            return gnChannelRestrainException2UseGuildGag;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getLevelAsString(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+ gnLevel);
           return gnLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public CUFFSLEGSLEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+ gnLevel);
            return CUFFSLEGSLEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return CUFFSLEGSLEVELS.INVALID;
        }
    }
    public long getChannelRestrainId(){
        String fName="[getChannelRestrainId]";
        try {
            logger.info(fName+"value="+ gnChannelRestrainId);
            return gnChannelRestrainId;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getImageUrlOn(){
        String fName="[getImageUrlOn]";
        try {
            logger.info(fName+"value="+gnImageUrlOn);
            return gnImageUrlOn;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getImageUrlOff(){
        String fName="[getImageUrlOff]";
        try {
            logger.info(fName+"value="+gnImageUrlOff);
            return gnImageUrlOff;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getImageUrlSecure(){
        String fName="[getImageUrlSecure]";
        try {
            logger.info(fName+"value="+gnImageUrlSecure);
            return gnImageUrlSecure;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getImageUrlUnSecure(){
        String fName="[getImageUrlUnSecure]";
        try {
            logger.info(fName+"value="+gnImageUrlUnSecure);
            return gnImageUrlUnSecure;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getImageUrlStruggle(){
        String fName="[getImageUrlStruggle]";
        try {
            logger.info(fName+"value="+gnImageUrlStruggle);
            return gnImageUrlStruggle;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityLegsCuffs setOn(boolean input){
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
    public entityLegsCuffs setChannelRestrainEnabled(boolean input){
        String fName="[setChannelRestrainEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnChannelRestrainEnabled =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setChannelRestrainException2UseUserGag(boolean input){
        String fName="[setChannelRestrainException2UseUserGag]";
        try {
            logger.info(fName+"input="+input);
            gnChannelRestrainException2UseUserGag =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setChannelRestrainException2UseGuildGag(boolean input){
        String fName="[setChannelRestrainException2UseGuildGag]";
        try {
            logger.info(fName+"input="+input);
            gnChannelRestrainException2UseGuildGag =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setLevel(String input){
        String fName="[setLevel]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnLevel =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setLevel(CUFFSLEGSLEVELS input){
        String fName="[setLevel]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.getName());
            gnLevel =input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs release(){
        String fName="[release]";
        try {
            setOn(false);
            setLevel(CUFFSLEGSLEVELS.None);
            logger.info(fName+"default >true");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setChannelRestrainId(long input){
        String fName="[setChannelRestrainId]";
        try {
            logger.info(fName+"input="+input);
            gnChannelRestrainId =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setImageUrlOn(String input){
        String fName="[setImageUrlOn]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnImageUrlOn=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setImageUrlOff(String input){
        String fName="[setImageUrlOff]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnImageUrlOff=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setImageUrlSecure(String input){
        String fName="[setImageUrlSecure]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnImageUrlSecure=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setImageUrlUnSecure(String input){
        String fName="[setImageUrlUnSecure]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnImageUrlUnSecure=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLegsCuffs setImageUrlStruggle(String input){
        String fName="[setImageUrlStruggle]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnImageUrlStruggle=input;
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
                case nChannelRestrainEnabled:
                    gnChannelRestrainEnabled = jsonObject.getBoolean(key);
                    break;
                case nChannelRestrainException2UseUserGag:
                    gnChannelRestrainException2UseUserGag = jsonObject.getBoolean(key);
                    break;
                case nChannelRestrainException2UseGuildGag:
                    gnChannelRestrainException2UseGuildGag = jsonObject.getBoolean(key);
                    break;
                case nLevel:
                    gnLevel =jsonObject.getString(key);
                    break;
                case nChannelRestrainId:
                    gnChannelRestrainId = jsonObject.getLong(key);
                    break;
                case nImageUrlOn:
                    gnImageUrlOn= jsonObject.getString(key);
                    break;
                case nImageUrlOff:
                    gnImageUrlOff= jsonObject.getString(key);
                    break;
                case nImageUrlSecure:
                    gnImageUrlSecure= jsonObject.getString(key);
                    break;
                case nImageUrlUnSecure:
                    gnImageUrlUnSecure= jsonObject.getString(key);
                    break;
                case nImageUrlStruggle:
                    gnImageUrlStruggle= jsonObject.getString(key);
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
                case nChannelRestrainEnabled:
                    gnChannelRestrainEnabled = (boolean) value;
                    break;
                case nChannelRestrainException2UseUserGag:
                    gnChannelRestrainException2UseUserGag = (boolean) value;
                    break;
                case nChannelRestrainException2UseGuildGag:
                    gnChannelRestrainException2UseGuildGag = (boolean) value;
                    break;
                case nLevel:
                    gnLevel = (String) value;
                    break;
                case nChannelRestrainId:
                    gnChannelRestrainId = (long) value;
                    break;
                case nImageUrlOn:
                    gnImageUrlOn= (String) value;
                    break;
                case nImageUrlOff:
                    gnImageUrlOff= (String) value;
                    break;
                case nImageUrlSecure:
                    gnImageUrlSecure= (String) value;
                    break;
                case nImageUrlUnSecure:
                    gnImageUrlUnSecure= (String) value;
                    break;
                case nImageUrlStruggle:
                    gnImageUrlStruggle= (String) value;
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
