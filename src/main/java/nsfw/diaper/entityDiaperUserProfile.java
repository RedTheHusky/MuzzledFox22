package nsfw.diaper;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ls.lsMemberHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;


import java.util.Arrays;


public class entityDiaperUserProfile implements iDiaperInteractive {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityDiaperUserProfile]";

     public lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
     lcGlobalHelper gGlobal;
     Member gMember;
     Guild gGuild;
     public boolean flagGet4Cache=false,flagPut2Cache=false;
    public entityDiaperUserProfile(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityDiaperUserProfile(lcGlobalHelper global, Member member){
        String fName="[constructor]";
        try {
            build(global,member);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean build(lcGlobalHelper global, Member member){
        String fName="[build]";
        try {
            logger.info(fName);
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
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean buildLight(lcGlobalHelper global, Member member){
        String fName="[buildLight]";
        try {
            logger.info(fName);
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
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(){
        String fName="[getProfile]";
        try {
            logger.info(fName);
            if(flagGet4Cache)getCacheProfile();
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
                return true;
            }else{
                logger.info(fName + ".need to get or create");
                return getSQLProfile();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(){
        String fName="[getCacheProfile]";
        try {
            logger.info(fName);
            if(gGlobal==null){
                logger.warn(fName + ".global is null");
            }
            gUserProfile=gGlobal.getUserProfile(iDiaperInteractive.profileName,gMember,gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
                return true;
            }else{
                logger.info(fName + ".not cached");
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(){
        String fName="[getSQLProfile]";
        try {
            logger.info(fName);
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild);
            if(gUserProfile.getProfile(iDiaperInteractive.gTable)){
                logger.info(fName + ".has sql entry");
                return true;
            }else{
                logger.info(fName + ".no sql entry");
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGlobal(lcGlobalHelper global){
        String fName="[setGlobal]";
        try {
            if(global==null){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            gGlobal=global;
            logger.info(fName+"set");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setMember(Member member){
        String fName="[setMember]";
        try {
            if(member==null){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            gMember=member;
            logger.info(fName+"member="+member.getId());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setGuild(Guild guild){
        String fName="[setGuild]";
        try {
            if(guild==null){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            gGuild=guild;
            logger.info(fName+"guild="+guild.getId());
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getProfile(lcGlobalHelper global,Member member){
        String fName="[getProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            if(!getProfile()){
                logger.info(fName+"failed to get>false");
                return false;
            }
            logger.info(fName+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getCacheProfile(lcGlobalHelper global,Member member){
        String fName="[getCacheProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            if(!getCacheProfile()){
                logger.info(fName+"failed to get>false");
                return false;
            }
            logger.info(fName+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean getSQLProfile(lcGlobalHelper global,Member member){
        String fName="[getSQLProfile]";
        try {
            if(!setGlobal(global)){
                logger.info(fName+"global can't be null>false");
                return false;
            }
            if(!setMember(member)){
                logger.info(fName+"member can't be null>false");
                return false;
            }
            if(!setGuild(member.getGuild())){
                logger.info(fName+"guild can't be null>false");
                return false;
            }
            if(!getSQLProfile()){
                logger.info(fName+"failed to get>false");
                return false;
            }
            logger.info(fName+"default & success > true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean newProfile(){
        String fName="[newProfile]";
        try {
            logger.info(fName);
            gUserProfile=new lcJSONUserProfile(gGlobal,gMember,gGuild);
            gUserProfile= iDiaperInteractive.safetyUserProfileEntry(gUserProfile);
            logger.info(fName + ".created");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setDefaultProfileVariables(){
        String fName="[setDefaultProfileVariables]";
        try {
            logger.info(fName);
            gUserProfile= iDiaperInteractive.safetyUserProfileEntry(gUserProfile);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return false;
            }
            logger.info(fName + ".updated");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putProfile(){
        String fName="[putProfile]";
        try {
            logger.info(fName);
            if(gGlobal.putUserProfile(gUserProfile,iDiaperInteractive.profileName)){
                logger.info(fName + ".success");return true;
            }
            logger.info(fName + ".failed");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveProfile(boolean skipPuttingentities,boolean updateEntities){
        String fName="[saveProfile]";
        try {
            logger.info(fName+"skipPuttingentities="+skipPuttingentities);
            if(!skipPuttingentities)putAllEntities2JSONUserProfile();
            if(flagPut2Cache)gGlobal.putUserProfile(gUserProfile,iDiaperInteractive.profileName);
            if(updateEntities){
                createEntities();
            }
            if(gUserProfile.saveProfile(iDiaperInteractive.gTable)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveJSON(){
        String fName="[saveJSON]";
        try {
            if(gUserProfile.saveProfile(iDiaperInteractive.gTable)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean save2Cache(boolean skipPuttingentities,boolean updateEntities){
        String fName="[save2Cache]";
        try {
            logger.info(fName+"skipPuttingentities="+skipPuttingentities);
            if(!skipPuttingentities)putAllEntities2JSONUserProfile();
            gGlobal.putUserProfile(gUserProfile,iDiaperInteractive.profileName);
            if(updateEntities){
                createEntities();
            }
            if(gUserProfile.saveProfile(iDiaperInteractive.gTable)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
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
                logger.info(fName+"isNull");
                return  null;
            }
            logger.info(fName+"member="+gMember.getId());
            return  gMember;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            if(gGuild==null){
                logger.info(fName+"isNull");
                return  null;
            }
            logger.info(fName+"guild="+gGuild.getId());
            return  gGuild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public entityDiaper cDIAPER=new entityDiaper();
    public entityProfile cProfile=new entityProfile();
    public entityWet cWet=new entityWet();
    public  entityMessy cMessy=new entityMessy();
    public  entityCaretaker cCaretaker=new entityCaretaker();
    public entitySuit cSuit=new entitySuit();
    public entityTimeLock cTimelock=new entityTimeLock(this);
    public entityCustomActions cCustomAction=new entityCustomActions();
    public boolean isProfile(){
        String fName="[isProfile]";
        try {
            logger.info(fName);
            return gUserProfile.isProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean createEntities(){
        String fName="[createEntities]";
        try {
            logger.info(fName);
            logger.info(fName+" diaper="+setEntity_Diaper());
            logger.info(fName+" profile="+setEntity_Profile());
            logger.info(fName+" wet="+setEntity_Wet());
            logger.info(fName+" messy="+setEntity_Messy());
            logger.info(fName+" custom="+setEntity_CustomAction());
            logger.info(fName+" caretaker="+setEntity_Caretaker());
            logger.info(fName+" suit="+setEntity_Suit());
            logger.info(fName+" timelock="+setEntity_Timelock());
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Diaper(){
        String fName="[setEntityDiaper]";
        try {
            logger.info(fName);
            cDIAPER.clear();
            cDIAPER.set(gUserProfile.jsonObject.getJSONObject(fieldDiaper));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Profile(){
        String fName="[setEntityProfile]";
        try {
            logger.info(fName);
            cProfile.clear();
            cProfile.set(gUserProfile.jsonObject.getJSONObject(fieldProfile));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Wet(){
        String fName="[setEntityWet]";
        try {
            logger.info(fName);
            cWet.clear();
            cWet.set(gUserProfile.jsonObject.getJSONObject(fieldWet));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Messy(){
        String fName="[setEntityMessy]";
        try {
            logger.info(fName);
            cMessy.clear();
            cMessy.set(gUserProfile.jsonObject.getJSONObject(fieldMessy));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Timelock(){
        String fName="[setEntityTimelock]";
        try {
            logger.info(fName);
            cTimelock.clear();
            cTimelock.set(gUserProfile.jsonObject.getJSONObject(fieldTimeLock));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Caretaker(){
        String fName="[setEntityCaretaker]";
        try {
            logger.info(fName);
            cCaretaker.clear();
            cCaretaker.set(gUserProfile.jsonObject.getJSONObject(fieldCaretaker));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_Suit(){
        String fName="[setEntitySuit]";
        try {
            logger.info(fName);
            cSuit.clear();
            cSuit.set(gUserProfile.jsonObject.getJSONObject(fieldSuit));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setEntity_CustomAction(){
        String fName="[setEntityCustomAction]";
        try {
            logger.info(fName);
            cCustomAction.clear();
            cCustomAction.set(gUserProfile.jsonObject.getJSONObject(fieldCustomActions));
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean putAllEntities2JSONUserProfile(){
        String fName="[putAllEtities2JSONUserProfile]";
        try {
            logger.info(fName);
            gUserProfile.putFieldEntry(fieldDiaper,cDIAPER.getJSON());
            gUserProfile.putFieldEntry(fieldProfile,cProfile.getJSON());
            gUserProfile.putFieldEntry(fieldWet,cWet.getJSON());
            gUserProfile.putFieldEntry(fieldMessy,cMessy.getJSON());
            gUserProfile.putFieldEntry(fieldTimeLock,cTimelock.getJSON());
            gUserProfile.putFieldEntry(fieldCaretaker,cCaretaker.getJSON());
            gUserProfile.putFieldEntry(fieldCustomActions,cCustomAction.getJSON());
            gUserProfile.putFieldEntry(fieldSuit,cSuit.getJSON());
            logger.info(fName + ".gUserProfile.json="+gUserProfile.jsonObject.toString());
            logger.info(fName + ".success");return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    /*private boolean putAllEntities2JSONUserProfileTry(){
        String fName="[putAllEtities2JSONUserProfileTry]";
        try {
            logger.info(fName);
            try {
                gUserProfile.putFieldEntry(fieldDiaper,cDIAPER.getJSON());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            logger.info(fName + ".success");return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }*/

    public boolean reset(){
        String fName="[reset]";
        try {
            gUserProfile.jsonObject =new JSONObject();
            createEntities();
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isPetOwned(){
        String fName="[isPetOwned]";
        try {
          if(cCaretaker.getCaretakerID().isBlank()){return false;}
          if(!cCaretaker.isAccepted()){return false;}
          return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean isPetLockedBySomebody(){
        String fName="[isPetLockedBySomebody]";
        try {
            if(!cProfile.isLocked()){logger.info(fName+"not locked");return false;}
            if(cProfile.getAccess()!=ACCESSLEVEL.Key){logger.info(fName+"not set to access key");return false;}
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean hasPetGotAccess2Restrain(){
        String fName="[hasPetGotAccess2Restrain]";
        try {
            if(!isPetOwned()){return true;}
            if(cProfile.getAccess()==ACCESSLEVEL.Caretaker){return false;}
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  true;
        }

    }
    public boolean hasUserOwnerAccess( User user){
        String fName="[hasUserOwnerAccess]";
        try {
            if(cCaretaker.getCaretakerID().isBlank()){return false;}
            if(!cCaretaker.isAccepted()){return false;}
            if(cCaretaker.getCaretakerID().equalsIgnoreCase(user.getId())){return true;}
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }

    }
    public boolean hasUserSecOwnerAccess(User user){
        String fName="[hasUserSecOwnerAccess]";
        try {
            return false;

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }

    }
    public boolean hasUserGotTheKeyOrOwner(User user){
        String fName="hasUserGotTheKeyOrOwner.";
        try {
            if(!cProfile.isLocked()){logger.info(fName+"is not locked");return true;}
            if(hasUserOwnerAccess(user)||hasUserSecOwnerAccess(user)){logger.info(fName+"is owner/secowner");return true;}
            if(cProfile.getAccess()!=ACCESSLEVEL.Key){logger.info(fName+"not set to access key");return true;}
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean hasUserGotAccess2Restrain(User user){
        String fName="[hasUserGotAccess2Restrain]";
        try {
            if(hasUserOwnerAccess(user)){logger.info(fName+"is owner access>true");return true;}
            if(hasUserSecOwnerAccess(user)){logger.info(fName+"is secowner access>true");return true;}
            if(cProfile.getAccess()==ACCESSLEVEL.Protected){logger.info(fName+"is access set to protected>false");return false;}
            if(cProfile.getAccess()==ACCESSLEVEL.Caretaker){logger.info(fName+"is access set to caretaker>false");return false;}
            logger.info(fName+"default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public String getUserWhoLockedPet(){
        String fName="[getUserWhoLockedPet]";
        try {
            if(cProfile.getLockedBy()!=0){
                String id=String.valueOf(cProfile.getLockedBy());
                String mention= lsMemberHelper.lsGetMemberMention(gUserProfile.getGuild(),id);
                if(mention==null||mention.isEmpty()||mention.isBlank()){
                    return "<"+id+">";
                }
                return mention;
            }
            return "n/a";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "n/a";
        }
    }


    public entityDiaperUserProfile runaway() {
       String fName="[runaway]";
        try {
            cProfile.runaway();
            cCaretaker.runaway();
            cWet.runaway();
            cMessy.runaway();
            cSuit.setOff();
            cDIAPER.setOff();
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
