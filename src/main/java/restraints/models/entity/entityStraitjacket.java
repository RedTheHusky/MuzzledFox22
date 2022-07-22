package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.models.enums.STRAITJACKETARMSLEVEL;
import restraints.models.enums.STRAITJACKETCROTCHSLEVEL;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityStraitjacket  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityStraitjacket]";
    /*gUserProfile.safetyCreateFieldEntry(nStraitjacket);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nOn,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapArms,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapArmsLevel,"");
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapCrotch,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapCrotchLevel,"");
            gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlOn,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlOff,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlSecure,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlStruggle,"");
            */
    final String nStraitjacket=iRestraints.nStraitjacket,nOn=iRestraints.nOn,nStrapArms=iRestraints.nStrapArms,nStrapArmsLevel=iRestraints.nStrapArmsLevel,
            nStrapCrotch=iRestraints.nStrapCrotch,nStrapCrotchLevel=iRestraints.nStrapCrotchLevel,
            nImageUrlOn=iRestraints.nImageUrlOn,nImageUrlOff=iRestraints.nImageUrlOff,nImageUrlSecure=iRestraints.nImageUrlSecure,nImageUrlUnSecure=iRestraints.nImageUrlUnSecure,nImageUrlStruggle=iRestraints.nImageUrlStruggle,
            nStrapStitch=iRestraints.nStrapStitch;
    boolean gnOn=false,
                    gnStrapArms=false,
                    gnStrapCrotch=false,
                    gnStrapStitch=false;
    String gnStrapArmsLevel="",
                gnStrapCrotchLevel="";
    String gnImageUrlOn="",gnImageUrlOff="",gnImageUrlSecure="",gnImageUrlUnSecure="",gnImageUrlStruggle="";
    public JSONObject extraJsonObject=new JSONObject();

    public entityStraitjacket(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityStraitjacket(JSONObject jsonObject){
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
            gnStrapArms=false;
            gnStrapCrotch=false;
            gnStrapStitch=false;
            gnStrapArmsLevel="";
            gnStrapCrotchLevel="";
            gnImageUrlOn="";gnImageUrlOff="";gnImageUrlSecure="";gnImageUrlUnSecure="";gnImageUrlStruggle="";
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityStraitjacket set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nStraitjacket)){
                logger.info(fName+"has key nStraitjacket");
                jsonObject=jsonObject.getJSONObject(nStraitjacket);
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
            jsonObject.put(nStrapArms,gnStrapArms);
            jsonObject.put(nStrapCrotch,gnStrapCrotch);
            jsonObject.put(nStrapArmsLevel,gnStrapArmsLevel);
            jsonObject.put(nStrapCrotchLevel,gnStrapCrotchLevel);
            jsonObject.put(nImageUrlOn,gnImageUrlOn);
            jsonObject.put(nImageUrlOff,gnImageUrlOff);
            jsonObject.put(nImageUrlSecure,gnImageUrlSecure);
            jsonObject.put(nImageUrlUnSecure,gnImageUrlUnSecure);
            jsonObject.put(nImageUrlStruggle,gnImageUrlStruggle);
            jsonObject.put(nStrapStitch,gnStrapStitch);
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
    public boolean isArmStrapped(){
        String fName="[isArmStrapped]";
        try {
            logger.info(fName+"value="+gnStrapArms);
            return gnStrapArms;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isCrotchStrapped(){
        String fName="[isCrotchStrapped]";
        try {
            logger.info(fName+"value="+gnStrapCrotch);
            return gnStrapCrotch;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isStiched(){
        String fName="[isStiched]";
        try {
            logger.info(fName+"value="+gnStrapStitch);
            return gnStrapStitch;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrained(){
        String fName="[areArmsRestrained]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(!isArmStrapped()){
                logger.info(fName+"arms not strapped >false");
                return false;
            }
            logger.info(fName+"default >true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isCrotchRestrained(){
        String fName="[isCrotchRestrained]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(!isCrotchStrapped()){
                logger.info(fName+"crotch not strapped >false");
                return false;
            }
            logger.info(fName+"default >true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getArmLevelAsString(){
        String fName="[getArmLevel]";
        try {
            logger.info(fName+"value="+gnStrapArmsLevel);
           return gnStrapArmsLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getCrotchLevelAsString(){
        String fName="[getCrotchLevel]";
        try {
            logger.info(fName+"value="+gnStrapCrotchLevel);
            return gnStrapCrotchLevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public STRAITJACKETARMSLEVEL getArmLevel(){
        String fName="[getArmLevel]";
        try {
            logger.info(fName+"value="+gnStrapArmsLevel);
            return STRAITJACKETARMSLEVEL.valueByName(gnStrapArmsLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return STRAITJACKETARMSLEVEL.INVALID;
        }
    }
    public STRAITJACKETCROTCHSLEVEL getCrotchLevel(){
        String fName="[getCrotchLevel]";
        try {
            logger.info(fName+"value="+gnStrapCrotchLevel);
            return STRAITJACKETCROTCHSLEVEL.valueByName(gnStrapCrotchLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return STRAITJACKETCROTCHSLEVEL.INVALID;
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
    public entityStraitjacket setOn(boolean input){
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
    public entityStraitjacket setArmsStrapped(boolean input){
        String fName="[setArmsStrapped]";
        try {
            logger.info(fName+"input="+input);
            gnStrapArms=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setCrotchStrapped(boolean input){
        String fName="[setCrotchStrapped]";
        try {
            logger.info(fName+"input="+input);
            gnStrapCrotch=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setStiched(boolean input){
        String fName="[setStiched]";
        try {
            logger.info(fName+"input="+input);
            gnStrapStitch=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setArmsLevel(String input){
        String fName="[setArmsLevel]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnStrapArmsLevel=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setArmsLevel(STRAITJACKETARMSLEVEL input){
        String fName="[setArmsLevel]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.getName());
            gnStrapArmsLevel=input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setCrotchLevel(String input){
        String fName="[setCrotchLevel]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnStrapCrotchLevel=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setCrotchLevel(STRAITJACKETCROTCHSLEVEL input){
        String fName="[setCrotchLevel]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.getName());
            gnStrapCrotchLevel=input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket release(){
        String fName="[release]";
        try {
            setOn(false);
            setStiched(false);
            setCrotchStrapped(false);setArmsStrapped(false);
            setCrotchLevel(STRAITJACKETCROTCHSLEVEL.NONE); setArmsLevel(STRAITJACKETARMSLEVEL.NONE);
            logger.info(fName+"default >true");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityStraitjacket setImageUrlOn(String input){
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
    public entityStraitjacket setImageUrlOff(String input){
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
    public entityStraitjacket setImageUrlSecure(String input){
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
    public entityStraitjacket setImageUrlUnSecure(String input){
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
    public entityStraitjacket setImageUrlStruggle(String input){
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
                case nStrapArms:
                    gnStrapArms= jsonObject.getBoolean(key);
                    break;
                case nStrapCrotch:
                    gnStrapCrotch= jsonObject.getBoolean(key);
                    break;
                case nStrapStitch:
                    gnStrapStitch= jsonObject.getBoolean(key);
                    break;
                case nStrapArmsLevel:
                    gnStrapArmsLevel= jsonObject.getString(key);
                    break;
                case nStrapCrotchLevel:
                    gnStrapCrotchLevel= jsonObject.getString(key);
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
                case nStrapArms:
                    gnStrapArms= (boolean) value;
                    break;
                case nStrapCrotch:
                    gnStrapCrotch= (boolean) value;
                    break;
                case nStrapStitch:
                    gnStrapStitch= (boolean) value;
                    break;
                case nStrapArmsLevel:
                    gnStrapArmsLevel= (String) value;
                    break;
                case nStrapCrotchLevel:
                    gnStrapCrotchLevel= (String) value;
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
