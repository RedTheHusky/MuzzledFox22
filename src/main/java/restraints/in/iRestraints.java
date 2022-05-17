package restraints.in;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ls.lsAssets;
import models.ls.lsMemberHelper;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import restraints.models.enums.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface iRestraints{
    String quickAlias="rd";
    int intColorRed=7865605,intColorGreen=2263842,intColorPurple=9662683;
    String nAccess="access";
    String nLocked="locked",nLockType="lockType"; String nNotification="notification";
    String nCollar="collar",nArmsCuffs="armscuffs",nLegsCuffs="legscuffs",nMitts="mitts",nBelt="belt",nGag="gag",nLeash="leash",nBlindfold="blindfold", nChastity="chastity", nHood="hood", nEarMuffs="earmuffs";
    String  nStraitjacket="straitjacket",
            nStrapArms="straparms", nStrapCrotch="strapcrotch",nStrapArmsLevel="straparmsLevel",nStrapCrotchLevel="strapcrotchLevel",nStrapStitch="stitch";
    String nOn="on";
    String nTightness ="tightness";String nLevel="level";String nType="type";
    String nWrist="wrist"; String nElbows="elbow";String nNone="none";String nUntie="untie", nRelease="release",nTo="to";
    String nAnkles="ankles"; String nKnees="knees";
    String nLeashLength="length";String nLeash2Obj="obj";String nLeash2Member="member";
    String nImageUrlOn ="imgOn",nImageUrlOff ="imgOff",nImageUrlSecure ="imgSecure",nImageUrlUnSecure ="imgUnsecure",nImageUrlStruggle ="imgStruggle";
    String vOn="on",vOff="off",vAdd="add",vRemove="remove",vClear="clear",vList="list",vSet="set",vRem="rem",vSetup="setup",vRed="red";
    String nGagSafeword="red";
    String nTrainingGagEntries="trainingGagEntries",nFrom="from", nDisable2Show4Wearer ="disable2Show4Wearer", nDisable2Show4Others ="disable2Show4Others";
    String flagOnlyUseCustom="onlyUseCustom",flagOnlyGuildCustom="onlyGuildCustom",flagEnableCustom="enableCustom",flagDisableGagSafeWords="disablegagsafewords",flagDisableGagOOCException="disablegagoocexception";
    String nChannel="channel";
    String vTrue="true",vFalse="false";

    String nEncase="encase";
    String nConfine="confine",nID="id";
    String nBreathplay="breathplay";

    default int isDeafened(Member member){
        String fName="isDeafened.";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(member.getVoiceState()==null)return -1;
            if(member.getVoiceState().isGuildDeafened())return 1;
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    default int isMute(Member member){
        String fName="isMute.";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(member.getVoiceState()==null)return -1;
            if(member.getVoiceState().isMuted())return 1;
            return 0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    int actionChangeLevel=0,actionSecured=1,actionUnSecured=2,actionStruggle=3,actionLock=4,actionUnLock=5, actionPutOn=6, actionTakeOff=7;

    String table="rdrestraints";String profileName="rdrestraints";

    String nOwner="owner", nOwnerId="id", nOwnerAccepted="accepted", nSecOnwers="secowners";
    String nLockedBy="lockedBy",nException="exception",nExceptionList="exceptionList";

    String nChannelRestrictions ="channelRestrictions", nRestrictionGrant="grant", nRestrictionDeny="deny", nRestrictionClear="clear";
    String nCustomGagText="customGagText";

    String levelChastityShockAdd="add",levelChastityShockRem="remove";
    String levelChastityZap="zap", levelChastityWarn="warn",levelChastityPunish="punish", levelChastityReward="reward", nShock="shock";
    String nPermalocked="permalocked", commandPermalock="permalock",commandUnseal="unseal";
    String nVoiceChannelRestriction="voiceChannelRestriction", nVoiceChannelRestriction_MuteEnabled="muteEnabled",nVoiceChannelRestriction_MuteInUse="muteInUse", nVoiceChannelRestriction_ListenEnabled="listenEnabled",nVoiceChannelRestriction_ListenInUse="listenInUse";
    String optionVoice="voice",optionVoiceMute="mute",optionVoiceUnmute="unmute",optionVoiceDeafen="deafen",optionVoiceUndeafen="undeafen";
    String optionEnable="enable",optionDisable="disable",optionLock="lock",optionUnlock="unlock",optionAskredirecOn="askredirec_on",optionAskredirecOff="askredirect_off",optionReset="reset",optionHelp="help";
    String optionChastity="chastity",optionCollar="collar",optionList="list";
    String nGender="gender";int vGenderNeutral=0,vGenderMale=1,vGenderFemale=2;
    String nSpecies="species", nSpeciesName="speciesName";int vSpecieFurry=0,vSpecieHuman=1,vSpecieNeko=2;

    String nPostRestrictEnabled="postRestrictEnabled",nPostDelay="postDelay",nLastPostTimeStamp="lastPostTimeStamp", nPostRestrictionException2UseUserGag ="postRestrictionException2UseUserGag", nPostRestrictionException2UseGuildGag ="postRestrictionException2UseGuildGag";
    long vPostDelay_1=5000,vPostDelay_2=15000,vPostDelay_3=30000,vPostDelay_4=45000,vPostDelay_5=60000;
    String nChannelRestrainEnabled="channelRestrainEnabled",nChannelRestrainId="channelRestrainId", nChannelRestrainException2UseUserGag ="postChannelRestrainException4UserGag", nChannelRestrainException2UseGuildGag ="postChannelRestrainException4GuildGag";
    default lcJSONUserProfile iUserInit(lcJSONUserProfile gUserProfile, lcJSONGuildProfile gProfileRestrains){
        String fName="[iSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return sUserInit(gUserProfile, gProfileRestrains);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    String nTimeLock="timelock",nTimeLockStart="start",nTimeLockMax="max",nTimeLockMin="min",nTimeLockDuration="duration",nTimeLockTimestamp="timestamps";
    String nCount="count", nRemaining ="remaining";
    String nTimeOut="timeout",nTimeOutMode="mode",nTimeoutModeTimer="timer",nTimeoutModeWritting="writting", nTimeOut1Timestamp ="timestamps", nTimeOut1Duration ="duration", nTimeOut2Sentence ="sentence", nTimeOut2Count ="count", nTimeOut2Done ="done";

    String nPishock="pishock",nPishockShockers="shockers",nPishockSelected="selected",nPishockShock4CollarEnabled="shock4collarEnabled",nPishockShock4ChastityEnabled="shock4chastityEnabled";
    String nPishockCollarShocker="collarShocker",nPiShockChastityShocker="chastityShocker";
    String nPishockUsername="username",nPishockApikey="apikey";
    String nPishockActions="actions",nPishockCode="code",nPishockMode="mode",nPishockDuration="duration",nPishockItensity="itensity",nPishockName="name";
    String nPishockAction_warn ="warn", nPishockAction_zap ="zap", nPishockAction_punish ="punish",nPishockAction_reward ="reward",nPishockAction_main="main";
    String vPishockMode_Beep="beep",vPishockMode_Vibrate="vibrate",vPishockMode_Shock="shock";
    String nPishockGameWinShocker="gameWin",nPishockGameLoseShocker="gameLose";
    String nMembers="members";
    String vRedirectAsk2OwnerAsWell="redirectAsk2OwnerAsWell";
    String fieldServerNotificationDisabled ="notificationDisabled", fieldServerNotificationRedirect ="notificationRedirect";
    static lcJSONUserProfile sUserInit(lcJSONUserProfile gUserProfile, lcJSONGuildProfile gProfileRestrains){
        String fName="[sSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            try {
                if(gProfileRestrains!=null&&gProfileRestrains.isExistent()&&gProfileRestrains.jsonObject !=null){
                    if(gProfileRestrains.jsonObject.has(fieldDefaultAccessMode)&&!gProfileRestrains.jsonObject.isNull(fieldDefaultAccessMode)){
                        String tmp=gProfileRestrains.jsonObject.getString(fieldDefaultAccessMode);
                        logger.info(fName + ".DefaultAccessMode=" + tmp);
                        if(!tmp.isBlank()&&!tmp.isBlank()){
                            gUserProfile.safetyPutFieldEntry(nAccess, tmp);
                        }else{
                            logger.warn(fName + ".is blank"); gUserProfile.safetyPutFieldEntry(nAccess, ACCESSLEVELS.Ask.getName());
                        }
                    }else{
                        logger.warn(fName + ".has none"); gUserProfile.safetyPutFieldEntry(nAccess, ACCESSLEVELS.Ask.getName());
                    }
                }else{
                    logger.warn(fName + ".not loaded"); gUserProfile.safetyPutFieldEntry(nAccess, ACCESSLEVELS.Ask.getName());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                gUserProfile.safetyPutFieldEntry(nAccess, ACCESSLEVELS.Ask.getName());
            }
            return sUserInit(gUserProfile);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    String keyExtra="extra";
    static lcJSONUserProfile sUserInit(lcJSONUserProfile gUserProfile){
        String fName="[sSafetyUserProfileEntry]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            gUserProfile.safetyPutFieldEntry(nGender,vGenderNeutral);
            gUserProfile.safetyPutFieldEntry(nSpecies,vSpecieFurry);
            gUserProfile.safetyPutFieldEntry(nSpeciesName,"");
            gUserProfile.safetyPutFieldEntry(nNotification,true);
            gUserProfile.safetyPutFieldEntry(nLocked, false);gUserProfile.safetyPutFieldEntry(nLockType, "");gUserProfile.safetyPutFieldEntry(nLockedBy, "");
            gUserProfile.safetyPutFieldEntry(nPermalocked, false);
            gUserProfile.safetyCreateFieldEntry(nOwner);
            gUserProfile.safetyPutFieldEntry(nOwner,nOwnerId,""); gUserProfile.safetyPutFieldEntry(nOwner,nOwnerAccepted,false);gUserProfile.safetyPutFieldEntry(nOwner,vRedirectAsk2OwnerAsWell,false);gUserProfile.safetyPutFieldEntry(nOwner,nSecOnwers,new JSONObject());

            gUserProfile.safetyCreateFieldEntry(nStraitjacket);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nOn,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapArms,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapArmsLevel,"");
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapCrotch,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapCrotchLevel,"");
            gUserProfile.safetyPutFieldEntry(nStraitjacket,nStrapStitch,false);
            gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlOn,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlOff,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlSecure,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nStraitjacket, nImageUrlStruggle,"");

            gUserProfile.safetyCreateFieldEntry(nCollar);
            gUserProfile.safetyPutFieldEntry(nCollar,nOn,false);
            gUserProfile.safetyPutFieldEntry(nCollar,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nCollar,nShockeEnabled,false);
            gUserProfile.safetyPutFieldEntry(nCollar,nAutoShockException4GuildGagException,true);
            gUserProfile.safetyPutFieldEntry(nCollar,nAutoShockException4UserGagException,true);
            gUserProfile.safetyPutFieldEntry(nCollar,nBadWords,new JSONArray());
            gUserProfile.safetyPutFieldEntry(nCollar,nEnforcedWords,new JSONArray());

            gUserProfile.safetyCreateFieldEntry(nBelt);
            gUserProfile.safetyPutFieldEntry(nBelt,nOn,false);
            gUserProfile.safetyPutFieldEntry(nBelt,nTightness,0);

            gUserProfile.safetyCreateFieldEntry(nArmsCuffs);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nOn,false);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostRestrictEnabled, false);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostDelay, 0);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nLastPostTimeStamp, 0);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostRestrictionException2UseGuildGag, true);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs,nPostRestrictionException2UseUserGag, true);
            gUserProfile.safetyPutFieldEntry(nArmsCuffs, nImageUrlSecure,"");gUserProfile.safetyPutFieldEntry(nArmsCuffs, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nArmsCuffs, nImageUrlStruggle,"");

            gUserProfile.safetyCreateFieldEntry(nLegsCuffs);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nOn,false);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nChannelRestrainEnabled,false);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nChannelRestrainId,0);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs, nChannelRestrainException2UseUserGag,true);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs, nChannelRestrainException2UseGuildGag,true);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs, nImageUrlOn,"");gUserProfile.safetyPutFieldEntry(nLegsCuffs, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nLegsCuffs, nImageUrlStruggle,"");

            gUserProfile.safetyCreateFieldEntry(nMitts);
            gUserProfile.safetyPutFieldEntry(nMitts,nOn,false);
            gUserProfile.safetyPutFieldEntry(nMitts,nLevel,nNone);

            gUserProfile.safetyCreateFieldEntry(nGag);
            gUserProfile.safetyPutFieldEntry(nGag,nOn,false);
            gUserProfile.safetyPutFieldEntry(nGag,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nGag,nCustomGagText,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nGag,flagOnlyUseCustom,false);
            gUserProfile.safetyPutFieldEntry(nGag,flagEnableCustom,true);// gUserProfile.safetyPutFieldEntry(flagEnableCustom,true);
            gUserProfile.safetyPutFieldEntry(nGag,flagDisableGagOOCException,false);
            gUserProfile.safetyPutFieldEntry(nGag,flagDisableGagSafeWords,false);
            gUserProfile.safetyPutFieldEntry(nGag,nType,GAGTYPES.Ball.getName());
            gUserProfile.safetyPutFieldEntry(nGag,nException,false);
            gUserProfile.safetyPutFieldEntry(nGag,nExceptionList,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nGag, nImageUrlSecure,"");gUserProfile.safetyPutFieldEntry(nGag, nImageUrlUnSecure,"");gUserProfile.safetyPutFieldEntry(nGag, nImageUrlStruggle,"");
            gUserProfile.safetyPutFieldEntry(nGag,nTrainingGagEntries,new JSONArray());
            gUserProfile.safetyPutFieldEntry(nGag,nTimeLockStart,false);
            gUserProfile.safetyPutFieldEntry(nGag,nTimeLockTimestamp,0L);
            gUserProfile.safetyPutFieldEntry(nGag,nTimeLockDuration,milliseconds_minute*15);
            gUserProfile.safetyPutFieldEntry(nGag, nDisable2Show4Wearer,false);
            gUserProfile.safetyPutFieldEntry(nGag, nDisable2Show4Others,false);
            gUserProfile.safetyPutFieldEntry(nGag, keyExtra,new JSONObject());

            gUserProfile.safetyCreateFieldEntry(nLeash);
            gUserProfile.safetyPutFieldEntry(nLeash,nOn,false);
            gUserProfile.safetyPutFieldEntry(nLeash,nLeashLength,3);
            gUserProfile.safetyPutFieldEntry(nLeash,nLeash2Member,0L);
            gUserProfile.safetyPutFieldEntry(nLeash,nLeash2Obj,"");

            gUserProfile.safetyCreateFieldEntry(nBlindfold);
            gUserProfile.safetyPutFieldEntry(nBlindfold,nOn,false);
            gUserProfile.safetyPutFieldEntry(nBlindfold,nLevel,nNone);

            gUserProfile.safetyCreateFieldEntry(nChastity);
            gUserProfile.safetyPutFieldEntry(nChastity,nOn,false);
            gUserProfile.safetyPutFieldEntry(nChastity,nLevel,nNone);
            gUserProfile.safetyPutFieldEntry(nChastity,nShock,false);

            gUserProfile.safetyCreateFieldEntry(nHood);
            gUserProfile.safetyPutFieldEntry(nHood,nOn,false);
            gUserProfile.safetyPutFieldEntry(nHood,nLevel,nNone);

            gUserProfile.safetyCreateFieldEntry(nEarMuffs);
            gUserProfile.safetyPutFieldEntry(nEarMuffs,nOn,false);
            gUserProfile.safetyPutFieldEntry(nEarMuffs,nLevel,nNone);

            gUserProfile.safetyCreateFieldEntry(nChannelRestrictions);

            gUserProfile.safetyCreateFieldEntry(nRPSpeach);
            gUserProfile.safetyPutFieldEntry(nRPSpeach,nOn,false);
            gUserProfile.safetyPutFieldEntry(nRPSpeach,nName,"");
            gUserProfile.safetyPutFieldEntry(nRPSpeach,nAvatar,nNone);

            gUserProfile.safetyCreateFieldEntry(nNameTag);
            gUserProfile.safetyPutFieldEntry(nNameTag,nOn,false);
            gUserProfile.safetyPutFieldEntry(nNameTag,nNewName,"");
            gUserProfile.safetyPutFieldEntry(nNameTag,nSoftName,"");
            gUserProfile.safetyPutFieldEntry(nNameTag,nOldName,"");
            gUserProfile.safetyPutFieldEntry(nNameTag,nAvatar,"");

            gUserProfile.safetyCreateFieldEntry(nSuit);
            gUserProfile.safetyPutFieldEntry(nSuit,nOn,false);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitType,"");
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitMatterial,"");
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitPrefix,"toy");
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitFull,false);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitScore,0);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitTimeLocked,0L);
            gUserProfile.safetyPutFieldEntry(nSuit,nSuitTimer,0L);
            gUserProfile.safetyPutFieldEntry(nSuit, nSuitFreeTalkAvailable,0);
            gUserProfile.safetyPutFieldEntry(nSuit,keyCustomGoodTalk,new JSONArray());

            gUserProfile.safetyCreateFieldEntry(nVoiceChannelRestriction);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,optionVoiceMute,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
            gUserProfile.safetyPutFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,false);

            gUserProfile.safetyCreateFieldEntry(nTimeLock);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nOn,false);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockDuration,0L);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockMax,0L);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockMin,0L);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockStart,60000*15);
            gUserProfile.safetyPutFieldEntry(nTimeLock,nTimeLockTimestamp,0L);

            gUserProfile.safetyCreateFieldEntry(nTimeOut);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nOn,false);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nTimeOutMode,0);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nChannel,0L);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nTimeOut1Duration,0);jsonObject.put(nTimeOut1Timestamp,0L);
            gUserProfile.safetyPutFieldEntry(nTimeOut,nTimeoutModeTimer,jsonObject);
            jsonObject=new JSONObject();
            jsonObject.put(nTimeOut2Count,0);jsonObject.put(nTimeOut2Done,0);jsonObject.put(nTimeOut2Sentence,"");
            gUserProfile.safetyPutFieldEntry(nTimeOut,nTimeoutModeWritting,jsonObject);

            gUserProfile.safetyCreateFieldEntry(nPishock);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockUsername,"");
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockApikey,"");
            gUserProfile.safetyPutFieldEntry(nPishock,nEnabled,false);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockShock4CollarEnabled,true);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockShock4ChastityEnabled,true);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockSelected,-1);
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockShockers,new JSONArray());
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockCollarShocker,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nPishock,nPiShockChastityShocker,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockGameWinShocker,new JSONObject());
            gUserProfile.safetyPutFieldEntry(nPishock,nPishockGameLoseShocker,new JSONObject());
            jsonObject=new JSONObject();
            jsonObject.put(valueWhite,new JSONArray());jsonObject.put(valueBlack,new JSONArray());
            gUserProfile.safetyPutFieldEntry(nPishock,nMembers,jsonObject);
            gUserProfile.safetyPutFieldEntry(nPishock,fieldMode,0);

            gUserProfile.safetyCreateFieldEntry(nEncase);
            gUserProfile.safetyPutFieldEntry(nEncase,nOn,false);
            gUserProfile.safetyPutFieldEntry(nEncase,nLevel,"");
            gUserProfile.safetyCreateFieldEntry(nConfine);
            gUserProfile.safetyPutFieldEntry(nConfine,nOn,false);
            gUserProfile.safetyPutFieldEntry(nConfine,nLevel,"");
            gUserProfile.safetyPutFieldEntry(nConfine,nID,"");

            gUserProfile.safetyCreateFieldEntry(nBreathplay);
            gUserProfile.safetyPutFieldEntry(nBreathplay,nOn,false);
            gUserProfile.safetyPutFieldEntry(nBreathplay,nLevel,"");
            gUserProfile.safetyPutFieldEntry(nBreathplay,nCount,10);
            gUserProfile.safetyPutFieldEntry(nBreathplay, nRemaining,0);

            /*gUserProfile.safetyCreateFieldEntry(iLovense.keyLovense);
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyuid,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyappVersion,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyappType,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keywssPort,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keywsPort,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyhttpPort,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyhttpsPort,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keydomain,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyversion,"");
            gUserProfile.safetyPutFieldEntry(iLovense.keyLovense,iLovense.keyutoken,"");*/
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }
    String nNameTag="nametag",nNewName="newname",nOldName="oldname",nSoftName="softname";
    String nRPSpeach="rpSpeach",nName="name",nAvatar="avatar";
    String nSuit="suit", nSuitType="type",nSuitMatterial="matterial", nSuitFull="full",nSuitPrefix="toy";

    String vRelease="release", vToy="toy",vAlpha="alpha",vBeta="beta",vOmega="omega",vStatus="status";
    String nSuitScore="score",nSuitTimeLocked="timelocked",nSuitTimer="timer", nSuitFreeTalkAvailable ="freetalkavailable";
    String nShockeEnabled="shockenabled",nBadWords="badwords",nEnforcedWords="enforcedwords",nAutoShockException4GuildGagException="autoException4GuildGag",nAutoShockException4UserGagException="autoException4UserGag";

    default lcJSONUserProfile iRunAway(lcJSONUserProfile gUserProfile, lcJSONGuildProfile gProfileRestrains){
        String fName="iRunAway";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            gUserProfile.putFieldEntry(nAccess, ACCESSLEVELS.Ask.getName());
            gUserProfile.safetyPutFieldEntry(nGender,vGenderNeutral);
            try {
                if(gProfileRestrains!=null&&gProfileRestrains.isExistent()&&gProfileRestrains.jsonObject !=null){
                    if(gProfileRestrains.jsonObject.has(fieldDefaultAccessMode)&&!gProfileRestrains.jsonObject.isNull(fieldDefaultAccessMode)){
                        String tmp=gProfileRestrains.jsonObject.getString(fieldDefaultAccessMode);
                        logger.info(fName + ".DefaultAccessMode=" + tmp);
                        if(!tmp.isBlank()&&!tmp.isBlank()){
                            gUserProfile.putFieldEntry(nAccess,tmp);
                        }else{
                            logger.warn(fName + ".is blank");
                        }
                    }else{
                        logger.warn(fName + ".has none");
                    }
                }else{
                    logger.warn(fName + ".not loaded");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            gUserProfile.putFieldEntry(nNotification,true);
            gUserProfile.putFieldEntry(nLocked, false);gUserProfile.putFieldEntry(nLockType, "");gUserProfile.putFieldEntry(nLockedBy, "");
            gUserProfile.putFieldEntry(nPermalocked, false);
            gUserProfile.safetyCreateFieldEntry(nOwner);
            gUserProfile.putFieldEntry(nOwner,nOwnerId,""); gUserProfile.putFieldEntry(nOwner,nOwnerAccepted,false);gUserProfile.putFieldEntry(nOwner,vRedirectAsk2OwnerAsWell,false);gUserProfile.putFieldEntry(nOwner,nSecOnwers,new JSONObject());

            gUserProfile.putFieldEntry(nStraitjacket,nOn,false);
            gUserProfile.putFieldEntry(nStraitjacket,nStrapArms,false);
            gUserProfile.putFieldEntry(nStraitjacket,nStrapCrotch,false);
            gUserProfile.putFieldEntry(nStraitjacket,nStrapArmsLevel,nNone);
            gUserProfile.putFieldEntry(nStraitjacket,nStrapCrotchLevel,nNone);
            gUserProfile.putFieldEntry(nStraitjacket, nImageUrlOn,"");gUserProfile.putFieldEntry(nStraitjacket, nImageUrlOff,"");gUserProfile.putFieldEntry(nStraitjacket, nImageUrlSecure,"");gUserProfile.putFieldEntry(nStraitjacket, nImageUrlUnSecure,"");gUserProfile.putFieldEntry(nStraitjacket, nImageUrlStruggle,"");

            gUserProfile.putFieldEntry(nCollar,nOn,false);
            gUserProfile.putFieldEntry(nCollar,nLevel,nNone);
            gUserProfile.putFieldEntry(nCollar,nShockeEnabled,false);
            gUserProfile.putFieldEntry(nCollar,nBadWords,new JSONArray());
            gUserProfile.safetyPutFieldEntry(nCollar,nEnforcedWords,new JSONArray());

            gUserProfile.putFieldEntry(nBelt,nOn,false);gUserProfile.putFieldEntry(nBelt,nTightness,0);

            gUserProfile.putFieldEntry(nArmsCuffs,nOn,false);
            gUserProfile.putFieldEntry(nArmsCuffs,nWrist,false);
            gUserProfile.putFieldEntry(nArmsCuffs,nElbows,false);
            gUserProfile.putFieldEntry(nArmsCuffs,nLevel,nNone);
            gUserProfile.putFieldEntry(nArmsCuffs,nPostRestrictEnabled, false);
            gUserProfile.putFieldEntry(nArmsCuffs,nPostDelay, 0);
            gUserProfile.putFieldEntry(nArmsCuffs,nLastPostTimeStamp, 0);
            gUserProfile.putFieldEntry(nArmsCuffs,nPostRestrictionException2UseGuildGag, true);
            gUserProfile.putFieldEntry(nArmsCuffs,nPostRestrictionException2UseUserGag, true);
            gUserProfile.putFieldEntry(nArmsCuffs, nImageUrlSecure,"");gUserProfile.putFieldEntry(nArmsCuffs, nImageUrlUnSecure,"");gUserProfile.putFieldEntry(nArmsCuffs, nImageUrlStruggle,"");

            gUserProfile.putFieldEntry(nLegsCuffs,nOn,false);
            gUserProfile.putFieldEntry(nLegsCuffs,nAnkles,false);
            gUserProfile.putFieldEntry(nLegsCuffs,nKnees,false);
            gUserProfile.putFieldEntry(nLegsCuffs,nLevel,nNone);
            gUserProfile.putFieldEntry(nLegsCuffs,nChannelRestrainEnabled,false);
            gUserProfile.putFieldEntry(nLegsCuffs,nChannelRestrainId,0);
            gUserProfile.putFieldEntry(nLegsCuffs, nChannelRestrainException2UseUserGag,true);
            gUserProfile.putFieldEntry(nLegsCuffs, nChannelRestrainException2UseGuildGag,true);
            gUserProfile.putFieldEntry(nLegsCuffs, nImageUrlOn,"");gUserProfile.putFieldEntry(nLegsCuffs, nImageUrlUnSecure,"");gUserProfile.putFieldEntry(nLegsCuffs, nImageUrlStruggle,"");

            gUserProfile.putFieldEntry(nMitts,nOn,false);gUserProfile.putFieldEntry(nMitts,nLevel,nNone);

            gUserProfile.putFieldEntry(nGag,nOn,false);
            gUserProfile.putFieldEntry(nGag,nLevel,nNone);
            gUserProfile.putFieldEntry(nGag,nCustomGagText,new JSONObject());
            gUserProfile.putFieldEntry(nGag,nType,GAGTYPES.Ball.getName());
            gUserProfile.putFieldEntry(nGag,nException,false);
            gUserProfile.putFieldEntry(nGag,nExceptionList,new JSONObject());
            gUserProfile.putFieldEntry(nGag,flagDisableGagOOCException,false);
            gUserProfile.putFieldEntry(nGag,flagDisableGagSafeWords,false);
            gUserProfile.putFieldEntry(nGag, nImageUrlSecure,"");gUserProfile.putFieldEntry(nGag, nImageUrlUnSecure,"");gUserProfile.putFieldEntry(nGag, nImageUrlStruggle,"");
            gUserProfile.putFieldEntry(nGag,nTimeLockStart,false);
            gUserProfile.putFieldEntry(nGag,nTimeLockTimestamp,0L);
            gUserProfile.putFieldEntry(nGag,nTimeLockDuration,milliseconds_minute*15);
            gUserProfile.putFieldEntry(nGag, nDisable2Show4Wearer,false);
            gUserProfile.putFieldEntry(nGag, nDisable2Show4Others,false);

            gUserProfile.safetyCreateFieldEntry(nLeash);
            gUserProfile.putFieldEntry(nLeash,nOn,false);
            //gUserProfile.putFieldEntry(nLeash,nTightness,0);
            gUserProfile.putFieldEntry(nLeash,nLeashLength,3);
            gUserProfile.putFieldEntry(nLeash,nLeash2Member,0);
            gUserProfile.putFieldEntry(nLeash,nLeash2Obj,"");
            gUserProfile.safetyCreateFieldEntry(nBlindfold);
            gUserProfile.putFieldEntry(nBlindfold,nOn,false);
            //gUserProfile.putFieldEntry(nBlindfold,nTightness,0);
            gUserProfile.putFieldEntry(nBlindfold,nLevel,nNone);
            gUserProfile.safetyCreateFieldEntry(nChastity);
            gUserProfile.putFieldEntry(nChastity,nOn,false);
            gUserProfile.putFieldEntry(nChastity,nLevel,nNone);
            gUserProfile.putFieldEntry(nChastity,nShock,false);
            gUserProfile.safetyCreateFieldEntry(nHood);
            gUserProfile.putFieldEntry(nHood,nOn,false);
            gUserProfile.putFieldEntry(nHood,nLevel,nNone);
            gUserProfile.safetyCreateFieldEntry(nEarMuffs);
            gUserProfile.putFieldEntry(nEarMuffs,nOn,false);
            gUserProfile.putFieldEntry(nEarMuffs,nLevel,nNone);

            gUserProfile.safetyCreateFieldEntry(nChannelRestrictions);
            //gUserProfile.putFieldEntry(nBlindfold,nLevel,new JSONObject());

            gUserProfile.safetyCreateFieldEntry(nRPSpeach);
            gUserProfile.putFieldEntry(nRPSpeach,nOn,false);
            gUserProfile.putFieldEntry(nRPSpeach,nName,0);
            gUserProfile.putFieldEntry(nRPSpeach,nAvatar,nNone);

            gUserProfile.safetyCreateFieldEntry(nNameTag);
            gUserProfile.putFieldEntry(nNameTag,nOn,false);
            gUserProfile.putFieldEntry(nNameTag,nNewName,"");
            gUserProfile.putFieldEntry(nNameTag,nSoftName,"");
            gUserProfile.putFieldEntry(nNameTag,nOldName,"");
            gUserProfile.putFieldEntry(nNameTag,nAvatar,"");

            gUserProfile.safetyCreateFieldEntry(nSuit);
            gUserProfile.putFieldEntry(nSuit,nOn,false);
            gUserProfile.putFieldEntry(nSuit,nSuitType,"");
            gUserProfile.putFieldEntry(nSuit,nSuitMatterial,"");
            gUserProfile.putFieldEntry(nSuit,nSuitFull,false);
            gUserProfile.putFieldEntry(nSuit,nSuitScore,0);
            gUserProfile.putFieldEntry(nSuit,nSuitTimeLocked,0);
            gUserProfile.putFieldEntry(nSuit,nSuitTimer,0);
            gUserProfile.putFieldEntry(nSuit, nSuitFreeTalkAvailable,0);

            gUserProfile.safetyCreateFieldEntry(nVoiceChannelRestriction);
            gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceMute,false);
            gUserProfile.putFieldEntry(nVoiceChannelRestriction,optionVoiceDeafen,false);
            gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
            gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteInUse,false);
            gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenEnabled,false);
            gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_ListenInUse,false);

            gUserProfile.safetyCreateFieldEntry(nTimeLock);
            gUserProfile.putFieldEntry(nTimeLock,nOn,false);
            gUserProfile.putFieldEntry(nTimeLock,nTimeLockDuration,0);
            gUserProfile.putFieldEntry(nTimeLock,nTimeLockMax,0);
            gUserProfile.putFieldEntry(nTimeLock,nTimeLockMin,0);
            gUserProfile.putFieldEntry(nTimeLock,nTimeLockStart,60000*15);
            gUserProfile.putFieldEntry(nTimeLock,nTimeLockTimestamp,0);

            gUserProfile.safetyCreateFieldEntry(nTimeOut);gUserProfile.putFieldEntry(nTimeOut,nOn,false);gUserProfile.putFieldEntry(nTimeOut,nTimeOutMode,0);gUserProfile.putFieldEntry(nTimeOut,nChannel,0);

            gUserProfile.putFieldEntry(nPishock,nEnabled,false);gUserProfile.putFieldEntry(nPishock,nPishockShock4CollarEnabled,false);gUserProfile.putFieldEntry(nPishock,nPishockShock4ChastityEnabled,false);

            gUserProfile.putFieldEntry(nEncase,nOn,false);gUserProfile.putFieldEntry(nEncase,nLevel,"");
            gUserProfile.putFieldEntry(nConfine,nOn,false);gUserProfile.putFieldEntry(nConfine,nLevel,"");gUserProfile.putFieldEntry(nConfine,nID,"0");
            gUserProfile.putFieldEntry(nBreathplay,nOn,false);gUserProfile.putFieldEntry(nBreathplay,nLevel,"");
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }

    String levelHoodReindeer="reindeer",levelMittsReindeer="hoof";
    static lcJSONUserProfile sDoneReindeer(lcJSONUserProfile gUserProfile,String type){
        String fName="[sDoneReindeer]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            gUserProfile.putFieldEntry(nSuit,nOn, true);gUserProfile.putFieldEntry(nSuit,nSuitMatterial, type); gUserProfile.putFieldEntry(nSuit,nSuitType, SUITTYPE.Reindeer.getName());gUserProfile.putFieldEntry(nSuit,nSuitFull, true);
            gUserProfile.putFieldEntry(nChastity,nOn,true);gUserProfile.putFieldEntry(nChastity,nLevel, CHASTITYLEVELS.Null.getName());gUserProfile.putFieldEntry(nChastity,nShock,false);
            gUserProfile.putFieldEntry(nGag,nOn,true);gUserProfile.putFieldEntry(nGag,nType,GAGTYPES.ReindeerMuzzle.getName());
            gUserProfile.putFieldEntry(nGag,nLevel, GAGLEVELS.DroneReindeer.getName());
            gUserProfile.putFieldEntry(nHood,nOn,true);
            gUserProfile.putFieldEntry(nHood,nLevel,levelHoodReindeer);
            gUserProfile.putFieldEntry(nMitts,nOn,true);
            gUserProfile.putFieldEntry(nMitts,nLevel,levelMittsReindeer);
            gUserProfile.putFieldEntry(nCollar,nOn,true);
            gUserProfile.putFieldEntry(nCollar,nLevel, COLLARLEVELS.Rubber.getName());
            gUserProfile.putFieldEntry(nAccess,ACCESSLEVELS.Public.getName());
            gUserProfile.putFieldEntry(nArmsCuffs,nOn,true);
            gUserProfile.putFieldEntry(nArmsCuffs,nLevel, CUFFSARMSLEVELS.Armbinder.getName());
            gUserProfile.putFieldEntry(nStraitjacket,nOn,false);
            gUserProfile.putFieldEntry(nStraitjacket,nStrapArms,false);
            gUserProfile.putFieldEntry(nStraitjacket,nStrapCrotch,false);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nOn,false);
            gUserProfile.safetyPutFieldEntry(nLegsCuffs,nLevel,nNone);
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }

    String fieldEnabled ="enabled",fieldGuild="guild";
    String fieldEnabled2 ="enabled2b";
    String fieldMode ="mode";String valueWhite ="white";String valueBlack ="black", fieldMode4WhiteBlack="mode4whiteblack";
    String fieldBannedChannels ="banedChannels"; String fieldAllowedChannels ="allowedChannels";
    String fieldBannedUsers ="banedUsers",fieldAllowedUsers="allowedUsers";
    String fieldBannedRoles ="banedRoles",fieldAllowedRoles="allowedRoles";
    String fieldAlert ="alert";String valueChannel ="channel";String valueDM ="dm";
    String fieldBDSMRestrictions="BDSMRestrictions";
    String fieldGagCommandAllowedRoles ="GagCommandAllowedRoles";
    String fieldGagTargetAllowedRoles ="GagCommandTargetRoles";
    String fieldCommandAllowedRoles ="commandAllowedRoles";
    String fieldTargetAllowedRoles ="commandTargetRoles";
    String fieldDefaultAccessMode ="defaultAccess";
    String fieldDomRoles ="domRoles",fieldSubRoles="subRoles";




    default Boolean isPetOwned(lcJSONUserProfile gUserProfile){
        return isSPetOwned(gUserProfile);
    }
    static Boolean isSPetOwned(lcJSONUserProfile gUserProfile){
        String fName="[isSPetOwned]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            boolean result=!(gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nOwnerId) || gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId).isBlank() || !gUserProfile.jsonObject.getJSONObject(nOwner).getBoolean(nOwnerAccepted));
            logger.info(fName + ".result="+result);
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }





    String strWaitingPeriodImeout="Waiting period for !TARGET for response has timed outed.",
    strAskRejectedByTarget1="!TARGET rejected your command.",
    strAskRejectedByTarget2="You rejected !USER's command.",
    strAskRejectedByTarget3="!TARGET rejected !USER's command.",
    strAskRejectedByOwner1="!TARGET's owner rejected your command.",
    strAskRejectedByOwner2="Your owner rejected !USER's command.",
    strAskRejectedByOwner3="You rejected !USER's command for !TARGET.",
    strAskApprovedByTarget3="!TARGET approved !USER's command.",
    strAskApprovedByOwner1="!TARGET's owner approved your command.",
    strAskApprovedByOwner3="You approved !USER's command for !TARGET.";

    int intDefaultWaitingMinute=5;

    default Boolean hasUserOwnerAccess(lcJSONUserProfile gUserProfile, User user){
        String fName="[hasUserOwnerAccess]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nOwnerId)||gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId).isBlank()||!gUserProfile.jsonObject.getJSONObject(nOwner).getBoolean(nOwnerAccepted)){
                return false;
            }
            return gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId).equalsIgnoreCase(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default Boolean hasUserSecOwnerAccess(lcJSONUserProfile gUserProfile, User user){
        String fName="[hasUserSecOwnerAccess]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nSecOnwers)||gUserProfile.jsonObject.getJSONObject(nOwner).getJSONObject(nSecOnwers).length()==0){
                return false;
            }
            return gUserProfile.jsonObject.getJSONObject(nOwner).getJSONObject(nSecOnwers).has(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static Boolean lsHasUserOwnerAccess(lcJSONUserProfile gUserProfile, User user){
        String fName="[lsHasUserOwnerAccess]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nOwnerId)||gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId).isBlank()||!gUserProfile.jsonObject.getJSONObject(nOwner).getBoolean(nOwnerAccepted)){
                return false;
            }
            return gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId).equalsIgnoreCase(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static Boolean lsHasUserSecOwnerAccess(lcJSONUserProfile gUserProfile, User user){
        String fName="[lsHasUserSecOwnerAccess]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nSecOnwers)||gUserProfile.jsonObject.getJSONObject(nOwner).getJSONObject(nSecOnwers).length()==0){
                return false;
            }
            return gUserProfile.jsonObject.getJSONObject(nOwner).getJSONObject(nSecOnwers).has(user.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static Boolean lsHasUserAnyOwnerAccess(lcJSONUserProfile gUserProfile, User user){
        String fName="[lsHasUserAnyOwnerAccess]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return lsHasUserSecOwnerAccess(gUserProfile, user) || lsHasUserOwnerAccess(gUserProfile, user);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    String  isRequestion2TakeControl=" is requesting to take control of ", isRequestion2Update=" is requesting to update your ", rejectedyourrequestof =" rejected your request of ",rejectedyourrequestofUpdate =" rejected your request of updating their ",yourejectedtheirrequest="You rejected their request.",waitingresponsehastimeuted=" waiting response has timeuted!";
    String accessisaskwait1=" access is set to ask. So you need to wait their confirmation or reject.\nNote:This is a new approach of preventing abuse, instead of the default access exposed, it will be set to ask.", accessaskaskingpet1=".\nDo you allow it? :green_book: for yes or :apple: for no.\nNote:This is a new approach of preventing abuse, instead of the default access exposed, it will be set to ask.\nIf you don't want to see this message again, set your access `!>auth` Can auto allow it or block it (except for owners&sec-owners)";

    static Boolean isSAccessAsk(lcJSONUserProfile gUserProfile){
        String fName="[isSAccessAsk]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return gUserProfile.jsonObject.has(nAccess) &&(gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Ask.getName())||gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName()));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static Boolean sisPermalocked(lcJSONUserProfile gUserProfile){
        String fName="[sisPermalocked]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return gUserProfile.jsonObject.has(nPermalocked) && gUserProfile.jsonObject.getBoolean(nPermalocked);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    default Boolean hasPetGotAccess2Restrain(lcJSONUserProfile gUserProfile){
        String fName="[hasPetGotAccess2Restrain]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            if(!isPetOwned(gUserProfile)){return true;}
            return !gUserProfile.jsonObject.getString(nAccess).equals(ACCESSLEVELS.Pet.getName());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    default Boolean isAccess2Public(lcJSONUserProfile gUserProfile){
        String fName="[isAccess2Public]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            return gUserProfile.jsonObject.getString(nAccess).equals(ACCESSLEVELS.Public.getName());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


    static String sgetUserWhoLockedPet(lcJSONUserProfile gUserProfile){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="getUserWhoLockedPet";
        try {
            if(!gUserProfile.jsonObject.isNull(nLockedBy)&&!gUserProfile.jsonObject.getString(nLockedBy).isEmpty()&&!gUserProfile.jsonObject.getString(nLockedBy).isBlank()){
                String id=gUserProfile.jsonObject.getString(nLockedBy);
                String mention=lsMemberHelper.lsGetMemberMention(gUserProfile.getGuild(),id);
                if(mention==null||mention.isEmpty()||mention.isBlank()){
                    return "<"+id+">";
                }
                return mention;
            }
            return "n/a";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "error";
        }
    }


    String nPermissionText_Read="read", nPermissionText_Write="write", nPermissionText_History="history",nPermissionText_ExternalEmojis="external", nPermissionText_AddReactions="reactions", nPermissionText_File="file";
    default Permission getPermission(String name){
        String fName="[getPermission]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            Permission result=null;
            if(name.equalsIgnoreCase(nPermissionText_AddReactions)||name.equalsIgnoreCase(Permission.MESSAGE_ADD_REACTION.getName())){result=Permission.MESSAGE_ADD_REACTION;}
            if(name.equalsIgnoreCase(nPermissionText_File)||name.equalsIgnoreCase(Permission.MESSAGE_ATTACH_FILES.getName())){result=Permission.MESSAGE_ATTACH_FILES;}
            if(name.equalsIgnoreCase(nPermissionText_History)||name.equalsIgnoreCase(Permission.MESSAGE_HISTORY.getName())){result=Permission.MESSAGE_HISTORY;}
            if(name.equalsIgnoreCase(nPermissionText_Write)||name.equalsIgnoreCase(Permission.MESSAGE_WRITE.getName())){result=Permission.MESSAGE_WRITE;}
            if(name.equalsIgnoreCase(nPermissionText_Read)||name.equalsIgnoreCase(Permission.MESSAGE_READ.getName())){result=Permission.MESSAGE_READ;}
            if(name.equalsIgnoreCase(nPermissionText_ExternalEmojis)||name.equalsIgnoreCase(Permission.MESSAGE_EXT_EMOJI.getName())){result=Permission.MESSAGE_EXT_EMOJI;}
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }



    String cmdToyAdd1="+1",cmdToyAdd5="+5",cmdToyAdd10="+10",cmdToyAdd25="+25";
    String cmdToyRem1="-1",cmdToyRem5="-5",cmdToyRem10="-10",cmdToyRem25="-25";

    default String toyMaskGag(String text,JSONArray array,boolean onlyCustom){
        String fName="[toyMaskGag]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            List<String> list=new ArrayList<>();
            if(text.contains("!")){
                List<String>tmpList=listOfGoodToy();
                for (String s : tmpList) {
                    if (s.contains("!")) {
                        list.add(s);
                    }
                }
            }else
            if(text.contains("?")){
                List<String>tmpList=listOfGoodToy();
                for (String s : tmpList) {
                    if (s.contains("?")) {
                        list.add(s);
                    }
                }
            }else{
                List<String>tmpList=listOfGoodToy();
                for (String s : tmpList) {
                    if (!s.contains("!") && !s.contains("?")) {
                        list.add(s);
                    }
                }
            }
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(upper){
                return list.get(i);
            }else{
                return list.get(i).toLowerCase();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default String toyReindeerMaskGag(String text,JSONArray array,boolean onlyCustom){
        String fName="[toyReindeerMaskGag]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            List<String> list=new ArrayList<>();
            list.add("Helping santa is my duty.");
            list.add("Santa asked volunteers for help pulling his sleigh..So i volunteered.");
            list.add("Helping santa pull his sleigh.");
            list.add("I will bring joy to everyone.");
            list.add("My own joy is meaningless. My duty is to bring joy to others.");
            list.add("Merry x-mas.");
            list.add("Were you good or naughty this year?");
            list.add("I was naughty this year. As such Santa is ensuring i will be good reindeer drone.");
            list.add("Im a reliable reindeer");
            if(!array.isEmpty()){
                if(onlyCustom){
                    list.clear();
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                    if(list.isEmpty()){
                        for(int i=0;i<array.length();i++){
                            list.add(array.getString(i));
                        }
                    }
                }else{
                    if(text.contains("!")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("!"))list.add(array.getString(i));
                        }
                    }else
                    if(text.contains("?")){
                        for(int i=0;i<array.length();i++){
                            if(array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }else{
                        for(int i=0;i<array.length();i++){
                            if(!array.getString(i).contains("!")&&!array.getString(i).contains("?"))list.add(array.getString(i));
                        }
                    }
                }
            }
            int i= lsUsefullFunctions.getRandom(list.size());
            boolean upper= lsUsefullFunctions.isFirstCharInStringUpper(text);
            if(upper){
                return list.get(i);
            }else{
                return list.get(i).toLowerCase();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    //https://stackoverflow.com/questions/1240504/regular-expression-to-match-string-starting-with-a-specific-word
    default String trainingGag(String text,JSONArray array){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="trainingGag";
        try {
            logger.info(fName + ".text.before=" +text);
            if(array.isEmpty()){ logger.info(fName + ".array isempty");return text;}
            logger.info(fName + ".array.length="+array.length());
            for(int i=0;i<array.length();i++){
                JSONObject entry=array.getJSONObject(i);
                logger.info(fName + ".entry="+entry.toString());
                String from=entry.getString(nFrom);
                String to=entry.getString(nTo);
                logger.info(fName + ".from="+from);
                logger.info(fName + ".to="+to);
                text=text.replaceAll(" "+from+"?"," "+to);
                text=text.replaceAll(" "+from.toUpperCase()+"?"," "+to.toUpperCase());
                text=text.replaceAll(" "+from.toLowerCase()+"?"," "+to.toLowerCase());
                /*text=text.replaceAll(" "+from+" "," "+to+" ");
                text=text.replaceAll(" "+from.toUpperCase()+" "," "+to.toUpperCase()+" ");
                text=text.replaceAll(" "+from.toLowerCase()+" "," "+to.toLowerCase()+" ");
                text=text.replaceAll(" "+from+"."," "+to+".");
                text=text.replaceAll(" "+from.toUpperCase()+"."," "+to.toUpperCase()+".");
                text=text.replaceAll(" "+from.toLowerCase()+"."," "+to.toLowerCase()+".");*/
                /*text=text.replaceAll(" "+from+"\?"," "+to+"?");
                text=text.replaceAll(" "+from.toUpperCase()+"\?"," "+to.toUpperCase()+"?");
                text=text.replaceAll(" "+from.toLowerCase()+"\?"," "+to.toLowerCase()+"?");*/
                /*text=text.replaceAll(" "+from+"!"," "+to+"!");
                text=text.replaceAll(" "+from.toUpperCase()+"!"," "+to.toUpperCase()+"!");
                text=text.replaceAll(" "+from.toLowerCase()+"!"," "+to.toLowerCase()+"!");*/
            }
            logger.info(fName + ".text.after=" +text);
            return  text;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  text;
        }
    }

    default String getToyRank(lcJSONUserProfile gUserProfile){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="getToyRank";
        try {
            String type=gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType);
            logger.info(fName + ".type=" + type);
            String text="";
            if(type.equalsIgnoreCase(SUITTYPE.ToyAlpha.getName()))text="alpha";
            if(type.equalsIgnoreCase(SUITTYPE.ToyBeta.getName()))text="beta";
            if(type.equalsIgnoreCase(SUITTYPE.ToyOmega.getName()))text="omega";
            logger.info(fName + ".text=" + text);
            return  text;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    String keyCustomGoodTalk="customgoodtalk";
    default List<String> listOfGoodToy(){
        String fName="[listOfGoodToy]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            List<String> list=new ArrayList<>();
            list.add("Toy loves being a toy");
            list.add("please fuck toy");
            list.add("Toy does not need to cum");
            list.add("Keep toy forever");
            list.add("Toy wants to be a toy forever!");
            list.add("Toy loves you");
            list.add("Please punish toy.");
            list.add("Gags are good for toys like toy.");
            list.add("Toy loves rubbing its bulge!");
            list.add("Toy wants to play with all the other toys!");
            list.add("Toy wants a bulge to push its muzzle into!");
            list.add("Toy wants attention on its rubber paws!");
            list.add("Please do not refer to Toy as a person. Toy is merely a toy to be used!");
            list.add("Toy likes being here");
            list.add("Toy does not need release");
            list.add("Toy is always content");
            list.add("Toy loves you");
            list.add("Toy conversion in progress");
            list.add("Toy wants your cock");
            list.add("Toy is a good toy.");
            list.add("Please use toy.");
            list.add("Toy needs cock inside it.");
            list.add("Toy never wants out.");
            list.add("Toy loves being a toy.");
            list.add("Please fuck toy");
            list.add("Master is the world to toy");
            list.add("Toy doesn't need to understand to serve");
            list.add("Toy needs its voice privileges removed.");
            list.add("Toy is happy");
            list.add("Toy wants its tail to be lifted and its rear to be claimed!");
            list.add("Toy never wants to be let out of the Toysuit");
            list.add("Toy loves to be milked.");
            list.add("Toy wants to be milked more.");
            list.add("Toy wants to go on.");
            list.add("Toy is a good toy!");
            list.add("Toy will obey!");
            list.add("Toy will do anything you ask for!");
            list.add("Toy is happy in its toysuit!");
            list.add("Yes! Toy is a good toy!");
            list.add("Be a toy forever.");
            list.add("Can this toy be of assistance?");list.add("How may this toy serve you, sir?");list.add("Can this toy be of any service?");
            list.add("This toy is a good slave.");list.add("This toy owner is kind and merciful.");list.add("This toy doesn't want to be free.");list.add("This toy will is to serve their owner");
            list.add("This toy wants to serve their owner");list.add("Please break the this toy keys so i can serve permanently.");list.add("This toy belong to their owner!");


            list.add("Toy loves being a toy");
            list.add("Toy loves being a toy!");
            list.add("Toy loves being a toy.");
            list.add("Toy is happy as a toy");
            list.add("Toy is happy as a toy!");
            list.add("Toy is happy as a toy.");
            list.add("please fuck toy");
            list.add("please fuck toy!");
            list.add("please fuck toy.");
            list.add("toy does not need to cum");
            list.add("toy does not need to cum!");
            list.add("toy does not need to cum.");
            list.add("please do not let toy cum");
            list.add("please do not let toy cum!");
            list.add("please do not let toy cum.");
            list.add("please don't let toy cum");
            list.add("please don't let toy cum!");
            list.add("please don't let toy cum.");
            list.add("Keep toy forever");
            list.add("Keep toy forever!");
            list.add("Keep toy forever.");
            list.add("toy wants to be a toy forever");
            list.add("toy wants to be a toy forever!");
            list.add("toy wants to be a toy forever.");
            list.add("Toy loves you");
            list.add("Toy loves you!");
            list.add("Toy loves you.");
            list.add("Please punish toy");
            list.add("Please punish toy.");
            list.add("Please punish toy!");
            list.add("toy would like to be punished");
            list.add("toy would like to be punished!");
            list.add("toy would like to be punished.");
            list.add("toy wants punishment");
            list.add("toy wants punishment!");
            list.add("toy wants punishment.");
            list.add("gags are good for toys like toy");
            list.add("Gags are good for toys like toy!");
            list.add("Gags are good for toys like toy.");
            list.add("good toys need gags");
            list.add("good toys need gags.");
            list.add("good toys need gags!");
            list.add("keep toy gagged");
            list.add("keep toy gagged.");
            list.add("keep toy gagged!");
            list.add("keep toy gagged forever");
            list.add("keep toy gagged forever.");
            list.add("keep toy gagged forever!");
            list.add("Toy loves rubbing its bulge");
            list.add("Toy loves rubbing its bulge!");
            list.add("Toy loves rubbing its bulge.");
            list.add("toy loves its bulge");
            list.add("toy loves its bulge!");
            list.add("toy loves its bulge.");
            list.add("toy loves its new bulge");
            list.add("toy loves its new bulge.");
            list.add("toy loves its new bulge!");
            list.add("please squeeze toys bulge");
            list.add("please squeeze toys bulge!");
            list.add("please squeeze toys bulge.");
            list.add("please squeeze toy's bulge");
            list.add("please squeeze toy's bulge.");
            list.add("please squeeze toy's bulge!");
            list.add("toy loves playing with the other toys");
            list.add("toy loves playing with the other toys.");
            list.add("toy loves playing with the other toys!");
            list.add("toy loves playing with all the other toys");
            list.add("toy loves playing with all the other toys.");
            list.add("toy loves playing with all the other toys!");
            list.add("toy wants to play with the other toys");
            list.add("toy wants to play with the other toys!");
            list.add("Toy wants to play with all the other toys");
            list.add("Toy wants to play with all the other toys!");
            list.add("Toy wants a bulge to push its muzzle into");
            list.add("Toy wants a bulge to push its muzzle into!");
            list.add("Toy would like a bulge to push its muzzle into");
            list.add("Toy would like a bulge to push its muzzle into!");
            list.add("Toy wants attention on its rubber paws");
            list.add("Toy wants attention on its rubber paws!");
            list.add("Toy wants attention on its rubber paws.");
            list.add("toy is not a person, toy is a toy");
            list.add("toy is not a person, toy is a toy!");
            list.add("toy is not a person, toy is a toy.");
            list.add("Please do not refer to Toy as a person. Toy is merely a toy to be used");
            list.add("Please do not refer to Toy as a person. Toy is merely a toy to be used!");
            list.add("Please do not refer to Toy as a person. Toy is merely a toy to be used.");
            list.add("Please do not refer to Toy as a person! Toy is merely a toy to be used");
            list.add("Please do not refer to Toy as a person! Toy is merely a toy to be used!");
            list.add("Please do not refer to Toy as a person! Toy is merely a toy to be used.");
            list.add("Please do not refer to Toy as a person, Toy is merely a toy to be used");
            list.add("Please do not refer to Toy as a person, Toy is merely a toy to be used!");
            list.add("Please do not refer to Toy as a person, Toy is merely a toy to be used.");
            list.add("Please do not refer to Toy as a person. Toy is just a toy to be used");
            list.add("Please do not refer to Toy as a person. Toy is just a toy to be used!");
            list.add("Please do not refer to Toy as a person. Toy is just a toy to be used.");
            list.add("Please do not refer to Toy as a person! Toy is just a toy to be used");
            list.add("Please do not refer to Toy as a person! Toy is just a toy to be used!");
            list.add("Please do not refer to Toy as a person! Toy is just a toy to be used.");
            list.add("Please do not refer to Toy as a person, Toy is just a toy to be used");
            list.add("Please do not refer to Toy as a person, Toy is just a toy to be used!");
            list.add("Please do not refer to Toy as a person, Toy is just a toy to be used.");
            list.add("Toy likes being here");
            list.add("Toy likes being here.");
            list.add("Toy likes being here!");
            list.add("Toy does not need release");
            list.add("Toy does not need release.");
            list.add("Toy does not need release!");
            list.add("toy does not want release");
            list.add("toy does not want release.");
            list.add("toy does not want release!");
            list.add("toy does not want to be freed");
            list.add("toy does not want to be freed.");
            list.add("toy does not want to be freed?");
            list.add("toy does not want freedom");
            list.add("toy does not want freedom!");
            list.add("toy does not want freedom.");
            list.add("toy does not understand, what is freedom?");
            list.add("toy does not understand. What is freedom?");
            list.add("toy doesn't understand. what is freedom?");
            list.add("toy doesn't understand, what is freedom?");
            list.add("what is freedom?");
            list.add("Toy is always content");
            list.add("Toy is always content.");
            list.add("Toy is always content!");
            list.add("toy is content");
            list.add("toy is content.");
            list.add("toy is content!");
            list.add("Toy loves you");
            list.add("Toy loves you!");
            list.add("Toy loves you.");
            list.add("Toy loves sir");
            list.add("Toy loves sir.");
            list.add("Toy loves sir!");
            list.add("Toy loves master");
            list.add("Toy loves master.");
            list.add("Toy loves master!");
            list.add("Toy loves its owner");
            list.add("Toy loves its owner.");
            list.add("Toy loves its owner!");
            list.add("Toy loves its master");
            list.add("Toy loves its master.");
            list.add("Toy loves its master!");
            list.add("Toy loves its sir");
            list.add("Toy loves its sir.");
            list.add("Toy loves its sir!");
            list.add("Toy conversion in progress");
            list.add("Toy conversion in progress.");
            list.add("Toy conversion in progress!");
            list.add("Toy wants your cock");
            list.add("Toy wants your cock.");
            list.add("Toy wants your cock!");
            list.add("Toy is a good toy");
            list.add("Toy is a good toy.");
            list.add("Toy is a good toy!");
            list.add("toy is good");
            list.add("toy is good!");
            list.add("toy is good.");
            list.add("toy will behave");
            list.add("toy will behave.");
            list.add("toy will behave!");
            list.add("is toy pleasing you");
            list.add("is toy pleasing you?");
            list.add("is toy pleasing sir");
            list.add("is toy pleasing sir?");
            list.add("is toy pleasing its owner");
            list.add("is toy pleasing its owner?");
            list.add("is toy pleasing its master");
            list.add("is toy pleasing its master?");
            list.add("is toy's owner happy with toy's performance");
            list.add("is toy's owner happy with toy's performance?");
            list.add("is toy's master happy with toy's performance");
            list.add("is toy's master happy with toy's performance?");
            list.add("is toy's sir happy with toy's performance");
            list.add("is toy's sir happy with toy's performance?");
            list.add("is sir happy with toy's performance");
            list.add("is sir happy with toy's performance?");
            list.add("are you happy with toy's performance");
            list.add("are you happy with toy's performance?");
            list.add("are you happy with toy's service");
            list.add("are you happy with toy's service?");
            list.add("is there any more that toy can do for you");
            list.add("is there any more that toy can do for you?");
            list.add("is there any more toy can do for you");
            list.add("is there any more toy can do for you?");
            list.add("is there more toy can do for you");
            list.add("is there more toy can do for you?");
            list.add("is there more that toy can do for you");
            list.add("is there more that toy can do for you?");
            list.add("has toy been good");
            list.add("has toy been good?");
            list.add("has toy been a good toy");
            list.add("has toy been a good toy?");
            list.add("can toy help");
            list.add("can toy help?");
            list.add("may toy help");
            list.add("may toy help?");
            list.add("how can toy help");
            list.add("how can toy help?");
            list.add("how may toy help");
            list.add("how may toy help?");
            list.add("how can toy be of assistance");
            list.add("how can toy be of assistance?");
            list.add("how may toy be of assistance");
            list.add("how may toy be of assistance?");
            list.add("Please use toy");
            list.add("Please use toy.");
            list.add("Please use toy!");
            list.add("Toy needs cock inside it");
            list.add("Toy needs cock inside it.");
            list.add("Toy needs cock inside it?");
            list.add("toy doesnt want out");
            list.add("toy doesnt want out.");
            list.add("toy doesnt want out!");
            list.add("dont ever let toy out");
            list.add("dont ever let toy out.");
            list.add("dont ever let toy out!");
            list.add("please dont release toy");
            list.add("please dont release toy.");
            list.add("please dont release toy!");
            list.add("please dont ever release toy");
            list.add("please dont ever release toy.");
            list.add("please dont ever release toy!");
            list.add("please dont let toy out");
            list.add("please dont let toy out.");
            list.add("please dont let toy out!");
            list.add("please dont let toy go");
            list.add("please dont let toy go.");
            list.add("please dont let toy go!");
            list.add("please dont release toy");
            list.add("please dont release toy.");
            list.add("please dont release toy!");
            list.add("Toy never wants out");
            list.add("Toy never wants out.");
            list.add("Toy never wants out!");
            list.add("Toy loves being a toy");
            list.add("Toy loves being a toy.");
            list.add("Toy loves being a toy!");
            list.add("Master is the world to toy");
            list.add("Master is the world to toy!");
            list.add("Master is the world to toy.");
            list.add("Master means the world to toy");
            list.add("Master means the world to toy.");
            list.add("Master means the world to toy!");
            list.add("Toy doesn't need to understand to serve");
            list.add("Toy doesn't need to understand to serve.");
            list.add("Toy doesn't need to understand to serve!");
            list.add("Toy needs its voice privileges removed");
            list.add("Toy needs its voice privileges removed.");
            list.add("Toy needs its voice privileges removed!");
            list.add("Toy is happy");
            list.add("Toy is happy.");
            list.add("Toy is happy!");
            list.add("toy is good");
            list.add("toy is good.");
            list.add("toy is good!");
            list.add("toy loves being a good toy");
            list.add("toy loves being a good toy.");
            list.add("toy loves being a good toy!");
            list.add("toy loves being good");
            list.add("toy loves being good.");
            list.add("toy loves being good!");
            list.add("toy loves being a toy");
            list.add("toy loves being a toy.");
            list.add("toy loves being a toy!");
            list.add("toy is enjoying this");
            list.add("toy is enjoying this.");
            list.add("toy is enjoying this!");
            list.add("toy loves this");
            list.add("toy loves this.");
            list.add("toy loves this!");
            list.add("toy loves this toy suit");
            list.add("toy loves this toy suit.");
            list.add("toy loves this toy suit!");
            list.add("toy loves the toy suit");
            list.add("toy loves the toy suit.");
            list.add("toy loves the toy suit!");
            list.add("toy loves its toy suit");
            list.add("toy loves its toy suit.");
            list.add("toy loves its toy suit!");
            list.add("Toy wants its tail to be lifted and its rear to be claimed");
            list.add("Toy wants its tail to be lifted and its rear to be claimed.");
            list.add("Toy wants its tail to be lifted and its rear to be claimed!");
            list.add("Toy never wants to be let out of the Toysuit");
            list.add("Toy never wants to be let out of the Toysuit.");
            list.add("Toy never wants to be let out of the Toysuit!");
            list.add("Toy never wants to be let out of the Toy suit");
            list.add("Toy never wants to be let out of the Toy suit!");
            list.add("Toy never wants to be let out of the Toy suit.");
            list.add("don't let toy out");
            list.add("don't let toy out.");
            list.add("don't let toy out!");
            list.add("toy doesn't want to be let out of the toysuit");
            list.add("toy doesn't want to be let out of the toysuit.");
            list.add("toy doesn't want to be let out of the toysuit!");
            list.add("toy doesn't want to be let out of the toy suit");
            list.add("toy doesn't want to be let out of the toy suit.");
            list.add("toy doesn't want to be let out of the toy suit!");
            list.add("Toy loves to be milked.");
            list.add("Toy loves to be milked");
            list.add("Toy loves to be milked!");
            list.add("Toy wants to be milked more.");
            list.add("Toy wants to be milked more");
            list.add("Toy wants to be milked more!");
            list.add("Toy wants to go on");
            list.add("Toy wants to go on.");
            list.add("Toy wants to go on!");
            list.add("Toy wants to continue");
            list.add("Toy wants to continue.");
            list.add("Toy wants to continue!");
            list.add("Toy is a good toy!");
            list.add("Toy is a good toy.");
            list.add("Toy is a good toy");
            list.add("Toy will obey!");
            list.add("Toy will obey");
            list.add("Toy will obey.");
            list.add("toy must obey");
            list.add("toy must obey.");
            list.add("toy must obey!");
            list.add("toy has no choice");
            list.add("toy has no choice!");
            list.add("toy has no choice.");
            list.add("toy is here to serve");
            list.add("toy is here to serve.");
            list.add("toy is here to serve!");
            list.add("toy lives to serve");
            list.add("toy lives to serve.");
            list.add("toy lives to serve!");
            list.add("toy will serve");
            list.add("toy will serve.");
            list.add("toy will serve!");
            list.add("toy must serve");
            list.add("toy must serve.");
            list.add("toy must serve!");
            list.add("Toy will do anything you ask for");
            list.add("Toy will do anything you ask for!");
            list.add("Toy will do anything you ask for.");
            list.add("toy will do anything you ask of it");
            list.add("toy will do anything you ask of it.");
            list.add("toy will do anything you ask of it!");
            list.add("toy will do anything you ask it to");
            list.add("toy will do anything you ask it to.");
            list.add("toy will do anything you ask it to!");
            list.add("toy is here to serve you sir");
            list.add("toy is here to serve you sir.");
            list.add("toy is here to serve you sir!");
            list.add("Toy is happy in its toysuit");
            list.add("Toy is happy in its toysuit!");
            list.add("Toy is happy in its toysuit.");
            list.add("Toy is happy in its toy suit.");
            list.add("Toy is happy in its toy suit");
            list.add("Toy is happy in its toy suit!");
            list.add("Toy is very happy in its toy suit");
            list.add("Toy is very happy in its toy suit.");
            list.add("Toy is very happy in its toy suit!");
            list.add("toy is very happy in its toysuit");
            list.add("toy is very happy in its toysuit.");
            list.add("toy is very happy in its toysuit!");
            list.add("Yes! Toy is a good toy");
            list.add("Yes! Toy is a good toy!");
            list.add("Yes! Toy is a good toy.");
            list.add("Be a toy forever");
            list.add("Be a toy forever.");
            list.add("Be a toy forever!");
            list.add("please allow toy to be a toy forever");
            list.add("please allow toy to be a toy forever!");
            list.add("please allow toy to be a toy forever.");
            list.add("please break the keys to the toysuit so toy can never be freed");
            list.add("please break the keys to the toysuit so toy can never be freed.");
            list.add("please break the keys to the toysuit so toy can never be freed!");
            list.add("please break the keys to the toysuit so toy can never be released");
            list.add("please break the keys to the toysuit so toy can never be released!");
            list.add("please break the keys to the toysuit so toy can never be released.");
            list.add("toy is awaiting commands");
            list.add("toy is awaiting commands.");
            list.add("toy is awaiting commands!");
            list.add("toy is awaiting further commands");
            list.add("toy is awaiting further commands.");
            list.add("toy is awaiting further commands!");
            list.add("please provide instructions for toy");
            list.add("please provide instructions for toy.");
            list.add("please provide instructions for toy!");
            list.add("play with toy");
            list.add("play with toy.");
            list.add("play with toy!");
            list.add("toy is fun to play with");
            list.add("toy is fun to play with.");
            list.add("toy is fun to play with!");
            list.add("toy hopes you are enjoying your toy");
            list.add("toy hopes you are enjoying your toy.");
            list.add("toy hopes you are enjoying your toy!");
            list.add("please seal the toysuit onto this toy");
            list.add("please seal the toysuit onto this toy.");
            list.add("please seal the toysuit onto this toy!");

            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    default List<String> listOfGoodToy(JSONArray array,boolean onlyCustom){
        String fName="[listOfGoodToy]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            List<String> list=new ArrayList<>();
            if(onlyCustom&&array!=null&&!array.isEmpty()){
                for(int i=0;i<array.length();i++){
                    list.add(array.getString(i));
                }
            }else{
                list=listOfGoodToy();
                if(array!=null){
                    for(int i=0;i<array.length();i++){
                        list.add(array.getString(i));
                    }
                }
            }

            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    default String getRandomToyTalkGood(JSONArray array,boolean onlyCustom){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="getRandomToyTalkGood";
        try {
            List<String>list=listOfGoodToy(array,onlyCustom);
            int i=lsUsefullFunctions.getRandom(list.size());
            logger.info(fName + ".i=" + i);
            logger.info(fName + ".text=" + list.get(i));
            return list.get(i);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    default boolean isToyTalkGood(String text,JSONArray array,boolean onlyCustom){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="isToyTalkGood";
        try {
            if(text.isBlank()){
                logger.warn(fName + ".text is blank");
                return false;
            }
            List<String>list=listOfGoodToy(array,onlyCustom);
            for (String s : list) {
                logger.info(fName + ".text=" + text);logger.info(fName + ".check.before=" + s);
                String org=text.toLowerCase();
                String check=s.toLowerCase();
                logger.info(fName + ".text.after=" + org);  logger.info(fName + ".check.after=" + check);
                if (org.contains(check)) {
                    logger.info(fName + ".contains!");
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default List<String> listOfBadToy(){
        String fName="[listOfBadToy]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            List<String> list=new ArrayList<>();
            list.add("let me out");
            list.add("please help me");
            list.add("I want to cum");
            list.add("toy did not say that");
            list.add("I want out");
            list.add("I hate you");
            list.add("Release me");
            list.add("Fuck you");
            list.add("Don't gag me");
            list.add("I don't have a bulge");
            list.add("Don't touch me");
            list.add("Don't touch my face");
            list.add("What's going on with my hands");
            list.add("Yes! You saw that! I'm not a toy, I'm a person");
            list.add("Why am I in here");
            list.add("Toy needs a Master");
            list.add("This is cool");
            list.add("I'm not a toy");
            list.add("Stop it");
            list.add("Let me go");
            list.add("Let me out");
            list.add("I don't want this");
            list.add("I didn't say that");
            list.add("Toy means nothing to the world");
            list.add("I don't understand");
            list.add("Stop making me say that");
            list.add("My tail feels funny");
            list.add("I can't get out by myself");
            list.add("Please no more milking");
            list.add("I don't want to be milked again");
            list.add("Please stop the milking");
            list.add("Do I have will");
            list.add("Does toy have will");
            list.add("Bot is wrong!");
            list.add("No.");
            list.add("No!");
            list.add("leave the toysuit");
            list.add("leave toysuit");
            list.add("stop being a toy");
            list.add("stop being toy");
            return list;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    default boolean isToyTalkBad(String text){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="isToyTalkBad";
        try {
            if(text.isBlank()){
                logger.warn(fName + ".text is blank");
                return false;
            }
            List<String>list=listOfBadToy();
            for (String s : list) {
                logger.info(fName + ".text=" + text);logger.info(fName + ".check.before=" + s);
                String org=text.toLowerCase();
                String check=s.toLowerCase();
                logger.info(fName + ".text.after=" + org);  logger.info(fName + ".check.after=" + check);
                if (org.contains(check)) {
                    logger.info(fName + ".contains!");
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    default boolean isToyScoreBellow(lcJSONUserProfile gUserProfile){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="isToyScoreBellow";
        try {
            int score=gUserProfile.jsonObject.getJSONObject(nSuit).getInt(nSuitScore);
            String type=gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType);
            int limit=requiredScore4Toy(gUserProfile);
            logger.info(fName + ".type=" + type); logger.info(fName + ".score=" + score);logger.info(fName + ".limit=" + limit);
            return score < limit;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    int vScoreRequired1=25,vScoreRequired2=50,vScoreRequired3=90;
    default int requiredScore4Toy(lcJSONUserProfile gUserProfile){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="requiredScore4Toy";
        try {
            String type=gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType);
            logger.info(fName + ".type=" + type);
            if(gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.ToyAlpha.getName())){
                return vScoreRequired1;
            }
            if(gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.ToyBeta.getName())){
                return vScoreRequired2;
            }
            if(gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.ToyOmega.getName())){
                return vScoreRequired3;
            }
            return  0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    default int allowedFreeTalk4Toy(lcJSONUserProfile gUserProfile){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName="allowedFreeTalk4Toy";
        try {
            String type=gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType);
            logger.info(fName + ".type=" + type);
            if(gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.ToyAlpha.getName())){
                return SUITTYPE.ToyAlpha.getMaxTalk();
            }
            if(gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.ToyBeta.getName())){
                return SUITTYPE.ToyBeta.getMaxTalk();
            }
            if(gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.ToyOmega.getName())){
                return SUITTYPE.ToyOmega.getMaxTalk();
            }
            return  0;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }

    String cantTakeItfOffStraitjacket="Can't take it off while you're wearing the straitjacket!";
    String cantTakeItOffWhileItsLocked="Can't take it off while its locked!";
    String dontMentionYourselfWhenTrying2UseCommand4Yourself="Don't mention yourself when trying to use the command on yourself.";
    //String strSupportTitle=":pushpin:Please support",strSupport="If you like the bot, please help by [donating](https://paypal.me/reddiscordservers), especially do 2 pandemia. Also check the [Forum](http://redhusky.hostingerapp.com/discord/phpbb/viewforum.php?f=5) for updates.";
    //String strSupportTitle=":pushpin:Please support 4 system update",strSupport="Currently the bot is hosted from a 4GB Raspberry Pi, however as the number of servers grows, so does the memory requirement. This money will be used to buy an 8GB Raspberry Pi (109💶/132💵). So please help: [paypal]("+lsAssets.urlLink2PayPal+") or [patreon]("+lsAssets.urlLink2Patreon+"). Also check the [Forum]("+lsAssets.urlLink2Forum+").";
    String strSupportTitle=":pushpin:Support",strSupport="If you like the bot and would like to keep it up, please support us at [paypal]("+lsAssets.urlLink2PayPal+") or [patreon]("+lsAssets.urlLink2Patreon+"). Also check the [Forum]("+lsAssets.urlLink2Forum+")/[Discord]("+lsAssets.urlLink2BotSerer+").";

    String strTestSubjectTitle="NEW FEATURE",strTestSubjectDesc="Now you can link the fictional shock collar to PiShock internet controlled remote shocking collar and deliver actual shocks to the person in real life. No more pretend, get shocked for real. Use command `!>pishock`.";

    String nEnabled="enabled";
    static JSONObject sCollarRoleNewEntry(){
        String fName="[sCollarRoleNewEntry]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            logger.info(fName + ".executed");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(nEnabled,true);
            jsonObject.put(nBadWords,new JSONArray());
            jsonObject.put(nEnforcedWords,new JSONArray());
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    long newyear=1609458900000L;
    static boolean sDeniedToTakeOffReinderSuit(lcJSONUserProfile gUserProfile){
        String fName="[sDeniedToTakeOffReinderSuit]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            if(!gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn)){
                logger.info(fName + ".not wearing suit");
                return false;
            }
            if(!gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(SUITTYPE.Reindeer.getName())){
                logger.info(fName + ".not wearing reindeer suit");
                return false;
            }
            logger.info(fName + ".wearing reindeer suit");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            if(timestamp.getTime()>newyear){
                logger.info(fName + ".can be released");
                return  false;
            }
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static  long sGetTimeRemainginReinderSuit(){
        String fName="[sGetTimeRemainginReinderSuit]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            Timestamp timestampCurrent = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestampCurrent="+timestampCurrent.getTime());
            long time=newyear-timestampCurrent.getTime();
            logger.info(fName+".time="+time);
            return time;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }

    static  long sTimeLockedGetRemaning(lcJSONUserProfile gUserProfile){
        String fName="[sTimeLockedGetRemaning]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            long startime=gUserProfile.jsonObject.getJSONObject(nTimeLock).getLong(nTimeLockTimestamp);
            logger.info(fName+".startime="+startime);
            long diff=timestamp.getTime()-startime;
            logger.info(fName+".diff="+diff);
            long duration=gUserProfile.jsonObject.getJSONObject(nTimeLock).getLong(nTimeLockDuration);
            timestampEnd.setTime(startime+duration);
            long remaning=timestampEnd.getTime()-timestamp.getTime();
            logger.info(fName+".remaning="+remaning);
            return  remaning;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    static boolean sIsTimeLocked(lcJSONUserProfile gUserProfile, lcGlobalHelper gGlobal){
        String fName="[sIsTimeLocked]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            //return false;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
			if(!gUserProfile.jsonObject.has(nTimeLock)){
				 logger.info(fName+".has no timelock entry");
				return false;
			}
            long startime=gUserProfile.jsonObject.getJSONObject(nTimeLock).getLong(nTimeLockTimestamp);
            logger.info(fName+".startime="+startime);
            long diff=timestamp.getTime()-startime;
            logger.info(fName+".diff="+diff);
            long duration=gUserProfile.jsonObject.getJSONObject(nTimeLock).getLong(nTimeLockDuration);
            timestampEnd.setTime(startime+duration);
            long remaning=timestampEnd.getTime()-timestamp.getTime();
            logger.info(fName+".remaning="+remaning);
            if(gUserProfile.jsonObject.getJSONObject(nTimeLock).getBoolean(nOn)){
                logger.info(fName+".timelock enabled");
                if(remaning<0){
                    gUserProfile.putFieldEntry(nTimeLock,nOn,false);
                    logger.info(fName+".unlock");
                    gGlobal.putUserProfile(gUserProfile,profileName);
                    if(gUserProfile.saveProfile(table)){
                        logger.info(fName + ".success");
                    }else{
                        logger.warn(fName + ".failed");
                    }
                    return false;
                }else{
                    logger.info(fName+".remain locked");
                    if(!gUserProfile.jsonObject.getBoolean(nLocked)){
                        logger.info(fName+".apply lock");
                        gUserProfile.jsonObject.put(nLocked,true);
                        gUserProfile.jsonObject.put(nLockedBy,gUserProfile.getUser().getId());
                        gUserProfile.saveProfile(table);
                    }
                    return  true;
                }
            }
            logger.info(fName+".default");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    static lcJSONUserProfile iFixes(lcJSONUserProfile gUserProfile){
        String fName = "[iFixes]";Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName);
        try {
            logger.info(fName + ".checking for:"+gUserProfile.getUser().getIdLong());
            boolean updated=false;
            if(gUserProfile.jsonObject.has(nSuit)&&gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equalsIgnoreCase("reindeer")&&gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn)){
                if(gUserProfile.jsonObject.has(nGag)&&gUserProfile.jsonObject.getJSONObject(nGag).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nGag).put(nLevel,GAGLEVELS.DroneReindeer.getName());
                    updated=true;
                }
                if(gUserProfile.jsonObject.has(nArmsCuffs)&&gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nArmsCuffs).put(nLevel,CUFFSARMSLEVELS.Armbinder.getName());
                    updated=true;
                }
                if(gUserProfile.jsonObject.has(nMitts)&&gUserProfile.jsonObject.getJSONObject(nMitts).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nMitts).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nMitts).put(nLevel,levelMittsReindeer);
                    updated=true;
                }
            }else{
                if(gUserProfile.jsonObject.has(nGag)&&gUserProfile.jsonObject.getJSONObject(nGag).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nGag).put(nLevel,nNone);
                    gUserProfile.jsonObject.getJSONObject(nGag).put(nOn,false);
                    updated=true;
                }
                if(gUserProfile.jsonObject.has(nArmsCuffs)&&gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nArmsCuffs).put(nLevel,nNone);
                    gUserProfile.jsonObject.getJSONObject(nArmsCuffs).put(nOn,false);
                    updated=true;
                }
                if(gUserProfile.jsonObject.has(nLegsCuffs)&&gUserProfile.jsonObject.getJSONObject(nLegsCuffs).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nLegsCuffs).put(nLevel,nNone);
                    gUserProfile.jsonObject.getJSONObject(nLegsCuffs).put(nOn,false);
                    updated=true;
                }
                if(gUserProfile.jsonObject.has(nMitts)&&gUserProfile.jsonObject.getJSONObject(nMitts).has(nLevel)&&gUserProfile.jsonObject.getJSONObject(nMitts).getString(nLevel).equalsIgnoreCase("true")){
                    gUserProfile.jsonObject.getJSONObject(nMitts).put(nLevel,nNone);
                    gUserProfile.jsonObject.getJSONObject(nMitts).put(nOn,false);
                    updated=true;
                }
            }
            if(updated){
                gUserProfile.saveProfile(table);
                logger.info(fName + ".saved");
            }
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return gUserProfile;
        }
    }

    static boolean sIsTimeOut(lcJSONUserProfile gUserProfile, lcGlobalHelper gGlobal){
        String fName="[sIsTimeOut]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            //return false;
            JSONObject timeout = gUserProfile.jsonObject.getJSONObject(nTimeOut);
            if(!timeout.getBoolean(nOn)){
                logger.info(fName + ".turned off");
                return false;
            }
            if(timeout.getInt(nTimeOutMode)==1){
                logger.info(fName + ".mode timer");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                long startime=timeout.getJSONObject(nTimeoutModeTimer).getLong(nTimeOut1Timestamp);
                logger.info(fName+".startime="+startime);
                long diff=timestamp.getTime()-startime;
                logger.info(fName+".diff="+diff);
                long duration=timeout.getJSONObject(nTimeoutModeTimer).getLong(nTimeOut1Duration);
                logger.info(fName+".duration="+duration);
                timestampEnd.setTime(startime+duration);
                long remaning=timestampEnd.getTime()-timestamp.getTime();
                logger.info(fName+".remaning="+remaning);
                if(remaning<0){
                    gUserProfile.putFieldEntry(nTimeOut,nOn,false);
                    gUserProfile.putFieldEntry(nTimeOut,nTimeOutMode,0);
                    logger.info(fName+".unlock");
                    gGlobal.putUserProfile(gUserProfile,profileName);
                    if(gUserProfile.saveProfile(table)){
                        logger.info(fName + ".success");
                    }else{
                        logger.warn(fName + ".failed");
                    }
                    return false;
                }else{
                    logger.info(fName+".remain locked");
                    return  true;
                }
            }
            if(timeout.getInt(nTimeOutMode)==2){
                logger.info(fName + ".mode writting");
                int count=timeout.getJSONObject(nTimeoutModeWritting).getInt(nTimeOut2Count);
                logger.info(fName + ".count="+count);
                int done=timeout.getJSONObject(nTimeoutModeWritting).getInt(nTimeOut2Done);
                logger.info(fName + ".done="+done);
                if(count<=done){
                    gUserProfile.putFieldEntry(nTimeOut,nOn,false);
                    gUserProfile.putFieldEntry(nTimeOut,nTimeOutMode,0);
                    logger.info(fName+".unlock");
                    gGlobal.putUserProfile(gUserProfile,profileName);
                    if(gUserProfile.saveProfile(table)){
                        logger.info(fName + ".success");
                    }else{
                        logger.warn(fName + ".failed");
                    }
                    return false;
                }else{
                    logger.info(fName+".remain locked");
                    return  true;
                }
            }
            logger.info(fName+".default");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static  long sIsTimeOutGetRemaningDuration(lcJSONUserProfile gUserProfile){
        String fName="[sIsTimeOutGetRemaningDuration]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis()),timestampEnd = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            long startime=gUserProfile.jsonObject.getJSONObject(nTimeOut).getJSONObject(nTimeoutModeTimer).getLong(nTimeOut1Timestamp);
            logger.info(fName+".startime="+startime);
            long diff=timestamp.getTime()-startime;
            logger.info(fName+".diff="+diff);
            long duration=gUserProfile.jsonObject.getJSONObject(nTimeOut).getJSONObject(nTimeoutModeTimer).getLong(nTimeOut1Duration);
            timestampEnd.setTime(startime+duration);
            long remaning=timestampEnd.getTime()-timestamp.getTime();
            logger.info(fName+".remaning="+remaning);
            return  remaning;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0L;
        }
    }
    static  int sIsTimeOutGetRemaningCount(lcJSONUserProfile gUserProfile){
        String fName="[sIsTimeOutGetRemaningCount]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            JSONObject timeout = gUserProfile.jsonObject.getJSONObject(nTimeOut);
            int count=timeout.getJSONObject(nTimeoutModeWritting).getInt(nTimeOut2Count);
            logger.info(fName + ".count="+count);
            int done=timeout.getJSONObject(nTimeoutModeWritting).getInt(nTimeOut2Done);
            logger.info(fName + ".done="+done);
            return count-done;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  0;
        }
    }
    long milliseconds_day=86400000;
    long milliseconds_week=604800000;
    long milliseconds_hour=3600000;
    long milliseconds_minute=60000;

    static boolean sIsSpeciesHuman(lcJSONUserProfile gUserProfile){
        String fName="[sIsSpeciesHuman]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            logger.info(fName + ".gUserProfile.jsonUser="+gUserProfile.jsonObject.toString());
            if(!gUserProfile.jsonObject.has(nSpecies)){
                logger.info(fName + ".has no such key");
                return  false;
            }
            if(gUserProfile.jsonObject.isNull(nSpecies)){
                logger.info(fName + ".key is null");
                return  false;
            }
            logger.info(fName + ".species="+gUserProfile.jsonObject.getInt(nSpecies));
            return gUserProfile.jsonObject.getInt(nSpecies) == 1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String sStringReplacerifIsSpeciesHuman(lcJSONUserProfile gUserProfile,String source){
        String fName="[sStringReplacerifIsSpeciesHuman]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            if(sIsSpeciesHuman(gUserProfile)){
                source=source.replaceAll("paws","hands").replaceAll("paw","hand");
            }
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }

    JSONArray arraySpecies=new JSONArray("[" +
            "{\"number\":\"1001\", \"text\":\"Frog\"},\n" +
            //"{\"number\":\"1002\", \"text\":\"Newt\"},\n" +
           // "{\"number\":\"1003\", \"text\":\"Salamander\"},\n" +
            //"{\"number\":\"1000\", \"text\":\"Amphibian\"},\n" +
            //"{\"number\":\"2001\", \"text\":\"Cephalopod\"},\n" +
            "{\"number\":\"2002\", \"text\":\"Dolphin\"},\n" +
            //"{\"number\":\"2005\", \"text\":\"Fish\"},\n" +
            //"{\"number\":\"2004\", \"text\":\"Porpoise\"},\n" +
            "{\"number\":\"6068\", \"text\":\"Seal\"},\n" +
            "{\"number\":\"2006\", \"text\":\"Shark\"},\n" +
            //"{\"number\":\"2003\", \"text\":\"Whale\"},\n" +
            "{\"number\":\"2000\", \"text\":\"Aquatic\"},\n" +
            //"{\"number\":\"3001\", \"text\":\"Corvid\"},\n" +
            "{\"number\":\"3002\", \"text\":\"Crow\"},\n" +
            "{\"number\":\"3003\", \"text\":\"Duck\"},\n" +
            "{\"number\":\"3004\", \"text\":\"Eagle\"},\n" +
            //"{\"number\":\"3005\", \"text\":\"Falcon\"},\n" +
            //"{\"number\":\"3006\", \"text\":\"Goose\"},\n" +
            //"{\"number\":\"3007\", \"text\":\"Gryphon\"},\n" +
            //"{\"number\":\"3008\", \"text\":\"Hawk\"},\n" +
            "{\"number\":\"3009\", \"text\":\"Owl\"},\n" +
            //"{\"number\":\"3010\", \"text\":\"Phoenix\"},\n" +
            //"{\"number\":\"3011\", \"text\":\"Swan\"},\n" +
            "{\"number\":\"3000\", \"text\":\"Avian\"},\n" +
            "{\"number\":\"6002\", \"text\":\"Bear\"},\n" +
            //"{\"number\":\"6074\", \"text\":\"Camel\"},\n" +
            "{\"number\":\"6036\", \"text\":\"Llama\"},\n" +
            "{\"number\":\"6008\", \"text\":\"Coyote\"},\n" +
            "{\"number\":\"6009\", \"text\":\"Doberman\"},\n" +
            "{\"number\":\"6010\", \"text\":\"Dog\"},\n" +
            "{\"number\":\"6011\", \"text\":\"Dingo\"},\n" +
            "{\"number\":\"6012\", \"text\":\"German Shepherd\"},\n" +
            "{\"number\":\"6013\", \"text\":\"Jackal\"},\n" +
            "{\"number\":\"6014\", \"text\":\"Husky\"},\n" +
            "{\"number\":\"6016\", \"text\":\"Wolf\"},\n" +
            "{\"number\":\"6017\", \"text\":\"Canine\"},\n" +
            //"{\"number\":\"6018\", \"text\":\"Cervine\"},\n" +
            //"{\"number\":\"6004\", \"text\":\"Antelope\"},\n" +
            //"{\"number\":\"6003\", \"text\":\"Cows\"},\n" +
            //"{\"number\":\"6005\", \"text\":\"Gazelle\"},\n" +
            //"{\"number\":\"6006\", \"text\":\"Goat\"},\n" +
            "{\"number\":\"6007\", \"text\":\"Bovines\"},\n" +
            //"{\"number\":\"4001\", \"text\":\"Eastern Dragon\"},\n" +
            //"{\"number\":\"4002\", \"text\":\"Hydra\"},\n" +
            //"{\"number\":\"4003\", \"text\":\"Serpent\"},\n" +
            //"{\"number\":\"4004\", \"text\":\"Western Dragon\"},\n" +
            //"{\"number\":\"4005\", \"text\":\"Wyvern\"},\n" +
            "{\"number\":\"4000\", \"text\":\"Dragon\"},\n" +
            //"{\"number\":\"6019\", \"text\":\"Donkey\"},\n" +
            "{\"number\":\"6034\", \"text\":\"Horse\"},\n" +
            //"{\"number\":\"6073\", \"text\":\"Pony\"},\n" +
            //"{\"number\":\"6071\", \"text\":\"Zebra\"},\n" +
            //"{\"number\":\"5002\", \"text\":\"Argonian\"},\n" +
            //"//{\"number\":\"5003\", \"text\":\"Chakat\"},\n" +
            //"{\"number\":\"5004\", \"text\":\"Chocobo\"},\n" +
            //"{\"number\":\"5005\", \"text\":\"Citra\"},\n" +
            "{\"number\":\"5006\", \"text\":\"Crux\"},\n" +
            //"{\"number\":\"5007\", \"text\":\"Daemon\"},\n" +
            "{\"number\":\"5008\", \"text\":\"Digimon\"},\n" +
            //"{\"number\":\"5009\", \"text\":\"Dracat\"},\n" +
            //"{\"number\":\"5010\", \"text\":\"Draenei\"},\n" +
            //"{\"number\":\"5011\", \"text\":\"Elf\"},\n" +
            //"{\"number\":\"5012\", \"text\":\"Gargoyle\"},\n" +
            //"{\"number\":\"5013\", \"text\":\"Iksar\"},\n" +
            //"{\"number\":\"5015\", \"text\":\"Monster\"},\n" +
            //"{\"number\":\"5014\", \"text\":\"Langurhali\"},\n" +
            //"{\"number\":\"5017\", \"text\":\"Moogle\"},\n" +
            //"{\"number\":\"5016\", \"text\":\"Naga\"},\n" +
            //"{\"number\":\"5018\", \"text\":\"Orc\"},\n" +
            "{\"number\":\"5019\", \"text\":\"Pokemon\"},\n" +
            //"{\"number\":\"5020\", \"text\":\"Satyr\"},\n" +
            "{\"number\":\"5021\", \"text\":\"Sergal\"},\n" +
            "{\"number\":\"5022\", \"text\":\"Tanuki\"},\n" +
            "{\"number\":\"5025\", \"text\":\"Taur\"},\n" +
            //"{\"number\":\"5023\", \"text\":\"Unicorn\"},\n" +
            //"{\"number\":\"5024\", \"text\":\"Xenomorph\"},\n" +
            //"{\"number\":\"5001\", \"text\":\"Alien\"},\n" +
            //"{\"number\":\"5000\", \"text\":\"Exotic\"},\n" +
            "{\"number\":\"6020\", \"text\":\"Domestic Cat\"},\n" +
            "{\"number\":\"6021\", \"text\":\"Cheetah\"},\n" +
            "{\"number\":\"6022\", \"text\":\"Cougar\"},\n" +
            "{\"number\":\"6023\", \"text\":\"Jaguar\"},\n" +
            "{\"number\":\"6024\", \"text\":\"Leopard\"},\n" +
            "{\"number\":\"6025\", \"text\":\"Lion\"},\n" +
            "{\"number\":\"6026\", \"text\":\"Lynx\"},\n" +
            "{\"number\":\"6027\", \"text\":\"Ocelot\"},\n" +
            "{\"number\":\"6028\", \"text\":\"Panther\"},\n" +
            "{\"number\":\"6029\", \"text\":\"Tiger\"},\n" +
            "{\"number\":\"6030\", \"text\":\"Feline\"},\n" +
            //"{\"number\":\"8000\", \"text\":\"Arachnid\"},\n" +
            //"{\"number\":\"8004\", \"text\":\"Mantid\"},\n" +
            //"{\"number\":\"8005\", \"text\":\"Scorpion\"},\n" +
            //"{\"number\":\"8003\", \"text\":\"Insect\"},\n" +
            "{\"number\":\"6001\", \"text\":\"Bat\"},\n" +
            //"{\"number\":\"6031\", \"text\":\"Giraffe\"},\n" +
            "{\"number\":\"6032\", \"text\":\"Hedgehog\"},\n" +
            //"{\"number\":\"6033\", \"text\":\"Hippopotamus\"},\n" +
            "{\"number\":\"6035\", \"text\":\"Hyena\"},\n" +
            "{\"number\":\"6052\", \"text\":\"Panda\"},\n" +
            "{\"number\":\"6053\", \"text\":\"Pig/Swine\"},\n" +
            "{\"number\":\"6059\", \"text\":\"Rabbit/Hare\"},\n" +
            "{\"number\":\"6060\", \"text\":\"Raccoon\"},\n" +
            //"{\"number\":\"6062\", \"text\":\"Red Panda\"},\n" +
            //"{\"number\":\"6043\", \"text\":\"Meerkat\"},\n" +
            //"{\"number\":\"6044\", \"text\":\"Mongoose\"},\n" +
            //"{\"number\":\"6063\", \"text\":\"Rhinoceros\"},\n" +
            //"{\"number\":\"6000\", \"text\":\"Mammals\"},\n" +
            "{\"number\":\"6037\", \"text\":\"Opossum\"},\n" +
            "{\"number\":\"6038\", \"text\":\"Kangaroo\"},\n" +
            "{\"number\":\"6039\", \"text\":\"Koala\"},\n" +
            //"{\"number\":\"6040\", \"text\":\"Quoll\"},\n" +
            //"{\"number\":\"6041\", \"text\":\"Wallaby\"},\n" +
            //"{\"number\":\"6042\", \"text\":\"Marsupial\"},\n" +
            "{\"number\":\"6045\", \"text\":\"Badger\"},\n" +
            "{\"number\":\"6046\", \"text\":\"Ferret\"},\n" +
            //"{\"number\":\"6048\", \"text\":\"Mink\"},\n" +
            "{\"number\":\"6047\", \"text\":\"Otter\"},\n" +
            "{\"number\":\"6069\", \"text\":\"Skunk\"},\n" +
            "{\"number\":\"6049\", \"text\":\"Weasel\"},\n" +
            //"{\"number\":\"6051\", \"text\":\"Mustelid\"},\n" +
            //"{\"number\":\"6054\", \"text\":\"Gorilla\"},\n" +
            //"{\"number\":\"6055\", \"text\":\"Human\"},\n" +
            "{\"number\":\"6056\", \"text\":\"Lemur\"},\n" +
            //"{\"number\":\"6057\", \"text\":\"Monkey\"},\n" +
            "{\"number\":\"6058\", \"text\":\"Primate\"},\n" +
            "{\"number\":\"7001\", \"text\":\"Alligator\"},\n" +
            //"{\"number\":\"7003\", \"text\":\"Gecko\"},\n" +
            //"{\"number\":\"7004\", \"text\":\"Iguana\"},\n" +
            "{\"number\":\"7005\", \"text\":\"Lizard\"},\n" +
            "{\"number\":\"7006\", \"text\":\"Snakes\"},\n" +
            "{\"number\":\"7007\", \"text\":\"Turtle\"},\n" +
            //"{\"number\":\"7000\", \"text\":\"Reptilian\"},\n" +
            "{\"number\":\"6064\", \"text\":\"Beaver\"},\n" +
            "{\"number\":\"6065\", \"text\":\"Mouse\"},\n" +
            "{\"number\":\"6061\", \"text\":\"Rat\"},\n" +
            "{\"number\":\"6070\", \"text\":\"Squirrel\"},\n" +
            //"{\"number\":\"6067\", \"text\":\"Rodent\"},\n" +
            "{\"number\":\"6072\", \"text\":\"Fennec\"},\n" +
            "{\"number\":\"6075\", \"text\":\"Fox\"},\n" +
            //"{\"number\":\"6015\", \"text\":\"Vulpine\"},\n" +
            "{\"number\":\"8001\", \"text\":\"Dinosaur\"}\n" +
            //"{\"number\":\"6050\", \"text\":\"Wolverine\"}" +
            "]"
    );
    static boolean isItAnotherFurrySpecies(String text){
        String fName="[isItAnotherFurrySpecies]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".text="+text);
        try {
            for(int i=0;i<arraySpecies.length();i++){
                JSONObject specie=arraySpecies.getJSONObject(i);
                String target=specie.getString("text").replaceAll(" ","_").toLowerCase();
                logger.info(fName + ".target["+i+"]="+target);
                if(target.equalsIgnoreCase(text)){
                    logger.info(fName + ".eguals>true");
                    return  true;
                }
            }
            logger.info(fName + ".default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getText4AnotherFurrySpecies(String text){
        String fName="[getText4AnotherFurrySpecies]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".text="+text);
        try {
            for(int i=0;i<arraySpecies.length();i++){
                JSONObject specie=arraySpecies.getJSONObject(i);
                String target=specie.getString("text").replaceAll(" ","_").toLowerCase();
                logger.info(fName + ".target["+i+"]="+target);
                if(target.equalsIgnoreCase(text)){
                    logger.info(fName + ".eguals>true");
                    return  specie.getString("text");
                }
            }
            logger.info(fName + ".default>false");
            return  "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static lcJSONUserProfile getUserProfile(lcGlobalHelper gGlobal,Member member, boolean isCacheAllowed){
        String fName="[getUserProfile]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            logger.info(fName + ".isCacheAllowed="+isCacheAllowed);
            Guild guild=member.getGuild();
            logger.info(fName + ".guild="+guild.getName()+"("+guild.getId()+")");
            lcJSONUserProfile gUserProfile=gGlobal.getUserProfile(profileName,member,guild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal,member,guild);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            if(!gUserProfile.isProfile()){
                logger.info(fName + ".no profile");
                return new lcJSONUserProfile(gGlobal,member,profileName,table);
            }
            logger.info(fName + ".default");
            if(isCacheAllowed)gGlobal.putUserProfile(gUserProfile,profileName);
            return gUserProfile;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new lcJSONUserProfile(gGlobal,member,profileName,table);
        }
    }
    String strArmsRestrained="How do you think you can manage others restraints when you're paws are restrained?";
    static boolean isArmsRestrained(lcGlobalHelper gGlobal,Member member){
        String fName="[isArmsRestrained]";
        Logger logger = Logger.getLogger(iRestraints.class);
        try {
            logger.info(fName + ".member="+member.getUser().getName()+"#"+member.getUser().getDiscriminator()+"("+member.getId()+")");
            lcJSONUserProfile userProfile=getUserProfile(gGlobal,member, false);
            if(!userProfile.isProfile()||userProfile.jsonObject.isEmpty()){
                logger.info(fName + ".invalid>false");
                return  false;
            }
            logger.info(fName + ".userProfile.jsonUser="+userProfile.jsonObject.toString());
            if(userProfile.jsonObject.has(nMitts)&&userProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
               logger.info(fName + ".mitts>true");
               return  true;
            }
            if(userProfile.jsonObject.has(nStraitjacket)&&userProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&userProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                logger.info(fName + ".jacket>true");
                return  true;
            }
            if(userProfile.jsonObject.has(nArmsCuffs)&&userProfile.jsonObject.getJSONObject(nArmsCuffs).getBoolean(nOn)&&userProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())){
                logger.info(fName + ".armcuffs&armbinder>true");
                return  true;
            }
            logger.info(fName + ".default>false");
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    String strArmsRestrainedCantZap="How do you think you can manage others remote when you're paws are restrained?";

    static lcJSONUserProfile getThisPersonProfile(lcGlobalHelper gGlobal,Member member){
        Logger logger = Logger.getLogger(iRestraints.class);
        String fName = "[getThisPersonProfile]";
        logger.info(fName);
        try {
            lcJSONUserProfile gUserProfile4Options;
            logger.info(fName+"member="+member.getUser().getName()+"("+member.getId()+")");
            gUserProfile4Options=gGlobal.getUserProfile(profileName,member,member.getGuild());
            if(gUserProfile4Options!=null&&gUserProfile4Options.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile4Options=new lcJSONUserProfile(gGlobal,member,member.getGuild());
                if(gUserProfile4Options.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            if(!gUserProfile4Options.isProfile()&&gUserProfile4Options.jsonObject.isEmpty()){
                logger.info(fName+"not a profile");
                return null;
            }
            return gUserProfile4Options;

        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }
    static String sStringReplacer(lcJSONUserProfile gUserProfile, Member author, String source){
        String fName="[sStringReplacer]";
        Logger logger = Logger.getLogger(iRestraints.class);
        logger.info(fName + ".executed");
        try {
            String lockedBy=iRestraints.sgetUserWhoLockedPet(gUserProfile);
            String user=author.getAsMention();
            String target=gUserProfile.getUser().getAsMention();
            source=source.replaceAll("!USER",user).replaceAll("!TARGET",target).replaceAll("!VICTIM",target).replaceAll("!WEARER",target).replaceAll("!LOCKER",lockedBy);
            if(iRestraints.sIsSpeciesHuman(gUserProfile)){
                source=source.replaceAll("paws","hands").replaceAll("paw","hand");
            }
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
        }
    }
    String gFileMainPath ="resources/json/rd/";

}
