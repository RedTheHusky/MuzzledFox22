package nsfw.diaper.modules.entities;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;


public class entityCaretaker {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityDiaper]";
    /*gUserProfile.safetyPutFieldEntry(field, keyEnabled,true);
            gUserProfile.safetyPutFieldEntry(field, keyType,typeDiaper_White);
            gUserProfile.safetyPutFieldEntry(field, keyMaxLevel,6);*/
    protected final String keyCaretakerId= iDiaperInteractive.keyCaretakerId,keyCaretakerAccepted=iDiaperInteractive.keyCaretakerAccepted;
    protected boolean gCaretakerAccepted=false;
    protected String gCaretakerId="";



    public entityCaretaker(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityCaretaker(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityCaretaker clear(){
        String fName="[clear]";
        try {
            gCaretakerAccepted=false;
            gCaretakerId="";

            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return false;
            }
            if(jsonObject.has(iDiaperInteractive.fieldCaretaker)){
                logger.info(fName+"has key fieldDiaper");
                jsonObject=jsonObject.getJSONObject(iDiaperInteractive.fieldCaretaker);
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
    public JSONObject getJSON(){
        String fName="[getJSON]";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(keyCaretakerAccepted,gCaretakerAccepted);
            jsonObject.put(keyCaretakerId,gCaretakerId);


            logger.info("jsonObject="+jsonObject.toString());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public boolean isAccepted(){
        String fName="[isAccepted]";
        try {
            logger.info(fName+"value="+gCaretakerAccepted);
            return gCaretakerAccepted;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getCaretakerID(){
        String fName="[getCaretakerID]";
        try {
            logger.info(fName+"value="+gCaretakerId);
            return gCaretakerId;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public Long getCaretakerIDLong(){
        String fName="[getCaretakerIDLong]";
        try {
            logger.info(fName+"value="+gCaretakerId);
            return Long.parseLong(gCaretakerId);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public entityCaretaker setAccepted(boolean input){
        String fName="[setAccepted]";
        try {
            logger.info(fName+"input="+input);
            gCaretakerAccepted=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCaretaker setCaretakerId(Member input){
        String fName="[setCaretakerId]";
        try {
            if(input==null){gCaretakerId="";return this;};
            logger.info(fName+"input="+input);
            gCaretakerId=input.getId();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCaretaker setCaretakerId(long input){
        String fName="[setCaretakerId]";
        try {
            logger.info(fName+"input="+input);
            if(input<=0){gCaretakerId="";return this;}
            gCaretakerId=String.valueOf(input);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityCaretaker setCaretakerId(String input){
        String fName="[setCaretakerId]";
        try {
            logger.info(fName+"input="+input);
            if(input==null)input="";
            gCaretakerId=input;
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
                case keyCaretakerAccepted:
                    gCaretakerAccepted= (boolean) value;
                    break;
                case keyCaretakerId:
                    gCaretakerId = (String) value;
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
                case keyCaretakerAccepted:
                    gCaretakerAccepted= jsonObject.optBoolean(key);
                    break;
                case keyCaretakerId:
                    gCaretakerId = jsonObject.optString(key);
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public entityCaretaker runaway() {
        String fName="[runaway]";
        try {
            setAccepted(false);setCaretakerId("");
            logger.info(fName+"done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public boolean hasCaretaker() {
        String fName="[runaway]";
        try {
            if(gCaretakerId==null)return false;
            if(gCaretakerId.isBlank())return false;
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
