package restraints.models.entity;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.CONFINELEVELS;

import java.util.Arrays;
import java.util.Iterator;


public class entityConfine {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityConfine]";
    /*gUserProfile.safetyCreateFieldEntry(nEarMuffs);
            gUserProfile.safetyPutFieldEntry(nEarMuffs,nOn,false);
            gUserProfile.safetyPutFieldEntry(nEarMuffs,nLevel,nNone);*/

    final String nOn=iRestraints.nOn,
            nLevel =iRestraints.nLevel, nConfine=iRestraints.nConfine,nID=iRestraints.nID;
    boolean gnOn=false;
    String gnLevel="",gnID="0";
    public JSONObject extraJsonObject=new JSONObject();

    public entityConfine(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityConfine(JSONObject jsonObject){
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
            gnLevel="";gnID="0";
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityConfine set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nConfine)){
                logger.info(fName+"has key nBlindfold");
                jsonObject=jsonObject.getJSONObject(nConfine);
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
            jsonObject.put(nID,gnID);
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
    public boolean isBlanIDk(){
        String fName="[isBlankID]";
        try {
            logger.info(fName+"value="+gnID);
            if(gnID.isBlank()){
                logger.info(fName+"blank>true");
                return true;
            }
            logger.info(fName+"default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public String getID(){
        String fName="[getID]";
        try {
            logger.info(fName+"value="+gnID);
            return gnID;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public CONFINELEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+gnLevel);
            return CONFINELEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return CONFINELEVELS.INVALID;
        }
    }
    public entityConfine setOn(boolean input){
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
    public entityConfine setLevel(String input){
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
    public entityConfine setID(String input){
        String fName="[setID]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnID=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityConfine setLevel(CONFINELEVELS input){
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
    public entityConfine release(){
        String fName="[release]";
        try {
            setOn(false);
            setLevel(CONFINELEVELS.None);
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
                case nID:
                    gnID= jsonObject.getString(key);
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
                case nID:
                    gnID= (String) value;
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
            CONFINELEVELS level=getLevel();
            if(level==null){
                logger.info(fName + ".isnull>false");
                return false;
            }
            if(level==CONFINELEVELS.INVALID){
                logger.info(fName + ".level is invalid>false");
                return false;
            }
            if(level==CONFINELEVELS.None){
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
            CONFINELEVELS level=getLevel();
            if(level==null){
                logger.info(fName + ".isnull>false");
                return false;
            }
            if(level==CONFINELEVELS.INVALID){
                logger.info(fName + ".level is invalid>false");
                return false;
            }
            if(level==CONFINELEVELS.None){
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
    public boolean isConfined(){
        String fName="[isConfined]";
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
    public boolean isSameConfinement(lcGlobalHelper gGlobal, Member member){
        String fName="[isSameConfinement]";
        try {
            logger.info(fName+"member="+member.getIdLong());
            lcJSONUserProfile userProfile=iRestraints.getThisPersonProfile(gGlobal,member);
            JSONObject userJSON=new JSONObject();
            if(userProfile!=null&userProfile.jsonObject !=null&&userProfile.jsonObject.has(iRestraints.nConfine)){
                userJSON=userProfile.jsonObject.getJSONObject(iRestraints.nConfine);
            }else{
                logger.info(fName + ".userprofile invalid>false");
                return false;
            }
            logger.info(fName + ".userJSON="+userJSON.toString());
            if(!gnLevel.equalsIgnoreCase(userJSON.getString(iRestraints.nLevel))){
                logger.info(fName + ".not same level>false");
                return false;
            }
            if(!gnID.equalsIgnoreCase(userJSON.getString(iRestraints.nID))){
                logger.info(fName + ".not same ID>false");
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
    public boolean isSameConfinementLevel(lcGlobalHelper gGlobal, Member member){
        String fName="[isSameConfinementLevel]";
        try {
            logger.info(fName+"member="+member.getIdLong());
            lcJSONUserProfile userProfile=iRestraints.getThisPersonProfile(gGlobal,member);
            JSONObject userJSON=new JSONObject();
            if(userProfile!=null&userProfile.jsonObject !=null&&userProfile.jsonObject.has(iRestraints.nConfine)){
                userJSON=userProfile.jsonObject.getJSONObject(iRestraints.nConfine);
            }else{
                logger.info(fName + ".userprofile invalid>false");
                return false;
            }
            logger.info(fName + ".userJSON="+userJSON.toString());
            if(!gnLevel.equalsIgnoreCase(userJSON.getString(iRestraints.nLevel))){
                logger.info(fName + ".not same level>false");
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
    public boolean isSameConfinementID(lcGlobalHelper gGlobal, Member member){
        String fName="[isSameConfinementID]";
        try {
            logger.info(fName+"member="+member.getIdLong());
            lcJSONUserProfile userProfile=iRestraints.getThisPersonProfile(gGlobal,member);
            JSONObject userJSON=new JSONObject();
            if(userProfile!=null&userProfile.jsonObject !=null&&userProfile.jsonObject.has(iRestraints.nConfine)){
                userJSON=userProfile.jsonObject.getJSONObject(iRestraints.nConfine);
            }else{
                logger.info(fName + ".userprofile invalid>false");
                return false;
            }
            logger.info(fName + ".userJSON="+userJSON.toString());
            if(!gnID.equalsIgnoreCase(userJSON.getString(iRestraints.nID))){
                logger.info(fName + ".not same ID>false");
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
    public boolean isAuthorConfinedAndNotSameConfinment(lcGlobalHelper gGlobal, Member author){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName = "[isAuthorConfined]";
        logger.info(fName);
        try {
            lcJSONUserProfile userProfile=iRestraints.getThisPersonProfile(gGlobal,author);
            entityConfine authorConfine=new entityConfine();
            authorConfine.set(userProfile.jsonObject);
            boolean isauthorconfined=  authorConfine.isConfined();
            logger.info(fName+"isauthorconfined="+isauthorconfined);
            if(!isauthorconfined){
                logger.info(fName+"author is not confined>false");
                return  false;
            }
            if(!isConfined()){
                logger.info(fName+"target not confined>true");
                return  true;
            }
            String authorCell=authorConfine.getLevelAsString()+"#"+authorConfine.getID();
            logger.info(fName+"authorCell="+ authorCell);
            String targetCell=getLevelAsString()+"#"+getID();
            logger.info(fName+"targetCell="+ authorCell);
            if(authorCell.equalsIgnoreCase(targetCell)){
                logger.info(fName+"equal>false");
                return  false;
            }
            logger.info(fName+"not equal>true");
            return  true;
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }
}
