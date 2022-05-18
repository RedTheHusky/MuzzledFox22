package nsfw.diaper;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Red;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import nsfw.diaper.modules.dExtension;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class diTimelock extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iDiaperInteractive {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gCommand="ditimelock";
	String sRTitle=iDiaperInteractive.sRTitle+" Timelock",sRTitles="Timelock";
    public diTimelock(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Timelock-Diaper";
        this.help = "timelock";
        this.aliases = new String[]{gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public diTimelock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public diTimelock(lcGlobalHelper global, CommandEvent ev, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,formawrd,target);
        new Thread(r).start();
    }
    public diTimelock(lcGlobalHelper global, CommandEvent ev, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,formawrd,null);
        new Thread(r).start();
    }
    public diTimelock(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public diTimelock(lcGlobalHelper global, InteractionHook interactionHook, String forward){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward);
        new Thread(r).start();
    }
    public diTimelock(lcGlobalHelper global, InteractionHook interactionHook,String forward,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward,target);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent gEvent) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(gEvent);
        new Thread(r).start();
    }
    protected class runLocal extends dExtension implements Runnable {
        String cName="[runLocal]";
        public runLocal(CommandEvent ev){
            loggerExt.info(".run build");
            launch(gGlobal,ev);
        }
        public runLocal(CommandEvent ev,boolean isForward){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,isForward);
        }
        public runLocal(CommandEvent ev,boolean isForward,Member target){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,isForward,target);
        }
        public runLocal(CommandEvent ev,boolean isForward,String formawrd){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,isForward,formawrd);
        }
        public runLocal(CommandEvent ev,String formawrd){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,formawrd);
        }
        public runLocal(CommandEvent ev,String formawrd,Member target){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,formawrd,target);
        }
        public runLocal(SlashCommandEvent ev){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,ev,sRTitle);
        }
        public runLocal(InteractionHook interactionHook,String forward){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,interactionHook,sRTitle,forward);
        }
        public runLocal(InteractionHook interactionHook,String forward,Member target){
            loggerExt.info(".run build");String fName="runLocal";
            launch(gGlobal,interactionHook,sRTitle,forward,target);
        }
        @Override
        public void run() {
            String fName="[run]";
            loggerExt.info(".run start");
            try {
                gBDSMCommands.restraints.init();
                gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"diTimelock_commands", global);
                gBasicFeatureControl.initProfile();
                if(gCurrentInteractionHook!=null){
                    logger.info(cName + fName + "InteractionHook@");
                    rInteraction();
                }else
                if(gSlashCommandEvent!=null){
                    logger.info(cName + fName + "slash@");
                    rSlashNT();
                }else{
                    if(!gBDSMCommands.restraints.getEnable()) {
                        loggerExt.info(fName + "its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                        return;
                    }
                    else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                        loggerExt.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        return;
                    }
                    else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                        loggerExt.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                        return;
                    }
                    else{
                        loggerExt.info(cName + fName + "basic@");
                        if(!isAdult&&bdsmRestriction==1){blocked();return;}
                        else if(bdsmRestriction==2){llSendMessageWithDelete(global,gTextChannel,"This is restricted!");return;}

                        boolean isInvalidCommand=true;
                        loggerExt.info(fName+".gIsForward="+gIsForward);
                        if(gIsForward&&(gRawForward.isBlank()||gRawForward.isEmpty())){
                            loggerExt.info(fName+".gRawForward is null");
                            if(!gBasicFeatureControl.getEnable()){
                                loggerExt.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                                loggerExt.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                                loggerExt.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else {menuLevels(null);isInvalidCommand=false;}
                        }else
                        if(gIsForward&&(gRawForward.equalsIgnoreCase("help"))){
                            loggerExt.info(fName+".gRawForward=help");
                            rHelp("main");
                            isInvalidCommand=false;
                        }else
                        if((!gIsForward&&gArgs.isEmpty())) {
                            loggerExt.info(fName + ".Args=0");
                            if (!gBasicFeatureControl.getEnable()) {
                                loggerExt.info(fName + "its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "This `rd` sub-command is disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand = false;
                            } else if (!gBasicFeatureControl.isChannelAllowed(gTextChannel)) {
                                loggerExt.info(fName + "its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "This  `rd` sub-command is not allowed in " + gTextChannel.getAsMention() + "!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand = false;
                            } else if (!gBasicFeatureControl.isRoleAllowed(gMember)) {
                                loggerExt.info(fName + "its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand = false;
                            } else {
                                menuLevels(null);
                                isInvalidCommand = false;
                            }
                        }else
                        if(!gIsForward){
                            loggerExt.info(fName + ".gRawForward="+gRawForward);
                            gItems =gRawForward.split("\\s+");
                            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                                if(gItems[0].equalsIgnoreCase("menu")){
                                    menuLevels(gTarget);isInvalidCommand=false;
                                }
                            }else{
                                if(gItems[0].equalsIgnoreCase("menu")){
                                    menuLevels(null);isInvalidCommand=false;
                                }
                            }
                        }
                        else
                        {
                            loggerExt.info(fName + ".Args");
                            if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                                gIsOverride =true;
                                gArgs=gArgs.replaceAll(llOverride,"");
                            }
                            gItems = gArgs.split("\\s+");
                            loggerExt.info(fName + ".gRawForward="+gRawForward);
                            loggerExt.info(fName + ".gRawForward:"+gRawForward.isEmpty()+"|"+gRawForward.isBlank());
                            if(!gRawForward.isEmpty()&&!gRawForward.isBlank()){
                                gItems = gRawForward.split("\\s+");
                            }
                            loggerExt.info(fName + ".items.size=" + gItems.length);
                            loggerExt.info(fName + ".items[0]=" + gItems[0]);
                            if(gItems[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                            else if(gItems[0].equalsIgnoreCase("guild")||gItems[0].equalsIgnoreCase("server")){
                                if(gItems.length>2){
                                    // allowchannels/blockchannels/ allowroles/blockroles list|add|rem|set|clear
                                    int group=0,type=0,action=0;
                                    switch (gItems[1].toLowerCase()){
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
                                    switch (gItems[2].toLowerCase()){
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
                                            setChannel(type,action,gCommandEvent.getMessage());
                                        }
                                    }
                                    else if(group==2){
                                        if(action==0){
                                            getRoles(type,false);isInvalidCommand=false;
                                        }else{
                                            setRole(type,action,gCommandEvent.getMessage());
                                        }
                                    }
                                }else{
                                    menuGuild();isInvalidCommand=false;
                                }
                            }
                            else if(!gBasicFeatureControl.getEnable()){
                                loggerExt.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                                loggerExt.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                                loggerExt.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            ///TARGETED
                            if(isInvalidCommand&&(gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")){
                                loggerExt.info(fName+".detect mention characters");
                                Member target;
                                List<Member>mentions=gCommandEvent.getMessage().getMentionedMembers();
                                if(mentions.isEmpty()){
                                    loggerExt.warn(fName+".zero member mentions in message>check itemns[0]");
                                    target=llGetMember(gGuild,gItems[0]);
                                }else{
                                    loggerExt.info(fName+".member mentions in message");
                                    target=mentions.get(0);

                                }
                                if(target!=null){
                                    loggerExt.info(fName+".target is not null");
                                    gTarget=target;
                                }
                                if(target==null){
                                    loggerExt.warn(fName+".zero member mentions");
                                }
                                else if(target.getIdLong()==gUser.getIdLong()){
                                    loggerExt.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                                    target=null;
                                    gTarget=null;
                                    //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                                }
                            }
                            if(isInvalidCommand&&gTarget!=null){
                                if(gItems.length<2){
                                    loggerExt.warn(fName+".invalid args length");
                                    menuLevels(gTarget);isInvalidCommand=false;
                                }else{
                                    if(gItems.length>=3&&gItems[1].equalsIgnoreCase("set")){
                                        if(gItems[2].equalsIgnoreCase("max")){
                                            setMaxDuration(gTarget,gArgs);isInvalidCommand=false;
                                        }
                                        else if(gItems[2].equalsIgnoreCase("min")){
                                            setMinDuration(gTarget,gArgs);isInvalidCommand=false;
                                        }
                                        else if(gItems[2].equalsIgnoreCase("start")){
                                            setStartingDuration(gTarget,gArgs);isInvalidCommand=false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase("status")){
                                        status(gTarget);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("start")){
                                        startSession(gTarget);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("red")){
                                        doRed(gTarget);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("add")){
                                        addDuration(gTarget,gArgs);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("sub")){
                                        subDuration(gTarget,gArgs);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("help")){
                                        rHelp("main");isInvalidCommand=false;
                                    }
                                }
                                if(isInvalidCommand&&gTarget!=null){
                                    menuLevels(gTarget);isInvalidCommand=false;
                                }
                            }
                            if(isInvalidCommand){
                                if(gItems==null||gItems.length==0){
                                    menuLevels(null);isInvalidCommand=false;
                                }
                                else if(gItems.length>=2&&gItems[0].equalsIgnoreCase("set")){
                                    if(gItems[1].equalsIgnoreCase("max")){
                                        setMaxDuration(gArgs);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("min")){
                                        setMinDuration(gArgs);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("start")){
                                        setStartingDuration(gArgs);isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[0].equalsIgnoreCase("start")){
                                    startSession();isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase("red")){
                                    doRed(null);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase("add")){
                                    addDuration(gArgs);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase("sub")){
                                    subDuration(gArgs);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase("status")){
                                    status(null);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase("help")){
                                    rHelp("main");isInvalidCommand=false;
                                }
                                else{
                                    menuLevels(null);isInvalidCommand=false;
                                }
                            }
                        }

                        if(isInvalidCommand){
                            llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sRTitle,"You provided an incorrect command!", llColorRed);
                        }
                    }
                }



            }catch (Exception e){
                loggerExt.error(fName + ".exception=" + e);
                loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
            loggerExt.info(".run ended");
        }

        boolean isAdult=false;
        private void blocked(){
            String fName = "[blocked]";
            llSendQuickEmbedMessageWithDelete(global,true,gTextChannel,sRTitle,"Require NSFW channel or server.",llColorRed);
            loggerExt.info(fName);
        }
        int bdsmRestriction=0;
        private void updateIsAdult(){
            String fName="[updateIsAdult]";
            loggerExt.info(fName);
            if(gTextChannel.isNSFW()){
                loggerExt.info(fName+"channel is nsfw"); isAdult=true; return;
            }
            if(lsGuildHelper.lsIsGuildNSFW(global,gGuild)){
                loggerExt.info(fName+"guild is adult"); isAdult=true; return;
            }
            bdsmRestriction= gBDSMCommands.getBDSMRestriction();
            loggerExt.info(fName+"is safe");
        }
    private void rHelp(String command){
        String fName="[rHelp]";
        loggerExt.info(fName);
        loggerExt.info(fName + ".command="+command);
        String desc;
        String quickSummonWithSpace2=llPrefixStr+"ditimelock <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(iRestraints.strSupportTitle,iRestraints.strSupport,false);
        embed.addField("OwO","Initiating timelock to prevent unlocking pets lock until time expires.",false);
        desc=newLine+quickSummonWithSpace2+"set start <number> <range of time>"+endLine+", sets the starting duration";
        desc+=newLine+quickSummonWithSpace2+"set max <number> <range of time>"+endLine+", sets the maximum duration that can be added to over time";
        desc+=newLine+quickSummonWithSpace2+"set min <number> <range of time>"+endLine+", sets the minimum duration that can be subtracted to over time";
        desc+=newLine+quickSummonWithSpace2+"start"+endLine+", starts the timelock";
        desc+=newLine+quickSummonWithSpace2+"add <number> <range of time>"+endLine+", adds time to remaining duration";
        desc+=newLine+quickSummonWithSpace2+"sub <number> <range of time>"+endLine+", subtracts time from remaining duration";
        if(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember)){
            desc+=newLine+quickSummonWithSpace2+"red"+endLine+", safeword to undo timelock.";
        }
        embed.addField("Commands",desc,false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(global,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }


   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void status(Member mtarget){
            String fName = "[status]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setAuthor(gNewUserProfile.getMember().getUser().getName(),null, lsUserHelper.getAuthorIcon(gNewUserProfile.getMember().getUser()));
                embedBuilder.setTitle("Timelock Status");embedBuilder.setColor(llColorOrange);
                String desc="";
                desc+="\nStar duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getStartDuration());
                if(gNewUserProfile.cTimelock.getMinDuration()< lsNumbersUsefullFunctions.milliseconds_minute*15){
                    desc+="\nMin duration: disabled";
                }else{
                    desc+="\nMin duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getMinDuration());
                }
                if(gNewUserProfile.cTimelock.getMaxDuration()< lsNumbersUsefullFunctions.milliseconds_minute*30){
                    desc+="\nMax duration: disabled";
                }else{
                    desc+="\nMax duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getMaxDuration());
                }
                if(!gNewUserProfile.cTimelock.isTimeLocked()){
                    desc+="\nDuration: Not active";
                }else{
                    try {
                        desc+="\nTotal Duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getDuration());
                        desc+="\nRemaining Duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getRemaining());
                    }catch (Exception e){
                        loggerExt.error(fName + ".exception=" + e);
                        loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void setStartingDuration(String message){
            String fName = "[setStartingDuration]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    loggerExt.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    loggerExt.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Public){
                    loggerExt.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                /*if (timeset < lsLongUsefullFunctions.milliseconds_minute*15) {
                    timeset=lsLongUsefullFunctions.milliseconds_minute*15;
                }*/
                if (timeset < lsNumbersUsefullFunctions.milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set starting duration! Starting duration needs to be minimum 15 minutes.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTimelock.getMinDuration()&&gNewUserProfile.cTimelock.getMinDuration()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be smaller than minimum.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTimelock.getMaxDuration()&&gNewUserProfile.cTimelock.getMaxDuration()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be bigger than maximum.", llColorRed);
                    return;
                }
                gNewUserProfile.cTimelock.setStartDuration(timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText( sRTitle, "Starting duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void setMinDuration(String message){
            String fName = "[setMinDuration]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    loggerExt.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    loggerExt.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Public){
                    loggerExt.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                if (timeset < lsNumbersUsefullFunctions.milliseconds_minute*15) {
                    timeset=0L;
                }
                else if (timeset >gNewUserProfile.cTimelock.getMaxDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than maximum duration.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTimelock.getStartDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than starting duration.", llColorRed);
                    return;
                }
                gNewUserProfile.cTimelock.setMinDuration(timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    sendEmbedInText( sRTitle, "Minimum duration disabled for "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    sendEmbedInText( sRTitle, "Minimum duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                }

            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setMaxDuration(String message){
            String fName = "[setMaxDuration]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    loggerExt.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    loggerExt.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Public){
                    loggerExt.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                if (timeset < lsNumbersUsefullFunctions.milliseconds_minute*30) {
                    timeset=0L;
                }
                else if (timeset <gNewUserProfile.cTimelock.getMinDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than minimum duration.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTimelock.getStartDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than starting duration.", llColorRed);
                    return;
                }
                gNewUserProfile.cTimelock.setMaxDuration(timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    sendEmbedInText( sRTitle, "Maximum duration disabled for "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    sendEmbedInText( sRTitle, "Maximum duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                }
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void startSession(){
            String fName = "[startSession]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    loggerExt.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    loggerExt.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Public){
                    loggerExt.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                if(gNewUserProfile.cTimelock.getStartDuration()< lsNumbersUsefullFunctions.milliseconds_minute*15){
                    gNewUserProfile.cTimelock.setStartDuration(lsNumbersUsefullFunctions.milliseconds_minute*15);
                }
                gNewUserProfile.cTimelock.setDuration(gNewUserProfile.cTimelock.getStartDuration());
                gNewUserProfile.cProfile.setLock(true).setLockedBy(gMember);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                loggerExt.info(fName+".timestamp="+timestamp.getTime());
                gNewUserProfile.cTimelock.setTimestamp(timestamp.getTime());
                gNewUserProfile.cTimelock.setEnabled(true);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText(sRTitle,"Timelock started for "+gMember.getAsMention()+".\nDuration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getStartDuration()), llColorPink1);
            }catch (Exception e){
                loggerExt.error(fName + ".exception=" + e);
                loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void addDuration(String message){
            String fName = "[addDuration]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    loggerExt.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    loggerExt.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Public){
                    loggerExt.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTimelock.getDuration()+timeset;
                loggerExt.info(fName + ".diff=" + diff);
                if(gNewUserProfile.cTimelock.getMaxDuration()>0&&gNewUserProfile.cTimelock.getMaxDuration()>= lsNumbersUsefullFunctions.milliseconds_minute*30&&diff>gNewUserProfile.cTimelock.getMaxDuration()){
                    loggerExt.info(fName + ".above max");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration above maximum.", llColorRed);
                    return;
                }
                gNewUserProfile.cTimelock.setDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText(sRTitle,"Added "+lsUsefullFunctions.displayDuration(timeset)+" to "+gMember.getAsMention()+"'s duration.\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                sendEmbedInText(sRTitle,"Failed to add duration!", llColorRed);
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void subDuration(String message){
            String fName = "[subDuration]";
            loggerExt.info(fName);
            try {
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"The sub can't subtract, ony add more time.", llColorRed);
                    return;
                }else
                if(gNewUserProfile.cTimelock.getMinDuration()< lsNumbersUsefullFunctions.milliseconds_minute*15){
                    loggerExt.info(fName + ".min disabled");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't subtract as its disabled.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTimelock.getDuration()-timeset;
                loggerExt.info(fName + ".diff=" + diff);
                if(diff<gNewUserProfile.cTimelock.getMinDuration()){
                    loggerExt.info(fName + ".bellow minimum");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration bellow minimum.", llColorRed);
                    return;
                }
                gNewUserProfile.cTimelock.setDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText(sRTitle,"Removed "+lsUsefullFunctions.displayDuration(timeset)+" from "+gMember.getAsMention()+"'s duration.\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                sendEmbedInText(sRTitle,"Failed to sub duration!", llColorRed);
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void setStartingDuration(Member mtarget,String message){
            String fName = "[setStartingDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    loggerExt.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Locked+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    loggerExt.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Settings, llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                if (timeset < lsNumbersUsefullFunctions.milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set starting duration! Starting duration needs to be minimum 15 minutes.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTimelock.getMinDuration()&&gNewUserProfile.cTimelock.getMinDuration()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be smaller than minimum.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTimelock.getMaxDuration()&&gNewUserProfile.cTimelock.getMaxDuration()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be bigger than maximum.", llColorRed);
                    return;
                }
                setStartingDurationSave(mtarget,timeset);
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void setMinDuration(Member mtarget,String message){
            String fName = "[setMinDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    loggerExt.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Locked+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    loggerExt.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Settings, llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                if (timeset < lsNumbersUsefullFunctions.milliseconds_minute*15) {
                    timeset=0L;
                }
                else if (timeset >gNewUserProfile.cTimelock.getMaxDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than maximum duration.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTimelock.getStartDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than starting duration.", llColorRed);
                    return;
                }
                setMinDurationSave(mtarget,timeset);

            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setMaxDuration(Member mtarget,String message){
            String fName = "[setMaxDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    loggerExt.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Locked+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    loggerExt.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Settings, llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                if (timeset < lsNumbersUsefullFunctions.milliseconds_minute*30) {
                    timeset=0L;
                }
                else if (timeset <gNewUserProfile.cTimelock.getMinDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than minimum duration.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTimelock.getStartDuration()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than starting duration.", llColorRed);
                    return;
                }
                setMaxDurationSave(mtarget,timeset);
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void startSession(Member mtarget){
            String fName = "[startSession]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    loggerExt.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Locked+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    loggerExt.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Settings, llColorRed);return;
                }
                startSessionSave(mtarget);
            }catch (Exception e){
                loggerExt.error(fName + ".exception=" + e);
                loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        private void setStartingDurationSave(Member mtarget,long timeset){
            String fName = "[setStartingDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                loggerExt.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTimelock.setStartDuration(timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText( sRTitle, "Starting duration for "+mtarget.getAsMention()+" set to "+lsUsefullFunctions.displayDuration(timeset)+" by "+gMember.getAsMention()+".", llColorPurple1);
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void setMinDurationSave(Member mtarget,long timeset){
            String fName = "[setMinDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                loggerExt.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTimelock.setMinDuration( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    sendEmbedInText( sRTitle, "Minimum duration for "+mtarget.getAsMention()+" disabled by "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    sendEmbedInText( sRTitle, "Minimum duration for "+mtarget.getAsMention()+" set to "+lsUsefullFunctions.displayDuration(timeset)+" by "+gMember.getAsMention()+".", llColorPurple1);
                }

            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setMaxDurationSave(Member mtarget,long timeset){
            String fName = "[setMaxDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                loggerExt.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTimelock.setMaxDuration( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    sendEmbedInText( sRTitle, "Maximum duration for "+mtarget.getAsMention()+" disabled by "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    sendEmbedInText( sRTitle, "Maximum duration for "+mtarget.getAsMention()+" set to "+lsUsefullFunctions.displayDuration(timeset)+" by "+gMember.getAsMention()+".", llColorPurple1);
                }
            } catch(Exception ex){
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); loggerExt.error(fName+"exception="+ex);
            }
        }
        private void startSessionSave(Member mtarget){
            String fName = "[startSession]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(gNewUserProfile.cTimelock.getStartDuration()< lsNumbersUsefullFunctions.milliseconds_minute*15){
                    gNewUserProfile.cTimelock.setStartDuration(lsNumbersUsefullFunctions.milliseconds_minute*15);
                }
                gNewUserProfile.cTimelock.setDuration(gNewUserProfile.cTimelock.getStartDuration());
                gNewUserProfile.cProfile.setLock(true).setLockedBy(gMember);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                loggerExt.info(fName+".timestamp="+timestamp.getTime());
                gNewUserProfile.cTimelock.setTimestamp(timestamp.getTime());
                gNewUserProfile.cTimelock.setEnabled(true);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText(sRTitle,"Timelock started for "+mtarget.getAsMention()+" by "+gMember.getAsMention()+".\nDuration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getStartDuration()), llColorPink1);
            }catch (Exception e){
                loggerExt.error(fName + ".exception=" + e);
                loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        private void addDuration(Member mtarget,String message){
            String fName = "[addDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    loggerExt.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Locked+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    loggerExt.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Settings, llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTimelock.getDuration()+timeset;
                loggerExt.info(fName + ".diff=" + diff);
                if(gNewUserProfile.cTimelock.getMaxDuration()>= lsNumbersUsefullFunctions.milliseconds_minute*30&&diff>gNewUserProfile.cTimelock.getMaxDuration()&&gNewUserProfile.cTimelock.getMaxDuration()>0){
                    loggerExt.info(fName + ".above max");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration above maximum.", llColorRed);
                    return;
                }
                addDurationSave(mtarget,timeset);
               } catch(Exception ex){
                sendEmbedInText(sRTitle,"Failed to add duration!", llColorRed);
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void subDuration(Member mtarget,String message){
            String fName = "[subDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTimelock.isEnabled()){
                    loggerExt.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    loggerExt.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Locked+gNewUserProfile.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    loggerExt.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,strCantManipulateTheirTimelockDue2Settings, llColorRed);return;
                }
                if(gNewUserProfile.cTimelock.getMinDuration()< lsNumbersUsefullFunctions.milliseconds_minute*15){
                    loggerExt.info(fName + ".min disabled");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't subtract as its disabled.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(global,gMember,2);
                loggerExt.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                loggerExt.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTimelock.getDuration()-timeset;
                loggerExt.info(fName + ".diff=" + diff);
                if(diff<gNewUserProfile.cTimelock.getMinDuration()){
                    loggerExt.info(fName + ".bellow minimum");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration bellow minimum.", llColorRed);
                    return;
                }
                subDurationSave(mtarget,timeset);
               } catch(Exception ex){
                sendEmbedInText(sRTitle,"Failed to sub duration!", llColorRed);
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void addDurationSave(Member mtarget,long timeset){
            String fName = "[addDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                loggerExt.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTimelock.getDuration()+timeset;
                loggerExt.info(fName + ".diff=" + diff);
                gNewUserProfile.cTimelock.setDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText(sRTitle,"Added "+lsUsefullFunctions.displayDuration(timeset)+" to "+mtarget.getAsMention()+"'s duration by "+gMember.getAsMention()+".\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                sendEmbedInText(sRTitle,"Failed to add duration!", llColorRed);
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void subDurationSave(Member mtarget,long timeset){
            String fName = "[subDuration]";
            loggerExt.info(fName);
            try {
                User target=mtarget.getUser();
                loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                loggerExt.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTimelock.getDuration()-timeset;
                loggerExt.info(fName + ".diff=" + diff);
                gNewUserProfile.cTimelock.setDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                sendEmbedInText(sRTitle,"Removed "+lsUsefullFunctions.displayDuration(timeset)+" from "+mtarget.getAsMention()+"'s duration by "+gMember.getAsMention()+".\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                sendEmbedInText(sRTitle,"Failed to sub duration!", llColorRed);
                loggerExt.error(".exception=" + ex);
                loggerExt.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            loggerExt.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                if(gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
                    embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s"+ sRTitle+" Menu");
                }else{
                    embed.setTitle(sRTitle+" Menu");
                }
                embed.addField(iRestraints.strSupportTitle,iRestraints.strSupport,false);
                desc+="\nStar duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getStartDuration());
                if(gNewUserProfile.cTimelock.getMinDuration()< lsNumbersUsefullFunctions.milliseconds_minute*15){
                    desc+="\nMin duration: disabled";
                }else{
                    desc+="\nMin duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getMinDuration());
                }
                if(gNewUserProfile.cTimelock.getMaxDuration()< lsNumbersUsefullFunctions.milliseconds_minute*30){
                    desc+="\nMax duration: disabled";
                }else{
                    desc+="\nMax duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getMaxDuration());
                }
                if(!gNewUserProfile.cTimelock.isEnabled()){
                    desc+="\nDuration: Not active";
                }else{
                    try {
                        desc+="\nTotal Duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getDuration());
                        desc+="\nRemaining Duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTimelock.getRemaining());
                    }catch (Exception e){
                        loggerExt.error(fName + ".exception=" + e);
                        loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }


                embed.addField("Help","Select "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(!gNewUserProfile.cTimelock.isTimeLocked()){
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" set starting duration";
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" set max duration";
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" set min duration";
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" start the timelock";
                }else{
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign)+" add time";
                    desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign)+" sub time";
                }
                embed.setDescription(desc);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(!gNewUserProfile.cTimelock.isTimeLocked()){
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign));
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));

                }

                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) { rHelp("main"); }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){menuText("setstart");}
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){menuText("setmax");}
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){menuText("setmin");}
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){menuText("add");}
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign))){menuText("sub");}
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        startSession();
                                    }else{
                                        startSession(mtarget);
                                    }
                                }

                                llMessageDelete(message);
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                loggerExt.error(fName+".exception=" + e);
                loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuText(String command){
            String fName="[menuText]";
            loggerExt.info(fName);
            try{
                loggerExt.info(fName+"command="+command);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.getMember()){
                    embed.setTitle(sRTitle);
                }else{
                    embed.setTitle(gNewUserProfile.getMember().getUser().getName()+" "+sRTitle);
                }
                embed.setDescription("Please enter time.\nThe format for time is `<number> <range>`, where number is integer number and rangers are m=minute,h=hour(s), also for Patreon tier 2+ supporters: d=day(s), w=week(s).\nEx: `2 h` means 2 hours.\nNon-Patreon max 24 hours.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                loggerExt.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    loggerExt.info(fName+".canceled");
                                }else
                                if(content.isBlank()){
                                    loggerExt.info(fName+".blank");
                                }else{
                                    if(command.equalsIgnoreCase("setstart")){
                                        if(gUser==gNewUserProfile.getMember()){
                                            setStartingDuration(content);
                                        }else{
                                            setStartingDuration(gNewUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("setmax")){
                                        if(gUser==gNewUserProfile.getMember()){
                                            setMaxDuration(content);
                                        }else{
                                            setMaxDuration(gNewUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("setmin")){
                                        if(gUser==gNewUserProfile.getMember()){
                                            setMinDuration(content);
                                        }else{
                                            setMinDuration(gNewUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("add")){
                                        if(gUser==gNewUserProfile.getMember()){
                                            addDuration(content);
                                        }else{
                                            addDuration(gNewUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("sub")){
                                        if(gUser==gNewUserProfile.getMember()){
                                            subDuration(content);
                                        }else{
                                            subDuration(gNewUserProfile.getMember(),content);
                                        }
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                loggerExt.error(fName+".exception=" + e);
                loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doRed(Member target){
            String fName="[doRed]";
            loggerExt.info(fName);
            try{
                if(!(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                    loggerExt.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied, only manager&admin can use this command!", llColors_Red.llColorRed_Cinnabar);
                    return;
                } 
                if(!getProfile()){
                    loggerExt.error(fName + ".can't get profile"); return;}
                Message message2=llSendQuickEmbedMessageResponse(gUser,sRTitle,"You're about to use the safeword on "+gNewUserProfile.getMember().getAsMention()+"'s timout. Are you sure?", llColorPurple2);
                message2.addReaction(global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple)).complete();
                message2.addReaction(global.emojis.getEmoji(lsUnicodeEmotes.aliasApple)).complete();
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message2.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))){
                                    loggerExt.info(fName + ".approved");
                                    gNewUserProfile.cTimelock.reset();
                                    saveProfile();
                                    if(target==null){
                                        llSendQuickEmbedMessageResponse(gUser,sRTitle,"You used the safeword on yours timelock.", llColorPurple2);
                                    }else{
                                        llSendQuickEmbedMessageResponse(gUser,sRTitle,"You used the safeword on "+gNewUserProfile.getMember().getAsMention()+"'s timelock.", llColorPurple2);
                                        llSendQuickEmbedMessageResponse(gNewUserProfile.getMember().getUser(),sRTitle,gUser.getAsMention()+" used their power to stop your timelock.", llColorPurple2);
                                    }
                                }else
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                    loggerExt.info(fName + ".reject");
                                }
                                llMessageDelete(message2);
                            }catch (Exception e2){
                                loggerExt.error(fName + ".exception=" + e2);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            loggerExt.info(fName + ".timeout");
                            llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                            llMessageDelete(message2);
                        });

            } catch (Exception e) {
                loggerExt.error(fName+".exception=" + e);
                loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            Member user=null;
            gCurrentInteractionHook=lsMessageHelper.lsDeferReply(gSlashCommandEvent,true);
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsMember();
                        }
                        break;
                }
            }
            if(user!=null&&gMember.getIdLong()!=user.getIdLong()){
                if(gTarget!=null){
                    logger.info(fName + ".target="+gTarget.getId());
                }
            }else{
                logger.info(fName + ".target=author");
            }
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            menuLevels(gTarget);
        }
        private void rInteraction() {
            String fName = "[rInteraction]";
            logger.info(fName);
            Member user=null;
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            sendOrUpdatePrivateEmbed(strTitle,"Openign DM",llColorBlue1);
            menuLevels(gTarget);
        }

        boolean isPatreon=false;
        private long String2Timeset4Duration(String str){
            String fName = "[String2Timeset4Duration]";
            //Logger logger = Logger.getLogger(fName);
            loggerExt.info(fName+"str="+str);
            try{
                long timeset = 0;
                String []items = str.split("\\s+");
                loggerExt.info(fName + ".items.size=" + items.length);
                for (int i = 0; i < items.length; i++) {
                    loggerExt.info(fName + ".iteme=" + items[i]);
                    if (items[i].equalsIgnoreCase("w") && i != 0&&isPatreon) {
                        timeset += Integer.parseInt(items[i - 1]) * lsNumbersUsefullFunctions.milliseconds_week;
                    }
                    if (items[i].equalsIgnoreCase("d") && i != 0&&isPatreon) {
                        timeset += Integer.parseInt(items[i - 1]) * lsNumbersUsefullFunctions.milliseconds_day;
                    }
                    if (items[i].equalsIgnoreCase("h") && i != 0) {
                        int number=Integer.parseInt(items[i - 1]);
                        if(number>24){
                            if(isPatreon){
                                timeset += number *  lsNumbersUsefullFunctions.milliseconds_hour;
                            }else{
                                timeset += 24 *  lsNumbersUsefullFunctions.milliseconds_hour;
                            }
                        }else{
                            timeset += number *  lsNumbersUsefullFunctions.milliseconds_hour;
                        }
                        //timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                    }
                    if (items[i].equalsIgnoreCase("m") && i != 0) {
                        int number=Integer.parseInt(items[i - 1]);
                        if(number>1440){
                            if(isPatreon){
                                timeset += number * lsNumbersUsefullFunctions.milliseconds_minute;
                            }else{
                                timeset += 59 * lsNumbersUsefullFunctions.milliseconds_minute;
                            }
                        }
                        else{
                            timeset += number * lsNumbersUsefullFunctions.milliseconds_minute;
                        }
                    }
                }
                loggerExt.info(fName + ".timeset=" +timeset);
                return  timeset;
            }
            catch (Exception ex){ loggerExt.error(fName+"exception="+ex); return 0;}
        }
     
       

        
        lcBasicFeatureControl gBasicFeatureControl;
        private void setEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                loggerExt.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    loggerExt.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gBasicFeatureControl.setEnable(enable);
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                loggerExt.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void getChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            try {
                loggerExt.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    loggerExt.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    loggerExt.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedChannelsAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied channels list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                loggerExt.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void getRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                loggerExt.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    loggerExt.info(fName+"allowed");
                    List<Long>list=gBasicFeatureControl.getAllowedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Allowed roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
                if(type==-1){
                    loggerExt.info(fName+"denied");
                    List<Long>list=gBasicFeatureControl.getDeniedRolesAsLong();
                    if(!list.isEmpty()){
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied roles list: "+lsRoleHelper.lsGetRolesMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
                        }
                    }else{
                        if(toDM){
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }else{
                            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Denied roles list is empty.", llColors.llColorOrange_Bittersweet);
                        }
                    }
                }
            } catch (Exception e) {
                loggerExt.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private boolean checkIFChannelsAreNSFW(List<TextChannel>textChannels) {
            String fName = "[checkIFChannelsAreNSFW]";
            try {
                loggerExt.info(fName + "textChannels.size=" +textChannels.size());
                for(TextChannel textChannel:textChannels){
                    loggerExt.info(fName + "textChannel.id=" +textChannel.getId()+" ,nsfw="+textChannel.isNSFW());
                    if(!textChannel.isNSFW()){
                        loggerExt.info(fName + "not nsfw");
                        return false;
                    }
                }
                loggerExt.info(fName + "default");
                return true;
            } catch (Exception e) {
                loggerExt.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
                return false;
            }
        }
        private void setChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            try {
                loggerExt.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    loggerExt.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    loggerExt.info(fName+"allowed");
                    if(action==1){
                        loggerExt.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        if(!checkIFChannelsAreNSFW(textChannels)){
                            loggerExt.warn(fName+"failed as not all are nsfw");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update as all channels are required to be NSFW!");
                            return;
                        }
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        loggerExt.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        if(!checkIFChannelsAreNSFW(textChannels)){
                            loggerExt.warn(fName+"failed as not all are nsfw");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update as all channels are required to be NSFW!");
                            return;
                        }
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        loggerExt.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        loggerExt.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedChannels()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    loggerExt.info(fName+"denied");
                    if(action==1){
                        loggerExt.info(fName+"add");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        loggerExt.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        loggerExt.info(fName+"rem");
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result=gBasicFeatureControl.remDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        loggerExt.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedChannels()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                loggerExt.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void setRole(int type, int action, Message message) {
            String fName = "[setRole]";
            try {
                loggerExt.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    loggerExt.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    loggerExt.info(fName+"allowed");
                    if(action==1){
                        loggerExt.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        loggerExt.info(fName+"set");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        loggerExt.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        loggerExt.info(fName+"clear");
                        if(!gBasicFeatureControl.clearAllowedRoles()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    loggerExt.info(fName+"denied");
                    if(action==1){
                        loggerExt.info(fName+"add");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        loggerExt.info(fName+"set");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        loggerExt.info(fName+"rem");
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result=gBasicFeatureControl.remDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            loggerExt.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString(gBasicFeatureControl.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        loggerExt.info(fName+"clear");
                        if(!gBasicFeatureControl.clearDeniedRoles()){
                            loggerExt.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(!gBasicFeatureControl.saveProfile()){
                            loggerExt.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                loggerExt.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void menuGuild(){
            String fName="[menuGuild]";
            loggerExt.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColors.llColorBlue1);
                embed.setTitle(sRTitle+" Options");
                embed.addField("Enable","Select "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
                embed.addField("Help","Select "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gBasicFeatureControl.getEnable()){
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gBasicFeatureControl.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBasicFeatureControl.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(!gBasicFeatureControl.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    rHelp("main");return;
                                }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setEnable(true);
                                }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setEnable(false);
                                }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                    getChannels(1,true);
                                }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                    getChannels(-1,true);
                                }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    getRoles(1,true);
                                }
                                else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    getRoles(-1,true);
                                }
                                else{
                                    menuGuild();
                                }
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            lsMessageHelper.lsMessageDelete(message);
                        });

            } catch (Exception e) {
                loggerExt.error(fName+".exception=" + e);
                loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
}}
