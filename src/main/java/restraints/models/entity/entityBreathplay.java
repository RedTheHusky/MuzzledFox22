package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.BREATHPLAYLEVELS;

import java.util.Arrays;
import java.util.Iterator;


public class entityBreathplay {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityBreathplay]";

    final String nOn=iRestraints.nOn, nBreathplay =iRestraints.nBreathplay,
            nLevel =iRestraints.nLevel,nCount=iRestraints.nCount, nRemaining=iRestraints.nRemaining;
    boolean gnOn=false;
    String gnLevel="";
    int gnCount=0, gnRemaining =0;
    public JSONObject extraJsonObject=new JSONObject();

    public entityBreathplay(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityBreathplay(JSONObject jsonObject){
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
            gnLevel="";
            gnCount=0;
            gnRemaining =0;
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityBreathplay set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nBreathplay)){
                logger.info(fName+"has key nBlindfold");
                jsonObject=jsonObject.getJSONObject(nBreathplay);
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
            jsonObject.put(nCount,gnCount);
            jsonObject.put(nRemaining, gnRemaining);
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
    public int getCount(){
        String fName="[getCount]";
        try {
            logger.info(fName+"value="+gnCount);
            return gnCount;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getRemaining(){
        String fName="[getRemaining]";
        try {
            logger.info(fName+"value="+ gnRemaining);
            return gnRemaining;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public BREATHPLAYLEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+gnLevel);
            return BREATHPLAYLEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return BREATHPLAYLEVELS.Invalid;
        }
    }
    public entityBreathplay setOn(boolean input){
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
    public entityBreathplay setCount(int input){
        String fName="[setCount]";
        try {
            logger.info(fName+"input="+input);
            gnCount=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean incCount(){
        String fName="[incCount]";
        try {
            gnCount++;
            logger.info(fName+"count="+gnCount);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean decCount(){
        String fName="[decCount]";
        try {
            gnCount--;
            logger.info(fName+"count="+gnCount);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityBreathplay setRemaining(int input){
        String fName="[setRemaining]";
        try {
            logger.info(fName+"input="+input);
            gnRemaining=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityBreathplay setLevel(String input){
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
    public entityBreathplay setLevel(BREATHPLAYLEVELS input){
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
    public entityBreathplay release(){
        String fName="[release]";
        try {
            setOn(false);
            setLevel(BREATHPLAYLEVELS.None);
            logger.info(fName+"default >true");
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
                case nLevel:
                    gnLevel= jsonObject.getString(key);
                    break;
                case nCount:
                    gnCount= jsonObject.getInt(key);
                    break;
                case nRemaining:
                    gnRemaining = jsonObject.getInt(key);
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
                case nLevel:
                    gnLevel= (String) value;
                    break;
                case nCount:
                    gnCount= (int) value;
                    break;
                case nRemaining:
                    gnRemaining = (int) value;
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
    public boolean isLevelValid(){
        String fName="[isLevelValid]";
        try {
            BREATHPLAYLEVELS level=getLevel();
            if(level==null){
                logger.info(fName + ".isnull>false");
                return false;
            }
            if(level==BREATHPLAYLEVELS.Invalid){
                logger.info(fName + ".level is invalid>false");
                return false;
            }
            if(level==BREATHPLAYLEVELS.None){
                logger.info(fName + ".level is none>false");
                return false;
            }
            logger.info(fName + ".default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isLevelBlank(){
        String fName="[isLevelBlank]";
        try {
            BREATHPLAYLEVELS level=getLevel();
            if(level==null){
                logger.info(fName + ".isnull>false");
                return false;
            }
            if(level==BREATHPLAYLEVELS.Invalid){
                logger.info(fName + ".level is invalid>false");
                return false;
            }
            if(level==BREATHPLAYLEVELS.None){
                logger.info(fName + ".level is none>true");
                return true;
            }
            logger.info(fName + ".default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEncased(){
        String fName="[isEncased]";
        try {
            if(!isOn()){
                logger.info(fName + ".not ont>false");
                return false;
            }
            if(!isLevelValid()){
                logger.info(fName + ".invalid level>false");
                return false;
            }
            logger.info(fName + ".default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
