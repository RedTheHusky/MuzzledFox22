package restraints.models.entity;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.COLLARLEVELS;

import java.util.*;


public class entityCollar  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityCollar]";
    /*gUserProfile.safetyCreateFieldEntry(nCollar);
     gUserProfile.safetyPutFieldEntry(nCollar,nOn,false);
     gUserProfile.safetyPutFieldEntry(nCollar,nLevel,nNone);
     gUserProfile.safetyPutFieldEntry(nCollar,nShockeEnabled,false);
     gUserProfile.safetyPutFieldEntry(nCollar,nAutoShockException4GuildGagException,true);
     gUserProfile.safetyPutFieldEntry(nCollar,nAutoShockException4UserGagException,true);
     gUserProfile.safetyPutFieldEntry(nCollar,nBadWords,new JSONArray());
     gUserProfile.safetyPutFieldEntry(nCollar,nEnforcedWords,new JSONArray());*/
    final String nOn=iRestraints.nOn,
            nLevel =iRestraints.nLevel,
            nShockeEnabled=iRestraints.nShockeEnabled,
    nAutoShockException4GuildGagException=iRestraints.nAutoShockException4GuildGagException,
    nAutoShockException4UserGagException=iRestraints.nAutoShockException4UserGagException,
    nBadWords=iRestraints.nBadWords,
    nEnforcedWords=iRestraints.nEnforcedWords,
    nNone=iRestraints.nNone,
    nCollar=iRestraints.nCollar;
    boolean gnOn=false;
    String gnLevel=nNone;
    boolean gnShockeEnabled=false,
            gnAutoShockException4GuildGagException=true,
            gnAutoShockException4UserGagException=true;
    JSONArray gnBadWords=new JSONArray(),
            gnEnforcedWords=new JSONArray();
    public JSONObject extraJsonObject=new JSONObject();

    public entityCollar(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityCollar(JSONObject jsonObject){
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
            gnShockeEnabled=false;
            gnAutoShockException4GuildGagException=true;
            gnAutoShockException4UserGagException=true;
            gnBadWords=new JSONArray();
            gnEnforcedWords=new JSONArray();
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityCollar set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nCollar)){
                logger.info(fName+"has key nCollar");
                jsonObject=jsonObject.getJSONObject(nCollar);
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
            jsonObject.put(nShockeEnabled,gnShockeEnabled);
            jsonObject.put(nAutoShockException4GuildGagException,gnAutoShockException4GuildGagException);
            jsonObject.put(nAutoShockException4UserGagException,gnAutoShockException4UserGagException);
            jsonObject.put(nBadWords,gnBadWords);
            jsonObject.put(nEnforcedWords,gnEnforcedWords);
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
    public boolean isShockeEnabled(){
        String fName="[isShockeEnabled]";
        try {
            logger.info(fName+"value="+gnShockeEnabled);
            return gnShockeEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isAutoShockException4GuildGagException(){
        String fName="[isAutoShockException4GuildGagException]";
        try {
            logger.info(fName+"value="+gnAutoShockException4GuildGagException);
            return gnAutoShockException4GuildGagException;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isAutoShockException4UserGagException(){
        String fName="[isAutoShockException4UserGagException]";
        try {
            logger.info(fName+"value="+gnAutoShockException4UserGagException);
            return gnAutoShockException4UserGagException;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public COLLARLEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+gnLevel);
            return COLLARLEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return COLLARLEVELS.INVALID;
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
    public JSONArray getBadWords(){
        String fName="[getBadWords]";
        try {
            logger.info(fName+"value="+gnBadWords.toString());
            return gnBadWords;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public List<String> getBadWordsAsList(){
        String fName="[getBadWordsAsList]";
        try {
            logger.info(fName+"value="+gnBadWords.toString());
            return gnBadWords.toList();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public int getBadWordsLength(){
        String fName="[getBadWordsLength]";
        try {
            int value=gnBadWords.length();
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmptyBadWords(){
        String fName="[isEmptyBadWords]";
        try {
            return gnBadWords.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public String getText4BadWords(int index){
        String fName="[getText4BadWords]";
        try {
            logger.info(fName+"index="+index+", array="+gnBadWords.toString());
            return gnBadWords.getString(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getLengthText4BadWords(int index){
        String fName="[getLengthText4BadWords]";
        try {
            logger.info(fName+"index="+index+", array="+gnBadWords.toString());
            return gnBadWords.getString(index).length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONArray getEnforcedWords(){
        String fName="[getEnforcedWords]";
        try {
            logger.info(fName+"value="+gnEnforcedWords.toString());
            return gnEnforcedWords;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public List<String> getEnforcedWordsAsList(){
        String fName="[getEnforcedWordsAsList]";
        try {
            logger.info(fName+"value="+gnEnforcedWords.toString());
            return gnEnforcedWords.toList();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public int getEnforcedWordsLength(){
        String fName="[getEnforcedWordsLength]";
        try {
            int value=gnEnforcedWords.length();
            logger.info(fName+"value="+value);
            return value;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmptyEnforcedWords(){
        String fName="[isEnforcedWords]";
        try {
            return gnEnforcedWords.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public String getText4EnforcedWords(int index){
        String fName="[getText4EnforcedWords]";
        try {
            logger.info(fName+"index="+index+", array="+gnEnforcedWords.toString());
            return gnEnforcedWords.getString(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getLengthText4EnforcedWords(int index){
        String fName="[getLengthText4EnforcedWords]";
        try {
            logger.info(fName+"index="+index+", array="+gnEnforcedWords.toString());
            return gnEnforcedWords.getString(index).length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public entityCollar setOn(boolean input){
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
    public entityCollar setShockeEnabled(boolean input){
        String fName="[setShockeEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnShockeEnabled=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar setAutoShockException4GuildGagException(boolean input){
        String fName="[setAutoShockException4GuildGagException]";
        try {
            logger.info(fName+"input="+input);
            gnAutoShockException4GuildGagException=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar setAutoShockException4UserGagException(boolean input){
        String fName="[setAutoShockException4UserGagException]";
        try {
            logger.info(fName+"input="+input);
            gnAutoShockException4UserGagException=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar setLevel(String input){
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
    public entityCollar setLevel(COLLARLEVELS input){
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
    public entityCollar release(){
        String fName="[release]";
        try {
            setOn(false);
            setShockeEnabled(false);
            setLevel(COLLARLEVELS.None);
            logger.info(fName+"default >true");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar setBadWords(JSONArray input){
        String fName="[setBadWords]";
        try {
            logger.info(fName+"input="+input.toString());
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnBadWords=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar setEnforcedWords(JSONArray input){
        String fName="[setEnforcedWords]";
        try {
            logger.info(fName+"input="+input.toString());
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnEnforcedWords=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar putBadWords(String input){
        String fName="[putBadWords]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnBadWords.put(input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar putEnforcedWords(String input){
        String fName="[putEnforcedWords]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnEnforcedWords.put(input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar remBadWords(int index){
        String fName="[remBadWords]";
        try {
            logger.info(fName+"index="+index);
            gnBadWords.remove(index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar remEnforcedWords(int index){
        String fName="[remEnforcedWords]";
        try {
            logger.info(fName+"index="+index);
            gnEnforcedWords.remove(index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar clearBadWords(){
        String fName="[clearBadWords]";
        try {
            gnBadWords=new JSONArray();
            logger.info(fName+"cleared");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCollar clearEnforcedWords(){
        String fName="[clearEnforcedWords]";
        try {
            gnEnforcedWords=new JSONArray();
            logger.info(fName+"cleared");
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
                case nShockeEnabled:
                    gnShockeEnabled=jsonObject.getBoolean(key);
                    break;
                case nAutoShockException4GuildGagException:
                    gnAutoShockException4GuildGagException= jsonObject.getBoolean(key);
                    break;
                case nAutoShockException4UserGagException:
                    gnAutoShockException4UserGagException=jsonObject.getBoolean(key);
                    break;
                case nLevel:
                    gnLevel= jsonObject.getString(key);
                    break;
                case nBadWords:
                    gnBadWords= jsonObject.getJSONArray(key);
                    break;
                case nEnforcedWords:
                    gnEnforcedWords= jsonObject.getJSONArray(key);
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
                case nShockeEnabled:
                    gnShockeEnabled= (boolean) value;
                    break;
                case nAutoShockException4GuildGagException:
                    gnAutoShockException4GuildGagException= (boolean) value;
                    break;
                case nAutoShockException4UserGagException:
                    gnAutoShockException4UserGagException= (boolean) value;
                    break;
                case nLevel:
                    gnLevel= (String) value;
                    break;
                case nBadWords:
                    gnBadWords= (JSONArray) value;
                    break;
                case nEnforcedWords:
                    gnEnforcedWords= (JSONArray) value;
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
