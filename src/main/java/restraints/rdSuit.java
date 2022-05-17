package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.enums.*;
import restraints.in.*;
import restraints.models.*;
import userprofiles.entities.rSlaveRegistry;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdSuit extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass()); 
	String sMainRTitle ="Suit",gQuickPrefix="suit",gCommand="suit";
    public rdSuit(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "Suit-DiscordRestraints";
        this.help = "rdSuit";
        this.aliases = new String[]{"suit","suits"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_SFW;
        this.hidden=true;
    }
    public rdSuit(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdSuit(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdSuit(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdSuit(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdSuit(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdSuit(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdSuit(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
    protected class runLocal extends rdExtension implements Runnable, iSuit {
        String cName="[runLocal]";

        public runLocal(CommandEvent ev){
            logger.info(".run build");
            launch(gGlobal,ev);
            messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String forward, Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(SlashCommandEvent ev) {

            logger.info(cName + ".run build");
            launch(gGlobal,ev);
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
                setTitleStr(rdSuit.this.sMainRTitle);setPrefixStr(rdSuit.this.llPrefixStr);setCommandStr(rdSuit.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdSuit_commands");
                lsUsefullFunctions.setThreadName4Display("rdSuit");
                if(!gBDSMCommands.restraints.getEnable()) {
                    logger.info(fName + "its disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser, sRTitle, "It's disabled in " + gGuild.getName() + "!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                else if(!gBDSMCommands.restraints.isChannelAllowed(gTextChannel)){
                    logger.info(fName+"its not allowed by channel");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                else if(!gBDSMCommands.restraints.isRoleAllowed(gMember)){
                    logger.info(fName+"its not allowed by roles");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                    return;
                }
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuLevels(gTarget,"main");
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuLevels(gTarget,"main");
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
                    if(!isAdult){blocked();return;}
                    else if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    boolean isInvalidCommand=true;
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
                    logger.info(fName+".gRawForward="+gRawForward);
                    if(gRawForward.isBlank()){
                        menuLevels(gTarget,"main");isInvalidCommand=false;
                    }
                    if(gRawForward.equalsIgnoreCase("help")){
                        rHelp("main");isInvalidCommand=false;
                    }
                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult){blocked();return;}
                    else if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}

                    boolean isInvalidCommand=true;
                    logger.info(fName+".gIsForward="+gIsForward);
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
                        else {menuLevels(null,"main");isInvalidCommand=false;}
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" +gItems[0]);
                        if(gItems[0].equalsIgnoreCase("help")){
                            rHelp("main");isInvalidCommand=false;}
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

                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                menuLevels(gTarget,"main");isInvalidCommand=false;
                            }else{
                                logger.info(fName + ".target:"+gTarget.getId()+"|"+gTarget.getEffectiveName());
                                if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                                if(gItems.length==3&&gItems[1].equalsIgnoreCase(vToy)&&gItems[2].equalsIgnoreCase("custom")){
                                    rToyGoodWords(gTarget); isInvalidCommand=false;
                                }
                                else if(gItems.length==3&&gItems[1].equalsIgnoreCase(vToy)&&gItems[2].equalsIgnoreCase("gag")){
                                    rToyGagWords(gTarget); isInvalidCommand=false;
                                } else
                                if(gItems.length==2&&gItems[1].equalsIgnoreCase(vToy)){
                                    rSuit(gTarget,vStatus); isInvalidCommand=false;
                                }else
                                if(gItems.length==2&&gItems[1].equalsIgnoreCase(vOff)){
                                    rSuit(gTarget,vOff); isInvalidCommand=false;
                                }else
                                if(gItems.length==2&&(gItems[1].equalsIgnoreCase(cmdToyAdd1)||gItems[1].equalsIgnoreCase(cmdToyAdd5)||gItems[1].equalsIgnoreCase(cmdToyAdd10)||gItems[1].equalsIgnoreCase(cmdToyAdd25))){
                                    rSuit(gTarget,gItems[1]); isInvalidCommand=false;
                                }else
                                if(gItems.length==2&&(gItems[1].equalsIgnoreCase(cmdToyRem1)||gItems[1].equalsIgnoreCase(cmdToyRem5)||gItems[1].equalsIgnoreCase(cmdToyRem10)||gItems[1].equalsIgnoreCase(cmdToyRem25))){
                                    rSuit(gTarget,gItems[1]); isInvalidCommand=false;
                                }else
                                if(gItems.length==2&&gItems[1].equalsIgnoreCase(vRelease)){
                                    rSuit(gTarget,vRelease); isInvalidCommand=false;
                                }else
                                if(gItems.length==2&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))
                                        &&(gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.name)||gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.command))
                                ){
                                    rSuit(gTarget, gItems[1]);
                                }else
                                if(gItems.length==2&&(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))
                                        &&(gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.name)||gItems[1].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.command))
                                ){
                                    rSuit(gTarget, gItems[1]);
                                }
                                else {
                                    select(gItems);
                                    if (gType.isBlank()) {
                                        logger.warn(fName + ".invalid type");
                                    } else if (gMatterial.equalsIgnoreCase(vToy)) {
                                        rSuit(gTarget, gType);
                                        isInvalidCommand = false;
                                    } else if (gType.equalsIgnoreCase("off")) {
                                        rSuit(gTarget, gType);
                                        isInvalidCommand = false;
                                    }else if(gType.equalsIgnoreCase(cmdToyAdd1)||gType.equalsIgnoreCase(cmdToyAdd5)||gType.equalsIgnoreCase(cmdToyAdd10)||gType.equalsIgnoreCase(cmdToyAdd25)){
                                        rSuit(gType); isInvalidCommand=false;
                                    }else if(gType.equalsIgnoreCase(cmdToyRem1)||gType.equalsIgnoreCase(cmdToyRem5)||gType.equalsIgnoreCase(cmdToyRem10)||gType.equalsIgnoreCase(cmdToyRem25)){
                                        rSuit(gType); isInvalidCommand=false;
                                    }else if (gMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())) {
                                        rSuit_latex(gTarget, gType);
                                        isInvalidCommand = false;
                                    } else if (gMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())) {
                                        rSuit_rubber(gTarget, gType);
                                        isInvalidCommand = false;
                                    } else {
                                        logger.warn(fName + ".invalid matterial");
                                    }
                                }
                            }
                            if(isInvalidCommand&&gTarget!=null){
                                menuLevels(gTarget,"main");isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                            if(gItems==null||gItems.length==0){
                                menuLevels(null,"main");isInvalidCommand=false;
                            }
                            else if(gItems.length==2&&gItems[0].equalsIgnoreCase(vToy)&&gItems[1].equalsIgnoreCase("custom")){
                                rToyGoodWords(); isInvalidCommand=false;
                            }
                            else if(gItems.length==2&&gItems[0].equalsIgnoreCase(vToy)&&gItems[1].equalsIgnoreCase("gag")){
                                rToyGagWords(); isInvalidCommand=false;
                            }
                            else if(gItems[0].equalsIgnoreCase(vRed)){
                                isInvalidCommand=false;gmoduleSoloRedHelper.doAsk(strRedSoloAsk,strRedSoloYes,() -> {gNewUserProfile.cSUIT.release();});}
                            else if(gItems.length==1&&gItems[0].equalsIgnoreCase(vToy)){
                                rSuit(vStatus); isInvalidCommand=false;
                            }
                            else if(gItems.length==1&&gItems[0].equalsIgnoreCase(vOff)){
                                rSuit(vOff); isInvalidCommand=false;
                            }
                            else if(gItems.length==1&&(gItems[0].equalsIgnoreCase(cmdToyAdd1)||gItems[0].equalsIgnoreCase(cmdToyAdd5)||gItems[0].equalsIgnoreCase(cmdToyAdd10)||gItems[0].equalsIgnoreCase(cmdToyAdd25))){
                                rSuit(gItems[0]); isInvalidCommand=false;
                            }
                            else if(gItems.length==1&&(gItems[0].equalsIgnoreCase(cmdToyRem1)||gItems[0].equalsIgnoreCase(cmdToyRem5)||gItems[0].equalsIgnoreCase(cmdToyRem10)||gItems[0].equalsIgnoreCase(cmdToyRem25))){
                                rSuit(gItems[0]); isInvalidCommand=false;
                            }
                            else if(gItems.length==1&&gItems[0].equalsIgnoreCase(vRelease)){
                                rSuit(vRelease); isInvalidCommand=false;
                            }
                            else if(gItems.length==1&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))
                                    &&(gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.name)||gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.command))
                            ){
                                rSuit(gItems[0]);
                            }
                            else if(gItems.length==1&&(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))
                                    &&(gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.name)||gItems[0].equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.command))
                            ){
                                rSuit(gItems[0]);
                            }
                            else{
                                select(gItems);
                                if(gType.isBlank()){
                                    logger.warn(fName + ".invalid type");
                                }else
                                if(gMatterial.equalsIgnoreCase(vToy)){
                                    rSuit(gType); isInvalidCommand=false;
                                }else
                                if(gType.equalsIgnoreCase("off")){
                                    rSuit(gType); isInvalidCommand=false;
                                }else if(gType.equalsIgnoreCase(cmdToyAdd1)||gType.equalsIgnoreCase(cmdToyAdd5)||gType.equalsIgnoreCase(cmdToyAdd10)||gType.equalsIgnoreCase(cmdToyAdd25)){
                                    rSuit(gType); isInvalidCommand=false;
                                }else if(gType.equalsIgnoreCase(cmdToyRem1)||gType.equalsIgnoreCase(cmdToyRem5)||gType.equalsIgnoreCase(cmdToyRem10)||gType.equalsIgnoreCase(cmdToyRem25)){
                                    rSuit(gType); isInvalidCommand=false;
                                }else if(gMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                    rSuit_latex(gType);isInvalidCommand=false;
                                }else if(gMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                    rSuit_rubber(gType);isInvalidCommand=false;
                                }else{
                                    logger.warn(fName + ".invalid matterial");
                                }
                            }
                            if(isInvalidCommand){
                                menuLevels(null,"main");isInvalidCommand=false;
                            }
                        }
                    }

                    if(isInvalidCommand){
                        llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sMainRTitle,"You provided an incorrect command!", llColorRed);
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
            logger.info(".run ended");
        }


    private void rHelp(String command){
        String fName="[rHelp]";
        logger.info(fName);
        logger.info(fName + ".command="+command);
        String desc="N/A";
        String quickSummonWithSpace=llPrefixStr+quickAlias+" <@Pet> suit ";
        String quickSummonWithSpace2=llPrefixStr+"suit <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strSupportTitle,strSupport,false);
        embed.addField("OwO","Thick suit that covers the wearer's body.",false);
        desc=newLine+quickSummonWithSpace2+"off"+endLine+" to take it off";
        desc+="\nTo put on a suit please input matterial `"+ SUITMATTERIAL.Latex.getName()+"/"+ COLLARLEVELS.Rubber.getName()+"` and type `"+iSuit.levelSuitCatsuit+","+iSuit.levelSuitPuppy+","+iSuit.levelSuitKitty+","+iSuit.levelSuitPony+","+iSuit.levelSuitDrone+","+iSuit.levelSuitCow+"`";
        embed.addField("Commands",desc,false);
        embed.addField("Toy suits","This are special full suit with special features. Following commands are `"+quickSummonWithSpace2+"toy "+vAlpha+"|"+vBeta+"|"+vOmega+"`.\nFor released|info: `"+quickSummonWithSpace2+vRelease+"|toy`\nRestrictions:\n\t"+vAlpha+": 25 score point required to release yourself without voting or voting by others, no gag safeword, 20 free speech and unable to take off or change restrictions that is part of the suit\n\t"+vBeta+":+access set to exposed, require 50 score and voting to get released, 5 free speech\n\t"+vOmega+":+require 90 score and 5 votes to get released, access set to exposed, 2 free speech"+"\n**To add  or remove points:** `"+quickSummonWithSpace2+"+1|+5|+10|+25|-1|-5|-10|-25`",false);
        if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
            logger.info(fName+"sent as slash");
        }
        else if(llSendMessageStatus(gUser,embed)){
            llSendMessageWithDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            llSendMessageStatus(gTextChannel,embed);
        }
    }
        String gType="",gMatterial="";boolean gIsFull=false;
        private void select(String items[]){
            String fName = "[select]";
            logger.info(fName);
            gMatterial=gNewUserProfile.cSUIT.getSuitMatterialAsString();
            gType=gNewUserProfile.cSUIT.getSuitTypeAsString();
            for(int i=0;i<items.length;i++){
                if(items[i].equalsIgnoreCase("off")){
                    gType="off";return;
                }
                switch (items[i].toLowerCase()){
                    case iSuit.levelSuitLatex:
                        gMatterial= iSuit.levelSuitLatex;break;
                    case  iSuit.levelSuitRubber:
                        gMatterial= iSuit.levelSuitRubber;break;
                    case  iSuit.levelSuitTextile:
                        gMatterial= iSuit.levelSuitTextile;break;
                    case  iSuit.levelSuitToy:
                        gMatterial= iSuit.levelSuitToy;break;
                }
                switch (items[i].toLowerCase()){
                    case iSuit.levelSuitPony:
                        gType=iSuit.levelSuitPony;break;
                    case iSuit.levelSuitPuppy:
                        gType=iSuit.levelSuitPuppy;break;
                    case iSuit.levelSuitKitty:
                        gType=iSuit.levelSuitKitty;break;
                    case iSuit.levelSuitCow:
                        gType=iSuit.levelSuitCow;break;
                    case iSuit.levelSuitDrone:
                        gType=iSuit.levelSuitDrone;break;
                    case iSuit.levelSuitCatsuit:
                        gType=iSuit.levelSuitCatsuit;break;
                    case iSuit.levelSuitPlush:
                        gType=iSuit.levelSuitPlush;break;
                    case vAlpha:
                        gType=iSuit.levelSuitSpecialToyAlpha;break;
                    case vBeta:
                        gType=iSuit.levelSuitSpecialToyBeta;break;
                    case vOmega:
                        gType=iSuit.levelSuitSpecialToyOmega;break;
                    case vStatus:
                        gType=vStatus;break;
                    case vRelease:
                        gType=vRelease;gMatterial=vToy;break;
                    case cmdToyAdd1:
                        gType=cmdToyAdd1;break;
                    case cmdToyAdd5:
                        gType=cmdToyAdd5;break;
                    case cmdToyAdd10:
                        gType=cmdToyAdd10;break;
                    case cmdToyAdd25:
                        gType=cmdToyAdd25;break;
                    case cmdToyRem1:
                        gType=cmdToyRem1;break;
                    case cmdToyRem5:
                        gType=cmdToyRem5;break;
                    case cmdToyRem10:
                        gType=cmdToyRem10;break;
                    case cmdToyRem25:
                        gType=cmdToyRem25;break;
                    case iSuit.levelSuitReindeer:
                        gType=iSuit.levelSuitReindeer;break;
                }
            }
        }
        private void rSuit(String command) {
            String fName = "[rSuit]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            JSONArray customToyGoodTalk=new JSONArray();boolean onlyUseCustom=false;
            customToyGoodTalk=gNewUserProfile.cSUIT.getCustomGoodTalk();
            try {
                logger.info(fName + ".has flagOnlyUseCustom");
                onlyUseCustom=gNewUserProfile.cGAG.isFlagOnlyUseCustom();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(command.equalsIgnoreCase(vStatus)||command.equalsIgnoreCase(vToy)){
                logger.info(fName + ".status");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gUser.getName()+"'s toy suit status");embedBuilder.setColor(llColorBlue1);
                if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                    embedBuilder.setDescription("Not wearing any toy suit.");
                }else{
                    embedBuilder.setDescription("Rank: "+getToyRank(gNewUserProfile.gUserProfile)+"\nScore:"+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+"\nFree talk:"+gNewUserProfile.cSUIT.getSuitFreeTalkAvailable()+"/"+allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                }
                lsMessageHelper.lsSendMessageWithDelete(gGlobal,gTextChannel,embedBuilder);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsTheyPermalocked, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Public, llColorRed);
                return;
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }
           
            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cSUIT.isOn()){
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
                        logger.info(fName + ".can't take off do to jacket");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItfOffStraitjacket, llColorRed);
                        userNotificationSuit( actionStruggle, gUser.getAsMention()+" attempts to take off their suit but the straitjacket sleeves are preventing it.");
                    }
                    else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                        userNotificationSuit( actionStruggle,gUser.getAsMention()+" struggled to take off the suit from their body but failed due to its locked with a padlock.");
                    }else{
                        if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                            logger.info(fName + ".can't use do to special suit");
                            sendOrUpdatePrivateEmbed(sRTitle,"Can't simple take it off due to its a toy suit. Run `"+llPrefixStr+"suit release` to take off the suit.", llColorRed);
                            return;
                        }
                        gNewUserProfile.cSUIT.setOn( false);gNewUserProfile.cSUIT.setSuitMatterial( ""); gNewUserProfile.cSUIT.setSuitType( "");gNewUserProfile.cSUIT.setSuitFull( false);
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,iSuit.strSoloOff1, llColorBlue1);
                        userNotificationSuit( actionTakeOff, stringReplacer(iSuit.strSoloOff2));
                    }
                }else{
                    logger.info(fName + ".suit is not on");
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsNotOn, llColorPurple1);
                }
            }else
            if(command.equalsIgnoreCase(vRelease)){
                if(gNewUserProfile.cSUIT.isOn()){
                    if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                        logger.info(fName + ".can't use do to not wearing special suit");
                        sendOrUpdatePrivateEmbed(sRTitle,"Not wearing toy suit!", llColorRed);
                        return;
                    }
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
                        logger.info(fName + ".can't take off do to jacket");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItfOffStraitjacket, llColorRed);
                        userNotificationSuit( actionStruggle, gUser.getAsMention()+" attempts to take off their suit but the straitjacket sleeves are preventing it.");
                    }
                    else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,cantTakeItOffWhileItsLocked, llColorRed);
                        userNotificationSuit( actionStruggle,gUser.getAsMention()+" struggled to take off the suit from their body but failed due to its locked with a padlock.");
                    }
                    else if(!gIsOverride&&isToyScoreBellow(gNewUserProfile.gUserProfile)&&gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)){
                        logger.info(fName + ".can't take off do to score bellow but rank alpha");
                        sendOrUpdatePrivateEmbed(sRTitle,"You can't take it off with your current score of "+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+". \nHowever somebody else can free you.", llColorRed_Barn);
                        Message message=llSendQuickEmbedMessageResponse(gTextChannel,null,gUser.getAsMention()+" struggled to take off the suit but can't be undo due to their score is bellow the required score. Toy has "+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+".\nHowever somebody else can free them, vote bellow.",llColorBlue1);
                        message.addReaction(gGlobal.emojis.getEmoji("green_heart")).queue();
                        message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)).queue();
                        message.addReaction(gGlobal.emojis.getEmoji("bomb")).queue();
                          gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                                e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()&&e.getUser()!=gUser),
                                e -> {
                                    try {
                                        String name=e.getReactionEmote().getName();
                                        logger.warn(fName+"name="+name);
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("green_heart"))){
                                            lsMessageHelper.lsMessageDelete(message);
                                            sendOrUpdatePrivateEmbed(sRTitle,gUser.getAsMention()+" was kind enough and deactivated the suit, releasing you from your own prison..", llColorGreen1);
                                            llSendQuickEmbedMessage(gTextChannel,null,gUser.getAsMention()+" is a lucky toy as somebody decided to release them. As the suit enters it shut down sequence, all the restraints it applied are undone before melting away." ,llColorGreen1);
                                            gNewUserProfile.cSUIT.setOn( false);gNewUserProfile.cSUIT.setSuitMatterial( ""); gNewUserProfile.cSUIT.setSuitType( "");gNewUserProfile.cSUIT.setSuitFull( false);gNewUserProfile.cSUIT.setSuitTimeLocked(0);gNewUserProfile.cSUIT.setSuitScore(0);
                                            gNewUserProfile.gUserProfile.putFieldEntry(nChastity,nOn,false);gNewUserProfile.gUserProfile.putFieldEntry(nChastity,nLevel,nNone);
                                            gNewUserProfile.gUserProfile.putFieldEntry(nGag,nOn,false);gNewUserProfile.gUserProfile.putFieldEntry(nGag,nLevel,nNone);gNewUserProfile.gUserProfile.putFieldEntry(nGag,nType, GAGTYPES.Ball.getName());
                                            gNewUserProfile.gUserProfile.putFieldEntry(nMitts,nOn,false);gNewUserProfile.gUserProfile.putFieldEntry(nMitts,nLevel,nNone);
                                            gNewUserProfile.gUserProfile.putFieldEntry(nCollar,nOn,false);gNewUserProfile.gUserProfile.putFieldEntry(nCollar,nLevel,nNone);
                                            saveProfile();
                                        }else
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                            lsMessageHelper.lsMessageDelete(message);
                                            llSendQuickEmbedMessage(gTextChannel,null,gUser.getAsMention()+" requirement for early release was denied. They still need to earn their release." ,llColorRed_Barn);

                                        }else
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                            lsMessageHelper.lsMessageDelete(message);
                                        }else{
                                            lsMessageHelper.lsMessageClearReactionsQueue(message);
                                        }
                                    }catch (Exception e2) {
                                        logger.error(fName + ".exception=" + e2);
                                        logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                    }
                                },15, TimeUnit.MINUTES, () -> {
                                      lsMessageHelper.lsMessageClearReactionsQueue(message);
                                });
                    }
                    else if(!gIsOverride&&isToyScoreBellow(gNewUserProfile.gUserProfile)){
                        logger.info(fName + ".can't take off do to score bellow");
                        sendOrUpdatePrivateEmbed(sRTitle,"You can't take it off with your current score of "+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+".", llColorRed_Barn);
                        llSendQuickEmbedMessage(gTextChannel,null,gUser.getAsMention()+" struggled to take off the suit but can't be undo due to their score is bellow the required score. Toy has "+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+".",llColorBlue1);
                    }
                    else{
                        if(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)){
                            logger.info(fName + ".special voting");
                            sendOrUpdatePrivateEmbed(sRTitle,"You managed to reach the desired score. Now just somebody needs to vote yes.", llColorRed_Barn);
                            Message message=llSendQuickEmbedMessageResponse(gTextChannel,null,gUser.getAsMention()+" managed to reach the required score to have the suit deactivated. Just need one vote from a stranger. Will you release them?",llColorBlue1);
                            message.addReaction(gGlobal.emojis.getEmoji("green_heart")).queue();
                            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)).queue();
                            message.addReaction(gGlobal.emojis.getEmoji("bomb")).queue();
                            rReleaseVoting(message,1);
                        }else
                        if(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                            logger.info(fName + ".special voting");
                            sendOrUpdatePrivateEmbed(sRTitle,"You managed to reach the desired score. Now just 5 peoples needs to vote yes.", llColorRed_Barn);
                            Message message=llSendQuickEmbedMessageResponse(gTextChannel,null,gUser.getAsMention()+" managed to reach the required score to have the suit deactivated. Just need 5 votes from stranger. Will you release them? Oh and side note, a No response sets the toy's score back to 0.",llColorBlue1);
                            message.addReaction(gGlobal.emojis.getEmoji("green_heart")).queue();
                            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)).queue();
                            message.addReaction(gGlobal.emojis.getEmoji("bomb")).queue();
                            rReleaseVoting(message,5);
                        }
                        else{
                            //gNewUserProfile.gUserProfile=iRestraints.sToyRelease(gNewUserProfile.gUserProfile);
                            //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                            setToyRelease();
                            sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You managed to deactivate the toy suit and to pull off from your body.", llColorBlue1);
                            userNotificationSuit( actionTakeOff, gUser.getAsMention()+" managed to deactivate the suit and to pull off from their body.");
                        }
                    }
                }else{
                    logger.info(fName + ".suit is not on");
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsNotOn, llColorPurple1);
                }
            }else
            if(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&command.equalsIgnoreCase(iSuit.levelSuitSpecialToyBeta)){
                set2ToyBeta(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You opted to downgrade the suit rank setting to beta. The suit hud display the following message \"Warning, you downgraded your rank, this cant be undone.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember..."+getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom)+"\"", llColorBlue1);
                userNotificationSuit( actionTakeOff, gUser.getAsMention()+" opted to downgrade the suit rank setting to beta.  The suit hud display the following message \"Warning, you downgraded your rank, this cant be undone.\nAccess mode set to exposed.\nRestricting untie all function for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember..."+getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom)+"\"");
            }else
            if((gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta))&&command.equalsIgnoreCase(iSuit.levelSuitSpecialToyOmega)){
                set2ToyOmega(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,"You opted to downgrade the suit rank setting to omega. The suit hud display the following message \"Warning, you downgraded your rank, this cant be undone.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember..."+getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom)+"\"", llColorBlue1);
                userNotificationSuit( actionTakeOff, gUser.getAsMention()+" opted to downgrade the suit rank setting to omega.  The suit hud display the following message \"Warning, you downgraded your rank, this cant be undone.\nAccess mode set to public.\nRestricting all untie functions for wearer.\nIn order to free yourself you need to behave and earn 50 points before a client can release you.\nHave a nice and productive day. And remember..."+getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom)+"\"");
            }else
            if(command.equalsIgnoreCase(cmdToyAdd1)){
                if(!gIsOverride){
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strBadToyCantAddPointSelf, llColorBlue1);
                    return;
                }
                gNewUserProfile.cSUIT.addSuitScore1();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCheaterAdded1, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd5)){
                if(!gIsOverride){
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strBadToyCantAddPointSelf, llColorBlue1);
                    return;
                }
                gNewUserProfile.cSUIT.addSuitScore5();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCheaterAdded5, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd10)){
                if(!gIsOverride){
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strBadToyCantAddPointSelf, llColorBlue1);
                    return;
                }
                gNewUserProfile.cSUIT.addSuitScore10();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCheaterAdded10, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd25)){
                if(!gIsOverride){
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strBadToyCantAddPointSelf, llColorBlue1);
                    return;
                }
                gNewUserProfile.cSUIT.addSuitScore25();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCheaterAdded25, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem1)){
                gNewUserProfile.cSUIT.remSuitScore1();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSubstracted1, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem5)){
                gNewUserProfile.cSUIT.remSuitScore5();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSubstracted5, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem10)){
                gNewUserProfile.cSUIT.remSuitScore10();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSubstracted10, llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem25)){
                gNewUserProfile.cSUIT.remSuitScore25();
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSubstracted25, llColorBlue1);
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateDue2ToySuit, llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyAlpha)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyAlpha(gNewUserProfile.gUserProfile,timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                set2ToyAlpha(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloToyAlpha1), llColorBlue1);
                userNotificationSuit( actionTakeOff,stringReplacer(iSuit.strSoloToyAlpha2) );
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyBeta)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyBeta(gNewUserProfile.gUserProfile,timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                set2ToyBeta(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                String str=getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strSoloToyBeta1,str), llColorBlue1);
                userNotificationSuit( actionTakeOff,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strSoloToyBeta2,str) );
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyOmega)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyOmega(gNewUserProfile.gUserProfile,timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                set2ToyOmega(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                String str=getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strSoloToyOmega1,str), llColorBlue1);
                userNotificationSuit( actionTakeOff,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strSoloToyOmega2,str) );
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.command))
            ){
                gNewUserProfile.cSUIT.setSuitType( iRDPatreon.patreonUser_239748154046545920_suit.level);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                gNewUserProfile=iRDPatreon.patreonUser_239748154046545920_suit.applyAdditionalRestraints(gNewUserProfile);
                iRDPatreon.patreonUser_239748154046545920_suit.sendApplyingWearer4RD(gNewUserProfile.gUserProfile,gMember,gTextChannel);
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.command))
            ){
                gNewUserProfile.cSUIT.setSuitType( iRDPatreon.patreonUser_118178462149115913_suit.level);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                gNewUserProfile=iRDPatreon.patreonUser_118178462149115913_suit.applyAdditionalRestraints(gNewUserProfile);
                iRDPatreon.patreonUser_118178462149115913_suit.sendApplyingWearer4RD(gNewUserProfile.gUserProfile,gMember,gTextChannel);
            }
            saveProfile();
        }
        private void rReleaseVoting(Message message,int required) {
            String fName = "[rReleaseVoting]";
            logger.info(fName);
            logger.info(fName + ".required=" + required);
            required--;
            int finalRequired = required;
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()&&e.getUser()!=gUser),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("green_heart"))){
                                if(finalRequired==0){
                                    lsMessageHelper.lsMessageDelete(message);
                                    sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strReleaseVotingAllow1), llColorGreen1);
                                    llSendQuickEmbedMessage(gTextChannel,null,stringReplacer(iSuit.strReleaseVotingAllow2) ,llColorGreen1);
                                    setToyRelease();
                                    saveProfile();
                                }else{
                                    rReleaseVoting(message,finalRequired);
                                }
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                lsMessageHelper.lsMessageDelete(message);
                                if(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                                    llSendQuickEmbedMessage(gTextChannel,null, stringReplacer(iSuit.strReleaseVotingDeny1),llColorRed_Barn);
                                    gNewUserProfile.cSUIT.setSuitScore(0);saveProfile();
                                }else{
                                    llSendQuickEmbedMessage(gTextChannel,null,stringReplacer(iSuit.strReleaseVotingDeny2),llColorRed_Barn);
                                }
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("bomb"))){
                                lsMessageHelper.lsMessageDelete(message);
                            }else{
                                lsMessageHelper.lsMessageClearReactionsQueue(message);
                            }
                        }catch (Exception e2) {
                            logger.error(fName + ".exception=" + e2);
                            logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                        }
                    },15, TimeUnit.MINUTES, () -> {
                        lsMessageHelper.lsMessageClearReactionsQueue(message);
                    });
        }
        private void rSuit_latex(String command) {
            String fName = "[rSuit_latex]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);

            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsTheyPermalocked, llColorRed);
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
                    sendOrUpdatePrivateEmbed(sRTitle,iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                    return;
                }
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Public, llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateDue2ToySuit, llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(command)&&gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsOn, llColorPurple1);
                return;
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPuppy)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Puppy);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexPuppy1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexPuppy2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitKitty)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Kitty);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexKitty1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexKitty2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPony)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Pony);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexPony1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexPony2));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Belt);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitDrone)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Drone);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexDrone1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexDrone2));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.General);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Null);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCow)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Cow);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexCow1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexCow2));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Udder);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCatsuit)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Catsuit);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexCatsuit1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexCatsuit2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitReindeer)){
                llSendQuickEmbedMessageResponse(gUser,sMainRTitle, "Winter is not here, yet", llColorPurple2);

                 /*Message message=llSendQuickEmbedMessageResponse(gUser,sMainRTitle, "You need to approbe it before being placed in the reindeer suit.\nThe suit will chaneg your access to public\nWill set other restraints too.\nThe suit is timelocked and will be available to remove on last day of the month.", llColorPurple2);
                 message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).complete();
                 message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).complete();
                 gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                         e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                         e -> {
                             try {
                                 String name=e.getReactionEmote().getName();
                                 logger.warn(fName+"name="+name);
                                 if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                     logger.info(fName + ".approved");
                                     gNewUserProfile.gUserProfile=iRestraints.sDoneReindeer(gNewUserProfile.gUserProfile, SUITTYPE.Latex.getName());
                                     sendOrUpdatePrivateEmbed(sRTitle,"You put on a latex reindeer suit. It cover you from neck to toe in shinny latex, ready to help out Santa."+"\nThe hood display some info for the wearer:**\nLoading default reinder.pers file....\nMitts: active\nSpeech: restricted\nOrgasm: denied\nTimer: "+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit())+"\nAbort and release? Say no in oder to abort in $time error....\nNo abort detected, finishing setup.**", llColorBlue1);
                                     userNotificationSuit( actionPutOn,gUser.getAsMention()+" has put on a latex reindeer suit. It cover them from neck to toe in shinny latex, ready to help out Santa.\nThe hood display some info for the wearer and they learned just how long they need to wear it.");
                                     saveProfile();
                                 }else
                                 if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){
                                     logger.info(fName + ".reject");
                                     sendOrUpdatePrivateEmbed(sRTitle,"You rejected to be put in the reindeer suit.",llColorRed_Cinnabar);
                                 }
                                 llMessageDelete(message);
                             }catch (Exception e2){
                                 logger.error(fName + ".exception=" + e2);
                                 logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                             }
                         },5, TimeUnit.MINUTES, () -> {
                             logger.info(fName + ".timeout");
                             sendOrUpdatePrivateEmbed(sRTitle,"Timeouted", llColorRed_Cinnabar);
                             llMessageDelete(message);
                         });*/
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexBitchsuit1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexBitchsuit2));
                setBitchsuit();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitHogsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexHogsack1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexHogsack2));
                setHogsack();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSleepsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloLatexSleepsack1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloLatexSleepsack2));
                setSleepsack();
            }
            saveProfile();
        }
        private void rSuit_rubber(String command) {
            String fName = "[rSuit_rubber]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);

            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsTheyPermalocked, llColorRed);
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
                    sendOrUpdatePrivateEmbed(sRTitle, iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                    return;
                }
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Public, llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateDue2ToySuit, llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(command)&&gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsOn, llColorPurple1);
                return;
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPuppy)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Puppy);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberPuppy1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberPuppy2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitKitty)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Kitty);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberKitty1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberKitty2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPony)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Pony);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberPony1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberPony2));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Belt);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitDrone)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Drone);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberDrone1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberDrone2));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.General);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Null);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCow)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Cow);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberCow1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberCow2));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Udder);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCatsuit)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Catsuit);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberCatsuit1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberCatsuit2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitReindeer)){
                llSendQuickEmbedMessageResponse(gUser,sMainRTitle, "Winter is not here, yet", llColorPurple2);
                /*Message message=llSendQuickEmbedMessageResponse(gUser,sMainRTitle, "You need to approbe it before being placed in the reindeer suit.\nThe suit will chaneg your access to public\nWill set other restraints too.\nThe suit is timelocked and will be available to remove on last day of the month.", llColorPurple2);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).complete();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).complete();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))) {
                                    logger.info(fName + ".approved");
                                    gNewUserProfile.gUserProfile=iRestraints.sDoneReindeer(gNewUserProfile.gUserProfile, SUITTYPE.Rubber.getName());
                                    sendOrUpdatePrivateEmbed(sRTitle,"You put on a rubber reindeer suit. It cover you from neck to toe in skin tight rubber, ready to help out Santa."+"\nThe hood display some info for the wearer:**\nLoading default reinder.pers file....\nMitts: active\nSpeech: restricted\nOrgasm: denied\nTimer: "+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit())+"\nAbort and release? Say no in oder to abort in $time error....\nNo abort detected, finishing setup.**", llColorBlue1);
                                    userNotificationSuit( actionPutOn,gUser.getAsMention()+" has put on a rubber reindeer suit. It cover them from neck to toe in in skin tight rubber, ready to help out Santa.\nThe hood display some info for the wearer and they learned just how long they need to wear it.");
                                    saveProfile();
                                }else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){
                                    logger.info(fName + ".reject");
                                    sendOrUpdatePrivateEmbed(sRTitle,"You rejected to be put in the reindeer suit.",llColorRed_Cinnabar);
                                }
                                llMessageDelete(message);
                            }catch (Exception e2){
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName + ".timeout");
                            sendOrUpdatePrivateEmbed(sRTitle,"Timeouted", llColorRed_Cinnabar);
                            llMessageDelete(message);
                        });*/
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberBitchsuit1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberBitchsuit2));
                setBitchsuit();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitHogsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberHogsack1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberHogsack2));
                setHogsack();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSleepsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloRubberSleepsack1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloRubberSleepsack2));
                setSleepsack();
            }
            saveProfile();
        }
        private void rSuit_textile(String command) {
            String fName = "[rSuit_textile]";
            logger.info(fName);
            logger.info(fName + ".command=" + command);

            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsTheyPermalocked, llColorRed);
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
                    sendOrUpdatePrivateEmbed(sRTitle,iCuffs.strCantOperateRestrainsDue2ArmCuffs_PetDenied, llColorRed);
                    return;
                }
            }
            if(!gIsOverride&&gNewUserProfile.isPetLockedBySomebody()){
                logger.info(fName + ".can't use do to locked by somebody");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Owner, llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                logger.info(fName + ".can't use do to access public");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateAsAccessSet2Public, llColorRed);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strCantManipulateDue2ToySuit, llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(command)&&gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsOn, llColorPurple1);
                return;
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPlush)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Plush);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloTextilePlush1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloTextilePlush2));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloTextileBitchsuit1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloTextileBitchsuit2));
                setBitchsuit();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitHogsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloTextileHogsack1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloTextileHogsack2));
                setHogsack();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSleepsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile);
                sendOrUpdatePrivateEmbed_withtoMessageSelfCommandAction(sRTitle,stringReplacer(iSuit.strSoloTextileSleepsack1), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strSoloTextileSleepsack2));
                setSleepsack();
            }
            saveProfile();
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void rSuit(Member mtarget, String command) {
            String fName = "[rSuit]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(vStatus)||command.equalsIgnoreCase(vToy)){
                logger.info(fName + ".status");
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(mtarget.getUser().getName()+"'s toy suit status");embedBuilder.setColor(llColorBlue1);
                if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                    embedBuilder.setDescription("Not wearing any toy suit.");
                }else{
                    embedBuilder.setDescription("Rank: "+getToyRank(gNewUserProfile.gUserProfile)+"\nScore:"+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+"\nFree talk:"+gNewUserProfile.cSUIT.getSuitFreeTalkAvailable()+"/"+allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                }
                lsMessageHelper.lsSendMessageWithDelete(gGlobal,gTextChannel,embedBuilder);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit  do to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to their access setting.", llColorRed);return;
            }else
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }else
            if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".isSameConfinement>return");
                sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                return;
            }else
            if(!gIsOverride&&iRestraints.isArmsRestrained(gGlobal,gMember)){
                if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                    logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                    iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                }else{
                    logger.info(fName + ".default message");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                }
                return;
            }else
            if(command.equalsIgnoreCase(vOff)){
                if(gNewUserProfile.cSUIT.isOn()){
                    if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't take it off while "+target.getAsMention()+" is locked in them.", llColorRed);
                    }else{
                        if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                            logger.info(fName + ".can't use do to special suit");
                            sendOrUpdatePrivateEmbed(sRTitle,"Can't simple take it off due to its a toy suit. Run `"+llPrefixStr+"suit <target> release` to take off the suit.", llColorRed);
                            return;
                        }
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can take off your suit.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can tak off !TARGET's suit.");
                            gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                            return;
                        }
                        rSuitSave4Target(mtarget,command);
                    }
                }else{
                    logger.info(fName + ".suit is not on");
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsNotOn, llColorPurple1);
                }
            }else
            if(command.equalsIgnoreCase(vRelease)){
                if(gNewUserProfile.cSUIT.isOn()){
                    if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                        logger.info(fName + ".can't use do to not wearing special suit");
                        sendOrUpdatePrivateEmbed(sRTitle,"They not wearing toy suit!", llColorRed);
                        return;
                    }
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)){
                        logger.info(fName + ".can't take off do to jacket");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't take it off while "+target.getAsMention()+" is wearing the straitjacket!", llColorRed);
                    }
                    else if(!gIsOverride&&gNewUserProfile.cLOCK.isLocked()){
                        logger.info(fName + ".can't take off do to locked");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't take it off while "+target.getAsMention()+" is locked in them.", llColorRed);
                    }
                    else if(!gIsOverride&&isToyScoreBellow(gNewUserProfile.gUserProfile)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)){
                        logger.info(fName + ".can't take off do to score bellow");
                        sendOrUpdatePrivateEmbed(sRTitle,"You can't take it off with their current score of "+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+".", llColorRed);
                        userNotificationSuit( actionStruggle,gUser.getAsMention()+" attempted to take off "+mtarget.getAsMention()+" toy suit, sadly they require to finish their task. Toy has "+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+".");
                    }
                    else{
                        if(gAskHandlingHelper.isAsk()){
                            logger.info(fName + ".requesting update restraint");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can release you from the toy suit.");
                            gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can release !TARGET' fro mtheir toy suit.");
                            gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                            return;
                        }
                        rSuitSave4Target(mtarget,command);
                    }
                }else{
                    logger.info(fName + ".suit is not on");
                    sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsNotOn, llColorPurple1);
                }
            }else
            if(command.equalsIgnoreCase(cmdToyAdd1)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd5)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd10)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd25)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyRem1)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyRem5)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyRem10)){
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(cmdToyRem25)){
                rSuitSave4Target(mtarget,command);
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to its a toy suit.", llColorRed);
                return;
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyAlpha)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a toy alpha suit..");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a toy alpha suit.");
                    gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                    return;
                }
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyBeta)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a toy beta suit.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a toy beta suit.");
                    gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                    return;
                }
                rSuitSave4Target(mtarget,command);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyOmega)){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a toy alpha omega.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a toy alpha omega.");
                    gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                    return;
                }
                rSuitSave4Target(mtarget,command);
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.command))
            ) {
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a plushy.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a plushy.");
                    gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                    return;
                }
                rSuitSave4Target(mtarget,command);
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.command))
            ) {
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a sleeper.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a sleeper.");
                    gAskHandlingHelper.doAsk(() -> {rSuitSave4Target(mtarget,command);});
                    return;
                }
                rSuitSave4Target(mtarget,command);
            }

        }
        private void rSuit_latex(Member mtarget, String command) {
            String fName = "[rSuit_latex]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
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
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit  do to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to their access setting.", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".isSameConfinement>return");
                sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(command)&&gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsOn, llColorPurple1);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to its a toy suit.", llColorRed);
                return;
            }
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }
            else if(command.equalsIgnoreCase(iSuit.levelSuitPuppy)||command.equalsIgnoreCase(iSuit.levelSuitKitty)||command.equalsIgnoreCase(iSuit.levelSuitPony)||command.equalsIgnoreCase(iSuit.levelSuitDrone)
                ||command.equalsIgnoreCase(iSuit.levelSuitCow)||command.equalsIgnoreCase(iSuit.levelSuitCatsuit)
                ||command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)||command.equalsIgnoreCase(iSuit.levelSuitHogsack)||command.equalsIgnoreCase(iSuit.levelSuitSleepsack)
            ){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a latex suit.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a latex suit.");
                    gAskHandlingHelper.doAsk(() -> {rSuit_latexSave4Target(mtarget,command);});
                    return;
                }
                rSuit_latexSave4Target(mtarget,command);
            }
            else if(command.equalsIgnoreCase(iSuit.levelSuitReindeer)){
                llSendQuickEmbedMessageResponse(gUser,sMainRTitle, "Winter is not here, yet", llColorPurple2);
                /*sendOrUpdatePrivateEmbed(sRTitle,mtarget.getAsMention()+" need to approve it before they placed in the reindeer suit.", llColorPurple2);
                Message message=llSendQuickEmbedMessageResponse(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" is trying to place you in a reindeer suit.\nThe suit will chaneg your access to public\nWill set other restraints too.\nThe suit is timelocked and will be available to remove on last day of the month.", llColorPurple2);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).complete();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).complete();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(mtarget.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                                    logger.info(fName + ".approved");
                                    rSuit_latexSave4Target(mtarget,command);}else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){
                                    logger.info(fName + ".reject");
                                    sendOrUpdatePrivateEmbed(sRTitle,target.getAsMention()+" rejected to be put in the reindeer suit.", llColorRed_Cinnabar);
                                    llSendQuickEmbedMessage(target,sMainRTitle,"You rejected to be put in the reindeer suit.",llColorRed_Cinnabar);
                                }
                                llMessageDelete(message);
                            }catch (Exception e2){
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName + ".timeout");
                            sendOrUpdatePrivateEmbed(sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                            llMessageDelete(message);
                        });*/
                return;
            }

        }
        private void rSuit_rubber(Member mtarget, String command) {
            String fName = "[rSuit_rubber]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
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
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit  do to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to their access setting.", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".isSameConfinement>return");
                sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(command)&&gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsOn, llColorPurple1);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to its a toy suit.", llColorRed);
                return;
            }
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }
            else if(command.equalsIgnoreCase(iSuit.levelSuitPuppy)||command.equalsIgnoreCase(iSuit.levelSuitKitty)||command.equalsIgnoreCase(iSuit.levelSuitPony)||command.equalsIgnoreCase(iSuit.levelSuitDrone)
                    ||command.equalsIgnoreCase(iSuit.levelSuitCow)||command.equalsIgnoreCase(iSuit.levelSuitCatsuit)
                    ||command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)||command.equalsIgnoreCase(iSuit.levelSuitHogsack)||command.equalsIgnoreCase(iSuit.levelSuitSleepsack)
            ){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a rubber suit.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a rubber suit.");
                    gAskHandlingHelper.doAsk(() -> {rSuit_rubberSave4Target(mtarget,command);});
                    return;
                }
                rSuit_rubberSave4Target(mtarget,command);
            }
            else if(command.equalsIgnoreCase(iSuit.levelSuitReindeer)){
                llSendQuickEmbedMessageResponse(gUser,sMainRTitle, "Winter is not here, yet", llColorPurple2);
                /*sendOrUpdatePrivateEmbed(sRTitle,mtarget.getAsMention()+" need to approve it before they placed in the reindeer suit.", llColorPurple2);
                Message message=llSendQuickEmbedMessageResponse(mtarget.getUser(),sMainRTitle,gUser.getAsMention()+" is trying to place you in a reindeer suit.\nThe suit will chaneg your access to public\nWill set other restraints too.\nThe suit is timelocked and will be available to remove on last day of the month.", llColorPurple2);
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)).complete();
                message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)).complete();
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(mtarget.getId())),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                                    logger.info(fName + ".approved");
                                    rSuit_rubberSave4Target(mtarget,command);}else
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){
                                    logger.info(fName + ".reject");
                                    sendOrUpdatePrivateEmbed(sRTitle,target.getAsMention()+" rejected to be put in the reindeer suit.", llColorRed_Cinnabar);
                                    llSendQuickEmbedMessage(target,sMainRTitle,"You rejected to be put in the reindeer suit.",llColorRed_Cinnabar);
                                }
                                llMessageDelete(message);
                            }catch (Exception e2){
                                logger.error(fName + ".exception=" + e2);
                                logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            logger.info(fName + ".timeout");
                            sendOrUpdatePrivateEmbed(sRTitle,target.getAsMention()+waitingresponsehastimeuted, llColorRed_Cinnabar);
                            llMessageDelete(message);
                        });*/
                return;
            }

        }
        private void rSuit_textile(Member mtarget, String command) {
            String fName = "[rSuit_textile]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
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
            if(!gIsOverride&&gNewUserProfile.cLOCK.isPermaLocked()&&gNewUserProfile.cSUIT.isOn()){
                logger.info(fName + ".permalock");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate "+target.getAsMention()+"'s restraints as they permanently locked!", llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit  do to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to their access setting.", llColorRed);return;
            }else
            if(!gIsOverride&&gNewUserProfile.cCONFINE.isOn()&&gNewUserProfile.cCONFINE.isSameConfinement(gGlobal,gMember)&&!iRestraints.lsHasUserAnyOwnerAccess(gNewUserProfile.gUserProfile,gUser)){
                logger.info(fName + ".isSameConfinement>return");
                sendOrUpdatePrivateEmbed(sRTitle,iConfine.sStringReplacer(gNewUserProfile.gUserProfile,gMember,iConfine.strYouAreNotInsameCell),llColorRed);
                return;
            }
            if(!gIsOverride&&gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(command)&&gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                logger.info(fName + ".same level");
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.strSuitIsOn, llColorPurple1);
                return;
            }else
            if(!gIsOverride&&(gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)||gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega))){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit due to its a toy suit.", llColorRed);
                return;
            }
            if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                logger.info(fName + ".can't use do to special suit");
                sendOrUpdatePrivateEmbed(sRTitle,"The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), llColorRed);
                return;
            }
            else if(command.equalsIgnoreCase(iSuit.levelSuitPlush)
                    ||command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)||command.equalsIgnoreCase(iSuit.levelSuitHogsack)||command.equalsIgnoreCase(iSuit.levelSuitSleepsack)
            ){
                if(gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they can place you in a textile suit.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can place !TARGET' in a textile suit.");
                    gAskHandlingHelper.doAsk(() -> {rSuit_textileSave4Target(mtarget,command);});
                    return;
                }
                rSuit_textileSave4Target(mtarget,command);
            }


        }
        private void rSuitSave4Target(Member mtarget, String command) {
            String fName = "[rSuitSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            JSONArray customToyGoodTalk=new JSONArray();boolean onlyUseCustom=false;
            try {
                customToyGoodTalk=gNewUserProfile.cSUIT.getCustomGoodTalk();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                logger.info(fName + ".has flagOnlyUseCustom");
                onlyUseCustom=gNewUserProfile.cGAG.isFlagOnlyUseCustom();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(command.equalsIgnoreCase(vOff)){
                gNewUserProfile.cSUIT.setOn( false);gNewUserProfile.cSUIT.setSuitMatterial( ""); gNewUserProfile.cSUIT.setSuitType( "");gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetOff1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetOff2), llColorBlue1);
                userNotificationSuit( actionTakeOff, stringReplacer(iSuit.strTargetOff3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyAlpha)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyAlpha(gNewUserProfile.gUserProfile,timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                set2ToyAlpha(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetToyAlpha1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetToyAlpha2), llColorBlue1);
                userNotificationSuit( actionTakeOff, stringReplacer(iSuit.strTargetToyAlpha3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyBeta)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyBeta(gNewUserProfile.gUserProfile,timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                set2ToyBeta(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                String str=getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom);
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strTargetToyBeta1,str), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strTargetToyBeta2,str), llColorBlue1);
                userNotificationSuit( actionTakeOff, iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strTargetToyBeta3,str));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSpecialToyOmega)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyOmega(gNewUserProfile.gUserProfile,timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                set2ToyOmega(timestamp,allowedFreeTalk4Toy(gNewUserProfile.gUserProfile));
                String str=getRandomToyTalkGood(customToyGoodTalk,onlyUseCustom);
                sendOrUpdatePrivateEmbed(sRTitle,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strTargetToyOmega1,str), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strTargetToyOmega2,str), llColorBlue1);
                userNotificationSuit( actionTakeOff, iSuit.sStringReplacer(gNewUserProfile,gMember,iSuit.strTargetToyOmega3,str));
            }else
            if(command.equalsIgnoreCase(vRelease)){
                //gNewUserProfile.gUserProfile=iRestraints.sToyRelease(gNewUserProfile.gUserProfile);
                //gNewUserProfile.cSUIT.set(gNewUserProfile.gUserProfile.jsonUser);
                setToyRelease();
                sendOrUpdatePrivateEmbed(sRTitle,"You managed to deactivate the toys suit and pull off from "+target.getAsMention()+"'s body.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" managed to deactivate the toy suit and pull off from your body.", llColorBlue1);
                userNotificationSuit( actionTakeOff, gUser.getAsMention()+" managed  deactivate the toy suit and pull off from "+target.getAsMention()+"'s suit.");
            }else
            if(command.equalsIgnoreCase(cmdToyAdd1)){
                gNewUserProfile.cSUIT.addSuitScore1();
                sendOrUpdatePrivateEmbed(sRTitle,"You add 1 point to toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You receive 1 point from "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd5)){
                gNewUserProfile.cSUIT.addSuitScore5();
                sendOrUpdatePrivateEmbed(sRTitle,"You add 5 points to toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You receive 5 points from "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd10)){
                gNewUserProfile.cSUIT.addSuitScore10();
                sendOrUpdatePrivateEmbed(sRTitle,"You add 10 points to toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You receive 10 points from "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyAdd25)){
                gNewUserProfile.cSUIT.addSuitScore25();
                sendOrUpdatePrivateEmbed(sRTitle,"You add 25 points to toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You receive 25 points from "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem1)){
                gNewUserProfile.cSUIT.remSuitScore1();
                sendOrUpdatePrivateEmbed(sRTitle,"You subtract 1 point from toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You lose 1 point by "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem5)){
                gNewUserProfile.cSUIT.remSuitScore5();
                sendOrUpdatePrivateEmbed(sRTitle,"You subtract 5 points from toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You lose 5 points by "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem10)){
                gNewUserProfile.cSUIT.remSuitScore10();
                sendOrUpdatePrivateEmbed(sRTitle,"You subtract 10 points from toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You lose 10 points by "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }else
            if(command.equalsIgnoreCase(cmdToyRem25)){
                gNewUserProfile.cSUIT.remSuitScore25();
                sendOrUpdatePrivateEmbed(sRTitle,"You subtract 25 points from toy "+target.getAsMention()+".", llColors.llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,"You lose 25 points by "+gUser.getAsMention()+".", llColors.llColorBlue1);
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_239748154046545920_suit.command))
            ) {
                gNewUserProfile.cSUIT.setSuitType( iRDPatreon.patreonUser_239748154046545920_suit.level);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                gNewUserProfile=iRDPatreon.patreonUser_239748154046545920_suit.applyAdditionalRestraints(gNewUserProfile);
                iRDPatreon.patreonUser_239748154046545920_suit.sendApplyingUser4RD(gNewUserProfile.gUserProfile,mtarget.getUser(),gMember,gTextChannel);
            }
            else if((gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))
                    &&(command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.name)||command.equalsIgnoreCase(iRDPatreon.patreonUser_118178462149115913_suit.command))
            ) {
                gNewUserProfile.cSUIT.setSuitType( iRDPatreon.patreonUser_118178462149115913_suit.level);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                gNewUserProfile=iRDPatreon.patreonUser_118178462149115913_suit.applyAdditionalRestraints(gNewUserProfile);
                iRDPatreon.patreonUser_118178462149115913_suit.sendApplyingUser4RD(gNewUserProfile.gUserProfile,mtarget.getUser(),gMember,gTextChannel);
            }
            saveProfile();
        }
        private void rSuit_latexSave4Target(Member mtarget, String command) {
            String fName = "[rSuit_latexSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(iSuit.levelSuitPuppy)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Puppy);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexPuppy1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetLatexPuppy2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexPuppy3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitKitty)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Kitty);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexKitty1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetLatexKitty2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexKitty3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPony)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Pony);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexPony1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetLatexPony2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexPony3));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Belt);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitDrone)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Drone);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexDrone1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetLatexDrone2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexDrone3));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.General);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Null);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCow)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Cow);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexCow1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetLatexCow2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexCow3));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Udder);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCatsuit)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Catsuit);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexCatsuit1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetLatexCatsuit2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexCatsuit3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitReindeer)){
                gNewUserProfile.gUserProfile=iRestraints.sDoneReindeer(gNewUserProfile.gUserProfile, SUITMATTERIAL.Latex.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put "+target.getAsMention()+" in a latex reindeer suit. It cover them from neck to toe in shinny latex, ready to help Santa.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" has put you in a latex reindeer suit. It cover you from neck to toe in shinny latex, ready to help Santa."+"\nThe hood display some info for the wearer:**\nLoading default reinder.pers file....\nMitts: active\nSpeech: restricted\nOrgasm: denied\nTimer: "+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit())+"\nAbort and release? Say no in oder to abort in $time error....\nNo abort detected, finishing setup.**", llColorBlue1);
                userNotificationSuit( actionPutOn,gUser.getAsMention()+" has put "+target.getAsMention()+" in a latex reindeer suit. It cover them from neck to toe in shinny latex, ready to help Santa.\nThe hood display some info for the wearer and they learned just how long they need to wear it.");
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexBitchsuit1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexBitchsuit2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexBitchsuit3));
                setBitchsuit();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitHogsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexHogsack1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexHogsack2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexHogsack3));
                setHogsack();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSleepsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Latex);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexSleepsack1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetLatexSleepsack2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetLatexSleepsack3));
                setSleepsack();
            }
            saveProfile();
        }
        private void rSuit_rubberSave4Target(Member mtarget, String command) {
            String fName = "[rSuit_rubberSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);

            if(command.equalsIgnoreCase(iSuit.levelSuitPuppy)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Puppy);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberPuppy1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetRubberPuppy2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberPuppy3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitKitty)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Kitty);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberKitty1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetRubberKitty2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberKitty3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitPony)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Pony);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberPony1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetRubberPony2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberPony3));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Belt);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitDrone)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Drone);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberDrone1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetRubberDrone2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberDrone3));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.General);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Null);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCow)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Cow);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberCow1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetRubberCow2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberCow3));
                gNewUserProfile.cMITTS.setOn(true).setLevel(MITTSLEVELS.Pony);
                gNewUserProfile.cCHASTITY.setOn(true).setLevel(CHASTITYLEVELS.Udder);
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitCatsuit)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Catsuit);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberCatsuit1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetRubberCatsuit2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberCatsuit3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitReindeer)){
                gNewUserProfile.gUserProfile=iRestraints.sDoneReindeer(gNewUserProfile.gUserProfile, SUITMATTERIAL.Rubber.getName());
                sendOrUpdatePrivateEmbed(sRTitle,"You put "+target.getAsMention()+" in a rubber reindeer suit. It cover them from neck to toe in skin tight rubber, ready to help Santa.", llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,gUser.getAsMention()+" has put you in a rubber reindeer suit. It cover you from neck to toe in skin tight rubber, ready to help Santa."+"\nThe hood display some info for the wearer:**\nLoading default reinder.pers file....\nMitts: active\nSpeech: restricted\nOrgasm: denied\nTimer: "+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit())+"\nAbort and release? Say no in oder to abort in $time error....\nNo abort detected, finishing setup.**", llColorBlue1);
                userNotificationSuit( actionPutOn,gUser.getAsMention()+" has put "+target.getAsMention()+" in a rubber reindeer suit. It cover them from neck to toe in skin tight rubber, ready to help Santa.\nThe hood display some info for the wearer and they learned just how long they need to wear it.");
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberBitchsuit1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberBitchsuit2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberBitchsuit3));
                setBitchsuit();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitHogsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberHogsack1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberHogsack2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberHogsack3));
                setHogsack();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSleepsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Rubber);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberSleepsack1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetRubberSleepsack2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetRubberSleepsack3));
                setSleepsack();
            }
            saveProfile();
        }
        private void rSuit_textileSave4Target(Member mtarget, String command) {
            String fName = "[rSuit_textileSave4Target]";
            logger.info(fName);
            User target=mtarget.getUser();
            logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
            logger.info(fName + ".command="+command);
            if(command.equalsIgnoreCase(iSuit.levelSuitPlush)){
                gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Plush);gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile.getName());gNewUserProfile.cSUIT.setSuitFull( false);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextilePlush1), llColorBlue1);
                llSendQuickEmbedMessage(target,sMainRTitle,stringReplacer(iSuit.strTargetTextilePlush2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetTextilePlush3));
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitBitchsuit)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextileBitchsuit1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextileBitchsuit2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetTextileBitchsuit3));
                setBitchsuit();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitHogsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextileHogsack1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextileHogsack2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetTextileHogsack3));
                setHogsack();
            }else
            if(command.equalsIgnoreCase(iSuit.levelSuitSleepsack)){
                gNewUserProfile.cSUIT.setSuitMatterial( SUITMATTERIAL.Textile);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextileSleepsack1), llColorBlue1);
                sendOrUpdatePrivateEmbed(sRTitle,stringReplacer(iSuit.strTargetTextileSleepsack2), llColorBlue1);
                userNotificationSuit( actionPutOn,stringReplacer(iSuit.strTargetTextileSleepsack3));
                setSleepsack();
            }
            saveProfile();
        }
        private void menuLevels(Member mtarget,String page){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                logger.info(fName+"page="+page);
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
                            iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sMainRTitle,gTextChannel);
                        }else{
                            logger.info(fName + ".default message");
                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sMainRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);
                        }
                        return;
                    }
                    menuLevelsOther(page);
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                    menuLevelsWearer(page);
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        String gCommandFilePath =gFileMainPath+"suit/";
        String gCommandFileMainPath =gCommandFilePath+"menuMain.json",gCommandFileSuitT=gCommandFilePath+"menuSuitT.json",gCommandFileSuitR=gCommandFilePath+"menuSuitR.json",gCommandFileSuitL=gCommandFilePath+"menuSuitL.json",gCommandFileSuitS=gCommandFilePath+"menuSuitS.json",gCommandFileSuitPatreon=gCommandFilePath+"menuSuitP.json";
        String selectedMatterial="",selectedType="";
        private void menuLevelsWearer(String page){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                logger.info(fName+"page="+page);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="",desc2="";
                embed.setColor(llColorOrange);
                embed.addField(strSupportTitle,strSupport,false);
                embed.setTitle(sRTitle+" Menu");
                if(gNewUserProfile.cSUIT.isOn()){
                    selectedMatterial=gNewUserProfile.cSUIT.getSuitMatterialAsString();
                    selectedType=gNewUserProfile.cSUIT.getSuitTypeAsString();
                    String type=gNewUserProfile.cSUIT.getSuitTypeAsString();
                    if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                        embed.addField("Currently",selectedMatterial+" "+type,false);
                    }else{
                        embed.addField("Currently","toy suit\n"+"Rank: "+getToyRank(gNewUserProfile.gUserProfile)+"\nScore:"+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+"\nFree talk:"+gNewUserProfile.cSUIT.getSuitFreeTalkAvailable()+"/"+allowedFreeTalk4Toy(gNewUserProfile.gUserProfile)+"\nprefix: "+gNewUserProfile.cSUIT.getSuitPrefix(),false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                boolean isLocked=false;
                if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                    logger.info(fName + ".can't use do to special suit");
                    embed.addField("Locked","The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), false);
                    isLocked=true;
                }
                if(!isLocked){
                    switch (page){
                        case "main":
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" "  + SUITMATTERIAL.Rubber.getName()+" suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL)+" "  + SUITMATTERIAL.Latex.getName()+" suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)+" "  + SUITMATTERIAL.Textile.getName()+" suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" "  +"toy suits";
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear)+" **VIP Suit** plush";
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed)+" **VIP Suit** sleeper";
                            }
                            break;
                        case iSuit.levelSuitLatex:
                        case iSuit.levelSuitRubber:
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" go back";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "  +iSuit.levelSuitCatsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "  +iSuit.levelSuitPuppy;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+iSuit.levelSuitPony;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" " +iSuit.levelSuitDrone;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" " +iSuit.levelSuitKitty;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "  +iSuit.levelSuitCow;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" "  +iSuit.levelSuitBitchsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)+" "  +iSuit.levelSuitHogsack;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9)+" "  +iSuit.levelSuitSleepsack;
                            break;
                        case iSuit.levelSuitTextile:
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" go back";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "  +iSuit.levelSuitPlush;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" "  +iSuit.levelSuitBitchsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)+" "  +iSuit.levelSuitHogsack;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9)+" "  +iSuit.levelSuitSleepsack;
                            break;
                        case iSuit.levelSuitToy:
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" release";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" alpha, score 25 point is required to release yourself without voting or voting by others, no gag safeword, 20 free speech, unable to take off or change restrictions that is part of the suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" beta, +access set to exposed, 5 free speech, require score 50 and voting to get released";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO)+" omega, +access set to exposed, 2 free speech, require score 90 and 5 votes to get released";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" +1 point";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" +5 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle)+" +10 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle)+" +25 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare)+" -1 point";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare)+" -5 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare)+" -10 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare)+" -25 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" set prefix they should call themselves";
                            desc+="\ncurrent prefix:"+gNewUserProfile.cSUIT.getSuitPrefix();
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound)+" set toy gag";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo)+" set list of good words/sentences";
                            break;
                        default:
                            //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" "  +SUITTYPE.Latex.getName()+" "+iSuit.levelSuitReindeer;
                            //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ)+" "+SUITTYPE.Rubber.getName()+" "+iSuit.levelSuitReindeer;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "  + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitCatsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "  + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitPuppy;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+ SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitPony;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" " + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitDrone;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" " + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitKitty;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "  + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitCow;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitCatsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitPuppy;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitPony;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitDrone;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitKitty;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitCow;
                            desc+="\nSpecial toy suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" alpha, score 25 point is required to release yourself without voting or voting by others, no gag safeword, 20 free speech, unable to take off or change restrictions that is part of the suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" beta, +access set to exposed, 5 free speech, require score 50 and voting to get released";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO)+" omega, +access set to exposed, 2 free speech, require score 90 and 5 votes to get released";
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear)+" **VIP Suit** plush";
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed)+" **VIP Suit** sleeper";
                            }
                            break;
                    }

                }
                //embed.setDescription(desc);

                embed.addField(" ", "Please select a suit option :"+desc+"\nQuick release: `"+gGuild.getSelfMember().getAsMention()+gCommand+" red`", false);
                if(gNewUserProfile.isWearerDenied0withException()||gNewUserProfile.isLockedwithException(gNewUserProfile.cSUIT.isOn())){
                    desc="";
                    if(gNewUserProfile.isLockedwithException(gNewUserProfile.cSUIT.isOn()))desc=iRdStr.strRestraintLocked;
                    if(gNewUserProfile.cSTRAITJACKET.areArmsRestrained())desc+=iRdStr.strRestraintJacketArms;
                    if(gNewUserProfile.cARMCUFFS.areArmsRestrained())desc+= iRdStr.strRestraintArmsCuffs;
                    if(gNewUserProfile.cMITTS.isOn())desc+=iRdStr.strRestraintMitts;
                    if(gNewUserProfile.cSUIT.isBDSMSuitOn()){
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit){
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
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                Message message=null;//llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                messageComponentManager.loadMessageComponents(gCommandFileMainPath,false);
                try {
                    logger.info(fName+"selectedMatterial="+selectedMatterial);
                    logger.info(fName+"selectedType="+selectedType);
                    JSONArray jsonArray=new JSONArray();
                    boolean isMultiple=true;
                    if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitL);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitR);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitT);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Toy.getName())){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitS);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                        //messageBuildComponents.removeComponent(3);
                    }else{
                        messageComponentManager.buildMessageComponents();isMultiple=false;
                    }
                    logger.info(fName+"component.pre="+messageComponentManager.messageBuildComponents.getJson());
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponent_Select.getSelectById("select_material");
                    lcMessageBuildComponent.SelectMenu selectContainer1=new lcMessageBuildComponent.SelectMenu();
                    if(isMultiple){
                        messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(1);
                        selectContainer1=messageComponentManager.messageBuildComponent_Select.getSelectById("select_type");
                    }

                    if(selectedMatterial.isBlank())selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName()))selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolL);
                        else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName()))selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolR);

                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Catsuit)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Puppy)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Pony)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Drone)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Kitty)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Cow)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias7);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias8);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias9);

                    }
                    else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolT);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Plush)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias7);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias8);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias9);
                    }
                    else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Toy.getName())){
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolS);
                        if(isToyScoreBellow(gNewUserProfile.gUserProfile)){
                            selectContainer0.setDisabled();
                        }
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyAlpha)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyBeta)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyOmega) {
                            selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolC);
                            selectContainer1.setDisabled();
                        }
                        messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("large_blue_circle",true).setDisable();
                        messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("green_circle",true).setDisable();
                        messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("yellow_circle",true).setDisable();
                        messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("orange_circle",true).setDisable();
                        if(!gNewUserProfile.cSUIT.isToySuitOn()){
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("blue_square",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("green_square",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("yellow_square",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("orange_square",true).setDisable();
                        }
                    }
                    if(isLocked){
                        logger.info(fName+"locked");
                        selectContainer0.setDisabled();selectContainer1.setDisabled();
                    }

                    logger.info(fName+"component.post="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                if(isLocked){
                    logger.info(fName+"options:1");
                }
                else if(gMember.getIdLong()==iRDPatreon.patreonUser_118178462149115913_suit.userID||gMember.getIdLong()==iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cSTRAITJACKET.isOn()&&!gNewUserProfile.cARMCUFFS.areArmsRestrained()&&!gNewUserProfile.cENCASE.isEncased()&&!gNewUserProfile.cSUIT.isBDSMSuitOn()&&!gNewUserProfile.cMITTS.isOn())){
                    logger.info(fName+"options:2");
                    switch (page){
                        case "main":
                            logger.info(fName+"removed for new menu");
                            /*if(gNewUserProfile.cSUIT.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS));
                            */
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear));
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed));
                            }
                            break;
                        case iSuit.levelSuitLatex:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            if(gNewUserProfile.cSUIT.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitCatsuit))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitPuppy))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitPony))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitDrone))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitKitty))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitCow))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Bitchsuit.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Hogsack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Sleepsack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                        case iSuit.levelSuitRubber:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            if(gNewUserProfile.cSUIT.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitCatsuit))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitPuppy))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitPony))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitDrone))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitKitty))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitCow))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Bitchsuit.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Hogsack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Sleepsack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                        case iSuit.levelSuitTextile:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            if(gNewUserProfile.cSUIT.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(iSuit.levelSuitPlush))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Textile.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Bitchsuit.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Textile.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Hogsack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));
                            if(!gNewUserProfile.cSUIT.getSuitMatterialAsString().equalsIgnoreCase(SUITMATTERIAL.Textile.getName())||!gNewUserProfile.cSUIT.getSuitTypeAsString().equalsIgnoreCase(SUITTYPE.Sleepsack.getName()))lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));
                            break;
                        case iSuit.levelSuitToy:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            if(gNewUserProfile.cSUIT.isToySuitOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO));
                            if(gNewUserProfile.cSUIT.isToySuitOn()){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare));
                            }
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                            break;
                        default:
                            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO));
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear));
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed));
                            }

                            break;
                    }
                }else{
                    logger.info(fName+"options:3");
                    switch (page){
                        case "main":
                            logger.info(fName+"removed for new menu");
                            /*if(gNewUserProfile.cSUIT.isOn())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS));*/
                            break;
                        case iSuit.levelSuitLatex:
                        case iSuit.levelSuitRubber:
                        case iSuit.levelSuitTextile:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            break;
                        case iSuit.levelSuitToy:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            if(gNewUserProfile.cSUIT.isToySuitOn()){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare));
                            }
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                            break;
                        default:
                            break;
                    }
                }
                menuLevelsWearerListener(message,page);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsOther(String page){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                logger.info(fName+"page="+page);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                embed.setColor(llColorOrange);
                embed.addField(strSupportTitle,strSupport,false);
                embed.setTitle(gNewUserProfile.getMember().getUser().getName()+"'s "+sRTitle+" Menu");
                if(gNewUserProfile.gUserProfile.jsonObject.has(nHood)&&gNewUserProfile.cSUIT.isOn()){
                    String matterial=gNewUserProfile.cSUIT.getSuitMatterialAsString();
                    String type=gNewUserProfile.cSUIT.getSuitTypeAsString();
                    if(!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyAlpha)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyBeta)&&!gNewUserProfile.cSUIT.getSuitTypeAsString().equals(iSuit.levelSuitSpecialToyOmega)){
                        embed.addField("Currently",matterial+" "+type,false);
                    }else{
                        embed.addField("Currently","toy suit\n"+"Rank: "+getToyRank(gNewUserProfile.gUserProfile)+"\nScore:"+gNewUserProfile.cSUIT.getSuitScore()+"/"+requiredScore4Toy(gNewUserProfile.gUserProfile)+"\nFree talk:"+gNewUserProfile.cSUIT.getSuitFreeTalkAvailable()+"/"+allowedFreeTalk4Toy(gNewUserProfile.gUserProfile)+"\nprefix: "+gNewUserProfile.cSUIT.getSuitPrefix(),false);
                    }
                }else{
                    embed.addField("Currently","(not wearing)",false);
                }
                boolean isLocked=false;
                if(!gIsOverride&&iRestraints.sDeniedToTakeOffReinderSuit(gNewUserProfile.gUserProfile)){
                    logger.info(fName + ".can't use do to special suit");
                    embed.addField("Locked","The reindeer suit is locked till last day of the year.\nDuration:"+lsUsefullFunctions.displayDuration(iRestraints.sGetTimeRemainginReinderSuit()), false);
                    isLocked=true;
                }
                if(!isLocked){
                    switch (page){
                        case "main":
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" "  + SUITMATTERIAL.Rubber.getName()+" suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL)+" "  + SUITMATTERIAL.Latex.getName()+" suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)+" "  + SUITMATTERIAL.Textile.getName()+" suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" "  +"toy suits";
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear)+" **VIP Suit** plush";
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed)+" **VIP Suit** sleeper";
                            }
                            break;
                        case iSuit.levelSuitLatex:
                        case iSuit.levelSuitRubber:
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" go back";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "  +iSuit.levelSuitCatsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "  +iSuit.levelSuitPuppy;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+iSuit.levelSuitPony;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" " +iSuit.levelSuitDrone;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" " +iSuit.levelSuitKitty;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "  +iSuit.levelSuitCow;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" "  +iSuit.levelSuitBitchsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)+" "  +iSuit.levelSuitHogsack;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9)+" "  +iSuit.levelSuitSleepsack;
                            break;
                        case iSuit.levelSuitTextile:
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp)+" go back";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "  +iSuit.levelSuitPlush;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7)+" "  +iSuit.levelSuitBitchsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8)+" "  +iSuit.levelSuitHogsack;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9)+" "  +iSuit.levelSuitSleepsack;
                            break;
                        case iSuit.levelSuitToy:
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" release";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" alpha, score 25 point is required to release yourself without voting or voting by others, no gag safeword, 20 free speech, unable to take off or change restrictions that is part of the suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" beta, +access set to exposed, 5 free speech, require score 50 and voting to get released";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO)+" omega, +access set to exposed, 2 free speech, require score 90 and 5 votes to get released";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" +1 point";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" +5 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle)+" +10 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle)+" +25 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare)+" -1 point";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare)+" -5 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare)+" -10 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare)+" -25 points";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" set prefix they should call themselves";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound)+" set toy gag";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo)+" set list of good words/sentences";
                            break;
                        default:
                            //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR)+" "  +SUITTYPE.Latex.getName()+" "+iSuit.levelSuitReindeer;
                            //desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ)+" "+SUITTYPE.Rubber.getName()+" "+iSuit.levelSuitReindeer;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" take off suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" "  + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitCatsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" "  + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitPuppy;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" "+ SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitPony;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" " + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitDrone;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" " + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitKitty;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" "  + SUITMATTERIAL.Latex.getName()+" "+iSuit.levelSuitCow;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitCatsuit;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitPuppy;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitPony;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitDrone;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitKitty;
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" "+ SUITMATTERIAL.Rubber.getName()+" "+iSuit.levelSuitCow;
                            desc+="\nSpecial toy suits";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" alpha, score 25 point is required to release yourself without voting or voting by others, no gag safeword, 20 free speech, unable to take off or change restrictions that is part of the suit";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" beta, +access set to exposed, 5 free speech, require score 50 and voting to get released";
                            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO)+" omega, +access set to exposed, 2 free speech, require score 90 and 5 votes to get released";
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear)+" **VIP Suit** plush";
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed)+" **VIP Suit** sleeper";
                            }
                            break;
                    }

                }
                embed.addField(" ", "Please select a suit option for " + gNewUserProfile.gUserProfile.getUser().getAsMention() + ":"+desc, false);
                if(page.equalsIgnoreCase("main")&&gNewUserProfile.cSUIT.isToySuitOn()){
                    desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle)+" +1 point";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" +5 points";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle)+" +10 points";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle)+" +25 points";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare)+" -1 point";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare)+" -5 points";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare)+" -10 points";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare)+" -25 points";
                    embed.addField(" ", "Please select a toy suit option :"+desc, false);
                }
                embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
                if(gNewUserProfile.isLocked_checkMember(gMember)||gNewUserProfile.cENCASE.isEncased()||gNewUserProfile.cSTRAITJACKET.isOn()){
                    desc="";
                    if(gNewUserProfile.cSTRAITJACKET.isOn()){
                        desc+=iRdStr.strRestraintJacket;
                    }
                    if(gNewUserProfile.cENCASE.isEncased()){
                        desc+=iRdStr.strRestraintEncased;
                    }
                    if(gNewUserProfile.isLocked_checkMember(gMember)){
                        desc+=iRdStr.strRestraintLocked;
                    }
                    if(gMember.getIdLong()==iRDPatreon.patreonUser_118178462149115913_suit.userID||gMember.getIdLong()==iRDPatreon.patreonUser_239748154046545920_suit.userID){
                        desc+="\n•  VIP bypass";
                    }
                    embed.addField(iRdStr.strNewLimitedTitle,iRdStr.strFollowingRestraintsOptionsAuth+desc,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                messageComponentManager.loadMessageComponents(gCommandFileMainPath,false);
                try {
                    JSONArray jsonArray=new JSONArray();
                    boolean isMultiple=true;
                    if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Latex){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitL);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Rubber){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitR);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Textile){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitT);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Toy){
                        jsonArray= messageComponentManager.readMessageComponentsJson(gCommandFileSuitS);
                        if(jsonArray!=null&&!jsonArray.isEmpty()){
                            messageComponentManager.addMessageComponents(jsonArray);
                        }
                    }
                    else{
                        messageComponentManager.buildMessageComponents();isMultiple=false;
                    }
                    messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent.SelectMenu selectContainer0=messageComponentManager.messageBuildComponent_Select.getSelectById("select_material");
                    lcMessageBuildComponent.SelectMenu selectContainer1=new lcMessageBuildComponent.SelectMenu();
                    if(isMultiple){
                        messageComponentManager.messageBuildComponent_Select=messageComponentManager.messageBuildComponents.getComponent(1);
                        selectContainer1=messageComponentManager.messageBuildComponent_Select.getSelectById("select_type");
                    }
                    if(!gNewUserProfile.cSUIT.isOn())selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasZero);
                    else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Latex||gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Rubber){
                        if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Latex)selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolL);
                        else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Rubber)selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolR);

                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Catsuit)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Puppy)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasTwo);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Pony)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasThree);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Drone)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFour);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Kitty)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasFive);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Cow)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSix);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias7);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias8);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias9);

                    }
                    else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Textile){
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolT);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Plush)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasOne);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Bitchsuit)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias7);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Hogsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias8);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.Sleepsack)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.alias9);
                    }
                    else if(gNewUserProfile.cSUIT.getSuitMatterial()==SUITMATTERIAL.Toy){
                        selectContainer0.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolS);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyAlpha)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolA);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyBeta)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolB);
                        if(gNewUserProfile.cSUIT.getSuitType()==SUITTYPE.ToyOmega)selectContainer1.setDefaultOptionByValue(lsUnicodeEmotes.aliasSymbolC);
                        if(!gNewUserProfile.cSUIT.isToySuitOn()){
                            messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("large_blue_circle",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("green_circle",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("yellow_circle",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(2).getButtonById("orange_circle",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("blue_square",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("green_square",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("yellow_square",true).setDisable();
                            messageComponentManager.messageBuildComponents.getComponent(3).getButtonById("orange_square",true).setDisable();
                        }
                    }
                    if(isLocked){
                        logger.info(fName+"locked");
                        selectContainer0.setDisabled();selectContainer1.setDisabled();
                    }

                    logger.info(fName+"component="+messageComponentManager.messageBuildComponents.getJson());
                    message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=sendOrUpdatePrivateEmbed(embed);
                }
                if(isLocked){
                    logger.info(fName+"options:1");
                }else
                if(gMember.getIdLong()==iRDPatreon.patreonUser_118178462149115913_suit.userID||gMember.getIdLong()==iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.allow2BypassNewRestrictions()||(!gNewUserProfile.cSTRAITJACKET.isOn()&&!gNewUserProfile.cENCASE.isEncased())) {
                    logger.info(fName+"options:2");
                    switch (page){
                        case "main":
                            logger.info(fName+"removed 4 new menu");
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear));
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed));
                            }
                            break;
                        default:
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear));
                            }
                            if(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember)){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed));
                            }
                            break;
                    }
                }else{
                    logger.info(fName+"options:3");
                    switch (page){
                        case "main":
                            logger.info(fName+"removed 4 new menu");
                            /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS));
                            if(gNewUserProfile.cSUIT.isToySuitOn()){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare));
                            }*/
                            break;
                        case iSuit.levelSuitLatex:
                        case iSuit.levelSuitRubber:
                        case iSuit.levelSuitTextile:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            break;
                        case iSuit.levelSuitToy:
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                            if(gNewUserProfile.cSUIT.isToySuitOn()){
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare));
                                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare));
                            }
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound));
                            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                            break;
                        default:
                            break;
                    }
                }
                menuLevelsOtherListener(message,page);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message,String page){
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
                                String level="",category="";
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource))level="information_source";
                                switch (id){
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";
                                    case lsUnicodeEmotes.aliasMemo: rToyGoodWords();return;
                                    case lsUnicodeEmotes.aliasSound: rToyGagWords();return;
                                    case lsUnicodeEmotes.aliasID: menuSetToyPrefix();return;
                                    case lsUnicodeEmotes.aliasZero:
                                        if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                            category=vToy;level=vRelease;
                                        }else{
                                            category="arm";level=vOff;
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasBlueCircle:category=vToy;level=cmdToyAdd1;break;
                                    case lsUnicodeEmotes.aliasGreenCircle:category=vToy;level=cmdToyAdd5;break;
                                    case lsUnicodeEmotes.aliasYellowCircle:category=vToy;level=cmdToyAdd10;break;
                                    case lsUnicodeEmotes.aliasOrangeCircle:category=vToy;level=cmdToyAdd25;break;
                                    case lsUnicodeEmotes.aliasBlueSquare:category=vToy;level=cmdToyRem1;break;
                                    case lsUnicodeEmotes.aliasGreenSquare:category=vToy;level=cmdToyRem5;break;
                                    case lsUnicodeEmotes.aliasYellowSquare:category=vToy;level=cmdToyRem10;break;
                                    case lsUnicodeEmotes.aliasOrangeSquare:category=vToy;level=cmdToyRem25;break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        if(level.equalsIgnoreCase(vRelease)||level.equalsIgnoreCase(vOff)){
                                            selectedMatterial="";selectedType="";
                                        }
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            rSuit_textile(level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                            rSuit_latex(level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            rSuit_rubber(level);
                                        }else{
                                            rSuit(level);
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsWearer(page);
                                    }
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
                                String id=e.getComponentId();
                                logger.warn(fName+"id="+id);
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                String level="",category="";
                                llMessageDelete(message);
                                switch (id){
                                    case "select_material":
                                        if(value.equalsIgnoreCase(lsUnicodeEmotes.aliasZero)){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        switch (value){
                                            case lsUnicodeEmotes.aliasZero:
                                                if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                    category=vToy;level=vRelease;
                                                }else{
                                                    category="arm";level=vOff;
                                                }break;
                                            case lsUnicodeEmotes.aliasSymbolR:selectedMatterial=SUITMATTERIAL.Rubber.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolL:selectedMatterial=SUITMATTERIAL.Latex.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolT:selectedMatterial=SUITMATTERIAL.Textile.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolS:selectedMatterial=SUITMATTERIAL.Toy.getName();break;
                                        }
                                        break;
                                    case "select_type":
                                        category=selectedMatterial;
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            switch (value){
                                                case lsUnicodeEmotes.aliasOne:level=iSuit.levelSuitCatsuit;break;
                                                case lsUnicodeEmotes.aliasTwo:level=iSuit.levelSuitPuppy;break;
                                                case lsUnicodeEmotes.aliasThree:level=iSuit.levelSuitPony;break;
                                                case lsUnicodeEmotes.aliasFour:level=iSuit.levelSuitDrone;break;
                                                case lsUnicodeEmotes.aliasFive:level=iSuit.levelSuitKitty;break;
                                                case lsUnicodeEmotes.aliasSix:level=iSuit.levelSuitCow;break;
                                                case lsUnicodeEmotes.alias7:level=iSuit.levelSuitBitchsuit;break;
                                                case lsUnicodeEmotes.alias8:level=iSuit.levelSuitHogsack;break;
                                                case lsUnicodeEmotes.alias9:level=iSuit.levelSuitSleepsack;break;
                                            }
                                        }
                                        else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            switch (value){
                                                case lsUnicodeEmotes.aliasOne:level=iSuit.levelSuitPlush;break;
                                                case lsUnicodeEmotes.alias7:level=iSuit.levelSuitBitchsuit;break;
                                                case lsUnicodeEmotes.alias8:level=iSuit.levelSuitHogsack;break;
                                                case lsUnicodeEmotes.alias9:level=iSuit.levelSuitSleepsack;break;
                                            }
                                        }
                                        else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Toy.getName())){
                                            switch (value){
                                                case lsUnicodeEmotes.aliasOne: case lsUnicodeEmotes.aliasSymbolA:level=iSuit.levelSuitSpecialToyAlpha;break;
                                                case lsUnicodeEmotes.aliasTwo:case lsUnicodeEmotes.aliasSymbolB:level=iSuit.levelSuitSpecialToyBeta;break;
                                                case lsUnicodeEmotes.aliasThree: case lsUnicodeEmotes.aliasSymbolO:level=iSuit.levelSuitSpecialToyOmega;break;
                                            }
                                        }

                                        break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(vRelease)||level.equalsIgnoreCase(vOff)){
                                        selectedMatterial="";selectedType="";
                                    }
                                    if(category.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                        rSuit_textile(level);
                                    }else
                                    if(category.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                        rSuit_latex(level);
                                    }else
                                    if(category.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                        rSuit_rubber(level);
                                    }else{
                                        rSuit(level);
                                    }
                                }
                                else if(!selectedMatterial.isBlank()){
                                    if(level.equalsIgnoreCase(vRelease)||level.equalsIgnoreCase(vOff)){
                                        selectedMatterial="";selectedType="";
                                    }
                                    if(gNewUserProfile.cSUIT.isOn()){
                                        level=gNewUserProfile.cSUIT.getSuitTypeAsString();
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            rSuit_textile(level);
                                        }else
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                            rSuit_latex(level);
                                        }else
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            rSuit_rubber(level);
                                        }else{
                                            rSuit(level);
                                        }
                                    }else{
                                        //menuLevelsWearer(page);
                                    }

                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer(page);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="",category="";
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){level="information_source";}
                                switch (page){
                                    case "main":
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(null,"main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))){
                                            menuLevels(null, SUITMATTERIAL.Rubber.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL))){
                                            menuLevels(null, SUITMATTERIAL.Latex.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolV))){
                                            menuLevels(null,"vip");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                            menuLevels(null, SUITMATTERIAL.Textile.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS))){
                                            menuLevels(null, SUITMATTERIAL.Toy.getName());return;
                                        }
                                        break;
                                    case iSuit.levelSuitLatex:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(null,"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitBitchsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitHogsack;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitSleepsack;}
                                        break;
                                    case iSuit.levelSuitRubber:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(null,"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitBitchsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitHogsack;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitSleepsack;}
                                        break;
                                    case iSuit.levelSuitTextile:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(null,"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Textile.getName();level=iSuit.levelSuitPlush;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){category= SUITMATTERIAL.Textile.getName();level=iSuit.levelSuitBitchsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){category= SUITMATTERIAL.Textile.getName();level=iSuit.levelSuitHogsack;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){category= SUITMATTERIAL.Textile.getName();level=iSuit.levelSuitSleepsack;}
                                        break;
                                    case iSuit.levelSuitToy:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(null,"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                            rToyGoodWords();return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))){
                                            rToyGagWords();return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                            menuSetToyPrefix();return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category=vToy;level=iSuit.levelSuitSpecialToyAlpha;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category=vToy;level=iSuit.levelSuitSpecialToyBeta;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category=vToy;level=iSuit.levelSuitSpecialToyOmega;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category=vToy;level=iSuit.levelSuitSpecialToyAlpha;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category=vToy;level=iSuit.levelSuitSpecialToyBeta;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO))){category=vToy;level=iSuit.levelSuitSpecialToyOmega;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category=vToy;level=cmdToyAdd1;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category=vToy;level=cmdToyAdd5;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category=vToy;level=cmdToyAdd10;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category=vToy;level=cmdToyAdd25;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare))){category=vToy;level=cmdToyRem1;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare))){category=vToy;level=cmdToyRem5;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare))){category=vToy;level=cmdToyRem10;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare))){category=vToy;level=cmdToyRem25;}
                                        break;
                                    default:
                                        //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                                        //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ));
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){ category="arm";level=vOff; }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category=vToy;level=iSuit.levelSuitSpecialToyAlpha;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category=vToy;level=iSuit.levelSuitSpecialToyBeta;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO))){category=vToy;level=iSuit.levelSuitSpecialToyOmega;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitReindeer;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitReindeer;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear))&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))){
                                            category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_239748154046545920_suit.command;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed))&&(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))){
                                            category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_118178462149115913_suit.command;
                                        }
                                        break;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear))&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))){
                                    category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_239748154046545920_suit.command;
                                    rSuit(level);
                                    return;

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed))&&(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))){
                                    category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_118178462149115913_suit.command;
                                    rSuit(level);
                                    return;
                                }

                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else{
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            rSuit_textile(level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                            rSuit_latex(level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            rSuit_rubber(level);
                                        }else{
                                            rSuit(level);
                                        }
                                    }
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuLevelsOtherListener(Message message,String page){
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
                                String level="",category="";
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource))level="information_source";
                                switch (id){
                                    case lsUnicodeEmotes.aliasInformationSource:level="information_source";
                                    case lsUnicodeEmotes.aliasMemo: rToyGoodWords(gTarget);return;
                                    case lsUnicodeEmotes.aliasSound: rToyGagWords(gTarget);return;
                                    case lsUnicodeEmotes.aliasID: menuSetToyPrefix();return;
                                    case lsUnicodeEmotes.aliasZero:
                                        if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                            category=vToy;level=vRelease;
                                        }else{
                                            category="arm";level=vOff;
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasBlueCircle:category=vToy;level=cmdToyAdd1;break;
                                    case lsUnicodeEmotes.aliasGreenCircle:category=vToy;level=cmdToyAdd5;break;
                                    case lsUnicodeEmotes.aliasYellowCircle:category=vToy;level=cmdToyAdd10;break;
                                    case lsUnicodeEmotes.aliasOrangeCircle:category=vToy;level=cmdToyAdd25;break;
                                    case lsUnicodeEmotes.aliasBlueSquare:category=vToy;level=cmdToyRem1;break;
                                    case lsUnicodeEmotes.aliasGreenSquare:category=vToy;level=cmdToyRem5;break;
                                    case lsUnicodeEmotes.aliasYellowSquare:category=vToy;level=cmdToyRem10;break;
                                    case lsUnicodeEmotes.aliasOrangeSquare:category=vToy;level=cmdToyRem25;break;
                                }
                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        if(gComponentInteractionHook!=null){
                                            sendOrUpdatePrivateEmbed(sRTitle,iRdStr.strOpenHelpMenu,llColorBlue1);
                                            gCurrentInteractionHook=gComponentInteractionHook;
                                        }
                                        rHelp("main");
                                    }else{
                                        if(level.equalsIgnoreCase(vRelease)||level.equalsIgnoreCase(vOff)){
                                            selectedMatterial="";selectedType="";
                                        }
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            rSuit_textile(gTarget,level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                            rSuit_latex(gTarget,level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            rSuit_rubber(gTarget,level);
                                        }else{
                                            rSuit(gTarget,level);
                                        }
                                        gCurrentInteractionHook=gComponentInteractionHook;
                                        menuLevelsOther(page);
                                    }
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
                                String id=e.getComponentId();
                                logger.warn(fName+"id="+id);
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                String level="",category="";
                                llMessageDelete(message);
                                switch (id){
                                    case "select_material":
                                        if(value.equalsIgnoreCase(lsUnicodeEmotes.aliasZero)){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        switch (value){
                                            case lsUnicodeEmotes.aliasZero:
                                                if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                    category=vToy;level=vRelease;
                                                }else{
                                                    category="arm";level=vOff;
                                                }break;
                                            case lsUnicodeEmotes.aliasSymbolR:selectedMatterial=SUITMATTERIAL.Rubber.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolL:selectedMatterial=SUITMATTERIAL.Latex.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolT:selectedMatterial=SUITMATTERIAL.Textile.getName();break;
                                            case lsUnicodeEmotes.aliasSymbolS:selectedMatterial=SUITMATTERIAL.Toy.getName();break;
                                        }
                                        break;
                                    case "select_type":
                                        category=selectedMatterial;
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())||selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            switch (value){
                                                case lsUnicodeEmotes.aliasOne:level=iSuit.levelSuitCatsuit;break;
                                                case lsUnicodeEmotes.aliasTwo:level=iSuit.levelSuitPuppy;break;
                                                case lsUnicodeEmotes.aliasThree:level=iSuit.levelSuitPony;break;
                                                case lsUnicodeEmotes.aliasFour:level=iSuit.levelSuitDrone;break;
                                                case lsUnicodeEmotes.aliasFive:level=iSuit.levelSuitKitty;break;
                                                case lsUnicodeEmotes.aliasSix:level=iSuit.levelSuitCow;break;
                                                case lsUnicodeEmotes.alias7:level=iSuit.levelSuitBitchsuit;break;
                                                case lsUnicodeEmotes.alias8:level=iSuit.levelSuitHogsack;break;
                                                case lsUnicodeEmotes.alias9:level=iSuit.levelSuitSleepsack;break;
                                            }
                                        }
                                        else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            switch (value){
                                                case lsUnicodeEmotes.aliasOne:level=iSuit.levelSuitPlush;break;
                                                case lsUnicodeEmotes.alias7:level=iSuit.levelSuitBitchsuit;break;
                                                case lsUnicodeEmotes.alias8:level=iSuit.levelSuitHogsack;break;
                                                case lsUnicodeEmotes.alias9:level=iSuit.levelSuitSleepsack;break;
                                            }
                                        }
                                        else if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Toy.getName())){
                                            switch (value){
                                                case lsUnicodeEmotes.aliasOne: case lsUnicodeEmotes.aliasSymbolA:level=iSuit.levelSuitSpecialToyAlpha;break;
                                                case lsUnicodeEmotes.aliasTwo:case lsUnicodeEmotes.aliasSymbolB:level=iSuit.levelSuitSpecialToyBeta;break;
                                                case lsUnicodeEmotes.aliasThree: case lsUnicodeEmotes.aliasSymbolO:level=iSuit.levelSuitSpecialToyOmega;break;
                                            }
                                        }
                                        break;
                                }


                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(vRelease)||level.equalsIgnoreCase(vOff)){
                                        selectedMatterial="";selectedType="";
                                    }
                                    if(category.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                        rSuit_textile(gTarget,level);
                                    }else
                                    if(category.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                        rSuit_latex(gTarget,level);
                                    }else
                                    if(category.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                        rSuit_rubber(gTarget,level);
                                    }else{
                                        rSuit(gTarget,level);
                                    }
                                }
                                else if(!selectedMatterial.isBlank()){
                                    if(level.equalsIgnoreCase(vRelease)||level.equalsIgnoreCase(vOff)){
                                        selectedMatterial="";selectedType="";
                                    }
                                    if(gNewUserProfile.cSUIT.isOn()){
                                        level=gNewUserProfile.cSUIT.getSuitTypeAsString();
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            rSuit_textile(gTarget,level);
                                        }else
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                            rSuit_latex(gTarget,level);
                                        }else
                                        if(selectedMatterial.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            rSuit_rubber(gTarget,level);
                                        }else{
                                            rSuit(gTarget,level);
                                        }
                                    }else{
                                        //menuLevelsWearer(page);
                                    }

                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsOther(page);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String level="",category="";
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){level="information_source";}
                                switch (page){
                                    case "main":
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))){
                                            menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Rubber.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolL))){
                                            menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Latex.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolV))){
                                            menuLevels(gNewUserProfile.getMember(),"vip");return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                            menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Textile.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS))){
                                            menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Toy.getName());return;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category=vToy;level=cmdToyAdd1;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category=vToy;level=cmdToyAdd5;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category=vToy;level=cmdToyAdd10;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category=vToy;level=cmdToyAdd25;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare))){category=vToy;level=cmdToyRem1;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare))){category=vToy;level=cmdToyRem5;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare))){category=vToy;level=cmdToyRem10;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare))){category=vToy;level=cmdToyRem25;}
                                        break;
                                    case iSuit.levelSuitLatex:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitBitchsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitHogsack;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitSleepsack;}
                                        break;
                                    case iSuit.levelSuitRubber:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitBitchsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitHogsack;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitSleepsack;}
                                        break;
                                    case iSuit.levelSuitTextile:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Textile.getName();level=iSuit.levelSuitPlush;}
                                        break;
                                    case iSuit.levelSuitToy:
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                            menuLevels(gNewUserProfile.getMember(),"main");return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                            rToyGoodWords(gNewUserProfile.getMember());return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSound))){
                                            rToyGagWords(gNewUserProfile.getMember());return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                            menuSetToyPrefix();return;
                                        }
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                            if(gNewUserProfile.cSUIT.isToySuitSelected()){
                                                category=vToy;level=vRelease;
                                            }else{
                                                category="arm";level=vOff;
                                            }
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category=vToy;level=iSuit.levelSuitSpecialToyAlpha;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category=vToy;level=iSuit.levelSuitSpecialToyBeta;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category=vToy;level=iSuit.levelSuitSpecialToyOmega;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueCircle))){category=vToy;level=cmdToyAdd1;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){category=vToy;level=cmdToyAdd5;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowCircle))){category=vToy;level=cmdToyAdd10;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeCircle))){category=vToy;level=cmdToyAdd25;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlueSquare))){category=vToy;level=cmdToyRem1;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenSquare))){category=vToy;level=cmdToyRem5;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasYellowSquare))){category=vToy;level=cmdToyRem10;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOrangeSquare))){category=vToy;level=cmdToyRem25;}
                                        break;
                                    default:
                                        //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                                        //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ));
                                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){ category="arm";level=vOff; }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCatsuit;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPuppy;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitPony;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitDrone;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitKitty;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitCow;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))){category=vToy;level=iSuit.levelSuitSpecialToyAlpha;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))){category=vToy;level=iSuit.levelSuitSpecialToyBeta;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolO))){category=vToy;level=iSuit.levelSuitSpecialToyOmega;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))){category= SUITMATTERIAL.Latex.getName();level=iSuit.levelSuitReindeer;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolZ))){category= SUITMATTERIAL.Rubber.getName();level=iSuit.levelSuitReindeer;}
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear))&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))){
                                            category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_239748154046545920_suit.command;
                                        }
                                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed))&&(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))){
                                            category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_118178462149115913_suit.command;
                                        }
                                        break;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTeddyBear))&&(gMember.getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_239748154046545920_suit.userID||lsMemberIsBotOwner(gMember))){
                                    category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_239748154046545920_suit.command;
                                    rSuit(gNewUserProfile.getMember(),level);
                                    return;

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBed))&&(gMember.getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||gNewUserProfile.gUserProfile.getUser().getIdLong()== iRDPatreon.patreonUser_118178462149115913_suit.userID||lsMemberIsBotOwner(gMember))){
                                    category= SUITMATTERIAL.Textile.getName();level= iRDPatreon.patreonUser_118178462149115913_suit.command;
                                    rSuit(gNewUserProfile.getMember(),level);
                                    return;
                                }

                                if(!level.isBlank()){
                                    if(level.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                                        rHelp("main");
                                    }else
                                    {
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Textile.getName())){
                                            rSuit_textile(gNewUserProfile.getMember(),level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Latex.getName())){
                                            rSuit_latex(gNewUserProfile.getMember(),level);
                                        }else
                                        if(category.equalsIgnoreCase(SUITMATTERIAL.Rubber.getName())){
                                            rSuit_rubber(gNewUserProfile.getMember(),level);
                                        }else{
                                            rSuit(gNewUserProfile.getMember(),level);
                                        }
                                    }
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }

        private void menuSetToyPrefix(){
            String fName="[menuSetToyPrefix]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".can't use do to access owner");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit prefix due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                        return;
                    }
                    embed.setTitle(iSuit.title4ToySuitPrefix);
                }else{
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                        logger.info(fName + ".can't use do to locked by not you");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit prefix due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                        return;
                    }else
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                        logger.info(fName + ".can't use do to access protected");
                        sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit prefix due to their access setting.", llColorRed);return;
                    }
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4ToySuitPrefix);
                }
                embed.setDescription("Please enter the toy suit prefix.Type '!cancel' to abort. Type `!reset` to restore it to default, toy.");
                Message message=llSendMessageResponse(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Toy.getName());
                                }else
                                if(content.equalsIgnoreCase("!reset")){
                                    gNewUserProfile.cSUIT.setSuitPrefix("toy");
                                    saveProfile();
                                    menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Toy.getName());
                                }else
                                if(content.isBlank()){
                                    menuSetToyPrefix();
                                }else{
                                    gNewUserProfile.cSUIT.setSuitPrefix(content);
                                    saveProfile();
                                    menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Toy.getName());
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void rToyGoodWords() {
            String fName = "[rToyGoodWords]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit good words list due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }
            menuToyGoodWords();
        }
        private void rToyGoodWords(Member mtarget) {
            String fName = "[rToyGoodWords]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit good words list due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit good words list due to their access setting.", llColorRed);return;
            }
            menuToyGoodWords();
        }
        boolean isPatreon=false;
        private void menuToyGoodWords(){
            String fName="[menuToyGoodWords]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                int length=gNewUserProfile.cSUIT.getCustomGoodTalkLength();
                String desc="";
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(isPatreon){
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle(iSuit.title4ToyCustomGoodTalk+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4ToyCustomGoodTalk+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }
                    desc="";
                    if(length>0) {
                        desc += "\n:one: for list";
                    }
                    desc+="\n:two: for adding new line";
                    desc+="\nFormatting: Use the world `!name` to show your nickname that is currently in use.Use `!slave` to show your slave number.";
                    if(length>0){
                        desc+="\n:four: to clear";
                    }
                    embed.setDescription("used/total:\n"+length +"/"+iSuit.patreonLimit4CustomText+desc);
                }else{
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle(iSuit.title4ToyCustomGoodTalk+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4ToyCustomGoodTalk+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }
                    desc="";
                    if(length>0) {
                        desc += "\n:one: for list";
                    }
                    desc+="\n:two: for adding new line";
                    desc+="\nFormatting: Use the world `!name` to show your nickname that is currently in use.Use `!slave` to show your slave number.";
                    if(length>0){
                        desc+="\n:four: to clear";
                    }
                    embed.setDescription("used/total:\n"+length +"/"+iSuit.normalLimit4CustomText+desc);
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(length>0) {
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                }
                if((isPatreon&&length<iSuit.patreonLimit4CustomText)||length<iSuit.normalLimit4CustomText){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                }
                if(length>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
								String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowUp)){
                                    logger.warn(fName+"trigger="+name);
                                    menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Toy.getName());
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias1)){
                                    logger.warn(fName+"trigger="+name);
                                    menuGoodTextList(0);
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias2)){
                                    logger.warn(fName+"trigger="+name);
                                    if((isPatreon&& length<iSuit.patreonLimit4CustomText)|| length<iSuit.normalLimit4CustomText){
                                        menuGoodTextAddText();
                                    }else{
                                        menuToyGoodWords();
                                    }
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias3)){
                                    logger.warn(fName+"trigger="+name);
                                    menuGoodTextRemoveText();
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias4)){
                                    logger.warn(fName+"trigger="+name);
                                    gNewUserProfile.cSUIT.setCustomGoodTalk(new JSONArray());
                                    saveProfile();
                                    menuToyGoodWords();
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias5)){
                                    logger.warn(fName+"trigger="+name);
                                    menuGoodTextViewText(false);
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias6)){
                                    logger.warn(fName+"trigger="+name);
                                    menuGoodTextViewText(true);
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);

                                }else{
                                    menuToyGoodWords();
                                }
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
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuGoodTextList(int page){
            String fName="[menuList]";
            logger.info(fName);
            try{
                logger.info(fName+"page="+page);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                int length=gNewUserProfile.cSUIT.getCustomGoodTalkLength();
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(iSuit.title4ToyCustomGoodTalk);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4ToyCustomGoodTalk);
                }
                embed.addField("List","size: "+length+"",false);
                JSONObject quicklist=new JSONObject();boolean canBeQuickList=false;
                if(length<10){
                    quicklist=listofPreviews2(gNewUserProfile.cSUIT.getCustomGoodTalk());
                    if(quicklist.has("hitLimit")&&!quicklist.getBoolean("hitLimit")){
                        if(quicklist.has("text")&&!quicklist.getString("text").isBlank()&&quicklist.getString("text").length()<=2000){
                            canBeQuickList=true;
                        }
                    }
                }
                if(canBeQuickList){
                    embed.setDescription(quicklist.getString("text"));
                }else{
                    String text=gNewUserProfile.cSUIT.getCustomGoodTalk(page);
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
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
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
                                    menuToyGoodWords();
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuGoodTextList(page-1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuGoodTextList(page+1);
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
                                            sendOrUpdatePrivateEmbed(sRTitle, iGag.cantmanipulatedueaccessset2owner, llColorRed);
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
                                    gNewUserProfile.cSUIT.remCustomGoodTalk(indexRemove);
                                    saveProfile();
                                    menuGoodTextList(0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
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

        private void menuGoodTextAddText(){
            String fName="[menuAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(iSuit.title4ToyCustomGoodTalk);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4ToyCustomGoodTalk);
                }
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuToyGoodWords();
                                }else
                                if(content.isBlank()){
                                    menuGoodTextAddText();
                                }else{
                                    gNewUserProfile.cSUIT.addCustomGoodTalk(content);
                                    saveProfile();
                                    menuToyGoodWords();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void rToyGagWords() {
            String fName = "[rToyGagWords]";
            logger.info(fName);
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                logger.info(fName + ".can't use do to access owner");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate your suit gag due to access set to owner. Only your owner and sec-owners have access", llColorRed);
                return;
            }
            menuToyGagWords();
        }
        private void rToyGagWords(Member mtarget) {
            String fName = "[rToyGagWords]";
            logger.info(fName);
            logger.info(fName + ".target:"+mtarget.getId()+"|"+mtarget.getUser().getName());
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.hasUserGotTheKeyOrOwner(gUser)){
                logger.info(fName + ".can't use do to locked by not you");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit gag due to they locked by "+gNewUserProfile.cLOCK.getUserWhoLockedPet(), llColorRed);
                return;
            }else
            if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                logger.info(fName + ".can't use do to access protected");
                sendOrUpdatePrivateEmbed(sRTitle,"Can't manipulate their suit gag due to their access setting.", llColorRed);return;
            }
            menuToyGagWords();
        }
        private void menuToyGagWords(){
            String fName="[menuToySuitOptions]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                String desc="";
                int lengthToyGag=gNewUserProfile.cGAG.getCustomGagTexLength(GAGLEVELS.Toy);
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                if(isPatreon){
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle(iSuit.title4CustomTextToyGag+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4CustomTextToyGag+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    }
                    if(lengthToyGag>0) {
                        desc += "\n:one: for list";
                    }
                    desc+="\n:two: for adding new line";
                    if(lengthToyGag>0){
                        desc+="\n:three: to clear";
                    }
                    embed.setDescription("used/total:\n"+lengthToyGag +"/"+iGag.patreonLimit4CustomText+desc);
                }else{
                    if(gUser==gNewUserProfile.gUserProfile.getUser()){
                        embed.setTitle(iSuit.title4CustomTextToyGag+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }else{
                        embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4CustomTextToyGag+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    }
                    if(lengthToyGag>0) {
                        desc += "\n:one: for list";
                    }
                    desc+="\n:two: for adding new line";
                    if(lengthToyGag>0){
                        desc+="\n:three: to clear";
                    }
                    embed.setDescription("used/total:\n"+lengthToyGag +"/"+iGag.normalLimit4CustomText+desc);
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(lengthToyGag>0) {
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));
                }
                if((isPatreon&&lengthToyGag<iSuit.patreonLimit4CustomText)||lengthToyGag<iSuit.normalLimit4CustomText){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));
                }
                if(lengthToyGag>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.aliasArrowUp)){
                                    logger.warn(fName+"trigger="+name);
                                    menuLevels(gNewUserProfile.getMember(), SUITMATTERIAL.Toy.getName());
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias1)){
                                    logger.warn(fName+"trigger="+name);
                                    menuToyGagTextList(0);
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias2)){
                                    logger.warn(fName+"trigger="+name);
                                    if((isPatreon&& lengthToyGag<iGag.patreonLimit4CustomText)|| lengthToyGag<iGag.normalLimit4CustomText){
                                        menuToyGagAddText();
                                    }else{
                                        menuToyGagWords();
                                    }
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.alias3)){
                                    logger.warn(fName+"trigger="+name);
                                    gNewUserProfile.cGAG.setCustomGagText(GAGLEVELS.Toy,new JSONArray());
                                    saveProfile();
                                    menuToyGagWords();
                                }else
                                if(name.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.warn(fName+"trigger="+name);
                                }else{
                                    menuToyGagWords();
                                }

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
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuToyGagTextList(int page){
            String fName="[menuToyGagList]";
            logger.info(fName);
            try{
                logger.info(fName+"page="+page);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                int length=gNewUserProfile.cGAG.getCustomGagTexLength(GAGLEVELS.Toy);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(iSuit.title4CustomTextToyGag);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4CustomTextToyGag);
                }
                embed.addField("List","size: "+length+"",false);
                JSONObject quicklist=new JSONObject();boolean canBeQuickList=false;
                if(length<10){
                    quicklist=listofPreviews2(gNewUserProfile.cSUIT.getCustomGoodTalk());
                    if(quicklist.has("hitLimit")&&!quicklist.getBoolean("hitLimit")){
                        if(quicklist.has("text")&&!quicklist.getString("text").isBlank()&&quicklist.getString("text").length()<=2000){
                            canBeQuickList=true;
                        }
                    }
                }
                if(canBeQuickList){
                    embed.setDescription(quicklist.getString("text"));
                }else{
                    String text=gNewUserProfile.cGAG.getCustomGagTexAsString(GAGLEVELS.Toy,page);
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
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
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
                                    menuToyGagWords();
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuToyGagTextList(page-1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuToyGagTextList(page+1);
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
                                            sendOrUpdatePrivateEmbed(sRTitle,iGag.cantmanipulatedueaccessset2owner, llColorRed);
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
                                    gNewUserProfile.cGAG.remCustomGagText(GAGLEVELS.Toy,indexRemove);
                                    saveProfile();
                                    menuToyGagTextList(0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }
        private void menuToyGagAddText(){
            String fName="[menuToyGagAddText]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(iSuit.title4CustomTextToyGag);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4CustomTextToyGag);
                }
                embed.setDescription("Please enter the text you want to add.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuToyGagWords();
                                }else
                                if(content.isBlank()){
                                    menuToyGagAddText();
                                }else{
                                    gNewUserProfile.cGAG.addCustomGagText(GAGLEVELS.Toy,content);
                                    saveProfile();
                                    menuToyGagWords();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },10, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e.toString());
            }
        }

        private void menuGoodTextRemoveText(){
            String fName="[menuRemoveText]";
            logger.info(fName);
            try{
                JSONArray array=new JSONArray();
                array=gNewUserProfile.cSUIT.getCustomGoodTalk();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle(iSuit.title4ToyCustomGoodTalk);
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" "+iSuit.title4ToyCustomGoodTalk);
                }
                embed.setDescription("Please enter nr of line between 0-"+(array.length()-1)+" you want to delete.Type '!cancel' to abort.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                JSONArray finalArray = array;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuToyGoodWords();
                                }else
                                if(content.isBlank()){
                                    menuGoodTextRemoveText();
                                }else{
                                    int i=Integer.parseInt(content);
                                    if(i>=0&&i< finalArray.length()){
                                        finalArray.remove(i);
                                        gNewUserProfile.cSUIT.setCustomGoodTalk(finalArray);
                                        saveProfile();
                                        menuToyGoodWords();
                                    }else{
                                        menuGoodTextRemoveText();
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },1, TimeUnit.MINUTES, () -> {
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuGoodTextViewText(boolean isPreview){
            String fName="[menuViewText]";
            logger.info(fName);
            try{
                JSONArray array=new JSONArray();
                array=gNewUserProfile.cSUIT.getCustomGoodTalk();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorPurple2);
                if(gUser==gNewUserProfile.gUserProfile.getUser()){
                    embed.setTitle("Custom Text 4 GagLevels");
                }else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+" Custom Text 4 GagLevels");
                }
                if(isPreview){
                    embed.setDescription("Please enter nr of line between 0-"+(array.length()-1)+" to preview it.Type '!cancel' to abort.");
                }else{
                    embed.setDescription("Please enter nr of line between 0-"+(array.length()-1)+" to view it.Type '!cancel' to abort.");
                }
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                JSONArray finalArray = array;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuToyGoodWords();
                                }else
                                if(content.isBlank()){
                                    logger.info(fName+".is blank");
                                    menuGoodTextViewText(isPreview);
                                }else{
                                    logger.info(fName+".content="+content);
                                    int i=Integer.parseInt(content);
                                    logger.info(fName+".i="+i);
                                    if(i>=0&&i< finalArray.length()){
                                        String text= finalArray.getString(i);
                                        menuGoodTextGenerateViewText(text,isPreview);
                                    }else{
                                        menuGoodTextViewText(isPreview);
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
                            sendOrUpdatePrivateEmbed( sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuGoodTextGenerateViewText(String text, boolean isPreview){
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
                                menuGoodTextViewText(isPreview);
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
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
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
                slashReplyPleaseWait();
                if(reqUser!=null&&gMember.getIdLong()!=reqUser.getIdLong()){
                    gTarget=lsMemberHelper.lsGetMember(gGuild,reqUser);
                    if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                }else{
                    if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
                }
                gCurrentInteractionHook=gSlashInteractionHook;
                menuLevels(gTarget,"");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }

    private void userNotificationSuit(int action,String desc){
            String fName="[userNotificationLegCuffs]";
            logger.info(fName+"action="+action);
            logger.info(fName+"desc="+desc);
            try {
                if(gBDSMCommands.restraints.getNotificationDisabled()){
                    logger.warn(fName+"notification disabled");
                    return;
                }
                String field=nSuit;
                if(gNewUserProfile.gUserProfile.jsonObject.has(nNotification)){
                    if(gNewUserProfile.gUserProfile.jsonObject.getBoolean(nNotification)){
                        if(isAdult&&action==actionPutOn){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlOn)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlOn)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOn).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOn);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(isAdult&&action==actionTakeOff){
                            if(gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).has(nImageUrlOff)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).isNull(nImageUrlOff)&&!gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOff).isEmpty()){
                                String url=gNewUserProfile.gUserProfile.jsonObject.getJSONObject(field).getString(nImageUrlOff);
                                EmbedBuilder embed=new EmbedBuilder();embed.setColor(llColorRed_Cinnabar);
                                embed.setImage(url);
                                embed.setDescription(desc);
                                llSendMessageWithDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed);
                            }else{
                                llSendQuickEmbedMessageWithDelete(gGlobal,gMember,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),null,desc,llColorBlue2);
                            }
                        }else
                        if(action==actionStruggle){
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
            }catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }


        void setToyRelease(){
            String fName="[setToyRelease]";
            Logger logger = Logger.getLogger(iRestraints.class);
            try {
                gNewUserProfile.cARMCUFFS.setOn(false);
                gNewUserProfile.cARMCUFFS.setLevel(nNone);
                gNewUserProfile.cLEGCUFFS.setOn(false);
                gNewUserProfile.cLEGCUFFS.setLevel(nNone);
                gNewUserProfile.cHOOD.setOn(false);
                gNewUserProfile.cHOOD.setLevel(nNone);
                gNewUserProfile.cMITTS.setOn(false);
                gNewUserProfile.cMITTS.setLevel(nNone);
                gNewUserProfile.cCOLLAR.setOn(false);
                gNewUserProfile.cCOLLAR.setLevel(nNone);
                gNewUserProfile.cCHASTITY.setOn(false);
                gNewUserProfile.cCHASTITY.setLevel(nNone);
                gNewUserProfile.cGAG.setOn(false);
                gNewUserProfile.cGAG.setLevel(nNone);
                gNewUserProfile.cSUIT.setOn( false);gNewUserProfile.cSUIT.setSuitMatterial(""); gNewUserProfile.cSUIT.setSuitType( "");gNewUserProfile.cSUIT.setSuitFull( false);gNewUserProfile.cSUIT.setSuitTimeLocked(0);gNewUserProfile.cSUIT.setSuitScore(0);gNewUserProfile.cSUIT.setSuitFreeTalkAvailable(0);
                if(gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName())){
                    gNewUserProfile.gUserProfile.putFieldEntry(nAccess,ACCESSLEVELS.Exposed.getName());
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        void set2ToyOmega( Timestamp timestamp, int allowedFreeTalk){
            String fName="[set2ToyOmega]";
            try {
                gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial(SUITMATTERIAL.Toy.getName()); gNewUserProfile.cSUIT.setSuitType(SUITTYPE.ToyOmega);gNewUserProfile.cSUIT.setSuitFull( true);gNewUserProfile.cSUIT.setSuitTimeLocked(timestamp.getTime());gNewUserProfile.cSUIT.setSuitScore(0);gNewUserProfile.cSUIT.setSuitFreeTalkAvailable(allowedFreeTalk);
                gNewUserProfile.cCHASTITY.setOn(true);gNewUserProfile.cCHASTITY.setLevel(CHASTITYLEVELS.Null.getName());gNewUserProfile.cCHASTITY.setShockEnabled(true);
                gNewUserProfile.cGAG.setOn(true);gNewUserProfile.cGAG.setType(GAGTYPES.Dildo);
                if(!gNewUserProfile.cGAG.isValidLevel()){
                    gNewUserProfile.cGAG.setLevel(GAGLEVELS.Loose);
                }
                if(!gNewUserProfile.cHOOD.isOn()){
                    gNewUserProfile.cHOOD.setOn(true);
                    gNewUserProfile.cHOOD.setLevel(HOODLEVELS.Drone);
                }
                if(!gNewUserProfile.cMITTS.isOn()){
                    gNewUserProfile.cMITTS.setOn(true);
                    gNewUserProfile.cMITTS.setLevel(MITTSLEVELS.General);
                }
                if(!gNewUserProfile.cCOLLAR.isOn()){
                    gNewUserProfile.cCOLLAR.setOn(true);
                    gNewUserProfile.cCOLLAR.setLevel(COLLARLEVELS.Rubber);
                }
                if(!gNewUserProfile.cARMCUFFS.isOn()&&!gNewUserProfile.cSTRAITJACKET.isOn()){
                    gNewUserProfile.cARMCUFFS.setOn(true);
                    gNewUserProfile.cARMCUFFS.setLevel(CUFFSARMSLEVELS.Armbinder);
                }
                if(!gNewUserProfile.cLEGCUFFS.isOn()){
                    gNewUserProfile.cLEGCUFFS.setOn(true);
                    gNewUserProfile.cLEGCUFFS.setLevel(CUFFSLEGSLEVELS.Spreaderbar);
                }
                if(! gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName())&&! gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){
                    gNewUserProfile.gUserProfile.putFieldEntry(nAccess,ACCESSLEVELS.Public.getName());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        void set2ToyBeta( Timestamp timestamp, int allowedFreeTalk){
            String fName="[set2ToyBeta]";
            try {
                gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial(SUITMATTERIAL.Toy.getName()); gNewUserProfile.cSUIT.setSuitType(SUITTYPE.ToyBeta);gNewUserProfile.cSUIT.setSuitFull( true);gNewUserProfile.cSUIT.setSuitTimeLocked(timestamp.getTime());gNewUserProfile.cSUIT.setSuitScore(0);gNewUserProfile.cSUIT.setSuitFreeTalkAvailable(allowedFreeTalk);
                gNewUserProfile.cCHASTITY.setOn(true);gNewUserProfile.cCHASTITY.setLevel(CHASTITYLEVELS.Null.getName());gNewUserProfile.cCHASTITY.setShockEnabled(true);
                gNewUserProfile.cGAG.setOn(true);gNewUserProfile.cGAG.setType(GAGTYPES.Dildo);
                if(!gNewUserProfile.cGAG.isValidLevel()){
                    gNewUserProfile.cGAG.setLevel(GAGLEVELS.Loose);
                }
                if(!gNewUserProfile.cHOOD.isOn()){
                    gNewUserProfile.cHOOD.setOn(true);
                    gNewUserProfile.cHOOD.setLevel(HOODLEVELS.Drone);
                }
                if(!gNewUserProfile.cMITTS.isOn()){
                    gNewUserProfile.cMITTS.setOn(true);
                    gNewUserProfile.cMITTS.setLevel(MITTSLEVELS.General);
                }
                if(!gNewUserProfile.cCOLLAR.isOn()){
                    gNewUserProfile.cCOLLAR.setOn(true);
                    gNewUserProfile.cCOLLAR.setLevel(COLLARLEVELS.Rubber);
                }
                if(!gNewUserProfile.cARMCUFFS.isOn()&&!gNewUserProfile.cSTRAITJACKET.isOn()){
                    gNewUserProfile.cARMCUFFS.setOn(true);
                    gNewUserProfile.cARMCUFFS.setLevel(CUFFSARMSLEVELS.Front);
                }
                if(!gNewUserProfile.cLEGCUFFS.isOn()){
                    gNewUserProfile.cLEGCUFFS.setOn(true);
                    gNewUserProfile.cLEGCUFFS.setLevel(CUFFSLEGSLEVELS.Taut);
                }
                if(! gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Public.getName())&&! gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Pet.getName())&&! gNewUserProfile.cAUTH.getAccessAsString().equalsIgnoreCase(ACCESSLEVELS.Protected.getName())){
                    gNewUserProfile.gUserProfile.putFieldEntry(nAccess,ACCESSLEVELS.Exposed.getName());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        void set2ToyAlpha( Timestamp timestamp, int allowedFreeTalk){
            String fName="[set2ToyAlpha]";
            try {
                gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitMatterial(SUITMATTERIAL.Toy.getName()); gNewUserProfile.cSUIT.setSuitType(SUITTYPE.ToyAlpha);gNewUserProfile.cSUIT.setSuitFull( true);gNewUserProfile.cSUIT.setSuitTimeLocked(timestamp.getTime());gNewUserProfile.cSUIT.setSuitScore(0);gNewUserProfile.cSUIT.setSuitFreeTalkAvailable(allowedFreeTalk);
                gNewUserProfile.cCHASTITY.setOn(true);gNewUserProfile.cCHASTITY.setLevel(CHASTITYLEVELS.Null.getName());gNewUserProfile.cCHASTITY.setShockEnabled(true);
                gNewUserProfile.cGAG.setOn(true);gNewUserProfile.cGAG.setType(GAGTYPES.Dildo);
                if(!gNewUserProfile.cGAG.isValidLevel()){
                    gNewUserProfile.cGAG.setLevel(GAGLEVELS.Faux);
                }
                if(!gNewUserProfile.cHOOD.isOn()){
                    gNewUserProfile.cHOOD.setOn(true);
                    gNewUserProfile.cHOOD.setLevel(HOODLEVELS.Drone);
                }
                if(!gNewUserProfile.cMITTS.isOn()){
                    gNewUserProfile.cMITTS.setOn(true);
                    gNewUserProfile.cMITTS.setLevel(MITTSLEVELS.General);
                }
                if(!gNewUserProfile.cCOLLAR.isOn()){
                    gNewUserProfile.cCOLLAR.setOn(true);
                    gNewUserProfile.cCOLLAR.setLevel(COLLARLEVELS.Rubber);
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        void setBitchsuit(){
            String fName="[setBitchsuit]";
            Logger logger = Logger.getLogger(iRestraints.class);
            try {
                gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Bitchsuit);gNewUserProfile.cSUIT.setSuitFull( true);
                gNewUserProfile.cARMCUFFS.setOn(true);gNewUserProfile.cARMCUFFS.setLevel(CUFFSARMSLEVELS.Elbows);
                gNewUserProfile.cLEGCUFFS.setOn(true);gNewUserProfile.cLEGCUFFS.setLevel(CUFFSLEGSLEVELS.HogTie);
                gNewUserProfile.cMITTS.setOn(true);gNewUserProfile.cMITTS.setLevel(MITTSLEVELS.General);
                gNewUserProfile.cSTRAITJACKET.release();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        void setHogsack(){
            String fName="[setHogsack]";
            Logger logger = Logger.getLogger(iRestraints.class);
            try {
                gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Hogsack);gNewUserProfile.cSUIT.setSuitFull( true);
                gNewUserProfile.cARMCUFFS.setOn(true);gNewUserProfile.cARMCUFFS.setLevel(CUFFSARMSLEVELS.FrontBelt);
                gNewUserProfile.cLEGCUFFS.setOn(true);gNewUserProfile.cLEGCUFFS.setLevel(CUFFSLEGSLEVELS.LayBack);
                gNewUserProfile.cMITTS.setOn(true);gNewUserProfile.cMITTS.setLevel(MITTSLEVELS.General);
                gNewUserProfile.cHOOD.setOn(true);gNewUserProfile.cHOOD.setLevel(HOODLEVELS.Bondage);
                gNewUserProfile.cSTRAITJACKET.release();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        void setSleepsack(){
            String fName="[setSleepsack]";
            Logger logger = Logger.getLogger(iRestraints.class);
            try {
                gNewUserProfile.cSUIT.setOn( true);gNewUserProfile.cSUIT.setSuitType(SUITTYPE.Sleepsack);gNewUserProfile.cSUIT.setSuitFull( true);
                gNewUserProfile.cARMCUFFS.setOn(true);gNewUserProfile.cARMCUFFS.setLevel(CUFFSARMSLEVELS.SidesTight);
                gNewUserProfile.cLEGCUFFS.setOn(true);gNewUserProfile.cLEGCUFFS.setLevel(CUFFSLEGSLEVELS.Stand);
                gNewUserProfile.cMITTS.setOn(true);gNewUserProfile.cMITTS.setLevel(MITTSLEVELS.General);
                gNewUserProfile.cHOOD.setOn(true);gNewUserProfile.cHOOD.setLevel(HOODLEVELS.Bondage);
                gNewUserProfile.cSTRAITJACKET.release();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        /*private void putFieldEntry(String field,String name, Object value){
            String fName="[putFieldEntry]";
            logger.info(fName+".field="+ field);
            logger.info(fName+".name="+name);
            logger.info(fName+".value="+value);
            gNewUserProfile.gUserProfile.putFieldEntry(field,name,value);
        }*/


}}
