package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityVoiceChannelRestriction {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityVoiceChannelRestriction]";
    /* gUserProfile.safetyCreateFieldEntry(nVoiceChannelRestriction);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,optionVoiceMute,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,false);*/
    final String
            nVoiceChannelRestriction=iRestraints.nVoiceChannelRestriction,
            optionVoiceMute=iRestraints.optionVoiceMute,
            optionVoiceDeafen=iRestraints.optionVoiceDeafen,
            nVoiceChannelRestriction_MuteEnabled=iRestraints.nVoiceChannelRestriction_MuteEnabled,
            nVoiceChannelRestriction_MuteInUse=iRestraints.nVoiceChannelRestriction_MuteInUse,
            nVoiceChannelRestriction_ListenEnabled=iRestraints.nVoiceChannelRestriction_ListenEnabled,
            nVoiceChannelRestriction_ListenInUse=iRestraints.nVoiceChannelRestriction_ListenInUse;
    boolean
        goptionVoiceMute=false,
        goptionVoiceDeafen=false,
        gnVoiceChannelRestriction_MuteEnabled=false,
        gnVoiceChannelRestriction_MuteInUse=false,
        gnVoiceChannelRestriction_ListenEnabled=false,
        gnVoiceChannelRestriction_ListenInUse=false;
    public JSONObject extraJsonObject=new JSONObject();

    public entityVoiceChannelRestriction(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityVoiceChannelRestriction(JSONObject jsonObject){
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
            goptionVoiceMute=false;
            goptionVoiceDeafen=false;
            gnVoiceChannelRestriction_MuteEnabled=false;
            gnVoiceChannelRestriction_MuteInUse=false;
            gnVoiceChannelRestriction_ListenEnabled=false;
            gnVoiceChannelRestriction_ListenInUse=false;
            extraJsonObject=new JSONObject();
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
            if(jsonObject.has(nVoiceChannelRestriction)){
                logger.info(fName+"has key nVoiceChannelRestriction");
                jsonObject=jsonObject.getJSONObject(nVoiceChannelRestriction);
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
            jsonObject.put(optionVoiceMute,goptionVoiceMute);
            jsonObject.put(optionVoiceDeafen,goptionVoiceDeafen);
            jsonObject.put(nVoiceChannelRestriction_MuteEnabled,gnVoiceChannelRestriction_MuteEnabled);
            jsonObject.put(nVoiceChannelRestriction_MuteInUse,gnVoiceChannelRestriction_MuteInUse);
            jsonObject.put(nVoiceChannelRestriction_ListenEnabled,gnVoiceChannelRestriction_ListenEnabled);
            jsonObject.put(nVoiceChannelRestriction_ListenInUse,gnVoiceChannelRestriction_ListenInUse);
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
    public boolean isOptionVoiceMute(){
        String fName="[isOptionVoiceMute]";
        try {
            logger.info(fName+"value="+goptionVoiceMute);
            return goptionVoiceMute;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isOptionVoiceDeafen(){
        String fName="[isOptionVoiceDeafen]";
        try {
            logger.info(fName+"value="+goptionVoiceDeafen);
            return goptionVoiceDeafen;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isVoiceChannelRestriction_MuteEnabled(){
        String fName="[isVoiceChannelRestriction_MuteEnabled]";
        try {
            logger.info(fName+"value="+gnVoiceChannelRestriction_MuteEnabled);
            return gnVoiceChannelRestriction_MuteEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isVoiceChannelRestriction_MuteInUse(){
        String fName="[isVoiceChannelRestriction_MuteInUse]";
        try {
            logger.info(fName+"value="+gnVoiceChannelRestriction_MuteInUse);
            return gnVoiceChannelRestriction_MuteInUse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isVoiceChannelRestriction_ListenEnabled(){
        String fName="[isVoiceChannelRestriction_ListenEnabled]";
        try {
            logger.info(fName+"value="+gnVoiceChannelRestriction_ListenEnabled);
            return gnVoiceChannelRestriction_ListenEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isVoiceChannelRestriction_ListenInUse(){
        String fName="[isVoiceChannelRestriction_ListenInUse]";
        try {
            logger.info(fName+"value="+gnVoiceChannelRestriction_ListenInUse);
            return gnVoiceChannelRestriction_ListenInUse;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setOptionVoiceMute(boolean input){
        String fName="[setOptionVoiceMute]";
        try {
            logger.info(fName+"input="+input);
            goptionVoiceMute=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setOptionVoiceDeafen(boolean input){
        String fName="[setOptionVoiceDeafen]";
        try {
            logger.info(fName+"input="+input);
            goptionVoiceDeafen=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setVoiceChannelRestriction_MuteEnabled(boolean input){
        String fName="[setVoiceChannelRestriction_MuteEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnVoiceChannelRestriction_MuteEnabled=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setVoiceChannelRestriction_MuteInUse(boolean input){
        String fName="[setVoiceChannelRestriction_MuteInUse]";
        try {
            logger.info(fName+"input="+input);
            gnVoiceChannelRestriction_MuteInUse=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setVoiceChannelRestriction_ListenEnabled(boolean input){
        String fName="[setVoiceChannelRestriction_ListenEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnVoiceChannelRestriction_ListenEnabled=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setVoiceChannelRestriction_ListenInUse(boolean input){
        String fName="[setVoiceChannelRestriction_ListenInUse]";
        try {
            logger.info(fName+"input="+input);
            gnVoiceChannelRestriction_ListenInUse=input;
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
                case optionVoiceMute:
                    goptionVoiceMute= jsonObject.getBoolean(key);
                    break;
                case optionVoiceDeafen:
                    goptionVoiceDeafen= jsonObject.getBoolean(key);
                    break;
                case nVoiceChannelRestriction_MuteEnabled:
                    gnVoiceChannelRestriction_MuteEnabled= jsonObject.getBoolean(key);
                    break;
                case nVoiceChannelRestriction_MuteInUse:
                    gnVoiceChannelRestriction_MuteInUse= jsonObject.getBoolean(key);
                    break;
                case nVoiceChannelRestriction_ListenEnabled:
                    gnVoiceChannelRestriction_ListenEnabled= jsonObject.getBoolean(key);
                    break;
                case nVoiceChannelRestriction_ListenInUse:
                    gnVoiceChannelRestriction_ListenInUse= jsonObject.getBoolean(key);
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
                case optionVoiceMute:
                    goptionVoiceMute= (boolean) value;
                    break;
                case optionVoiceDeafen:
                    goptionVoiceDeafen= (boolean) value;
                    break;
                case nVoiceChannelRestriction_MuteEnabled:
                    gnVoiceChannelRestriction_MuteEnabled= (boolean) value;
                    break;
                case nVoiceChannelRestriction_MuteInUse:
                    gnVoiceChannelRestriction_MuteInUse= (boolean) value;
                    break;
                case nVoiceChannelRestriction_ListenEnabled:
                    gnVoiceChannelRestriction_ListenEnabled= (boolean) value;
                    break;
                case nVoiceChannelRestriction_ListenInUse:
                    gnVoiceChannelRestriction_ListenInUse= (boolean) value;
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
