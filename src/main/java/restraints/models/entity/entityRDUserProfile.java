package restraints.models.entity;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import restraints.models.entity.pishock.entityPiShock;
import restraints.models.enums.*;
import restraints.in.iRestraints;
import restraints.models.lcBDSMGuildProfiles;

import java.sql.Timestamp;
import java.util.Arrays;


public class entityRDUserProfile {

    Logger logger = Logger.getLogger(getClass());

     public lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
     lcGlobalHelper gGlobal;
     Member gMember;
     Guild gGuild; lcBDSMGuildProfiles gBDSMCommands;
     public boolean flagGet4Cache=false,flagPut2Cache=false,flagUseBDSMCommands=false;
    public entityRDUserProfile(){
        String fName="[constructor]";
        logger.info(fName+printUser());
    }
    public entityRDUserProfile(lcGlobalHelper global, Member member, lcBDSMGuildProfiles BDSMCommands){
        String fName="[constructor]";
        try {
            build(global, member,BDSMCommands);
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean build(lcGlobalHelper global, Member member, lcBDSMGuildProfiles BDSMCommands){
        String fName="[build]";
        try {
            logger.info(fName+printUser());
            gGlobal=global;
            gMember=member;
            gGuild=gMember.getGuild();
            gBDSMCommands=BDSMCommands;
            flagGet4Cache=true;flagPut2Cache=true;flagUseBDSMCommands=false;
            if(!getProfile()){
                logger.warn("failed to get profile>new profile");
                if(!newProfile()){
                    logger.warn("failed to create new profile");
                    return false;
                }
            }else{
                logger.info("got profile");
            }
            if(setDefaultProfileVariables()){
                logger.info("setDefaultProfileVariables updated>save");
                if(!saveProfile(true,false)){
                    logger.warn("failed to save>ignore");
                }
            }
            if(flagPut2Cache){
                if(putProfile()){
                    logger.info("successfully put profile to cache");
                }else{
                    logger.warn("failed put profile to cache");
                }
            }else{
                logger.info("disabled to put profile to cache");
            }
            logger.info("gUserProfile.json="+gUserProfile.jsonObject.toString());
            if(createEntities()){
                logger.info("successfully createEntities");
            }else{
                logger.warn("failed to createEntities");
                return false;
            }
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean buildLight(lcGlobalHelper global, Member member, lcBDSMGuildProfiles BDSMCommands){
        String fName="[buildLight]";
        try {
            logger.info(fName+printUser());
            gGlobal=global;
            gMember=member;
            gGuild=gMember.getGuild();
            gBDSMCommands=BDSMCommands;
            flagGet4Cache=true;flagPut2Cache=true;flagUseBDSMCommands=false;
            if(!getProfile()){
                logger.warn("failed to get profile>new profile");
                if(!newProfile()){
                    logger.warn("failed to create new profile");
                    return false;
                }
            }else{
                logger.info("got profile");
            }
            if(setDefaultProfileVariables()){
                logger.info("setDefaultProfileVariables updated>save");
                if(!saveProfile(true,false)){
                    logger.warn("failed to save>ignore");
                }
            }
            if(flagPut2Cache){
                if(putProfile()){
                    logger.info("successfully put profile to cache");
                }else{
                    logger.warn("failed put profile to cache");
                }
            }else{
                logger.info("disabled to put profile to cache");
            }
            logger.info("gUserProfile.json="+gUserProfile.jsonObject.toString());
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(fName+printUser());
            if(flagGet4Cache)getCacheProfile();
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName+printUser() + ".is locally cached");
                return true;
            }else{
                logger.info(fName+printUser() + ".need to get or create");
                return getSQLProfile();
            }
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(){
        String fName="[getCacheProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=gGlobal.getUserProfile(iRestraints.profileName,gMember,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName+printUser() + ".is locally cached");
                return true;
            }else{
                logger.info(fName+printUser() + ".not cached");
               return false;
            }
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(){
        String fName="[getSQLProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild);
            if(gUserProfile.getProfile(iRestraints.table)){
                logger.info(fName+printUser() + ".has sql entry");
                return true;
            }else{
                logger.info(fName+printUser() + ".no sql entry");
                return false;
            }
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            if(global==null){
                logger.info(fName+printUser()+"global can't be null>false");
                return false;
            }
            gGlobal=global;
            logger.info(fName+printUser()+"set");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMember(Member member){
        String fName="[setMember]";
        try {
            if(member==null){
                logger.info(fName+printUser()+"member can't be null>false");
                return false;
            }
            gMember=member;
            logger.info(fName+printUser()+"member="+member.getId());
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            if(guild==null){
                logger.info(fName+printUser()+"guild can't be null>false");
                return false;
            }
            gGuild=guild;
            logger.info(fName+printUser()+"guild="+guild.getId());
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(lcGlobalHelper global,Member member){
        String fName="[getProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+printUser()+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+printUser()+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+printUser()+"guild can't be null>false");
                return false;
            }
            if(!getProfile()){
                logger.info(fName+printUser()+"failed to get>false");
                return false;
            }
            logger.info(fName+printUser()+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(lcGlobalHelper global,Member member){
        String fName="[getCacheProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+printUser()+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+printUser()+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+printUser()+"guild can't be null>false");
                return false;
            }
            if(!getCacheProfile()){
                logger.info(fName+printUser()+"failed to get>false");
                return false;
            }
            logger.info(fName+printUser()+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(lcGlobalHelper global,Member member){
        String fName="[getSQLProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+printUser()+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+printUser()+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+printUser()+"guild can't be null>false");
                return false;
            }
            if(!getSQLProfile()){
                logger.info(fName+printUser()+"failed to get>false");
                return false;
            }
            logger.info(fName+printUser()+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean newProfile(){
        String fName="[newProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild);
            if(flagUseBDSMCommands)gUserProfile= iRestraints.sUserInit(gUserProfile,gBDSMCommands.getRestrainsProfile());
            else gUserProfile= iRestraints.sUserInit(gUserProfile,null);
            logger.info(fName+printUser() + ".created");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDefaultProfileVariables(){
        String fName="[setDefaultProfileVariables]";
        try {
            logger.info(fName+printUser());
            if(flagUseBDSMCommands)gUserProfile=iRestraints.sUserInit(gUserProfile,gBDSMCommands.getRestrainsProfile());
            else gUserProfile=iRestraints.sUserInit(gUserProfile);
            if(!gUserProfile.isUpdated){
                logger.info(fName+printUser() + ".no update>ignore");return false;
            }
            logger.info(fName+printUser() + ".updated");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putProfile(){
        String fName="[putProfile]";
        try {
            logger.info(fName+printUser());
            if(gGlobal.putUserProfile(gUserProfile,iRestraints.profileName)){
                logger.info(fName+printUser() + ".success");return true;
            }
            logger.info(fName+printUser() + ".failed");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveProfile(boolean skipPuttingentities,boolean updateEntities){
        String fName="[saveProfile]";
        try {
            logger.info(fName+printUser()+"skipPuttingentities="+skipPuttingentities);
            if(!skipPuttingentities)putAllEntities2JSONUserProfile();
            if(flagPut2Cache)gGlobal.putUserProfile(gUserProfile,iRestraints.profileName);
            if(updateEntities){
                createEntities();
            }
            if(gUserProfile.saveProfile(iRestraints.table)){
                logger.info(fName+printUser() + ".success");return  true;
            }
            logger.warn(fName+printUser() + ".failed");return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveJSON(){
        String fName="[saveJSON]";
        try {
            if(gUserProfile.saveProfile(iRestraints.table)){
                logger.info(fName+printUser() + ".success");return  true;
            }
            logger.warn(fName+printUser() + ".failed");return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean save2Cache(boolean skipPuttingentities,boolean updateEntities){
        String fName="[save2Cache]";
        try {
            logger.info(fName+printUser()+"skipPuttingentities="+skipPuttingentities);
            if(!skipPuttingentities)putAllEntities2JSONUserProfile();
            gGlobal.putUserProfile(gUserProfile,iRestraints.profileName);
            if(updateEntities){
                createEntities();
            }
            if(gUserProfile.saveProfile(iRestraints.table)){
                logger.info(fName+printUser() + ".success");return  true;
            }
            logger.warn(fName+printUser() + ".failed");return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean save2Cache(){
        return save2Cache(false,false);
    }
    public boolean saveProfile(){
        return saveProfile(false,true);
    }
    public Member getMember() {
        String fName = "[getMember]";
        try {
            if(gMember==null){
                logger.info(fName+printUser()+"isNull");
                return  null;
            }
            logger.info(fName+printUser()+"member="+gMember.getId());
            return  gMember;
        } catch (Exception e) {
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            if(gGuild==null){
                logger.info(fName+printUser()+"isNull");
                return  null;
            }
            logger.info(fName+printUser()+"guild="+gGuild.getId());
            return  gGuild;
        } catch (Exception e) {
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityBlindfold cBLINDFOLD=new entityBlindfold();
    public entityChastity cCHASTITY=new entityChastity();
    public entityCollar cCOLLAR=new entityCollar();
    public entityArmsCuffs cARMCUFFS=new entityArmsCuffs();
    public entityLegsCuffs cLEGCUFFS=new entityLegsCuffs();
    public entityEarMuffs cEAR=new entityEarMuffs();
    public entityGag cGAG=new entityGag();
    public entityHood cHOOD=new entityHood();
    public entityMitts cMITTS=new entityMitts();
    public entityNameTag cNAMETAG=new entityNameTag();
    public entityStraitjacket cSTRAITJACKET=new entityStraitjacket();
    public entitySuit cSUIT=new entitySuit();
    public entityTimeLock cTIMELOCK=new entityTimeLock();
    public entityTimeOut cTIMEOUT=new entityTimeOut();
    public entityPiShock cPISHOCK=new entityPiShock();
    public entityLock cLOCK=new entityLock();
    public entityConfine cCONFINE=new entityConfine();
    public entityEncase cENCASE=new entityEncase();
    public entityBreathplay cBREATPLAY=new entityBreathplay();
    public  entityAuth cAUTH=new entityAuth();
    public entityLeash cLEASH=new entityLeash();
    private String printUser(){
        String f2Name="[printUser]";
        try {
            logger.info(f2Name);
            if(gMember==null){
                logger.info(f2Name+"gMember:isNull");
                return "[]";
            }
            String str="[u:"+gMember.getId()+",g:"+gMember.getGuild().getId()+"]";
            logger.info(f2Name+"str="+str);
            return str;
        }catch (Exception e){
            logger.error(f2Name + ".exception=" + e);
            logger.error(f2Name + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public boolean isProfile(){
        String fName="[isProfile]";
        try {
            logger.info(fName+printUser());
            return gUserProfile.isProfile();
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean createEntities(){
        String fName="[createEntities]";
        try {
            logger.info(fName+printUser());
            logger.info(fName+printUser()+" blindfold="+setEntity_BLINDFOLD());
            logger.info(fName+printUser()+" chastity="+setEntity_CHASTITY());
            logger.info(fName+printUser()+" collar="+setEntity_COLLAR());
            logger.info(fName+printUser()+" armcuffs="+setEntity_ARMCUFFS());
            logger.info(fName+printUser()+" legcuffs="+setEntity_LEGCUFFS());
            logger.info(fName+printUser()+" ear="+setEntity_EAR());
            logger.info(fName+printUser()+" gaf="+setEntity_GAG());
            logger.info(fName+printUser()+" hood="+setEntity_HOOD());
            logger.info(fName+printUser()+" mitts="+setEntity_MITTS());
            logger.info(fName+printUser()+" nametag="+setEntity_NAMETAG());
            logger.info(fName+printUser()+" sj="+setEntity_STRAITJACKET());
            logger.info(fName+printUser()+" suit="+setEntity_SUIT());
            logger.info(fName+printUser()+" timelock="+setEntity_TIMELOCK());
            logger.info(fName+printUser()+" timeout="+setEntity_TIMEOUT());
            logger.info(fName+printUser()+" pishock="+setEntity_PISHOCK());
            logger.info(fName+printUser()+" lock="+setEntity_Lock());
            logger.info(fName+printUser()+" auth="+setEntity_Auth());
            logger.info(fName+printUser()+" confine="+setEntity_CONFINE());
            logger.info(fName+printUser()+" encase="+setEntity_ENCASE());
            logger.info(fName+printUser()+" breathplay="+setEntity_BREATHPLAY());
            logger.info(fName+printUser()+"done");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_BLINDFOLD(){
        String fName="[setEntityBLINDFOLD]";
        try {
            logger.info(fName+printUser());
            cBLINDFOLD.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nBlindfold)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cBLINDFOLD.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nBlindfold))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_CHASTITY(){
        String fName="[setEntityCHASTITY]";
        try {
            logger.info(fName+printUser());
            cCHASTITY.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nChastity)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cCHASTITY.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nChastity))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_COLLAR(){
        String fName="[setEntityCOLLAR]";
        try {
            logger.info(fName+printUser());
            cCOLLAR.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nCollar)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cCOLLAR.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nCollar))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_ARMCUFFS(){
        String fName="[setEntityARMCUFFS]";
        try {
            logger.info(fName+printUser());
            cARMCUFFS.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nArmsCuffs)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cARMCUFFS.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_LEGCUFFS(){
        String fName="[setEntityLEGCUFFS]";
        try {
            logger.info(fName+printUser());
            cLEGCUFFS.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nLegsCuffs)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cLEGCUFFS.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nLegsCuffs))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_EAR(){
        String fName="[setEntityEAR]";
        try {
            logger.info(fName+printUser());
            cEAR.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nEarMuffs)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cEAR.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nEarMuffs))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_GAG(){
        String fName="[setEntityGAG]";
        try {
            logger.info(fName+printUser());
            cGAG.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nGag)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cGAG.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nGag))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_HOOD(){
        String fName="[setEntityHOOD]";
        try {
            logger.info(fName+printUser());
            cHOOD.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nHood)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cHOOD.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nHood))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_MITTS(){
        String fName="[setEntityMITTS]";
        try {
            logger.info(fName+printUser());
            cMITTS.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nMitts)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cMITTS.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nMitts))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_NAMETAG(){
        String fName="[setEntityNAMETAG]";
        try {
            logger.info(fName+printUser());
            cNAMETAG.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nNameTag)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cNAMETAG.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nNameTag))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_STRAITJACKET(){
        String fName="[setEntitySTRAITJACKET]";
        try {
            logger.info(fName+printUser());
            cSTRAITJACKET.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nStraitjacket)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            JSONObject jsonObject=gUserProfile.jsonObject.getJSONObject(iRestraints.nStraitjacket);
            try {
                String key=iRestraints.nStrapCrotch;
                if(jsonObject.has(key)){
                    Object object=jsonObject.get(key);
                    if(object instanceof Boolean){
                        logger.info(fName+printUser()+"ignore key="+key);
                    }else{
                        jsonObject.put(key,false);
                        logger.info(fName+printUser()+"fixed key="+key);
                    }
                }
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            boolean result=cSTRAITJACKET.set(jsonObject)!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_SUIT(){
        String fName="[setEntitySUIT]";
        try {
            logger.info(fName+printUser());
            cSUIT.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nSuit)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cSUIT.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nSuit))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_TIMELOCK(){
        String fName="[setEntityTIMELOCK]";
        try {
            logger.info(fName+printUser());
            cTIMELOCK.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nTimeLock)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cTIMELOCK.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeLock))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_TIMEOUT(){
        String fName="[setEntityTIMEOUT]";
        try {
            logger.info(fName+printUser());
            cTIMEOUT.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nTimeOut)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cTIMEOUT.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeOut))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_PISHOCK(){
        String fName="[setEntityPISHOCK]";
        try {
            logger.info(fName+printUser());
            cPISHOCK.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nPishock)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cPISHOCK.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nPishock));
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_CONFINE(){
        String fName="[setEntityCONFINE]";
        try {
            logger.info(fName+printUser());
            cCONFINE.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nConfine)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cCONFINE.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nConfine))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_ENCASE(){
        String fName="[setEntityENCASE]";
        try {
            logger.info(fName+printUser());
            cENCASE.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nEncase)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cENCASE.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nEncase))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_BREATHPLAY(){
        String fName="[setEntityBREATHPLAY]";
        try {
            logger.info(fName+printUser());
            cBREATPLAY.clear();
            if(!gUserProfile.jsonObject.has(iRestraints.nBreathplay)){
                logger.warn(fName+printUser()+"no such key");
                return false;
            }
            boolean result=cBREATPLAY.set(gUserProfile.jsonObject.getJSONObject(iRestraints.nBreathplay))!=null;
            logger.info(fName+printUser()+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Lock(){
        String fName="[setEntityLock]";
        try {
            logger.info(fName+printUser());
            cLOCK.clear();
            cLOCK.set(gUserProfile.jsonObject);
            cLOCK.set(getGuild());
            logger.info(fName+printUser()+"done");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Auth(){
        String fName="[setEntity_Auth]";
        try {
            logger.info(fName+printUser());
            cAUTH.clear();
            cAUTH.set(gUserProfile.jsonObject);
            logger.info(fName+printUser()+"done");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Leash(){
        String fName="[setEntity_Leash]";
        try {
            logger.info(fName+printUser());
            cLEASH.clear();
            cLEASH.set(gUserProfile.jsonObject);
            logger.info(fName+printUser()+"done");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putAllEntities2JSONUserProfile(){
        String fName="[putAllEtities2JSONUserProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile.putFieldEntry(iRestraints.nBlindfold,cBLINDFOLD.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nChastity,cCHASTITY.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nCollar,cCOLLAR.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nArmsCuffs,cARMCUFFS.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nLegsCuffs,cLEGCUFFS.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nEarMuffs,cEAR.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nGag,cGAG.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nHood,cHOOD.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nMitts,cMITTS.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nNameTag,cNAMETAG.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nStraitjacket,cSTRAITJACKET.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nSuit,cSUIT.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nTimeLock,cTIMELOCK.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nTimeOut,cTIMEOUT.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nConfine,cCONFINE.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nEncase,cENCASE.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nPishock,cPISHOCK.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nBreathplay,cBREATPLAY.getJSON());
            gUserProfile.putFieldEntry(iRestraints.nLeash,cLEASH.getJSON());
            try {
                logger.info(fName+printUser()+"entity lock");
                gUserProfile.putFieldEntry(iRestraints.nLocked,cLOCK.isLocked());
                gUserProfile.putFieldEntry(iRestraints.nLockedBy,cLOCK.getLockedByAsString());
                gUserProfile.putFieldEntry(iRestraints.nLockType,cLOCK.getTypeAsString());
                gUserProfile.putFieldEntry(iRestraints.nPermalocked,cLOCK.isPermaLocked());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            try {
                logger.info(fName+printUser()+"entity lock");
                gUserProfile.putFieldEntry(iRestraints.nAccess,cAUTH.getAccessAsString());
                gUserProfile.putFieldEntry(iRestraints.nOwner,iRestraints.nOwnerId,cAUTH.getOwnerId());
                gUserProfile.putFieldEntry(iRestraints.nOwner,iRestraints.nOwnerAccepted,cAUTH.isOwnerAccepted());
                gUserProfile.putFieldEntry(iRestraints.nOwner,iRestraints.vRedirectAsk2OwnerAsWell,cAUTH.isRedirectAsk2OwnerAsWell());
                gUserProfile.putFieldEntry(iRestraints.nOwner,iRestraints.nSecOnwers,cAUTH.getSecOwnerJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
            logger.info(fName+printUser() + ".gUserProfile.json="+gUserProfile.jsonObject.toString());
            logger.info(fName+printUser() + ".success");return  true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean putAllEntities2JSONUserProfileTry(){
        String fName="[putAllEtities2JSONUserProfileTry]";
        try {
            logger.info(fName+printUser());
            try {
                gUserProfile.putFieldEntry(iRestraints.nBlindfold,cBLINDFOLD.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nChastity,cCHASTITY.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nCollar,cCOLLAR.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nArmsCuffs,cARMCUFFS.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nLegsCuffs,cLEGCUFFS.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nEarMuffs,cEAR.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nGag,cGAG.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nHood,cHOOD.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nMitts,cMITTS.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nNameTag,cNAMETAG.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nStraitjacket,cSTRAITJACKET.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nSuit,cSUIT.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nTimeLock,cTIMELOCK.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                gUserProfile.putFieldEntry(iRestraints.nTimeOut,cTIMEOUT.getJSON());
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName+printUser() + ".success");return  true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getGenderAsInt(){
        String fName="[getGenderAsInt]";
        try {
            if(gUserProfile.jsonObject.has(iRestraints.nGender)&&!gUserProfile.jsonObject.isNull(iRestraints.nGender)){
                int i=gUserProfile.jsonObject.getInt(iRestraints.nGender);
                if(0<=i&&i<=2){
                    logger.info(fName+printUser()+" result="+i);
                    return i;
                }
            }
            logger.info(fName+printUser()+" default=-1");
            return -1;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public entityRDUserProfile setGender(int value){
        String fName="[setGender]";
        try {
            logger.info(fName+printUser()+" value="+value);
            gUserProfile.jsonObject.put(iRestraints.nGender,value);
            return  this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getSpeciesAsInt(){
        String fName="[getSpeciesAsInt]";
        try {
            if(gUserProfile.jsonObject.has(iRestraints.nSpecies)&&!gUserProfile.jsonObject.isNull(iRestraints.nSpecies)){
                int i=gUserProfile.jsonObject.getInt(iRestraints.nSpecies);
                if(0<=i){
                    logger.info(fName+printUser()+" result="+i);
                    return i;
                }
            }
            logger.info(fName+printUser()+" default=-1");
            return -1;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public entityRDUserProfile setSpecies(int value){
        String fName="[setSpecies]";
        try {
            logger.info(fName+printUser()+" value="+value);
            gUserProfile.jsonObject.put(iRestraints.nSpecies,value);
            return  this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getSpeciesName(){
        String fName="[getSpeciesAsInt]";
        try {
            if(gUserProfile.jsonObject.has(iRestraints.nSpeciesName)&&!gUserProfile.jsonObject.isNull(iRestraints.nSpeciesName)){
                String i=gUserProfile.jsonObject.optString(iRestraints.nSpeciesName);
                logger.info(fName+printUser()+" result="+i);
                return i;
            }
            logger.info(fName+printUser()+" default");
            return "";
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public entityRDUserProfile setSpeciesName(String value){
        String fName="[setSpeciesName]";
        try {
            if(value==null)value="";
            logger.info(fName+printUser()+" value="+value);
            gUserProfile.jsonObject.put(iRestraints.nSpeciesName,value);
            return  this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityRDUserProfile clearSpeciesName(){
        String fName="[clearSpeciesName]";
        try {

            gUserProfile.jsonObject.put(iRestraints.nSpeciesName,"");
            return  this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public boolean isPetLockedBySomebody(){
        String fName="[isPetLockedBySomebody]";
        try {
            boolean result=false;
            if(gUserProfile.jsonObject.getBoolean(iRestraints.nLocked)){
                logger.info(fName+printUser()+"locked");
                if(gUserProfile.jsonObject.getString(iRestraints.nAccess).equals(ACCESSLEVELS.Key.getName())){
                    logger.info(fName+printUser()+"access is key");
                    if(!gUserProfile.jsonObject.isNull(iRestraints.nLockedBy)&&!gUserProfile.jsonObject.getString(iRestraints.nLockedBy).isBlank()&&!gUserProfile.jsonObject.getString(iRestraints.nLockedBy).isEmpty()){
                        logger.info(fName+printUser()+"has key 'nLockedBy' set");
                        if(!gUserProfile.jsonObject.getString(iRestraints.nLockedBy).equalsIgnoreCase(gUserProfile.getUser().getId())){
                            logger.info(fName+printUser()+"not self locked");result=true;
                        }
                    }
                }
            }
            logger.info(fName+printUser()+" result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isFreeEnought2afectRestraints(){
        String fName="[isFreeEnought2afectRestraints]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(access.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                logger.info(fName+printUser()+" return true");
                return true;
            }
            if(access.equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                logger.info(fName+printUser()+" return true");
                return true;
            }
            if(access.equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){
                logger.info(fName+printUser()+" return true");
                return true;
            }
            logger.info(fName+printUser()+" return default>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean canNewRestrictionsDeny2ChangeTheirRestraints(){
        String fName="[canNewRestrictionsDeny2ChangeTheirRestraints]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(isFreeEnought2afectRestraints()){
                logger.info(fName+printUser()+" return false");
                return false;
            }
            logger.info(fName+printUser()+" return default>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean allow2BypassNewRestrictions(){
        String fName="[allow2BypassNewRestrictions]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(isFreeEnought2afectRestraints()){
                logger.info(fName+printUser()+" return true");
                return true;
            }
            logger.info(fName+printUser()+" return default>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isArmCuffsNewRestriction(){
        String fName="[isArmCuffsNewRestriction]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(isFreeEnought2afectRestraints()){
                logger.info(fName+printUser()+" due to access>false");
                return false;
            }
            boolean response=cARMCUFFS.areArmsRestrained();
            logger.info(fName+printUser()+" response="+response);
            return response;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEncasedNewRestriction(){
        String fName="[isEncasedNewRestriction]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(isFreeEnought2afectRestraints()){
                logger.info(fName+printUser()+" due to access>false");
                return false;
            }
            boolean response=cENCASE.isEncased();
            logger.info(fName+printUser()+" response="+response);
            return response;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isMittsNewRestriction(){
        String fName="[isMittsNewRestriction]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(isFreeEnought2afectRestraints()){
                logger.info(fName+printUser()+" due to access>false");
                return false;
            }
            boolean response=cMITTS.isOn();
            logger.info(fName+printUser()+" response="+response);
            return response;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isArmsStraitjacketRestriction(){
        String fName="[isArmsStraitjacketRestriction]";
        try {
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser()+" access="+access);
            if(isFreeEnought2afectRestraints()){
                logger.info(fName+printUser()+" due to access>false");
                return false;
            }
            boolean response=cSTRAITJACKET.areArmsRestrained();
            logger.info(fName+printUser()+" response="+response);
            return response;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasPetGotAccess2Restrain(){
        String fName="[hasPetGotAccess2Restrain]";
        try {
            if(!isPetOwned()){return true;}
            return !gUserProfile.jsonObject.getString(iRestraints.nAccess).equals(ACCESSLEVELS.Pet.getName());
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean  isPermalocked(){
        String fName="[isPermalocked (outdated)]";
        try {
            return cLOCK.isPermaLocked();
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPetOwned(){
        String fName="[isPetOwned]";
        try {
            boolean result=!(gUserProfile.jsonObject.getJSONObject(iRestraints.nOwner).isNull(iRestraints.nOwnerId) || gUserProfile.jsonObject.getJSONObject(iRestraints.nOwner).getString(iRestraints.nOwnerId).isBlank() || !gUserProfile.jsonObject.getJSONObject(iRestraints.nOwner).getBoolean(iRestraints.nOwnerAccepted));
            logger.info(fName+printUser() + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isAccessLevelEqual(ACCESSLEVELS access){
        String fName="[isAccessLevelEqual]";
        try {
            if(access==null)access=ACCESSLEVELS.INVALID;
            boolean result= gUserProfile.jsonObject.getString(iRestraints.nAccess).equals(access.getName());
            logger.info(fName+printUser()+" result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isTimeLocked(){
        String fName="[isTimeLocked]";
        logger.info(fName+printUser() + ".executed");
        try {
            //return false;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
            logger.info(fName+printUser()+".timestamp="+timestamp.getTime());
            if(!gUserProfile.jsonObject.has(iRestraints.nTimeLock)){
                logger.info(fName+printUser()+".has no timelock entry");
                return false;
            }
            long startime=gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeLock).getLong(iRestraints.nTimeLockTimestamp);
            logger.info(fName+printUser()+".startime="+startime);
            long diff=timestamp.getTime()-startime;
            logger.info(fName+printUser()+".diff="+diff);
            long duration=gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeLock).getLong(iRestraints.nTimeLockDuration);
            timestampEnd.setTime(startime+duration);
            long remaning=timestampEnd.getTime()-timestamp.getTime();
            logger.info(fName+printUser()+".remaning="+remaning);
            if(gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeLock).getBoolean(iRestraints.nOn)){
                logger.info(fName+printUser()+".timelock enabled");
                if(remaning<0){
                    gUserProfile.putFieldEntry(iRestraints.nTimeLock,iRestraints.nOn,false);
                    logger.info(fName+printUser()+".unlock");
                    gGlobal.putUserProfile(gUserProfile,iRestraints.profileName);
                    if(gUserProfile.saveProfile(iRestraints.table)){
                        logger.info(fName+printUser() + ".success");
                        createEntities();
                    }else{
                        logger.warn(fName+printUser() + ".failed");
                    }
                    return false;
                }else{
                    logger.info(fName+printUser()+".remain locked");
                    if(!gUserProfile.jsonObject.getBoolean(iRestraints.nLocked)){
                        logger.info(fName+printUser()+".apply lock");
                        gUserProfile.jsonObject.put(iRestraints.nLocked,true);
                        gUserProfile.jsonObject.put(iRestraints.nLockedBy,gUserProfile.getUser().getId());
                        if(gUserProfile.saveProfile(iRestraints.table)){
                            logger.info(fName+printUser() + ".success");
                            createEntities();
                        }else{
                            logger.warn(fName+printUser() + ".failed");
                        }
                    }
                    return  true;
                }
            }
            logger.info(fName+printUser()+".default");
            return  false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public entityRDUserProfile untieAll(){
        String fName="[untieAll]";
        try {
            if(cSUIT.getSuitType()==SUITTYPE.ToyAlpha){
                logger.info(fName+printUser() + ".toy suit alpha");
            }else{
                logger.info(fName+printUser() + ".default");
                cGAG.setOn(false).setLevel(GAGLEVELS.None);
                cCHASTITY.setOn(false).setLevel(CHASTITYLEVELS.None).setShockEnabled(false);
                cSUIT.setOn(false).setSuitType( "").setSuitMatterial("");
                gUserProfile.putFieldEntry(iRestraints.nLeash,iRestraints.nOn, false);gUserProfile.putFieldEntry(iRestraints.nLeash,iRestraints.nLeash2Member, 0);
                cHOOD.setOn(false).setLevel(HOODLEVELS.None);
                cEAR.setOn( false).setLevel(EARMUFFSLEVELS.None);
                cBLINDFOLD.setOn( false).setLevel(BLINDFOLDLEVELS.None);
                cCOLLAR.setOn( false).setLevel(COLLARLEVELS.None).setShockeEnabled(false);
                cMITTS.setOn( false).setLevel(MITTSLEVELS.None);
            }
            logger.info(fName+printUser() + ".basic");
            cSTRAITJACKET.setOn( false).setArmsStrapped(false).setCrotchStrapped(false).setStiched(false);
            cLOCK.setLocked(false).setType(LOCKTYPES.DEFAULT).setLockedBy("");
            cARMCUFFS.setOn( false).setLevel(CUFFSARMSLEVELS.None);
            cLEGCUFFS.setOn( false).setLevel(CUFFSLEGSLEVELS.None);
            cEAR.setOn( false).setLevel(EARMUFFSLEVELS.None);
            cBLINDFOLD.setOn( false).setLevel(BLINDFOLDLEVELS.None);
            cENCASE.setOn( false).setLevel(ENCASELEVELS.None);
            cCONFINE.setOn( false).setLevel(CONFINELEVELS.None);
            cBREATPLAY.setOn( false).setLevel(BREATHPLAYLEVELS.None);
            return this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityRDUserProfile  redRestraints(){
        String fName="[redRestraints]";

        try {
            cLOCK.setLocked(false).setType(LOCKTYPES.DEFAULT).setLockedBy("").setPermaLocked(false);

            cARMCUFFS.setOn( false).setLevel(CUFFSARMSLEVELS.None);
            cARMCUFFS.setPostRestrictEnabled(false).setPostDelay(0).setLastPostTimeStamp(0).setPostRestrictionException2UseGuildGag(true).setPostRestrictionException2UseUserGag(true);

            cLEGCUFFS.setOn( false).setLevel(CUFFSLEGSLEVELS.None);
            cLEGCUFFS.setChannelRestrainEnabled(false).setChannelRestrainId(0).setChannelRestrainException2UseGuildGag(true).setChannelRestrainException2UseUserGag(true);

            cGAG.setOn(false).setLevel(GAGLEVELS.None);
            cGAG.setFlagDisableGagOOCException(false).setFlagDisableGagSafeWords(false);
            cGAG.setTimeLockStarted(false).setTimeLockDuration(iRestraints.milliseconds_minute*15).setTimeLockTimestamp(0);
            cGAG.setDisable2Show4Others(false).setDisable2Show4Wearer(false);

            cCHASTITY.setOn(false).setLevel(CHASTITYLEVELS.None).setShockEnabled(false);
            cSUIT.setOn(false).setSuitType( "").setSuitMatterial("");
            gUserProfile.putFieldEntry(iRestraints.nLeash,iRestraints.nOn, false);gUserProfile.putFieldEntry(iRestraints.nLeash,iRestraints.nLeash2Member, 0);
            cHOOD.setOn(false).setLevel(HOODLEVELS.None);
            cEAR.setOn( false).setLevel(EARMUFFSLEVELS.None);
            cBLINDFOLD.setOn( false).setLevel(BLINDFOLDLEVELS.None);
            cCOLLAR.setOn( false).setLevel(COLLARLEVELS.None).setShockeEnabled(false);
            cMITTS.setOn( false).setLevel(MITTSLEVELS.None);
            cSTRAITJACKET.setOn( false).setArmsStrapped(false).setCrotchStrapped(false).setStiched(false).setCrotchLevel(STRAITJACKETCROTCHSLEVEL.NONE).setArmsLevel(STRAITJACKETARMSLEVEL.NONE);

            cTIMELOCK.setOn(false).setTimeLockStart(60000*15).setTimeLockDuration(0).setTimeLockMax(0).setTimeLockMin(0).setTimeLockTimestamp(0);

            cTIMEOUT.setOn(false).setTimeOutMode(0).setChannel(0).setTimeLockMin(0).setTimeOut1Duration(0).setTimeOut1Timestamp(0).setTimeOut2Count(0).setTimeOut2Done(0).setTimeOut2Sentence("");

            cPISHOCK.setEnabled(false).setShock4CollarEnabled(false).setShock4ChastityEnabled(false);

            cENCASE.setOn( false).setLevel(ENCASELEVELS.None);
            cCONFINE.setOn( false).setLevel(CONFINELEVELS.None).setID("");
            cBREATPLAY.setOn( false).setLevel(BREATHPLAYLEVELS.None);
            return this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isPetDenied2HaveAccessIfRestrained(){
        String fName="[isPetDenied2HaveAccessIfRestrained]";
        try {
            ACCESSLEVELS access=cAUTH.getAccess();
            boolean result=false;
            switch (access){
                /*case Ask: case AskPlus:case Protected:
                    result=false;break;*/
                case Public:case Exposed:case ExposedOld:case Key: case Pet:
                    result=true;break;
            }
            logger.info(fName+printUser() + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isOwnerAskedAsWell(){
        String fName="[isOwnerAskedAsWell]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            JSONObject owners=gUserProfile.jsonObject.getJSONObject(iRestraints.nOwner);
            logger.info(fName+printUser() + ".owners="+owners.toString());
            String access=gUserProfile.jsonObject.getString(iRestraints.nAccess);
            logger.info(fName+printUser() + ".access="+access);
            if(access.equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){
                logger.info(fName+printUser() + ".enter 1");
                if(isPetOwned()){
                    logger.info(fName+printUser() + ".enter 2");
                    if(owners.has(iRestraints.vRedirectAsk2OwnerAsWell)&&!owners.isNull(iRestraints.vRedirectAsk2OwnerAsWell)&&owners.getBoolean(iRestraints.vRedirectAsk2OwnerAsWell)){
                        logger.info(fName+printUser() + ".yes>true");
                        return true;
                    }
                }
            }
            logger.info(fName+printUser() + ".default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean reset(){
        String fName="[reset]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            gUserProfile.jsonObject =new JSONObject();
            createEntities();
            return  true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserGotTheKeyOrOwner(User user){
        String fName="[hasUserGotTheKeyOrOwner]";
        try {
            if(!cLOCK.isLocked()){logger.info(fName+printUser()+"is not locked");return true;}
            if(hasUserOwnerAccess(user)||hasUserSecOwnerAccess(user)){logger.info(fName+printUser()+"is owner/secowner");return true;}
            if(cAUTH.getAccess()!=ACCESSLEVELS.Key){logger.info(fName+printUser()+"not set to access key");return true;}
            String sockedBy=cLOCK.getLockedByAsString();
            if(sockedBy==null||sockedBy.isBlank()||sockedBy.isEmpty()){logger.info(fName+printUser()+"no locker");return true;}
            if(cLOCK.getLockedByAsLong()==user.getIdLong()){logger.info(fName+printUser()+"locked by this user");return true;}
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserGotAccess2Restrain(User user){
        String fName="[hasUserGotAccess2Restrain]";
        try {
            if(hasUserOwnerAccess(user)){logger.info(fName+printUser()+"is owner access>true");return true;}
            if(hasUserSecOwnerAccess(user)){logger.info(fName+printUser()+"is secowner access>true");return true;}
            ACCESSLEVELS access=cAUTH.getAccess();
            if(access==ACCESSLEVELS.Protected){logger.info(fName+printUser()+"is access set to protected>false");return false;}
            if(access==ACCESSLEVELS.Key){logger.info(fName+printUser()+"is access set to key>false");return false;}
            logger.info(fName+printUser()+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserOwnerAccess(User user){
        String fName="[hasUserOwnerAccess]";
        try {
            boolean result=cAUTH.hasUserOwnerAccess(user);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean hasUserSecOwnerAccess(User user){
        String fName="[hasUserSecOwnerAccess]";
        try {
            boolean result=cAUTH.hasUserSecOwnerAccess(user);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isAccessAsk(){
        String fName="[isAccessAsk]";
        try {
            boolean result=cAUTH.getAccess()==ACCESSLEVELS.Ask;
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isArmsRestrained(Member member){
        String fName="[isArmsRestrained]";
        try {
            if(isMittsNewRestriction()){
                logger.info(fName+printUser() + ".mitts>true");
                return  true;
            }
            if(isArmsStraitjacketRestriction()){
                logger.info(fName+printUser() + ".jacket>true");
                return  true;
            }
            if(isArmCuffsNewRestriction()){
                logger.info(fName+printUser() + ".armcuffs&armbinder>true");
                return  true;
            }
            logger.info(fName+printUser() + ".default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isLocked_checkMemberAsKeyholder(Member member){
        String fName="[isLocked_checkMemberAsKeyholder]";
        try {
            return cLOCK.isLocked_checkMemberAsKeyholder(member);
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isLocked_checkMemberAsOwner(Member member){
        String fName="[isLocked_checkMemberAsOwner]";
        try {
            if(!cLOCK.isLocked()){
                logger.info(fName+printUser()+" not locked>false");
                return false;
            }
            if(! cAUTH.isOwned()){
                logger.info(fName+printUser()+" not owned>true");
                return true;
            }
            if(cAUTH.getOwnerIdAsLong()==member.getIdLong()){
                logger.info(fName+printUser()+" memebr is owner>false");
                return false;
            }
            logger.info(fName+printUser()+" member is not owner>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isLocked_checkMemberAsSecOwner(Member member){
        String fName="[isLocked_checkMemberAsSecOwner]";
        try {
            if(!cLOCK.isLocked()){
                logger.info(fName+printUser()+" not locked>false");
                return false;
            }
            if(cAUTH.hasUserSecOwnerAccess(member.getId())){
                logger.info(fName+printUser()+" member is secowner>false");
                return false;
            }
            logger.info(fName+printUser()+" member is not secowner>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isLocked_checkMember(Member member){
        String fName="[isLocked_checkMember]";
        try {
            if(!cLOCK.isLocked()){
                logger.info(fName+printUser()+" not locked>false");
                return false;
            }
            if(isLocked_checkMemberAsKeyholder(member)){
                logger.info(fName+printUser()+" member is keyholder>false");
                return false;
            }
            if(isLocked_checkMemberAsOwner(member)){
                logger.info(fName+printUser()+" member is owner>false");
                return false;
            }
            if(isLocked_checkMemberAsSecOwner(member)){
                logger.info(fName+printUser()+" member is secowner>false");
                return false;
            }
            logger.info(fName+printUser()+" default>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0(){
        String fName="[isWearerDenied0]";
        try {
            if(cSTRAITJACKET.areArmsRestrained()){
                logger.info(fName+printUser()+"cSTRAITJACKET.areArmsRestrained>true");
                return true;
            }
            if(cARMCUFFS.areArmsRestrained()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrained>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cMITTS.isOn()){
                logger.info(fName+printUser()+"cMITTS.isOn>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0withException(){
        String fName="[isWearerDenied0withException]";
        try {
            if(!isWearerDenied0()){
                logger.info(fName+printUser()+"!isWearerDenied0>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0Collar(){
        String fName="[isWearerDenied0Collar]";
        try {
            if(cSTRAITJACKET.areArmsRestrained()){
                logger.info(fName+printUser()+"cSTRAITJACKET.areArmsRestrained>true");
                return true;
            }
            if(cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrainedImpossible2ManageCollar>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cMITTS.isOn()){
                logger.info(fName+printUser()+"cMITTS.isOn>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0CollarwithException(){
        String fName="[isWearerDenied0CollarwithException]";
        try {
            if(!isWearerDenied0Collar()){
                logger.info(fName+printUser()+"!isWearerDenied0>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0Chastity(){
        String fName="[isWearerDenied0Chastity]";
        try {
            if(cSTRAITJACKET.areArmsRestrained()){
                logger.info(fName+printUser()+"cSTRAITJACKET.areArmsRestrained>true");
                return true;
            }
            if(cARMCUFFS.areArmsRestrainedImpossible2ManageChastity()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrainedImpossible2ManageCollar>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cMITTS.isOn()){
                logger.info(fName+printUser()+"cMITTS.isOn>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0ChastitywithException(){
        String fName="[isWearerDenied0ChastitywithException]";
        try {
            if(!isWearerDenied0Chastity()){
                logger.info(fName+printUser()+"!isWearerDenied0>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0Ear(){
        String fName="[isWearerDenied0Ear]";
        try {
            if(cSTRAITJACKET.areArmsRestrained()){
                logger.info(fName+printUser()+"cSTRAITJACKET.areArmsRestrained>true");
                return true;
            }
            if(cARMCUFFS.areArmsRestrainedImpossible2ManageEar()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrainedImpossible2ManageEar>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cMITTS.isOn()){
                logger.info(fName+printUser()+"cMITTS.isOn>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0EarwithException(){
        String fName="[isWearerDenied0EarwithException]";
        try {
            if(!isWearerDenied0Ear()){
                logger.info(fName+printUser()+"!isWearerDenied0>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isLockedwithException(boolean restrainOn){
        String fName="[isLockedwithException]";
        try {

            if(!restrainOn){
                logger.info(fName+printUser()+"!restrainOn==false>false");
                return false;
            }
            if(!cLOCK.isLocked()){
                logger.info(fName+printUser()+"isLocked=false>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"allow2BypassNewRestrictions=true>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0Gag(){
        String fName="[isWearerDenied0Gag]";
        try {
            if(cGAG.isOn()&&cLOCK.isLocked()){
                logger.info(fName+printUser()+"cGAG.isOn()&&cLOCK.isLocked()>true");
                return true;
            }
            if(cGAG.isOn()&&cGAG.isTimeLockStarted()){
                logger.info(fName+printUser()+"cGAG.isOn()&&.cGAG.isTimeLockStarted()>true");
                return true;
            }
            if(cSTRAITJACKET.areArmsRestrained()){
                logger.info(fName+printUser()+"cSTRAITJACKET.areArmsRestrained>true");
                return true;
            }
            if(cARMCUFFS.areArmsRestrained()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrained>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cMITTS.isOn()){
                logger.info(fName+printUser()+"cMITTS.isOn>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0GagwithException(){
        String fName="[isWearerDenied0GagwithException]";
        try {
            if(!isWearerDenied0Gag()){
                logger.info(fName+printUser()+"!isWearerDenied2>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0Mitts(){
        String fName="[isWearerDenied0Mitts]";
        try {
            if(cSTRAITJACKET.areArmsRestrained()){
                logger.info(fName+printUser()+"cSTRAITJACKET.areArmsRestrained>true");
                return true;
            }
            if(cARMCUFFS.areArmsRestrainedImpossible2ManageMitts()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrainedImpossible2ManageMitts>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied0MittswithException(){
        String fName="[isWearerDenied0MittswithException]";
        try {
            if(!isWearerDenied0Mitts()){
                logger.info(fName+printUser()+"!isWearerDenied2>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied1(){
        String fName="[isWearerDenied1]";
        try {
            if(cARMCUFFS.areArmsRestrained()){
                logger.info(fName+printUser()+"cARMCUFFS.areArmsRestrained>true");
                return true;
            }
            if(cENCASE.isEncased()){
                logger.info(fName+printUser()+"cENCASE.isEncased>true");
                return true;
            }
            if(cMITTS.isOn()){
                logger.info(fName+printUser()+"cMITTS.isOn>true");
                return true;
            }
            if(cSUIT.isBDSMSuitOn()){
                logger.info(fName+printUser()+"cSUIT.isBDSMSuitOn>true");
                return true;
            }
            logger.info(fName+printUser()+"defualt>false");
            return false;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isWearerDenied1withException(){
        String fName="[isWearerDenied1withException]";
        try {
            if(!isWearerDenied1()){
                logger.info(fName+printUser()+"!isWearerDenied0>false");
                return false;
            }
            if(allow2BypassNewRestrictions()){
                logger.info(fName+printUser()+"exception>false");
                return false;
            }
            logger.info(fName+printUser()+"defualt>true");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isSpeciesHuman(){
        String fName="[isSpeciesHuman]";
        logger.info(fName + ".executed");
        try {
            logger.info(fName + ".gUserProfile.jsonUser="+gUserProfile.jsonObject.toString());
            if(!gUserProfile.jsonObject.has(iRestraints.nSpecies)){
                logger.info(fName + ".has no such key");
                return  false;
            }
            if(gUserProfile.jsonObject.isNull(iRestraints.nSpecies)){
                logger.info(fName + ".key is null");
                return  false;
            }
            logger.info(fName + ".species="+gUserProfile.jsonObject.getInt(iRestraints.nSpecies));
            return gUserProfile.jsonObject.getInt(iRestraints.nSpecies) == 1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
