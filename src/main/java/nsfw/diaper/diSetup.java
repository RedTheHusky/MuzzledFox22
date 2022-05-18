package nsfw.diaper;
//implemented Runnable
//temporary removed some elements, needs fixiing

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ll.llNetworkHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
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
import nsfw.diaper.modules.dExtension;
import nsfw.diaper.modules.entities.entityCustomActions;
import nsfw.diaper.modules.interfaces.iDiaperInteractive;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;
import restraints.rdRestrictions;

import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class diSetup extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, llNetworkHelper, iDiaperInteractive {
        Logger logger = Logger.getLogger(getClass()); String cName="[diaperInteractive2]";
        lcGlobalHelper gGlobal;
        JSONObject rfEntries;
    public diSetup(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Setup_InteractiveDiaper";
        this.help = "Diaper fun module";
        this.aliases = new String[]{"diSetup"};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llCommandCategory_NSFW;this.hidden=true;
    }
    public diSetup(lcGlobalHelper global,CommandEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    public diSetup(lcGlobalHelper global,CommandEvent event,String forward){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event,forward);
        new Thread(r).start();
    }
    public diSetup(lcGlobalHelper global,CommandEvent event,String forward,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event,forward,target);
        new Thread(r).start();
    }
    public diSetup(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public diSetup(lcGlobalHelper global, InteractionHook interactionHook, String forward){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward);
        new Thread(r).start();
    }
    public diSetup(lcGlobalHelper global, InteractionHook interactionHook,String forward,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,forward,target);
        new Thread(r).start();
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

protected class runLocal extends dExtension implements Runnable {
    String cName = "[runLocal]";
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
        String fName = "[run]";
        loggerExt.info(".run start");
        try {
            if(gCurrentInteractionHook!=null){
                logger.info(cName + fName + "InteractionHook@");
                rInteraction();
            }else
            if(gSlashCommandEvent!=null){
                logger.info(cName + fName + "slash@");
                rSlashNT();
            }else{
                boolean isInvalidCommand = true;
                if(gForward!=null&&gIsForward){
                    if(gForward.isBlank()){
                        rHelp("main");isInvalidCommand = false;
                    }else{
                        gItems = gForward.split("\\s+");
                        if(gItems.length==0){rHelp("main");isInvalidCommand = false;}
                        else if(gItems[0].equalsIgnoreCase("set")&&gItems.length >= 4){
                            setField(gItems[1],gItems[2],gItems[3]);isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("help")){
                            if(gItems.length>=2&&(gItems[1].equalsIgnoreCase("main")||gItems[1].equalsIgnoreCase("setup")||gItems[1].equalsIgnoreCase("caretaker"))){
                                rHelp(gItems[1]);isInvalidCommand = false;
                            }
                            else{
                                rHelp("setup");
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("setup")){
                            if(gItems.length>=2){
                                if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                                    if(gItems[1].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Private.getString())){
                                        rAccess(gTarget,gItems[1]);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("wetenable")){
                                        setupWetEnable(gTarget,true);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("wetdisable")){
                                        setupWetEnable(gTarget,false);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("messenable")){
                                        setupMessEnable(gTarget,true);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("messdisable")){
                                        setupMessEnable(gTarget,false);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("wetchance")){
                                        if(gItems.length>=3){
                                            setupWetChance(gTarget,gItems[2]);isInvalidCommand = false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase("messchance")){
                                        if(gItems.length>=3){
                                            setupMessChance(gTarget,gItems[2]);isInvalidCommand = false;
                                        }
                                    }
                                }else{
                                    if(gItems[1].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Private.getString())){
                                        rAccess(gItems[1]);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("wetenable")){
                                        setupWetEnable(true);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("wetdisable")){
                                        setupWetEnable(false);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("messenable")){
                                        setupMessEnable(true);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("messdisable")){
                                        setupMessEnable(false);isInvalidCommand = false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase("wetchance")){
                                        if(gItems.length>=3){
                                            setupWetChance(gItems[2]);isInvalidCommand = false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase("messchance")){
                                        if(gItems.length>=3){
                                            setupMessChance(gItems[2]);isInvalidCommand = false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase("runaway")){
                                        rRunAway();isInvalidCommand = false;
                                    }
                                }
                                
                            }
                            if(isInvalidCommand){
                                menuSetup();isInvalidCommand = false;
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("lock")){
                            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                                rLock(gTarget,"lock");
                            }else{
                                rLock("lock");
                            }
                            isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("unlock")){
                            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                                rLock(gTarget,"unlock");
                            }else{
                                rLock("unlock");
                            }
                            isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("runaway")){
                            rRunAway();isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("owner")||gItems[0].equalsIgnoreCase("caretaker")){
                            if(gItems.length<2){
                                loggerExt.warn(fName+".invalid args length");
                                rHelp("owner");isInvalidCommand=false;
                            }else{
                                if(gItems[1].equalsIgnoreCase("add")){
                                    addOwner();isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("remove")){
                                    removeOwner();isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("runaway")){
                                    rRunAway();isInvalidCommand=false;
                                }
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("access")){
                            if(gItems.length<2){
                                loggerExt.warn(fName+".invalid args length");
                                rHelp("access");isInvalidCommand=false;
                            }else{
                                if(gItems[1].equalsIgnoreCase(ACCESSLEVEL.Private.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Protected.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Exposed.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Key.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Ask.getString())){
                                    if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                                        rAccess(gTarget,gItems[1].toLowerCase());
                                    }else{
                                        rAccess(gItems[1].toLowerCase());
                                    }
                                   isInvalidCommand=false;
                                }
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("actions")){
                            menuCustomActions();isInvalidCommand = false;
                        }
                    }

                }else
                if(gCommandEvent.getArgs().isEmpty()){
                    loggerExt.info(fName+".Args=0");
                    rHelp("main"); isInvalidCommand=false;
                    //gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();
                }else {
                    loggerExt.info(fName + ".Args");
                    gItems = gCommandEvent.getArgs().split("\\s+");
                    if(gCommandEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                    loggerExt.info(fName + ".items.size=" + gItems.length);
                    loggerExt.info(fName + ".items[0]=" + gItems[0]);
                    if(gItems[0].equalsIgnoreCase("guild")&&(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGECHANNEL(gMember)||llMemberHasPermission_MANAGESERVER(gMember))){
                        isInvalidCommand=false;
                        loadValues();
                        if(gItems.length<3){
                            menuGuild();
                        }else{
                            if(gItems[1].equalsIgnoreCase("exceptionmembers")){
                                switch (gItems[2].toLowerCase()){
                                    case "add":
                                        setupAdd2Member();isInvalidCommand = false;
                                        break;
                                    case "rem":
                                    case "remove":
                                        setupRem2Member();isInvalidCommand = false;
                                        break;
                                    case "list":
                                        setupViewList(iRestraints.fieldBannedUsers);isInvalidCommand = false;
                                        break;
                                }
                            }else
                            if(gItems[1].equalsIgnoreCase("allowedchannels")){
                                switch (gItems[2].toLowerCase()){
                                    case "add":
                                        setupAdd2Channel(true);isInvalidCommand = false;
                                        break;
                                    case "rem":
                                    case "remove":
                                        setupRem2Channel(true);isInvalidCommand = false;
                                        break;
                                    case "list":
                                        setupViewList(iRestraints.fieldAllowedChannels);isInvalidCommand = false;
                                        break;
                                }
                            }else
                            if(gItems[1].equalsIgnoreCase("commandroles")){
                                switch (gItems[2].toLowerCase()){
                                    case "add":
                                        setupAdd2Role(iRestraints.fieldCommandAllowedRoles);isInvalidCommand = false;
                                        break;
                                    case "rem":
                                    case "remove":
                                        setupRem2Role(iRestraints.fieldCommandAllowedRoles);isInvalidCommand = false;
                                        break;
                                    case "list":
                                        setupViewList(iRestraints.fieldCommandAllowedRoles);isInvalidCommand = false;
                                        break;
                                }
                            }else
                            if(gItems[1].equalsIgnoreCase("targetroles")){
                                switch (gItems[2].toLowerCase()){
                                    case "add":
                                        setupAdd2Role(iRestraints.fieldTargetAllowedRoles);isInvalidCommand = false;
                                        break;
                                    case "rem":
                                    case "remove":
                                        setupRem2Role(iRestraints.fieldTargetAllowedRoles);isInvalidCommand = false;
                                        break;
                                    case "list":
                                        setupViewList(iRestraints.fieldTargetAllowedRoles);isInvalidCommand = false;
                                        break;
                                }
                            }
                        }
                    }
                    loadValues();
                    if(!vEnabled){
                        sendEmbedInText(sRTitle,"Its disabled!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else
                    if(!gBDSMCommands.diaper.isAllowedChannel4Command(gTextChannel)){
                        sendEmbedInText(sRTitle,"Not allowed channel!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else
                    if(!gBDSMCommands.diaper.hasPermission2UseCommand(gMember)){
                        sendEmbedInText(sRTitle,"Member not allowed to use this!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }else
                    if(gBDSMCommands.diaper.isMemberBanned2UseCommand(gMember)){
                        sendEmbedInText(sRTitle,"Member not allowed to use this!",llColorRed_Cinnabar);isInvalidCommand = false;
                    }
                    if(isInvalidCommand&&isTargeted()){
                        if((gTarget!=null&&!gBDSMCommands.diaper.hasPermission2TargetCommand(gTarget))){
                            sendEmbedInText(sRTitle,"Member "+gTarget.getAsMention()+" can't be targeted!",llColorRed_Cinnabar);
                            isInvalidCommand = false;
                            return;
                        }else if(!gBDSMCommands.diaper.hasPermission2TargetCommand(gMember)){
                            sendEmbedInText(sRTitle,stringReplacer("Member !USER can't be targeted!"),llColorRed_Cinnabar);
                            isInvalidCommand = false;
                            return;
                        }
                        if(gItems[1].equalsIgnoreCase("set")&&gItems.length >= 5){
                            setField(gItems[2],gItems[3],gItems[4]);isInvalidCommand = false;
                        }
                        if(gItems[1].equalsIgnoreCase("setup")){
                            if(gItems.length>=3&&isInvalidCommand){
                                if(gItems[2].equalsIgnoreCase("wetenable")){
                                    setupWetEnable(gTarget,true);isInvalidCommand = false;
                                }
                                else if(gItems[2].equalsIgnoreCase("wetdisable")){
                                    setupWetEnable(gTarget,false);isInvalidCommand = false;
                                }
                                else if(gItems[2].equalsIgnoreCase("messenable")){
                                    setupMessEnable(gTarget,true);isInvalidCommand = false;
                                }
                                else if(gItems[2].equalsIgnoreCase("messdisable")){
                                    setupMessEnable(gTarget,false);isInvalidCommand = false;
                                }
                                else if(gItems[2].equalsIgnoreCase("wetchance")){
                                    if(gItems.length>=4){
                                        setupWetChance(gTarget,gItems[3]);isInvalidCommand = false;
                                    }
                                }
                                else if(gItems[2].equalsIgnoreCase("messchance")){
                                    if(gItems.length>=4){
                                        setupMessChance(gTarget,gItems[3]);isInvalidCommand = false;
                                    }
                                }
                                if(gItems[2].equalsIgnoreCase(ACCESSLEVEL.Private.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Protected.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Exposed.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Key.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Ask.getString())){
                                    rAccess(gTarget,gItems[2].toLowerCase());isInvalidCommand=false;
                                }
                            }
                            if(isInvalidCommand){
                                menuSetup();isInvalidCommand = false;
                            }
                        }
                        if(gItems[1].equalsIgnoreCase("owner")||gItems[1].equalsIgnoreCase("caretaker")){
                            if(gItems.length<3){
                                loggerExt.warn(fName+".invalid args length");
                                rHelp("owner");isInvalidCommand=false;
                            }else{
                                if(gItems[2].equalsIgnoreCase("reject")){
                                    rejectSub(gTarget);isInvalidCommand=false;
                                }
                                if(gItems[2].equalsIgnoreCase("accept")){
                                    acceptSub(gTarget);isInvalidCommand=false;
                                }
                                if(gItems[2].equalsIgnoreCase("release")){
                                    releaseSub(gTarget);isInvalidCommand=false;
                                }
                            }
                        }
                        else if(gItems[1].equalsIgnoreCase("lock")){
                            rLock(gTarget,"lock");isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase("unlock")){
                            rLock(gTarget,"unlock");isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase("list")){
                            listAuth(gTarget);isInvalidCommand=false;
                        }
                        else if(gItems.length>=3&&(gItems[2].equalsIgnoreCase("owner")||gItems[2].equalsIgnoreCase("caretaker"))){
                            if(gItems.length<4){
                                loggerExt.warn(fName+".invalid args length");
                                rHelp("owner");isInvalidCommand=false;
                            }else{
                                if(gItems[3].equalsIgnoreCase("reject")){
                                    rejectSub(gTarget);isInvalidCommand=false;
                                }
                                if(gItems[3].equalsIgnoreCase("accept")){
                                    acceptSub(gTarget);isInvalidCommand=false;
                                }
                                if(gItems[3].equalsIgnoreCase("release")){
                                    releaseSub(gTarget);isInvalidCommand=false;
                                }
                            }
                        }
                        else if(gItems.length>=3&&(gItems[3].equalsIgnoreCase("list"))){
                            listAuth(gTarget);isInvalidCommand=false;
                        }
                        else if(gItems.length>=3&&gItems[1].equalsIgnoreCase("access")){
                            if(gItems[2].equalsIgnoreCase(ACCESSLEVEL.Private.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Protected.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Exposed.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Key.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[2].equalsIgnoreCase(ACCESSLEVEL.Ask.getString())){
                                rAccess(gTarget,gItems[2].toLowerCase());isInvalidCommand=false;
                            }
                        }
                        else if(gItems.length>=3&&gItems[2].equalsIgnoreCase("access")){
                            if(gItems[3].equalsIgnoreCase(ACCESSLEVEL.Private.getString())||gItems[3].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[3].equalsIgnoreCase(ACCESSLEVEL.Protected.getString())||gItems[3].equalsIgnoreCase(ACCESSLEVEL.Exposed.getString())||gItems[3].equalsIgnoreCase(ACCESSLEVEL.Key.getString())||gItems[3].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[3].equalsIgnoreCase(ACCESSLEVEL.Ask.getString())){
                                rAccess(gTarget,gItems[3].toLowerCase());isInvalidCommand=false;
                            }
                        }
                        //end target
                    }else{
                        if(gItems[0].equalsIgnoreCase("set")&&gItems.length >= 4){
                            setField(gItems[1],gItems[2],gItems[3]);isInvalidCommand = false;
                        }
                        else if(gItems[0].equalsIgnoreCase("help")){
                            if(gItems.length>=2&&(gItems[1].equalsIgnoreCase("main")||gItems[1].equalsIgnoreCase("setup")||gItems[1].equalsIgnoreCase("caretaker"))){
                                rHelp(gItems[1]);isInvalidCommand = false;
                            }
                            else{
                                rHelp("setup");
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("setup")){
                            if(gItems.length>=2){
                                if(gItems[1].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Private.getString())){
                                    rAccess(gItems[1]);isInvalidCommand = false;
                                }
                                else if(gItems[1].equalsIgnoreCase("wetenable")){
                                    setupWetEnable(true);isInvalidCommand = false;
                                }
                                else if(gItems[1].equalsIgnoreCase("wetdisable")){
                                    setupWetEnable(false);isInvalidCommand = false;
                                }
                                else if(gItems[1].equalsIgnoreCase("messenable")){
                                    setupMessEnable(true);isInvalidCommand = false;
                                }
                                else if(gItems[1].equalsIgnoreCase("messdisable")){
                                    setupMessEnable(false);isInvalidCommand = false;
                                }
                                else if(gItems[1].equalsIgnoreCase("wetchance")){
                                    if(gItems.length>=3){
                                        setupWetChance(gItems[2]);isInvalidCommand = false;
                                    }
                                }
                                else if(gItems[1].equalsIgnoreCase("messchance")){
                                    if(gItems.length>=3){
                                        setupMessChance(gItems[2]);isInvalidCommand = false;
                                    }
                                }
                                else if(gItems[1].equalsIgnoreCase("runaway")){
                                    rRunAway();isInvalidCommand = false;
                                }
                            }
                            if(isInvalidCommand){
                                menuSetup();isInvalidCommand = false;
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("lock")){
                            rLock("lock");isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("unlock")){
                            rLock("unlock");isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("runaway")){
                            rRunAway();isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase("owner")||gItems[0].equalsIgnoreCase("caretaker")){
                            if(gItems.length<2){
                                loggerExt.warn(fName+".invalid args length");
                                rHelp("owner");isInvalidCommand=false;
                            }else{
                                if(gItems[1].equalsIgnoreCase("add")){
                                    addOwner();isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("remove")){
                                    removeOwner();isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("runaway")){
                                    rRunAway();isInvalidCommand=false;
                                }
                            }
                        }
                        else if(gItems[0].equalsIgnoreCase("access")){
                            if(gItems.length<2){
                                loggerExt.warn(fName+".invalid args length");
                                rHelp("access");isInvalidCommand=false;
                            }else{
                                if(gItems[1].equalsIgnoreCase(ACCESSLEVEL.Private.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Public.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Protected.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Exposed.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Key.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())||gItems[1].equalsIgnoreCase(ACCESSLEVEL.Ask.getString())){
                                    rAccess(gItems[1].toLowerCase());isInvalidCommand=false;
                                }
                            }
                        }
                    }
                }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gCommandEvent);*/
                if(isInvalidCommand){
                    sendOrUpdatePrivateEmbed(sRTitle,"You provided an incorrect command!", llColorRed);
                }
            }

        }catch (Exception e){
            loggerExt.error(fName + ".exception=" + e);
            loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        loggerExt.info(".run ended");
    }


    private void setupWetEnable(Boolean enable){
        String fName = "[setupWetEnable]";
        loggerExt.info(fName);
        if (!loadedProfile()) { sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){ loggerExt.info(fName+"denied");return;}
        loggerExt.info(fName+"enable="+enable);
        if(enable==gNewUserProfile.cWet.isEnabled()&&gNewUserProfile.cWet.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already enabled!", llColorPurple1);return;
        }
        if(enable==gNewUserProfile.cWet.isEnabled()&&!gNewUserProfile.cWet.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already disabled!", llColorPurple1);return;
        }
        gNewUserProfile.cWet.setEnabled(enable);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(enable){
            sendOrUpdatePrivateEmbed(sRTitle,"Wetness enabled.", llColorGreen1);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("Wetness enabled for !WEARER."), llColorGreen1);
        }else{
            sendOrUpdatePrivateEmbed(sRTitle,"Wetness disabled.", llColorRed_Barn);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("Wetness disabled for !WEARER."), llColorRed_Barn);
        }
    }
    private void setupMessEnable(Boolean enable){
        String fName = "[setupMessEnable]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){ loggerExt.info(fName+"denied");return;}
        loggerExt.info(fName+"enable="+enable);
        if(enable==gNewUserProfile.cMessy.isEnabled()&&gNewUserProfile.cMessy.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already enabled!", llColorPurple1);return;
        }
        if(enable==gNewUserProfile.cMessy.isEnabled()&&!gNewUserProfile.cMessy.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already disabled!", llColorPurple1);return;
        }
        gNewUserProfile.cMessy.setEnabled(enable);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(enable){
            sendOrUpdatePrivateEmbed(sRTitle,"Messy enabled.", llColorGreen1);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("Messy enabled for !WEARER."), llColorGreen1);
        }else{
            sendOrUpdatePrivateEmbed(sRTitle,"Messy disabled.", llColorRed_Barn);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("Messy disabled for !WEARER."), llColorRed_Barn);
        }
    }
    private void setupWetChance(String option){
        String fName = "[setupWetChance]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){ loggerExt.info(fName+"denied");return;}
        if(gTarget==null){
            loggerExt.info(fName+"self");
        }else{
            loggerExt.info(fName+"target");
        }
        loggerExt.info(fName+"option="+option);
        int number;
        if(option.isEmpty()){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        number= Integer.parseInt(option);
        if(number<0||number>25){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        gNewUserProfile.cWet.setChance(number);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(0<number){
            sendOrUpdatePrivateEmbed(sRTitle,"1 out of "+number+" chance for you to wet yourself", llColorGreen1);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER wet themself when performing actions!"), llColorGreen1);
        }else{
            sendOrUpdatePrivateEmbed(sRTitle,"0 chance for you to wet yourself", llColorRed_Barn);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("Chance for !WEARER wet theemself is disabled."), llColorRed);
        }
    }
    private void setupMessChance(String option){
        String fName = "[setupMessChance]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){ loggerExt.info(fName+"denied");return;}
        if(gTarget==null){
            loggerExt.info(fName+"self");
        }else{
            loggerExt.info(fName+"target");
        }
        loggerExt.info(fName+"option="+option);
        int number=0;
        if(option.isEmpty()){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        number= Integer.parseInt(option);
        if(number<0||number>25){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        gNewUserProfile.cMessy.setChance(number);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(0<number){
            sendOrUpdatePrivateEmbed(sRTitle,"1 out of "+number+" chance for you to mess yourself", llColorGreen1);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER mess themself when performing actions!"), llColorGreen1);
        }else{
            sendOrUpdatePrivateEmbed(sRTitle,"0 chance for you to mess yourself", llColorRed_Barn);
            llSendQuickEmbedMessageWithDelete(global,gTextChannel,sRTitle,stringReplacer("Chance of  !WEARER messing themself is disabled"), llColorRed_Barn);
        }
    }


    private void setField(String field, String key, String value){
        String fName = "[diaperLock]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!llMemberIsStaff(gMember)){
            loggerExt.info(fName+"denied");
           sendOrUpdatePrivateEmbed(sRTitle, "Denied! Staff only!", llColorRed);return;}
        loggerExt.info(fName+"field="+field);
        loggerExt.info(fName+"key="+key);
        loggerExt.info(fName+"value="+value);
        if(!field.equals(fieldProfile) && !field.equals(fieldWet) && !field.equals(fieldMessy) && !field.equals(fieldCaretaker)){
            loggerExt.info(fName+"invalid field");
           sendOrUpdatePrivateEmbed(sRTitle, "Invalid field: "+field, llColorRed);return;
        }
        if(!key.equals(keyProfileLocked) || !key.equals(keyProfileAccess) || !key.equals(keyLevel) || !key.equals(keyEnabled) || !key.equals(keyChance) || !key.equals(keyCaretakerId) || !key.equals(keyCaretakerAccepted)){
            loggerExt.info(fName+"invalid key");
           sendOrUpdatePrivateEmbed(sRTitle, "Invalid key: "+key, llColorRed);return;
        }
        gNewUserProfile.gUserProfile.putFieldEntry(field,key,value);
        if(!gNewUserProfile.saveJSON()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Updated for !WEARER: "+field+"."+key+"="+value), llColorGreen1);
    }
    private Boolean accessApprove4Setup(User user){
        String fName = "[accessApprove4Action]";
        loggerExt.info(fName);
        loggerExt.info(fName+"user:"+user.getId()+"|"+user.getName());
        if(gTarget==null) {
            loggerExt.info(fName+"self");
            if(gNewUserProfile.cCaretaker.getCaretakerID().isEmpty()||!gNewUserProfile.cCaretaker.isAccepted()){
                loggerExt.info(fName+"no caretaker"); return true;
            }
            if(gNewUserProfile.cCaretaker.getCaretakerID().equalsIgnoreCase(gUser.getId())){
                loggerExt.info(fName+"equals"); return true;
            }
            loggerExt.info(fName+"has caretaker");
            if(gNewUserProfile.cProfile.getAccess()!=ACCESSLEVEL.Caretaker){
                loggerExt.info(fName+"access is not caretaker"); return true;
            }
            sendOrUpdatePrivateEmbed(sRTitle,"Denied! Only your caretaker has access to setup!", llColorRed); return false;
        }else{
            loggerExt.info(fName+"target");
            if(gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Public){
                loggerExt.info(fName+"access is public"); return true;
            }
            if(gNewUserProfile.cCaretaker.getCaretakerID().isEmpty()||!gNewUserProfile.cCaretaker.isAccepted()){
                loggerExt.info(fName+"no caretaker"); sendOrUpdatePrivateEmbed(sRTitle,"Denied! You need to be "+gTarget.getAsMention()+"'s caretaker to access setup.", llColorRed);return false;
            }
            if(gNewUserProfile.cCaretaker.getCaretakerID().equalsIgnoreCase(gUser.getId())){
                loggerExt.info(fName+"equals"); return true;
            }
            loggerExt.info(fName+"not equals"); sendOrUpdatePrivateEmbed(sRTitle,"Denied! You need to be "+gTarget.getAsMention()+"'s caretaker to access setup.", llColorRed);return false;
        }
    }
    private Boolean isTargeted(){
        String fName = "[isTargeted]";
        loggerExt.info(fName);
        try{ String[] items = gCommandEvent.getArgs().split("\\s+");
            loggerExt.info(fName + ".items.size=" + items.length);
            loggerExt.info(fName + ".items[0]=" + items[0]);
            if((items[0].contains("<@")||items[0].contains("<@!")||items[0].contains("<!@"))&&items[0].contains(">")){
                String tmp=items[0].replace("<!@","").replace("<@!","").replace("<@","").replace(">","");
                Member m=gGuild.getMemberById(tmp);
                if(m!=null){
                    if(m.getId().equals(gUser.getId())){
                        loggerExt.info(fName + ".target same");
                        return false;
                    }
                    loggerExt.info(fName + ".target ok");
                    gTarget=m;
                    return true;
                }
            }
            loggerExt.info(fName + ".target none");
            return false;
        }
        catch(Exception ex){
            loggerExt.error(fName + ".exception: "+ex);
            return false;
        }
    }

    private void menuSetup(){
        String fName="[menuSetup]";
        loggerExt.info(fName);
        try{
            if (!getProfile()) { sendOrUpdatePrivateEmbed( sRTitle, "Failed to load  profile!", llColorRed);return; }
            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                menuSetupWearer();
            }else if(gNewUserProfile.cCaretaker.getCaretakerIDLong()==gMember.getIdLong()){
                menuSetupCaretaker();
            }else{
                menuSetupSomebody();
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    String gCommandFileMainPath =gFileMainPath+"menuSetup.json";
    private void menuSetupWearer(){
        String fName="[menuSetupWearer]";
        loggerExt.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();String desc="";
            embed.setColor(llColorBlue1).setTitle(sRTitle+" Setup Menu");
            embed.addField("Access"," Currently: "+gNewUserProfile.cProfile.getAccessAsString(),false);
            embed.addField("Wetness","Currently: "+gNewUserProfile.cWet.isEnabled(),false);
            embed.addField("Messiness","Currently: "+gNewUserProfile.cMessy.isEnabled(),false);
            embed.addField("Wetness chance","Currently: "+gNewUserProfile.cWet.getChance(),false);
            embed.addField("Messiness chance","Currently: "+gNewUserProfile.cMessy.getChance(),false);
            desc="Assigned: ";
            if(gNewUserProfile.cCaretaker.hasCaretaker()){
                String caretakerId=gNewUserProfile.cCaretaker.getCaretakerID();
                Member cartekar=lsMemberHelper.lsGetMember(gGuild,caretakerId);
                if(cartekar!=null)desc+=cartekar.getAsMention();else desc+="<@"+caretakerId+">";
                if(!gNewUserProfile.cCaretaker.isAccepted())desc+=" [they need to approve it]";
            }else desc="n/a";
            desc+="\n`<@Bot>diaper caretaker add <@User>`, gives @User a caretaker offer. They still need to accept it";
            desc+="\n`<@Bot>diaper caretaker remove`,  removes the suggested caretaker, works while they did nto accept it.";
            desc+="\n`<@Bot>diaper caretaker runaway`, resets the profile and removes the caretaker.";
            embed.addField("Caretaker",desc,false);
            messageComponentManager.loadMessageComponents(gCommandFileMainPath);
            logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
            Message message=null;
            try {
                lcMessageBuildComponent.SelectMenu selectMenuAccess=messageComponentManager.messageBuildComponents.getSelectMenuAt(0);
                switch (gNewUserProfile.cProfile.getAccess()){
                    case Caretaker:
                        selectMenuAccess.setDefaultOptionAt(2);
                        break;
                    case Private:
                        selectMenuAccess.setDefaultOptionAt(1);
                        break;
                    case Public:
                        selectMenuAccess.setDefaultOptionAt(0);
                        break;
                    default:
                        break;
                }
                lcMessageBuildComponent.Button buttonWetEnable=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                lcMessageBuildComponent.Button buttonWetDes=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                lcMessageBuildComponent.Button buttonWetInc=messageComponentManager.messageBuildComponents.getButtonAt(1,2);
                lcMessageBuildComponent.Button buttonMessEnable=messageComponentManager.messageBuildComponents.getButtonAt(2,0);
                lcMessageBuildComponent.Button buttonMessDes=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                lcMessageBuildComponent.Button buttonMessInc=messageComponentManager.messageBuildComponents.getButtonAt(2,2);
                if(!gNewUserProfile.cWet.isEnabled()){
                    buttonWetEnable.setStyle(ButtonStyle.SUCCESS).setLabel("1️⃣Wet Enable");
                    buttonWetDes.setDisable();buttonWetInc.setDisable();
                }else{
                    buttonWetEnable.setStyle(ButtonStyle.DANGER).setLabel("1️⃣Wet Disable");
                }
                if(!gNewUserProfile.cMessy.isEnabled()){
                    buttonMessEnable.setStyle(ButtonStyle.SUCCESS).setLabel("2️⃣Messy Enable");
                    buttonMessDes.setDisable();buttonMessInc.setDisable();
                }else{
                    buttonMessEnable.setStyle(ButtonStyle.DANGER).setLabel("2️⃣Messy Disable");
                }
                lcMessageBuildComponent.Button buttonCustomActions=messageComponentManager.messageBuildComponents.getButtonAt(3,0);
                lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(3,2);
                if(gCurrentInteractionHook!=null)buttonClose.setIgnored();
                if(gNewUserProfile.cCaretaker.hasCaretaker()&&gNewUserProfile.cCaretaker.isAccepted()&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Caretaker){
                    buttonWetEnable.setDisable();buttonWetDes.setDisable();buttonWetInc.setDisable();
                    buttonMessEnable.setDisable();buttonMessDes.setDisable();buttonMessInc.setDisable();
                    selectMenuAccess.setDisabled();
                }
                logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);
            }
            menuSetupWearer_listener(message);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuSetupWearer_listener(Message message){
        String fName="[menuMain]";
        loggerExt.info(fName);
        try{
            global.waiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        if(gCurrentInteractionHook!=null)gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                        try {
                            String value=e.getValues().get(0);
                            logger.warn(fName+"value="+value);
                            llMessageDelete(message);
                            switch (value){
                                case "access_public":  rAccess(ACCESSLEVEL.Public.getString());break;
                                case "access_private":  rAccess(ACCESSLEVEL.Private.getString());break;
                                case "access_caretaker":  rAccess(ACCESSLEVEL.Caretaker.getString());break;
                            }
                            gCurrentInteractionHook=gComponentInteractionHook;
                            menuSetup();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_selectmenu));
            global.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        if(gCurrentInteractionHook!=null)gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            int tmp=-1;
                            switch (id){
                                case "wetness_enable":
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(false);
                                    }else{
                                        setupWetEnable(true);
                                    }
                                    break;
                                case "wetness_chancge_des":
                                    tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                    break;
                                case "wetness_chancge_inc":
                                    tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                    break;
                                case "messy_enable":
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(false);
                                    }else{
                                        setupMessEnable(true);
                                    }
                                    break;
                                case "messy_chancge_des":
                                    tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(String.valueOf(tmp));
                                    }
                                    break;
                                case "messy_chancge_inc":
                                    tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(String.valueOf(tmp));
                                    }
                                    break;
                                case "custom_actions":
                                    if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuCustomActions();
                                    return;
                                case lsUnicodeEmotes.aliasInformationSource:
                                    if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    rHelp("main");
                                    return;
                            }
                            gCurrentInteractionHook=gComponentInteractionHook;
                            menuSetup();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(false);
                                    }else{
                                        setupWetEnable(true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(false);
                                    }else{
                                        setupMessEnable(true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCar))){
                                    rAccess(ACCESSLEVEL.Private.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasBus))){
                                    rAccess(ACCESSLEVEL.Public.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasOlderWoman))){
                                    rAccess(ACCESSLEVEL.Caretaker.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||asCodepoints.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                                    menuCustomActions();
                                    llMessageDelete(message);
                                    return;
                                }
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    llMessageDelete(message);return;
                                }
                                llMessageDelete(message);
                                menuSetup();
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }

                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            }else{
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(false);
                                    }else{
                                        setupWetEnable(true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(false);
                                    }else{
                                        setupMessEnable(true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCar))){
                                    rAccess(ACCESSLEVEL.Private.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasBus))){
                                    rAccess(ACCESSLEVEL.Public.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasOlderWoman))){
                                    rAccess(ACCESSLEVEL.Caretaker.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||asCodepoints.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                                    menuCustomActions();
                                    llMessageDelete(message);
                                    return;
                                }
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    llMessageDelete(message);return;
                                }
                                llMessageDelete(message);
                                menuSetup();
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }

                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuSetupCaretaker(){
        String fName="[menuSetupCaretaker]";
        loggerExt.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();String desc="";
            embed.setColor(llColorBlue1).setTitle(gNewUserProfile.getMember().getUser().getName()+"'s"+" Setup Menu");
            embed.addField("Access"," Currently: "+gNewUserProfile.cProfile.getAccessAsString(),false);
            embed.addField("Wetness","Currently: "+gNewUserProfile.cWet.isEnabled(),false);
            embed.addField("Messiness","Currently: "+gNewUserProfile.cMessy.isEnabled(),false);
            embed.addField("Wetness chance","Currently: "+gNewUserProfile.cWet.getChance(),false);
            embed.addField("Messiness chance","Currently: "+gNewUserProfile.cMessy.getChance(),false);
            desc="Assigned: ";
            if(gNewUserProfile.cCaretaker.hasCaretaker()){
                String caretakerId=gNewUserProfile.cCaretaker.getCaretakerID();
                Member cartekar=lsMemberHelper.lsGetMember(gGuild,caretakerId);
                if(cartekar!=null)desc+=cartekar.getAsMention();else desc+="<@"+caretakerId+">";
                if(!gNewUserProfile.cCaretaker.isAccepted())desc+=" [they need to approve it]";
            }else desc="what.. this should be impossible";
            desc+="\n`<@Bot>diaper <@Wearer> caretaker accept`, accepts the caretaker offer from wearer.";
            desc+="\n`<@Bot>diaper <@Wearer> caretaker reject`, rejects the caretaker offer from wearer.";
            desc+="\n`<@Bot>diaper <@Wearer> caretaker release`, releases @wearer from you.";
            embed.addField("Caretaker",desc,false);
            messageComponentManager.loadMessageComponents(gCommandFileMainPath);
            logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
            Message message=null;
            try {
                lcMessageBuildComponent.SelectMenu selectMenuAccess=messageComponentManager.messageBuildComponents.getSelectMenuAt(0);
                switch (gNewUserProfile.cProfile.getAccess()){
                    case Caretaker:
                        selectMenuAccess.setDefaultOptionAt(2);
                        break;
                    case Private:
                        selectMenuAccess.setDefaultOptionAt(1);
                        break;
                    case Public:
                        selectMenuAccess.setDefaultOptionAt(0);
                        break;
                    default:
                        break;
                }
                lcMessageBuildComponent.Button buttonWetEnable=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                lcMessageBuildComponent.Button buttonWetDes=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                lcMessageBuildComponent.Button buttonWetInc=messageComponentManager.messageBuildComponents.getButtonAt(1,2);
                lcMessageBuildComponent.Button buttonMessEnable=messageComponentManager.messageBuildComponents.getButtonAt(2,0);
                lcMessageBuildComponent.Button buttonMessDes=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                lcMessageBuildComponent.Button buttonMessInc=messageComponentManager.messageBuildComponents.getButtonAt(2,2);
                if(!gNewUserProfile.cWet.isEnabled()){
                    buttonWetEnable.setStyle(ButtonStyle.SUCCESS).setLabel("1️⃣Wet Enable");
                    buttonWetDes.setDisable();buttonWetInc.setDisable();
                }else{
                    buttonWetEnable.setStyle(ButtonStyle.DANGER).setLabel("1️⃣Wet Disable");
                }
                if(!gNewUserProfile.cMessy.isEnabled()){
                    buttonMessEnable.setStyle(ButtonStyle.SUCCESS).setLabel("2️⃣Messy Enable");
                    buttonMessDes.setDisable();buttonMessInc.setDisable();
                }else{
                    buttonMessEnable.setStyle(ButtonStyle.DANGER).setLabel("2️⃣Messy Disable");
                }
                lcMessageBuildComponent.Button buttonCustomActions=messageComponentManager.messageBuildComponents.getButtonAt(3,0);
                lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(3,2);
                if(gCurrentInteractionHook!=null)buttonClose.setIgnored();
                logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);
            }
            menuSetupCaretaker_listener(message);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuSetupCaretaker_listener(Message message){
        String fName="[menuMain]";
        loggerExt.info(fName);
        try{
            global.waiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        if(gCurrentInteractionHook!=null)gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                        try {
                            String value=e.getValues().get(0);
                            logger.warn(fName+"value="+value);
                            llMessageDelete(message);
                            switch (value){
                                case "access_public":  rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Public.getString());break;
                                case "access_private":  rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Private.getString());break;
                                case "access_caretaker":  rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Caretaker.getString());break;
                            }
                            gCurrentInteractionHook=gComponentInteractionHook;
                            menuSetup();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_selectmenu));
            global.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        if(gCurrentInteractionHook!=null)gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            int tmp=-1;
                            switch (id){
                                case "wetness_enable":
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupWetEnable(gNewUserProfile.getMember(),true);
                                    }
                                    break;
                                case "wetness_chancge_des":
                                    tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "wetness_chancge_inc":
                                    tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "messy_enable":
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupMessEnable(gNewUserProfile.getMember(),true);
                                    }
                                    break;
                                case "messy_chancge_des":
                                    tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "messy_chancge_inc":
                                    tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "custom_actions":
                                    if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuCustomActions();
                                    return;
                                case lsUnicodeEmotes.aliasInformationSource:
                                    if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    rHelp("main");
                                    return;
                            }
                            gCurrentInteractionHook=gComponentInteractionHook;
                            menuSetup();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupWetEnable(gNewUserProfile.getMember(),true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupMessEnable(gNewUserProfile.getMember(),true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCar))){
                                    rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Private.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasBus))){
                                    rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Public.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasOlderWoman))){
                                    rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Caretaker.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||asCodepoints.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                                    menuCustomActions();
                                    llMessageDelete(message);
                                    return;
                                }
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    llMessageDelete(message);return;
                                }
                                llMessageDelete(message);
                                menuSetup();
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }

                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            }else{
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupWetEnable(gNewUserProfile.getMember(),true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupMessEnable(gNewUserProfile.getMember(),true);
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    int tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    int tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCar))){
                                    rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Private.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasBus))){
                                    rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Public.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasOlderWoman))){
                                    rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Caretaker.getString());
                                }
                                if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||asCodepoints.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                                    menuCustomActions();
                                    llMessageDelete(message);
                                    return;
                                }
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    llMessageDelete(message);return;
                                }
                                llMessageDelete(message);
                                menuSetup();
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }

                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            }

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuSetupSomebody(){
        String fName="[menuSetupSomebody]";
        loggerExt.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();String desc="";
            embed.setColor(llColorBlue1).setTitle(gNewUserProfile.getMember().getUser().getName()+"'s"+" Setup Menu");
            embed.addField("Access"," Currently: "+gNewUserProfile.cProfile.getAccessAsString(),false);
            embed.addField("Wetness","Currently: "+gNewUserProfile.cWet.isEnabled(),false);
            embed.addField("Messiness","Currently: "+gNewUserProfile.cMessy.isEnabled(),false);
            embed.addField("Wetness chance","Currently: "+gNewUserProfile.cWet.getChance(),false);
            embed.addField("Messiness chance","Currently: "+gNewUserProfile.cMessy.getChance(),false);
            desc="Assigned: ";
            if(gNewUserProfile.cCaretaker.hasCaretaker()){
                String caretakerId=gNewUserProfile.cCaretaker.getCaretakerID();
                Member cartekar=lsMemberHelper.lsGetMember(gGuild,caretakerId);
                if(cartekar!=null)desc+=cartekar.getAsMention();else desc+="<@"+caretakerId+">";
                if(!gNewUserProfile.cCaretaker.isAccepted())desc+=" [they need to approve it]";
            }else desc="n/a";
            if(gNewUserProfile.cCaretaker.getCaretakerIDLong()==gMember.getIdLong()){
                desc+="\n`<@Bot>diaper <@Wearer> caretaker accept`, accepts the caretaker offer from wearer.";
                desc+="\n`<@Bot>diaper <@Wearer> caretaker reject`, rejects the caretaker offer from wearer.";
                desc+="\n`<@Bot>diaper <@Wearer> caretaker release`, releases @wearer from you.";
            }
            embed.addField("Caretaker",desc,false);
            messageComponentManager.loadMessageComponents(gCommandFileMainPath);
            logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
            Message message=null;
            try {
                lcMessageBuildComponent.SelectMenu selectMenuAccess=messageComponentManager.messageBuildComponents.getSelectMenuAt(0);
                switch (gNewUserProfile.cProfile.getAccess()){
                    case Caretaker:
                        selectMenuAccess.setDefaultOptionAt(2);
                        break;
                    case Private:
                        selectMenuAccess.setDefaultOptionAt(1);
                        break;
                    case Public:
                        selectMenuAccess.setDefaultOptionAt(0);
                        break;
                    default:
                        break;
                }
                lcMessageBuildComponent.Button buttonWetEnable=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                lcMessageBuildComponent.Button buttonWetDes=messageComponentManager.messageBuildComponents.getButtonAt(1,1);
                lcMessageBuildComponent.Button buttonWetInc=messageComponentManager.messageBuildComponents.getButtonAt(1,2);
                lcMessageBuildComponent.Button buttonMessEnable=messageComponentManager.messageBuildComponents.getButtonAt(2,0);
                lcMessageBuildComponent.Button buttonMessDes=messageComponentManager.messageBuildComponents.getButtonAt(2,1);
                lcMessageBuildComponent.Button buttonMessInc=messageComponentManager.messageBuildComponents.getButtonAt(2,2);

                if(!gNewUserProfile.cWet.isEnabled()){
                    buttonWetEnable.setStyle(ButtonStyle.SUCCESS).setLabel("1️⃣Wet Enable");
                    buttonWetDes.setDisable();buttonWetInc.setDisable();
                }else{
                    buttonWetEnable.setStyle(ButtonStyle.DANGER).setLabel("1️⃣Wet Disable");
                }
                if(!gNewUserProfile.cMessy.isEnabled()){
                    buttonMessEnable.setStyle(ButtonStyle.SUCCESS).setLabel("2️⃣Messy Enable");
                    buttonMessDes.setDisable();buttonMessInc.setDisable();
                }else{
                    buttonMessEnable.setStyle(ButtonStyle.DANGER).setLabel("2️⃣Messy Disable");
                }
                lcMessageBuildComponent.Button buttonCustomActions=messageComponentManager.messageBuildComponents.getButtonAt(3,0);
                buttonCustomActions.setDisable();
                lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("white_check_mark");
                if(gCurrentInteractionHook!=null)buttonClose.setDisable();
                if(gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Caretaker||gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Protected||gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Private){
                    buttonWetEnable.setDisable();buttonWetDes.setDisable();buttonWetInc.setDisable();
                    buttonMessEnable.setDisable();buttonMessDes.setDisable();buttonMessInc.setDisable();
                    selectMenuAccess.setDisabled();
                }
                logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);
            }
            menuSetupSomebody_listener(message);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuSetupSomebody_listener(Message message){
        String fName="[menuMain]";
        loggerExt.info(fName);
        try{
            global.waiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        if(gCurrentInteractionHook!=null)gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                        try {
                            String value=e.getValues().get(0);
                            logger.warn(fName+"value="+value);
                            llMessageDelete(message);
                            switch (value){
                                case "access_public":  rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Public.getString());break;
                                case "access_private":  rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Private.getString());break;
                                case "access_caretaker":  rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Caretaker.getString());break;
                            }
                            gCurrentInteractionHook=gComponentInteractionHook;
                            menuSetup();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_selectmenu));
            global.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        if(gCurrentInteractionHook!=null)gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            int tmp=-1;
                            switch (id){
                                case "wetness_enable":
                                    if(gNewUserProfile.cWet.isEnabled()){
                                        setupWetEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupWetEnable(gNewUserProfile.getMember(),true);
                                    }
                                    break;
                                case "wetness_chancge_des":
                                    tmp=gNewUserProfile.cWet.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "wetness_chancge_inc":
                                    tmp=gNewUserProfile.cWet.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "messy_enable":
                                    if(gNewUserProfile.cMessy.isEnabled()){
                                        setupMessEnable(gNewUserProfile.getMember(),false);
                                    }else{
                                        setupMessEnable(gNewUserProfile.getMember(),true);
                                    }
                                    break;
                                case "messy_chancge_des":
                                    tmp=gNewUserProfile.cMessy.getChance();
                                    tmp--;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "messy_chancge_inc":
                                    tmp=gNewUserProfile.cMessy.getChance();
                                    tmp++;
                                    if(tmp>=0&&tmp<=25){
                                        setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                    }
                                    break;
                                case "custom_actions":
                                    if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    menuCustomActions();
                                    return;
                                case lsUnicodeEmotes.aliasInformationSource:
                                    if(gCurrentInteractionHook!=null)sendOrUpdatePrivateEmbed(sRTitle,"Opening menu",llColorBlue1);
                                    gCurrentInteractionHook=gComponentInteractionHook;
                                    rHelp("main");
                                    return;
                            }
                            gCurrentInteractionHook=gComponentInteractionHook;
                            menuSetup();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(gIsOverride){
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        if(gNewUserProfile.cWet.isEnabled()){
                                            setupWetEnable(gNewUserProfile.getMember(),false);
                                        }else{
                                            setupWetEnable(gNewUserProfile.getMember(),true);
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        if(gNewUserProfile.cMessy.isEnabled()){
                                            setupMessEnable(gNewUserProfile.getMember(),false);
                                        }else{
                                            setupMessEnable(gNewUserProfile.getMember(),true);
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        int tmp=gNewUserProfile.cWet.getChance();
                                        tmp--;
                                        if(tmp>=0&&tmp<=25){
                                            setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                        int tmp=gNewUserProfile.cWet.getChance();
                                        tmp++;
                                        if(tmp>=0&&tmp<=25){
                                            setupWetChance(String.valueOf(tmp));
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                        int tmp=gNewUserProfile.cMessy.getChance();
                                        tmp--;
                                        if(tmp>=0&&tmp<=25){
                                            setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                        int tmp=gNewUserProfile.cMessy.getChance();
                                        tmp++;
                                        if(tmp>=0&&tmp<=25){
                                            setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                        }
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCar))){
                                        rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Private.getString());
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasBus))){
                                        rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Public.getString());
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasOlderWoman))){
                                        rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Caretaker.getString());
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||asCodepoints.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                                        menuCustomActions();
                                        llMessageDelete(message);
                                        return;
                                    }
                                }
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    llMessageDelete(message);return;
                                }
                                llMessageDelete(message);
                                menuSetup();
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }

                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            }else{
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                loggerExt.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                loggerExt.warn(fName+"asCodepoints="+name);
                                if(gIsOverride){
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        if(gNewUserProfile.cWet.isEnabled()){
                                            setupWetEnable(gNewUserProfile.getMember(),false);
                                        }else{
                                            setupWetEnable(gNewUserProfile.getMember(),true);
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        if(gNewUserProfile.cMessy.isEnabled()){
                                            setupMessEnable(gNewUserProfile.getMember(),false);
                                        }else{
                                            setupMessEnable(gNewUserProfile.getMember(),true);
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        int tmp=gNewUserProfile.cWet.getChance();
                                        tmp--;
                                        if(tmp>=0&&tmp<=25){
                                            setupWetChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                        int tmp=gNewUserProfile.cWet.getChance();
                                        tmp++;
                                        if(tmp>=0&&tmp<=25){
                                            setupWetChance(String.valueOf(tmp));
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                        int tmp=gNewUserProfile.cMessy.getChance();
                                        tmp--;
                                        if(tmp>=0&&tmp<=25){
                                            setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                        }
                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                        int tmp=gNewUserProfile.cMessy.getChance();
                                        tmp++;
                                        if(tmp>=0&&tmp<=25){
                                            setupMessChance(gNewUserProfile.getMember(),String.valueOf(tmp));
                                        }
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasRedCar))){
                                        rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Private.getString());
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasBus))){
                                        rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Public.getString());
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasOlderWoman))){
                                        rAccess(gNewUserProfile.getMember(),ACCESSLEVEL.Caretaker.getString());
                                    }
                                    if(name.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||asCodepoints.contains(global.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))){
                                        menuCustomActions();
                                        llMessageDelete(message);
                                        return;
                                    }
                                }
                                if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                    llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                                    llMessageDelete(message);return;
                                }
                                llMessageDelete(message);
                                menuSetup();
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }

                        },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });
            }

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }


    JSONArray numbersStrList=new JSONArray();
    private void fillNumbersList(){
        String fName="[fillNumbersList]";
        loggerExt.info(fName);
        try{
            numbersStrList=new JSONArray();
            numbersStrList.put(lsUnicodeEmotes.aliasZero);
            numbersStrList.put(lsUnicodeEmotes.aliasOne);
            numbersStrList.put(lsUnicodeEmotes.aliasTwo);
            numbersStrList.put(lsUnicodeEmotes.aliasThree);
            numbersStrList.put(lsUnicodeEmotes.aliasFour);
            numbersStrList.put(lsUnicodeEmotes.aliasFive);
            numbersStrList.put(lsUnicodeEmotes.aliasSix);
            numbersStrList.put(lsUnicodeEmotes.aliasSeven);
            numbersStrList.put(lsUnicodeEmotes.aliasEight);
            numbersStrList.put(lsUnicodeEmotes.aliasNine);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    boolean isMenuLevel=false;
    boolean isPatreon=false,isPatreonGuild=false;
    private void menuCustomActions(){
        String fName="[menuCustomActions]";
        loggerExt.info(fName);
        try{
            if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
            loadDUserValues();
            isPatreon= lsPatreon.hasPatreonTierOrAbove(global,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(global,gGuild,69);
            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                menuCustomActions_Wearer();
            }else{
                if(gNewUserProfile.hasUserOwnerAccess(gUser)||gNewUserProfile.hasUserSecOwnerAccess(gUser)){
                    menuCustomActions_Owner();
                }else{
                    menuCustomActions_Somebody();
                }
            }
        }catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    String customActionTitle="Diaper Custom Action";
    private void menuCustomActions_Wearer(){
        String fName="[menuCustomActions_Wearer]";
        loggerExt.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorBlue1);
            loggerExt.info(fName+".isPatreon="+isPatreon+", isPatreonGuild="+isPatreonGuild);
            if(isPatreon){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
            }
            else if(isPatreonGuild){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
            }else {
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
            }
            if(gNewUserProfile.cCustomAction.isEmpty()){
                embed.addField("Actions","(is empty)",false);
            }else {
                fillNumbersList();
                int i=0;
                while(i<gNewUserProfile.cCustomAction.size()&&i<maxActions) {
                    try {
                        action = gNewUserProfile.cCustomAction.getAction(i);
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append(action.getName());
                    } catch (Exception e) {
                        loggerExt.error(fName + ".exception=" + e);
                        loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append("(error)");
                    }
                    i++;
                }
                embed.addField("Actions",desc3.toString(),false);
            }
            embed.addField("How many slots?","The normal size is "+maxNormalUserActions+", however Patreon supporters from tier 2+ can have max "+maxActions+".",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            if((isPatreon||isPatreonGuild)&&gNewUserProfile.cCustomAction.size()< maxActions)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
            else if(gNewUserProfile.cCustomAction.size()<maxNormalUserActions)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
            if(gNewUserProfile.cCustomAction.size()>=1)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
            if(gNewUserProfile.cCustomAction.size()>=2)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(gNewUserProfile.cCustomAction.size()>=3)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(gNewUserProfile.cCustomAction.size()>=4)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(gNewUserProfile.cCustomAction.size()>=5)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            if(gNewUserProfile.cCustomAction.size()>=6)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
            if(gNewUserProfile.cCustomAction.size()>=7)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
            if(gNewUserProfile.cCustomAction.size()>=8)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
            if(gNewUserProfile.cCustomAction.size()>=9)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
            if(gNewUserProfile.cCustomAction.size()>=10)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+asCodepoints);isMenuLevel=true;
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSetup();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                dmInputNewAction(1);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                menuAction(0);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                menuAction(1);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                menuAction(2);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                menuAction(3);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                menuAction(4);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                menuAction(5);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                menuAction(6);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                menuAction(7);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                menuAction(8);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                menuAction(9);
                            }
                            else{
                                menuCustomActions();
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuCustomActions_Owner(){
        String fName="[menuCustomActions_Owner]";
        loggerExt.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorBlue1);
            loggerExt.info(fName+".isPatreon="+isPatreon+", isPatreonGuild="+isPatreonGuild);
            if(isPatreon){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
            }
            else if(isPatreonGuild){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
            }else {
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
            }
            if(gNewUserProfile.cCustomAction.isEmpty()){
                embed.addField("Actions","(is empty)",false);
            }else {
                fillNumbersList();
                int i=0;
                while(i<gNewUserProfile.cCustomAction.size()&&i< maxActions) {
                    try {
                        action= gNewUserProfile.cCustomAction.getAction(i);
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append(action.getName());
                    } catch (Exception e) {
                        loggerExt.error(fName + ".exception=" + e);
                        loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append("(error)");
                    }
                    i++;
                }
            }
            embed.addField("How many slots?","The normal size is "+maxNormalUserActions+", however Patreon supporters from tier 2+ can have max "+maxActions+".",false);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            if((isPatreon||isPatreonGuild)&&gNewUserProfile.cCustomAction.size()< maxActions)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
            else if(gNewUserProfile.cCustomAction.size()<maxNormalUserActions)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
            if(gNewUserProfile.cCustomAction.size()>=1)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
            if(gNewUserProfile.cCustomAction.size()>=2)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(gNewUserProfile.cCustomAction.size()>=3)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(gNewUserProfile.cCustomAction.size()>=4)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(gNewUserProfile.cCustomAction.size()>=5)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            if(gNewUserProfile.cCustomAction.size()>=6)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
            if(gNewUserProfile.cCustomAction.size()>=7)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
            if(gNewUserProfile.cCustomAction.size()>=8)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
            if(gNewUserProfile.cCustomAction.size()>=9)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
            if(gNewUserProfile.cCustomAction.size()>=10)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+asCodepoints);isMenuLevel=true;
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSetup();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                dmInputNewAction(1);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                menuAction(0);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                menuAction(1);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                menuAction(2);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                menuAction(3);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                menuAction(4);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                menuAction(5);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                menuAction(6);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                menuAction(7);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                menuAction(8);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                menuAction(9);
                            }
                            else{
                                menuCustomActions();
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        llMessageDelete(message);
                    });



        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuCustomActions_Somebody(){
        String fName="[menuCustomActions_Somebody]";
        loggerExt.info(fName);
        try{

            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorBlue1);
            if(isPatreon){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
            }
            else if(isPatreonGuild){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
            }else {
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
            }
            if(gNewUserProfile.cCustomAction.isEmpty()){
                embed.addField("Actions","(is empty)",false);
            }else {
                fillNumbersList();
                int i=0;
                while(i<gNewUserProfile.cCustomAction.size()&&i< maxActions) {
                    try {
                        action=gNewUserProfile.cCustomAction.getAction(i);
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append(action.getName());
                    } catch (Exception e) {
                        loggerExt.error(fName + ".exception=" + e);
                        loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc3.append("\n:"+numbersStrList.getString(i)+": ").append("(error)");
                    }
                    i++;
                }
            }
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            if(gNewUserProfile.cCustomAction.size()>=1)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
            if(gNewUserProfile.cCustomAction.size()>=2)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            if(gNewUserProfile.cCustomAction.size()>=3)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            if(gNewUserProfile.cCustomAction.size()>=4)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
            if(gNewUserProfile.cCustomAction.size()>=5)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
            if(gNewUserProfile.cCustomAction.size()>=6)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
            if(gNewUserProfile.cCustomAction.size()>=7)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
            if(gNewUserProfile.cCustomAction.size()>=8)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
            if(gNewUserProfile.cCustomAction.size()>=9)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
            if(gNewUserProfile.cCustomAction.size()>=10)lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+asCodepoints);isMenuLevel=true;
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSetup();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                menuAction(0);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                menuAction(1);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                menuAction(2);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                menuAction(3);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                menuAction(4);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                menuAction(5);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                menuAction(6);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                menuAction(7);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                menuAction(8);
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                menuAction(9);
                            }
                            else{
                                menuCustomActions();
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    int ACTION_INDEX=-1; entityCustomActions.ACTION action=new entityCustomActions.ACTION();
    private void menuAction(int index){
        String fName="[menuAction]";
        try{
            loggerExt.info(fName+".index="+index);
            if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
            loadDUserValues();
            if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                menuAction_Wearer(index);
            }else{
                if(gNewUserProfile.hasUserOwnerAccess(gUser)||gNewUserProfile.hasUserSecOwnerAccess(gUser)){
                    menuAction_Owner(index);
                }else{
                    menuAction_Somebody(index);
                }
            }

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuAction_Wearer(int index){
        String fName="[menuAction_Wearer]";
        try{
            loggerExt.info(fName+".index="+index);
            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorBlue1);
            if(isPatreon){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
            }
            else if(isPatreonGuild){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
            }else {
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
            }
            ACTION_INDEX=-1;
            boolean hasAction=false,hasName=false,hasText=false;
            if(gNewUserProfile.cCustomAction.size()==0){
                embed.addField("Action","(is empty)",false);
            }else
            if(index<0||index>=gNewUserProfile.cCustomAction.size()){
                embed.addField("Action","(invalid index)",false);
            }
            else {
                try {
                    action=gNewUserProfile.cCustomAction.getAction(index); ACTION_INDEX=index;
                    loggerExt.info(fName+". ACTION_INDEX="+ ACTION_INDEX);
                    loggerExt.info(fName+". ACTION="+ action.getJSON());
                    String name=action.getName();
                    if(name.isBlank()||name.isEmpty()){
                        embed.addField("Action","index:"+index+"\nname: (blank)",false);
                    }else{
                        embed.addField("Action","index:"+index+"\nname: "+name,false);
                        hasName=true;
                    }
                    hasAction=true;
                    String text=action.getText1();
                    if(text.isBlank()||text.isEmpty()){
                        embed.addField("Text (Self)","(blank)",false);
                    }else{
                        embed.addField("Text (Self)",text,false);
                        hasText=true;
                    }
                    String text2=action.getText2();
                    if(text2.isBlank()||text2.isEmpty()){
                        embed.addField("Text (Targeted)","(blank)",false);
                    }else{
                        embed.addField("Text (Targeted)",text2,false);
                        hasText=true;
                    }
                }catch (Exception e){
                    loggerExt.error(fName + ".exception=" + e);
                    loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    embed.addField("Action","(error)",false);
                }
                desc= global.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" enter name";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasMemo)+" enter self text";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasExclamation)+" enter targeted text";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" remove action";
                embed.addField("Options",desc,false);
            }


            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            if(hasAction){
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasExclamation));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
            }
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+asCodepoints);isMenuLevel=true;
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSetup();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                dmInputActionName();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                dmInputActionText1();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasExclamation))){
                                dmInputActionText2();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                removeAction(index);
                                menuCustomActions();
                            }
                            else{
                                menuAction(index);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        llMessageDelete(message);
                    });
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuAction_Owner(int index){
        String fName="[menuAction_Owner]";
        try{
            loggerExt.info(fName+".index="+index);
            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorBlue1);
            if(isPatreon){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
            }
            else if(isPatreonGuild){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
            }else {
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
            }
            ACTION_INDEX=-1;
            boolean hasAction=false,hasName=false,hasText=false;
            if(gNewUserProfile.cCustomAction.isEmpty()){
                embed.addField("Action","(is empty)",false);
            }else
            if(index<0||index>=gNewUserProfile.cCustomAction.size()){
                embed.addField("Action","(invalid index)",false);
            }
            else {
                try {
                    action=gNewUserProfile.cCustomAction.getAction(index); ACTION_INDEX=index;
                    loggerExt.info(fName+". ACTION_INDEX="+ ACTION_INDEX);
                    loggerExt.info(fName+". ACTION="+ action.getJSON());
                    String name=action.getName();
                    if(name.isBlank()||name.isEmpty()){
                        embed.addField("Action","index:"+index+"\nname: (blank)",false);
                    }else{
                        embed.addField("Action","index:"+index+"\nname: "+name,false);
                        hasName=true;
                    }
                    hasAction=true;
                    String text=action.getText1();
                    if(text.isBlank()||text.isEmpty()){
                        embed.addField("Text (Self)","(blank)",false);
                    }else{
                        embed.addField("Text (Self)",text,false);
                        hasText=true;
                    }
                    String text2=action.getText2();
                    if(text2.isBlank()||text2.isEmpty()){
                        embed.addField("Text (Targeted)","(blank)",false);
                    }else{
                        embed.addField("Text (Targeted)",text2,false);
                        hasText=true;
                    }
                }catch (Exception e){
                    loggerExt.error(fName + ".exception=" + e);
                    loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    embed.addField("Action","(error)",false);
                }
                desc= global.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" enter name";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasMemo)+" enter self text";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasExclamation)+" enter targeted text";
                desc+="\n"+ global.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" remove action";
                embed.addField("Options",desc,false);
            }
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            if(hasAction){
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
            }
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+asCodepoints);isMenuLevel=true;
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSetup();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                dmInputActionName();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                dmInputActionText1();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasExclamation))){
                                dmInputActionText2();
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                removeAction(index);
                                menuCustomActions();
                            }
                            else{
                                menuAction(index);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        llMessageDelete(message);
                    });
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuAction_Somebody(int index){
        String fName="[menuAction_Somebody]";
        try{
            loggerExt.info(fName+".index="+index);
            EmbedBuilder embed=new EmbedBuilder();String desc="";StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorBlue1);
            if(isPatreon){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
            }
            else if(isPatreonGuild){
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
            }else {
                embed.setTitle(customActionTitle+" "+ global.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
            }
            ACTION_INDEX=-1;
            boolean hasAction=false,hasName=false,hasText=false;
            if(gNewUserProfile.cCustomAction.isEmpty()){
                embed.addField("Action","(is empty)",false);
            }else
            if(index<0||index>=gNewUserProfile.cCustomAction.size()){
                embed.addField("Action","(invalid index)",false);
            }
            else {
                try {
                    action=gNewUserProfile.cCustomAction.getAction(index); ACTION_INDEX=index;
                    loggerExt.info(fName+". ACTION_INDEX="+ ACTION_INDEX);
                    loggerExt.info(fName+". ACTION="+ action.getJSON());
                    String name=action.getName();
                    if(name.isBlank()||name.isEmpty()){
                        embed.addField("Action","index:"+index+"\nname: (blank)",false);
                    }else{
                        embed.addField("Action","index:"+index+"\nname: "+name,false);
                        hasName=true;
                    }
                    hasAction=true;
                    String text=action.getText1();
                    if(text.isBlank()||text.isEmpty()){
                        embed.addField("Text (Self)","(blank)",false);
                    }else{
                        embed.addField("Text (Self)",text,false);
                        hasText=true;
                    }
                    String text2=action.getText2();
                    if(text2.isBlank()||text2.isEmpty()){
                        embed.addField("Text (Targeted)","(blank)",false);
                    }else{
                        embed.addField("Text (Targeted)",text2,false);
                        hasText=true;
                    }
                }catch (Exception e){
                    loggerExt.error(fName + ".exception=" + e);
                    loggerExt.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    embed.addField("Action","(error)",false);
                }
            }
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            lsMessageHelper.lsMessageAddReactions(message, global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+asCodepoints);isMenuLevel=true;
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSetup();
                            }
                            else{
                                menuAction(index);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                        llMessageDelete(message);
                    });
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void dmInputActionName(){
        String fName="[dmInputActionName]";
        loggerExt.info(fName);
        try{
            String value=action.getName();;Message message;
            if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current name is "+value+".\nPlease enter a new name or enter !cancel to leave it as it is or enter !clear to remove it.", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter a name.", llColorBlue1);
            }
            global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerExt.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuAction(ACTION_INDEX);return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                action.clearName();
                            }
                            else{
                                action.setName(content);
                            }
                            if(ACTION_INDEX>-1){
                                saveProfile();
                                menuAction(ACTION_INDEX);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }
    private void dmInputActionText1(){
        String fName="[dmInputActionText1]";
        loggerExt.info(fName);
        try{
            String value=action.getText1();Message message;
            if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current self action text is "+value+".\nPlease enter a new text or enter !cancel to leave it as it is or enter !clear to remove it.\nSome words are automatically replaced with users mention before posting it, `!WEARER`==wearer, `!USER`==who triggered the command, `!CARETAKER` wearer's caretaker.", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter self action text.\nSome words are automatically replaced with users mention before posting it, `!WEARER`==wearer, `!USER`==who triggered the command, `!CARETAKER` wearer's caretaker.", llColorBlue1);
            }
            global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerExt.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuAction(ACTION_INDEX);return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                action.clearText1();
                            }
                            else{
                                action.setText1(content);
                            }
                            if(ACTION_INDEX>-1){
                                saveProfile();
                                menuAction(ACTION_INDEX);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }
    private void dmInputActionText2(){
        String fName="[dmInputActionText2]";
        loggerExt.info(fName);
        try{
            String value=action.getText2();Message message;
            if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current targeted action text is "+value+".\nPlease enter a new text or enter !cancel to leave it as it is or enter !clear to remove it.\nSome words are automatically replaced with users mention before posting it, `!WEARER`==wearer, `!USER`==who triggered the command, `!CARETAKER` wearer's caretaker.", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter targeted action text.\nSome words are automatically replaced with users mention before posting it, `!WEARER`==wearer, `!USER`==who triggered the command, `!CARETAKER` wearer's caretaker.", llColorBlue1);
            }
            global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            loggerExt.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuAction(ACTION_INDEX);return;
                            }else if(content.equalsIgnoreCase("!clear")){
                               action.clearText2();
                            }
                            else{
                                action.setText2(content);
                            }
                            if(ACTION_INDEX>-1){
                                saveProfile();
                                menuAction(ACTION_INDEX);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }
    private void dmInputNewAction(int mode){
        String fName="[dmInputNewActio]";
        try{
            loggerExt.info(fName+".mode="+mode);
            Message message;
            isPatreon= lsPatreon.hasPatreonTierOrAbove(global,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(global,gGuild,69);
            loggerExt.info(fName+".isPatreon="+isPatreon+", isPatreonGuild="+isPatreonGuild);
            if((isPatreon||isPatreonGuild)&&gNewUserProfile.cCustomAction.size()>= maxActions){
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Reached max actions count!",llColorRed_Cinnabar);
                return;
            }
            else if(!isPatreon&&!isPatreonGuild&&gNewUserProfile.cCustomAction.size()>= maxNormalUserActions){
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Reached max actions count! Patreon users can extend this limit.",llColorRed_Cinnabar);
                return;
            }
            if(mode==0||mode==1){
                action=new entityCustomActions.ACTION();
            }
            if(mode==1){
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a name for action that will be used to call it.", llColorBlue1);
                Message finalMessage = message;
                global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                loggerExt.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuAction(ACTION_INDEX);return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    action.clearName();
                                }
                                else{
                                   action.setName(content);
                                }
                                dmInputNewAction(2);
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(finalMessage);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(finalMessage);
                        });
            }
            if(mode==2){
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a text for self action.\nSome words are automatically replaced with users mention before posting it, `!WEARER`==wearer, `!USER`==who triggered the command, `!CARETAKER` wearer's caretaker.", llColorBlue1);
                Message finalMessage = message;
                global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                loggerExt.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuAction(ACTION_INDEX);return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    action.clearText1();
                                }
                                else{
                                    action.setText1(content);
                                }
                                dmInputNewAction(3);
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(finalMessage);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(finalMessage);
                        });
            }
            if(mode==3){
                message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a text for targeted action.\nSome words are automatically replaced with users mention before posting it, `!WEARER`==wearer, `!USER`==who triggered the command, `!CARETAKER` wearer's caretaker.", llColorBlue1);
                Message finalMessage = message;
                global.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                loggerExt.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuAction(ACTION_INDEX);return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                   action.clearText2();
                                }
                                else{
                                    action.setText2(content);
                                }
                                dmInputNewAction(4);
                            }catch (Exception e3){
                                loggerExt.error(fName + ".exception=" + e3);
                                loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(finalMessage);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(finalMessage);
                        });
            }
            if(mode==4){
                gNewUserProfile.cCustomAction.addAction(action);
                saveProfile();
                menuAction(gNewUserProfile.cCustomAction.size()-1);
            }

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }
    private void removeAction(int index){
        String fName="[removeAction]";
        try{
            loggerExt.info(fName+".index="+index);
            gNewUserProfile.cCustomAction.remAction(index);
            if(ACTION_INDEX>index){
                ACTION_INDEX--;
            }
            saveProfile();
            action=gNewUserProfile.cCustomAction.getAction(ACTION_INDEX);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }
    private void clearActionText(){
        String fName="[clearActionText]";
        try{
            loggerExt.info(fName);
            if(ACTION_INDEX>=0){
                action.reset();
                saveProfile();
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
        }
    }
    int intDefaultMinutes=5;
    Calendar gCalendarToday;
    private void getTodayDate(){
        String fName="[getTodayDate]";
        loggerExt.info(fName);
        gCalendarToday = Calendar.getInstance();
        loggerExt.info(fName + ".today:"+gCalendarToday.get(gCalendarToday.YEAR)+"|"+gCalendarToday.get(gCalendarToday.MONTH)+"|"+gCalendarToday.get(gCalendarToday.DAY_OF_MONTH)+"@"+gCalendarToday.get(gCalendarToday.HOUR_OF_DAY)+gCalendarToday.get(gCalendarToday.MINUTE));
    }

    private void rHelp(String command) {
        String fName = "[help]";
        loggerExt.info(fName);
        loggerExt.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+"diaper <@wearer>";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorPink1);
        embed.setTitle(sRTitle);
        if(command.equalsIgnoreCase("caretaker")||command.equalsIgnoreCase("owner")){
            quickSummonWithSpace=llPrefixStr+"diaper caretaker";
            embed.addField("Add (Wearer side)","`" + quickSummonWithSpace + " add <@User>  gives @User a caretaker offer. They still need to accept it",false);
            embed.addField("Remove (Wearer side)","`" + quickSummonWithSpace + " remove ` removes the suggested caretaker, works while they did nto accept it.",false);
            embed.addField("Runaway (Wearer side)","`"+quickSummonWithSpace+" runaway` resets the profile and removes the caretaker.",false);
            quickSummonWithSpace=llPrefixStr+"diaper <@Wearer> caretaker";
            embed.addField("Accept (Caretaker side)","`" + quickSummonWithSpace + " accept` accepts the caretaker offer from wearer.",false);
            embed.addField("Reject (Caretaker side)","`" + quickSummonWithSpace + " reject` rejects the caretaker offer from wearer.",false);
            embed.addField("Release (Caretaker side)","`" + quickSummonWithSpace + " release` releases @wearer from you.",false);
        }
        else if(command.equalsIgnoreCase("setup")) {
            quickSummonWithSpace = llPrefixStr + "diaper <@wearer> setup";
            embed.addField("Wetness", "`" + quickSummonWithSpace + " wetenable/wetdisable` enable or disable for wearer to get wet" + "\n`" + quickSummonWithSpace + " wetchance [0-25]` changes the chance for the wearer to get wet from action, 0 to disable it", false);
            embed.addField("Messiness", "`" + quickSummonWithSpace + " messenable/messdisable` enable or disable for wearer to get messy" + "\n`" + quickSummonWithSpace + " messchance [0-25]` changes the chance for the wearer to get mess from action, 0 to disable it", false);
            embed.addField("Access", "`" + quickSummonWithSpace + " " + ACCESSLEVEL.Public.getString() + "/" + ACCESSLEVEL.Private.getString() + "/" + ACCESSLEVEL.Caretaker.getString() + "` to set the diaper access.", false);
            embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
        }else if(command.equalsIgnoreCase("access")){
            quickSummonWithSpace=llPrefixStr+"diaper <@wearer>";
            embed.addField("Access","`" + quickSummonWithSpace + " setup "+ACCESSLEVEL.Public.getString()+"/"+ACCESSLEVEL.Private.getString()+"/"+ACCESSLEVEL.Caretaker.getString()+"` to set the diaper access.\nIn public mode everyone has access.\nIn private mode only wearer and their caretaker have most access.\nIn caretaker mode only caretaker has most access.",false);
            embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
        }else{
            embed.addField("Display","`" + quickSummonWithSpace + " display` display the wearer diaper status",false);
            embed.addField("Lock/Unlock","`" + quickSummonWithSpace + " lock/unlock` locking or unlocking wearer diaper.\nThe wearer can lock&unlock while access mode is not set to caretaker.",false);
            quickSummonWithSpace = llPrefixStr + "diaper <@wearer> setup";
            embed.addField("Wetness", "`" + quickSummonWithSpace + " wetenable/wetdisable` enable or disable for wearer to get wet" + "\n`" + quickSummonWithSpace + " wetchance [0-25]` changes the chance for the wearer to get wet from action, 0 to disable it", false);
            embed.addField("Messiness", "`" + quickSummonWithSpace + " messenable/messdisable` enable or disable for wearer to get messy" + "\n`" + quickSummonWithSpace + " messchance [0-25]` changes the chance for the wearer to get mess from action, 0 to disable it", false);
            embed.addField("Access","`" + quickSummonWithSpace+" "+ACCESSLEVEL.Public.getString()+"/"+ACCESSLEVEL.Private.getString()+"/"+ACCESSLEVEL.Caretaker.getString()+"` to set the diaper access.",false);
            quickSummonWithSpace=llPrefixStr+"diaper caretaker";
            embed.addField("Add (Wearer side)","`" + quickSummonWithSpace + " add <@User>  gives @User a caretaker offer. They still need to accept it",false);
            embed.addField("Remove (Wearer side)","`" + quickSummonWithSpace + " remove ` removes the suggested caretaker, works while they did nto accept it.",false);
            embed.addField("Runaway (Wearer side)","`"+quickSummonWithSpace+" runaway` resets the profile and removes the caretaker.",false);
            quickSummonWithSpace=llPrefixStr+"diaper <@Wearer> caretaker";
            embed.addField("Accept (Caretaker side)","`" + quickSummonWithSpace + " accept` accepts the caretaker offer from wearer.",false);
            embed.addField("Reject (Caretaker side)","`" + quickSummonWithSpace + " reject` rejects the caretaker offer from wearer.",false);
            embed.addField("Release (Caretaker side)","`" + quickSummonWithSpace + " release` releases @wearer from you.",false);
            embed.addField("Info", "<@wearer> is an optional value, it represents how you need to mention somebody in order to apply the command to them instead yourself.", false);
            if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                embed.addField("Guild Setup","To access the guild setup `"+llPrefixStr+"diaper guild`"+"\nTo add elements or view the list `"+llPrefixStr+"diaper guild commandroles|targetroles|allowedchannels|exceptionmembers add|rem|list <@mention>`\n\tcommandroles, list of roles who can give diaper commands\n\ttargetroles, list of roles who can get targeted with diaper commands\n\tallowedchannels, channels where the diaper has effect, if used the diaper wont work on channels not part of the list\n\texceptionmembers, members who the diaper effect does not work \n\t!!!empty list means no restrictions, it means everyone has access" , false);
            }
        }
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(global,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }

    private void rRunAway(){
        String fName = "[rRunAway]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loggerExt.info(fName + ".do permalock");
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(sRTitle);embed.setColor(llColorOrange);
        embed.setDescription("Are you sure you want to do a runaway?\nBesides freeing you from your caretaker, it will also resets your profile."+ global.emojis.getEmoji("unlock")+" for yes/"+ global.emojis.getEmoji("lock")+" for no.");
        Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        message.addReaction(global.emojis.getEmoji("lock")).queue();
        message.addReaction(global.emojis.getEmoji("unlock")).queue();
        global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        loggerExt.warn(fName+"name="+name);
                        String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        loggerExt.warn(fName+"asCodepoints="+name);
                        if(name.equalsIgnoreCase(global.emojis.getEmoji("unlock"))) {
                            gNewUserProfile.runaway();
                            if(!saveProfile()){
                                sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
                            new rdRestrictions(global,gCommandEvent,false,"",true);
                            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("!USER has run away! Its free, has no caretaker."), llColorRed);
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
    }
    private void addOwner(){
        String fName = "[addOwner]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(gNewUserProfile.cCaretaker.hasCaretaker()){
           sendOrUpdatePrivateEmbed(sRTitle,"Already have a caretaker", llColorRed); return;
        }
        List<Member> members=gCommandEvent.getMessage().getMentionedMembers();
        if(members.isEmpty()){
            loggerExt.warn(fName + ".no mention");
           sendOrUpdatePrivateEmbed(sRTitle,"No mention!", llColorRed); return;
        }
        Member member=members.get(0);
        loggerExt.info(fName + ".member:"+member.getId()+"|"+member.getUser().getName());
        if(member.getId().equals(gUser.getId())){
            loggerExt.warn(fName + ".owner cant be the wearer");
           sendOrUpdatePrivateEmbed(sRTitle,"You can't name yourself as caretaker!", llColorRed); return;
        }
        gNewUserProfile.cCaretaker.setAccepted(false).setCaretakerId(member);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
       sendOrUpdatePrivateEmbed(sRTitle,"Added "+member.getAsMention()+" as your caretaker. Now they need to confirm it!", llColorPurple1);
        Message message=llSendQuickEmbedMessageResponse(member.getUser(),sRTitle,stringReplacer("!USER added you as their caretaker! Please confirm or deny it by typing in the server channel. \nAccept:`!>diaper "+"!USER caretaker accept`\nReject `!>diaper "+"!USER caretaker reject`. Or use the reactions."), llColorPurple1);
        message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
        message.addReaction(lsUnicodeEmotes.unicode_ThumbsDown).queue();
        waitForKeyholderResponse(message,member);
    }
    private void removeOwner(){
        String fName = "[removeOwner]";
        loggerExt.info(fName);
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gNewUserProfile.cCaretaker.hasCaretaker()){
            loggerExt.warn(fName + ".no owner");
           sendOrUpdatePrivateEmbed(sRTitle,"Don't have any owner request", llColorRed); return;
        }
        if(gNewUserProfile.cCaretaker.isAccepted()){
            loggerExt.info(fName + ".owner accepted");
           sendOrUpdatePrivateEmbed(sRTitle,"Sorry, you can't undo this action once the owner accepted your request. Only they can free you from ownership.", llColorRed); return;
        }
        gNewUserProfile.cCaretaker.setAccepted(false).setCaretakerId("");
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
       sendOrUpdatePrivateEmbed(sRTitle,"Removed your owner request.", llColorPurple1);
    }
    private void acceptSub(Member mtarget){
        String fName = "[acceptSub]";
        loggerExt.info(fName);
        User target=mtarget.getUser();
        loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile"); return;}
        if(!gNewUserProfile.cCaretaker.hasCaretaker()){
            loggerExt.warn(fName + ".no owner");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed); return;
        }
        if(!gUser.getId().equalsIgnoreCase(gNewUserProfile.cCaretaker.getCaretakerID())){
            loggerExt.warn(fName + ".not a match");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed); return;
        }
        if(gNewUserProfile.cCaretaker.isAccepted()){
            loggerExt.info(fName + ".owner accepted");
           sendOrUpdatePrivateEmbed(sRTitle,"You already accepted this", llColorRed); return;
        }
        gNewUserProfile.cCaretaker.setAccepted(true);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
       sendOrUpdatePrivateEmbed(sRTitle,"You accepted "+target.getAsMention()+" leash!" ,llColorGreen2);
        llSendQuickEmbedMessage(target,sRTitle,stringReplacer("!USER accepted your ownership contract!"), llColorGreen2);
    }
    private void rejectSub(Member mtarget){
        String fName = "[rejectSub]";
        loggerExt.info(fName);
        User target=mtarget.getUser();
        loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile"); return;}
        if(!gNewUserProfile.cCaretaker.hasCaretaker()){
            loggerExt.warn(fName + ".no owner");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed); return;
        }
        if(!gUser.getId().equalsIgnoreCase(gNewUserProfile.cCaretaker.getCaretakerID())){
            loggerExt.warn(fName + ".not a match");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed); return;
        }
        if(gNewUserProfile.cCaretaker.isAccepted()){
            loggerExt.info(fName + ".owner accepted");
           sendOrUpdatePrivateEmbed(sRTitle,"You already accepted this", llColorRed); return;
        }
        gNewUserProfile.cCaretaker.setCaretakerId("");
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
       sendOrUpdatePrivateEmbed(sRTitle,"You rejected "+target.getAsMention()+" leash!" ,llColorRed_CoralPink);
        llSendQuickEmbedMessage(target,sRTitle,stringReplacer("!USER rejected your ownership contract!"), llColorRed_CoralPink);
    }
    private void releaseSub(Member mtarget){
        String fName = "[releaseKeyHolder]";
        loggerExt.info(fName);
        loggerExt.info(fName);
        User target=mtarget.getUser();
        loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile"); return;}
        if(!gNewUserProfile.cCaretaker.hasCaretaker()){
            loggerExt.warn(fName + ".no owner");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed); return;
        }
        if(!gUser.getId().equalsIgnoreCase(gNewUserProfile.cCaretaker.getCaretakerID())){
            loggerExt.warn(fName + ".not a match");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied", llColorRed); return;
        }
        if(!gNewUserProfile.cCaretaker.isAccepted()){
            loggerExt.info(fName + ".owner not accepted");
           sendOrUpdatePrivateEmbed(sRTitle,"You not accepted this, yet.", llColorRed); return;
        }
        gNewUserProfile.cCaretaker.setCaretakerId("").setAccepted(false);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
       sendOrUpdatePrivateEmbed(sRTitle,"You released "+target.getAsMention()+" leash!" ,llColorGreen2);
        llSendQuickEmbedMessage(target,sRTitle,stringReplacer("!USER releases you from ownership contract!"), llColorGreen2);

    }

    private void listAuth(Member mtarget){
        String fName = "[removeOwner]";
        loggerExt.info(fName);
        User target=mtarget.getUser();
        loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile"); return;}
        EmbedBuilder embed= new EmbedBuilder();
        embed.setColor(llColorBlue1);
        embed.setTitle(target.getName()+" auth status");
        if(gNewUserProfile.cCaretaker.hasCaretaker()){
            embed.addField("Owner",llGetMemberMention(gGuild,gNewUserProfile.cCaretaker.getCaretakerID()),true);
        }

        String access;

        switch (gNewUserProfile.cProfile.getAccess()) {
            case Private:
                access = "private";
                break;
            case Caretaker:
                access = "caretaker";
                break;
            case Key:
                access = "key";
                break;
            case Exposed:
                access = "exposed";
                break;
            case Public:
                access = "public";
                break;
            case Protected:
                access = "protected";
                break;
            default:
                access = "invalid";
                break;
        }

        embed.addBlankField(false);
        embed.addField("Access",access,true);
        llSendMessageWithDelete(global,gTextChannel,embed);
    }

    private void rAccess(String command){
        String fName="[rAccess]";
        loggerExt.info(fName);
        loggerExt.info(fName + ".command="+command);
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&gNewUserProfile.isPetOwned()){
            loggerExt.warn(fName + ".no access");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied, you are owned!", llColorRed); return;
        }
        else if(command.equalsIgnoreCase(ACCESSLEVEL.Public.getString())){
            gNewUserProfile.cProfile.setAccess(ACCESSLEVEL.Public);
           sendOrUpdatePrivateEmbed(sRTitle,"You set your access to public.", llColorBlue1);
            llSendMessageWithDelete(global,gTextChannel,stringReplacer("!USER sets their access to "+ACCESSLEVEL.Public.getString()+"."));
        }
        else if(command.equalsIgnoreCase(ACCESSLEVEL.Private.getString())){
            gNewUserProfile.cProfile.setAccess(ACCESSLEVEL.Private);
           sendOrUpdatePrivateEmbed(sRTitle,"You set your access to private.", llColorBlue1);
            llSendMessageWithDelete(global,gTextChannel,stringReplacer("!USER sets their access to "+ACCESSLEVEL.Private.getString()+"."));
        }
        else if(command.equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())){
            gNewUserProfile.cProfile.setAccess(ACCESSLEVEL.Caretaker);
           sendOrUpdatePrivateEmbed(sRTitle,"You set your access to caretaker.", llColorBlue1);
            llSendMessageWithDelete(global,gTextChannel,stringReplacer("!USER sets their access to "+ ACCESSLEVEL.Caretaker.getString() +"."));
        }
        saveProfile();
    }
    private void rLock(String command){
        String fName="[rLock]";
        loggerExt.info(fName);
        loggerExt.info(fName + ".command="+command);
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile");
            sendOrUpdatePrivateEmbed(strTitle,"Can't get profile.", llColorRed);
            return;}
        if(!gIsOverride&&(gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&iDiaperInteractive.doPrevented2AccessOption2ArmsRestrained(global, gMember,gTextChannel)){
            loggerExt.info(".prevented do to restraints");
            sendOrUpdatePrivateEmbed(strTitle,"Can't manipulate your locks do to restraints.", llColorRed);
            return;
        }
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            loggerExt.info(fName + ".can't use do to access owner");
            sendOrUpdatePrivateEmbed(strTitle,"Can't manipulate your locks as access set to caretaker.", llColorRed);
            return;
        }else
        if(command.equalsIgnoreCase("lock")){
            if(gIsOverride||!gNewUserProfile.cProfile.isLocked()){
                loggerExt.info(fName + ".locking");
                gNewUserProfile.cProfile.lock(gMember);
                sendOrUpdatePrivateEmbed(strTitle,"You locked your diaper!", llColorBlue1);
                userNotification( stringReplacer("!USER has locked their diaper. Hope they wont lose their key."));
            }else{
                loggerExt.info(fName + ".not unlocked");
                sendOrUpdatePrivateEmbed(strTitle,"You can't lock that is already locked!", llColorRed);
            }
        }
        else if(command.equalsIgnoreCase("unlock")){
            if(!gIsOverride&&gNewUserProfile.cTimelock.isTimeLocked()){
                loggerExt.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(strTitle,"Can't unlock do to timelocked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                loggerExt.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Can't manipulate your locks due to they locked by !LOCKER"), llColorRed);
                return;
            }else
            if(gNewUserProfile.cProfile.isLocked()){
                if(!gIsOverride&&gNewUserProfile.cProfile.getAccess()==ACCESSLEVEL.Exposed){
                    loggerExt.info(fName + ".can't unlock do to exposed access");
                    sendOrUpdatePrivateEmbed(strTitle,"Can't unlock your diaper due to exposed access!", llColorRed);
                }
                else{
                    loggerExt.info(fName + ".unlocking");
                    gNewUserProfile.cProfile.unlock();
                    sendOrUpdatePrivateEmbed(strTitle,"You unlocked your diaper.", llColorBlue1);
                    userNotification( stringReplacer("!USER has unlocked their diaper. Someone should have placed the key in a safe."));
                }
            }else{
                loggerExt.info(fName + ".not locked");
                sendOrUpdatePrivateEmbed(strTitle,"You can't unlock that is not locked!", llColorRed);
            }
        }
        saveProfile();
    }


    private void rAccess(Member mtarget,String command){
        String fName="[rAccess]";
        loggerExt.info(fName);
        User target=mtarget.getUser();
        loggerExt.info(fName + ".command="+command);
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile"); return;}
            /*if(!gIsOverride&&gUserProfile.jsonUser.getJSONObject(nSuit).getString(nSuitType).equals(levelSuitSpecialToyOmega)){
                logger.info(fName + ".can't use do to special suit");
               sendOrUpdatePrivateEmbed(sRTitle,"Warning, they are wearing a special toy suit. First run `"+llPrefixStr+"suit <target> release` to take off the suit to change their access mode.", llColorRed);
                return;
            }*/
        if(!gIsOverride&&!gNewUserProfile.hasUserOwnerAccess(gUser)&&!gNewUserProfile.hasUserSecOwnerAccess(gUser)){
            loggerExt.warn(fName + ".no access");
           sendOrUpdatePrivateEmbed(sRTitle,"Denied! Only caretaker has access.", llColorRed); return;
        }
        else if(command.equalsIgnoreCase(ACCESSLEVEL.Public.getString())){
            gNewUserProfile.cProfile.setAccess(ACCESSLEVEL.Public);
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("!USER sets your access to public."), llColorBlue1);
            sendOrUpdatePrivateEmbed(strTitle,stringReplacer("You set !WEARER's access to "+ACCESSLEVEL.Public.getString()+"."), llColorBlue1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("!USER sets !WEARER's access to "+ACCESSLEVEL.Public.getString()+"."), llColorBlue1));
        }
        else if(command.equalsIgnoreCase(ACCESSLEVEL.Private.getString())){
            gNewUserProfile.cProfile.setAccess(ACCESSLEVEL.Private);
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("!USER sets your access to private."), llColorBlue1);
            sendOrUpdatePrivateEmbed(strTitle,stringReplacer("You set !WEARER's access to "+ACCESSLEVEL.Public.getString()+"."), llColorBlue1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("!USER sets !WEARER's access to "+ACCESSLEVEL.Private.getString()+"."),llColorBlue1));
        }
        else if(command.equalsIgnoreCase(ACCESSLEVEL.Caretaker.getString())){
            gNewUserProfile.cProfile.setAccess(ACCESSLEVEL.Caretaker);
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("!USER sets your access to caretaker."), llColorBlue1);
            sendOrUpdatePrivateEmbed(strTitle,stringReplacer("You set !WEARER's access to "+ACCESSLEVEL.Public.getString()+"."), llColorBlue1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("!USER sets !WEARER access to "+ ACCESSLEVEL.Caretaker.getString() +"."), llColorBlue1));
        }
        saveProfile();
    }
    private void rLock(Member mtarget,String command){
        String fName="[rLock]";
        loggerExt.info(fName);
        User target=mtarget.getUser();
        loggerExt.info(fName + ".target:"+target.getId()+"|"+target.getName());
        loggerExt.info(fName + ".command="+command);
        if(!getProfile()){
            loggerExt.error(fName + ".can't get profile");
            sendOrUpdatePrivateEmbed(strTitle,"Can't get profile.", llColorRed);
            return;}
        if(!gIsOverride&&gNewUserProfile.cTimelock.isTimeLocked()){
            loggerExt.info(fName + ".can't use do to locked by somebody");
            sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Can't unlock do to timelocked!"), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            loggerExt.info(fName + ".can't use do to locked by not you");
            sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Can't manipulate their locks due to they locked by !LOCKER"), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            loggerExt.info(fName + ".can't use do to access protected");
            sendOrUpdatePrivateEmbed(strTitle,stringReplacer("Can't manipulate their locks due to their access setting."), llColorRed);
            return;
        }else
        if(command.equalsIgnoreCase("lock")){
            if(gIsOverride||!gNewUserProfile.cProfile.isLocked()){
                loggerExt.info(fName + ".locking");
                gNewUserProfile.cProfile.lock(gMember);
                sendOrUpdatePrivateEmbed(strTitle,stringReplacer("You locked !WEARER's diaper!"), llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,stringReplacer("!USER locked your diaper!"), llColorBlue1);
                userNotification(stringReplacer("!USER locked !WEARER's diaper."));
            }else{
                loggerExt.info(fName + ".not unlocked");
                sendOrUpdatePrivateEmbed(strTitle,"You can't lock that is already locked!", llColorRed);
            }
        }
        else if(command.equalsIgnoreCase("unlock")){
            if(gNewUserProfile.cProfile.isLocked()){
                loggerExt.info(fName + ".unlocking");
                gNewUserProfile.cProfile.unlock();
                sendOrUpdatePrivateEmbed(strTitle,stringReplacer("You unlocked !WEARER's diaper!"), llColorBlue1);
                llSendQuickEmbedMessage(target,sRTitle,stringReplacer("!USER unlocked your diaper!"), llColorBlue1);
                userNotification(stringReplacer("!USER unlocked !WEARER's diaper."));
            }else{
                loggerExt.info(fName + ".not locked");
                sendOrUpdatePrivateEmbed(strTitle,"You can't unlock that is not locked!", llColorRed);
            }
        }
        saveProfile();
    }
    private void userNotification(String desc){
        String fName="[userNotification]";
        //logger.info(fName+"area="+area);
        loggerExt.info(fName+"desc="+desc);  llSendMessageWithDelete(global,gTextChannel,desc);
            /*if(gUserProfile.jsonUser.has(nNotification)){
                if(gUserProfile.jsonUser.getBoolean(nNotification)){

                }
            }*/
    }
  
    private void rSlashNT() {
        String fName = "[rSlashNT]";
        logger.info(fName);
        gCurrentInteractionHook=lsMessageHelper.lsDeferReply(gSlashCommandEvent,true);
        Member user=null;
        boolean subdirProvided=false;
        loadValues();
        for(OptionMapping option:gSlashCommandEvent.getOptions()){
            switch (option.getName()){
                case llCommonKeys.SlashCommandReceive.subdir:
                    subdirProvided=true;
                    break;
                case llCommonKeys.SlashCommandReceive.user:
                    if(option.getType()== OptionType.USER){
                        user=option.getAsMember();
                    }
                    break;
            }
        }
        if(user!=null&&gMember.getIdLong()!=user.getIdLong()){
            logger.info(fName + ".target="+gTarget.getId());
        }else{
            logger.info(fName + ".target=author");
        }
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        logger.info(fName + ".subdirProvided="+subdirProvided);
        menuSetup();
    }
    private void rInteraction() {
        String fName = "[rInteraction]";
        logger.info(fName);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsForward||gRawForward==null||gRawForward.isBlank()){
            sendOrUpdatePrivateEmbed(strTitle,"Opening DM",llColorBlue1);
            gCurrentInteractionHook=null;
            menuSetup();
            return;
        }
        switch (gRawForward){
            case "lock":case "unlock":
                if(gTarget==null){
                    rLock(gRawForward);
                }else{
                    rLock(gTarget,gRawForward);
                }
                break;
            case "setup":
                sendOrUpdatePrivateEmbed(strTitle,"Opening DM",llColorBlue1);
                gCurrentInteractionHook=null;
                menuSetup();
                break;
            case "actions":
                sendOrUpdatePrivateEmbed(strTitle,"Opening DM",llColorBlue1);
                gCurrentInteractionHook=null;
                menuCustomActions();
                break;
        }
    }

    private void setupWetEnable(Member mtarget,Boolean enable){
        String fName = "[setupWetEnable]";
        loggerExt.info(fName+"mtarget="+mtarget.getId());
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){
            loggerExt.info(fName+"denied");return;}
        loggerExt.info(fName+"enable="+enable);
        if(enable==gNewUserProfile.cWet.isEnabled()&&gNewUserProfile.cWet.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already enabled!", llColorPurple1);return;
        }
        if(enable==gNewUserProfile.cWet.isEnabled()&&!gNewUserProfile.cWet.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already disabled!", llColorPurple1);return;
        }
        gNewUserProfile.cWet.setEnabled(enable);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(enable){
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("Wetness enabled by !USER."), llColorGreen1);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Wetness enabled for !WEARER by !USER."), llColorGreen1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("Wetness enabled for !WEARER by !USER."), llColorGreen1));
        }else{
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("Wetness disabled by !USER."), llColorRed);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Wetness disabled for !WEARER by !USER."), llColorRed);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("Wetness disabled for !WEARER by !USER."), llColorRed));
        }
    }
    private void setupMessEnable(Member mtarget,Boolean enable){
        String fName = "[setupMessEnable]";
        loggerExt.info(fName+"mtarget="+mtarget.getId());
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){
            loggerExt.info(fName+"denied");return;}
        loggerExt.info(fName+"enable="+enable);
        if(enable==gNewUserProfile.cMessy.isEnabled()&&gNewUserProfile.cMessy.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already enabled!", llColorPurple1);return;
        }
        if(enable==gNewUserProfile.cMessy.isEnabled()&&!gNewUserProfile.cMessy.isEnabled()){
            loggerExt.info(fName+"same");
           sendOrUpdatePrivateEmbed(sRTitle, "It's already disabled!", llColorPurple1);return;
        }
        gNewUserProfile.cMessy.setEnabled(enable);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(enable){
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("Messy enabled for !WEARER by !USER."), llColorGreen1);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Messy enabled for !WEARER by !USER."), llColorGreen1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("Messy enabled for !WEARER by !USER."), llColorGreen1));
        }else{
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("Messy disabled for !WEARER by !USER."), llColorRed);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Messy disabled for !WEARER by !USER."), llColorRed);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("Messy disabled for !WEARER by !USER."), llColorRed));
        }
    }
    private void setupWetChance(Member mtarget,String option){
        String fName = "[setupWetChance]";
        loggerExt.info(fName+"mtarget="+mtarget.getId());
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){
            loggerExt.info(fName+"denied");return;}
        if(gTarget==null){
            loggerExt.info(fName+"self");
        }else{
            loggerExt.info(fName+"target");
        }
        loggerExt.info(fName+"option="+option);
        int number;
        if(option.isEmpty()){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        number= Integer.parseInt(option);
        if(number<0||number>25){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        gNewUserProfile.cWet.setChance(number);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(0<number){
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER wet themself when performing actions, updated by !USER."), llColorGreen1);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER wet themself when performing actions, updated by !USER."), llColorGreen1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER wet themself when performing actions, updated by !USER."), llColorGreen1));
        }else{
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("Chance for !WEARER wet theemself is disabled by !USER."), llColorRed);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Chance for !WEARER wet theemself is disabled by !USER."), llColorRed);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("Chance for !WEARER wet theemself is disabled by !USER."), llColorRed));
        }
    }
    private void setupMessChance(Member mtarget,String option){
        String fName = "[setupMessChance]";
        loggerExt.info(fName+"mtarget="+mtarget.getId());
        if (!loadedProfile()) {sendOrUpdatePrivateEmbed(sRTitle, "Failed to load  profile!", llColorRed);return; }
        loadDUserValues();
        if(!gIsOverride&&!accessApprove4Setup(gUser)){
            loggerExt.info(fName+"denied");return;}
        if(gTarget==null){
            loggerExt.info(fName+"self");
        }else{
            loggerExt.info(fName+"target");
        }
        loggerExt.info(fName+"option="+option);
        int number=0;
        if(option.isEmpty()){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        number= Integer.parseInt(option);
        if(number<0||number>25){
            sendOrUpdatePrivateEmbed(sRTitle,"Please mention a number between 0-25", llColorRed);return;}
        gNewUserProfile.cMessy.setChance(number);
        if(!saveProfile()){
            sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
        if(0<number){
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER mess themself when performing actions updated by !USER."), llColorGreen1);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER mess themself when performing actions updated by !USER."), llColorGreen1);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("1 out of "+number+" chance for !WEARER mess themself when performing actions updated by !USER."), llColorGreen1));
        }else{
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,stringReplacer("Chance of  !WEARER messing themself is disabled  by !USER."), llColorRed);
            sendOrUpdatePrivateEmbed(sRTitle,stringReplacer("Chance of  !WEARER messing themself is disabled by !USER."), llColorRed);
            llSendMessageWithDelete(global,gTextChannel,lsMessageHelper.lsGenerateEmbed(sRTitle,stringReplacer("Chance of  !WEARER messing themself is disabled by !USER."), llColorRed));
        }
    }

   

  
    private void waitForKeyholderResponse(Message message, Member keyholder){
        String fName = "[waitForKeyholderResponse]";
        loggerExt.info(fName);
        try{
            loggerExt.info(fName + "keyholder=" + keyholder.getId());
            loggerExt.info(fName + "wearer=" + gUser.getId());
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==keyholder.getIdLong()),
                    e -> {
                        try {
                            String codepoints = e.getReactionEmote().getAsCodepoints();
                            loggerExt.info(fName + "codepoints=" + codepoints);
                            if(gNewUserProfile.cCaretaker.hasCaretaker()||!gNewUserProfile.cCaretaker.getCaretakerID().equalsIgnoreCase(keyholder.getId())){
                                loggerExt.warn(fName + ".no owner");
                                llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"Denied!\nIts possible they change it or removed it!", llColorRed); return;
                            }
                            if (codepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)) {
                                llMessageDelete(e.getChannel(), e.getMessageId());
                                if(gNewUserProfile.cCaretaker.isAccepted()){
                                    loggerExt.info(fName + ".owner accepted");
                                    llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"You already accepted this", llColorRed); return;
                                }
                                gNewUserProfile.cCaretaker.setAccepted(true);
                                if(!saveProfile()){
                                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
                                llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,stringReplacer("You accepted !USER leash!") ,llColorGreen2);
                               sendOrUpdatePrivateEmbed(sRTitle,keyholder.getAsMention()+" accepted your ownership contract!", llColorGreen2);
                            }else
                            if (codepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_ThumbsDown)) {
                                if(gNewUserProfile.cCaretaker.isAccepted()){
                                    loggerExt.info(fName + ".owner accepted");
                                    llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,"You already accepted this", llColorRed); return;
                                }
                                gNewUserProfile.cCaretaker.setCaretakerId("");
                                if(!saveProfile()){
                                    sendOrUpdatePrivateEmbed(sRTitle,"Failed to write in Db!", llColorRed); return;}
                                llSendQuickEmbedMessage(keyholder.getUser(),sRTitle,stringReplacer("You rejected !USER leash!") ,llColorRed_CoralPink);
                               sendOrUpdatePrivateEmbed(sRTitle,keyholder.getAsMention()+" rejected your ownership contract!", llColorRed_CoralPink);
                            }else{
                                waitForKeyholderResponse(message,keyholder);
                            }
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }

                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, sRTitle, "Timeout for quick react. Us text command in guild.", llColorRed);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }



   


    

    

    private Boolean IsStringAMember(String str){
        String fName = "[IsStringAMember]";
        loggerExt.info(fName+"str="+str);
        try{
            Member u=gGuild.getMemberById(str);
            if(u!=null){
                loggerExt.info(fName+".is not null");return true;}
            loggerExt.info(fName+".is null");return false;
        }
        catch (Exception ex){ loggerExt.error(fName+"exception="+ex); return false;}
    }
    String strGuildSetup="Diaper Setup";
    private void menuGuild(){
        String fName="[menuGuild]";
        loggerExt.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="Select one option";
            if(vEnabled){
                desc+="\n:one: to disable this branch";
            }else{
                desc+="\n:one: to enable this branch";
            }
            desc+="\n:three: Clear channels list";
            desc+="\n:four: Clear banned users list";
            desc+="\n:six: Reset Restrictions";
            embed.setTitle(strGuildSetup);embed.setColor(llColorPurple2);
            embed.setDescription(desc);
            embed.addField("Done","Select :white_check_mark: to finish.",false);
            //embed.addField("Close","Select :x: to finish.",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            message.addReaction(global.emojis.getEmoji("one")).queue();
            //message.addReaction(gGlobal.emojis.getEmoji("two")).queue();
            message.addReaction(global.emojis.getEmoji("three")).queue();
            message.addReaction(global.emojis.getEmoji("four")).queue();
            message.addReaction(global.emojis.getEmoji("six")).queue();
            message.addReaction(lsUnicodeEmotes.unicode_WhiteCheckMark).queue();
            global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            loggerExt.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            loggerExt.warn(fName+"asCodepoints="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                loggerExt.warn(fName+"trigger="+name);
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldEnabled,!vEnabled);
                                loggerExt.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                                if(saveServerProfile()){
                                    String t;
                                    if(!vEnabled){
                                        t="enable";
                                    }else{
                                        t="disable";
                                    }
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Updated to "+t,llColorGreen2);
                                }else{
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to update",llColorRed_Imperial);
                                }
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                loggerExt.warn(fName+"trigger="+name);
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldAllowedChannels,new JSONArray());
                                loggerExt.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                                if(saveServerProfile()){
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Cleared channels list",llColorGreen2);
                                }else{
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to cleared channels list",llColorRed_Imperial);
                                }
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                loggerExt.warn(fName+"trigger="+name);
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldBannedUsers,new JSONArray());
                                loggerExt.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                                if(saveServerProfile()){
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Cleared forbidden users list",llColorGreen2);
                                }else{
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to cleared forbidden members list",llColorRed_Imperial);
                                }
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                loggerExt.warn(fName+"trigger="+name);
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldAllowedChannels,new JSONArray());
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldBannedUsers,new JSONArray());
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldCommandAllowedRoles,new JSONArray());
                                gBDSMCommands.diaper.gProfile.putFieldEntry(iRestraints.fieldTargetAllowedRoles,new JSONArray());
                                loggerExt.info(fName+"json updated="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
                                if(saveServerProfile()){
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Reset restrictions done",llColorGreen2);
                                }else{
                                    llSendQuickEmbedMessage(gUser,strGuildSetup,"Failed to clear restrictions",llColorRed_Imperial);
                                }
                            }else
                            if(asCodepoints.equalsIgnoreCase(lsUnicodeEmotes.unicode_WhiteCheckMark)){
                                loggerExt.warn(fName+"trigger="+name);
                                llSendQuickEmbedMessage(gUser, sRTitle, "Done", llColorGreen2);
                            }else{
                                menuGuild();
                            }
                            llMessageDelete(e.getChannel(),e.getMessageId());
                        }catch (Exception e3){
                            loggerExt.error(fName + ".exception=" + e3);
                            loggerExt.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }

                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                       sendOrUpdatePrivateEmbed(sRTitle, "Timeout", llColorRed);
                    });

        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessage(gUser,strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupAdd2Channel(boolean isAllowed){
        String fName="[setupAdd2Channel]";
        loggerExt.info(fName);
        try{
            List<TextChannel>channels=gCommandEvent.getMessage().getMentionedChannels();
            JSONArray array;String field="";

            field=iRestraints.fieldAllowedChannels;

            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull( field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
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
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                sendOrUpdatePrivateEmbed(strGuildSetup,"Updated channels list", llColorGreen2);
            }else{
                sendOrUpdatePrivateEmbed(strGuildSetup,"Failed to update channels list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupRem2Channel(boolean isAllowed){
        String fName="[setupRem2Channel]";
        loggerExt.info(fName);
        try{
            List<TextChannel>channels=gCommandEvent.getMessage().getMentionedChannels();
            JSONArray array;String field="";

            field=iRestraints.fieldAllowedChannels;

            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull( field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
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
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                sendOrUpdatePrivateEmbed(strGuildSetup,"Updated channels list", llColorGreen2);
            }else{
                sendOrUpdatePrivateEmbed(strGuildSetup,"Failed to update channels list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupAdd2Member(){
        String fName="[setupAdd2Member]";
        loggerExt.info(fName);
        try{
            List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( iRestraints.fieldBannedUsers)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(  iRestraints.fieldBannedUsers)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( iRestraints.fieldBannedUsers);
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
            gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                sendOrUpdatePrivateEmbed(strGuildSetup,"Updated members list", llColorGreen2);
            }else{
                sendOrUpdatePrivateEmbed(strGuildSetup,"Failed to update members list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupRem2Member(){
        String fName="[setupRem2Channel]";
        loggerExt.info(fName);
        try{
            List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( iRestraints.fieldBannedUsers)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(  iRestraints.fieldBannedUsers)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( iRestraints.fieldBannedUsers);
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
            gBDSMCommands.diaper.gProfile.jsonObject.put( iRestraints.fieldBannedUsers,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                sendOrUpdatePrivateEmbed(strGuildSetup,"Updated members list", llColorGreen2);
            }else{
                sendOrUpdatePrivateEmbed(strGuildSetup,"Failed to update members list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupViewList(String field){
        String fName="[setupViewList]";
        loggerExt.info(fName);
        try{
            loggerExt.info(fName+"field="+field);
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull( field)){
                loggerExt.info(fName+"null");
                if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in allowed channels list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in blocked users list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in command allowed roles list! Everyone can use the diaper commands.", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in target allowed roles list! Everyone can get targeted.", llColorRed_Imperial);
                return;
            }
            loggerExt.info(fName+"step 2");
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
            if(array.isEmpty()){
                loggerExt.info(fName+"empty");
                if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in allowed channels list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in blocked users list!", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in command allowed roles list! Everyone can use the diaper commands.", llColorRed_Imperial);
                if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                    sendOrUpdatePrivateEmbed(strGuildSetup,"Nothing in target allowed roles list! Everyone can get targeted.", llColorRed_Imperial);
                return;
            }
            String tmp="";
            for(int i=0;i<array.length();i++){
                if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                    tmp+=" "+ lsChannelHelper.lsGetChannelMentionById(gGuild,array.getString(i));
                if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                    tmp+=" "+llGetMemberMention(gGuild,array.getString(i));
                if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                    tmp+=" "+ lsRoleHelper.lsGetRoleMentionByID(gGuild,array.getString(i));
                if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                    tmp+=" "+lsRoleHelper.lsGetRoleMentionByID(gGuild,array.getString(i));
            }
            if(field.equalsIgnoreCase(iRestraints.fieldAllowedChannels))
                sendOrUpdatePrivateEmbed(strGuildSetup,"Allowed channels list: "+tmp, llColorBlue3);
            if(field.equalsIgnoreCase(iRestraints.fieldBannedUsers))
                sendOrUpdatePrivateEmbed(strGuildSetup,"Blocked users list: "+tmp, llColorBlue3);
            if(field.equalsIgnoreCase(iRestraints.fieldCommandAllowedRoles))
                sendOrUpdatePrivateEmbed(strGuildSetup,"Allowed command roles: "+tmp, llColorBlue3);
            if(field.equalsIgnoreCase(iRestraints.fieldTargetAllowedRoles))
                sendOrUpdatePrivateEmbed(strGuildSetup,"Allowed target roles: "+tmp, llColorBlue3);
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupAdd2Role(String field){
        String fName="[setupAdd2Role]";
        loggerExt.info(fName);
        try{
            loggerExt.info(fName+" field="+ field);
            List<Role>roles=gCommandEvent.getMessage().getMentionedRoles();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has(  field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(  field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put(  field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray(  field);
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
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                sendOrUpdatePrivateEmbed(strGuildSetup,"Updated roles list", llColorGreen2);
            }else{
                sendOrUpdatePrivateEmbed(strGuildSetup,"Failed to update roles list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    private void setupRem2Role(String field){
        String fName="[setupRem2Channel]";
        loggerExt.info(fName);
        try{
            loggerExt.info(fName+" field="+ field);
            List<Role>roles=gCommandEvent.getMessage().getMentionedRoles();
            JSONArray array;
            if(!gBDSMCommands.diaper.gProfile.jsonObject.has( field)||gBDSMCommands.diaper.gProfile.jsonObject.isNull(field)){
                gBDSMCommands.diaper.gProfile.jsonObject.put( field,new JSONArray());
            }
            array=gBDSMCommands.diaper.gProfile.jsonObject.getJSONArray( field);
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
            gBDSMCommands.diaper.gProfile.jsonObject.put( field,array);gBDSMCommands.diaper.gProfile.isUpdated=true;
            if(saveServerProfile()){
                sendOrUpdatePrivateEmbed(strGuildSetup,"Updated roles list", llColorGreen2);
            }else{
                sendOrUpdatePrivateEmbed(strGuildSetup,"Failed to update roles list", llColorRed_Imperial);
            }
        } catch (Exception e) {
            loggerExt.error(fName+".exception=" + e);
            loggerExt.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            sendOrUpdatePrivateEmbed(strGuildSetup,"Exception:"+e.toString(), llColorRed_Imperial);
        }
    }
    Boolean vEnabled=false;JSONArray vAllowedChannels=null;JSONArray vBannedUsers=null;
    private void loadValues(){
        String fName = "[loadValues]";
        loggerExt.info(fName);
        loggerExt.info(fName+"json="+gBDSMCommands.diaper.gProfile.jsonObject.toString());
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldEnabled)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldEnabled)){
            vEnabled = gBDSMCommands.diaper.gProfile.getFieldEntryAsBoolean(iRestraints.fieldEnabled);
        }
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldBannedUsers)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldBannedUsers)){
            vBannedUsers= gBDSMCommands.diaper.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldBannedUsers);
        }
        if(gBDSMCommands.diaper.gProfile.jsonObject.has(iRestraints.fieldAllowedChannels)&&!gBDSMCommands.diaper.gProfile.jsonObject.isNull(iRestraints.fieldAllowedChannels)){
            vAllowedChannels= gBDSMCommands.diaper.gProfile.getFieldEntryAsJSONArray(iRestraints.fieldAllowedChannels);
        }



    }
    private Boolean saveServerProfile(){
        String fName="[saveServerProfile]";
        loggerExt.info(fName);
        global.putGuildSettings(gGuild,gBDSMCommands.diaper.gProfile);
        if(gBDSMCommands.diaper.gProfile.saveProfile()){
            loggerExt.info(fName + ".success");return  true;
        }
        loggerExt.warn(fName + ".failed");return false;
    }

    ////////////////////////
}

}
