package restraints.models.entity;

import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.CUFFSARMSLEVELS;

import java.util.Arrays;
import java.util.Iterator;


public class entityArmsCuffs  {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityArmsCuffs]";
    /*gUserProfile.safetyCreateFieldEntry(nArmsCuffs);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nOn,false);
            //gUserProfile.safetyPutFieldEntry(nArmsCuffs,nWrist,false);
            //gUserProfile.safetyPutFieldEntry(nArmsCuffs,nElbows,false);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostRestrictEnabled, false);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostDelay, 0);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nLastPostTimeStamp, 0);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostRestrictionException2UseGuildGag, true);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostRestrictionException2UseUserGag, true);
            //gUserProfile.safetyPutFieldEntry(nArmsCuffs,nTightness,0);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs, nImageUrlSecure,"");gUserProfile.safetyPutFieldEntry(nArmsCuffs, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nArmsCuffs, nImageUrlStruggle,"");
            */
    final String nOn=iRestraints.nOn,
        nPostRestrictEnabled =iRestraints.nPostRestrictEnabled,
        nPostRestrictionException2UseGuildGag =iRestraints.nPostRestrictionException2UseGuildGag,
        nPostRestrictionException2UseUserGag=iRestraints.nPostRestrictionException2UseUserGag,
        nLevel =iRestraints.nLevel,
        nImageUrlOn=iRestraints.nImageUrlOn,nImageUrlOff=iRestraints.nImageUrlOff,nImageUrlSecure=iRestraints.nImageUrlSecure,nImageUrlUnSecure=iRestraints.nImageUrlUnSecure,nImageUrlStruggle=iRestraints.nImageUrlStruggle,
        nPostDelay=iRestraints.nPostDelay,
        nLastPostTimeStamp=iRestraints.nLastPostTimeStamp,nNone=iRestraints.nNone, nArmsCuffs=iRestraints.nArmsCuffs;
    boolean gnOn=false,
                    gnPostRestrictEnabled =false,
                    gnPostRestrictionException2UseGuildGag =false,
                    gnPostRestrictionException2UseUserGag=false;
    String gnLevel =nNone;
    String gnImageUrlOn="",gnImageUrlOff="",gnImageUrlSecure="",gnImageUrlUnSecure="",gnImageUrlStruggle="";
    long gnPostDelay=0L,
            gnLastPostTimeStamp=0L;
    public JSONObject extraJsonObject=new JSONObject();

    public entityArmsCuffs(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityArmsCuffs(JSONObject jsonObject){
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
            gnPostRestrictEnabled =false;
            gnPostRestrictionException2UseGuildGag =false;
            gnPostRestrictionException2UseUserGag=false;
            gnLevel =nNone;
            gnImageUrlOn="";gnImageUrlOff="";gnImageUrlSecure="";gnImageUrlUnSecure="";gnImageUrlStruggle="";
            gnPostDelay=0L;
            gnLastPostTimeStamp=0L;
            extraJsonObject=new JSONObject();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityArmsCuffs set(JSONObject jsonObject){
        String fName="[set]";
        try {
            if(jsonObject==null){
                logger.info(fName+"jsonObject is null");
                return null;
            }
            if(jsonObject.has(nArmsCuffs)){
                logger.info(fName+"has key nStraitjacket");
                jsonObject=jsonObject.getJSONObject(nArmsCuffs);
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
            jsonObject.put(nPostRestrictEnabled, gnPostRestrictEnabled);
            jsonObject.put(nPostRestrictionException2UseGuildGag, gnPostRestrictionException2UseGuildGag);
            jsonObject.put(nPostRestrictionException2UseUserGag, gnPostRestrictionException2UseUserGag);
            jsonObject.put(nLevel, gnLevel);
            jsonObject.put(nPostDelay, gnPostDelay);
            jsonObject.put(nLastPostTimeStamp, gnLastPostTimeStamp);
            jsonObject.put(nImageUrlOn,gnImageUrlOn);
            jsonObject.put(nImageUrlOff,gnImageUrlOff);
            jsonObject.put(nImageUrlSecure,gnImageUrlSecure);
            jsonObject.put(nImageUrlUnSecure,gnImageUrlUnSecure);
            jsonObject.put(nImageUrlStruggle,gnImageUrlStruggle);
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
    public boolean areArmsRestrained(){
        String fName="[areArmsRestrained]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            logger.info(fName+"default >true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrained4Straitjacket(){
        String fName="[areArmsRestrained4Straitjacket]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            if(getLevel()== CUFFSARMSLEVELS.Armbinder){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.SidesTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.ReversePrayer){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            logger.info(fName+"default >false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrained4Mitts(){
        String fName="[areArmsRestrained4Mitts]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            if(getLevel()==CUFFSARMSLEVELS.Armbinder){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.SidesTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.ReversePrayer){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            logger.info(fName+"default >false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrainedImpossible2ManageMitts(){
        String fName="[areArmsRestrainedImpossible2ManageMitts]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            if(getLevel()==CUFFSARMSLEVELS.Armbinder){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.ReversePrayer){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            logger.info(fName+"default >false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrainedImpossible2ManageCollar(){
        String fName="[areArmsRestrainedImpossible2ManageCollar]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            if(getLevel()==CUFFSARMSLEVELS.Armbinder){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.ReversePrayer){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Behind){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.FrontBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.SidesTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Sides){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Elbows){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            logger.info(fName+"default >false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrainedImpossible2ManageEar(){
        String fName="[areArmsRestrainedImpossible2ManageEar]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            if(getLevel()==CUFFSARMSLEVELS.Armbinder){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.ReversePrayer){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Behind){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.FrontBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.SidesTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Sides){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Elbows){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            logger.info(fName+"default >false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean areArmsRestrainedImpossible2ManageChastity(){
        String fName="[areArmsRestrainedImpossible2ManageChastity]";
        try {
            if(!isOn()){
                logger.info(fName+"not on>false");
                return false;
            }
            if(getLevelAsString().isBlank()){
                logger.info(fName+"arms level is blank >false");
                return false;
            }
            if(getLevelAsString().equalsIgnoreCase(nNone)){
                logger.info(fName+"arms level is none >false");
                return false;
            }
            if(getLevel()==CUFFSARMSLEVELS.Armbinder){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.ReversePrayer){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Behind){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindTBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.BehindBelt){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.SidesTight){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Sides){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            if(getLevel()==CUFFSARMSLEVELS.Elbows){
                logger.info(fName+"arms level is high >true");
                return true;
            }
            logger.info(fName+"default >false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPostRestrictEnabled(){
        String fName="[isPostRestrictEnabled]";
        try {
            logger.info(fName+"value="+ gnPostRestrictEnabled);
            return gnPostRestrictEnabled;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPostRestrictionException2UseGuildGag(){
        String fName="[isPostRestrictionException2UseGuildGag]";
        try {
            logger.info(fName+"value="+ gnPostRestrictionException2UseGuildGag);
            return gnPostRestrictionException2UseGuildGag;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPostRestrictionException2UseUserGag(){
        String fName="[isPostRestrictionException2UseUserGag]";
        try {
            logger.info(fName+"value="+ gnPostRestrictionException2UseUserGag);
            return gnPostRestrictionException2UseUserGag;
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
    public CUFFSARMSLEVELS getLevel(){
        String fName="[getLevel]";
        try {
            logger.info(fName+"value="+ gnLevel);
            return CUFFSARMSLEVELS.valueByName(gnLevel);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return CUFFSARMSLEVELS.INVALID;
        }
    }
    public long getPostDelay(){
        String fName="[getPostDelay]";
        try {
            logger.info(fName+"value="+ gnPostDelay);
            return gnPostDelay;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getLastPostTimeStamp(){
        String fName="[getLastPostTimeStamp]";
        try {
            logger.info(fName+"value="+ gnLastPostTimeStamp);
            return gnLastPostTimeStamp;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
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
    public entityArmsCuffs setOn(boolean input){
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
    public entityArmsCuffs setPostRestrictEnabled(boolean input){
        String fName="[setPostRestrictEnabled]";
        try {
            logger.info(fName+"input="+input);
            gnPostRestrictEnabled =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs setPostRestrictionException2UseGuildGag(boolean input){
        String fName="[setPostRestrictionException2UseGuildGag]";
        try {
            logger.info(fName+"input="+input);
            gnPostRestrictionException2UseGuildGag =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs setPostRestrictionException2UseUserGag(boolean input){
        String fName="[setPostRestrictionException2UseUserGag]";
        try {
            logger.info(fName+"input="+input);
            gnPostRestrictionException2UseUserGag =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs setLevel(String input){
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
    public entityArmsCuffs setLevel(CUFFSARMSLEVELS input){
        String fName="[setLevel]";
        try {
            if(input==null){
                logger.info(fName+"can't be null");
                return null;
            }
            logger.info(fName+"input="+input.getName());
            gnLevel =input.getName();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs release(){
        String fName="[release]";
        try {
            setOn(false);
            setLevel(CUFFSARMSLEVELS.None);
            logger.info(fName+"default >true");
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs setPostDelay(long input){
        String fName="[setPostDelay]";
        try {
            logger.info(fName+"input="+input);
            gnPostDelay =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs setLastPostTimeStamp(long input){
        String fName="[setLastPostTimeStamp]";
        try {
            logger.info(fName+"input="+input);
            gnLastPostTimeStamp =input;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityArmsCuffs setImageUrlOn(String input){
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
    public entityArmsCuffs setImageUrlOff(String input){
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
    public entityArmsCuffs setImageUrlSecure(String input){
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
    public entityArmsCuffs setImageUrlUnSecure(String input){
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
    public entityArmsCuffs setImageUrlStruggle(String input){
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
    public boolean put(String key,Object value){
        String fName="[put]";
        try {
            logger.info(fName+"key="+key);
            switch (key){
                case nOn:
                    gnOn= (boolean) value;
                    break;
                case nPostRestrictEnabled:
                    gnPostRestrictEnabled = (boolean) value;
                    break;
                case nPostRestrictionException2UseGuildGag:
                    gnPostRestrictionException2UseGuildGag = (boolean) value;
                    break;
                case nPostRestrictionException2UseUserGag:
                    gnPostRestrictionException2UseUserGag = (boolean) value;
                    break;
                case nLevel:
                    gnLevel = (String) value;
                    break;
                case nPostDelay:
                    gnPostDelay = (long) value;
                    break;
                case nLastPostTimeStamp:
                    gnLastPostTimeStamp = (long) value;
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
                case nOn:
                    gnOn= jsonObject.getBoolean(key);
                    break;
                case nPostRestrictEnabled:
                    gnPostRestrictEnabled = jsonObject.getBoolean(key);
                    break;
                case nPostRestrictionException2UseGuildGag:
                    gnPostRestrictionException2UseGuildGag  =jsonObject.getBoolean(key);
                    break;
                case nPostRestrictionException2UseUserGag:
                    gnPostRestrictionException2UseUserGag = jsonObject.getBoolean(key);
                    break;
                case nLevel:
                    gnLevel = jsonObject.getString(key);
                    break;
                case nPostDelay:
                    gnPostDelay = jsonObject.getLong(key);
                    break;
                case nLastPostTimeStamp:
                    gnLastPostTimeStamp = jsonObject.getLong(key);
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
}
