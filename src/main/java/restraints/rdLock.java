package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.ls.lsUsefullFunctions;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import org.apache.log4j.Logger;
import restraints.models.enums.LOCKTYPES;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdLock extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iLock{
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Lock",gCommand="lock";
    public rdLock(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Lock-DiscordRestraints";
        this.help = "rdLock";
        this.aliases = new String[]{gCommand,"rd"+gCommand};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdLock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdLock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdLock(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdLock(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdLock(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdLock(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdLock(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
                setTitleStr(rdLock.this.sMainRTitle);setPrefixStr(rdLock.this.llPrefixStr);setCommandStr(rdLock.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdLock_commands");
                lsUsefullFunctions.setThreadName4Display("rdLock");
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
                else
                if(gIsForward){
                    logger.info(cName + fName + "forward@");
                    if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    boolean isInvalidCommand=true;
                    if(!checkIFAllowed2UseCommand2_text()){
                        return;
                    }
                    logger.info(fName+".gRawForward="+gRawForward);
                    if(gRawForward.isBlank()){
                        menuLevels(gTarget);isInvalidCommand=false;
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
                                switch (gItems[1].toLowerCase()){
                                    case iLock.commandUnlock:
                                    case iLock.commandCurse:
                                    case iLock.commandDefault:
                                    case iLock.commandExtra:
                                    case iLock.commandGlue:
                                    case iLock.commandLock:
                                    case iLock.commandStitch:
                                    case iLock.commandTape:
                                    case iLock.commandWarden:
                                        rLock(gTarget,gItems[1]);isInvalidCommand=false;
                                    case commandPermalock:
                                    case commandUnseal:
                                        rPermalock(gTarget,gItems[1]);isInvalidCommand=false;
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
                            else{
                                switch (gItems[0].toLowerCase()){
                                    case iLock.commandUnlock:
                                    case iLock.commandCurse:
                                    case iLock.commandDefault:
                                    case iLock.commandExtra:
                                    case iLock.commandGlue:
                                    case iLock.commandLock:
                                    case iLock.commandStitch:
                                    case iLock.commandTape:
                                    case iLock.commandWarden:
                                        rLock(gItems[0]);isInvalidCommand=false;
                                    case commandPermalock:
                                    case commandUnseal:
                                        rPermalock(gItems[0]);isInvalidCommand=false;
                                }
                            }

                            if(isInvalidCommand){
                                menuLevels(null);isInvalidCommand=false;
                            }
                        }

                    }
                    //logger.info(fName+".deleting op message");
                    //llQuckCommandMessageDelete(gEvent);
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
        String desc="N/A";
        String quickSummonWithSpace2=llPrefixStr+"lock <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        desc+=newLine+quickSummonWithSpace2+iLock.commandUnlock+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandCurse+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandDefault+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandExtra+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandGlue+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandLock+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandStitch+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandTape+endLine;
        desc+=newLine+quickSummonWithSpace2+iLock.commandWarden+endLine;
        embed.addField("Commands",desc,false);
        embed.addField("Permalock", "Locks the restraints permanently\n`" + llPrefixStr + "lock <@Pet> permalock`", false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server sub-options","Type `"+llPrefixStr +"lock server` for managing this command.",false);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    
    private void rLock(String command) {
        String fName = "[rLock]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}


        if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
            logger.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(sRTitle, strLocked.replaceAll("!LOCKER",gNewUserProfile.cLOCK.getUserWhoLockedPet()), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(sRTitle, strAccessSet2Pet, llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            sendOrUpdatePrivateEmbed(sRTitle, strAccessSet2Public, llColorRed);
            return;
        }
        if(command.equalsIgnoreCase(commandLock)){
            if(gIsOverride||!gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locking");
                gNewUserProfile.cLOCK.setLocked( true); gNewUserProfile.cLOCK.setLockedBy( gUser.getId());
                sendOrUpdatePrivateEmbed(sMainRTitle,"You locked your restraints!", llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle, stringReplacer("!WEARER has locked their restraints. Hope they wont lose their key."),llColors.llColorBlue1);
            }else{
                logger.info(fName + ".not unlocked");
                llSendQuickEmbedMessage(gUser,sMainRTitle,"You can't lock that is already locked!", llColorRed);
            }
        }else
        if(command.equalsIgnoreCase(commandUnlock)){
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sMainRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
               return;
            }
            if(!gIsOverride&&iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                logger.info(fName + ".can't do, timelocked");
                sendOrUpdatePrivateEmbed(sMainRTitle,"Can't unlock while timelock is running.", llColorRed);
                return;
            }
            gNewUserProfile.cLOCK.setLocked(false);gNewUserProfile.cLOCK.setLockedBy("");
            switch (gNewUserProfile.cLOCK.getTypeAsString().toLowerCase()){
                case "glue":
                    sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOffGlue1),llColors.llColorBlue1);
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOffGlue2),llColors.llColorBlue1);
                    break;
                case "tape":
                    sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOffTape1),llColors.llColorBlue1);
                   lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOffTape2),llColors.llColorBlue1);
                    break;
                case "extra":
                    sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOffExtra1),llColors.llColorBlue1);
                   lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOffExtra2),llColors.llColorBlue1);
                    break;
                case "stitch":
                    sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOffStitch1),llColors.llColorBlue1);
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOffStitch2),llColors.llColorBlue1);
                    break;
                default:
                    sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOffDefault1),llColors.llColorBlue1);
                   lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle, stringReplacer(iLock.strSoloOffDefault2),llColors.llColorBlue1);
            }
        }else
        if(command.equalsIgnoreCase(commandDefault)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.DEFAULT);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
            sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOnDefault1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOnDefault2),llColors.llColorBlue1);
        }else
        if(command.equalsIgnoreCase(commandGlue)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.GLUE);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
            sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOnGlue1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOnGlue2),llColors.llColorBlue1);
        }else
        if(command.equalsIgnoreCase(commandCurse)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.CURSE);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
        }else
        if(command.equalsIgnoreCase(commandTape)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.TAPE);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
            sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOnTape1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOnTape2),llColors.llColorBlue1);
        }else
        if(command.equalsIgnoreCase(commandExtra)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.EXTRA);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
            sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOnExtra1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOnExtra2),llColors.llColorBlue1);
        }else
        if(command.equalsIgnoreCase(commandStitch)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.STITCH);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
            sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strSoloOnStitch1),llColors.llColorBlue1);
            lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strSoloOnStitch2),llColors.llColorBlue1);
        }else
        if(command.equalsIgnoreCase(commandWarden)){
            gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.WARDEN);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
        }
        saveProfile();
    }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
    private void rLock(Member mtarget, String command) {
        String fName = "[rLock]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
            }else{
                logger.info(fName + ".default message");
                sendOrUpdatePrivateEmbed(sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
            }
            return;
        }
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strCantDueLockedTarget), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(sRTitle,strCantDueAccess, llColorRed);return;
        }
        if(command.equalsIgnoreCase(commandUnlock)&&!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
            logger.info(fName + ".permalock");
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(strCantDuePermalockTarget),llColorRed);
            return;
        }
        if(gAskHandlingHelper.isAsk()){
            logger.info(fName + ".requesting update restraint");
            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can update your locks !");
            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can update !TARGET's locks!");
            gAskHandlingHelper.doAsk(() -> {rLock_Save4Target(target,command);});
            return;
        }
        rLock_Save4Target(target,command);

    }
        private void rLock_Save4Target(User target,String command) {
            String fName = "[rLock_Save4Target]";
            logger.info(fName);
            logger.info(fName + ".target="+target.getName()+"("+target.getId()+")");
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(commandUnlock)){
                gNewUserProfile.cLOCK.setLocked(false);gNewUserProfile.cLOCK.setLockedBy("");
                switch (gNewUserProfile.cLOCK.getTypeAsString().toLowerCase()){
                    case "glue":
                        sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strTargetOffGlue1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOffGlue2),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOffGlue3),llColors.llColorBlue1);
                        break;
                    case "tape":
                        sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strTargetOffTape1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOffTape2),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOffTape3),llColors.llColorBlue1);
                        break;
                    case "extra":
                        sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strTargetOffExtra1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOffExtra2),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOffExtra3),llColors.llColorBlue1);
                        break;
                    case "stitch":
                        sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strTargetOffStitch1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOffStitch2),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOffStitch3),llColors.llColorBlue1);
                        break;
                    default:
                        sendOrUpdatePrivateEmbed(sRTitle, stringReplacer(iLock.strTargetOffDefault1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOffDefault2),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle, stringReplacer(iLock.strTargetOffDefault3),llColors.llColorBlue1);
                }
            }
            if(command.equalsIgnoreCase(commandDefault)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.DEFAULT);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iLock.strTargetOnDefault1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOnDefault2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOnDefault3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandGlue)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.GLUE);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iLock.strTargetOnGlue1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOnGlue2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOnGlue3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandCurse)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.CURSE);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
            }
            if(command.equalsIgnoreCase(commandTape)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.TAPE);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iLock.strTargetOnTape1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOnTape2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOnTape3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandExtra)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.EXTRA);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iLock.strTargetOnExtra1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOnExtra2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOnExtra3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandStitch)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.STITCH);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iLock.strTargetOnStitch1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,stringReplacer(iLock.strTargetOnStitch2),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iLock.strTargetOnStitch3),llColors.llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandWarden)){
                gNewUserProfile.cLOCK.setLocked(true);gNewUserProfile.cLOCK.setType(LOCKTYPES.WARDEN);gNewUserProfile.cLOCK.setLockedBy(gMember.getId());

            }
            saveProfile();
            if(isMenu)menuLevelsSomebody();

        }
        boolean isMenu=false;
        String gCommandFileMainPath =gFileMainPath+"lock/menuLevels.json",gAskFilePath=gFileMainPath+"lock/ask.json";
        private void menuLevels(Member mtarget){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    if(!gIsOverride&&gNewUserProfile.cCONFINE.isAuthorConfinedAndNotSameConfinment(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                        logger.info(fName + ".confinement exception");
                        if(gNewUserProfile.cCONFINE.isConfined()){
                            sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                        }else{
                            sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strTargetIsNotLockedWithYou),llColorRed);
                        }
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            sendOrUpdatePrivateEmbed(sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                        }
                        return;
                    }
                    menuLevelsSomebody();
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsWearer();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuLevelsWearer(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                isMenu=true;
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                boolean isLocked=gNewUserProfile.cLOCK.isLocked();String type=gNewUserProfile.cLOCK.getTypeAsString();
                desc="Locked: "+isLocked;
                if(type==null||type.isBlank())type="default";
                desc+="\nLock Type:"+type;
                if(isLocked){
                    String lockedById=gNewUserProfile.cLOCK.getLockedByAsString();
                    if(lockedById.equalsIgnoreCase(gNewUserProfile.getMember().getId())){
                        desc+="\nBy: self";
                    }else{
                        String lockedByMention=llGetMemberMention(gGuild,lockedById);
                        if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){

                            desc+="\nBy: <"+lockedById+">";
                        }else{
                            desc+="\nBy: "+lockedByMention;

                        }
                    }
                }
                embed.addField("Status", desc, false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" unlock";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" default";
                desc+="\ncant do self, others need too ";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" glue";
                //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" curse";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" tape";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" extra";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)+" stitch";
                //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" warden";
                embed.addField(" ", "Please select a lock option :"+desc, false);
                if(gNewUserProfile.isWearerDenied0withException()){
                    desc="";
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained())desc+=iRdStr.strRestraintJacketArms;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrained())desc+= iRdStr.strRestraintArmsCuffs;
                    if(gNewUserProfile.cMITTS.isOn())desc+=iRdStr.strRestraintMitts;
                    if(gNewUserProfile.cSUIT.isBDSMSuitOn()){
                        if(gNewUserProfile.cSUIT.getSuitType()== SUITTYPE.Bitchsuit){
                            desc+=iRdStr.strRestraintBDSMSuitBitchsuit;
                        }else
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack){
                            desc+=iRdStr.strRestraintBDSMSuitHogsack;
                        }else
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack){
                            desc+=iRdStr.strRestraintBDSMSuitSleepsack;
                        }
                    }
                    if(gNewUserProfile.cENCASE.isEncased())desc+=iRdStr.strRestraintEncased;
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraints+desc,false);
                }
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_lock");
                    if(gNewUserProfile.cLOCK.isLocked()){
                        switch (gNewUserProfile.cLOCK.getType()){
                            case DEFAULT:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias1);break;
                            case GLUE:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias2);break;
                            case CURSE:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias3);break;
                            case TAPE:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias4);break;
                            case EXTRA:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias5);break;
                            case STITCH:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias6);break;
                        }
                       }else{
                        messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias0);
                    }
                    if(gNewUserProfile.canNewRestrictionsDeny2ChangeTheirRestraints()||gNewUserProfile.isWearerDenied0withException()){
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    lcMessageBuildComponent.Button buttonLock=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                    lcMessageBuildComponent.Button buttonPermalock=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.cLOCK.isPermaLocked()){
                        buttonLock.setCustomId(lsUnicodeEmotes.aliasUnlock).setStyle(ButtonStyle.SUCCESS).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
                    }else {
                        buttonLock.setCustomId(lsUnicodeEmotes.aliasLock).setStyle(ButtonStyle.DANGER).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                    }
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        buttonPermalock.setDisable();
                        buttonLock.setStyle(ButtonStyle.DANGER).setDisable();
                    }
                    if(!gIsOverride&&gNewUserProfile.canNewRestrictionsDeny2ChangeTheirRestraints()||gNewUserProfile.isWearerDenied0withException()){
                        buttonLock.setDisable();
                        buttonPermalock.setDisable();
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsWearerListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuLevelsSomebody(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                isMenu=true;
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getAsMention()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                boolean isLocked=gNewUserProfile.cLOCK.isLocked();String type=gNewUserProfile.cLOCK.getTypeAsString();
                desc="Locked: "+isLocked;
                if(type==null||type.isBlank())type="default";
                desc+="\nLock Type:"+type;
                if(isLocked){
                    String lockedById=gNewUserProfile.cLOCK.getLockedByAsString();
                    if(lockedById.equalsIgnoreCase(gNewUserProfile.getMember().getId())){
                        desc+="\nBy: self";
                    }else{
                        String lockedByMention=llGetMemberMention(gGuild,lockedById);
                        if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){

                            desc+="\nBy: <"+lockedById+">";
                        }else{
                            desc+="\nBy: "+lockedByMention;

                        }
                    }
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        desc+="\nIs permalocked";
                    }
                }
                embed.addField("Status", desc, false);
                desc="";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0)+" unlock";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1)+" default";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2)+" glue";
                //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3)+" curse";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4)+" tape";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5)+" extra";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6)+" stitch";
                //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" warden";
                embed.addField(" ", "Please select a lock option for "+gNewUserProfile.getMember().getAsMention()+":"+desc, false);
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gNewUserProfile.cLOCK.isLocked()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(gNewUserProfile.cLOCK.getType()!= LOCKTYPES.DEFAULT)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(gNewUserProfile.cLOCK.getType()!= LOCKTYPES.GLUE)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    //if(gNewUserProfile.cLOCK.getType()!=LOCKTYPES.CURSE) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(gNewUserProfile.cLOCK.getType()!= LOCKTYPES.TAPE)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    if(gNewUserProfile.cLOCK.getType()!= LOCKTYPES.EXTRA)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    if(gNewUserProfile.cLOCK.getType()!= LOCKTYPES.STITCH) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);messageComponentManager.selectContainer=messageComponentManager.messageBuildComponent_Select.getSelectById("select_lock");
                    if(!gNewUserProfile.canNewRestrictionsDeny2ChangeTheirRestraints()||(!gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&!gNewUserProfile.cARMCUFFS.areArmsRestrained()&&!gNewUserProfile.cENCASE.isEncased()&&!gNewUserProfile.cMITTS.isOn()&&!gNewUserProfile.cSUIT.isBDSMSuitOn())){
                        if(gNewUserProfile.cLOCK.isLocked()){
                            if(gNewUserProfile.cLOCK.getType()== LOCKTYPES.DEFAULT)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias1);
                            if(gNewUserProfile.cLOCK.getType()==LOCKTYPES.GLUE)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias2);
                            if(gNewUserProfile.cLOCK.getType()==LOCKTYPES.CURSE)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias3);
                            if(gNewUserProfile.cLOCK.getType()==LOCKTYPES.TAPE)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias4);
                            if(gNewUserProfile.cLOCK.getType()==LOCKTYPES.EXTRA)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias5);
                            if(gNewUserProfile.cLOCK.getType()==LOCKTYPES.STITCH)messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias6);
                        }else{
                            messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.alias0);
                        }
                    }
                    lcMessageBuildComponent.Button buttonLock=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                    lcMessageBuildComponent.Button buttonPermalock=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                    if(gNewUserProfile.cLOCK.isLocked()&&!gNewUserProfile.cLOCK.isPermaLocked()){
                        buttonLock.setCustomId(lsUnicodeEmotes.aliasUnlock).setStyle(ButtonStyle.SUCCESS).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
                    }else {
                        buttonLock.setCustomId(lsUnicodeEmotes.aliasLock).setStyle(ButtonStyle.DANGER).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                    }
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        buttonPermalock.setDisable();
                        buttonLock.setStyle(ButtonStyle.DANGER).setDisable();
                    }
                    if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gMember.getId())&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gMember.getId())){
                        buttonLock.setDisable();
                        buttonPermalock.setDisable();
                    }

                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsSomebodyListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName + "close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:
                                        logger.info(fName + "close");
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                        return;
                                    case lsUnicodeEmotes.aliasLock:
                                        rLock(lsUnicodeEmotes.aliasLock);
                                        break;
                                    case lsUnicodeEmotes.aliasUnlock:
                                        rLock(lsUnicodeEmotes.aliasUnlock);
                                        break;
                                    case lsUnicodeEmotes.aliasFire:
                                        rPermalock(commandPermalock);
                                        break;
                                    default:
                                        rLock(level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.alias0:level=commandUnlock;break;
                                    case lsUnicodeEmotes.alias1:level=commandDefault;break;
                                    case lsUnicodeEmotes.alias2:level=commandGlue;break;
                                    case lsUnicodeEmotes.alias3:level=commandCurse;break;
                                    case lsUnicodeEmotes.alias4:level=commandTape;break;
                                    case lsUnicodeEmotes.alias5:level=commandExtra;break;
                                    case lsUnicodeEmotes.alias6:level=commandStitch;break;
                                }
                                if(!level.isBlank()){
                                    rLock(level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){level=commandUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandDefault;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandGlue;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandCurse;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandTape;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level=commandExtra;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){level=commandStitch;}
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rLock(level);
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }else{
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){level=commandUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandDefault;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandGlue;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandCurse;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandTape;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level=commandExtra;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){level=commandStitch;}
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rLock(level);
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuLevelsSomebodyListener(Message message){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName + "close");
                                        return;
                                    case lsUnicodeEmotes.aliasInformationSource:
                                        logger.info(fName + "close");
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                       return;
                                    case lsUnicodeEmotes.aliasLock:
                                        rLock(gNewUserProfile.getMember(),lsUnicodeEmotes.aliasLock);
                                        break;
                                    case lsUnicodeEmotes.aliasUnlock:
                                        rLock(gNewUserProfile.getMember(),lsUnicodeEmotes.aliasUnlock);
                                        break;
                                    case lsUnicodeEmotes.aliasFire:
                                        rPermalock(gNewUserProfile.getMember(),commandPermalock);
                                        break;
                                    default:
                                        rLock(gNewUserProfile.getMember(),level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsSomebody();

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value.toLowerCase()){
                                    case lsUnicodeEmotes.alias0:level=commandUnlock;break;
                                    case lsUnicodeEmotes.alias1:level=commandDefault;break;
                                    case lsUnicodeEmotes.alias2:level=commandGlue;break;
                                    case lsUnicodeEmotes.alias3:level=commandCurse;break;
                                    case lsUnicodeEmotes.alias4:level=commandTape;break;
                                    case lsUnicodeEmotes.alias5:level=commandExtra;break;
                                    case lsUnicodeEmotes.alias6:level=commandStitch;break;
                                }
                                if(!level.isBlank()){
                                    rLock(gNewUserProfile.getMember(),level);
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsSomebody();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){level=commandUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandDefault;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandGlue;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandCurse;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandTape;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level=commandExtra;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){level=commandStitch;}

                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rLock(gNewUserProfile.getMember(),level);
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }else{
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){level=commandUnlock;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){level=commandDefault;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){level=commandGlue;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){level=commandCurse;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){level=commandTape;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){level=commandExtra;}
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){level=commandStitch;}

                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rLock(gNewUserProfile.getMember(),level);
                                        }
                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
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
                User user=null;
                boolean subdirProvided=false;
                boolean lockProvided=false,lockValue=false;
                slashReplyPleaseWait();
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case llCommonKeys.SlashCommandReceive.user:
                            if(option.getType()== OptionType.USER){
                                user=option.getAsUser();
                            }
                            break;
                        case llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;
                        case "lock":
                            if(option.getType()== OptionType.BOOLEAN){
                                lockValue=option.getAsBoolean();
                                lockProvided=true;
                            }
                            break;
                    }
                }
                if(user!=null&&gMember.getIdLong()!=user.getIdLong()){
                    gTarget=lsMemberHelper.lsGetMember(gGuild,user);
                    if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                gCurrentInteractionHook=gSlashInteractionHook;
                String subcommand=gSlashCommandEvent.getSubcommandName();
                if(subcommand==null)subcommand="";
                if(subdirProvided||!subcommand.equalsIgnoreCase("padlock")||!lockProvided){
                    menuLevels(gTarget);
                    return;
                }
                String lockStr="";
                if(lockValue)lockStr=commandLock;else lockStr=commandUnlock;
                if(gTarget==null){
                    rLock(lockStr);
                }else{
                    rLock(gTarget,lockStr);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        private void rPermalock(String command){
            String fName="[rPermalock]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&!llMemberIsAdministrator(gMember)){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sMainRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cAUTH.isOwned()&&!(llMemberIsAdministrator(gMember)&&command.equalsIgnoreCase(commandUnseal))){
                logger.warn(fName + ".no access");
                sendOrUpdatePrivateEmbed(sMainRTitle,"Denied, you are owned!", llColorRed); return;
            }
            if(command.equalsIgnoreCase(commandPermalock)&&!gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".do permalock");
                 rPermalockWearerAsk();
            }else
            if(command.equalsIgnoreCase(commandUnseal)&&gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".do unseal");
                if(!gIsOverride&&iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    logger.info(fName + ".can't do, timelocked");
                    sendOrUpdatePrivateEmbed(sMainRTitle,"Can't unlock while timelock is running.", llColorRed);
                    return;
                }else{
                    gNewUserProfile.createEntities();
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(llColorOrange);
                embed.setDescription("Are you sure you want to unseal your gears?");
                embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+", by using your Admin powers you break the locks ",false);
                embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" if you disagree, nahhh, i'm just an object .",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {

                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                    gNewUserProfile.cLOCK.setPermaLocked(false);
                                    llSendQuickEmbedMessage(gUser,sMainRTitle,"You managed to break the perma-locks, freeing yourself from your permanent bindings.", llColorBlue1);
                                    saveProfile();
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llMessageDelete(message));
            }
        }
        private void rPermalock(Member mtarget,String command){
            String fName="[rPermalock]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            User target=mtarget.getUser();
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!(llMemberIsAdministrator(gMember)&&command.equalsIgnoreCase(commandUnseal))){
                logger.warn(fName + ".no access");
                sendOrUpdatePrivateEmbed(sMainRTitle,iAuth.deniedOnlyOwnerAccess, llColorRed);
            }else
            if(gNewUserProfile.cLOCK.isPermaLocked()&&!gIsOverride&&!llMemberIsAdministrator(gMember)){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sMainRTitle,"Can't manipulate "+target.getAsMention()+" restraints as they permanently locked!", llColorRed);
            }else
            if(command.equalsIgnoreCase(commandPermalock)&&!gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".do permalock");
                rPermalockOwnerAsk();

            }else
            if(command.equalsIgnoreCase(commandUnseal)&&gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".do unseal");
                if(!gIsOverride&& iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    logger.info(fName + ".can't do, timelocked");
                    sendOrUpdatePrivateEmbed(sMainRTitle,"Can't unlock while timelock is running.", llColorRed);
                    return;
                }else{
                    gNewUserProfile.createEntities();
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(llColorOrange);
                embed.setDescription("Are you sure you want to unseal "+target.getAsMention()+"'s gears?");
                embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+", by using your Admin powers you break the locks.",false);
                embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" if you disagree, nahhh, they just an object .",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                    gNewUserProfile.cLOCK.setPermaLocked(false);
                                    llSendQuickEmbedMessage(gUser,sMainRTitle,"You managed to break the perma-locks, freeing "+target.getAsMention()+" from their permanent bindings.", llColorBlue1);
                                    saveProfile();
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llMessageDelete(message));
            }

        }
        private void rPermalockUnsealSomebodyAsk(){
            String fName="[rPermalockUnsealSomebodyAsk]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription(stringReplacer("Are you sure you want to unseal !WEARER's gears?"));
            messageComponentManager.loadMessageComponents(gAskFilePath);
            Message message=null;
            try {
                logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=lsMessageHelper.lsSendEmbed(gNewUserProfile.getMember().getUser(),embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                if(message==null)throw  new Exception("could not create message with actionrows");
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=lsMessageHelper.lsSendMessageResponse(gNewUserProfile.getMember().getUser(),embed);
            }
            rPermalockUnsealSomebodyAskListener(message);
        }
        private void rPermalockUnsealSomebodyAskListener(Message message){
            String fName="[rPermalockUnsealSomebodyAskListener]";
            logger.info(fName);
            try {
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            //if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasGreenCircle: rPermalockUnsealSomebodyAskResponse("1");break;
                                    case lsUnicodeEmotes.aliasRedCircle:rPermalockUnsealSomebodyAskResponse("0"); break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gMember.getIdLong()),
                        e -> {
                            try {

                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                    rPermalockUnsealSomebodyAskResponse("1");
                                }else{
                                    rPermalockUnsealSomebodyAskResponse("0");
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockUnsealSomebodyAskResponse(String command){
            String fName="[rPermalockUnsealSomebodyAskListener]";
            logger.info(fName);
            try {
                switch (command){
                    case "1":
                        gNewUserProfile.cLOCK.setPermaLocked(true);gNewUserProfile.cLOCK.setLocked( true); gNewUserProfile.cLOCK.setLockedBy( gUser.getId());
                        sendOrUpdatePrivateEmbed(sMainRTitle,stringReplacer("You burn the keys to !TARGET's locks and fill their holes with rubber cement xtra hold. The gears are permanently locked. Only special tool can free them now."), llColorBlue1);
                        llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer("!USER burns the keys to your locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on you. Only special tool can free you now."), llColorBlue1);
                        llSendMessageWithDelete(gGlobal,gTextChannel,stringReplacer("!USER burns the keys to !TARGET's locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on !TARGET. Only special tool can free them now."));
                        saveProfile();
                        break;
                    case "0":
                        sendOrUpdatePrivateEmbed(sMainRTitle,stringReplacer("You decided to not lock your gears permanently."), llColorRed_CoralPink);
                        break;
                }
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockOwnerAsk(){
            String fName="[rPermalockOwnerAsk]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription("Are you sure you want to permanently lock "+gNewUserProfile.getMember().getAsMention()+"'s gears?\nEven their owner& sec onwers can't undo it.\nOnly the runaway command and server Admin(s) can undo it.");
            messageComponentManager.loadMessageComponents(gAskFilePath);
            Message message=null;
            try {
                logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=lsMessageHelper.lsSendEmbed(gNewUserProfile.getMember().getUser(),embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                if(message==null)throw  new Exception("could not create message with actionrows");
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=lsMessageHelper.lsSendMessageResponse(gNewUserProfile.getMember().getUser(),embed);
            }
            rPermalockOwnerAskListener(message);
        }
        private void rPermalockOwnerAskListener(Message message){
            String fName="[rPermalockOwnerAskListener]";
            logger.info(fName);
            try {
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            //if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasGreenCircle: rPermalockOwnerAskResponse("1");break;
                                    case lsUnicodeEmotes.aliasRedCircle:rPermalockOwnerAskResponse("0"); break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gMember.getIdLong()),
                        e -> {
                            try {

                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                    rPermalockOwnerAskResponse("1");
                                }else{
                                    rPermalockOwnerAskResponse("0");
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockOwnerAskResponse(String command){
            String fName="[rPermalockOwnerAskListener]";
            logger.info(fName);
            try {
                switch (command){
                    case "1":
                        gNewUserProfile.cLOCK.setPermaLocked(true);gNewUserProfile.cLOCK.setLocked( true); gNewUserProfile.cLOCK.setLockedBy( gUser.getId());
                        sendOrUpdatePrivateEmbed(sMainRTitle,stringReplacer("You burn the keys to !TARGET's locks and fill their holes with rubber cement xtra hold. The gears are permanently locked. Only special tool can free them now."), llColorBlue1);
                        llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer("!USER burns the keys to your locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on you. Only special tool can free you now."), llColorBlue1);
                        llSendMessageWithDelete(gGlobal,gTextChannel,stringReplacer("!USER burns the keys to !TARGET's locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on !TARGET. Only special tool can free them now."));
                        saveProfile();
                        break;
                    case "0":
                        sendOrUpdatePrivateEmbed(sMainRTitle,stringReplacer("You decided to not lock your gears permanently."), llColorRed_CoralPink);
                        break;
                }
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockWearerAsk(){
            String fName="[rPermalockWearerAsk]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription("Are you sure you want to permanently lock your gears?\nEven your owner& sec onwers can't undo it.\nOnly the runaway command and server Admin(s) can undo it.");
            messageComponentManager.loadMessageComponents(gAskFilePath);
            Message message=null;
            try {
                logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=lsMessageHelper.lsSendEmbed(gNewUserProfile.getMember().getUser(),embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                if(message==null)throw  new Exception("could not create message with actionrows");
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=lsMessageHelper.lsSendMessageResponse(gNewUserProfile.getMember().getUser(),embed);
            }
            rPermalockWearerAskListener(message);
        }
        private void rPermalockWearerAskListener(Message message){
            String fName="[rPermalockWearerAskListener]";
            logger.info(fName);
            try {
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            //if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasGreenCircle: rPermalockWearerAskResponse("1");break;
                                    case lsUnicodeEmotes.aliasRedCircle:rPermalockWearerAskResponse("0"); break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gNewUserProfile.getMember().getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                    rPermalockWearerAskResponse("1");
                                }else{
                                    rPermalockWearerAskResponse("0");
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockWearerAskResponse(String command){
            String fName="[rPermalockWearerAskListener]";
            logger.info(fName);
            try {
                switch (command){
                    case "1":
                        gNewUserProfile.cLOCK.setPermaLocked(true);gNewUserProfile.cLOCK.setLocked( true); gNewUserProfile.cLOCK.setLockedBy( gUser.getId());
                        sendOrUpdatePrivateEmbed(sMainRTitle,"You burn the keys to your locks and fill their holes with rubber cement xtra hold. The gears are permanently locked. Only special tool can free them now.", llColorBlue1);
                        llSendMessageWithDelete(gGlobal,gTextChannel,stringReplacer("!USER burns the keys to their locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on !WEARER. Only special tool can free them now."));
                        saveProfile();
                        break;
                    case "0":
                        sendOrUpdatePrivateEmbed(sMainRTitle,"You decided to not lock your gears permanently as you're playing smart and safe", llColorRed_CoralPink);
                        break;
                }
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockPetAsk(){
            String fName="[rPermalockPetAsk]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription(gUser.getAsMention()+" wants to permalock your gears. Are you sure you want to permanently lock your gears?\nEven your owner& sec onwers can't undo it.\nOnly the runaway command and server Admin(s) can undo it.");
            messageComponentManager.loadMessageComponents(gAskFilePath);
            Message message=null;
            try {
                logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                message=lsMessageHelper.lsSendEmbed(gNewUserProfile.getMember().getUser(),embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                if(message==null)throw  new Exception("could not create message with actionrows");
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=lsMessageHelper.lsSendMessageResponse(gNewUserProfile.getMember().getUser(),embed);
            }
            rPermalockPetAskListener(message);
        }
        private void rPermalockPetAskListener(Message message){
            String fName="[rPermalockPetAskListener]";
            logger.info(fName);
            try {
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            //if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id.toLowerCase()){
                                    case lsUnicodeEmotes.aliasWhiteCheckMark:
                                        logger.info(fName+"close");
                                        return;
                                    case lsUnicodeEmotes.aliasGreenCircle: rPermalockPetAskResponse("1");break;
                                    case lsUnicodeEmotes.aliasRedCircle:rPermalockPetAskResponse("0"); break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gNewUserProfile.getMember().getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                    rPermalockPetAskResponse("1");
                                }else{
                                    rPermalockPetAskResponse("0");
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }
        private void rPermalockPetAskResponse(String command){
            String fName="[rPermalockPetAskListener]";
            logger.info(fName);
            try {
                switch (command){
                    case "1":
                        gNewUserProfile.cLOCK.setPermaLocked(true);gNewUserProfile.cLOCK.setLocked( true); gNewUserProfile.cLOCK.setLockedBy( gUser.getId());
                        sendOrUpdatePrivateEmbed(sMainRTitle,stringReplacer("You burn the keys to !WEARER's locks and fill their holes with rubber cement xtra hold. The gears are permanently locked. Only special tool can free them now."), llColorBlue1);
                        llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer("!USER burns the keys to your locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on you. Only special tool can free you now."), llColorBlue1);
                        llSendMessageWithDelete(gGlobal,gTextChannel,stringReplacer("!USER burns the keys to !WEARER's locks and fill their holes with rubber cement xtra hold. The gears are permanently locked on !WEARER. Only special tool can free them now."));

                        saveProfile();
                        break;
                    case "0":
                        sendOrUpdatePrivateEmbed(sMainRTitle,stringReplacer("!WEARER decided to hide under the blanked as they dotn want their gears to be permalocked."), llColorRed_CoralPink);
                        llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sMainRTitle,stringReplacer("You decided to not lock your gears permanently as ordered by !USER as you're playing smart and safe"), llColorRed_CoralPink);
                        break;
                }
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

            }
        }

    }}
