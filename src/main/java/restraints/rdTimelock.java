package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lcGlobalHelper;
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
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.models.enums.ACCESSLEVELS;
import restraints.models.rdExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdTimelock extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String gCommand="timelock";
	String sMainRTitle ="Timelock";
    public rdTimelock(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Timelock-DiscordRestraints";
        this.help = "timelock";
        this.aliases = new String[]{gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public rdTimelock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdTimelock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdTimelock(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdTimelock(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdTimelock(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdTimelock(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdTimelock(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd,target);
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
    protected class runLocal extends rdExtension implements Runnable {
        String cName="[runLocal]";
        public runLocal(CommandEvent ev){
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(GuildMessageReactionAddEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(SlashCommandEvent ev) {

            logger.info(cName + ".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,interactionHook,isForward,forward);
        }
        public runLocal(InteractionHook interactionHook, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,interactionHook,isForward,forward,target);
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                setTitleStr(rdTimelock.this.sMainRTitle);setPrefixStr(rdTimelock.this.llPrefixStr);setCommandStr(rdTimelock.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdTimelock_commands");
                lsUsefullFunctions.setThreadName4Display("rdTimelock");
                if(!checkIFAllowed2UseCommand0()){
                    return;
                }
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuLevels(gTarget);
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuLevels(gTarget);
                            break;
                    }
                }
                else if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    rSlashNT();
                }
                else if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    boolean isInvalidCommand=true;
                    if(!checkIFAllowed2UseCommand2_text()){
                        return;
                    }
                    logger.info(fName+".gRawForward="+gRawForward);
                    if(gRawForward.isBlank()){
                        status(gTarget);isInvalidCommand=false;
                    }
                    if(gRawForward.equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }
                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}

                    boolean isInvalidCommand=true;
                    if(gArgs.isEmpty()){
                        logger.info(fName+".Args=0");
                        if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }
                        else {menuLevels(null);isInvalidCommand=false;}
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){ rHelp("main");isInvalidCommand=false;}
                        else if(isCommand2BasicFeatureControl(gItems)){
                            isInvalidCommand=false;
                        }
                        else if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }
                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
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
                            else if(gItems[0].equalsIgnoreCase("debug")){
                                //getDebug();
                                logger.info(fName+".debug");
                                isInvalidCommand=false;
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

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
            logger.info(".run ended");
        }


    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc;
        String quickSummonWithSpace2=llPrefixStr+"timelock <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
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
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }


   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private void status(Member mtarget){
            String fName = "[status]";
            logger.info(fName);
            try {
                if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setAuthor(gNewUserProfile.gUserProfile.getUser().getName(),null, lsUserHelper.getAuthorIcon(gNewUserProfile.gUserProfile.getUser()));
                embedBuilder.setTitle("Timelock Status");embedBuilder.setColor(llColorOrange);
                String desc="";
                desc+="\nStar duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockStart());
                if(gNewUserProfile.cTIMELOCK.getTimeLockMin()<milliseconds_minute*15){
                    desc+="\nMin duration: disabled";
                }else{
                    desc+="\nMin duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockMin());
                }
                if(gNewUserProfile.cTIMELOCK.getTimeLockMax()<milliseconds_minute*30){
                    desc+="\nMax duration: disabled";
                }else{
                    desc+="\nMax duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockMax());
                }
                if(!iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    desc+="\nDuration: Not active";
                }else{
                    try {
                        desc+="\nTotal Duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockDuration());
                        desc+="\nRemaining Duration: "+lsUsefullFunctions.displayDuration(iRestraints.sTimeLockedGetRemaning(gNewUserProfile.gUserProfile));
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                embedBuilder.setDescription(desc);
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setStartingDuration(String message){
            String fName = "[setStartingDuration]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                /*if (timeset < milliseconds_minute*15) {
                    timeset=milliseconds_minute*15;
                }*/
                if (timeset < milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set starting duration! Starting duration needs to be minimum 15 minutes.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTIMELOCK.getTimeLockMin()&&gNewUserProfile.cTIMELOCK.getTimeLockMin()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be smaller than minimum.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTIMELOCK.getTimeLockMax()&&gNewUserProfile.cTIMELOCK.getTimeLockMax()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be bigger than maximum.", llColorRed);
                    return;
                }
                else if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                gNewUserProfile.cTIMELOCK.setTimeLockStart( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Starting duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setMinDuration(String message){
            String fName = "[setMinDuration]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*15) {
                    timeset=0L;
                }
                else if (timeset >gNewUserProfile.cTIMELOCK.getTimeLockMax()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than maximum duration.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTIMELOCK.getTimeLockStart()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than starting duration.", llColorRed);
                    return;
                }
                else if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                gNewUserProfile.cTIMELOCK.setTimeLockMin( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Minimum duration disabled for "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Minimum duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                }

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setMaxDuration(String message){
            String fName = "[setMaxDuration]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*30) {
                    timeset=0L;
                }
                else if (timeset <gNewUserProfile.cTIMELOCK.getTimeLockMin()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than minimum duration.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTIMELOCK.getTimeLockStart()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than starting duration.", llColorRed);
                    return;
                }
                else if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                gNewUserProfile.cTIMELOCK.setTimeLockMax( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Maximum duration disabled for "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Maximum duration for "+gMember.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
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
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                if(gNewUserProfile.cTIMELOCK.getTimeLockStart()<milliseconds_minute*15){
                    gNewUserProfile.cTIMELOCK.setTimeLockStart(milliseconds_minute*15);
                }
                gNewUserProfile.cTIMELOCK.setTimeLockDuration(gNewUserProfile.cTIMELOCK.getTimeLockStart());
                gNewUserProfile.cLOCK.setLocked(true);
                gNewUserProfile.cLOCK.setLockedBy(gMember.getIdLong());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                gNewUserProfile.cTIMELOCK.setTimeLockTimestamp(timestamp.getTime());
                gNewUserProfile.cTIMELOCK.setOn(true);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Timelock started for "+gMember.getAsMention()+".\nDuration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockStart()), llColorPink1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        private void addDuration(String message){
            String fName = "[addDuration]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your timelock due to access set to public. While public everyone else can access it except you.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTIMELOCK.getTimeLockDuration()+timeset;
                logger.info(fName + ".diff=" + diff);
                if(gNewUserProfile.cTIMELOCK.getTimeLockMax()>0&&gNewUserProfile.cTIMELOCK.getTimeLockMax()>=milliseconds_minute*30&&diff>gNewUserProfile.cTIMELOCK.getTimeLockMax()){
                    logger.info(fName + ".above max");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration above maximum.", llColorRed);
                    return;
                }
                gNewUserProfile.cTIMELOCK.setTimeLockDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Added "+lsUsefullFunctions.displayDuration(timeset)+" to "+gMember.getAsMention()+"'s duration.\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to add duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void subDuration(String message){
            String fName = "[subDuration]";
            logger.info(fName);
            try {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"The sub can't subtract, ony add more time.", llColorRed);
                    return;
                }else
                if(gNewUserProfile.cTIMELOCK.getTimeLockMin()<milliseconds_minute*15){
                    logger.info(fName + ".min disabled");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't subtract as its disabled.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTIMELOCK.getTimeLockDuration()-timeset;
                logger.info(fName + ".diff=" + diff);
                if(diff<gNewUserProfile.cTIMELOCK.getTimeLockMin()){
                    logger.info(fName + ".bellow minimum");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration bellow minimum.", llColorRed);
                    return;
                }
                gNewUserProfile.cTIMELOCK.setTimeLockDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed "+lsUsefullFunctions.displayDuration(timeset)+" from "+gMember.getAsMention()+"'s duration.\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to sub duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void setStartingDuration(Member mtarget,String message){
            String fName = "[setStartingDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+target.getAsMention()+"'s timelock as they permanently locked!", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set starting duration! Starting duration needs to be minimum 15 minutes.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTIMELOCK.getTimeLockMin()&&gNewUserProfile.cTIMELOCK.getTimeLockMin()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be smaller than minimum.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTIMELOCK.getTimeLockMax()&&gNewUserProfile.cTIMELOCK.getTimeLockMax()>0) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set start duration! Start duration can't be bigger than maximum.", llColorRed);
                    return;
                }
                else if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if the can update your starting duration.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's starting duration.");
                    gAskHandlingHelper.doAsk(() -> {setStartingDurationSave(mtarget,timeset);});
                    return;
                }
                setStartingDurationSave(mtarget,timeset);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setMinDuration(Member mtarget,String message){
            String fName = "[setMinDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+target.getAsMention()+"'s timelock as they permanently locked!", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*15) {
                    timeset=0L;
                }
                else if (timeset >gNewUserProfile.cTIMELOCK.getTimeLockMax()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than maximum duration.", llColorRed);
                    return;
                }
                else if (timeset >gNewUserProfile.cTIMELOCK.getTimeLockStart()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set min duration! Minimum can't be bigger than starting duration.", llColorRed);
                    return;
                }
                else if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your minimum duration.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's minimum duration.");
                    long finalTimeset = timeset;
                    gAskHandlingHelper.doAsk(() -> {setMinDurationSave(mtarget, finalTimeset);});
                    return;
                }
                setMinDurationSave(mtarget,timeset);

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setMaxDuration(Member mtarget,String message){
            String fName = "[setMaxDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+target.getAsMention()+"'s timelock as they permanently locked!", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                if (timeset < milliseconds_minute*30) {
                    timeset=0L;
                }
                else if (timeset <gNewUserProfile.cTIMELOCK.getTimeLockMin()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than minimum duration.", llColorRed);
                    return;
                }
                else if (timeset <gNewUserProfile.cTIMELOCK.getTimeLockStart()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set max duration! Maximum can't be smaller than starting duration.", llColorRed);
                    return;
                }
                else if (!isPatreon&&timeset> milliseconds_day) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to set duration! Non patreon users can set up to 24h.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your maximum duration.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's maximum duration.");
                    long finalTimeset = timeset;
                    gAskHandlingHelper.doAsk(() -> {setMaxDurationSave(mtarget, finalTimeset);});
                    return;
                }
                setMaxDurationSave(mtarget,timeset);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void startSession(Member mtarget){
            String fName = "[startSession]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock already started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+target.getAsMention()+"'s timelock as they permanently locked!", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can start the session.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can start !TARGET's session.");
                    gAskHandlingHelper.doAsk(() -> { startSessionSave(mtarget);});
                    return;
                }
                startSessionSave(mtarget);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        private void setStartingDurationSave(Member mtarget,long timeset){
            String fName = "[setStartingDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTIMELOCK.setTimeLockStart( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel, sRTitle, "Starting duration for "+mtarget.getAsMention()+" set to "+lsUsefullFunctions.displayDuration(timeset)+" by "+gMember.getAsMention()+".", llColorPurple1);
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set starting duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void setMinDurationSave(Member mtarget,long timeset){
            String fName = "[setMinDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTIMELOCK.setTimeLockMin( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Minimum duration for "+mtarget.getAsMention()+" disabled by "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Minimum duration for "+mtarget.getAsMention()+" set to "+lsUsefullFunctions.displayDuration(timeset)+" by "+gMember.getAsMention()+".", llColorPurple1);
                }

            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set minimum duration!", llColorRed);
            }
        }
        private void setMaxDurationSave(Member mtarget,long timeset){
            String fName = "[setMaxDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".timeset=" + timeset);
                gNewUserProfile.cTIMELOCK.setTimeLockMax( timeset);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                if(timeset==0){
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Maximum duration for "+mtarget.getAsMention()+" disabled by "+gMember.getAsMention()+".", llColorPurple1);
                }else{
                    llSendQuickEmbedMessage(gTextChannel, sRTitle, "Maximum duration for "+mtarget.getAsMention()+" set to "+lsUsefullFunctions.displayDuration(timeset)+" by "+gMember.getAsMention()+".", llColorPurple1);
                }
            } catch(Exception ex){
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
                llSendQuickEmbedMessage(gUser,sRTitle,"Failed to set maximum duration!", llColorRed); logger.error(fName+"exception="+ex);
            }
        }
        private void startSessionSave(Member mtarget){
            String fName = "[startSession]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(gNewUserProfile.cTIMELOCK.getTimeLockStart()<milliseconds_minute*15){
                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nTimeLockStart,milliseconds_minute*15);
                }
                gNewUserProfile.cTIMELOCK.setTimeLockDuration(gNewUserProfile.cTIMELOCK.getTimeLockStart());
                gNewUserProfile.cLOCK.setLocked(true);
                gNewUserProfile.cLOCK.setLockedBy(gMember.getIdLong());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".timestamp="+timestamp.getTime());
                gNewUserProfile.cTIMELOCK.setTimeLockTimestamp(timestamp.getTime());
               gNewUserProfile.cTIMELOCK.setOn(true);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Timelock started for "+mtarget.getAsMention()+" by "+gMember.getAsMention()+".\nDuration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockStart()), llColorPink1);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        private void addDuration(Member mtarget,String message){
            String fName = "[addDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+target.getAsMention()+"'s timelock as they permanently locked!", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTIMELOCK.getTimeLockDuration()+timeset;
                logger.info(fName + ".diff=" + diff);
                if(gNewUserProfile.cTIMELOCK.getTimeLockMax()>=milliseconds_minute*30&&diff>gNewUserProfile.cTIMELOCK.getTimeLockMax()&&gNewUserProfile.cTIMELOCK.getTimeLockMax()>0){
                    logger.info(fName + ".above max");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration above maximum.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can add duration.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can add duration to !TARGET.");
                    gAskHandlingHelper.doAsk(() -> {addDurationSave(mtarget,timeset);});
                    return;
                }
                addDurationSave(mtarget,timeset);
               } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to add duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void subDuration(Member mtarget,String message){
            String fName = "[subDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!gNewUserProfile.cTIMELOCK.isOn()){
                    logger.info(fName + ".started");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Timelock is not  started.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                    logger.info(fName + ".permalock");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+target.getAsMention()+"'s timelock as they permanently locked!", llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their lock due to their access setting.", llColorRed);return;
                }else
                if(gNewUserProfile.cTIMELOCK.getTimeLockMin()<milliseconds_minute*15){
                    logger.info(fName + ".min disabled");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't subtract as its disabled.", llColorRed);
                    return;
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                logger.info(fName + ".message=" + message);
                long timeset = String2Timeset4Duration(message);
                logger.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTIMELOCK.getTimeLockDuration()-timeset;
                logger.info(fName + ".diff=" + diff);
                if(diff<gNewUserProfile.cTIMELOCK.getTimeLockMin()){
                    logger.info(fName + ".bellow minimum");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't have duration bellow minimum.", llColorRed);
                    return;
                }
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can subtract duration.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can add subtract to !TARGET.");
                    gAskHandlingHelper.doAsk(() -> {subDurationSave(mtarget,timeset);});
                    return;
                }
                subDurationSave(mtarget,timeset);
               } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to sub duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void addDurationSave(Member mtarget,long timeset){
            String fName = "[addDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTIMELOCK.getTimeLockDuration()+timeset;
                logger.info(fName + ".diff=" + diff);
                gNewUserProfile.cTIMELOCK.setTimeLockDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Added "+lsUsefullFunctions.displayDuration(timeset)+" to "+mtarget.getAsMention()+"'s duration by "+gMember.getAsMention()+".\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to add duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        private void subDurationSave(Member mtarget,long timeset){
            String fName = "[subDuration]";
            logger.info(fName);
            try {
                User target=mtarget.getUser();
                logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                logger.info(fName + ".timeset=" + timeset);
                long diff=gNewUserProfile.cTIMELOCK.getTimeLockDuration()-timeset;
                logger.info(fName + ".diff=" + diff);
                gNewUserProfile.cTIMELOCK.setTimeLockDuration(diff);
                if (!saveProfile()) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to write in Db!", llColorRed);
                    return;
                }
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed "+lsUsefullFunctions.displayDuration(timeset)+" from "+mtarget.getAsMention()+"'s duration by "+gMember.getAsMention()+".\nUpdated duration: "+lsUsefullFunctions.displayDuration(diff), llColorPink1);
            } catch(Exception ex){
                llSendQuickEmbedMessage(gTextChannel,sRTitle,"Failed to sub duration!", llColorRed);
                logger.error(".exception=" + ex);
                logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            }
        }
        String gCommandFileMainPath =gFileMainPath+"timelock/menuLevels.json";
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(mtarget!=null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    embed.addField(" ","Please select a timelock option for: "+mtarget.getAsMention()+".",false);
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    embed.addField(" ","Please select a timelock options.",false);
                }
                desc+="\nStar duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockStart());
                if(gNewUserProfile.cTIMELOCK.getTimeLockMin()<milliseconds_minute*15){
                    desc+="\nMin duration: disabled";
                }else{
                    desc+="\nMin duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockMin());
                }
                if(gNewUserProfile.cTIMELOCK.getTimeLockMax()<milliseconds_minute*30){
                    desc+="\nMax duration: disabled";
                }else{
                    desc+="\nMax duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockMax());
                }
                if(!gNewUserProfile.cTIMELOCK.isOn()){
                    desc+="\nDuration: Not active";
                }else{
                    try {
                        desc+="\nTotal Duration: "+lsUsefullFunctions.displayDuration(gNewUserProfile.cTIMELOCK.getTimeLockDuration());
                        desc+="\nRemaining Duration: "+lsUsefullFunctions.displayDuration(iRestraints.sTimeLockedGetRemaning(gNewUserProfile.gUserProfile));
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }


                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(!iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" set starting duration";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" set max duration";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" set min duration";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" start the timelock";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign)+" add time";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign)+" sub time";
                }
                embed.setDescription(desc);
                Message message=null;//llSendMessageResponse(gUser,embed);
               /* lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(!iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));

                }*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    if(!iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                        messageComponentManager.messageBuildComponents.removeComponent(1);
                    }else{
                        messageComponentManager.messageBuildComponents.removeComponent(0);
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuLevelsListener(message,mtarget);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuLevelsListener(Message message,Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource: rHelp("main");break;
                                    case lsUnicodeEmotes.aliasOne:menuText("setstart");break;
                                    case lsUnicodeEmotes.aliasTwo:menuText("setmax");break;
                                    case lsUnicodeEmotes.aliasThree:menuText("setmin");break;
                                    case lsUnicodeEmotes.aliasHeavyPlusSign:menuText("add");break;
                                    case lsUnicodeEmotes.aliasHeavyMinusSign:menuText("sub");break;
                                    case lsUnicodeEmotes.aliasLock:
                                        if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                            startSession();
                                        }else{
                                            startSession(mtarget);
                                        }
                                        break;
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) { rHelp("main"); }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){menuText("setstart");}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){menuText("setmax");}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){menuText("setmin");}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){menuText("add");}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign))){menuText("sub");}
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                                    if(mtarget==null||mtarget.getIdLong()==gMember.getIdLong()){
                                        startSession();
                                    }else{
                                        startSession(mtarget);
                                    }
                                }

                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () ->{llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuText(String command){
            String fName="[menuText]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+command);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(sRTitle);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+sRTitle);
                }
                embed.setDescription("Please enter time.\nThe format for time is `<number> <range>`, where number is integer number and rangers are m=minute,h=hour(s), also for Patreon tier 2+ supporters: d=day(s), w=week(s).\nEx: `2 h` means 2 hours.\nNon-Patreon max 24 hours.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".blank");
                                }else{
                                    if(command.equalsIgnoreCase("setstart")){
                                        if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                            setStartingDuration(content);
                                        }else{
                                            setStartingDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("setmax")){
                                        if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                            setMaxDuration(content);
                                        }else{
                                            setMaxDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("setmin")){
                                        if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                            setMinDuration(content);
                                        }else{
                                            setMinDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("add")){
                                        if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                            addDuration(content);
                                        }else{
                                            addDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                        }
                                    }
                                    else if(command.equalsIgnoreCase("sub")){
                                        if(gUser==gNewUserProfile.gUserProfile.getUser()){
                                            subDuration(content);
                                        }else{
                                            subDuration(gNewUserProfile.gUserProfile.getMember(),content);
                                        }
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doRed(Member target){
            String fName="[doRed]";
            logger.info(fName);
            try{
                if(!(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied, only manager&admin can use this command!", llColors_Red.llColorRed_Cinnabar);
                    return;
                }
                if(target==null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                }
                Message message2=llSendQuickEmbedMessageResponse(gUser,sRTitle,"You're about to use the safeword on "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s timout. Are you sure?", llColorPurple2);
                message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple)).complete();
                message2.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple)).complete();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message2.getId())&&e.getUserId().equalsIgnoreCase(gMember.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))){
                                    logger.info(fName + ".approved");
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nOn,false);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nTimeLockDuration,0);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nTimeLockMax,0);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nTimeLockMin,0);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nTimeLockStart,60000*15);
                                    gNewUserProfile.gUserProfile.putFieldEntry(nTimeLock,nTimeLockTimestamp,0);
                                    saveProfile();
                                    if(target==null){
                                        llSendQuickEmbedMessageResponse(gUser,sRTitle,"You used the safeword on yours timelock.", llColorPurple2);
                                    }else{
                                        llSendQuickEmbedMessageResponse(gUser,sRTitle,"You used the safeword on "+gNewUserProfile.gUserProfile.getUser().getAsMention()+"'s timelock.", llColorPurple2);
                                        llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,gUser.getAsMention()+" used their power to stop your timelock.", llColorPurple2);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                    logger.info(fName + ".reject");
                                }
                                llMessageDelete(message2);
                            }catch (Exception e2){
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName + ".timeout");
                            llSendQuickEmbedMessage(gUser,sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                            llMessageDelete(message2);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }

        }
        public void rSlashNT() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
                User reqUser=null;
                boolean subdirProvided=false;
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case  llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;
                        case llCommonKeys.SlashCommandReceive.user:
                            if(option.getType()== OptionType.USER){
                                reqUser=option.getAsUser();
                            }
                            break;
                    }
                }
                if(reqUser!=null&&gMember.getIdLong()!=reqUser.getIdLong()){
                    gTarget=lsMemberHelper.lsGetMember(gGuild,reqUser);
                    if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                slashReplyCheckDm();
                menuLevels(gTarget);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        boolean isPatreon=false;
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
                    if (items[i].equalsIgnoreCase("w") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_week;
                    }
                    if (items[i].equalsIgnoreCase("d") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_day;
                    }
                    if (items[i].equalsIgnoreCase("h") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_hour;
                    }
                    if (items[i].equalsIgnoreCase("m") && i != 0) {
                        timeset += Integer.parseInt(items[i - 1]) * milliseconds_minute;
                    }
                }
                logger.info(fName + ".timeset=" +timeset);
                return  timeset;
            }
            catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
        }
        public Boolean getProfile(Member member){
            String fName="[getProfile]";
            logger.info(fName);
            logger.info(fName + ".member:"+ member.getId()+"|"+member.getUser().getName());
            if(!gNewUserProfile.build(gGlobal,member,gBDSMCommands)){
                return false;
            }
            if(gNewUserProfile.cTIMELOCK.getTimeLockStart()<60000*15){
                gNewUserProfile.cTIMELOCK.setTimeLockStart(60000*15);
            }
            return true;
        }

    }}
