package nsfw.diaper;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import nsfw.diaper.iDiaperInteractive.ACCESSLEVEL;

public class entityProfile {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityDiaper]";
    /* field=fieldProfile;
            gUserProfile.safetyCreateFieldEntry(field);
            gUserProfile.safetyPutFieldEntry(field, keyProfileLocked,false);
            gUserProfile.safetyPutFieldEntry(field, keyProfileAccess,nAccessPrivate);
            gUserProfile.safetyPutFieldEntry(field, keyProfileLockedBy,0);
            */
    protected final String fieldProfile =iDiaperInteractive.fieldProfile,
            keyProfileLocked=iDiaperInteractive.keyProfileLocked,
            keyProfileAccess=iDiaperInteractive.keyProfileAccess,
            keyProfileLockedBy=iDiaperInteractive.keyProfileLockedBy;

    protected boolean gProfileLocked =false;
    protected String gProfileAccess ="";
    protected long gProfileLockedBy =0;


    public entityProfile(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityProfile(JSONObject jsonObject){
        String fName="[constructor]";
        try {
           set(jsonObject);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public entityProfile clear(){
        String fName="[clear]";
        try {
            gProfileLocked =false;
            gProfileAccess =ACCESSLEVEL.Private.getString();
            gProfileLockedBy =0;

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
            if(jsonObject.has(fieldProfile)){
                logger.info(fName+"has key fieldDiaper");
                jsonObject=jsonObject.getJSONObject(fieldProfile);
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
            jsonObject.put(keyProfileLocked, gProfileLocked);
            jsonObject.put(keyProfileAccess, gProfileAccess);
            jsonObject.put(keyProfileLockedBy, gProfileLockedBy);

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
            logger.info(fName+"value="+ gProfileLocked);
            return gProfileLocked;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getAccessAsString(){
        String fName="[getAccess]";
        try {
            logger.info(fName+"value="+ gProfileAccess);
            return gProfileAccess;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public ACCESSLEVEL getAccess(){
        String fName="[getAccess]";
        try {
            logger.info(fName+"value="+ gProfileAccess);
            ACCESSLEVEL accesslevel=ACCESSLEVEL.valueByString(gProfileAccess);
            if(accesslevel==null)accesslevel=ACCESSLEVEL.INVALID;
            return accesslevel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return ACCESSLEVEL.INVALID;
        }
    }
    public long getLockedBy(){
        String fName="[getLockedBy]";
        try {
            logger.info(fName+"value="+ gProfileLockedBy);
            return gProfileLockedBy;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public entityProfile setLock(boolean input){
        String fName="[setLock]";
        try {
            logger.info(fName+"input="+input);
            gProfileLocked =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityProfile setAccess(String input){
        String fName="[setAccess]";
        try {
            logger.info(fName+"input="+input);
            gProfileAccess =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityProfile setAccess(ACCESSLEVEL input){
        String fName="[setAccess]";
        try {
            if(input==null){return null;}
            logger.info(fName+"input="+input);
            gProfileAccess =input.getString();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityProfile setLockedBy(Member member){
        String fName="[setLockedBy]";
        try {
            logger.info(fName+"member="+member.getId());
            gProfileLockedBy =member.getIdLong();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityProfile setLockedBy(long id){
        String fName="[setLockedBy]";
        try {
            logger.info(fName+"id="+id);
            gProfileLockedBy =id;
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
                case keyProfileLocked:
                    gProfileLocked = (boolean) value;
                    break;
                case keyProfileAccess:
                    gProfileAccess = (String) value;
                    break;
                case keyProfileLockedBy:
                    gProfileLockedBy = (long) value;
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
                case keyProfileLocked:
                    gProfileLocked = jsonObject.optBoolean(key);
                    break;
                case keyProfileAccess:
                    gProfileAccess = jsonObject.optString(key);
                    break;
                case keyProfileLockedBy:
                    gProfileLockedBy = jsonObject.optLong(key);
                    break;

            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public entityProfile runaway() {
        String fName="[runaway]";
        try {
            setLockedBy(0);setLock(false);setAccess(ACCESSLEVEL.Private);
            logger.info(fName+"done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public entityProfile lock(Member member) {
        String fName="[lock]";
        try {
            setLockedBy(member);setLock(true);
            logger.info(fName+"done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityProfile unlock() {
        String fName="[unlock]";
        try {
            setLockedBy(0);setLock(false);
            logger.info(fName+"done");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
