package restraints.models.entity;

import kong.unirest.json.JSONObject;
import models.ls.lsMemberHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;
import restraints.models.enums.LOCKTYPES;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityLock {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityBlindfold]";
    /*gUserProfile.safetyPutFieldEntry(nLocked, false);gUserProfile.safetyPutFieldEntry(nLockedBy, "");
            gUserProfile.safetyPutFieldEntry(nPermalocked, false);*/
    Guild guild;
    final String nLocked=iRestraints.nLocked,
            nLockedBy =iRestraints.nLockedBy,
            nPermalocked=iRestraints.nPermalocked,nLockType=iRestraints.nLockType;
    boolean gnLocked=false,gnPermalocked=false;
    String gnLockedByStr="",gnLockType="";
    public JSONObject extraJsonObject=new JSONObject();

    public entityLock(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLock(JSONObject jsonObject, Guild guild){
        String fName="[constructor]";
        try {
           set(jsonObject);
           set(guild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean clear(){
        String fName="[clear]";
        try {
            gnLocked=false;gnPermalocked=false;
            gnLockedByStr="";
            gnLockType="";
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityLock set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
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
    public entityLock set(Guild guild){
        String fName="[set]";
        try {
            if(guild==null){
                logger.info(fName+"guild is null");
                return null;
            }
            this.guild=guild;
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
            jsonObject.put(nLocked,gnLocked);
            jsonObject.put(nLockType,gnLockType);
            jsonObject.put(nLockedBy,gnLockedByStr);
            jsonObject.put(nPermalocked,gnPermalocked);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isLocked(){
        String fName="[isLocked]";
        try {
            logger.info(fName+"value="+gnLocked);
            return gnLocked;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPermaLocked(){
        String fName="[isPermaLocked]";
        try {
            logger.info(fName+"value="+gnPermalocked);
            return gnPermalocked;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public String getLockedByAsString(){
        String fName="[getLockedByAsString]";
        try {
            logger.info(fName+"value="+gnLockedByStr);
            return gnLockedByStr;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public Long getLockedByAsLong(){
        String fName="[getLockedByAsLong]";
        try {
            logger.info(fName+"value="+gnLockedByStr);
            return Long.parseLong(gnLockedByStr);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public LOCKTYPES getType(){
        String fName="[getType]";
        try {
            logger.info(fName+"value="+gnLockType);
            return LOCKTYPES.valueByName(gnLockType);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return LOCKTYPES.INVALID;
        }
    }
    public String getTypeAsString(){
        String fName="[getTypeAsString]";
        try {
            logger.info(fName+"value="+gnLockType);
            return gnLockType;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityLock setLocked(boolean input){
        String fName="[setLocked]";
        try {
            logger.info(fName+"input="+input);
            gnLocked=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLock setPermaLocked(boolean input){
        String fName="[setPermaLocked]";
        try {
            logger.info(fName+"input="+input);
            gnPermalocked=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLock setLockedBy(String input){
        String fName="[setLockedBy]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                return null;
            }
            gnLockedByStr=input;

            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLock setLockedBy(Long input){
        String fName="[setLockedBy]";
        try {
            logger.info(fName+"input="+input);
            gnLockedByStr=String.valueOf(input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLock setType(String input){
        String fName="[setType]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnLockType=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityLock setType(LOCKTYPES input){
        String fName="[setType]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.getName());
            gnLockType=input.getName();
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
                case nLocked:
                    gnLocked= (boolean)value;
                    break;
                case nPermalocked:
                    gnPermalocked= (boolean)value;
                    break;
                case nLockType:
                    gnLockType =(String) value;
                    break;
                case nLockedBy:
                    gnLockedByStr= (String) value;
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
                case nLocked:
                    gnLocked= jsonObject.getBoolean(key);
                    break;
                case nPermalocked:
                    gnPermalocked= jsonObject.getBoolean(key);
                    break;
                case nLockType:
                    gnLockType =jsonObject.getString(key);
                    break;
                case nLockedBy:
                    try {
                        Object o=jsonObject.get(key);
                        gnLockedByStr=jsonObject.getString(key);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean isLocked_checkMemberAsKeyholder(Member member){
        String fName="[isLocked_checkMemberAsKeyholder]";
        try {
           if(!isLocked()){
               logger.info(fName+" not locked>false");
               return false;
           }
           if(getLockedByAsLong()==member.getIdLong()){
               logger.info(fName+" is keyholder>false");
               return false;
           }
            logger.info(fName+"is locked and not keyholder>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public String getUserWhoLockedPet(){
        String fName="[getUserWhoLockedPet]";
        try {
            String lockedby=getLockedByAsString();
            if(lockedby!=null&&!lockedby.isEmpty()&&!lockedby.isBlank()){
                String mention= lsMemberHelper.lsGetMemberMention(guild,lockedby);
                if(mention==null||mention.isEmpty()||mention.isBlank()){
                    return "<"+lockedby+">";
                }
                return mention;
            }
            return "n/a";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "error";
        }
    }


}
