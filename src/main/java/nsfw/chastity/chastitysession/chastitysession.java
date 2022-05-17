package nsfw.chastity.chastitysession;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcBasicFeatureControl;
import models.lc.lcTempZipFile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class chastitysession extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper,ichastitysession {
        Logger logger = Logger.getLogger(getClass());
        String table="chastitySession"; String sqlLog="chastitySessionLog";
       
        lcGlobalHelper gGlobal;
        String gTitle="Chastity Session",gCommand="chastity";
        JSONObject rfEntries;
    public chastitysession(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Creating, viewing or performing a chastity session";
        this.aliases = new String[]{gCommand,"chastitysession","2chastity","2chastitysession"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
    }
        @Override
        protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }

protected class runLocal implements Runnable {
    CommandEvent gEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel sessionChannel;
    TextChannel gTextChannel;
    lcJSONUserProfile gUserProfile;
    Member gTarget;
    boolean gIsOverride=false;
    public runLocal(CommandEvent ev) {
        String fName="build";logger.info(".run build");
        gEvent = ev;
        gUser = gEvent.getAuthor();gMember=gEvent.getMember();
        gGuild = gEvent.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gEvent.getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
    }

    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");
        String[] items;
        Boolean isInvalidCommand = true;
        try{
            gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"chastitysession",gGlobal);
            gBasicFeatureControl.initProfile();
            if(!isNSFW()){
                blocked();return;
            }
            if(gEvent.getArgs().isEmpty()){
                logger.info(fName+".Args=0");
                help("main");
                gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();isInvalidCommand=false;
            }else {
                logger.info(fName + ".Args");
                items = gEvent.getArgs().split("\\s+");
                if(gEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                logger.info(fName + ".items.size=" + items.length);
                logger.info(fName + ".items[0]=" + items[0]);
                if(isTargeted()){
                    if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                    }
                    if(items.length>=3&&isInvalidCommand){
                        if(items[1].equalsIgnoreCase("keyholder")){
                            if(items[2].equalsIgnoreCase("release")){
                                releaseKeyHolder();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("accept")){
                                acceptKeyHolder();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("reject")){
                                rejectKeyHolder();isInvalidCommand = false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("verification")){
                            if(items[2].equalsIgnoreCase("set")){
                                setVerification();isInvalidCommand = false;
                            }else
                            if(items[2].equalsIgnoreCase("get")){
                                getLastVerification();isInvalidCommand = false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("duration")){
                            if(items[2].equalsIgnoreCase("start")){
                                setStartingDuration();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("min")){
                                setMinDuration();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("max")){
                                setMaxDuration();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("add")){
                                addDuration();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("sub")){
                                subDuration();isInvalidCommand = false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("hygiene")){
                            if(items[2].equalsIgnoreCase("setperiod")){
                                hygineSetCountPeriod();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("setduration")){
                                hygineSetDuration();isInvalidCommand = false;
                            }
                            if(items[2].equalsIgnoreCase("setpenalty")){
                                hygineSetPenalty();isInvalidCommand = false;
                            }
                        }
                        else if(items[1].equalsIgnoreCase("guests")||items[1].equalsIgnoreCase("guest")||items[1].equalsIgnoreCase("friend")||items[1].equalsIgnoreCase("voting")||items[1].equalsIgnoreCase("vote")){
                            if(items.length>=4&&isInvalidCommand){
                                if(items[2].equalsIgnoreCase("enableadd")){
                                    friendVotingEnableAdd(items[3]);isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("enablesub")){
                                    friendVotingEnableSub(items[3]);isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("enablerandom")){
                                    friendVotingEnableRandom(items[3]);isInvalidCommand = false;
                                }
                            }
                            if(isInvalidCommand){
                                if(items[2].equalsIgnoreCase("enableadd")){
                                    friendVotingEnableAdd("");isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("enablesub")){
                                    friendVotingEnableSub("");isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("enablerandom")){
                                    friendVotingEnableRandom("");isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("add")){
                                    friendVotingDoAdd();isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("sub")){
                                    friendVotingDoSub();isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("random")){
                                    friendVotingDoRandom();isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("setadd")){
                                    friendVotingSetAddTime();isInvalidCommand = false;
                                }
                                if(items[2].equalsIgnoreCase("setsub")){
                                    friendVotingSetSubTime();isInvalidCommand = false;
                                }
                            }
                        }
                    }
                    if(items.length>=2&&isInvalidCommand){
                        if(items[1].equalsIgnoreCase("display")){
                            displaySession();isInvalidCommand = false;
                        }
                        else if(items[1].equalsIgnoreCase("addtime")){
                            addDuration();isInvalidCommand = false;
                        }
                        else if(items[1].equalsIgnoreCase("subtime")){
                            subDuration();isInvalidCommand = false;
                        }
                        else if(items[1].equalsIgnoreCase("add")){
                            friendVotingDoAdd();isInvalidCommand = false;
                        }
                        else if(items[1].equalsIgnoreCase("sub")){
                            friendVotingDoSub();isInvalidCommand = false;
                        }
                        else if(items[1].equalsIgnoreCase("random")){
                            friendVotingDoRandom();isInvalidCommand = false;
                        }
                    }
                }else{
                    if(items[0].equalsIgnoreCase("clear")){
                        clearUser();isInvalidCommand=false;
                    }
                    else if(items[0].equalsIgnoreCase("clearall")){
                        clearAll();isInvalidCommand=false;
                    }
                    if(items.length>=2&&isInvalidCommand){
                        if(items[0].equalsIgnoreCase("help")){
                            if(items[1].equalsIgnoreCase("main")||items[1].equalsIgnoreCase("session")||items[1].equalsIgnoreCase("keyholder")||items[1].equalsIgnoreCase("verification")||items[1].equalsIgnoreCase("duration")||items[1].equalsIgnoreCase("hygiene")){
                                help(items[1]);isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("guests")||items[1].equalsIgnoreCase("guest")||items[1].equalsIgnoreCase("friend")||items[1].equalsIgnoreCase("voting")||items[1].equalsIgnoreCase("vote")){
                                help(items[1]);isInvalidCommand = false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("guild")||items[0].equalsIgnoreCase("server")){
                            if(items.length>2){
                                // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                                int group=0,type=0,action=0;
                                switch (items[1].toLowerCase()){
                                    case "allowedchannels":
                                    case "allowchannels":
                                        group=1;type=1;
                                        break;
                                    case "blockedchannels":
                                    case "blockchannels":
                                        group=1;type=-1;
                                        break;
                                    case "allowedroles":
                                    case "allowroles":
                                        group=2;type=1;
                                        break;
                                    case "blockedroles":
                                    case "blockroles":
                                        group=2;type=-1;
                                        break;
                                }
                                switch (items[2].toLowerCase()){
                                    case "list":
                                        action=0;
                                        break;
                                    case "add":
                                        action=1;
                                        break;
                                    case "set":
                                        action=2;
                                        break;
                                    case "rem":
                                        action=-1;
                                        break;
                                    case "clear":
                                        action=-2;
                                        break;
                                }
                                if(group==1){
                                    if(action==0){
                                        getChannels(type,false);isInvalidCommand=false;
                                    }else{
                                        setChannel(type,action,gEvent.getMessage());
                                    }
                                }
                                else if(group==2){
                                    if(action==0){
                                        getRoles(type,false);isInvalidCommand=false;
                                    }else{
                                        setRole(type,action,gEvent.getMessage());
                                    }
                                }
                            }else{
                                menuGuild();isInvalidCommand=false;
                            }
                        }
                        else if(!gBasicFeatureControl.getEnable()){
                            logger.info(fName+"its disabled");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                            logger.info(fName+"its not allowed by channel");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                            logger.info(fName+"its not allowed by roles");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(items[0].equalsIgnoreCase("keyholder")){
                            if(items[1].equalsIgnoreCase("add")){
                                addKeyHolder();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("remove")){
                                removeKeyHolder();isInvalidCommand = false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("verification")){
                            if(items[1].equalsIgnoreCase("set")){
                                setVerification();isInvalidCommand = false;
                            }else
                            if(items[1].equalsIgnoreCase("do")){
                                doVerification();isInvalidCommand = false;
                            }else
                            if(items[1].equalsIgnoreCase("do_channel")){
                                doVerificationTextChannel();isInvalidCommand = false;
                            }else
                            if(items[1].equalsIgnoreCase("get")){
                                getLastVerification();isInvalidCommand = false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("duration")){
                            if(items[1].equalsIgnoreCase("start")){
                                setStartingDuration();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("min")){
                                setMinDuration();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("max")){
                                setMaxDuration();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("add")){
                                addDuration();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("sub")){
                                subDuration();isInvalidCommand = false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("session")){
                            if(items[1].equalsIgnoreCase("start")){
                                startSession();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("end")){
                                stopSession();isInvalidCommand = false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("hygiene")){
                            if(items[1].equalsIgnoreCase("setperiod")){
                                hygineSetCountPeriod();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("setduration")){
                                hygineSetDuration();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("setpenalty")){
                                hygineSetPenalty();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("start")||items[1].equalsIgnoreCase("open")||items[1].equalsIgnoreCase("unlock")){
                                hygineUnlock();isInvalidCommand = false;
                            }
                            if(items[1].equalsIgnoreCase("end")||items[1].equalsIgnoreCase("stop")||items[1].equalsIgnoreCase("close")||items[1].equalsIgnoreCase("lock")){
                                hygineLock();isInvalidCommand = false;
                            }
                        }
                        else if(items[0].equalsIgnoreCase("guests")||items[0].equalsIgnoreCase("guest")||items[0].equalsIgnoreCase("friend")||items[0].equalsIgnoreCase("voting")||items[0].equalsIgnoreCase("vote")) {
                            if (items.length >= 3 && isInvalidCommand) {
                                if (items[1].equalsIgnoreCase("enableadd")) {
                                    friendVotingEnableAdd(items[2]);
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("enablesub")) {
                                    friendVotingEnableSub(items[2]);
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("enablerandom")) {
                                    friendVotingEnableRandom(items[2]);
                                    isInvalidCommand = false;
                                }
                            }
                            if (isInvalidCommand) {
                                if (items[1].equalsIgnoreCase("enableadd")) {
                                    friendVotingEnableAdd("");
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("enablesub")) {
                                    friendVotingEnableSub("");
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("enablerandom")) {
                                    friendVotingEnableRandom("");
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("add")) {
                                    friendVotingDoAdd();
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("sub")) {
                                    friendVotingDoSub();
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("random")) {
                                    friendVotingDoRandom();
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("setadd")) {
                                    friendVotingSetAddTime();
                                    isInvalidCommand = false;
                                }
                                if (items[1].equalsIgnoreCase("setsub")) {
                                    friendVotingSetSubTime();
                                    isInvalidCommand = false;
                                }
                            }
                        }
                    }
                    if(isInvalidCommand){
                        if(items[0].equalsIgnoreCase("session")||items[0].equalsIgnoreCase("keyholder")||items[0].equalsIgnoreCase("verification")||items[0].equalsIgnoreCase("duration")||items[0].equalsIgnoreCase("hygiene")){
                            help(items[0]);isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("help")){
                            help("main");isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("display")){
                            displaySession();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("startsession")||items[0].equalsIgnoreCase("start")){
                            startSession();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("stopsession")||items[0].equalsIgnoreCase("stop")){
                            stopSession();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("startsession_channel")||items[0].equalsIgnoreCase("start_channel")){
                            startSessionTextChannel();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("stopsession_channel")||items[0].equalsIgnoreCase("stop_channel")){
                            stopSessionTextChannel();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("addtime")){
                            addDuration();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("subtime")){
                            subDuration();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("hygienestart")||items[0].equalsIgnoreCase("hygieneunlock")){
                            hygineUnlock();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("hygieneend")||items[0].equalsIgnoreCase("hygienestop")||items[0].equalsIgnoreCase("hygienelock")){
                            hygineLock();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("hygienestart_channel")||items[0].equalsIgnoreCase("hygieneunlock_channel")){
                            hygineUnlockTextChannel();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("hygieneend_channel")||items[0].equalsIgnoreCase("hygienestop_channel")||items[0].equalsIgnoreCase("hygienelock_channel")){
                            hygineLockTextChannel();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("add")){
                            friendVotingDoAdd();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("sub")){
                            friendVotingDoSub();isInvalidCommand = false;
                        }
                        else if(items[0].equalsIgnoreCase("random")){
                            friendVotingDoRandom();isInvalidCommand = false;
                        }
                    }
                }
            }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gEvent);*/
            if(isInvalidCommand){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"You provided an incorrect command!", llColorRed);
            }

        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,sRTitle,"Exception:"+ex, llColorRed); }
        logger.info(".run ended");
    }
    private boolean isNSFW(){
        String fName = "[isNSFW]";
        if(gTextChannel.isNSFW()){
            return true;
        }
        if(lsGuildHelper.lsIsGuildNSFW(gGlobal,gGuild)){
            return true;
        }
        return false;
    }
    private void blocked(){
        String fName = "[blocked]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel or server.",llColorRed);
        logger.info(fName);
    }
    private void clearUser(){
        String fName="[clearUser]";
        logger.info(fName);
        gGlobal.clearUserProfile(profileName,gUser,gGuild);
        llSendQuickEmbedMessage(gUser,sRTitle,"Cleared", llColorPurple1);
    }
    private void clearAll(){
        String fName="[clearAll]";
        logger.info(fName);
        gGlobal.clearUserProfile(profileName,gGuild);
        llSendQuickEmbedMessage(gUser,sRTitle,"Cleared", llColorPurple1);
    }
    private void displaySession(){
        String fName = "[displaySession]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) { llSendQuickEmbedMessage(gUser, sRTitle, "Failed to load " + gUser.getAsMention() + " profile!", llColorRed);
                return;
            }
            loadDUserValues();
            gTarget=gMember;
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
            loadDUserValues();
            if(keyHolder.isEmpty()||!gUser.getId().equalsIgnoreCase(keyHolder)){
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their keyholder!", llColorRed); return;
            }
        }
        EmbedBuilder embedBuilder=new EmbedBuilder();embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorPurple2);
        String desc="";
        embedBuilder.addField("Wearer",gUserProfile.getUser().getAsMention(),true);
        if(keyHolderAccepted&&IsStringAMember(gGuild,keyHolder)){
            embedBuilder.addField("Keyholder",gGuild.getMemberById(keyHolder).getAsMention(),true);
        }else {
            embedBuilder.addField("Keyholder", gUserProfile.getUser().getAsMention(), true);
        }
        if(startingDuration==0){
            desc+="**Starting:** disabled";
        }else{
            desc+="**Starting:** "+displayDuration(startingDuration);
        }
        if(maxDuration==0){
            desc+="\n**Max:** disabled";
        }else{
            desc+="\n**Max:** "+displayDuration(maxDuration);
        }
        if(minDuration==0){
            desc+="\n**Min:** disabled";
        }else{
            desc+="\n**Min:** "+displayDuration(minDuration);
        }
        embedBuilder.addField("Duration", desc, false);desc="";
        getRemaining();
        if(!isActive){
            desc+="Session inactive";
        }else{
            desc+="**Started date:** "+gCalendarStart.get(gCalendarStart.YEAR)+"."+(gCalendarStart.get(gCalendarStart.MONTH)+1)+"."+gCalendarStart.get(gCalendarStart.DAY_OF_MONTH)+" "+gCalendarStart.get(gCalendarStart.HOUR_OF_DAY)+":"+gCalendarStart.get(gCalendarStart.MINUTE);
            desc+="\n**End date:** "+gCalendarEnd.get(gCalendarEnd.YEAR)+"."+(gCalendarEnd.get(gCalendarEnd.MONTH)+1)+"."+gCalendarEnd.get(gCalendarEnd.DAY_OF_MONTH)+" "+gCalendarEnd.get(gCalendarEnd.HOUR_OF_DAY)+":"+gCalendarEnd.get(gCalendarEnd.MINUTE);
            desc+="\n**Remaining duration:**  "+displayDuration(remainingDuration);
            if(verificationRemaining<0){
                desc+="\n**Verification:**  oversleeped "+displayDuration(verificationRemaining*-1);
            }else
            if(verificationRemaining==0){
                desc+="\n**Verification:**  Time is up";
            }else{
                desc+="\n**Verification:**  "+displayDuration(verificationRemaining);
            }

        }
        embedBuilder.addField("Session", desc, true);desc="";
        if(!isHygieneActive){
            desc="**Hygiene opening disabled**";
        }else{
            String strPeriod=" daily";

            if(hygienePeriod==1){strPeriod="weekly";}
            else  if(hygienePeriod==2){strPeriod="monthly";}
            if(isHygieneOpen){
                desc+="**WARNING: They unlocked for cleaning! Remaining duration for cleaning:"+displayDuration(remainingHygieneDuration)+"**";
            }
            desc+="\n\tperiod:"+ hygieneUsed +"/"+hygieneCount+" "+strPeriod;
            desc+="\n\tduration:"+displayDuration(hygieneDuration);
            if(hygienePenalty==0){
                desc+="\n\tpenalty:disabled";
            }else{
                desc+="\n\tpenalty:"+displayDuration(hygienePenalty);
            }
            if(isHygieneOpen){
                desc+="\n\tremaning:"+displayDuration(remainingHygieneDuration);
            }
        }
        embedBuilder.addField("Hygiene", desc, true);desc="";
        if(isFriendEnableAdd||isFriendEnableSub||isFriendEnableRand){
            desc+="\n**Voting**";
            desc+="\n\tmode:";
            String mode="";
            if(isFriendEnableAdd)mode+=" add";
            if(isFriendEnableSub){ if(!mode.isBlank())mode+=", ";mode+=" sub";}
            if(isFriendEnableRand){if(!mode.isBlank())mode+=", ";mode+=" random";};
            desc+=" "+mode;
            if(isFriendEnableAdd&&friendAddTime>0){
                desc+="\n\tadd time:"+displayDuration(friendAddTime);
            }
            if(isFriendEnableSub&&friendSubTime>0){
                desc+="\n\tsub time:"+displayDuration(friendSubTime);
            }
            embedBuilder.addField("Guests voting", desc, true);desc="";
        }
       llSendMessage(gTextChannel,embedBuilder);
    }

    private void addKeyHolder(){
        String fName = "[addKeyHolder]";
        logger.info(fName);
        logger.info(fName + ".me");
        if(!loadedProfile(gUser)){
            Failed2LoadProfile(gUser); return;}
        loadDUserValues();
        if(!keyHolder.isEmpty()){
            llSendQuickEmbedMessage(gUser,sRTitle,"Already have a keyholder", llColorRed); return;
        }
        List<Member> members=gEvent.getMessage().getMentionedMembers();
        if(members.isEmpty()){
            llSendQuickEmbedMessage(gUser,sRTitle,"No mention!", llColorRed); return;
        }
        if(members.get(0).getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't name yourself as keyholder!", llColorRed); return;
        }
        Member member=members.get(0);
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,false);
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolder,member.getId());
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessage(gUser,sRTitle,"Added "+member.getAsMention()+" as your keyholder. They need to confirm it!", llColorPurple1);
        Message message=llSendQuickEmbedMessageResponse(member.getUser(),sRTitle,gUser.getAsMention()+" added you as their keyholder! Please confirm or deny it. \nAccept:!>chastity "+gUser.getAsMention()+" keyholder accept\nReject !>chastity "+gUser.getAsMention()+" keyholder reject", llColorPurple1);
        message.addReaction(gGlobal.emojis.getEmoji("key")).queue();
        message.addReaction(gGlobal.emojis.getEmoji("leftwards_arrow_with_hook")).queue();
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        if(name.contains(gGlobal.emojis.getEmoji("key"))){
                            if(!loadedProfile(gUser)){
                                Failed2LoadProfile(member.getUser(),gUser); return;}
                            loadDUserValues();
                            if(keyHolder.isEmpty()||!member.getId().equalsIgnoreCase(keyHolder)){
                                llSendQuickEmbedMessage(member.getUser(),sRTitle,"Denied! You are not their keyholder!", llColorRed); return;
                            }
                            gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,true);
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            llSendQuickEmbedMessage(member.getUser(),sRTitle,"You grabbed "+gUser.getAsMention()+"s key that they offered it to you.", llColorGreen1);
                            llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+" has accepted your key offering.", llColorGreen1);
                            llMessageDelete(message);
                        }else
                        if(name.contains(gGlobal.emojis.getEmoji("leftwards_arrow_with_hook"))){
                            gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,false);
                            gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolder,"");
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                            llSendQuickEmbedMessage(member.getUser(),sRTitle,"You rejected "+gUser.getAsMention()+"'s offering.", llColorPurple1);
                            llSendQuickEmbedMessage(gUser,sRTitle,member.getAsMention()+" has rejected your key offering.", llColorPurple1);
                            llMessageDelete(message);
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    }
                },15, TimeUnit.MINUTES, () -> {
                    llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                    llMessageClearReactions(message);
                });
    }
    private void removeKeyHolder(){
        String fName = "[removeKeyHolder]";
        logger.info(fName);
         logger.info(fName + ".me");
         if(!loadedProfile(gUser)){
             Failed2LoadProfile(gUser); return;}
         loadDUserValues();
        if(!keyHolder.isEmpty()&&isActive&& keyHolderAccepted){
            llSendQuickEmbedMessage(gUser,sRTitle,"Session is active. You don't have access to this parameters only your keyholder.", llColorRed); return;
        }
        if(keyHolder.isEmpty()){
            llSendQuickEmbedMessage(gUser,sRTitle,"No keyholder to remove!", llColorRed); return;
        }
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,false);
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolder,"");
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}

        llSendQuickEmbedMessage(gUser,sRTitle,"Removed your keyholder.", llColorPurple1);
        llSendQuickEmbedMessage(gGuild.getMemberById(keyHolder).getUser(),sRTitle,gUser.getAsMention()+" removed you as their keyholder.", llColorPurple1);
    }
    private void acceptKeyHolder(){
        String fName = "[acceptKeyHolder]";
        logger.info(fName);
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the wearer!", llColorRed); return;
        }
        if(gTarget.getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't use yourself as keyholder!", llColorRed); return;
        }
         if(!loadedProfile(gTarget.getUser())){
             Failed2LoadProfile(gUser,gTarget); return;}
         loadDUserValues();
         if(keyHolder.isEmpty()||!gUser.getId().equalsIgnoreCase(keyHolder)){
             llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their keyholder!", llColorRed); return;
         }
         gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,true);
         if(!saveProfile()){
             llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
         llSendQuickEmbedMessage(gUser,sRTitle,"You grabbed "+gTarget.getAsMention()+"s key that they offered it to you.", llColorGreen1);
         llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,gUser.getAsMention()+" has accepted your key offering.", llColorGreen1);

    }
    private void rejectKeyHolder(){
        String fName = "[rejectKeyHolder]";
        logger.info(fName);
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the wearer!", llColorRed); return;
        }
        if(gTarget.getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't use yourself as keyholder!", llColorRed); return;
        }
        if(!loadedProfile(gTarget.getUser())){
            Failed2LoadProfile(gUser,gTarget); return;}
        loadDUserValues();
        if(keyHolder.isEmpty()||!gUser.getId().equalsIgnoreCase(keyHolder)){
            llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their keyholder!", llColorRed); return;
        }
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,false);
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolder,"");
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessage(gUser,sRTitle,"You rejected "+gTarget.getAsMention()+"'s offering.", llColorPurple1);
        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,gUser.getAsMention()+" has rejected your key offering.", llColorPurple1);

    }
    private void releaseKeyHolder(){
        String fName = "[releaseKeyHolder]";
        logger.info(fName);
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Please mention the wearer!", llColorRed); return;
        }
        if(gTarget.getId().equals(gUser.getId())){
            llSendQuickEmbedMessage(gUser,sRTitle,"You can't use yourself as keyholder!", llColorRed); return;
        }
        if(!loadedProfile(gTarget.getUser())){
            Failed2LoadProfile(gUser,gTarget); return;}
        loadDUserValues();
        if(keyHolder.isEmpty()||!gUser.getId().equalsIgnoreCase(keyHolder)){
            llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their keyholder!", llColorRed); return;
        }
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolderAccept,false);
        gUserProfile.putFieldEntry(fieldSession,keySessionKeyHolder,"");
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
        llSendQuickEmbedMessage(gUser,sRTitle,"You gave back the key to "+gTarget.getAsMention()+".", llColorPurple1);
        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,gUser.getAsMention()+" has given back your key.", llColorPurple1);

    }

    private void setVerification(){
        String fName = "[setVerification]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if(!loadedProfile(gUser)){
                    Failed2LoadProfile(gUser); return;}
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

            long timeset = String2Timeset4Duration(gEvent.getArgs());
            logger.info(fName + ".timeset=" + timeset);
            if (timeset < milliseconds_hour) {
                logger.info(fName + ".turn off");
                gUserProfile.putFieldEntry(fieldSession,keySessionVerificationRequired,false);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Turned off request verification!", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Turned off request verification for "+gTarget.getAsMention()+".", llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Request verification turned off by your keyholder!", llColorPurple1);
                }
            }else{
                logger.info(fName + ".turn on & set");
                gUserProfile.putFieldEntry(fieldSession, keySessionVerificationInterval, timeset);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                gUserProfile.putFieldEntry(fieldSession, keySessionVerificationLastDate, timestamp.getTime());
                gUserProfile.putFieldEntry(fieldSession, keySessionVerificationRequired, true);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Failed to write in Db!", llColorRed); return;}
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser,sRTitle,"Verification on & set to: "+displayDuration(timeset), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Verification for "+gTarget.getAsMention()+" on and set to:" +displayDuration(timeset), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Verification updated by your keyholder: "+displayDuration(timeset), llColorPurple1);
                }
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set verification time!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doVerification() {
        String fName = "[doVerification]";
        if(gTarget==null) {
            logger.info(fName + ".me");
            if(!loadedProfile(gUser)){
                Failed2LoadProfile(gUser); return;}
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!isActive){
            llSendQuickEmbedMessage(gUser,sRTitle,"Session is not active.", llColorRed); return;
        }
        String code = randomCode();
        logger.info(fName + ".code=" + code);String title="Chastity Verification";

        Message message=llSendQuickEmbedMessageResponse(gUser, title, "Please submit an verification image with the code:" + code + "\nTimeout 5 minutes.\nTo cancel type'!cancel'", llColorPurple1);

        gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel, and for safety, a different message
                e -> e.getAuthor().equals(gUser),
                // respond, inserting the name they listed into the response
                e -> {
                    try {
                        String fWName = "[verifyChastityWaiter]";
                        if (e.getMessage().getContentRaw().equals("!cancel")) {
                            logger.info(fWName + ".canceled");
                        } else {
                            try {
                                llMessageDelete(e.getMessage());
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, title, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        llSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        return;
                                    }
                                    String attachmentUrl = attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName = attachment.getFileName();
                                    String fileExtension = attachment.getFileExtension();
                                    String fileId = attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath = pathLSave4Verification.replaceAll("userid", gUser.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder = new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }
                                    boolean bool = folder.mkdir();
                                    if (bool) {
                                        logger.info(fName + "Directory created successfully");
                                    } else {
                                        logger.warn(fName + "Sorry couldn’t create specified directory");
                                    }

                                    String filePath = folderPath + "/" + fileId + "." + fileExtension;
                                    logger.info(fName + "filePath=" + filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if (!filex.exists()) {
                                            logger.error(fName + "  file does not exists");
                                        } else {
                                            logger.info(fName + "  file exists");
                                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                            logger.info(fName+".timestamp="+timestamp.getTime());
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationLastDate, timestamp.getTime());
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationCode, code);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationFile, fileId+"."+fileExtension);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationName, fileId);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationExtension, fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            logger.info(fName + "  profile updated");
                                            llSendQuickEmbedMessage(gUser, title, "Verification submitted!", llColorGreen1);
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            logger.info(fName + "  embed created");
                                            embedBuilder.setDescription(gUserProfile.getUser().getAsMention()+" submitted their verification with code "+code+".");
                                            embedBuilder.setImage(attachmentUrl);
                                            logger.info(fName + "  ready");
                                            llSendMessage(gTextChannel,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, title, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:" + t);
                                        logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });
                                }
                            }catch (Exception ex){
                                logger.error(fName + ".exception=" + ex);
                                logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                                llSendQuickEmbedMessage(gUser, title, "Failed to do command!", llColorRed);
                            }

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },
                // if the user takes more than a minute, time out
                5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser, "Chastity Verify", "Timeout", llColorRed));
        logger.info(fName + ".waiter created");
    }
    private void doVerificationTextChannel() {
        String fName = "[doVerificationTextChannel]";
        if(gTarget==null) {
            logger.info(fName + ".me");
            if(!loadedProfile(gUser)){
                Failed2LoadProfile(gUser); return;}
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!isActive){
            llSendQuickEmbedMessage(gUser,sRTitle,"Session is not active.", llColorRed); return;
        }
        String code = randomCode();
        logger.info(fName + ".code=" + code);String title="Chastity Verification";

        Message message=llSendMessageResponse(gTextChannel,gMember.getAsMention()+", please submit an verification image with the code:" + code + "\nTimeout 5 minutes.\nTo cancel type'!cancel'");

        gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel, and for safety, a different message
                e -> e.getAuthor().equals(gUser)&&e.getChannel()==gTextChannel,
                // respond, inserting the name they listed into the response
                e -> {
                    try {
                        llMessageDelete(message);
                        String fWName = "[verifyChastityWaiter]";
                        if (e.getMessage().getContentRaw().equals("!cancel")) {
                            logger.info(fWName + ".canceled");
                        } else {
                            try {
                                llMessageDelete(e.getMessage());
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        return;
                                    }
                                    String attachmentUrl = attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName = attachment.getFileName();
                                    String fileExtension = attachment.getFileExtension();
                                    String fileId = attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath = pathLSave4Verification.replaceAll("userid", gUser.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder = new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }
                                    boolean bool = folder.mkdir();
                                    if (bool) {
                                        logger.info(fName + "Directory created successfully");
                                    } else {
                                        logger.warn(fName + "Sorry couldn’t create specified directory");
                                    }

                                    String filePath = folderPath + "/" + fileId + "." + fileExtension;
                                    logger.info(fName + "filePath=" + filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if (!filex.exists()) {
                                            logger.error(fName + "  file does not exists");
                                        } else {
                                            logger.info(fName + "  file exists");
                                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                            logger.info(fName+".timestamp="+timestamp.getTime());
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationLastDate, timestamp.getTime());
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationCode, code);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationFile, fileId+"."+fileExtension);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationName, fileId);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionVerificationExtension, fileExtension);
                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            logger.info(fName + "  profile updated");
                                            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Verification submitted!", llColorGreen1);
                                            EmbedBuilder embedBuilder=new EmbedBuilder();
                                            logger.info(fName + "  embed created");
                                            embedBuilder.setDescription(gUserProfile.getUser().getAsMention()+" submitted their verification with code "+code+".");
                                            // embedBuilder.setImage(attachmentUrl);
                                            logger.info(fName + "  ready");
                                            llSendMessage(gTextChannel,embedBuilder);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, title, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:" + t);
                                        logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });
                                }
                            }catch (Exception ex){
                                logger.error(fName + ".exception=" + ex);
                                logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to do command!", llColorRed);
                            }

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },
                // if the user takes more than a minute, time out
                5, TimeUnit.MINUTES, () -> llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, "Chastity Verify", "Timeout", llColorRed));
        logger.info(fName + ".waiter created");
    }
    private void getLastVerification(){
        String fName = "[getLastVerification]";
        logger.info(fName);
        try {
            User selectedUser=null;Member selectedMember=null;
            if(gTarget==null) {
                logger.info(fName + ".me");
                selectedUser=gUser;selectedMember=gMember;
            }else{
                logger.info(fName + ".them");
                selectedUser=gTarget.getUser();selectedMember=gTarget;
            }
            if (!loadedProfile( selectedUser)) {Failed2LoadProfile( selectedUser);
                return;
            }
            loadDUserValues();
            if(!isActive){
                llSendQuickEmbedMessage(gUser,sRTitle,"Session is not active.", llColorRed); return;
            }
            File file=null;
            if(verificationFile!=null&&!verificationFile.isBlank()&&!verificationFile.isEmpty()){
                file=new File(pathLSave4Verification.replaceAll("userid", selectedUser.getId())+"/"+ verificationFile);
            }
            if(file!=null&&file.exists()){
                gTextChannel.sendMessage("Here is "+gUserProfile.getUser().getAsMention()+" latest verification with code "+verificationCode+".").addFile(file,verificationCode+"."+verificationImageExt).complete();
            }else{
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "No verification image to display for "+selectedMember.getAsMention()+".", llColorGreen1);
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }


    private void setStartingDuration(){
        String fName = "[setStartingDuration]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) { llSendQuickEmbedMessage(gUser, sRTitle, "Failed to load " + gUser.getAsMention() + " profile!", llColorRed);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!gIsOverride&&!accessApproveResponse()){
                logger.info(fName+"noaccess");SendDenied(gUser);return; }
            if(isActive){
                logger.info(fName+"Session is active, can't change starting duration!");
                llSendQuickEmbedMessage(gUser,sRTitle,"Session is active, can't change starting duration!", llColorRed);
                return;
            }

            long timeset = String2Timeset4Duration(gEvent.getArgs());
            logger.info(fName + ".timeset=" + timeset);
            if (timeset < milliseconds_hour) {
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set starting duration!", llColorRed);
                return;
            }
            gUserProfile.putFieldEntry(fieldDuration, keyDuration0StartingDuration, timeset);
            if (!saveProfile()) {
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                return;
            }
            if(gTarget==null){
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Starting duration set to:"+displayDuration(timeset), llColorPurple1);
            }else{
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Starting duration for "+gTarget.getAsMention()+" set to:"+displayDuration(timeset), llColorPurple1);
                llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Starting duration set by your keyholder to:"+displayDuration(timeset), llColorPurple1);
            }
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void setMinDuration(){
        String fName = "[setMinDuration]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) { llSendQuickEmbedMessage(gUser, sRTitle, "Failed to load " + gUser.getAsMention() + " profile!", llColorRed);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();getRemaining();
            if(!gIsOverride&&!accessApproveResponse()){
                logger.info(fName+"noaccess");SendDenied(gUser);return; }

            long timeset = String2Timeset4Duration(gEvent.getArgs());
            logger.info(fName + ".timeset=" + timeset);
            if(timeset ==0||timeset >= milliseconds_hour){
                if(timeset>maxDuration&&maxDuration>0){ llSendQuickEmbedMessage(gUser, sRTitle, "Minimum duration can't be bigger than the maximum duration, except when maximum is disabled.", llColorRed);return; }
                gUserProfile.putFieldEntry(fieldDuration, keyDuration0MinimumDuration, timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    if(gTarget==null){
                        llSendQuickEmbedMessage(gTextChannel, sRTitle, "Minimum duration disabled!", llColorPurple1);
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,sRTitle,"Minimum duration disabled for "+gTarget.getAsMention()+" by "+gUser.getAsMention(), llColorPurple1);
                        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Minimum duration disabled by "+gUser.getAsMention(), llColorPurple1);
                    }
                }else{
                    if(gTarget==null){
                        llSendQuickEmbedMessage(gTextChannel, sRTitle, "Minimum duration set to "+displayDuration(timeset)+" for "+gUserProfile.getUser().getAsMention(), llColorPurple1);
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,sRTitle,"Minimum duration for "+gTarget.getAsMention()+" set to "+displayDuration(timeset)+" by "+gUser.getAsMention(), llColorPurple1);
                        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Minimum duration set to "+displayDuration(timeset)+" by "+gUser.getAsMention(), llColorPurple1);
                    }
                }
            }else{
                logger.info(fName + ".if rejected");
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set minimum duration!", llColorRed);
            }
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
        }
    }
    private void setMaxDuration(){
        String fName = "[setMaxDuration]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) { llSendQuickEmbedMessage(gUser, sRTitle, "Failed to load " + gUser.getAsMention() + " profile!", llColorRed);return; }
            }else{
                if(!loadedProfile(gTarget.getUser())){ Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();getRemaining();
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }
            long timeset = String2Timeset4Duration(gEvent.getArgs());
            logger.info(fName + ".timeset=" + timeset);
            if(timeset ==0||timeset >= milliseconds_hour){
                if(timeset!=0&&timeset<remainingDuration){ logger.info(fName+"Max can't be smaller than remaining duration!");llSendQuickEmbedMessage(gUser,sRTitle,"Maximum can't be smaller than remaining duration!", llColorRed);return;}
                if(timeset!=0&&timeset<totalDuration){ logger.info(fName+"Max can't be smaller than total duration!");llSendQuickEmbedMessage(gUser,sRTitle,"Maximum can't be smaller than total duration!", llColorRed);return;}
                if(timeset!=0&&timeset<minDuration){ llSendQuickEmbedMessage(gUser, sRTitle, "Maximum duration can't be smaller than the minimum duration.", llColorRed);return; }
                gUserProfile.putFieldEntry(fieldDuration, keyDuration0MaximumDuration, timeset);
                if (!saveProfile()) { llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);return; }
                if(timeset==0){
                    if(gTarget==null){
                        llSendQuickEmbedMessage(gTextChannel, sRTitle, "Maximum duration disabled for "+gUserProfile.getUser().getAsMention(), llColorPurple1);
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,sRTitle,"Maximum duration disabled for "+gTarget.getAsMention()+" by "+gUser.getAsMention(), llColorPurple1);
                        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Maximum duration disabled by "+gUser.getAsMention(), llColorPurple1);
                    }
                }else{
                    if(gTarget==null){
                        llSendQuickEmbedMessage(gTextChannel, sRTitle, "Maximum duration set to "+displayDuration(timeset)+" for "+gUserProfile.getUser().getAsMention(), llColorPurple1);
                    }else{
                        llSendQuickEmbedMessage(gTextChannel,sRTitle,"Maximum duration for "+gTarget.getAsMention()+" set to "+displayDuration(timeset)+" by "+gUser.getAsMention(), llColorPurple1);
                        llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Maximum duration set to "+displayDuration(timeset)+" by "+gUser.getAsMention(), llColorPurple1);
                    }
                }
            }else{
                logger.info(fName + ".if rejected");
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set maximum duration!", llColorRed);
            }
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }

    private void startSession(){
        String fName = "[startSession]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
                loadDUserValues();
            }else{
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! Only the wearer can start the session.", llColorRed);return;
            }
            loadDUserValues();
            if(isActive){
                llSendQuickEmbedMessage(gUser,sRTitle,"Session is already active.", llColorRed); return;
            }
            String title ="Submit your lockbox code";
            Message message=llSendQuickEmbedMessageResponse(gUser, title, "Please submit your lockbox code.\nIf you have issues with submitting the pic type `noimg` to start without submitting a pic.\nIf you want to cancel it type `cancel`.", llColorPurple1);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser),
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            llMessageDelete(message);
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName + "text=" + content);
                            if (content.equalsIgnoreCase("cancel")) {
                                llSendQuickEmbedMessage(gUser, title, "Canceled!", llColorRed);
                                return;
                            }

                            gUserProfile.putFieldEntry(fieldSession, keySessionActive, true);
                            gUserProfile.putFieldEntry(fieldDuration,keyDuration0TotalDuration ,startingDuration);
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            logger.info(fName+".timestamp="+timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldSession,keySessionStartDate,timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldSession,keySessionVerificationLastDate,timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldSession,keySessionVerificationFile,"");
                            if (content.equalsIgnoreCase("noimg")) {
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");
                                if (!saveProfile()) {
                                    llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                    return;
                                }
                                llSendQuickEmbedMessage(gUser,title,"Session started!",llColorGreen1);
                                llSendQuickEmbedMessage(gTextChannel, title, "Session started for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
                                return;
                            }else{
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessage(gUser, title, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        llSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Lockbox.replaceAll("userid",gUser.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }
                                    boolean bool = folder.mkdir();
                                    if(bool){
                                        logger.info(fName +"Directory created successfully");
                                    }else{
                                        logger.warn(fName +"Sorry couldn’t create specified directory");
                                    }

                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, fileId+"."+fileExtension);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, fileId);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, fileExtension);

                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            llSendQuickEmbedMessage(gUser,title,"Session started!",llColorGreen1);
                                            llSendQuickEmbedMessage(gTextChannel, title, "Session started for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, title, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, title, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }
                        }catch (Exception ex) {
                            llSendQuickEmbedMessage(gUser, title, "Failed to do command!", llColorRed);
                            llMessageDelete(message);
                            logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                        }

                    },
                    // if the user takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed);
                    });
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void stopSession(){
        String fName = "[stopSession]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! Only the wearer can stop the session.", llColorRed);return;
            }
            loadDUserValues();
            if(!isActive){
                llSendQuickEmbedMessage(gUser,sRTitle,"Session is not active.", llColorRed); return;
            }
            getRemaining();
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(remainingDuration>0){
                embed.setDescription("Are you sure you want to stop?\nYou still have Remaining duration: "+displayDuration(remainingDuration));
            }else{
                embed.setDescription("Are you sure you want to stop?");
            }
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
            message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                logger.info(fName + "lockBoxImageFile=" + lockBoxImageFile);
                                logger.info(fName + "lockBoxImageName=" + lockBoxImageName);
                                logger.info(fName + "lockBoxImageFile=" + lockBoxImageExt);
                                File file=null;
                                if(lockBoxImageFile!=null&&!lockBoxImageFile.isBlank()&&!lockBoxImageFile.isEmpty()){
                                    file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageFile);
                                }else
                                if(lockBoxImageName!=null&&!lockBoxImageName.isBlank()&&!lockBoxImageName.isEmpty()){
                                    if(lockBoxImageExt!=null&&!lockBoxImageExt.isBlank()&&!lockBoxImageExt.isEmpty()){
                                        file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName);
                                    }else{
                                        file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName+"."+lockBoxImageExt);
                                    }
                                }
                                if(file!=null){
                                    InputStream fileStream = new FileInputStream(file);
                                    lcTempZipFile zipFile=new lcTempZipFile();
                                    zipFile.addEntity("code."+lockBoxImageExt,fileStream);
                                    InputStream targetStream = zipFile.getInputStream();
                                    PrivateChannel privateChannel=null;
                                    try {
                                        privateChannel=gUser.openPrivateChannel().complete();
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                    Message lockBox0=null,lockBox1=null;
                                    try {
                                        if(privateChannel!=null){
                                            lockBox0=privateChannel.sendMessage("Here is your lockbox key.").addFile(file,"code."+lockBoxImageExt).complete();
                                        }else{
                                            lockBox0=gTextChannel.sendMessage("Here is your lockbox key.").addFile(file,"code."+lockBoxImageExt).complete();
                                        }
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                    try {
                                        if(privateChannel!=null){
                                            lockBox1=privateChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                        }else{
                                            lockBox1=gTextChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                        }
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                    if(lockBox0==null&&lockBox1==null){
                                        llSendQuickEmbedMessage(gUser, sRTitle, "Failed to stop session!", llColorRed);
                                        return;
                                    }
                                }else{
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Session stopped!", llColorGreen1);
                                }

                                if(remainingDuration>0){
                              
                                    llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" session stopped with remaining duration: "+displayDuration(remainingDuration), llColorGreen1);
                                }else{
                                    llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" session stopped!", llColorGreen1);
                                }
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionActive, false);
                                if (!saveProfile()) {
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                                }
                            }
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            llSendQuickEmbedMessage(gUser, sRTitle, "Failed to stop session!", llColorRed);
                        }

                    },1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);
                    });
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void startSessionTextChannel(){
        String fName = "[startSessionTextChannel]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
                loadDUserValues();
            }else{
                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser,sRTitle,"Denied! Only the wearer can start the session.", llColorRed);return;
            }
            loadDUserValues();
            if(isActive){
                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser,sRTitle,"Session is already active.", llColorRed); return;
            }
            String title ="Submit your lockbox code";
            Message message=llSendMessageResponse(gTextChannel,gMember.getAsMention()+", please submit your lockbox code.\nIf you have issues with submitting the pic type `noimg` to start without submitting a pic.\nIf you want to cancel it type `cancel`.");
            gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                    // make sure it's by the same user, and in the same channel, and for safety, a different message
                    e -> e.getAuthor().equals(gUser)&&e.getChannel()==gTextChannel,
                    // respond, inserting the name they listed into the response
                    e -> {
                        try {
                            llMessageDelete(message);
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName + "text=" + content);
                            if (content.equalsIgnoreCase("cancel")) {
                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Canceled!", llColorRed);
                                return;
                            }

                            gUserProfile.putFieldEntry(fieldSession, keySessionActive, true);
                            gUserProfile.putFieldEntry(fieldDuration,keyDuration0TotalDuration ,startingDuration);
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            logger.info(fName+".timestamp="+timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldSession,keySessionStartDate,timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldSession,keySessionVerificationLastDate,timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldSession,keySessionVerificationFile,"");
                            if (content.equalsIgnoreCase("noimg")) {
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");
                                if (!saveProfile()) {
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to update profile!", llColorRed);
                                    return;
                                }
                                llSendQuickEmbedMessage(gTextChannel, title, "Session started for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
                                return;
                            }else{
                                List<Message.Attachment> attachments = e.getMessage().getAttachments();
                                logger.info(fName + ".attachments.size=" + attachments.size());
                                if (attachments.size() > 0) {
                                    Message.Attachment attachment = attachments.get(0);
                                    if (!attachment.isImage()) {
                                        logger.error(fName + ".attachment is not an image");
                                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "The attachment is not an image!", llColorRed);
                                        logger.error(fName + "  not image");
                                        return;
                                    }
                                    if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                        logger.error(fName + ".image too big");
                                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "The image is above 10MB", llColorRed);
                                        return;
                                    }
                                    String attachmentUrl=attachment.getUrl();
                                    logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                    String fileName=attachment.getFileName();
                                    String fileExtension=attachment.getFileExtension();
                                    String fileId=attachment.getId();
                                    logger.info(fName + "  fileName=" + fileName);
                                    logger.info(fName + "  fileExtension=" + fileExtension);
                                    logger.info(fName + "  fileId=" + fileId);

                                    String folderPath= pathLSave4Lockbox.replaceAll("userid",gUser.getId());
                                    logger.info(fName + " folderPath=" + folderPath);
                                    File folder=new File(folderPath);
                                    if(folder.exists()){
                                        logger.info(fName +"Directory exists");
                                    }
                                    boolean bool = folder.mkdir();
                                    if(bool){
                                        logger.info(fName +"Directory created successfully");
                                    }else{
                                        logger.warn(fName +"Sorry couldn’t create specified directory");
                                    }

                                    String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                    logger.info(fName +"filePath="+filePath);
                                    File file = new File(filePath);
                                    if (file.createNewFile()) {
                                        logger.info(fName + " created");
                                    } else {
                                        logger.info(fName + " already exists");
                                    }

                                    OutputStream out = new FileOutputStream(file);
                                    out.close();

                                    attachment.downloadToFile(file).thenAccept(filex -> {
                                        if(!filex.exists()){
                                            logger.error(fName + "  file does not exists");
                                        }else{
                                            logger.info(fName + "  file exists");
                                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, fileId+"."+fileExtension);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, fileId);
                                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, fileExtension);

                                            if (!saveProfile()) {
                                                llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                                return;
                                            }
                                            llSendQuickEmbedMessage(gUser,title,"Session started!",llColorGreen1);
                                            llSendQuickEmbedMessage(gTextChannel, title, "Session started for "+gUserProfile.getUser().getAsMention(), llColorGreen1);
                                        }
                                    }).exceptionally(t -> { // handle failure
                                        llSendQuickEmbedMessage(gUser, title, "Failed to process image!", llColorRed);
                                        logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                        return null;
                                    });

                                }else{
                                    llSendQuickEmbedMessage(gUser, title, "Zero attachment!", llColorRed);
                                    logger.error(fName + "  zero attachment");
                                }
                            }
                        }catch (Exception ex) {
                            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to do command!", llColorRed);
                            llMessageDelete(message);
                            logger.error(fName + "  exception:"+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                        }

                    },
                    // if the user takes more than a minute, time out
                    1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Timeout", llColorRed);
                    });
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void stopSessionTextChannel(){
        String fName = "[stopSessionTextChannel]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser,sRTitle,"Denied! Only the wearer can stop the session.", llColorRed);return;
            }
            loadDUserValues();
            if(!isActive){
                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser,sRTitle,"Session is not active.", llColorRed); return;
            }
            getRemaining();
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(remainingDuration>0){
                embed.setDescription("Are you sure you want to stop, "+gMember.getAsMention()+"?\nYou still have Remaining duration: "+displayDuration(remainingDuration));
            }else{
                embed.setDescription("Are you sure you want to stop, "+gMember.getAsMention()+"?");
            }
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
            message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            llMessageDelete(e.getChannel(),e.getMessageId());
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                                logger.info(fName + "lockBoxImageFile=" + lockBoxImageFile);
                                logger.info(fName + "lockBoxImageName=" + lockBoxImageName);
                                logger.info(fName + "lockBoxImageFile=" + lockBoxImageExt);
                                File file=null;
                                if(lockBoxImageFile!=null&&!lockBoxImageFile.isBlank()&&!lockBoxImageFile.isEmpty()){
                                    file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageFile);
                                }else
                                if(lockBoxImageName!=null&&!lockBoxImageName.isBlank()&&!lockBoxImageName.isEmpty()){
                                    if(lockBoxImageExt!=null&&!lockBoxImageExt.isBlank()&&!lockBoxImageExt.isEmpty()){
                                        file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName);
                                    }else{
                                        file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName+"."+lockBoxImageExt);
                                    }
                                }
                                if(file!=null){
                                    InputStream fileStream = new FileInputStream(file);
                                    lcTempZipFile zipFile=new lcTempZipFile();
                                    zipFile.addEntity("code."+lockBoxImageExt,fileStream);
                                    InputStream targetStream = zipFile.getInputStream();
                                    PrivateChannel privateChannel=null;
                                    try {
                                        privateChannel=gUser.openPrivateChannel().complete();
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                    Message lockBox0=null,lockBox1=null;
                                    try {
                                        if(privateChannel!=null){
                                            lockBox0=privateChannel.sendMessage("Here is your lockbox key, "+gMember.getAsMention()+".").addFile(file,"code."+lockBoxImageExt).complete();
                                        }else{
                                            lockBox0=gTextChannel.sendMessage("Here is your lockbox key, "+gMember.getAsMention()+".").addFile(file,"code."+lockBoxImageExt).complete();
                                        }
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                    try {
                                        if(privateChannel!=null){
                                            lockBox1=privateChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                        }else{
                                            lockBox1=gTextChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                        }
                                    }catch (Exception e2){
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                    if(lockBox0==null&&lockBox1==null){
                                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Failed to stop session!", llColorRed);
                                        return;
                                    }
                                }else{
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Session stopped!", llColorGreen1);
                                }

                                if(remainingDuration>0){

                                    llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" session stopped with remaining duration: "+displayDuration(remainingDuration), llColorGreen1);
                                }else{
                                    llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" session stopped!", llColorGreen1);
                                }
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");
                                gUserProfile.putFieldEntry(fieldSession, keySessionActive, false);
                                if (!saveProfile()) {
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Failed to update profile!", llColorRed);
                                }
                            }
                        }catch (Exception e2){
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Failed to stop session!", llColorRed);
                        }

                    },1, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, gTitle, "Timeout", llColorRed);
                    });
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }

    private void addDuration(){
        String fName = "[addDuration]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!isActive){ llSendQuickEmbedMessage(gTextChannel,sRTitle,"No session active for "+gUserProfile.getUser().getAsMention(), llColorRed); return; }
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

            long timeset = String2Timeset4Duration(gEvent.getArgs());
            logger.info(fName + ".timeset=" + timeset);
            if(timeset >= milliseconds_hour){
                totalDuration+=timeset;
                logger.info(fName + ".totalDuration=" + totalDuration);
                if(totalDuration>maxDuration&&maxDuration!=0){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Can't add more time than the max duration!", llColorRed);
                    return;
                }
                gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Added "+displayDuration(timeset)+" to "+gUserProfile.getUser().getAsMention(), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Added "+displayDuration(timeset) +" to "+gUserProfile.getUser().getAsMention()+" by "+gUser.getAsMention(), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Added "+displayDuration(timeset) +" by "+gUser.getAsMention(), llColorPurple1);
                }
            }else{
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Failed to add duration!", llColorRed);
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to add duration!", llColorRed);
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
        }
    }
    private void subDuration(){
        String fName = "[subDuration]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!isActive){ llSendQuickEmbedMessage(gTextChannel,sRTitle,"No session active for "+gUserProfile.getUser().getAsMention(), llColorRed); return; }
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

            long timeset = String2Timeset4Duration(gEvent.getArgs());
            logger.info(fName + ".timeset=" + timeset);
            if(timeset >= milliseconds_hour){
                logger.info(fName + ".totalDuration.befor=" + totalDuration);
                totalDuration-=timeset;
                logger.info(fName + ".minDuration=" + minDuration);
                logger.info(fName + ".totalDuration.after=" + totalDuration);
                if(minDuration==0){
                    llSendQuickEmbedMessage(gUser, sRTitle, "It's disabled removing time!", llColorRed);
                    return;
                }
                if(totalDuration<minDuration){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Can't remove more time bellow minimum!", llColorRed);
                    return;
                }
                gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Sub duration "+displayDuration(timeset), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,"Sub duration "+displayDuration(timeset) +" to "+gTarget.getAsMention()+" by "+gUser.getAsMention(), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Sub duration "+displayDuration(timeset) +" by "+gUser.getAsMention(), llColorPurple1);
                }
            }else{
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Failed to sub duration!", llColorRed);
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to sub duration!", llColorRed);
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
        }
    }

    private void hygineSetDuration(){
        String fName = "[hygineSetDuration]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

            String []items = gEvent.getArgs().split("\\s+");
            logger.info(fName + ".items.size=" + items.length);
            long timeset = 0;
            for (int i = 0; i < items.length; i++) {
                logger.info(fName + ".iteme=" + items[i]);

                if (items[i].equalsIgnoreCase("m") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_minute;
                }
                if (items[i].equalsIgnoreCase("h") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                }
            }
            logger.info(fName + ".timeset=" + timeset);
            if(timeset > milliseconds_minute){
                hygieneDuration=timeset;
                logger.info(fName + ".hygieneDuration=" + hygieneDuration);
                if(hygieneDuration>milliseconds_day){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Cant accept this time, max is 24h!", llColorRed);
                    return;
                }
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningDuration, hygieneDuration);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Set hygiene opening duration:"+displayDuration(hygieneDuration), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Set hygiene opening duration for "+gTarget.getAsMention()+":"+displayDuration(hygieneDuration), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder set hygiene opening duration:"+displayDuration(hygieneDuration), llColorPurple1);
                }
            }else{
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set hygiene opening duration!", llColorRed);
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set hygiene opening duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void hygineSetPenalty(){
        String fName = "[hygineSetPenalty]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

            String []items = gEvent.getArgs().split("\\s+");
            logger.info(fName + ".items.size=" + items.length);
            long timeset = 0;
            for (int i = 0; i < items.length; i++) {
                logger.info(fName + ".iteme=" + items[i]);
                if (items[i].equalsIgnoreCase("w") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_week;
                }
                if (items[i].equalsIgnoreCase("d") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day;
                }
                if (items[i].equalsIgnoreCase("h") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                }
            }
            logger.info(fName + ".timeset=" + timeset);
            if(timeset > milliseconds_minute){
                hygienePenalty=timeset;
                logger.info(fName + ".hygienePenalty=" + hygienePenalty);
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningPenalty, hygienePenalty);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Set hygiene penalty:"+displayDuration(hygienePenalty), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Set hygiene penalty for "+gTarget.getAsMention()+":"+displayDuration(hygienePenalty), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder set your hygiene penalty:"+displayDuration(hygienePenalty), llColorPurple1);
                }
            }else{
                logger.info(fName + ".hygienePenalty:disable");
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningPenalty, 0);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Disabled hygiene penalty.", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Disabled hygiene penalty for "+gTarget.getAsMention()+".", llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder disabled your hygiene penalty.", llColorPurple1);
                }
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set hygiene opening duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void hygineSetCountPeriod(){
        String fName = "[hygineSetCountPeriod]";
        logger.info(fName);
        try {
            if(gTarget==null) {
                logger.info(fName + ".me");
                if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                    return;
                }
            }else{
                if(!loadedProfile(gTarget.getUser())){
                    Failed2LoadProfile(gUser,gTarget); return;}
            }
            loadDUserValues();
            if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

            String []items = gEvent.getArgs().split("\\s+");
            logger.info(fName + ".items.size=" + items.length);
            int count = 0;
            int period = 0;
            for (int i = 0; i < items.length; i++) {
                logger.info(fName + ".iteme=" + items[i]);

                if (items[i].equalsIgnoreCase("d") && i != 0) {
                    count = Integer.parseInt(items[i - 1]) ;period=0;
                }
                if (items[i].equalsIgnoreCase("w") && i != 0) {
                    count = Integer.parseInt(items[i - 1]) ;period=1;
                }
                if (items[i].equalsIgnoreCase("m") && i != 0) {
                    count = Integer.parseInt(items[i - 1]) ;period=2;
                }
            }
            logger.info(fName + ".count=" + count);logger.info(fName + ".period=" + period);
            if(count>0){
                isHygieneActive =true;
                hygienePeriod=period;
                hygieneCount=count;
                logger.info(fName + ".hygienePeriod=" + hygienePeriod);
                logger.info(fName + ".hygieneCount=" + hygieneCount);
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningActive, isHygieneActive);
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningPeriod, hygienePeriod);
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningCount, hygieneCount);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                String tmp="";
                if(period==2){
                    tmp+=count+" monthly";
                }else
                if(period==1){
                    tmp+=count+" weekly";
                }else{
                    tmp+=count+" daily";
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Set hygiene opening "+tmp, llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Set hygiene opening for "+gTarget.getAsMention()+" to "+tmp, llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder set hygiene opening "+tmp, llColorPurple1);
                }
            }else{
                isHygieneActive =false;
                gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningActive, isHygieneActive);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Disabled hygiene opening.", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Disabled hygiene opening for "+gTarget.getAsMention()+".", llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder disabled hygiene opening.", llColorPurple1);
                }
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set hygiene opening duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void hygineUnlock(){
        String fName = "[hygineUnlock]";
        logger.info(fName);
        if(gTarget!=null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Only the wearer can perform hygiene unlocks!", llColorRed); return;
        }
        logger.info(fName + ".me");
        if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
        loadDUserValues();
        if (!isActive) { llSendQuickEmbedMessage(gUser, sRTitle, "Session is not active!", llColorRed);return; }
        if (isHygieneOpen) { llSendQuickEmbedMessage(gUser, sRTitle, "Already open!", llColorRed);return; }
        logger.info(fName + ".hygieneUsed=" + hygieneUsed);
        if(hygieneUsed>=hygieneCount) { llSendQuickEmbedMessage(gUser, sRTitle, "Don't have any count remaining!", llColorRed);return; }
        String title="Hygiene Opening";
        logger.info(fName + ".hygieneUsed=" + hygieneUsed);
        Message message=llSendQuickEmbedMessageResponse(gUser, title, "Are you sure you want to open it?", llColorPurple1);
        message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
        message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        llMessageDelete(e.getChannel(),e.getMessageId());
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                            File file=null;
                            if(lockBoxImageFile!=null&&!lockBoxImageFile.isBlank()&&!lockBoxImageFile.isEmpty()){
                                file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageFile);
                            }else
                            if(lockBoxImageName!=null&&!lockBoxImageName.isBlank()&&!lockBoxImageName.isEmpty()){
                                if(lockBoxImageExt!=null&&!lockBoxImageExt.isBlank()&&!lockBoxImageExt.isEmpty()){
                                    file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName);
                                }else{
                                    file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName+"."+lockBoxImageExt);
                                }
                            }
                            if(file!=null){
                                InputStream fileStream = new FileInputStream(file);
                                lcTempZipFile zipFile=new lcTempZipFile();
                                zipFile.addEntity("code."+lockBoxImageExt,fileStream);
                                InputStream targetStream = zipFile.getInputStream();
                                PrivateChannel privateChannel=null;
                                try {
                                    privateChannel=gUser.openPrivateChannel().complete();
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                                Message lockBox0=null,lockBox1=null;
                                try {
                                    if(privateChannel!=null){
                                        lockBox0=privateChannel.sendMessage("Here is your lockbox key.").addFile(file,"code."+lockBoxImageExt).complete();
                                    }else{
                                        lockBox0=gTextChannel.sendMessage("Here is your lockbox key.").addFile(file,"code."+lockBoxImageExt).complete();
                                    }
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                                try {
                                    if(privateChannel!=null){
                                        lockBox1=privateChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                    }else{
                                        lockBox1=gTextChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                    }
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                                if(lockBox0==null&&lockBox1==null){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to stop session!", llColorRed);
                                    return;
                                }
                            }else{
                                llSendQuickEmbedMessage(gUser, sRTitle, "Unlocked for cleaning", llColorGreen1);
                            }
                            llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" unlocked their chastity for cleaning!", llColorGreen1);
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            logger.info(fName+".timestamp="+timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningIsOpen, true);
                            gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningUnlockDate, timestamp.getTime());
                            if (!saveProfile()) {
                                llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                            }
                            return;
                        }
                    } catch (Exception ex) {
                        llSendQuickEmbedMessage(gUser, title, "Failed to open for hygiene!", llColorRed);
                        logger.error(fName + "  exception:"+ex);
                    }
                },
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> {
                    llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed);
                });
    }
    private void hygineUnlockTextChannel(){
        String fName = "[hygineUnlock]";
        logger.info(fName);
        if(gTarget!=null){
            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Only the wearer can perform hygiene unlocks!", llColorRed);
         return;
        }
        logger.info(fName + ".me");
        if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
        loadDUserValues();
        if (!isActive) {
            llSendQuickEmbedMessage(gUser, sRTitle, "Session is not active!", llColorRed);
            return; }
        if (isHygieneOpen) {
            llSendQuickEmbedMessage(gUser, sRTitle, "Already open!", llColorRed);
            return; }
        logger.info(fName + ".hygieneUsed=" + hygieneUsed);
        if(hygieneUsed>=hygieneCount) {
            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Don't have any count remaining!", llColorRed);
            return; }
        String title="Hygiene Opening";
        logger.info(fName + ".hygieneUsed=" + hygieneUsed);
        Message message=llSendQuickEmbedMessageResponse(gTextChannel, title, gMember.getAsMention()+", are you sure you want to open it?", llColorPurple1);
        message.addReaction(lsUnicodeEmotes.unicodeEmote_X_Red).queue();
        message.addReaction(gGlobal.emojis.getEmoji("white_check_mark")).queue();
        gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                e -> {
                    try {
                        lsMessageHelper.lsMessageDelete(message);
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        llMessageDelete(e.getChannel(),e.getMessageId());
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("white_check_mark"))){
                            File file=null;
                            if(lockBoxImageFile!=null&&!lockBoxImageFile.isBlank()&&!lockBoxImageFile.isEmpty()){
                                file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageFile);
                            }else
                            if(lockBoxImageName!=null&&!lockBoxImageName.isBlank()&&!lockBoxImageName.isEmpty()){
                                if(lockBoxImageExt!=null&&!lockBoxImageExt.isBlank()&&!lockBoxImageExt.isEmpty()){
                                    file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName);
                                }else{
                                    file=new File(pathLSave4Lockbox.replaceAll("userid",gUser.getId())+"/"+ lockBoxImageName+"."+lockBoxImageExt);
                                }
                            }
                            if(file!=null){
                                InputStream fileStream = new FileInputStream(file);
                                lcTempZipFile zipFile=new lcTempZipFile();
                                zipFile.addEntity("code."+lockBoxImageExt,fileStream);
                                InputStream targetStream = zipFile.getInputStream();
                                PrivateChannel privateChannel=null;
                                try {
                                    privateChannel=gUser.openPrivateChannel().complete();
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                                Message lockBox0=null,lockBox1=null;
                                try {
                                    if(privateChannel!=null){
                                        lockBox0=privateChannel.sendMessage("Here is your lockbox key, "+gMember.getAsMention()+".").addFile(file,"code."+lockBoxImageExt).complete();
                                    }else{
                                        lockBox0=gTextChannel.sendMessage("Here is your lockbox key, "+gMember.getAsMention()+".").addFile(file,"code."+lockBoxImageExt).complete();
                                    }
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                                try {
                                    if(privateChannel!=null){
                                        lockBox1=privateChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                    }else{
                                        lockBox1=gTextChannel.sendMessage("Also zip of it.").addFile(targetStream,"lockbox.zip").complete();
                                    }
                                }catch (Exception e2){
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                                if(lockBox0==null&&lockBox1==null){
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Failed to stop session!", llColorRed);
                                    return;
                                }
                            }else{
                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Unlocked for cleaning", llColorGreen1);
                            }
                            llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" unlocked their chastity for cleaning!", llColorGreen1);
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            logger.info(fName+".timestamp="+timestamp.getTime());
                            gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningIsOpen, true);
                            gUserProfile.putFieldEntry(fieldHygieneOpening, keyHygieneOpeningUnlockDate, timestamp.getTime());
                            if (!saveProfile()) {
                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to update profile!", llColorRed);
                            }
                            return;
                        }
                    } catch (Exception ex) {
                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to open for hygiene!", llColorRed);
                        logger.error(fName + "  exception:"+ex);
                    }
                },
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> {
                    lsMessageHelper.lsMessageDelete(message);
                });
    }
    private void hygineLock(){
        String fName = "[hygineLock]";
        logger.info(fName);
        if(gTarget!=null){
            llSendQuickEmbedMessage(gUser,sRTitle,"Only the wearer can perform hygiene locks!", llColorRed); return;
        }
        logger.info(fName + ".me");
        if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
        loadDUserValues();
        if (!isActive) { llSendQuickEmbedMessage(gUser, sRTitle, "Session is not active!", llColorRed);return; }
        if (!isHygieneOpen) { llSendQuickEmbedMessage(gUser, sRTitle, "Not unlocked!", llColorRed);return; }
        getRemaining();
        String title="Hygiene Closing";
        Message message=llSendQuickEmbedMessageResponse(gUser, title, "Attach combination pic to close it?\nIn case you cant upload pic type `noimg` or 'channel' in attemt to ask it from text channel.\nIf you want to cancel it type `cancel`.", llColorPurple1);
        gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel, and for safety, a different message
                e -> e.getAuthor().equals(gUser),
                // respond, inserting the name they listed into the response
                e -> {
                    try {
                        lsMessageHelper.lsMessageDelete(message);
                        Map<String,Guild>guilds=gGlobal.getGuildMap4JDA();
                        String content = e.getMessage().getContentStripped();
                        logger.info(fName + "text=" + content);
                        if (content.equalsIgnoreCase("cancel")) {
                            llSendQuickEmbedMessage(gUser, title, "Canceled!", llColorRed);
                            return;
                        }
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        logger.info(fName+".timestamp="+timestamp.getTime());
                        logger.info(fName+".remainingHygieneDuration="+remainingHygieneDuration);
                        if(remainingHygieneDuration<=0){
                            logger.info(fName+".punish");
                            logger.info(fName+".penalty="+hygienePenalty);
                            if(hygienePenalty>0){
                                totalDuration+=hygienePenalty;
                                logger.info(fName + ".totalDuration=" + totalDuration);
                                gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
                            }
                        }
                        gUserProfile.putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLockDate,timestamp.getTime());
                        gUserProfile.putFieldEntry(fieldHygieneOpening,keyHygieneOpeningIsOpen,false);
                        hygieneUsed++;
                        logger.info(fName + ".hygieneUsed=" + hygieneUsed);
                        gUserProfile.putFieldEntry(fieldHygieneOpening,keyHygieneOpeningUsed,hygieneUsed);
                        if (content.equalsIgnoreCase("channel")) {
                           hygineLockTextChannel();
                           return;
                        }
                        if (content.equalsIgnoreCase("noimg")) {
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");

                            if (!saveProfile()) {
                                llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                return;
                            }
                            llSendQuickEmbedMessage(gUser, title, "Hygiene unlock ended!", llColorGreen1);
                            llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" ended their hygiene unlock and are locked back up.", llColorGreen1);
                            return;
                        }else{
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            logger.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    logger.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessage(gUser, title, "The attachment is not an image!", llColorRed);
                                    logger.error(fName + "  not image");
                                    return;
                                }
                                if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                    logger.error(fName + ".image too big");
                                    llSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                    return;
                                }
                                if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                    logger.error(fName + ".image too big");
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "The image is above 10MB", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                    return;
                                }
                                String attachmentUrl=attachment.getUrl();
                                logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                String fileName=attachment.getFileName();
                                String fileExtension=attachment.getFileExtension();
                                String fileId=attachment.getId();
                                logger.info(fName + "  fileName=" + fileName);
                                logger.info(fName + "  fileExtension=" + fileExtension);
                                logger.info(fName + "  fileId=" + fileId);

                                String folderPath= pathLSave4Lockbox.replaceAll("userid",gUser.getId());
                                logger.info(fName + " folderPath=" + folderPath);
                                File folder=new File(folderPath);
                                if(folder.exists()){
                                    logger.info(fName +"Directory exists");
                                }
                                boolean bool = folder.mkdir();
                                if(bool){
                                    logger.info(fName +"Directory created successfully");
                                }else{
                                    logger.warn(fName +"Sorry couldn’t create specified directory");
                                }

                                String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                logger.info(fName +"filePath="+filePath);
                                File file = new File(filePath);
                                if (file.createNewFile()) {
                                    logger.info(fName + " created");
                                } else {
                                    logger.info(fName + " already exists");
                                }

                                OutputStream out = new FileOutputStream(file);
                                out.close();

                                attachment.downloadToFile(file).thenAccept(filex -> {
                                    if(!filex.exists()){
                                        logger.error(fName + "  file does not exists");
                                    }else{
                                        logger.info(fName + "  file exists");
                                        gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, fileId+"."+fileExtension);
                                        gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, fileId);
                                        gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, fileExtension);
                                        if (!saveProfile()) {
                                            llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                            return;
                                        }
                                        llSendQuickEmbedMessage(gUser, title, "Hygiene unlock ended!", llColorGreen1);
                                        llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" ended their hygiene unlock aand are locked back up.", llColorGreen1);
                                    }
                                }).exceptionally(t -> { // handle failure
                                    llSendQuickEmbedMessage(gUser, title, "Failed to process image!", llColorRed);
                                    logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                    return null;
                                });

                            }else{
                                llSendQuickEmbedMessage(gUser, title, "Zero attachment!", llColorRed);
                                logger.error(fName + "  zero attachment");
                            }
                        }
                    }catch (Exception ex) {
                        llSendQuickEmbedMessage(gUser, title, "Failed to do command!", llColorRed);
                        logger.error(fName + "  exception:"+ex);
                    }

                },
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> {
                    llSendQuickEmbedMessage(gUser, title, "Timeout", llColorRed);
                });
    }
    private void hygineLockTextChannel(){
        String fName = "[hygineLockTextChannel]";
        logger.info(fName);
        if(gTarget!=null){
            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser,sRTitle,"Only the wearer can perform hygiene locks!", llColorRed); return;
        }
        logger.info(fName + ".me");
        if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
        loadDUserValues();
        if (!isActive) { llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Session is not active!", llColorRed);return; }
        if (!isHygieneOpen) { llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "Not unlocked!", llColorRed);return; }
        getRemaining();
        String title="Hygiene Closing";
        Message message=llSendMessageResponse(gTextChannel, gUser.getAsMention()+", attach combination pic to close it?\nIn case you cant upload pic type `noimg`.\nIf you want to cancel it type `cancel`.");
        gGlobal.waiter.waitForEvent(GuildMessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel, and for safety, a different message
                e -> e.getAuthor().equals(gUser)&&e.getChannel()==gTextChannel,
                // respond, inserting the name they listed into the response
                e -> {
                    try {
                        Map<String,Guild>guilds=gGlobal.getGuildMap4JDA();
                        String content = e.getMessage().getContentStripped();
                        logger.info(fName + "text=" + content);
                        if (content.equalsIgnoreCase("cancel")) {
                            llSendQuickEmbedMessage(gTextChannel, title, "Canceled!", llColorRed);
                            return;
                        }
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        logger.info(fName+".timestamp="+timestamp.getTime());
                        logger.info(fName+".remainingHygieneDuration="+remainingHygieneDuration);
                        if(remainingHygieneDuration<=0){
                            logger.info(fName+".punish");
                            logger.info(fName+".penalty="+hygienePenalty);
                            if(hygienePenalty>0){
                                totalDuration+=hygienePenalty;
                                logger.info(fName + ".totalDuration=" + totalDuration);
                                gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
                            }
                        }
                        gUserProfile.putFieldEntry(fieldHygieneOpening,keyHygieneOpeningLockDate,timestamp.getTime());
                        gUserProfile.putFieldEntry(fieldHygieneOpening,keyHygieneOpeningIsOpen,false);
                        hygieneUsed++;
                        logger.info(fName + ".hygieneUsed=" + hygieneUsed);
                        gUserProfile.putFieldEntry(fieldHygieneOpening,keyHygieneOpeningUsed,hygieneUsed);

                        if (content.equalsIgnoreCase("noimg")) {
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, "");
                            gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, "");

                            if (!saveProfile()) {
                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to update profile!", llColorRed);
                                return;
                            }
                            llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" ended their hygiene unlock and are locked back up.", llColorGreen1);
                            return;
                        }else{
                            List<Message.Attachment> attachments = e.getMessage().getAttachments();
                            logger.info(fName + ".attachments.size=" + attachments.size());
                            if (attachments.size() > 0) {
                                Message.Attachment attachment = attachments.get(0);
                                if (!attachment.isImage()) {
                                    logger.error(fName + ".attachment is not an image");
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "The attachment is not an image!", llColorRed);
                                    logger.error(fName + "  not image");
                                    return;
                                }
                                if(attachment.getSize()>lsGlobalHelper.lsImageBytesLimit){
                                    logger.error(fName + ".image too big");
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "The image is above 10MB", llColorRed);
                                    return;
                                }
                                if(attachment.getSize()> lsGlobalHelper.lsImageBytesLimit){
                                    logger.error(fName + ".image too big");
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, sRTitle, "The image is above 10MB", llColorRed);
                                    lsMessageHelper.lsMessageDelete(message);
                                    return;
                                }
                                String attachmentUrl=attachment.getUrl();
                                logger.info(fName + "  attachmentUrl=" + attachmentUrl);
                                String fileName=attachment.getFileName();
                                String fileExtension=attachment.getFileExtension();
                                String fileId=attachment.getId();
                                logger.info(fName + "  fileName=" + fileName);
                                logger.info(fName + "  fileExtension=" + fileExtension);
                                logger.info(fName + "  fileId=" + fileId);

                                String folderPath= pathLSave4Lockbox.replaceAll("userid",gUser.getId());
                                logger.info(fName + " folderPath=" + folderPath);
                                File folder=new File(folderPath);
                                if(folder.exists()){
                                    logger.info(fName +"Directory exists");
                                }
                                boolean bool = folder.mkdir();
                                if(bool){
                                    logger.info(fName +"Directory created successfully");
                                }else{
                                    logger.warn(fName +"Sorry couldn’t create specified directory");
                                }

                                String filePath=folderPath+"/"+fileId+"."+fileExtension;
                                logger.info(fName +"filePath="+filePath);
                                File file = new File(filePath);
                                if (file.createNewFile()) {
                                    logger.info(fName + " created");
                                } else {
                                    logger.info(fName + " already exists");
                                }

                                OutputStream out = new FileOutputStream(file);
                                out.close();

                                attachment.downloadToFile(file).thenAccept(filex -> {
                                    if(!filex.exists()){
                                        logger.error(fName + "  file does not exists");
                                    }else{
                                        logger.info(fName + "  file exists");
                                        gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageFile, fileId+"."+fileExtension);
                                        gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageName, fileId);
                                        gUserProfile.putFieldEntry(fieldSession, keySessionLockboxImageExtension, fileExtension);
                                        if (!saveProfile()) {
                                            llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to update profile!", llColorRed);
                                            return;
                                        }
                                        llSendQuickEmbedMessage(gTextChannel, sRTitle, gUserProfile.getUser().getAsMention()+" ended their hygiene unlock and are locked back up.", llColorGreen1);
                                    }
                                }).exceptionally(t -> { // handle failure
                                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to process image!", llColorRed);
                                    logger.error(fName + "  exception:"+t); logger.error(".exception:" + Arrays.toString(t.getStackTrace()));
                                    return null;
                                });

                            }else{
                                llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Zero attachment!", llColorRed);
                                logger.error(fName + "  zero attachment");
                            }
                        }
                    }catch (Exception ex) {
                        llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Failed to do command!", llColorRed);
                        logger.error(fName + "  exception:"+ex);
                    }

                },
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> {
                    llSendQuickEmbedMessageWithAuthor(gTextChannel,gUser, title, "Timeout", llColorRed);
                });
    }

    private void friendVotingEnableAdd(String option){
        String fName = "[friendVotingEnableAdd]";
        logger.info(fName);
        logger.info(fName+"option:"+option);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                return;
            }

        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

        Boolean select=false;
        if(option.equalsIgnoreCase("true")||option.equalsIgnoreCase("1")){select=true;}
        else if(option.equalsIgnoreCase("false")||option.equalsIgnoreCase("0")){select=false;}
        else{{select=!isFriendEnableAdd;}}
        logger.info(fName+"select="+select);
        gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendEnableAdd,select);
        gUserProfile.safetyPutFieldEntry(fieldVotingFriend,keyVotingFriendAddTime ,milliseconds_hour);
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
            return;
        }
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser, sRTitle, "Friend voting add: "+select, llColorPurple1);
        }else{
            llSendQuickEmbedMessage(gUser,sRTitle,"You set "+gTarget.getAsMention()+" friend voting add: "+select, llColorPurple1);
            llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder set friend voting add: "+select, llColorPurple1);
        }
    }
    private void friendVotingEnableSub(String option){
        String fName = "[friendVotingEnableSub]";
        logger.info(fName);
        logger.info(fName+"option:"+option);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                return;
            }
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }

        Boolean select=false;
        if(option.equalsIgnoreCase("true")||option.equalsIgnoreCase("1")){select=true;}
        else if(option.equalsIgnoreCase("false")||option.equalsIgnoreCase("0")){select=false;}
        else{{select=!isFriendEnableSub;}}
        logger.info(fName+"select="+select);
        gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendEnableSub,select);
        gUserProfile.safetyPutFieldEntry(fieldVotingFriend,keyVotingFriendSubTime ,milliseconds_hour);
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
            return;
        }
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser, sRTitle, "Friend voting sub: "+select, llColorPurple1);
        }else{
            llSendQuickEmbedMessage(gUser,sRTitle,"You set "+gTarget.getAsMention()+" friend voting sub: "+select, llColorPurple1);
            llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder set friend voting sub: "+select, llColorPurple1);
        }
    }
    private void friendVotingEnableRandom(String option){
        String fName = "[friendVotingEnableRandom]";
        logger.info(fName);
        logger.info(fName+"option:"+option);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                return;
            }
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }
        Boolean select=false;
        if(option.equalsIgnoreCase("true")||option.equalsIgnoreCase("1")){select=true;}
        else if(option.equalsIgnoreCase("false")||option.equalsIgnoreCase("0")){select=false;}
        else{{select=!isFriendEnableRand;}}
        logger.info(fName+"select="+select);
        gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendEnableRandom,select);
        gUserProfile.safetyPutFieldEntry(fieldVotingFriend,keyVotingFriendAddTime ,milliseconds_hour);
        gUserProfile.safetyPutFieldEntry(fieldVotingFriend,keyVotingFriendSubTime ,milliseconds_hour);
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
            return;
        }
        if(gTarget==null){
            llSendQuickEmbedMessage(gUser, sRTitle, "Friend voting random: "+select, llColorPurple1);
        }else{
            llSendQuickEmbedMessage(gUser,sRTitle,"You set "+gTarget.getAsMention()+" friend voting random: "+select, llColorPurple1);
            llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Your keyholder set friend voting random: "+select, llColorPurple1);
        }
    }
    private void friendVotingSetAddTime(){
        String fName = "[friendVotingSetAddTime]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                return;
            }
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess");SendDenied(gUser); return; }
        String []items = gEvent.getArgs().split("\\s+");
        logger.info(fName + ".items.size=" + items.length);
        try {
            long timeset = 0;
            for (int i = 0; i < items.length; i++) {
                logger.info(fName + ".iteme=" + items[i]);

                if (items[i].equalsIgnoreCase("m") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day * 30;
                }
                if (items[i].equalsIgnoreCase("w") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_week;
                }
                if (items[i].equalsIgnoreCase("d") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day;
                }
                if (items[i].equalsIgnoreCase("h") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                }
            }
            logger.info(fName + ".timeset=" + timeset);
            if(timeset > milliseconds_hour){
                gUserProfile.putFieldEntry(fieldVotingFriend, keyVotingFriendAddTime,timeset);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Friend voting add time set to: "+displayDuration(timeset), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Updated "+gTarget.getAsMention() +" friend voting add time to: "+displayDuration(timeset), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Keyholder set your friend voting add time to: "+displayDuration(timeset), llColorPurple1);
                }
            }else{
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set!", llColorRed);
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set!", llColorRed);
        }
    }
    private void friendVotingSetSubTime(){
        String fName = "[friendVotingSetSubTime]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);
                return;
            }
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }
        String []items = gEvent.getArgs().split("\\s+");
        logger.info(fName + ".items.size=" + items.length);
        try {
            long timeset = 0;
            for (int i = 0; i < items.length; i++) {
                logger.info(fName + ".iteme=" + items[i]);

                if (items[i].equalsIgnoreCase("m") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day * 30;
                }
                if (items[i].equalsIgnoreCase("w") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_week;
                }
                if (items[i].equalsIgnoreCase("d") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day;
                }
                if (items[i].equalsIgnoreCase("h") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                }
            }
            logger.info(fName + ".timeset=" + timeset);
            if(timeset > milliseconds_hour){
                gUserProfile.putFieldEntry(fieldVotingFriend, keyVotingFriendSubTime,timeset);
                if(!saveProfile()){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                    return;
                }
                if(gTarget==null){
                    llSendQuickEmbedMessage(gUser, sRTitle, "Friend voting sub time set to: "+displayDuration(timeset), llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"Updated "+gTarget.getAsMention() +" friend voting sub time to: "+displayDuration(timeset), llColorPurple1);
                    llSendQuickEmbedMessage(gTarget.getUser(),sRTitle,"Keyholder set your friend voting sub time to: "+displayDuration(timeset), llColorPurple1);
                }
            }else{
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set!", llColorRed);
            }
        } catch(Exception ex){
            llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set!", llColorRed);
        }
    }
    private void friendVotingDoAdd(){
        String fName = "[friendVotingEnableRandom]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
            llSendQuickEmbedMessage(gUser, sRTitle, "You can't vote on yourself!", llColorRed);return;
        }else{
            if(!loadedProfile(gTarget.getUser())){ Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }
        getRemaining();
        /*if(friendDiffTime<milliseconds_day){
            llSendQuickEmbedMessage(gUser, sRTitle, "You need to wait a day to vote!", llColorRed);return;
        }*/
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        logger.info(fName+".timestamp="+timestamp.getTime());
        friendUsers.put(gUser.getId(),timestamp.getTime());
        gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendUsers,friendUsers);
        totalDuration+=friendAddTime;
        logger.info(fName + ".totalDuration=" + totalDuration);
        if(totalDuration>maxDuration){
            llSendQuickEmbedMessage(gUser, sRTitle, "Can't add more time than the max duration!", llColorRed);
            return;
        }
        gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
            return;
        }
        llSendQuickEmbedMessage(gUser,sRTitle,"You voted on "+gTarget.getAsMention()+" and added time: "+displayDuration(friendAddTime), llColorPurple1);
    }
    private void friendVotingDoSub(){
        String fName = "[friendVotingDoSub]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
            llSendQuickEmbedMessage(gUser, sRTitle, "You can't vote on yourself!", llColorRed);return;
        }else{
            if(!loadedProfile(gTarget.getUser())){ Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }
        getRemaining();
        /*if(friendDiffTime<milliseconds_day){
            llSendQuickEmbedMessage(gUser, sRTitle, "You need to wait a day to vote!", llColorRed);return;
        }*/
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        logger.info(fName+".timestamp="+timestamp.getTime());
        friendUsers.put(gUser.getId(),timestamp.getTime());
        gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendUsers,friendUsers);
        totalDuration-=friendSubTime;
        logger.info(fName + ".totalDuration=" + totalDuration);
        if(totalDuration<minDuration){
            llSendQuickEmbedMessage(gUser, sRTitle, "Can't subtract more time than the min duration!", llColorRed);
            return;
        }
        gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
        if(!saveProfile()){
            llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
            return;
        }
        llSendQuickEmbedMessage(gUser,sRTitle,"You voted on "+gTarget.getAsMention()+" and subtracted time: "+displayDuration(friendSubTime), llColorPurple1);
    }
    private void friendVotingDoRandom(){
        String fName = "[friendVotingDoSub]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName + ".me");
            if (!loadedProfile(gUser)) {Failed2LoadProfile(gUser);return; }
            llSendQuickEmbedMessage(gUser, sRTitle, "You can't vote on yourself!", llColorRed);return;
        }else{
            if(!loadedProfile(gTarget.getUser())){
                Failed2LoadProfile(gUser,gTarget); return;}
        }
        loadDUserValues();
        if(!gIsOverride&&!accessApproveResponse()){ logger.info(fName+"noaccess"); SendDenied(gUser);return; }
        getRemaining();
        /*if(friendDiffTime<milliseconds_day){
            llSendQuickEmbedMessage(gUser, sRTitle, "You need to wait a day to vote!", llColorRed);return;
        }*/
        List<String>list=new ArrayList<>();
        if(isFriendEnableAdd){list.add("add");} if(isFriendEnableSub){list.add("sub");} if(isFriendEnableRand){list.add("radd");}if(isFriendEnableRand){list.add("rsub");}
        Random rand = new Random();
        logger.info(fName+"list.size="+list.size());
        int randint = rand.nextInt(list.size());
        logger.info(fName+"randint="+randint);
        String selected=list.get(randint);
        logger.info(fName+"selected="+selected);
        if(selected.equalsIgnoreCase("add")||selected.equalsIgnoreCase("radd")){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            friendUsers.put(gUser.getId(),timestamp.getTime());
            gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendUsers,friendUsers);
            totalDuration+=friendAddTime;
            logger.info(fName + ".totalDuration=" + totalDuration);
            if(totalDuration>maxDuration){
                llSendQuickEmbedMessage(gUser, sRTitle, "Can't add more time than the max duration!", llColorRed);
                return;
            }
            gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                return;
            }
            llSendQuickEmbedMessage(gUser,sRTitle,"You voted on "+gTarget.getAsMention()+" and added time: "+displayDuration(friendAddTime), llColorPurple1);
        }
        if(selected.equalsIgnoreCase("sub")||selected.equalsIgnoreCase("rsub")){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            friendUsers.put(gUser.getId(),timestamp.getTime());
            gUserProfile.putFieldEntry(fieldVotingFriend,keyVotingFriendUsers,friendUsers);
            totalDuration-=friendSubTime;
            logger.info(fName + ".totalDuration=" + totalDuration);
            if(totalDuration<minDuration){
                llSendQuickEmbedMessage(gUser, sRTitle, "Can't subtract more time than the min duration!", llColorRed);
                return;
            }
            gUserProfile.putFieldEntry(fieldDuration, keyDuration0TotalDuration, totalDuration);
            if(!saveProfile()){
                llSendQuickEmbedMessage(gUser, sRTitle, "Failed to update profile!", llColorRed);
                return;
            }
            llSendQuickEmbedMessage(gUser,sRTitle,"You voted on "+gTarget.getAsMention()+" and subtracted time: "+displayDuration(friendSubTime), llColorPurple1);
        }
    }

    private Boolean accessApproveResponse(){
        String fName = "[accessApproveResponse]";
        logger.info(fName);
        if(gTarget==null) {
            logger.info(fName+"me");
            if(!keyHolder.isEmpty()&&!gUser.getId().equalsIgnoreCase(keyHolder)&&keyHolderAccepted){
                logger.info(fName+"has keyholder and is not equial to wearer");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! Only keyholder can change this!", llColorRed); return false;
            }
            logger.info(fName+"has no keyholder or is equal to wearer");
            return true;
        }else{
            logger.info(fName+"target");
            if(keyHolder.isEmpty()){
                logger.info(fName+"emtpy");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their keyholder!", llColorRed); return false;
            }
            if(!gUser.getId().equalsIgnoreCase(keyHolder)){
                logger.info(fName+"nomatch");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You are not their keyholder!", llColorRed); return false;
            }
            if(!keyHolderAccepted){
                logger.info(fName+"noaccepted");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied! You did not accepted their offer yet.", llColorRed); return false;
            }
            logger.info(fName+"has no keyholder");
            return true;
        }

    }
    private Boolean isTargeted(){
        String fName = "[isTargeted]";
        logger.info(fName);
        try{ String[] items = gEvent.getArgs().split("\\s+");
            logger.info(fName + ".items.size=" + items.length);
            logger.info(fName + ".items[0]=" + items[0]);
            if((items[0].contains("<@")&&items[0].contains(">"))||(items[0].contains("<@!")&&items[0].contains(">"))){
                String tmp=items[0].replace("<@!","").replace("<@","").replace(">","");
                Member m=gGuild.getMemberById(tmp);
                if(m!=null){
                    if(m.getId().equals(gUser.getId())){
                        logger.info(fName + ".target same");
                        return false;
                    }
                    logger.info(fName + ".target ok");
                    gTarget=m;
                    return true;
                }
            }
            logger.info(fName + ".target none");
            return false;
        }
        catch(Exception ex){
            logger.error(fName + ".exception: "+ex);
            return false;
        }
    }

    Calendar gCalendarToday;Calendar gCalendarStart;Calendar gCalendarEnd;Calendar gCalendarHygineOpen;Calendar gCalendarFriendLastVoting;
    private void getTodayDate(){
        String fName="[getTodayDate]";
        logger.info(fName);
        gCalendarToday = Calendar.getInstance();
        logger.info(fName + ".today:"+gCalendarToday.get(gCalendarToday.YEAR)+"|"+gCalendarToday.get(gCalendarToday.MONTH)+"|"+gCalendarToday.get(gCalendarToday.DAY_OF_MONTH)+"@"+gCalendarToday.get(gCalendarToday.HOUR_OF_DAY)+gCalendarToday.get(gCalendarToday.MINUTE));
    }
    private void getStartDate(){
        String fName="[getStartDate]";
        logger.info(fName);
        gCalendarStart = Calendar.getInstance();
        if(startingDate!=null){
            logger.info(fName+ " startingDate="+startingDate);
            gCalendarStart.setTimeInMillis(startingDate);
        }
        logger.info(fName + ".today:"+gCalendarStart.get(gCalendarStart.YEAR)+"|"+gCalendarStart.get(gCalendarStart.MONTH)+"|"+gCalendarStart.get(gCalendarStart.DAY_OF_MONTH)+"@"+gCalendarStart.get(gCalendarStart.HOUR_OF_DAY)+":"+gCalendarStart.get(gCalendarStart.MINUTE));
    }
    private void getEndDate(){
        String fName="[getEndDate]";
        logger.info(fName);
        gCalendarEnd = Calendar.getInstance();
        if(startingDate!=null&&totalDuration!=null) {
            logger.info(fName + "startingDate=" + startingDate);
            logger.info(fName + "totalDuration=" + totalDuration);
            Long t = startingDate + totalDuration;
            logger.info(fName + "t=" + t);
            gCalendarEnd.setTimeInMillis(t);
        }
        logger.info(fName + ".today:"+gCalendarEnd.get(gCalendarEnd.YEAR)+"|"+gCalendarEnd.get(gCalendarEnd.MONTH)+"|"+gCalendarEnd.get(gCalendarEnd.DAY_OF_MONTH)+"@"+gCalendarEnd.get(gCalendarEnd.HOUR_OF_DAY)+":"+gCalendarEnd.get(gCalendarEnd.MINUTE));
    }
    private void getHygieneOpen(){
        String fName="[getHygieneOpe]";
        logger.info(fName);
        gCalendarHygineOpen = Calendar.getInstance();
        if(hygieneUnlockDate!=null) {
            logger.info(fName + "hygieneUnlockDate=" + hygieneUnlockDate);
            gCalendarHygineOpen.setTimeInMillis(hygieneUnlockDate);
        }
        logger.info(fName + ".today:"+gCalendarHygineOpen.get(gCalendarHygineOpen.YEAR)+"|"+gCalendarHygineOpen.get(gCalendarHygineOpen.MONTH)+"|"+gCalendarHygineOpen.get(gCalendarHygineOpen.DAY_OF_MONTH)+"@"+gCalendarHygineOpen.get(gCalendarHygineOpen.HOUR_OF_DAY)+":"+gCalendarHygineOpen.get(gCalendarHygineOpen.MINUTE));
    }
    private void getFriendLastVoting(){
        String fName="[getHygieneOpe]";
        logger.info(fName);
        gCalendarFriendLastVoting = Calendar.getInstance();
        Long temp=null;
        if(friendUsers.isEmpty()){
            logger.info(fName + "isEmpty");
            gCalendarFriendLastVoting.setTimeInMillis(0);
        }
        if(friendUsers.has(gUser.getId())){
            logger.info(fName + "isEmpty");
            temp=friendUsers.getLong(gUser.getId());
            gCalendarFriendLastVoting.setTimeInMillis(0);
        }
        if(temp!=null) {
            logger.info(fName + "temp=" + temp);
            gCalendarFriendLastVoting.setTimeInMillis(temp);
        }
        logger.info(fName + ".today:"+gCalendarFriendLastVoting.get(gCalendarFriendLastVoting.YEAR)+"|"+gCalendarFriendLastVoting.get(gCalendarFriendLastVoting.MONTH)+"|"+gCalendarFriendLastVoting.get(gCalendarFriendLastVoting.DAY_OF_MONTH)+"@"+gCalendarFriendLastVoting.get(gCalendarFriendLastVoting.HOUR_OF_DAY)+":"+gCalendarFriendLastVoting.get(gCalendarFriendLastVoting.MINUTE));
    }
    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+"chastity ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);embed.setTitle(sRTitle);


        if(command.equalsIgnoreCase("session")){
            embed.addField("Start session","Type `"  + quickSummonWithSpace + "startsession` starts session.\nIt will ask you to upload an image or can choice no image.\nIn case of using an image, please delete it after upload so you dont cheat :P",false);
            embed.addField("End session","Type `"  + quickSummonWithSpace + "stopsession` terminates session.",false);
            desc="`"  + quickSummonWithSpace + "[@wearer] duration start <time>` sets the start time. Once the session started this can't be changed!";
            desc+="\n`"  + quickSummonWithSpace + "[@wearer] duration max <time>` sets the max. Max can't be smaller than min!";
            desc+="\n`"+ quickSummonWithSpace + "[@wearer] duration min <time>` sets the min. Min can't be bigger than max!";
            embed.addField("Set session durations",desc,false);
            desc = "[@wearer] is an optional parameter, its used when targeting the command on somebody and not on yourself";
            desc += "\nSetting the time parameter: [ x range y range...] where x,y... are integer numbers and range is m for month, w for week, h for hour";
            embed.addField("Parameters",desc,false);desc="N/a";
        }else
        if(command.equalsIgnoreCase("keyholder")){
            embed.addField("Add keyholder","Type `"  + quickSummonWithSpace + "keyholder add <@mention>` adds mentioned user as keyholder.\nThey still need to accept it to be confirmed!",false);
            embed.addField("Remove suggested keyholder","Type `"  + quickSummonWithSpace + "keyholder remove` removes the currenly suggested keyholder.\nAvailable till they did not accepted!\nOnce they accepted it, only they can release you!",false);
            embed.addField("Accept keyholder position","Type `"  +quickSummonWithSpace + "<@wearer> keyholder accept` to accept becoming wearer keyholder",false);
            embed.addField("Reject keyholder position","Type `"  + quickSummonWithSpace + "<@wearer> keyholder reject` to rejects becoming wearer keyholder",false);
            embed.addField("Give back the key","Type `"  +quickSummonWithSpace + "<@wearer> keyholder release` to release wearer from your control",false);
            embed.addField("Parameters","[@wearer] is an optional parameter, its used when targeting the command on somebody and not on yourself",false);
        }else
        if(command.equalsIgnoreCase("duration")){
            desc = "`" + quickSummonWithSpace + "[@wearer] duration start <time>` sets the start time. Once the session started this can't be changed!";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] duration max <time>` sets the max. Max can't be smaller than min!";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] duration min <time>` sets the min. Min can't be bigger than max!";
            embed.addField("Set session durations",desc,false);
            desc = "`" + quickSummonWithSpace + "[@wearer] addtime <time>` to add time";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] subtime <time>` to remove time.";
            embed.addField("Add/sub duration",desc,false);
            desc = "[@wearer] is an optional parameter, its used when targeting the command on somebody and not on yourself";
            desc += "\nSetting the time parameter: [ x range y range...] where x,y... symbolizes integer numbers and range symbolizes m for month, w for week, h for hour";
            embed.addField("Parameters",desc,false);desc="N/a";
        }else
        if(command.equalsIgnoreCase("verification")){
            embed.addField("Perform verification", "\n`" + quickSummonWithSpace + "verification do` to perform verification.\nGives you a code to take a photo with to verify that you are locked.\nIf Discord explicit filter is preventing it, use `do_channel` instead, will ask you to submit in the text channel.",false);
            embed.addField("Display the latest verification","\n`" + quickSummonWithSpace + "verification get` to display it",false);
            desc="`" + quickSummonWithSpace + "[@wearer] verification set <period>` set verification period, to disable enter 0 as time";
            desc += "\nperiod parameter: [ x range y range ], where x symbolizes an integer numbers and range symbolizes d for day, w for week, m for month";
            embed.addField("Options",desc,false);desc="N/a";
        }else
        if(command.equalsIgnoreCase("hygiene")){
            embed.addField("Unlock for cleaning", "\n`" + quickSummonWithSpace + "hygiene start` starts hygiene unlock",false);
            embed.addField("Lock back after cleaning","\n`" + quickSummonWithSpace + "hygiene end` ends hygiene unlock",false);
            desc="`" + quickSummonWithSpace + "[@wearer] hygiene setperiod <period>` set hygiene unlock period, to disable enter 0 as time";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] hygiene setduration <time>` update verification time, to disable enter 0 as time";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] hygiene setpenalty <penalty>` update verification time, to disable enter 0 as time";
            desc += "\nperiod parameter: x period, where x symbolizes an integer numbers and period is d for daily, w for weekly, m for monthly";
            desc += "\ntime parameter: [ x range y range ] where x,y symbolizes integer numbers and range symbolizes m for minute and h for hour";
            desc += "\npenalty parameter: [ x range] where x symbolizes integer number and range symbolizes h for hour, d for day and w for week";
            embed.addField("Options",desc,false);desc="N/a";
        }else
        if(command.equalsIgnoreCase("voting")||command.equalsIgnoreCase("vote")||command.equalsIgnoreCase("friend")||command.equalsIgnoreCase("guest")||command.equalsIgnoreCase("guests")){
            desc="`" + quickSummonWithSpace + "[@wearer] guest enableadd <1/0>` to enable/disable add";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] guest enablesub <1/0>` to enable/disable sub";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] guest enablerandom <1/0>` to enable/disable random, that can add or sub randomly";
            embed.addField("Enabling/Disabling",desc,false);
            desc = "`" + quickSummonWithSpace + "[@wearer] guest setadd <time>` sets the add duration. This is the time that gets added when voting add or get time added from random";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] guest setsub <time>` sets the duration that gets removed. This is the time that gets removed when voting sub or random voting removes time.";
            embed.addField("Set duration to add/sub",desc,false);
            desc += "`" + quickSummonWithSpace + "[@wearer] add` Adds some time to remaining duration.";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] sub` Substratests some time from remaining duration.";
            desc += "\n`" + quickSummonWithSpace + "[@wearer] random` There is choice it will add or remove duration.";
            embed.addField("Action",desc,false);
            desc = "[@wearer] is an optional parameter, its used when targeting the command on somebody and not on yourself";
            desc += "\nSetting the time parameter: [ x range y range...] where x,y... are integer numbers and range is m for month, w for week, h for hour";
            embed.addField("Parameters",desc,false);desc="N/a";
        }else{
            desc="`" + quickSummonWithSpace + "[@target] display` display the session info";
            desc+="\n`" + quickSummonWithSpace + "[@target] add` add duration as guest";
            desc+="\n`" + quickSummonWithSpace + "[@target] sub` remove duration as guest";
            desc+="\n`" + quickSummonWithSpace + "[@target] random` add or remove duration as guest";
            desc+="\n`" + quickSummonWithSpace + "[@target] addtime|subtime <time>` add or remove duration as kh or wearer";
            desc+="\n[@target] is an optional value, only used when trying to use the commands on others and not yourself";
            embed.addField("Quick commands",desc,false);
            embed.addField("Session","Type `" + quickSummonWithSpace + "help session` display setting up session",false);
            embed.addField("Keyholder","Type `" + quickSummonWithSpace + "help keyholder` setting up a keyholder",false);
            embed.addField("verification","Type `" + quickSummonWithSpace + "help verification` setting up and using verification",false);
            embed.addField("Duration","Type `"+ quickSummonWithSpace + "help duration` to display setting up duration",false);
            embed.addField("Hygiene","Type `" + quickSummonWithSpace + "help hygiene` to display hygiene opening settings",false);
            embed.addField("Guests","Type `" + quickSummonWithSpace + "help guest` to display guests voting settings",false);
            //embed.addField("Voting","Type `" + quickSummonWithSpace + "help voting` display voting settings",false);
        }
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);

        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }







   
    private Boolean saveProfile(){
        String fName="[saveProfile]";
        logger.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile(table)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }

    private Boolean loadedProfile(User user){
        String fName="[loadedProfile]";
        logger.info(fName);
        logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
        gUserProfile=gGlobal.getUserProfile(profileName,user);
        if(gUserProfile!=null&&gUserProfile.isProfile()){
            logger.info(fName + ".is locally cached");
        }else{
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(gGlobal,user);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");
            }
        }
        gUserProfile=safetyUserProfileEntry(gUserProfile);gGlobal.putUserProfile(gUserProfile,profileName);
        logger.info(fName + ".isUpdated="+gUserProfile.isUpdated);
        if(!gUserProfile.isUpdated){
            logger.info(fName + ".no update>ignore");return true;
        }
        logger.info(fName + ".is updated");
        if(!saveProfile()){ logger.error(fName+".failed to write in Db");
            llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to write in Db!", llColorRed);}
        return true;
    }

    String keyHolder="";
    Boolean keyHolderAccepted =false;
    Boolean isActive=false;
    String lockBoxImageFile ="",lockBoxImageName="",lockBoxImageExt="",verificationFile="",verificationCode="",verificationImageName="",verificationImageExt="";
    Boolean isVerificationRequired=false;
    Long startingDate=null,totalDuration=null,startingDuration=null,minDuration=null,maxDuration=null,remainingDuration=null,verificationInterval=null,verificationLastDate=null;
    Boolean isHygieneActive =false,isHygieneOpen =false; int hygieneCount=0,hygienePeriod=0, hygieneUsed =0; Long hygieneDuration=null, hygienePenalty=null,hygieneUnlockDate=null,remainingHygieneDuration=null;
    JSONObject friendUsers=new JSONObject();Boolean  isFriendEnableAdd=false, isFriendEnableSub=false, isFriendEnableRand=false;Long friendAddTime=null,friendSubTime=null;int countFriendAdd=0,countFriendSub=0,countFriendRand=0;
    private void loadDUserValues(){
        String fName = "[loadDUserValues]";
        logger.info(fName);
        logger.info(fName+"json="+gUserProfile.jsonObject.toString());
        keyHolder= gUserProfile.getFieldEntryAsString(fieldSession,keySessionKeyHolder);
        keyHolderAccepted = gUserProfile.getFieldEntryAsBoolean(fieldSession,keySessionKeyHolderAccept);
        isActive= gUserProfile.getFieldEntryAsBoolean(fieldSession,keySessionActive);
        isVerificationRequired=gUserProfile.getFieldEntryAsBoolean(fieldSession,keySessionVerificationRequired);
        startingDate= gUserProfile.getFieldEntryAsLong(fieldSession,keySessionStartDate);

        totalDuration= gUserProfile.getFieldEntryAsLong(fieldDuration, keyDuration0TotalDuration);
        startingDuration= gUserProfile.getFieldEntryAsLong(fieldDuration, keyDuration0StartingDuration);
        minDuration= gUserProfile.getFieldEntryAsLong(fieldDuration, keyDuration0MinimumDuration);
        maxDuration= gUserProfile.getFieldEntryAsLong(fieldDuration, keyDuration0MaximumDuration);
        getStartDate();getEndDate();getTodayDate();

        isHygieneActive = gUserProfile.getFieldEntryAsBoolean(fieldHygieneOpening,keyHygieneOpeningActive);
        isHygieneOpen = gUserProfile.getFieldEntryAsBoolean(fieldHygieneOpening,keyHygieneOpeningIsOpen);
        hygienePeriod= gUserProfile.getFieldEntryAsInt(fieldHygieneOpening, keyHygieneOpeningPeriod);
        hygieneCount= gUserProfile.getFieldEntryAsInt(fieldHygieneOpening, keyHygieneOpeningCount);
        hygieneUsed = gUserProfile.getFieldEntryAsInt(fieldHygieneOpening, keyHygieneOpeningUsed);
        hygieneDuration= gUserProfile.getFieldEntryAsLong(fieldHygieneOpening, keyHygieneOpeningDuration);
        hygienePenalty= gUserProfile.getFieldEntryAsLong(fieldHygieneOpening, keyHygieneOpeningPenalty);
        hygieneUnlockDate= gUserProfile.getFieldEntryAsLong(fieldHygieneOpening, keyHygieneOpeningUnlockDate);
        isFriendEnableAdd= gUserProfile.getFieldEntryAsBoolean(fieldVotingFriend,keyVotingFriendEnableAdd);
        isFriendEnableSub= gUserProfile.getFieldEntryAsBoolean(fieldVotingFriend,keyVotingFriendEnableSub);
        isFriendEnableRand= gUserProfile.getFieldEntryAsBoolean(fieldVotingFriend,keyVotingFriendEnableRandom);
        friendAddTime= gUserProfile.getFieldEntryAsLong(fieldVotingFriend, keyVotingFriendAddTime);
        friendSubTime= gUserProfile.getFieldEntryAsLong(fieldVotingFriend, keyVotingFriendSubTime);
        countFriendAdd= gUserProfile.getFieldEntryAsInt(fieldVotingFriend, keyVotingFriendLogAdd);
        countFriendSub= gUserProfile.getFieldEntryAsInt(fieldVotingFriend, keyVotingFriendLogSub);
        countFriendRand= gUserProfile.getFieldEntryAsInt(fieldVotingFriend, keyVotingFriendLogRand);
        friendUsers =gUserProfile.getFieldEntryAsJSONObject(fieldVotingFriend,keyVotingFriendUsers);

        lockBoxImageFile =gUserProfile.getFieldEntryAsString(fieldSession, keySessionLockboxImageFile);
        lockBoxImageName =gUserProfile.getFieldEntryAsString(fieldSession, keySessionLockboxImageName);
        lockBoxImageExt =gUserProfile.getFieldEntryAsString(fieldSession, keySessionLockboxImageExtension);

        verificationInterval= gUserProfile.getFieldEntryAsLong(fieldSession, keySessionVerificationInterval);
        verificationLastDate= gUserProfile.getFieldEntryAsLong(fieldSession, keySessionVerificationLastDate);
        verificationFile =gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationFile);
        verificationCode =gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationCode);
        verificationImageName="img";verificationImageExt="png";
        if(gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationName)!=null&&!gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationName).isBlank()){
            verificationImageName =gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationName);
        }
        if(gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationExtension)!=null&&!gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationExtension).isBlank()){
            verificationImageExt =gUserProfile.getFieldEntryAsString(fieldSession, keySessionVerificationExtension);
        }
    }
    Long friendDiffTime=null;
    private void getRemaining(){
        String fName = "[getRemaining]";
        logger.info(fName);
        getTodayDate();getStartDate();getEndDate();getHygieneOpen();getFriendLastVoting();
        getVerificationTimeRemaning();
        Long remaining=gCalendarEnd.getTimeInMillis()-gCalendarToday.getTimeInMillis();
        logger.info(fName+"remainingDuration="+remaining);
        remainingDuration=remaining;
        remaining=gCalendarHygineOpen.getTimeInMillis()-gCalendarToday.getTimeInMillis();
        logger.info(fName+"remainingHygieneDuration="+remaining);
        remainingHygieneDuration=remaining;
        friendDiffTime=gCalendarToday.getTimeInMillis()-gCalendarFriendLastVoting.getTimeInMillis();
        logger.info(fName+"friendDiffTime="+friendDiffTime);
        logger.info(fName+"hygienePeriod="+hygienePeriod);
        if(hygienePeriod==2&&gCalendarHygineOpen.get(gCalendarHygineOpen.MONTH)<gCalendarToday.get(gCalendarToday.MONTH)){
            hygieneUsed=0;logger.info(fName+"hygieneUsed reset");
        }else
        if(hygienePeriod==1&&gCalendarHygineOpen.get(gCalendarHygineOpen.WEEK_OF_YEAR)<gCalendarToday.get(gCalendarToday.WEEK_OF_YEAR)){
            hygieneUsed=0;logger.info(fName+"hygieneUsed reset");
        }else
        if(hygienePeriod==0&&gCalendarHygineOpen.get(gCalendarHygineOpen.DAY_OF_YEAR)<gCalendarToday.get(gCalendarToday.DAY_OF_YEAR)){
            hygieneUsed=0;logger.info(fName+"hygieneUsed reset");
        }
        getVerificationTimeRemaning();
    }
    private long String2Timeset4Duration(String str){
        String fName = "[String2Timeset4Duration]";
        //Logger logger = Logger.getLogger(fName);
        logger.info(fName+"str="+str);
        try{
            long timeset = 0;
            String []items = str.split("\\s+");
            logger.info(fName + ".items.size=" + items.length);
            for (int i = 0; i < items.length; i++) {
                logger.info(fName + ".iteme=" + items[i]);
                if (items[i].equalsIgnoreCase("m") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day * 30;
                }
                if (items[i].equalsIgnoreCase("w") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_week;
                }
                if (items[i].equalsIgnoreCase("d") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_day;
                }
                if (items[i].equalsIgnoreCase("h") && i != 0) {
                    timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                }
            }
            logger.info(fName + ".timeset=" +timeset);
            return  timeset;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
    }
    private void Failed2LoadProfile(User user){
        String fName = "[Failed2LoadProfile]";
        //Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(user, sRTitle, "Failed to load  profile!", llColors.llColorRed);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); }
    }
    private void SendDenied(User user){
        String fName = "[SendDenied]";
        //Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(user, sRTitle, "Denied", llColors.llColorRed);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); }
    }
    private  void Failed2LoadProfile(User user, User target){
        String fName = "[Failed2LoadProfile]";
        //Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(user, sRTitle, "Failed to load "+target.getAsMention()+" profile!", llColors.llColorRed);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); }
    }
    private  void Failed2LoadProfile(User user, Member target){
        String fName = "[Failed2LoadProfile]";
        //Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(user, sRTitle, "Failed to load "+target.getAsMention()+" profile!", llColors.llColorRed);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); }
    }
    Calendar gCalendarVerification;
    long verificationRemaining=0;
    private void getVerificationTimeRemaning(){
        String fName="[getVerificationTimeRemaning]";
        logger.info(fName);
        gCalendarVerification = Calendar.getInstance();
        if(verificationLastDate!=null) {
            logger.info(fName + "verificationLastDate=" + verificationLastDate);
            gCalendarVerification.setTimeInMillis(verificationLastDate);
        }
        logger.info(fName + ".last verification:"+gCalendarVerification.get(gCalendarVerification.YEAR)+"|"+gCalendarVerification.get(gCalendarVerification.MONTH)+"|"+gCalendarVerification.get(gCalendarVerification.DAY_OF_MONTH)+"@"+gCalendarVerification.get(gCalendarVerification.HOUR_OF_DAY)+":"+gCalendarVerification.get(gCalendarVerification.MINUTE));
        long diff=gCalendarToday.getTimeInMillis()-gCalendarVerification.getTimeInMillis();
        logger.info(fName + "diff=" + diff);
        verificationRemaining=verificationInterval-diff;
        logger.info(fName + "verificationRemaining=" + verificationRemaining);
    }

    lcBasicFeatureControl gBasicFeatureControl;
    private void setEnable(boolean enable) {
        String fName = "[setEnable]";
        try {
            logger.info(fName + "enable=" + enable);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.",llColors.llColorOrange_InternationalEngineering);
                return;
            }
            gBasicFeatureControl.setEnable(enable);
            if(enable){
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
            }else{
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
            }
        } catch (Exception e) {
            logger.error(cName+fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    private void getChannels(int type, boolean toDM) {
        String fName = "[setChannel]";
        try {
            logger.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                logger.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+ lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(cName+fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    private void getRoles(int type, boolean toDM) {
        String fName = "[getRoles]";
        try {
            logger.info(fName + "type=" +type+", toDM="+toDM);
            if(type==1){
                logger.info(fName+"allowed");
                List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                if(!list.isEmpty()){
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                }else{
                    if(toDM){
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(cName+fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    private boolean checkIFChannelsAreNSFW(List<TextChannel>textChannels) {
        String fName = "[checkIFChannelsAreNSFW]";
        try {
            logger.info(fName + "textChannels.size=" +textChannels.size());
            for(TextChannel textChannel:textChannels){
                logger.info(fName + "textChannel.id=" +textChannel.getId()+" ,nsfw="+textChannel.isNSFW());
                if(!textChannel.isNSFW()){
                    logger.info(fName + "not nsfw");
                    return false;
                }
            }
            logger.info(fName + "default");
            return true;
        } catch (Exception e) {
            logger.error(cName+fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
            return false;
        }
    }
    private void setChannel(int type, int action, Message message) {
        String fName = "[setChannel]";
        try {
            logger.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            boolean updated=false, result=false;
            if(type==1){
                logger.info(fName+"allowed");
                if(action==1){
                    logger.info(fName+"add");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    if(!checkIFChannelsAreNSFW(textChannels)){
                        logger.warn(fName+"failed as not all are nsfw");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update as all channels are required to be NSFW!");
                        return;
                    }
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    if(!checkIFChannelsAreNSFW(textChannels)){
                        logger.warn(fName+"failed as not all are nsfw");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update as all channels are required to be NSFW!");
                        return;
                    }
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.remAllowedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                if(action==1){
                    logger.info(fName+"add");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.addDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<TextChannel>textChannels=message.getMentionedChannels();
                    for(TextChannel textChannel:textChannels){
                        result=gBasicFeatureControl.remDeniedChannel(textChannel);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedChannels()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                }
            }
        } catch (Exception e) {
            logger.error(cName+fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    private void setRole(int type, int action, Message message) {
        String fName = "[setRole]";
        try {
            logger.info(fName + "type=" +type+", action="+action);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                return;
            }
            boolean updated=false, result=false;
            if(type==1){
                logger.info(fName+"allowed");
                if(action==1){
                    logger.info(fName+"add");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.remAllowedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearAllowedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                }
            }
            if(type==-1){
                logger.info(fName+"denied");
                if(action==1){
                    logger.info(fName+"add");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==2){
                    logger.info(fName+"set");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.addDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                }
                if(action==-1){
                    logger.info(fName+"rem");
                    List<Role>roles=message.getMentionedRoles();
                    for(Role role:roles){
                        result=gBasicFeatureControl.remDeniedRole(role);
                        if(!updated&&result)updated=true;
                    }
                    if(!updated){
                        logger.warn(fName+"failed to update");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to update!");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save!");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                }
                if(action==-2){
                    logger.info(fName+"clear");
                    if(!gBasicFeatureControl.clearDeniedRoles()){
                        logger.warn(fName+"failed to clear");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to set");
                        return;
                    }
                    if(!gBasicFeatureControl.saveProfile()){
                        logger.warn(fName+"failed to save");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,"Failed to save");
                        return;
                    }
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                }
            }
        } catch (Exception e) {
            logger.error(cName+fName+"exception:"+e);
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }
    private void menuGuild(){
        String fName="[menuGuild]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColors.llColorBlue1);
            embed.setTitle(gTitle+" Options");
            embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
            embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
            embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
            embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
            embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
            embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gBasicFeatureControl.getEnable()){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            lsMessageHelper.lsMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                help("main");return;
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setEnable(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setEnable(false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                getChannels(1,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                getChannels(-1,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                getRoles(1,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                getRoles(-1,true);
                            }
                            else{
                                menuGuild();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> {
                        lsMessageHelper.lsMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }

    ////////////////////////
}

    public chastitysession(lcGlobalHelper global, Guild guild, User user,String command){
        logger.info(".run build");
        Runnable r = new runUtility(global,guild,user,command);
        new Thread(r).start();
    }
    protected class runUtility implements Runnable {
        String cName = "[runReset]";

        lcGlobalHelper gGlobal;
        Guild gGuild;
        User gUser;
        String gCommand;

        public runUtility(lcGlobalHelper global, Guild g, User user,String command) {
            logger.info(".run build");
            gGlobal = global;
            gUser=user;
            gGuild = g;
            gCommand=command;

        }
        lcJSONUserProfile gUserProfile;
        @Override
        public void run() {
            String fName = "run";
            try {
                logger.info(".run");
                if(gCommand.equalsIgnoreCase("reset")){
                    getProfile();
                    gUserProfile.jsonObject =new JSONObject();
                    saveProfile();
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private Boolean getProfile(){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".user:"+ gUser.getId()+"|"+gUser.getName());
            gUserProfile=new lcJSONUserProfile(gGlobal,gUser,gGuild);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");  return true;
            }
            return false;
        }
        private Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            gGlobal.putUserProfile(gUserProfile,profileName);
            if(gUserProfile.saveProfile(table)){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }


    }
}
