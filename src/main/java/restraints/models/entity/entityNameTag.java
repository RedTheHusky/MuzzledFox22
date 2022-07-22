package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityNameTag  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityNameTag]";
    /* gUserProfile.safetyCreateFieldEntry(nNameTag);
            gUserProfile.safetyPutFieldEntry(nNameTag,nOn,false);
            gUserProfile.safetyPutFieldEntry(nNameTag,nNewName,"");
            gUserProfile.safetyPutFieldEntry(nNameTag,nSoftName,"");
            gUserProfile.safetyPutFieldEntry(nNameTag,nOldName,"");
            gUserProfile.safetyPutFieldEntry(nNameTag,nAvatar,"");*/
    final String nOn=iRestraints.nOn,
            nNewName=iRestraints.nNewName,
            nSoftName=iRestraints.nSoftName,
            nOldName=iRestraints.nOldName,
            nAvatar=iRestraints.nAvatar,
            nNameTag=iRestraints.nNameTag;
    boolean gnOn=false;
    String gnNewName="",
            gnSoftName="",
            gnOldName="",
            gnAvatar="";
    public JSONObject extraJsonObject=new JSONObject();

    public entityNameTag(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityNameTag(JSONObject jsonObject){
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
            gnNewName="";
            gnSoftName="";
            gnOldName="";
            gnAvatar="";
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityNameTag set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nNameTag)){
                logger.info(fName+"has key nNameTag");
                jsonObject=jsonObject.getJSONObject(nNameTag);
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
            jsonObject.put(nNewName,gnNewName);
            jsonObject.put(nSoftName,gnSoftName);
            jsonObject.put(nOldName,gnOldName);
            jsonObject.put(nAvatar,gnAvatar);
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
    public String getNewName(){
        String fName="[getNewName]";
        try {
            logger.info(fName+"value="+gnNewName);
           return gnNewName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getSoftName(){
        String fName="[getSoftName]";
        try {
            logger.info(fName+"value="+gnSoftName);
            return gnSoftName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getOldName(){
        String fName="[getOldName]";
        try {
            logger.info(fName+"value="+gnOldName);
            return gnOldName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getAvatar(){
        String fName="[getAvatar]";
        try {
            logger.info(fName+"value="+gnAvatar);
            return gnAvatar;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityNameTag setOn(boolean input){
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
    public entityNameTag setNewName(String input){
        String fName="[setNewName]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnNewName=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityNameTag setSoftName(String input){
        String fName="[setSoftName]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnSoftName=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityNameTag setOldName(String input){
        String fName="[setOldName]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnOldName=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityNameTag setAvatar(String input){
        String fName="[setAvatar]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnAvatar=input;
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
                case nNewName:
                    gnNewName= jsonObject.getString(key);
                    break;
                case nSoftName:
                    gnSoftName= jsonObject.getString(key);
                    break;
                case nOldName:
                    gnOldName= jsonObject.getString(key);
                    break;
                case nAvatar:
                    gnAvatar= jsonObject.getString(key);
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
                case nNewName:
                    gnNewName= (String) value;
                    break;
                case nSoftName:
                    gnSoftName= (String) value;
                    break;
                case nOldName:
                    gnOldName= (String) value;
                    break;
                case nAvatar:
                    gnAvatar= (String) value;
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
