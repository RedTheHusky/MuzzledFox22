package nsfw.lovense;

import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.HOODLEVELS;

import java.util.Arrays;
import java.util.Iterator;

import static nsfw.lovense.iLovense.gUrlLovesenseCommand;


public class entityLovensenseToyNet {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityLovensenseToy]";
    /*"de68826ef711": {
			"nickName": "",
			"name": "hush",
			"id": "de68826ef711",
			"status": 1
		}*/
    final String keyId=iLovense.keyToyId,
            keyNickname=iLovense.keyToyNickName,
            keyName=iLovense.keyToyName,
            keyStatus=iLovense.keyToyStatus;
    String valueId="",
            valueNickname="",
            valueName="";
    int valueStatus=0;

    public entityLovensenseToyNet(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityLovensenseToyNet(JSONObject jsonObject){
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
            valueId="";
            valueNickname="";
            valueName="";
            valueStatus=0;
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
            logger.info(fName+"jsonObject="+jsonObject.toString());
            if(!jsonObject.isEmpty()){
                Iterator<String> keys=jsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    put(key,jsonObject);
                }
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
                case keyStatus:
                    valueStatus= jsonObject.getInt(key);
                    break;
                case keyId:
                    valueId= jsonObject.getString(key);
                    break;
                case keyName:
                    valueName= jsonObject.getString(key);
                    break;
                case keyNickname:
                    valueNickname= jsonObject.getString(key);
                    break;
            }
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
            jsonObject.put(keyId,valueId);
            jsonObject.put(keyName,valueName);
            jsonObject.put(keyNickname,valueNickname);
            jsonObject.put(keyStatus,valueStatus);
            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getID(){
        String fName="[getID]";
        try {
            logger.info("value="+valueId);
            return valueId;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getName(){
        String fName="[getName]";
        try {
            logger.info("value="+valueName);
            return valueName;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public String getNickname(){
        String fName="[getNickname]";
        try {
            logger.info("value="+valueNickname);
            return valueNickname;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getStatus(){
        String fName="[getStatus]";
        try {
            logger.info("value="+valueStatus);
            return valueStatus;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean setID(String input){
        String fName="[setID]";
        try {
            if(input==null){
                logger.info("input can't be null");
                return false;
            }
            logger.info("input="+input);
            valueId=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setName(String input){
        String fName="[setName]";
        try {
            if(input==null){
                logger.info("input can't be null");
                return false;
            }
            logger.info("input="+input);
            valueName=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setNickname(String input){
        String fName="[setNickname]";
        try {
            if(input==null){
                logger.info("input can't be null");
                return false;
            }
            logger.info("input="+input);
            valueNickname=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setStatus(int input){
        String fName="[setStatus]";
        try {
            logger.info("input="+input);
            valueStatus=input;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    //https://www.lovense.com/sextoys/developer/doc#standard-api

}
