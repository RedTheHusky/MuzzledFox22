package restraints.models.entity.pishock;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class entityPiShock {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityPiShock]";
    /*gUserProfile.safetyCreateFieldEntry(nPishock);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockUsername,"");
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockApikey,"");
            gUserProfile.safetyPutFieldEntry(nPishock,nEnabled,false);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockShock4CollarEnabled,true);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockShock4ChastityEnabled,true);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockSelected,-1);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockShockers,new JSONArray());
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockCollarShocker,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nPishock,nPiShockChastityShocker,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockGameWinShocker,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockGameLoseShocker,new JSONObject());*/
   final String
       nPishock=iRestraints.nPishock,
       nPishockUsername=iRestraints.nPishockUsername,
       nPishockApikey=iRestraints.nPishockApikey,
       nEnabled=iRestraints.nEnabled,
       nPishockShock4CollarEnabled=iRestraints.nPishockShock4CollarEnabled,
       nPishockShock4ChastityEnabled=iRestraints.nPishockShock4ChastityEnabled,
       nPishockSelected=iRestraints.nPishockSelected,
       nPishockShockers=iRestraints.nPishockShockers,
       nPishockCollarShocker=iRestraints.nPishockCollarShocker,
       nPiShockChastityShocker=iRestraints.nPiShockChastityShocker,
       nPishockGameWinShocker=iRestraints.nPishockGameWinShocker,
       nPishockGameLoseShocker=iRestraints.nPishockGameLoseShocker;
    final String  nMembers=iRestraints.nMembers,
           fieldMode=iRestraints.fieldMode;
    String gnPishockUsername="",
            gnPishockApikey="";
    boolean gnEnabled=false,
            gnPishockShock4CollarEnabled=true,
            gnPishockShock4ChastityEnabled=true;
    int gnPishockSelected=-1;
    JSONArray gnPishockShockers=new JSONArray();
    JSONObject gnPishockCollarShocker=new JSONObject(),
                gnPiShockChastityShocker=new JSONObject(),
                gnPishockGameWinShocker=new JSONObject(),
                gnPishockGameLoseShocker=new JSONObject();
    public  JSONObject gnMembers=new JSONObject();
    int gfieldMode=0;
    public JSONObject extraJsonObject=new JSONObject();
    public entityPiShock(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityPiShock(JSONObject jsonObject){
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
            gnPishockUsername="";
            gnPishockApikey="";
            gnEnabled=false;
            gnPishockShock4CollarEnabled=true;
            gnPishockShock4ChastityEnabled=true;
            gnPishockSelected=-1;
            gnPishockShockers=new JSONArray();
            gnPishockCollarShocker=new JSONObject();
            gnPiShockChastityShocker=new JSONObject();
            gnPishockGameWinShocker=new JSONObject();
            gnPishockGameLoseShocker=new JSONObject();
            gnMembers=new JSONObject();
            gfieldMode=0;
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

            if(jsonObject.has(nPishock)){
                logger.info(fName+"has key nPishock");
                jsonObject=jsonObject.getJSONObject(nPishock);
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
            jsonObject.put(nPishockUsername,gnPishockUsername);
            jsonObject.put(nPishockApikey,gnPishockApikey);
            jsonObject.put(nEnabled,gnEnabled);
            jsonObject.put(nPishockShock4CollarEnabled,gnPishockShock4CollarEnabled);
            jsonObject.put(nPishockShock4ChastityEnabled,gnPishockShock4ChastityEnabled);
            jsonObject.put(nPishockSelected,gnPishockSelected);
            jsonObject.put(nPishockShockers,gnPishockShockers);
            jsonObject.put(nPishockCollarShocker,gnPishockCollarShocker);
            jsonObject.put(nPiShockChastityShocker,gnPiShockChastityShocker);
            jsonObject.put(nPishockGameWinShocker,gnPishockGameWinShocker);
            jsonObject.put(nPishockGameLoseShocker,gnPishockGameLoseShocker);
            jsonObject.put(nMembers,gnMembers);
            jsonObject.put(fieldMode,gfieldMode);
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
    public entityPiShock setUsername(String input){
        String fName="[setUsername]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnPishockUsername=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getUsername(){
        String fName="[getUsername]";
        try {
            logger.info(fName+"value="+gnPishockUsername);
            return gnPishockUsername;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getApiKey(){
        String fName="[getApiKey]";
        try {
            logger.info(fName+"value="+gnPishockApikey);
            return gnPishockApikey;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean setApiKey(String input){
        String fName="[setApiKey]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return false;
            }
            logger.info(fName+"input="+input);
            gnPishockApikey=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEnabled(){
        String fName="[isEnabled]";
        try {
            logger.info(fName+"value="+gnEnabled);
            return gnEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isShock4CollarEnabled(){
        String fName="[isShock4CollarEnabled]";
        try {
            logger.info(fName+"value="+gnPishockShock4CollarEnabled);
            return gnPishockShock4CollarEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isShock4ChastityEnabled(){
        String fName="[isShock4ChastityEnabled]";
        try {
            logger.info(fName+"value="+gnPishockShock4ChastityEnabled);
            return gnPishockShock4ChastityEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityPiShock setEnabled(boolean input){
        String fName="[setEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnEnabled=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setShock4CollarEnabled(boolean input){
        String fName="[setShock4CollarEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnPishockShock4CollarEnabled=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setShock4ChastityEnabled(boolean input){
        String fName="[setShock4ChastityEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnPishockShock4ChastityEnabled=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getMainShockerIndex(){
        String fName="[getMainShockerIndex]";
        try {
            logger.info(fName+"value="+gnPishockSelected);
            return gnPishockSelected;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONObject getMainShockerJSON(){
        String fName="[getMainShockerJSON]";
        try {
            JSONObject jsonObject=gnPishockShockers.getJSONObject(gnPishockSelected);
            logger.info(fName+"value="+jsonObject);
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public entityShocker getMainShocker(){
        String fName="[getMainShocker]";
        try {
            entityShocker cSHOCKER=new entityShocker();
            cSHOCKER.set( gnPishockShockers.getJSONObject(gnPishockSelected));
            logger.info(fName+"value="+cSHOCKER.getJSON());
            return cSHOCKER;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShocker();
        }
    }
    public entityPiShock setMainShocker(int input){
        String fName="[setMainShocker]";
        try {
            logger.info(fName+"input="+input);
            gnPishockSelected=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isMainShocker(int index){
        String fName="[isMainShocker]";
        try {
            logger.info(fName+"index="+index);
            if(gnPishockSelected==index)return true;
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMainShocker(entityShocker input){
        String fName="[setMainShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return false;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"input="+jsonObject);
            gnPishockShockers.put(gnPishockSelected,jsonObject);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public JSONArray getShockersJSON(){
        String fName="[getShockersJSON]";
        try {
            logger.info(fName+"value="+gnPishockShockers.toString());
            return gnPishockShockers;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public int getShockersSize(){
        String fName="[getShockersSize]";
        try {
            logger.info(fName+"value="+gnPishockShockers.length());
            return gnPishockShockers.length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isShockersEmpty(){
        String fName="[isShockersEmpty]";
        try {
            logger.info(fName+"value="+gnPishockShockers.length());
            return gnPishockShockers.isEmpty();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public List<entityShocker> getShockers(){
        String fName="[getShockers]";
        try {
            logger.info(fName+"value="+gnPishockShockers.toString());
           List<entityShocker>shockers=new ArrayList<>();
           for(int i=0;i<gnPishockShockers.length();i++){
               try {
                   JSONObject jsonObject=gnPishockShockers.getJSONObject(i);
                   logger.info(fName+"array["+i+"]="+jsonObject.toString());
                   entityShocker shocker=new entityShocker(jsonObject);
                   shockers.add(shocker);
               }catch (Exception e){
                   logger.error(fName + ".exception=" + e);
                   logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
               }
           }
           return shockers;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public entityPiShock setShockers(JSONArray input){
        String fName="[setShockersJSON]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"value="+input.toString());
            gnPishockShockers=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setShockers(List<entityShocker> input){
        String fName="[setShockersJSON]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"value="+input.toString());
            JSONArray jsonArray=new JSONArray();
            for(int i=0;i<input.size();i++){
                try {
                    JSONObject jsonObject=input.get(i).getJSON();
                    logger.info(fName+"shocker["+i+"]="+jsonObject.toString());
                    jsonArray.put(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            gnPishockShockers=jsonArray;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock clearShockers(){
        String fName="[clearShockers]";
        try {
            gnPishockShockers=new JSONArray();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock remShocker(int index){
        String fName="[remShocker]";
        try {
            logger.info(fName+"value="+index);
            gnPishockShockers.remove(index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityShocker getShocker(int index){
        String fName="[getShocker]";
        try {
            logger.info(fName+"value="+index);
            JSONObject jsonObject=gnPishockShockers.getJSONObject(index);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            entityShocker shocker=new entityShocker(jsonObject);
            return shocker;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShocker();
        }
    }
    public JSONObject getShockerJSON(int index){
        String fName="[getShockerJSON]";
        try {
            logger.info(fName+"value="+index);
            JSONObject jsonObject=gnPishockShockers.getJSONObject(index);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public entityPiShock addShocker(JSONObject input){
        String fName="[addShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"value="+input.toString());
            gnPishockShockers.put(input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock addShocker(entityShocker input){
        String fName="[addShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"value="+jsonObject.toString());
            gnPishockShockers.put(jsonObject);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setShocker(int index,JSONObject input){
        String fName="[setShocker]";
        try {
            logger.info(fName+"index="+index);
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"value="+input.toString());
            gnPishockShockers.put(index,input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setShocker(int index,entityShocker input){
        String fName="[setShocker]";
        try {
            logger.info(fName+"index="+index);
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"value="+jsonObject.toString());
            gnPishockShockers.put(index,jsonObject);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getCollarShockerJSON(){
        String fName="[getCollarShockerJSON]";
        try {
            logger.info(fName+"value="+gnPishockCollarShocker.toString());
            return gnPishockCollarShocker;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getChastityShockerJSON(){
        String fName="[getChastityShockerJSON]";
        try {
            logger.info(fName+"value="+gnPiShockChastityShocker.toString());
            return gnPiShockChastityShocker;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getGameWinShockerJSON(){
        String fName="[getGameWinShockerJSON]";
        try {
            logger.info(fName+"value="+gnPishockGameWinShocker.toString());
            return gnPishockGameWinShocker;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getGameLoseShockerJSON(){
        String fName="[getGameLoseShockerJSON]";
        try {
            logger.info(fName+"value="+gnPishockGameLoseShocker.toString());
            return gnPishockGameLoseShocker;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public entityShocker getCollarShocker(){
        String fName="[getCollarShocker]";
        try {
            entityShocker cSHOCKER=new entityShocker();
            cSHOCKER.set( gnPishockCollarShocker);
            logger.info(fName+"value="+cSHOCKER.getJSON());
            return cSHOCKER;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShocker();
        }
    }
    public entityShocker getChastityShocker(){
        String fName="[getChastityShocker]";
        try {
            entityShocker cSHOCKER=new entityShocker();
            cSHOCKER.set( gnPiShockChastityShocker);
            logger.info(fName+"value="+cSHOCKER.getJSON());
            return cSHOCKER;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShocker();
        }
    }
    public entityShocker getGameWinShocker(){
        String fName="[getGameWinShocker]";
        try {
            entityShocker cSHOCKER=new entityShocker();
            cSHOCKER.set( gnPishockGameWinShocker);
            logger.info(fName+"value="+cSHOCKER.getJSON());
            return cSHOCKER;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShocker();
        }
    }
    public entityShocker getGameLoseShocker(){
        String fName="[getGameLoseShocker]";
        try {
            entityShocker cSHOCKER=new entityShocker();
            cSHOCKER.set( gnPishockGameLoseShocker);
            logger.info(fName+"value="+cSHOCKER.getJSON());
            return cSHOCKER;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new entityShocker();
        }
    }
    public entityPiShock setCollarShocker(JSONObject input){
        String fName="[setCollarShockerJSON]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnPishockCollarShocker=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setChastityShocker(JSONObject input){
        String fName="[setChastityShockerJSON]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnPiShockChastityShocker=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setGameWinShocker(JSONObject input){
        String fName="[setCollarShockerJSON]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnPishockGameWinShocker=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setGameLoseShocker(JSONObject input){
        String fName="[setGameLoseShockerJSON]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnPishockGameLoseShocker=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setCollarShocker(entityShocker input){
        String fName="[setCollarShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"value="+jsonObject.toString());
            gnPishockCollarShocker=jsonObject;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setChastityShocker(entityShocker input){
        String fName="[setChastityShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"value="+jsonObject.toString());
            gnPiShockChastityShocker=jsonObject;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setGameWinShocker(entityShocker input){
        String fName="[setCollarShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"value="+jsonObject.toString());
            gnPishockGameWinShocker=jsonObject;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setGameLoseShocker(entityShocker input){
        String fName="[setGameLoseShocker]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            JSONObject jsonObject=input.getJSON();
            logger.info(fName+"value="+jsonObject.toString());
            gnPishockGameLoseShocker=jsonObject;
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
                case nPishockUsername:
                    gnPishockUsername= jsonObject.getString(key);
                    break;
                case nPishockApikey:
                    gnPishockApikey= jsonObject.getString(key);
                    break;
                case nEnabled:
                    gnEnabled= jsonObject.getBoolean(key);
                    break;
                case nPishockShock4CollarEnabled:
                    gnPishockShock4CollarEnabled= jsonObject.getBoolean(key);
                    break;
                case nPishockShock4ChastityEnabled:
                    gnPishockShock4ChastityEnabled= jsonObject.getBoolean(key);
                    break;
                case nPishockSelected:
                    gnPishockSelected= jsonObject.getInt(key);
                    break;
                case nPishockShockers:
                    gnPishockShockers= jsonObject.getJSONArray(key);
                    break;
                case nPishockCollarShocker:
                    gnPishockCollarShocker= jsonObject.getJSONObject(key);
                    break;
                case nPiShockChastityShocker:
                    gnPiShockChastityShocker= jsonObject.getJSONObject(key);
                    break;
                case nPishockGameWinShocker:
                    gnPishockGameWinShocker= jsonObject.getJSONObject(key);
                    break;
                case nPishockGameLoseShocker:
                    gnPishockGameLoseShocker= jsonObject.getJSONObject(key);
                    break;
                case nMembers:
                    gnMembers= jsonObject.getJSONObject(key);
                    break;
                case fieldMode:
                    gfieldMode= jsonObject.getInt(key);
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
                case nPishockUsername:
                    gnPishockUsername= (String) value;
                    break;
                case nPishockApikey:
                    gnPishockApikey= (String) value;
                    break;
                case nEnabled:
                    gnEnabled= (boolean) value;
                    break;
                case nPishockShock4CollarEnabled:
                    gnPishockShock4CollarEnabled= (boolean) value;
                    break;
                case nPishockShock4ChastityEnabled:
                    gnPishockShock4ChastityEnabled= (boolean) value;
                    break;
                case nPishockSelected:
                    gnPishockSelected= (int) value;
                    break;
                case nPishockShockers:
                    gnPishockShockers= (JSONArray) value;
                    break;
                case nPishockCollarShocker:
                    gnPishockCollarShocker= (JSONObject) value;
                    break;
                case nPiShockChastityShocker:
                    gnPiShockChastityShocker= (JSONObject) value;
                    break;
                case nPishockGameWinShocker:
                    gnPishockGameWinShocker= (JSONObject) value;
                    break;
                case nPishockGameLoseShocker:
                    gnPishockGameLoseShocker= (JSONObject) value;
                    break;
                case nMembers:
                    gnMembers= (JSONObject) value;
                    break;
                case fieldMode:
                    gfieldMode= (int) value;
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
    public int getFieldMode(){
        String fName="[getFieldMode]";
        try {
            logger.info(fName+"value="+gfieldMode);
            return gfieldMode;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public entityPiShock setFieldMode(int input){
        String fName="[getFieldMode]";
        try {
            logger.info(fName+"input="+input);
            gfieldMode=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    final String valueWhite=iRestraints.valueWhite,valueBlack=iRestraints.valueBlack;
    public JSONArray geMembersWhiteList(){
        String fName="[geMembersWhiteList]";
        try {
            JSONArray jsonArray=gnMembers.getJSONArray(valueWhite);
            logger.info(fName+"value="+jsonArray.toString());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONArray geMembersBlackList(){
        String fName="[geMembersBlackList]";
        try {
            JSONArray jsonArray=gnMembers.getJSONArray(valueBlack);
            logger.info(fName+"value="+jsonArray.toString());
            return jsonArray;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public entityPiShock setMembersWhiteList(JSONArray input){
        String fName="[setMembersWhiteList]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input.toString());
            gnMembers.put(valueWhite,input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityPiShock setMembersBlackList(JSONArray input){
        String fName="[setMembersBlackList]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input.toString());
            gnMembers.put(valueBlack,input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
