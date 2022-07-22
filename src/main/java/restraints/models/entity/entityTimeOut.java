package restraints.models.entity;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entityTimeOut {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityTimeOut]";
    /*gUserProfile.safetyCreateFieldEntry(nTimeOut);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nOn,false);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nTimeOutMode,0);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nChannel,0);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nTimeOut1Duration,0);jsonObject.put(nTimeOut1Timestamp,0);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nTimeoutModeTimer,jsonObject);
            jsonObject=new JSONObject();
            jsonObject.put(nTimeOut2Count,0);jsonObject.put(nTimeOut2Done,0);jsonObject.put(nTimeOut2Sentence,"");
            gUserProfile.safetyPutFieldEntry(nTimeOut,nTimeoutModeWritting,jsonObject);*/
   final String
        nTimeOut=iRestraints.nTimeOut,
        nOn=iRestraints.nOn,
        nTimeOutMode=iRestraints.nTimeOutMode,
        nChannel=iRestraints.nChannel,
        nTimeoutModeTimer=iRestraints.nTimeoutModeTimer,nTimeOut1Duration=iRestraints.nTimeOut1Duration,nTimeOut1Timestamp=iRestraints.nTimeOut1Timestamp,
        nTimeoutModeWritting=iRestraints.nTimeoutModeWritting,nTimeOut2Count=iRestraints.nTimeOut2Count,nTimeOut2Done=iRestraints.nTimeOut2Done,nTimeOut2Sentence=iRestraints.nTimeOut2Sentence;
   final String nTimeLockMin=iRestraints.nTimeLockMin;
   boolean gnOn=false;
   int gnTimeOutMode=0;
   long  gnChannel=0,
   gnTimeOut1Duration=0,gnTimeOut1Timestamp=0,gnTimeLockMin=0;
   int gnTimeOut2Count=0,gnTimeOut2Done=0;
   String gnTimeOut2Sentence="";
    public JSONObject extraJsonObject=new JSONObject();

    public entityTimeOut(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityTimeOut(JSONObject jsonObject){
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
            gnTimeOutMode=0;
            gnChannel=0;gnTimeOut1Duration=0;gnTimeOut1Timestamp=0;
            gnTimeOut2Count=0;gnTimeOut2Done=0;
            gnTimeOut2Sentence="";
            gnTimeLockMin=0;
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityTimeOut set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nTimeOut)){
                logger.info(fName+"has key nTimeOut");
                jsonObject=jsonObject.getJSONObject(nTimeOut);
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
            jsonObject.put(nTimeOutMode,gnTimeOutMode);
            jsonObject.put(nChannel,gnChannel);
            JSONObject jsonObject2=new JSONObject();
            jsonObject2.put(nTimeOut1Duration,gnTimeOut1Duration);jsonObject2.put(nTimeOut1Timestamp,gnTimeOut1Timestamp);
            jsonObject.put(nTimeoutModeTimer,jsonObject2);
            jsonObject2=new JSONObject();
            jsonObject2.put(nTimeOut2Count,gnTimeOut2Count);jsonObject2.put(nTimeOut2Done,gnTimeOut2Done);jsonObject2.put(nTimeOut2Sentence,gnTimeOut2Sentence);
            jsonObject.put(nTimeoutModeWritting,jsonObject2);
            if(!extraJsonObject.isEmpty()){
                logger.info("extraJsonObject="+extraJsonObject.toString());
                Iterator<String>keys=extraJsonObject.keys();
                while (keys.hasNext()){
                    String key=keys.next();
                    jsonObject.put(key,extraJsonObject.get(key));
                }
            }
            logger.info(fName+"jsonObject="+jsonObject.toString());
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
    public int getTimeOutMode(){
        String fName="[getTimeOutMode]";
        try {
            logger.info(fName+"value="+gnTimeOutMode);
            return gnTimeOutMode;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getChannel(){
        String fName="[getChannel]";
        try {
            logger.info(fName+"value="+gnChannel);
            return gnChannel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public TextChannel getTextChannel(Guild guild){
        String fName="[getChannel]";
        try {
            logger.info(fName+"value="+gnChannel);
            if(gnChannel<=0){
                return null;
            }
            TextChannel textChannel=guild.getTextChannelById(gnChannel);
            if(textChannel==null){
                return null;
            }
            return  textChannel;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasChannel(){
        String fName="[hasChannel]";
        try {
            logger.info(fName+"value="+gnChannel);
            if(gnChannel<=0){
                return false;
            }
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isChannelValid(Guild guild){
        String fName="[getChannel]";
        try {
            logger.info(fName+"value="+gnChannel);
            if(gnChannel<=0){
                return false;
            }
            TextChannel textChannel=guild.getTextChannelById(gnChannel);
            if(textChannel==null){
                return false;
            }
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public long getTimeOut1Duration(){
        String fName="[getTimeOut1Duration]";
        try {
            logger.info(fName+"value="+gnTimeOut1Duration);
            return gnTimeOut1Duration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getTimeOut1Timestamp(){
        String fName="[getTimeOut1Timestamp]";
        try {
            logger.info(fName+"value="+gnTimeOut1Timestamp);
            return gnTimeOut1Timestamp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public int getTimeOut2Count(){
        String fName="[getTimeOut2Count]";
        try {
            logger.info(fName+"value="+gnTimeOut2Count);
            return gnTimeOut2Count;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getTimeOut2Done(){
        String fName="[getTimeOut2Done]";
        try {
            logger.info(fName+"value="+gnTimeOut2Done);
            return gnTimeOut2Done;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getTimeLockMin(){
        String fName="[getTimeLockMin]";
        try {
            logger.info(fName+"value="+gnTimeLockMin);
            return gnTimeLockMin;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getTimeOut2Sentence(){
        String fName="[getTimeOut2Sentence]";
        try {
            logger.info(fName+"value="+gnTimeOut2Sentence);
            return gnTimeOut2Sentence;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityTimeOut setOn(boolean input){
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
    public entityTimeOut setTimeOutMode(int input){
        String fName="[setTimeOutMode]";
        try {
            logger.info(fName+"input="+input);
            gnTimeOutMode=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setTimeOut1Duration(long input){
        String fName="[setTimeOut1Duration]";
        try {
            logger.info(fName+"input="+input);
            gnTimeOut1Duration=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setTimeOut1Timestamp(long input){
        String fName="[setTimeOut1Timestamp]";
        try {
            logger.info(fName+"input="+input);
            gnTimeOut1Timestamp=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setTimeLockMin(long input){
        String fName="[setTimeLockMin]";
        try {
            logger.info(fName+"input="+input);
            gnTimeLockMin=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setTimeOut2Count(int input){
        String fName="[setTimeOut2Count]";
        try {
            logger.info(fName+"input="+input);
            gnTimeOut2Count=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setTimeOut2Done(int input){
        String fName="[setTimeOut2Done]";
        try {
            logger.info(fName+"input="+input);
            gnTimeOut2Done=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setTimeOut2Sentence(String input){
        String fName="[setTimeLockTimestamp]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                return null;
            }
            logger.info(fName+"input="+input);
            gnTimeOut2Sentence=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setChannel(long input){
        String fName="[setChannel]";
        try {
            logger.info(fName+"input="+input);
            gnChannel=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityTimeOut setChannel(TextChannel input){
        String fName="[setChannel]";
        try {
            if(input==null){
                logger.info(fName+"input is null");
                gnChannel=0;
                return null;
            }
            logger.info(fName+"input="+input.getId());
            gnChannel=input.getIdLong();
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
                case nTimeOutMode:
                    gnTimeOutMode= jsonObject.getInt(key);
                    break;
                case nChannel:
                    gnChannel= jsonObject.getLong(key);
                    break;
                case nTimeoutModeTimer:
                case nTimeoutModeWritting:
                    Iterator<String>keys=jsonObject.getJSONObject(key).keys();
                    while (keys.hasNext()){
                        String key2=keys.next();
                        put(key2,jsonObject.getJSONObject(key));
                    }
                    break;
                case nTimeOut1Duration:
                    gnTimeOut1Duration= jsonObject.getLong(key);
                    break;
                case nTimeOut1Timestamp:
                    gnTimeOut1Timestamp= jsonObject.getLong(key);
                    break;
                case nTimeOut2Count:
                    gnTimeOut2Count= jsonObject.getInt(key);
                    break;
                case nTimeOut2Done:
                    gnTimeOut2Done= jsonObject.getInt(key);
                    break;
                case nTimeOut2Sentence:
                    gnTimeOut2Sentence= jsonObject.getString(key);
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
                case nTimeOutMode:
                    gnTimeOutMode= (int) value;
                    break;
                case nChannel:
                    gnChannel= (long) value;
                    break;
                case nTimeOut1Duration:
                    gnTimeOut1Duration= (long) value;
                    break;
                case nTimeOut1Timestamp:
                    gnTimeOut1Timestamp= (long) value;
                    break;
                case nTimeOut2Count:
                    gnTimeOut2Count= (int) value;
                    break;
                case nTimeOut2Done:
                    gnTimeOut2Done= (int) value;
                    break;
                case nTimeOut2Sentence:
                    gnTimeOut2Sentence= (String) value;
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
