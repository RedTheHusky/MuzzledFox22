package restraints.models.entity;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.GAGTYPES;
import restraints.in.iRestraints;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class entityGag  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityGag]";
    /*gUserProfile.safetyPutFieldEntry(nGag,nTightness,0);
            gUserProfile.safetyPutFieldEntry(nGag,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nGag,nCustomGagText,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nGag,flagOnlyUseCustom,false);
            gUserProfile.safetyPutFieldEntry(nGag,flagEnableCustom,true);// gUserProfile.safetyPutFieldEntry(flagEnableCustom,true);
            gUserProfile.safetyPutFieldEntry(nGag,flagDisableGagOOCException,false);
            gUserProfile.safetyPutFieldEntry(nGag,flagDisableGagSafeWords,false);
            gUserProfile.safetyPutFieldEntry(nGag,nType,gagTypeBall);
            gUserProfile.safetyPutFieldEntry(nGag,nException,false);
            gUserProfile.safetyPutFieldEntry(nGag,nExceptionList,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nGag, nImageUrlSecure,"");gUserProfile.safetyPutFieldEntry(nGag, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nGag, nImageUrlStruggle,"");
            gUserProfile.safetyPutFieldEntry(nGag,nTrainingGagEntries,new JSONArray())
            gUserProfile.safetyPutFieldEntry(nGag,nTimeLockStart,false);
            gUserProfile.safetyPutFieldEntry(nGag,nTimeLockTimestamp,0L);
            gUserProfile.safetyPutFieldEntry(nGag,nTimeLockDuration,0L);*/
    final String nOn=iRestraints.nOn,
            nLevel =iRestraints.nLevel,
            nImageUrlOn=iRestraints.nImageUrlOn,nImageUrlOff=iRestraints.nImageUrlOff,nImageUrlSecure=iRestraints.nImageUrlSecure,nImageUrlUnSecure=iRestraints.nImageUrlUnSecure,nImageUrlStruggle=iRestraints.nImageUrlStruggle,
            nNone=iRestraints.nNone,nGag=iRestraints.nGag,
            nExceptionList=iRestraints.nExceptionList,nTrainingGagEntries=iRestraints.nTrainingGagEntries,nCustomGagText=iRestraints.nCustomGagText,
    nType=iRestraints.nType,nException=iRestraints.nException,nPostRestrictEnabled=iRestraints.nPostRestrictEnabled,nPostRestrictionException2UseGuildGag=iRestraints.nPostRestrictionException2UseGuildGag,
    nPostRestrictionException2UseUserGag=iRestraints.nPostRestrictionException2UseUserGag,gagTypeBall= GAGTYPES.Ball.getName(),
    flagOnlyUseCustom=iRestraints.flagOnlyUseCustom,flagEnableCustom=iRestraints.flagEnableCustom,flagDisableGagOOCException=iRestraints.flagDisableGagOOCException,
    flagDisableGagSafeWords=iRestraints.flagDisableGagSafeWords,
            nTimeLockStart=iRestraints.nTimeLockStart,nTimeLockTimestamp=iRestraints.nTimeLockTimestamp,nTimeLockDuration=iRestraints.nTimeLockDuration,
            nDisable2Show4Wearer =iRestraints.nDisable2Show4Wearer, nDisable2Show4Others =iRestraints.nDisable2Show4Others,
    keyExtra=iRestraints.keyExtra;
    private boolean gnOn=false,
                gflagOnlyUseCustom =false,
                gflagEnableCustom =true,
                gflagDisableGagOOCException =false,
                gflagDisableGagSafeWords=false,
                gnException=false,
                gnTimeLockStart=false,
                gnDisable2Show4Wearer =false,
                gnDisable2Show4Others =false;
    private String gnLevel =nNone,gnType=gagTypeBall;
    private String gnImageUrlOn="",gnImageUrlOff="",gnImageUrlSecure="",gnImageUrlUnSecure="",gnImageUrlStruggle="";
    private long gnTimeLockTimestamp=0,gnTimeLockDuration=0;
    private JSONObject gnExceptionList=new JSONObject(),gnCustomGagText=new JSONObject();
    private JSONArray gnTrainingGagEntries=new JSONArray();
    public JSONObject extraJsonObject=new JSONObject();

    public entityGag(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityGag(JSONObject jsonObject){
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
            gflagOnlyUseCustom =false;
            gflagEnableCustom =true;
            gflagDisableGagOOCException =false;
            gflagDisableGagSafeWords=false;
            gnException=false;
            gnLevel =nNone;gnType=gagTypeBall;
            gnExceptionList=new JSONObject();gnCustomGagText=new JSONObject();
            gnTrainingGagEntries=new JSONArray();
            gnImageUrlOn="";gnImageUrlOff="";gnImageUrlSecure="";gnImageUrlUnSecure="";gnImageUrlStruggle="";
            extraJsonObject=new JSONObject();
            gnTimeLockStart=false;
            gnTimeLockTimestamp=0;gnTimeLockDuration=iRestraints.milliseconds_minute*15;
            gnDisable2Show4Wearer =false;
            gnDisable2Show4Others =false;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityGag set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nGag)){
                logger.info(fName+"has key nGag");
                jsonObject=jsonObject.getJSONObject(nGag);
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
            jsonObject.put(flagOnlyUseCustom, gflagOnlyUseCustom);
            jsonObject.put(flagEnableCustom, gflagEnableCustom);
            jsonObject.put(flagDisableGagOOCException, gflagDisableGagOOCException);
            jsonObject.put(nLevel, gnLevel);
            jsonObject.put(nType, gnType);
            jsonObject.put(nCustomGagText, gnCustomGagText);
            jsonObject.put(flagDisableGagSafeWords,gflagDisableGagSafeWords);
            jsonObject.put(nException,gnException);
            jsonObject.put(nExceptionList,gnExceptionList);
            jsonObject.put(nTrainingGagEntries,gnTrainingGagEntries);
            jsonObject.put(nTimeLockStart,gnTimeLockStart);
            jsonObject.put(nTimeLockTimestamp,gnTimeLockTimestamp);
            jsonObject.put(nTimeLockDuration,gnTimeLockDuration);
            jsonObject.put(nImageUrlOn,gnImageUrlOn);
            jsonObject.put(nImageUrlOff,gnImageUrlOff);
            jsonObject.put(nImageUrlSecure,gnImageUrlSecure);
            jsonObject.put(nImageUrlUnSecure,gnImageUrlUnSecure);
            jsonObject.put(nImageUrlStruggle,gnImageUrlStruggle);
            jsonObject.put(nDisable2Show4Wearer, gnDisable2Show4Wearer);
            jsonObject.put(nDisable2Show4Others, gnDisable2Show4Others);
            jsonObject.put(keyExtra, extra.getJSON());
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
    public boolean isFlagOnlyUseCustom(){
        String fName="[isflagOnlyUseCustom]";
        try {
            logger.info(fName+"value="+gflagOnlyUseCustom);
            return gflagOnlyUseCustom;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isFlagEnableCustom(){
        String fName="[isflagEnableCustom]";
        try {
            logger.info(fName+"value="+gflagEnableCustom);
            return gflagEnableCustom;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isFlagDisableGagOOCException(){
        String fName="[isflagDisableGagOOCException]";
        try {
            logger.info(fName+"value="+gflagDisableGagOOCException);
            return gflagDisableGagOOCException;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isFlagDisableGagSafeWords(){
        String fName="[isflagDisableGagSafeWords]";
        try {
            logger.info(fName+"value="+gflagDisableGagSafeWords);
            return gflagDisableGagSafeWords;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isExceptionEnabled(){
        String fName="[isExceptionEnabled]";
        try {
            logger.info(fName+"value="+gnException);
            return gnException;
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
    public GAGLEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+ gnLevel);
            return  GAGLEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return GAGLEVELS.INVALID;
        }
    }
    public boolean isValidLevel(){
        String fName="[isValidLevel]";
        try {
            GAGLEVELS level=getLevel();
            if(level==GAGLEVELS.INVALID||level==GAGLEVELS.None){
                logger.info(fName+"null>false");
                return false;
            }
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getTypeAsString(){
        String fName="[getType]";
        try {
            logger.info(fName+"value="+ gnType);
            return gnType;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public GAGTYPES getType(){
        String fName="[getType]";
        try {
            logger.info(fName+"value="+ gnType);
            return GAGTYPES.valueByName(gnType);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return GAGTYPES.INVALID;
        }
    }
    public JSONObject getExceptionList(){
        String fName="[getExceptionList]";
        try {
            logger.info(fName+"value="+ gnExceptionList.toString());
            return gnExceptionList;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isEmptyExceptionList(){
        String fName="[isEmptyExceptionList]";
        try {
            logger.info(fName+"value="+ gnExceptionList.toString());
            return gnExceptionList.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public List<Long> getExceptionListAsLongList(){
        String fName="[getExceptionListAsLongList]";
        try {
            logger.info(fName+"value="+ gnExceptionList.toString());
            List<Long> list=new ArrayList<>();
            Iterator<String>keys=gnExceptionList.keys();
            while(keys.hasNext()){
                try {
                    list.add(Long.parseLong(keys.next()));
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<String> getExceptionListAsStringList(){
        String fName="[getExceptionListAsLongList]";
        try {
            logger.info(fName+"value="+ gnExceptionList.toString());
            List<String> list=new ArrayList<>();
            Iterator<String>keys=gnExceptionList.keys();
            while(keys.hasNext()){
                try {
                    list.add(keys.next());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return  list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public boolean hasExceptionList(long id){
        String fName="[getExceptionList]";
        try {
            logger.info(fName+"id="+id+", list="+ gnExceptionList.toString());
            return hasExceptionList(String.valueOf(id));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasExceptionList(String id){
        String fName="[getExceptionList]";
        try {
            logger.info(fName+"id="+id+", list="+ gnExceptionList.toString());
            if(gnExceptionList.isEmpty()){
                logger.info(fName+"list isEmpty>false");
                return false;
            }
            if(gnExceptionList.has(id)){
                logger.info(fName+"has such id>true");
                return true;
            }
            logger.info(fName+"dafault>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


    ///
    public JSONObject getCustomGagText(){
        String fName="[getCustomGagText]";
        try {
            logger.info(fName+"value="+ gnCustomGagText.toString());
            return gnCustomGagText;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONArray getCustomGagText(GAGLEVELS level){
        String fName="[getCustomGagText]";
        try {
            return getCustomGagText(level.getName());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray getCustomGagText(String level){
        String fName="[getCustomGagText]";
        try {
            logger.info(fName+"list="+ gnCustomGagText.toString());
            if(gnCustomGagText.isEmpty()){
                logger.info(fName+"is empty");
                return new JSONArray();
            }
            logger.info(fName+"level="+level);
            if(!gnCustomGagText.has(level)){
                logger.info(fName+"no such level");
                return new JSONArray();
            }
            if(gnCustomGagText.isNull(level)){
                logger.info(fName+"level is null");
                return new JSONArray();
            }
            return gnCustomGagText.getJSONArray(level);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public int getCustomGagTexLength(String level){
        String fName="[getCustomGagTextLength]";
        try {
            logger.info(fName+"level="+level+", list="+ gnCustomGagText.toString());
            if(gnCustomGagText.isEmpty()){
                logger.info(fName+"is empty");
                return 0;
            }
            if(!gnCustomGagText.has(level)){
                logger.info(fName+"no such level");
                return 0;
            }
            if(gnCustomGagText.isNull(level)){
                logger.info(fName+"level is null");
                return 0;
            }
            return gnCustomGagText.getJSONArray(level).length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getCustomGagTexLength(GAGLEVELS level){
        String fName="[getCustomGagTextLength]";
        try {
            logger.info(fName+"level="+level.getName()+", list="+ gnCustomGagText.toString());
            if(gnCustomGagText.isEmpty()){
                logger.info(fName+"is empty");
                return 0;
            }
            String key=level.getName();
            if(!gnCustomGagText.has(key)){
                logger.info(fName+"no such level");
                return 0;
            }
            if(gnCustomGagText.isNull(key)){
                logger.info(fName+"level is null");
                return 0;
            }
            return gnCustomGagText.getJSONArray(key).length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getCustomGagTexAsString(String level,int index){
        String fName="[getCustomGagTextAsString]";
        try {
            logger.info(fName+"level="+level+", index="+index+", list="+ gnCustomGagText.toString());
            if(gnCustomGagText.isEmpty()){
                logger.info(fName+"is empty");
                return "";
            }
            if(!gnCustomGagText.has(level)){
                logger.info(fName+"no such level");
                return "";
            }
            if(gnCustomGagText.isNull(level)){
                logger.info(fName+"level is null");
                return "";
            }
            if(index<0||gnCustomGagText.getJSONArray(level).length()<=index){
                logger.info(fName+"index invalid");
                return "";
            }
            return gnCustomGagText.getJSONArray(level).getString(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getCustomGagTexAsString(GAGLEVELS level,int index){
        String fName="[getCustomGagTextAsString]";
        try {
            logger.info(fName+"level="+level.getName()+", index="+index+", list="+ gnCustomGagText.toString());
            String key=level.getName();
            if(gnCustomGagText.isEmpty()){
                logger.info(fName+"is empty");
                return "";
            }
            if(!gnCustomGagText.has(key)){
                logger.info(fName+"no such level");
                return "";
            }
            if(gnCustomGagText.isNull(key)){
                logger.info(fName+"level is null");
                return "";
            }
            if(index<0||gnCustomGagText.getJSONArray(key).length()<=index){
                logger.info(fName+"index invalid");
                return "";
            }
            return gnCustomGagText.getJSONArray(key).getString(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean hasTextCustomGagText(GAGLEVELS level, String text){
        String fName="[hasTextCustomGagText]";
        try {
            return hasTextCustomGagText(level.getName(),text);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasTextCustomGagText(GAGLEVELS level, String text, boolean ignoreCase){
        String fName="[hasTextCustomGagText]";
        try {
            return hasTextCustomGagText(level.getName(),text,ignoreCase);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasTextCustomGagText(String level, String text){
        String fName="[hasTextCustomGagText]";
        return hasTextCustomGagText(level,text,false);
    }
    public boolean hasTextCustomGagText(String level, String text, boolean ignoreCase){
        String fName="[hasCustomGagText]";
        try {
            JSONArray array=getCustomGagText(level);
            logger.info(fName+"array="+array.toString());
            logger.info(fName+"text="+text+", ignoreCase="+ignoreCase);
            for(int i=0;i<array.length();i++){
                String item=array.getString(i);
                logger.info(fName+"array["+i+"]="+item);
                if(ignoreCase){
                    if(item.equalsIgnoreCase(text)){
                        logger.info(fName+"found>true");
                        return true;
                    }
                }else{
                    if(item.equals(text)){
                        logger.info(fName+"found>true");
                        return true;
                    }
                }
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEmptyCustomGagText(){
        String fName="[isEmptyCustomGagText]";
        try {
            if(gnCustomGagText==null){
                logger.info(fName+"json is null>true");
                return true;
            }
            logger.info(fName+"json="+ gnCustomGagText.toString());
            if(gnCustomGagText.isEmpty()){
                logger.info(fName+"isEmpty>true");
                return true;
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEmptyCustomGagText(GAGLEVELS level){
        String fName="[isEmptyCustomGagText]";
        try {
            logger.info(fName+"level="+level.getName());
            return isEmptyCustomGagText(level.getName());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEmptyCustomGagText(String level){
        String fName="[isEmptyCustomGagText]";
        try {
            logger.info(fName+"level="+level);
            JSONArray array=getCustomGagText(level);
            if(array==null){
                logger.info(fName+"array is null>true");
                return true;
            }
            logger.info(fName+"array="+array.toString());
            if(array.isEmpty()){
                logger.info(fName+"array.isEmpty>true");
                return true;
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONArray getTrainingGagEntries(){
        String fName="[getTrainingGagEntries]";
        try {
            logger.info(fName+"value="+ gnTrainingGagEntries.toString());
            return gnTrainingGagEntries;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public int getTrainingGagLength(){
        String fName="[getTrainingGagLength]";
        try {
            logger.info(fName+"value="+ gnTrainingGagEntries.length());
            return gnTrainingGagEntries.length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean hasFromTextTrainingGag(String text,boolean ignoreCase){
        String fName="[hasFromTextTrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"text="+text+", ignoreCase="+ignoreCase);
            for(int i=0;i<gnTrainingGagEntries.length();i++){
                JSONObject jsonObject=gnTrainingGagEntries.getJSONObject(i);
                logger.info(fName+"array["+i+"]="+jsonObject.toString());
                if(jsonObject.has(iRestraints.nFrom)){
                    try {
                        String item=jsonObject.getString(iRestraints.nFrom);
                        if(ignoreCase){
                            if(item.equalsIgnoreCase(text)){
                                logger.info(fName+"found>true");
                                return true;
                            }
                        }else{
                            if(item.equals(text)){
                                logger.info(fName+"found>true");
                                return true;
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getFromTextIndexTrainingGag(String text,boolean ignoreCase){
        String fName="[getFromTextIndexTrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"text="+text+", ignoreCase="+ignoreCase);
            for(int i=0;i<gnTrainingGagEntries.length();i++){
                JSONObject jsonObject=gnTrainingGagEntries.getJSONObject(i);
                logger.info(fName+"array["+i+"]="+jsonObject.toString());
                if(jsonObject.has(iRestraints.nFrom)){
                    try {
                        String item=jsonObject.getString(iRestraints.nFrom);
                        if(ignoreCase){
                            if(item.equalsIgnoreCase(text)){
                                logger.info(fName+"found>true");
                                return i;
                            }
                        }else{
                            if(item.equals(text)){
                                logger.info(fName+"found>true");
                                return i;
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            logger.info(fName+"default>false");
            return -1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public boolean hasToTextTrainingGag(String text,boolean ignoreCase){
        String fName="[hasToTextTrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"text="+text+", ignoreCase="+ignoreCase);
            for(int i=0;i<gnTrainingGagEntries.length();i++){
                JSONObject jsonObject=gnTrainingGagEntries.getJSONObject(i);
                logger.info(fName+"array["+i+"]="+jsonObject.toString());
                if(jsonObject.has(iRestraints.nTo)){
                    try {
                        String item=jsonObject.getString(iRestraints.nTo);
                        if(ignoreCase){
                            if(item.equalsIgnoreCase(text)){
                                logger.info(fName+"found>true");
                                return true;
                            }
                        }else{
                            if(item.equals(text)){
                                logger.info(fName+"found>true");
                                return true;
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getToTextIndexTrainingGag(String text,boolean ignoreCase){
        String fName="[getToTextIndexTrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"text="+text+", ignoreCase="+ignoreCase);
            for(int i=0;i<gnTrainingGagEntries.length();i++){
                JSONObject jsonObject=gnTrainingGagEntries.getJSONObject(i);
                logger.info(fName+"array["+i+"]="+jsonObject.toString());
                if(jsonObject.has(iRestraints.nTo)){
                    try {
                        String item=jsonObject.getString(iRestraints.nTo);
                        if(ignoreCase){
                            if(item.equalsIgnoreCase(text)){
                                logger.info(fName+"found>true");
                                return i;
                            }
                        }else{
                            if(item.equals(text)){
                                logger.info(fName+"found>true");
                                return i;
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            logger.info(fName+"default>false");
            return -1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public JSONObject getEntry4TrainingGag(int index){
        String fName="[getEntry4TrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"index="+index);
            return gnTrainingGagEntries.getJSONObject(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getFromText4TrainingGag(int index){
        String fName="[getFromText4TrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"index="+index);
            return gnTrainingGagEntries.getJSONObject(index).getString(iRestraints.nFrom);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getToText4TrainingGag(int index){
        String fName="[getToText4TrainingGag]";
        try {
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            logger.info(fName+"index="+index);
            return gnTrainingGagEntries.getJSONObject(index).getString(iRestraints.nTo);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
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
    public boolean isTimeLockStarted(){
        String fName="[isTimeLockStarted]";
        try {
            logger.info(fName+"value="+gnTimeLockStart);
            return gnTimeLockStart;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTimeLocked(){
        String fName="[isTimeLocked]";
        try {
            logger.info(fName+"value="+gnTimeLockStart);
            if(!isValid2BePutinTimeLock()){
                logger.info(fName+"not valid gag statement>false");
                return false;
            }
            if(!isTimeLockStarted()){
                logger.info(fName+"not started>false");
                return false;
            }
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isValid2BePutinTimeLock(){
        String fName="[isValid2BePutinTimelock]";
        try {
            if(!isOn()){
                logger.info(fName+"gag not on>false");
                return false;
            }
            GAGLEVELS gaglevel=getLevel();
            if(gaglevel==null){
                logger.info(fName+"gag level is null>false");
                return false;
            }
            if(gaglevel==GAGLEVELS.INVALID){
                logger.info(fName+"gag level is invcalid>false");
                return false;
            }
            if(gaglevel==GAGLEVELS.None){
                logger.info(fName+"gag level is none>false");
                return false;
            }
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isValidTimeLockDuration(){
        String fName="[isValidTimeLockDuration]";
        try {
            logger.info(fName+"gnTimeLockDuration="+gnTimeLockDuration);
            if(gnTimeLockDuration<iRestraints.milliseconds_minute*15){
                logger.info(fName+"duration bellow 15 minutes>false");
                return  false;
            }
            if(gnTimeLockDuration>iRestraints.milliseconds_day*14){
                logger.info(fName+"duration above 14 days>false");
                return  false;
            }
            logger.info(fName+"default>true");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isDisable2Show4Wearer(){
        String fName="[isDisable2ShowByWearer]";
        try {
            logger.info(fName+"value="+ gnDisable2Show4Wearer);
            return gnDisable2Show4Wearer;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isDisable2Show4Others(){
        String fName="[isDisable2Show4Others]";
        try {
            logger.info(fName+"value="+ gnDisable2Show4Others);
            return gnDisable2Show4Others;
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
    public boolean isTimeLockeDuration6h(){
        String fName="[isTimeLockeDuration6h]";
        try {
            logger.info(fName+"value="+gnTimeLockDuration);
            long tmp=6*iRestraints.milliseconds_hour;
            if(tmp<=gnTimeLockDuration){
                logger.info(fName+"equal or big>true");
                return true;
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
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
    public long getTmeLockReleaseTimestamp(){
        String fName="[getTmeLockReleaseTimestamp]";
        try {
            logger.info(fName+"Duration="+gnTimeLockDuration);
            logger.info(fName+"LockTimestamp="+gnTimeLockTimestamp);
            long diff=gnTimeLockTimestamp+gnTimeLockDuration;
            logger.info(fName+"diff="+diff);
            return diff;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getTimeLockRemaning(){
        String fName="[getTimeLockRemaning]";
        try {
            logger.info(fName+"Duration="+gnTimeLockDuration);
            logger.info(fName+"LockTimestamp="+gnTimeLockTimestamp);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".CurrentTimestamp="+currentTimestamp.getTime());
            if(gnTimeLockTimestamp>currentTimestamp.getTime()){
                logger.info(fName+"timestamp cant be bigger than current one>false");
                return 0L;
            }
            if(gnTimeLockTimestamp<=0){
                logger.info(fName+"timestamp cant be be zero or negative>false");
                return 0L;
            }
            if(!isValidTimeLockDuration()){
                logger.info(fName+"invalid duration>false");
                return 0L;
            }
            long releasetime=getTmeLockReleaseTimestamp();
            logger.info(fName+"releasetime="+releasetime);
            long diff=releasetime-currentTimestamp.getTime();
            logger.info(fName+"diff="+diff);
            return diff;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public boolean doesItNeedTimeLockRelease(){
        String fName="[doesItNeedTimeLockRelease]";
        try {
            if(!isTimeLockStarted()){
                logger.info(fName+"not started>true");
                return true;
            }
            if(!isValid2BePutinTimeLock()){
                logger.info(fName+"not valid gag statement>true");
                return true;
            }
            if(gnTimeLockTimestamp<=0){
                logger.info(fName+"timestamp cant be be zero or negative>true");
                return true;
            }
            if(getTimeLockRemaning()<=0){
                logger.info(fName+"remaning equal or sub 0>true");
                return true;
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasStillTimeLockRemaning(){
        String fName="[hasStillTimeLockRemaning]";
        try {
            if(!isTimeLockStarted()){
                logger.info(fName+"not started>false");
                return false;
            }
            if(!isValidTimeLockDuration()){
                logger.info(fName+"invalud duration>false");
                return false;
            }
            long value=getTimeLockRemaning();
            logger.info(fName+"value="+value);
            if(value<=0){
                logger.info(fName+"no>false");
                return false;
            }
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityGag setOn(boolean input){
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
    public entityGag setFlagOnlyUseCustom(boolean input){
        String fName="[setflagOnlyUseCustom]";
        try {
            logger.info(fName+"input="+input);
            gflagOnlyUseCustom=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setFlagEnableCustom(boolean input){
        String fName="[setflagEnableCustom]";
        try {
            logger.info(fName+"input="+input);
            gflagEnableCustom=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setFlagDisableGagSafeWords(boolean input){
        String fName="[setflagDisableGagSafeWords]";
        try {
            logger.info(fName+"input="+input);
            gflagDisableGagSafeWords=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setFlagDisableGagOOCException(boolean input){
        String fName="[setflagDisableGagOOCException]";
        try {
            logger.info(fName+"input="+input);
            gflagDisableGagOOCException=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setExceptionEnable(boolean input){
        String fName="[setExceptionEnable]";
        try {
            logger.info(fName+"input="+input);
            gnException=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setDisable2Show4Wearer(boolean input){
        String fName="[setDisable2ShowByWearer]";
        try {
            gnDisable2Show4Wearer=input;
            logger.info(fName+"value="+ gnDisable2Show4Wearer);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setDisable2Show4Others(boolean input){
        String fName="[setDisable2Show4Others]";
        try {
            gnDisable2Show4Others=input;
            logger.info(fName+"value="+ gnDisable2Show4Others);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setLevel(String input){
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
    public entityGag setLevel(GAGLEVELS input){
        String fName="[setLevel]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnLevel =input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setType(String input){
        String fName="[setType]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnType =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setType(GAGTYPES input){
        String fName="[setType]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnType =input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag release(){
        String fName="[release]";
        try {
            gnTimeLockDuration=15*iRestraints.milliseconds_minute;
            gnTimeLockTimestamp=0;
            gnTimeLockStart=false;
            setLevel(GAGLEVELS.None);
            setOn(false);
            logger.info(fName+"default >true");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setExceptionList(JSONObject input){
        String fName="[setExceptionList]";
        try {
            if(input==null){
            logger.info(fName+"can't be null");
                return null;
        }
            logger.info(fName+"input="+input.toString());
            gnExceptionList =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setCustomGagText(JSONObject input){
        String fName="[setCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.toString());
            gnCustomGagText =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setCustomGagText(String level,JSONArray input){
        String fName="[setCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"level="+level+", input="+input.toString());
            gnCustomGagText.put(level,input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag addCustomGagText(String level,String input){
        String fName="[addCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"level="+level+", input="+input);
            JSONArray array=getCustomGagText(level);
            array.put(input);
            logger.info(fName+"array="+array);
            gnCustomGagText.put(level,array);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag putCustomGagText(String level,String input,boolean ignoreCase){
        String fName="[putCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"level="+level+", ignoreCase="+ignoreCase+", input="+input);
            JSONArray array=getCustomGagText(level);
            if(ignoreCase){
                for(int i=0;i<array.length();i++){
                    String compare=array.getString(i);
                    if(compare.equalsIgnoreCase(input)){
                        logger.info(fName+"already has>false");
                        return null;
                    }
                }
            }else{
                for(int i=0;i<array.length();i++){
                    String compare=array.getString(i);
                    if(compare.equals(input)){
                        logger.info(fName+"already has>false");
                        return null;
                    }
                }
            }

            array.put(input);
            logger.info(fName+"adding");
            gnCustomGagText.put(level,array);
            logger.info(fName+"added");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag remCustomGagText(String level,int index){
        String fName="[remCustomGagText]";
        try {
            logger.info(fName+"level="+level+", index="+index);
            JSONArray array=getCustomGagText(level);
            array.remove(index);
            logger.info(fName+"array="+array);
            gnCustomGagText.put(level,array);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setCustomGagText(GAGLEVELS level,JSONArray input){
        String fName="[setCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"level="+level.getName()+", input="+input.toString());
            gnCustomGagText.put(level.getName(),input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag addCustomGagText(GAGLEVELS level,String input){
        String fName="[addCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"level="+level.getName()+", input="+input);
            JSONArray array=getCustomGagText(level);
            array.put(input);
            logger.info(fName+"array="+array);
            gnCustomGagText.put(level.getName(),array);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag putCustomGagText(GAGLEVELS level,String input,boolean ignoreCase){
        String fName="[putCustomGagText]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"level="+level.getName()+", ignoreCase="+ignoreCase+", input="+input);
            JSONArray array=getCustomGagText(level);
            if(ignoreCase){
                for(int i=0;i<array.length();i++){
                    String compare=array.getString(i);
                    if(compare.equalsIgnoreCase(input)){
                        logger.info(fName+"already has>false");
                        return null;
                    }
                }
            }else{
                for(int i=0;i<array.length();i++){
                    String compare=array.getString(i);
                    if(compare.equals(input)){
                        logger.info(fName+"already has>false");
                        return null;
                    }
                }
            }
            array.put(input);
            logger.info(fName+"adding");
            gnCustomGagText.put(level.getName(),array);
            logger.info(fName+"added");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean remCustomGagText(GAGLEVELS level,int index){
        String fName="[remCustomGagText]";
        try {
            logger.info(fName+"level="+level.getName()+", index="+index);
            JSONArray array=getCustomGagText(level);
            array.remove(index);
            logger.info(fName+"array="+array);
            gnCustomGagText.put(level.getName(),array);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityGag setTrainingGagEntries(JSONArray input){
        String fName="[setTrainingGagEntries]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.toString());
            gnTrainingGagEntries =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag addTrainingGagEntries(JSONObject input){
        String fName="[addTrainingGagEntries]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.toString());
            gnTrainingGagEntries.put(input);
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag remTrainingGagEntries(int index){
        String fName="[addTrainingGagEntries]";
        try {
            logger.info(fName+"index="+index);
            gnTrainingGagEntries.remove(index);
            logger.info(fName+"array="+gnTrainingGagEntries.toString());
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag setImageUrlOn(String input){
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
    public entityGag setImageUrlOff(String input){
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
    public entityGag setImageUrlSecure(String input){
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
    public entityGag setImageUrlUnSecure(String input){
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
    public entityGag setImageUrlStruggle(String input){
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
    public entityGag setTimeLockStarted(boolean input){
        String fName="[setTimeLockStarted]";
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
    public entityGag setTimeLockDuration(long input){
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
    public entityGag setTimeLockTimestamp(long input){
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
    public entityGag startTimeLock(){
        String fName="[startTimeLock]";
        try {
            logger.info(fName+"duration="+gnTimeLockDuration);
            if(gnTimeLockDuration<iRestraints.milliseconds_minute*15){
                logger.info(fName+"duration bellow 15 minutes>false");
                return null;
            }
            if(gnTimeLockDuration>iRestraints.milliseconds_day*14){
                logger.info(fName+"duration above 14 days>false");
                return null;
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            gnTimeLockTimestamp=timestamp.getTime();
            gnTimeLockStart=true;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag startTimeLock(long duration){
        String fName="[startTimeLock]";
        try {
            logger.info(fName+"duration="+duration);
            if(duration<iRestraints.milliseconds_minute*15){
                logger.info(fName+"duration bellow 15 minutes>false");
                return null;
            }
            if(duration>iRestraints.milliseconds_day*14){
                logger.info(fName+"duration above 14 days>false");
                return null;
            }
            gnTimeLockDuration=duration;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            gnTimeLockTimestamp=timestamp.getTime();
            gnTimeLockStart=true;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag startTimeLock(long duration,long timestamp){
        String fName="[startTimeLock]";
        try {
            logger.info(fName+"duration="+duration+", timestamp="+timestamp);
            if(duration<iRestraints.milliseconds_minute*15){
                logger.info(fName+"duration bellow 15 minutes>false");
                return null;
            }
            if(duration>iRestraints.milliseconds_day*14){
                logger.info(fName+"duration above 14 days>false");
                return null;
            }
            if(timestamp<=0){
                logger.info(fName+"timestamp is bellow or zero");
                return null;
            }
            gnTimeLockDuration=duration;
            gnTimeLockTimestamp=timestamp;
            gnTimeLockStart=true;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityGag stopTimeLock(){
       return stopTimeLock(true);
    }
    public entityGag stopTimeLock(boolean undogag){
        String fName="[stopTimeLock]";
        try {
            gnTimeLockDuration=15*iRestraints.milliseconds_minute;
            gnTimeLockTimestamp=0;
            gnTimeLockStart=false;
            if(undogag){
                setLevel(GAGLEVELS.None);
                setOn(false);
            }
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
                    gnLevel = jsonObject.getString(key);
                    break;
                case nType:
                    gnType = jsonObject.getString(key);
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
                case nTimeLockStart:
                    gnTimeLockStart= jsonObject.getBoolean(key);
                    break;
                case nTimeLockDuration:
                    gnTimeLockDuration= jsonObject.getLong(key);
                    break;
                case nTimeLockTimestamp:
                    gnTimeLockTimestamp= jsonObject.getLong(key);
                    break;
                case nDisable2Show4Wearer:
                    gnDisable2Show4Wearer = jsonObject.getBoolean(key);
                    break;
                case nDisable2Show4Others:
                    gnDisable2Show4Others = jsonObject.getBoolean(key);
                    break;
                case nCustomGagText:
                    gnCustomGagText =jsonObject.getJSONObject(key);
                    break;
                //
                case flagOnlyUseCustom:
                    gflagOnlyUseCustom =jsonObject.getBoolean(key);
                    break;
                case flagDisableGagOOCException:
                    gflagDisableGagOOCException =jsonObject.getBoolean(key);
                    break;
                case flagDisableGagSafeWords:
                    gflagDisableGagSafeWords =jsonObject.getBoolean(key);
                    break;
                case flagEnableCustom:
                    gflagEnableCustom =jsonObject.getBoolean(key);
                    break;
                case nException:
                    gnException =jsonObject.getBoolean(key);
                    break;
                case nExceptionList:
                    gnExceptionList =jsonObject.getJSONObject(key);
                    break;
                case nTrainingGagEntries:
                    gnTrainingGagEntries =jsonObject.getJSONArray(key);
                    break;
                case keyExtra:
                    extra.set(jsonObject.getJSONObject(key));
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
                case nPostRestrictEnabled:
                    gflagOnlyUseCustom = (boolean) value;
                    break;
                case nPostRestrictionException2UseGuildGag:
                    gflagEnableCustom = (boolean) value;
                    break;
                case nPostRestrictionException2UseUserGag:
                    gflagDisableGagOOCException = (boolean) value;
                    break;
                case nLevel:
                    gnLevel = (String) value;
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
                case nTimeLockStart:
                    gnTimeLockStart= (boolean) value;
                    break;
                case nTimeLockDuration:
                    gnTimeLockDuration= (long) value;
                    break;
                case nTimeLockTimestamp:
                    gnTimeLockTimestamp= (long) value;
                    break;
                case nDisable2Show4Wearer:
                    gnDisable2Show4Wearer = (boolean) value;
                    break;
                case nDisable2Show4Others:
                    gnDisable2Show4Others = (boolean) value;
                    break;
                case nCustomGagText:
                    gnCustomGagText = (JSONObject) value;
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
    public boolean checkGagTimelockDoRelease(){
        String fName="[checkGagTimelockDoRelease]";
        logger.info(fName );
        try {
            if(isTimeLocked()){
                logger.info(fName+"TimeLocked");
                if(doesItNeedTimeLockRelease()){
                    logger.info(fName+"stop timelock");
                    stopTimeLock();
                    logger.info(fName+"stopped timelock");
                    return true;
                }else{
                    logger.info(fName+"ignore");
                }
            }else{
                logger.info(fName+"No TimeLock Started");
            }
        }catch (Exception e) {
            logger.error(fName+" Exception:"+e);
        }
        return false;
    }
    public EXTRA extra=new EXTRA();
    public class EXTRA{
        public  EXTRA(){}
        public  String cName="[EXTRA]";
        public EXTRA set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(cName+fName+"jsonObject is null");
                    return null;
                }
                if(jsonObject.has(nGag)){
                    logger.info(cName+fName+"has key nGag");
                    jsonObject=jsonObject.getJSONObject(nGag);
                }
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
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
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean put(String key,JSONObject jsonObject){
            String fName="[put]";
            try {

                logger.info(cName+fName+"key="+key);
                switch (key){
                    case "isLaughterOn":
                        laughteron= jsonObject.getBoolean(key);
                        break;
                }
                return true;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean put(String key,Object value){
            String fName="[put]";
            try {
                logger.info(cName+fName+"key="+key);
                switch (key){
                    case "isLaughterOn":
                        laughteron= (boolean) value;
                        break;
                }
                return true;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public JSONObject getJSON(){
            String fName="[getJSON]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("isLaughterOn",laughteron);
                logger.info(cName+fName+"jsonObject="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(cName+fName + ".exception=" + e);
                logger.error(cName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        boolean laughteron=false;
        public boolean isLaughterOn(){
            String fName="[isLaughterOn]";
            try {
                logger.info(fName+"value="+laughteron);
                return laughteron;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public EXTRA setLaughterOn(boolean input){
            String fName="[setLaughterOn]";
            try {
                logger.info(fName+"input="+input);
                laughteron=input;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public EXTRA toggleLaughterOn(){
            String fName="[toggleLaughterOn]";
            try {
                logger.info(fName+"old="+laughteron);
                laughteron=!laughteron;
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public EXTRA clear(){
            String fName="[clear]";
            try {
                laughteron=false;
                logger.info(fName+"cleared");
                return  this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

    }
}
