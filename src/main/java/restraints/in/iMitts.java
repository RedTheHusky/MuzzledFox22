package restraints.in;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface iMitts {

    String strCantGrabAnything="You aren't able to grab anything with those mittens on your paws!";
    String strCantGrabAnything_PetDenied="You aren't able to grab anything with those mittens on your paws!\n~~Your access level must be set to ask or protected.~~";
    static boolean isMittsOn(JSONObject mitts){
        String fName="[isMittsOn]";
        Logger logger = Logger.getLogger(iMitts.class);
        try {
            boolean result=mitts.getBoolean(iRestraints.nOn);
            logger.info(fName + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isMittsEqualLevel(JSONObject mitts,String level){
        String fName="[isMittsEqualLevel]";
        Logger logger = Logger.getLogger(iMitts.class);
        try {
            if(!mitts.has(iRestraints.nLevel)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(mitts.getString(iRestraints.nLevel).equalsIgnoreCase(level)){
                logger.info(fName + ".found>true");
                return true;
            }
            logger.info(fName + ".default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isMittsEqualLevel(lcJSONUserProfile gUserProfile,String level){
        String fName="[isMittsEqualLevel]";
        Logger logger = Logger.getLogger(iMitts.class);
        try {
            JSONObject mitts=gUserProfile.jsonObject.getJSONObject(iRestraints.nMitts);
            if(!mitts.has(iRestraints.nLevel)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(mitts.getString(iRestraints.nLevel).equalsIgnoreCase(level)){
                logger.info(fName + ".found>true");
                return true;
            }
            logger.info(fName + ".default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isMittsOn(lcGlobalHelper gGlobal, Member member){
        String fName="[isMittsOn]";
        Logger logger = Logger.getLogger(iMitts.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            lcJSONUserProfile userProfile=iRestraints.getUserProfile(gGlobal,member, false);
            if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                logger.info(fName + ".invalid>false");
                return  false;
            }
            logger.info(fName + ".userProfile.jsonUser="+userProfile.jsonObject.toString());
            JSONObject mitts=userProfile.jsonObject.getJSONObject(iRestraints.nMitts);
            boolean result=mitts.getBoolean(iRestraints.nOn);
            logger.info(fName + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isMittsEqualLevel(lcGlobalHelper gGlobal, Member member, String level){
        String fName="[isMittsEqualLevel]";
        Logger logger = Logger.getLogger(iMitts.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            lcJSONUserProfile userProfile=iRestraints.getUserProfile(gGlobal,member, false);
            if(userProfile==null||!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                logger.info(fName + ".invalid>false");
                return  false;
            }
            logger.info(fName + ".userProfile.jsonUser="+userProfile.jsonObject.toString());
            JSONObject mitts=userProfile.jsonObject.getJSONObject(iRestraints.nMitts);
            if(!mitts.has(iRestraints.nLevel)){
                logger.info(fName + ".invalid>false");
                return false;
            }
            if(mitts.getString(iRestraints.nLevel).equalsIgnoreCase(level)){
                logger.info(fName + ".found>true");
                return true;
            }
            logger.info(fName + ".default>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    String strRedSoloAsk="Do you wish to used safeword for mitts?",
            strRedSoloYes="You have safeworded for mitts.";
}
