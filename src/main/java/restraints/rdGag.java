package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
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
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;
import org.apache.log4j.Logger;
import restraints.PostSynthesizer.rdVoicePostSynthesizer;
import restraints.models.enums.GAGLEVELS;
import restraints.models.enums.GAGTYPES;
import restraints.models.enums.SUITTYPE;
import restraints.in.*;
import restraints.models.*;
import restraints.models.enums.ACCESSLEVELS;
import userprofiles.entities.rSlaveRegistry;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdGag extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iGag {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String sMainRTitle ="Gag";
	String gQuickPrefix="gag";
    public rdGag(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Gag-DiscordRestraints";
        this.help = "rdGag";
        this.aliases = new String[]{gQuickPrefix,"gags"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdGag(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdGag(lcGlobalHelper global,CommandEvent ev,boolean isForward,String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdGag(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdGag(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdGag(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdGag(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdGag(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
            logger.info(".run build");
            launch(gGlobal,ev);
            gBDSMCommands.muzzle.init();
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
            gBDSMCommands.muzzle.init();
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
            gBDSMCommands.muzzle.init();
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
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
            String fName = "[run]";
            logger.info(".run start");
            try {
                gBDSMCommands.muzzle.init();
                getGuildProfile();
                setTitleStr(rdGag.this.sMainRTitle);setPrefixStr(rdGag.this.llPrefixStr);setCommandStr(rdGag.this.gQuickPrefix);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdGag_commands");
                lsUsefullFunctions.setThreadName4Display("rdGag");
                if(!checkIFAllowed2UseCommand0()){
                    return;
                }
                loadValues();
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuMain(gTarget);
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuMain(gTarget);
                            break;
                    }
                }
                else if(gSlashCommandEvent!=null){
                    /*
                     logger.info(cName + fName + "slash@");
                    if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        return;
                    }
                    else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        return;
                    }
                    else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        return;
                    }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                     */
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
                        menuMain(gTarget);isInvalidCommand=false;
                    }
                    if(gRawForward.equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }
                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    boolean isInvalidCommand = true;
                    if(gArgs.isEmpty()){
                        logger.info(fName+".Args=0");
                        if(!gBasicFeatureControl.getEnable()){
                            logger.info(fName+"its disabled");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                            logger.info(fName+"its not allowed by channel");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                            logger.info(fName+"its not allowed by roles");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                            isInvalidCommand=false;
                        }
                        else {menuMain(null);isInvalidCommand=false;}
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs!=null&&gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            rHelp("main");isInvalidCommand=false;
                        }
                        else if(isCommand2BasicFeatureControl(gItems)){
                            isInvalidCommand=false;
                        }
                        else if(!checkIFAllowed2UseCommand2_text()){
                            return;
                        }

                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");

                            if(!gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)){
                                sendOrUpdatePrivateEmbed(sRTitle,"Denied to use gag command!",llColorRed_Imperial);return;
                            }
                            else if(!gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gTarget)){
                                sendOrUpdatePrivateEmbed(sRTitle,"Denied to target "+gTarget.getAsMention()+"!",llColorRed_Imperial);return;
                            }
                            else if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                menuMain(gTarget);isInvalidCommand=false;
                            }
                            else{
                                if(gItems.length>=4){
                                    switch (gItems[1].toLowerCase()){
                                        case "exception":
                                            if(isGagExceptionCommand(gItems[2])){
                                                rGagException(gTarget,gItems[2].toLowerCase());isInvalidCommand=false;
                                            }
                                            break;
                                        default:
                                            isInvalidCommand= quickCommands(gItems[2], gItems[3], gTarget);break;
                                    }
                                }

                                if(isInvalidCommand&&gItems.length>=3){
                                    switch (gItems[1].toLowerCase()){
                                        case "safety":
                                            switch (gItems[2].toLowerCase()){
                                                case optionDisable:
                                                    rDisableSafety(gTarget,optionDisable);isInvalidCommand=false;
                                                    break;
                                                case optionEnable:
                                                    rDisableSafety(gTarget,optionEnable);isInvalidCommand=false;
                                                    break;
                                            }
                                            break;
                                        case optionVoice:
                                            switch (gItems[2].toLowerCase()){
                                                case optionEnable: case optionDisable:
                                                    rVoiceRestriction(gTarget,gItems[1],gItems[2]);isInvalidCommand=false;
                                                    break;
                                            }
                                            break;
                                        case "exception":
                                            if(isGagExceptionCommand(gItems[2])){
                                                rGagException(gTarget,gItems[2].toLowerCase());isInvalidCommand=false;
                                            }
                                            break;
                                        case nTimeLock:  case "lock":
                                            switch (gItems[2].toLowerCase()){
                                                case "set":
                                                    isInvalidCommand=false;
                                                    if(gItems.length>=4){
                                                        rGagTimeLock(gTarget,gItems[2],gItems[3]);
                                                    }else{
                                                        rGagTimeLock(gTarget,gItems[2],"");
                                                    }
                                                    break;
                                                case "start":
                                                    rGagTimeLock(gTarget,gItems[2],"");isInvalidCommand=false;
                                                    break;
                                                case "red":
                                                    rGagTimeLock(gTarget,gItems[1],"");isInvalidCommand=false;
                                                    break;
                                            }
                                            break;
                                        case iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeather:  case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_tickle:
                                            switch (gItems[2].toLowerCase()){
                                                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_on: case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_off: case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle:
                                                    rGagExtra(gTarget,gItems[2].toLowerCase());
                                                    isInvalidCommand = false;
                                                    break;
                                            }
                                            break;
                                        default:
                                            isInvalidCommand= quickCommands(gItems[1], gItems[2], gTarget);
                                            break;
                                    }
                                }
                                if(isInvalidCommand&&gItems.length>=2){
                                    switch (gItems[1].toLowerCase()){
                                        case optionVoiceMute: case optionVoiceUnmute:
                                            rVoiceRestriction(gTarget,".",gItems[1]);isInvalidCommand=false;
                                            break;
                                        case "custom":
                                            rGagCustom(gTarget);isInvalidCommand=false;
                                            break;
                                    }

                                }
                                if(isInvalidCommand){
                                    isInvalidCommand= quickCommands(gItems[1], "", gTarget);
                                }
                                if(isInvalidCommand&&gTarget!=null){
                                    menuMain(gTarget);isInvalidCommand=false;
                                }
                            }

                        }
                        if(isInvalidCommand){
                            if(isInvalidCommand){
                                if(getProfile()){checkGagTimelock();}
                                if(gItems==null||gItems.length==0){
                                    menuMain(null);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(vRed)){
                                    isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cGAG.release();});}
                                else switch (gItems[0].toLowerCase())
                                {
                                    case "setup":
                                        if(!(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember))){
                                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"Request admin, manage server or manage channel permission.",llColorRed);
                                            isInvalidCommand=false;
                                        }else{
                                            if(gItems.length<2){
                                                menuSetup();isInvalidCommand=false;
                                            }
                                            else if (gItems.length >= 3) {
                                                switch (gItems[1].toLowerCase()){
                                                    case "exceptionmembers":
                                                        switch (gItems[2].toLowerCase()){
                                                            case "add":
                                                                setupAdd2Member();isInvalidCommand=false;
                                                                break;
                                                            case "rem":
                                                            case "remove":
                                                                setupRem4Member();isInvalidCommand=false;
                                                                break;
                                                            case "clear":
                                                                setupClear4Member();isInvalidCommand=false;
                                                                break;
                                                            case "list":
                                                                setupViewList(fieldBannedUsers);isInvalidCommand=false;
                                                                break;
                                                        }
                                                        break;
                                                    case "allowedchannels":
                                                        switch (gItems[2].toLowerCase()){
                                                            case "add":
                                                                setupAdd2Channel(true);isInvalidCommand=false;
                                                                break;
                                                            case "rem":
                                                            case "remove":
                                                                setupRem4Channel(true);isInvalidCommand=false;
                                                                break;
                                                            case "clear":
                                                                setupClear4Channel(true);isInvalidCommand=false;
                                                                break;
                                                            case "list":
                                                                setupViewList(fieldAllowedChannels);isInvalidCommand=false;
                                                                break;
                                                        }
                                                        break;
                                                    case "deniedchannels":
                                                        switch (gItems[2].toLowerCase()){
                                                            case "add":
                                                                setupAdd2Channel(false);isInvalidCommand=false;
                                                                break;
                                                            case "rem":
                                                            case "remove":
                                                                setupRem4Channel(false);isInvalidCommand=false;
                                                                break;
                                                            case "clear":
                                                                setupClear4Channel(false);isInvalidCommand=false;
                                                                break;
                                                            case "list":
                                                                setupViewList(fieldBannedChannels);isInvalidCommand=false;
                                                                break;
                                                        }
                                                        break;
                                                    case "commandroles":
                                                        switch (gItems[2].toLowerCase()){
                                                            case "add":
                                                                setupAdd2Role(fieldGagCommandAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                            case "rem":
                                                            case "remove":
                                                                setupRem4Role(fieldGagCommandAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                            case "clear":
                                                                setupClear4Role(fieldGagCommandAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                            case "list":
                                                                setupViewList(fieldGagCommandAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                        }
                                                        break;
                                                    case "targetroles":
                                                        switch (gItems[2].toLowerCase()){
                                                            case "add":
                                                                setupAdd2Role(fieldGagTargetAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                            case "rem":
                                                            case "remove":
                                                                setupRem4Role(fieldGagTargetAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                            case "clear":
                                                                setupClear4Role(fieldGagTargetAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                            case "list":
                                                                setupViewList(fieldGagTargetAllowedRoles);isInvalidCommand=false;
                                                                break;
                                                        }
                                                        break;
                                                    default:
                                                        menuSetup();isInvalidCommand=false;
                                                        break;
                                                }
                                            }
                                            else if(gItems[1].equalsIgnoreCase("custom")){
                                                    menuCustomGag4Server(); isInvalidCommand=false;
                                            }else{
                                                menuSetup();isInvalidCommand=false;
                                            }
                                        }
                                        break;
                                    case "safety":
                                        if(gItems.length>=2)switch(gItems[1].toLowerCase()){
                                            case optionDisable:
                                                rDisableSafety(optionDisable);isInvalidCommand=false;
                                                break;
                                            case optionEnable:
                                                rDisableSafety(optionEnable);isInvalidCommand=false;
                                                break;
                                        }
                                        break;
                                    case "custom":
                                        rGagCustom();isInvalidCommand=false;
                                        break;
                                    case "type": case "gagtype":
                                        if(gItems.length<2){
                                            logger.warn(fName+".invalid args length");
                                            rHelp("main");isInvalidCommand=false;
                                        }else
                                        if(isGagTypeCommand(gItems[1])){
                                            rGagType(gItems[1].toLowerCase());isInvalidCommand=false;
                                        }
                                        break;
                                    case "exception":
                                        if(gItems.length<2){
                                            logger.warn(fName+".invalid args length");
                                            rHelp("main");isInvalidCommand=false;
                                        }else
                                        if(isGagExceptionCommand(gItems[1])){
                                            rGagException(gItems[1].toLowerCase());isInvalidCommand=false;
                                        }
                                        break;
                                    case nTimeLock: case "lock":
                                        if(gItems.length>1)
                                            switch (gItems[1].toLowerCase()){
                                                case "set":
                                                    isInvalidCommand=false;
                                                    if(gItems.length>=3){
                                                        rGagTimeLock(gItems[1],gItems[2]);
                                                    }else{
                                                        rGagTimeLock(gItems[1],"");
                                                    }
                                                    break;
                                                case "start":
                                                    rGagTimeLock(gItems[1],"");isInvalidCommand=false;
                                                    break;
                                                case "red":
                                                    rGagTimeLock(gItems[1],"");isInvalidCommand=false;
                                                break;
                                            }
                                        break;
                                    case optionVoice:
                                        if(gItems.length>1){
                                            switch (gItems[1].toLowerCase()){
                                                case optionEnable: case optionDisable:
                                                    rVoiceRestriction(gItems[0],gItems[1]);isInvalidCommand=false;
                                                    break;
                                            }
                                        }
                                        break;
                                    case optionVoiceMute: case optionVoiceUnmute:
                                        rVoiceRestriction(".",gItems[1]);isInvalidCommand=false;
                                        break;
                                    case iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeather: case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_tickle:
                                        if(gItems.length>1){
                                            switch (gItems[1].toLowerCase()){
                                                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_status:
                                                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_show: case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_hide:case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle_show:
                                                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_on: case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_off:case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle:
                                                    rGagExtra(gItems[1].toLowerCase());
                                                    isInvalidCommand=false;
                                                    break;
                                            }
                                        }

                                        break;
                                }
                                if(isInvalidCommand&&isGagLevelCommand(gItems[0])){
                                    if(!gBDSMCommands.muzzle.hasPermission2UseGagCommand(gMember)){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Denied to use gag command!",llColorRed_Imperial);return;
                                    }
                                    if(!gBDSMCommands.muzzle.hasPermission2TargetGagCommand(gMember)){
                                        sendOrUpdatePrivateEmbed(sRTitle,"Denied to get targeted!",llColorRed_Imperial);return;
                                    }
                                    rGagBinding(gItems[0].toLowerCase());isInvalidCommand=false;
                                }
                                if(isInvalidCommand)
                                {
                                    menuMain(null);isInvalidCommand=false;
                                }
                            }
                        }
                        if(isInvalidCommand){
                            //test
                            if(getProfile()){checkGagTimelock();}

                        }
                    }
                    if(isInvalidCommand){
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
            logger.info(".run ended");
        }
        private boolean quickCommands(String item1, String item2,Member target){
            String fName="[quickCommands]";
            if(item1!=null)logger.info(fName+"item1="+item1);
            if(item2!=null)logger.info(fName+"item2="+item2);
            if(target!=null)logger.info(fName+"target="+target.getId());
            if (item1!=null&&isGagLevelCommand(item1)) {
                rGagBinding(target, item1.toLowerCase());
                return false;
            }else
            if(item1!=null&&isGagTypeCommand(item1)){
                rGagType(target,item1.toLowerCase());
                return false;
            }else
            if(item2!=null&&isGagTypeCommand(item2)){
                rGagType(target,item2.toLowerCase());
                return false;
            }else
            if (item2!=null&&isGagLevelCommand(item2)) {
                rGagBinding(target, item2.toLowerCase());
                return false;
            }
            return true;
        }
        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            String desc;
            String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> "+gQuickPrefix+" ";
            String quickSummonWithSpace2=llPrefixStr+gQuickPrefix+" <@Pet> ";
            String newLine="\n  `", spacingDual="` , `" , endLine="`";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            embed.addField(strSupportTitle,strSupport,false);
            embed.addField("Important","This gag actually does stuff than leash,cuffs,mits,straitjacket.\nIf you are gagged, your post will be deleted and replaced with a gagged version.",false);
            embed.addField("How does it work?","It copies your post content and deletes it.\nCreates a webhook and uses that to post your original message but altered based on gag level.",false);
            embed.addField("Info","You can post OOC text by using `*text*`,`_text_`,`(text)`. OOC text are text that is not converted during gag convertion.\nIf the message contains `\"` you change the behavior, any text inside `\"text\"` will be converted while the rest will be OOC text.Additionally if it has one and you start the message with it the entire message will be OOC text.\nIf message starts with `((` it will ignore it.\nSafeword is `"+nGagSafeword+"`\n[New]Each gag post will have temporary, 2 minutes, :bomb: and :eye: reaction that allows to delete the post or view ungaged post.",false);
            desc="";
            /*desc+=newLine+quickSummonWithSpace2+nUngag+endLine+ " removes gag";
            desc+="\n[new] `"+quickSummonWithSpace2+GAGLEVELS.Corrupt.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelFaux.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelLoose.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelSevere.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelExtreme.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.NucleusMask.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.DroneMask.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelPuppy.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelKitty.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Pony.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Piggy.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Dinosaur.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Sheep.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Bird.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Cow.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Squeaky.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Paci.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.Turkey.getName()+endLine;
            desc+=newLine+quickSummonWithSpace2+GAGLEVELS.gagLevelMute.getName()+endLine;*/
            desc+=newLine+quickSummonWithSpace2+nUngag+endLine+ " removes gag";
            desc+=newLine+quickSummonWithSpace2+"<levels>"+endLine+ " use of of the gag levels:";
            desc+="\n•  no text replacement: "+ GAGLEVELS.Faux.getName();
            desc+="\n•  replaces characters: "+GAGLEVELS.Loose.getName();
            desc+=", "+GAGLEVELS.Severe.getName();
            desc+=", "+GAGLEVELS.Extreme.getName();
            desc+="\n•  replaces entire sentence: "+GAGLEVELS.NucleusMask.getName();
            desc+=", "+GAGLEVELS.DroneMask.getName();
            desc+=", "+GAGLEVELS.Mute.getName();
            desc+=", "+GAGLEVELS.Toy.getName();
            desc+=", "+GAGLEVELS.Squeaky.getName();
            desc+=", "+GAGLEVELS.Paci.getName();
            desc+=", "+GAGLEVELS.Puppy.getName();
            desc+=", "+GAGLEVELS.Kitty.getName();
            desc+=", "+GAGLEVELS.Pony.getName();
            desc+=", "+GAGLEVELS.Piggy.getName();
            desc+=", "+GAGLEVELS.Dinosaur.getName();
            desc+=", "+GAGLEVELS.Sheep.getName();
            desc+=", "+GAGLEVELS.Bird.getName();
            desc+=", "+GAGLEVELS.Cow.getName();
            desc+=", "+GAGLEVELS.Turkey.getName();
            desc+="\n•  converts the sentence: "+GAGLEVELS.Corrupt.getName();
            desc+=", "+GAGLEVELS.ComputerNerd_Hex.getName();
            desc+=", "+GAGLEVELS.ComputerNerd_Base64.getName();
            desc+=", "+GAGLEVELS.ComputerNerd_Binary.getName();
            desc+=", "+GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
            desc+="\n•  other: "+GAGLEVELS.Corrupt.getName();
            desc+=", "+GAGLEVELS.Training.getName();
            embed.addField("Gag Levels","Use one of this commands:"+desc, false);
            embed.addField("Gag Levels Custom text","Some levels can also include custom texts `"+quickSummonWithSpace2+"custom"+endLine, false);
            desc=newLine+quickSummonWithSpace2+"type "+ GAGTYPES.Ball.getName()+"|"+GAGTYPES.LeatherMuzzle.getName()+"|"+GAGTYPES.WireFrameMuzzle.getName()+"|"+GAGTYPES.Paci.getName()+"|"+GAGTYPES.Tape.getName();
            if(isAdult){
                desc+="|"+GAGTYPES.Dildo.getName()+"|"+GAGTYPES.DReverseildo.getName()+"|"+GAGTYPES.Ring.getName()+"|"+GAGTYPES.Sock.getName()+"|"+GAGTYPES.Underwear.getName();
            }
            desc+=endLine;
            embed.addField("Gag Types","Use one of this commands:"+desc, false);
            desc=newLine+quickSummonWithSpace2+"timelock start"+endLine+" to start the gag timelock";
            desc+=newLine+quickSummonWithSpace2+"timelock set <number values>"+endLine+" set duration.";
            embed.addField("Gag Timelock","Set a timelock for gag:"+desc, false);
            desc=newLine+quickSummonWithSpace2+"exception "+nOn+spacingDual+quickSummonWithSpace2+"exception "+vOff+endLine;
            desc+=newLine+quickSummonWithSpace2+"exception "+vList+endLine;
            desc+=newLine+quickSummonWithSpace2+"exception "+vAdd+" <mentioned channels>"+endLine;
            desc+=newLine+quickSummonWithSpace2+"exception "+vRemove+" <mentioned channels>"+endLine;
            desc+=newLine+quickSummonWithSpace2+"exception "+vClear+endLine;
            embed.addField("Gag Exception","Set exception channels for the gag:"+desc, false);
            embed.addField("Safety","Disable/re-enable gag quick safeword and OOC posts."+newLine+quickSummonWithSpace2+"safety "+optionDisable+"/"+optionEnable+endLine, false);
            if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                embed.addField("Setup","To access the setup `"+llPrefixStr+"gag setup`"+"\nTo add elements or view the list `"+llPrefixStr+"gag setup commandroles|targetroles|allowedchannels|exceptionmembers add|rem|list|clear <@mention>`\n\tcommandroles, list of roles who can give gag commands\n\ttargetroles, list of roles who can get targeted with gag commands\n\tallowedchannels, channels where the gag has effect, if used the gag wont work on channels not part of the list\n\texceptionmembers, members who the gag effect does not work \n\t!!!empty list means no restrictions, it means everyone has access" , false);

            }
            if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server sub-options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
            if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
                logger.info(fName+"sent as slash");
            } else
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }

        private void rGagBinding(String command) {
            String fName = "[rGagBinding]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulaleduepermalocked, llColorRed);
                return;
            }else
            if(gNewUserProfile.isPetDenied2HaveAccessIfRestrained()){
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't restrain wile jacket is on");
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iStraitjacket.strCantTakeItOffWhileJacketed_PetDenied), llColorRed);
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                    logger.info(fName + ".can't restrain wile mitts are on");
                    if(iMitts.isMittsEqualLevel(gNewUserProfile.gUserProfile, iRDPatreon.patreonUser_239748154046545920_mitts.level)){
                        logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                        iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                    }else{
                        logger.info(fName + ".default message");
                        sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything_PetDenied), llColorRed);
                    }
                    return;
                }
                if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                    logger.info(fName + ".can't restrain wile cuffs are on");
                    sendOrUpdatePrivateEmbed(sRTitle,"How do you think you will manipulate your gag while you already have your arms tied?\n~~Change your access to ask or protected.~~", llColorRed);
                    return;
                }
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2public, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cGAG.isOn()&&gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(command.toLowerCase())){
                logger.info(fName + ".same level>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already set to this!", llColorRed);return;
            }else
            if(command.equalsIgnoreCase(nUngag)){
                /*if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                    logger.info(fName + ".can't use do to special suit");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't remove your gag as its part of the suit.", llColorRed);
                    return;
                }else*/
                if(gNewUserProfile.cGAG.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't untie while locked");
                        sendOrUpdatePrivateEmbed(sRTitle,"You can't untie your gag while locked with padlock.", llColorRed);return;
                    }else{
                        //putFieldEntry(nGag,nLevel, nNone);
                       gNewUserProfile.cGAG.setOn( false);
                        if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You removed your gag.", llColorRed);
                        userNotificationGag(actionUnSecured, gUser.getAsMention()+" managed to remove their gag. Someone must have forgot to secure it.");
                        voiceChannelRestriction(-1);
                    }
                }else{
                    logger.info(fName + ".wrist not restrained");
                    sendOrUpdatePrivateEmbed(sRTitle,"You are not gagged, silly.", llColorPurple1);return;
                }
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locked and access exposed");
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You dont have the key to do that!", llColorRed);
                userNotificationGag(actionStruggle,  gUser.getAsMention()+" tries to play with their gag but fail as they dont have the key to do that.");return;
            }else{
                int actionSelected=actionChangeLevel;
                if(!gNewUserProfile.cGAG.isOn()){actionSelected=actionSecured;}
                if(!gTextChannel.isNSFW()&&(command.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())||command.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())||command.equalsIgnoreCase(GAGLEVELS.Toy.getName()))){
                    blockedDM();
                    return;
                }
                if(command.equalsIgnoreCase(GAGLEVELS.Faux.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself just for show.", llColorBlue1);
                    userNotificationGag(actionSelected,  gUser.getAsMention()+" gags themselves just for show.");
                    voiceChannelRestriction(-1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Loose.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself enough to affect your speech but still can talk. ", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves enough to affect their speech.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Severe.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself to the point is becoming difficult to speak. ", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves to the point is becoming difficult to speak");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Extreme.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself so tight that nobody can understand what your saying.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves so tight that nobody will be able to understand them.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Mute.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself till no words you can say.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags themselves till they can't say a word.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Puppy.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself till you can only speak like a good puppy.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags themselves till they  speak like a good puppy.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Kitty.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself till the only word you can say is MEOW..", llColorBlue1);
                    userNotificationGag(actionSecured, gUser.getAsMention()+" gags themselves till the only word they can say is MEOW.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Paci.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a paci like a big baby.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a paci like a big baby.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Pony.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like a good pony.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like a good pony.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like a good toy.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like a good toy.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like an angry dinosaur.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like an angry dinosaur.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Bird.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like birdy.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like birdy.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Sheep.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like a docile sheep.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like a docile sheep.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Piggy.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like a good piggy.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like a good piggy.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Cow.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like a good cow.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like a good cow.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You pull over your head a sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure you only say authorized speech.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" pulls over their head a tight sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You pull over your head a sealed high-tech mask hiding your identity, with an intake hole for feeding and voice synthesizer to ensure you only say authorized speech.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" pulls over their head a sealed high-tech mask hiding your identity, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Turkey.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like the turkey you are for thanksgiving dinner.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like the turkey they are for thanksgiving dinner.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Toy.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself like the toy you are.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves like the toy they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Corrupt.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a smart gag that corrupts your speech.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves  with a smart gag that corrupts your speech.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Training.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a training gag.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves  with a training gag.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.DroneReindeer.getName())||command.equalsIgnoreCase("reindeer")){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.DroneReindeer.getName());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a red gag. Its a special gag to enforce the wearer to speak like a reindeer drone should.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a red gag. Its a special gag to enforce the wearer to speak like a reindeer drone should.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Binary.getName())){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.ComputerNerd_Binary.getName());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a digital enhanced gag set to binary talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a digital enhanced gag set to binary talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_BinaryBlocks.getName())){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.ComputerNerd_BinaryBlocks.getName());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a digital enhanced gag set to binary talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a digital enhanced gag set to binary talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Base64.getName())){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.ComputerNerd_Base64.getName());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a digital enhanced gag set to 64 talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a digital enhanced gag set to 64 talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Hex.getName())){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.ComputerNerd_Hex.getName());
                   gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a digital enhanced gag set to hex talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a digital enhanced gag set to hex talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Pokemon_Pikachu.getName())){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.Pokemon_Pikachu.getName());
                    gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You press a pokeball button and a strange gas spits into your face.", llColorBlue1);
                    //userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a digital enhanced gag set to hex talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Pokemon_Eevee.getName())){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.Pokemon_Eevee.getName());
                    gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You press a pokeball button and a strange gas spits into your face.", llColorBlue1);
                    //userNotificationGag(actionSelected,gUser.getAsMention()+" gags themselves with a digital enhanced gag set to hex talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Chocke.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                    gNewUserProfile.cGAG.setOn( true);
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You block your airway, you will start chocking.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" blocks their airway, they will start chocking.");
                    voiceChannelRestriction(1);
                }
            }
            saveProfile();
        }
        private void rGagType(String command) {
            String fName = "[rGagType]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulaleduepermalocked, llColorRed);
                return;
            }else
            /*if(!gIsOverride&&!llMemberIsBooster(gMember)){
                logger.info(fName + ".can't use do to not a booster");
                sendOrUpdatePrivateEmbed(sRTitle,"Only boosters have access to this extra options!", llColorRed);return;
            }else*/
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2public, llColorRed);return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulateduepartofsuit, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                sendOrUpdatePrivateEmbed(sRTitle,cantduewearingjacket, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                sendOrUpdatePrivateEmbed(sRTitle,cantduearmscuffed, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.getTypeAsString().equalsIgnoreCase(command.toLowerCase())){
                logger.info(fName + ".same level>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already set to this!", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locked and access exposed");
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You dont have the key to do that!", llColorRed);
                userNotificationGag( gUser.getAsMention()+" tries to play with their gag but fail as they dont have the key to do that.");return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(!gTextChannel.isNSFW()&&(command.equalsIgnoreCase(GAGTYPES.Dildo.getName())||command.equalsIgnoreCase(GAGTYPES.DReverseildo.getName())||command.equalsIgnoreCase(GAGTYPES.Underwear.getName())||command.equalsIgnoreCase(GAGTYPES.Ring.getName()))){
                blockedDM();
                return;
            }
            if(command.equalsIgnoreCase(GAGTYPES.Ball.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a ball gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with a ball gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Dildo.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a dildo gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with a dildo gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.DReverseildo.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a reverse-dildo gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with a reverse-dildo gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Paci.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a paci gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with a paci gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Sock.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with some socks.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with some socks.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Underwear.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with an underwear.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with an underwear.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Tape.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with tape, sticky tape.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with tape, sticky tape.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.LeatherMuzzle.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with leather muzzle.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with leather muzzle.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.WireFrameMuzzle.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with wire-framed muzzle.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with wired-framed muzzle.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Ring.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with a ring-gag, ensuring you can't bite and ready to be used by other.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with with a ring-gag, ensuring you can't bite and ready to be used by other.");
                gNewUserProfile.cGAG.setType( GAGTYPES.ReindeerMuzzle.getName());
            }else
            if(command.equalsIgnoreCase(GAGTYPES.ReindeerMuzzle.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You gag yourself with reindeer muzzle.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags themselves with reindeer muzzle.");
            }
            saveProfile();
        }
        private void rGagTimeLock(String command,String value) {
            String fName = "[rGagTimeLock]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulaleduepermalocked, llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2public, llColorRed);return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulateduepartofsuit, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                sendOrUpdatePrivateEmbed(sRTitle,cantduewearingjacket, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                sendOrUpdatePrivateEmbed(sRTitle,cantduearmscuffed, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.getTypeAsString().equalsIgnoreCase(command.toLowerCase())){
                logger.info(fName + ".same level>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already set to this!", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locked and access exposed");
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You dont have the key to do that!", llColorRed);
                userNotificationGag( gUser.getAsMention()+" tries to play with their gag but fail as they dont have the key to do that.");return;
            }
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()&&!command.equalsIgnoreCase("red")){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(command.equalsIgnoreCase("set")){
                if(value==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value can't be null!", llColorRed_Blood);
                    return;
                }
                logger.info(fName + ".value="+value);
                long input=0L;
                try {
                    input=Long.getLong(value);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    sendOrUpdatePrivateEmbed(sRTitle,"Value is invalid!", llColorRed_Blood);
                }
                logger.info(fName + ".input_before="+input);
                input=input*iRestraints.milliseconds_minute;
                logger.info(fName + ".input_after="+input);
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(input<iRestraints.milliseconds_minute*15){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be minimum 15 minutes!", llColorRed_Blood);
                    return;
                }
                if(!isPatreon&&input>iRestraints.milliseconds_hour*6){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be bellow or equal to 6 hours!", llColorRed_Blood);
                    return;
                }
                if(input>iRestraints.milliseconds_day*14){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be bellow or equal to 14 days!", llColorRed_Blood);
                    return;
                }
                if(gNewUserProfile.cGAG.setTimeLockDuration(input)==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to set time!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(null);
                }else{
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You set gag timelock duration to "+lsUsefullFunctions.displayDuration(input)+".", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" set their gag timelock duration to "+lsUsefullFunctions.displayDuration(input)+".");
                }
            }
            if(command.equalsIgnoreCase("start")){
                if(gNewUserProfile.cGAG.startTimeLock()==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to start!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(null);
                    userNotificationGag( gUser.getAsMention()+" started their "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }else{
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You started a "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" started their "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }
            }
            if(command.equalsIgnoreCase("red")) {
                if (gNewUserProfile.cGAG.stopTimeLock(false)==null) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to stop!", llColorRed_Blood);
                    return;
                }
                llSendQuickEmbedMessage(gUser, sRTitle, "Emergency timelock stop used.", llColorRed_Blood);
            }
            saveProfile();
        }
        private void rGagTimeLock(String command,long value) {
            String fName = "[rGagTimeLock]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulaleduepermalocked, llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2public, llColorRed);return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulateduepartofsuit, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                sendOrUpdatePrivateEmbed(sRTitle,cantduewearingjacket, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                sendOrUpdatePrivateEmbed(sRTitle,cantduearmscuffed, llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iMitts.strCantGrabAnything), llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.getTypeAsString().equalsIgnoreCase(command.toLowerCase())){
                logger.info(fName + ".same level>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already set to this!", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locked and access exposed");
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You dont have the key to do that!", llColorRed);
                userNotificationGag( gUser.getAsMention()+" tries to play with their gag but fail as they dont have the key to do that.");return;
            }
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()&&!command.equalsIgnoreCase("red")){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(command.equalsIgnoreCase("set")){
                logger.info(fName + ".value="+value);
                if(value<iRestraints.milliseconds_minute*15){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be minimum 15 minutes!", llColorRed_Blood);
                    return;
                }
                if(value>iRestraints.milliseconds_day*14){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value can be maximum 14 days!", llColorRed_Blood);
                    return;
                }
                if(gNewUserProfile.cGAG.setTimeLockDuration(value)==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to set time!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(null);
                }else{
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You set gag timelock duration to "+lsUsefullFunctions.displayDuration(value)+".", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" set their gag timelock duration to "+lsUsefullFunctions.displayDuration(value)+".");
                }
            }
            if(command.equalsIgnoreCase("start")){
                if(gNewUserProfile.cGAG.startTimeLock()==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to start!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(null);
                    userNotificationGag( gUser.getAsMention()+" started their "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }else{
                    if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You started a "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" started their "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }
            }
            if(command.equalsIgnoreCase("red")) {
                if (gNewUserProfile.cGAG.stopTimeLock(false)==null) {
                    llSendQuickEmbedMessage(gUser, sRTitle, "Failed to stop!", llColorRed_Blood);
                    return;
                }
                llSendQuickEmbedMessage(gUser, sRTitle, "Emergency timelock stop used.", llColorRed_Blood);
            }
            saveProfile();
        }
        private void rGagException(String command) {
            String fName = "[rGagException]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your gag exception list do to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&command.equalsIgnoreCase(nOn)&&gNewUserProfile.cGAG.isExceptionEnabled()){
                logger.info(fName + ".already turned on>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already turned on!", llColorRed);return;
            }else
            if(!gIsOverride&&command.equalsIgnoreCase(vOff)&&!gNewUserProfile.cGAG.isExceptionEnabled()){
                logger.info(fName + ".already turned off>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already turned off!", llColorRed);return;
            }else
            if(command.equalsIgnoreCase("menu")){
                menuExceptions();return;
            }else
            if(command.equalsIgnoreCase(nOn)){
                gNewUserProfile.cGAG.setExceptionEnable( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You enable exceptions for:"+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" enables exceptions for:"+getExceptionChannelMention(), llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vOff)){
                gNewUserProfile.cGAG.setExceptionEnable( false);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You disable exceptions.", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" disables exceptions.", llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vClear)){
                gNewUserProfile.cGAG.setExceptionList( new JSONObject());
                sendOrUpdatePrivateEmbed(sRTitle,"You clear exceptions.", llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vList)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" has the following exceptions:"+getExceptionChannelMention(), llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vAdd)){
                JSONObject list=gNewUserProfile.cGAG.getExceptionList();
                logger.info(fName + ".listExceptionList="+list);
                List<TextChannel>textChannels=gCommandEvent.getMessage().getMentionedChannels();
                if(textChannels.isEmpty()){
                    logger.warn(fName + ".No channel mentioned!");
                    sendOrUpdatePrivateEmbed(sRTitle,"No channels mentioned!", llColorRed);
                    return;
                }
                for(TextChannel textChannel:textChannels){
                    logger.info(fName + ".textChannel:"+textChannel.getName()+"|"+textChannel.getId());
                    if(!list.has(textChannel.getId())){
                        logger.info(fName + ".not present");
                        list.put(textChannel.getId(),"");
                    }
                }
                gNewUserProfile.cGAG.setExceptionList( list);gNewUserProfile.cGAG.setExceptionEnable( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"Updated to following exceptions:"+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" updated to following exceptions:"+getExceptionChannelMention(), llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vRemove)){
                JSONObject list=gNewUserProfile.cGAG.getExceptionList();
                logger.info(fName + ".listExceptionList="+list);
                List<TextChannel>textChannels=gCommandEvent.getMessage().getMentionedChannels();
                if(textChannels.isEmpty()){
                    logger.warn(fName + ".No channel mentioned!");
                    sendOrUpdatePrivateEmbed(sRTitle,"No channels mentioned!", llColorRed);
                    return;
                }
                for(TextChannel textChannel:textChannels){
                    logger.info(fName + ".textChannel:"+textChannel.getName()+"|"+textChannel.getId());
                    if(list.has(textChannel.getId())){
                        logger.info(fName + ".is present");
                        list.remove(textChannel.getId());
                    }
                }
                gNewUserProfile.cGAG.setExceptionList( list);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"Updated to following exceptions:"+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" updated to following exceptions:"+getExceptionChannelMention(), llColorBlue1);
            }
            saveProfile();
        }
        String commandWearerTrue="wearer:true",commandWearerFalse="wearer:false";
        String commandOthersTrue="others:true",commandOthersFalse="others:false";
        private void rGagShow(String command) {
            String fName = "[rGagShow]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your gag eye feature do to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(commandWearerTrue)){
                gNewUserProfile.cGAG.setDisable2Show4Wearer( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You disabled the eye function for yourself.", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" disabled the eye function for themselves.", llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandWearerFalse)){
                gNewUserProfile.cGAG.setDisable2Show4Wearer( false);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You enabled the eye function for yourself.", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" enabled the eye function for themselves.", llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandOthersTrue)){
                gNewUserProfile.cGAG.setDisable2Show4Others( true);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You disabled the eye function for others.", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" disabled the eye function for others.", llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandOthersFalse)){
                gNewUserProfile.cGAG.setDisable2Show4Others( false);
                if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You enabled the eye function for others.", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" enabled the eye function for others.", llColorBlue1);
            }
            saveProfile();
        }
        private String getExceptionChannelMention(){
            String fName = "[getExceptionChannelMention]";
            logger.info(fName);
            try {
                JSONObject list=gNewUserProfile.cGAG.getExceptionList();
                logger.info(fName + ".listExceptionList="+list);
                StringBuilder tmp= new StringBuilder("<no channels>");
                Iterator<String> keys=list.keys();
                boolean notFirst=false;
                while(keys.hasNext()){
                    String id=keys.next();
                    TextChannel textChannel= lsChannelHelper.lsGetTextChannelById(gGuild, id);
                    if(textChannel!=null){
                        if(notFirst){
                            tmp.append(", ").append(textChannel.getAsMention());
                        }else{
                            tmp = new StringBuilder(textChannel.getAsMention());notFirst=true;
                        }
                    }
                }
                return tmp.toString();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return "";
            }
        }
        private void rDisableSafety(String command){
            String fName="[rDisableSafety]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(command.equalsIgnoreCase(optionDisable)){
                logger.info(fName + ".do disable");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(llColorOrange);
                embed.setDescription("Are you sure you want to disable gag safety?\nThis will disable the quick ungag safety `red` and disable the OOC chat.\nIf owned, only your owner can re-enable it.");
                embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" if you agree",false);
                embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" if you disagree",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                   gNewUserProfile.cGAG.setFlagDisableGagOOCException(true);gNewUserProfile.cGAG.setFlagDisableGagSafeWords( true);
                                    if( saveProfile()){
                                        if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You disable the safety for your gag.", llColorBlue1);
                                        llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" disabled their safety for their gag.");
                                    }
                                    if(isMenu){
                                        menuMain(null);
                                    }
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

            }
            if(command.equalsIgnoreCase(optionEnable)){
                logger.info(fName + ".do enable");
                if(!gIsOverride&&gNewUserProfile.cAUTH.isOwned()){
                    logger.warn(fName + ".no access");
                    sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are owned!", llColorRed); return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(llColorOrange);
                embed.setDescription("Are you sure you want to re-neable the gag safety?");
                embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" to enable.",false);
                embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" to abort.",false);
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
                                   gNewUserProfile.cGAG.setFlagDisableGagOOCException(false);gNewUserProfile.cGAG.setFlagDisableGagSafeWords( false);
                                    if( saveProfile()){
                                        if(toMessageSelfCommandAction)sendOrUpdatePrivateEmbed(sRTitle,"You re-enabled your safety for your gag.", llColorBlue1);
                                        llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" re-enabled their safety for their gag.");
                                    }
                                    if(isMenu){
                                        menuMain(null);
                                    }
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
            }

        }
        private void rVoiceRestriction(String category,String command){
            String fName="[rVoiceRestriction]";
            logger.info(fName);
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restrictions as access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your restrictions due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(optionEnable)||command.equalsIgnoreCase(optionVoiceMute)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,true);
                sendOrUpdatePrivateEmbed(sRTitle,"You enabled gag voice mute.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionDisable)||command.equalsIgnoreCase(optionVoiceUnmute)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
                sendOrUpdatePrivateEmbed(sRTitle,"You disabled gag voice mute.", llColorBlue3);
            }
            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gNewUserProfile.gUserProfile.getMember().getVoiceState());
            if(isMenu){
                menuMain(null);
            }
        }
        private void rGagExtra(String command) {
            String fName = "[rGagExtra]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            switch (command){
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeather:case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle:
                    gNewUserProfile.cGAG.extra.toggleLaughterOn();
                    if( gNewUserProfile.cGAG.extra.isLaughterOn()){
                        sendOrUpdatePrivateEmbed(sRTitle,"You enable tickle option for gag.", llColorBlue1);
                    }else{
                        sendOrUpdatePrivateEmbed(sRTitle,"You disable tickle option for gag.", llColorBlue1);
                    }
                    saveProfile();
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_off:
                    gNewUserProfile.cGAG.extra.setLaughterOn(false);
                    sendOrUpdatePrivateEmbed(sRTitle,"You disable tickle option for gag.", llColorBlue1);
                    saveProfile();
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_on:
                    gNewUserProfile.cGAG.extra.setLaughterOn(true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You enable tickle option for gag.", llColorBlue1);
                    saveProfile();
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_status:
                    if(!guildGagProfile.isProfile()){
                        sendOrUpdatePrivateEmbed(sRTitle,"Show tickle missing profile.", llColorRed_Blood);
                    }
                    else if(!guildGagProfile.hasFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                        sendOrUpdatePrivateEmbed(sRTitle,"Show tickle key is missing.",  llColorRed_Blood);
                    }
                    else if(!guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                        sendOrUpdatePrivateEmbed(sRTitle,"Show tickle is set to false.", llColorBlue1);
                    }else{
                        sendOrUpdatePrivateEmbed(sRTitle,"Show tickle is set to true.", llColorBlue1);
                    }
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_hide:
                    if(!lsMemberIsBotOwner(gMember)&&!iRDPatreon.patreonUser_365946661442158603_tickleaddon.lsRegisteredMember(gMember)){
                        sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed_Blood);
                        return;
                    }
                    guildGagProfile.putFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow,false);
                    saveGuildProfile();
                    sendOrUpdatePrivateEmbed(sRTitle,"Hide tickle option for gag.", llColorBlue1);
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_show:
                    if(!lsMemberIsBotOwner(gMember)&&!iRDPatreon.patreonUser_365946661442158603_tickleaddon.lsRegisteredMember(gMember)){
                        sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed_Blood);
                        return;
                    }
                    guildGagProfile.putFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow,true);
                    saveGuildProfile();
                    sendOrUpdatePrivateEmbed(sRTitle,"Show tickle option for gag.", llColorBlue1);
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle_show:
                    if(!lsMemberIsBotOwner(gMember)&&!iRDPatreon.patreonUser_365946661442158603_tickleaddon.lsRegisteredMember(gMember)){
                        sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed_Blood);
                        return;
                    }
                    guildGagProfile.putFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow,!guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow));
                    saveGuildProfile();
                    if(guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                        sendOrUpdatePrivateEmbed(sRTitle,"Show tickle option for gag.", llColorBlue1);
                    }else{
                        sendOrUpdatePrivateEmbed(sRTitle,"Hide tickle option for gag.", llColorBlue1);
                    }
                    break;
            }

        }
        lcJSONGuildProfile guildGagProfile=new lcJSONGuildProfile(null);
        public Boolean getGuildProfile(){
            String fName="[getGuildProfile]";
            logger.info(fName);
            guildGagProfile=gGlobal.getGuildSettings(gGuild,"rdGag");
            if(guildGagProfile==null)guildGagProfile=new lcJSONGuildProfile(gGlobal,"rdGag",gGuild);
            return guildGagProfile.isProfile();
        }
        public Boolean saveGuildProfile(){
            String fName="[saveGuildProfile]";
            logger.info(fName);
            gGlobal.putGuildSettings(guildGagProfile);
            return guildGagProfile.saveProfile();
        }

        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void rGagBinding(Member mtarget,String command) {
            String fName = "[rGagBinding]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".isSameConfinement>return");
                sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                return;
            }
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cGAG.isOn()&&gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(command.toLowerCase())){
                logger.info(fName + ".same level>ignore"); sendOrUpdatePrivateEmbed(sRTitle,"It's already set to this level!", llColorPurple1);return;
            }else
            if(command.equalsIgnoreCase(nUngag)){
                /*if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                    logger.info(fName + ".can't use do to special suit");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't remove their gag as its part of the suit.", llColorRed);
                    return;
                }else*/
                if(gNewUserProfile.cGAG.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't untie while locked");
                        sendOrUpdatePrivateEmbed(sRTitle,"You can't remove "+target.getAsMention()+"'s gags while locked with padlock.", llColorRed);return;
                    }else{
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking to ungag you!");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking to ungag !TARGET!");
                            gAskHandlingHelper.doAsk(() -> {rGagBindingSave4Target(target,command);});
                            return;
                        }
                        rGagBindingSave4Target(target,command);
                    }
                }else{
                    logger.info(fName + ".wrist not restrained");
                    sendOrUpdatePrivateEmbed(sRTitle,"The jaw is not restrained.", llColorPurple1);
                   return;
                }
            }else{
                int actionSelected=actionChangeLevel;
                if(!gNewUserProfile.cGAG.isOn()){actionSelected=actionSecured;}
                if(command.equalsIgnoreCase(GAGLEVELS.Faux.getName())||command.equalsIgnoreCase(GAGLEVELS.Loose.getName())||command.equalsIgnoreCase(GAGLEVELS.Severe.getName())||
                        command.equalsIgnoreCase(GAGLEVELS.Extreme.getName())||command.equalsIgnoreCase(GAGLEVELS.Mute.getName())||command.equalsIgnoreCase(GAGLEVELS.Puppy.getName())||
                        command.equalsIgnoreCase(GAGLEVELS.Kitty.getName())||command.equalsIgnoreCase(GAGLEVELS.Paci.getName())||command.equalsIgnoreCase(GAGLEVELS.Pony.getName())||
                        command.equalsIgnoreCase(GAGLEVELS.Piggy.getName())||command.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())||command.equalsIgnoreCase(GAGLEVELS.Bird.getName())||
                        command.equalsIgnoreCase(GAGLEVELS.Cow.getName())||command.equalsIgnoreCase(GAGLEVELS.Sheep.getName())||command.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())||
                        command.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())||command.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())||command.equalsIgnoreCase(GAGLEVELS.Turkey.getName())||command.equalsIgnoreCase(GAGLEVELS.Corrupt.getName())||command.equalsIgnoreCase(GAGLEVELS.Toy.getName())
                        ||command.equalsIgnoreCase(GAGLEVELS.DroneReindeer.getName())||command.equalsIgnoreCase("reindeer")
                        ||command.equalsIgnoreCase(GAGLEVELS.Training.getName())
                        ||command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Base64.getName()) ||command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Binary.getName()) ||command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_BinaryBlocks.getName()) ||command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Hex.getName())
                        ||command.equalsIgnoreCase(GAGLEVELS.Pokemon_Pikachu.getName())||command.equalsIgnoreCase(GAGLEVELS.Pokemon_Eevee.getName())
                        ||command.equalsIgnoreCase(GAGLEVELS.Chocke.getName())
                ){
                    if(!gTextChannel.isNSFW()&&(command.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())||command.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())||command.equalsIgnoreCase(GAGLEVELS.Toy.getName()))){
                        blockedDM();
                        return;
                    }
                    if(gAskHandlingHelper.isAsk()){
                        logger.info(fName + ".requesting update restraint");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking to gag you!");
                        gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking to gag !TARGET!");
                        gAskHandlingHelper.doAsk(() -> {rGagBindingSave4Target(target,command);});
                        return;
                    }
                    rGagBindingSave4Target(target,command);
                }
            }

        }
        private void rGagBindingSave4Target(User target,String command) {
            String fName = "[rGagBindingSave4Target]";
            logger.info(fName);
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(nUngag)){
                gNewUserProfile.cGAG.setLevel( nNone);gNewUserProfile.cGAG.setOn( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You remove "+target.getAsMention()+"'s gag.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" removes gag.", llColorBlue1);
                userNotificationGag(actionUnSecured,gUser.getAsMention()+" removes "+target.getAsMention()+"'s gag.");
                voiceChannelRestriction(-1);
            }else{
                int actionSelected=actionChangeLevel;
                if(!gNewUserProfile.cGAG.isOn()){actionSelected=actionSecured;}
                if(command.equalsIgnoreCase(GAGLEVELS.Faux.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw for show.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw, lucky its just for show.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw for show.");
                    voiceChannelRestriction(-1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Loose.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw enough for them to speak with some issues.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw enough for them to speak with some issues.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw enough for them to speak with some issues.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Severe.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw, its starting to get hard for them to speak.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw, its starting to get hard to speak.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw, its starting to get hard for them to speak.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Extreme.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw, they can barely say any words.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw, you can barely say any words at all.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw, they can barely say any words.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Mute.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw to the point they can't speak anymore.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw, to the point you can't speak anymore.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw, to the point they can't speak anymore.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Puppy.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You muzzle "+target.getAsMention()+" , like good puppies should be.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" muzzles you like the good puppies should be.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" muzzles "+target.getAsMention()+" like the good puppies should be.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Kitty.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw with a ball of yarn.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw with a ball of yarn.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw with a ball of yarn.");
                    voiceChannelRestriction(1);
                }else
                if(command.equalsIgnoreCase(GAGLEVELS.Paci.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw with a paci like those used for big babies.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw with a paci like those used for big babies.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw with a paci like those used for big babies.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Pony.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw with a bit-gag like those used for ponies.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw with a bit-gag like those used for ponies.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw with a bit gag like those used for ponies.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Piggy.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s jaw with muzzle like used for pigs.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw with a muzzle like used for pigs.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw with a muzzle like used for pigs.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Dinosaur.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s like the big angry dino they are.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw like the big angry dino you are.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw like the big angry dino they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Bird.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s like the bird they are.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw like the bird you are.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw like the bird they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Cow.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s like the cow they are.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw like the cow you are.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw like the cow they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Sheep.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s like the sheep they are.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw like the sheep you are.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw like the sheep they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Squeaky.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+"'s like the toy they are.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags your jaw like the toy you are.", llColorBlue1);
                    userNotificationGag(actionSelected, gUser.getAsMention()+" gags "+target.getAsMention()+"'s jaw like the toy they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.NucleusMask.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You pull over "+target.getAsMention()+" head a sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" pulls over your head a sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure you only say authorized speech.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" pulls over "+target.getAsMention()+" head a tight sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.DroneMask.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You pull over "+target.getAsMention()+" head a sealed high-tech mask, hiding your identity, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" pulls over your head a sealed high-tech mask, hiding their identity, with an intake hole for feeding and voice synthesizer to ensure you only say authorized speech.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" pulls over "+target.getAsMention()+" head a tight sealed high-tech mask, hiding their identity, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Turkey.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" like the turkey they are for thanksgiving dinner.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you like the turkey you are for thanksgiving dinner.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" like the turkey they are for thanksgiving dinner.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Toy.getName())){
                    logger.info(fName + ".gags "+target.getName()+" with "+command+" by"+gUser.getName());
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" like the toy they are.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you like the toy you are.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" like the toy they are.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Corrupt.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a smart gag that corrupts their speech.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with a smart gag that corrupts your speech.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a smart gag that corrupts their speech.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Training.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a training gag.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with a training gag.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a training gag.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.DroneReindeer.getName())||command.equalsIgnoreCase("reindeer")){
                    gNewUserProfile.cGAG.setLevel( GAGLEVELS.DroneReindeer.getName()); gNewUserProfile.cGAG.setType( GAGTYPES.ReindeerMuzzle.getName());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a red gag. Its a special gag to enforce the wearer to speak like a reindeer drone should.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with red gag. Its a special gag to enforce the wearer to speak like a reindeer drone should.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with red gag. Its a special gag to enforce the wearer to speak like a reindeer drone should.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Hex.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a digital enhanced gag set to hex talk.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with a digital enhanced gag set to hex talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a digital enhanced gag set to hex talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Base64.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a digital enhanced gag set to 64 talk.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with a digital enhanced gag set to 64 talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a digital enhanced gag set to 64 talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_Binary.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a digital enhanced gag set to binary talk.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with a digital enhanced gag set to binary talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a digital enhanced gag set to binary talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.ComputerNerd_BinaryBlocks.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                   gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a digital enhanced gag set to binary talk.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you with a digital enhanced gag set to binary talk.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a digital enhanced gag set to binary talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Pokemon_Pikachu.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                    gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You throw a pokeball at "+target.getAsMention()+", releasing a starnge gas right on their face.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" throws a pokeball at your face, releasing a strange gas that you breath in.", llColorBlue1);
                    //userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a digital enhanced gag set to binary talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Pokemon_Eevee.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                    gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You throw a pokeball at "+target.getAsMention()+", releasing a starnge gas right on their face.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" throws a pokeball at your face, releasing a strange gas that you breath in.", llColorBlue1);
                    //userNotificationGag(actionSelected,gUser.getAsMention()+" gags "+target.getAsMention()+" with a digital enhanced gag set to binary talk.");
                    voiceChannelRestriction(1);
                }
                else if(command.equalsIgnoreCase(GAGLEVELS.Chocke.getName())){
                    gNewUserProfile.cGAG.setLevel( command.toLowerCase());
                    gNewUserProfile.cGAG.setOn( true);
                    sendOrUpdatePrivateEmbed(sRTitle,"You block "+target.getAsMention()+" airway, they will start chocking.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" blocks your airway, they will start chocking.", llColorBlue1);
                    userNotificationGag(actionSelected,gUser.getAsMention()+" blocks "+target.getAsMention()+" airway, they will start chocking.");
                    voiceChannelRestriction(1);
                }
            }

            saveProfile();
        }
        private void rGagType(Member mtarget,String command) {
            String fName = "[rGagType]";
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag as its part of the suit.", llColorRed);
                return;
            }else
            /*if(!gIsOverride&&!llMemberIsBooster(gMember)){
                logger.info(fName + ".can't use do to not a booster");
                sendOrUpdatePrivateEmbed(sRTitle,"Only boosters have access to this extra options!", llColorRed);return;
            }else*/
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cGAG.getTypeAsString().equalsIgnoreCase(command.toLowerCase())){
                logger.info(fName + ".same level>ignore"); sendOrUpdatePrivateEmbed(sRTitle,"It's already set to this level!", llColorPurple1);return;
            }else
            if(!gTextChannel.isNSFW()&&(command.equalsIgnoreCase(GAGTYPES.Dildo.getName())||command.equalsIgnoreCase(GAGTYPES.DReverseildo.getName())||command.equalsIgnoreCase(GAGTYPES.Underwear.getName())||command.equalsIgnoreCase(GAGTYPES.Ring.getName()))){
                blockedDM();
                return;
            }
            if(command.equalsIgnoreCase(GAGTYPES.Ball.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a ball gag.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with a ball gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with a ball gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Paci.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a paci gag.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with a paci gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with a paci gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Tape.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with tape, sticky tape.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with tape, sticky tape.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with tape, sticky tape.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.LeatherMuzzle.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with leather muzzle.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with leather muzzle.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with leather muzzle.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.WireFrameMuzzle.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with wire-framed muzzle.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with wire-framed muzzle.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with wired-framed muzzle.");
            }else
            if(!isAdult){
                logger.info(fName + ".not adult ");
                sendOrUpdatePrivateEmbed(sRTitle,"Not an NSFW channel or server", llColorRed);return;
            }
            if(command.equalsIgnoreCase(GAGTYPES.Dildo.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a dildo gag.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with a dildo gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with a dildo gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.DReverseildo.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a reverse-dildo gag.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with a reverse-dildo gag.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with a reverse-dildo gag.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Sock.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with some socks.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with some socks.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with some socks.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Underwear.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with an underwear.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with an underwear.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with an underwear.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.Ring.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a ring gag, ensuring they can't bite and ready to be used.", llColorBlue1);
				llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with a ring gag, ensuring they can't bite and ready to be used.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with a rung gag, ensuring they can't bite and ready to be used.");
            }else
            if(command.equalsIgnoreCase(GAGTYPES.ReindeerMuzzle.getName())){
                gNewUserProfile.cGAG.setType( command.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You gag "+target.getAsMention()+" with a reindeer muzzle.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" gags you"+" with a reindeer muzzle.", llColorBlue1);
                userNotificationGag( gUser.getAsMention()+" gags "+target.getAsMention()+" with a reindeer muzzle.");

            }
            saveProfile();
        }
        private void rGagTimeLock(Member mtarget,String command,String value) {
            String fName = "[rGagTimeLock]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag as its part of the suit.", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                }
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()&&!command.equalsIgnoreCase("red")){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(command.equalsIgnoreCase("set")){
                if(value==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value can't be null!", llColorRed_Blood);
                    return;
                }
                logger.info(fName + ".value="+value);
                long input=0L;
                try {
                    input=Long.getLong(value);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    sendOrUpdatePrivateEmbed(sRTitle,"Value is invalid!", llColorRed_Blood);
                }
                logger.info(fName + ".input_before="+input);
                input=input*iRestraints.milliseconds_minute;
                logger.info(fName + ".input_after="+input);
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(input<iRestraints.milliseconds_minute*15){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be minimum 15 minutes!", llColorRed_Blood);
                    return;
                }
                if(!isPatreon&&input>iRestraints.milliseconds_hour*6){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be bellow or equal to 6 hours!", llColorRed_Blood);
                    return;
                }
                if(input>iRestraints.milliseconds_day*14){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be bellow or equal to 14 days!", llColorRed_Blood);
                    return;
                }
                if(gNewUserProfile.cGAG.setTimeLockDuration(input)==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to set time!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(mtarget);
                }else{
                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getAsMention()+"'s gag timelock duration to "+lsUsefullFunctions.displayDuration(input)+".", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" set your gag timelock duration to "+lsUsefullFunctions.displayDuration(input)+".", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" set "+target.getAsMention()+"'s gag timelock duration to "+lsUsefullFunctions.displayDuration(input)+".");
                }
            }
            if(command.equalsIgnoreCase("start")){
                if(gNewUserProfile.cGAG.startTimeLock()==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to start!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(mtarget);
                    llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" started your "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" started "+target.getAsMention()+"'s "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }else{
                    sendOrUpdatePrivateEmbed(sRTitle,"You started "+target.getAsMention()+"'s "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" started your "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" started "+target.getAsMention()+"'s "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }
            }
            if(command.equalsIgnoreCase("red")){
                if(gNewUserProfile.cGAG.stopTimeLock(false)==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to stop!", llColorRed_Blood);
                    return;
                }
                sendOrUpdatePrivateEmbed(sRTitle,"Emergency timelock stop used on "+target.getAsMention()+".", llColorRed_Blood);
                llSendQuickEmbedMessage(target,sMainRTitle,"Emergency timelock stop was used on you by "+gMember.getAsMention()+".", llColorRed_Blood);
            }
            saveProfile();
        }
        private void rGagTimeLock(Member mtarget,String command,long value) {
            String fName = "[rGagTimeLock]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType).equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag as its part of the suit.", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
            }
            if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()&&!command.equalsIgnoreCase("red")){
                lsMessageHelper.lsSendQuickEmbedMessage(gMember.getUser(),sMainRTitle,cantmanipualetduetimelocked,llColorRed_Blood);
                return;
            }
            if(command.equalsIgnoreCase("set")){
                logger.info(fName + ".value="+value);
                if(value<iRestraints.milliseconds_minute*15){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value must be minimum 15 minutes!", llColorRed_Blood);
                    return;
                }
                if(value>iRestraints.milliseconds_day*14){
                    sendOrUpdatePrivateEmbed(sRTitle,"Value can be maximum 14 days!", llColorRed_Blood);
                    return;
                }
                if(gNewUserProfile.cGAG.setTimeLockDuration(value)==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to set time!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(mtarget);
                }else{
                    sendOrUpdatePrivateEmbed(sRTitle,"You set "+target.getAsMention()+"'s gag timelock duration to "+lsUsefullFunctions.displayDuration(value)+".", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" set your gag timelock duration to "+lsUsefullFunctions.displayDuration(value)+".", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" set "+target.getAsMention()+"'s gag timelock duration to "+lsUsefullFunctions.displayDuration(value)+".");
                }
            }
            if(command.equalsIgnoreCase("start")){
                if(gNewUserProfile.cGAG.startTimeLock()==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to start!", llColorRed_Blood);
                    return;
                }
                if(isMenu){
                    menuTimelock(mtarget);
                    llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" started your "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" started "+target.getAsMention()+"'s "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }else{
                    sendOrUpdatePrivateEmbed(sRTitle,"You started "+target.getAsMention()+"'s "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" started your "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.", llColorBlue1);
                    userNotificationGag( gUser.getAsMention()+" started "+target.getAsMention()+"'s "+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockDuration())+" gag timelock.");
                }
            }
            if(command.equalsIgnoreCase("red")){
                if(gNewUserProfile.cGAG.stopTimeLock(false)==null){
                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to stop!", llColorRed_Blood);
                    return;
                }
                sendOrUpdatePrivateEmbed(sRTitle,"Emergency timelock stop used on "+target.getAsMention()+".", llColorRed_Blood);
                llSendQuickEmbedMessage(target,sMainRTitle,"Emergency timelock stop was used on you by "+gMember.getAsMention()+".", llColorRed_Blood);
            }
            saveProfile();
        }
        private void rGagException(Member mtarget,String command) {
            String fName = "[rGagException]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                logger.info(fName + ".can't use as not owner or secowner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+mtarget.getAsMention()+" gag exception list do to your not their owner and sec-owners.", llColorRed);
                return;
            }else
            if(!gIsOverride&&command.equalsIgnoreCase(nOn)&&gNewUserProfile.cGAG.isExceptionEnabled()){
                logger.info(fName + ".already turned on>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already turned on for "+mtarget.getAsMention()+"!", llColorRed);return;
            }else
            if(!gIsOverride&&command.equalsIgnoreCase(vOff)&&!gNewUserProfile.cGAG.isExceptionEnabled()){
                logger.info(fName + ".already turned off>ignore");
                sendOrUpdatePrivateEmbed(sRTitle,"Its already turned off for "+mtarget.getAsMention()+"!", llColorRed);return;
            }else
            if(command.equalsIgnoreCase("menu")){
                menuExceptions();return;
            }else
            if(command.equalsIgnoreCase(nOn)){
                gNewUserProfile.cGAG.setExceptionEnable( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You enable exceptions for "+mtarget.getAsMention()+": "+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" enables exceptions: "+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" enables exceptions for "+mtarget.getAsMention()+": "+getExceptionChannelMention(), llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vOff)){
                gNewUserProfile.cGAG.setExceptionEnable( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You disable exceptions for "+mtarget.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" disables exceptions.", llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,gUser.getAsMention()+" disables exceptions for "+mtarget.getAsMention()+".", llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vClear)){
                gNewUserProfile.cGAG.setExceptionList( new JSONObject());
                sendOrUpdatePrivateEmbed(sRTitle,"You clear exceptions for "+mtarget.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" clears exceptions.", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" clears exceptions for "+mtarget.getAsMention()+".", llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vList)){
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,mtarget.getAsMention()+" has the following exceptions:"+getExceptionChannelMention(), llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vAdd)){
                JSONObject list=gNewUserProfile.cGAG.getExceptionList();
                logger.info(fName + ".listExceptionList="+list);
                List<TextChannel>textChannels=gCommandEvent.getMessage().getMentionedChannels();
                if(textChannels.isEmpty()){
                    logger.warn(fName + ".No channel mentioned!");
                    sendOrUpdatePrivateEmbed(sRTitle,"No channels mentioned!", llColorRed);
                    return;
                }
                for(TextChannel textChannel:textChannels){
                    logger.info(fName + ".textChannel:"+textChannel.getName()+"|"+textChannel.getId());
                    if(!list.has(textChannel.getId())){
                        logger.info(fName + ".not present");
                        list.put(textChannel.getId(),"");
                    }
                }
                gNewUserProfile.cGAG.setExceptionList( list);gNewUserProfile.cGAG.setExceptionEnable( true);
                sendOrUpdatePrivateEmbed(sRTitle,"Current exceptions for "+mtarget.getAsMention()+": "+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" changes your exceptions to: "+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" changes "+mtarget.getAsMention()+" exceptions to: "+getExceptionChannelMention(), llColorBlue1);
            }else
            if(command.equalsIgnoreCase(vRemove)){
                JSONObject list=gNewUserProfile.cGAG.getExceptionList();
                logger.info(fName + ".listExceptionList="+list);
                List<TextChannel>textChannels=gCommandEvent.getMessage().getMentionedChannels();
                if(textChannels.isEmpty()){
                    logger.warn(fName + ".No channel mentioned!");
                    sendOrUpdatePrivateEmbed(sRTitle,"No channels mentioned!", llColorRed);
                    return;
                }
                for(TextChannel textChannel:textChannels){
                    logger.info(fName + ".textChannel:"+textChannel.getName()+"|"+textChannel.getId());
                    if(list.has(textChannel.getId())){
                        logger.info(fName + ".is present");
                        list.remove(textChannel.getId());
                    }
                }
                gNewUserProfile.cGAG.setExceptionList( list);
                sendOrUpdatePrivateEmbed(sRTitle,"Current exceptions for "+mtarget.getAsMention()+": "+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" changes your exceptions to: "+getExceptionChannelMention(), llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" changes "+mtarget.getAsMention()+" exceptions to: "+getExceptionChannelMention(), llColorBlue1);
            }
            saveProfile();
        }
        private void rGagShow(Member mtarget,String command) {
            String fName = "[rGagShow]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                logger.info(fName + ".can't use as not owner or secowner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+mtarget.getAsMention()+" gag eye option do to your not their owner and sec-owners.", llColorRed);
                return;
            }

            if(command.equalsIgnoreCase(commandWearerTrue)){
                gNewUserProfile.cGAG.setDisable2Show4Wearer( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You disabled the self eye function for "+mtarget.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" disabled the self eye function for yourself", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" disabled the  self  eye function for "+mtarget.getAsMention()+".", llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandWearerFalse)){
                gNewUserProfile.cGAG.setDisable2Show4Wearer( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You enabled the self eye function for "+mtarget.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" enabled the self eye function for yourself", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" enabled the self eye function for "+mtarget.getAsMention()+".", llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandOthersTrue)){
                gNewUserProfile.cGAG.setDisable2Show4Others( true);
                sendOrUpdatePrivateEmbed(sRTitle,"You disabled the others eye function for "+mtarget.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" disabled the others eye function for yourself", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" disabled the others eye function for "+mtarget.getAsMention()+".", llColorBlue1);
            }
            if(command.equalsIgnoreCase(commandOthersFalse)){
                gNewUserProfile.cGAG.setDisable2Show4Others( false);
                sendOrUpdatePrivateEmbed(sRTitle,"You enabled the others eye function for "+mtarget.getAsMention()+".", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gMember.getAsMention()+" enabled the others eye function for yourself", llColorBlue1);
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,sMainRTitle,gUser.getAsMention()+" enabled the others eye function for "+mtarget.getAsMention()+".", llColorBlue1);
            }
            saveProfile();
        }
        private void rDisableSafety(Member mtarget,String command){
            String fName="[rDisableSafety]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)){
                logger.info(fName + ".denied, not owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are not their owner!", llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(optionDisable)){
                logger.info(fName + ".do disable");
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(sRTitle);embed.setColor(llColorOrange);
                embed.setDescription("Are you sure you want to disable "+mtarget.getAsMention()+"'s gag safety?");
                embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" to disable.",false);
                embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" to abort.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                    gNewUserProfile.cGAG.setFlagDisableGagOOCException(true);gNewUserProfile.cGAG.setFlagDisableGagSafeWords( true);
                                    if( saveProfile()){
                                        llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" disabled your safety for your gag.", llColorBlue1);
                                        llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" disabled "+mtarget.getAsMention()+"'s safety for their gag.");
                                    }
                                    if(isMenu){
                                        menuMain(mtarget);
                                    }
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
            }
            if(command.equalsIgnoreCase(optionEnable)){
                    logger.info(fName + ".do enable");
                    EmbedBuilder embed=new EmbedBuilder();
                    embed.setTitle(sRTitle);embed.setColor(llColorOrange);
                    embed.setDescription("Are you sure you want to re-enable "+mtarget.getAsMention()+"'s gag safety?");
                    embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" to enable.",false);
                    embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" to abort.",false);
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
                                       gNewUserProfile.cGAG.setFlagDisableGagOOCException(false);gNewUserProfile.cGAG.setFlagDisableGagSafeWords( false);
                                        if( saveProfile()){
                                            llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" re-enabled your safety for your gag.", llColorBlue1);
                                            llSendMessageWithDelete(gGlobal,gTextChannel,gUser.getName()+" re-enabled "+mtarget.getAsMention()+"'s safety for their gag.");
                                        }
                                        if(isMenu){
                                            menuMain(mtarget);
                                        }
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
                }
        }
        private void rVoiceRestriction(Member mtarget,String category,String command){
            String fName="[rVoiceRestriction]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".category="+category);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                logger.warn(fName + ".not owner or secc owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are not their owner or sec-owner!", llColorRed); return;
            }
            if(command.equalsIgnoreCase(optionEnable)||command.equalsIgnoreCase(optionVoiceMute)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,true);
                sendOrUpdatePrivateEmbed(sRTitle,"You enabled gag voice mute for "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" enabled gag voice mute for you.", llColorBlue3);
            }else
            if(command.equalsIgnoreCase(optionDisable)||command.equalsIgnoreCase(optionVoiceUnmute)){
                gNewUserProfile.gUserProfile.putFieldEntry(nVoiceChannelRestriction,nVoiceChannelRestriction_MuteEnabled,false);
                sendOrUpdatePrivateEmbed(sRTitle,"You disabled gag voice mute for "+mtarget.getAsMention()+".", llColorBlue3);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" disabled gag voice mute for you.", llColorBlue3);
            }
            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gNewUserProfile.gUserProfile.getMember().getVoiceState());
            if(isMenu){
                menuMain(mtarget);
            }
        }
        private void rGagExtra(Member mtarget,String command) {
            String fName = "[rGagExtra]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            switch (command){
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeather:case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle:
                    gNewUserProfile.cGAG.extra.toggleLaughterOn();
                    if( gNewUserProfile.cGAG.extra.isLaughterOn()){
                        sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("You enable tickle option for !TARGET gag."), llColorBlue1);
                        llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,stringReplacer("!USER enable tickle option for your gag."), llColorBlue1);
                    }else{
                        sendOrUpdatePrivateEmbed(sRTitle,"You disable tickle option for !TARGET gag.", llColorBlue1);
                        llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,"!USER disable tickle option for your gag.", llColorBlue1);
                    }
                    saveProfile();
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_off:
                    gNewUserProfile.cGAG.extra.setLaughterOn(false);
                    sendOrUpdatePrivateEmbed(sRTitle,"You disable tickle option for !TARGET gag.", llColorBlue1);
                    llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,"!USER disable tickle option for your gag.", llColorBlue1);
                    saveProfile();
                    break;
                case iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_on:
                    gNewUserProfile.cGAG.extra.setLaughterOn(true);
                    sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("You enable tickle option for !TARGET gag."), llColorBlue1);
                    llSendQuickEmbedMessage(mtarget.getUser(),sMainRTitle,stringReplacer("!USER enable tickle option for your gag."), llColorBlue1);
                    saveProfile();
                    break;
            }

        }

        private void userNotificationGag(String desc){
            String fName="[userNotificationGag]";
            //logger.info(fName+"area="+area);
            logger.info(fName+"desc="+desc);
            if(gBDSMCommands.restraints.getNotificationDisabled()){
                logger.warn(fName+"notification disabled");
                return;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                }
            }
        }
        private void userNotificationGag(int action,String desc){
            String fName="[userNotificationGag]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nGag;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                    if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                        if(isAdult&&action==actionSecured){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlSecure).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlSecure);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(isAdult&&action==actionUnSecured){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlUnSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlUnSecure)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlUnSecure).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlUnSecure);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(isAdult&&action==actionStruggle){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlStruggle)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlStruggle)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlStruggle).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlStruggle);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        } else{
                            llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            User user=null;String level="",type="";
            logger.info(fName + ".level=" + level+", type="+type);
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColorRed_Barn);
            slashReplyPleaseWait();
            gCurrentInteractionHook=gSlashInteractionHook;
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsUser();
                        }
                        break;
                    case llCommonKeys.SlashCommandReceive.level:
                        if(option.getType()==OptionType.STRING){
                            level=option.getAsString();
                        }
                        break;
                    case llCommonKeys.SlashCommandReceive.type:
                        if(option.getType()==OptionType.STRING){
                            type=option.getAsString();
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
            String messageAuth="",messagePublic="",messagePublic2="";Color color=lsMessageHelper.llColorBlue3;
            checkGagTimelock();
            if(gTarget==null){
                if(level.isBlank()&&type.isBlank()){
                    messageAuth=iRdStr.strOpeningDMMenu;
                    menuMain(null);
                }else
                if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                    logger.info(fName + ".permalock");
                    messageAuth=cantmanipulaleduepermalocked;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    messageAuth="Can't manipulate your gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet();
                }else
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    messageAuth=cantmanipulatedueaccessset2owner;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    messageAuth=cantmanipulatedueaccessset2public;
                }else
                if(!gIsOverride&&gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                    logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                    messageAuth=cantduewearingjacket;
                }else
                if(!gIsOverride&&gNewUserProfile.cARMCUFFS.areArmsRestrained()){
                    logger.info(fName + ".can't restrain via cuffs while the jacket is on");
                    messageAuth=cantduearmscuffed;
                }else
                if(!gIsOverride&&gNewUserProfile.cMITTS.isOn()){
                    logger.info(fName + ".can't restrain via cuffs while the mittens are on");
                    messageAuth=stringReplacer(iMitts.strCantGrabAnything);
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Exposed.getName())&&gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".locked and access exposed");
                    messageAuth="You dont have the key to do that!";
                }else
                if(!gIsOverride&&gNewUserProfile.cGAG.isTimeLocked()){
                    messageAuth=cantmanipualetduetimelocked;
                }else{
                    if(level!=null&&!level.isBlank()){
                        if(!gIsOverride&&gNewUserProfile.cGAG.isOn()&&gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(level.toLowerCase())){
                            logger.info(fName + ".same level>ignore");
                            messageAuth="Gag level is already set to this!";color=llColorRed_Blood;
                        }else
                        if(level.equalsIgnoreCase(nUngag)){
                            if(gNewUserProfile.cGAG.isOn()){
                                if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                                    logger.info(fName + ".can't untie while locked");
                                    messageAuth="You can't untie your gag while locked with padlock.";color=llColorRed_Blood;
                                }else{
                                    gNewUserProfile.cGAG.setOn( false);
                                    messagePublic=gUser.getAsMention()+" managed to remove their gag. Someone must have forgot to secure it.";
                                    voiceChannelRestriction(-1);
                                }
                            }else{
                                logger.info(fName + ".wrist not restrained");
                                messageAuth="You are not gagged, silly.";
                            }
                        }else
                        {
                            gNewUserProfile.cGAG.setLevel( level.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                            if(level.equalsIgnoreCase(GAGLEVELS.Faux.getName())){
                                voiceChannelRestriction(-1);
                            }else{
                                voiceChannelRestriction(1);
                            }
                            switch (gNewUserProfile.cGAG.getLevel()){
                                case Faux:
                                    messagePublic=gUser.getAsMention()+" gags themselves just for show.";
                                    break;
                                case Loose:
                                    gNewUserProfile.cGAG.setLevel( level.toLowerCase());
                                    gNewUserProfile.cGAG.setOn( true);
                                    messagePublic=gUser.getAsMention()+" gags themselves enough to affect their speech.";
                                    break;
                                case Severe:
                                    messagePublic=gUser.getAsMention()+" gags themselves to the point is becoming difficult to speak";
                                    break;
                                case Extreme:
                                    messagePublic=gUser.getAsMention()+" gags themselves so tight that nobody will be able to understand them.";
                                    break;
                                case Mute:
                                    messagePublic=gUser.getAsMention()+" gags themselves till they can't say a word.";
                                    break;
                                case Puppy:
                                    messagePublic=gUser.getAsMention()+" gags themselves till they  speak like a good puppy.";
                                    break;
                                case Kitty:
                                    messagePublic=gUser.getAsMention()+" gags themselves till the only word they can say is MEOW.";
                                    break;
                                case Paci:
                                    messagePublic=gUser.getAsMention()+" gags themselves with a paci like a big baby.";
                                    break;
                                case Pony:
                                    messagePublic=gUser.getAsMention()+" gags themselves like a good pony.";
                                    break;
                                case Squeaky:
                                    messagePublic=gUser.getAsMention()+" gags themselves like a good toy.";
                                    break;
                                case Dinosaur:
                                    messagePublic=gUser.getAsMention()+" gags themselves like an angry dinosaur.";
                                    break;
                                case Bird:
                                    messagePublic=gUser.getAsMention()+" gags themselves like birdy.";
                                    break;
                                case Sheep:
                                    messagePublic=gUser.getAsMention()+" gags themselves like a docile sheep.";
                                    break;
                                case Piggy:
                                    messagePublic=gUser.getAsMention()+" gags themselves like a good piggy.";
                                    break;
                                case Cow:
                                    messagePublic=gUser.getAsMention()+" gags themselves like a good cow.";
                                    break;
                                case NucleusMask:
                                    messagePublic=gUser.getAsMention()+" pulls over their head a tight sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.";
                                    break;
                                case DroneMask:
                                    messagePublic=gUser.getAsMention()+" pulls over their head a sealed high-tech mask hiding your identity, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.";
                                    break;
                                case Turkey:
                                    messagePublic=gUser.getAsMention()+" gags themselves like the turkey they are for thanksgiving dinner.";
                                    break;
                                case Toy:
                                    messagePublic=gUser.getAsMention()+" gags themselves like the toy they are.";
                                    break;
                                case Corrupt:
                                    messagePublic=gUser.getAsMention()+" gags themselves  with a smart gag that corrupts their speech.";
                                    break;
                            }

                        }
                    }
                    if(type!=null&&!type.isBlank()){
                        gNewUserProfile.cGAG.setType( type.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                        switch (gNewUserProfile.cGAG.getType()){
                            case Ball:
                                messagePublic2=gUser.getAsMention()+" gags themselves with a ball gag.";
                                break;
                            case Dildo:
                                messagePublic2=gUser.getAsMention()+" gags themselves with a dildo gag.";
                                break;
                            case DReverseildo:
                                messagePublic2=gUser.getAsMention()+" gags themselves with a reverse-dildo gag.";
                                break;
                            case Paci:
                                messagePublic2=gUser.getAsMention()+" gags themselves with a paci gag.";
                                break;
                            case Sock:
                                messagePublic2=gUser.getAsMention()+" gags themselves with some socks.";
                                break;
                            case Underwear:
                                messagePublic2=gUser.getAsMention()+" gags themselves with an underwear.";
                                break;
                            case Tape:
                                messagePublic2=gUser.getAsMention()+" gags themselves with tape, sticky tape.";
                                break;
                            case LeatherMuzzle:
                                messagePublic2=gUser.getAsMention()+" gags themselves with leather muzzle.";
                                break;
                            case WireFrameMuzzle:
                                messagePublic2=gUser.getAsMention()+" gags themselves with wired-framed muzzle.";
                                break;
                            case Ring:
                                messagePublic2=gUser.getAsMention()+" gags themselves with with a ring-gag, ensuring you can't bite and ready to be used by other.";
                                break;
                        }
                    }
                }
            }else{
                if(level.isBlank()&&type.isBlank()){
                    messageAuth=iRdStr.strOpeningDMMenu;
                    menuMain(gTarget);
                }else
                if(gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".requesting update restraint");
                    messageAuth=iRdStr.strCatUseSlash4AskUseDmMeny;
                }else
                if(iRestraints.isArmsRestrained(gGlobal,gMember)){
                    messageAuth=iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained);
                }else
                if(iRestraints.isArmsRestrained(gGlobal,gMember)){
                    messageAuth=iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained);
                }else
                if(gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cGAG.isOn()){
                    logger.info(fName + ".permalock");
                    messageAuth="Can't manipulate "+gTarget.getAsMention()+"'s restraints as they permanently locked!";
                }else
                if(!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                    logger.info(fName + ".can't use do to locked by not you");
                    messageAuth="Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet();
                }else
                if(!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    messageAuth="Can't manipulate their gag due to their access setting.";
                }else
                if(gNewUserProfile.cGAG.isTimeLocked()){
                    messageAuth="You can't manage gag while "+gTarget.getAsMention()+"'s gag is timelocked.";
                }else{
                    if(level!=null&&!level.isBlank()){
                        if(level.equalsIgnoreCase(nUngag)){
                            gNewUserProfile.cGAG.setLevel( nNone);gNewUserProfile.cGAG.setOn( false);
                            messagePublic=gUser.getAsMention()+" removes "+gTarget.getAsMention()+"'s gag.";
                            voiceChannelRestriction(-1);
                        }else{
                            gNewUserProfile.cGAG.setLevel( level.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                            if(level.equalsIgnoreCase(GAGLEVELS.Faux.getName())){
                                voiceChannelRestriction(-1);
                            }else{
                                voiceChannelRestriction(1);
                            }
                            switch ( gNewUserProfile.cGAG.getLevel()){
                                case Faux:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw for show.";
                                    break;
                                case Loose:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw enough for them to speak with some issues.";
                                    break;
                                case Severe:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw, its starting to get hard for them to speak.";
                                    break;
                                case Extreme:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw, they can barely say any words.";
                                    break;
                                case Mute:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw, to the point they can't speak anymore.";
                                    break;
                                case Puppy:
                                    messagePublic=gUser.getAsMention()+" muzzles "+gTarget.getAsMention()+" like the good puppies should be.";
                                    break;
                                case Kitty:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw with a ball of yarn.";
                                    break;
                                case Paci:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw with a paci like those used for big babies.";
                                    break;
                                case Pony:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw with a bit gag like those used for ponies.";
                                    break;
                                case Piggy:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw with a muzzle like used for pigs.";
                                    break;
                                case Dinosaur:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw like the big angry dino they are.";
                                    break;
                                case Bird:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw like the bird they are.";
                                    break;
                                case Cow:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw like the cow they are.";
                                    break;
                                case Sheep:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw like the sheep they are.";
                                    break;
                                case Squeaky:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+"'s jaw like the toy they are.";
                                    break;
                                case NucleusMask:
                                    messagePublic=gUser.getAsMention()+" pulls over "+gTarget.getAsMention()+" head a tight sealed high-tech mask, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.";
                                    break;
                                case DroneMask:
                                    messagePublic=gUser.getAsMention()+" pulls over "+gTarget.getAsMention()+" head a tight sealed high-tech mask, hiding their identity, with an intake hole for feeding and voice synthesizer to ensure they only say authorized speech.";
                                    break;
                                case Turkey:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" like the turkey they are for thanksgiving dinner.";
                                    break;
                                case Toy:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" like the toy they are.";
                                    break;
                                case Corrupt:
                                    messagePublic=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with a smart gag that corrupts their speech.";
                                    break;
                            }

                        }
                    }
                    if(type!=null&&!type.isBlank()){
                        gNewUserProfile.cGAG.setType( type.toLowerCase());gNewUserProfile.cGAG.setOn( true);
                        switch (gNewUserProfile.cGAG.getType()){
                            case Ball:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with a ball gag.";
                                break;
                            case Paci:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with a paci gag.";
                                break;
                            case Tape:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with tape, sticky tape.";
                                break;
                            case LeatherMuzzle:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with leather muzzle.";
                                break;
                            case WireFrameMuzzle:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with wired-framed muzzle.";
                                break;
                            case Dildo:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with a dildo gag.";
                                break;
                            case DReverseildo:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with a reverse-dildo gag.";
                                break;
                            case Sock:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with some socks.";
                                break;
                            case Underwear:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with an underwear.";
                                break;
                            case Ring:
                                messagePublic2=gUser.getAsMention()+" gags "+gTarget.getAsMention()+" with a rung gag, ensuring they can't bite and ready to be used.";
                                break;
                        }
                    }
                }
            }
            if(!messageAuth.isBlank()){
                sendOrUpdatePrivateEmbed(embedBuilder);
            }else{
                embedBuilder.setColor(color);
                embedBuilder.setDescription("Completed");
                sendOrUpdatePrivateEmbed(embedBuilder);
                embedBuilder.setDescription(messagePublic+"\n"+messagePublic2);
                lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,embedBuilder);
                saveProfile();
            }
        }



        private void checkGagTimelock(){
            String fName="[checkGagTimelock]";
            logger.info(fName );
            try {
                if(gNewUserProfile.cGAG.checkGagTimelockDoRelease()){
                    logger.info(fName+"released>do save");
                    gNewUserProfile.save2Cache();
                }else{
                    logger.info(fName+"n/a");
                }
            }catch (Exception e) {
                logger.error(fName+" Exception:"+e);
            }
        }
        boolean isMenu=false;
        String gCommandFilePath=gFileMainPath+"gag/";
        String gCommandFileMainPath =gCommandFilePath+"menuMain.json";
        String gFileLevel0Path =gCommandFilePath+"menuLevel0.json",gFileLevel1Path =gCommandFilePath+"menuLevel1.json",gFileLevel2Path =gCommandFilePath+"menuLevel2.json",gFileLevel3Path =gCommandFilePath+"menuLevel3.json",gFileLevelGPath =gCommandFilePath+"menuLevelG.json";
        String gFileTypePath =gCommandFilePath+"menuType.json";
        private void menuMain(Member mtarget){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                if(mtarget!=null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                        logger.info(fName + ".isSameConfinement>return");
                        sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                        }
                        return;
                    }
                    menuMainSomebody(mtarget);
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuMainWearer();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuMainWearer(){
            String fName="[menuMainWearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                isMenu=true;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nGag)&&gNewUserProfile.cGAG.isOn()){
                    String gagLevel, gagType;
                    if (GAGLEVELS.None.getName().equals(gNewUserProfile.cGAG.getLevelAsString())) {
                        gagLevel = "no";
                    } else {
                        gagLevel = gNewUserProfile.cGAG.getLevelAsString();
                    }
                    embed.addField("Gag level",gagLevel,true);
                    if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Ball.getName())){gagType="ball gag";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Dildo.getName())){gagType="dildo";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.DReverseildo.getName())){gagType="reverse-dildo";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.LeatherMuzzle.getName())){gagType="leather muzzle";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Paci.getName())){gagType="paci";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Sock.getName())){gagType="socks";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Tape.getName())){gagType="sticky tape";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Underwear.getName())){gagType="musky underwear";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.WireFrameMuzzle.getName())){gagType="wire-framed muzzle";}
                    else{gagType="?";}
                    embed.addField("Gag type",gagType,true);
                }  else{
                    embed.addField("Gag level","N/A",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" gag level sub-commands";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" gag type sub-commands";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" custom level gag text sub-command";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" training gag sub-command";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" exception channel sub-command";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasAlarmClock)+" timelock";
                embed.addField(" ", "Please select an option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                try {
                    desc="Muting user in servers voice channel during gagging.";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_MuteEnabled)){
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMicrophone2)+" disable";
                    }else{
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute)+" enable";
                    }
                    embed.addField("Voice Channel Restriction", desc, false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                desc="Disable option showing original text of gagged text.\nExceptiopn are owners.";
                if(gNewUserProfile.cGAG.isDisable2Show4Wearer()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEyeRoll)+" allow "+gNewUserProfile.getMember().getAsMention()+"";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEyeRoll)+" deny "+gNewUserProfile.getMember().getAsMention()+"";
                }
                if(gNewUserProfile.cGAG.isDisable2Show4Others()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFaceWithRaisedEyebrow)+" allow others";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFaceWithRaisedEyebrow)+" deny others";
                }
                embed.addField("Disable "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye), desc, false);
                desc="Can be used to prevent the safeword `red`";
                if(gNewUserProfile.cGAG.isFlagDisableGagSafeWords()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGrimacing)+" enable safeword";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZipperMouth)+" disable safeword";
                }
                embed.addField("Disable gag safety", desc, false);
                checkGagTimelock();
                if(gNewUserProfile.cGAG.isTimeLockStarted()){
                    embed.addField("Timelocked!", "Due to timelock, ist imposible to change the gag level&type.\nDuration:"+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockRemaning()), false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isWearerDenied0GagwithException()){
                    desc="";
                    if(gNewUserProfile.cGAG.isOn()&&gNewUserProfile.cGAG.isTimeLocked())desc=iRdStr.strRestraintTimeLocked;
                    else if(gNewUserProfile.isLockedwithException(gNewUserProfile.cGAG.isOn()))desc=iRdStr.strRestraintLocked;
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
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(3,1);
                    if(gCurrentInteractionHook!=null){
                        buttonClose.setIgnored();
                    }
                    lcMessageBuildComponent component0=messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1=messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2=messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(2);
                    if(gNewUserProfile.isWearerDenied0GagwithException())
                    {
                        component0.getButtonById("regional_indicator_symbol_a",true).setDisable();
                        component0.getButtonById("regional_indicator_symbol_b",true).setDisable();
                    }
                    try {
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_MuteEnabled)){
                            component1.getButtonById("!VoiceChannelRestriction",true).setCustomId(lsUnicodeEmotes.aliasMicrophone2).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMicrophone2));
                        }else{
                            component1.getButtonById("!VoiceChannelRestriction",true).setCustomId(lsUnicodeEmotes.aliasMute).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute));
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    if(gNewUserProfile.cGAG.isFlagDisableGagSafeWords()){
                        component1.getButtonById("!DisableGagSafeWords",true).setCustomId(lsUnicodeEmotes.aliasGrimacing).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGrimacing));
                    }else{
                        component1.getButtonById("!DisableGagSafeWords",true).setCustomId(lsUnicodeEmotes.aliasZipperMouth).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZipperMouth));
                    }

                    lcMessageBuildComponent.Button button2a=component2.getButtonAt4(0);
                    lcMessageBuildComponent.Button button2b=component2.getButtonAt4(1);
                    if(gMember.getIdLong()!=iRDPatreon.patreonUser_365946661442158603_tickleaddon.userID&&!lsMemberIsBotOwner(gMember)){
                        button2b.setIgnored();
                    }else{
                        if(!guildGagProfile.isProfile()||!guildGagProfile.hasFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)||!guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                            button2b.setStyle(ButtonStyle.SUCCESS).setLabel("Tickle show");
                        }else{
                            button2b.setStyle(ButtonStyle.DANGER).setLabel("Tickle hide");
                        }
                    }
                    if(!guildGagProfile.isProfile()||!guildGagProfile.hasFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)||!guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                        //logger.info(fName+"show_feather is false");
                        button2a.setIgnored();
                    }else{
                        logger.warn(fName+"show_feather show");
                        if(gNewUserProfile.cGAG.extra.isLaughterOn()){
                            button2a.setStyle(ButtonStyle.DANGER);
                        }else{
                            button2a.setStyle(ButtonStyle.SUCCESS);
                        }
                    }
                    if(button2b.isIgnored()&&button2a.isIgnored()){
                        component2.setIgnored();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuMainListener(message,null);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuMainSomebody(Member mtarget){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(mtarget.getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                isMenu=true;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nGag)&&gNewUserProfile.cGAG.isOn()){
                    String gagLevel, gagType;
                    if (GAGLEVELS.None.getName().equals(gNewUserProfile.cGAG.getLevelAsString())) {
                        gagLevel = "no";
                    } else {
                        gagLevel = gNewUserProfile.cGAG.getLevelAsString();
                    }
                    embed.addField("Gag level",gagLevel,true);
                    if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Ball.getName())){gagType="ball gag";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Dildo.getName())){gagType="dildo";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.DReverseildo.getName())){gagType="reverse-dildo";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.LeatherMuzzle.getName())){gagType="leather muzzle";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Paci.getName())){gagType="paci";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Sock.getName())){gagType="socks";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Tape.getName())){gagType="sticky tape";}
                    else if(isAdult&&gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Underwear.getName())){gagType="musky underwear";}
                    else if(gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.WireFrameMuzzle.getName())){gagType="wire-framed muzzle";}
                    else{gagType="?";}
                    embed.addField("Gag type",gagType,true);
                }  else{
                    embed.addField("Gag level","N/A",false);
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" gag level sub-commands";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" gag type sub-commands";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" custom level gag text sub-command";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" training gag sub-command";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" exception channel sub-command";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasAlarmClock)+" timelock";
                embed.addField(" ", "Please select an option for " + gNewUserProfile.gUserProfile.getUser().getAsMention() + ":"+desc, false);
                try {
                    desc="Muting user in servers voice channel during gagging.";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_MuteEnabled)){
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMicrophone2)+" disable";
                    }else{
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute)+" enable";
                    }
                    embed.addField("Voice Channel Restriction", desc, false);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                desc="Disable option showing original text of gagged text.\nExceptiopn are owners.";
                if(gNewUserProfile.cGAG.isDisable2Show4Wearer()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEyeRoll)+" allow "+gNewUserProfile.getMember().getAsMention()+"";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEyeRoll)+" deny "+gNewUserProfile.getMember().getAsMention()+"";
                }
                if(gNewUserProfile.cGAG.isDisable2Show4Others()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFaceWithRaisedEyebrow)+" allow others";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFaceWithRaisedEyebrow)+" deny others";
                }
                embed.addField("Disable "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye), desc, false);
                desc="Can be used to prevent the safeword `red`";
                if(gNewUserProfile.cGAG.isFlagDisableGagSafeWords()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGrimacing)+" enable safeword";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZipperMouth)+" disable safeword";
                }
                embed.addField("Disable gag safety", desc, false);
                checkGagTimelock();
                if(gNewUserProfile.cGAG.isTimeLockStarted()){
                    embed.addField("Timelocked!", "Due to timelock, ist imposible to change the gag level&type.\nDuration:"+lsUsefullFunctions.displayDuration(gNewUserProfile.cGAG.getTimeLockRemaning()), false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0=messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1=messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2=messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(2);
                    if(gNewUserProfile.cGAG.isTimeLockStarted()) {
                        component0.getButtonById("regional_indicator_symbol_a",true).setDisable();
                        component0.getButtonById("regional_indicator_symbol_b",true).setDisable();
                    }
                    try {
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction).getBoolean(nVoiceChannelRestriction_MuteEnabled)){
                            component1.getButtonById("!VoiceChannelRestriction",true).setCustomId(lsUnicodeEmotes.aliasMicrophone2).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMicrophone2));
                        }else{
                            component1.getButtonById("!VoiceChannelRestriction",true).setCustomId(lsUnicodeEmotes.aliasMute).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute));
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                    if(gNewUserProfile.cGAG.isFlagDisableGagSafeWords()){
                        component1.getButtonById("!DisableGagSafeWords",true).setCustomId(lsUnicodeEmotes.aliasGrimacing).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGrimacing));
                    }else{
                        component1.getButtonById("!DisableGagSafeWords",true).setCustomId(lsUnicodeEmotes.aliasZipperMouth).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZipperMouth));
                    }

                    lcMessageBuildComponent.Button button2a=component2.getButtonAt4(0);
                    lcMessageBuildComponent.Button button2b=component2.getButtonAt4(1);
                    if(gMember.getIdLong()!=iRDPatreon.patreonUser_365946661442158603_tickleaddon.userID&&!lsMemberIsBotOwner(gMember)){
                        button2b.setIgnored();
                    }else{
                        if(!guildGagProfile.isProfile()||!guildGagProfile.hasFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)||!guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                            button2b.setStyle(ButtonStyle.SUCCESS).setLabel("Tickle show");
                        }else{
                            button2b.setStyle(ButtonStyle.DANGER).setLabel("Tickle hide");
                        }
                    }
                    if(!guildGagProfile.isProfile()||!guildGagProfile.hasFieldEntry(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)||!guildGagProfile.getFieldEntryAsBoolean(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow)){
                        //logger.info(fName+"show_feather is false");
                        button2a.setIgnored();
                    }else{
                        logger.warn(fName+"show_feather show");
                        if(gNewUserProfile.cGAG.extra.isLaughterOn()){
                            button2a.setStyle(ButtonStyle.DANGER);
                        }else{
                            button2a.setStyle(ButtonStyle.SUCCESS);
                        }
                    }
                    if(button2b.isIgnored()&&button2a.isIgnored()){
                        component2.setIgnored();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuMainListener(message,mtarget);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuLevels(Member mtarget,int page){
            String fName="[menuLevels]";
            logger.info(fName+"page="+page);
            try{
                if(mtarget!=null){
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                        logger.info(fName + ".isSameConfinement>return");
                        sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                        }
                        return;
                    }
                    menuLevelsSomebody(page);
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsWearer(page);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuLevelsWearer(int page){
            String fName="[menuLevels]";
            logger.info(fName+"page="+page);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                String gagLevel, gagType;
                if(gNewUserProfile.cGAG.isOn()) {
                    if (GAGLEVELS.None.getName().equals(gNewUserProfile.cGAG.getLevelAsString())) {
                        gagLevel = "none";
                    } else {
                        gagLevel = gNewUserProfile.cGAG.getLevelAsString();
                    }
                }else{
                    gagLevel="no";
                }
                embed.addField("Gag level",gagLevel,true);
                gagType="?";
                switch (gNewUserProfile.cGAG.getType()){
                    case Ball:gagType="ball gag";break;
                    case Dildo:gagType="dildo";break;
                    case DReverseildo:gagType="reverse-dildo";break;
                    case LeatherMuzzle:gagType="leather muzzle";break;
                    case Paci:gagType="paci";break;
                    case Sock:gagType="socks";break;
                    case Tape:gagType="sticky tape";break;
                    case Underwear:gagType="musky underwear";break;
                    case WireFrameMuzzle:gagType="wire-framed muzzle";break;
                }
                embed.addField("Gag type",gagType,true);

                switch (page){
                    case -1:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM)+" main  category";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" animal category";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN)+" nerd category";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" pokemon category";
                        break;
                    case 0:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.Faux.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.Loose.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+GAGLEVELS.Severe.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+GAGLEVELS.Extreme.getName();
                        if(gTextChannel.isNSFW())desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+GAGLEVELS.NucleusMask.getName();
                        if(gTextChannel.isNSFW())desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+GAGLEVELS.DroneMask.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+GAGLEVELS.Mute.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+GAGLEVELS.Corrupt.getName();
                        if(gTextChannel.isNSFW())desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+GAGLEVELS.Toy.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+GAGLEVELS.Squeaky.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+GAGLEVELS.Paci.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+GAGLEVELS.Training.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+GAGLEVELS.Chocke.getName();
                        break;
                    case 1:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.Puppy.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.Kitty.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+GAGLEVELS.Pony.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+GAGLEVELS.Piggy.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+GAGLEVELS.Dinosaur.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+GAGLEVELS.Sheep.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+GAGLEVELS.Bird.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+GAGLEVELS.Cow.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+GAGLEVELS.Turkey.getName();
                        break;
                    case 2:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.ComputerNerd_Hex.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.ComputerNerd_Base64.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+GAGLEVELS.ComputerNerd_Binary.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
                        break;
                    case 3:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.Pokemon_Pikachu.getName();
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.Pokemon_Eevee.getName();
                        break;
                }
                embed.addField(" ", "Please select a gag option :"+desc, false);
                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                if(gNewUserProfile.isWearerDenied0()){
                    desc="";
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained()){
                        desc+=iRdStr.strRestraintJacketArms;
                    }
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()){
                        desc+=iRdStr.strRestraintArmsCuffs;
                    }
                    if(gNewUserProfile.cMITTS.isOn()){
                        desc+=iRdStr.strRestraintMitts;
                    }
                    if(gNewUserProfile.cSUIT.isBDSMSuitOn()){
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit){
                            desc+=iRdStr.strRestraintBDSMSuitBitchsuit;
                        }
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack){
                            desc+=iRdStr.strRestraintBDSMSuitHogsack;
                        }
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack){
                            desc+=iRdStr.strRestraintBDSMSuitSleepsack;
                        }
                    }
                    if(gNewUserProfile.cENCASE.isEncased()){
                        desc+=iRdStr.strRestraintEncased;
                    }
                    desc+=iRdStr.strRestraintOnlyIf;
                    if(gNewUserProfile.cENCASE.isEncased()){
                        desc2=iRdStr.strRestrainedYouEncased;
                    }else{
                        desc2=iRdStr.strRestrainedYou;
                    }
                    if(gNewUserProfile.cGAG.isOn()){
                        desc2+=" How do you think you can ungag yourself?";
                    }else{
                        desc2+=" How do you think you can gag yourself?";
                    }
                    embed.addField(iRdStr.strNewRestrictionTitle,desc2+iRdStr.strFollowingRestraints+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(page>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                }
                if(page<3){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                }
                if(gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cSTRAITJACKET.areArmsRestrained()&&!gNewUserProfile.cARMCUFFS.areArmsRestrainedImpossible2ManageCollar()&&!gNewUserProfile.cENCASE.isEncased()&&!gNewUserProfile.cMITTS.isOn()&&!gNewUserProfile.cSUIT.isBDSMSuitOn())){
                    switch (page){
                        case -1:
                            if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                            break;
                        case 0:
                            if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Faux.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Loose.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Severe.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Extreme.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                            if(gTextChannel.isNSFW()&&!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.NucleusMask.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                            if(gTextChannel.isNSFW()&&!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.DroneMask.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Mute.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Corrupt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Toy.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Squeaky.getName())) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Paci.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Training.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Chocke.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                            break;
                        case 1:
                            if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Puppy.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Kitty.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Pony.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Piggy.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Dinosaur.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Sheep.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Bird.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Cow.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Turkey.getName())) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                            break;
                        case 2:
                            if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_Hex.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_Base64.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_Binary.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_BinaryBlocks.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                            break;
                        case 3:
                            if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Pokemon_Pikachu.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Pokemon_Eevee.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            break;
                    }
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                switch (page){
                    case -1:
                        messageComponentManager.loadMessageComponents(gFileLevelGPath);
                        break;
                    case 0:
                        messageComponentManager.loadMessageComponents(gFileLevel0Path);
                        break;
                    case 1:
                        messageComponentManager.loadMessageComponents(gFileLevel1Path);
                        break;
                    case 2:
                        messageComponentManager.loadMessageComponents(gFileLevel2Path);
                        break;
                    case 3:
                        messageComponentManager.loadMessageComponents(gFileLevel3Path);
                        break;
                }

                try {
                    switch (page){
                        case -1:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_levelg");
                            break;
                        case 0:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level0");
                            break;
                        case 1:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level1");
                            break;
                        case 2:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level2");
                            break;
                        case 3:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level3");
                            break;
                    }
                    if(page>=0&&gNewUserProfile.isWearerDenied0withException()) {
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    switch (page){
                        case 0:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case Faux:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case Loose:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case Severe:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                                case Extreme:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                                case NucleusMask:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                                case DroneMask:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                                case Mute:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                                case Corrupt:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                                case Toy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                                case Squeaky:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                                case Paci:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);break;
                                case Training:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolC);break;
                                case Chocke:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolD);break;
                            }
                           break;
                        case 1:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case Puppy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case Kitty:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case Pony:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                                case Piggy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                                case Dinosaur:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                                case Sheep:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                                case Bird:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                                case Cow:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                                case Turkey:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            }
                            break;
                        case 2:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case ComputerNerd_Hex:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case ComputerNerd_Base64:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case ComputerNerd_Binary:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                                case ComputerNerd_BinaryBlocks:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            }
                            break;
                        case 3:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case Pokemon_Pikachu:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case Pokemon_Eevee:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            }
                            break;
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                 menuLevelsWearerListener(message,page);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuLevelsSomebody(int page){
            String fName="[menuLevels]";
            logger.info(fName+"page="+page);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Options");
                embed.addField(strSupportTitle,strSupport,false);
                String gagLevel, gagType;
                if(gNewUserProfile.cGAG.isOn()) {
                    if (GAGLEVELS.None.getName().equals(gNewUserProfile.cGAG.getLevelAsString())) {
                        gagLevel = "none";
                    } else {
                        gagLevel = gNewUserProfile.cGAG.getLevelAsString();
                    }
                }else{
                    gagLevel="no";
                }
                embed.addField("Gag level",gagLevel,true);
                gagType="?";
                switch (gNewUserProfile.cGAG.getType()){
                    case Ball:gagType="ball gag";break;
                    case Dildo:gagType="dildo";break;
                    case DReverseildo:gagType="reverse-dildo";break;
                    case LeatherMuzzle:gagType="leather muzzle";break;
                    case Paci:gagType="paci";break;
                    case Sock:gagType="socks";break;
                    case Tape:gagType="sticky tape";break;
                    case Underwear:gagType="musky underwear";break;
                    case WireFrameMuzzle:gagType="wire-framed muzzle";break;
                }
                embed.addField("Gag type",gagType,true);

                //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" "+GAGLEVELS.DroneReindeer.getName();
                switch (page){
                    case -1:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM)+" main  category";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" animal category";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN)+" nerd category";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" pokemon category";
                        break;
                    case 0:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.Faux.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.Loose.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+GAGLEVELS.Severe.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+GAGLEVELS.Extreme.getName();
                        if(gTextChannel.isNSFW())desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+GAGLEVELS.NucleusMask.getName();
                        if(gTextChannel.isNSFW())desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+GAGLEVELS.DroneMask.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+GAGLEVELS.Mute.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+GAGLEVELS.Corrupt.getName();
                        if(gTextChannel.isNSFW())desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+GAGLEVELS.Toy.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" "+GAGLEVELS.Squeaky.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" "+GAGLEVELS.Paci.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+GAGLEVELS.Training.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+GAGLEVELS.Chocke.getName();
                        break;
                    case 1:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.Puppy.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.Kitty.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+GAGLEVELS.Pony.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+GAGLEVELS.Piggy.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" "+GAGLEVELS.Dinosaur.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "+GAGLEVELS.Sheep.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" "+GAGLEVELS.Bird.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" "+GAGLEVELS.Cow.getName();
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" "+GAGLEVELS.Turkey.getName();
                        break;
                    case 2:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.ComputerNerd_Hex.getName();
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.ComputerNerd_Base64.getName();
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+GAGLEVELS.ComputerNerd_Binary.getName();
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" "+GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
                        break;
                    case 3:
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" "+nUngag;
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "+GAGLEVELS.Pokemon_Pikachu.getName();
                        desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "+GAGLEVELS.Pokemon_Eevee.getName();
                        break;
                }

                //embed.setDescription(desc);
                if(gNewUserProfile.gUserProfile.getUser().getIdLong()==gUser.getIdLong()){
                    embed.addField(" ", "Please select a gag option :"+desc, false);
                }else{
                    embed.addField(" ", "Please select a gag option for " + gNewUserProfile.gUserProfile.getUser().getAsMention() + ":"+desc, false);
                }
                //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                if(gNewUserProfile.isLocked_checkMember(gMember)){
                    desc="";
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(page>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                }
                if(page<3){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                }
                switch (page){
                    case -1:
                        if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                        break;
                    case 0:
                        if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Faux.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Loose.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Severe.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Extreme.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        if(gTextChannel.isNSFW()&&!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.NucleusMask.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                        if(gTextChannel.isNSFW()&&!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.DroneMask.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Mute.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Corrupt.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Toy.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Squeaky.getName())) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Paci.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Training.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Chocke.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                        break;
                    case 1:
                        if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Puppy.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Kitty.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Pony.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Piggy.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Dinosaur.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Sheep.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Bird.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Cow.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Turkey.getName())) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                        break;
                    case 2:
                        if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_Hex.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_Base64.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_Binary.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.ComputerNerd_BinaryBlocks.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        break;
                    case 3:
                        if(gNewUserProfile.cGAG.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Pokemon_Pikachu.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        if(!gNewUserProfile.cGAG.getLevelAsString().equalsIgnoreCase(GAGLEVELS.Pokemon_Eevee.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        break;
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                switch (page){
                    case -1:
                        messageComponentManager.loadMessageComponents(gFileLevelGPath);
                        break;
                    case 0:
                        messageComponentManager.loadMessageComponents(gFileLevel0Path);
                        break;
                    case 1:
                        messageComponentManager.loadMessageComponents(gFileLevel1Path);
                        break;
                    case 2:
                        messageComponentManager.loadMessageComponents(gFileLevel2Path);
                        break;
                    case 3:
                        messageComponentManager.loadMessageComponents(gFileLevel3Path);
                        break;
                }
                try {
                    switch (page){
                        case -1:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_levelg");
                            break;
                        case 0:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level0");
                            break;
                        case 1:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level1");
                            break;
                        case 2:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level2");
                            break;
                        case 3:
                            messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_level3");
                            break;
                    }
                    if(page>=0&&gNewUserProfile.isLocked_checkMember(gMember)&&gNewUserProfile.cGAG.isOn()) {
                        messageComponentManager.selectContainer.setDisabled();
                    }
                    switch (page){
                        case 0:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case Faux:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case Loose:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case Severe:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                                case Extreme:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                                case NucleusMask:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                                case DroneMask:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                                case Mute:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                                case Corrupt:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                                case Toy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                                case Squeaky:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);break;
                                case Paci:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);break;
                                case Training:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolC);break;
                                case Chocke:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolD);break;
                            }
                            break;
                        case 1:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case Puppy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case Kitty:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case Pony:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                                case Piggy:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                                case Dinosaur:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                                case Sheep:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                                case Bird:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven);break;
                                case Cow:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                                case Turkey:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine);break;
                            }
                            break;
                        case 2:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case ComputerNerd_Hex:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case ComputerNerd_Base64:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                                case ComputerNerd_Binary:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);break;
                                case ComputerNerd_BinaryBlocks:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);break;
                            }
                            break;
                        case 3:
                            if(!gNewUserProfile.cGAG.isOn())messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                            else switch (gNewUserProfile.cGAG.getLevel()){
                                case Pokemon_Pikachu:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                                case Pokemon_Eevee:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                            }
                            break;
                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuLevelsSomebodyListener(message,page);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuTypes(Member mtarget){
            String fName="[menuTypes]";
            logger.info(fName);
            try {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc = "";
                embed.setColor(llColorOrange);
                embed.addField(strSupportTitle, strSupport, false);
                if (mtarget != null) {
                    if (!getProfile()) {
                        logger.error(fName + ".can't get profile");
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                        }
                        return;
                    }
                    embed.setTitle(mtarget.getUser().getName()+"'s "+sRTitle + " Options");
                } else {
                    if (!getProfile()) {
                        logger.error(fName + ".can't get profile");
                        return;
                    }
                    embed.setTitle(sRTitle + " Options");
                }
                if (gNewUserProfile.gUserProfile.jsonObject.has(nGag) && gNewUserProfile.cGAG.isOn() ) {
                    String gagLevel, gagType;
                    if (GAGLEVELS.None.getName().equals(gNewUserProfile.cGAG.getLevelAsString())) {
                        gagLevel = "no";
                    } else {
                        gagLevel = gNewUserProfile.cGAG.getLevelAsString();
                    }
                    embed.addField("Gag level", gagLevel, true);
                    if (gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Ball.getName())) {
                        gagType = "ball gag";
                    } else if (isAdult && gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Dildo.getName())) {
                        gagType = "dildo";
                    } else if (isAdult && gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.DReverseildo.getName())) {
                        gagType = "reverse-dildo";
                    } else if (gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.LeatherMuzzle.getName())) {
                        gagType = "leather muzzle";
                    } else if (gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Paci.getName())) {
                        gagType = "paci";
                    } else if (isAdult && gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Sock.getName())) {
                        gagType = "socks";
                    } else if (gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Tape.getName())) {
                        gagType = "sticky tape";
                    } else if (isAdult && gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.Underwear.getName())) {
                        gagType = "musky underwear";
                    } else if (gNewUserProfile.cGAG.getTypeAsString().equals(GAGTYPES.WireFrameMuzzle.getName())) {
                        gagType = "wire-framed muzzle";
                    } else {
                        gagType = "?";
                    }
                    embed.addField("Gag type", gagType, true);
                } else {
                    embed.addField("Gag level", "N/A", false);
                }
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne) + " " + GAGTYPES.Ball.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo) + " " + GAGTYPES.LeatherMuzzle.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree) + " " + GAGTYPES.WireFrameMuzzle.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour) + " " + GAGTYPES.Paci.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive) + " " + GAGTYPES.Sock.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix) + " " + GAGTYPES.Dildo.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven) + " " + GAGTYPES.DReverseildo.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight) + " " + GAGTYPES.Ring.getName();
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine) + " " + GAGTYPES.Underwear.getName();

                //embed.setDescription(desc);
                if (gNewUserProfile.gUserProfile.getUser().getIdLong() == gUser.getIdLong()) {
                    embed.addField(" ", "Please select a gag option :" + desc, false);
                } else {
                    embed.addField(" ", "Please select a gag option for " + gNewUserProfile.gUserProfile.getUser().getAsMention() + ":" + desc, false);
                }
                //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                Message message=null;// = llSendMessageResponse(gUser, embed);
                //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if (gTextChannel.isNSFW()){
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                }*/
                messageComponentManager.loadMessageComponents(gFileTypePath);
                try {
                    messageComponentManager.selectContainer=messageComponentManager.messageBuildComponents.getComponent(0).getSelectById("select_type");
                    switch (gNewUserProfile.cGAG.getType()){
                        case Ball: messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);break;
                        case LeatherMuzzle:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);break;
                        case WireFrameMuzzle:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree) ;break;
                        case Paci:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour) ;break;
                        case Sock:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);break;
                        case Dildo:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);break;
                        case DReverseildo:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasSeven) ;break;
                        case Ring:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasEight);break;
                        case Underwear:messageComponentManager.selectContainer.setDefaultOptionByValue(lsUnicodeEmotes.aliasNine) ;break;

                    }
                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                menuTypesListener(message,mtarget);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuMainListener(Message message,Member mtarget){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasInformationSource:
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolA:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening gag level menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevels(mtarget,0);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolB:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening gag type menu",llColorBlue1);
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuTypes(mtarget);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening gag customization",llColorBlue1);
                                        if(mtarget==null||mtarget==gMember){
                                            rGagCustom();
                                        }else{
                                            rGagCustom(mtarget);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening gag training",llColorBlue1);
                                        if(mtarget==null||mtarget==gMember){
                                            rGagTraining();
                                        }else{
                                            rGagTraining(mtarget);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolE:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening gag exception",llColorBlue1);
                                        if(mtarget==null||mtarget==gMember){
                                            rGagException("menu");
                                        }else{
                                            rGagException(mtarget,"menu");
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasAlarmClock:
                                        sendOrUpdatePrivateEmbed(sRTitle,"Opening timelock",llColorBlue1);
                                        if(mtarget==null||mtarget==gMember){
                                            menuTimelock(null);
                                        }else{
                                            menuTimelock(mtarget);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasMute:
                                        if(mtarget==null||mtarget==gMember){
                                            rVoiceRestriction("",optionVoiceMute);
                                        }else{
                                            rVoiceRestriction(mtarget,"",optionVoiceMute);
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuMain(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasMicrophone2:
                                        if(mtarget==null||mtarget==gMember){
                                            rVoiceRestriction("",optionVoiceUnmute);
                                        }else{
                                            rVoiceRestriction(mtarget,"",optionVoiceUnmute);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasZipperMouth:
                                        if(mtarget==null||mtarget==gMember){
                                            rDisableSafety(optionDisable);
                                        }else{
                                            rDisableSafety(mtarget,optionDisable);
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuMain(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasGrimacing:
                                        if(mtarget==null||mtarget==gMember){
                                            rDisableSafety(optionEnable);
                                        }else{
                                            rDisableSafety(mtarget,optionEnable);
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuMain(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasEyeRoll:
                                        if(gNewUserProfile.cGAG.isDisable2Show4Wearer()){
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandWearerFalse);
                                            }else{
                                                rGagShow(mtarget,commandWearerFalse);
                                            }
                                        }else{
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandWearerTrue);
                                            }else{
                                                rGagShow(mtarget,commandWearerTrue);
                                            }
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuMain(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasFaceWithRaisedEyebrow:
                                        if(gNewUserProfile.cGAG.isDisable2Show4Others()){
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandOthersFalse);
                                            }else{
                                                rGagShow(mtarget,commandOthersFalse);
                                            }
                                        }else{
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandOthersTrue);
                                            }else{
                                                rGagShow(mtarget,commandOthersTrue);
                                            }
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuMain(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasFeather:
                                        if(mtarget==null||mtarget==gMember){
                                            rGagExtra(iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeather);
                                        }else{
                                            rGagExtra(mtarget,lsUnicodeEmotes.aliasFeather);
                                        }
                                        break;
                                    case iRDPatreon.patreonUser_365946661442158603_tickleaddon.keyFeatherShow:
                                        if(mtarget==null||mtarget==gMember){
                                            rGagExtra(iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle_show);
                                        }else{
                                            rGagExtra(mtarget,iRDPatreon.patreonUser_365946661442158603_tickleaddon.com_toggle_show);
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuMain(gTarget);
                                        break;
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                        return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                        rHelp("main");return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))) {
                                        menuLevels(mtarget,0);

                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))) {
                                        menuTypes(mtarget);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagCustom();
                                        }else{
                                            rGagCustom(mtarget);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagTraining();
                                        }else{
                                            rGagTraining(mtarget);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagException("menu");
                                        }else{
                                            rGagException(mtarget,"menu");
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasAlarmClock))) {
                                        if(mtarget==null||mtarget==gMember){
                                            menuTimelock(null);
                                        }else{
                                            menuTimelock(mtarget);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rVoiceRestriction("",optionVoiceMute);
                                        }else{
                                            rVoiceRestriction(mtarget,"",optionVoiceMute);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMicrophone2))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rVoiceRestriction("",optionVoiceUnmute);
                                        }else{
                                            rVoiceRestriction(mtarget,"",optionVoiceUnmute);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZipperMouth))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rDisableSafety(optionDisable);
                                        }else{
                                            rDisableSafety(mtarget,optionDisable);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGrimacing))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rDisableSafety(optionEnable);
                                        }else{
                                            rDisableSafety(mtarget,optionEnable);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEyeRoll))) {
                                        if(gNewUserProfile.cGAG.isDisable2Show4Wearer()){
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandWearerFalse);
                                            }else{
                                                rGagShow(mtarget,commandWearerFalse);
                                            }
                                        }else{
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandWearerTrue);
                                            }else{
                                                rGagShow(mtarget,commandWearerTrue);
                                            }
                                        }

                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFaceWithRaisedEyebrow))) {
                                        if(gNewUserProfile.cGAG.isDisable2Show4Others()){
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandOthersFalse);
                                            }else{
                                                rGagShow(mtarget,commandOthersFalse);
                                            }
                                        }else{
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandOthersTrue);
                                            }else{
                                                rGagShow(mtarget,commandOthersTrue);
                                            }
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPoultryLeg))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagExtra(lsUnicodeEmotes.aliasFeather);
                                        }else{
                                            rGagExtra(mtarget,lsUnicodeEmotes.aliasFeather);
                                        }
                                    }
                                    else{
                                        menuMain(mtarget);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))) {
                                        return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                        rHelp("main");return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))) {
                                        menuLevels(mtarget,0);

                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))) {
                                        menuTypes(mtarget);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagCustom();
                                        }else{
                                            rGagCustom(mtarget);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagTraining();
                                        }else{
                                            rGagTraining(mtarget);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagException("menu");
                                        }else{
                                            rGagException(mtarget,"menu");
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasAlarmClock))) {
                                        if(mtarget==null||mtarget==gMember){
                                            menuTimelock(null);
                                        }else{
                                            menuTimelock(mtarget);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMute))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rVoiceRestriction("",optionVoiceMute);
                                        }else{
                                            rVoiceRestriction(mtarget,"",optionVoiceMute);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMicrophone2))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rVoiceRestriction("",optionVoiceUnmute);
                                        }else{
                                            rVoiceRestriction(mtarget,"",optionVoiceUnmute);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZipperMouth))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rDisableSafety(optionDisable);
                                        }else{
                                            rDisableSafety(mtarget,optionDisable);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGrimacing))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rDisableSafety(optionEnable);
                                        }else{
                                            rDisableSafety(mtarget,optionEnable);
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEyeRoll))) {
                                        if(gNewUserProfile.cGAG.isDisable2Show4Wearer()){
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandWearerFalse);
                                            }else{
                                                rGagShow(mtarget,commandWearerFalse);
                                            }
                                        }else{
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandWearerTrue);
                                            }else{
                                                rGagShow(mtarget,commandWearerTrue);
                                            }
                                        }

                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFaceWithRaisedEyebrow))) {
                                        if(gNewUserProfile.cGAG.isDisable2Show4Others()){
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandOthersFalse);
                                            }else{
                                                rGagShow(mtarget,commandOthersFalse);
                                            }
                                        }else{
                                            if(mtarget==null||mtarget==gMember){
                                                rGagShow(commandOthersTrue);
                                            }else{
                                                rGagShow(mtarget,commandOthersTrue);
                                            }
                                        }
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPoultryLeg))) {
                                        if(mtarget==null||mtarget==gMember){
                                            rGagExtra(lsUnicodeEmotes.aliasFeather);
                                        }else{
                                            rGagExtra(mtarget,lsUnicodeEmotes.aliasFeather);
                                        }
                                    }
                                    else{
                                        menuMain(mtarget);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message,int page){
            String fName="[menuLevels]";
            logger.info(fName+"page="+page);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowUp))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going back",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    if(page==-1){
                                        menuMain(null);
                                    }else{
                                        menuLevelsWearer(-1);
                                    }

                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowBackward))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going back",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsWearer(page-1);
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowForward))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going forward",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsWearer(page+1);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getId();
                                logger.warn(fName+"id="+id);
                                String value=e.getValues().get(0);
                                logger.warn(fName+"value="+value);
                                String level="";
                                llMessageDelete(message);
                                switch (page){
                                    case -1:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasSymbolM:menuLevelsWearer(0);break;
                                            case lsUnicodeEmotes.aliasSymbolA:menuLevelsWearer(1);break;
                                            case lsUnicodeEmotes.aliasSymbolN:menuLevelsWearer(2);break;
                                            case lsUnicodeEmotes.aliasSymbolP:menuLevelsWearer(3);break;
                                        }
                                        return;
                                    case 0:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.Faux.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.Loose.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=GAGLEVELS.Severe.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=GAGLEVELS.Extreme.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=GAGLEVELS.NucleusMask.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=GAGLEVELS.DroneMask.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=GAGLEVELS.Mute.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=GAGLEVELS.Corrupt.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=GAGLEVELS.Toy.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolA:level=GAGLEVELS.Squeaky.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolB:level=GAGLEVELS.Paci.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolC:level=GAGLEVELS.Training.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolD:level=GAGLEVELS.Chocke.getName();break;
                                        }
                                        break;
                                    case 1:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.Puppy.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.Kitty.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=GAGLEVELS.Pony.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=GAGLEVELS.Piggy.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=GAGLEVELS.Dinosaur.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=GAGLEVELS.Sheep.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=GAGLEVELS.Bird.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=GAGLEVELS.Cow.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=GAGLEVELS.Turkey.getName();break;
                                        }
                                        break;
                                    case 2:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.ComputerNerd_Hex.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.ComputerNerd_Base64.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=GAGLEVELS.ComputerNerd_Binary.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=GAGLEVELS.ComputerNerd_BinaryBlocks.getName();break;

                                        }
                                        break;
                                    case 3:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.Pokemon_Pikachu.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.Pokemon_Eevee.getName();break;
                                        }
                                        break;
                                }
                                if(value.equalsIgnoreCase(lsUnicodeEmotes.aliasZero))level=nUngag;
                                if(!level.isBlank()){
                                    rGagBinding(level);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsWearer(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                    {
                                        if(page==-1){
                                            menuMain(null);
                                        }else{
                                            menuLevelsWearer(-1);
                                        }
                                        return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward)))
                                    {
                                        menuLevelsWearer(page-1);return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward)))
                                    {
                                        menuLevelsWearer(page+1);return;
                                    }
                                    switch (page){
                                        case -1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM))) menuLevelsWearer(0);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))menuLevelsWearer(1);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN)))menuLevelsWearer(2);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)))menuLevelsWearer(3);
                                            return;
                                        case 0:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Faux.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Loose.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Severe.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Extreme.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.NucleusMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.DroneMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Mute.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Corrupt.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Toy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))level=GAGLEVELS.Squeaky.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)))level=GAGLEVELS.Paci.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)))level=GAGLEVELS.Training.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)))level=GAGLEVELS.Chocke.getName();
                                            break;
                                        case 1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Puppy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Kitty.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Pony.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Piggy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.Dinosaur.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.Sheep.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Bird.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Cow.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Turkey.getName();
                                            break;
                                        case 2:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.ComputerNerd_Hex.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.ComputerNerd_Base64.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.ComputerNerd_Binary.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
                                            break;
                                        case 3:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Pokemon_Pikachu.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Pokemon_Eevee.getName();
                                            break;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)))level=nUngag;
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rGagBinding(level);
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                    {
                                        if(page==-1){
                                            menuMain(null);
                                        }else{
                                            menuLevelsWearer(-1);
                                        }
                                        return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward)))
                                    {
                                        menuLevelsWearer(page-1);return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward)))
                                    {
                                        menuLevelsWearer(page+1);return;
                                    }
                                    switch (page){
                                        case -1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM))) menuLevelsWearer(0);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))menuLevelsWearer(1);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN)))menuLevelsWearer(2);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)))menuLevelsWearer(3);
                                            return;
                                        case 0:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Faux.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Loose.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Severe.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Extreme.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.NucleusMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.DroneMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Mute.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Corrupt.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Toy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))level=GAGLEVELS.Squeaky.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)))level=GAGLEVELS.Paci.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)))level=GAGLEVELS.Training.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)))level=GAGLEVELS.Chocke.getName();
                                            break;
                                        case 1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Puppy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Kitty.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Pony.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Piggy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.Dinosaur.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.Sheep.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Bird.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Cow.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Turkey.getName();
                                            break;
                                        case 2:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.ComputerNerd_Hex.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.ComputerNerd_Base64.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.ComputerNerd_Binary.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
                                            break;
                                        case 3:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Pokemon_Pikachu.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Pokemon_Eevee.getName();
                                            break;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)))level=nUngag;
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rGagBinding(level);
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuLevelsSomebodyListener(Message message,int page){
            String fName="[menuLevels]";
            logger.info(fName+"page="+page);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowUp))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going back",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    if(page==-1){
                                        menuMain(gNewUserProfile.getMember());
                                    }else{
                                        menuLevelsSomebody(-1);
                                    }
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowBackward))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going back",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsSomebody(page-1);
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowForward))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going forward",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsSomebody(page+1);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getId();
                                logger.warn(fName+"id="+id);
                                String value=e.getValues().get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (page){
                                    case -1:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasSymbolM:menuLevelsWearer(0);break;
                                            case lsUnicodeEmotes.aliasSymbolA:menuLevelsWearer(1);break;
                                            case lsUnicodeEmotes.aliasSymbolN:menuLevelsWearer(2);break;
                                            case lsUnicodeEmotes.aliasSymbolP:menuLevelsWearer(3);break;
                                        }
                                        return;
                                    case 0:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.Faux.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.Loose.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=GAGLEVELS.Severe.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=GAGLEVELS.Extreme.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=GAGLEVELS.NucleusMask.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=GAGLEVELS.DroneMask.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=GAGLEVELS.Mute.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=GAGLEVELS.Corrupt.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=GAGLEVELS.Toy.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolA:level=GAGLEVELS.Squeaky.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolB:level=GAGLEVELS.Paci.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolC:level=GAGLEVELS.Training.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolD:level=GAGLEVELS.Chocke.getName();break;
                                        }
                                        break;
                                    case 1:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.Puppy.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.Kitty.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=GAGLEVELS.Pony.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=GAGLEVELS.Piggy.getName();break;
                                            case lsUnicodeEmotes.aliasFive:level=GAGLEVELS.Dinosaur.getName();break;
                                            case lsUnicodeEmotes.aliasSix:level=GAGLEVELS.Sheep.getName();break;
                                            case lsUnicodeEmotes.aliasSeven:level=GAGLEVELS.Bird.getName();break;
                                            case lsUnicodeEmotes.aliasEight:level=GAGLEVELS.Cow.getName();break;
                                            case lsUnicodeEmotes.aliasNine:level=GAGLEVELS.Turkey.getName();break;
                                        }
                                        break;
                                    case 2:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.ComputerNerd_Hex.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.ComputerNerd_Base64.getName();break;
                                            case lsUnicodeEmotes.aliasThree:level=GAGLEVELS.ComputerNerd_Binary.getName();break;
                                            case lsUnicodeEmotes.aliasFour:level=GAGLEVELS.ComputerNerd_BinaryBlocks.getName();break;

                                        }
                                        break;
                                    case 3:
                                        switch (value){
                                            case lsUnicodeEmotes.aliasOne:level=GAGLEVELS.Pokemon_Pikachu.getName();break;
                                            case lsUnicodeEmotes.aliasTwo:level=GAGLEVELS.Pokemon_Eevee.getName();break;
                                        }
                                        break;
                                }
                                if(value.equalsIgnoreCase(lsUnicodeEmotes.aliasZero))level=nUngag;
                                if(!level.isBlank()){
                                    rGagBinding(gNewUserProfile.getMember(),level);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuLevelsSomebody(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                    {
                                        if(page==-1){
                                            menuMain(null);
                                        }else{
                                            menuLevelsSomebody(-1);
                                        }
                                        return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward)))
                                    {
                                        menuLevelsSomebody(page-1);return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward)))
                                    {
                                        menuLevelsSomebody(page+1);return;
                                    }
                                    switch (page){
                                        case -1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM))) menuLevelsSomebody(0);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))menuLevelsSomebody(1);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN)))menuLevelsSomebody(2);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)))menuLevelsSomebody(3);
                                            return;
                                        case 0:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Faux.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Loose.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Severe.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Extreme.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.NucleusMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.DroneMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Mute.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Corrupt.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Toy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))level=GAGLEVELS.Squeaky.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)))level=GAGLEVELS.Paci.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)))level=GAGLEVELS.Training.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)))level=GAGLEVELS.Chocke.getName();
                                            break;
                                        case 1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Puppy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Kitty.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Pony.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Piggy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.Dinosaur.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.Sheep.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Bird.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Cow.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Turkey.getName();
                                            break;
                                        case 2:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.ComputerNerd_Hex.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.ComputerNerd_Base64.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.ComputerNerd_Binary.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
                                            break;
                                        case 3:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Pokemon_Pikachu.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Pokemon_Eevee.getName();
                                            break;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)))level=nUngag;
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rGagBinding(gNewUserProfile.getMember(),level);
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                    {
                                        if(page==-1){
                                            menuMain(null);
                                        }else{
                                            menuLevelsSomebody(-1);
                                        }
                                        return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward)))
                                    {
                                        menuLevelsSomebody(page-1);return;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward)))
                                    {
                                        menuLevelsSomebody(page+1);return;
                                    }
                                    switch (page){
                                        case -1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolM))) menuLevelsSomebody(0);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))menuLevelsSomebody(1);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolN)))menuLevelsSomebody(2);
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)))menuLevelsSomebody(3);
                                            return;
                                        case 0:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Faux.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Loose.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Severe.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Extreme.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.NucleusMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.DroneMask.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Mute.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Corrupt.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Toy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)))level=GAGLEVELS.Squeaky.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)))level=GAGLEVELS.Paci.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)))level=GAGLEVELS.Training.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)))level=GAGLEVELS.Chocke.getName();
                                            break;
                                        case 1:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Puppy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Kitty.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.Pony.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.Piggy.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGLEVELS.Dinosaur.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level=GAGLEVELS.Sheep.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGLEVELS.Bird.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGLEVELS.Cow.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGLEVELS.Turkey.getName();
                                            break;
                                        case 2:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.ComputerNerd_Hex.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.ComputerNerd_Base64.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGLEVELS.ComputerNerd_Binary.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGLEVELS.ComputerNerd_BinaryBlocks.getName();
                                            break;
                                        case 3:
                                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGLEVELS.Pokemon_Pikachu.getName();
                                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGLEVELS.Pokemon_Eevee.getName();
                                            break;
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)))level=nUngag;
                                    if(!level.isBlank()){
                                        if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                            rHelp("main");
                                        }else{
                                            rGagBinding(gNewUserProfile.getMember(),level);
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuTypesListener(Message message,Member mtarget){
            String fName="[menuTypes]";
            logger.info(fName);
            try {
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowUp))
                                {
                                    sendOrUpdatePrivateEmbed(sRTitle,"Going back",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuMain(gNewUserProfile.getMember());
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) deferReplySet(e);
                            try {
                                String id=e.getId();
                                logger.warn(fName+"id="+id);
                                String value=e.getValues().get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                String level="";
                                switch (value){
                                    case lsUnicodeEmotes.aliasOne:level=GAGTYPES.Ball.getName();break;
                                    case lsUnicodeEmotes.aliasTwo:level=GAGTYPES.LeatherMuzzle.getName();break;
                                    case lsUnicodeEmotes.aliasThree:level=GAGTYPES.WireFrameMuzzle.getName();break;
                                    case lsUnicodeEmotes.aliasFour:level=GAGTYPES.Paci.getName();break;
                                    case lsUnicodeEmotes.aliasFive:level=GAGTYPES.Sock.getName();break;
                                    case lsUnicodeEmotes.aliasSix:level= GAGTYPES.Dildo.getName();break;
                                    case lsUnicodeEmotes.aliasSeven:level=GAGTYPES.DReverseildo.getName();break;
                                    case lsUnicodeEmotes.aliasEight:level=GAGTYPES.Ring.getName();break;
                                    case lsUnicodeEmotes.aliasNine:level=GAGTYPES.Underwear.getName();break;
                                }

                                if(!level.isBlank()){
                                    if(mtarget!=null){
                                        rGagType(mtarget,level);
                                    }else{
                                        rGagType(level);
                                    }
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuTypes(gTarget);
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                    {
                                        menuMain(mtarget);return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                        rHelp("main");return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGTYPES.Ball.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGTYPES.LeatherMuzzle.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGTYPES.WireFrameMuzzle.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGTYPES.Paci.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGTYPES.Sock.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level= GAGTYPES.Dildo.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGTYPES.DReverseildo.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGTYPES.Ring.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGTYPES.Underwear.getName();
                                    if(!level.isBlank()){
                                        if(mtarget!=null){
                                            rGagType(mtarget,level);
                                        }else{
                                            rGagType(level);
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
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
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)))
                                    {
                                        menuMain(mtarget);return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                        rHelp("main");return;
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)))level=GAGTYPES.Ball.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)))level=GAGTYPES.LeatherMuzzle.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)))level=GAGTYPES.WireFrameMuzzle.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)))level=GAGTYPES.Paci.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)))level=GAGTYPES.Sock.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)))level= GAGTYPES.Dildo.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)))level=GAGTYPES.DReverseildo.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)))level=GAGTYPES.Ring.getName();
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)))level=GAGTYPES.Underwear.getName();
                                    if(!level.isBlank()){
                                        if(mtarget!=null){
                                            rGagType(mtarget,level);
                                        }else{
                                            rGagType(level);
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        boolean timelockdurationhit6h =false;
        private void menuTimelock(Member mtarget){
            String fName="[menuTypes]";
            logger.info(fName);
            try {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc = "";
                embed.setColor(llColorOrange);
                embed.addField(strSupportTitle, strSupport, false);
                if (mtarget != null) {
                    if (!getProfile()) {
                        logger.error(fName + ".can't get profile");
                        return;
                    }
                    if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                        if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                            logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                        }
                        return;
                    }
                } else {
                    if (!getProfile()) {
                        logger.error(fName + ".can't get profile");
                        return;
                    }
                }
                isMenu=true;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                timelockdurationhit6h =gNewUserProfile.cGAG.isTimeLockeDuration6h();
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    if(isPatreon){
                        embed.setTitle(sRTitle + " Timelock Options "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(sRTitle + " Timelock Options "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }

                }else{
                    if(isPatreon){
                        embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle + " Timelock Options "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle + " Timelock Options "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }
                }
                if (gNewUserProfile.gUserProfile.jsonObject.has(nGag) && gNewUserProfile.cGAG.isOn() ) {
                    String gagLevel;
                    if (GAGLEVELS.None.getName().equals(gNewUserProfile.cGAG.getLevelAsString())) {
                        gagLevel = "no";
                    } else {
                        gagLevel = gNewUserProfile.cGAG.getLevelAsString();
                    }
                    embed.addField("Gag level", gagLevel, true);
                } else {
                    embed.addField("Gag level", "N/A", false);
                }
                long duration=gNewUserProfile.cGAG.getTimeLockDuration();
                long remaning=gNewUserProfile.cGAG.getTimeLockRemaning();
                logger.info(fName+"duration="+duration+",  remaning="+ remaning);
                desc = "duration: "+lsUsefullFunctions.displayDuration(duration);
                if(remaning<=0){
                    desc += "\nremaning: 0";
                }else{
                    desc += "\nremaning: "+lsUsefullFunctions.displayDuration(remaning);
                }
                embed.addField("Gag Timelock", desc, false);
                desc = "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne) + " +5 minutes";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo) + " +30 minutes";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree) + " +1 hour";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour) + " +6 hours (Patreon supporter tier 2+)";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive) + " +12 hours (Patreon supporter tier 2+)";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix) + " +1 day (Patreon supporter tier 2+)";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA) + " -5 minutes";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB) + " -30 minutes";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC) + " -1 hour";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD) + " -6 hours (Patreon supporter tier 2+)";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE) + " -12 hours (Patreon supporter tier 2+)";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF) + " -1 day (Patreon supporter tier 2+)";
                desc += "\n" + gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock) + " start";
                desc+="\nNote that once started it can't be changed.";
                desc+="\nPatreon tier 2+ supporters can add duration above 6h.";
                if(!gNewUserProfile.cGAG.isValid2BePutinTimeLock()){
                    desc+="\nInvalid gag state to start timelock";
                }
                if(!gNewUserProfile.cGAG.isValidTimeLockDuration()){
                    desc+="\nInvalid duration to start timelock. It needs to be between 15 minutes and 14 days.";
                }
                embed.addField(" ", "Please select a timelock option :" + desc, false);

                //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                Message message = llSendMessageResponse(gUser, embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(!gNewUserProfile.cGAG.isTimeLockStarted()){
                    if(isPatreon){
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    }
                    else if(!timelockdurationhit6h){
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    }
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                    if(isPatreon){
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                    }
                    if(gNewUserProfile.cGAG.isValid2BePutinTimeLock()&&gNewUserProfile.cGAG.isValidTimeLockDuration())lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
                }
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    menuMain(mtarget);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                       rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()+5*milliseconds_minute);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()+5*milliseconds_minute);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()+30*milliseconds_minute);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()+30*milliseconds_minute);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()+milliseconds_hour);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()+milliseconds_hour);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()-5*milliseconds_minute);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()-5*milliseconds_minute);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()-30*milliseconds_minute);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()-30*milliseconds_minute);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))&&(!timelockdurationhit6h||isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()-milliseconds_hour);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()-milliseconds_hour);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))&&(isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()+6*milliseconds_hour);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()+6*milliseconds_hour);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))&&(isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()+12*milliseconds_hour);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()+12*milliseconds_hour);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))&&(isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()+milliseconds_day);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()+milliseconds_day);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))&&(isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()-6*milliseconds_hour);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()-6*milliseconds_hour);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))&&(isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()-12*milliseconds_hour);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()-12*milliseconds_hour);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))&&(isPatreon||lsMemberIsBotOwner(gMember)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("set",gNewUserProfile.cGAG.getTimeLockDuration()-milliseconds_day);
                                    }else{
                                        rGagTimeLock(mtarget,"set",gNewUserProfile.cGAG.getTimeLockDuration()-milliseconds_day);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)))
                                {
                                    if(mtarget==null||mtarget==gMember){
                                        rGagTimeLock("start",0);
                                    }else{
                                        rGagTimeLock(mtarget,"start",0);
                                    }
                                }else{
                                    menuTimelock(mtarget);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle+" Timelock",e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llMessageDelete(message));

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle+" Timelock",e.toString());
            }
        }



        private void menuExceptions(){
            String fName="[menuExceptions]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s Gag Channel Exceptions");
                embed.setColor(llColorBlue1);
                String desc="";
                if(gNewUserProfile.cGAG.isEmptyExceptionList()){
                    embed.addField("Channel list","<is empty>",false);
                }else{
                    embed.addField("Channel list",getExceptionChannelMention(),false);
                }

                desc="`"+llPrefixStr+"gag <@Pet> exception add <mentioned channels>` to add a channel to exception list";
                desc+="\n`"+llPrefixStr+"gag <@Pet> exception remove <mentioned channels>` to remove a channel from exception list";
                if(gNewUserProfile.cGAG.isExceptionEnabled()){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable";
                }
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" to clear";
                embed.addField("Selecting an option",desc,false);


                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);

                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(gNewUserProfile.cGAG.isExceptionEnabled()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gNewUserProfile.cGAG.isEmptyExceptionList())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    if(gNewUserProfile.gUserProfile.getUser().getIdLong()==gUser.getIdLong()){
                                        menuMain(null);
                                    }else{
                                        menuMain(gNewUserProfile.gUserProfile.getMember());
                                    }
                                    return;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rGagException(vOn);
                                    }else{
                                        rGagException(gNewUserProfile.getMember(),vOn);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rGagException(vOff);
                                    }else{
                                        rGagException(gNewUserProfile.getMember(),vOff);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        rGagException(vClear);
                                    }else{
                                        rGagException(gNewUserProfile.getMember(),vClear);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    logger.warn(fName+"trigger="+name);
                                    //llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                }else{
                                    menuExceptions();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        int boosterLimit4CustomText=40;
        private void rGagCustom() {
            String fName = "[rGagBinding]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                return;
            }
            menuCustomGag();
        }
        private void rGagCustom(Member mtarget) {
            String fName = "[rGagBinding]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
            }
            menuCustomGag();
        }
        private void menuCustomGag(){
            String fName="[menuCustomGag]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Custom Text 4 GagLevels");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s Custom Text 4 GagLevels");
                }
                embed.setColor(llColorBlue1);
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                String desc="Select one:";
                if(gTextChannel.isNSFW()){
                    desc+="\n:regional_indicator_a: for "+ GAGLEVELS.NucleusMask.getName();
                    desc+="\n:regional_indicator_b: for "+GAGLEVELS.DroneMask.getName();
                    desc+="\n:regional_indicator_t: for "+GAGLEVELS.Toy.getName();
                }
                desc+="\n:one: for "+GAGLEVELS.Puppy.getName();
                desc+="\n:two: for "+GAGLEVELS.Kitty.getName();
                desc+="\n:three: for "+GAGLEVELS.Pony.getName();
                desc+="\n:four: for "+GAGLEVELS.Piggy.getName();
                desc+="\n:five: for "+GAGLEVELS.Dinosaur.getName();
                desc+="\n:six: for "+GAGLEVELS.Squeaky.getName();
                desc+="\n:seven: for "+GAGLEVELS.Sheep.getName();
                desc+="\n:eight: for "+GAGLEVELS.Bird.getName();
                desc+="\n:nine: for "+GAGLEVELS.Cow.getName();
                desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" for "+GAGLEVELS.Pokemon_Pikachu.getName()+" (only Patreon tier 2 supporters)";
                desc+="\n[new]"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" for "+GAGLEVELS.Pokemon_Eevee.getName()+" (only Patreon tier 2 supporters)";
                embed.addField("Selecting a gag level",desc,false);
                embed.addField("Limitations","Each level has "+normalLimit4CustomText+" available +"+(patreonLimit4CustomText-normalLimit4CustomText)+" for Patreon tier 2+ members.",false);

                try {
                    logger.info(fName + ".has flagOnlyUseCustom");
                    if(gNewUserProfile.cGAG.isFlagOnlyUseCustom()){
                        embed.addField("Only custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to disable.",false);
                    }else{
                        embed.addField("Only custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.\nIf that gag level has custom entries, it will use only that.",false);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                //embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,embed);
                if(message==null)message=lsMessageHelper.lsSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(gTextChannel.isNSFW()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                if(isPatreon){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                menuCustomGagListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuCustomGagListener(Message message){
            String fName="[menuCustomGagListener]";
            logger.info(fName);
            try{
                if(message.isFromGuild()){
                    gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                        if(gNewUserProfile.gUserProfile.getUser().getIdLong()==gUser.getIdLong()){
                                            menuMain(null);
                                        }else{
                                            menuMain(gNewUserProfile.gUserProfile.getMember());
                                        }
                                        return;
                                    }
                                    if(name.contains(lsUnicodeEmotes.aliasGreenCircle)||name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuOnlyCustomGagOption("toggle");
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Puppy.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Kitty.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Pony.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Piggy.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Dinosaur.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Squeaky.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Sheep.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Bird.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Cow.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Pokemon_Pikachu.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Pokemon_Eevee.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.NucleusMask.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.DroneMask.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                        logger.warn(fName+"trigger="+name);
                                        lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,lsMessageHelper.lsGenerateEmbed(sRTitle,"Check your DM",llColorBlue1));
                                        menuCustomSelected(GAGLEVELS.Toy.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                        logger.warn(fName+"trigger="+name);
                                        //llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    }else{
                                        if(gCurrentInteractionHook==null)menuCustomGag();
                                    }
                                    if(gCurrentInteractionHook!=null){
                                        menuCustomGagListener(message);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },15, TimeUnit.MINUTES, () -> {
                                //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                                llMessageDelete(message);
                            });
                }else{
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                        if(gNewUserProfile.gUserProfile.getUser().getIdLong()==gUser.getIdLong()){
                                            menuMain(null);
                                        }else{
                                            menuMain(gNewUserProfile.gUserProfile.getMember());
                                        }
                                        return;
                                    }
                                    if(name.contains(lsUnicodeEmotes.aliasGreenCircle)||name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                        logger.warn(fName+"trigger="+name);
                                        menuOnlyCustomGagOption("toggle");
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Puppy.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Kitty.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Pony.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Piggy.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Dinosaur.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Squeaky.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Sheep.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Bird.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Cow.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Pokemon_Pikachu.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Pokemon_Eevee.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.NucleusMask.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.DroneMask.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                        logger.warn(fName+"trigger="+name);
                                        menuCustomSelected(GAGLEVELS.Toy.getName());
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                        logger.warn(fName+"trigger="+name);
                                        //llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    }else{
                                        menuCustomGag();
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },15, TimeUnit.MINUTES, () -> {
                                //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                                llMessageDelete(message);
                            });
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuOnlyCustomGagOption(String option){
            String fName="[menuOnlyCustomGagOption]";
            logger.info(fName);
            try{
                logger.info(fName+"option="+option);
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".can't use do to access owner");
                        sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                        return;
                    }
                }else{
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                        logger.info(fName + ".can't use do to locked by not you");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                        return;
                    }else
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                        logger.info(fName + ".can't use do to access protected");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                    }
                }
                if(option.equalsIgnoreCase("toggle")){
                    try {
                        logger.info(fName + ".has flagOnlyUseCustom");
                        if(gNewUserProfile.cGAG.isFlagOnlyUseCustom()){
                            option="false";
                        }else{
                            option="true";
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(option.equalsIgnoreCase("true")||option.equalsIgnoreCase(vOn)){
                    gNewUserProfile.cGAG.setFlagOnlyUseCustom(true);
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        sendOrUpdatePrivateEmbed(sRTitle,"Restricted to only use custom texts if it has.", llColorPurple2);
                    }else{
                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sMainRTitle,"Restricted to only use custom texts if it has by "+gUser.getAsMention(), llColorPurple2);
                        sendOrUpdatePrivateEmbed(sRTitle,"Restricted "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" to only use custom texts if it has.", llColorPurple2);
                    }
                }
                else if(option.equalsIgnoreCase("false")||option.equalsIgnoreCase(vOff)){
                    gNewUserProfile.cGAG.setFlagOnlyUseCustom(false);
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        sendOrUpdatePrivateEmbed(sRTitle,"Restriction lifted to only use custom text.", llColorPurple2);
                    }else{
                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sMainRTitle,"Restriction lifted to only use custom texts by "+gUser.getAsMention(), llColorPurple2);
                        sendOrUpdatePrivateEmbed(sRTitle,"Restriction lifted for "+gNewUserProfile.gUserProfile.getUser().getAsMention()+" to only use custom texts.", llColorPurple2);
                    }
                }
                saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuCustomSelected(String key){
            String fName="[menuCustomSelected]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                int lenght=gNewUserProfile.cGAG.getCustomGagTexLength(key);
                String desc="";
                if(lenght>0) {
                    desc += "\n:one: for list";
                }
                desc+="\n:two: for adding new line";
                if(lenght>0){
                    desc+="\n:three: to clear";
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                embed.addField("Gag Level",key,false);
                if(isPatreon){
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle("Custom Text 4 GagLevels "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Custom Text 4 GagLevels "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }
                    embed.addField("Lines used/total:", lenght +"/"+patreonLimit4CustomText,false);
                }else{
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle("Custom Text 4 GagLevels "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Custom Text 4 GagLevels "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }
                    embed.addField("Lines used/total:", lenght +"/"+normalLimit4CustomText,false);
                }

                embed.addField("Options",desc,false);
                embed.addField("Formatting","Use the world `!name` to show your nickname that is currently in use.\nUse `!slave` to show your slave number.",false);
                embed.addField("Done","Select :white_check_mark: to finish & go back",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse(gUser,embed);
                if(lenght>0) {
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).queue();
                }
                if((isPatreon&&lenght<patreonLimit4CustomText)||lenght<normalLimit4CustomText){
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).queue();
                }
                if(lenght>0){
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).queue();
                }
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    menuCustomList(key,0);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    if((isPatreon&&lenght<patreonLimit4CustomText)||lenght<normalLimit4CustomText){
                                        menuCustomAddText(key);
                                    }else{
                                        menuCustomSelected(key);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                                            logger.info(fName + ".can't use do to access owner");
                                            sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                                            return;
                                        }
                                    }else{
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                                            logger.info(fName + ".can't use do to locked by not you");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                                            return;
                                        }else
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                                            logger.info(fName + ".can't use do to access protected");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                                        }
                                    }
                                    gNewUserProfile.cGAG.setCustomGagText(key,new JSONArray());
                                    saveProfile();
                                    menuCustomGag();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    logger.warn(fName+"trigger="+name);
                                    menuCustomGag();
                                }else{
                                    menuCustomSelected(key);
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuCustomAddText(String key){
            String fName="[menuCustomAddText]";
            logger.info(fName);
            try{
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".can't use do to access owner");
                        sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                        return;
                    }
                }else{
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                        logger.info(fName + ".can't use do to locked by not you");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                        return;
                    }else
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                        logger.info(fName + ".can't use do to access protected");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                    }
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Custom Text 4 GagLevels");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Custom Text 4 GagLevels");
                }
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuCustomSelected(key); llMessageDelete(message);
                                }else
                                if(content.isBlank()){
                                    menuCustomAddText(key); llMessageDelete(message);
                                }else{
                                    if(gNewUserProfile.cGAG.getCustomGagText()==null){
                                        gNewUserProfile.cGAG.setCustomGagText(new JSONObject());
                                    }
                                    if(gNewUserProfile.cGAG.hasTextCustomGagText(key,content)){
                                        embed.setDescription("Already has such entry");

                                    }
                                    else if(gNewUserProfile.cGAG.addCustomGagText(key,content)!=null){
                                        saveProfile();
                                        embed.setDescription("Added entry.");
                                    }
                                    else{
                                        embed.setDescription("Failed to add entry.");
                                    }
                                    lsMessageHelper.lsEditMessage(message,embed);
                                    menuCustomSelected(key);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuCustomList(String key, int page){
            String fName="[menuCustomList]";
            logger.info(fName);
            try{
                logger.info(fName+"key="+key+", page="+page);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                int length=gNewUserProfile.cGAG.getCustomGagTexLength(key);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Custom Text 4 GagLevels");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Custom Text 4 GagLevels");
                }
                embed.addField("List","name: "+key+"\nsize: "+length+"",false);
                JSONObject quicklist=new JSONObject();boolean canBeQuickList=false;
                if(length<10){
                    quicklist=listofPreviews2(gNewUserProfile.cGAG.getCustomGagText(key));
                    if(quicklist.has("hitLimit")&&!quicklist.getBoolean("hitLimit")){
                        if(quicklist.has("text")&&!quicklist.getString("text").isBlank()&&quicklist.getString("text").length()<=2000){
                            canBeQuickList=true;
                        }
                    }
                }
                if(canBeQuickList){
                    embed.setDescription(quicklist.getString("text"));
                }else{
                    String text=gNewUserProfile.cGAG.getCustomGagTexAsString(key,page);
                    if(text==null||text.isBlank()){
                        embed.setDescription("<no text>");
                    }
                    else if(text.length()>2000){
                        embed.setDescription("<text too big>");
                    }
                    else{
                        embed.setDescription(text);
                    }
                }
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(canBeQuickList){
                    if(quicklist.optInt("index",-1)>=0)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));
                    if(quicklist.optInt("index",-1)>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                    if(quicklist.optInt("index",-1)>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                    if(quicklist.optInt("index",-1)>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                    if(quicklist.optInt("index",-1)>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));
                    if(quicklist.optInt("index",-1)>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));
                    if(quicklist.optInt("index",-1)>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));
                    if(quicklist.optInt("index",-1)>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                    if(quicklist.optInt("index",-1)>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                    if(quicklist.optInt("index",-1)>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                }else{
                    if(length>1){
                        if(page>0){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                        }
                        if(page<length-1){
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                        }
                    }
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                llMessageDelete(message);
                                int indexRemove=-1;
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuCustomSelected(key);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuCustomList(key,page-1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuCustomList(key,page+1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    indexRemove=page;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){
                                   indexRemove=0;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                                    indexRemove=1;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                                    indexRemove=2;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                                    indexRemove=3;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                                    indexRemove=4;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                                    indexRemove=5;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){
                                    indexRemove=6;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){
                                    indexRemove=7;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){
                                    indexRemove=8;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){
                                    indexRemove=9;
                                }
                                if(indexRemove>-1){
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                                            logger.info(fName + ".can't use do to access owner");
                                            sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                                            return;
                                        }
                                    }else{
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                                            logger.info(fName + ".can't use do to locked by not you");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                                            return;
                                        }else
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                                            logger.info(fName + ".can't use do to access protected");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                                        }
                                    }
                                    gNewUserProfile.cGAG.remCustomGagText(key,indexRemove);
                                    saveProfile();
                                    menuCustomList(key,0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private JSONObject listofPreviews2(JSONArray array){
            String fName="[listofPreviews]";
            logger.info(fName);
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("hitLimit",false);
                jsonObject.put("text","");
                jsonObject.put("index",-1);
                logger.info(fName+"array="+ array.toString());
                int i=0;String result="";
                String tmp1="";
                StringBuilder tmp2= new StringBuilder();
                while(i<array.length()&&tmp1.length()<2000){
                    tmp2 = new StringBuilder();
                    String[] items = array.getString(i).split("\\s+");
                    if(items.length==1){
                        if(tmp1.isBlank()){
                            tmp1+=i+". "+items[0];
                        }else{
                            tmp1+="\n"+i+". "+items[0];
                        }

                    }else{
                        int j=0;
                        int l=items.length;
                        if(l>3){
                            tmp2 = new StringBuilder(items[0] + " " + items[1] + " " + items[l - 1]);
                        }else{
                            while(j<l){
                                tmp2.append(items[j]).append(" ");
                                j++;
                            }
                        }
                        if(tmp1.isBlank()){
                            tmp1+=i+". "+ tmp2.toString().trim();
                        }else{
                            tmp1+="\n"+i+". "+ tmp2.toString().trim();
                        }
                    }

                    if(tmp1.length()<2000){
                        result=tmp1;
                        jsonObject.put("index", i);
                    }else{
                        jsonObject.put("hitLimit",true);
                        jsonObject.put("index", i);
                    }
                    i++;
                }
                jsonObject.put("text",result);
                logger.info(fName+"result="+jsonObject.toString());
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }

        private Boolean saveServerProfile(){
            String fName="[saveServerProfile]";
            logger.info(fName);
            gGlobal.putGuildSettings(gGuild,gBDSMCommands.muzzle.gProfile);
            if(gBDSMCommands.muzzle.gProfile.saveProfile()){
                logger.info(fName + ".success");return  true;
            }
            logger.warn(fName + ".failed");return false;
        }
        Boolean vEnabled=false; String vMode="";String vAlert="";JSONArray vBannedChannels=null;JSONArray vAllowedChannels=null;JSONArray vBannedUsers=null;
        private void loadValues(){
            String fName = "[loadValues]";
            try {
                logger.info(fName);
                logger.info(fName+"json="+gBDSMCommands.muzzle.gProfile.jsonObject.toString());
                vMode= gBDSMCommands.muzzle.gProfile.getFieldEntryAsString(fieldMode);
                vEnabled = gBDSMCommands.muzzle.gProfile.getFieldEntryAsBoolean(fieldEnabled);
                vAlert= gBDSMCommands.muzzle.gProfile.getFieldEntryAsString(fieldAlert);
                vBannedChannels= gBDSMCommands.muzzle.gProfile.getFieldEntryAsJSONArray(fieldBannedChannels);
                vBannedUsers= gBDSMCommands.muzzle.gProfile.getFieldEntryAsJSONArray(fieldBannedUsers);
                vAllowedChannels= gBDSMCommands.muzzle.gProfile.getFieldEntryAsJSONArray(fieldAllowedChannels);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void menuSetup(){
            String fName="[menuSetup]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="Select one option";
                if(vEnabled){
                    desc+="\n:one: to disable its effect (this will not prevent executing the command, just no gagging effect occurs)";
                }else{
                    desc+="\n:one: to enable its effect";
                }
                /*if(vMode.equals(valueMuzzleWhite)){
                    desc+="\n:two: to change mode "+vMode+" to "+valueMuzzleBlack;
                }else{
                    desc+="\n:two: to change mode "+vMode+" to "+valueMuzzleWhite;
                }*/
                desc+="\n:three: Clear channels list";
                desc+="\n:four: Clear banned users list";
                desc+="\n:six: Reset Restrictions";
                desc+="\n:seven: Server Side Custom Text 4 Gag Levels";
                embed.setTitle(strSetupTitle);embed.setColor(llColorPurple2);
                embed.setDescription(desc);
                embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).queue();
                //message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)).queue();
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldEnabled,!vEnabled);
                                    logger.info(fName+"json updated="+gBDSMCommands.muzzle.gProfile.jsonObject.toString());
                                    if(saveServerProfile()){
                                        String t;
                                        if(!vEnabled){
                                            t="enable";
                                        }else{
                                            t="disable";
                                        }
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Updated to "+t,llColorGreen2);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Failed to update",llColorRed_Imperial);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);String mode;
                                    if(vMode.equalsIgnoreCase(valueWhite)){
                                        mode= valueBlack;
                                    }else{
                                        mode= valueWhite;
                                    }
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldMode,mode);
                                    logger.info(fName+"json updated="+gBDSMCommands.muzzle.gProfile.jsonObject.toString());
                                    if(saveServerProfile()){
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Updated to "+mode,llColorGreen2);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Failed to update",llColorRed_Imperial);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldAllowedChannels,new JSONArray());
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldBannedChannels,new JSONArray());
                                    logger.info(fName+"json updated="+gBDSMCommands.muzzle.gProfile.jsonObject.toString());
                                    if(saveServerProfile()){
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Cleared channels list",llColorGreen2);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Failed to cleared channels list",llColorRed_Imperial);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldBannedUsers,new JSONArray());
                                    logger.info(fName+"json updated="+gBDSMCommands.muzzle.gProfile.jsonObject.toString());
                                    if(saveServerProfile()){
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Cleared forbidden users list",llColorGreen2);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Failed to cleared forbidden members list",llColorRed_Imperial);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    logger.warn(fName+"trigger="+name);
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldAllowedChannels,new JSONArray());
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldBannedChannels,new JSONArray());
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldBannedUsers,new JSONArray());
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldMode, valueWhite);
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldGagCommandAllowedRoles,new JSONArray());
                                    gBDSMCommands.muzzle.gProfile.putFieldEntry(fieldGagTargetAllowedRoles,new JSONArray());
                                    logger.info(fName+"json updated="+gBDSMCommands.muzzle.gProfile.jsonObject.toString());
                                    if(saveServerProfile()){
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Reset restrictions done",llColorGreen2);
                                    }else{
                                        llSendQuickEmbedMessage(gUser,strSetupTitle,"Failed to clear restrictions",llColorRed_Imperial);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    logger.warn(fName+"trigger="+name);
                                    menuCustomGag4Server();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    logger.warn(fName+"trigger="+name);
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                }else{
                                    menuSetup();
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessage(gUser,strSetupTitle,"Exception:"+e.toString(), llColorRed_Imperial);
            }
        }

        private void setupAdd2Channel(boolean isAllowed){
            String fName="[setupAdd2Channel]";
            logger.info(fName);
            try{
                List<TextChannel>channels=gCommandEvent.getMessage().getMentionedChannels();
                JSONArray array;String field="";
                if(isAllowed){
                    field= fieldAllowedChannels;
                }else{
                    field= fieldBannedChannels;
                }

                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has( field)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull( field)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put( field,new JSONArray());
                }
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray( field);
                for(TextChannel channel:channels){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(channel.getId())){
                            has=true;
                        }
                        i++;
                    }
                    if(!has){
                        array.put(channel.getId());
                    }
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put( field,array);gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Updated channels list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to update channels list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupRem4Channel(boolean isAllowed){
            String fName="[setupRem4Channel]";
            logger.info(fName);
            try{
                List<TextChannel>channels=gCommandEvent.getMessage().getMentionedChannels();
                JSONArray array;String field="";
                if(isAllowed){
                    field= fieldAllowedChannels;
                }else{
                    field= fieldBannedChannels;
                }

                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has( field)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull( field)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put( field,new JSONArray());
                }
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray( field);
                for(TextChannel channel:channels){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(channel.getId())){
                            has=true;
                        }else{
                            i++;
                        }
                    }
                    if(has){
                        array.remove(i);
                    }
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put( field,array);gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Updated channels list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to update channels list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupClear4Channel(boolean isAllowed){
            String fName="[setupClear4Channel]";
            logger.info(fName);
            try{
                String field="";
                if(isAllowed){
                    field= fieldAllowedChannels;
                }else{
                    field= fieldBannedChannels;
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put( field,new JSONArray());gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Cleared channels list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to clear channels list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupAdd2Member(){
            String fName="[setupAdd2Member]";
            logger.info(fName);
            try{
                List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
                JSONArray array;
                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has(fieldBannedUsers)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull(fieldBannedUsers)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put(fieldBannedUsers,new JSONArray());
                }
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray(fieldBannedUsers);
                for(Member cmember:members){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(cmember.getId())){
                            has=true;
                        }
                        i++;
                    }
                    if(!has){
                        array.put(cmember.getId());
                    }
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put(fieldBannedUsers,array);gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Updated members list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to update members list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupRem4Member(){
            String fName="[setupRem4Member]";
            logger.info(fName);
            try{
                List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
                JSONArray array;
                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has(fieldBannedUsers)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull(fieldBannedUsers)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put(fieldBannedUsers,new JSONArray());
                }
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray(fieldBannedUsers);
                for(Member cmember:members){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(cmember.getId())){
                            has=true;
                        }else{
                            i++;
                        }
                    }
                    if(has){
                        array.remove(i);
                    }
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put(fieldBannedUsers,array);gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Updated members list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to update members list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupClear4Member(){
            String fName="[setupClear4Member]";
            logger.info(fName);
            try{
                gBDSMCommands.muzzle.gProfile.jsonObject.put(fieldBannedUsers,new JSONArray());gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Cleared members list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to clear members list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupViewList(String field){
            String fName="[setupViewList]";
            logger.info(fName);
            try{
                logger.info(fName+"field="+field);
                JSONArray array;
                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has( field)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull( field)){
                    logger.info(fName+"null");
                    if(field.equalsIgnoreCase(fieldAllowedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in allowed channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in blocked channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedUsers))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in blocked users list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldGagCommandAllowedRoles))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in command allowed roles list! Everyone can use the gag commands.", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldGagTargetAllowedRoles))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in target allowed roles list! Everyone can get targeted.", llColorRed_Imperial);
                    return;
                }
                logger.info(fName+"step 2");
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray( field);
                if(array.isEmpty()){
                    logger.info(fName+"empty");
                    if(field.equalsIgnoreCase(fieldAllowedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in allowed channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedChannels))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in blocked channels list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldBannedUsers))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in blocked users list!", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldGagCommandAllowedRoles))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in command allowed roles list! Everyone can use the gag commands.", llColorRed_Imperial);
                    if(field.equalsIgnoreCase(fieldGagTargetAllowedRoles))
                        llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Nothing in target allowed roles list! Everyone can get targeted.", llColorRed_Imperial);
                    return;
                }
                StringBuilder tmp= new StringBuilder();
                for(int i=0;i<array.length();i++){
                    if(field.equalsIgnoreCase(fieldAllowedChannels))
                        tmp.append(" ").append(lsChannelHelper.lsGetChannelMentionById(gGuild, array.getString(i)));
                    if(field.equalsIgnoreCase(fieldBannedChannels))
                        tmp.append(" ").append(lsChannelHelper.lsGetChannelMentionById(gGuild, array.getString(i)));
                    if(field.equalsIgnoreCase(fieldBannedUsers))
                        tmp.append(" ").append(llGetMemberMention(gGuild, array.getString(i)));
                    if(field.equalsIgnoreCase(fieldGagCommandAllowedRoles))
                        tmp.append(" ").append(lsRoleHelper.lsGetRoleMentionByID(gGuild, array.getString(i)));
                    if(field.equalsIgnoreCase(fieldGagTargetAllowedRoles))
                        tmp.append(" ").append(lsRoleHelper.lsGetRoleMentionByID(gGuild, array.getString(i)));
                }
                if(field.equalsIgnoreCase(fieldAllowedChannels))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Allowed channels list: "+tmp, llColorBlue3);
                if(field.equalsIgnoreCase(fieldBannedChannels))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Blocked channels list: "+tmp, llColorBlue3);
                if(field.equalsIgnoreCase(fieldBannedUsers))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Blocked users list: "+tmp, llColorBlue3);
                if(field.equalsIgnoreCase(fieldGagCommandAllowedRoles))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Allowed command roles: "+tmp, llColorBlue3);
                if(field.equalsIgnoreCase(fieldGagTargetAllowedRoles))
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Allowed target roles: "+tmp, llColorBlue3);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupAdd2Role(String field){
            String fName="[setupAdd2Role]";
            logger.info(fName);
            try{
                logger.info(fName+" field="+ field);
                List<Role>roles=gCommandEvent.getMessage().getMentionedRoles();
                JSONArray array;
                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has(  field)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull(  field)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put(  field,new JSONArray());
                }
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray(  field);
                for(Role role:roles){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(role.getId())){
                            has=true;
                        }
                        i++;
                    }
                    if(!has){
                        array.put(role.getId());
                    }
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put( field,array);gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Updated roles list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to update roles list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupRem4Role(String field){
            String fName="[setupRem4Role]";
            logger.info(fName);
            try{
                logger.info(fName+" field="+ field);
                List<Role>roles=gCommandEvent.getMessage().getMentionedRoles();
                JSONArray array;
                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has( field)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull(field)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put( field,new JSONArray());
                }
                array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONArray( field);
                for(Role role:roles){
                    boolean has=false;int i=0;
                    while(i<array.length()&&!has){
                        if(array.getString(i).equalsIgnoreCase(role.getId())){
                            has=true;
                        }else{
                            i++;
                        }
                    }
                    if(has){
                        array.remove(i);
                    }
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put( field,array);gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Updated roles list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to update roles list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void setupClear4Role(String field){
            String fName="[setupClear4Role]";
            logger.info(fName);
            try{
                logger.info(fName+" field="+ field);
                if(!gBDSMCommands.muzzle.gProfile.jsonObject.has( field)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull(field)){
                    gBDSMCommands.muzzle.gProfile.jsonObject.put( field,new JSONArray());
                }
                gBDSMCommands.muzzle.gProfile.jsonObject.put( field,new JSONArray());gBDSMCommands.muzzle.gProfile.isUpdated=true;
                if(saveServerProfile()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Cleared roles list", llColorGreen2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel,strSetupTitle,"Failed to clear roles list", llColorRed_Imperial);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,strSetupTitle,e.toString());
            }
        }
        private void voiceChannelRestriction(int action){
            String fName = "[voiceChannelRestriction]";
            try {
                new rdVoicePostSynthesizer(gGlobal,gNewUserProfile.gUserProfile.getMember().getVoiceState());
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void rGagTraining() {
            String fName = "[rGagTraining]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                return;
            }
            menuGagTraining();
        }
        private void rGagTraining(Member mtarget) {
            String fName = "[rGagTraining]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
            }
            menuGagTraining();
        }
        private void menuGagTraining(){
            String fName="[menuGagTraining]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="Select one:";
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                int length=gNewUserProfile.cGAG.getTrainingGagLength();
                if(isPatreon){
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle("Training gag "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s Training gag "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }
                }else{
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle("Training gag "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s Training gag "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }
                }
                if(length>0)desc+="\n:one: list entries";
                if((isPatreon&&length<patreonLimit4CustomText)||length<normalLimit4CustomText)desc+="\n:two: add an entry";
                //desc+="\n:three: edit an entry";
                if(length>0) {
                    desc += "\n:three: clear all entries";
                }
                embed.addField("Selecting a gag level",desc,false);
                embed.addField("Limitations","Max 10 entries + 30 more for Patreon tier 2+.",false);
                try {
                    logger.info(fName + ".has flagOnlyUseCustom");
                    if(gNewUserProfile.cGAG.isFlagOnlyUseCustom()){
                        embed.addField("Only custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to disable.",false);
                    }else{
                        embed.addField("Only custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.\nIf that gag level has custom entries, it will use only that.",false);
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                //embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(length>0)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if((isPatreon&&length<patreonLimit4CustomText)||length<normalLimit4CustomText)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(length>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    if(gNewUserProfile.gUserProfile.getUser().getIdLong()==gUser.getIdLong()){
                                        menuMain(null);
                                    }else{
                                        menuMain(gNewUserProfile.gUserProfile.getMember());
                                    }
                                    return;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    menuList4GagTraining(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    menuAddText4GagTraining_1();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                                            logger.info(fName + ".can't use do to access owner");
                                            sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                                            return;
                                        }
                                    }else{
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                                            logger.info(fName + ".can't use do to locked by not you");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                                            return;
                                        }else
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                                            logger.info(fName + ".can't use do to access protected");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                                        }
                                    }
                                    gNewUserProfile.cGAG.setTrainingGagEntries(new JSONArray());
                                    saveProfile();
                                    menuCustomGag();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    logger.warn(fName+"trigger="+name);

                                }else{
                                    menuGagTraining();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuAddText4GagTraining_1(){
            String fName="[menuAddText4GagTraining_1]";
            logger.info(fName);
            try{
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".can't use do to access owner");
                        sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                        return;
                    }
                }else{
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                        logger.info(fName + ".can't use do to locked by not you");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                        return;
                    }else
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                        logger.info(fName + ".can't use do to access protected");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                    }
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Training gag");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Training gag");
                }
                embed.setDescription("Please enter the text you want to chang from. Once entered will ask to change to. Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuGagTraining();
                                }else
                                if(content.isBlank()){
                                    menuGagTraining();
                                }else{
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put(nFrom,content);
                                    menuAddText4GagTraining_2(jsonObject);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuAddText4GagTraining_2(JSONObject jsonObject){
            String fName="[menuAddText4GagTraining_2]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Training gag");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Training gag");
                }
                embed.setDescription("Please enter the text you want to chang to. Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuGagTraining(); llMessageDelete(message);
                                }else
                                if(content.isBlank()){
                                    menuGagTraining(); llMessageDelete(message);
                                }else{
                                    jsonObject.put(nTo,content);
                                    if(gNewUserProfile.cGAG.addTrainingGagEntries(jsonObject)!=null){
                                        saveProfile();
                                        embed.setDescription("Added new entry");
                                        lsMessageHelper.lsEditMessage(message,embed);
                                        menuGagTraining();
                                    }else{
                                        embed.setDescription("Failed to add!");
                                        lsMessageHelper.lsEditMessage(message,embed);
                                        menuGagTraining();
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuList4GagTraining(int page){
            String fName="[menuList4GagTraining]";
            logger.info(fName);
            try{
                logger.info(fName+"page="+page);
                int length=gNewUserProfile.cGAG.getTrainingGagLength();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Training Gag");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Training Gag");
                }
                embed.addField("List","size: "+length+"",false);
                String textFrom=gNewUserProfile.cGAG.getFromText4TrainingGag(page);
                String textTo=gNewUserProfile.cGAG.getToText4TrainingGag(page);
                if(textFrom==null||textFrom.isBlank()){
                    embed.addField("Text to change","<no text>",false);
                }
                else if(textFrom.length()>2000){
                    embed.addField("Text to change","<text too big>",false);
                }
                else{
                    embed.addField("Text to change",textFrom,false);
                }
                if(textTo==null||textTo.isBlank()){
                    embed.addField("Text to change to","<no text>",false);
                }
                else if(textTo.length()>2000){
                    embed.addField("Text to change to","<text too big>",false);
                }
                else{
                    embed.addField("Text to change to",textTo,false);
                }
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(length>1){
                    if(page>0){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                    }
                    if(page<length-1){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                    }
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="";
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuGagTraining();
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuList4GagTraining(page-1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuList4GagTraining(page+1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                                            logger.info(fName + ".can't use do to access owner");
                                            sendOrUpdatePrivateEmbed(sRTitle,cantmanipulatedueaccessset2owner, llColorRed);
                                            return;
                                        }
                                    }else{
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                                            logger.info(fName + ".can't use do to locked by not you");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                                            return;
                                        }else
                                        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                                            logger.info(fName + ".can't use do to access protected");
                                            sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their gag due to their access setting.", llColorRed);return;
                                        }
                                    }
                                    gNewUserProfile.cGAG.remTrainingGagEntries(page);
                                    saveProfile();
                                    menuList4GagTraining(0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }

        private void menuCustomGag4Server(){
            String fName="[menuMain]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setTitle("Server Custom Text 4 GagLevels");
                embed.setColor(llColorBlue1);
                String desc="Select one:";
                desc+="\n:regional_indicator_a: for "+GAGLEVELS.NucleusMask.getName();
                desc+="\n:regional_indicator_b: for "+GAGLEVELS.DroneMask.getName();
                desc+="\n:regional_indicator_t: for "+GAGLEVELS.Toy.getName();
                desc+="\n:one: for "+GAGLEVELS.Puppy.getName();
                desc+="\n:two: for "+GAGLEVELS.Kitty.getName();
                desc+="\n:three: for "+GAGLEVELS.Pony.getName();
                desc+="\n:four: for "+GAGLEVELS.Piggy.getName();
                desc+="\n:five: for "+GAGLEVELS.Dinosaur.getName();
                desc+="\n:six: for "+GAGLEVELS.Squeaky.getName();
                desc+="\n:seven: for "+GAGLEVELS.Sheep.getName();
                desc+="\n:eight: for "+GAGLEVELS.Bird.getName();
                desc+="\n:nine: for "+GAGLEVELS.Cow.getName();
                embed.addField("Selecting a gag level",desc,false);
                embed.addField("Limitations","Each level has "+normalLimit4CustomText+" available.",false);
                if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagEnableCustom)){
                    try {
                        logger.info(fName + ".has flagEnableCustom");
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagEnableCustom)){
                            embed.addField("Enable custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to disable.",false);
                        }else{
                            embed.addField("Enable custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.\nIf that gag level has custom entries, it will use only that.",false);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagOnlyUseCustom)){
                    try {
                        logger.info(fName + ".has flagOnlyUseCustom");
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagOnlyUseCustom)){
                            embed.addField("Only custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" to disable.",false);
                        }else{
                            embed.addField("Only custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" to enable.\nIf that gag level has custom entries (guild&user), it will use only that.",false);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagOnlyGuildCustom)){
                    try {
                        logger.info(fName + ".has flagOnlyUseCustom");
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagOnlyGuildCustom)){
                            embed.addField("Only guild custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.",false);
                        }else{
                            embed.addField("Only guild custom text","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable.\nIf that gag level has guild custom entries, it will use only that.",false);
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                embed.addField("Up","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" to move up from sub-command.",false);
                //embed.addField("Done","Select :white_check_mark: to finish.",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)).queue();
                //message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)).queue();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)).queue();
                if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagEnableCustom)){
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)).queue();
                    if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagEnableCustom)){
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagOnlyUseCustom)){
                            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)).queue();
                        }
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagOnlyGuildCustom)){
                            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)).queue();
                        }
                    }
                }
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuSetup();
                                    return;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                    logger.warn(fName+"trigger="+name);
                                    menuOnlyCustomGagOption4Server(flagEnableCustom,"toggle");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){
                                    logger.warn(fName+"trigger="+name);
                                    menuOnlyCustomGagOption4Server(flagOnlyUseCustom,"toggle");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                    logger.warn(fName+"trigger="+name);
                                    menuOnlyCustomGagOption4Server(flagOnlyGuildCustom,"toggle");
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Puppy.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Kitty.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Pony.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Piggy.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Dinosaur.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Squeaky.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Sheep.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    logger.warn(fName+"trigger="+name);
                                    menuCustomSelected(GAGLEVELS.Bird.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Cow.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.NucleusMask.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.DroneMask.getName());
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(GAGLEVELS.Toy.getName());
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);
                                    //llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                }else{
                                    menuCustomGag4Server();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },2, TimeUnit.MINUTES, () -> {
                            //sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuOnlyCustomGagOption4Server(String command,String option){
            String fName="[menuOnlyCustomGagOption]";
            logger.info(fName);
            try{
                logger.info(fName+"command="+ command+" ,option="+option);
                if(command.equalsIgnoreCase(flagEnableCustom)){
                    if(option.equalsIgnoreCase("toggle")){
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagEnableCustom)){
                            try {
                                logger.info(fName + ".has flagEnableCustom");
                                if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagEnableCustom)){
                                    option="false";
                                }else{
                                    option="true";
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        logger.info(fName+"option after toggle="+option);
                    }
                    if(option.equalsIgnoreCase(vTrue)||option.equalsIgnoreCase(vOn)){
                        gBDSMCommands.muzzle.gProfile.jsonObject.put(flagEnableCustom,true);
                        sendOrUpdatePrivateEmbed(sRTitle,"Enabled to use guild custom text.", llColorPurple2);
                    }else if(option.equalsIgnoreCase(vFalse)||option.equalsIgnoreCase(vOff)){
                        gBDSMCommands.muzzle.gProfile.jsonObject.put(flagEnableCustom,false);
                        sendOrUpdatePrivateEmbed(sRTitle,"Disabled to use guild custom text.", llColorPurple2);
                    }
                }
                else if(command.equalsIgnoreCase(flagOnlyUseCustom)){
                    if(option.equalsIgnoreCase("toggle")){
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagOnlyUseCustom)){
                            try {
                                logger.info(fName + ".has flagOnlyUseCustom");
                                if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagOnlyUseCustom)){
                                    option="false";
                                }else{
                                    option="true";
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        logger.info(fName+"option after toggle="+option);
                    }
                    if(option.equalsIgnoreCase(vTrue)||option.equalsIgnoreCase(vOn)){
                        gBDSMCommands.muzzle.gProfile.jsonObject.put(flagOnlyUseCustom,true);
                        sendOrUpdatePrivateEmbed(sRTitle,"Enabled to only use custom text.", llColorPurple2);
                    }else if(option.equalsIgnoreCase(vFalse)||option.equalsIgnoreCase(vOff)){
                        gBDSMCommands.muzzle.gProfile.jsonObject.put(flagOnlyUseCustom,false);
                        sendOrUpdatePrivateEmbed(sRTitle,"Disabled to only use custom text.", llColorPurple2);
                    }
                }
                else if(command.equalsIgnoreCase(flagOnlyGuildCustom)){
                    if(option.equalsIgnoreCase("toggle")){
                        if(gBDSMCommands.muzzle.gProfile.jsonObject.has(flagOnlyGuildCustom)){
                            try {
                                logger.info(fName + ".has flagOnlyGuildCustom");
                                if(gBDSMCommands.muzzle.gProfile.jsonObject.getBoolean(flagOnlyGuildCustom)){
                                    option="false";
                                }else{
                                    option="true";
                                }
                            }catch (Exception e){
                                logger.error(fName + ".exception=" + e);
                                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                            }
                        }
                        logger.info(fName+"option after toggle="+option);
                    }
                    if(option.equalsIgnoreCase(vTrue)||option.equalsIgnoreCase(vOn)){
                        gBDSMCommands.muzzle.gProfile.jsonObject.put(flagOnlyGuildCustom,true);
                        sendOrUpdatePrivateEmbed(sRTitle,"Enabled to only use guild custom text.", llColorPurple2);
                    }else if(option.equalsIgnoreCase(vFalse)||option.equalsIgnoreCase(vOff)){
                        gBDSMCommands.muzzle.gProfile.jsonObject.put(flagOnlyGuildCustom,false);
                        sendOrUpdatePrivateEmbed(sRTitle,"Disabled to only use guild custom text.", llColorPurple2);
                    }
                }


                gBDSMCommands.muzzle.save();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private JSONArray getCustomTextArray4Server(String key){
            String fName="[getCustomTextArray4Server]";
            logger.info(fName);
            try{
                JSONArray array=new JSONArray();
                if(gBDSMCommands.muzzle.gProfile.jsonObject.has(nCustomGagText)&&!gBDSMCommands.muzzle.gProfile.jsonObject.isNull(nCustomGagText)&&gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).has(key)&&!gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).isNull(key)){
                    array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).getJSONArray(key);
                }
                return array;

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONArray();
            }
        }

        boolean isPatreon=false;

        private void menuSelected4Server(String key){
            String fName="[menuSelected]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                JSONArray array=getCustomTextArray4Server(key);
                String desc="";
                if(!array.isEmpty()) {
                    desc += "\n:one: for list";
                }
                desc+="\n:two: for adding new line";
                if(!array.isEmpty()){
                    desc+="\n:three: for removing";
                    desc+="\n:four: to clear";
                    desc+="\n:five: to view";
                }
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                embed.addField("Gag Level",key,false);
                if(isPatreon){
                    embed.setTitle("Server Custom Text 4 GagLevels "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    embed.addField("Lines used/total:", array.length() +"/"+(patreonLimit4CustomText-normalLimit4CustomText),false);
                }else{
                    embed.setTitle("Server Custom Text 4 GagLevels "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    embed.addField("Lines used/total:", array.length() +"/"+normalLimit4CustomText,false);
                }

                embed.addField("Options",desc,false);
                embed.addField("Formatting","Use the world `!name` to show your nickname that is currently in use.\nUse `!slave` to show your slave number.",false);
                embed.addField("Done","Select :white_check_mark: to finish & go back",false);
                //embed.addField("Close","Select :x: to finish.",false);
                Message message=llSendMessageResponse(gUser,embed);
                if(!array.isEmpty()) {
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)).queue();
                }
                if((isPatreon&&array.length()<patreonLimit4CustomText)||array.length()<normalLimit4CustomText){
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)).queue();
                }
                if(!array.isEmpty()){
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)).queue();
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)).queue();
                    message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)).queue();
                }
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    logger.warn(fName+"trigger="+name);
                                    menuList4Server(key);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    logger.warn(fName+"trigger="+name);
                                    if((isPatreon&&array.length()<patreonLimit4CustomText)||array.length()<normalLimit4CustomText){
                                        menuAddText4Server(key);
                                    }else{
                                        menuSelected4Server(key);
                                    }
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    logger.warn(fName+"trigger="+name);
                                    menuRemoveText4Server(key);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    logger.warn(fName+"trigger="+name);
                                    gBDSMCommands.muzzle.gProfile.jsonObject.put(nCustomGagText,gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).put(key,new JSONArray()));
                                    menuCustomGag4Server();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    logger.warn(fName+"trigger="+name);
                                    menuViewText4Server(key,false);
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    logger.warn(fName+"trigger="+name);
                                    menuCustomGag4Server();
                                }else{
                                    menuCustomSelected(key);
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuList4Server(String key){
            String fName="[menuList]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                JSONArray array=getCustomTextArray4Server(key);
                StringBuilder desc= new StringBuilder();
                embed.setTitle("Server Custom Text 4 GagLevels");
                embed.addField(strSupportTitle,strSupport,false);
                embed.addField("Gag Level",key,false);
                for(int i=0;i<array.length();i++){
                    desc.append("\n").append(i).append(". ").append(array.getString(i));
                }
                embed.addField("Lines", desc.toString(),false);
                embed.addField("Done","Select :white_check_mark: to go back",false);
                Message message=llSendMessageResponse(gUser,embed);
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);
                                    menuSelected4Server(key);
                                }else{
                                    menuSelected4Server(key);
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuAddText4Server(String key){
            String fName="[menuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("Server Custom Text 4 GagLevels");
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuSelected4Server(key); llMessageDelete(message);
                                }else
                                if(content.isBlank()){
                                    menuAddText4Server(key); llMessageDelete(message);
                                }else{
                                    if(!gBDSMCommands.muzzle.gProfile.jsonObject.has(nCustomGagText)||gBDSMCommands.muzzle.gProfile.jsonObject.isNull(nCustomGagText)){
                                        gBDSMCommands.muzzle.gProfile.jsonObject.put(nCustomGagText,new JSONObject());
                                    }
                                    if(gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).has(key)||gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).isNull(key)){
                                        gBDSMCommands.muzzle.gProfile.jsonObject.put(nCustomGagText,gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).put(key,new JSONArray()));
                                    }
                                    JSONArray array=gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).getJSONArray(key);
                                    array.put(content);
                                    gBDSMCommands.muzzle.gProfile.jsonObject.put(nCustomGagText,gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).put(key,array));
                                    gBDSMCommands.muzzle.save();
                                    embed.setDescription("Added entry.");
                                    lsMessageHelper.lsEditMessage(message,embed);
                                    menuSelected4Server(key);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private String listofPreviews(JSONArray array){
            String fName="[listofPreviews]";
            logger.info(fName);
            try{
                logger.info(fName+"array="+ array.toString());
                int i=0;String result="";
                String tmp1="";
                StringBuilder tmp2= new StringBuilder();
                while(i<array.length()&&tmp1.length()<2000){
                    String[] items = array.getString(i).split("\\s+");
                    if(items.length==1){
                        if(tmp1.isBlank()){
                            tmp1+=i+". "+items[0];
                        }else{
                            tmp1+="\n"+i+". "+items[0];
                        }

                    }else{
                        int j=0;
                        while(j<items.length&&j<3){
                            tmp2.append(items[j]).append(" ");
                            j++;
                        }
                        if(tmp1.isBlank()){
                            tmp1+=i+". "+ tmp2.toString().trim();
                        }else{
                            tmp1+="\n"+i+". "+ tmp2.toString().trim();
                        }
                    }

                    if(tmp1.length()<2000){
                        result=tmp1;
                    }
                    i++;
                }
                logger.info(fName+"result="+result);
                return  result;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        private void menuRemoveText4Server(String key){
            String fName="[menuRemoveText]";
            logger.info(fName);
            try{
                JSONArray finalArray = getCustomTextArray4Server(key);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("Server Custom Text 4 GagLevels");
                embed.addField("How to?","Please enter nr of line between 0-"+(finalArray.length()-1)+" you want to delete.Type '!cancel' to abort.",false);
                embed.setDescription("Preview:\n"+listofPreviews(finalArray));
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuSelected4Server(key); llMessageDelete(message);
                                }else
                                if(content.isBlank()){
                                    menuRemoveText4Server(key); llMessageDelete(message);
                                }else{
                                    int i=Integer.parseInt(content);
                                    if(i>=0&&i< finalArray.length()){
                                        finalArray.remove(i);
                                        gBDSMCommands.muzzle.gProfile.jsonObject.put(nCustomGagText,gBDSMCommands.muzzle.gProfile.jsonObject.getJSONObject(nCustomGagText).put(key,finalArray));
                                        gBDSMCommands.muzzle.save();
                                        menuSelected4Server(key);
                                        embed.setDescription("Removed entry.");
                                        lsMessageHelper.lsEditMessage(message,embed);
                                    }else{
                                        menuRemoveText4Server(key); llMessageDelete(message);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuViewText4Server(String key,boolean isPreview){
            String fName="[menuViewText]";
            logger.info(fName);
            try{
                JSONArray finalArray = getCustomTextArray4Server(key);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                embed.setTitle("Server Custom Text 4 GagLevels");
                if(isPreview){
                    embed.addField("How to?","Please enter nr of line between 0-"+(finalArray.length()-1)+" to preview it.Type '!cancel' to abort.",false);
                    embed.setDescription("Preview:\n"+listofPreviews(finalArray));
                }else{
                    embed.addField("How to?","Please enter nr of line between 0-"+(finalArray.length()-1)+" to view it.Type '!cancel' to abort.",false);
                    embed.setDescription("Preview:\n"+listofPreviews(finalArray));
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuSelected4Server(key);
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".is blank");
                                    menuViewText4Server(key,isPreview);
                                }else{
                                    logger.info(fName+".content="+content);
                                    int i=Integer.parseInt(content);
                                    logger.info(fName+".i="+i);
                                    if(i>=0&&i< finalArray.length()){
                                        String text=finalArray.getString(i);
                                        menuGenerateViewText4Server(key,text,isPreview);
                                    }else{
                                        menuViewText4Server(key,isPreview);
                                    }
                                }
                                llMessageDelete(message);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuGenerateViewText4Server(String key,String text,boolean isPreview){
            String fName="[menuGenerateViewText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Custom Text 4 GagLevels");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Custom Text 4 GagLevels");
                }
                if(isPreview){
                    String name=gNewUserProfile.gUserProfile.getMember().getEffectiveName();
                    JSONObject rpspeach;
                    if (gNewUserProfile.gUserProfile.jsonObject.has(nRPSpeach)&&!gNewUserProfile.gUserProfile.jsonObject.isNull(nRPSpeach)) {
                        try {
                            rpspeach = gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nRPSpeach);
                            if (rpspeach.has(nOn)) {
                                if(rpspeach.getBoolean(nOn)){
                                    if (rpspeach.has(nName)) {
                                        name=rpspeach.getString(nName);
                                    }
                                }
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    if(gNewUserProfile.gUserProfile.jsonObject.has(nNameTag)){
                        try {
                            logger.info(fName + ".has nametag field:"+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).toString());
                            boolean isNameTagOn=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getBoolean(nOn);
                            logger.info(fName + ".isNameTagOn="+isNameTagOn);
                            if(isNameTagOn){
                                boolean hasHardName=false,hasSoftName=false;
                                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).has(nNewName)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).isNull(nNewName)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getString(nNewName).isEmpty()&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getString(nNewName).isBlank()){
                                    name=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getString(nNewName);
                                }
                                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).has(nSoftName)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).isNull(nSoftName)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getString(nSoftName).isEmpty()&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getString(nSoftName).isBlank()){
                                    name=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nNameTag).getString(nSoftName);
                                }
                                logger.info(fName + ".hasSoftName="+hasSoftName+", hasHardName="+hasHardName);
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    text=text.replaceAll("!name",name).replaceAll("!slave", rSlaveRegistry.iGenerateSlaveNumber(gNewUserProfile.gUserProfile.getUser()));
                    embed.setDescription("Preview text:\n"+text);
                }else{
                    embed.setDescription("View text:\n"+text);
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                menuViewText4Server(key,isPreview);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }


}}
