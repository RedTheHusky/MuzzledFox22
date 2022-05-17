package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.emotes.lcEmote;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.PostSynthesizer.rdVoicePostSynthesizer;
import restraints.models.enums.*;
import restraints.in.iLock;
import restraints.in.iRestraints;
import restraints.in.iSuit;
import restraints.models.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdRestraints extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper,  iRestraints {
    EventWaiter gWaiter;lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
	String sRTitle="DiscordRestraints",gCommand="rd";
    public rdRestraints(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "DiscordRestraints";
        this.help = "Restraining your pet with gag and cuffs. A fun bdsm social interactive.";
        if(!quickAlias.equalsIgnoreCase("rd")){
            this.aliases = new String[]{quickAlias,"restraints"};
        }else{
            this.aliases = new String[]{quickAlias,"rd","restraints"};
        }
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        //this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_Between;
    }
    public rdRestraints(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdRestraints(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent gCommandEvent) {
        String fName="[execute]";
        logger.info(fName);
        if(llDebug){
            logger.info(fName+".global debug true");return;
        }
        Runnable r = new runLocal(gCommandEvent);
        new Thread(r).start();
    }
    protected class runLocal  extends rdExtension implements Runnable {
        String cName="[runLocal]";
        public runLocal(CommandEvent ev){
            logger.info(".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sRTitle,gUser);
        }
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try{
               setTitleStr(rdRestraints.this.sRTitle);setPrefixStr(rdRestraints.this.llPrefixStr);setCommandStr(rdRestraints.this.gCommand);
                lsUsefullFunctions.setThreadName4Display("rdRestraints");
                gBDSMCommands.restraints.isAutoSave =false;
                boolean isInvalidCommand=true;
                if(glUsercommand!=null){
                    //glInteractionResponse=glUsercommand.respondWithAck().send();
                    String name=glUsercommand.getName();
                    boolean isEmptyMembers=glUsercommand.isEmptyMembers();
                    logger.info(fName+"isEmptyMembers="+isEmptyMembers);
                    if(!isEmptyMembers&&gMember.getIdLong()!=glUsercommand.getMembers().get(0).getIdLong()){
                        gTarget=glUsercommand.getMembers().get(0);
                    }
                    if(name.equalsIgnoreCase("rd")){
                        glInteractionResponse=glUsercommand.respondWithMessage("Opening rd quick menu, please check DM.",true).send();
                        dmMainQuickMenu();
                    }
                }
                else if(gSlashCommandEvent!=null){
                    rSlashNT();
                }else{
                    if(gCommandEvent.getArgs().isEmpty()){
                        logger.info(fName+".Args=0");
                        rHelp("main");
                        isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        this.gItems = gCommandEvent.getArgs().split("\\s+");
                        if(gCommandEvent.getArgs().contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){ gIsOverride =true;}
                        logger.info(fName + ".gItems.size=" + gItems.length);
                        logger.info(fName + ".gItems[0]=" + gItems[0]);
                        StringBuilder forward= new StringBuilder();
                        for(int i=1;i<gItems.length;i++){
                            if(i==1){
                                forward = new StringBuilder(gItems[i]);
                            }else{
                                forward.append(" ").append(gItems[i]);
                            }
                        }
                        if(gItems.length==1&&gItems[0].equalsIgnoreCase("help")) {
                            rHelp("main");
                            isInvalidCommand = false;
                        }else
                        if(gItems[0].equalsIgnoreCase("server")||gItems[0].equalsIgnoreCase("guild")){
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
                                    case vList:
                                        action=0;
                                        break;
                                    case vAdd:
                                        action=1;
                                        break;
                                    case vSet:
                                        action=2;
                                        break;
                                    case vRem:
                                        action=-1;
                                        break;
                                    case vClear:
                                        action=-2;
                                        break;
                                }
                                if(group==1){
                                    if(action==0){
                                        getRDChannels(type,false);isInvalidCommand=false;
                                    }else{
                                        setRDChannel(type,action,gCommandEvent.getMessage());isInvalidCommand=false;
                                    }
                                }
                                else if(group==2){
                                    if(action==0){
                                        getRDRoles(type,false);isInvalidCommand=false;
                                    }else{
                                        setRDRole(type,action,gCommandEvent.getMessage());isInvalidCommand=false;
                                    }
                                }
                            }else{
                                menuGuild();isInvalidCommand=false;
                            }
                        }else
                        if(gItems[0].equalsIgnoreCase(vSetup)&&gItems.length==1){
                            //rHelp(vSetup);isInvalidCommand=false;
                            MenuGuildSetup();isInvalidCommand=false;
                        }else
                        if(gItems[0].equalsIgnoreCase("restrict")&&gItems.length>=2){
                            if(gItems[1].equalsIgnoreCase("set")&&gItems.length>=3){
                                if(gItems[2].equalsIgnoreCase("none")){
                                    setBDSMRestriction(0);isInvalidCommand=false;
                                }else
                                if(gItems[2].equalsIgnoreCase("nsfw")){
                                    setBDSMRestriction(1);isInvalidCommand=false;
                                }else
                                if(gItems[2].equalsIgnoreCase("denied")){
                                    setBDSMRestriction(2);isInvalidCommand=false;
                                }
                            }else
                            if(gItems[1].equalsIgnoreCase("get")){
                                getBDSMRestriction();isInvalidCommand=false;
                            }
                        }else
                        if(gItems[0].equalsIgnoreCase("restrict")){
                            getBDSMRestriction();isInvalidCommand=false;
                        }else
                        if(gItems[0].equalsIgnoreCase("getrestrict")){
                            getBDSMRestriction();isInvalidCommand=false;
                        }else
                        if(gItems.length>=2&&gItems[0].equalsIgnoreCase("setrestrict")&&gItems[1].equalsIgnoreCase("none")){
                            setBDSMRestriction(0);isInvalidCommand=false;
                        }else
                        if(gItems.length>=2&&gItems[0].equalsIgnoreCase("setrestrict")&&gItems[1].equalsIgnoreCase("nsfw")){
                            setBDSMRestriction(1);isInvalidCommand=false;
                        }else
                        if(gItems.length>=2&&gItems[0].equalsIgnoreCase("setrestrict")&&gItems[1].equalsIgnoreCase("denied")){
                            setBDSMRestriction(2);isInvalidCommand=false;
                        }

                        if(!isAdult&&bdsmRestriction==1){blocked();return;}
                        else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");
                            if(!gBDSMCommands.restraints.getEnable()){
                                logger.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                                logger.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                                logger.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                            }else{
                                if(gItems[1].equalsIgnoreCase("lock")||gItems[1].equalsIgnoreCase("unlock")){
                                    rLock(gTarget,gItems[1].toLowerCase());isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase(nUntie)||gItems[1].equalsIgnoreCase("untieall")){
                                    rUntieAll(gTarget,gItems[1].toLowerCase());isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("red")){
                                    if(gItems.length>2){
                                        if(gItems[2].equalsIgnoreCase("yes")){
                                            rRedRestraint(gTarget,false,true);
                                        }else
                                        if(gItems[2].equalsIgnoreCase("timelock")){
                                            rRedRestraint(gTarget,true,false);
                                        }
                                    }
                                    else{
                                        rRedRestraint(gTarget,false,false);
                                    }
                                    isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("neutral")||gItems[1].equalsIgnoreCase("male")||gItems[1].equalsIgnoreCase("female")){
                                    rGender(gTarget,gItems[1]);isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("furry")||gItems[1].equalsIgnoreCase("human")|| iRestraints.isItAnotherFurrySpecies(gItems[1])){
                                    rSpecies(gTarget,gItems[1]);isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("gender")&&gItems.length>=3){
                                    if(gItems[2].equalsIgnoreCase("neutral")||gItems[2].equalsIgnoreCase("male")||gItems[2].equalsIgnoreCase("female")){
                                        rGender(gTarget,gItems[2]);isInvalidCommand=false;
                                    }else
                                    if(gItems[2].equalsIgnoreCase("0")||gItems[2].equalsIgnoreCase("1")||gItems[2].equalsIgnoreCase("2")){
                                        rGender(gTarget,gItems[2]);isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[1].equalsIgnoreCase("species")&&gItems.length>=3){
                                    if(gItems[2].equalsIgnoreCase("furry")||gItems[2].equalsIgnoreCase("human")||gItems[2].equalsIgnoreCase("neko")||iRestraints.isItAnotherFurrySpecies(gItems[2])){
                                        rSpecies(gTarget,gItems[2]);isInvalidCommand=false;
                                    }else
                                    if(gItems[2].equalsIgnoreCase("0")||gItems[2].equalsIgnoreCase("1")||gItems[2].equalsIgnoreCase("2")){
                                        rSpecies(gTarget,gItems[2]);isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[1].equalsIgnoreCase("status")){
                                    rStatus(gTarget,"channel");isInvalidCommand=false;
                                }
                                else if(gItems[1].equalsIgnoreCase("image")){
                                    if(gItems.length<3){
                                        rHelp("image");isInvalidCommand=false;
                                        logger.warn(fName+".invalid args length");
                                    }else{
                                        imageView(gTarget,gItems[2]);isInvalidCommand=false;
                                    }
                                }
                            }
                        }
                        if(isInvalidCommand){
                            String call="";
                            String command="";
                            if(gTarget!=null&&gTarget.getIdLong()!=gMember.getIdLong()){
                                if(gItems.length>1)call=gItems[1].toLowerCase();
                                if(gItems.length>2)command=gItems[2].toLowerCase();
                                logger.info(fName+".jump: targeted, call="+call+", command="+command);
                                switch (call){
                                    case"auth":new rdAuth(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "image":rHelp("image");isInvalidCommand=false; break;
                                    case "jacket": case "straitjacket": case "straightjacket":new rdStraitjacket(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "mitts": case"mitten":case"mittens":new rdMitts(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "cuff":case"cuffs":
                                    case "arm":case"arms":
                                    case "leg":case"legs":new rdCuffs(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "gag":case"gags":new rdGag(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "collar": case "collars":new rdCollar(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "leash":new rdLeash(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "suit":new rdSuit(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "chastity":new rdChastity(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "timeout":new rdTimeout(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "timelock":new rdTimelock(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "blindfold":new rdBlindfold(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "ear":case "ears":new rdEars(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "hood":new rdHood(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "tag": case "nametag":new rdNameTag(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false; break;
                                    case "main": dmMainQuickMenu();isInvalidCommand=false; break;
                                }
                            }else{
                                if(gItems.length>0)call=gItems[0].toLowerCase();
                                if(gItems.length>1)command=gItems[1].toLowerCase();
                                logger.info(fName+".jump: current, call="+call+", command="+command);
                                switch (call){
                                    case"auth":new rdAuth(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "image":rHelp("image");isInvalidCommand=false; break;
                                    case "jacket": case "straitjacket": case "straightjacket":new rdStraitjacket(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "mitts": case"mitten":case"mittens":new rdMitts(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "cuff":case"cuffs":
                                    case "arm":case"arms":
                                    case "leg":case"legs":new rdCuffs(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "gag":case"gags":new rdGag(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "collar": case "collars":new rdCollar(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "leash":new rdLeash(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "suit":new rdSuit(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "chastity":new rdChastity(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "timeout":new rdTimeout(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "timelock":new rdTimelock(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "blindfold":new rdBlindfold(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "ear":case "ears":new rdEars(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "hood":new rdHood(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "tag": case "nametag":new rdNameTag(gGlobal,gCommandEvent,true,command);isInvalidCommand=false; break;
                                    case "main": dmMainQuickMenu();isInvalidCommand=false; break;
                                }
                            }

                            //else if(call.equalsIgnoreCase("restriction")){new rdRestrictions(gGlobal,gCommandEvent,true,command,gTarget);isInvalidCommand=false;}
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                logger.warn(fName+".blank command");
                            }
                            else if(gItems[0].equalsIgnoreCase(vClear)){
                                clearUser();isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("clearall")){
                                clearAll();isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("red")){
                                if(gItems.length>1){
                                    if(gItems[1].equalsIgnoreCase("yes")){
                                        rRedRestraint(false,true);
                                    }else
                                    if(gItems[1].equalsIgnoreCase("timelock")){
                                        rRedRestraint(true,false);
                                    }
                                }
                                else{
                                    rRedRestraint(false,false);
                                }
                                isInvalidCommand=false;
                            }
                            else if(!gBDSMCommands.restraints.getEnable()){
                                logger.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                                logger.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                                logger.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase(ACCESSLEVELS.Public.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Protected.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Key.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Pet.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.Ask.getName())||gItems[0].equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){
                                new rdAuth(gGlobal,gCommandEvent,true,gItems[0]);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("lock")||gItems[0].equalsIgnoreCase("unlock")){
                                rLock(gItems[0].toLowerCase());isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase(nUntie)||gItems[0].equalsIgnoreCase("untieall")){
                                rUntieAll(gItems[0].toLowerCase());isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("status")){
                                rStatus("channel");isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("dm")){
                                rDMMenu("main");isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("image")){
                                if(gItems.length<4){
                                    rHelp("image");isInvalidCommand=false;
                                    logger.warn(fName+".invalid args length");
                                }else{
                                    if(gItems[1].equalsIgnoreCase("view")){imageView(gItems[2],gItems[3]);isInvalidCommand=false;}
                                    if(gItems[1].equalsIgnoreCase(vSet)){imageSet(gItems[2],gItems[3]);isInvalidCommand=false;}
                                    if(gItems[1].equalsIgnoreCase("delete")){imageDelete(gItems[2],gItems[3]);isInvalidCommand=false;}
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("neutral")||gItems[0].equalsIgnoreCase("male")||gItems[0].equalsIgnoreCase("female")){
                                rGender(gItems[0]);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("furry")||gItems[0].equalsIgnoreCase("human")||gItems[0].equalsIgnoreCase("neko")||iRestraints.isItAnotherFurrySpecies(gItems[0])){
                                rSpecies(gItems[0]);isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase("gender")&&gItems.length>=2){
                                if(gItems[1].equalsIgnoreCase("neutral")||gItems[1].equalsIgnoreCase("male")||gItems[1].equalsIgnoreCase("female")){
                                    rGender(gItems[0]);isInvalidCommand=false;
                                }else
                                if(gItems[1].equalsIgnoreCase("0")||gItems[1].equalsIgnoreCase("1")||gItems[1].equalsIgnoreCase("2")){
                                    rGender(gItems[0]);isInvalidCommand=false;
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("species")&&gItems.length>=2){
                                if(gItems[1].equalsIgnoreCase("furry")||gItems[1].equalsIgnoreCase("human")||gItems[1].equalsIgnoreCase("neko")||iRestraints.isItAnotherFurrySpecies(gItems[1])){
                                    rSpecies(gItems[0]);isInvalidCommand=false;
                                }else
                                if(gItems[1].equalsIgnoreCase("0")||gItems[1].equalsIgnoreCase("1")||gItems[1].equalsIgnoreCase("2")){
                                    rSpecies(gItems[0]);isInvalidCommand=false;
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("notificationredirect")&&gItems.length>=2){
                                if(gItems[1].equalsIgnoreCase(vSet)){
                                    setNotificationChannel(vSet);isInvalidCommand=false;
                                }
                                if(gItems[1].equalsIgnoreCase(vClear)){
                                    setNotificationChannel(vClear);isInvalidCommand=false;
                                }
                            }
                            else if(gItems[0].equalsIgnoreCase("print")){
                                print(gCommandEvent);
                            }
                        }
                    }
                    //logger.info(fName+".deleting op message");
                    //llQuckCommandMessageDelete(gCommandEvent);
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
       
        lcEmote emojiXPunish= new lcEmote(),emojiWand= new lcEmote();
        private void rHelp(String command){
            String fName="[rHelp]";
            logger.info(fName);
            String desc="";
            logger.info(fName + ".command="+command);
            String quickSummonWithSpace=llPrefixStr+quickAlias+" ";
            String quickSummonWithSpaceAuth=llPrefixStr+"auth ";
            String quickSummonWithSpace2=llPrefixStr+"";
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            loadEmojis();
            if(command.equalsIgnoreCase("main")){
                rDMMenuHelp("main");
                return;
            }
            if(command.equalsIgnoreCase("main2")){
                embed.setTitle("Restrain Discord Help");
                embed.addField(strSupportTitle,strSupport,false);
                embed.addField("TO TARGET SOMEBODY ELSE"," To target somebody use this style: `"+quickSummonWithSpace2+"<command> <@pet> ...`, where <@pet> is the target mention.",false);
                embed.addField("Emergency release or untie all.","Use`"+quickSummonWithSpace+"red` or `"+quickSummonWithSpace+"red yes` for emergency undo restraints! Don't mention yourself.\nTo untie all restraints `"+quickSummonWithSpace+"<@pet> untieall`",false);
                embed.addField("Access","Sets the subs restraints access.\n`"+quickSummonWithSpace+"ask` , `"+quickSummonWithSpaceAuth+"<@pet> ask` anyone can but sub gets the power to accept or reject restrictions"+"\n`"+quickSummonWithSpace+"public`, `"+quickSummonWithSpaceAuth+"<@pet> public` sets that anyone can use the sub except the sub has no access to its restraints\n`"+quickSummonWithSpace+"protected` , `"+quickSummonWithSpaceAuth+"<@pet> protected` only the sub and its owner, sec-owner have access to its restraints\n`"+quickSummonWithSpace+"exposed` , `"+quickSummonWithSpaceAuth+"<@pet> exposed` anyone including the sub has access"+"\n`"+quickSummonWithSpace+"key` , `"+quickSummonWithSpaceAuth+"<@pet> key` anyone until locked, then only who locked has"+"\n`"+quickSummonWithSpace+"pet` , `"+quickSummonWithSpaceAuth+"<@pet> pet` sets that the sub owner&sec-owner have access",false);
                embed.addField("Auth","  `"+quickSummonWithSpace2+"auth` Set the pets owner&secowner.",false);
                embed.addField("Lock","Prevents the sub undoing their restraints.\n`"+quickSummonWithSpace+"lock` locks it while `unlock` unlocks it",false);
                desc="`"+quickSummonWithSpace+"status` Gets the rd status.";
                desc+="\n`"+quickSummonWithSpace+"neutral|male|female` Set gender.";
                desc+="\n`"+quickSummonWithSpace+"furry|human|neko` Set species.";
                embed.addField("Status & Gender",desc,false);
                desc="`"+quickSummonWithSpace2+"gag` Gags the sub's jaw.";
                desc+="\n`"+quickSummonWithSpace2+"nametag` Allows setting the name&avatar used for gagged posts.";
                embed.addField("Gag & Nametag",desc,false);
                desc="`"+quickSummonWithSpace2+"straitjacket` Places the sub inside a straitjacket";
                desc+="\n`"+quickSummonWithSpace2+"cuffs` Ties up sub's arms&legs.";
                desc+="\n`"+quickSummonWithSpace2+"mitts` Places mittens over the sub's paws/hands.";
                desc+="\n`"+quickSummonWithSpace2+"suit` Placing the sub into a suit.";
                embed.addField("Bindings",desc,false);
                desc="`"+quickSummonWithSpace2+"collar` Placing a collar around sub's neck.";
                desc+="\n`"+quickSummonWithSpace2+"leash` Leashes the sub. ";
                desc+="\n`"+quickSummonWithSpace2+"pishock` PiShock functions (beta&new). ";
                embed.addField("Collar & Leash",desc,false);
                desc="";
                if(isAdult)desc="`"+quickSummonWithSpace2+"hood` Placing a hood over the sub's head."+"\n";
                desc+="`"+quickSummonWithSpace2+"blindfold` Blindfolding the sub eyes.";
                if(isAdult)desc+="\n`"+quickSummonWithSpace2+"rchastity`Locking the subs genitals away.";
                desc+="\n`"+quickSummonWithSpace2+"ears` for more commands.Deafening the sub's ears.";
                embed.addField("Other bindings",desc,false);
                desc="`"+quickSummonWithSpace2+"timelock` locking their restraints till timer expires.";
                desc+="\n`"+quickSummonWithSpace2+"timeout no talkin till timer expires.`";
                embed.addField("Timelock & Timeout",desc,false);
                desc="`"+quickSummonWithSpace2+"punish|reward <@pet>` to punish or reward the sub.";
                desc+="\nOr react to their messages with "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" for punish";
                if(emojiWand!=null&&emojiWand.getEmote()!=null){
                    desc+="and "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGiftHeart)+"/"+emojiWand.getAsMention()+" for reward.";
                }else{
                    desc+="and "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGiftHeart)+" for reward.";
                }
                embed.addField("Reward&Punish",desc,false);
                //embed.addField("Images","Add images to action.\n`"+quickSummonWithSpace+"image` for more commands.",false);
                embed.addField("Restrictions","\n`"+quickSummonWithSpace2+"restriction` Allows setting up channel restrictions for the pet.",false);
                if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                    embed.addField("Setup","\n`"+quickSummonWithSpace+"help setup` for setup commands.",false);
                    embed.addField("Server options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
                    embed.addField("!ADMINS&MODERATORS","They can add at the command end `"+llOverride+"` to bypass restrictions for commands.",false);
                }
            }else
            if(command.equalsIgnoreCase(vSetup)){
                embed.setTitle("Setup Help");
                if(isAdult){
                    embed.addField("Restriction","Set the bdsm command restrictions to: `"+quickSummonWithSpace+" restrict set none/nsfw/denied`\n none will allow most commands to work, just the chastity and some options in the gag wont work\nnsfw forces the commands to run only in nsfw channel or server\ndenied wile denies any access even in nsfw channel or server\n`"+quickSummonWithSpace+"restrict get` will get the current restrictions",false);
                }
                embed.addField("Server options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
            }else
            if(command.equalsIgnoreCase("image")){
                embed.setTitle("rd_Image Handling");
                if(isAdult){
                    embed.addField("Boost Members","Nitro Boost users can add images to boundage actions",false);
                     embed.addField("Command"," `"+quickSummonWithSpace+"image <option> <area> <action>` ",false);
                     embed.addField("Options"," \n\t_set_ = set image\n\t_view_ view image\n\t_delete_ delete image",false);
                     embed.addField("Straitjacket"," has following actions: on, off, secure, unsecure, struggle",false);
                     embed.addField("Mitts"," has following actions: on, off, struggle",false);
                     embed.addField("Arm"," cuffs has following actions: secure, unsecure, struggle",false);
                     embed.addField("Leg"," cuffs has following actions: secure, unsecure, struggle",false);
                     embed.addField("Gag"," has following actions: secure, unsecure, struggle",false);
                }else{
                    embed.addField("NSFW","Only in NSFW channels or servers.",false);
                }
            }
            if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
                logger.info(fName+"sent as slash");
            } else
            if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        private void rDMMenuHelp(String command){
                String fName="[rDMMenuHelp]";
                try {
                    logger.info(fName);
                    String desc="";
                    logger.info(fName + ".command="+command);
                    String quickSummonWithSpace=llPrefixStr+quickAlias+" ";
                    String quickSummonWithSpaceAuth=llPrefixStr+"auth ";
                    String quickSummonWithSpace2=llPrefixStr+"";
                    EmbedBuilder embed=new EmbedBuilder();
                    embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
                    loadEmojis();
                    embed.setTitle("Restrain Discord Help");
                    embed.addField(strSupportTitle,strSupport,false);
                    if(command.equalsIgnoreCase("basic")||command.equalsIgnoreCase("0")){
                        embed.addField("TO TARGET SOMEBODY ELSE"," To target somebody **(not yourself)** use this style: `"+quickSummonWithSpace2+"<command> <@pet> ...`, where <@pet> is the target mention.",false);
                        embed.addField("Emergency release or untie all.","Use`"+quickSummonWithSpace+"red` or `"+quickSummonWithSpace+"red yes` for emergency undo restraints! Don't mention yourself.\nTo untie all restraints `"+quickSummonWithSpace+"<@pet> untieall`",false);
                        embed.addField("Auth","  `"+quickSummonWithSpace2+"auth` Set the pets owner&secowner.",false);
                        embed.addField("Lock","Prevents the sub undoing their restraints.\n`"+quickSummonWithSpace+"lock` locks it while `unlock` unlocks it",false);
                        embed.addField("Access","Sets the subs restraints access.\n`"+quickSummonWithSpace+"ask` , `"+quickSummonWithSpaceAuth+"<@pet> ask` anyone can but sub gets the power to accept or reject restrictions"+"\n`"+quickSummonWithSpace+"public`, `"+quickSummonWithSpaceAuth+"<@pet> public` sets that anyone can use the sub except the sub has no access to its restraints\n`"+quickSummonWithSpace+"protected` , `"+quickSummonWithSpaceAuth+"<@pet> protected` only the sub and its owner, sec-owner have access to its restraints\n`"+quickSummonWithSpace+"exposed` , `"+quickSummonWithSpaceAuth+"<@pet> exposed` anyone including the sub has access"+"\n`"+quickSummonWithSpace+"key` , `"+quickSummonWithSpaceAuth+"<@pet> key` anyone until locked, then only who locked has"+"\n`"+quickSummonWithSpace+"pet` , `"+quickSummonWithSpaceAuth+"<@pet> pet` sets that the sub owner&sec-owner have access",false);
                        embed.addField("Status","`"+quickSummonWithSpace+"status` Gets the rd status.",false);
                        embed.addField("Internet Shock Collar PiShock","`"+quickSummonWithSpace2+"pishock` PiShock functions (beta&new). ",false);
                    }
                    else if(command.equalsIgnoreCase("species")||command.equalsIgnoreCase("1")){
                        embed.addField("Gender","`"+quickSummonWithSpace+"neutral|male|female` Set gender.",false);
                        StringBuilder desc3=new StringBuilder();
                        for(int i=0;i<arraySpecies.length();i++){
                            JSONObject specie=arraySpecies.getJSONObject(i);
                            if(desc3.length()>0){
                                desc3.append(", ");
                            }
                            desc3.append(specie.getString("text").replaceAll(" ","_").toLowerCase());
                        }
                        embed.addField("Species","`"+quickSummonWithSpace+"furry|human|neko|<other>` Set species. \nOther species: "+desc3.toString(),false);
                    }
                    else if(command.equalsIgnoreCase("gag")||command.equalsIgnoreCase("2")){
                        embed.addField("Gag ","`"+quickSummonWithSpace2+"gag` Gags the sub's jaw.",false);
                        embed.addField("Nametag","`"+quickSummonWithSpace2+"nametag` Allows setting the name&avatar used for gagged posts.",false);
                    }
                    else if(command.equalsIgnoreCase("restraints")||command.equalsIgnoreCase("3")){
                        desc="`"+quickSummonWithSpace2+"straitjacket` Places the sub inside a straitjacket";
                        desc+="\n`"+quickSummonWithSpace2+"cuff` Ties up sub's arms&legs.";
                        desc+="\n`"+quickSummonWithSpace2+"mitts` Places mittens over the sub's paws/hands.";
                        desc+="\n`"+quickSummonWithSpace2+"suit` Placing the sub into a suit.";
                        embed.addField("Bindings",desc,false);
                        desc="`"+quickSummonWithSpace2+"collar` Placing a collar around sub's neck.";
                        desc+="\n`"+quickSummonWithSpace2+"leash` Leashes the sub. ";
                        embed.addField("Collar & Leash",desc,false);
                        desc="";
                        if(isAdult)desc="`"+quickSummonWithSpace2+"hood` Placing a hood over the sub's head."+"\n";
                        desc+="`"+quickSummonWithSpace2+"blindfold` Blindfolding the sub eyes.";
                        if(isAdult)desc+="\n`"+quickSummonWithSpace2+"rchastity`Locking the subs genitals away.";
                        desc+="\n`"+quickSummonWithSpace2+"ears` for more commands.Deafening the sub's ears.";
                        embed.addField("Other bindings",desc,false);
                    }
                    else if(command.equalsIgnoreCase("restrictions")||command.equalsIgnoreCase("4")){
                        desc="`"+quickSummonWithSpace2+"timelock` locking their restraints till timer expires.";
                        desc+="\n`"+quickSummonWithSpace2+"timeout no talkin till timer expires.`";
                        embed.addField("Timelock & Timeout",desc,false);
                        embed.addField("Restrictions","\n`"+quickSummonWithSpace2+"restriction` Allows setting up channel restrictions for the pet.",false);
                        if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                            embed.addField("Setup","\n`"+quickSummonWithSpace+"help setup` for setup commands.",false);
                            embed.addField("Server options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
                            embed.addField("!ADMINS&MODERATORS","They can add at the command end `"+llOverride+"` to bypass restrictions for commands.",false);
                        }
                    }
                    else if(command.equalsIgnoreCase("other")||command.equalsIgnoreCase("5")){
                        desc="`"+quickSummonWithSpace2+"punish|reward <@pet>` to punish or reward the sub.";
                        desc+="\nOr react to their messages with "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" for punish";
                        if(emojiWand!=null&&emojiWand.getEmote()!=null){
                            desc+="and "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGiftHeart)+"/"+emojiWand.getAsMention()+" for reward.";
                        }else{
                            desc+="and "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGiftHeart)+" for reward.";
                        }
                        embed.addField("Reward&Punish",desc,false);
                        //embed.addField("Images","Add images to action.\n`"+quickSummonWithSpace+"image` for more commands.",false);
                    }
                    else if(command.equalsIgnoreCase("admin")||command.equalsIgnoreCase("6")){
                        embed.addField("Setup","\n`"+quickSummonWithSpace+"help setup` for setup commands.",false);
                        embed.addField("Server options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
                        embed.addField("!ADMINS&MODERATORS","They can add at the command end `"+llOverride+"` to bypass restrictions for commands.",false);
                    }
                    else if(command.equalsIgnoreCase("wip")||command.equalsIgnoreCase("-2")){
                        desc="`"+quickSummonWithSpace2+"lock <@pet>` for wip (work in progress) lock sub-commands.";
                        desc+="\n`"+quickSummonWithSpace2+"confine <@pet>` for wip (work in progress) confine sub-commands.";
                        desc+="\n`"+quickSummonWithSpace2+"encase <@pet>` for wip (work in progress) encase sub-commands.";
                        embed.addField("Reward&Punish",desc,false);
                    }
                    else if(command.equalsIgnoreCase("main")||command.equalsIgnoreCase("-1")){
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" Basic","Commands for: auth, lock, access, status and internet shock collar PiShock.\nThe basic to start out.",false);
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Gender and species","Commands for: gender and species.\nChanges some text in the message sentences based on this settings.",false);
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" Gag","Commands for: gag and nametag.\nThe main special feature of the bot.",false);
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" Restraints","Commands for: cuffs,straitjacket,mitts,suits,hood,earmuffs,chastity.\nAdding more flavor.",false);
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" Restrictions","Commands for: timeout,timelock,restrictions.\nMore restrictions to the sub.",false);
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" Other","Commands for: reward/punish.\nCommands that could not be categorized.",false);
                        embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolW)+" WIP","Commands for: lock/confine.\nWork in progress commands.",false);
                        if(lsMemberIsBotOwner(gMember)||llMemberIsAdministrator(gMember)||llMemberHasPermission_MANAGESERVER(gMember)){
                            embed.addField(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+"Admin","Commands for the admins&managers.",false);
                        }
                    }
                    Message message=null;
                    messageComponentManager.loadMessageComponents(gCommandFileMenuRd);
                    logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                    try {
                        lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                        lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                        lcMessageBuildComponent.SelectMenu selectMenu0=component0.getSelect();
                        if(!lsMemberIsBotOwner(gMember)&&!llMemberIsAdministrator(gMember)&&!llMemberHasPermission_MANAGESERVER(gMember)){
                            selectMenu0.getOptionByValue("6").setIgnored();
                        }
                        if(command.equalsIgnoreCase("main")||command.equalsIgnoreCase("-1")){
                            component1.getButtonAt4(0).setDisable();
                        }
                        logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                        try {
                            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            message=gTextChannel.sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        try {
                            message=llSendMessageResponse(gUser,embed);
                        }catch (Exception e4){
                            logger.error(fName + ".exception=" + e4);
                            logger.error(fName + ".exception:" + Arrays.toString(e4.getStackTrace()));
                            message=llSendMessageResponse(gTextChannel,embed);
                        }
                    }
                    rDMMenuHelpListener(message,command);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
                }
            }
        int intDefaultMinutes=15;
        private void rDMMenuHelpListener(Message message,String command){
            String fName="[rDMMenuHelpListener]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            assert id != null;
                            if (id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)) {
                                rHelp("main2");
                            }
                            else if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowUp)){
                                rDMMenuHelp("-1");
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String value=e.getValues().get(0);
                            logger.warn(fName+"value="+value);
                            llMessageDelete(message);
                            switch (value){
                                case lsUnicodeEmotes.aliasArrowUp:
                                    rDMMenuHelp("-1");
                                    break;
                                case lsUnicodeEmotes.aliasZero:
                                    rDMMenuHelp("basic");
                                    break;
                                case lsUnicodeEmotes.aliasOne:
                                    rDMMenuHelp("species");
                                    break;
                                case lsUnicodeEmotes.aliasTwo:
                                    rDMMenuHelp("gag");
                                    break;
                                case lsUnicodeEmotes.aliasThree:
                                    rDMMenuHelp("restraints");
                                    break;
                                case lsUnicodeEmotes.aliasFour:
                                    rDMMenuHelp("restrictions");
                                    break;
                                case lsUnicodeEmotes.aliasFive:
                                    rDMMenuHelp("other");
                                    break;
                                case lsUnicodeEmotes.aliasSix:
                                    rDMMenuHelp("admin");
                                    break;
                                case lsUnicodeEmotes.aliasSymbolW:
                                    rDMMenuHelp("wip");
                                    break;
                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
            if(!message.isFromGuild()){
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    logger.info(fName+"up1");
                                    rDMMenuHelp("-1");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    logger.info(fName+"up2");
                                    rDMMenuHelp("-1");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    rDMMenuHelp("basic");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    rDMMenuHelp("species");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    rDMMenuHelp("gag");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    rDMMenuHelp("restraints");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    rDMMenuHelp("restrictions");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    rDMMenuHelp("other");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    rDMMenuHelp("admin");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolW))){
                                    rDMMenuHelp("wip");
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                gWaiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                llMessageDelete(e.getChannel(),e.getMessageId());
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    logger.info(fName+"up1");
                                    rDMMenuHelp("-1");
                                }
                                else if(asCodepoints.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    logger.info(fName+"up2");
                                    rDMMenuHelp("-1");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    rDMMenuHelp("basic");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    rDMMenuHelp("species");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    rDMMenuHelp("gag");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    rDMMenuHelp("restraints");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    rDMMenuHelp("restrictions");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    rDMMenuHelp("other");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    rDMMenuHelp("admin");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolW))){
                                    rDMMenuHelp("wip");
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }
            
        }
        private void rDMMenu(String command){
            String fName="[rDMMenu]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            llSendQuickEmbedMessage(gUser,sRTitle,"Removed", llColorPurple1);
        }
        private void loadEmojis(){
            String fName="[loadEmojis]";
            logger.info(fName);
            try {
                if(emojiWand.getEmote()!=null){
                    emojiWand.setGuild(gGlobal.getGuild(lsCustomGuilds.lsGuildKeyAdministration));
                    emojiWand.getEmoteByName("wand");
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
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
        private void rLock(String command){
            String fName="[rLock]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".permalock");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your locks as access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.isAccessLevelEqual(ACCESSLEVELS.Public)){
                logger.info(fName + ".can't use do to access public");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your locks due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase("lock")){
                if(gIsOverride||!gNewUserProfile.cLOCK.isLocked()){
                    logger.info(fName + ".locking");
                    gNewUserProfile.cLOCK.setLocked(true).setLockedBy(gUser.getId()).setType(LOCKTYPES.DEFAULT);
                    if(toMessageSelfCommandAction)llSendQuickEmbedMessage(gUser,sRTitle,"You locked your restraints!", llColorBlue1);
                    userNotification( gUser.getName()+" has locked their restraints. Hope they wont lose their key.");
                }else{
                    logger.info(fName + ".not unlocked");
                    llSendQuickEmbedMessage(gUser,sRTitle,"You can't lock that is already locked!", llColorRed);
                }
            }
            else if(command.equalsIgnoreCase("unlock")){
                if(!gIsOverride&&gNewUserProfile.isTimeLocked()){
                    logger.info(fName + ".can't do, timelocked");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't unlock while timelock is running.", llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                    logger.info(fName + ".can't use do to locked by somebody");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your locks due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                    return;
                }else
                if(gNewUserProfile.cLOCK.isLocked()){
                    if(!gIsOverride&&gNewUserProfile.isAccessLevelEqual(ACCESSLEVELS.Exposed)){
                        logger.info(fName + ".can't unlock do to exposed access");
                        llSendQuickEmbedMessage(gUser,sRTitle,"Can't unlock your restraints due to exposed access!", llColorRed);return;
                    }
                    else{
                        logger.info(fName + ".unlocking");
                        gNewUserProfile.cLOCK.setLocked(false).setLockedBy("");
                        if(toMessageSelfCommandAction)llSendQuickEmbedMessage(gUser,sRTitle,"You unlocked your restraints.", llColorBlue1);
                        userNotification( gUser.getName()+" has unlocked their restraints. Someone should have placed the key in a safe.");
                    }
                }else{
                    logger.info(fName + ".not locked");
                    llSendQuickEmbedMessage(gUser,sRTitle,"You can't unlock that is not locked!", llColorRed);
                }
            }
            saveProfile();
        }
        private void rGender(String command){
            String fName="[rGender]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(command.equalsIgnoreCase("male")||command.equalsIgnoreCase("1")){
                logger.info(fName + ".set gender to male");
                gNewUserProfile.setGender(vGenderMale);
                //llSendQuickEmbedMessage(gUser,sRTitle,"You set your gender to male.", llColorBlue1);
                userNotification( gUser.getName()+" set gender to male.");
            }
            else if(command.equalsIgnoreCase("female")||command.equalsIgnoreCase("2")){
                logger.info(fName + ".set gender to female");
                gNewUserProfile.setGender(vGenderFemale);
                //llSendQuickEmbedMessage(gUser,sRTitle,"You set your gender to female.", llColorBlue1);
                userNotification( gUser.getName()+" set gender to female.");
            }
            else if(command.equalsIgnoreCase("neutral")||command.equalsIgnoreCase("0")){
                logger.info(fName + ".set gender to neutral");
                gNewUserProfile.setGender(vGenderNeutral);
                //llSendQuickEmbedMessage(gUser,sRTitle,"You set your gender to neutral.", llColorBlue1);
                userNotification( gUser.getName()+" set gender to neutral.");
            }
            saveProfile();
        }
        private void rSpecies(String command){
            String fName="[rGender]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(command.equalsIgnoreCase("human")||command.equalsIgnoreCase("1")){
                logger.info(fName + ".set species to human");
                gNewUserProfile.setSpecies( vSpecieHuman);
                gNewUserProfile.clearSpeciesName();
                //llSendQuickEmbedMessage(gUser,sRTitle,"You set your gender to male.", llColorBlue1);
                userNotification( gUser.getName()+" set species to human.");
            }
            else if(command.equalsIgnoreCase("furry")||command.equalsIgnoreCase("0")){
                logger.info(fName + ".set species to furry");
                gNewUserProfile.setSpecies(vSpecieFurry);
                gNewUserProfile.clearSpeciesName();
                //llSendQuickEmbedMessage(gUser,sRTitle,"You set your gender to neutral.", llColorBlue1);
                userNotification( gUser.getName()+" set species to furry.");
            }
            else if(command.equalsIgnoreCase("neko")||command.equalsIgnoreCase("2")){
                logger.info(fName + ".set species to furry");
                gNewUserProfile.setSpecies( vSpecieNeko);
                gNewUserProfile.clearSpeciesName();
                //llSendQuickEmbedMessage(gUser,sRTitle,"You set your gender to neutral.", llColorBlue1);
                userNotification( gUser.getName()+" set species to neko.");
            }
            else if(iRestraints.isItAnotherFurrySpecies(command)){
                logger.info(fName + ".is another furry species");
                gNewUserProfile.setSpecies(vSpecieFurry);
                gNewUserProfile.setSpeciesName(iRestraints.getText4AnotherFurrySpecies(command).toLowerCase());
                userNotification( gUser.getName()+" set species to "+iRestraints.getText4AnotherFurrySpecies(command).toLowerCase()+".");
            }
            saveProfile();
        }
        private void rUntieAll(String command){
            String fName="[rUntieAll]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
                logger.info(fName + ".permalock");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.isAccessLevelEqual(ACCESSLEVELS.Public)){
                logger.info(fName + ".can't use do to access public");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate your restraints due to access set to public. While public everyone else can access it except you.", llColorRed);
                return;
            }else
            if(!gIsOverride&&( gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyBeta|| gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyOmega)){
                logger.info(fName + ".can't use do to special suit");
                llSendQuickEmbedMessage(gUser,sRTitle,"Warning, you are wearing a special toy suit. First run `"+llPrefixStr+"suit release` to take off the suit to use clear all.", llColorRed);
                return;
            }
            /*if(!gIsOverride&&gUserProfile.jsonUser.getBoolean(nLocked)){
                logger.info(fName + ".can't use do to locked");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't untie all restraints do to they locked with a padlock.", llColorRed);
                return;
            }*/
            if( gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyAlpha){
                logger.info(fName + ".special suit");
                gNewUserProfile.untieAll();
                llSendQuickEmbedMessage(gUser,sRTitle,"All restraints untied and removed except the suit ones.", llColorBlue1);
            }else{
                gNewUserProfile.untieAll();
                llSendQuickEmbedMessage(gUser,sRTitle,"All restraints untied and removed.", llColorBlue1);
            }

            saveProfile();
            new rdVoicePostSynthesizer(gGlobal,gMember.getVoiceState());
        }
        String gSafewordMenuPath=gFileMainPath+"safeword.json";
        String gCommandFileMenuRd=gFileMainPath+"menuRD.json";
        private void rRedRestraint(boolean timelock,boolean yes){
            String fName="[rRedRestraint]";
            logger.info(fName);
            logger.info(fName+"timelock="+timelock+", yes="+yes);
            if(!getProfile()){logger.error(fName + ".can't get profile");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Ca't get user profile");
                return;
            }
            if(yes){
                gNewUserProfile.redRestraints();
                if(saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You used emergency safeword to undo your restraints.", llColorBlue1);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,gMember.getAsMention()+" used emergency safeword to undo their restraints.", llColorBlue1);
                }
                new rdVoicePostSynthesizer(gGlobal,gMember.getVoiceState());
                return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription("Are you sure you want to use the emergency safety?\nThis will clear all restraints and some settings.");
            embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" to yes i want to be free.",false);
            embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" to remain helpless.",false);
            Message message=null;
            messageComponentManager.loadMessageComponents(gSafewordMenuPath);
            try {
                logger.info(fName+"component.post="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
            rRedRestraintListener(message);
        }
        private void rRedRestraintListener(Message message){
            String fName="[rRedRestraintListener]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            String level="",category="";
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                logger.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasUnlock)){
                                rRedRestraintUnlocker();
                            };


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
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                rRedRestraintUnlocker();
                            }
                            llMessageDelete(message);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },5, TimeUnit.MINUTES, () -> llMessageDelete(message));
        }
        private void rRedRestraintUnlocker(){
            String fName="[rRedRestraintUnlocker]";
            logger.info(fName);
            gNewUserProfile.redRestraints();
            if(saveProfile()){
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You used emergency safeword to undo your restraints.", llColorBlue1);
                }else{
                    llSendQuickEmbedMessage(gUser,sRTitle,"You used emergency safeword to undo "+gNewUserProfile.getMember().getAsMention()+"'s restraints.", llColorBlue1);
                    llSendQuickEmbedMessage(gNewUserProfile.getMember().getUser(),sRTitle,gUser.getAsMention()+" used emergency safeword to undo "+gNewUserProfile.getMember().getAsMention()+"'s restraints.", llColorBlue1);
                }
            }
            new rdVoicePostSynthesizer(gGlobal,gMember.getVoiceState());
        }
    private void rStatus(String command) {
        String fName = "[rStatus]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        rStatus(gCommandEvent.getMember(),command);
    }
    private void imageSet(String area, String action){
        String fName = "[imageSet]";
        logger.info(fName);
        logger.info(fName + ".area=" + area);
        logger.info(fName + ".action=" + action);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        llSendQuickEmbedMessage(gUser,sRTitle, "This feature temporary disabled", llColorRed);
        
        /*if(!gIsOverride&&!isAdult){logger.info(fName + ".not adult channel or guild");
            llSendQuickEmbedMessage(gUser,sRTitle,"Only on NSFW channel or servers!", llColorRed);return;}
        if(!gIsOverride&&!llMemberIsBooster(gMember)){ logger.info(fName + ".can't use do to not a booster");
            llSendQuickEmbedMessage(gUser,sRTitle,"Only boosters have access to this extra options!", llColorRed);return; }
        String areaField=getArea(area);String  actionField=getAction(action);
        logger.info(fName + ".area field=" +  areaField); logger.info(fName + ".action field=" +  actionField);
        if(areaField.isEmpty()){logger.error(fName + ".invalid area"); llSendQuickEmbedMessage(gUser,sRTitle,"Inva;id area", llColorRed);return;}
        if(actionField.isEmpty()){logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return;}

        if((areaField.equalsIgnoreCase(nGag)||areaField.equalsIgnoreCase(nArmsCuffs)||areaField.equalsIgnoreCase(nLegsCuffs))&&(actionField.equalsIgnoreCase(nImageUrlOn)||actionField.equalsIgnoreCase(nImageUrlOff))){ logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return; }
        if(areaField.equalsIgnoreCase(nMitts)&&(actionField.equalsIgnoreCase(nImageUrlSecure)||actionField.equalsIgnoreCase(nImageUrlUnSecure))){ logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return; }

        String title="Upload image to "+area+" for action "+action;
        llSendQuickEmbedMessage(gUser, title, "Please submit an image\nIf you want to cancel it type `cancel`.", llColorPurple1);
        gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel, and for safety, a different message
                e -> e.getAuthor().equals(gUser),
                // respond, inserting the name they listed into the response
                e -> {
                    try {
                        String content = e.getMessage().getContentStripped();
                        logger.info(fName + "text=" + content);
                        if(content.equalsIgnoreCase("cancel")){
                            llSendQuickEmbedMessage(gUser, title, "Canceled!", llColorRed);
                            return;
                        }
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
                            String letterImageUrl;
                            letterImageUrl = attachment.getUrl();
                            logger.info(fName + "  letterImageUrl=" + letterImageUrl);
                            logger.info(fName + "  areaField=" + areaField);logger.info(fName + "  actionField=" + actionField);
                            gUserProfile.jsonUser.getJSONObject(areaField).put(actionField,letterImageUrl);
                            if(!saveProfile()){
                                llSendQuickEmbedMessage(gUser, title, "Failed to update profile!", llColorRed);
                                return;
                            }
                            llSendQuickEmbedMessage(gUser, title, "Successfully update profile!", llColorGreen1);
                        }
                    } catch (Exception ex) {
                        llSendQuickEmbedMessage(gUser, title, "Failed to open for hygiene!", llColorRed);
                        logger.error(fName + "  exception:"+ex);
                    }
                },
                // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES, () -> llSendQuickEmbedMessage(gUser,title, "Timeout", llColorRed));*/
    }
    private void imageDelete(String area, String action){
        String fName = "[imageDelete]";
        logger.info(fName);
        logger.info(fName + ".area=" + area);
        logger.info(fName + ".action=" + action);
        String  areaField=getArea(area);String  actionField=getAction(action);logger.info(fName + ".action field=" +  actionField);
        logger.info(fName + ".area field=" +  areaField);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        llSendQuickEmbedMessage(gUser,sRTitle, "This feature temporary disabled", llColorRed);
        /*if(!gIsOverride&&!isAdult){logger.info(fName + ".not adult channel or guild");
            llSendQuickEmbedMessage(gUser,sRTitle,"Only on NSFW channel or servers!", llColorRed);return;}
        if(areaField.isEmpty()){logger.error(fName + ".invalid area");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid area", llColorRed);return;}
        if(actionField.isEmpty()){logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return;}

        if((areaField.equalsIgnoreCase(nGag)||areaField.equalsIgnoreCase(nArmsCuffs)||areaField.equalsIgnoreCase(nLegsCuffs))&&(actionField.equalsIgnoreCase(nImageUrlOn)||actionField.equalsIgnoreCase(nImageUrlOff))){ logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return; }
        if(areaField.equalsIgnoreCase(nMitts)&&(actionField.equalsIgnoreCase(nImageUrlSecure)||actionField.equalsIgnoreCase(nImageUrlUnSecure))){ logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return; }

        gUserProfile.jsonUser.getJSONObject( areaField).put(actionField,"");
        llSendQuickEmbedMessage(gUser,sRTitle,"Deleted image from "+ areaField, llColorRed);
        saveProfile();*/
    }
    private void imageView(String area, String action){
        String fName = "[imageView]";
        logger.info(fName);
        logger.info(fName + ".area=" + area);
        logger.info(fName + ".action=" + action);
        llSendQuickEmbedMessage(gUser,sRTitle, "This feature temporary disabled", llColorRed);
        /*String  areaField=getArea(area);String  actionField=getAction(action);logger.info(fName + ".action field=" +  actionField);
        logger.info(fName + ".area field=" +  areaField);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&!isAdult){logger.info(fName + ".not adult channel or guild");
            llSendQuickEmbedMessage(gUser,sRTitle,"Only on NSFW channel or servers!", llColorRed);return;}
        if(areaField.isEmpty()){logger.error(fName + ".invalid area");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid area", llColorRed);return;}
        if(actionField.isEmpty()){logger.error(fName + ".invalid action");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid action", llColorRed);return;}
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(sRTitle); embed.setColor(llColorRed_Garnet);
        String url=gUserProfile.jsonUser.getJSONObject(areaField).getString(actionField);
        if(url!=null&&!url.isEmpty()){
            logger.info(fName + ".url=" +  url);
            embed.setDescription( gUser.getAsMention()+"'s "+ areaField+" image ");
            embed.setImage(url);
        }else{
            embed.setDescription( gUser.getAsMention()+" has no image");
        }
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);
        */
    }
    private String getArea(String area){
        String fName = "[getArea]";
        logger.info(fName);
        logger.info(fName + ".area=" + area);
        if(area.equalsIgnoreCase(nArmsCuffs)||area.equalsIgnoreCase("arm")||area.equalsIgnoreCase("arms")){return nArmsCuffs;}
        if(area.equalsIgnoreCase(nLegsCuffs)||area.equalsIgnoreCase("leg")||area.equalsIgnoreCase("legs")){ return nLegsCuffs;}
        if(area.equalsIgnoreCase(nStraitjacket)||area.equalsIgnoreCase("straitjacket")){return nStraitjacket;}
        if(area.equalsIgnoreCase(nMitts)||area.equalsIgnoreCase("mitts")||area.equalsIgnoreCase("mitt")){return nMitts;}
        if(area.equalsIgnoreCase(nGag)||area.equalsIgnoreCase("gag")||area.equalsIgnoreCase("gags")){return nGag;}
        return "";
    }
    private String getAction(String action) {
        String fName = "[getAction]";
        logger.info(fName);
        logger.info(fName + ".action=" + action);
        if(action.equalsIgnoreCase("put on")||action.equalsIgnoreCase("on")){return nImageUrlOn;}
        if(action.equalsIgnoreCase("take off")||action.equalsIgnoreCase("off")){ return nImageUrlOff;}
        if(action.equalsIgnoreCase("secure")){return nImageUrlSecure;}
        if(action.equalsIgnoreCase("unsecure")){return nImageUrlUnSecure;}
        if(action.equalsIgnoreCase("strugle")||action.equalsIgnoreCase("struggle")){return nImageUrlStruggle;}
        return "";
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void imageView(Member mtarget,String area){
        String fName = "[imageView]";
        logger.info(fName);
        logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
        logger.info(fName + ".area=" + area);
        String  areaField=getArea(area);
        logger.info(fName + ".area field=" +  areaField);
        llSendQuickEmbedMessage(gUser,sRTitle, "This feature temporary disabled", llColorRed);
        /*if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if( areaField.isEmpty()){logger.error(fName + ".invalid area");
            llSendQuickEmbedMessage(gUser,sRTitle,"Invalid area", llColorRed);return;}
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(sRTitle); embed.setColor(llColorRed_Garnet);
        String url=gUserProfile.jsonUser.getJSONObject(areaField).getString(nImageUrlOn);
        if(url!=null&&!url.isEmpty()){
            embed.setImage( mtarget.getAsMention()+"'s "+ areaField+" image ");
            embed.setImage(url);
        }else{
            embed.setImage( mtarget.getAsMention()+" has no image");
        }
        llSendMessageWithDelete(gGlobal,gTextChannel,embed);*/
    }
    private void rLock(Member mtarget,String command){
        String fName="[rLock]";
        logger.info(fName);
        logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
            logger.info(fName + ".permalock");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+mtarget.getAsMention()+" restraints as they permanently locked!", llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their locks due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return;
        }else
        if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their locks due to their access setting.", llColorRed);
            return;
        }else
        if(command.equalsIgnoreCase("lock")){
            if(gIsOverride||!gNewUserProfile.cLOCK.isLocked()){
                logger.info(fName + ".locking");
                gNewUserProfile.cLOCK.setLocked(true).setLockedBy(gUser.getId()).setType(LOCKTYPES.DEFAULT);
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iLock.strTargetOnDefault1),llColors.llColorBlue1);
                lsMessageHelper.lsSendQuickEmbedMessage( gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOnDefault2),llColors.llColorBlue1);
                userNotification(stringReplacer(iLock.strTargetOnDefault3));
            }else{
                logger.info(fName + ".not unlocked");
                llSendQuickEmbedMessage(gUser,sRTitle,"You can't lock that is already locked!", llColorRed);
            }
        }
        else if(command.equalsIgnoreCase("unlock")){
            if(gNewUserProfile.cLOCK.isLocked()){
                if(!gIsOverride&&gNewUserProfile.isTimeLocked()){
                    logger.info(fName + ".can't do, timelocked");
                    llSendQuickEmbedMessage(gUser,sRTitle,"Can't unlock while timelock is running.", llColorRed);
                    return;
                }
                logger.info(fName + ".unlocking");
                gNewUserProfile.cLOCK.setLocked(false).setLockedBy("");
                switch (gNewUserProfile.cLOCK.getTypeAsString().toLowerCase()){
                    case "glue":
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iLock.strTargetOffGlue1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOffGlue2),llColors.llColorBlue1);
                        userNotification(stringReplacer(iLock.strTargetOffGlue3));
                        break;
                    case "tape":
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iLock.strTargetOffTape1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOffTape2),llColors.llColorBlue1);
                        userNotification(stringReplacer(iLock.strTargetOffTape3));
                        break;
                    case "extra":
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iLock.strTargetOffExtra1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOffExtra2),llColors.llColorBlue1);
                        userNotification(stringReplacer(iLock.strTargetOffExtra3));
                        break;
                    case "stitch":
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iLock.strTargetOffStitch1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOffStitch2),llColors.llColorBlue1);
                        userNotification(stringReplacer(iLock.strTargetOffStitch3));
                        break;
                    default:
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle, stringReplacer(iLock.strTargetOffDefault1),llColors.llColorBlue1);
                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iLock.strTargetOffDefault2),llColors.llColorBlue1);
                        userNotification(stringReplacer(iLock.strTargetOffDefault3));
                }

            }else{
                logger.info(fName + ".not locked");
                llSendQuickEmbedMessage(gUser,sRTitle,"You can't unlock that is not locked!", llColorRed);
            }
        }
        saveProfile();
    }
        private void rGender(Member mtarget,String command){
            String fName="[rGender]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their gender due to their access setting.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase("male")||command.equalsIgnoreCase("1")){
                logger.info(fName + ".set gender to male");
                gNewUserProfile.setGender(vGenderMale);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your gender to male.", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s gender to male.",llColorBlue1);
            }
            else if(command.equalsIgnoreCase("female")||command.equalsIgnoreCase("2")){
                logger.info(fName + ".set gender to female");
                gNewUserProfile.setGender(vGenderFemale);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your gender to female.", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s gender to female.",llColorBlue1);
            }
           else if(command.equalsIgnoreCase("neutral")||command.equalsIgnoreCase("0")){
                logger.info(fName + ".set gender to neutral");
                gNewUserProfile.setGender(vGenderNeutral);
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your gender to neutral.", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s gender to neutral.",llColorBlue1);
            }
            saveProfile();
        }
        private void rSpecies(Member mtarget,String command){
            String fName="[rSpecies]";
            logger.info(fName);
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their species due to their access setting.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase("human")||command.equalsIgnoreCase("1")){
                logger.info(fName + ".set species to human");
                gNewUserProfile.setSpecies(vSpecieHuman).clearSpeciesName();
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your species to human.", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s species to human.",llColorBlue1);
            }
            else if(command.equalsIgnoreCase("furry")||command.equalsIgnoreCase("0")){
                logger.info(fName + ".set species to furry");
                gNewUserProfile.setSpecies(vSpecieFurry).clearSpeciesName();
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your species to furry.", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s species to furry.",llColorBlue1);
            }
            else if(command.equalsIgnoreCase("nekp")||command.equalsIgnoreCase("2")){
                logger.info(fName + ".set species to neko");
                gNewUserProfile.setSpecies( vSpecieNeko).clearSpeciesName();
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your species to neko.", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s species to neko.",llColorBlue1);
            }
            else if(iRestraints.isItAnotherFurrySpecies(command)){
                logger.info(fName + ".is another furry species");
                gNewUserProfile.setSpecies(vSpecieFurry).setSpeciesName(iRestraints.getText4AnotherFurrySpecies(command).toLowerCase());
                llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,gUser.getName()+" set your species to "+iRestraints.getText4AnotherFurrySpecies(command).toLowerCase()+".", llColorBlue1);
                llSendQuickEmbedMessage(gTextChannel,sRTitle, gUser.getName()+" set "+mtarget.getAsMention()+"'s species to "+iRestraints.getText4AnotherFurrySpecies(command).toLowerCase()+".",llColorBlue1);
            }

            saveProfile();
        }
    private void rUntieAll(Member mtarget,String command){
        String fName="[rUntieAll]";
        logger.info(fName);
        logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
        logger.info(fName + ".command="+command);
        if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()){
            logger.info(fName + ".permalock");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate "+mtarget.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
            return;
        }else
        if(!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
            logger.info(fName + ".can't use do to locked by not you");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their restraints due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
            return;
        }else
        if(!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
            logger.info(fName + ".can't use do to access protected");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't manipulate their locks due to their access setting.", llColorRed);
            return;
        }else
        if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyBeta||gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyOmega)){
            logger.info(fName + ".can't use do to special suit");
            llSendQuickEmbedMessage(gUser,sRTitle,"Warning, they are wearing a special toy suit. First run `"+llPrefixStr+"suit <target> release` to take off the suit to use clear all.", llColorRed);
            return;
        }
        /*if(gUserProfile.jsonUser.getBoolean(nLocked)){
            logger.info(fName + ".can't use do to locked");
            llSendQuickEmbedMessage(gUser,sRTitle,"Can't untie all restraints do to they locked with a padlock.", llColorRed);
            return;
        }*/
        gNewUserProfile.untieAll();
        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyAlpha){
            logger.info(fName + ".special suit");
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,"All restraints untied by "+gUser.getName()+", except the suit ones.", llColorBlue1);
            llSendQuickEmbedMessage(gUser,sRTitle,"You have untied "+mtarget.getAsMention()+"'s restraints, except the suit ones.", llColorBlue1);
        }else{
            llSendQuickEmbedMessage(mtarget.getUser(),sRTitle,"All restraints untied by "+gUser.getName()+".", llColorBlue1);
            llSendQuickEmbedMessage(gUser,sRTitle,"You have untied "+mtarget.getAsMention()+"'s restraints.", llColorBlue1);
        }
        saveProfile();
        new rdVoicePostSynthesizer(gGlobal,mtarget.getVoiceState());
    }
        private void rRedRestraint(Member mtarget,boolean timelock,boolean yes){
            String fName="[rRedRestraint]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            logger.info(fName+"timelock="+timelock);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}

            if(!gNewUserProfile.hasUserOwnerAccess(gUser)&&!hasUserSecOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                llSendQuickEmbedMessage(gUser,sRTitle,"Denied, only owner or sec-owner.", llColorRed);
                return;
            }
            if(yes){
                gNewUserProfile.redRestraints();
                if(saveProfile()){
                    llSendQuickEmbedMessage(gUser,sRTitle,"You used emergency safeword to undo "+gNewUserProfile.getMember().getAsMention()+" restraints.", llColorBlue1);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,gMember.getAsMention()+" used emergency safeword to undo "+gNewUserProfile.getMember().getAsMention()+" restraints.", llColorBlue1);
                }
                new rdVoicePostSynthesizer(gGlobal,gMember.getVoiceState());
                return;
            }
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(sRTitle);embed.setColor(llColorOrange);
            embed.setDescription("Are you sure you want to use the emergency safety on "+gNewUserProfile.getMember().getAsMention()+"?\nThis will clear all restraints and some settings.");
            embed.addField("Yes","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" to set them free.",false);
            embed.addField("No","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" to keep them helpless.",false);
            Message message=null;
            messageComponentManager.loadMessageComponents(gSafewordMenuPath);
            try {
                logger.info(fName+"component.post="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
            rRedRestraintListener(mtarget,message);
        }
        private void rRedRestraintListener(Member mtarget,Message message){
            String fName="[rRedRestraintListener]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            String level="",category="";
                            llMessageDelete(message);
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                logger.info(fName + "close");
                                return;
                            }
                            if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasUnlock)){
                                rRedRestraintUnlocker();
                            };


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
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))) {
                                rRedRestraintUnlocker();
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
        private void rStatus(Member mtarget,String command) {
            String fName = "[rStatus]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            logger.info(fName + ".command="+command);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            EmbedBuilder embed= new EmbedBuilder();
            embed.setColor(llColorBlue1);
            embed.setTitle(mtarget.getUser().getName()+" restraints status");
            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).has(nOwnerId)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nOwnerId)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getBoolean(nOwnerAccepted)){
                String str= lsUserHelper.lsGetUserMentionById(gGuild,gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId));
                if(str!=null){
                    embed.addField("Owner",str,true);
                }else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId)!=null){
                    embed.addField("Owner",gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId),true);
                }
            }else{
                embed.addField("Owner","<Free>",true);
            }
            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).has(nSecOnwers)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nSecOnwers)){
                Iterator<String> keys=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getJSONObject(nSecOnwers).keys();
                StringBuilder tmp = new StringBuilder();
                while (keys.hasNext()) {
                    String id = keys.next();
                    tmp.append("\n").append(llGetMemberMention(gGuild, id));
                }
                if(tmp.length()!=0) embed.addField("Sec-Owners", tmp.toString(),true);
            }
            String access;
            if(gNewUserProfile.gUserProfile.jsonObject.has(nAccess)){
                if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){ access = "ask";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){ access = "ask";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){ access = "ask+";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Pet.getName())){ access = "owner&secowner";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Key.getName())){access = "key";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){ access = "exposed";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Public.getName())){access = "public";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){access = "protected";}
                else{ access = "invalid"; }
            } else{access="invalid";}
            embed.addField("Access",access,true);
            String gender="",species="";
            if(gNewUserProfile.gUserProfile.jsonObject.has(nGender)){
                switch (gNewUserProfile.gUserProfile.jsonObject.getInt(nGender)) {
                    case vGenderMale:
                        gender="male";
                        break;
                    case vGenderFemale:
                        gender="female";
                        break;
                    default:
                        gender="neutral";
                }
            } else{
                gender="neutral";
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nSpecies)){
                switch (gNewUserProfile.gUserProfile.jsonObject.getInt(nSpecies)) {
                    case vSpecieNeko:
                        species="neko";
                        break;
                    case vSpecieHuman:
                        species="human";
                        break;
                    default:
                        species ="furry";
                }
                if(gNewUserProfile.gUserProfile.jsonObject.has(nSpeciesName)&&!gNewUserProfile.gUserProfile.jsonObject.isNull(nSpeciesName)&&!gNewUserProfile.gUserProfile.jsonObject.getString(nSpeciesName).isBlank()){
                    species =gNewUserProfile.gUserProfile.jsonObject.getString(nSpeciesName);
                }
            } else{
                species="furry";
            }
            embed.addField("Gender&Species",gender+", "+species,true);
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLocked)&&gNewUserProfile.gUserProfile.jsonObject.getBoolean(nLocked)){
                String lockeCategory="",lockedValue="";
                if(gNewUserProfile.gUserProfile.jsonObject.has(nLockedBy)&&!gNewUserProfile.gUserProfile.jsonObject.isNull(nLockedBy)&&!gNewUserProfile.gUserProfile.jsonObject.getString(nLockedBy).isBlank()&&!gNewUserProfile.gUserProfile.jsonObject.getString(nLockedBy).isEmpty()){
                    String lockedById=gNewUserProfile.gUserProfile.jsonObject.getString(nLockedBy);
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        lockeCategory="PermaLocked by";
                        if(lockedById.equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())){
                            lockedValue="Self locked";
                        }else{
                            String lockedByMention=llGetMemberMention(gGuild,lockedById);
                            if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){
                                lockedValue="<"+lockedById+">";
                            }else{
                                lockedValue=lockedByMention;
                            }
                        }
                    }else{
                        lockeCategory="Locked by";
                        if(lockedById.equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())){
                            lockedValue="Self locked";
                        }else{
                            String lockedByMention=llGetMemberMention(gGuild,lockedById);
                            if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){
                                lockedValue="<"+lockedById+">";
                            }else{
                                lockedValue=lockedByMention;
                            }
                        }
                    }
                }else{
                    lockeCategory="Locked";lockedValue="Yes";
                }

                if(gNewUserProfile.gUserProfile.jsonObject.has(nLockType)){
                    String locktype=gNewUserProfile.gUserProfile.jsonObject.getString(nLockType);
                    logger.info(fName+"locktype="+locktype);
                    if(!locktype.isBlank()&&!(locktype.equalsIgnoreCase(LOCKTYPES.INVALID.getName())||locktype.equalsIgnoreCase(LOCKTYPES.DEFAULT.getName()))){
                        lockedValue+="\ntype "+locktype;
                    }
                }

                if(iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    lockedValue+="\n:timer:"+lsUsefullFunctions.displayDuration(iRestraints.sTimeLockedGetRemaning(gNewUserProfile.gUserProfile));
                }
                embed.addField(lockeCategory,lockedValue,true);
            }
            String vstr="";
            if(gNewUserProfile.gUserProfile.jsonObject.has(nArmsCuffs)){
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nPostRestrictEnabled)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nPostDelay)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).isNull(nPostRestrictEnabled)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).isNull(nPostDelay)){
                    boolean isPostRestrictEnabled=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getBoolean(nPostRestrictEnabled);
                    long lPostDelay=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getLong(nPostDelay);
                    if(!isPostRestrictEnabled){
                        vstr+="\npost slowdown:no";
                    }
                    else if(lPostDelay==1){
                        vstr+="\npost slowdown: 5s";
                    }
                    else if(lPostDelay==2){
                        vstr+="\npost slowdown: 15s";
                    }
                    else if(lPostDelay==3){
                        vstr+="\npost slowdown: 30s";
                    }
                    else if(lPostDelay==4){
                        vstr+="\npost slowdown: 45s";
                    }
                    else if(lPostDelay==5){
                        vstr+="\npost slowdown: 60s";
                    }
                    else if(lPostDelay==9){
                        vstr+="\npost slowdown: restrain base";
                    }
                    else if(lPostDelay>=5000){
                        vstr+="\npost slowdown: "+lPostDelay/1000+" seconds";
                    }
                }
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLegsCuffs)){
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).has(nChannelRestrainEnabled)){
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getBoolean(nChannelRestrainEnabled)){
                        vstr+="\nchannel limiter:yes\nchannel bound to:"+ lsChannelHelper.lsGetTextChannelMention(gGuild,gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getLong(nChannelRestrainId));
                    }else{
                        vstr+="\nchannel limiter:no";
                    }
                }
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                JSONObject jsonObject=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                boolean listenenabled=false,listeninuse=false;
                boolean voiceenabled=false,voiceinuse=false;
                boolean rmuted=false,rdeafened=false;
                if(jsonObject.has(optionVoiceMute)){
                    rmuted=jsonObject.getBoolean(optionVoiceMute);
                }
                if(jsonObject.has(optionVoiceDeafen)){
                    rdeafened=jsonObject.getBoolean(optionVoiceDeafen);
                }
                if(jsonObject.has(nVoiceChannelRestriction_ListenEnabled)){
                    listenenabled=jsonObject.getBoolean(nVoiceChannelRestriction_ListenEnabled);
                }
                if(jsonObject.has(nVoiceChannelRestriction_ListenInUse)){
                    listeninuse=jsonObject.getBoolean(nVoiceChannelRestriction_ListenInUse);
                }
                jsonObject=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                if(jsonObject.has(nVoiceChannelRestriction_MuteEnabled)){
                    voiceenabled=jsonObject.getBoolean(nVoiceChannelRestriction_MuteEnabled);
                }
                if(jsonObject.has(nVoiceChannelRestriction_MuteInUse)){
                    voiceinuse=jsonObject.getBoolean(nVoiceChannelRestriction_MuteInUse);
                }
                if(rdeafened){
                    vstr+="\ndeafened:enabled";
                }
                if(rmuted){
                    vstr+="\nmuted:enabled";
                }
                if(listenenabled&&listeninuse){
                    vstr+="\nearmuffs:enabled&used";
                }else if(listenenabled){
                    vstr+="\nearmuffs:enabled";
                }else{
                    vstr+="\nearmuffs:disabled";
                }
                if(voiceenabled&&voiceinuse){
                    vstr+="\ngag:enabled&used";
                }else if(voiceenabled){
                    vstr+="\ngag:enabled";
                }else{
                    vstr+="\ngag:disabled";
                }

            }
            embed.addField("Restriction",vstr,true);
            if(gNewUserProfile.gUserProfile.jsonObject.has(nStraitjacket)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
                String armstrap="arms: free",crotchstrap="crotch: free";
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                    armstrap="arms: secured";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).has(nStrapArmsLevel)){
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapArmsLevel).equalsIgnoreCase(STRAITJACKETARMSLEVEL.Front.getName())){
                            armstrap="arms: front secured";
                        }
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapArmsLevel).equalsIgnoreCase(STRAITJACKETARMSLEVEL.Reverse.getName())){
                            armstrap="arms: reverse secured";
                        }
                    }
                }
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapCrotch)){
                    crotchstrap="crotch:secured";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).has(nStrapCrotchLevel)){
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapCrotchLevel).equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Double.getName())){
                            crotchstrap="crotch: double strap secured";
                        }
                        else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapCrotchLevel).equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Mono.getName())){
                            crotchstrap="crotch: single strap secured";
                        }
                        else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapCrotchLevel).equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Triple.getName())){
                            crotchstrap="crotch: triple strap secured";
                        }
                    }
                }
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).has(nStrapStitch)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapStitch)){
                    embed.addField("Straitjacket",armstrap+"\n"+crotchstrap+"\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread),true);
                }else{
                    embed.addField("Straitjacket",armstrap+"\n"+crotchstrap,true);
                }

            }
            String strBindings="";
            if(gNewUserProfile.gUserProfile.jsonObject.has(nMitts)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                if(!strBindings.isBlank())strBindings+="\n";
                strBindings+="Mitts: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getString(nLevel);
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nArmsCuffs)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nLevel)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).isBlank()&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equalsIgnoreCase(nNone)){
                if(!strBindings.isBlank())strBindings+="\n";
                strBindings+="Arms: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel);
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLegsCuffs)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).has(nLevel)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel).isBlank()&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel).equalsIgnoreCase(nNone)){
                if(!strBindings.isBlank())strBindings+="\n";
                strBindings+="Legs: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel);
            }
            if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.has(nChastity)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nChastity).getString(nLevel);
                boolean shock=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nShock);
                if(!strBindings.isBlank())strBindings+="\n";
                if(shock){
                    strBindings+="Chastity: "+level+"+shock device";
                }else{
                    strBindings+="Chastity: "+level;
                }
            }
            if(!strBindings.isBlank())embed.addField("Bindings",strBindings,true);
            if(gNewUserProfile.gUserProfile.jsonObject.has(nGag)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).has(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).has(nLevel)){
                String gagLevel="", gagType="";
                if (GAGLEVELS.None.getName().equals(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel))) {
                    gagLevel = "no";
                } else {
                    gagLevel = gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel);
                }
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Ball.getName())){gagType="ball gag";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Dildo.getName())){gagType="dildo";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.DReverseildo.getName())){gagType="reverse-dildo";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.LeatherMuzzle.getName())){gagType="leather muzzle";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Paci.getName())){gagType="paci";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Sock.getName())){gagType="socks";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Tape.getName())){gagType="sticky tape";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Underwear.getName())){gagType="musky underwear";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.WireFrameMuzzle.getName())){gagType="wire-framed muzzle";}
                else{gagType="?";}
                embed.addField("Gag","level: "+gagLevel+"\ntype: "+gagType,true);
            }

            if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.has(nSuit)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn)){
                String matterial=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitMatterial);
                String type=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType);
                if(type.equalsIgnoreCase(iSuit.levelSuitSpecialToyAlpha))embed.addField("Suit","toy alpha",true);
                else if(type.equalsIgnoreCase(iSuit.levelSuitSpecialToyBeta))embed.addField("Suit","toy beta",true);
                else if(type.equalsIgnoreCase(iSuit.levelSuitSpecialToyOmega))embed.addField("Suit","toy omega",true);
                else embed.addField("Suit",matterial+" "+type,true);
            }

            String strHead="";
            if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.has(nHood)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nHood).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nHood).getString(nLevel);
                if(!strHead.isBlank())strHead+="\n";
                strHead+="Hood: "+level;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nBlindfold)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nBlindfold).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nBlindfold).getString(nLevel);
                if(!strHead.isBlank())strHead+="\n";
                strHead+="Blindfold: "+level;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nEarMuffs)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nEarMuffs).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nEarMuffs).getString(nLevel);
                if(!strHead.isBlank())strHead+="\n";
                strHead+="Ear Muffs: "+level;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nCollar)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getBoolean(nOn)){
                if(!strHead.isBlank())strHead+="\n";
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getBoolean(nShockeEnabled)){
                    strHead+="Collar: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getString(nLevel)+" +shock box";
                }else{
                    strHead+="Collar: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getString(nLevel);
                }

            }
            if(!strHead.isBlank())embed.addField("Head",strHead,true);
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLeash)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLeash).getBoolean(nOn)){
                String to=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLeash).getString(nLeash2Member);
                Member mto=llGetMember(gGuild,to);
                if(mto!=null){
                    embed.addField("Leash",mto.getAsMention(),true);
                }else{
                    embed.addField("Leash","Yes",true);
                }
            }
            if(command.equalsIgnoreCase("dm")){
                llSendMessage(gUser,embed);
            }else{
                llSendMessageWithDelete(gGlobal,gTextChannel,embed);
            }
        }
    private void userNotification(String desc){
        String fName="[userNotification]";
            //logger.info(fName+"area="+area);
            logger.info(fName+"desc="+desc);
            if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                    llSendMessageWithDelete(gGlobal,gTextChannel,desc);
                }
            }
    }
    

    private void print(CommandEvent gCommandEvent){
        String fName="[print]";
        logger.info(fName);
        User user=gCommandEvent.getAuthor();
        Guild guild=gCommandEvent.getGuild();
        logger.info(fName + ".user:"+user.getId()+"|"+user.getName());
        logger.info(fName + ".guild:"+guild.getId()+"|"+guild.getName());
            logger.info(fName+".found entry for user:"+user.getId()+"|"+user.getName());
            logger.info(fName+".pritning:"+gNewUserProfile.gUserProfile.jsonObject.toString());
            llSendMessage(user,gNewUserProfile.gUserProfile.jsonObject.toString());
    }


        private void rSlashNT() {
            String fName = "[rSlashNT]";
            logger.info(fName);
            User user=null;String type="",zap="";boolean shockValue=false,shockProvided=false;
            EmbedBuilder embedBuilder=new EmbedBuilder();
            embedBuilder.setColor(llColorRed_Barn);
            //gInteractionHook=gSlashCommandEvent.deferReply(true).complete();
            for(OptionMapping option:gSlashCommandEvent.getOptions()){
                switch (option.getName()){
                    case llCommonKeys.SlashCommandReceive.user:
                        if(option.getType()== OptionType.USER){
                            user=option.getAsUser();
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
            slashReplyPleaseWait();
            if(gSlashInteractionHook==null)logger.error("gSlashInteractionHook is null");
            gCurrentInteractionHook=gSlashInteractionHook;
            if(gCurrentInteractionHook==null)logger.error("gCurrentInteractionHook is null");
            dmMainQuickMenu();
        }
        String gCommandFileMainPath =gFileMainPath+"menuMain.json";
        private void dmMainQuickMenu() {
            String fName = "[dmMenu]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorPurple2);
            if (gTarget == null) {
                if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            }else{
                if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
            }
            if(gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
                embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s"+ sRTitle+" Menu");
            }else{
                embed.setTitle(sRTitle+" Menu");
            }
            String status="";
            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).has(nOwnerId)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nOwnerId)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getBoolean(nOwnerAccepted)){
                String str= lsUserHelper.lsGetUserMentionById(gGuild,gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId));
                if(str!=null){
                    status+="Owned by:"+str;
                }else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId)!=null){
                    status+="Owned by: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getString(nOwnerId);
                }
            }else{
               status+="Not owned";
            }
            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).has(nSecOnwers)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).isNull(nSecOnwers)){
                Iterator<String> keys=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nOwner).getJSONObject(nSecOnwers).keys();
                StringBuilder tmp = new StringBuilder();
                while (keys.hasNext()) {
                    String id = keys.next();
                    tmp.append("\n").append(llGetMemberMention(gGuild, id));
                }
                if(tmp.length()!=0) {
                    status+="\nSec-Owners: "+tmp;
                }
            }
            String access;
            if(gNewUserProfile.gUserProfile.jsonObject.has(nAccess)){
                if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.ExposedOld.getName())){ access = "ask";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Ask.getName())){ access = "ask";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.AskPlus.getName())){ access = "ask+";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Pet.getName())){ access = "owner&secowner";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Key.getName())){access = "key";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Exposed.getName())){ access = "exposed";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Public.getName())){access = "public";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getString(nAccess).equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){access = "protected";}
                else{ access = "invalid"; }
            } else{access="invalid";}
            status+="\nAccess: "+access;
            String gender="",species="";
            if(gNewUserProfile.gUserProfile.jsonObject.has(nGender)){
                switch (gNewUserProfile.gUserProfile.jsonObject.getInt(nGender)) {
                    case vGenderMale:
                        gender="male";
                        break;
                    case vGenderFemale:
                        gender="female";
                        break;
                    default:
                        gender="neutral";
                }
            } else{
                gender="neutral";
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nSpecies)){
                switch (gNewUserProfile.gUserProfile.jsonObject.getInt(nSpecies)) {
                    case vSpecieNeko:
                        species="neko";
                        break;
                    case vSpecieHuman:
                        species="human";
                        break;
                    default:
                        species ="furry";
                }
                if(gNewUserProfile.gUserProfile.jsonObject.has(nSpeciesName)&&!gNewUserProfile.gUserProfile.jsonObject.isNull(nSpeciesName)&&!gNewUserProfile.gUserProfile.jsonObject.getString(nSpeciesName).isBlank()){
                    species =gNewUserProfile.gUserProfile.jsonObject.getString(nSpeciesName);
                }
            } else{
                species="furry";
            }
            status+="\nGender&Species: "+gender+", "+species;
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLocked)&&gNewUserProfile.gUserProfile.jsonObject.getBoolean(nLocked)){
                String lockeCategory="",lockedValue="";
                if(gNewUserProfile.gUserProfile.jsonObject.has(nLockedBy)&&!gNewUserProfile.gUserProfile.jsonObject.isNull(nLockedBy)&&!gNewUserProfile.gUserProfile.jsonObject.getString(nLockedBy).isBlank()&&!gNewUserProfile.gUserProfile.jsonObject.getString(nLockedBy).isEmpty()){
                    String lockedById=gNewUserProfile.gUserProfile.jsonObject.getString(nLockedBy);
                    if(gNewUserProfile.cLOCK.isPermaLocked()){
                        lockeCategory="PermaLocked by";
                        if(lockedById.equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())){
                            lockedValue="Self locked";
                        }else{
                            String lockedByMention=llGetMemberMention(gGuild,lockedById);
                            if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){
                                lockedValue="<"+lockedById+">";
                            }else{
                                lockedValue=lockedByMention;
                            }
                        }
                    }else{
                        lockeCategory="Locked by";
                        if(lockedById.equalsIgnoreCase(gNewUserProfile.gUserProfile.getUser().getId())){
                            lockedValue="Self locked";
                        }else{
                            String lockedByMention=llGetMemberMention(gGuild,lockedById);
                            if(lockedByMention==null||lockedByMention.isEmpty()||lockedByMention.isBlank()){
                                lockedValue="<"+lockedById+">";
                            }else{
                                lockedValue=lockedByMention;
                            }
                        }
                    }
                }else{
                    lockeCategory="Locked";lockedValue="Yes";
                }

                if(gNewUserProfile.gUserProfile.jsonObject.has(nLockType)){
                    String locktype=gNewUserProfile.gUserProfile.jsonObject.getString(nLockType);
                    logger.info(fName+"locktype="+locktype);
                    if(!locktype.isBlank()&&!(locktype.equalsIgnoreCase(LOCKTYPES.INVALID.getName())||locktype.equalsIgnoreCase(LOCKTYPES.DEFAULT.getName()))){
                        lockedValue+="\ntype "+locktype;
                    }
                }

                if(iRestraints.sIsTimeLocked(gNewUserProfile.gUserProfile,gGlobal)){
                    lockedValue+="\n:timer:"+lsUsefullFunctions.displayDuration(iRestraints.sTimeLockedGetRemaning(gNewUserProfile.gUserProfile));
                }
                status+="\n"+lockeCategory+": "+lockedValue;
            }
            String vstr="";
            if(gNewUserProfile.gUserProfile.jsonObject.has(nArmsCuffs)){
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nPostRestrictEnabled)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nPostDelay)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).isNull(nPostRestrictEnabled)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).isNull(nPostDelay)){
                    boolean isPostRestrictEnabled=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getBoolean(nPostRestrictEnabled);
                    long lPostDelay=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getLong(nPostDelay);
                    if(!isPostRestrictEnabled){
                        vstr+="\npost slowdown:no";
                    }
                    else if(lPostDelay==1){
                        vstr+="\npost slowdown: 5s";
                    }
                    else if(lPostDelay==2){
                        vstr+="\npost slowdown: 15s";
                    }
                    else if(lPostDelay==3){
                        vstr+="\npost slowdown: 30s";
                    }
                    else if(lPostDelay==4){
                        vstr+="\npost slowdown: 45s";
                    }
                    else if(lPostDelay==5){
                        vstr+="\npost slowdown: 60s";
                    }
                    else if(lPostDelay==9){
                        vstr+="\npost slowdown: restrain base";
                    }
                    else if(lPostDelay>=5000){
                        vstr+="\npost slowdown: "+lPostDelay/1000+" seconds";
                    }
                }
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLegsCuffs)){
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).has(nChannelRestrainEnabled)){
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getBoolean(nChannelRestrainEnabled)){
                        vstr+="\nchannel limiter:yes\nchannel bound to:"+ lsChannelHelper.lsGetTextChannelMention(gGuild,gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getLong(nChannelRestrainId));
                    }else{
                        vstr+="\nchannel limiter:no";
                    }
                }
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nVoiceChannelRestriction)){
                JSONObject jsonObject=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                boolean listenenabled=false,listeninuse=false;
                boolean voiceenabled=false,voiceinuse=false;
                boolean rmuted=false,rdeafened=false;
                if(jsonObject.has(optionVoiceMute)){
                    rmuted=jsonObject.getBoolean(optionVoiceMute);
                }
                if(jsonObject.has(optionVoiceDeafen)){
                    rdeafened=jsonObject.getBoolean(optionVoiceDeafen);
                }
                if(jsonObject.has(nVoiceChannelRestriction_ListenEnabled)){
                    listenenabled=jsonObject.getBoolean(nVoiceChannelRestriction_ListenEnabled);
                }
                if(jsonObject.has(nVoiceChannelRestriction_ListenInUse)){
                    listeninuse=jsonObject.getBoolean(nVoiceChannelRestriction_ListenInUse);
                }
                jsonObject=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nVoiceChannelRestriction);
                if(jsonObject.has(nVoiceChannelRestriction_MuteEnabled)){
                    voiceenabled=jsonObject.getBoolean(nVoiceChannelRestriction_MuteEnabled);
                }
                if(jsonObject.has(nVoiceChannelRestriction_MuteInUse)){
                    voiceinuse=jsonObject.getBoolean(nVoiceChannelRestriction_MuteInUse);
                }
                if(rdeafened){
                    vstr+="\ndeafened:enabled";
                }
                if(rmuted){
                    vstr+="\nmuted:enabled";
                }
                if(listenenabled&&listeninuse){
                    vstr+="\nearmuffs:enabled&used";
                }else if(listenenabled){
                    vstr+="\nearmuffs:enabled";
                }else{
                    vstr+="\nearmuffs:disabled";
                }
                if(voiceenabled&&voiceinuse){
                    vstr+="\ngag:enabled&used";
                }else if(voiceenabled){
                    vstr+="\ngag:enabled";
                }else{
                    vstr+="\ngag:disabled";
                }

            }
            status+="\n**Restriction**: "+vstr;
            status+="\n**Bindings**";
            if(gNewUserProfile.gUserProfile.jsonObject.has(nStraitjacket)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
                String armstrap="arms: free",crotchstrap="crotch: free";
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                    armstrap="arms: secured";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).has(nStrapArmsLevel)){
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapArmsLevel).equalsIgnoreCase(STRAITJACKETARMSLEVEL.Front.getName())){
                            armstrap="arms: front secured";
                        }
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapArmsLevel).equalsIgnoreCase(STRAITJACKETARMSLEVEL.Reverse.getName())){
                            armstrap="arms: reverse secured";
                        }
                    }
                }
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapCrotch)){
                    crotchstrap="crotch:secured";
                    if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).has(nStrapCrotchLevel)){
                        if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapCrotchLevel).equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Double.getName())){
                            crotchstrap="crotch: double strap secured";
                        }
                        else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapCrotchLevel).equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Mono.getName())){
                            crotchstrap="crotch: single strap secured";
                        }
                        else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getString(nStrapCrotchLevel).equalsIgnoreCase(STRAITJACKETCROTCHSLEVEL.Triple.getName())){
                            crotchstrap="crotch: triple strap secured";
                        }
                    }
                }
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).has(nStrapStitch)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapStitch)){
                    status+="\nStraitjacket: "+armstrap+", "+crotchstrap+""+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSpoolOfThread);
                }else{
                    status+="\nStraitjacket: "+armstrap+", "+crotchstrap;
                }

            }

            if(gNewUserProfile.gUserProfile.jsonObject.has(nMitts)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                status+="\nMitts: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getString(nLevel);
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nArmsCuffs)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).has(nLevel)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).isBlank()&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel).equalsIgnoreCase(nNone)){
                status+="\nArms: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nArmsCuffs).getString(nLevel);
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLegsCuffs)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).has(nLevel)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel).isBlank()&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel).equalsIgnoreCase(nNone)){
                status+="\nLegs: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLegsCuffs).getString(nLevel);
            }
            if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.has(nChastity)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nChastity).getString(nLevel);
                boolean shock=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nChastity).getBoolean(nShock);
                if(shock){
                    status+="\nChastity: "+level+"+shock device";
                }else{
                    status+="\nChastity: "+level;
                }
            }

            if(gNewUserProfile.gUserProfile.jsonObject.has(nGag)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).has(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).has(nLevel)){
                String gagLevel="", gagType="";
                if (GAGLEVELS.None.getName().equals(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel))) {
                    gagLevel = "no";
                } else {
                    gagLevel = gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nLevel);
                }
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Ball.getName())){gagType="ball gag";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Dildo.getName())){gagType="dildo";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.DReverseildo.getName())){gagType="reverse-dildo";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.LeatherMuzzle.getName())){gagType="leather muzzle";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Paci.getName())){gagType="paci";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Sock.getName())){gagType="socks";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Tape.getName())){gagType="sticky tape";}
                else if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.Underwear.getName())){gagType="musky underwear";}
                else if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nGag).getString(nType).equals(GAGTYPES.WireFrameMuzzle.getName())){gagType="wire-framed muzzle";}
                else{gagType="?";}
                status+="\nGag level: "+gagLevel+", type: "+gagType;
            }

            if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.has(nSuit)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getBoolean(nOn)){
                String matterial=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitMatterial);
                String type=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nSuit).getString(nSuitType);
                if(type.equalsIgnoreCase(iSuit.levelSuitSpecialToyAlpha))embed.addField("Suit","toy alpha",true);
                else if(type.equalsIgnoreCase(iSuit.levelSuitSpecialToyBeta))embed.addField("Suit","toy beta",true);
                else if(type.equalsIgnoreCase(iSuit.levelSuitSpecialToyOmega))embed.addField("Suit","toy omega",true);
                status+="\nSuit: "+matterial+" "+type;
            }
            if(isAdult&&gNewUserProfile.gUserProfile.jsonObject.has(nHood)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nHood).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nHood).getString(nLevel);
                status+="\nHood: "+level;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nBlindfold)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nBlindfold).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nBlindfold).getString(nLevel);
                status+="\nBlindfold: "+level;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nEarMuffs)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nEarMuffs).getBoolean(nOn)){
                String level=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nEarMuffs).getString(nLevel);
                status+="\nEar Muffs: "+level;
            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nCollar)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getBoolean(nOn)){
                if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getBoolean(nShockeEnabled)){
                    status+="\nCollar: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getString(nLevel)+" +shock box";
                }else{
                    status+="\nCollar: "+gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nCollar).getString(nLevel);
                }

            }
            if(gNewUserProfile.gUserProfile.jsonObject.has(nLeash)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLeash).getBoolean(nOn)){
                String to=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nLeash).getString(nLeash2Member);
                Member mto=llGetMember(gGuild,to);
                if(mto!=null){
                    status+="\nLeashed to "+mto.getAsMention();
                }else{
                    status+="\nLeashed";
                }
            }
            embed.setDescription(status+"\n\nA quick menu for accessing different sub-commands of virtual bondage roleplay/rd.\nThe selection menu will open the main Dm menu for that sub-command.\nThe buttons allow other quick functions&access.");
            Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);

            messageComponentManager.loadMessageComponents(gCommandFileMainPath);
            logger.info(fName+"component.before="+messageComponentManager.messageBuildComponents.getJson());
            try {
                lcMessageBuildComponent.Button lock=messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("!lock/unlock");
                if(gNewUserProfile.cLOCK.isLocked()){
                    lock.setLabel("🔓Unlock").setCustomId("unlock");
                }else{
                    lock.setLabel("🔒Lock").setCustomId("lock");
                }
                logger.info(fName+"component.after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);
            }
            dmMainQuickMenuListener(message);
        }
        private void dmMainQuickMenuListener(Message message) {
            String fName = "[dmMenu]";
            gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        try {
                            String value=e.getValues().get(0);
                            logger.warn(fName+"id="+value);
                            llMessageDelete(message);
                            if(glUsercommand!=null){
                                switch (value){
                                    case "auth":
                                        if(gTarget!=null){
                                            new rdAuth(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdAuth(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "nametag":
                                        if(gTarget!=null){
                                            new rdNameTag(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdNameTag(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "pishock":
                                        if(gTarget!=null){
                                            new rdPishock(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdPishock(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "timelock":
                                        if(gTarget!=null){
                                            new rdTimelock(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdTimelock(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "timeout":
                                        if(gTarget!=null){
                                            new rdTimeout(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdTimeout(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "gag":
                                        if(gTarget!=null){
                                            new rdGag(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdGag(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "collar":
                                        if(gTarget!=null){
                                            new rdCollar(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdCollar(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "cuffs":
                                        if(gTarget!=null){
                                            new rdCuffs(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdCuffs(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "straitjacket":
                                        if(gTarget!=null){
                                            new rdStraitjacket(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdStraitjacket(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "mitts":
                                        if(gTarget!=null){
                                            new rdMitts(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdMitts(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "suit":
                                        if(gTarget!=null){
                                            new rdSuit(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdSuit(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "hood":
                                        if(gTarget!=null){
                                            new rdHood(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdHood(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "chastity":
                                        if(gTarget!=null){
                                            new rdChastity(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdChastity(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "blindfold":
                                        if(gTarget!=null){
                                            new rdBlindfold(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdBlindfold(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "ears":
                                        if(gTarget!=null){
                                            new rdEars(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdEars(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "lock":
                                        if(gTarget!=null){
                                            new rdLock(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdLock(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                    case "encase":
                                        if(gTarget!=null){
                                            new rdEncase(gGlobal,glUsercommand,true,"",gTarget);
                                        }else{
                                            new rdEncase(gGlobal,glUsercommand,true,"");
                                        }
                                        break;
                                }
                            }else if(gSlashCommandEvent!=null){
                                deferReplySet(e);
                                if(gComponentInteractionHook==null) logger.error("gComponentInteractionHook is null");
                                switch (value){
                                    case "auth":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected auth menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdAuth(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdAuth(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "nametag":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected tag menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdNameTag(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdNameTag(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "pishock":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected pishock menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdPishock(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdPishock(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "timelock":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected timelock menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdTimelock(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdTimelock(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "timeout":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected timeout menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdTimeout(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdTimeout(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "gag":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected gag menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdGag(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdGag(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "collar":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected collar menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdCollar(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdCollar(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "cuffs":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected cuffs menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdCuffs(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdCuffs(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "straitjacket":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected straitjacket menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdStraitjacket(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdStraitjacket(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "mitts":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected mitts menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdMitts(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdMitts(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "suit":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected suit menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdSuit(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdSuit(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "hood":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected hood menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdHood(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdHood(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "chastity":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected chastity menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdChastity(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdChastity(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "blindfold":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected blindfold menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdBlindfold(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdBlindfold(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "ears":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected ears menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdEars(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdEars(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "lock":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected lock menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdLock(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdLock(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                    case "encase":
                                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"Selected encase menu", llColorBlue1);
                                        if(gTarget!=null){
                                            new rdEncase(gGlobal,gComponentInteractionHook,true,"",gTarget);
                                        }else{
                                            new rdEncase(gGlobal,gComponentInteractionHook,true,"");
                                        }
                                        break;
                                }
                            }
                            else
                            switch (value){
                                case "auth":
                                    if(gTarget!=null){
                                        new rdAuth(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdAuth(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "nametag":
                                    if(gTarget!=null){
                                        new rdNameTag(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdNameTag(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "pishock":
                                    if(gTarget!=null){
                                        new rdPishock(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdPishock(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "timelock":
                                    if(gTarget!=null){
                                        new rdTimelock(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdTimelock(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "timeout":
                                    if(gTarget!=null){
                                        new rdTimeout(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdTimeout(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "gag":
                                    if(gTarget!=null){
                                        new rdGag(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdGag(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "collar":
                                    if(gTarget!=null){
                                        new rdCollar(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdCollar(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "cuffs":
                                    if(gTarget!=null){
                                        new rdCuffs(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdCuffs(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "straitjacket":
                                    if(gTarget!=null){
                                        new rdStraitjacket(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdStraitjacket(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "mitts":
                                    if(gTarget!=null){
                                        new rdMitts(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdMitts(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "suit":
                                    if(gTarget!=null){
                                        new rdSuit(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdSuit(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "hood":
                                    if(gTarget!=null){
                                        new rdHood(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdHood(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "chastity":
                                    if(gTarget!=null){
                                        new rdChastity(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdChastity(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "blindfold":
                                    if(gTarget!=null){
                                        new rdBlindfold(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdBlindfold(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "ears":
                                    if(gTarget!=null){
                                        new rdEars(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdEars(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "lock":
                                    if(gTarget!=null){
                                        new rdLock(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdLock(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                                case "encase":
                                    if(gTarget!=null){
                                        new rdEncase(gGlobal,gCommandEvent,true,"",gTarget);
                                    }else{
                                        new rdEncase(gGlobal,gCommandEvent,true,"");
                                    }
                                    break;
                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
            gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            switch (id){
                                case lsUnicodeEmotes.aliasWhiteCheckMark:
                                    logger.info(fName+"close");
                                    return;
                                case lsUnicodeEmotes.aliasInformationSource:
                                    rHelp("main");
                                    break;
                                case lsUnicodeEmotes.aliasLock:
                                    if(gTarget!=null){
                                        rLock(gTarget,"lock");
                                    }else{
                                        rLock("lock");
                                    }
                                    break;
                                case lsUnicodeEmotes.aliasUnlock:
                                    if(gTarget!=null){
                                        rLock(gTarget,"unlock");
                                    }else{
                                        rLock("unlock");
                                    }
                                    break;
                                case lsUnicodeEmotes.aliasSOS:
                                    if(gTarget!=null){
                                        rRedRestraint(gTarget,false,false);
                                    }else{
                                        rRedRestraint(false,false);
                                    }

                                    break;
                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                return;
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                rDMMenuHelp("main");
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
        }
   
        private void getBDSMRestriction(){
            String fName = "[getBDSMRestriction]";
            logger.info(fName);
            try {
                lcJSONGuildProfile entry=gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
                if(!entry.isExistent()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "No such entry", llColorRed);return;
                }
                if(!entry.jsonObject.has(gGlobal.keyBDSMRestrictions)||entry.jsonObject.isNull(gGlobal.keyBDSMRestrictions)){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "No such flag "+gGlobal.keyBDSMRestrictions, llColorRed);return;
                }
                int result=entry.jsonObject.getInt(gGlobal.keyBDSMRestrictions);
                if(result==2){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Flag "+gGlobal.keyBDSMRestrictions+"= blocked", llColorPurple2);
                }else
                if(result==1){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Flag "+gGlobal.keyBDSMRestrictions+"= restricted to nsfw channels or server", llColorPurple2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Flag "+gGlobal.keyBDSMRestrictions+"= none", llColorPurple2);
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Error!", llColorRed);
            }
        }
        private void setBDSMRestriction(int flag){
            String fName = "[BDSMRestriction]";
            logger.info(fName);
            try {
                lcJSONGuildProfile entry=gGlobal.getGuildSettings(gGuild,gGlobal.keyServer);
                if(!entry.isExistent()){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "No such entry", llColorRed);return;
                }
                entry.jsonObject.put(gGlobal.keyBDSMRestrictions,flag);
                gGlobal.putGuildSettings(gGuild,entry);
                if(flag==2){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Flag "+gGlobal.keyBDSMRestrictions+"= blocked", llColorPurple2);
                }else
                if(flag==1){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Flag "+gGlobal.keyBDSMRestrictions+"= restricted to nsfw channels or server", llColorPurple2);
                }else{
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Flag "+gGlobal.keyBDSMRestrictions+"= none", llColorPurple2);
                }
                if(!entry.saveProfile(llv2_GuildsSettings)){
                    llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Failed to save!", llColorRed);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gTextChannel, sRTitle, "Error!", llColorRed);
            }
        }

        private void setRDEnable(boolean enable) {
            String fName = "[setEnable]";
            try {
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                gBDSMCommands.restraints.setEnable(enable);gBDSMCommands.restraints.save();
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void getRDChannels(int type, boolean toDM) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list= gBDSMCommands.restraints.getAllowedChannelsAsLong();
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
                    logger.info(fName+"denied");
                    List<Long>list= gBDSMCommands.restraints.getDeniedChannelsAsLong();
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
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void getRDRoles(int type, boolean toDM) {
            String fName = "[getRoles]";
            try {
                logger.info(fName + "type=" +type+", toDM="+toDM);
                if(type==1){
                    logger.info(fName+"allowed");
                    List<Long>list= gBDSMCommands.restraints.getAllowedRolesAsLong();
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
                    logger.info(fName+"denied");
                    List<Long>list= gBDSMCommands.restraints.getDeniedRolesAsLong();
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
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void setRDChannel(int type, int action, Message message) {
            String fName = "[setChannel]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+vAdd);
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result= gBDSMCommands.restraints.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString( gBDSMCommands.restraints.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+vSet);
                        if(! gBDSMCommands.restraints.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result= gBDSMCommands.restraints.addAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set channels.\nAllowed channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString( gBDSMCommands.restraints.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+vRem);
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result= gBDSMCommands.restraints.remAllowedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed channels.\nAllowed channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString( gBDSMCommands.restraints.getAllowedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+vClear);
                        if(! gBDSMCommands.restraints.clearAllowedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared allowed channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+vAdd);
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result= gBDSMCommands.restraints.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString( gBDSMCommands.restraints.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+vSet);
                        if(! gBDSMCommands.restraints.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result= gBDSMCommands.restraints.addDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set channels.\nDenied channels set to: "+lsChannelHelper.lsGetTextChannelsMentionAsString( gBDSMCommands.restraints.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+vRem);
                        List<TextChannel>textChannels=message.getMentionedChannels();
                        for(TextChannel textChannel:textChannels){
                            result= gBDSMCommands.restraints.remDeniedChannel(textChannel);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed channels.\nDenied channels set to:"+lsChannelHelper.lsGetTextChannelsMentionAsString( gBDSMCommands.restraints.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+vClear);
                        if(! gBDSMCommands.restraints.clearDeniedChannels()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared denied channels.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void setRDRole(int type, int action, Message message) {
            String fName = "[setRole]";
            try {
                logger.info(fName + "type=" +type+", action="+action);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                boolean updated=false, result=false;
                if(type==1){
                    logger.info(fName+"allowed");
                    if(action==1){
                        logger.info(fName+vAdd);
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result= gBDSMCommands.restraints.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString( gBDSMCommands.restraints.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+vSet);
                        if(! gBDSMCommands.restraints.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result= gBDSMCommands.restraints.addAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set roles.\nAllowed roles set to: "+lsRoleHelper.lsGetRolesMentionAsString( gBDSMCommands.restraints.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+vRem);
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result= gBDSMCommands.restraints.remAllowedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed roles.\nAllowed roles set to:"+lsRoleHelper.lsGetRolesMentionAsString( gBDSMCommands.restraints.getAllowedRolesAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+vClear);
                        if(! gBDSMCommands.restraints.clearAllowedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared allowed roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
                if(type==-1){
                    logger.info(fName+"denied");
                    if(action==1){
                        logger.info(fName+vAdd);
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result= gBDSMCommands.restraints.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Added new roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString( gBDSMCommands.restraints.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==2){
                        logger.info(fName+vSet);
                        if(! gBDSMCommands.restraints.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result= gBDSMCommands.restraints.addDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Set roles.\nDenied roles set to: "+lsRoleHelper.lsGetRolesMentionAsString( gBDSMCommands.restraints.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);

                    }
                    if(action==-1){
                        logger.info(fName+vRem);
                        List<Role>roles=message.getMentionedRoles();
                        for(Role role:roles){
                            result= gBDSMCommands.restraints.remDeniedRole(role);
                            if(!updated&&result)updated=true;
                        }
                        if(!updated){
                            logger.warn(fName+"failed to update");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to update!");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save!");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Removed roles.\nDenied roles set to:"+lsRoleHelper.lsGetRolesMentionAsString( gBDSMCommands.restraints.getDeniedChannelsAsLong(),", ",gGuild), llColors.llColorOrange_Bittersweet);
                    }
                    if(action==-2){
                        logger.info(fName+vClear);
                        if(! gBDSMCommands.restraints.clearDeniedRoles()){
                            logger.warn(fName+"failed to clear");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to set");
                            return;
                        }
                        if(! gBDSMCommands.restraints.save()){
                            logger.warn(fName+"failed to save");
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Failed to save");
                            return;
                        }
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,sRTitle,"Cleared denied roles.", llColors.llColorOrange_Bittersweet);
                    }
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void menuRDGuild(){
            String fName="[menuGuild]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                logger.info(fName+"gProfile="+gBDSMCommands.restraints.gProfile.jsonObject.toString());
                embed.setColor(llColorBlue1);
                embed.setTitle(sRTitle+" Options");
                embed.addField("Enable","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" or "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to enable/disable for this server.",false);
                embed.addField("Allowed channels","Commands:`"+llPrefixStr+"rd server allowchannels  :one:/list|add|rem|set|clear`",false);
                embed.addField("Blocked channels","Commands:`"+llPrefixStr+"rd server blockchannels :two:/list|add|rem|set|clear`",false);
                embed.addField("Allowed roles","Commands:`"+llPrefixStr+"rd server allowroles :three:/list|add|rem|set|clear`",false);
                embed.addField("Blocked roles","Commands:`"+llPrefixStr+"rd server blockroles :four:/list|add|rem|set|clear`",false);
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
                if(gBDSMCommands.restraints.getEnable()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                if(!gBDSMCommands.restraints.getAllowedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(!gBDSMCommands.restraints.getDeniedChannelsAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(!gBDSMCommands.restraints.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(!gBDSMCommands.restraints.getAllowedRolesAsLong().isEmpty()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    rHelp("main");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setRDEnable(true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setRDEnable(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                    getRDChannels(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                    getRDChannels(-1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                                    getRDRoles(1,true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                                    getRDRoles(-1,true);
                                }
                                else{
                                    menuGuild();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }


        private void MenuGuildSetup(){
            String fName="[MenuGuildSetup]";
            try {
                logger.info(fName);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
                embed.setTitle("Restrain Discord Setup");
                boolean isDIsabled=false;
                isDIsabled= gBDSMCommands.restraints.getNotificationDisabled();
                logger.info(fName+"isDIsabled="+isDIsabled);
                String desc="";
                String quickSummonWithSpace=llPrefixStr+quickAlias+" ",quickSummonWithSpaceAuth=llPrefixStr+"auth ",quickSummonWithSpace2=llPrefixStr+"";
                if(isDIsabled){
                    desc+="\nStatus: disabled";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable";
                }else{
                    desc+="\nStatus: enabled";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable";
                }
                long notificationRedirectId= gBDSMCommands.restraints.getRedirectNotificationChanelAsLong();
                TextChannel notificationRedirect= gBDSMCommands.restraints.getRedirectNotificationChanel(gGuild);
                if(notificationRedirectId==0){
                    desc+="\nRedirect: disabled";
                }else
                if(notificationRedirect==null){
                    desc+="\nRedirect: invalid("+notificationRedirectId+")";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" to clear";
                }else{
                    desc+="\nRedirect: "+notificationRedirect.getAsMention()+"";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" to clear";
                }
                embed.addField("Notification",desc,false);
                if(isAdult){
                    embed.addField("Restriction","Set the bdsm command restrictions to: `"+quickSummonWithSpace+" restrict set none/nsfw/denied`\n none will allow most commands to work, just the chastity and some options in the gag wont work\nnsfw forces the commands to run only in nsfw channel or server\ndenied wile denies any access even in nsfw channel or server\n`"+quickSummonWithSpace+"restrict get` will get the current restrictions",false);
                }
                embed.addField("Server options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                if(isDIsabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }
                if(notificationRedirectId!=0&&notificationRedirect!=null){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))) {
                                    rHelp(vSetup);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                    setNotificationEnable(true);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                    setNotificationEnable(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))) {
                                    setNotificationChannel(vClear);
                                }
                                else{
                                    MenuGuildSetup();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> llMessageDelete(message));
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void setNotificationEnable(boolean enable) {
            String fName = "[setNotificationEnable]";
            try {
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                logger.info(fName + "enable=" + enable);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                 gBDSMCommands.restraints.setDisableNotification(enable); gBDSMCommands.restraints.save();
                if(enable){
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to enable for guild.", llColors.llColorOrange_Bittersweet);
                }else{
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Set to disable for guild.", llColors.llColorOrange_Bittersweet);
                }
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
        private void setNotificationChannel(String command) {
            String fName = "[setNotificationChannel]";
            try {
                logger.info(fName+"command="+command);
                if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    logger.info(fName+"denied");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
                    return;
                }
                if(command.equalsIgnoreCase(vSet)){ 
                    List<TextChannel>textChannels=gCommandEvent.getMessage().getMentionedChannels();
                    if(textChannels.size()>0){
                         TextChannel textChannel=textChannels.get(0);
                         if( gBDSMCommands.restraints.setNotificationChannel(textChannel)&& gBDSMCommands.restraints.save()){
                             lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Set "+textChannel.getAsMention()+" as notification channel!",llColors.llColorPurple1);

                         }
                    }else{
                        lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Require mentioning a channel!",llColors.llColorRed_Barn);
                    }
                }else
                if(command.equalsIgnoreCase(vClear)){
                    if( gBDSMCommands.restraints.clearNotificationChannel()&& gBDSMCommands.restraints.save()){
                        lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Cleared notification channel!",llColors.llColorPurple1);

                    }
                }else{
                    Message message=lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Please mention the channel were you want the notifications to be sent.",llColors.llColorPurple1);
                    gWaiter.waitForEvent(GuildMessageReceivedEvent.class,
                            e -> e.getAuthor().getIdLong()==gUser.getIdLong()&&e.getChannel().getIdLong()==gTextChannel.getIdLong(),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    List<TextChannel>textChannels=e.getMessage().getMentionedChannels();
                                    llMessageDelete(message);
                                    if(content.equalsIgnoreCase("!cancel")){
                                        MenuGuildSetup();
                                    }else if(textChannels.size()>0){
                                        TextChannel textChannel=textChannels.get(0);
                                        if( gBDSMCommands.restraints.setNotificationChannel(textChannel)&& gBDSMCommands.restraints.save()){
                                            lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Set "+textChannel.getAsMention()+" as notification channel!",llColors.llColorPurple1);

                                        }
                                    }else {
                                        lsMessageHelper.lsSendQuickEmbedMessageResponse(gTextChannel,sRTitle,"Require mentioning a channel!",llColors.llColorRed_Barn);
                                    }

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }
                   
               
            } catch (Exception e) {
                logger.error(cName+fName+"exception:"+e);
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e.toString());
            }
        }
}}
