package restraints.in;

import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;
import restraints.models.entity.entityConfine;

import java.util.Arrays;

public interface iConfine {
    String stPermaLock ="Can't manipulate your restraints as they are permanently locked!",
    strLocked ="Can't manipulate your confinement as !LOCKER has the keys!",
    strAccessSet2Pet ="Can't manipulate your confinement as only your owner and sec-owners can do that.",
    strAccessSet2Public ="Can't manipulate your confinement as your access set to public. While public everyone else can access it except you.",
    strIsAlreadyOn ="The confinement is already on, silly; What are you going to do with 2 of them!?",
    strPutBlindolfOn="You put a confinement over your eyes.",
    srtPutBlindfoldOnMention="!USER has put a confinement over their eyes.",
    strPutPannel="You put a panelled confinement over your eyes, loosing your sight in the process.",
    strPutPannelMention="!USER has put panelled confinement over their eyes, loosing their sight in the process.",
    strPutContacts="You put contacts into your eyes, perfectly white, yet not letting so much as a beam of light in!",
    strPutContactsMention="!USER has put contacts into their eyes, perfectly white, yet not letting so much as a beam of light in!",
    strTakeOff="You managed to remove the confinement from your eyes, you can use your eyes again -- after they adjust that is!",
    strTakeOffMention="!USER managed to take off their confinement. Someone must have forgot to secure it.",
    strNotOn="The confinement is not on, silly.",
    strCantTakeOffDue2Jacket="!USER attempted to take off their confinement, but thankfully someone was thoughtful enough to tighten their straitjacket enough to prevent it.",
    strCantTakeOffDueLocked="!USER struggled to take off their confinement but failed because of the big padlock in the way!";

    String strCantDuePermalockTarget="Can't manipulate !TARGET's restraints as they are permanently locked!",
    strCantDueLockedTarget="Can't manipulate their confinement as it is locked by !LOCKER",
    strCantDueAccess="Can't manipulate their confinement due to their access setting.";

    String strSoloRelease1="You release yourself from confines.",
            strSoloRelease2="!USER release themselves from confines.";
    String strSoloCell1="You confine yourself in a cell.",
            strSoloCell2="!USER confines themselves to a cell.";
    String strSoloPadded1="You confine yourself in a squishy padded room.",
            strSoloPadded2="!USER confines themselves in a squishy padded room.";
    String strSoloSack1="You place yourself into a bag.",
            strSoloSack2="!USER places themselves into a bag.";
    String strSoloCircle1="You summon a magic circle around yourself.",
            strSoloCircle2="!USER summons themselves a magic circle themselves.";
    String strSoloPit1="You drop yourself into a pit",
            strSoloPit2="!USER drops themselves into a pit.";

    String strTargetRelease1="You release !TARGET from confines",
            strTargetRelease2="!USER releases you from confines",
            strTargetRelease3="!USER releases !TARGET from confines";
    String strTargetCell1="You confine !TARGET to a cell.",
            strTargetCell2="!USER confines you to a cell.",
            strTargetCell3="!USER confines !TARGET to a cell.";
    String strTargetPadded1="You confine !TARGET in a squishy padded room.",
            strTargetPadded2="!USER confines you in a squishy padded room.",
            strTargetPadded3="!USER confines !TARGET in a squishy padded room.";
    String strTargetSack1="You places !TARGET into a bag.",
            strTargetSack2="!USER places you into a bag.",
            strTargetSack3="!USER places !TARGET into a bag.";
    String strTargetCircle1="You summon a magic circle around !TARGET.",
            strTargetCircle2="!USER summons a magic circle around you.",
            strTargetCircle3="!USER summons a magic circle around !TARGET.";
    String strTargetPit1="You drop !TARGET into a pit.",
            strTargetPit2="!USER drops you into a pit.",
            strTargetPit3="!USER drops !TARGET into a pit.";
    String commandCell="cell",commandPadded="padded",commandSack="sack",commandCircle="circle",commandPit="pit",commandRelease="release";
    String commandIDClear="idclear",commandID="id",commandIDSet="idset";
    static String sStringReplacer(lcJSONUserProfile gUserProfile, Member author, String source){
        String fName="[sStringReplacer]";
        Logger logger = Logger.getLogger(iMitts.class);
        logger.info(fName + ".executed");
        try {
            source=iRestraints.sStringReplacer(gUserProfile,author,source);
            String id="",level="";
            if(gUserProfile.jsonObject.has(iRestraints.nConfine)&&gUserProfile.jsonObject.getJSONObject(iRestraints.nConfine).has(iRestraints.nLevel)){
                level=gUserProfile.jsonObject.getJSONObject(iRestraints.nConfine).getString(iRestraints.nLevel);
            }
            if(gUserProfile.jsonObject.has(iRestraints.nConfine)&&gUserProfile.jsonObject.getJSONObject(iRestraints.nConfine).has(iRestraints.nID)){
                id=gUserProfile.jsonObject.getJSONObject(iRestraints.nConfine).getString(iRestraints.nID);
            }
            source=source.replaceAll("!ID",id).replaceAll("!LEVEL",level);
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    static boolean sSameConfinement(lcJSONUserProfile WearerProfile, lcJSONUserProfile UserProfile){
        String fName="[sSameConfinement]";
        Logger logger = Logger.getLogger(iConfine.class);
        logger.info(fName + ".executed");
        try {
            JSONObject wearerJSON=new JSONObject(),userJSON=new JSONObject();
            if(WearerProfile!=null&&WearerProfile.jsonObject !=null&&WearerProfile.jsonObject.has(iRestraints.nConfine)){
                wearerJSON=WearerProfile.jsonObject.getJSONObject(iRestraints.nConfine);
            }else{
                logger.info(fName + ".wearerprofile invalid>false");
                return false;
            }
            if(UserProfile!=null&UserProfile.jsonObject !=null&&UserProfile.jsonObject.has(iRestraints.nConfine)){
                userJSON=UserProfile.jsonObject.getJSONObject(iRestraints.nConfine);
            }else{
                logger.info(fName + ".userprofile invalid>false");
                return false;
            }
            logger.info(fName + ".wearerprofile="+wearerJSON.toString());
            logger.info(fName + ".userJSON="+userJSON.toString());
            if(!wearerJSON.getString(iRestraints.nLevel).equalsIgnoreCase(userJSON.getString(iRestraints.nLevel))){
                logger.info(fName + ".not same level>false");
                return false;
            }
            if(!wearerJSON.getString(iRestraints.nID).equalsIgnoreCase(userJSON.getString(iRestraints.nID))){
                logger.info(fName + ".not same ID>false");
                return false;
            }
            logger.info(fName + ".default>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    String strYouAreNotInsameCell="You are not in same cell as !TARGET!";
    String strTargetIsNotLockedWithYou="!TARGET is not confined like you!";
    static boolean isAuthorConfined(lcGlobalHelper gGlobal, Member member){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName = "[isAuthorConfined]";
        logger.info(fName);
        try {
            lcJSONUserProfile userProfile=iRestraints.getThisPersonProfile(gGlobal,member);
            entityConfine authorConfine=new entityConfine();
            authorConfine.set(userProfile.jsonObject);
            boolean result=  authorConfine.isConfined();
            logger.info(fName+"result="+result);
            return result;
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }
    static boolean isAuthorConfinedAndNotSameConfinment(lcGlobalHelper gGlobal, Member author,entityConfine targetConfine){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName = "[isAuthorConfined]";
        logger.info(fName);
        try {
            lcJSONUserProfile userProfile=iRestraints.getThisPersonProfile(gGlobal,author);
            entityConfine authorConfine=new entityConfine();
            authorConfine.set(userProfile.jsonObject);
            boolean isauthorconfined=  authorConfine.isConfined();
            logger.info(fName+"isauthorconfined="+isauthorconfined);
            if(!isauthorconfined){
                logger.info(fName+"author is not confined>false");
                return  false;
            }
            if(!targetConfine.isConfined()){
                logger.info(fName+"target not confined>true");
                return  true;
            }
            String authorCell=authorConfine.getLevelAsString()+"#"+authorConfine.getID();
            logger.info(fName+"authorCell="+ authorCell);
            String targetCell=targetConfine.getLevelAsString()+"#"+targetConfine.getID();
            logger.info(fName+"targetCell="+ authorCell);
            if(authorCell.equalsIgnoreCase(targetCell)){
                logger.info(fName+"equal>false");
                return  false;
            }
            logger.info(fName+"not equal>true");
            return  true;
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }

    String strRedSoloAsk="Do you wish to used safeword for confine?",
            strRedSoloYes="You have safeworded for confine.";
}
