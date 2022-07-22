package restraints.models.entity;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.models.enums.SUITMATTERIAL;
import restraints.models.enums.SUITTYPE;
import restraints.in.iRestraints;

import java.util.Arrays;
import java.util.Iterator;


public class entitySuit {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityBlindfold]";
    /* gUserProfile.safetyCreateFieldEntry(nSuit);
            gUserProfile.safetyPutFieldEntry(nSuit,nOn,false);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitType,"");
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitMatterial,"");
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitFull,false);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitScore,0);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitTimeLocked,0);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitTimer,0);
            gUserProfile.safetyPutFieldEntry(nSuit, nSuitFreeTalkAvailable,0);
            gUserProfile.safetyPutFieldEntry(nSuit,keyCustomGoodTalk,new JSONArray());
*/

   final  String nSuit=iRestraints.nSuit,
            nOn=iRestraints.nOn,
            nSuitType=iRestraints.nSuitType,
            nSuitMatterial=iRestraints.nSuitMatterial,
            nSuitFull=iRestraints.nSuitFull,
            nSuitScore=iRestraints.nSuitScore,
            nSuitTimeLocked=iRestraints.nSuitTimeLocked,
            nSuitTimer=iRestraints.nSuitTimer,
             nSuitFreeTalkAvailable=iRestraints.nSuitFreeTalkAvailable,
            keyCustomGoodTalk=iRestraints.keyCustomGoodTalk,
            nSuitPrefix=iRestraints.nSuitPrefix;
    boolean gnOn=false;
    String gnSuitType="",gnSuitMatterial="",gnSuitPrefix="";
    boolean gnSuitFull=false;
    int gnSuitScore=0, gnSuitFreeTalkAvailable =0;
    long gnSuitTimeLocked=0, gnSuitTimer =0;
    JSONArray gkeyCustomGoodTalk=new JSONArray();
    public JSONObject extraJsonObject=new JSONObject();

    public entitySuit(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entitySuit(JSONObject jsonObject){
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
            gnSuitType="";gnSuitMatterial="";gnSuitPrefix="";
            gnSuitFull=false;
            gnSuitScore=0;
            gnSuitFreeTalkAvailable =0;
            gnSuitTimeLocked=0;
            gnSuitTimer =0;
            gkeyCustomGoodTalk=new JSONArray();
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entitySuit set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nSuit)){
                logger.info(fName+"has key nSuit");
                jsonObject=jsonObject.getJSONObject(nSuit);
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
            jsonObject.put(nSuitType,gnSuitType);
            jsonObject.put(nSuitMatterial,gnSuitMatterial);
            jsonObject.put(nSuitFull,gnSuitFull);
            jsonObject.put(nSuitScore,gnSuitScore);
            jsonObject.put(nSuitFreeTalkAvailable,gnSuitFreeTalkAvailable);
            jsonObject.put(nSuitTimeLocked,gnSuitTimeLocked);
            jsonObject.put(nSuitTimer,gnSuitTimer);
            jsonObject.put(keyCustomGoodTalk,gkeyCustomGoodTalk);
            jsonObject.put(nSuitPrefix,gnSuitPrefix);
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
    public boolean isSuitFull(){
        String fName="[isSuitFull]";
        try {
            logger.info(fName+"value="+gnSuitFull);
            return gnSuitFull;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public String getSuitTypeAsString(){
        String fName="[getSuitTypeAsString]";
        try {
            logger.info(fName+"value="+gnSuitType);
           return gnSuitType;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public SUITTYPE getSuitType(){
        String fName="[getSuitType]";
        try {
            logger.info(fName+"value="+gnSuitType);
            SUITTYPE result= SUITTYPE.valueByName(gnSuitType);
            logger.info(fName+"result="+result.getName());
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return SUITTYPE.INVALID;
        }
    }
    public String getSuitMatterialAsString(){
        String fName="[getSuitMatteriaAsString]";
        try {
            logger.info(fName+"value="+gnSuitMatterial);
            return gnSuitMatterial;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public SUITMATTERIAL getSuitMatterial(){
        String fName="[getSuitMatterial]";
        try {
            logger.info(fName+"value="+gnSuitMatterial);
            SUITMATTERIAL result= SUITMATTERIAL.valueByName(gnSuitMatterial);
            logger.info(fName+"result="+result.getName());
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return SUITMATTERIAL.INVALID;
        }
    }
    public String getSuitPrefix(){
        String fName="[getSuitPrefix]";
        try {
            logger.info(fName+"value="+gnSuitPrefix);
            return gnSuitPrefix;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getSuitScore(){
        String fName="[getSuitScore]";
        try {
            logger.info(fName+"value="+gnSuitScore);
            return gnSuitScore;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getSuitFreeTalkAvailable(){
        String fName="[getSuitFreeTalkAvailable]";
        try {
            logger.info(fName+"value="+gnSuitFreeTalkAvailable);
            return gnSuitFreeTalkAvailable;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getSuitMaxTalkAvailable(){
        String fName="[getSuitMaxTalkAvailable]";
        try {
            logger.info(fName+"value="+gnSuitFreeTalkAvailable);
            int result=0;
            return gnSuitFreeTalkAvailable;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isToySuitOn(){
        String fName="[isToySuitOn]";
        try {
            if(!isOn()){
                logger.info(fName+"suit not on>false");
                return false;
            }
            if(isToySuitSelected()){
                logger.info(fName+"selected toysuit>true");
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
    public boolean isToySuitSelected(){
        String fName="[isToySelected]";
        try {
            if(getSuitType().isToy()){
                logger.info(fName+"isToy>true");
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
    public boolean isFree2Talk(){
        String fName="[isFree2Talk]";
        try {
            if(!isToySuitOn()){
                logger.info(fName+"suit not on or not toy suit>true");
                return true;
            }
            if(getSuitFreeTalkAvailable()>0){
                logger.info(fName+"FreeTalkAvailable bigger than zero>true");
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
    public boolean isBDSMSuitOn(){
        String fName="[isBDSMSuitOn]";
        try {
            if(!isOn()){
                logger.info(fName+"suit not on>false");
                return false;
            }
            if(isBDSMSuitSelected()){
                logger.info(fName+"selected bdsmsuit>true");
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
    public boolean isBDSMSuitSelected(){
        String fName="[isBDSMSuitSelected]";
        try {
            if(getSuitType().isBDSM()){
                logger.info(fName+"isBDSM>true");
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
    public long getSuitTimeLocked(){
        String fName="[getSuitTimeLocked]";
        try {
            logger.info(fName+"value="+gnSuitTimeLocked);
            return gnSuitTimeLocked;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getSuitTimer(){
        String fName="[getSuitTimer]";
        try {
            logger.info(fName+"value="+gnSuitTimer);
            return gnSuitTimer;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public JSONArray getCustomGoodTalk(){
        String fName="[getCustomGoodTalk]";
        try {
            logger.info(fName+"value="+gkeyCustomGoodTalk);
            return gkeyCustomGoodTalk;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public String getCustomGoodTalk(int index){
        String fName="[getCustomGoodTalk]";
        try {
            logger.info(fName+"index="+index+", array="+gkeyCustomGoodTalk);
            if(gkeyCustomGoodTalk.isEmpty()){
                logger.info(fName+"array is empty");
                return "";
            }
            if(index<0||index>=gkeyCustomGoodTalk.length()){
                logger.info(fName+"invalid index");
                return "";
            }
            return gkeyCustomGoodTalk.getString(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public int getCustomGoodTalkLength(){
        String fName="[getCustomGoodTalkLength]";
        try {
            logger.info(fName+"value="+gkeyCustomGoodTalk.length());
            return gkeyCustomGoodTalk.length();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public entitySuit setOn(boolean input){
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
    public entitySuit setSuitFull(boolean input){
        String fName="[setSuitFull]";
        try {
            logger.info(fName+"input="+input);
            gnSuitFull=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitType(String input){
        String fName="[setSuitType]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnSuitType=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitType(SUITTYPE input){
        String fName="[setSuitType]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnSuitType=input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitMatterial(String input){
        String fName="[setSuitMatterial]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnSuitMatterial=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitMatterial(SUITMATTERIAL input){
        String fName="[setSuitMatterial]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnSuitMatterial=input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitPrefix(String input){
        String fName="[setSuitPrefix]";
        try {
            logger.info(fName+"input="+input);
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            gnSuitPrefix=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitScore(int input){
        String fName="[setSuitScore]";
        try {
            logger.info(fName+"input="+input);
            gnSuitScore=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit addSuitScore(int input){
        String fName="[addSuitScore]";
        try {
            logger.info(fName+"input="+input);
            gnSuitScore+=input;
            logger.info(fName+"gnSuitScore="+gnSuitScore);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit remSuitScore(int input){
        String fName="[remSuitScore]";
        try {
            logger.info(fName+"input="+input);
            gnSuitScore-=input;
            logger.info(fName+"gnSuitScore="+gnSuitScore);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit addSuitScore1(){
        String fName="[addSuitScore1]";
        try {
            return  addSuitScore(1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit remSuitScore1(){
        String fName="[remSuitScore1]";
        try {
            return remSuitScore(1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit addSuitScore5(){
        String fName="[addSuitScore1]";
        try {
            return  addSuitScore(5);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit remSuitScore5(){
        String fName="[remSuitScore1]";
        try {
            return remSuitScore(5);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit addSuitScore10(){
        String fName="[addSuitScore1]";
        try {
            return  addSuitScore(10);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit remSuitScore10(){
        String fName="[remSuitScore1]";
        try {
            return remSuitScore(10);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit addSuitScore25(){
        String fName="[addSuitScore1]";
        try {
            return  addSuitScore(25);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit remSuitScore25(){
        String fName="[remSuitScore1]";
        try {
            return remSuitScore(25);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit incSuitScore(){
        String fName="[incSuitScore]";
        try {
            gnSuitScore++;
            logger.info(fName+"gnSuitScore="+gnSuitScore);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit decSuitScore(){
        String fName="[decSuitScore]";
        try {
            gnSuitScore--;
            logger.info(fName+"gnSuitScore="+gnSuitScore);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit release(){
        String fName="[release]";
        try {
            setOn(false);
            setSuitFull(false);
            setSuitMatterial(SUITMATTERIAL.None);
            setSuitType(SUITTYPE.None);
            setSuitScore(0);
            logger.info(fName+"default >true");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitFreeTalkAvailable(int input){
        String fName="[setSuitFreeTalkAvailable]";
        try {
            logger.info(fName+"input="+input);
            gnSuitFreeTalkAvailable=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitTimeLocked(long input){
        String fName="[setSuitTimeLocked]";
        try {
            logger.info(fName+"input="+input);
            gnSuitTimeLocked=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setSuitTimer(long input){
        String fName="[setSuitTimer]";
        try {
            logger.info(fName+"input="+input);
            gnSuitTimer=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit setCustomGoodTalk(JSONArray input){
        String fName="[setCustomGoodTalk]";
        try {
            if(input==null){
            logger.info(fName+"can't be null");
                return null;
        }
            logger.info(fName+"input="+input);
            gkeyCustomGoodTalk=input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit remCustomGoodTalk(int index){
        String fName="[remCustomGoodTalk]";
        try {
            logger.info(fName+"index="+index+", array="+gkeyCustomGoodTalk);
            if(gkeyCustomGoodTalk.isEmpty()){
                logger.info(fName+"array is empty");
                return null;
            }
            if(index<0||index>=gkeyCustomGoodTalk.length()){
                logger.info(fName+"invalid index");
                return null;
            }
            gkeyCustomGoodTalk.remove(index);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit addCustomGoodTalk(String str){
        String fName="[addCustomGoodTalk]";
        try {
            logger.info(fName+"str="+str+", array="+gkeyCustomGoodTalk);
            gkeyCustomGoodTalk.put(str);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entitySuit putCustomGoodTalk(String str,boolean ignorecase){
        String fName="[putCustomGoodTalk]";
        try {
            logger.info(fName+"ignorecase="+ignorecase+", str="+str+", array="+gkeyCustomGoodTalk);
            if(ignorecase){
                for(int i=0;i<gkeyCustomGoodTalk.length();i++){
                    String compare=gkeyCustomGoodTalk.getString(i);
                    logger.info(fName+"array["+i+"]="+compare);
                    if(compare.equalsIgnoreCase(str)){
                        logger.info(fName+"already has>false");
                        return null;
                    }
                }
            }else{
                for(int i=0;i<gkeyCustomGoodTalk.length();i++){
                    String compare=gkeyCustomGoodTalk.getString(i);
                    logger.info(fName+"array["+i+"]="+compare);
                    if(compare.equals(str)){
                        logger.info(fName+"already has>false");
                        return null;
                    }
                }
            }
            gkeyCustomGoodTalk.put(str);
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasCustomGoodTalk(String str,boolean ignorecase){
        String fName="[hasCustomGoodTalk]";
        try {
            logger.info(fName+"ignorecase="+ignorecase+", str="+str+", array="+gkeyCustomGoodTalk);
            if(ignorecase){
                for(int i=0;i<gkeyCustomGoodTalk.length();i++){
                    String compare=gkeyCustomGoodTalk.getString(i);
                    logger.info(fName+"array["+i+"]="+compare);
                    if(compare.equalsIgnoreCase(str)){
                        logger.info(fName+"already has>true");
                        return true;
                    }
                }
            }else{
                for(int i=0;i<gkeyCustomGoodTalk.length();i++){
                    String compare=gkeyCustomGoodTalk.getString(i);
                    logger.info(fName+"array["+i+"]="+compare);
                    if(compare.equals(str)){
                        logger.info(fName+"already has>true");
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
    public boolean put(String key,JSONObject jsonObject){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case nOn:
                    gnOn= jsonObject.getBoolean(key);
                    break;
                case nSuitType:
                    gnSuitType= jsonObject.getString(key);
                    break;
                case nSuitMatterial:
                    gnSuitMatterial= jsonObject.getString(key);
                    break;
                case nSuitFull:
                    gnSuitFull= jsonObject.getBoolean(key);
                    break;
                case nSuitScore:
                    gnSuitScore= jsonObject.getInt(key);
                    break;
                case nSuitFreeTalkAvailable:
                    gnSuitFreeTalkAvailable= jsonObject.getInt(key);
                    break;
                case nSuitTimeLocked:
                    gnSuitTimeLocked= jsonObject.getLong(key);
                    break;
                case nSuitTimer:
                    gnSuitTimer= jsonObject.getLong(key);
                    break;
                case keyCustomGoodTalk:
                    gkeyCustomGoodTalk= jsonObject.getJSONArray(key);
                    break;
                case nSuitPrefix:
                    gnSuitPrefix= jsonObject.getString(key);
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
                case nSuitType:
                    gnSuitType= (String) value;
                    break;
                case nSuitMatterial:
                    gnSuitMatterial= (String) value;
                    break;
                case nSuitFull:
                    gnSuitFull= (boolean) value;
                    break;
                case nSuitScore:
                    gnSuitScore= (int) value;
                    break;
                case nSuitFreeTalkAvailable:
                    gnSuitFreeTalkAvailable= (int) value;
                    break;
                case nSuitTimeLocked:
                    gnSuitTimeLocked= (long) value;
                    break;
                case nSuitTimer:
                    gnSuitTimer= (long) value;
                    break;
                case keyCustomGoodTalk:
                    gkeyCustomGoodTalk= (JSONArray) value;
                    break;
                case nSuitPrefix:
                    gnSuitPrefix= (String) value;
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
