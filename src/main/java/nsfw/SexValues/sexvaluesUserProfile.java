package nsfw.SexValues;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;


public class sexvaluesUserProfile {

    Logger logger = Logger.getLogger(getClass());

     public lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
     lcGlobalHelper gGlobal;
     Member gMember;
     Guild gGuild;
     public boolean flagGet4Cache=false,flagPut2Cache=false;
    public sexvaluesUserProfile(){
        String fName="[constructor]";
        logger.info(fName+printUser());
    }
    public sexvaluesUserProfile(lcGlobalHelper global, Member member){
        String fName="[constructor]";
        try {
            build(global, member);
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean build(lcGlobalHelper global, Member member){
        String fName="[build]";
        try {
            logger.info(fName+printUser());
            gGlobal=global;
            gMember=member;
            gGuild=gMember.getGuild();
            flagGet4Cache=true;flagPut2Cache=true;
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
    private boolean buildLight(lcGlobalHelper global, Member member){
        String fName="[buildLight]";
        try {
            logger.info(fName+printUser());
            gGlobal=global;
            gMember=member;
            gGuild=gMember.getGuild();
            flagGet4Cache=true;flagPut2Cache=true;
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
    private boolean getProfile(){
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
    private boolean getCacheProfile(){
        String fName="[getCacheProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=gGlobal.getUserProfile(llGlobalHelper.llMemberProfile,gMember,gGuild,iSexValues.profileName);
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
    private boolean getSQLProfile(){
        String fName="[getSQLProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild,iSexValues.profileName,llGlobalHelper.llMemberProfile);
            if(gUserProfile.getProfile()){
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
    private boolean getProfile(lcGlobalHelper global,Member member){
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
    private boolean getCacheProfile(lcGlobalHelper global,Member member){
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
    private boolean getSQLProfile(lcGlobalHelper global,Member member){
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
    private boolean newProfile(){
        String fName="[newProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild,iSexValues.profileName,llGlobalHelper.llMemberProfile);
            setDefaultProfileVariables();
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
            if(gUserProfile.jsonObject ==null)gUserProfile.jsonObject =new JSONObject();
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,new JSONObject());
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,iSexValues.PROFILE.Score.affect,-1);
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,iSexValues.PROFILE.Score.attract,-1);
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,iSexValues.PROFILE.Score.deviance,-1);
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,iSexValues.PROFILE.Score.dominant,-1);
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,iSexValues.PROFILE.Score.drive,-1);
            gUserProfile.safetyPutFieldEntry(iSexValues.PROFILE.score,iSexValues.PROFILE.started,false);
            logger.info(fName+printUser() + ".completed");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean resetProfile(){
        String fName="[resetProfile]";
        try {
            logger.info(fName+printUser());
            gUserProfile.jsonObject =new JSONObject();
            setDefaultProfileVariables();
            logger.info(fName+printUser() + ".completed");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean putProfile(){
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
    private boolean saveProfile(boolean skipPuttingentities,boolean updateEntities){
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
    private boolean saveJSON(){
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
    private boolean save2Cache(boolean skipPuttingentities,boolean updateEntities){
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
    private boolean save2Cache(){
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
    private boolean createEntities(){
        String fName="[createEntities]";
        try {
            logger.info(fName+printUser());
            score.set(gUserProfile.jsonObject.getJSONObject(iSexValues.PROFILE.score));
            logger.info(fName+printUser()+"done");
            return true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean putAllEntities2JSONUserProfile(){
        String fName="[putAllEtities2JSONUserProfile]";
        try {
            logger.info(fName+printUser());

            logger.info(fName+printUser() + ".gUserProfile.json="+gUserProfile.jsonObject.toString());
            logger.info(fName+printUser() + ".success");return  true;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    SCORE score=new SCORE();
    public class SCORE{
        Logger logger = Logger.getLogger(getClass());
        private JSONObject jsonScore=new JSONObject();
        public SCORE(){

        }
        public SCORE set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null) throw  new Exception("input cant be null");
                if(jsonObject.isEmpty()) throw  new Exception("input cant be emoty");
                jsonScore=jsonObject;
                logger.info("jsonScore="+jsonScore.toString());
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SCORE start(){
            String fName="[start]";
            try {
                double value=50;
                jsonScore.put(iSexValues.PROFILE.Score.attract,value);
                jsonScore.put(iSexValues.PROFILE.Score.affect,value);
                jsonScore.put(iSexValues.PROFILE.Score.deviance,value);
                jsonScore.put(iSexValues.PROFILE.Score.dominant,value);
                jsonScore.put(iSexValues.PROFILE.Score.drive,value);
                logger.info("done");
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SCORE setAttract(double value){
            String fName="[setAttract]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.attract,value);
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SCORE setAffect(double value){
            String fName="[setAffect]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.affect,value);
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SCORE setDeviance(double value){
            String fName="[setDeviance]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.deviance,value);
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SCORE setDominant(double value){
            String fName="[setDominant]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.dominant,value);
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public SCORE setDrive(double value){
            String fName="[setDrive]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.drive,value);
                return this;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public double getAttract(){
            String fName="[getAttract]";
            try {
                double value=jsonScore.getDouble(iSexValues.PROFILE.Score.attract);
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getAffect(){
            String fName="[getAffect]";
            try {
                double value=jsonScore.getDouble(iSexValues.PROFILE.Score.affect);
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDeviance(){
            String fName="[getDeviance]";
            try {
                double value=jsonScore.getDouble(iSexValues.PROFILE.Score.deviance);
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDominant(){
            String fName="[getDominant]";
            try {
                double value=jsonScore.getDouble(iSexValues.PROFILE.Score.dominant);
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDrive(){
            String fName="[getDrive]";
            try {
                double value=jsonScore.getDouble(iSexValues.PROFILE.Score.drive);
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getAttract_Male(){
            String fName="[getAttract_Male]";
            try {
                double value=getAttract();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getAttract_Female(){
            String fName="[getAttract_Female]";
            try {
                double value=100-getAttract();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getAffect_Affection(){
            String fName="[getAffect_Affection]";
            try {
                double value=getAffect();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getAffect_Hedonism(){
            String fName="[getAffect_Hedonism(]";
            try {
                double value=100-getAffect();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDeviance_Deviant(){
            String fName="[getDeviance_Deviant]";
            try {
                double value=getDeviance();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDeviance_Pure(){
            String fName="[getDeviance_Pure]";
            try {
                double value=100-getDeviance();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDominant_Dominant(){
            String fName="[getDominant_Dominant]";
            try {
                double value=getDominant();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDominant_Submissive(){
            String fName="[getDominant_Submissive]";
            try {
                double value=100-getDominant();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDrive_Hyper(){
            String fName="[getDrive_Hyper_Hyper]";
            try {
                double value=getDrive();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getDrive_Hypo(){
            String fName="[getDrive_Hypo]";
            try {
                double value=100-getDrive();
                logger.info("value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double addAttract(double value){
            String fName="[addAttract]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.attract,getAttract()+value);
                return getAttract();
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double addAffect(double value){
            String fName="[addAffect]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.affect,getAffect()+value);
                return getAffect();
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double addDeviance(double value){
            String fName="[addDeviance]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.deviance,getDeviance()+value);
                return getDeviance();
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double addDominant(double value){
            String fName="[addDominant]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.dominant,getDominant()+value);
                return getDominant();
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double addDrive(double value){
            String fName="[addDrive]";
            try {
                logger.info("value="+value);
                jsonScore.put(iSexValues.PROFILE.Score.drive,getDrive()+value);
                return getDrive();
            }catch (Exception e){
                logger.error(fName+printUser() + ".exception=" + e);
                logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
    }
    public sexvaluesUserProfile setStarted(boolean value){
        String fName="[isStarted]";
        try {
            logger.info("value="+value);
            gUserProfile.jsonObject.put(iSexValues.PROFILE.started,value);
            return this;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isStarted(){
        String fName="[isStarted]";
        try {
            boolean result=gUserProfile.jsonObject.getBoolean(iSexValues.PROFILE.started);
            logger.info("result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName+printUser() + ".exception=" + e);
            logger.error(fName+printUser() + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
