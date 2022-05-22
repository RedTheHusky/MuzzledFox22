package web.jexpress.childrens;

import express.DynExpress;
import express.http.RequestMethod;
import express.http.request.Request;
import express.http.response.Response;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.webhook.lcWebHookBuild;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;
import restraints.in.iGagWork;
import restraints.in.iRestraints;
import restraints.in.iSuit;
import restraints.models.lcBDSMGuildProfiles;
import restraints.models.enums.CUFFSARMSLEVELS;
import restraints.models.enums.CUFFSLEGSLEVELS;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.HOODLEVELS;
import userprofiles.entities.rSlaveRegistry;
import web.jexpress.models.expStats;
import web.jexpress.models.collectdata.CollectData;
import web.jexpress.models.collectdata.DataSetDasboardCommon;
import web.jexpress.models.collectdata.DataSetDiscord;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class expresschildRD  {
    Logger logger = Logger.getLogger(getClass());Logger logger2 = Logger.getLogger(CrunchifyLog4jLevel.GAGUSED_STR);
    lcGlobalHelper gGlobal;
    public expresschildRD(lcGlobalHelper global,Request req, Response res,String command) {
        String fName="build";
        try {
            gGlobal=global;
            Runnable r = new runLocal(req,res,command);
            new Thread(r).start();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    protected class runLocal implements Runnable,iRestraints,iGagWork {
        String cName = "[runLocal]";
        String gCommand="";Request gReq; Response gRes;
        CollectData collectData=new CollectData();
        DataSetDasboardCommon dataSetDasboardCommon=new DataSetDasboardCommon();
        DataSetDiscord dataSetDiscord =new DataSetDiscord();
        public runLocal(Request req, Response res,String command) {
            String fName="runLocal";
            try {
                logger.info(".run build");
                gCommand=command;
                logger.info(".gCommand="+gCommand);
                gReq=req;gRes=res;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        @Override
        public void run() {
            String fName="run";
            try {
                collectData.set(gReq);
                dataSetDasboardCommon.set(collectData);
                dataSetDiscord.set(collectData,gGlobal);
                switch (gCommand){
                    case "getVerify":
                        getVerify();
                        break;
                    case "getGag":
                        getGag();
                    case "postGag":
                        getGag_Post();
                        break;
                    default:
                        logger.warn("invalid command="+gCommand);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }



        String keyVersion="version",valueVersion="0.0.2";

        @DynExpress(context = "/rd/verify")
        public void getVerify() {
            String fName="getVerify";
            try {
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();
                jsonResponse.put(keyVersion,valueVersion);
                if(dataSetDiscord.gGuild!=null){
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        jsonGuild.put(llCommonKeys.GuildStructure.keyId, dataSetDiscord.gGuild.getIdLong());
                        jsonGuild.put(llCommonKeys.GuildStructure.keyName, dataSetDiscord.gGuild.getName());
                        jsonResponse.put(llCommonKeys.keyGuild,jsonGuild);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gUser!=null){
                    try {
                        JSONObject jsonUser=new JSONObject();
                        jsonUser.put(llCommonKeys.UserStructure.keyId, dataSetDiscord.gUser.getIdLong());
                        jsonUser.put(llCommonKeys.UserStructure.keyUsername, dataSetDiscord.gUser.getName());
                        jsonUser.put(llCommonKeys.UserStructure.keyDiscriminator, dataSetDiscord.gUser.getDiscriminator());
                        jsonResponse.put(llCommonKeys.keyUser,jsonUser);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gMember!=null){
                    try {
                        JSONObject jsonMember=new JSONObject();
                        jsonMember.put(llCommonKeys.keyId, dataSetDiscord.gMember.getIdLong());
                        jsonMember.put(llCommonKeys.keyEffectiveName, dataSetDiscord.gMember.getEffectiveName());
                        jsonMember.put(llCommonKeys.keyRoles, dataSetDiscord.gMember.getRoles().toString());
                        jsonResponse.put(llCommonKeys.keyMember,jsonMember);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gTextChannel!=null){
                    try {
                        JSONObject jsonTextChannel=new JSONObject();
                        jsonTextChannel.put(llCommonKeys.keyId, dataSetDiscord.gTextChannel.getIdLong());
                        jsonTextChannel.put(llCommonKeys.keyName, dataSetDiscord.gTextChannel.getName());
                        jsonResponse.put(llCommonKeys.keyTextChannel,jsonTextChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gPrivateChannel!=null){
                    try {
                        JSONObject jsonPrivateChannel=new JSONObject();
                        jsonPrivateChannel.put(llCommonKeys.keyId, dataSetDiscord.gPrivateChannel.getIdLong());
                        jsonPrivateChannel.put(llCommonKeys.keyName, dataSetDiscord.gPrivateChannel.getName());
                        jsonResponse.put(llCommonKeys.keyPrivateChannel,jsonPrivateChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/rd/verify",method = RequestMethod.POST)
        public void getVerify_Post() {
            String fName="getVerify_Post";
            try {
                getVerify();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/rd/gag",method = RequestMethod.POST)
        public void getGag_Post() {
            String fName="getGag_Post";
            try {
                getGag();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        @DynExpress(context = "/rd/gag")
        public void getGag() {
            String fName="getGag";
            try {
                JSONObject jsonStatus=new JSONObject(),jsonResponse=new JSONObject();

                jsonResponse.put(expStats.keyMeta, generateMeta());
                jsonResponse.put("gag",TextPostSynthesizer());

                jsonStatus.put(expStats.keyCode,expStats.CODE_OK);
                jsonStatus.put(expStats.keyLable,expStats.LABEL_OK);
                jsonStatus.put(expStats.keyMessage,expStats.INFO_OK);
                jsonResponse.put(expStats.keyStatus,jsonStatus);
                gRes=expStats.defaultHeaders(gRes);
                gRes.send(jsonResponse.toString());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                expStats.respondInternalError(gRes);
            }
        }
        public JSONObject generateMeta(){
            String fName="generateMeta";
            try {
                JSONObject jsonMeta=new JSONObject();
                jsonMeta.put(keyVersion,valueVersion);
                if(dataSetDiscord.gGuild!=null){
                    try {
                        JSONObject jsonGuild=new JSONObject();
                        jsonGuild.put(llCommonKeys.GuildStructure.keyId, dataSetDiscord.gGuild.getIdLong());
                        jsonGuild.put(llCommonKeys.GuildStructure.keyName, dataSetDiscord.gGuild.getName());
                        jsonMeta.put(llCommonKeys.keyGuild,jsonGuild);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gUser!=null){
                    try {
                        JSONObject jsonUser=new JSONObject();
                        jsonUser.put(llCommonKeys.UserStructure.keyId, dataSetDiscord.gUser.getIdLong());
                        jsonUser.put(llCommonKeys.UserStructure.keyUsername, dataSetDiscord.gUser.getName());
                        jsonUser.put(llCommonKeys.UserStructure.keyDiscriminator, dataSetDiscord.gUser.getDiscriminator());
                        jsonMeta.put(llCommonKeys.keyUser,jsonUser);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gMember!=null){
                    try {
                        JSONObject jsonMember=new JSONObject();
                        jsonMember.put(llCommonKeys.keyId, dataSetDiscord.gMember.getIdLong());
                        jsonMember.put(llCommonKeys.keyEffectiveName, dataSetDiscord.gMember.getEffectiveName());
                        jsonMember.put(llCommonKeys.keyRoles, dataSetDiscord.gMember.getRoles().toString());
                        jsonMeta.put(llCommonKeys.keyMember,jsonMember);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gTextChannel!=null){
                    try {
                        JSONObject jsonTextChannel=new JSONObject();
                        jsonTextChannel.put(llCommonKeys.keyId, dataSetDiscord.gTextChannel.getIdLong());
                        jsonTextChannel.put(llCommonKeys.keyName, dataSetDiscord.gTextChannel.getName());
                        jsonMeta.put(llCommonKeys.keyTextChannel,jsonTextChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(dataSetDiscord.gPrivateChannel!=null){
                    try {
                        JSONObject jsonPrivateChannel=new JSONObject();
                        jsonPrivateChannel.put(llCommonKeys.keyId, dataSetDiscord.gPrivateChannel.getIdLong());
                        jsonPrivateChannel.put(llCommonKeys.keyName, dataSetDiscord.gPrivateChannel.getName());
                        jsonMeta.put(llCommonKeys.keyPrivateChannel,jsonPrivateChannel);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                return  jsonMeta;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        String keyMessage="message",keyOriginal="original",keyEdited="edited";
        String keyGag="gag",keyLevel="level";
        String keyRestraints="restraints";
        public JSONObject TextPostSynthesizer() {
            String fName = "[TextPostSynthesizer]";logger.info( fName +".TextPostSynthesizer start");
            try {
                JSONObject jsonResponse=new JSONObject();
                logger.info(fName + ".gUser=" + dataSetDiscord.gUser.getName()+"("+ dataSetDiscord.gUser.getId()+") .gGuild=" + dataSetDiscord.gGuild.getName()+"("+ dataSetDiscord.gGuild.getId()+") .gTextChannel=" + dataSetDiscord.gTextChannel.getName()+"("+ dataSetDiscord.gTextChannel.getId()+")");
                guildloader();
                leashHandlingTarget();
                if(!userloader()){
                    logger.info(fName + ".userload>false>ignore");
                    jsonResponse.put(expStats.keyStatus,"invalid");
                    return jsonResponse;
                }

                if(dataSetDiscord.inputMessageContent.startsWith(lsUnicode.CombiningGraphemeJoiner)|| dataSetDiscord.inputMessageContent.startsWith(lsUnicode.unicodeCombiningGraphemeJoiner)){
                    logger.info(fName + ".message starts with CombiningGraphemeJoiner>ignore");
                    jsonResponse.put(expStats.keyStatus,"invalid");
                    return jsonResponse;
                }

                boolean isSafeWordDisabled4Toysuit=false,isSafeWordDisabled4Timelock=false;
                try {
                    if(!gUserProfile.jsonObject.isEmpty()&&gUserProfile.jsonObject.has(iRestraints.nSuit)){
                        JSONObject suit=gUserProfile.jsonObject.getJSONObject(iRestraints.nSuit);
                        if(suit.has(iRestraints.nSuitFreeTalkAvailable)&&suit.getBoolean(iRestraints.nOn)){
                            String suitType=suit.getString(iRestraints.nSuitType);
                            logger.info(fName + ".suitType=" +suitType);
                            if(suitType.equals(iSuit.levelSuitSpecialToyAlpha)||suitType.equals(iSuit.levelSuitSpecialToyBeta)||suitType.equals(iSuit.levelSuitSpecialToyOmega)){
                                isSafeWordDisabled4Toysuit=true;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    if(iRestraints.sIsTimeLocked(gUserProfile,gGlobal)){
                        isSafeWordDisabled4Timelock=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                boolean isSafeWordDisabled=false;
                try {
                    if(GAG.has(iRestraints.flagDisableGagSafeWords)&&GAG.getBoolean(iRestraints.flagDisableGagSafeWords)){
                        isSafeWordDisabled=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if (dataSetDiscord.inputMessageContent.equalsIgnoreCase(iRestraints.nGagSafeword)) {
                    logger.info(fName + ".isSafeWordDisabled4Toysuit=" +isSafeWordDisabled4Toysuit);
                    logger.info(fName + ".isSafeWordDisabled4Timelock=" +isSafeWordDisabled4Timelock);
                    if(isSafeWordDisabled4Timelock){
                        logger.info(fName + ".safeword detected & timelock >ignore");
                        extraMessages.put("*My safeword disabled do to running timelock!");
                    }else
                    if(isSafeWordDisabled4Toysuit){
                        logger.info(fName + ".safeword detected & toysuiton >ignore");
                        extraMessages.put("*My safeword disabled do to wearing the suit!");
                    }else
                    if(iRestraints.sisPermalocked(gUserProfile)){
                        logger.info(fName + ".safeword detected & permalock >ignore");
                        extraMessages.put("*My safeword disabled do to permalocked!");
                    }else
                    if(isSafeWordDisabled){
                        logger.info(fName + ".safeword detected & disabled >ignore");
                        extraMessages.put("*My safeword disabled!");
                    }
                    else{
                        logger.info(fName + ".safeword detected > set gag to off");
                        try {
                            logger.info(fName + "GAG="+GAG.toString());
                            GAG.put(iRestraints.nOn, false);
                            GAG.put(iRestraints.nLevel, GAGLEVELS.None.getName());
                            gUserProfile.jsonObject.put(iRestraints.nGag,GAG);
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        try {
                            if(gUserProfile.jsonObject.has(iRestraints.nCollar)&&gUserProfile.jsonObject.getJSONObject(iRestraints.nCollar).has(iRestraints.nShockeEnabled)){
                                logger.info(fName + ".has collar field:"+gUserProfile.jsonObject.getJSONObject(iRestraints.nCollar).toString());
                                gUserProfile.jsonObject.getJSONObject(iRestraints.nCollar).put(iRestraints.nShockeEnabled, false);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        try {
                            if(ARMCUFFS!=null&&!ARMCUFFS.isEmpty()&&ARMCUFFS.has(iRestraints.nPostRestrictEnabled)){
                                logger.info(fName + ".has PostRestrictEnabled field:"+ARMCUFFS.toString());
                                gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).put(iRestraints.nPostRestrictEnabled, false);
                                gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).put(iRestraints.nPostDelay, 0);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                        saveProfile();
                        jsonResponse.put(expStats.keyInfo,"safeword used");
                        jsonResponse.put(expStats.keyStatus,"ignored:safeword");
                        return jsonResponse;
                    }
                }
                if(isChannelBlockedDue2Leash()){
                    logger.info(fName + ".channel is blocked>delete message");
                    jsonResponse.put(expStats.keyInfo,"channel is blocked do to leash");
                    jsonResponse.put(expStats.keyStatus,"post_override:channel_blocked_leash");
                }
                else if(isPostRestrictedDue2TimeOut()){
                    logger.info(fName + ".post is restricted>delete message");
                    jsonResponse.put(expStats.keyInfo,"post is blocked do to timeout");
                    jsonResponse.put(expStats.keyStatus,"post_override:post_blocked_timeout");
                }
                else if(isChannelLimitedDue2Cuffing()){
                    logger.info(fName + ".channel is limited>delete message");
                    jsonResponse.put(expStats.keyInfo,"channel is blocked do to leg cuffs");
                    jsonResponse.put(expStats.keyStatus,"post_override:channel_blocked_legcuffs");
                }
                else if(isPostLimitedDue2Cuffing()){
                    logger.info(fName + ".post is limited>delete message");
                    jsonResponse.put(expStats.keyInfo,"post is limited do to arm cuffs");
                    jsonResponse.put(expStats.keyStatus,"post_override:post_limited_armcuffs");
                }
                else{
                    if(muffleSpeach()){
                        jsonResponse.put("messages",messages2Send);
                        jsonResponse.put(expStats.keyInfo,"gag true");
                        jsonResponse.put(expStats.keyStatus,"post_override:gag used");
                    }else{
                        jsonResponse.put(expStats.keyInfo,"gag false");
                        jsonResponse.put(expStats.keyStatus,"ignored:no gag used");
                    }
                    if(wasShocked){
                        shockedPost(valueShockInt,valueShockWord);
                        if(jsonResponse.has(expStats.keyStatus)){
                            jsonResponse.put(expStats.keyStatus,jsonResponse.getString(expStats.keyStatus)+"|shocked");
                        }
                        if(jsonResponse.has(expStats.keyInfo)){
                            jsonResponse.put(expStats.keyInfo,jsonResponse.getString(expStats.keyInfo)+"|shocked");
                        }
                    }
                }
                jsonResponse.put("extramessages", extraMessages);
                logger.info(".TextPostSynthesizer ended");
                return  jsonResponse;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        private boolean guildloader(){
            String fName="[guildloader]";
            logger.info(fName );
            try {
                initProfile();
                loadValues();
                return true;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                return  false;
            }
        }
        lcBDSMGuildProfiles gBDSMCommands;
        private void initProfile(){
            String fName="[initProfile]";
            logger.info(fName+".safety check");
            gBDSMCommands=new lcBDSMGuildProfiles(gGlobal, dataSetDiscord.gGuild);
            gBDSMCommands.muzzle.init();
            gBDSMCommands.collar.init();

        }
        private void leashHandlingTarget(){
            String fName="[leashHandlingTarget]";
            logger.info(fName );
            try {
                logger.info(fName+"removed as this handles the leash target and not the wearer" );
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);

            }
        }
        Boolean vEnabled=false; String vMode="";String vAlert="";
        JSONArray vBannedChannels=null;JSONArray vAllowedChannels=null;JSONArray vBannedUsers=null;
        private void loadValues(){
            String fName = "[loadValues]";
            logger.info(fName);
            try {

                vMode= gBDSMCommands.muzzle.gProfile.getFieldEntryAsString(iRestraints.fieldMode);
                vEnabled = gBDSMCommands.muzzle.gProfile.getFieldEntryAsBoolean(iRestraints.fieldEnabled);
                vAlert= gBDSMCommands.muzzle.gProfile.getFieldEntryAsString(iRestraints.fieldAlert);
                vBannedChannels= gBDSMCommands.muzzle.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldBannedChannels);
                vBannedUsers= gBDSMCommands.muzzle.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldBannedUsers);
                vAllowedChannels= gBDSMCommands.muzzle.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldAllowedChannels);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        int maxcycle=2147483630;
        private Boolean isAllowedHere(){
            String fName = "[isAllowedHere]";
            logger.info(fName);
            logger.info(fName+"vMode="+vMode);
            if(!vMode.equals(iRestraints.valueBlack) && !vMode.equals(iRestraints.valueWhite)){
                logger.info(fName+"invalid mode");return true;
            }
            if(vMode.equals(iRestraints.valueWhite)){
                if(vAllowedChannels==null){
                    logger.info(fName+"white>list null");return true;
                }
                if(vAllowedChannels.isEmpty()){
                    logger.info(fName+"white>list empty");return true;
                }
                if(vAllowedChannels.length()==0){
                    logger.info(fName+"white>list length 0");return true;
                }
                logger.info(fName+"white>vAllowedChannels.length()="+vAllowedChannels.length());
                for(int i=0;i<vAllowedChannels.length();i++){
                    if(i>=maxcycle)break;
                    if(vAllowedChannels.getString(i).equals(dataSetDiscord.gTextChannel.getId())){
                        logger.info(fName+"white>found channel");return true;
                    }
                }
                logger.info(fName+"white mode exception");
                return false;
            }else
            if(vMode.equals(iRestraints.valueBlack)){
                if(vBannedChannels==null){
                    logger.info(fName+"black>list null");return true;
                }
                if(vBannedChannels.isEmpty()){
                    logger.info(fName+"black>list empty");return true;
                }
                if(vBannedChannels.length()==0){
                    logger.info(fName+"black>list length 0");return true;
                }
                logger.info(fName+"white>vBannedChannels.length()="+vBannedChannels.length());
                logger.info(fName + ".vBannedChannels=:" + vBannedChannels.toString());
                for(int i=0;i<vBannedChannels.length();i++){
                    if(i>=maxcycle)break;
                    if(vBannedChannels.getString(i).equals(dataSetDiscord.gTextChannel.getId())){
                        logger.info(fName+"black>found channel");return false;
                    }
                }
            }
            logger.info(fName+"default");
            return true;
        }
        private Boolean isAllowedUser(){
            String fName = "[isAllowedUser]";
            logger.info(fName);
            if(vBannedUsers==null||vBannedUsers.isEmpty()){
                logger.info(fName+"list null");return true;
            }
            for(int i=0;i<vBannedUsers.length();i++){
                if(i>=maxcycle)break;
                if(vBannedUsers.getString(i).equals(dataSetDiscord.gUser.getId())){
                    logger.info(fName+"banned user found");return false;
                }
            }
            logger.info(fName+"default");
            return true;
        }
        private boolean userloader(){
            String fName="[userloader]";
            logger.info(fName );
            try {
                if (dataSetDiscord.gUser.isBot()) {
                    logger.info(fName + "the user is a main.java.bot > ignore");
                    return false;
                }
                //logger.info(fName + ".gMessageContentRaw="+gMessageContentRaw);
                if (!getProfile()) {
                    logger.info(fName + "user has no profile > ignore");
                    return false;
                }
                try {
                    if(GAG.has(iRestraints.flagDisableGagOOCException)&&GAG.getBoolean(iRestraints.flagDisableGagOOCException)){
                        isDisabledOOC=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName + ".isDisabledOOC="+isDisabledOOC);
                logger.info(fName + ".prefix="+ lsGlobalHelper.llPrefixStr);
                logger.info(fName + ".mention="+ dataSetDiscord.gGuild.getSelfMember().getAsMention());
                if (dataSetDiscord.inputMessageContent.startsWith(lsGlobalHelper.llPrefixStr)&&!isDisabledOOC) {
                    logger.info(fName + ".starts with prefix> ignore");
                    return false;
                }
                if (dataSetDiscord.inputMessageContent.startsWith(dataSetDiscord.gGuild.getSelfMember().getAsMention())) {
                    logger.info(fName + ".starts with @mention> ignore");
                    return false;
                }
                if (dataSetDiscord.inputMessageContent.startsWith(dataSetDiscord.gGuild.getSelfMember().getAsMention().replaceFirst("<@","<@!"))) {
                    logger.info(fName + ".starts with @!mention> ignore");
                    return false;
                }
                if (dataSetDiscord.inputMessageContent.startsWith("((")&&!isDisabledOOC) {
                    logger.info(fName + ".starts with ((> ignore");
                    return false;
                }
                if (gUserProfile==null||gUserProfile.jsonObject ==null) {
                    logger.info(fName + "json invalid missing");
                    return false;
                }
                return true;

            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                return  false;
            }
        }
        lcJSONUserProfile gUserProfile;
        String  table="rdrestraints",profileName="rdrestraints";
        private Boolean getProfile(){
            String fName="[getProfile]";
            logger.info(fName);
            gUserProfile=gGlobal.getUserProfile(profileName, dataSetDiscord.gUser, dataSetDiscord.gGuild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(gGlobal, dataSetDiscord.gUser, dataSetDiscord.gGuild);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            if(!gUserProfile.isProfile()){
                logger.info(fName + ".not a profile"); return false;
            }
            //gGlobal.putUserProfile(gUserProfile,profileName);
            gUserProfile=iRestraints.iFixes(gUserProfile);
            getMainJsons();return true;
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            getMainJsons();
            if(gUserProfile.saveProfile(table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        private void addProfile(){
            String fName="[addProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
        }
        JSONObject GAG,LEGCUFFS,ARMCUFFS;
        JSONObject LEASHHANDLE=new JSONObject();
        JSONArray TRAININGGAGENTRIES=new JSONArray();
        boolean isDisabledOOC=false;
        private void getMainJsons(){
            String fName="[getMainJsons]";
            logger.info(fName);
            try {
                if(gUserProfile.jsonObject.has(iRestraints.nGag)){  GAG=gUserProfile.jsonObject.getJSONObject(iRestraints.nGag);}
                if(gUserProfile.jsonObject.has(iRestraints.nLegsCuffs)){ LEGCUFFS = gUserProfile.jsonObject.getJSONObject(iRestraints.nLegsCuffs);}
                if(gUserProfile.jsonObject.has(iRestraints.nArmsCuffs)){    ARMCUFFS = gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs);}
                if(GAG.has(iRestraints.nTrainingGagEntries)){  TRAININGGAGENTRIES=GAG.getJSONArray(iRestraints.nTrainingGagEntries);}
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean isChannelBlockedDue2Leash(){
            String fName="[isChannelBlockedDue2Leash]";
            logger.info(fName );
            try {
                LEASHHANDLE=gBDSMCommands.collar.getLeashByWearer(dataSetDiscord.gUser);
                if(LEASHHANDLE.isEmpty()){
                    logger.info(fName+"isEmpty");
                    return false;
                }
                if(LEASHHANDLE.isNull(gBDSMCommands.collar.keyMemberTarget)){
                    logger.info(fName+"isNull="+gBDSMCommands.collar.keyMemberTarget);
                    return false;
                }
                if(LEASHHANDLE.isNull(gBDSMCommands.collar.keyTextChannelTarget)){
                    logger.info(fName+"isNull="+gBDSMCommands.collar.keyTextChannelTarget);
                    return false;
                }
                Member target= dataSetDiscord.gGuild.getMemberById(LEASHHANDLE.getString(gBDSMCommands.collar.keyMemberTarget));
                if(target==null){
                    logger.info(fName+"target="+gBDSMCommands.collar.keyTextChannelTarget);
                    return false;
                }
                TextChannel textChannel= dataSetDiscord.gGuild.getTextChannelById(LEASHHANDLE.getString(gBDSMCommands.collar.keyTextChannelTarget));
                if(textChannel.getIdLong()!= dataSetDiscord.gTextChannel.getIdLong()){
                    logger.info(fName+"not same channel>step 2");
                    logger.info(fName+"target="+target.getUser().getName()+"("+target.getId()+")");
                    OnlineStatus targetOnlineStatus=target.getOnlineStatus();
                    logger.info(fName+"targetOnlineStatus="+targetOnlineStatus.getKey());
                    if(targetOnlineStatus==OnlineStatus.ONLINE||targetOnlineStatus==OnlineStatus.IDLE||targetOnlineStatus==OnlineStatus.DO_NOT_DISTURB){
                        logger.info(fName+"they online>true");
                        extraMessages.put("I is yanked back by the leash to "+textChannel.getAsMention()+" by "+target.getAsMention()+".");

                        return true;
                    }
                }
                logger.info(fName+"default>false");
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
            return false;
        }
        private boolean isPostRestrictedDue2TimeOut(){
            String fName="[isPostRestrictedDue2TimeOut]";
            logger.info(fName );
            try {
                if(!gUserProfile.jsonObject.has(iRestraints.nTimeOut)){
                    logger.info(fName+".no such entry");
                    return false;
                }
                JSONObject timeout = gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeOut);
                logger.info(fName + "timeout="+timeout.toString());
                if(!timeout.getBoolean(iRestraints.nOn)){
                    logger.info(fName+".not enabaled");
                    return false;
                }
                if(iRestraints.sIsTimeOut(gUserProfile,gGlobal)){
                    boolean isCustomChannel=false,isGuildChannel=false;
                    gBDSMCommands.timeout.init();
                    if(gBDSMCommands.timeout.gProfile.isExistent()){
                        logger.info(fName+".timeout.gProfile=="+gBDSMCommands.timeout.gProfile.jsonObject.toString());
                        if(gBDSMCommands.timeout.gProfile.jsonObject.has(gBDSMCommands.timeout.keyChannel)){
                            long guildchannelID=gBDSMCommands.timeout.gProfile.jsonObject.getLong(gBDSMCommands.timeout.keyChannel);
                            logger.info(fName+".guildchannelID="+guildchannelID);
                            if(lsChannelHelper.lsHasTextChannelById(dataSetDiscord.gGuild,guildchannelID)){
                                logger.info(fName+"exists1");
                                if(dataSetDiscord.gTextChannel.getIdLong()!=guildchannelID){
                                    logger.info(fName+"not a match>return true");
                                    return true;
                                }
                            }
                        }
                    }
                    if(timeout.has(iRestraints.nChannel)){
                        long channelID=timeout.getLong(iRestraints.nChannel);
                        logger.info(fName+".channelID="+channelID);
                        if(lsChannelHelper.lsHasTextChannelById(dataSetDiscord.gGuild,channelID)){
                            logger.info(fName+"exists2");
                            if(dataSetDiscord.gTextChannel.getIdLong()!=channelID){
                                logger.info(fName+"not a match>return true");
                                return true;
                            }
                        }
                    }
                    String text="";
                    if(timeout.getInt(iRestraints.nTimeOutMode)==1){
                        logger.info(fName + ".mode timer");
                        text="*I'm still in timout! Remaining duration "+lsUsefullFunctions.displayDuration(iRestraints.sIsTimeOutGetRemaningDuration(gUserProfile))+".*";
                    }
                    else if(timeout.getInt(iRestraints.nTimeOutMode)==2){
                        logger.info(fName + ".mode writting");
                        String sentence=timeout.getJSONObject(iRestraints.nTimeoutModeWritting).getString(iRestraints.nTimeOut2Sentence);
                        logger.info(fName + ".sentence="+sentence);
                        if(dataSetDiscord.inputMessageContent.equals(sentence)){
                            logger.info(fName + ".sentence found");
                            int count=timeout.getJSONObject(iRestraints.nTimeoutModeWritting).getInt(iRestraints.nTimeOut2Count);
                            logger.info(fName + ".count="+count);
                            int done=timeout.getJSONObject(iRestraints.nTimeoutModeWritting).getInt(iRestraints.nTimeOut2Done);
                            logger.info(fName + ".done="+done);
                            gUserProfile.jsonObject.getJSONObject(iRestraints.nTimeOut).getJSONObject(iRestraints.nTimeoutModeWritting).put(iRestraints.nTimeOut2Done,done+1);
                            gUserProfile.saveProfile(table);
                            return false;
                        }
                        text="*I'm still in timout. Remaining count "+iRestraints.sIsTimeOutGetRemaningCount(gUserProfile)+".*";
                    }
                    extraMessages.put(text);
                    logger.info(fName+".return true");
                    return true;
                }
                logger.info(fName+".default>false");
                return false;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        String gagStatus="";
        private boolean isChannelLimitedDue2Cuffing(){
            String fName="[isChannelLimitedDue2Cuffing]";
            logger.info(fName );
            try {

                logger.info(fName + "legcuffs="+LEGCUFFS.toString());
                if(LEGCUFFS.has(iRestraints.nChannelRestrainEnabled)&&LEGCUFFS.has(iRestraints.nChannelRestrainId)&&!LEGCUFFS.isNull(iRestraints.nChannelRestrainEnabled)&&!LEGCUFFS.isNull(iRestraints.nChannelRestrainId)){
                    boolean isenabled=LEGCUFFS.getBoolean(iRestraints.nChannelRestrainEnabled);
                    logger.info(fName + "isenabled="+isenabled);
                    if(!isenabled){
                        logger.info(fName + "not enabled");
                        return false;
                    }
                    String cuffsLevel=LEGCUFFS.getString(iRestraints.nLevel);
                    logger.info(fName + "cuffsLevel="+cuffsLevel);
                    boolean cuffsOn=LEGCUFFS.getBoolean(iRestraints.nOn);
                    logger.info(fName + "cuffsOn="+cuffsOn);
                    if(!cuffsOn){
                        logger.info(fName + "legcuffs not on");
                        return false;
                    }
                    try {
                        boolean flagExceptionGuild=LEGCUFFS.getBoolean(iRestraints.nChannelRestrainException2UseGuildGag);
                        logger.info(fName + "flagExceptionGuild="+flagExceptionGuild);
                        boolean flagExceptionUser=LEGCUFFS.getBoolean(iRestraints.nChannelRestrainException2UseUserGag);
                        logger.info(fName + "flagExceptionUser="+flagExceptionUser);
                        if(flagExceptionGuild){
                            if (!isAllowedUser()) {
                                logger.info(fName + "not allowed by guild.gag.user");
                                return false;
                            }
                            if (!isAllowedHere()) {
                                logger.info(fName + "not allowed by guild.gag.channel");
                                return false;
                            }
                        }
                        if(flagExceptionUser){
                            logger.info(fName + "GAG="+GAG.toString());
                            boolean isExceptionEnabled=GAG.getBoolean(iRestraints.nException);
                            JSONObject exceptionList=GAG.getJSONObject(iRestraints.nExceptionList);
                            if(isExceptionEnabled){
                                logger.info(fName + ".exception enabled");
                                logger.info(fName + ".exceptionList="+exceptionList.toString());
                                String id= dataSetDiscord.gTextChannel.getId();
                                logger.info(fName + ".textChannel="+id);
                                if(exceptionList.has(id)){
                                    logger.info(fName + "not allowed by user.gag.channel");
                                    return false;
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    if(cuffsLevel.equalsIgnoreCase(CUFFSLEGSLEVELS.FauxTaut.getName())||cuffsLevel.equalsIgnoreCase(iRestraints.nNone)){
                        logger.info(fName + "legcuffs set to faux or none");
                        return false;
                    }
                    long channelID=LEGCUFFS.getLong(iRestraints.nChannelRestrainId);
                    logger.info(fName + "channelID="+channelID);
                    if(channelID==0L){
                        logger.info(fName + "no channelID>set new one");
                        gUserProfile.jsonObject.getJSONObject(iRestraints.nLegsCuffs).put(iRestraints.nChannelRestrainId, dataSetDiscord.gTextChannel.getId());
                        saveProfile();
                    }
                    TextChannel textChannel= lsChannelHelper.lsGetTextChannelById(dataSetDiscord.gGuild,channelID);
                    if(textChannel==null){
                        logger.info(fName + "channelID is invalid>set new one");
                        gUserProfile.jsonObject.getJSONObject(iRestraints.nLegsCuffs).put(iRestraints.nChannelRestrainId, dataSetDiscord.gTextChannel.getId());
                        textChannel= dataSetDiscord.gTextChannel;
                        saveProfile();
                    }
                    if(textChannel== dataSetDiscord.gTextChannel){
                        logger.info(fName + "same channel>allow");
                        return false;
                    }
                    logger.info(fName + "not same channel>don't allow");
                    extraMessages.put("*My legs are cuffed, restrained in "+textChannel.getAsMention()+".*");
                    return true;
                }
                logger.info(fName+".default>false");
                return false;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean isPostLimitedDue2Cuffing(){
            String fName="[isPostLimitedDue2Cuffing]";
            logger.info(fName );
            try {

                logger.info(fName + "armcuffs="+ARMCUFFS.toString());
                if(ARMCUFFS.has(iRestraints.nPostRestrictEnabled)&&ARMCUFFS.has(iRestraints.nPostDelay)&&ARMCUFFS.has(iRestraints.nLastPostTimeStamp)&&!ARMCUFFS.isNull(iRestraints.nPostRestrictEnabled)&&!ARMCUFFS.isNull(iRestraints.nPostDelay)&&!ARMCUFFS.isNull(iRestraints.nLastPostTimeStamp)){
                    boolean isenabled=ARMCUFFS.getBoolean(iRestraints.nPostRestrictEnabled);
                    logger.info(fName + "isenabled="+isenabled);
                    if(!isenabled){
                        logger.info(fName + "not enabled");
                        return false;
                    }
                    try {
                        boolean flagExceptionGuild=ARMCUFFS.getBoolean(iRestraints.nPostRestrictionException2UseGuildGag);
                        logger.info(fName + "flagExceptionGuild="+flagExceptionGuild);
                        boolean flagExceptionUser=ARMCUFFS.getBoolean(iRestraints.nPostRestrictionException2UseUserGag);
                        logger.info(fName + "flagExceptionUser="+flagExceptionUser);
                        if(flagExceptionGuild){
                            if (!isAllowedUser()) {
                                logger.info(fName + "not allowed by guild.gag.user");
                                return false;
                            }
                            if (!isAllowedHere()) {
                                logger.info(fName + "not allowed by guild.gag.channel");
                                return false;
                            }
                        }
                        if(flagExceptionUser){
                            logger.info(fName + "GAG="+GAG.toString());
                            boolean isExceptionEnabled=GAG.getBoolean(iRestraints.nException);
                            JSONObject exceptionList=GAG.getJSONObject(iRestraints.nExceptionList);
                            if(isExceptionEnabled){
                                logger.info(fName + ".exception enabled");
                                logger.info(fName + ".exceptionList="+exceptionList.toString());
                                String id= dataSetDiscord.gTextChannel.getId();
                                logger.info(fName + ".textChannel="+id);
                                if(exceptionList.has(id)){
                                    logger.info(fName + "not allowed by user.gag.channel");
                                    return false;
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    String cuffsLevel=ARMCUFFS.getString(iRestraints.nLevel);
                    logger.info(fName + "cuffsLevel="+cuffsLevel);
                    boolean cuffsOn=ARMCUFFS.getBoolean(iRestraints.nOn);
                    logger.info(fName + "cuffsOn="+cuffsOn);

                    JSONObject straitjacket = gUserProfile.jsonObject.getJSONObject(iRestraints.nStraitjacket);
                    logger.info(fName + "straitjacket="+straitjacket.toString());
                    boolean straitjacketsOn=straitjacket.getBoolean(iRestraints.nOn);
                    logger.info(fName + "straitjacketOn="+straitjacketsOn);
                    boolean straitjacketsStrapArms=straitjacket.getBoolean(iRestraints.nStrapArms);
                    logger.info(fName + "straitjacketsStrapArms="+straitjacketsStrapArms);

                    JSONObject mitts = gUserProfile.jsonObject.getJSONObject(iRestraints.nMitts);
                    logger.info(fName + "mitts="+mitts.toString());
                    String mittsLevel=mitts.getString(iRestraints.nLevel);
                    logger.info(fName + "mittsLevel="+mittsLevel);
                    boolean mittsOn=mitts.getBoolean(iRestraints.nOn);
                    logger.info(fName + "mittsOn="+mittsOn);

                    long postSlowdownOption=ARMCUFFS.getLong(iRestraints.nPostDelay);
                    logger.info(fName+".postSlowdownOption="+postSlowdownOption);
                    if(postSlowdownOption!=9&&!straitjacketsOn&&!mittsOn&&!cuffsOn){
                        logger.info(fName + "no hand restrain is on");
                        return false;
                    }
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    logger.info(fName+".timestamp="+timestamp.getTime());

                    long lastPostTimeStamp=ARMCUFFS.getLong(iRestraints.nLastPostTimeStamp);
                    logger.info(fName+".lastPostTimeStamp="+lastPostTimeStamp);
                    if(lastPostTimeStamp<=0){
                        logger.info(fName+".no lastPostTimeStamp entry>false");
                        gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).put(iRestraints.nLastPostTimeStamp,timestamp.getTime());
                        saveProfile();
                        return false;
                    }
                    long diff=timestamp.getTime()-lastPostTimeStamp;
                    logger.info(fName+".diff="+diff);
                    long postSlowdownWait=0L;
                    if(postSlowdownOption==1)postSlowdownWait=iRestraints.vPostDelay_1;
                    else if(postSlowdownOption==2)postSlowdownWait=iRestraints.vPostDelay_2;
                    else if(postSlowdownOption==3)postSlowdownWait=iRestraints.vPostDelay_3;
                    else if(postSlowdownOption==4)postSlowdownWait=iRestraints.vPostDelay_4;
                    else if(postSlowdownOption==5)postSlowdownWait=iRestraints.vPostDelay_5;
                    else if(postSlowdownOption==9) {
                        logger.info(fName+".postslowdown depends on restraint");


                        logger.info(fName + "legcuffs="+LEGCUFFS.toString());
                        String legcuffsLevel=LEGCUFFS.getString(iRestraints.nLevel);
                        logger.info(fName + "legcuffsLevel="+legcuffsLevel);
                        boolean legcuffsOn=LEGCUFFS.getBoolean(iRestraints.nOn);
                        logger.info(fName + "legcuffsOn="+legcuffsOn);


                        logger.info(fName + "gag="+GAG.toString());
                        String gagLevel=GAG.getString(iRestraints.nLevel);
                        logger.info(fName + "gagLevel="+gagLevel);
                        boolean gagOn=GAG.getBoolean(iRestraints.nOn);
                        logger.info(fName + "gagOn="+gagOn);

                        JSONObject hood = gUserProfile.jsonObject.getJSONObject(iRestraints.nHood);
                        logger.info(fName + "hood="+hood.toString());
                        String hoodLevel=hood.getString(iRestraints.nLevel);
                        logger.info(fName + "hoodLevel="+hoodLevel);
                        boolean hoodOn=hood.getBoolean(iRestraints.nOn);
                        logger.info(fName + "hoodOn="+hoodOn);

                        JSONObject blindfold = gUserProfile.jsonObject.getJSONObject(iRestraints.nBlindfold);
                        logger.info(fName + "blindfold="+blindfold.toString());
                        String blindfoldLevel=blindfold.getString(iRestraints.nLevel);
                        logger.info(fName + "blindfoldLevel="+blindfoldLevel);
                        boolean blindfoldOn=blindfold.getBoolean(iRestraints.nOn);
                        logger.info(fName + "blindfoldOn="+blindfoldOn);

                        if(straitjacketsOn&&straitjacketsStrapArms){
                            postSlowdownWait+=60000;
                            logger.info(fName + "straitjacketsStrapArms +60s");
                        }
                        if(straitjacketsOn){
                            postSlowdownWait+=15000;
                            logger.info(fName + "straitjacketOn +15s");
                        }
                        else if(mittsOn){
                            postSlowdownWait+=45000;
                            logger.info(fName + "mitts +15s");
                        }
                        if(cuffsOn){
                            if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.Armbinder.getName())){logger.info(fName + "cuffs +60s");postSlowdownWait+=60000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTight.getName())){logger.info(fName + "cuffs +60s");postSlowdownWait+=60000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.BehindTBelt.getName())){logger.info(fName + "cuffs +60s");postSlowdownWait+=60000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.ReversePrayer.getName())){logger.info(fName + "cuffs +60s");postSlowdownWait+=60000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.Behind.getName())){logger.info(fName + "cuffs +45s");postSlowdownWait+=45000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.BehindBelt.getName())){logger.info(fName + "cuffs +45s");postSlowdownWait+=45000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.Elbows.getName())){logger.info(fName + "cuffs +45s");postSlowdownWait+=45000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.SidesTight.getName())){logger.info(fName + "cuffs +45s");postSlowdownWait+=45000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.Sides.getName())){logger.info(fName + "cuffs +30s");postSlowdownWait+=30000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.FrontBelt.getName())){logger.info(fName + "cuffs +15s");postSlowdownWait+=15000;}
                            else if(cuffsLevel.equalsIgnoreCase(CUFFSARMSLEVELS.Front.getName())){logger.info(fName + "cuffs +5s");postSlowdownWait+=5000;}

                        }
                        if(gagOn){
                            postSlowdownWait+=5000;logger.info(fName + "gag +5s");
                        }
                        if(hoodOn){
                            postSlowdownWait+=5000;logger.info(fName + "hood +5s");
                            if(hoodLevel.equalsIgnoreCase(HOODLEVELS.Drone.getName())){postSlowdownWait+=45000;logger.info(fName + "hood_level +45s");}
                            else if(hoodLevel.equalsIgnoreCase(HOODLEVELS.Bondage.getName())){postSlowdownWait+=30000;logger.info(fName + "hood_level +30s");}
                            else if(hoodLevel.equalsIgnoreCase(HOODLEVELS.Puppy.getName())){postSlowdownWait+=15000;logger.info(fName + "hood_level +15s");}
                            else if(hoodLevel.equalsIgnoreCase(HOODLEVELS.Kitty.getName())){postSlowdownWait+=15000;logger.info(fName + "hood_level +15s");}
                            else if(hoodLevel.equalsIgnoreCase(HOODLEVELS.Cow.getName())){postSlowdownWait+=15000;logger.info(fName + "hood_level +15s");}
                            else if(hoodLevel.equalsIgnoreCase(HOODLEVELS.Pony.getName())){postSlowdownWait+=15000;logger.info(fName + "hood_level +15s");}
                        }
                        if(blindfoldOn){
                            postSlowdownWait+=15000;logger.info(fName + "blindfold +15s");
                        }
                    }
                    else if(postSlowdownOption>=5000)postSlowdownWait=postSlowdownOption;
                    logger.info(fName+".postSlowdownWait="+postSlowdownWait);
                    if(postSlowdownWait<5000){
                        logger.info(fName+".ignore, postSlowdownWait is bellow 5 seconds");
                        return  false;
                    }
                    if(diff>postSlowdownWait+1000){
                        logger.info(fName+".ignore, diff is beyong the limit");
                        gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).put(iRestraints.nLastPostTimeStamp,timestamp.getTime());
                        saveProfile();
                        return  false;
                    }
                    logger.info(fName+".its in the limit");
                    gUserProfile.jsonObject.getJSONObject(iRestraints.nArmsCuffs).put(iRestraints.nLastPostTimeStamp,timestamp.getTime());
                    saveProfile();
                    String text="";
                    if(straitjacketsOn&&straitjacketsStrapArms){
                        text="It seams I'm is having difficulties writing while hugging themselves.";
                    }
                    else if(straitjacketsOn){
                        text="Its seams I'm is having difficulties writing while hands in a straitjacket sleeves that acts as mitts.";
                    }
                    else if(mittsOn){
                        text="Its seams I'm is having difficulties writing while hands in mitts.";
                    }
                    else if(cuffsOn){
                        text="Its seams I'm is having difficulties writing while hands all cuffed up.";
                    }
                    text+="\nI should wait ";
                    if(postSlowdownOption==1){
                        text+="5";
                    }
                    else if(postSlowdownOption==2){
                        text+="15";
                    }
                    else if(postSlowdownOption==3){
                        text+="30";
                    }
                    else if(postSlowdownWait>=5000){
                        text+=""+postSlowdownWait/1000;
                    }
                    text+=" seconds before posting again.";
                    extraMessages.put("*"+text+"*");
                    logger.info(fName+".return true");
                    return true;
                }
                logger.info(fName+".default>false");
                return false;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        boolean wasShocked=false;
        private boolean muffleSpeach(){
            String fName="[muffleSpeach]";
            logger.info(fName );
            try {
                if (!vEnabled) {
                    logger.info(fName + "is disabled");
                    return false;
                }
                if (!isAllowedUser() || !isAllowedHere()) {
                    logger.info(fName + "not allowed");
                    return false;
                }
                String messageContent= dataSetDiscord.inputMessageContent;
                if (!gUserProfile.jsonObject.has(iRestraints.nGag) || gUserProfile.jsonObject.isNull(iRestraints.nGag)) {
                    logger.info(fName + "gag key missing"); return false;
                }
                logger.info(fName + "GAG="+GAG.toString());
                if (!GAG.has(iRestraints.nOn)) {
                    logger.info(fName + "on key missing");
                    return false;
                }
                if (!GAG.has(iRestraints.nLevel)) {
                    logger.info(fName + "gagLevel key missing");
                    return false;
                }
                boolean on = GAG.getBoolean(iRestraints.nOn);
                String gagLevel =GAG.getString(iRestraints.nLevel);
                logger.info(fName + ".on=" + on);
                logger.info(fName + ".gagLevel=" + gagLevel);
                if (!on) {
                    logger.info(fName + ".is turned off");
                    return false;
                }
                if (gagLevel.equals("none")) {
                    logger.info(fName + ".severity is null");
                    return false;
                }
                if(messageContent.startsWith("https://")||messageContent.startsWith("http://")){
                    logger.warn(fName + ".starts with 'http(s)://'");
                    return false;
                }
                if(GAG.has(iRestraints.nException)){
                    try {
                        logger.info(fName + ".has exception field");
                        boolean isExceptionEnabled=GAG.getBoolean(iRestraints.nException);
                        JSONObject exceptionList=GAG.getJSONObject(iRestraints.nExceptionList);
                        if(isExceptionEnabled){
                            logger.info(fName + ".exception enabled");
                            logger.info(fName + ".exceptionList="+exceptionList);
                            String id= dataSetDiscord.gTextChannel.getId();
                            logger.info(fName + ".textChannel="+id);
                            if(exceptionList.has(id)){
                                logger.info(fName + ".exception found>ignore gag effect");
                                return false;
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(GAG.has(iRestraints.flagOnlyUseCustom)){
                    try {
                        logger.info(fName + ".has flagOnlyUseCustom");
                        onlyUseCustom_User =GAG.getBoolean(iRestraints.flagOnlyUseCustom);
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }

                try {
                    if(gBDSMCommands.muzzle.gProfile.jsonObject.has(iRestraints.flagEnableCustom)){
                        logger.info(fName + ".has flagEnableCustom");
                        enableCustomServer =gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(iRestraints.flagEnableCustom);
                    }
                    if( enableCustomServer){
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(iRestraints.flagOnlyUseCustom)){
                            logger.info(fName + ".has flagOnlyUseCustom");
                            onlyUseCustom_Server =gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(iRestraints.flagOnlyUseCustom);
                        }
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(iRestraints.flagOnlyGuildCustom)){
                            logger.info(fName + ".has flagOnlyGuildCustom");
                            onlyUseGuildCustom_Server =gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(iRestraints.flagOnlyGuildCustom);
                        }
                    }

                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName + ".onlyUseCustom_User="+ onlyUseCustom_User+", enableCustomServer="+enableCustomServer+", onlyUseCustom_Server="+onlyUseCustom_Server+", onlyUseGuildCustom_Server="+onlyUseGuildCustom_Server);
                try {
                    JSONObject suit=gUserProfile.jsonObject.getJSONObject(iRestraints.nSuit);
                    if(suit.has(iRestraints.nSuitFreeTalkAvailable)&&suit.getBoolean(iRestraints.nOn)){
                        String suitType=suit.getString(iRestraints.nSuitType);
                        logger.info(fName + ".suitType=" +suitType);
                        if(suitType.equals(iSuit.levelSuitSpecialToyAlpha)||suitType.equals(iSuit.levelSuitSpecialToyBeta)||suitType.equals(iSuit.levelSuitSpecialToyOmega)){
                            int freeTalkAvailable=suit.getInt(iRestraints.nSuitFreeTalkAvailable);
                            int freeTalkLimit=allowedFreeTalk4Toy(gUserProfile);
                            int score=suit.getInt(iRestraints.nSuitScore);int diff=0;
                            logger.info(fName + ".freeTalkAvailable=" +freeTalkAvailable+", freeTalkLimit="+freeTalkLimit);
                            logger.info(fName + ".score=" +score);
                            if(-1<freeTalkAvailable)freeTalkAvailable--;
                            if(freeTalkAvailable<-1)freeTalkAvailable=-1;
                            JSONArray customToyGoodTalk=new JSONArray();
                            if(suit.has(iRestraints.keyCustomGoodTalk)&&!suit.isNull(iRestraints.keyCustomGoodTalk)){
                                customToyGoodTalk=suit.getJSONArray(iRestraints.keyCustomGoodTalk);
                            }
                            boolean isGood=isToyTalkGood(messageContent,customToyGoodTalk, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                            boolean isBad=isToyTalkBad(messageContent);
                            boolean isChastityOn=gUserProfile.jsonObject.getJSONObject(iRestraints.nChastity).getBoolean(iRestraints.nOn), isZapOn=gUserProfile.jsonObject.getJSONObject(iRestraints.nChastity).getBoolean(iRestraints.nShock);
                            logger.info(fName + ".isGood=" +isGood+", isBad="+isBad);
                            if(isGood){
                                freeTalkAvailable=freeTalkLimit;
                                score++;
                                gUserProfile.putFieldEntry(iRestraints.nSuit,iRestraints.nSuitScore,score);
                            }else
                            if(isBad){
                                if(suitType.equals(iSuit.levelSuitSpecialToyBeta)||(suitType.equals(iSuit.levelSuitSpecialToyOmega)&&(!isChastityOn||isZapOn))){
                                    score--;
                                    gUserProfile.putFieldEntry(iRestraints.nSuit,iRestraints.nSuitScore,score);
                                    gUserProfile.putFieldEntry(iRestraints.nSuit,iRestraints.nSuitFreeTalkAvailable,freeTalkAvailable);
                                    saveProfile();
                                    usedMyffleSpeach=true;
                                    extraMessages.put("*The suit prevented me from typing my message as i said a bad word/sentence*");
                                    return true;
                                }else
                                if(suitType.equals(iSuit.levelSuitSpecialToyOmega)){
                                    score--;
                                    gUserProfile.putFieldEntry(nSuit,nSuitScore,score);
                                    gUserProfile.putFieldEntry(nSuit,nSuitFreeTalkAvailable,freeTalkAvailable);
                                    saveProfile();
                                    usedMyffleSpeach=true;
                                    extraMessages.put("*The suit prevented me from typing my message as i said a bad word/sentence*");
                                    return true;
                                }
                            }
                            gUserProfile.putFieldEntry(iRestraints.nSuit,iRestraints.nSuitFreeTalkAvailable,freeTalkAvailable);
                            if(freeTalkAvailable<0){
                                //diff=freeTalkAvailable/2;
                                //if(diff<0)diff=diff*-1;
                                //if(diff==0)diff=1;
                                //score-=diff;
                                score--;
                                gUserProfile.putFieldEntry(iRestraints.nSuit,iRestraints.nSuitScore,score);
                                usedMyffleSpeach=true;
                                saveProfile();
                                extraMessages.put("*The suit prevented me from typing my message as i have no free speech left.*");
                                return true;
                            }else
                            if(freeTalkAvailable==1){
                                //saveProfile();JSONArray finalCustomToyGoodTalk = customToyGoodTalk;
                                //alteredMessages.add("*I've used up all my free speech*");

                            }else
                            if(freeTalkAvailable==0){
                                saveProfile();JSONArray finalCustomToyGoodTalk = customToyGoodTalk;
                                usedMyffleSpeach=true;
                                saveProfile();
                                extraMessages.put("*I've used up all my free speech*");
                                return true;
                            }
                            String messageL=messageContent.toLowerCase();
                            if(!isGood&&!messageL.startsWith("_toy")&&!messageL.startsWith("/metoy")&&!messageL.startsWith("toy")&&!messageL.startsWith("toy ")&&!messageL.startsWith("toy's ")&&!messageContent.toLowerCase().contains("_toy ")&&!messageL.contains(" toy ")&&!messageL.contains(" toy's ")&&!messageL.contains(" toy.")&&!messageL.contains(" toy?")&&!messageL.contains(" toy!")&&!messageL.contains(" toy's.")&&!messageL.contains(" toy's?")&&!messageL.contains(" toy's!")){
                                logger.warn("contain no toy");
                                if(suitType.equals(iSuit.levelSuitSpecialToyBeta)||(suitType.equals(iSuit.levelSuitSpecialToyOmega)&&(!isChastityOn||isZapOn))){
                                    logger.warn("contain no toy");
                                    score--;
                                    gUserProfile.putFieldEntry(nSuit,nSuitScore,score);
                                    saveProfile();
                                    usedMyffleSpeach=true;
                                    extraMessages.put("*The suit prevented me from typing my message as i have not said i'm a toy.*");
                                    return true;
                                }
                                if(suitType.equals(iSuit.levelSuitSpecialToyOmega)){
                                    logger.warn("contain no toy");
                                    score--;
                                    gUserProfile.putFieldEntry(nSuit,nSuitScore,score);
                                    saveProfile();
                                    usedMyffleSpeach=true;
                                    extraMessages.put("*The suit prevented me from typing my message as i have not said i'm a toy.*");
                                    return true;
                                }
                            }
                            saveProfile();
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName + ".onlyUseCustom="+ onlyUseCustom_User);
                String message2Post = stringManipulator(messageContent, gagLevel);
                lcWebHookBuild whh = new lcWebHookBuild();
                whh.doSafetyCleanwToken(dataSetDiscord.gTextChannel);
                JSONObject json = new JSONObject();

                message2Post=message2Post.replaceAll("!name", dataSetDiscord.gMember.getEffectiveName()).replaceAll("!slave", rSlaveRegistry.iGenerateSlaveNumber(gUserProfile.getUser()));

                logger.info(fName + ".delete message");
                usedMyffleSpeach=true;
                logger.info(fName + ".send webhook");
                try {
                    addProfile();
                    logger2.log(CrunchifyLog4jLevel.GAGUSED, "[plugin] member="+ dataSetDiscord.gUser.getName()+"("+ dataSetDiscord.gUser.getId()+"), channel="+ dataSetDiscord.gTextChannel.getName()+"("+ dataSetDiscord.gTextChannel.getId()+"), guild="+ dataSetDiscord.gGuild.getName()+"("+ dataSetDiscord.gGuild.getId()+"), level="+GAG.getString(nLevel)+" \n@message="+ dataSetDiscord.inputMessageContent);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                usedMyffleSpeach=true;
                JSONArray arrayOfMessage=stringSizeLimit(message2Post);
                for(int i=0;i<arrayOfMessage.length();i++){
                    messages2Send.put(arrayOfMessage.getString(i));
                }
                gGlobal.logGuildUsage(dataSetDiscord.gGuild,gGlobal.keyGagUsedCount);
                return true;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return false;
            }
        }

        JSONArray messages2Send =new JSONArray(), extraMessages =new JSONArray();
        boolean  usedMyffleSpeach=false;
        private String stringManipulator(String message,String severity){
            String fName="[stringManipulator]";
            logger.info(fName+"severity="+severity);
            logger.info(fName+"old="+message);
            StringBuilder newMessage= new StringBuilder();
            JSONArray jsonArray=new JSONArray(); JSONObject jsonObject=new JSONObject();
            StringBuilder tmpCut= new StringBuilder();
            if(message.contains("\"")){
                logger.info(fName+"mode=2");
                int count=0,detectIC=0;
                boolean isIgnored=true,isIC=false;
                while(message.length()>0&&count<20000){
                    count++;
                    jsonObject=new JSONObject();
                    String character= String.valueOf(message.charAt(0));
                    if(message.length()>1){
                        message=message.substring(1);
                        logger.info(fName+"cut to="+message);
                    }else{
                        message="";
                    }
                    if(character.equals("\"")){
                        if(detectIC>0){
                            detectIC=0;
                        }else{
                            detectIC=1;isIC=true;
                        }
                    }
                    logger.info(fName+"character="+character+" |isIgnored("+isIgnored+")IC("+detectIC+","+isIC+")");
                    if(isIgnored&&isIC){
                        if(tmpCut.length()>0){
                            logger.info(fName+"this is ignored="+tmpCut);
                            jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",true);
                            jsonArray.put(jsonObject);
                        }
                        tmpCut = new StringBuilder(character);isIgnored=false;
                    }else
                    if(!isIgnored&&detectIC==0){
                        tmpCut.append(character);logger.info(fName+"this is added="+tmpCut);
                        jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",false);
                        jsonArray.put(jsonObject);
                        tmpCut = new StringBuilder();isIgnored=true;isIC=false;
                    }else{
                        tmpCut.append(character);
                    }
                }
                if(tmpCut.length()>0){
                    logger.info(fName+"leftover add to ignore="+tmpCut);
                    jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",true);
                    jsonArray.put(jsonObject);
                }
            }else{
                logger.info(fName+"mode=1");
                int count=0,detectStar=0,detectLine=0,detectOOC=0;
                boolean isIgnored=false,isStar=false,isLine=false,isOOC=false;
                while(message.length()>0&&count<20000){
                    count++;
                    jsonObject=new JSONObject();
                    String character= String.valueOf(message.charAt(0));
                    if(message.length()>1){
                        message=message.substring(1);
                        logger.info(fName+"cut to="+message);
                    }else{
                        message="";
                    }
                    if(character.equals("*")){
                        if(detectStar>0){
                            detectStar=0;
                        }else{
                            detectStar=1;isStar=true;
                        }
                    }
                    if(character.equals("_")){
                        if(detectLine>0){
                            detectLine=0;
                        }else{
                            detectLine=1;isLine=true;
                        }
                    }
                    if(character.equals("(")&&!isDisabledOOC){
                        detectOOC++;isOOC=true;
                    }
                    if(detectOOC>0&&character.equals(")")&&!isDisabledOOC){
                        detectOOC--;
                    }
                    logger.info(fName+"character=`"+character+"` |isIgnored("+isIgnored+")OOC("+detectOOC+","+isOOC+")detectStar("+detectStar+","+isStar+")detectLine("+detectLine+","+isLine+")");
                    //if(isIgnored==false&&tmpCut.length()>0&&((isLine&&detectLine==1)||(isStar&&detectStar==1)||(isOOC&&detectOOC==1))){
                    if(!isIgnored &&(isLine||isStar||isOOC)){
                        if(tmpCut.length()>0){
                            logger.info(fName+"add to convert=`"+tmpCut+"`");
                            jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",false);
                            jsonArray.put(jsonObject);

                        }
                        tmpCut = new StringBuilder(character);isIgnored=true;
                    }else
                    if(isIgnored&&detectLine==0&&detectStar==0&&detectOOC==0){
                        tmpCut.append(character);logger.info(fName+"this is ignored=`"+tmpCut+"`");
                        jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",true);
                        jsonArray.put(jsonObject);
                        tmpCut = new StringBuilder();isIgnored=false;
                        isLine=false;isOOC=false;isStar=false;
                    }else{
                        logger.info(fName+"add to tmp `"+character+"` ->`"+tmpCut.toString()+"`");
                        tmpCut.append(character);
                    }
                }
                if(tmpCut.length()>0){
                    logger.info(fName+"leftover add to convert=`"+tmpCut+"`");
                    jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",false);
                    jsonArray.put(jsonObject);
                }
            }



            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                if(i>=maxcycle)break;
                jsonObject=jsonArray.getJSONObject(i);
                tmpCut = new StringBuilder(jsonObject.getString("value"));
                if(jsonObject.getBoolean("isIgnored")){
                    logger.info(fName+"text["+tmpCut+"]isIgnored");
                    newMessage.append(tmpCut);
                }else{
                    logger.info(fName+"text["+tmpCut+"]isProcessed");
                    shockCollar(tmpCut);
                    newMessage.append(stringReplacer(tmpCut.toString(), severity));
                }
            }
            logger.info(fName+"newMessage="+newMessage);
            return newMessage.toString();
        }
        private void shockCollar(StringBuilder message){
            String fName="[shockCollar]";
            logger.info(fName );
            try {
                logger.info(fName+"wasShocked="+wasShocked);
                if (wasShocked) {
                    logger.info(fName + "already shocked once");return;
                }
                if (!gUserProfile.jsonObject.has(iRestraints.nCollar)) {
                    logger.info(fName + "no collar entry");return;
                }
                JSONObject collar=gUserProfile.jsonObject.getJSONObject(iRestraints.nCollar);
                logger.info(fName + "collar="+collar.toString());
                if (!collar.has(iRestraints.nShockeEnabled)) {
                    logger.info(fName + "no collar shockenabled entry");return;
                }

                boolean isCollarOn=collar.getBoolean(iRestraints.nOn);
                boolean isShockOn=collar.getBoolean(iRestraints.nShockeEnabled);
                logger.info(fName + ".isCollarOn="+isCollarOn+", isShockOn="+isShockOn);
                if (!isCollarOn) {
                    logger.info(fName + "collar is not on");return;
                }
                if (!isShockOn) {
                    logger.info(fName + "shock is not enabled");return;
                }
                try {
                    boolean flagExceptionGuild=false;
                    if(collar.has(iRestraints.nAutoShockException4GuildGagException)){
                        flagExceptionGuild=collar.getBoolean(iRestraints.nAutoShockException4GuildGagException);
                    }
                    logger.info(fName + "flagExceptionGuild="+flagExceptionGuild);
                    boolean flagExceptionUser=false;
                    if(collar.has(iRestraints.nAutoShockException4UserGagException)){
                        flagExceptionUser=collar.getBoolean(iRestraints.nAutoShockException4UserGagException);
                    }
                    logger.info(fName + "flagExceptionUser="+flagExceptionUser);
                    if(flagExceptionGuild){
                        if (!isAllowedUser()) {
                            logger.info(fName + "not allowed by guild.gag.user");
                            return;
                        }
                        if (!isAllowedHere()) {
                            logger.info(fName + "not allowed by guild.gag.channel");
                            return;
                        }
                    }
                    if(flagExceptionUser){
                        logger.info(fName + "GAG="+GAG.toString());
                        boolean isExceptionEnabled=GAG.getBoolean(iRestraints.nException);
                        JSONObject exceptionList=GAG.getJSONObject(iRestraints.nExceptionList);
                        if(isExceptionEnabled){
                            logger.info(fName + ".exception enabled");
                            logger.info(fName + ".exceptionList="+exceptionList.toString());
                            String id= dataSetDiscord.gTextChannel.getId();
                            logger.info(fName + ".textChannel="+id);
                            if(exceptionList.has(id)){
                                logger.info(fName + "not allowed by user.gag.channel");
                                return;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                String source = message.toString().replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                List sourceList= lsStringUsefullFunctions.String2ListStringNoNull(source,"\\s+");
                shockCollar_User(collar,source,sourceList);
                if(!wasShocked){
                    if(!gBDSMCommands.collar.gProfile.jsonObject.getBoolean(iRestraints.fieldEnabled)){
                        logger.info(fName + ".guild shock is disabled");
                        return;
                    }
                    logger.info(fName + ".guild shock is enabled");
                    if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(iRestraints.fieldGuild).getBoolean(iRestraints.fieldEnabled)){
                        logger.info(fName + ".guild general shock is enabled");
                        shockCollar_Server(source,sourceList);
                    }else{
                        logger.info(fName + ".guild general shock is disabed");
                    }
                    if(!wasShocked){
                        logger.info(fName + ".check roles shock");
                        shockCollar_Role(source,sourceList);
                    }
                }
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        private void shockCollar_User(JSONObject collar,String source, List<String>sourceList){
            String fName="[shockCollar_User]";
            logger.info(fName );
            try {
                try {
                    JSONArray arrayBadwords=collar.getJSONArray(iRestraints.nBadWords);
                    logger.info(fName + "arrayBadwords.length()="+arrayBadwords.length()+", arrayBadwords="+arrayBadwords.toString());
                    for(int j=0;j<arrayBadwords.length();j++){
                        if(j>=maxcycle)break;
                        String match=arrayBadwords.getString(j).replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                        logger.info(fName + "match["+j+"]="+match);
                        //String []matchItems=match.split("\\s+");
                        List <String>matchList= lsStringUsefullFunctions.String2ListStringNoNull(match,"\\s+");
                        logger.info(fName + "matchList.size="+matchList.size());
                        logger.info(fName + "matchList="+matchList.toString());
                        if(matchList.size()>=2){
                            logger.info(fName + "check 1");
                            if(source.equalsIgnoreCase(match)){
                                logger.info(fName + ".do shock_1_a at word="+arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }
                            if(source.contains(match)){
                                logger.info(fName + ".do shock_1_b at word="+arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }
                            if(source.replaceAll("\\s","").contains(match.replaceAll("\\s",""))){
                                logger.info(fName + ".do shock_1_c at word="+arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }

                        }else{
                            logger.info(fName + "check 2");
                            if (sourceList.contains(match)) {
                                logger.info(fName + ".do bad_word found_2=" + arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    JSONArray arrayEnforcedWords=new JSONArray();
                    if(collar.has(iRestraints.nEnforcedWords)){
                        arrayEnforcedWords=collar.getJSONArray(iRestraints.nEnforcedWords);
                    }
                    logger.info(fName + "arrayEnforcedWords.length()="+arrayEnforcedWords.length()+", arrayEnforcedWords="+arrayEnforcedWords.toString());
                    if(!wasShocked&&!arrayEnforcedWords.isEmpty()){
                        boolean found=false;
                        for(int j=0;j<arrayEnforcedWords.length();j++){
                            if(j>=maxcycle)break;
                            String match=arrayEnforcedWords.getString(j).replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                            logger.info(fName + "match["+j+"]="+match);
                            List <String>matchList= lsStringUsefullFunctions.String2ListStringNoNull(match,"\\s+");
                            logger.info(fName + "matchList.size="+matchList.size());
                            if(matchList.size()>=2){
                                logger.info(fName + "check 1");
                                if(source.equalsIgnoreCase(match)){
                                    logger.info(fName + ".enforced word found_1a="+arrayEnforcedWords.getString(j));
                                    found=true;
                                    break;
                                }
                                if(source.contains(match)){
                                    logger.info(fName + ".enforced word found_1b="+arrayEnforcedWords.getString(j));
                                    found=true;
                                    break;
                                }
                                if(source.replaceAll("\\s","").contains(match.replaceAll("\\s",""))){
                                    logger.info(fName + ".enforced word found_1c="+arrayEnforcedWords.getString(j));
                                    found=true;
                                    break;
                                }
                            }else {
                                logger.info(fName + "check 2");
                                if (sourceList.contains(match)) {
                                    logger.info(fName + ".enforced word found_2=" + arrayEnforcedWords.getString(j));
                                    found = true;
                                    break;
                                }
                            }
                        }
                        logger.info(fName + ".found="+found);
                        if(!found){
                            shocked(2, "");
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }


            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        private void shockCollar_Server(String source, List<String>sourceList){
            String fName="[shockCollar_User]";
            logger.info(fName );
            try {
                try {
                    JSONArray arrayBadwords=gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(iRestraints.fieldGuild).getJSONArray(iRestraints.nBadWords);
                    logger.info(fName + "arrayBadwords.length()="+arrayBadwords.length()+", arrayBadwords="+arrayBadwords.toString());
                    for(int j=0;j<arrayBadwords.length();j++){
                        if(j>=maxcycle)break;
                        String match=arrayBadwords.getString(j).replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                        logger.info(fName + "match["+j+"]="+match);
                        //String []matchItems=match.split("\\s+");
                        List <String>matchList= lsStringUsefullFunctions.String2ListStringNoNull(match,"\\s+");
                        logger.info(fName + "matchList.size="+matchList.size());
                        logger.info(fName + "matchList="+matchList.toString());
                        if(matchList.size()>=2){
                            logger.info(fName + "check 1");
                            if(source.equalsIgnoreCase(match)){
                                logger.info(fName + ".do shock_1_a at word="+arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }
                            if(source.contains(match)){
                                logger.info(fName + ".do shock_1_b at word="+arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }
                            if(source.replaceAll("\\s","").contains(match.replaceAll("\\s",""))){
                                logger.info(fName + ".do shock_1_c at word="+arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }

                        }else{
                            logger.info(fName + "check 2");
                            if (sourceList.contains(match)) {
                                logger.info(fName + ".do bad_word found_2=" + arrayBadwords.getString(j));
                                shocked(1, arrayBadwords.getString(j));
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    JSONArray arrayEnforcedWords=new JSONArray();
                    if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(iRestraints.fieldGuild).has(iRestraints.nEnforcedWords)){
                        arrayEnforcedWords=gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(iRestraints.fieldGuild).getJSONArray(iRestraints.nEnforcedWords);
                    }
                    logger.info(fName + "arrayEnforcedWords.length()="+arrayEnforcedWords.length()+", arrayEnforcedWords="+arrayEnforcedWords.toString());
                    if(!wasShocked&&!arrayEnforcedWords.isEmpty()){
                        boolean found=false;
                        for(int j=0;j<arrayEnforcedWords.length();j++){
                            if(j>=maxcycle)break;
                            String match=arrayEnforcedWords.getString(j).replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                            logger.info(fName + "match["+j+"]="+match);
                            List <String>matchList= lsStringUsefullFunctions.String2ListStringNoNull(match,"\\s+");
                            logger.info(fName + "matchList.size="+matchList.size());
                            if(matchList.size()>=2){
                                logger.info(fName + "check 1");
                                if(source.equalsIgnoreCase(match)){
                                    logger.info(fName + ".enforced word found_1a="+arrayEnforcedWords.getString(j));
                                    found=true;
                                    break;
                                }
                                if(source.contains(match)){
                                    logger.info(fName + ".enforced word found_1b="+arrayEnforcedWords.getString(j));
                                    found=true;
                                    break;
                                }
                                if(source.replaceAll("\\s","").contains(match.replaceAll("\\s",""))){
                                    logger.info(fName + ".enforced word found_1c="+arrayEnforcedWords.getString(j));
                                    found=true;
                                    break;
                                }
                            }else {
                                logger.info(fName + "check 2");
                                if (sourceList.contains(match)) {
                                    logger.info(fName + ".enforced word found_2=" + arrayEnforcedWords.getString(j));
                                    found = true;
                                    break;
                                }
                            }
                        }
                        logger.info(fName + ".found="+found);
                        if(!found){
                            shocked(2, "");
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }


            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        private void shockCollar_Role(String source, List<String>sourceList){
            String fName="[shockCollar_User]";
            logger.info(fName );
            try {
                if(!gBDSMCommands.collar.gProfile.jsonObject.has(gBDSMCommands.collar.keyRoles)||gBDSMCommands.collar.gProfile.jsonObject.isNull(gBDSMCommands.collar.keyRoles)){
                    logger.info(fName + ".no role level words");
                }else if(!gBDSMCommands.collar.gProfile.jsonObject.getBoolean(iRestraints.nEnabled)){
                    logger.info(fName + ".role level words not enabled");

                }else{
                    List<Role>memberRoles= dataSetDiscord.gMember.getRoles();
                    if(memberRoles.isEmpty()){
                        logger.info(fName + ".member has no role");
                    }else{
                        logger.info(fName + ".memberRoles.size="+memberRoles.size());
                        logger.info(fName + ".memberRoles="+memberRoles.toString());
                        for(int i=0;i<memberRoles.size();i++){
                            Role role=memberRoles.get(i);
                            logger.info(fName + ".role["+i+"]="+role.getName()+" ("+role.getId()+")");
                            if(gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).has(role.getId())){
                                JSONObject jsonObject=gBDSMCommands.collar.gProfile.jsonObject.getJSONObject(gBDSMCommands.collar.keyRoles).getJSONObject(role.getId());
                                logger.info(fName + ".json="+jsonObject.toString());
                                if(jsonObject.getBoolean(iRestraints.nEnabled)){
                                    try {
                                        JSONArray arrayBadwords=jsonObject.getJSONArray(iRestraints.nBadWords);
                                        logger.info(fName + "arrayBadwords.length()="+arrayBadwords.length()+", arrayBadwords="+arrayBadwords.toString());
                                        for(int j=0;j<arrayBadwords.length();j++){
                                            if(j>=maxcycle)break;
                                            String match=arrayBadwords.getString(j).replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                                            logger.info(fName + "match["+j+"]="+match);
                                            //String []matchItems=match.split("\\s+");
                                            List <String>matchList= lsStringUsefullFunctions.String2ListStringNoNull(match,"\\s+");
                                            logger.info(fName + "matchList.size="+matchList.size());
                                            if(matchList.size()>=2){
                                                logger.info(fName + "check 1");
                                                if(source.equalsIgnoreCase(match)){
                                                    logger.info(fName + ".do shock_1_a at word="+arrayBadwords.getString(j));
                                                    shocked(3, arrayBadwords.getString(j));
                                                    break;
                                                }
                                                if(source.contains(match)){
                                                    logger.info(fName + ".do shock_1_b at word="+arrayBadwords.getString(j));
                                                    shocked(1, arrayBadwords.getString(j));
                                                    break;
                                                }
                                                if(source.replaceAll("\\s","").contains(match.replaceAll("\\s",""))){
                                                    logger.info(fName + ".do shock_1_c at word="+arrayBadwords.getString(j));
                                                    shocked(3, arrayBadwords.getString(j));
                                                    break;
                                                }

                                            }else{
                                                logger.info(fName + "check 2");
                                                if (sourceList.contains(match)) {
                                                    logger.info(fName + ".do bad_word found_2=" + arrayBadwords.getString(j));
                                                    shocked(3, arrayBadwords.getString(j));
                                                    break;
                                                }
                                            }
                                        }
                                    }catch (Exception e){
                                        logger.error(fName + ".exception=" + e);
                                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                    try {
                                        JSONArray arrayEnforcedWords=jsonObject.getJSONArray(iRestraints.nEnforcedWords);
                                        logger.info(fName + "arrayEnforcedWords.length()="+arrayEnforcedWords.length()+", arrayEnforcedWords="+arrayEnforcedWords.toString());
                                        if(!wasShocked&&!arrayEnforcedWords.isEmpty()){
                                            boolean found=false;
                                            for(int j=0;j<arrayEnforcedWords.length();j++){
                                                if(j>=maxcycle)break;
                                                String match=arrayEnforcedWords.getString(j).replaceAll("'s","").replaceAll("`s","").replaceAll("'t","").replaceAll("`t","").replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                                                logger.info(fName + "match["+j+"]="+match);
                                                List <String>matchList= lsStringUsefullFunctions.String2ListStringNoNull(match,"\\s+");
                                                logger.info(fName + "matchList.size="+matchList.size());
                                                if(matchList.size()>=2){
                                                    logger.info(fName + "check 1");
                                                    if(source.equalsIgnoreCase(match)){
                                                        logger.info(fName + ".enforced word found_1a="+arrayEnforcedWords.getString(j));
                                                        found=true;
                                                        break;
                                                    }
                                                    if(source.contains(match)){
                                                        logger.info(fName + ".enforced word found_1b="+arrayEnforcedWords.getString(j));
                                                        found=true;
                                                        break;
                                                    }
                                                    if(source.replaceAll("\\s","").contains(match.replaceAll("\\s",""))){
                                                        logger.info(fName + ".enforced word found_1c="+arrayEnforcedWords.getString(j));
                                                        found=true;
                                                        break;
                                                    }
                                                }else {
                                                    logger.info(fName + "check 2");
                                                    if (sourceList.contains(match)) {
                                                        logger.info(fName + ".enforced word found_2=" + arrayEnforcedWords.getString(j));
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            logger.info(fName + ".found="+found);
                                            if(!found){
                                                shocked(4, "");
                                            }
                                        }
                                    }catch (Exception e){
                                        logger.error(fName + ".exception=" + e);
                                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                                    }
                                }else{
                                    logger.info(fName + ".not enabled");
                                }
                            }
                        }
                    }
                }
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        int valueShockInt=-1;String valueShockWord="";
        private void shocked(int mode, String word){
            String fName="[shockCollar]";
            logger.info(fName );
            try {
                wasShocked=true;
                valueShockInt=mode;valueShockWord=word;
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        private void shockedPost(int mode, String word){
            String fName="[shockCollar]";
            logger.info(fName );
            try {
                logger.info(fName + "mode="+mode);
                if(mode==1){
                    if(word==null||word.isBlank()){
                        logger.info(fName + ".do shock for bad word");
                        extraMessages.put("*I gets shocked for saying an bad word.*");
                    }else{
                        logger.info(fName + ".do shock at bad word="+word);
                        extraMessages.put("*I gets shocked for saying the bad word `"+word+"`.*");
                    }
                }else
                if(mode==3){
                    if(word==null||word.isBlank()){
                        logger.info(fName + ".do shock for bad word by role");
                        extraMessages.put("*I gets shocked for saying an bad word.*");
                    }else{
                        logger.info(fName + ".do shock at bad word="+word+" by role");
                        extraMessages.put("*I gets shocked for saying the bad word `"+word+"`.*");
                    }
                }else
                if(mode==4){
                    logger.info(fName + ".do shock as missign enforced word by role");
                    extraMessages.put("*I gets shocked for forgetting to say an enforced words.*");
                }else
                if(mode==2){
                    logger.info(fName + ".do shock as missign enforced word");
                    extraMessages.put("*I gets shocked for forgetting to say an enforced words.*");
                }
                else{
                    logger.info(fName + ".silence shock");
                }

            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        private void stringSpliter(String message){
            String fName="[stringSpliter]";
            logger.info(fName+"old="+message);
            StringBuilder newMessage= new StringBuilder();
            JSONArray jsonArray=new JSONArray(); JSONObject jsonObject=new JSONObject();
            StringBuilder tmpCut= new StringBuilder();
            if(message.contains("\"")){
                logger.info(fName+"mode=2");
                int count=0,detectIC=0;
                boolean isIgnored=true,isIC=false;
                while(message.length()>0&&count<20000){
                    count++;
                    jsonObject=new JSONObject();
                    String character= String.valueOf(message.charAt(0));
                    if(message.length()>1){
                        message=message.substring(1);
                        logger.info(fName+"cut to="+message);
                    }else{
                        message="";
                    }
                    if(character.equals("\"")){
                        if(detectIC>0){
                            detectIC=0;
                        }else{
                            detectIC=1;isIC=true;
                        }
                    }
                    logger.info(fName+"character="+character+" |isIgnored("+isIgnored+")IC("+detectIC+","+isIC+")");
                    if(isIgnored&&isIC){
                        if(tmpCut.length()>0){
                            logger.info(fName+"this is ignored="+tmpCut);
                            jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",true);
                            jsonArray.put(jsonObject);
                        }
                        tmpCut = new StringBuilder(character);isIgnored=false;
                    }else
                    if(!isIgnored&&detectIC==0){
                        tmpCut.append(character);logger.info(fName+"this is added="+tmpCut);
                        jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",false);
                        jsonArray.put(jsonObject);
                        tmpCut = new StringBuilder();isIgnored=true;isIC=false;
                    }else{
                        tmpCut.append(character);
                    }
                }
                if(tmpCut.length()>0){
                    logger.info(fName+"leftover add to ignore="+tmpCut);
                    jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",true);
                    jsonArray.put(jsonObject);
                }
            }else{
                logger.info(fName+"mode=1");
                int count=0,detectStar=0,detectLine=0,detectOOC=0;
                boolean isIgnored=false,isStar=false,isLine=false,isOOC=false;
                while(message.length()>0&&count<20000){
                    count++;
                    jsonObject=new JSONObject();
                    String character= String.valueOf(message.charAt(0));
                    if(message.length()>1){
                        message=message.substring(1);
                        logger.info(fName+"cut to="+message);
                    }else{
                        message="";
                    }
                    if(character.equals("*")){
                        if(detectStar>0){
                            detectStar=0;
                        }else{
                            detectStar=1;isStar=true;
                        }
                    }
                    if(character.equals("_")){
                        if(detectLine>0){
                            detectLine=0;
                        }else{
                            detectLine=1;isLine=true;
                        }
                    }
                    if(character.equals("(")&&!isDisabledOOC){
                        detectOOC++;isOOC=true;
                    }
                    if(detectOOC>0&&character.equals(")")&&!isDisabledOOC){
                        detectOOC--;
                    }
                    logger.info(fName+"character="+character+" |isIgnored("+isIgnored+")OOC("+detectOOC+","+isOOC+")detectStar("+detectStar+","+isStar+")detectLine("+detectLine+","+isLine+")");
                    if(!isIgnored &&(isLine||isStar||isOOC)){
                        if(tmpCut.length()>0){
                            logger.info(fName+"add to convert="+tmpCut);
                            jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",false);
                            jsonArray.put(jsonObject);

                        }
                        tmpCut = new StringBuilder(character);isIgnored=true;
                    }else
                    if(isIgnored&&detectLine==0&&detectStar==0&&detectOOC==0){
                        tmpCut.append(character);logger.info(fName+"this is ignored="+tmpCut);
                        jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",true);
                        jsonArray.put(jsonObject);
                        tmpCut = new StringBuilder();isIgnored=false;
                        isLine=false;isOOC=false;isStar=false;
                    }else{
                        tmpCut.append(character);
                    }
                }
                if(tmpCut.length()>0){
                    logger.info(fName+"leftover add to convert="+tmpCut);
                    jsonObject.put("value", tmpCut.toString());jsonObject.put("isIgnored",false);
                    jsonArray.put(jsonObject);
                }
            }



            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                if(i>=maxcycle)break;
                jsonObject=jsonArray.getJSONObject(i);
                tmpCut = new StringBuilder(jsonObject.getString("value"));
                if(jsonObject.getBoolean("isIgnored")){
                    logger.info(fName+"text["+tmpCut+"]isIgnored");
                }else{
                    logger.info(fName+"text["+tmpCut+"]isProcessed");
                    shockCollar(tmpCut);
                }
            }
            logger.info(fName+"done");

        }
        private JSONArray getCustomTextArray(String key){
            String fName="[getCustomTextArray]";
            logger.info(fName);
            try{
                JSONArray array=new JSONArray();
                try {
                    if(enableCustomServer){
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(iRestraints.nCustomGagText)&&!gBDSMCommands.muzzle.gProfile.jsonObject.isNull(iRestraints.nCustomGagText)&&!gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).isEmpty())
                            if( gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).has(key)&&!gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).isNull(key)){
                                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).getJSONArray(key);
                            }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(!(enableCustomServer&&onlyUseGuildCustom_Server)){
                    try {
                        if(gUserProfile.jsonObject.has(iRestraints.nCustomGagText)&&!gUserProfile.jsonObject.isNull(iRestraints.nCustomGagText)&&gUserProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).has(key)&&!gUserProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).isNull(key)){
                            if(array.isEmpty()){
                                array=gUserProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).getJSONArray(key);
                            }else{
                                JSONArray arraytmp=gUserProfile.jsonObject.getJSONObject(iRestraints.nCustomGagText).getJSONArray(key);
                                for(int i=0;i<arraytmp.length();i++){
                                    array.put(arraytmp.getString(i));
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+"arraym="+array.toString());
                return array;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONArray();
            }
        }
        boolean repetiveTextOff=true, onlyUseCustom_User =false, onlyUseCustom_Server =false, onlyUseGuildCustom_Server =false, enableCustomServer=false;
        private String stringReplacer(String message,String severity){
            String fName="[stringReplacer]";
            Logger logger = Logger.getLogger(iRestraints.class);
            try {
                logger.info(fName+"severity="+severity);
                logger.info(fName+"old="+message);
                String oldmessage=message;
                if(severity.equalsIgnoreCase(GAGLEVELS.Mute.getName())) {
                    int l=message.length();
                    StringBuilder tmp= new StringBuilder();
                    for(int i=0;i<l;i++){
                        if(i>=maxcycle)break;
                        if(message.charAt(i)!=' '&&message.charAt(i)!='\n'&&message.charAt(i)!='\t'){
                            tmp.append("#");
                        }else{
                            tmp.append(message.charAt(i));
                        }
                    }
                    message=tmp+" ";
                }
                else if(severity.equalsIgnoreCase(GAGLEVELS.Paci.getName())) {
                    message ="[makes sucking paci noises]";
                }
                else if(severity.equalsIgnoreCase(GAGLEVELS.Loose.getName())) {
                    message = message.replaceAll("l", "w").replaceAll("R", "W").replaceAll("L", "W").replaceAll("s", "f").replaceAll("S", "F").replaceAll("t", "g").replaceAll("T", "G");
                }
                else if(severity.equalsIgnoreCase(GAGLEVELS.Severe.getName())) {
                    message = message.replaceAll("[abjsvz]", "r").replaceAll("[ABJSVZ]", "R").replaceAll("[dklv]", "f").replaceAll("[DKLV]", "F").replaceAll("[gx]", "n").replaceAll("[GX]", "N").replaceAll("[himu]", "d").replaceAll("[HIMU]", "D").replaceAll("q", "m").replaceAll("Q", "M");
                }
                else if(severity.equalsIgnoreCase(GAGLEVELS.Extreme.getName())) {
                    message = message.replaceAll("[BDKTV]", "Mph").replaceAll("[bdktv]", "m").replaceAll("[DJLQR]", "M").replaceAll("[djlqr]", "ph").replaceAll("[AEIOU]", "Mph").replaceAll("[aeiou]", "m").replaceAll("[CVNY]", "Mh").replaceAll("[cvny]", "ph").replaceAll("[WZX]", "Mf").replaceAll("[wzx]", "f").replaceAll("S", "h").replaceAll("s", "m");
                }
                else if(severity.equalsIgnoreCase(GAGLEVELS.Corrupt.getName())) {
                    message=corruptedGag(message);
                }
                else if(severity.equalsIgnoreCase(GAGLEVELS.Turkey.getName())) {
                    message=turkeyGag(message);
                }
                else{
                    JSONArray array=getCustomTextArray(severity);
                    if(severity.equalsIgnoreCase(GAGLEVELS.Puppy.getName())) {
                        message=puppyGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Kitty.getName())) {
                        message=kittenGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Pony.getName())) {
                        message=ponyGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Piggy.getName())) {
                        message=piggyGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())) {
                        message=dinosaurGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())) {
                        message=squeakyGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Bird.getName())) {
                        message=birdGag(message, array,repetiveTextOff,onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Cow.getName())) {
                        message=cowGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Sheep.getName())) {
                        message=sheepGag(message, array,repetiveTextOff, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())) {
                        message=nucleusMaskGag(message, array, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())) {
                        message=droneMaskGag(message, array, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Toy.getName())) {
                        message=toyMaskGag(message, array, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.DroneReindeer.getName())) {
                        message=toyReindeerMaskGag(message, array, onlyUseCustom_User||onlyUseCustom_Server||onlyUseGuildCustom_Server);
                    }
                    if(severity.equalsIgnoreCase(GAGLEVELS.Training.getName())) {
                        message=trainingGag(message,TRAININGGAGENTRIES);
                    }
                }
                if(oldmessage.charAt(0)==' '){message=" "+message;}
                if(oldmessage.charAt(oldmessage.length()-1)==' '){message+=" ";}
                logger.info(fName+"new="+message);
                return message;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return message;
            }
        }
        private JSONArray stringSizeLimit(String str){
            String fName="[stringSizeLimit]";
            JSONArray jsonArray=new JSONArray();
            try {
                logger.info(fName+"str="+str);
                int l=str.length();
                logger.info(fName+"str.length="+l);
                if(l<=1995){
                    jsonArray.put(str);
                }else{
                    while (str.length() >1990) {
                        jsonArray.put(str.substring(0, 1995)+"...");
                        str=str.substring(1996);
                    }
                    if(str.length()>0){
                        jsonArray.put("..."+str);
                    }
                }
                return jsonArray;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                jsonArray=new JSONArray();
                jsonArray.put(str);
                return jsonArray;
            }
        }
        int gGender=0;
        private void getGender(){
            String fName="[getGender]";
            logger.info(fName );
            try {
                if(gUserProfile.jsonObject.has(nGender)&&!gUserProfile.jsonObject.isNull(nGender)){
                    int i=gUserProfile.jsonObject.getInt(nGender);
                    if(0<=i&&i<=2){
                        gGender=i;
                    }
                }
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
    }



}
