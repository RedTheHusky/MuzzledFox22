package restraints.models.entity;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityLeash {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLeash]";
    /* gUserProfile.safetyCreateFieldEntry(nLeash);
            gUserProfile.safetyPutFieldEntry(nLeash,nOn,false);
            //gUserProfile.safetyPutFieldEntry(nLeash,nTightness,0);
            gUserProfile.safetyPutFieldEntry(nLeash,nLeashLength,3);
            gUserProfile.safetyPutFieldEntry(nLeash,nLeash2Member,"");
            gUserProfile.safetyPutFieldEntry(nLeash,nLeash2Obj,"");*/
    final String nOn=iRestraints.nOn,nLeash=iRestraints.nLeash,
            nLeashLength=iRestraints.nLeashLength,nLeash2Member=iRestraints.nLeash2Member,nLeash2Obj=iRestraints.nLeash2Obj;
    boolean gnOn=false;
   int gnLeashLength=3;
     long gnLeash2Member=0;
     String gnLeash2Obj="";
    public JSONObject extraJsonObject=new JSONObject();

    public entityLeash(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLeash(JSONObject jsonObject){
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
            gnLeashLength=3;
            gnLeash2Member=0;
            gnLeash2Obj="";
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
            if(jsonObject.has(nLeash)){
                logger.info(fName+"has key nLeash");
                jsonObject=jsonObject.getJSONObject(nLeash);
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
            jsonObject.put(nOn,gnOn);
            jsonObject.put(nOn,gnOn);
            jsonObject.put(nLeashLength,gnLeashLength);
            jsonObject.put(nLeash2Member,gnLeash2Member);
            jsonObject.put(nLeash2Obj,gnLeash2Obj);
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
    public int getLeashLength(){
        String fName="[getLeashLength]";
        try {
            logger.info(fName+"value="+gnLeashLength);
           return gnLeashLength;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getLeash2Member(){
        String fName="[getLeash2Member]";
        try {
            logger.info(fName+"value="+gnLeash2Member);
            return gnLeash2Member;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getLeash2Obj(){
        String fName="[getLeash2Obj]";
        try {
            logger.info(fName+"value="+gnLeash2Obj);
            return gnLeash2Obj;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityLeash setOn(boolean input){
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
    public entityLeash setLeashLength(int input){
        String fName="[setLeashLength]";
        try {
            logger.info(fName+"input="+input);
            gnLeashLength=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLeash setLeash2Member(long input){
        String fName="[setLeash2Member]";
        try {
            logger.info(fName+"input="+input);
            gnLeash2Member=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLeash setLeash2Obj(String input){
        String fName="[setLeash2Obj]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){input=""; }
            gnLeash2Obj=input;
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
                case nLeashLength:
                    gnLeashLength= jsonObject.getInt(key);
                    break;
                case nLeash2Member:
                    gnLeash2Member= jsonObject.getLong(key);
                    break;
                case nLeash2Obj:
                    gnLeash2Obj= jsonObject.getString(key);
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
                case nLeashLength:
                    gnLeashLength= (int) value;
                    break;
                case nLeash2Member:
                    gnLeash2Member= (long) value;
                    break;
                case nLeash2Obj:
                    gnLeash2Obj= (String) value;
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
    public entityLeash release(){
        String fName="[release]";
        try{
            setOn(false);
            gnLeash2Member=0;gnLeash2Obj="";
            logger.info(fName+"released");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLeash leashedTo(Member member){
        String fName="[leashedTo]";
        try {
            logger.info(fName);
            leashedTo(member.getIdLong());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLeash leashedTo(long member){
        String fName="[leashedTo]";
        try {
            logger.info(fName+"member="+member);
            setOn(true);
            setLeash2Member(member);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLeash leashedTo(String object){
        String fName="[leashedTo]";
        try {
            logger.info(fName+"object="+object);
            if(object==null){object=""; }
            setOn(true);
            setLeash2Obj(object);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
