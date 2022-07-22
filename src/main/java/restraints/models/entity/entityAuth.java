package restraints.models.entity;

import kong.unirest.json.JSONObject;
import models.ls.lsMemberHelper;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.ACCESSLEVELS;

import java.util.*;


public class entityAuth {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityAuth]";
    /*  gUserProfile.safetyPutFieldEntry(nAccess, "");
    gUserProfile.safetyPutFieldEntry(nOwner,nOwnerId,""); gUserProfile.safetyPutFieldEntry(nOwner,nOwnerAccepted,false);gUserProfile.safetyPutFieldEntry(nOwner,vRedirectAsk2OwnerAsWell,false);
    gUserProfile.safetyPutFieldEntry(nOwner,nSecOnwers,new JSONObject());
     */


    final String nAccess=iRestraints.nAccess,
            nOwner =iRestraints.nOwner,nOwnerId=iRestraints.nOwnerId,nOwnerAccepted=iRestraints.nOwnerAccepted,vRedirectAsk2OwnerAsWell=iRestraints.vRedirectAsk2OwnerAsWell,
        nSecOnwers=iRestraints.nSecOnwers;
    String gnAccess= ACCESSLEVELS.Ask.getName(), gnOwnerId="";
    boolean gnOwnerAccepted=false,gvRedirectAsk2OwnerAsWell=false;
    JSONObject gnSecOnwers=new JSONObject();
    public JSONObject extraJsonObject=new JSONObject();

    public entityAuth(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityAuth(JSONObject jsonObject){
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
            gnAccess=ACCESSLEVELS.Ask.getName(); gnOwnerId="";
            gnOwnerAccepted=false;gvRedirectAsk2OwnerAsWell=false;
            gnSecOnwers=new JSONObject();
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityAuth set(JSONObject jsonObject){
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
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nAccess,gnAccess);
            JSONObject owner=new JSONObject();
            owner.put(nOwnerId,gnOwnerId);
            owner.put(nOwnerAccepted,gnOwnerAccepted);
            owner.put(vRedirectAsk2OwnerAsWell,gvRedirectAsk2OwnerAsWell);
            owner.put(nSecOnwers,gnSecOnwers);
            logger.info("owner="+owner.toString());
            jsonObject.put(nOwner,owner);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getOwnerJSON(){
        String fName="[getOwnerJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nOwnerId,gnOwnerId);
            jsonObject.put(nOwnerAccepted,gnOwnerAccepted);
            jsonObject.put(vRedirectAsk2OwnerAsWell,gvRedirectAsk2OwnerAsWell);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getSecOwnerJSON(){
        String fName="[getSecOwnerJSON]";
        try {
            logger.info("jsonObject="+gnSecOnwers.toString());
            return gnSecOnwers;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public List<String> getSecOwnerAsList(){
        String fName="[getSecOwnerAsList]";
        try {
            logger.info("jsonObject="+gnSecOnwers.toString());
            List<String>result=new ArrayList<>();
            Iterator<String>iteratorKeys=gnSecOnwers.keys();
            while (iteratorKeys.hasNext()){
                result.add(iteratorKeys.next());
            }
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public List<String> getSecOwnerAsUserMentionList(Guild guild){
        String fName="[getSecOwnerAsList]";
        try {
            logger.info("jsonObject="+gnSecOnwers.toString());
            List<String>result=new ArrayList<>();
            Iterator<String>iteratorKeys=gnSecOnwers.keys();
            while (iteratorKeys.hasNext()){
                String key=iteratorKeys.next();
                logger.info("key="+key);
                try {
                    User user= lsUserHelper.lsGetUser(guild,Long.parseLong(key));
                    logger.info(fName + ".user="+user.getName());
                    result.add(user.getAsMention());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    result.add("<"+key+">");
                }

            }
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public String getSecOwnerAsString(int index){
        String fName="[getSecOwnerAsString]";
        try {
            logger.info("index="+index);
            logger.info("jsonObject="+gnSecOnwers.toString());
            Iterator<String>iteratorKeys=gnSecOnwers.keys();
            Set<String> setKeys=gnSecOnwers.keySet();
            String result=(String)setKeys.toArray()[index];
            logger.info("result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getSecOwnerLength(){
        String fName="[getSecOwnerLength]";
        try {
            logger.info("jsonObject="+gnSecOnwers.length());
            return gnSecOnwers.length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isRedirectAsk2OwnerAsWell(){
        String fName="[isRedirectAsk2OwnerAsWell]";
        try {
            logger.info("value="+gvRedirectAsk2OwnerAsWell);
            return gvRedirectAsk2OwnerAsWell;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityAuth setRedirectAsk2OwnerAsWell(boolean input){
        String fName="[setRedirectAsk2OwnerAsWell]";
        try {
            logger.info("input="+input);
            gvRedirectAsk2OwnerAsWell=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isOwnerAccepted(){
        String fName="[isOwnerAccepted]";
        try {
            logger.info("value="+gnOwnerAccepted);
            return gnOwnerAccepted;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityAuth setOwnerAccepted(boolean input){
        String fName="[setOwnerAccepted]";
        try {
            logger.info("input="+input);
            gnOwnerAccepted=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityAuth setOwnerId(String input){
        String fName="[setOwnerId]";
        try {
            logger.info("input="+input);
            if(input==null){
                logger.info("input can't be null");
                return null;
            }
            gnOwnerId=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasOwner(){
        String fName="[hasOwner]";
        try {
            logger.info("value="+gnOwnerId);
            if(gnOwnerId.isBlank()){
                logger.info("is blank>false");
                return  false;
            }
            if(gnOwnerId.length()<10){
                logger.info("to small>false");
                return  false;
            }
            if( lsStringUsefullFunctions.String2Long(gnOwnerId)<=1000){
                logger.info("to small as long>false");
                return  false;
            }
            logger.info("default>true");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isOwned(){
        String fName="[isOwned]";
        try {
            if(!hasOwner()){
                logger.info("hasOwner==false>false");
                return  false;
            }
            if(!isOwnerAccepted()){
                logger.info("isOwnerAccepted==falsel>false");
                return  false;
            }

            logger.info("default>true");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isOwner(String id){
        String fName="[isOwner]";
        try {
            if(!hasOwner()){
                logger.info("hasOwner==false>false");
                return  false;
            }
            if(id==null){
                logger.info("input can't be null");
                return false;
            }
            logger.info("input="+id+", gnOwnerId="+gnOwnerId);
            if(gnOwnerId.equalsIgnoreCase(id)){
                logger.info("equals>false");
                return  true;
            }
            logger.info("default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isOwner(User user){
        String fName="[isOwner]";
        try {
            if(user==null){
                logger.info("input can't be null");
                return false;
            }

            return  isOwner(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isOwner(Member member){
        String fName="[isOwner]";
        try {
            if(member==null){
                logger.info("input can't be null");
                return false;
            }

            return  isOwner(member.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasSecOwner(String id){
        String fName="[hasSecOwner]";
        try {
            logger.info("id="+id);
            if(gnSecOnwers.has(id)){
                logger.info("found>true");
                return  true;
            }
            logger.info("default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean addSecOwner(String id){
        String fName="[addsSecOwner]";
        try {
            logger.info("id="+id);
            gnSecOnwers.put(id,true);
            logger.info("default>true");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remSecOwner(String id){
        String fName="[remSecOwner]";
        try {
            logger.info("id="+id);
            gnSecOnwers.remove(id);
            logger.info("default>true");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getOwnerId(){
        String fName="[getOwnerId]";
        try {
            logger.info("value="+gnOwnerId);
            return gnOwnerId;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public Long getOwnerIdAsLong(){
        String fName="[getOwnerIdAsLong]";
        try {
            logger.info("value="+gnOwnerId);
            Long l=Long.parseLong(gnOwnerId);
            logger.info("l="+l);
            return l;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public User getOwnerAsUser(Guild guild){
        String fName="[getOwnerAsUser]";
        try {
            String id=getOwnerId();
            User user= lsUserHelper.lsGetUser(guild,Long.parseLong(id));
            logger.info(fName + ".user="+user.getName());
            return  user;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getOwnerAsMember(Guild guild){
        String fName="[getOwnerAsMember]";
        try {
            String id=getOwnerId();
            Member user= lsMemberHelper.lsGetMember(guild, Long.parseLong(id));
            logger.info(fName + ".user="+user.getEffectiveName());
            return  user;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getAccessAsString(){
        String fName="[getAccessAsString]";
        try {
            logger.info("value="+gnAccess);
            return gnAccess;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public ACCESSLEVELS getAccess(){
        String fName="[getAccess]";
        try {
            logger.info("value="+gnAccess);
            ACCESSLEVELS accesslevels=ACCESSLEVELS.valueByName(gnAccess);
            if(accesslevels==null){
                logger.info("accesslevels is null>returl invalid");
                return  ACCESSLEVELS.INVALID;
            }
            return accesslevels;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return ACCESSLEVELS.INVALID;
        }
    }
    public entityAuth setAccess(String input){
        String fName="[setAccess]";
        try {
            logger.info("input="+input);
            if(input==null){
                logger.info("input can't be null");
                return null;
            }
            gnAccess=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityAuth setAccess(ACCESSLEVELS input){
        String fName="[setAccess]";
        try {
            logger.info("input="+input);
            if(input==null){
                logger.info("input can't be null");
                return null;
            }
            gnAccess=input.getName();
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
                case nAccess:
                    gnAccess =jsonObject.getString(key);
                    break;
                case nOwner:
                    JSONObject jsonObject2=jsonObject.getJSONObject(key);
                    logger.info(fName+"jsonObject2="+jsonObject2.toString());
                    Iterator<String> keys2=jsonObject2.keys();
                    while (keys2.hasNext()){
                        try {
                            String key2=keys2.next();
                            logger.info(fName+"key2="+key2);
                            switch (key2){
                                case nOwnerId:
                                    gnOwnerId =jsonObject2.getString(key2);
                                    break;
                                case nOwnerAccepted:
                                    gnOwnerAccepted =jsonObject2.getBoolean(key2);
                                    break;
                                case vRedirectAsk2OwnerAsWell:
                                    gvRedirectAsk2OwnerAsWell =jsonObject2.getBoolean(key2);
                                    break;
                                case nSecOnwers:
                                    gnSecOnwers =jsonObject2.getJSONObject(key2);
                                    break;
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
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
    public boolean hasUserOwnerAccess(String id){
        String fName="[hasUserOwnerAccess]";
        try {
            logger.info("id="+id+", gnOwnerId="+gnOwnerId);
            if(!hasOwner()){
                logger.info("has no owner>false");
                return  false;
            }
            if(gnOwnerId.equalsIgnoreCase(id)){
                logger.info("equals>true");
                return  true;
            }
            logger.info("default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserOwnerAccess(User user){
        String fName="[hasUserOwnerAccess]";
        try {
            return  hasUserOwnerAccess(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserSecOwnerAccess(String id){
        String fName="[hasUserSecOwnerAccess]";
        try {
            logger.info("id="+id+", gnOwnerId="+gnOwnerId);
            if(getSecOwnerLength()<=0){
                logger.info("has no secowners>false");
                return  false;
            }
            if(gnSecOnwers.has(id)){
                logger.info("has>true");
                return  true;
            }
            logger.info("default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserSecOwnerAccess(User user){
        String fName="[hasUserSecOwnerAccess]";
        try {
            return  hasUserSecOwnerAccess(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
