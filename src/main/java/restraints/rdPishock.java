package restraints;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.discordentities.lcMyEmbedBuilder;
import models.lc.interaction.applicationcommand.lcApplicationInteractionReceive;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.slash.lcINTERACTIONCREATE_Option;
import models.lc.interaction.slash.lcSlashInteractionReceive;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.json.lcText2Json;
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
import restraints.in.*;
import restraints.models.*;
import restraints.models.entity.pishock.entityShocker;
import restraints.models.entity.pishock.entityShockerAction;
import restraints.models.entity.pishock.entityShockerActions;
import restraints.models.enums.ACCESSLEVELS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class rdPishock extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper, iRestraints, iPishock {
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    Logger loggerSuccess = Logger.getLogger(CrunchifyLog4jLevel.PiShockUseSuccess_STR);
    Logger loggerFail = Logger.getLogger(CrunchifyLog4jLevel.PiShockUseFail_STR);
	String sMainRTitle ="Pishock [beta]";
    String gQuickPrefix="pishock",gCommand="pishock";
    public rdPishock(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "PishockCollar-DiscordRestraints";
        this.help = "Connection to internet controlled remote shock collar";
        this.aliases = new String[]{gQuickPrefix,"shockcollar"};
        //quickSummonWithSpace=llPrefixStr+quickAlias+" ";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;
    }
    public rdPishock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, String formawrd, Guild guild, TextChannel textChannel, Member author, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(formawrd,guild,textChannel,author,target);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, GuildMessageReactionAddEvent event){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, lcSlashInteractionReceive ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, lcApplicationInteractionReceive.lUserCommand ev, boolean isForward, String formawrd, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global, InteractionHook interactionHook, boolean isForward, String formawrd){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(interactionHook,isForward,formawrd);
        new Thread(r).start();
    }
    public rdPishock(lcGlobalHelper global,  InteractionHook interactionHook, boolean isForward, String formawrd, Member target){
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
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward);
        }
        public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
            logger.info(".run build");
            launch(gGlobal,ev,isForward,forward,target);
        }
        public runLocal(String formawrd, Guild guild, TextChannel textChannel, Member author, Member target){
            logger.info(".run build");
            launch(gGlobal,formawrd, guild, textChannel, author, target);
        }
        public runLocal(GuildMessageReactionAddEvent ev) {
            logger.info(cName + ".run build");
            launch(gGlobal,ev);
        }
        public runLocal(lcSlashInteractionReceive ev) {
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
        String piWebsiteEmbed ="",piWebsiteUrl="https://pishock.com",piDiscordEmbed="",piDiscordUrl="https://discord.gg/MrNb9CQyYA",piName="PiShock",piAPIWebsiteEmbed="",piAPIWebsiteUrl="https://apidocs.pishock.com";
        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try {
                setTitleStr(rdPishock.this.sMainRTitle);setPrefixStr(rdPishock.this.llPrefixStr);setCommandStr(rdPishock.this.gCommand);
                messageComponentManager.set(gGlobal,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdPishock_commands");
                lsUsefullFunctions.setThreadName4Display("rdPishock");
                if(!checkIFAllowed2UseCommand0()){
                    return;
                }
                piWebsiteEmbed =lsUsefullFunctions.getUrlTextString("**Site**",piWebsiteUrl);
                piDiscordEmbed=lsUsefullFunctions.getUrlTextString("**Discord**",piDiscordUrl);
                piAPIWebsiteEmbed=lsUsefullFunctions.getUrlTextString("**API**",piAPIWebsiteUrl);
                if(gCurrentInteractionHook!=null){
                    if(gTarget==null){
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }
                    menuPiShock(gTarget);
                }else
                if(glUsercommand!=null){
                    logger.info(cName + fName + "lUsercommand@");
                    switch (glUsercommand.getName()){
                        case "rd":
                            menuPiShock(gTarget);
                            break;
                    }
                }
                else if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    rSlashNT();
                }else if(gInteractionCreate!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){
                        return;
                    }
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    int optionsSize=gInteractionCreate.getOptionSize();
                    logger.info(cName + fName + "optionssize="+optionsSize);
                    String inputAction="",inputUser="",inputItensity="",inputDuration="",inputShareCode="";
                    for(int i=0;i<optionsSize;i++){
                        lcINTERACTIONCREATE_Option option=gInteractionCreate.getOption(i);
                        String name=option.getName();
                        String value=option.getValue();
                        logger.info(cName + fName + "option["+i+"]="+name+"|"+value);
                        switch (name) {
                            case "action":
                            case "op":
                                inputAction = value;
                                break;
                            case "user":
                            case "member":
                                inputUser = value;
                                break;
                            case "duration":
                                inputDuration = value;
                                break;
                            case "intensity":
                                inputItensity = value;
                                break;
                            case "code":
                                inputShareCode= value;
                                break;
                        }
                    }
                    Member member=null;
                    if(!inputUser.isBlank()){
                        member=lsMemberHelper.lsGetMemberById(gGuild,inputUser);
                    }

                    if(member==null){
                        slashSelf(inputAction,inputDuration,inputItensity,inputShareCode);
                    }
                    else if(member.getIdLong()==gMember.getIdLong()){
                        slashSelf(inputAction,inputDuration,inputItensity,inputShareCode);
                    }
                    else{
                        slashTargeted(member,inputAction,inputDuration,inputItensity,inputShareCode,false);
                    }
                }
                else if(gCommandEvent!=null){
                    logger.info(cName + fName + "basic@");
                    if(!isAdult&&bdsmRestriction==1){blocked();return;}
                    else if(bdsmRestriction==2){llSendMessageWithDelete(gGlobal,gTextChannel,"This is restricted!");return;}
                    else{

                        boolean isInvalidCommand=true;
                        logger.info(fName+".gIsForward="+gIsForward);
                        if(gIsForward&&(gRawForward.isBlank()||gRawForward.isEmpty())){
                            logger.info(fName+".gRawForward is null");
                            menuPiShock(null);isInvalidCommand=false;
                        }else
                        if(gIsForward&&(gRawForward.equalsIgnoreCase(optionHelp))){
                            logger.info(fName+".gRawForward=help");
                            rHelp("main");
                            isInvalidCommand=false;
                        }else
                        if((!gIsForward&& gArgs.isEmpty())){
                            logger.info(fName+".Args=0");
                            if(!gBasicFeatureControl.getEnable()){
                                logger.info(fName+"its disabled");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                                logger.info(fName+"its not allowed by channel");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This  `rd` sub-command is not allowed in "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                                logger.info(fName+"its not allowed by roles");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"This `rd` sub-command is not allowed with the roles you have!", lsMessageHelper.llColorRed_Cardinal);
                                isInvalidCommand=false;
                            }
                            else {menuPiShock(null);isInvalidCommand=false;}
                        }else {
                            logger.info(fName + ".Args");
                            if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                                gIsOverride =true;
                                gArgs=gArgs.replaceAll(llOverride,"");
                            }
                            if(gArgs.contains("debug")&&lsMemberIsBotOwner(gMember)){
                                gDebugSend =true;
                                gArgs=gArgs.replaceAll("debug","");
                            }
                            gItems = gArgs.split("\\s+");
                            logger.info(fName + ".gRawForward="+gRawForward);
                            logger.info(fName + ".gRawForward:"+gRawForward.isEmpty()+"|"+gRawForward.isBlank());
                            if(!gRawForward.isEmpty()&&!gRawForward.isBlank()){
                                gItems = gRawForward.split("\\s+");
                            }
                            logger.info(fName + ".items.size=" + gItems.length);
                            logger.info(fName + ".items[0]=" + gItems[0]);
                            if(gItems[0].equalsIgnoreCase(optionHelp)){ rHelp("main");isInvalidCommand=false;}
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
                                    menuPiShock(gTarget);isInvalidCommand=false;
                                }else{

                                    if(gItems[1].equalsIgnoreCase(optionHelp)){
                                        rHelp("main");isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(optionEnable)||gItems[1].equalsIgnoreCase(optionDisable)||gItems[1].equalsIgnoreCase(optionReset)){
                                        rPishock(gTarget,gItems[1]);isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(strBeep)){
                                        if(gItems.length>3){
                                            doBeep(gTarget,gItems[2],gItems[3],false);isInvalidCommand=false;
                                        }
                                        else if(gItems.length==3){
                                            doBeep(gTarget,gItems[2],"",false);isInvalidCommand=false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase(strVibrate)){
                                        if(gItems.length>4){
                                            doVibrate(gTarget,gItems[2],gItems[3],gItems[4],false);isInvalidCommand=false;
                                        }
                                        else if(gItems.length==4){
                                            doVibrate(gTarget,gItems[2],gItems[3],"",false);isInvalidCommand=false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase(strShock)){
                                        if(gItems.length>4){
                                            doShock(gTarget,gItems[2],gItems[3],gItems[4],false);isInvalidCommand=false;
                                        }
                                        else if(gItems.length==4){
                                            doShock(gTarget,gItems[2],gItems[3],"",false);isInvalidCommand=false;
                                        }
                                    }
                                    else if((gItems[1].equalsIgnoreCase(optionChastity)||gItems[1].equalsIgnoreCase(optionCollar))&&gItems.length>=3&&((gItems[2].equalsIgnoreCase(optionEnable)||gItems[2].equalsIgnoreCase(optionDisable)))){
                                        rPishock(gItems[1],gItems[2],"");isInvalidCommand=false;
                                    }
                                    else if(gItems[1].equalsIgnoreCase(optionList)&&gItems.length>=4){
                                        String option="",value="";
                                        switch (gItems[2].toLowerCase()){
                                            case "white":
                                                value="white";
                                                break;
                                            case "black":
                                                value="black";
                                                break;
                                            case "both":
                                                value="both";
                                                break;
                                            case "toggle":
                                                value="toggle";
                                                break;
                                        }
                                        switch (gItems[3].toLowerCase()){
                                            case "add":
                                                option="add";
                                                break;
                                            case "remove":
                                                option="remove";
                                                break;
                                            case "clear":
                                                option="clear";
                                                break;
                                            case "mode":
                                                option="mode";
                                                break;
                                        }
                                        if(!option.isBlank()&&!value.isBlank()){
                                            rPishock(gTarget,gItems[1],option,value);isInvalidCommand=false;
                                        }
                                    }
                                    else if(gItems[1].equalsIgnoreCase(commandGame)){
                                       menuGame();
                                    }
                                    else if(gItems[1].equalsIgnoreCase(commandShockers)){
                                        menuShockers();
                                    }
                                }
                                if(isInvalidCommand&&gTarget!=null){
                                    menuPiShock(gTarget);isInvalidCommand=false;
                                }
                            }
                            if(isInvalidCommand){
                                gTarget=null;
                                if(gItems==null||gItems.length==0){
                                    menuPiShock(null);isInvalidCommand=false;
                                }
                                else if(gItems[0].isBlank()&&gIsOverride){
                                    menuPiShock(null);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(optionHelp)){
                                   rHelp("main");isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(optionEnable)||gItems[0].equalsIgnoreCase(optionDisable)||gItems[0].equalsIgnoreCase(optionReset)){
                                   rPishock(gItems[0]);isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(strBeep)){
                                    if(gItems.length>2){
                                        doBeep(gItems[1],gItems[2]);isInvalidCommand=false;
                                    }
                                    else if(gItems.length==2){
                                        doBeep(gItems[1],"");isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[0].equalsIgnoreCase(strVibrate)){
                                    if(gItems.length>3){
                                        doVibrate(gItems[1],gItems[2],gItems[3]);isInvalidCommand=false;
                                    }
                                    else if(gItems.length==3){
                                        doVibrate(gItems[1],gItems[2],"");isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[0].equalsIgnoreCase(strShock)){
                                    if(gItems.length>3){
                                        doShock(gItems[1],gItems[2],gItems[3]);isInvalidCommand=false;
                                    }
                                    else if(gItems.length==3){
                                        doShock(gItems[1],gItems[2],"");isInvalidCommand=false;
                                    }
                                }
                                else if((gItems[0].equalsIgnoreCase(optionChastity)||gItems[0].equalsIgnoreCase(optionCollar))&&gItems.length>=2&&((gItems[1].equalsIgnoreCase(optionEnable)||gItems[1].equalsIgnoreCase(optionDisable)))){
                                   rPishock(gItems[0],gItems[1],"");isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(optionList)&&gItems.length>=3){
                                    String option="",value="";
                                    switch (gItems[1].toLowerCase()){
                                        case "white":
                                            value="white";
                                            break;
                                        case "black":
                                            value="black";
                                            break;
                                        case "both":
                                            value="both";
                                            break;
                                        case "toggle":
                                            value="toggle";
                                            break;
                                    }
                                    switch (gItems[2].toLowerCase()){
                                        case "add":
                                            option="add";
                                            break;
                                        case "remove":
                                            option="remove";
                                            break;
                                        case "clear":
                                            option="clear";
                                            break;
                                        case "mode":
                                            option="mode";
                                            break;
                                    }
                                    if(!option.isBlank()&&!value.isBlank()){
                                        rPishock(gItems[0],option,value);isInvalidCommand=false;
                                    }
                                }
                                else if(gItems[0].equalsIgnoreCase("rps")){
                                   gameRockPaperScisors_1();isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase("hilow")){
                                    gameHiLow_1();isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(commandGame)){
                                    menuGame();isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(keyGame_Roulette)&&gItems.length>=2&&((lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)||lsGlobalHelper.slMemberIsBotOwner(gMember)))){
                                   switch (gItems[1].toLowerCase()){
                                       case "join":
                                           gameRoulette_join();
                                           isInvalidCommand=false;
                                           break;
                                       case "leave":
                                           gameRoulette_leave();
                                           isInvalidCommand=false;
                                           break;
                                       case "reset":
                                           gameRoulette_reset();
                                           isInvalidCommand=false;
                                           break;
                                       case "clear":
                                           gameRoulette_clear();
                                           isInvalidCommand=false;
                                           break;
                                       case "status":
                                           gameRoulette_status();
                                           isInvalidCommand=false;
                                           break;
                                       case "draw":
                                           gameRoulette_draw();
                                           isInvalidCommand=false;
                                           break;
                                   }
                                }
                                else if(gItems[0].equalsIgnoreCase(keyGame_Roulette)){
                                    menuGame_Roulette();isInvalidCommand=false;
                                }
                                else if(gItems[0].equalsIgnoreCase(commandShockers)){
                                    menuShockers();isInvalidCommand=false;
                                }
                                else{
                                    menuPiShock(null);isInvalidCommand=false;
                                }

                            }
                        }
                        //logger.info(fName+".deleting op message");
                        //llQuckCommandMessageDelete(gEvent);
                        if(isInvalidCommand){
                            llSendQuickEmbedMessage(gCommandEvent.getAuthor(),sRTitle,"You provided an incorrect command!", llColorRed);
                        }
                    }
                }
                else{
                    logger.info(cName + fName + "no commandevent@");
                    if(!isAdult&&bdsmRestriction==1){logger.info(cName + fName + "denied1");return;}
                    else if(bdsmRestriction==2){logger.info(cName + fName + "denied2");return;}
                    else if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                    }
                    logger.info(cName + fName + "gRawForward="+gRawForward);
                    if(gTarget!=null){
                        switch (gRawForward){
                            case strRdAction0:
                                doSpecialShocker(gTarget,1, 1);
                                doSpecialShocker(gTarget,2, 1);
                                break;
                            case strRdAction1:
                                doSpecialShocker(gTarget,1, 1);
                                break;
                            case strRdAction2:
                                doSpecialShocker(gTarget,2, 1);
                                break;
                            case strRdAction4:
                                doSpecialShocker(gTarget,1, 2);
                                doSpecialShocker(gTarget,2, 2);
                                break;
                            case strRdAction5:
                                doSpecialShocker(gTarget,1, 2);
                                break;
                            case strRdAction6:
                                doSpecialShocker(gTarget,2, 2);
                                break;
                            case strRdAction8:
                                doSpecialShocker(gTarget,1, 0);
                                doSpecialShocker(gTarget,2, 0);
                                break;
                            case strRdAction9:
                                doSpecialShocker(gTarget,1, 0);
                                break;
                            case strRdAction10:
                                doSpecialShocker(gTarget,2, 0);
                                break;
                            case "game_punish":
                                doGameShocker(gTarget,1);
                                break;
                            default:
                                menuPiShock(gTarget);
                        }
                    }else{
                        switch (gRawForward){
                            case strRdAction0:
                                doSpecialShocker(1, 1);
                                doSpecialShocker(2, 1);
                                break;
                            case strRdAction1:
                                doSpecialShocker(1, 1);
                                break;
                            case strRdAction2:
                                doSpecialShocker(2, 1);
                                break;
                            case strRdAction4:
                                doSpecialShocker(1, 2);
                                doSpecialShocker(2, 2);
                                break;
                            case strRdAction5:
                                doSpecialShocker(1, 2);
                                break;
                            case strRdAction6:
                                doSpecialShocker(2, 2);
                                break;
                            case strRdAction8:
                                doSpecialShocker(1, 0);
                                doSpecialShocker(2, 0);
                                break;
                            case strRdAction9:
                                doSpecialShocker(1, 0);
                                break;
                            case strRdAction10:
                                doSpecialShocker(2, 0);
                                break;
                            default:
                                menuPiShock(null);
                        }
                    }
                    postHTTPErrorMessages(gTextChannel);

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
        String quickSummonWithSpace=llPrefixStr+gQuickPrefix+" <@Pet> ";
        String newLine="\n  `", spacingDual="` , `" , endLine="`";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
        embed.addField(strWhatIs,"The PiShock is an internet controlled remote shocking device that can be used as a shock collar.",false);
        embed.addField(strSiteDiscord,"For more information about the product visit "+piWebsiteEmbed+" or "+piDiscordEmbed+".",false);
        desc="Custom triggerd command:";
        desc+="\n`"+quickSummonWithSpace+strBeep+strMenu4a;
        desc+="\n`"+quickSummonWithSpace+strVibrate+strMenu4b;
        desc+="\n`"+quickSummonWithSpace+strShock+strMenu4c;
        desc+="\n--<@Pet> is a mentioned member if targeting somebody else and not yourself\n--<duration> is an integer between 1-15, representing seconds\n--<intensity> an integer between 1-100 represinting vibration or shock intensity\n--<code> optional string, representing shocker sharecode, using that instead the selected one. ";
        embed.addField("Custom Triggers",desc,false);
        desc="`"+quickSummonWithSpace+"<@Pet> enable|disable` enabled or disables PiShock";
        desc+="\n`"+quickSummonWithSpace+"reset` reset PiShock entry";
        embed.addField("Othere commands",desc,false);
        desc="";
        if(lsMemberHelper.lsMemberIsManager(gMember)){
            embed.addField("Slash command","To add/remobe slash comand go to  `"+llPrefixStr +"slash`.",false);
            embed.addField("Server sub-options","Type `"+llPrefixStr + quickAlias+" server` for managing this command.",false);
        }
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs",secondsBeforeDelete);
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
        private void menuSelect() {
            String fName = "[menuSelect]";
            try{
                logger.info(fName+"isMenuLevel= "+isMenuLevel+", indexMenuLevel="+indexMenuLevel+", optionMenuLevel="+optionMenuLevel);
                if(isMenuLevel){
                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                        switch (indexMenuLevel){
                            case 1:
                                menuPiShock_Wearer(0);
                                break;
                            case 2:
                                menuShockers_Wearer();
                                break;
                            case 3:
                                menuShockerWearer();
                                break;
                            case 4:
                                menuSpecialShockerWearer(optionMenuLevel);
                                break;
                            case 5:
                                menuListWearer(optionMenuLevel);
                                break;
                        }
                    }else{
                        if(gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                            switch (indexMenuLevel){
                                case 1:
                                    menuPiShock_Owner(0);
                                    break;
                                case 2:
                                    menuShockers_Owner();
                                    break;
                                case 3:
                                    menuShockerOwner();
                                    break;
                                case 4:
                                    menuSpecialShockerOwner(optionMenuLevel);
                                    break;
                                case 5:
                                    menuListOwner(optionMenuLevel);
                                    break;
                            }
                        }else{
                            switch (indexMenuLevel){
                                case 1:
                                    menuPiShock_Somebody(0);
                                    break;
                                case 2:
                                    menuShockers_Somebody();
                                    break;
                                case 3:
                                    menuPiShock_Somebody(0);
                                    break;
                                case 4:
                                    menuSpecialShockerSomebody(optionMenuLevel);
                                    break;
                                case 5:
                                    menuPiShock_Somebody(0);
                                    break;
                            }

                        }
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
    private void rPishock(String command) {
        String fName = "[rPishock]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
            logger.info(fName + ".can't use do to access owner");
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Pet), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
            logger.info(fName + ".can't use do to access public");
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Public), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
            logger.info(fName + ".can't while the jacket is on and hands strapped");
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantJacket1), llColorRed);
            llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantJacket2), llColorRed);
            return;
        }else
        if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
            logger.info(fName + ".can't while the mittens are on");
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantMitts1), llColorRed);
            llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantMitts2), llColorRed);
            return;
        }
        if(command.equalsIgnoreCase(optionEnable)){
            gNewUserProfile.cPISHOCK.setEnabled(true);
            saveProfile();
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strEnabled), llColorGreen2);

        }
        if(command.equalsIgnoreCase(optionDisable)){
            gNewUserProfile.cPISHOCK.setEnabled(false);
            saveProfile();
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strDisabled), llColorPurple2);
        }
        if(command.equalsIgnoreCase(optionReset)){
            reset();
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strReset), llColorPurple2);
        }
        menuSelect();
    }
        private void rPishock(String command,String option,String value) {
            String fName = "[rPishock]";
            logger.info(fName);
            logger.info(fName + ".command=" + command+", option="+option+", value="+value);
            if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
            if(command.equalsIgnoreCase(optionList)){

                if(option.equalsIgnoreCase("mode")){
                    if(value.equalsIgnoreCase("toggle")){
                        switch ( gNewUserProfile.cPISHOCK.getFieldMode()) {
                            case 1:
                                gNewUserProfile.cPISHOCK. setFieldMode( 2);
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSellected2), llColorBlue2);
                                break;
                            case 2:
                                gNewUserProfile.cPISHOCK. setFieldMode( 0);
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected0), llColorBlue2);
                                break;
                            default:
                                gNewUserProfile.cPISHOCK. setFieldMode( 1);
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected1), llColorBlue2);
                                break;
                        }
                        saveProfile();
                    }
                    if(value.equalsIgnoreCase("white")){
                        gNewUserProfile.cPISHOCK. setFieldMode(2);
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSellected2), llColorBlue2);
                        saveProfile();
                    }
                    if(value.equalsIgnoreCase("black")){
                        gNewUserProfile.cPISHOCK. setFieldMode(1);
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected1), llColorBlue2);
                        saveProfile();
                    }
                    if(value.equalsIgnoreCase("both")){
                        gNewUserProfile.cPISHOCK. setFieldMode(0);
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected0), llColorBlue2);
                        saveProfile();
                    }
                }
                else{
                    List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
                    isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                    isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                    logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                    boolean updated=false,found=false;
                    if(option.equalsIgnoreCase("add")&&value.equalsIgnoreCase("white")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(isPatreon||isPatreonGuild){
                            if(WHITELISTMMEMBERS.length()>=maxMembers){
                                logger.info(fName + ".max reached1");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReached), llColorRed);
                                return;
                            }
                        }else{
                            if(WHITELISTMMEMBERS.length()>=normalLimitMembers){
                                logger.info(fName + ".max reached2");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReachedNoPatreon), llColorRed);
                                return;
                            }
                        }
                        for(Member member:members){
                            found=false;
                            for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                                if(WHITELISTMMEMBERS.getLong(i)==member.getIdLong()){
                                    found=true;
                                    break;
                                }
                            }
                            if(!found){
                                WHITELISTMMEMBERS.put(member.getIdLong());
                                updated=true;
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersWhiteList(WHITELISTMMEMBERS);
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("remove")&&value.equalsIgnoreCase("white")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(WHITELISTMMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        for(Member member:members){
                            for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                                if(WHITELISTMMEMBERS.getLong(i)==member.getIdLong()){
                                    WHITELISTMMEMBERS.remove(i);updated=true;
                                    break;
                                }
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersWhiteList(WHITELISTMMEMBERS);
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("clear")&&value.equalsIgnoreCase("white")){
                        if(WHITELISTMMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        gNewUserProfile.cPISHOCK.setMembersWhiteList(new JSONArray());
                        saveProfile();
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListCleared), llColorGreen1);
                        if(isMenuLevel){
                            menuList(1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("add")&&value.equalsIgnoreCase("black")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(isPatreon||isPatreonGuild){
                            if(BLACKLISTMEMBERS.length()>=maxMembers){
                                logger.info(fName + ".max reached1");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReached), llColorRed);
                                return;
                            }
                        }else{
                            if(BLACKLISTMEMBERS.length()>=normalLimitMembers){
                                logger.info(fName + ".max reached2");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReachedNoPatreon), llColorRed);
                                return;
                            }
                        }
                        for(Member member:members){
                            found=false;
                            for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                                if(BLACKLISTMEMBERS.getLong(i)==member.getIdLong()){
                                    found=true;
                                    break;
                                }
                            }
                            if(!found){
                                BLACKLISTMEMBERS.put(member.getIdLong());
                                updated=true;
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersBlackList(BLACKLISTMEMBERS);
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(-1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("remove")&&value.equalsIgnoreCase("black")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(BLACKLISTMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        for(Member member:members){
                            for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                                if(BLACKLISTMEMBERS.getLong(i)==member.getIdLong()){
                                    BLACKLISTMEMBERS.remove(i);
                                    break;
                                }
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersBlackList(BLACKLISTMEMBERS);
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(-1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("clear")&&value.equalsIgnoreCase("black")){
                        if(BLACKLISTMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        gNewUserProfile.cPISHOCK.setMembersBlackList(new JSONArray());
                        saveProfile();
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListCleared), llColorGreen1);
                        if(isMenuLevel){
                            menuList(-1);return;
                        }
                    }
                }

            }
            else if(command.equalsIgnoreCase("listremove")){
                boolean updated=false,found=false;
                if(option.equalsIgnoreCase("white")){
                    if(WHITELISTMMEMBERS.isEmpty()){
                        logger.info(fName + ".list is empty");
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                        return;
                    }
                    for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                        if(WHITELISTMMEMBERS.getLong(i)==Long.valueOf(value)){
                            WHITELISTMMEMBERS.remove(i);
                            updated=true;
                            break;
                        }
                    }
                    if(updated){
                        gNewUserProfile.cPISHOCK.setMembersWhiteList(WHITELISTMMEMBERS);
                        saveProfile();
                    }
                    if(isMenuLevel){
                        menuList(1);return;
                    }
                }
                if(option.equalsIgnoreCase("black")){
                    if(BLACKLISTMEMBERS.isEmpty()){
                        logger.info(fName + ".list is empty");
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                        return;
                    }
                    for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                        if(BLACKLISTMEMBERS.getLong(i)==Long.valueOf(value)){
                            BLACKLISTMEMBERS.remove(i);
                            break;
                        }
                    }
                    if(updated){
                        gNewUserProfile.cPISHOCK.setMembersBlackList(BLACKLISTMEMBERS);
                        saveProfile();
                    }
                    if(isMenuLevel){
                        menuList(-1);return;
                    }
                }
            }
            else if(command.equalsIgnoreCase(optionCollar)){
                if(option.equalsIgnoreCase(optionEnable)){
                    gNewUserProfile.cPISHOCK.setShock4CollarEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCollarShockerEnabled), llColorPurple2);
                }
                else if(option.equalsIgnoreCase(optionDisable)){
                    gNewUserProfile.cPISHOCK.setShock4CollarEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCollarShockerDisabled), llColorPurple2);
                }
            }
            else if(command.equalsIgnoreCase(optionChastity)){
                if(option.equalsIgnoreCase(optionEnable)){
                    gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strChastityShockerEnabled), llColorPurple2);
                }
                else if(option.equalsIgnoreCase(optionDisable)){
                    gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCChastityShockerDisabled), llColorPurple2);
                }
            }
            menuSelect();

        }

   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void rPishock(Member mtarget, String command) {
        String fName = "[rPishock]";
        logger.info(fName);
        User target=mtarget.getUser();
        logger.info(fName + ".target:"+target.getId()+"|"+target.getName());
        logger.info(fName + ".command="+command);
        if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
        if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
            logger.info(fName + ".can't use do to not owner or secowner");
            llSendQuickEmbedMessage(gUser,sRTitle,"Only their owner or sec-owner has access", llColorRed);
            return;
        }
        if(command.equalsIgnoreCase(optionEnable)){
            gNewUserProfile.cPISHOCK.setEnabled(true);
            saveProfile();
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strTargetEnabled1), llColorGreen2);
            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strTargetEnabled2), llColorGreen2);
        }
        if(command.equalsIgnoreCase(optionDisable)){
            gNewUserProfile.cPISHOCK.setEnabled(false);
            saveProfile();
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strTargetDisabled1), llColorPurple2);
            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strTargetDisabled2), llColorPurple2);
        }
        if(command.equalsIgnoreCase(optionReset)){
            reset();
            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strTargetReset1), llColorPurple2);
            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strTargetReset2), llColorPurple2);
        }
        menuSelect();
    }
        private void rPishock(Member mtarget,String command,String option,String value) {
            String fName = "[rPishock]";
            logger.info(fName);
            logger.info(fName + ".command=" + command+", option="+option+", value="+value);
            if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
            if(!gIsOverride&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                logger.info(fName + ".can't use do to not owner or secowner");
                llSendQuickEmbedMessage(gUser,sRTitle,"Only their owner or sec-owner has access", llColorRed);
                return;
            }
            if(command.equalsIgnoreCase(optionList)){
                if(option.equalsIgnoreCase("mode")){
                    if(value.equalsIgnoreCase("toggle")){
                        switch ( gNewUserProfile.cPISHOCK.getFieldMode()) {
                            case 1:
                                gNewUserProfile.cPISHOCK. setFieldMode( 2);
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSellected2), llColorBlue2);
                                break;
                            case 2:
                                gNewUserProfile.cPISHOCK. setFieldMode( 0);
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected0), llColorBlue2);
                                break;
                            default:
                                gNewUserProfile.cPISHOCK. setFieldMode( 1);
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected1), llColorBlue2);
                                break;
                        }
                        
                        saveProfile();
                    }
                    if(value.equalsIgnoreCase("white")){
                        gNewUserProfile.cPISHOCK. setFieldMode(2);
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSellected2), llColorBlue2);
                        
                        saveProfile();
                    }
                    if(value.equalsIgnoreCase("black")){
                        gNewUserProfile.cPISHOCK. setFieldMode(1);
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected1), llColorBlue2);
                        
                        saveProfile();
                    }
                    if(value.equalsIgnoreCase("both")){
                        gNewUserProfile.cPISHOCK. setFieldMode(0);
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListModeSelected0), llColorBlue2);
                        
                        saveProfile();
                    }
                }
                else{
                    List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
                    isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);
                    isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                    logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                    boolean updated=false,found=false;
                    if(option.equalsIgnoreCase("add")&&value.equalsIgnoreCase("white")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(isPatreon||isPatreonGuild){
                            if(WHITELISTMMEMBERS.length()>=maxMembers){
                                logger.info(fName + ".max reached1");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReached), llColorRed);
                                return;
                            }
                        }else{
                            if(WHITELISTMMEMBERS.length()>=normalLimitMembers){
                                logger.info(fName + ".max reached2");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReachedNoPatreon), llColorRed);
                                return;
                            }
                        }
                        for(Member member:members){
                            found=false;
                            for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                                if(WHITELISTMMEMBERS.getLong(i)==member.getIdLong()){
                                    found=true;
                                    break;
                                }
                            }
                            if(!found){
                                WHITELISTMMEMBERS.put(member.getIdLong());
                                updated=true;
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersWhiteList(WHITELISTMMEMBERS);
                            
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("remove")&&value.equalsIgnoreCase("white")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(WHITELISTMMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        for(Member member:members){
                            for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                                if(WHITELISTMMEMBERS.getLong(i)==member.getIdLong()){
                                    WHITELISTMMEMBERS.remove(i);
                                    updated=true;
                                    break;
                                }
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersWhiteList(WHITELISTMMEMBERS);
                            
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("clear")&&value.equalsIgnoreCase("white")){
                        if(WHITELISTMMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        gNewUserProfile.cPISHOCK.setMembersWhiteList(new JSONArray());
                        
                        saveProfile();
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strWhiteListCleared), llColorGreen1);
                        if(isMenuLevel){
                            menuList(1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("add")&&value.equalsIgnoreCase("black")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(isPatreon||isPatreonGuild){
                            if(BLACKLISTMEMBERS.length()>=maxMembers){
                                logger.info(fName + ".max reached1");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReached), llColorRed);
                                return;
                            }
                        }else{
                            if(BLACKLISTMEMBERS.length()>=normalLimitMembers){
                                logger.info(fName + ".max reached2");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListMaxReachedNoPatreon), llColorRed);
                                return;
                            }
                        }
                        for(Member member:members){
                            found=false;
                            for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                                if(BLACKLISTMEMBERS.getLong(i)==member.getIdLong()){
                                    found=true;
                                    break;
                                }
                            }
                            if(!found){
                                BLACKLISTMEMBERS.put(member.getIdLong());
                                updated=true;
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersBlackList(BLACKLISTMEMBERS);
                            
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(-1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("remove")&&value.equalsIgnoreCase("black")){
                        if(members.isEmpty()){
                            logger.info(fName + ".no mentioned members");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strNoMentionedMembers), llColorRed);
                            return;
                        }
                        if(BLACKLISTMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        for(Member member:members){
                            for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                                if(BLACKLISTMEMBERS.getLong(i)==member.getIdLong()){
                                    BLACKLISTMEMBERS.remove(i);
                                    updated=true;
                                    break;
                                }
                            }
                        }
                        if(updated){
                            gNewUserProfile.cPISHOCK.setMembersBlackList(BLACKLISTMEMBERS);
                            
                            saveProfile();
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListUpdated), llColorGreen1);
                        }else{
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListFailed2Update), llColorRed_Barn);
                        }
                        if(isMenuLevel){
                            menuList(-1);return;
                        }
                    }
                    if(option.equalsIgnoreCase("clear")&&value.equalsIgnoreCase("black")){
                        if(BLACKLISTMEMBERS.isEmpty()){
                            logger.info(fName + ".list is empty");
                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                            return;
                        }
                        gNewUserProfile.cPISHOCK.setMembersBlackList(new JSONArray());
                        
                        saveProfile();
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strBlackListCleared), llColorGreen1);
                        if(isMenuLevel){
                            menuList(-1);return;
                        }
                    }
                }
            }
            if(command.equalsIgnoreCase("listremove")){
                boolean updated=false,found=false;
                if(option.equalsIgnoreCase("white")){
                    if(WHITELISTMMEMBERS.isEmpty()){
                        logger.info(fName + ".list is empty");
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                        return;
                    }
                    for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                        if(WHITELISTMMEMBERS.getLong(i)==Long.valueOf(value)){
                            WHITELISTMMEMBERS.remove(i);
                            break;
                        }
                    }
                    if(updated){
                        gNewUserProfile.cPISHOCK.setMembersWhiteList(WHITELISTMMEMBERS);
                        
                        saveProfile();
                    }
                    if(isMenuLevel){
                        menuList(1);return;
                    }
                }
                if(option.equalsIgnoreCase("black")){
                    if(BLACKLISTMEMBERS.isEmpty()){
                        logger.info(fName + ".list is empty");
                        llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strListIsEmpty), llColorRed);
                        return;
                    }
                    for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                        if(BLACKLISTMEMBERS.getLong(i)==Long.valueOf(value)){
                            BLACKLISTMEMBERS.remove(i);
                            updated=true;
                            break;
                        }
                    }
                    if(updated){
                        gNewUserProfile.cPISHOCK.setMembersBlackList(BLACKLISTMEMBERS);
                        saveProfile();
                    }
                    if(isMenuLevel){
                        menuList(-1);return;
                    }
                }
            }
            else if(command.equalsIgnoreCase(optionCollar)){
                if(option.equalsIgnoreCase(optionEnable)){
                    gNewUserProfile.cPISHOCK.setShock4CollarEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCollarShockerEnabled), llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strCollarShockerEnabled), llColorPurple2);
                }
                else if(option.equalsIgnoreCase(optionEnable)){
                    gNewUserProfile.cPISHOCK.setShock4CollarEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCollarShockerDisabled), llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strCollarShockerDisabled), llColorPurple2);
                }
            }
            else if(command.equalsIgnoreCase(optionChastity)){
                if(option.equalsIgnoreCase(optionEnable)){
                    gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strChastityShockerEnabled), llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strChastityShockerEnabled), llColorPurple2);
                }
                else if(option.equalsIgnoreCase(optionEnable)){
                    gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(false);
                    saveProfile();
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCChastityShockerDisabled), llColorPurple2);
                    llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer(iPishock.strCChastityShockerDisabled), llColorPurple2);
                }
            }
            menuSelect();
        }
        

   boolean isMenuLevel=false,isPatreon=false,isPatreonGuild=false, isWearerHandsRestrained =false, isWearerAccessLimited =false;int indexMenuLevel=0,optionMenuLevel=0;

        private void menuPiShock(Member mtarget){
            String fName="[menuPiShock]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(mtarget!=null){
                        if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                getShockers();getDefaultShoker();
                if(gMember.getIdLong()==gNewUserProfile.getMember().getIdLong()){
                    menuPiShock_Wearer(0);
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuPiShock_Owner(0);
                    }else{
                        if(iRestraints.isArmsRestrained(gGlobal,gMember)){
                            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                            }else{
                                logger.info(fName + ".default message");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                            }
                            return;
                        }
                        menuPiShock_Somebody(0);
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        String gCommandsPath =gFileMainPath+"pishock/";
        String gCommandFileMainPath =gCommandsPath+"menuMain.json";
        private void menuPiShock_Wearer(int page){
            String fName="[menuPiShock_Wearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(sRTitle);
                isMenuLevel=true;indexMenuLevel=1;
                isWearerHandsRestrained =false;isWearerAccessLimited=false;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                desc2="";desc="";String code="";
                boolean validShocker=false,action1=false,action2=false,action3=false;
                switch (page){
                    case 0:
                        embed.addField(strWhatIs,strWhatIsDetails,false);
                        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
                        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" to access shockers menu.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+"game menu";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" to select shocker.";
                        embed.addField("Other menus&options",desc,false);
                        desc=""; desc3= new StringBuilder();
                        if(SHOCKER_INDEX==-2){
                            embed.addField("Shocker","Invalid shocker selected!",false);embed.setColor(llColorRed_Barn);
                        }else
                        if(SHOCKER_INDEX<0){
                            embed.addField("Shocker","None selected!",false);
                        }
                        else{
                            logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                            logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            try{

                                if(!cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                                code=cSHOCKER.getCode();
                                if(code!=null&&!code.isBlank()){
                                    desc+="\ncode: "+code;
                                }else{
                                    desc+="\ncode: <null>";
                                }
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nActions:"+desc3.toString();
                                    action1=true;
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nActions:(null)";
                                }
                                embed.addField("Shocker",desc,false);validShocker=true;
                                desc="Uses main shocker" ;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+strMenu2b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+strMenu3b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+strMenu5b;
                                embed.addField("Quick Triggers",desc,false);
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                embed.addField("Main Shocker","(null)",false);
                            }
                        }
                        desc="\n`"+llPrefixStr+gQuickPrefix+" "+strBeep+strMenu4a;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strVibrate+strMenu4b;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strShock+strMenu4c;
                        desc+=strMenu4d;
                        embed.addField("Custom Triggers",desc,false);
                        if(lsMemberHelper.lsMemberIsManager(gMember)){
                            embed.addField("Slash command","To add/remobe slash comand go to  `"+llPrefixStr +"slash`.",false);
                        }
                        break;
                    case 1:
                        if(gNewUserProfile.cPISHOCK.isEnabled()){
                            desc="Enabled:true\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                        }else{
                            desc="Enabled:false\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                        }
                        embed.addField("Status",desc,false);
                        desc="Linking shockers to rd's collar and chastity";
                        if(hasCollarShocker){
                            try{
                                desc+="\n**collar**";
                                desc+="\ncode: "+cSHOCKER_COLLAR.getCode();
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError collar shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to collar, default to main one";
                        }
                        if(!gNewUserProfile.cCOLLAR.isOn()){
                            if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                                desc+="\nCollar on, shock enabled";
                            }else{
                                desc+="\nCollar on, shock disabled";
                            }
                        }else{
                            desc+="\nCollar off";
                        }
                        if(hasChastityShocker){
                            try{
                                desc+="\n**chastity**";
                                desc+="\ncode: "+cSHOCKER_CHASTITY.getCode();
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError chastity shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to chastity, default to main one";
                        }
                        if(gNewUserProfile.cCHASTITY.isOn()){
                            if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                                desc+="\nChastity on, shock enabled";
                            }else{
                                desc+="\nChastity on, shock disabled";
                            }
                        }else{
                            desc+="\nChastity off";
                        }
                        embed.addField("Specific Linking",desc,false);
                        break;
                    case 2:
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" whitelist managing";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle)+" blacklist managing";
                        switch (gNewUserProfile.cPISHOCK.getFieldMode()){
                            case 1:
                                desc2="black";break;
                            case 2:
                                desc2="white";break;
                            default:
                                desc2="both";break;
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare)+" switch mode, currently: "+desc2;
                        embed.addField("White&Black Listing",desc,false);
                        embed.addField(strBetaTitle,strBetaDescription,false);
                        embed.addField(strDisclaim,strDisclaimMore,false);
                        break;
                    default:
                        if(gNewUserProfile.cPISHOCK.isEnabled()){
                            desc="Enabled:true\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                        }else{
                            desc="Enabled:false\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                        }
                        embed.addField("Status",desc,false);
                        desc="";
                        desc=""; desc3= new StringBuilder();

                        if(SHOCKER_INDEX==-2){
                            embed.addField("Main Shocker","Invalid shocker selected!",false);embed.setColor(llColorRed_Barn);
                        }else
                        if(SHOCKER_INDEX<0){
                            embed.addField("Main Shocker","None selected!",false);
                        }
                        else{
                            logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                            logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            try{

                                if(!cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                                code=cSHOCKER.getCode();
                                if(code!=null&&!code.isBlank()){
                                    desc+="\ncode: "+code;
                                }else{
                                    desc+="\ncode: <null>";
                                }
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nActions:"+desc3.toString();
                                    action1=true;
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nActions:(null)";
                                }
                                embed.addField("Shocker",desc,false);validShocker=true;
                                desc="Uses this shocker for" ;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+strMenu2b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+strMenu3b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+strMenu5b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRocket)+strMenu6b;
                                embed.addField("Quick Triggers",desc,false);
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                embed.addField("Main Shocker","(null)",false);
                            }
                        }
                        desc="Custom trigger command:";
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strBeep+strMenu4a;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strVibrate+strMenu4b;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strShock+strMenu4c;
                        desc+=strMenu4d;
                        embed.addField("Custom Triggers",desc,false);

                        desc="Linking shockers to rd's collar and chastity";
                        if(hasCollarShocker){
                            try{
                                desc+="\n**collar**";
                                desc+="\ncode: "+cSHOCKER_COLLAR.getCode();
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError collar shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to collar";
                        }
                        if(hasChastityShocker){
                            try{
                                desc+="\n**chastity**";
                                desc+="\ncode: "+cSHOCKER_CHASTITY.getCode();
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError chastity shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to chastity";
                        }
                        embed.addField("Specific Linking",desc,false);
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" whitelist managing";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle)+" blacklist managing";
                        switch (gNewUserProfile.cPISHOCK.getFieldMode()){
                            case 1:
                                desc2="black";break;
                            case 2:
                                desc2="white";break;
                            default:
                                desc2="both";break;
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare)+" switch mode, currently: "+desc2;
                        embed.addField("White&Black Listing",desc,false);
                        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" to access shockers menu.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+"to quickly trigger i shocker action.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+"game menu";
                        embed.addField("Extra options",desc,false);
                        embed.addField(strBetaTitle,strBetaDescription,false);
                        embed.addField(strDisclaim,strDisclaimMore,false);
                }
                if(page!=2){
                    if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                        logger.info(fName + ".can't use do to access public");
                        embed.addField("Hands tied up",stringReplacer(iPishock.strCantSet2Public),false);
                        isWearerHandsRestrained =true;
                    }else
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                        logger.info(fName + ".can't while the jacket is on and hands strapped");
                        embed.addField("Hands tied up",stringReplacer(iPishock.strCantJacket1),false);
                        isWearerHandsRestrained =true;
                    }else
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                        logger.info(fName + ".can't while the mittens are on");
                        embed.addField("Hands tied up",stringReplacer(iPishock.strCantMitts1),false);
                        isWearerHandsRestrained =true;
                    }
                    if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".is set to owner access");
                        embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                        isWearerAccessLimited =true;
                    }else
                    if(!gIsOverride&&isAccess2Public(gNewUserProfile.gUserProfile)){
                        logger.info(fName + ".is set to public access");
                        embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                        isWearerAccessLimited =true;
                    }
                }
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                /*if(page>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                }
                if(page<2){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                }
                if(gNewUserProfile.cPISHOCK.isEnabled()){
                    if(!isWearerAccessLimited)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }

                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS));
                if(!isWearerHandsRestrained){
                    if(validShocker){
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap));
                    }
                    if(SHOCKERS_COUNT>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                    if(SHOCKERS_COUNT>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(SHOCKERS_COUNT>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(SHOCKERS_COUNT>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    if(SHOCKERS_COUNT>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    if(SHOCKERS_COUNT>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                    if(SHOCKERS_COUNT>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                    if(SHOCKERS_COUNT>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                    if(SHOCKERS_COUNT>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                    if(SHOCKERS_COUNT>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle));

                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare));

                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    lcMessageBuildComponent component3= messageComponentManager.messageBuildComponents.getComponent(3);
                    lcMessageBuildComponent component4= messageComponentManager.messageBuildComponents.getComponent(4);
                    if(!gNewUserProfile.cPISHOCK.isEnabled()){
                        component0.getButtonById("!enable/disable").setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel("🟢 Enable");
                        component0.getButtonById("game_die").setDisable();
                        component2.getButtonById("bell").setDisable();
                        component2.getButtonById("vibration_mode").setDisable();
                        component2.getButtonById("zap").setDisable();
                    }else{
                        component0.getButtonById("!enable/disable").setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel("🔴 Disable");
                    }
                    if(SHOCKERS_COUNT==0){
                        component2.setIgnored();
                        for(int i2=0;i2<10;i2++){
                            component1.getSelect().getOptionAt(i2).setIgnored();
                        }
                    }else{
                        for(int i1=0;i1<10;i1++){
                            if(i1<SHOCKERS_COUNT){
                                lcMessageBuildComponent.SelectMenu.Option option=component1.getSelect().getOptionAt(i1);
                                option.setLabel(option.getLabel()+gNewUserProfile.cPISHOCK.getShocker(i1).getName());
                            }else{
                                component1.getSelect().getOptionAt(i1).setIgnored();
                            }
                        }
                        if(SHOCKER_INDEX>-1){
                            component1.getSelect().getOptionAt(SHOCKER_INDEX).setDefault();
                        }
                        if(SHOCKERS_COUNT>=10){
                            component1.getSelect().getOptionAt(11).setIgnored();
                        }
                    }
                    if(page==0){
                        component4.getButtonById("arrow_backward").setDisable();
                    }else
                    if(page==2){
                        component4.getButtonById("arrow_forward").setDisable();
                    }
                    switch (page){
                        case 0: case 1:
                            component3.setIgnored();
                            break;
                        case 2:
                            component1.setIgnored();
                            component2.setIgnored();
                            break;
                    }

                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuPiShockListener_Wearer(message,page);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuPiShock_Owner(int page){
            String fName="[menuPiShock_Somebody]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle);
                isMenuLevel=true;indexMenuLevel=1;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                desc2="";desc="";String code="";
                boolean validShocker=false,action1=false,action2=false,action3=false;

                switch (page){
                    case 0:
                        embed.addField(strWhatIs,strWhatIsDetails,false);
                        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
                        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" to access shockers menu.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+"game menu";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" to select shocker.";
                        embed.addField("Other menus&options",desc,false);
                        desc=""; desc3= new StringBuilder();
                        if(SHOCKER_INDEX==-2){
                            embed.addField("Shocker","Invalid shocker selected!",false);embed.setColor(llColorRed_Barn);
                        }else
                        if(SHOCKER_INDEX<0){
                            embed.addField("Shocker","None selected!",false);
                        }
                        else{
                            logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                            logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            try{

                                if(!cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                                code=cSHOCKER.getCode();
                                if(code!=null&&!code.isBlank()){
                                    desc+="\ncode: (hiden)";
                                }else{
                                    desc+="\ncode: <null>";
                                }
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nActions:"+desc3.toString();
                                    action1=true;
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nActions:(null)";
                                }
                                embed.addField("Shocker",desc,false);validShocker=true;
                                desc="Uses this shocker for" ;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+strMenu2b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+strMenu3b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+strMenu5b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRocket)+strMenu6b;
                                embed.addField("Quick Triggers",desc,false);
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                embed.addField("Main Shocker","(null)",false);
                            }
                        }
                        desc="\n`"+llPrefixStr+gQuickPrefix+" "+strBeep+strMenu4a;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strVibrate+strMenu4b;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strShock+strMenu4c;
                        desc+=strMenu4d;
                        embed.addField("Custom Triggers",desc,false);
                        if(lsMemberHelper.lsMemberIsManager(gMember)){
                            embed.addField("Slash command","To add/remobe slash comand go to  `"+llPrefixStr +"slash`.",false);
                        }
                        break;
                    case 1:
                        if(gNewUserProfile.cPISHOCK.isEnabled()){
                            desc="Enabled:true\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                        }else{
                            desc="Enabled:false\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                        }
                        embed.addField("Status",desc,false);
                        desc="Linking shockers to rd's collar and chastity";
                        if(hasCollarShocker){
                            try{
                                desc+="\n**collar**";
                                desc+="\ncode: (hidden)";
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError collar shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to collar, default to main one";
                        }
                        if(gNewUserProfile.cCOLLAR.isOn()){
                            if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                                desc+="\nCollar on, shock enabled";
                            }else{
                                desc+="\nCollar on, shock disabled";
                            }
                        }else{
                            desc+="\nCollar off";
                        }
                        if(hasChastityShocker){
                            try{
                                desc+="\n**chastity**";
                                desc+="\ncode: (hidden)";
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError chastity shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to chastity, default to main one";
                        }
                        if(gNewUserProfile.cCHASTITY.isOn()){
                            if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                                desc+="\nChastity on, shock enabled";
                            }else{
                                desc+="\nChastity on, shock disabled";
                            }
                        }else{
                            desc+="\nChastity off";
                        }
                        embed.addField("Specific Linking",desc,false);
                        break;
                    case 2:
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" whitelist managing";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle)+" blacklist managing";
                        switch (gNewUserProfile.cPISHOCK.getFieldMode()){
                            case 1:
                                desc2="black";break;
                            case 2:
                                desc2="white";break;
                            default:
                                desc2="both";break;
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare)+" switch mode, currently: "+desc2;
                        embed.addField("White&Black Listing",desc,false);
                        embed.addField(strBetaTitle,strBetaDescription,false);
                        embed.addField(strDisclaim,strDisclaimMore,false);
                        break;
                    default:
                        if(gNewUserProfile.cPISHOCK.isEnabled()){
                            desc="Enabled:true\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                        }else{
                            desc="Enabled:false\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                        }
                        embed.addField("Status",desc,false);
                        desc="";
                        desc=""; desc3= new StringBuilder();

                        if(SHOCKER_INDEX==-2){
                            embed.addField("Main Shocker","Invalid shocker selected!",false);embed.setColor(llColorRed_Barn);
                        }else
                        if(SHOCKER_INDEX<0){
                            embed.addField("Main Shocker","None selected!",false);
                        }
                        else{
                            logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                            logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            try{

                                if(!cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                                code=cSHOCKER.getCode();
                                if(code!=null&&!code.isBlank()){
                                    desc+="\ncode: "+code;
                                }else{
                                    desc+="\ncode: <null>";
                                }
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nActions:"+desc3.toString();
                                    action1=true;
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nActions:(null)";
                                }
                                embed.addField("Main Shocker",desc,false);validShocker=true;
                                desc="Uses main shocker" ;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+strMenu2;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+strMenu3;
                                embed.addField("Quick Triggers",desc,false);
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                embed.addField("Main Shocker","(null)",false);
                            }
                        }
                        desc="Custom trigger command:";
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strBeep+strMenu4a;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strVibrate+strMenu4b;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strShock+strMenu4c;
                        desc+=strMenu4d;
                        embed.addField("Custom Triggers",desc,false);

                        desc="Linking shockers to rd's collar and chastity";
                        if(hasCollarShocker){
                            try{
                                desc+="\n**collar**";
                                desc+="\ncode: "+cSHOCKER_COLLAR.getCode();
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError collar shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to collar";
                        }
                        if(hasChastityShocker){
                            try{
                                desc+="\n**chastity**";
                                desc+="\ncode: "+cSHOCKER_CHASTITY.getCode();
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError chastity shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to chastity";
                        }
                        embed.addField("Specific Linking",desc,false);
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" whitelist managing";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle)+" blacklist managing";
                        switch (gNewUserProfile.cPISHOCK.getFieldMode()){
                            case 1:
                                desc2="black";break;
                            case 2:
                                desc2="white";break;
                            default:
                                desc2="both";break;
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare)+" switch mode, currently: "+desc2;
                        embed.addField("White&Black Listing",desc,false);
                        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" to access shockers menu.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+"to quickly trigger i shocker action.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+"game menu";
                        embed.addField("Extra options",desc,false);
                        embed.addField(strBetaTitle,strBetaDescription,false);
                        embed.addField(strDisclaim,strDisclaimMore,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                /*if(page>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                }
                if(page<2){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                }
                if(gNewUserProfile.cPISHOCK.isEnabled()){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS));
                if(validShocker){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                }
                if(SHOCKERS_COUNT>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(SHOCKERS_COUNT>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(SHOCKERS_COUNT>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(SHOCKERS_COUNT>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(SHOCKERS_COUNT>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(SHOCKERS_COUNT>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(SHOCKERS_COUNT>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(SHOCKERS_COUNT>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(SHOCKERS_COUNT>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(SHOCKERS_COUNT>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle));

                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare));

                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    lcMessageBuildComponent component3= messageComponentManager.messageBuildComponents.getComponent(3);
                    lcMessageBuildComponent component4= messageComponentManager.messageBuildComponents.getComponent(4);
                    if(!gNewUserProfile.cPISHOCK.isEnabled()){
                        component0.getButtonById("!enable/disable").setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel("🟢 Enable");
                        component0.getButtonById("game_die").setDisable();
                        component2.getButtonById("bell").setDisable();
                        component2.getButtonById("vibration_mode").setDisable();
                        component2.getButtonById("zap").setDisable();
                    }else{
                        component0.getButtonById("!enable/disable").setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel("🔴 Disable");
                    }
                    component1.getSelect().getOptionAt(11).setIgnored();
                    if(SHOCKERS_COUNT==0){
                        component2.setIgnored();
                        for(int i2=0;i2<10;i2++){
                            component1.getSelect().getOptionAt(i2).setIgnored();
                        }
                    }else{
                        for(int i1=0;i1<10;i1++){
                            if(i1<SHOCKERS_COUNT){
                                lcMessageBuildComponent.SelectMenu.Option option=component1.getSelect().getOptionAt(i1);
                                option.setLabel(option.getLabel()+gNewUserProfile.cPISHOCK.getShocker(i1).getName());
                            }else{
                                component1.getSelect().getOptionAt(i1).setIgnored();
                            }
                        }
                        if(SHOCKER_INDEX>-1){
                            component1.getSelect().getOptionAt(SHOCKER_INDEX).setDefault();
                        }
                    }
                    if(page==0){
                        component4.getButtonById("arrow_backward").setDisable();
                    }else
                    if(page==2){
                        component4.getButtonById("arrow_forward").setDisable();
                    }
                    switch (page){
                        case 0: case 1:
                            component3.setIgnored();
                            break;
                        case 2:
                            component1.setIgnored();
                            component2.setIgnored();
                            break;
                    }

                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuPiShockListener_Owner(message,page);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuPiShock_Somebody(int page){
            String fName="[menuPiShock_Somebody]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle);
                isMenuLevel=true;indexMenuLevel=1;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);

                desc2="";desc="";String code="";
                boolean validShocker=false,action1=false,action2=false,action3=false;

                switch (page){
                    case 0:
                        embed.addField(strWhatIs,strWhatIsDetails,false);
                        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
                        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" to access shockers menu.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+"game menu";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+" to select shocker.";
                        embed.addField("Other menus&options",desc,false);
                        desc=""; desc3= new StringBuilder();
                        if(SHOCKER_INDEX==-2){
                            embed.addField("Shocker","Invalid shocker selected!",false);embed.setColor(llColorRed_Barn);
                        }else
                        if(SHOCKER_INDEX<0){
                            embed.addField("Shocker","None selected!",false);
                        }
                        else{
                            logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                            logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            try{

                                if(!cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                                code=cSHOCKER.getCode();
                                if(code!=null&&!code.isBlank()){
                                    desc+="\ncode: (hiden)";
                                }else{
                                    desc+="\ncode: <null>";
                                }
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nActions:"+desc3.toString();
                                    action1=true;
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nActions:(null)";
                                }
                                embed.addField("Shocker",desc,false);validShocker=true;
                                desc="Uses this shocker for" ;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+strMenu2b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+strMenu3b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+strMenu5b;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRocket)+strMenu6b;
                                embed.addField("Quick Triggers",desc,false);
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                embed.addField("Shocker","(null)",false);
                            }
                        }
                        desc="\n`"+llPrefixStr+gQuickPrefix+" "+strBeep+strMenu4a;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strVibrate+strMenu4b;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strShock+strMenu4c;
                        desc+=strMenu4d;
                        embed.addField("Custom Triggers",desc,false);
                        if(lsMemberHelper.lsMemberIsManager(gMember)){
                            embed.addField("Slash command","To add/remobe slash comand go to  `"+llPrefixStr +"slash`.",false);
                        }
                        break;
                    case 1:
                        if(gNewUserProfile.cPISHOCK.isEnabled()){
                            desc="Enabled:true";
                        }else{
                            desc="Enabled:false";
                        }
                        embed.addField("Status",desc,false);
                        desc="Linking shockers to rd's collar and chastity";
                        if(hasCollarShocker){
                            try{
                                desc+="\n**collar**";
                                desc+="\ncode: (hidden)";
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError collar shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to collar, default to main one";
                        }
                        if(gNewUserProfile.cCOLLAR.isOn()){
                            if(gNewUserProfile.cCOLLAR.isShockeEnabled()){
                                desc+="\nCollar on, shock enabled";
                            }else{
                                desc+="\nCollar on, shock disabled";
                            }
                        }else{
                            desc+="\nCollar off";
                        }
                        if(hasChastityShocker){
                            try{
                                desc+="\n**chastity**";
                                desc+="\ncode: (hidden)";
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError chastity shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to chastity, default to main one";
                        }
                        if(gNewUserProfile.cCHASTITY.isOn()){
                            if(gNewUserProfile.cCHASTITY.isShockEnabled()){
                                desc+="\nChastity on, shock enabled";
                            }else{
                                desc+="\nChastity on, shock disabled";
                            }
                        }else{
                            desc+="\nChastity off";
                        }
                        embed.addField("Specific Linking",desc,false);
                        break;
                    case 2:
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" whitelist managing";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle)+" blacklist managing";

                        switch (gNewUserProfile.cPISHOCK.getFieldMode()){
                            case 1:
                                desc2="black";break;
                            case 2:
                                desc2="white";break;
                            default:
                                desc2="both";break;
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare)+" switch mode, currently: "+desc2;

                        embed.addField("White&Black Listing",desc+"\nThis option not available for random strangers!",false);
                        embed.addField(strBetaTitle,strBetaDescription,false);
                        embed.addField(strDisclaim,strDisclaimMore,false);
                        break;
                    default:
                        if(gNewUserProfile.cPISHOCK.isEnabled()){
                            desc="Enabled:true\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                        }else{
                            desc="Enabled:false\nClick on "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                        }
                        embed.addField("Status",desc,false);
                        desc="";
                        desc=""; desc3= new StringBuilder();

                        if(SHOCKER_INDEX==-2){
                            embed.addField("Main Shocker","Invalid shocker selected!",false);embed.setColor(llColorRed_Barn);
                        }else
                        if(SHOCKER_INDEX<0){
                            embed.addField("Main Shocker","None selected!",false);
                        }
                        else{
                            logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                            logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            try{

                                if(!cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                                code=cSHOCKER.getCode();
                                if(code!=null&&!code.isBlank()){
                                    desc+="\ncode: "+code;
                                }else{
                                    desc+="\ncode: <null>";
                                }
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nActions:"+desc3.toString();
                                    action1=true;
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nActions:(null)";
                                }
                                embed.addField("Main Shocker",desc,false);validShocker=true;
                                desc="Uses main shocker" ;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+strMenu2;
                                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+strMenu3;
                                embed.addField("Quick Triggers",desc,false);
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                embed.addField("Main Shocker","(null)",false);
                            }
                        }
                        desc="Custom trigger command:";
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strBeep+strMenu4a;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strVibrate+strMenu4b;
                        desc+="\n`"+llPrefixStr+gQuickPrefix+" "+strShock+strMenu4c;
                        desc+=strMenu4d;
                        embed.addField("Custom Triggers",desc,false);

                        desc="Linking shockers to rd's collar and chastity";
                        if(hasCollarShocker){
                            try{
                                desc+="\n**collar**";
                                desc+="\ncode: (hidden)";
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError collar shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to collar";
                        }
                        if(hasChastityShocker){
                            try{
                                desc+="\n**chastity**";
                                desc+="\ncode: (hidden)";
                                JSONArray tasks=new JSONArray();
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption warn"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption warn (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption punish"+desc3.toString();
                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption punish (null)";
                                }
                                try{
                                    desc3= new StringBuilder();
                                    tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                                    for(int i=0;i<tasks.length();i++){
                                        JSONObject task=tasks.getJSONObject(i);
                                        String  mode=task.getString(nPishockMode).toLowerCase();
                                        int duration=0;
                                        int itensity=0;
                                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                            duration=task.getInt(nPishockDuration);
                                        }
                                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                            duration=task.getInt(nPishockDuration);
                                            itensity=task.getInt(nPishockItensity);
                                        }
                                        desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                                    }
                                    desc+="\nOption reward"+desc3.toString();

                                } catch (Exception e) {
                                    logger.error(fName+".exception=" + e);
                                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                    desc+="\nOption reward (null)";
                                }
                            } catch (Exception e) {
                                logger.error(fName+".exception=" + e);
                                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                                desc+="\nError chastity shocker!";
                            }
                        }
                        else{
                            desc+="\nHas no shocker linked to chastity";
                        }
                        embed.addField("Specific Linking",desc,false);
                        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle)+" whitelist managing";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle)+" blacklist managing";

                        switch (gNewUserProfile.cPISHOCK.getFieldMode()){
                            case 1:
                                desc2="black";break;
                            case 2:
                                desc2="white";break;
                            default:
                                desc2="both";break;
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare)+" switch mode, currently: "+desc2;

                        embed.addField("White&Black Listing",desc,false);
                        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS)+" to access shockers menu.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+"-"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine)+"to quickly trigger i shocker action.";
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+"game menu";
                        embed.addField("Extra options",desc,false);
                        embed.addField(strBetaTitle,strBetaDescription,false);
                        embed.addField(strDisclaim,strDisclaimMore,false);
                }
                Message message=null;//llSendMessageResponse_withReactionNotification(gUser,embed);
                /*if(page>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
                }
                if(page<2){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS));
                if(validShocker){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                }
                if(SHOCKERS_COUNT>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(SHOCKERS_COUNT>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(SHOCKERS_COUNT>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(SHOCKERS_COUNT>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(SHOCKERS_COUNT>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(SHOCKERS_COUNT>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(SHOCKERS_COUNT>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(SHOCKERS_COUNT>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(SHOCKERS_COUNT>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(SHOCKERS_COUNT>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    lcMessageBuildComponent component3= messageComponentManager.messageBuildComponents.getComponent(3);
                    lcMessageBuildComponent component4= messageComponentManager.messageBuildComponents.getComponent(4);
                    if(!gNewUserProfile.cPISHOCK.isEnabled()){
                        component0.getButtonById("!enable/disable").setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel("🟢 Enable").setDisable();
                        component0.getButtonById("game_die").setDisable();
                        component2.getButtonById("bell").setDisable();
                        component2.getButtonById("vibration_mode").setDisable();
                        component2.getButtonById("zap").setDisable();
                    }else{
                        component0.getButtonById("!enable/disable").setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel("🔴 Disable").setDisable();
                    }

                    component1.getSelect().getOptionAt(11).setIgnored();
                    if(SHOCKERS_COUNT==0){
                        component2.setIgnored();
                        for(int i2=0;i2<10;i2++){
                            component1.getSelect().getOptionAt(i2).setIgnored();
                        }
                    }else{
                        for(int i1=0;i1<10;i1++){
                            if(i1<SHOCKERS_COUNT){
                                lcMessageBuildComponent.SelectMenu.Option option=component1.getSelect().getOptionAt(i1);
                                option.setLabel(option.getLabel()+gNewUserProfile.cPISHOCK.getShocker(i1).getName());
                            }else{
                                component1.getSelect().getOptionAt(i1).setIgnored();
                            }
                        }
                        if(SHOCKER_INDEX>-1){
                            component1.getSelect().getOptionAt(SHOCKER_INDEX).setDefault();
                        }
                    }
                    if(page==0){
                        component4.getButtonById("arrow_backward").setDisable();
                    }else
                    if(page==2){
                        component4.getButtonById("arrow_forward").setDisable();
                    }
                    component3.setIgnored();
                    switch (page){
                        case 0: case 1:
                            break;
                        case 2:
                            component1.setIgnored();
                            component2.setIgnored();
                            break;
                    }

                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuPiShockListener_Somebody(message,page);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuPiShockListener_Wearer(Message message,int page){
            String fName="[menuPiShock_Wearer]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowBackward:
                                        menuPiShock_Wearer(page-1);
                                        break;
                                    case lsUnicodeEmotes.aliasArrowForward:
                                        menuPiShock_Wearer(page+1);
                                        break;
                                    case lsUnicodeEmotes.aliasGreenCircle:
                                        rPishock(optionEnable);
                                        break;
                                    case lsUnicodeEmotes.aliasRedCircle:
                                        rPishock(optionDisable);
                                        break;
                                    case lsUnicodeEmotes.aliasID:
                                        dmInputAuthor();
                                        break;
                                    case lsUnicodeEmotes.aliasKey:
                                        dmInputAPI();
                                        break;
                                    case lsUnicodeEmotes.aliasCrossMark:
                                        gNewUserProfile.cPISHOCK.clearShockers();
                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                        menuPiShock_Wearer(page);
                                        break;
                                    case lsUnicodeEmotes.aliasBell:
                                        doBeep(false);
                                        break;
                                    case lsUnicodeEmotes.aliasVibrationMode:
                                        doVibrate(false);
                                        break;
                                    case lsUnicodeEmotes.aliasZap:
                                        doShock(false);
                                        break;
                                    case lsUnicodeEmotes.aliasZero:
                                        doAction0(getShoker(0));
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        doAction0(getShoker(1));
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        doAction0(getShoker(2));
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        doAction0(getShoker(3));
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        doAction0(getShoker(4));
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        doAction0(getShoker(5));
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        doAction0(getShoker(6));
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        doAction0(getShoker(7));
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        doAction0(getShoker(8));
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        doAction0(getShoker(9));
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolS:
                                        menuShockers_Wearer();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        if(!hasCollarShocker){
                                            dmAddLinkedShocker(-1,1);
                                        }else{
                                            menuSpecialShocker(1);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        if(!hasChastityShocker){
                                            dmAddLinkedShocker(-1,2);
                                        }else{
                                            menuSpecialShocker(2);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasWhiteCircle:
                                        menuList(1);
                                        break;
                                    case lsUnicodeEmotes.aliasBlackCircle:
                                        menuList(-1);
                                        break;
                                    case lsUnicodeEmotes.aliasWhiteSmallSquare:
                                        rPishock(optionList,"mode","toggle");
                                        break;
                                    case lsUnicodeEmotes.aliasGameDie:
                                        menuGame();
                                        break;
                                    case "shocker_set":
                                        menuShocker(SHOCKER_INDEX);
                                        break;
                                    case "shocker_action":
                                        doAction0(cSHOCKER);
                                        break;
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero:
                                        selectedShocker=true;SHOCKER_INDEX=0;
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        selectedShocker=true;SHOCKER_INDEX=1;
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        selectedShocker=true;SHOCKER_INDEX=2;
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        selectedShocker=true;SHOCKER_INDEX=3;
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        selectedShocker=true;SHOCKER_INDEX=4;
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        selectedShocker=true;SHOCKER_INDEX=5;
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        selectedShocker=true;SHOCKER_INDEX=6;
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        selectedShocker=true;SHOCKER_INDEX=7;
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        selectedShocker=true;SHOCKER_INDEX=8;
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        selectedShocker=true;SHOCKER_INDEX=9;
                                        break;
                                    case "add":
                                        dmAddShocker(0,-1,null,null);
                                        break;
                                }
                                if(selectedShocker){
                                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(SHOCKER_INDEX);
                                    menuPiShock_Wearer(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuPiShock_Wearer(page-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuPiShock_Wearer(page+1);
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                    rPishock(optionEnable);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                    rPishock(optionDisable);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmInputAuthor();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasKey))){
                                    dmInputAPI();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    gNewUserProfile.cPISHOCK.clearShockers();
                                    saveProfile();
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                    menuPiShock_Wearer(page);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell))){
                                    doBeep(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){
                                    doVibrate(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                    doShock(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    selectedShocker=true;SHOCKER_INDEX=0;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    selectedShocker=true;SHOCKER_INDEX=1;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    selectedShocker=true;SHOCKER_INDEX=2;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    selectedShocker=true;SHOCKER_INDEX=3;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    selectedShocker=true;SHOCKER_INDEX=4;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    selectedShocker=true;SHOCKER_INDEX=5;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    selectedShocker=true;SHOCKER_INDEX=6;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    selectedShocker=true;SHOCKER_INDEX=7;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    selectedShocker=true;SHOCKER_INDEX=8;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    selectedShocker=true;SHOCKER_INDEX=9;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRocket))){
                                    doAction0(cSHOCKER);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS))){
                                    menuShockers_Wearer();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                    if(!hasCollarShocker){
                                        dmAddLinkedShocker(-1,1);
                                    }else{
                                        menuSpecialShocker(1);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){
                                    if(!hasChastityShocker){
                                        dmAddLinkedShocker(-1,2);
                                    }else{
                                        menuSpecialShocker(2);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle))){
                                    menuList(1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle))){
                                    menuList(-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare))){
                                    rPishock(optionList,"mode","toggle");
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                                    menuGame();
                                }
                                if(selectedShocker){
                                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(SHOCKER_INDEX);
                                    menuPiShock_Wearer(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuPiShockListener_Owner(Message message,int page){
            String fName="[menuPiShock_Somebody]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowBackward:
                                        menuPiShock_Owner(page-1);
                                        break;
                                    case lsUnicodeEmotes.aliasArrowForward:
                                        menuPiShock_Owner(page+1);
                                        break;
                                    case lsUnicodeEmotes.aliasGreenCircle:
                                        rPishock(gTarget,optionEnable);
                                        break;
                                    case lsUnicodeEmotes.aliasRedCircle:
                                        rPishock(gTarget,optionDisable);
                                        break;
                                    case lsUnicodeEmotes.aliasBell:
                                        doBeep(false);
                                        break;
                                    case lsUnicodeEmotes.aliasVibrationMode:
                                        doVibrate(false);
                                        break;
                                    case lsUnicodeEmotes.aliasZap:
                                        doShock(false);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolS:
                                        menuShockers_Owner();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        menuSpecialShocker(1);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        menuSpecialShocker(2);
                                        break;
                                    case lsUnicodeEmotes.aliasWhiteCircle:
                                        menuList(1);
                                        break;
                                    case lsUnicodeEmotes.aliasBlackCircle:
                                        menuList(-1);
                                        break;
                                    case lsUnicodeEmotes.aliasWhiteSmallSquare:
                                        rPishock(gTarget,optionList,"mode","toggle");
                                        break;
                                    case lsUnicodeEmotes.aliasGameDie:
                                        menuGame();
                                        break;
                                    case "shocker_set":
                                        menuShocker(SHOCKER_INDEX);
                                        break;
                                    case "shocker_action":
                                        doAction0(cSHOCKER,gTarget,false);
                                        break;
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero:
                                        selectedShocker=true;SHOCKER_INDEX=0;
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        selectedShocker=true;SHOCKER_INDEX=1;
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        selectedShocker=true;SHOCKER_INDEX=2;
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        selectedShocker=true;SHOCKER_INDEX=3;
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        selectedShocker=true;SHOCKER_INDEX=4;
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        selectedShocker=true;SHOCKER_INDEX=5;
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        selectedShocker=true;SHOCKER_INDEX=6;
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        selectedShocker=true;SHOCKER_INDEX=7;
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        selectedShocker=true;SHOCKER_INDEX=8;
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        selectedShocker=true;SHOCKER_INDEX=9;
                                        break;
                                }
                                if(selectedShocker){
                                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(SHOCKER_INDEX);
                                    menuPiShock_Owner(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuPiShock_Owner(page-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuPiShock_Owner(page+1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                    rPishock(gNewUserProfile.getMember(),optionEnable);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                    rPishock(gNewUserProfile.getMember(),optionDisable);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmInputAuthor();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasKey))){
                                    dmInputAPI();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                    dmAddShocker(0,-1,null,null);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                    dmInputSelectShocker4Edit();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign))){
                                    dmInputSelectShocker4Remove();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    gNewUserProfile.cPISHOCK.clearShockers();

                                    saveProfile();
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                    menuPiShock_Owner(page);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell))){
                                    doBeep(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){
                                    doVibrate(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                    doShock(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    selectedShocker=true;SHOCKER_INDEX=0;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    selectedShocker=true;SHOCKER_INDEX=1;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    selectedShocker=true;SHOCKER_INDEX=2;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    selectedShocker=true;SHOCKER_INDEX=3;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    selectedShocker=true;SHOCKER_INDEX=4;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    selectedShocker=true;SHOCKER_INDEX=5;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    selectedShocker=true;SHOCKER_INDEX=6;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    selectedShocker=true;SHOCKER_INDEX=7;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    selectedShocker=true;SHOCKER_INDEX=8;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    selectedShocker=true;SHOCKER_INDEX=9;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRocket))){
                                    doAction0(cSHOCKER);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS))){
                                    menuShockers_Owner();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCircle))){
                                    menuList(1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBlackCircle))){
                                    menuList(-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteSmallSquare))){
                                    rPishock(gNewUserProfile.getMember(),optionList,"mode","toggle");
                                }
                                if(selectedShocker){
                                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(SHOCKER_INDEX);
                                    menuPiShock_Wearer(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () ->{llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuPiShockListener_Somebody(Message message,int page){
            String fName="[menuPiShock_Somebody]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowBackward:
                                        menuPiShock_Somebody(page-1);
                                        break;
                                    case lsUnicodeEmotes.aliasArrowForward:
                                        menuPiShock_Somebody(page+1);
                                        break;
                                    case lsUnicodeEmotes.aliasBell:
                                        doBeep(false);
                                        break;
                                    case lsUnicodeEmotes.aliasVibrationMode:
                                        doVibrate(false);
                                        break;
                                    case lsUnicodeEmotes.aliasZap:
                                        doShock(false);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolS:
                                        menuShockers_Somebody();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        menuSpecialShocker(1);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        menuSpecialShocker(2);
                                        break;
                                    case lsUnicodeEmotes.aliasGameDie:
                                        menuGame();
                                        break;
                                    case "shocker_set":
                                        menuShocker(SHOCKER_INDEX);
                                        break;
                                    case "shocker_action":
                                        doAction0(cSHOCKER,gTarget,false);
                                        break;
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero:
                                        selectedShocker=true;SHOCKER_INDEX=0;
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        selectedShocker=true;SHOCKER_INDEX=1;
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        selectedShocker=true;SHOCKER_INDEX=2;
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        selectedShocker=true;SHOCKER_INDEX=3;
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        selectedShocker=true;SHOCKER_INDEX=4;
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        selectedShocker=true;SHOCKER_INDEX=5;
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        selectedShocker=true;SHOCKER_INDEX=6;
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        selectedShocker=true;SHOCKER_INDEX=7;
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        selectedShocker=true;SHOCKER_INDEX=8;
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        selectedShocker=true;SHOCKER_INDEX=9;
                                        break;
                                }
                                if(selectedShocker){
                                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(SHOCKER_INDEX);
                                    menuPiShock_Somebody(page);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                                    menuPiShock_Somebody(page-1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                                    menuPiShock_Somebody(page+1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell))){
                                    doBeep(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){
                                    doVibrate(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                    doShock(false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    doAction0(getShoker(0),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    doAction0(getShoker(1),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    doAction0(getShoker(2),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    doAction0(getShoker(3),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    doAction0(getShoker(4),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    doAction0(getShoker(5),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    doAction0(getShoker(6),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    doAction0(getShoker(7),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    doAction0(getShoker(8),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    doAction0(getShoker(9),gNewUserProfile.getMember(),false);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolS))){
                                    menuShockers_Somebody();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void reset(){
            String fName="[reset]";
            logger.info(fName);
            try{
                gNewUserProfile.cPISHOCK. clear();
                saveProfile();
                /*if(isMenuLevel){
                    menuPiShock(gNewUserProfile.getMember());
                }*/
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void dmInputAuthor(){
            String fName="[dmInputAuthor]";
            logger.info(fName);
            try{
                String value=gNewUserProfile.cPISHOCK.getUsername();Message message;
                if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current username is "+value+".\nPlease enter a new username that you use at "+ piWebsiteEmbed +" or enter !cancel to leave it as it is or enter !clear to remove it.", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter a username that you use at "+ piWebsiteEmbed +".", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuShocker();return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    gNewUserProfile.cPISHOCK.setUsername("");
                                }
                                else{
                                    gNewUserProfile.cPISHOCK.setUsername(content);
                                }

                                saveProfile();menuPiShock(gNewUserProfile.getMember());
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void dmInputAPI(){
            String fName="[dmInputAPI]";
            logger.info(fName);
            try{
                String value=gNewUserProfile.cPISHOCK.getApiKey();Message message;
                if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current apikey is "+value+".\nPlease enter a new apikey that is provided by "+ piWebsiteEmbed +" or enter !cancel to leave it as it is or enter !clear to remove it.", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter an apikey that is provided by "+ piWebsiteEmbed +".", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuShocker();return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    gNewUserProfile.cPISHOCK.setApiKey("");
                                }
                                else{
                                    gNewUserProfile.cPISHOCK.setApiKey(content);
                                }

                                saveProfile();menuPiShock(gNewUserProfile.getMember());
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuShockers(){
            String fName="[menuShockers]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                getShockers();getDefaultShoker();
                if(gMember.getIdLong()==gNewUserProfile.getMember().getIdLong()){
                    menuShockers_Wearer();
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuShockers_Owner();
                    }else{
                        if(iRestraints.isArmsRestrained(gGlobal,gMember)){
                            if(iRDPatreon.patreonUser_239748154046545920_mitts.isValid(gGlobal,gMember)){
                                logger.info(fName + ".special patreon message:"+ iRDPatreon.patreonUser_239748154046545920_mitts.name);
                                iRDPatreon.patreonUser_239748154046545920_mitts.sendDeniedMessage(gMember,sRTitle,gTextChannel);
                            }else{
                                logger.info(fName + ".default message");
                                lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,iRestraints.sStringReplacerifIsSpeciesHuman(iRestraints.getUserProfile(gGlobal,gMember,false),iRestraints.strArmsRestrained),llColors.llColorRed_Barn);

                            }
                            return;
                        }
                        menuShockers_Somebody();
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        String gCommandFileShockersPath =gCommandsPath+"menuShockers.json";
        private void menuShockers_Wearer(){
            String fName="[menuShockers_Wearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(sRTitle);
                isMenuLevel=true;indexMenuLevel=2;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                desc2="";desc="";
                try {
                    desc3=new StringBuilder();
                    for(int i=0;i<gNewUserProfile.cPISHOCK.getShockersSize();i++){
                        try{
                            entityShocker shocker=gNewUserProfile.cPISHOCK.getShocker(i);
                            logger.info(fName+"shocker["+i+"]="+shocker.getJSON().toString());
                            desc3.append("\n["+i+", "+shocker.getCode());
                            if(shocker.hasName())  desc3.append(", "+shocker.getName());
                            if(SHOCKER_INDEX==i){
                                desc3.append(", main:");
                            }
                            desc3.append("]");
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    embed.addField("Shockers:",desc3.toString(),false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Shockers:","(null)",false);
                }
                desc="";
                if(SHOCKERS_COUNT<maxShockersCount){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign)+" add shocker";
                }
                if(SHOCKERS_COUNT>0){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" remove all shockers";
                }
                embed.addField("Shockers options",strMenu1+desc,false);
                desc="Linking to rd's collar and chastity.";
                if(hasCollarShocker){
                    try{
                        desc+="\n**collar**";
                        desc+="\ncode: "+cSHOCKER_COLLAR.getCode();
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption warn"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption warn (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption punish"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption punish (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption reward"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption reward (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError collar shocker!";
                    }
                }
                else{
                    desc+="\nHas no shocker linked to collar, use"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" to add one.";
                }
                if(hasChastityShocker){
                    try{
                        desc+="\n**chastity**";
                        desc+="\ncode: "+cSHOCKER_CHASTITY.getCode();
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption warn"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption warn (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption punish"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption punish (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption reward"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption reward (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError chastity shocker!";
                    }
                }
                else{
                    desc+="\nHas no shocker linked to chastity, use"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" to add one.";
                }
                embed.addField("Specific Linking",desc,false);
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(SHOCKERS_COUNT<10){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
                }
                if(SHOCKERS_COUNT>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }
                if(SHOCKERS_COUNT>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(SHOCKERS_COUNT>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(SHOCKERS_COUNT>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(SHOCKERS_COUNT>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(SHOCKERS_COUNT>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(SHOCKERS_COUNT>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(SHOCKERS_COUNT>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(SHOCKERS_COUNT>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(SHOCKERS_COUNT>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(SHOCKERS_COUNT>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                messageComponentManager.loadMessageComponents(gCommandFileShockersPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    if(SHOCKERS_COUNT>=10){
                        component0.getButtonById(lsUnicodeEmotes.aliasHeavyPlusSign).setDisable();
                    }
                    if(SHOCKERS_COUNT==0){
                        component0.getButtonById(lsUnicodeEmotes.aliasCrossMark).setDisable();
                        component1.getSelect().setDisabled();
                    }else{
                        for(int i1=0;i1<10;i1++){
                            if(i1<SHOCKERS_COUNT){
                                lcMessageBuildComponent.SelectMenu.Option option=component1.getSelect().getOptionAt(i1);
                                option.setLabel(option.getLabel()+gNewUserProfile.cPISHOCK.getShocker(i1).getName());
                            }else{
                                component1.getSelect().getOptionAt(i1).setIgnored();
                            }
                        }
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuShockersListener_Wearer(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockers_Owner(){
            String fName="[menuShockers_Owner]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle);
                isMenuLevel=true;indexMenuLevel=2;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                try {
                    desc3=new StringBuilder();
                    for(int i=0;i<gNewUserProfile.cPISHOCK.getShockersSize();i++){
                        try{
                            entityShocker shocker=gNewUserProfile.cPISHOCK.getShocker(i);
                            logger.info(fName+"shocker["+i+"]="+shocker.toString());
                            desc3.append("\n["+i+", "+shocker.getCode());
                            if(shocker.hasName())  desc3.append(", "+shocker.getName());
                            if(SHOCKER_INDEX==i){
                                desc3.append(", main:");
                            }
                            desc3.append("]");
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    embed.addField("Shockers:",desc3.toString(),false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Shockers:","(null)",false);
                }
                desc="";
                if(SHOCKERS_COUNT<10){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign)+" add shocker";
                }
                if(SHOCKERS_COUNT>0){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" remove all shockers";
                }
                embed.addField("Shockers options",strMenu1+desc,false);
                boolean validShocker=false,action1=false,action2=false,action3=false;
                desc="Linking to rd's collar and chastity.";
                if(hasCollarShocker){
                    try{
                        desc+="\n**collar**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption warn"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption warn (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption punish"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption punish (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption reward"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption reward (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError collar shocker!";
                    }
                }
                else{
                    desc+="\nHas no shocker linked to collar, use"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" to add one.";
                }
                if(hasChastityShocker){
                    try{
                        desc+="\n**chastity**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption warn"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption warn (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption punish"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption punish (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption reward"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption reward (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError chastity shocker!";
                    }
                }
                else{
                    desc+="\nHas no shocker linked to chastity, use"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" to add one.";
                }
                embed.addField("Specific Linking",desc,false);
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(SHOCKERS_COUNT<10){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign));
                }
                if(SHOCKERS_COUNT>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }
                if(SHOCKERS_COUNT>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(SHOCKERS_COUNT>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(SHOCKERS_COUNT>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(SHOCKERS_COUNT>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(SHOCKERS_COUNT>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(SHOCKERS_COUNT>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(SHOCKERS_COUNT>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(SHOCKERS_COUNT>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(SHOCKERS_COUNT>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(SHOCKERS_COUNT>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                messageComponentManager.loadMessageComponents(gCommandFileShockersPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    if(SHOCKERS_COUNT>=10){
                        component0.getButtonById(lsUnicodeEmotes.aliasHeavyPlusSign).setDisable();
                    }
                    if(SHOCKERS_COUNT==0){
                        component0.getButtonById(lsUnicodeEmotes.aliasCrossMark).setDisable();
                        component1.getSelect().setDisabled();
                    }else{
                        for(int i1=0;i1<10;i1++){
                            if(i1<SHOCKERS_COUNT){
                                lcMessageBuildComponent.SelectMenu.Option option=component1.getSelect().getOptionAt(i1);
                                option.setLabel(option.getLabel()+gNewUserProfile.cPISHOCK.getShocker(i1).getName());
                            }else{
                                component1.getSelect().getOptionAt(i1).setIgnored();
                            }
                        }
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuShockersListener_Owner(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockers_Somebody(){
            String fName="[menuShockers_Somebody]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle);
                isMenuLevel=true;indexMenuLevel=2;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                try {
                    desc3=new StringBuilder();
                    for(int i=0;i<gNewUserProfile.cPISHOCK.getShockersSize();i++){
                        try{
                            entityShocker shocker=gNewUserProfile.cPISHOCK.getShocker(i);
                            logger.info(fName+"shocker["+i+"]="+shocker.toString());
                            desc3.append("\n["+i+", <code hidden>");
                            if(shocker.hasName())  desc3.append(", "+shocker.getName());
                            if(SHOCKER_INDEX==i){
                                desc3.append(", main:");
                            }
                            desc3.append("]");
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    embed.addField("Shockers:",desc3.toString(),false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Shockers:","(null)",false);
                }
                embed.addField("Shockers options",strMenu1+desc,false);
                desc="";
                boolean validShocker=false,action1=false,action2=false,action3=false;
                desc="Linking to rd's collar and chastity.";
                if(hasCollarShocker){
                    try{
                        desc+="\n**collar**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_warn);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption warn"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption warn (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_punish);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption punish"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption punish (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_COLLAR.getActionKeyAsJSON(nPishockAction_reward);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption reward"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption reward (null)";
                        }
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError collar shocker!";
                    }
                }
                else{
                    desc+="\nHas no shocker linked to collar.";
                }
                if(hasChastityShocker){
                    try{
                        desc+="\n**chastity**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_warn);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption warn"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption warn (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_punish);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption punish"+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption punish (null)";
                        }
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER_CHASTITY.getActionKeyAsJSON(nPishockAction_reward);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nOption reward"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nOption reward (null)";
                        }
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError chastity shocker!";
                    }
                }
                else{
                    desc+="\nHas no shocker linked to chastity.";
                }
                embed.addField("Specific Linking",desc,false);
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                if(SHOCKERS_COUNT>0){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo));
                }
                if(SHOCKERS_COUNT>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(SHOCKERS_COUNT>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(SHOCKERS_COUNT>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(SHOCKERS_COUNT>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(SHOCKERS_COUNT>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(SHOCKERS_COUNT>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(SHOCKERS_COUNT>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(SHOCKERS_COUNT>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(SHOCKERS_COUNT>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(SHOCKERS_COUNT>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);*/
                messageComponentManager.loadMessageComponents(gCommandFileShockersPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    component0.getButtonById(lsUnicodeEmotes.aliasHeavyPlusSign).setDisable();
                    component0.getButtonById(lsUnicodeEmotes.aliasCrossMark).setDisable();
                    component0.getButtonById(lsUnicodeEmotes.aliasSymbolC).setDisable();
                    component0.getButtonById(lsUnicodeEmotes.aliasSymbolD).setDisable();
                    if(SHOCKERS_COUNT==0){
                        component1.getSelect().setDisabled();
                    }else{
                        for(int i1=0;i1<10;i1++){
                            if(i1<SHOCKERS_COUNT){
                                lcMessageBuildComponent.SelectMenu.Option option=component1.getSelect().getOptionAt(i1);
                                option.setLabel(option.getLabel()+gNewUserProfile.cPISHOCK.getShocker(i1).getName());
                            }else{
                                component1.getSelect().getOptionAt(i1).setIgnored();
                            }
                        }
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuShockersListener_Somebody(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockersListener_Wearer(Message message){
            String fName="[menuShockers_Wearer]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowUp:
                                        menuPiShock(null);
                                        break;
                                    case lsUnicodeEmotes.aliasHeavyPlusSign:
                                        dmAddShocker(0,-1,null,null);
                                        break;
                                    case lsUnicodeEmotes.aliasCrossMark:
                                        gNewUserProfile.cPISHOCK.clearShockers();
                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                        menuPiShock_Wearer(0);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        if(!hasCollarShocker){
                                            dmAddLinkedShocker(-1,1);
                                        }else{
                                            isMenuShocker4Edit=true;menuSpecialShocker(1);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        if(!hasChastityShocker){
                                            dmAddLinkedShocker(-1,2);
                                        }else{
                                            isMenuShocker4Edit=true;menuSpecialShocker(2);
                                        }
                                        break;
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero:
                                        selectedShocker=true;SHOCKER_INDEX=0;
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        selectedShocker=true;SHOCKER_INDEX=1;
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        selectedShocker=true;SHOCKER_INDEX=2;
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        selectedShocker=true;SHOCKER_INDEX=3;
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        selectedShocker=true;SHOCKER_INDEX=4;
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        selectedShocker=true;SHOCKER_INDEX=5;
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        selectedShocker=true;SHOCKER_INDEX=6;
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        selectedShocker=true;SHOCKER_INDEX=7;
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        selectedShocker=true;SHOCKER_INDEX=8;
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        selectedShocker=true;SHOCKER_INDEX=9;
                                        break;
                                }
                                if(selectedShocker){
                                    isMenuShocker4Edit=true;
                                    menuShocker(SHOCKER_INDEX);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(null);return;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmInputAuthor();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasKey))){
                                    dmInputAPI();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                                    dmInputSelectShocker4View();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                    dmAddShocker(0,-1,null,null);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight))){
                                    dmInputSelectShocker4Main();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                    isMenuShocker4Edit=true;dmInputSelectShocker4Edit();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign))){
                                    dmInputSelectShocker4Remove();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    gNewUserProfile.cPISHOCK.clearShockers();

                                    saveProfile();
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                    menuPiShock_Wearer(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    isMenuShocker4Edit=true;menuShocker(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    isMenuShocker4Edit=true;menuShocker(1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    isMenuShocker4Edit=true;menuShocker(2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    isMenuShocker4Edit=true; menuShocker(3);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    isMenuShocker4Edit=true;menuShocker(4);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    isMenuShocker4Edit=true;menuShocker(5);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    isMenuShocker4Edit=true; menuShocker(6);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    isMenuShocker4Edit=true;menuShocker(7);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    isMenuShocker4Edit=true;menuShocker(8);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    isMenuShocker4Edit=true; menuShocker(9);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                    if(!hasCollarShocker){
                                        dmAddLinkedShocker(-1,1);
                                    }else{
                                        isMenuShocker4Edit=true;menuSpecialShocker(1);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){
                                    if(!hasChastityShocker){
                                        dmAddLinkedShocker(-1,2);
                                    }else{
                                        isMenuShocker4Edit=true;menuSpecialShocker(2);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockersListener_Owner(Message message){
            String fName="[menuShockers_Owner]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowUp:
                                        menuPiShock(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasHeavyPlusSign:
                                        dmAddShocker(0,-1,null,null);
                                        break;
                                    case lsUnicodeEmotes.aliasCrossMark:
                                        gNewUserProfile.cPISHOCK.clearShockers();
                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                        menuPiShock(gTarget);
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        if(!hasCollarShocker){
                                            dmAddLinkedShocker(-1,1);
                                        }else{
                                            isMenuShocker4Edit=true;menuSpecialShocker(1);
                                        }
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        if(!hasChastityShocker){
                                            dmAddLinkedShocker(-1,2);
                                        }else{
                                            isMenuShocker4Edit=true;menuSpecialShocker(2);
                                        }
                                        break;
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero:
                                        selectedShocker=true;SHOCKER_INDEX=0;
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        selectedShocker=true;SHOCKER_INDEX=1;
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        selectedShocker=true;SHOCKER_INDEX=2;
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        selectedShocker=true;SHOCKER_INDEX=3;
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        selectedShocker=true;SHOCKER_INDEX=4;
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        selectedShocker=true;SHOCKER_INDEX=5;
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        selectedShocker=true;SHOCKER_INDEX=6;
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        selectedShocker=true;SHOCKER_INDEX=7;
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        selectedShocker=true;SHOCKER_INDEX=8;
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        selectedShocker=true;SHOCKER_INDEX=9;
                                        break;
                                }
                                if(selectedShocker){
                                    isMenuShocker4Edit=true;
                                    menuShocker(SHOCKER_INDEX);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmInputAuthor();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasKey))){
                                    dmInputAPI();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                                    dmInputSelectShocker4View();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyPlusSign))){
                                    dmAddShocker(0,-1,null,null);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight))){
                                    dmInputSelectShocker4Main();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasMemo))){
                                    dmInputSelectShocker4Edit();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHeavyMinusSign))){
                                    dmInputSelectShocker4Remove();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    gNewUserProfile.cPISHOCK.clearShockers();

                                    saveProfile();
                                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared all shockers.",llColors.llColorGreen2);
                                    menuPiShock_Owner(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    isMenuShocker4Edit=true;menuShocker(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    isMenuShocker4Edit=true;menuShocker(1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    isMenuShocker4Edit=true;menuShocker(2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    isMenuShocker4Edit=true; menuShocker(3);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    isMenuShocker4Edit=true;menuShocker(4);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    isMenuShocker4Edit=true;menuShocker(5);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    isMenuShocker4Edit=true; menuShocker(6);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    isMenuShocker4Edit=true;menuShocker(7);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    isMenuShocker4Edit=true;menuShocker(8);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    isMenuShocker4Edit=true; menuShocker(9);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                    if(!hasCollarShocker){
                                        dmAddLinkedShocker(-1,1);
                                    }else{
                                        isMenuShocker4Edit=true;menuSpecialShocker(1);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){
                                    if(!hasChastityShocker){
                                        dmAddLinkedShocker(-1,2);
                                    }else{
                                        isMenuShocker4Edit=true;menuSpecialShocker(2);
                                    }
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockersListener_Somebody(Message message){
            String fName="[menuShockers_Somebody]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowUp:
                                        menuPiShock(gTarget);
                                        break;
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
                                List<String> values=e.getValues();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value);
                                llMessageDelete(message);
                                boolean selectedShocker=false;
                                switch (value){
                                    case lsUnicodeEmotes.aliasZero:
                                        selectedShocker=true;SHOCKER_INDEX=0;
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        selectedShocker=true;SHOCKER_INDEX=1;
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        selectedShocker=true;SHOCKER_INDEX=2;
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        selectedShocker=true;SHOCKER_INDEX=3;
                                        break;
                                    case lsUnicodeEmotes.aliasFour:
                                        selectedShocker=true;SHOCKER_INDEX=4;
                                        break;
                                    case lsUnicodeEmotes.aliasFive:
                                        selectedShocker=true;SHOCKER_INDEX=5;
                                        break;
                                    case lsUnicodeEmotes.aliasSix:
                                        selectedShocker=true;SHOCKER_INDEX=6;
                                        break;
                                    case lsUnicodeEmotes.aliasSeven:
                                        selectedShocker=true;SHOCKER_INDEX=7;
                                        break;
                                    case lsUnicodeEmotes.aliasEight:
                                        selectedShocker=true;SHOCKER_INDEX=8;
                                        break;
                                    case lsUnicodeEmotes.aliasNine:
                                        selectedShocker=true;SHOCKER_INDEX=9;
                                        break;
                                }
                                if(selectedShocker){
                                    isMenuShocker4Edit=false;
                                    menuShocker(SHOCKER_INDEX);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    isMenuShocker4Edit=false;menuShocker(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    isMenuShocker4Edit=false;menuShocker(1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    isMenuShocker4Edit=false;menuShocker(2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    isMenuShocker4Edit=false; menuShocker(3);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    isMenuShocker4Edit=false;menuShocker(4);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    isMenuShocker4Edit=false;menuShocker(5);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    isMenuShocker4Edit=false;menuShocker(6);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    isMenuShocker4Edit=false;menuShocker(7);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    isMenuShocker4Edit=false;menuShocker(8);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    isMenuShocker4Edit=false; menuShocker(9);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                                    dmInputSelectShocker4View();
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        int intDefaultMinutes=15;
        private void dmInputSelectShocker4Main(){
            String fName="[dmInputSelectShocker4Main]";
            logger.info(fName);
            try{
                if(gNewUserProfile.cPISHOCK.isShockersEmpty()){
                    llSendQuickEmbedMessageResponse(gUser,sRTitle,"No shockers added!", llColorRed_Barn);
                    menuPiShock(gNewUserProfile.getMember());
                    return;
                }
                if(gNewUserProfile.cPISHOCK.getShockersSize()==1){
                    gNewUserProfile.cPISHOCK.setMainShocker(0);
                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(0);
                    saveProfile();
                    llSendQuickEmbedMessageResponse(gUser,sRTitle,"You only have 1 shocker, it was selected as main one.", llColorGreen2);
                    menuPiShock(gNewUserProfile.getMember());
                    return;
                }
                else{
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a number between 0-"+(gNewUserProfile.cPISHOCK.getShockersSize()-1)+".", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    llMessageDelete(message);
                                    logger.info(fName+".content="+content);
                                    if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                        //nothing or canceled >return
                                        menuPiShock(gNewUserProfile.getMember());return;
                                    }
                                    else{
                                        int index=Integer.valueOf(content);
                                        if(index<0||index>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                            llSendQuickEmbedMessageResponse(gUser,sRTitle,"Invalid number!", llColorRed_Barn);
                                            dmInputSelectShocker4Main();
                                        }else{
                                            gNewUserProfile.cPISHOCK.setMainShocker(index);
                                            cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(index);
                                            saveProfile();
                                            menuPiShock(gNewUserProfile.getMember());
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputSelectShocker4View(){
            String fName="[dmInputSelectShocker4View]";
            logger.info(fName);
            try{

                if(gNewUserProfile.cPISHOCK.isShockersEmpty()){
                    llSendQuickEmbedMessageResponse(gUser,sRTitle,"No shockers added!", llColorRed_Barn);
                    menuPiShock(gNewUserProfile.getMember());
                    return;
                }
                if(gNewUserProfile.cPISHOCK.getShockersSize()==1){
                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(0);
                    SHOCKER_INDEX=0;
                    logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    menuShocker();
                    return;
                }
                else{
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a number between 0-"+(gNewUserProfile.cPISHOCK.getShockersSize()-1)+".", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    llMessageDelete(message);
                                    logger.info(fName+".content="+content);
                                    if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                        //nothing or canceled >return
                                        menuPiShock(gNewUserProfile.getMember());return;
                                    }
                                    else{
                                        if(content.equalsIgnoreCase(optionCollar)){
                                            logger.info(fName+".removed");
                                            //menuSpecialShocker(1);
                                        }
                                        else if(content.equalsIgnoreCase(optionChastity)){
                                            logger.info(fName+".removed");
                                            //menuSpecialShocker(2);
                                        }
                                        else {
                                            int index=Integer.valueOf(content);
                                            if(index<0||index>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                                llSendQuickEmbedMessageResponse(gUser,sRTitle,"Invalid number!", llColorRed_Barn);
                                                dmInputSelectShocker4View();
                                            }else{
                                                isMenuShocker4Edit=false;menuShocker(index);
                                            }
                                        }

                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputSelectShocker4Edit(){
            String fName="[dmInputSelectShocker4Edit]";
            logger.info(fName);
            try{

                if(gNewUserProfile.cPISHOCK.isShockersEmpty()){
                    llSendQuickEmbedMessageResponse(gUser,sRTitle,"No shockers added!", llColorRed_Barn);
                    menuPiShock(gNewUserProfile.getMember());
                    return;
                }
                if(gNewUserProfile.cPISHOCK.getShockersSize()==1){
                    cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(0);
                    SHOCKER_INDEX=0;
                    logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    menuShocker();
                    return;
                }
                else{
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a number between 0-"+(gNewUserProfile.cPISHOCK.getShockersSize()-1)+".", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    llMessageDelete(message);
                                    logger.info(fName+".content="+content);
                                    if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                        //nothing or canceled >return
                                        menuPiShock(gNewUserProfile.getMember());return;
                                    }
                                    else{
                                        if(content.equalsIgnoreCase(optionCollar)){
                                            menuSpecialShocker(1);
                                        }
                                        else if(content.equalsIgnoreCase(optionChastity)){
                                            menuSpecialShocker(2);
                                        }
                                        else {
                                            int index=Integer.valueOf(content);
                                            if(index<0||index>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                                llSendQuickEmbedMessageResponse(gUser,sRTitle,"Invalid number!", llColorRed_Barn);
                                                dmInputSelectShocker4Edit();
                                            }else{
                                                isMenuShocker4Edit=true;menuShocker(index);
                                            }
                                        }

                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputSelectShocker4Remove(){
            String fName="[dmInputSelectShocker4Remove]";
            logger.info(fName);
            try{

                if(gNewUserProfile.cPISHOCK.isShockersEmpty()){
                    llSendQuickEmbedMessageResponse(gUser,sRTitle,"No shockers added!", llColorRed_Barn);
                    menuPiShock(gNewUserProfile.getMember());
                    return;
                }
                if(gNewUserProfile.cPISHOCK.getShockersSize()==1){
                   gNewUserProfile.cPISHOCK.setMainShocker(-1);
                    gNewUserProfile.cPISHOCK.clearShockers();

                    saveProfile();
                    llSendQuickEmbedMessageResponse(gUser,sRTitle,"You only have 1 shocker, it was removed.", llColorGreen2);
                    menuPiShock(gNewUserProfile.getMember());
                    return;
                }
                else{
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a number between 0-"+(gNewUserProfile.cPISHOCK.getShockersSize()-1)+".", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    llMessageDelete(message);
                                    logger.info(fName+".content="+content);
                                    if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                        //nothing or canceled >return
                                        menuPiShock(gNewUserProfile.getMember());return;
                                    }
                                    else{
                                        int index=Integer.valueOf(content);
                                        if(index<0||index>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                            llSendQuickEmbedMessageResponse(gUser,sRTitle,"Invalid number!", llColorRed_Barn);
                                            dmInputSelectShocker4Remove();
                                        }else{
                                            gNewUserProfile.cPISHOCK.remShocker(index);
                                            if(gNewUserProfile.cPISHOCK.getMainShockerIndex()>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                                gNewUserProfile.cPISHOCK.setMainShocker(gNewUserProfile.cPISHOCK.getShockersSize()-1);
                                            }
                                            saveProfile();
                                            menuPiShock(gNewUserProfile.getMember());
                                        }
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        boolean isMenuShocker4Edit=false;
        private void menuShocker(){
            String fName="[menuShocker]";
            logger.info(fName);
            try{
                isMenuLevel=true;indexMenuLevel=3; SHOCKER_TYPE=0;
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    menuShockerWearer();
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuShockerOwner();
                    }else{
                        menuShockerSomebody();
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShocker(int index){
            String fName="[menuShocker]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                getShockers();
                logger.info(fName+"index="+index);
                SHOCKER_INDEX=index;
                SHOCKER_TYPE=0;
                cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(index);
                logger.info(fName+"SHOCKER["+index+"]="+cSHOCKER.getJSON().toString());
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    menuShockerWearer();
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuShockerOwner();
                    }else{
                        menuShockerSomebody();
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        String gCommandFileShockerPath =gCommandsPath+"menuShocker.json",
                gCommandFileInputActionOption =gCommandsPath+"menuInputAction_Option.json",
                gCommandFileInputActionAskNewLine=gCommandsPath+"menuInputAction_AskNewLine.json";
        private void menuShockerWearer(){
            String fName="[menuShockerWearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";StringBuilder desc3=new StringBuilder();
                isWearerAccessLimited=false;
                try {
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    if(cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                    desc+="\ncode: "+cSHOCKER.getCode();
                    try {
                        if(gNewUserProfile.cPISHOCK.isMainShocker(SHOCKER_INDEX)){
                            desc+="\nmain: true";
                        }else{
                            desc+="\nmain: false";
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    }
                    JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                           for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\n**Action**"+desc3.toString();

                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    desc="(null)";
                }
                embed.addField("Shocker ["+SHOCKER_INDEX+"]",desc,false);
                desc="**Please select an option for shocker.**";
                if(isMenuShocker4Edit){
                    desc="\n:id: enter code";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCardIndex)+" enter name";
                }
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                if(isMenuShocker4Edit){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning)+" enter action";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBarOfSoap)+" clear all actions";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHandPointRight)+" select this default";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" select this to use code for collar";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" select this to use code for chastity";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace)+" select this to use code for game won";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace)+" select this to use code for game lost";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" delete";
                }
                embed.setDescription(desc);
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".is set to owner access");
                    embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                    isWearerAccessLimited =true;
                }else
                if(!gIsOverride&&isAccess2Public(gNewUserProfile.gUserProfile)){
                    logger.info(fName + ".is set to public access");
                    embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                    isWearerAccessLimited =true;
                }
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*if(isMenuShocker4Edit) {
                    if(isWearerAccessLimited){
                        if(!cSHOCKER.hasCode())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                        if(!cSHOCKER.hasName())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl));
                        if(!cSHOCKER.hasActions())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning));
                        if(!cSHOCKER_COLLAR.hasCode())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        if(!cSHOCKER_COLLAR.hasCode())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                        if(!cSHOCKERGAMEWIN.hasCode()) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace));
                        if(!cSHOCKERGAMELOSE.hasCode())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace));
                    }else{
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning));
                        lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace));
                        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace));
                    }
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileShockerPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    if(SHOCKERS_COUNT>=10){
                        component0.getButtonById(lsUnicodeEmotes.aliasHeavyPlusSign).setDisable();
                    }
                    if(SHOCKERS_COUNT==0){
                        component0.getButtonById(lsUnicodeEmotes.aliasCrossMark).setDisable();
                    }
                    if(!isMenuShocker4Edit){
                        component0.setIgnored();component1.setIgnored();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuShockerListenerWearer(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockerOwner(){
            String fName="[menuShockerOwner]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";StringBuilder desc3=new StringBuilder();
                try {
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    if(cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                    desc+="\ncode: (hidden)";
                    try {
                        if(gNewUserProfile.cPISHOCK.isMainShocker(SHOCKER_INDEX)){
                            desc+="\nmain: true";
                        }else{
                            desc+="\nmain: false";
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    }
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\n**Action**"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    desc="(null)";
                }
                embed.addField("Shocker ["+SHOCKER_INDEX+"]",desc,false);
                desc="**Please select an option for shocker.**";
                if(isMenuShocker4Edit){desc="\n:id: enter code";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCardIndex)+" enter name";}
                if(isPatreon){
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                if(isMenuShocker4Edit){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning)+" enter action";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBarOfSoap)+" clear all actions";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasHandPointRight)+" select this default";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" select this to use code for collar";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" select this to use code for chastity";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace)+" select this to use code for game won";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace)+" select this to use code for game lost";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" delete";
                }
                embed.setDescription(desc);
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*if(isMenuShocker4Edit) {
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning));
                    lsMessageHelper.lsMessageAddReactions(message, gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace));
                };
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileShockerPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    if(SHOCKERS_COUNT>=10){
                        component0.getButtonById(lsUnicodeEmotes.aliasHeavyPlusSign).setDisable();
                    }
                    if(SHOCKERS_COUNT==0){
                        component0.getButtonById(lsUnicodeEmotes.aliasCrossMark).setDisable();
                    }
                    if(!isMenuShocker4Edit){
                        component0.setIgnored();component1.setIgnored();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuShockerListenerOwner(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockerSomebody(){
            String fName="[menuShockerOwner]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";StringBuilder desc3=new StringBuilder();
                try {
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    if(cSHOCKER.hasName())desc+="\nname: "+cSHOCKER.getName();
                    desc+="\ncode: (hidden)";
                    try {
                        if(gNewUserProfile.cPISHOCK.isMainShocker(SHOCKER_INDEX)){
                            desc+="\nmain: true";
                        }else{
                            desc+="\nmain: false";
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    }
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_main);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\n**Action**"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    }

                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    desc="(null)";
                }
                embed.addField("Shocker ["+SHOCKER_INDEX+"]",desc,false);
                Message message=null;//llSendMessageResponse(gUser,embed);
                /*lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
                messageComponentManager.loadMessageComponents(gCommandFileShockerPath);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent component1= messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent component2= messageComponentManager.messageBuildComponents.getComponent(2);
                    component0.setIgnored();component1.setIgnored();

                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                menuShockerListenerSomebody(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockerListenerWearer(Message message){
            String fName="[menuShockerWearer]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowUp:
                                        menuPiShock(gNewUserProfile.getMember());
                                    break;
                                    case lsUnicodeEmotes.aliasID:
                                        dmInputCode();
                                        break;
                                    case lsUnicodeEmotes.aliasCardIndex:
                                        dmInputName();
                                        break;
                                    case lsUnicodeEmotes.aliasPersonRunning:
                                        dmInputAction0();
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        dmInputAction1();
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        dmInputAction2();
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        dmInputAction3();
                                        break;
                                    case lsUnicodeEmotes.aliasBarOfSoap:
                                        JSONObject action=new JSONObject();
                                        action.put(nPishockMode,vPishockMode_Vibrate);action.put(nPishockDuration,5);action.put(nPishockItensity,10);
                                        JSONArray actions=new JSONArray();
                                        cSHOCKER.setActionKey(nPishockAction_main,actions);
                                        cSHOCKER.setActionKey(nPishockAction_warn,actions);
                                        cSHOCKER.setActionKey(nPishockAction_zap,actions);
                                        cSHOCKER.setActionKey(nPishockAction_punish,actions);
                                        if(SHOCKER_INDEX>-1){
                                            gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared shocker "+SHOCKER_INDEX+" actions.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                    case lsUnicodeEmotes.aliasCrossMark:
                                        if(gNewUserProfile.cPISHOCK.getShockersSize()==1) {
                                            gNewUserProfile.cPISHOCK.setMainShocker(-1);
                                            gNewUserProfile.cPISHOCK.clearShockers();
                                        }else{
                                            gNewUserProfile.cPISHOCK.remShocker(SHOCKER_INDEX);
                                            if(gNewUserProfile.cPISHOCK.getMainShockerIndex()>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                                gNewUserProfile.cPISHOCK.setMainShocker(gNewUserProfile.cPISHOCK.getShockersSize()-1);
                                            }
                                            saveProfile();
                                        }
                                        menuPiShock(gNewUserProfile.getMember());
                                        break;
                                    case lsUnicodeEmotes.aliasPointRight:
                                        if(SHOCKER_INDEX>-1){
                                            gNewUserProfile.cPISHOCK.setMainShocker(SHOCKER_INDEX);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" as main shocker.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                    case lsUnicodeEmotes.aliasFirstPlace:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKERGAMEWIN.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKERGAMEWIN.hasActions()){
                                                cSHOCKERGAMEWIN.setActions(defaultActions());
                                                cSHOCKERGAMEWIN.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_1());
                                            }
                                            gNewUserProfile.cPISHOCK.setGameWinShocker(cSHOCKERGAMEWIN);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game win shocker code.",llColors.llColorGreen2);
                                        }
                                        menuGame();
                                        break;
                                    case lsUnicodeEmotes.aliasThirdPlace:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKERGAMELOSE.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKERGAMELOSE.hasActions()){
                                                cSHOCKERGAMELOSE.setActions(defaultActions());
                                                cSHOCKERGAMELOSE.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_0());
                                            }
                                            gNewUserProfile.cPISHOCK.setGameLoseShocker(cSHOCKERGAMELOSE.getJSON());
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game lose shocker code.",llColors.llColorGreen2);
                                        }
                                        menuGame();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKER_COLLAR.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKER_COLLAR.hasActions()){
                                                cSHOCKER_COLLAR.setActions(defaultActions());
                                            }
                                            gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as collar shocker code.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKER_CHASTITY.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKER_CHASTITY.hasActions()){
                                                cSHOCKER_CHASTITY.setActions(defaultActions());
                                            }
                                            gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as chastity shocker code.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));

                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                llMessageDelete(message);
                                isMenuLevel=true;
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmInputCode();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCardIndex))){
                                    dmInputName();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasPersonRunning))){
                                    dmInputAction0();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    dmInputAction1();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    dmInputAction2();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    dmInputAction3();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBarOfSoap))){
                                    JSONObject action=new JSONObject();
                                    action.put(nPishockMode,vPishockMode_Vibrate);action.put(nPishockDuration,5);action.put(nPishockItensity,10);
                                    JSONArray actions=new JSONArray();
                                    cSHOCKER.setActionKey(nPishockAction_main,actions);
                                    cSHOCKER.setActionKey(nPishockAction_warn,actions);
                                    cSHOCKER.setActionKey(nPishockAction_zap,actions);
                                    cSHOCKER.setActionKey(nPishockAction_punish,actions);
                                    if(SHOCKER_INDEX>-1){
                                        gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared shocker "+SHOCKER_INDEX+" actions.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    if(gNewUserProfile.cPISHOCK.getShockersSize()==1) {
                                        gNewUserProfile.cPISHOCK.setMainShocker(-1);
                                        gNewUserProfile.cPISHOCK.clearShockers();
                                    }else{
                                        gNewUserProfile.cPISHOCK.remShocker(SHOCKER_INDEX);
                                        if(gNewUserProfile.cPISHOCK.getMainShockerIndex()>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                            gNewUserProfile.cPISHOCK.setMainShocker(gNewUserProfile.cPISHOCK.getShockersSize()-1);
                                        }
                                        saveProfile();
                                    }
                                    menuPiShock(gNewUserProfile.getMember());
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight))){
                                    if(SHOCKER_INDEX>-1){
                                        gNewUserProfile.cPISHOCK.setMainShocker(SHOCKER_INDEX);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" as main shocker.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKERGAMEWIN.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKERGAMEWIN.hasActions()){
                                            cSHOCKERGAMEWIN.setActions(defaultActions());
                                            cSHOCKERGAMEWIN.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_1());
                                        }
                                        gNewUserProfile.cPISHOCK.setGameWinShocker(cSHOCKERGAMEWIN);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game win shocker code.",llColors.llColorGreen2);

                                    }
                                    menuGame();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKERGAMELOSE.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKERGAMELOSE.hasActions()){
                                            cSHOCKERGAMELOSE.setActions(defaultActions());
                                            cSHOCKERGAMELOSE.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_0());
                                        }
                                        gNewUserProfile.cPISHOCK.setGameLoseShocker(cSHOCKERGAMELOSE.getJSON());

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game lose shocker code.",llColors.llColorGreen2);
                                    }
                                    menuGame();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKER_COLLAR.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKER_COLLAR.hasActions()){
                                            cSHOCKER_COLLAR.setActions(defaultActions());
                                        }
                                        gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as collar shocker code.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKER_CHASTITY.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKER_CHASTITY.hasActions()){
                                            cSHOCKER_CHASTITY.setActions(defaultActions());
                                        }
                                        gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as chastity shocker code.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else{
                                    menuShocker();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockerListenerOwner(Message message){
            String fName="[menuShockerOwner]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowUp:
                                        menuPiShock(gNewUserProfile.getMember());
                                        break;
                                    case lsUnicodeEmotes.aliasID:
                                        dmInputCode();
                                        break;
                                    case lsUnicodeEmotes.aliasCardIndex:
                                        dmInputName();
                                        break;
                                    case lsUnicodeEmotes.aliasPersonRunning:
                                        dmInputAction0();
                                        break;
                                    case lsUnicodeEmotes.aliasOne:
                                        dmInputAction1();
                                        break;
                                    case lsUnicodeEmotes.aliasTwo:
                                        dmInputAction2();
                                        break;
                                    case lsUnicodeEmotes.aliasThree:
                                        dmInputAction3();
                                        break;
                                    case lsUnicodeEmotes.aliasBarOfSoap:
                                        JSONObject action=new JSONObject();
                                        action.put(nPishockMode,vPishockMode_Vibrate);action.put(nPishockDuration,5);action.put(nPishockItensity,10);
                                        JSONArray actions=new JSONArray();
                                        cSHOCKER.setActionKey(nPishockAction_main,actions);
                                        cSHOCKER.setActionKey(nPishockAction_warn,actions);
                                        cSHOCKER.setActionKey(nPishockAction_zap,actions);
                                        cSHOCKER.setActionKey(nPishockAction_punish,actions);
                                        if(SHOCKER_INDEX>-1){
                                            gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared shocker "+SHOCKER_INDEX+" actions.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                    case lsUnicodeEmotes.aliasCrossMark:
                                        if(gNewUserProfile.cPISHOCK.getShockersSize()==1) {
                                            gNewUserProfile.cPISHOCK.setMainShocker(-1);
                                            gNewUserProfile.cPISHOCK.clearShockers();
                                        }else{
                                            gNewUserProfile.cPISHOCK.remShocker(SHOCKER_INDEX);
                                            if(gNewUserProfile.cPISHOCK.getMainShockerIndex()>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                                gNewUserProfile.cPISHOCK.setMainShocker(gNewUserProfile.cPISHOCK.getShockersSize()-1);
                                            }
                                            saveProfile();
                                        }
                                        menuPiShock(gNewUserProfile.getMember());
                                        break;
                                    case lsUnicodeEmotes.aliasPointRight:
                                        if(SHOCKER_INDEX>-1){
                                            gNewUserProfile.cPISHOCK.setMainShocker(SHOCKER_INDEX);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" as main shocker.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                    case lsUnicodeEmotes.aliasFirstPlace:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKERGAMEWIN.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKERGAMEWIN.hasActions()){
                                                cSHOCKERGAMEWIN.setActions(defaultActions());
                                                cSHOCKERGAMEWIN.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_1());
                                            }
                                            gNewUserProfile.cPISHOCK.setGameWinShocker(cSHOCKERGAMEWIN);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game win shocker code.",llColors.llColorGreen2);
                                        }
                                        menuGame();
                                        break;
                                    case lsUnicodeEmotes.aliasThirdPlace:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKERGAMELOSE.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKERGAMELOSE.hasActions()){
                                                cSHOCKERGAMELOSE.setActions(defaultActions());
                                                cSHOCKERGAMELOSE.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_0());
                                            }
                                            gNewUserProfile.cPISHOCK.setGameLoseShocker(cSHOCKERGAMELOSE.getJSON());
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game lose shocker code.",llColors.llColorGreen2);
                                        }
                                        menuGame();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolC:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKER_COLLAR.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKER_COLLAR.hasActions()){
                                                cSHOCKER_COLLAR.setActions(defaultActions());
                                            }
                                            gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as collar shocker code.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                    case lsUnicodeEmotes.aliasSymbolD:
                                        if(SHOCKER_INDEX>-1){
                                            cSHOCKER_CHASTITY.setCode(cSHOCKER.getCode());
                                            if(!cSHOCKER_CHASTITY.hasActions()){
                                                cSHOCKER_CHASTITY.setActions(defaultActions());
                                            }
                                            gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);
                                            saveProfile();
                                            lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as chastity shocker code.",llColors.llColorGreen2);
                                        }
                                        menuShocker();
                                        break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmInputCode();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCardIndex))){
                                    dmInputName();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPersonRunning))||name.equalsIgnoreCase(gGlobal.emojis.getEmojiChar(lsUnicodeEmotes.aliasPersonRunning))){
                                    dmInputAction0();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    dmInputAction1();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    dmInputAction2();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    dmInputAction3();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPointRight))){
                                    if(SHOCKER_INDEX>-1){
                                        gNewUserProfile.cPISHOCK.setMainShocker(SHOCKER_INDEX);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("Selected shocker "+SHOCKER_INDEX+" as main shocker for !TARGET."),llColors.llColorGreen2);
                                        lsMessageHelper.lsSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER selected shocker "+SHOCKER_INDEX+" as main shocker."),llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKERGAMEWIN.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKERGAMEWIN.hasActions()){
                                            cSHOCKERGAMEWIN.setActions(defaultActions());
                                            cSHOCKERGAMEWIN.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_1());
                                        }
                                        gNewUserProfile.cPISHOCK.setGameWinShocker(cSHOCKERGAMEWIN);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game win shocker code.",llColors.llColorGreen2);

                                    }
                                    menuGame();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKERGAMELOSE.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKERGAMELOSE.hasActions()){
                                            cSHOCKERGAMELOSE.setActions(defaultActions());
                                            cSHOCKERGAMELOSE.setActionKey(iRestraints.nPishockAction_main,defaultActionTasks_0());
                                        }
                                        gNewUserProfile.cPISHOCK.setGameLoseShocker(cSHOCKERGAMELOSE);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as game lose shocker code.",llColors.llColorGreen2);
                                    }
                                    menuGame();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKER_COLLAR.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKER_COLLAR.hasActions()){
                                            cSHOCKER_COLLAR.setActions(defaultActions());
                                        }
                                        gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as collar shocker code.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))){
                                    if(SHOCKER_INDEX>-1){
                                        cSHOCKER_CHASTITY.setCode(cSHOCKER.getCode());
                                        if(!cSHOCKER_CHASTITY.hasActions()){
                                            cSHOCKER_CHASTITY.setActions(defaultActions());
                                        }
                                        gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Selected shocker "+SHOCKER_INDEX+" code as chastity shocker code.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBarOfSoap))){
                                    JSONObject action=new JSONObject();
                                    action.put(nPishockMode,vPishockMode_Vibrate);action.put(nPishockDuration,5);action.put(nPishockItensity,10);
                                    JSONArray actions=new JSONArray();
                                    cSHOCKER.setActionKey(nPishockAction_main,actions);
                                    cSHOCKER.setActionKey(nPishockAction_warn,actions);
                                    cSHOCKER.setActionKey(nPishockAction_zap,actions);
                                    cSHOCKER.setActionKey(nPishockAction_punish,actions);
                                    if(SHOCKER_INDEX>-1){
                                        gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared shocker "+SHOCKER_INDEX+" actions.",llColors.llColorGreen2);
                                    }
                                    menuShocker();
                                }
                                else{
                                    menuShocker();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuShockerListenerSomebody(Message message){
            String fName="[menuShockerOwner]";
            logger.info(fName);
            try{
                gWaiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                llMessageDelete(message);
                                switch (id){
                                    case lsUnicodeEmotes.aliasArrowUp:
                                        menuPiShock(gNewUserProfile.getMember());
                                        break;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, null);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else{
                                    menuShocker();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private JSONArray defaultActionTasks_1(){
            String fName="[ defaultActionTasks1]";
            logger.info(fName);
            try{
                JSONObject jsonTask=new JSONObject();
                JSONArray jsonTasks=new JSONArray();
                jsonTask.put(nPishockMode,vPishockMode_Vibrate);
                jsonTask.put(nPishockDuration,5);
                jsonTask.put(nPishockItensity,10);
                jsonTasks.put(jsonTask);
                return  jsonTasks;

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONArray();
            }
        }
        private JSONArray defaultActionTasks_2(){
            String fName="[ defaultActionTasks2]";
            logger.info(fName);
            try{
                JSONObject jsonTask=new JSONObject();
                JSONArray jsonTasks=new JSONArray();
                jsonTask.put(nPishockMode,vPishockMode_Beep);
                jsonTask.put(nPishockDuration,1);
                jsonTask.put(nPishockItensity,0);
                jsonTasks.put(jsonTask);
                return  jsonTasks;

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONArray();
            }
        }
        private JSONArray defaultActionTasks_0(){
            String fName="[ defaultActionTasks0]";
            logger.info(fName);
            try{
                JSONObject jsonTask=new JSONObject();
                JSONArray jsonTasks=new JSONArray();
                jsonTask.put(nPishockMode,vPishockMode_Shock);
                jsonTask.put(nPishockDuration,5);
                jsonTask.put(nPishockItensity,10);
                jsonTasks.put(jsonTask);
                return  jsonTasks;

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONArray();
            }
        }
        private JSONObject defaultActions(){
            String fName="[ defaultActions]";
            logger.info(fName);
            try{
                    JSONObject jsonActions=new JSONObject();
                    jsonActions.put(nPishockAction_warn,defaultActionTasks_1());
                    jsonActions.put(nPishockAction_zap,defaultActionTasks_0());
                    jsonActions.put(nPishockAction_punish,defaultActionTasks_0());
                    jsonActions.put(nPishockAction_reward,defaultActionTasks_1());
                    jsonActions.put(nPishockAction_main,defaultActionTasks_0());
                    logger.info(fName+"jsonActions="+jsonActions.toString());
                    return  jsonActions;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  new JSONObject();
            }
        }
        private void dmAddShocker(int category, int action, entityShockerAction TASK, entityShockerActions ACTIONS){
            String fName="[dmAddShocker]";
            logger.info(fName+"category="+category+"action="+action);
            try{
                if(TASK==null){
                    TASK=new entityShockerAction();
                }
                if(ACTIONS==null){
                    ACTIONS=new entityShockerActions();
                }
                if(action==-1){
                    cSHOCKER=new entityShocker();
                    cSHOCKER.setActions(defaultActions());
                    action=0;
                }
                else if(action==-2||action==6){
                    switch (category){
                        case 0:
                            //main shockers
                            gNewUserProfile.cPISHOCK.addShocker(cSHOCKER);
                            if(gNewUserProfile.cPISHOCK.getMainShockerIndex()<0){
                                gNewUserProfile.cPISHOCK.setMainShocker(0);
                            }
                            saveProfile();
                            break;
                        case 3:
                            gNewUserProfile.cPISHOCK.setGameLoseShocker(cSHOCKER);
                            saveProfile();
                            break;
                        case 4:
                            gNewUserProfile.cPISHOCK.setGameWinShocker(cSHOCKER);
                            saveProfile();
                            break;
                    }
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                if(action==0){
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a code.", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.isBlank()||content.equalsIgnoreCase("!skip")){
                                        dmAddShocker(category,1,null,null);return;
                                    }
                                    else if(content.equalsIgnoreCase("!cancel")){
                                        menuPiShock(gNewUserProfile.getMember());return;
                                    }else if(content.equalsIgnoreCase("!clear")){
                                        cSHOCKER.setCode("");
                                    }
                                    else{
                                        cSHOCKER.setCode(content);
                                    }
                                    dmAddShocker(category,1,null,null);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                    dmAddShocker(category,-2,null,null);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                                dmAddShocker(category,-2,null,null);
                            });
                }
                if(action==1){
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a name.", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.isBlank()||content.equalsIgnoreCase("!skip")){
                                        dmAddShocker(category,2,null,null);return;
                                    }
                                    else if(content.equalsIgnoreCase("!cancel")){
                                        menuPiShock(gNewUserProfile.getMember());return;
                                    }else if(content.equalsIgnoreCase("!clear")){
                                        cSHOCKER.setName("");
                                    }
                                    else{
                                        cSHOCKER.setName(content);
                                    }
                                    dmAddShocker(category,2,null,null);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                    dmAddShocker(category,-2,null,null);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                                dmAddShocker(category,-2,null,null);
                            });
                }
                else if(action==2){
                    //option
                    desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+" beep";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+" vibrate";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" shock";
                    embed.setDescription("Please select an option"+desc);
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                    entityShockerAction finalTask = TASK;
                    entityShockerActions finalACTIONS3= ACTIONS;
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                        menuShocker();
                                        return;
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell))){
                                        finalTask.setMode(vPishockMode_Beep);

                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){
                                        finalTask.setMode(vPishockMode_Vibrate);

                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                        finalTask.setMode(vPishockMode_Shock);
                                    }else{
                                        dmAddShocker(0,2, finalTask, finalACTIONS3);
                                        return;
                                    }
                                    dmAddShocker(category,3, finalTask, finalACTIONS3);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                    menuShocker();
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }
                else if(action==3){
                    //duration
                    boolean requireItensity=false;
                    try {
                        String  mode=TASK.getMode();
                        int duration=0;
                        int itensity=0;
                        if(TASK.getOp()==2){
                            duration=TASK.getDuration();
                            embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                        }else
                        if(TASK.getOp()==1||TASK.getOp()==0){
                            duration=TASK.getDuration();
                            itensity=TASK.getIntensity();
                            requireItensity=true;
                            embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                        }else{
                            embed.addField("Current line", "(invalid)",false);
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                        embed.addField("Current line", "(error)",false);
                    }
                    embed.setDescription("Please enter duration from 1-15. This number represents seconds.");
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    boolean finalRequireItensity = requireItensity;
                    entityShockerAction finalTASK = TASK;
                    entityShockerActions finalACTIONS2= ACTIONS;
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().getIdLong()==gUser.getIdLong(),
                            e -> {
                                try {
                                    String content=e.getMessage().getContentRaw();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                        return;
                                    }
                                    int value=0;
                                    try {
                                        value=Integer.parseInt(content);
                                    }catch (Exception e3){
                                        logger.error(fName + ".exception=" + e3);
                                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be a number!");
                                        dmAddShocker(category,3, finalTASK, finalACTIONS2);
                                        return;
                                    }
                                    logger.info(fName+".value="+value);
                                    if(value<1||value>15){
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be between 1-15!");
                                        dmAddShocker(category,3, finalTASK, finalACTIONS2);
                                        return;
                                    }
                                    finalTASK.setDuration(value);
                                    if(finalRequireItensity){
                                        dmAddShocker(category,4, finalTASK, finalACTIONS2);
                                    }else{
                                        dmAddShocker(category,5, finalTASK, finalACTIONS2);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    dmAddShocker(category,3, finalTASK, finalACTIONS2);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }
                else if(action==4){
                    try {
                        String  mode=TASK.getMode();
                        int duration=0;
                        int itensity=0;
                        if(TASK.getOp()==2){
                            duration=TASK.getDuration();
                            embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                        }else
                        if(TASK.getOp()==1||TASK.getOp()==0){
                            duration=TASK.getDuration();
                            itensity=TASK.getIntensity();
                            embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                        }else{
                            embed.addField("Current line", "(invalid)",false);
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                        embed.addField("Current line", "(error)",false);
                    }
                    embed.setDescription("Please enter intensity from 1-100.");
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    entityShockerAction finalTASK1 = TASK;
                    entityShockerActions finalACTIONS1 = ACTIONS;
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().getIdLong()==gUser.getIdLong(),
                            e -> {
                                try {
                                    String content=e.getMessage().getContentRaw();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                        return;
                                    }
                                    int value=0;
                                    try {
                                        value=Integer.parseInt(content);
                                    }catch (Exception e3){
                                        logger.error(fName + ".exception=" + e3);
                                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be a number!");
                                        dmAddShocker(category,4, finalTASK1, finalACTIONS1);
                                        return;
                                    }
                                    logger.info(fName+".value="+value);
                                    if(value<1||value>100){
                                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be between 1-100!");
                                        dmAddShocker(category,4, finalTASK1, finalACTIONS1);
                                        return;
                                    }
                                    finalTASK1.setIntensity(value);
                                    dmAddShocker(category,5, finalTASK1, finalACTIONS1);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    dmAddShocker(category,4, finalTASK1, finalACTIONS1);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }
                else if(action==5){
                    try {
                        String  mode=TASK.getMode();
                        int duration=0;
                        int itensity=0;
                        if(TASK.getOp()==2){
                            duration=TASK.getDuration();
                            embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                        }else
                        if(TASK.getOp()==1||TASK.getOp()==0){
                            duration=TASK.getDuration();
                            itensity=TASK.getIntensity();
                            embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                        }else{
                            embed.addField("Current line", "(invalid)",false);
                        }
                    }catch (Exception ex){
                        logger.error(fName + ".exception=" + ex);
                        logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                        embed.addField("Current line", "(error)",false);
                    }
                    ACTIONS.addAction(TASK);
                    cSHOCKER.setActionKey(nPishockAction_main,ACTIONS);
                    embed.setDescription("Do you wish to add a new line?");
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                    entityShockerActions finalACTIONS = ACTIONS;
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    JSONObject task=new JSONObject();
                                    llMessageDelete(message);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                        return;
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                        task.put(nPishockMode,vPishockMode_Beep);
                                        dmAddShocker(category,2, null, finalACTIONS);
                                    }else
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                        dmAddShocker(category,6, null, finalACTIONS);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                            });
                }
                else if(action==6){
                    embed.setDescription("Added new shocker successfully!");
                    llSendMessageResponse(gUser,embed);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmAddDefaultShockerIfNotAdded(String code){
            String fName="[dmAddDefaultShockerIfNotAdded]";
            logger.info(fName+"code="+code);
            try{
                getShockers();
                if(SHOCKER_INDEX>=0){
                    logger.info(fName+"has a default shocker>return");
                    return;
                }
                JSONObject jsonTask=new JSONObject();
                JSONArray jsonTasks=new JSONArray();
                JSONObject jsonShocker=new JSONObject();
                jsonTask.put(nPishockMode,vPishockMode_Beep);
                jsonTask.put(nPishockDuration,5);
                jsonTask.put(nPishockItensity,0);
                jsonTasks.put(jsonTask);
                JSONObject jsonActions=new JSONObject();
                jsonActions.put(nPishockAction_warn,jsonTasks);
                jsonActions.put(nPishockAction_zap,jsonTasks);
                jsonActions.put(nPishockAction_punish,jsonTasks);
                jsonShocker.put(nPishockActions,jsonActions);
                jsonShocker.put(nPishockCode,code);
                jsonShocker.put(nPishockName,code);
                cSHOCKER=new entityShocker(jsonShocker);
                gNewUserProfile.cPISHOCK.addShocker(cSHOCKER);
                gNewUserProfile.cPISHOCK.setMainShocker(gNewUserProfile.cPISHOCK.getShockersSize()-1);
                saveProfile();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputCode(){
            String fName="[dmInputCode]";
            logger.info(fName);
            try{
                String value=cSHOCKER.getCode();Message message;
                if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current code is "+value+".\nPlease enter a new code or enter !cancel to leave it as it is or enter !clear to remove it.", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter a code.", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuShocker();return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    cSHOCKER.setCode("");
                                }
                                else{
                                    cSHOCKER.setCode(content);
                                }
                                if(SHOCKER_INDEX>-1){
                                    gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);

                                    saveProfile();
                                    menuShocker();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputName(){
            String fName="[dmInputName]";
            logger.info(fName);
            try{
                String value="<blank>";Message message;
                if(cSHOCKER.hasName()){
                    value=cSHOCKER.getName();
                }
                if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current name is "+value+".\nPlease enter a new name or enter !cancel to leave it as it is or enter !clear to remove it.", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter a name.", llColorBlue1);
                }
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuShocker();return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    cSHOCKER.setName("");
                                }
                                else{
                                    cSHOCKER.setName(content);
                                }
                                if(SHOCKER_INDEX>-1){
                                    gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);

                                    saveProfile();
                                    menuShocker();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction(int group){
            String fName="[dmInputAction]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                entityShockerActions shockerActions= new entityShockerActions();
                switch (SHOCKER_TYPE){
                    case 0:
                        switch (group){
                            case 1:
                                shockerActions=cSHOCKER.getActions(nPishockAction_warn);
                                break;
                            case 2:
                                shockerActions=cSHOCKER.getActions(nPishockAction_zap);
                                break;
                            case 3:
                                shockerActions=cSHOCKER.getActions(nPishockAction_punish);
                                break;
                            case 4:
                                shockerActions=cSHOCKER.getActions(nPishockAction_reward);
                                break;
                            case 0:
                                shockerActions=cSHOCKER.getActions(nPishockAction_main);
                                break;
                        }
                        break;
                    case 3:
                        shockerActions=cSHOCKERGAMELOSE.getActions(nPishockAction_main);

                        break;
                    case 4:
                        shockerActions=cSHOCKERGAMEWIN.getActions(nPishockAction_main);
                        break;
                }
                dmInputAction_Option(group,shockerActions);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction_Option(int group, entityShockerActions ACTIONS){
            String fName="[dmInputAction_Option]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                try {
                    JSONArray jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+" beep";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+" vibrate";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" shock";
                embed.setDescription("Please select an option"+desc);
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileInputActionOption);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                dmInputAction_OptionListen(message,group, ACTIONS);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction_OptionListen(Message message,int group, entityShockerActions ACTIONS){
            String fName="[dmInputAction_OptionListen]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            entityShockerAction task=new entityShockerAction();
                            switch (id){
                                case lsUnicodeEmotes.aliasArrowUp:
                                    menuSelect();
                                    break;
                                case lsUnicodeEmotes.aliasCrossMark:
                                    dmInputAction_Save(group,new entityShockerActions());
                                    menuSelect();
                                    break;
                                case lsUnicodeEmotes.aliasBell:
                                    task.setMode(vPishockMode_Beep);
                                    dmInputAction_Duration(group, ACTIONS,task);
                                    break;
                                case lsUnicodeEmotes.aliasVibrationMode:
                                    task.setMode(vPishockMode_Vibrate);
                                    dmInputAction_Duration(group, ACTIONS,task);
                                    break;
                                case lsUnicodeEmotes.aliasZap:
                                    task.setMode(vPishockMode_Shock);
                                    dmInputAction_Duration(group, ACTIONS,task);
                                    break;
                                default:
                                    dmInputAction_Option(group,ACTIONS);
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            entityShockerAction task=new entityShockerAction();
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSelect();
                                return;
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                dmInputAction_Save(group,new entityShockerActions());
                                menuSelect();
                                return;
                            }
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell))){
                                task.setMode(vPishockMode_Beep);

                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){
                                task.setMode(vPishockMode_Vibrate);

                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                task.setMode(vPishockMode_Shock);
                            }else{
                                dmInputAction_Option(group,ACTIONS);
                                return;
                            }
                            dmInputAction_Duration(group, ACTIONS,task);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        }
        private void dmInputAction_Duration(int group, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_Duration]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                try {
                    JSONArray jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                boolean requireItensity=false;
                try {
                   String  mode=TASK.getMode();
                   int duration=0;
                   int itensity=0;
                   if(TASK.getOp()==2){
                       duration=TASK.getDuration();
                       embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                   }else
                   if(TASK.getOp()==1||TASK.getOp()==0){
                       duration=TASK.getDuration();
                       itensity=TASK.getIntensity();
                       requireItensity=true;
                       embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                   }else{
                       embed.addField("Current line", "(invalid)",false);
                   }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Current line", "(error)",false);
                }
                embed.setDescription("Please enter duration from 1-15. This number represents seconds.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                boolean finalRequireItensity = requireItensity;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> (!e.getAuthor().isBot()&&e.getAuthor().getIdLong()==gUser.getIdLong()&&message!=null),
                        e -> {
                            try {
                                String content=e.getMessage().getContentRaw();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    menuSelect();
                                    return;
                                }
                                int value=0;
                                try {
                                    value=Integer.parseInt(content);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be a number!");
                                    dmInputAction_Duration(group, ACTIONS,TASK);
                                    return;
                                }
                                logger.info(fName+".value="+value);
                                if(value<1||value>15){
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be between 1-15!");
                                    dmInputAction_Duration(group, ACTIONS,TASK);
                                    return;
                                }
                                TASK.setDuration(value);
                                if(finalRequireItensity){
                                    dmInputAction_Itensity(group, ACTIONS,TASK);
                                }else{
                                    dmInputAction_AskNewLine(group, ACTIONS,TASK);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction_Itensity(int group, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_Itensity]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                try {
                    JSONArray jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                try {
                    String  mode=TASK.getMode();
                    int duration=0;
                    int itensity=0;
                    if(TASK.getOp()==2){
                        duration=TASK.getDuration();
                        embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                    }else
                    if(TASK.getOp()==1||TASK.getOp()==0){
                        duration=TASK.getDuration();
                        itensity=TASK.getIntensity();
                        embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                    }else{
                        embed.addField("Current line", "(invalid)",false);
                    }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Current line", "(error)",false);
                }
                embed.setDescription("Please enter intensity from 1-100.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> (!e.getAuthor().isBot()&&e.getAuthor().getIdLong()==gUser.getIdLong()&&message!=null),
                        e -> {
                            try {
                                String content=e.getMessage().getContentRaw();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    menuSelect();
                                    return;
                                }
                                int value=0;
                                try {
                                    value=Integer.parseInt(content);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be a number!");
                                    dmInputAction_Itensity(group, ACTIONS,TASK);
                                    return;
                                }
                                logger.info(fName+".value="+value);
                                if(value<1||value>100){
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be between 1-100!");
                                    dmInputAction_Itensity(group, ACTIONS,TASK);
                                    return;
                                }
                                TASK.setIntensity(value);
                                dmInputAction_AskNewLine(group, ACTIONS,TASK);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction_AskNewLine(int group, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_AskNewLine]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                try {
                    JSONArray jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                try {
                    String  mode=TASK.getMode();
                    int duration=0;
                    int itensity=0;
                    if(TASK.getOp()==2){
                        duration=TASK.getDuration();
                        embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                    }else
                    if(TASK.getOp()==1||TASK.getOp()==0){
                        duration=TASK.getDuration();
                        itensity=TASK.getIntensity();
                        embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                    }else{
                        embed.addField("Current line", "(invalid)",false);
                    }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Current line", "(error)",false);
                }
                ACTIONS.put(TASK);
                embed.setDescription("Do you wish to add a new line?");
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileInputActionAskNewLine);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                dmInputAction_AskNewLineListen(message,group, ACTIONS, TASK);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction_AskNewLineListen(Message message,int group, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_AskNewLine]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            entityShockerAction task=new entityShockerAction();
                            switch (id){
                                case lsUnicodeEmotes.aliasGreenCircle:
                                    dmInputAction_Option(group,ACTIONS);
                                    break;
                                case lsUnicodeEmotes.aliasRedCircle:
                                    dmInputAction_Save(group, ACTIONS);
                                    menuSelect();
                                    break;
                                case lsUnicodeEmotes.aliasCrossMark:
                                    logger.info(fName+"canceled");
                                    break;
                                default:
                                    dmInputAction_AskNewLine(group,ACTIONS,TASK);
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, null);
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                return;
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                dmInputAction_Option(group,ACTIONS);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                dmInputAction_Save(group, ACTIONS);
                                menuSelect();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});


        }
        private void dmInputAction_Save(int group, entityShockerActions ACTIONS){
            String fName="[dmInputAction_Save]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                switch (SHOCKER_TYPE){
                    case 0:
                        switch (group){
                            case 1:
                                cSHOCKER.setActionKey(nPishockAction_warn,ACTIONS);
                                break;
                            case 2:
                                cSHOCKER.setActionKey(nPishockAction_zap,ACTIONS);
                                break;
                            case 3:
                                cSHOCKER.setActionKey(nPishockAction_punish,ACTIONS);
                                break;
                            case 4:
                                cSHOCKER.setActionKey(nPishockAction_reward,ACTIONS);
                                break;
                            case 0:
                                cSHOCKER.setActionKey(nPishockAction_main,ACTIONS);
                                break;
                        }
                        if(SHOCKER_INDEX>-1){
                            gNewUserProfile.cPISHOCK.setShocker(SHOCKER_INDEX,cSHOCKER);

                            saveProfile();
                        }
                    break;
                    case 3:
                        cSHOCKERGAMELOSE.setActionKey(nPishockAction_main,ACTIONS);
                        gNewUserProfile.cPISHOCK.setGameLoseShocker(cSHOCKERGAMELOSE);

                        saveProfile();
                        break;
                    case 4:
                        cSHOCKERGAMEWIN.setActionKey(nPishockAction_main,ACTIONS);
                        gNewUserProfile.cPISHOCK.setGameWinShocker(cSHOCKERGAMEWIN);

                        saveProfile();
                        break;
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmInputAction0(){
            String fName="[dmInputAction1]";
            logger.info(fName);dmInputAction(0);
        }
        private void dmInputAction1(){
            String fName="[dmInputAction1]";
            logger.info(fName);dmInputAction(1);
        }
        private void dmInputAction2(){
            String fName="[dmInputAction2]";
            logger.info(fName); dmInputAction(2);
        }
        private void dmInputAction3(){
            String fName="[dmInputAction3]";
            logger.info(fName); dmInputAction(3);
        }
        String gUrl="https://do.pishock.com/api/apioperate";
        String fieldUsername="Username",fieldName="Name",fieldCode="Code",fieldIntensity="Intensity",fieldDuration="Duration",fieldApikey="Apikey",fieldOp="Op";
        String vFieldName="mf";
        private boolean selectDefaultShoker(int index){
            String fName="[selectDefaultShoker]";
            logger.info(fName);
            try{
                logger.info(fName+"index="+index);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return false;}
                getShockers();
                cSHOCKER=gNewUserProfile.cPISHOCK.getShocker(index);
                SHOCKER_INDEX=index;
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                cSHOCKER=new entityShocker();
                SHOCKER_INDEX=-2;
                return  false;
            }
        }
        private entityShocker getShoker(int index){
            String fName="[getShoker]";
            logger.info(fName);
            try{
                logger.info(fName+"index="+index);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return null;}
                getShockers();
                return gNewUserProfile.cPISHOCK.getShocker(index);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return new entityShocker();
            }
        }
        private void doAction(entityShocker shocker, int group){
            String fName="[doAction]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}

                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    if(gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strTargetCantDueNotenabled), llColorRed);
                    }else{
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strCantDueNotenabled), llColorRed);
                    }
                    return;
                }
                logger.info(fName + "shocker=" + shocker.toString());
                if(!shocker.hasActions()){
                    logger.info(fName + "nPishockActions is nonexistent");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer("This shocker has no actions to send."), llColorRed);
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                JSONObject jsonObject=new JSONObject();
                a.config().verifySsl(false);
                entityShockerActions tasks=new entityShockerActions();
                switch (group){
                    case 1:
                        tasks=shocker.getActions(nPishockAction_warn);
                        break;
                    case 2:
                        tasks=shocker.getActions(nPishockAction_zap);
                        break;
                    case 3:
                        tasks=shocker.getActions(nPishockAction_punish);
                        break;
                    case 4:
                        tasks=shocker.getActions(nPishockAction_reward);
                        break;
                    case 0:
                       tasks=shocker.getActions(nPishockAction_main);
                        break;
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.info(fName + "tasks is null or empty");
                    embed.setDescription("No action!");embed.setColor(llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                logger.info(fName+"tasks.length="+tasks.length());
                logger.info(fName+"tasks="+tasks.toString());
                if(doHttp(shocker,tasks)){
                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strPressButton1), llColorYellow_Cream);
                        llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), sRTitle, stringReplacer(strPressButton2), llColorYellow_Cream);
                    }else{
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strTargetPressButton1), llColorYellow_Cream);
                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), sRTitle, stringReplacer(strTargetPressButton2), llColorYellow_Cream);
                        llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), sRTitle, stringReplacer(strTargetPressButton3), llColorYellow_Cream);
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), gUser,sRTitle, e.toString());
            }
        }
        private void doAction0(entityShocker shocker){
            String fName="[doAction0]";
            logger.info(fName);
            doAction(shocker,0);
            postHTTPErrorMessages(gTextChannel);
        }
        Message messageOwner=null,messageUserInfoAsk=null;User messageOwnerUser=null;
        private void doAction(entityShocker shocker, int group, Member mtarget, boolean isBypass){
            String fName="[doAction1]";
            logger.info(fName);
            try{
                logger.info(fName+"group="+group);
                if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}

                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    if(gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strTargetCantDueNotenabled), llColorRed);
                    }else{
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strCantDueNotenabled), llColorRed);
                    }

                    return;
                }
                if(!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.info(fName + ".can't use do to white&black list");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock. strCantUseDueList), llColorRed);
                    return;
                }
                if(!isBypass&&gAskHandlingHelper.isAsk()){
                    logger.info(fName + ".requesting update restraint");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.target_ask,"!USER is asking you if they shock you by your shocker.");
                    gAskHandlingHelper.setStr(gAskHandlingHelper.owner_ask,"!USER is asking if they can shock !TARGET by their shocker.");
                    gAskHandlingHelper.doAsk(() -> {doAction(shocker,group,mtarget,true);});
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                JSONObject jsonObject=new JSONObject();
                a.config().verifySsl(false);
                entityShockerActions tasks=new entityShockerActions();
                logger.info(fName+"shocker="+shocker.toString());
                if(!shocker.hasActions()){
                    logger.info(fName + "nPishockActions is nonexistent");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer("This shocker has no actions to send."), llColorRed);
                    return;
                }
                switch (group){
                    case 1:
                        tasks=shocker.getActions(nPishockAction_warn);
                        break;
                    case 2:
                        tasks=shocker.getActions(nPishockAction_zap);
                        break;
                    case 3:
                        tasks=shocker.getActions(nPishockAction_punish);
                        break;
                    case 4:
                       tasks=shocker.getActions(nPishockAction_reward);
                        break;
                    case 0:
                        tasks=shocker.getActions(nPishockAction_main);
                        break;
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.info(fName + "tasks is null or empty");
                    embed.setDescription("No action!");embed.setColor(llColorRed_Barn);
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                logger.info(fName+"tasks.length="+tasks.length());
                logger.info(fName+"tasks="+tasks.toString());
                if(doHttp(shocker,tasks)){
                    if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strPressButton1), llColorYellow_Cream);
                        llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), sRTitle, stringReplacer(strPressButton2), llColorYellow_Cream);
                    }else{
                        llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strTargetPressButton1), llColorYellow_Cream);
                        llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(), sRTitle, stringReplacer(strTargetPressButton2), llColorYellow_Cream);
                        llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), sRTitle, stringReplacer(strTargetPressButton3), llColorYellow_Cream);
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void doAction0(entityShocker shocker, Member mtarget, boolean isBypass){
            String fName="[doAction1]";
            logger.info(fName);
            doAction(shocker,0,mtarget,isBypass);
            postHTTPErrorMessages(gTextChannel);
        }



        private boolean doHttp(entityShocker shocker, entityShockerActions tasks){
            String fName="[doHttp]";
            logger.info(fName);
            try{
                logger.info(fName+"readFile="+ getConfigValues());
                boolean result=false;
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                JSONObject jsonObject=new JSONObject();
                logger.info(fName+"shocker="+shocker.getJSON().toString());
                logger.info(fName+"url="+gUrl);
                int i=0;

                if(!shocker.hasCode()){
                    logger.info(fName+"code is missing>return");
                    return false;
                }
                logger.info(fName+"tasks="+tasks.getJSON().toString());
                while(i<tasks.length()&&i<iMaxTask){
                    try {
                        logger.info(fName+"prepare");
                        JSONObject task=tasks.getJSONObject(i);
                        jsonObject=new JSONObject();
                        jsonObject.put(fieldUsername,gPiUsername);
                        jsonObject.put(fieldApikey,gPiApiKey);
                        jsonObject=getProfileAuthofication(jsonObject);
                        jsonObject.put(fieldCode,shocker.getCode());
                        jsonObject.put(fieldName,gUser.getName());
                        logger.info(fName+"task["+i+"="+task.toString());
                        switch (task.getString(nPishockMode).toLowerCase()){
                            case vPishockMode_Beep:
                                jsonObject.put(fieldDuration,task.getInt(nPishockDuration));
                                jsonObject.put(fieldOp,2);
                                break;
                            case vPishockMode_Vibrate:
                                jsonObject.put(fieldIntensity,task.getInt(nPishockItensity));
                                jsonObject.put(fieldDuration,task.getInt(nPishockDuration));
                                jsonObject.put(fieldOp,1);
                                break;
                            case vPishockMode_Shock:
                                jsonObject.put(fieldIntensity,task.getInt(nPishockItensity));
                                jsonObject.put(fieldDuration,task.getInt(nPishockDuration));
                                jsonObject.put(fieldOp,0);
                                break;
                        }
                        logger.info(fName+"jsonObject="+jsonObject.toString());
                        if(gDebugSend){
                            logger.info(fName + ".debug");
                            addNewLine2HTTPErrorMessages(jsonObject.toString());
                        }else{
                            HttpResponse<String> jsonResponse =a.post(gUrl)
                                    .header("accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .body(jsonObject.toString())
                                    .asString();
                            logger.info(fName+".status ="+jsonResponse.getStatus());
                            logger.info(fName+".body ="+ jsonResponse.getBody());
                            if(jsonResponse.getStatus()!=200){
                                addNewLine2HTTPErrorMessages(jsonResponse.getBody());
                            }
                            else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                                addNewLine2HTTPErrorMessages(jsonResponse.getBody());
                            }
                            else{
                                loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                                result=true;
                            }
                            if(i<tasks.length()-1){
                                if(task.getInt(nPishockDuration)<=0){
                                    logger.info(fName+"sleep=15");
                                    //Thread.sleep(15000);
                                    Thread.sleep(20000);
                                }else{
                                    logger.info(fName+"sleep="+task.getInt(nPishockDuration));
                                    //Thread.sleep(task.getInt(nPishockDuration)*1000+5000);
                                    Thread.sleep(20000);
                                }
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        addNewLine2HTTPErrorMessages(e.toString());
                    }
					i++;
                }
                if(HTTPErrorMessages.length()>0){
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+HTTPErrorMessages.toString());
                }
                logger.info(fName+"result="+result);
                return  result;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        StringBuilder HTTPErrorMessages=new StringBuilder();
        private void postHTTPErrorMessages(TextChannel textChannel){
            String fName="[postHTTPErrorMessages]";
            logger.info(fName);
            try{
                if(HTTPErrorMessages.length()==0){
                    logger.info(fName+"HTTPErrorMessages lenght ==0>nothing to post");
                    return;
                }
                logger.info(fName+"HTTPErrorMessages="+HTTPErrorMessages.toString());
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorRed_Barn);
                HTTPErrorMessages.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong()&&gMember.getIdLong()!=gGuild.getSelfMember().getIdLong())HTTPErrorMessages.append("\nBy:"+gMember.getAsMention());
                embed.setTitle(sRTitle);
                embed.setDescription(HTTPErrorMessages.toString());
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(textChannel),embed,secondsBeforeDelete);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void postHTTPErrorMessages(TextChannel textChannel,Member target){
            String fName="[postHTTPErrorMessages]";
            logger.info(fName);
            try{
                if(HTTPErrorMessages.length()==0){
                    logger.info(fName+"HTTPErrorMessages lenght ==0>nothing to post");
                    return;
                }
                logger.info(fName+"HTTPErrorMessages="+HTTPErrorMessages.toString());
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorRed_Barn);
                if(target!=null)HTTPErrorMessages.append("\nTarget:"+target.getAsMention());
                else  HTTPErrorMessages.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong()&&gMember.getIdLong()!=gGuild.getSelfMember().getIdLong())HTTPErrorMessages.append("\nBy:"+gMember.getAsMention());
                embed.setTitle(sRTitle);
                embed.setDescription(HTTPErrorMessages.toString());
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(textChannel),embed,secondsBeforeDelete);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void postHTTPErrorMessages(TextChannel textChannel,Member target,Member author){
            String fName="[postHTTPErrorMessages]";
            logger.info(fName);
            try{
                if(HTTPErrorMessages.length()==0){
                    logger.info(fName+"HTTPErrorMessages lenght ==0>nothing to post");
                    return;
                }
                logger.info(fName+"HTTPErrorMessages="+HTTPErrorMessages.toString());
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorRed_Barn);
                if(target!=null)HTTPErrorMessages.append("\nTarget:"+target.getAsMention());
                else HTTPErrorMessages.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                if(author!=null)HTTPErrorMessages.append("\nBy:"+author.getAsMention());
                else if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong()&&gMember.getIdLong()!=gGuild.getSelfMember().getIdLong())HTTPErrorMessages.append("\nBy:"+gMember.getAsMention());
                embed.setTitle(sRTitle);
                embed.setDescription(HTTPErrorMessages.toString());
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(textChannel),embed,secondsBeforeDelete);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void add2HTTPErrorMessages(String message){
            String fName="[add2HTTPErrorMessages]";
            logger.info(fName);
            try{
                logger.info(fName+"message="+message);
                HTTPErrorMessages.append(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void addNewLine2HTTPErrorMessages(String message){
            String fName="[addNewLine2HTTPErrorMessages]";
            logger.info(fName);
            try{
                logger.info(fName+"message="+message);
                HTTPErrorMessages.append("\n").append(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }

        private void menuSpecialShocker(int link){
            String fName="[menuSpecialShocker]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                //getShockers();
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    menuSpecialShockerWearer(link);
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuSpecialShockerOwner(link);
                    }else{
                        menuSpecialShockerSomebody(link);
                    }

                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuSpecialShockerWearer(int link){
            String fName="[menuSpecialShockerWearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc3=new StringBuilder();
                isMenuLevel=true;indexMenuLevel=4;optionMenuLevel=link;isWearerAccessLimited=false;
                boolean isEnabled=false;
                try{
                    logger.info(fName+"link="+link);
                    switch (link){
                        case 1:
                            logger.info(fName+"cSHOCKER_COLLAR="+cSHOCKER_COLLAR.getJSON().toString());
                            cSHOCKER=cSHOCKER_COLLAR;
                            SHOCKER_TYPE=1;
                            break;
                        case 2:
                            logger.info(fName+"cSHOCKER_CHASTITY="+cSHOCKER_CHASTITY.getJSON().toString());
                            cSHOCKER=cSHOCKER_CHASTITY;
                            SHOCKER_TYPE=2;
                            break;
                    }
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    desc+="\ncode: "+cSHOCKER.getCode();
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward (null)";
                    }
                } catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    desc+="\nError shocker!";
                }
                switch (link){
                    case 1:
                        embed.addField("Collar Shocker",desc,false);
                        break;
                    case 2:
                        embed.addField("Chastity Shocker",desc,false);
                        break;
                    default:
                        embed.addField("Shocker",desc,false);
                        break;
                }
                if(link==1){
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4CollarEnabled();
                }
                else if(link==2){
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4ChastityEnabled();
                }
                desc="\n:id: enter code";
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc+="\n:one: enter action warn";
                desc+="\n:two: enter action punish";
                desc+="\n:three: enter action reward";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" clear all actions";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye)+" to test out";
                if(isEnabled){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                }
                embed.setDescription(desc);
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".is set to owner access");
                    embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                    isWearerAccessLimited =true;
                }else
                if(!gIsOverride&&isAccess2Public(gNewUserProfile.gUserProfile)){
                    logger.info(fName + ".is set to public access");
                    embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                    isWearerAccessLimited =true;
                }
                Message message=llSendMessageResponse(gUser,embed);
                if(isWearerAccessLimited){
                    if(!cSHOCKER.hasCode())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                    if(cSHOCKER.getActions(nPishockAction_warn).isEmpty())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    if(cSHOCKER.getActions(nPishockAction_punish).isEmpty())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    if(cSHOCKER.getActions(nPishockAction_reward).isEmpty())lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                }

                if(isEnabled){
                    if(!isWearerAccessLimited)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmSpecialInputCode(link);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    dmSpecialInputActions(link,0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    dmSpecialInputActions(link,1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    dmSpecialInputActions(link,2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                    if(link==1){
                                        gNewUserProfile.cPISHOCK.setShock4CollarEnabled(true);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                    else if(link==2){
                                        gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(true);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                    if(link==1){
                                        gNewUserProfile.cPISHOCK.setShock4CollarEnabled(false);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                    else if(link==2){
                                        gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(false);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    JSONObject action=new JSONObject();
                                    action.put(nPishockMode,vPishockMode_Vibrate);action.put(nPishockDuration,5);action.put(nPishockItensity,10);
                                    JSONArray actions=new JSONArray();
                                    actions.put(action);
                                    if(link==1){
                                        cSHOCKER_COLLAR.setActionKey(nPishockAction_warn,actions);
                                        cSHOCKER_COLLAR.setActionKey(nPishockAction_punish,actions);
                                        cSHOCKER_COLLAR.setActionKey(nPishockAction_reward,actions);
                                        gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared collar shocker actions.",llColors.llColorGreen2);
                                    }
                                    else if(link==2){
                                        cSHOCKER_CHASTITY.setActionKey(nPishockAction_warn,actions);
                                        cSHOCKER_CHASTITY.setActionKey(nPishockAction_punish,actions);
                                        cSHOCKER_CHASTITY.setActionKey(nPishockAction_reward,actions);
                                        gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared chastity shocker actions.",llColors.llColorGreen2);
                                    }
                                    menuSpecialShocker(link);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                                    menuSpecialShockerWearer_Test(link);
                                }
                                else{
                                    menuSpecialShocker(link);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuSpecialShockerWearer_Test(int link){
            String fName="[menuSpecialShockerWearer_Test]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc3=new StringBuilder();
                isMenuLevel=true;indexMenuLevel=4;optionMenuLevel=link;isWearerAccessLimited=false;
                boolean isEnabled=false;
                try{
                    switch (link){
                        case 1:
                            cSHOCKER=cSHOCKER_COLLAR;
                            SHOCKER_TYPE=1;
                            desc+="\n**collar**";
                            break;
                        case 2:
                            cSHOCKER=cSHOCKER_CHASTITY;
                            SHOCKER_TYPE=2;
                            desc+="\n**chastity**";
                            break;
                    }
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    desc+="\ncode: "+cSHOCKER.getCode();
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward (null)";
                    }
                } catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    desc+="\nError shocker!";
                }
                embed.addField("Shocker",desc,false);
                if(link==1){
                    embed.addField(" ","Please select an option for collar shocker .",false);
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4CollarEnabled();
                }
                else if(link==2){
                    embed.addField(" ","Please select an option for chastity shocker .",false);
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4ChastityEnabled();
                }
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc="\n:one: try out warn";
                desc+="\n:two: try out punish";
                desc+="\n:three: try out reward";
                if(!isEnabled){
                    desc+="\nNeeds to be enabled to try out!";
                }
                embed.setDescription(desc);
                Message message=llSendMessageResponse(gUser,embed);
                if(isEnabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuSpecialShocker(link);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    entityShockerActions shockerActions=cSHOCKER.getActions(iRestraints.nPishockAction_warn);
                                    logger.info(fName+"shockerActions="+shockerActions.getJSON().toString());
                                    if(doHttp(cSHOCKER,shockerActions)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Operation was success",llColors.llColorGreen1);
                                    }else{
                                        postHTTPErrorMessages(gTextChannel);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    entityShockerActions shockerActions=cSHOCKER.getActions(iRestraints.nPishockAction_punish);
                                    logger.info(fName+"shockerActions="+shockerActions.getJSON().toString());
                                    if(doHttp(cSHOCKER,shockerActions)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Operation was success",llColors.llColorGreen1);
                                    }else{
                                        postHTTPErrorMessages(gTextChannel);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    entityShockerActions shockerActions=cSHOCKER.getActions(iRestraints.nPishockAction_reward);
                                    logger.info(fName+"shockerActions="+shockerActions.getJSON().toString());
                                    if(doHttp(cSHOCKER,shockerActions)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Operation was success",llColors.llColorGreen1);
                                    }else{
                                        postHTTPErrorMessages(gTextChannel);
                                    }
                                }
                                else{
                                    menuSpecialShocker(link);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuSpecialShockerOwner(int link){
            String fName="[menuSpecialShockerOwner]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc3=new StringBuilder();
                isMenuLevel=true;indexMenuLevel=4;optionMenuLevel=link;
                boolean isEnabled=false;
                try{
                    switch (link){
                        case 1:
                            cSHOCKER=cSHOCKER_COLLAR;
                            SHOCKER_TYPE=1;
                            break;
                        case 2:
                            cSHOCKER=cSHOCKER_CHASTITY;
                            SHOCKER_TYPE=2;
                            break;
                    }
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    desc+="\ncode: (hidden)";
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward (null)";
                    }
                } catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    desc+="\nError shocker!";
                }
                switch (link){
                    case 1:
                        embed.addField("Collar Shocker",desc,false);
                        break;
                    case 2:
                        embed.addField("Chastity Shocker",desc,false);
                        break;
                    default:
                        embed.addField("Shocker",desc,false);
                        break;
                }
                if(link==1){
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4CollarEnabled();
                }
                else if(link==2){
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4ChastityEnabled();
                }
                desc="\n:id: enter code";
                if(isPatreon){
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc+="\n:one: enter action warn";
                desc+="\n:two: enter action punish";
                desc+="\n:three: enter action reward";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" clear all actions";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye)+" to test out";
                if(isEnabled){
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable.";
                }else{
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable.";
                }
                embed.setDescription(desc);
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                if(isEnabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                    dmSpecialInputCode(link);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    dmSpecialInputActions(link,0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    dmSpecialInputActions(link,1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    dmSpecialInputActions(link,2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                    if(link==1){
                                        gNewUserProfile.cPISHOCK.setShock4CollarEnabled(true);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                    else if(link==2){
                                        gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(true);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                    if(link==1){
                                        gNewUserProfile.cPISHOCK.setShock4CollarEnabled(false);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                    else if(link==2){
                                        gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(false);

                                        saveProfile();
                                        menuSpecialShocker(link);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                    JSONObject action=new JSONObject();
                                    action.put(nPishockMode,vPishockMode_Vibrate);action.put(nPishockDuration,5);action.put(nPishockItensity,10);
                                    JSONArray actions=new JSONArray();
                                    actions.put(action);
                                    if(link==1){
                                        cSHOCKER_COLLAR.setActionKey(nPishockAction_warn,actions);
                                        cSHOCKER_COLLAR.setActionKey(nPishockAction_punish,actions);
                                        cSHOCKER_COLLAR.setActionKey(nPishockAction_reward,actions);
                                        gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared collar shocker actions.",llColors.llColorGreen2);
                                    }
                                    else if(link==2){
                                        cSHOCKER_CHASTITY.setActionKey(nPishockAction_warn,actions);
                                        cSHOCKER_CHASTITY.setActionKey(nPishockAction_punish,actions);
                                        cSHOCKER_CHASTITY.setActionKey(nPishockAction_reward,actions);
                                        gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);

                                        saveProfile();
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Cleared chastity shocker actions.",llColors.llColorGreen2);
                                    }
                                    menuSpecialShocker(link);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEye))){
                                    menuSpecialShockerOwner_Test(link);
                                }
                                else{
                                    menuSpecialShocker(link);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuSpecialShockerOwner_Test(int link){
            String fName="[menuSpecialShockerOwner_Test]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc3=new StringBuilder();
                isMenuLevel=true;indexMenuLevel=4;optionMenuLevel=link;isWearerAccessLimited=false;
                boolean isEnabled=false;
                try{
                    switch (link){
                        case 1:
                            cSHOCKER=cSHOCKER_COLLAR;
                            SHOCKER_TYPE=1;
                            desc+="\n**collar**";
                            break;
                        case 2:
                            cSHOCKER=cSHOCKER_CHASTITY;
                            SHOCKER_TYPE=2;
                            desc+="\n**chastity**";
                            break;
                    }
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    desc+="\ncode: (hidden)";
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward (null)";
                    }
                } catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    desc+="\nError shocker!";
                }
                embed.addField("Shocker",desc,false);
                if(link==1){
                    embed.addField(" ","Please select an option for collar shocker .",false);
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4CollarEnabled();
                }
                else if(link==2){
                    embed.addField(" ","Please select an option for chastity shocker .",false);
                    isEnabled=gNewUserProfile.cPISHOCK.isShock4ChastityEnabled();
                }
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc="\n:one: try out warn";
                desc+="\n:two: try out punish";
                desc+="\n:three: try out reward";
                if(!isEnabled){
                    desc+="\nNeeds to be enabled to try out!";
                }
                embed.setDescription(desc);
                Message message=llSendMessageResponse(gUser,embed);
                if(isEnabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuSpecialShocker(link);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    entityShockerActions shockerActions=cSHOCKER.getActions(iRestraints.nPishockAction_warn);
                                    logger.info(fName+"shockerActions="+shockerActions.getJSON().toString());
                                    if(doHttp(cSHOCKER,shockerActions)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Operation was success",llColors.llColorGreen1);
                                    }else{
                                        postHTTPErrorMessages(gTextChannel);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    entityShockerActions shockerActions=cSHOCKER.getActions(iRestraints.nPishockAction_punish);
                                    logger.info(fName+"shockerActions="+shockerActions.getJSON().toString());
                                    if(doHttp(cSHOCKER,shockerActions)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Operation was success",llColors.llColorGreen1);
                                    }else{
                                        postHTTPErrorMessages(gTextChannel);
                                    }
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    entityShockerActions shockerActions=cSHOCKER.getActions(iRestraints.nPishockAction_warn);
                                    logger.info(fName+"shockerActions="+shockerActions.getJSON().toString());
                                    if(doHttp(cSHOCKER,shockerActions)){
                                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,sRTitle,"Operation was success",llColors.llColorGreen1);
                                    }else{
                                        postHTTPErrorMessages(gTextChannel);
                                    }
                                }
                                else{
                                    menuSpecialShocker(link);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuSpecialShockerSomebody(int link){
            String fName="[menuSpecialShockerSomebody]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc3=new StringBuilder();
                isMenuLevel=true;indexMenuLevel=4;optionMenuLevel=link;
                boolean isEnabled=false;
                try{
                    switch (link){
                        case 1:
                            cSHOCKER=cSHOCKER_COLLAR;
                            SHOCKER_TYPE=1;
                            break;
                        case 2:
                            cSHOCKER=cSHOCKER_CHASTITY;
                            SHOCKER_TYPE=2;
                            break;
                    }
                    logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    desc+="\ncode: (hidden)";
                    JSONArray tasks=new JSONArray();
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_warn);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption warn"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption warn (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_punish);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption punish"+desc3.toString();
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption punish (null)";
                    }
                    try{
                        desc3= new StringBuilder();
                        tasks=cSHOCKER.getActionKeyAsJSON(nPishockAction_reward);
                        for(int i=0;i<tasks.length();i++){
                            JSONObject task=tasks.getJSONObject(i);
                            String  mode=task.getString(nPishockMode).toLowerCase();
                            int duration=0;
                            int itensity=0;
                            if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                duration=task.getInt(nPishockDuration);
                            }
                            if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                duration=task.getInt(nPishockDuration);
                                itensity=task.getInt(nPishockItensity);
                            }
                            desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                        }
                        desc+="\nOption reward"+desc3.toString();

                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nOption reward (null)";
                    }
                } catch (Exception e) {
                    logger.error(fName+".exception=" + e);
                    logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                    desc+="\nError shocker!";
                }
                switch (link){
                    case 1:
                        embed.addField("Collar Shocker",desc,false);
                        break;
                    case 2:
                        embed.addField("Chastity Shocker",desc,false);
                        break;
                    default:
                        embed.addField("Shocker",desc,false);
                        break;
                }
                embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle);
                //embed.setDescription(desc);
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else{
                                    menuSpecialShocker(link);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmAddLinkedShocker(int action,int link){
            String fName="[dmAddLinkedShocker]";
            logger.info(fName+"action="+action);
            try{
                if(action==-1){
                    action=0;
                    cSHOCKER=new entityShocker();
                    cSHOCKER.setActions(defaultActions());
                }
                else if(action==-2||action==4){
                    if(link==1){
                        gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER);
                        gNewUserProfile.cPISHOCK.setShock4CollarEnabled(true);

                        saveProfile();
                    }else if(link==2){
                        gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER);
                        gNewUserProfile.cPISHOCK.setShock4ChastityEnabled(true);

                        saveProfile();
                    }
                }
                if(action==0){
                    Message message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a code.", llColorBlue1);
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.isBlank()||content.equalsIgnoreCase("!skip")){
                                        dmAddLinkedShocker(4,link);return;
                                    }
                                    else if(content.equalsIgnoreCase("!cancel")){
                                        menuSpecialShocker(link);return;
                                    }else if(content.equalsIgnoreCase("!clear")){
                                        cSHOCKER.setCode("");
                                    }
                                    else{
                                        cSHOCKER.setCode(content);
                                    }
                                    dmAddLinkedShocker(4,link);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                    dmAddLinkedShocker(-2,link);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                                dmAddLinkedShocker(-2,link);
                            });
                }
                /*else if(action==1||action==2||action==3){
                    if(!isPatreon&&!isPatreonGuild&&link==2){
                        logger.info(fName+".skip to save");
                        dmAddLinkedShocker(4,link);
                    }
                    EmbedBuilder embed=new EmbedBuilder();
                    embed.setColor(llColorBlue1);String desc="";
                    switch (action){
                        case 1: desc=strTrigger0+"warning."+strTrigger1;break;
                        case 2: desc=strTrigger0+"punish."+strTrigger1;break;
                        case 3: desc=strTrigger0+"reward."+strTrigger1;break;
                    }

                    embed.addField("How to set?",desc,false);
                    Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                    int finalAction = action;
                    gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                            e -> e.getAuthor().equals(gUser),
                            e -> {
                                try {
                                    JSONObject jsonTask=new JSONObject();
                                    JSONArray jsonTasks=new JSONArray();
                                    jsonTasks=new JSONArray();
                                    boolean save=false;
                                    String content = e.getMessage().getContentStripped();
                                    logger.info(fName+".content="+content);
                                    llMessageDelete(message);
                                    if(content.isBlank()||content.equalsIgnoreCase("!skip")){
                                        dmAddLinkedShocker(finalAction+1,link);return;
                                    }
                                    else if(content.equalsIgnoreCase("!cancel")){
                                        menuSpecialShocker(link);return;
                                    }else if(content.equalsIgnoreCase("!clear")){
                                        save=true;
                                    }
                                    else{
                                        String items[]=content.split("~");
                                        int i=0;
                                        while(i<iMaxTask&&i<items.length){
                                            String item=items[i];
                                            jsonTask=new JSONObject();
                                            String subitems[]=item.split(",");
                                            if(subitems[0].equalsIgnoreCase(vPishockMode_Beep)&&subitems.length<2){
                                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Invalid format.");
                                                dmAddLinkedShocker(finalAction,link);return;
                                            }
                                            else if(subitems[0].equalsIgnoreCase(vPishockMode_Beep)){
                                                jsonTask.put(nPishockMode,subitems[0].toLowerCase());
                                                jsonTask.put(nPishockDuration,Integer.valueOf(subitems[1]));
                                                jsonTasks.put(jsonTask);
                                                save=true;
                                            }
                                            else if((subitems[0].equalsIgnoreCase(vPishockMode_Vibrate)||subitems[0].equalsIgnoreCase(vPishockMode_Shock))&&subitems.length<3){
                                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Invalid format.");
                                                dmAddLinkedShocker(finalAction,link);return;
                                            }
                                            else if((subitems[0].equalsIgnoreCase(vPishockMode_Vibrate)||subitems[0].equalsIgnoreCase(vPishockMode_Shock))){
                                                jsonTask.put(nPishockMode,subitems[0].toLowerCase());
                                                jsonTask.put(nPishockDuration,Integer.valueOf(subitems[1]));
                                                jsonTask.put(nPishockItensity,Integer.valueOf(subitems[2]));
                                                jsonTasks.put(jsonTask);
                                                save=true;
                                            }
                                            i++;
                                        }

                                    }
                                    if(save){
                                        switch (finalAction){
                                            case 1:  SHOCKER.getJSONObject(nPishockActions).put(nPishockAction_warn,jsonTasks);break;
                                            case 2:  SHOCKER.getJSONObject(nPishockActions).put(nPishockAction_punish,jsonTasks);break;
                                            case 3:  SHOCKER.getJSONObject(nPishockActions).put(nPishockAction_reward,jsonTasks);break;
                                        }
                                    }
                                    dmAddLinkedShocker(finalAction+1,link);

                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(message);
                                    dmAddLinkedShocker(finalAction,link);
                                }
                            },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                                dmAddLinkedShocker(-2,link);
                            });
                }*/
                else if(action==4){
                    EmbedBuilder embed=new EmbedBuilder();
                    embed.setColor(llColorBlue1);String desc="";
                    embed.setDescription("Added new shocker successfully!");
                    llSendMessageResponse(gUser,embed);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputCode(int link){
            String fName="[dmSpecialInputCode]";
            logger.info(fName);
            try{
                entityShocker shocker=new entityShocker();
                if(link==1){
                    shocker=cSHOCKER_COLLAR;
                }
                else if(link==2){
                    shocker=cSHOCKER_CHASTITY;
                }
                String value=shocker.getCode();Message message;
                if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"Current code is "+value+".\nPlease enter a new code or enter !cancel to leave it as it is or enter !clear to remove it.", llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,sRTitle,"\nPlease enter a code.", llColorBlue1);
                }

                entityShocker finalShocker = shocker;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    menuSpecialShocker(link);return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    finalShocker.setCode("");
                                }
                                else{
                                    finalShocker.setCode(content);
                                }

                                if(link==1){
                                    cSHOCKER_COLLAR= finalShocker;
                                    gNewUserProfile.cPISHOCK.setCollarShocker( finalShocker);
                                }
                                else if(link==2){
                                    cSHOCKER_CHASTITY= finalShocker;
                                    gNewUserProfile.cPISHOCK.setChastityShocker( finalShocker);
                                }

                                saveProfile();
                                menuSpecialShocker(link);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputActions(int link, int action){
            String fName="[dmSpecialInputActions]";
            logger.info(fName);
            try{
                entityShocker shocker=new entityShocker();
                if(link==1){
                    shocker=cSHOCKER_COLLAR;
                }
                else if(link==2){
                    shocker=cSHOCKER_CHASTITY;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                entityShockerActions ACTIONS=new entityShockerActions();
                JSONArray jsonArrayActions=new JSONArray();
                try {
                    switch (action){
                        case 0: desc=strTrigger0+"warning."+strTrigger1;
                            ACTIONS=shocker.getActions(nPishockAction_warn);
                            break;
                        case 1:desc=strTrigger0+"punish."+strTrigger1;
                            ACTIONS=shocker.getActions(nPishockAction_punish);
                            break;
                        case 2: desc=strTrigger0+"reward."+strTrigger1;
                            ACTIONS=shocker.getActions(nPishockAction_reward);
                            break;
                    }
                    jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("Currently", desc2.toString(),false);
                    else embed.addField("Currently", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Currently", "(error)",false);
                }

                dmSpecialInputActions_Option(link,action, ACTIONS);
                /*embed.addField("How to set?",desc,false);
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);

                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                JSONObject jsonTask=new JSONObject();
                                JSONArray jsonTasks=new JSONArray();
                                boolean save=false;
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.equalsIgnoreCase("!cancel")){
                                    menuSpecialShocker(link);return;
                                }else if(content.equalsIgnoreCase("!clear")){
                                    save=true;
                                }
                                else{
                                    String[] items =content.split("~");
                                    int i=0;
                                    if(isPatreon||isPatreonGuild){
                                        while(i<iMaxTask&&i<items.length){
                                            String item=items[i];
                                            jsonTask=new JSONObject();
                                            String[] subitems =item.replaceAll(" ","").split(",");
                                            if(subitems[0].equalsIgnoreCase(vPishockMode_Beep)&&subitems.length<2){
                                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Invalid format.");
                                                dmSpecialInputActions(link,action);return;
                                            }
                                            else if(subitems[0].equalsIgnoreCase(vPishockMode_Beep)){
                                                jsonTask.put(nPishockMode,subitems[0].toLowerCase());
                                                jsonTask.put(nPishockDuration,Integer.valueOf(subitems[1]));
                                                jsonTasks.put(jsonTask);
                                                save=true;
                                            }
                                            else if((subitems[0].equalsIgnoreCase(vPishockMode_Vibrate)||subitems[0].equalsIgnoreCase(vPishockMode_Shock))&&subitems.length<3){
                                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Invalid format.");
                                                dmSpecialInputActions(link,action);return;
                                            }
                                            else if((subitems[0].equalsIgnoreCase(vPishockMode_Vibrate)||subitems[0].equalsIgnoreCase(vPishockMode_Shock))){
                                                jsonTask.put(nPishockMode,subitems[0].toLowerCase());
                                                jsonTask.put(nPishockDuration,Integer.valueOf(subitems[1]));
                                                jsonTask.put(nPishockItensity,Integer.valueOf(subitems[2]));
                                                jsonTasks.put(jsonTask);
                                                save=true;
                                            }
                                            i++;
                                        }
                                    }else{
                                        String item=items[i];
                                        jsonTask=new JSONObject();
                                        String subitems[]=item.split(",");
                                        if(subitems[0].equalsIgnoreCase(vPishockMode_Beep)&&subitems.length<2){
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Invalid format.");
                                            dmSpecialInputActions(link,action);return;
                                        }
                                        else if(subitems[0].equalsIgnoreCase(vPishockMode_Beep)){
                                            jsonTask.put(nPishockMode,subitems[0].toLowerCase());
                                            jsonTask.put(nPishockDuration,Integer.valueOf(subitems[1]));
                                            jsonTasks.put(jsonTask);
                                            save=true;
                                        }
                                        else if((subitems[0].equalsIgnoreCase(vPishockMode_Vibrate)||subitems[0].equalsIgnoreCase(vPishockMode_Shock))&&subitems.length<3){
                                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Invalid format.");
                                            dmSpecialInputActions(link,action);return;
                                        }
                                        else if((subitems[0].equalsIgnoreCase(vPishockMode_Vibrate)||subitems[0].equalsIgnoreCase(vPishockMode_Shock))){
                                            jsonTask.put(nPishockMode,subitems[0].toLowerCase());
                                            jsonTask.put(nPishockDuration,Integer.valueOf(subitems[1]));
                                            jsonTask.put(nPishockItensity,Integer.valueOf(subitems[2]));
                                            jsonTasks.put(jsonTask);
                                            save=true;
                                        }
                                    }
                                }
                                if(save){
                                    if(jsonTasks==null){
                                        jsonTasks=defaultActionTasks();
                                    }
                                    if(link==1){
                                        switch (action){
                                            case 0:
                                                SHOCKER_COLLAR.getJSONObject(nPishockActions).put(nPishockAction_warn,jsonTasks);
                                                break;
                                            case 1:
                                                SHOCKER_COLLAR.getJSONObject(nPishockActions).put(nPishockAction_punish,jsonTasks);
                                                break;
                                            case 2:
                                                SHOCKER_COLLAR.getJSONObject(nPishockActions).put(nPishockAction_reward,jsonTasks);
                                                break;
                                        }
                                    }
                                    else if(link==2){
                                        switch (action){
                                            case 0:
                                                SHOCKER_CHASTITY.getJSONObject(nPishockActions).put(nPishockAction_warn,jsonTasks);
                                                break;
                                            case 1:
                                                SHOCKER_CHASTITY.getJSONObject(nPishockActions).put(nPishockAction_punish,jsonTasks);
                                                break;
                                            case 2:
                                                SHOCKER_CHASTITY.getJSONObject(nPishockActions).put(nPishockAction_reward,jsonTasks);
                                                break;
                                        }
                                    }

                                }
                                menuSpecialShocker(link);

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                                menuSpecialShocker(link);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });*/

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputActions_Option(int link, int action, entityShockerActions ACTIONS){
            String fName="[dmInputAction_Option]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                JSONArray jsonArrayActions=new JSONArray();
                try {
                    jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell)+" beep";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode)+" vibrate";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap)+" shock";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" clear";
                embed.setDescription("Please select an option"+desc);
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                dmSpecialInputActions_OptionListen(message,link, action, ACTIONS);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputActions_OptionListen(Message message,int link, int action, entityShockerActions ACTIONS){
            String fName="[dmInputAction_OptionListen]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            entityShockerAction task=new entityShockerAction();
                            switch (id){
                                case lsUnicodeEmotes.aliasArrowUp:
                                    menuSpecialShocker(link);
                                    break;
                                case lsUnicodeEmotes.aliasCrossMark:
                                    dmSpecialInputActions_Save(link, action, new entityShockerActions());
                                    menuSpecialShocker(link);
                                    break;
                                case lsUnicodeEmotes.aliasBell:
                                    task.setMode(vPishockMode_Beep);
                                    dmSpecialInputActions_Duration(link, action, ACTIONS,task);
                                    break;
                                case lsUnicodeEmotes.aliasVibrationMode:
                                    task.setMode(vPishockMode_Vibrate);
                                    dmSpecialInputActions_Duration(link, action, ACTIONS,task);
                                    break;
                                case lsUnicodeEmotes.aliasZap:
                                    task.setMode(vPishockMode_Shock);
                                    dmSpecialInputActions_Duration(link, action, ACTIONS,task);
                                    break;
                                default:
                                    dmSpecialInputActions_Option(link, action,ACTIONS);
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            entityShockerAction task=new entityShockerAction();
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuSpecialShocker(link);
                                return;
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                dmSpecialInputActions_Save(link, action, new entityShockerActions());
                                menuSpecialShocker(link);
                                return;
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBell))){
                                task.setMode(vPishockMode_Beep);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasVibrationMode))){
                                task.setMode(vPishockMode_Vibrate);

                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZap))){
                                task.setMode(vPishockMode_Shock);
                            }else{
                                dmSpecialInputActions_Option(link, action,ACTIONS);
                                return;
                            }

                            dmSpecialInputActions_Duration(link, action, ACTIONS,task);
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                            menuShocker();
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });
        }
        private void dmSpecialInputActions_Duration(int link, int action, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_Duration]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                JSONArray jsonArrayActions=new JSONArray();
                try {
                    jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                boolean requireItensity=false;
                try {
                    String  mode=TASK.getMode();
                    int duration=0;
                    int itensity=0;
                    if(TASK.getOp()==2){
                        duration=TASK.getDuration();
                        embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                    }else
                    if(TASK.getOp()==1||TASK.getOp()==0){
                        duration=TASK.getDuration();
                        itensity=TASK.getIntensity();
                        requireItensity=true;
                        embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                    }else{
                        embed.addField("Current line", "(invalid)",false);
                    }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Current line", "(error)",false);
                }
                embed.setDescription("Please enter duration from 1-15. This number represents seconds.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                boolean finalRequireItensity = requireItensity;
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> (!e.getAuthor().isBot()&&e.getAuthor().getIdLong()==gUser.getIdLong()&&message!=null),
                        e -> {
                            try {
                                String content=e.getMessage().getContentRaw();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    menuSpecialShocker(link);
                                    return;
                                }
                                int value=0;
                                try {
                                    value=Integer.parseInt(content);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be a number!");
                                    dmSpecialInputActions_Duration(link, action, ACTIONS,TASK);
                                    return;
                                }
                                logger.info(fName+".value="+value);
                                if(value<1||value>15){
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be between 1-15!");
                                    dmSpecialInputActions_Duration(link, action, ACTIONS,TASK);
                                    return;
                                }
                                TASK.setDuration(value);
                                if(finalRequireItensity){
                                    dmSpecialInputActions_Itensity(link, action, ACTIONS,TASK);
                                }else{
                                    dmSpecialInputActions_AskNewLine(link, action, ACTIONS,TASK);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                dmSpecialInputActions_Duration(link, action, ACTIONS,TASK);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputActions_Itensity(int link, int action, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_Itensity]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                JSONArray jsonArrayActions=new JSONArray();
                try {
                    jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                try {
                    String  mode=TASK.getMode();
                    int duration=0;
                    int itensity=0;
                    if(TASK.getOp()==2){
                        duration=TASK.getDuration();
                        embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                    }else
                    if(TASK.getOp()==1||TASK.getOp()==0){
                        duration=TASK.getDuration();
                        itensity=TASK.getIntensity();
                        embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                    }else{
                        embed.addField("Current line", "(invalid)",false);
                    }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Current line", "(error)",false);
                }
                embed.setDescription("Please enter intensity from 1-100.");
                Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> (!e.getAuthor().isBot()&&e.getAuthor().getIdLong()==gUser.getIdLong()&&message!=null),
                        e -> {
                            try {
                                String content=e.getMessage().getContentRaw();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    menuSpecialShocker(link);
                                    return;
                                }
                                int value=0;
                                try {
                                    value=Integer.parseInt(content);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be a number!");
                                    dmSpecialInputActions_Itensity(link, action, ACTIONS,TASK);
                                    return;
                                }
                                logger.info(fName+".value="+value);
                                if(value<1||value>100){
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Must be between 1-100!");
                                    dmSpecialInputActions_Itensity(link, action, ACTIONS,TASK);
                                    return;
                                }
                                TASK.setIntensity(value);
                                dmSpecialInputActions_AskNewLine(link, action, ACTIONS,TASK);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                dmSpecialInputActions_Itensity(link, action, ACTIONS,TASK);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputActions_AskNewLine(int link, int action, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_AskNewLine]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                StringBuilder desc2= new StringBuilder();
                JSONArray jsonArrayActions=new JSONArray();
                try {
                    jsonArrayActions=ACTIONS.getJSON();
                    for(int i=0;i<jsonArrayActions.length();i++){
                        JSONObject ACTION=jsonArrayActions.getJSONObject(i);
                        String  mode=ACTION.getString(nPishockMode).toLowerCase();
                        int duration=0;
                        int itensity=0;
                        if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                            duration=ACTION.getInt(nPishockDuration);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration);
                        }
                        if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                            duration=ACTION.getInt(nPishockDuration);
                            itensity=ACTION.getInt(nPishockItensity);
                            desc2.append("\ntype=").append(mode).append(strDuration45).append(duration).append(strItensity45).append(itensity);
                        }
                    }
                    if(desc2.length()>0)embed.addField("All lines", desc2.toString(),false);
                    else embed.addField("All lines", "(null)",false);
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("All lines", "(error)",false);
                }
                try {
                    String  mode=TASK.getMode();
                    int duration=0;
                    int itensity=0;
                    if(TASK.getOp()==2){
                        duration=TASK.getDuration();
                        embed.addField("Current line", "type="+mode+strDuration45+duration,false);
                    }else
                    if(TASK.getOp()==1||TASK.getOp()==0){
                        duration=TASK.getDuration();
                        itensity=TASK.getIntensity();
                        embed.addField("Current line", "type="+mode+strDuration45+duration+strItensity45+itensity,false);
                    }else{
                        embed.addField("Current line", "(invalid)",false);
                    }
                }catch (Exception ex){
                    logger.error(fName + ".exception=" + ex);
                    logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
                    embed.addField("Current line", "(error)",false);
                }
                ACTIONS.addAction(TASK);
                embed.setDescription("Do you wish to add a new line?");
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileInputActionAskNewLine);
                logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                try {
                    lcMessageBuildComponent component0= messageComponentManager.messageBuildComponents.getComponent(0);
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
                }
                dmSpecialInputActions_AskNewLineListen(message,link,action, ACTIONS, TASK);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void dmSpecialInputActions_AskNewLineListen(Message message,int link, int action, entityShockerActions ACTIONS, entityShockerAction TASK){
            String fName="[dmInputAction_AskNewLineListen]";
            logger.info(fName);
            gWaiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            llMessageDelete(message);
                            entityShockerAction task=new entityShockerAction();
                            switch (id){
                                case lsUnicodeEmotes.aliasGreenCircle:
                                    dmSpecialInputActions_Option(link, action, ACTIONS);
                                    break;
                                case lsUnicodeEmotes.aliasRedCircle:
                                    dmSpecialInputActions_Save(link, action, ACTIONS);
                                    menuSpecialShocker(link);
                                    break;
                                case lsUnicodeEmotes.aliasCrossMark:
                                    logger.info(fName+"canceled");
                                    break;
                                default:
                                    dmSpecialInputActions_AskNewLine(link,action, ACTIONS, TASK);
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
            gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                return;
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                                dmSpecialInputActions_Option(link, action, ACTIONS);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                                dmSpecialInputActions_Save(link, action, ACTIONS);
                                menuSpecialShocker(link);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
        }
        private void dmSpecialInputActions_Save(int link, int action, entityShockerActions ACTIONS){
            String fName="[dmSpecialInputActions_Save]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                switch (link){
                    case 1:
                        logger.info(fName+"cSHOCKER_COLLAR.before="+cSHOCKER_COLLAR.getJSON().toString());
                        switch (action){
                            case 0:
                                cSHOCKER_COLLAR.setActionKey(nPishockAction_warn,ACTIONS);
                                break;
                            case 1:
                                cSHOCKER_COLLAR.setActionKey(nPishockAction_punish,ACTIONS);
                                break;
                            case 2:
                                cSHOCKER_COLLAR.setActionKey(nPishockAction_reward,ACTIONS);
                                break;
                        }
                        gNewUserProfile.cPISHOCK.setCollarShocker(cSHOCKER_COLLAR);

                        saveProfile();
                        break;
                    case 2:
                        logger.info(fName+"cSHOCKER_CHASTITY.before="+cSHOCKER_CHASTITY.getJSON().toString());
                        switch (action) {
                            case 0:
                                cSHOCKER_CHASTITY.setActionKey(nPishockAction_warn, ACTIONS);
                                break;
                            case 1:
                                cSHOCKER_CHASTITY.setActionKey(nPishockAction_punish, ACTIONS);
                                break;
                            case 2:
                                cSHOCKER_CHASTITY.setActionKey(nPishockAction_reward, ACTIONS);
                                break;
                        }
                        gNewUserProfile.cPISHOCK.setChastityShocker(cSHOCKER_CHASTITY);

                        saveProfile();
                        break;
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        private void doSpecialShocker(int link, int action){
            String fName="[doSpecialShocker]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}

                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    return;
                }
                if(!(gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.warn(fName + ".can't use do to white&black list");
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                JSONObject jsonObject=new JSONObject();
                a.config().verifySsl(false);
                entityShocker shocker=new entityShocker();
                getShockers();
                if(link==1){
                    if(!gNewUserProfile.cPISHOCK.isShock4CollarEnabled()){
                        logger.info(fName + "shocker is disabled by CollarEnabled");
                        return;
                    }
                    shocker=cSHOCKER_COLLAR;
                }
                else if(link==2){
                    if(!gNewUserProfile.cPISHOCK.isShock4ChastityEnabled()){
                        logger.info(fName + "shocker is disabled ChastityEnabled");
                        return;
                    }
                    shocker=cSHOCKER_CHASTITY;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null >use main");
                    shocker=cSHOCKER;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null , attempt 2");
                    return;
                }
                logger.info(fName + "shocker=" + shocker.getJSON().toString());
                entityShockerActions tasks=new entityShockerActions();
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code > use main");
                    shocker=cSHOCKER;
                }
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code , attempt 2");
                    return;
                }
                switch (action){
                    case 0:
                        tasks =shocker.getActions(nPishockAction_warn);
                        break;
                    case 1:
                        tasks =shocker.getActions(nPishockAction_punish);
                        break;
                    case 2:
                        tasks =shocker.getActions(nPishockAction_reward);
                        break;
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty > use main");
                    tasks =cSHOCKER.getActions(iRestraints.nPishockAction_main);
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty attempt 2");
                    return;
                }
                logger.info(fName + "tasks=" + tasks.toString());
                if(doHttp(shocker,tasks)){
                    logger.info(fName + "success");
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doSpecialShocker(Member target,int link, int action){
            String fName="[doSpecialShocker]";
            logger.info(fName);
            try{
                logger.info(fName+"link="+link+", action="+action);
                if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}


                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    return;
                }
                if(!(gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.warn(fName + ".can't use do to white&black list");
                    return;
                }
                getShockers();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                JSONObject jsonObject=new JSONObject();
                a.config().verifySsl(false);
                entityShocker shocker=new entityShocker();
                if(link==1){
                    if(!gNewUserProfile.cPISHOCK.isShock4CollarEnabled()){
                        logger.info(fName + "shocker is disabled by CollarEnabled");
                        return;
                    }
                    shocker=cSHOCKER_COLLAR;
                }
                else if(link==2){
                    if(!gNewUserProfile.cPISHOCK.isShock4ChastityEnabled()){
                        logger.info(fName + "shocker is disabled by ChastityEnabled");
                        return;
                    }
                    shocker=cSHOCKER_CHASTITY;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null >use main");
                    shocker=cSHOCKER;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null , attempt 2");
                    return;
                }
                logger.info(fName + "shocker=" + shocker.getJSON().toString());
                entityShockerActions tasks=new entityShockerActions();
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code > use main");
                    shocker=cSHOCKER;
                }
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code , attempt 2");
                    return;
                }
                switch (action){
                    case 0:
                        tasks =shocker.getActions(nPishockAction_warn);
                        break;
                    case 1:
                        tasks =shocker.getActions(nPishockAction_punish);
                        break;
                    case 2:
                        tasks =shocker.getActions(nPishockAction_reward);
                        break;
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty > use main");
                    tasks =cSHOCKER.getActions(iRestraints.nPishockAction_main);
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty attempt 2");
                    return;
                }
                logger.info(fName + "tasks=" + tasks.toString());
                if(doHttp(shocker,tasks)){
                    logger.info(fName + "success");
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        JSONObject COMPLEXACTION=new JSONObject();
        private boolean add2ActionList(JSONObject shocker,JSONArray tasks){
            String fName="[add2ActionList]";
            logger.info(fName);
            try{
                COMPLEXACTION=new JSONObject();
                logger.info(fName+"shocker="+shocker.toString());
                int i=0;
                if(!shocker.has(nPishockCode)){
                    logger.info(fName+"code is missing>return");
                    return false;
                }
                String code=shocker.getString(nPishockCode);
                logger.info(fName+" code"+ code);
                logger.info(fName+"tasks="+tasks.toString());
                if(tasks.isEmpty()){
                    logger.info(fName+"task is empty>return");
                    return false;
                }
                while(i<tasks.length()&&i<iMaxTask){
                    try {
                        JSONObject task=tasks.getJSONObject(i);
                        if(!COMPLEXACTION.has(code)){
                            COMPLEXACTION.put(code,new JSONArray());
                            COMPLEXACTION.getJSONArray(code).put(task);
                        }
                    }catch (Exception e){

                    }
                    i++;
                }
                logger.info(fName+"added");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean doHttp_ComplexActions(JSONObject actions){
            String fName="[doHttp_ComplexActions]";
            logger.info(fName);
            try{
                logger.info(fName+"readFile="+ getConfigValues());
                boolean result=false;
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                JSONObject jsonObject=new JSONObject();
                logger.info(fName+"actions="+actions.toString());
                logger.info(fName+"url="+gUrl);
                int i=0;
                int iSleep=0;
                JSONArray tasks=new JSONArray();
                JSONObject shocker=new JSONObject();
                /*while(i<tasks.length()&&i<iMaxTask){

                }*/
                while(i<tasks.length()&&i<iMaxTask){
                    try {
                        logger.info(fName+"prepare");
                        JSONObject task=tasks.getJSONObject(i);
                        jsonObject=new JSONObject();
                        jsonObject.put(fieldUsername,gPiUsername);
                        jsonObject.put(fieldApikey,gPiApiKey);
                        jsonObject=getProfileAuthofication(jsonObject);
                        jsonObject.put(fieldCode,shocker.getString(nPishockCode));
                        jsonObject.put(fieldName,gUser.getName());
                        logger.info(fName+"task["+i+"="+task.toString());
                        switch (task.getString(nPishockMode).toLowerCase()){
                            case vPishockMode_Beep:
                                jsonObject.put(fieldDuration,task.getInt(nPishockDuration));
                                jsonObject.put(fieldOp,2);
                                break;
                            case vPishockMode_Vibrate:
                                jsonObject.put(fieldIntensity,task.getInt(nPishockItensity));
                                jsonObject.put(fieldDuration,task.getInt(nPishockDuration));
                                jsonObject.put(fieldOp,1);
                                break;
                            case vPishockMode_Shock:
                                jsonObject.put(fieldIntensity,task.getInt(nPishockItensity));
                                jsonObject.put(fieldDuration,task.getInt(nPishockDuration));
                                jsonObject.put(fieldOp,0);
                                break;
                        }
                        logger.info(fName+"jsonObject="+jsonObject.toString());
                        HttpResponse<String> jsonResponse =a.post(gUrl)
                                .header("accept", "application/json")
                                .header("Content-Type", "application/json")
                                .body(jsonObject.toString())
                                .asString();
                        logger.info(fName+".status ="+jsonResponse.getStatus());
                        logger.info(fName+".body ="+ jsonResponse.getBody());
                        if(jsonResponse.getStatus()!=200){
                            desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                        }
                        else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                            desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                        }
                        else{
                            loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                            result=true;
                        }
                        if(i<tasks.length()-1){
                            if(task.getInt(nPishockDuration)<=0){
                                logger.info(fName+"sleep=15");
                                //Thread.sleep(15000);
                                Thread.sleep(20000);
                            }else{
                                logger.info(fName+"sleep="+task.getInt(nPishockDuration));
                                //Thread.sleep(task.getInt(nPishockDuration)*1000);
                                Thread.sleep(20000);
                            }
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc.append("\n").append(e);embed.setColor(llColorRed_Barn);
                    }
                    i++;
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setTitle(sRTitle);
                    embed.setDescription(desc.toString());
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                    lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel,embed,secondsBeforeDelete);
                }
                logger.info(fName+"result="+result);
                return  result;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        private void doBeep(boolean failedNotANumber) {
            String fName = "[doBeep]";
            logger.info(fName);
            try {
                logger.info(fName+" failedNotANumber="+failedNotANumber);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                String desc="";
                if(failedNotANumber)desc+="Not a number!\n";
                Message messageRequestDuration=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,desc+"Please enter a number between 1-15 for beep duration.\nEnter `!abort` to abort it",llColorGreen_Jungle);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!abort")){
                                    //nothing or canceled >return
                                    return;
                                }
                                int value=lsStringUsefullFunctions.String2Int(content);
                                if(value<=0||value>maxDuration){
                                    doBeep(true);return;
                                }
                                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                    doCustomPiShock(optionBeep,content,"");
                                }else{
                                    doCustomPiShock(gNewUserProfile.getMember(),optionBeep,content,"",false);
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(messageRequestDuration);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(messageRequestDuration);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }

        }
        private void doVibrate(boolean failedNotANumber) {
            String fName = "[doVibrate]";
            logger.info(fName);
            try {
                logger.info(fName+" failedNotANumber="+failedNotANumber);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                String desc="";
                if(failedNotANumber)desc+="Not a number!\n";
                Message messageRequestDuration=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,desc+"Please enter a number between 1-15 for vibration duration.\nEnter `!abort` to abort it",llColorGreen_Jungle);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!abort")){
                                    //nothing or canceled >return
                                    return;
                                }
                                int value=lsStringUsefullFunctions.String2Int(content);
                                if(value<=0||value>maxDuration){
                                    doVibrate(true);return;
                                }
                                Message messageRequestItensity=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a number between 1-100 for vibration intensity.\nEnter `!abort` to abort it",llColorGreen_Jungle);
                                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                                        e2 -> e2.getAuthor().equals(gUser),
                                        e2 -> {
                                            try {
                                                String content2 = e2.getMessage().getContentStripped();
                                                logger.info(fName+".content="+content2);
                                                if(content2.isBlank()||content2.equalsIgnoreCase("!abort")){
                                                    //nothing or canceled >return
                                                    return;
                                                }
                                                int value2=lsStringUsefullFunctions.String2Int(content2);
                                                if(value2<=0||value2>maxItensity){
                                                    doVibrate(true);return;
                                                }
                                                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                                    doCustomPiShock(optionVibrate,content,"");
                                                }else{
                                                    doCustomPiShock(gNewUserProfile.getMember(),optionVibrate,content,"",false);
                                                }
                                            }catch (Exception e3){
                                                logger.error(fName + ".exception=" + e3);
                                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                                llMessageDelete(messageRequestItensity);
                                            }
                                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(messageRequestItensity);
                                        });
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(messageRequestDuration);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(messageRequestDuration);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void doShock(boolean failedNotANumber) {
            String fName = "[doShock]";
            logger.info(fName);
            try {
                logger.info(fName+" failedNotANumber="+failedNotANumber);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                String desc="";
                if(failedNotANumber)desc+="Not a number!\n";
                Message messageRequestDuration=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,desc+"Please enter a number between 1-15 for shock duration.\nEnter `!abort` to abort it",llColorGreen_Jungle);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                if(content.isBlank()||content.equalsIgnoreCase("!abort")){
                                    //nothing or canceled >return
                                    return;
                                }
                                int value=lsStringUsefullFunctions.String2Int(content);
                                if(value<=0||value>maxDuration){
                                    doShock(true);return;
                                }
                                Message messageRequestItensity=lsMessageHelper.lsSendQuickEmbedMessageResponse(gUser,sRTitle,"Please enter a number between 1-100 for shock intensity.\nEnter `!abort` to abort it",llColorGreen_Jungle);
                                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                                        e2 -> e2.getAuthor().equals(gUser),
                                        e2 -> {
                                            try {
                                                String content2 = e2.getMessage().getContentStripped();
                                                logger.info(fName+".content="+content2);
                                                if(content2.isBlank()||content2.equalsIgnoreCase("!abort")){
                                                    //nothing or canceled >return
                                                    return;
                                                }
                                                int value2=lsStringUsefullFunctions.String2Int(content2);
                                                if(value2<=0||value2>maxItensity){
                                                    doShock(true);return;
                                                }
                                                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                                                    doCustomPiShock(optionShock,content,"");
                                                }else{
                                                    doCustomPiShock(gNewUserProfile.getMember(),optionShock,content,"",false);
                                                }
                                            }catch (Exception e3){
                                                logger.error(fName + ".exception=" + e3);
                                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                                llMessageDelete(messageRequestItensity);
                                            }
                                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(messageRequestItensity);
                                        });
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(messageRequestDuration);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(messageRequestDuration);
                        });
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void doBeep(String duration,String code) {
            String fName = "[doBeep]";
            logger.info(fName);
            doCustomPiShock(optionBeep,duration,"",code);
        }
        private void doVibrate(String duration,String itensity,String code) {
            String fName = "[doVibrate]";
            logger.info(fName);
            doCustomPiShock(optionVibrate,duration,itensity,code);
        }
        private void doShock(String duration,String itensity,String code) {
            String fName = "[doShock]";
            logger.info(fName);
            doCustomPiShock(optionShock,duration,itensity,code);
        }
        private void doCustomPiShock(int opt,String duration,String itensity) {
            String fName = "[doCustomPiShock]";
            try{
                logger.info(fName +".opt="+opt+",duration=" + duration+strItensity45+itensity);
                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strCantDueNotenabled), llColorRed);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Pet), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Public), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                    logger.info(fName + ".can't while the jacket is on and hands strapped");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantJacket1), llColorRed);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantJacket2), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                    logger.info(fName + ".can't while the mittens are on");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantMitts1), llColorRed);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantMitts2), llColorRed);
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                getShockers(); int codeIndex=-1;
                logger.info(fName+"readFile="+ getConfigValues());
                jsonObject.put(fieldUsername,gPiUsername);
                jsonObject.put(fieldApikey,gPiApiKey);
                jsonObject=getProfileAuthofication(jsonObject);
                if(cSHOCKER==null||!cSHOCKER.hasCode())getDefaultShoker();
                jsonObject.put(fieldCode,cSHOCKER.getCode());
                logger.info(fName+"code="+jsonObject.getString(fieldCode));
                int iDuration=0,iItensity=0;
                try {
                    if(duration!=null&&!duration.isBlank()){
                        iDuration=Integer.parseInt(duration);
                    }
                    logger.info(fName+"iDuration="+iDuration);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    if(itensity!=null&&!itensity.isBlank()){
                        iItensity=Integer.parseInt(itensity);
                    }
                    logger.info(fName+"iItensity="+iItensity);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                switch (opt){
                    case  optionBeep:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }
                        else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        jsonObject.put(fieldOp,2);
                        break;
                    case optionVibrate:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,1);
                        break;
                    case optionShock:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,0);
                        break;
                }
                logger.info(fName + ".ready to send");
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                logger.info(fName+"url="+gUrl);
                jsonObject.put(fieldName,gUser.getName());
                logger.info(fName+".jsonObject ="+jsonObject.toString());
                if(gDebugSend){
                    logger.info(fName + ".debug");
                    embed.setTitle(sRTitle+"(debug)");
                    embed.setDescription(jsonObject.toString());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                HttpResponse<String> jsonResponse =a.post(gUrl)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                logger.info(fName+".body ="+ jsonResponse.getBody());
                if(jsonResponse.getStatus()!=200){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }
                else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }else{
                    //success
                    switch (opt){
                        case  optionBeep:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep1,iDuration), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep2,iDuration), llColorYellow_Cream);
                            break;
                        case optionVibrate:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate2,iDuration,iItensity), llColorYellow_Cream);
                            break;
                        case optionShock:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock2,iDuration,iItensity), llColorYellow_Cream);
                            break;
                    }
                    loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setDescription(desc.toString());
                    lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed,secondsBeforeDelete);
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), gUser,sRTitle, e.toString());
            }
        }
        private void doCustomPiShock(int opt,String duration,String itensity,String code) {
            String fName = "[doCustomPiShock]";
            try{
                logger.info(fName +".opt="+opt+",duration=" + duration+strItensity45+itensity+", code="+code);
                if(gNewUserProfile==null||!gNewUserProfile.isProfile()||gNewUserProfile.getMember().getIdLong()!=gMember.getIdLong()){
                    if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                }

                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strCantDueNotenabled), llColorRed);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Pet), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Public), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                    logger.info(fName + ".can't while the jacket is on and hands strapped");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantJacket1), llColorRed);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantJacket2), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                    logger.info(fName + ".can't while the mittens are on");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantMitts1), llColorRed);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantMitts2), llColorRed);
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                getShockers(); int codeIndex=-1;
                logger.info(fName+"readFile="+ getConfigValues());
                jsonObject.put(fieldUsername,gPiUsername);
                jsonObject.put(fieldApikey,gPiApiKey);
                jsonObject=getProfileAuthofication(jsonObject);
                boolean usedShocked=false;
                if(code==null||code.isBlank()){
                    usedShocked=true;
                }else{
                    codeIndex=getCode2Index(code);
                    if(codeIndex<0){
                        jsonObject.put(fieldCode,code);
                    }else{
                        usedShocked=true;
                    }
                }
                if(usedShocked){
                    logger.info(fName + ".codeIndex="+codeIndex);
                    if(codeIndex>=0){
                        selectDefaultShoker(codeIndex);
                    }else{
                        getDefaultShoker();
                    }
                    int selected=SHOCKER_INDEX;
                    logger.info(fName + ".selected="+selected);
                    if(selected<0){
                        logger.info(fName + ".no shocker selected");
                        embed.setDescription("None selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }else if(selected>=gNewUserProfile.cPISHOCK.getShockersSize()){
                        logger.info(fName + ".Invalid shocker selected");
                        embed.setDescription("Invalid shocker selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }
                    else {
                        logger.info(fName + ".shocker selected");
                        logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                        logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    }
                    jsonObject.put(fieldCode,cSHOCKER.getCode());
                }
                logger.info(fName+"code="+jsonObject.getString(fieldCode));
                int iDuration=0,iItensity=0;
                try {
                    if(duration!=null&&!duration.isBlank()){
                        iDuration=Integer.parseInt(duration);
                    }
                    logger.info(fName+"iDuration="+iDuration);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    if(itensity!=null&&!itensity.isBlank()){
                        iItensity=Integer.parseInt(itensity);
                    }
                    logger.info(fName+"iItensity="+iItensity);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                switch (opt){
                    case  optionBeep:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }
                        else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        jsonObject.put(fieldOp,2);
                        break;
                    case optionVibrate:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,1);
                        break;
                    case optionShock:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,0);
                        break;
                }
                logger.info(fName + ".ready to send");
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                logger.info(fName+"url="+gUrl);
                jsonObject.put(fieldName,gUser.getName());
                logger.info(fName+".jsonObject ="+jsonObject.toString());
                if(gDebugSend){
                    logger.info(fName + ".debug");
                    embed.setTitle(sRTitle+"(debug)");
                    embed.setDescription(jsonObject.toString());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                HttpResponse<String> jsonResponse =a.post(gUrl)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                logger.info(fName+".body ="+ jsonResponse.getBody());
                if(jsonResponse.getStatus()!=200){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }
                else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }else{
                    //success
                    switch (opt){
                        case  optionBeep:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep1,iDuration), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep2,iDuration), llColorYellow_Cream);
                            break;
                        case optionVibrate:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate2,iDuration,iItensity), llColorYellow_Cream);
                            break;
                        case optionShock:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock2,iDuration,iItensity), llColorYellow_Cream);
                            break;
                    }
                    loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                    if(code!=null&&!code.isBlank()&&!usedShocked){
                        dmAddDefaultShockerIfNotAdded(code);
                    }
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setDescription(desc.toString());
                    lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel),embed,secondsBeforeDelete);
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), gUser,sRTitle, e.toString());
            }
        }

        private void doBeep(Member target,String duration,String code,boolean isBypass) {
            String fName = "[doBeep]";
            logger.info(fName);
            doCustomPiShock(target,optionBeep,duration,"",code,isBypass);
        }
        private void doVibrate(Member target,String duration,String itensity,String code,boolean isBypass) {
            String fName = "[doVibrate]";
            logger.info(fName);
            doCustomPiShock(target,optionVibrate,duration,itensity,code,isBypass);
        }
        private void doShock(Member target,String duration,String itensity,String code,boolean isBypass) {
            String fName = "[doShock]";
            logger.info(fName);
            doCustomPiShock(target,optionShock,duration,itensity,code,isBypass);
        }
        boolean gDebugSend=false;
        private void doCustomPiShock(Member target,int opt,String duration,String itensity,boolean isBypass) {
            String fName = "[doCustomPiShock]";
            try{
                logger.info(fName +".target="+target.getId()+", opt="+opt+",duration=" + duration+strItensity45+itensity+", isBypass="+isBypass);
                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strTargetCantDueNotenabled), llColorRed);
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.info(fName + ".can't use do to white&black list");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock. strCantUseDueList), llColorRed);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strTargetCantDueAccess), llColorRed);
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                getShockers(); int codeIndex=-1;
                logger.info(fName+"readFile="+ getConfigValues());
                jsonObject.put(fieldUsername,gPiUsername);
                jsonObject.put(fieldApikey,gPiApiKey);
                jsonObject=getProfileAuthofication(jsonObject);
                if(cSHOCKER==null||!cSHOCKER.hasCode())getDefaultShoker();
                jsonObject.put(fieldCode,cSHOCKER.getCode());
                logger.info(fName+"code="+jsonObject.getString(fieldCode));
                int iDuration=0,iItensity=0;
                try {
                    if(duration!=null&&!duration.isBlank()){
                        iDuration=Integer.parseInt(duration);
                    }
                    logger.info(fName+"iDuration="+iDuration);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    if(itensity!=null&&!itensity.isBlank()){
                        iItensity=Integer.parseInt(itensity);
                    }
                    logger.info(fName+"iItensity="+iItensity);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                switch (opt){
                    case  optionBeep:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }
                        else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        jsonObject.put(fieldOp,2);
                        break;
                    case optionVibrate:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,1);
                        break;
                    case optionShock:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,0);
                        break;
                }
                if(!isBypass&&gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".ask mode");
                    messageUserInfoAsk=llSendQuickEmbedMessageResponse(gUser,sRTitle,stringReplacer("!TARGET is in ask modde, please wait till they confirm your command.."), llColorPurple2);
                    Message message=null;
                    switch (opt){
                        case  optionBeep:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a beep command to your shocker for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                        case optionVibrate:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a vibrate command to your shocker with "+iItensity+" itensity for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                        case optionShock:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a shock command to your shocker with "+iItensity+" itensity for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                    }
                    Message finalMessage = message;
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple));
                    if(gNewUserProfile.isOwnerAskedAsWell()){
                        messageOwnerUser=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                        messageOwner=llSendQuickEmbedMessageResponse(messageOwnerUser,sRTitle,stringReplacer("!USER wants to send a Pishock command. Do you accept it?"), llColorPurple2);
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple));
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple));
                    }
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (
                                    (e.getMessageId().equalsIgnoreCase(finalMessage.getId())&&e.getUserId().equalsIgnoreCase(target.getId()))
                                            ||
                                            (messageOwner!=null&&messageOwnerUser!=null&&e.getMessageId().equalsIgnoreCase(messageOwner.getId())&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId()))
                            ),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                                        logger.info(fName + ".approved");
                                        doCustomPiShock(target,opt,duration,itensity,true);
                                        if(messageOwnerUser!=null&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId())){
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET's owner approved your command."),llColorGreen1);
                                            llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("You approved !USER's command for !TARGET."),llColorGreen1);
                                        }else{
                                            if(messageOwner!=null&&messageOwnerUser!=null)llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("!TARGET approved !USER's command."),llColorGreen1);
                                        }

                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                        logger.info(fName + ".reject");
                                        if(messageOwnerUser!=null&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId())){
                                            logger.info(fName + ".reject");
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET's owner rejected your command."), llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("Your owner rejected !USER's command."),llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("You rejected !USER's command for !TARGET."),llColorRed_Cinnabar);

                                        }else{
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET rejected your command."), llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("You rejected !USER's command."),llColorRed_Cinnabar);
                                            if(messageOwner !=null&&messageOwnerUser!=null)llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("!TARGET rejected !USER's command."),llColorRed_Cinnabar);
                                        }
                                    }
                                    llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                    if(messageOwner !=null)llMessageDelete(messageOwner);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                    if(messageOwner !=null)llMessageDelete(messageOwner);
                                }
                            },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                                logger.info(fName + ".timeout");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(strWaitingPeriodImeout), llColorRed_Cinnabar);
                                llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                if(messageOwner !=null)llMessageDelete(messageOwner);
                            });
                    return;
                }
                logger.info(fName + ".ready to send");
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                logger.info(fName+"url="+gUrl);
                jsonObject.put(fieldName,gUser.getName());
                logger.info(fName+".jsonObject ="+jsonObject.toString());
                if(gDebugSend){
                    logger.info(fName + ".debug");
                    embed.setTitle(sRTitle+"(debug)");
                    embed.setDescription(jsonObject.toString());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                HttpResponse<String> jsonResponse =a.post(gUrl)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                logger.info(fName+".body ="+ jsonResponse.getBody());
                if(jsonResponse.getStatus()!=200){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }
                else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }else{
                    //success
                    switch (opt){
                        case  optionBeep:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep1,iDuration), llColorYellow_Cream);
                            llSendQuickEmbedMessage(target.getUser(),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep2,iDuration), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gTextChannel,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep3,iDuration), llColorYellow_Cream);
                            break;
                        case optionVibrate:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(target.getUser(),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate2,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gTextChannel,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate3,iDuration,iItensity), llColorYellow_Cream);
                            break;
                        case optionShock:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(target.getUser(),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock2,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gTextChannel,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock3,iDuration,iItensity), llColorYellow_Cream);
                            break;
                    }
                    loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setDescription(desc.toString());
                    lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel,embed,secondsBeforeDelete);
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doCustomPiShock(Member target,int opt,String duration,String itensity,String code,boolean isBypass) {
            String fName = "[doCustomPiShock]";
            try{
                logger.info(fName);
                logger.info(fName +".target="+target.getId()+", opt="+opt+",duration=" + duration+strItensity45+itensity+", code="+code+", isBypass="+isBypass);
                if(gNewUserProfile==null||!gNewUserProfile.isProfile()||gNewUserProfile.getMember().getIdLong()!=target.getIdLong()){
                    if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                }


                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strTargetCantDueNotenabled), llColorRed);
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
                if(!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.info(fName + ".can't use do to white&black list");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock. strCantUseDueList), llColorRed);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strTargetCantDueAccess), llColorRed);
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                getShockers(); int codeIndex=-1;
                logger.info(fName+"readFile="+ getConfigValues());
                jsonObject.put(fieldUsername,gPiUsername);
                jsonObject.put(fieldApikey,gPiApiKey);
                jsonObject=getProfileAuthofication(jsonObject);
                boolean usedShocked=false;
                if(code==null||code.isBlank()){
                    usedShocked=true;
                }else{
                    codeIndex=getCode2Index(code);
                    if(codeIndex<0){
                        jsonObject.put(fieldCode,code);
                    }else{
                        usedShocked=true;
                    }
                }
                if(usedShocked){
                    logger.info(fName + ".codeIndex="+codeIndex);
                    if(codeIndex>=0){
                        selectDefaultShoker(codeIndex);
                    }else{
                        getDefaultShoker();
                    }
                    int selected=SHOCKER_INDEX;
                    logger.info(fName + ".selected="+selected);
                    if(selected<0){
                        logger.info(fName + ".no shocker selected");
                        embed.setDescription("None selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }else if(selected>=gNewUserProfile.cPISHOCK.getShockersSize()){
                        logger.info(fName + ".Invalid shocker selected");
                        embed.setDescription("Invalid shocker selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }
                    else {
                        logger.info(fName + ".shocker selected");
                        logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                        logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    }
                    jsonObject.put(fieldCode,cSHOCKER.getCode());
                }
                logger.info(fName+"code="+jsonObject.getString(fieldCode));
                int iDuration=0,iItensity=0;
                try {
                    if(duration!=null&&!duration.isBlank()){
                        iDuration=Integer.parseInt(duration);
                    }
                    logger.info(fName+"iDuration="+iDuration);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    if(itensity!=null&&!itensity.isBlank()){
                        iItensity=Integer.parseInt(itensity);
                    }
                    logger.info(fName+"iItensity="+iItensity);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                switch (opt){
                    case  optionBeep:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }
                        else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        jsonObject.put(fieldOp,2);
                        break;
                    case optionVibrate:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,1);
                        break;
                    case optionShock:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,0);
                        break;
                }
                if(!isBypass&&gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".ask mode");
                    messageUserInfoAsk=llSendQuickEmbedMessageResponse(gUser,sRTitle,stringReplacer("!TARGET is in ask modde, please wait till they confirm your command.."), llColorPurple2);
                    Message message=null;
                    switch (opt){
                        case  optionBeep:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a beep command to your shocker for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                        case optionVibrate:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a vibrate command to your shocker with "+iItensity+" itensity for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                        case optionShock:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a shock command to your shocker with "+iItensity+" itensity for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                    }
                    Message finalMessage = message;
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple));
                    if(gNewUserProfile.isOwnerAskedAsWell()){
                        messageOwnerUser=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                        messageOwner=llSendQuickEmbedMessageResponse(messageOwnerUser,sRTitle,stringReplacer("!USER wants to send a Pishock command. Do you accept it?"), llColorPurple2);
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple));
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple));
                    }
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (
                                    (e.getMessageId().equalsIgnoreCase(finalMessage.getId())&&e.getUserId().equalsIgnoreCase(target.getId()))
                                            ||
                                            (messageOwner!=null&&messageOwnerUser!=null&&e.getMessageId().equalsIgnoreCase(messageOwner.getId())&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId()))
                            ),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                                        logger.info(fName + ".approved");
                                        doCustomPiShock(target,opt,duration,itensity,code,true);
                                        if(messageOwnerUser!=null&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId())){
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET's owner approved your command."),llColorGreen1);
                                            llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("You approved !USER's command for !TARGET."),llColorGreen1);
                                        }else{
                                            if(messageOwner!=null&&messageOwnerUser!=null)llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("!TARGET approved !USER's command."),llColorGreen1);
                                        }

                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                        logger.info(fName + ".reject");
                                        if(messageOwnerUser!=null&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId())){
                                            logger.info(fName + ".reject");
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET's owner rejected your command."), llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("Your owner rejected !USER's command."),llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("You rejected !USER's command for !TARGET."),llColorRed_Cinnabar);

                                        }else{
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET rejected your command."), llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("You rejected !USER's command."),llColorRed_Cinnabar);
                                            if(messageOwner !=null&&messageOwnerUser!=null)llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("!TARGET rejected !USER's command."),llColorRed_Cinnabar);
                                        }
                                    }
                                    llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                    if(messageOwner !=null)llMessageDelete(messageOwner);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                    if(messageOwner !=null)llMessageDelete(messageOwner);
                                }
                            },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                                logger.info(fName + ".timeout");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(strWaitingPeriodImeout), llColorRed_Cinnabar);
                                llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                if(messageOwner !=null)llMessageDelete(messageOwner);
                            });
                    return;
                }
                logger.info(fName + ".ready to send");
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                logger.info(fName+"url="+gUrl);
                jsonObject.put(fieldName,gUser.getName());
                logger.info(fName+".jsonObject ="+jsonObject.toString());
                if(gDebugSend){
                    logger.info(fName + ".debug");
                    embed.setTitle(sRTitle+"(debug)");
                    embed.setDescription(jsonObject.toString());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                HttpResponse<String> jsonResponse =a.post(gUrl)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                logger.info(fName+".body ="+ jsonResponse.getBody());
                if(jsonResponse.getStatus()!=200){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }
                else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                    desc.append("\n").append(jsonResponse.getBody());embed.setColor(llColorRed_Barn);
                }else{
                    //success
                    switch (opt){
                        case  optionBeep:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep1,iDuration), llColorYellow_Cream);
                            llSendQuickEmbedMessage(target.getUser(),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep2,iDuration), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gTextChannel,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep3,iDuration), llColorYellow_Cream);
                            break;
                        case optionVibrate:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(target.getUser(),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate2,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gTextChannel,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate3,iDuration,iItensity), llColorYellow_Cream);
                            break;
                        case optionShock:
                            llSendQuickEmbedMessage(gUser,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock1,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(target.getUser(),sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock2,iDuration,iItensity), llColorYellow_Cream);
                            llSendQuickEmbedMessage(gTextChannel,sRTitle,iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock3,iDuration,iItensity), llColorYellow_Cream);
                            break;
                    }
                    loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setDescription(desc.toString());
                    lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel,embed,secondsBeforeDelete);
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        JSONArray numbersStrList=new JSONArray();
        private void fillNumbersList(){
            String fName="[fillNumbersList]";
            logger.info(fName);
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
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuList(int type){
            String fName="[menuList]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    menuListWearer(type);
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuListOwner(type);
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuListWearer(int type){
            String fName="[menuListWearer]";
            logger.info(fName);
            try{
                logger.info(fName+" type="+ type);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                isMenuLevel=true;indexMenuLevel=5;optionMenuLevel=type;
                StringBuilder desc2= new StringBuilder();
                JSONArray members=new JSONArray();
                switch (type){
                    case 1:
                        members=WHITELISTMMEMBERS;
                        break;
                    case -1:
                        members=BLACKLISTMEMBERS;
                        break;
                }
                int length=members.length();
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    if(length<patreonLimitMembers){
                        switch (type){
                            case 1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list white add <@Member>`\nUp to 10 can be added.",false);
                                break;
                            case -1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list black add <@Member>`\nUp to 10 can be added.",false);
                                break;
                        }
                    }else{
                        embed.addField("To add member to list","Max member count reached, can't add more.",false);
                    }
                }else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                    if(length<patreonLimitMembers){
                        switch (type){
                            case 1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list white add <@Member>`\nUp to 10 can be added.",false);
                                break;
                            case -1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list black add <@Member>`\nUp to 10 can be added.",false);
                                break;
                        }
                    }else{
                        embed.addField("To add member to list","Max member count reached, can't add more.",false);
                    }
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    if(length<normalLimitMembers){
                        switch (type){
                            case 1:
                                embed.addField("To add member to list","Only Patreon supporters can add to white list!",false);
                                //embed.addField("To add member to list","`"+llPrefixStr+"list white add <@Member>`\nUp to 3 (+7 if Patreon supporter) can be added.",false);
                                break;
                            case -1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list black add <@Member>`\nUp to 3 (+7 if Patreon supporter) can be added.",false);
                                break;
                        }
                    }else{
                        embed.addField("To add member to list","Max member count reached (non patreon members), can't add more.",false);
                    }
                }

                fillNumbersList();
                for(int i=0;i<members.length();i++){
                    Member member=lsMemberHelper.lsGetMember(gGuild,members.getLong(i));
                    if(i<10){
                        if(member!=null){
                            desc2.append("\n:").append(numbersStrList.getString(i)).append(": ").append(member.getAsMention());
                        }else{
                            desc2.append("\n:").append(numbersStrList.getString(i)).append(": <@").append(members.getLong(i)).append(">");
                        }
                    }
                }
                switch (type){
                    case 1:
                        embed.addField("White list member", desc2.toString(),false);
                        break;
                    case -1:
                        embed.addField("Black list members", desc2.toString(),false);
                        break;
                }

                Message message=llSendMessageResponse(gUser,embed);
                if(length>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(length>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(length>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(length>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(length>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(length>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(length>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(length>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(length>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(length>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                //if(length>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                JSONArray finalMembers = members;
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                String strType="";
                                switch (type){
                                    case 1:
                                        strType=valueWhite;
                                        break;
                                    case -1:
                                        strType=valueBlack;
                                        break;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    rPishock("listremove",strType, finalMembers.getString(0));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    rPishock("listremove",strType, finalMembers.getString(1));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    rPishock("listremove",strType, finalMembers.getString(2));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    rPishock("listremove",strType, finalMembers.getString(3));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    rPishock("listremove",strType, finalMembers.getString(4));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    rPishock("listremove",strType, finalMembers.getString(5));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    rPishock("listremove",strType, finalMembers.getString(6));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    rPishock("listremove",strType, finalMembers.getString(7));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    rPishock("listremove",strType, finalMembers.getString(8));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    rPishock("listremove",strType, finalMembers.getString(9));
                                }
                                else{
                                    menuList(type);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void menuListOwner(int type){
            String fName="[menuListOwner]";
            logger.info(fName);
            try{
                logger.info(fName+" type="+ type);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="";
                isMenuLevel=true;indexMenuLevel=5;optionMenuLevel=type;
                StringBuilder desc2= new StringBuilder();
                JSONArray members=new JSONArray();
                switch (type){
                    case 1:
                        members=WHITELISTMMEMBERS;
                        break;
                    case -1:
                        members=BLACKLISTMEMBERS;
                        break;
                }
                int length=members.length();
                if(isPatreon){
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                    if(length<patreonLimitMembers){
                        switch (type){
                            case 1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list white add <@Member>`\nUp to 10 can be added.",false);
                                break;
                            case -1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list black add <@Member>`\nUp to 10 can be added.",false);
                                break;
                        }
                    }else{
                        embed.addField("To add member to list","Max member count reached, can't add more.",false);
                    }
                }else if (isPatreonGuild) {
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                    if(length<patreonLimitMembers){
                        switch (type){
                            case 1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list white add <@Member>`\nUp to 10 can be added.",false);
                                break;
                            case -1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list black add <@Member>`\nUp to 10 can be added.",false);
                                break;
                        }
                    }else{
                        embed.addField("To add member to list","Max member count reached, can't add more.",false);
                    }
                }
                else{
                    embed.setTitle(gNewUserProfile.gUserProfile.getUser().getName()+"'s "+sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                    if(length<normalLimitMembers){
                        switch (type){
                            case 1:
                                embed.addField("To add member to list","Only Patreon supporters can add to white list!",false);
                                //embed.addField("To add member to list","`"+llPrefixStr+"list white add <@Member>`\nUp to 3 (+7 if Patreon supporter) can be added.",false);
                                break;
                            case -1:
                                embed.addField("To add member to list","`"+llPrefixStr+"list black add <@Member>`\nUp to 3 (+7 if Patreon supporter) can be added.",false);
                                break;
                        }
                    }else{
                        embed.addField("To add member to list","Max member count reached (non patreon members), can't add more.",false);
                    }
                }

                fillNumbersList();
                for(int i=0;i<members.length();i++){
                    Member member=lsMemberHelper.lsGetMember(gGuild,members.getLong(i));
                    if(i<10){
                        if(member!=null){
                            desc2.append("\n:").append(numbersStrList.getString(i)).append(": ").append(member.getAsMention());
                        }else{
                            desc2.append("\n:").append(numbersStrList.getString(i)).append(": <@").append(members.getLong(i)).append(">");
                        }
                    }
                }
                switch (type){
                    case 1:
                        embed.addField("White list member", desc2.toString(),false);
                        break;
                    case -1:
                        embed.addField("Black list members", desc2.toString(),false);
                        break;
                }

                Message message=llSendMessageResponse(gUser,embed);
                if(length>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero));
                if(length>=2)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(length>=3)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                if(length>=4)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                if(length>=5)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                if(length>=6)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                if(length>=7)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix));
                if(length>=8)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven));
                if(length>=9)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight));
                if(length>=10)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine));
                //if(length>=1)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
                JSONArray finalMembers = members;
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                String strType="";
                                switch (type){
                                    case 1:
                                        strType=valueWhite;
                                        break;
                                    case -1:
                                        strType=valueBlack;
                                        break;
                                }
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                    return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(gNewUserProfile.getMember());return;
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(0));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(1));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(2));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(3));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(4));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(5));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(6));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(7));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(8));
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasNine))){
                                    rPishock(gNewUserProfile.getMember(),"listremove",strType, finalMembers.getString(9));
                                }
                                else{
                                    menuList(type);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        JSONArray WHITELISTMMEMBERS=new JSONArray(),BLACKLISTMEMBERS=new JSONArray();
        private boolean allowedToUse(){
            String fName="[allowedToUse]";
            logger.info(fName);
            try{
                int mode=gNewUserProfile.cPISHOCK.getFieldMode();
                logger.info(fName+"mode="+mode);
                if((mode==0||mode==1)&&!BLACKLISTMEMBERS.isEmpty()){
                    logger.info(fName+"BLACKLISTMEMBERS.length="+BLACKLISTMEMBERS.length());
                    for(int i=0;i<BLACKLISTMEMBERS.length();i++){
                        if(gMember.getIdLong()==BLACKLISTMEMBERS.getLong(i)){
                            logger.info(fName+"BLACKLISTMEMBERS.found>false");
                            return  false;
                        }
                    }
                }
                if((mode==0||mode==2)&&!WHITELISTMMEMBERS.isEmpty()){
                    logger.info(fName+"WHITELISTMMEMBERS.length="+WHITELISTMMEMBERS.length());
                    for(int i=0;i<WHITELISTMMEMBERS.length();i++){
                        if(gMember.getIdLong()==WHITELISTMMEMBERS.getLong(i)){
                            logger.info(fName+"WHITELISTMMEMBERS.found>true");
                            return  true;
                        }
                    }
                    logger.info(fName+"WHITELISTMMEMBERS.not found found>false");
                    return  false;
                }
                logger.info(fName+"default>true");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  true;
            }
        }

        JSONObject GAME=new JSONObject();
        private boolean getGameObject(){
            String fName="[getGameObject]";
            logger.info(fName);
            try{
               if(gGlobal.jsonObject.has(keyGame_Field)){
                   if(gGlobal.jsonObject.getJSONObject(keyGame_Field).has(gGuild.getId())){
                       GAME=gGlobal.jsonObject.getJSONObject(keyGame_Field).getJSONObject(gGuild.getId());
                       logger.info(fName+"got game object");
                       return  true;
                   }
               }
                logger.info(fName+"did not found game object");
                return  false;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        private boolean putGameObject(){
            String fName="[putGameObject]";
            logger.info(fName);
            try{
                if(!gGlobal.jsonObject.has(keyGame_Field)){
                    gGlobal.jsonObject.put(keyGame_Field,new JSONObject());
                }
                gGlobal.jsonObject.getJSONObject(keyGame_Field).put(gGuild.getId(),GAME);
                logger.info(fName+"put game object");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        private boolean createGameObject(){
            String fName="[createGameObject]";
            logger.info(fName);
            try{
                GAME=new JSONObject();
                GAME.put(keyGame_Roulette,new JSONObject());
                GAME.getJSONObject(keyGame_Roulette).put(keyGame_PlayersAray,new JSONArray());
                GAME.getJSONObject(keyGame_Roulette).put(keyGame_Turn,0);
                logger.info(fName+"created");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
        private void menuGame(){
            String fName="[menuGame]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                    }
                }
                if(!getGameObject()){
                    if(createGameObject()){
                        putGameObject();
                    }
                }
                if(gNewUserProfile.getMember().getIdLong()==gMember.getIdLong()){
                    menuGame_Wearer();
                }else{
                    if(gIsOverride||gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)||gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        menuGame_Owner();
                    }else{
                        menuGame_Somebody();
                    }
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuGame_Wearer(){
            String fName="[menuGame_Wearer]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(sRTitle);
                getShockers(); isMenuLevel=true;indexMenuLevel=6;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);isWearerAccessLimited=false;
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc2="";desc="";
                logger.info(fName + ".hasGameLoseShocker=" + hasGameLoseShocker+", hasGameWinShocker="+hasGameWinShocker);
                if(hasGameLoseShocker){
                    try{
                        logger.info(fName+"SHOCKERGAMELOSE="+cSHOCKERGAMELOSE.getJSON().toString());
                        desc+="\n**game lose**";
                        desc+="\ncode: "+cSHOCKERGAMELOSE.getCode();
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKERGAMELOSE.getActionKeyAsJSON(nPishockAction_main);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nAction: "+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nAction: (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError game lose shocker!";
                    }
                }
                else{
                    desc+="\nHas no game lose shocker, go to shockers and select one. This is a required shocker!";
                }
                if(hasGameWinShocker){
                    try{
                        logger.info(fName+"SHOCKERGAMELOSE="+cSHOCKERGAMEWIN.getJSON().toString());
                        desc+="\n**game lose**";
                        desc+="\ncode: "+cSHOCKERGAMEWIN.getCode();
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKERGAMEWIN.getActionKeyAsJSON(nPishockAction_main);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nAction: "+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nAction: (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError game win shocker!";
                    }
                }
                else{
                    desc+="\nHas no game win shocker. This is an optional shocker.";
                }
                embed.addField("Shockers",desc,false);
                desc=""+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace)+" set up game lose shocker";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace)+" set up game win shocker";
                embed.addField("Options",desc,false);
                desc="`"+llPrefixStr+gCommand+" rps <@Player2>`";
                embed.addField("Rock paper scissors",desc,false);
                desc="`"+llPrefixStr+gCommand+" hilow`";
                embed.addField("Hi/Low",desc,false);
                desc="\nThis games also support punishing via Pishock but they not required in order to play.";
                desc+="\n`"+llPrefixStr+"minesweeper`";
                desc+="\n`"+llPrefixStr+"hangman`";
                embed.addField("Other games",desc,false);
                String commandQ2=llPrefixStr+gCommand+" "+keyGame_Roulette+" ";
                desc="\n`"+commandQ2+"` for menu";
                desc+="\n`"+commandQ2+"status`";
                desc="\n`"+commandQ2+"join` to join";
                desc+="\n`"+commandQ2+"leave` to leave";
                desc+="\n`"+commandQ2+"draw` to randomly shock a player";
                desc+="\n`"+commandQ2+"clear` to clear the scores";
                desc+="\n`"+commandQ2+"reset`";
                embed.addField("Roulette (not tested)",desc,false);
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".is set to owner access");
                    embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                    isWearerAccessLimited =true;
                }else
                if(!gIsOverride&&isAccess2Public(gNewUserProfile.gUserProfile)){
                    logger.info(fName + ".is set to public access");
                    embed.addField("Restricted","Do to access setting, your options are restricted!",false);
                    isWearerAccessLimited =true;
                }
                Message message=llSendMessageResponse(gUser,embed);
                if(isWearerAccessLimited){
                    if(cSHOCKERGAMELOSE.getActions(nPishockAction_main).isEmpty()&&hasGameLoseShocker)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace));
                    if(cSHOCKERGAMEWIN.getActions(nPishockAction_main).isEmpty()&&hasGameWinShocker)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace));
                }else{
                    if(hasGameLoseShocker)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace));
                    if(hasGameWinShocker)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace));
                }

                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                   menuPiShock(null);

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace))){
                                    SHOCKER_TYPE=4;
                                    dmInputAction(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace))){
                                    SHOCKER_TYPE=3;
                                    dmInputAction(0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuGame_Owner(){
            String fName="[menuGame_Owner]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(sRTitle);
                getShockers(); isMenuLevel=true;indexMenuLevel=6;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc2="";desc="";
                logger.info(fName + ".hasGameLoseShocker=" + hasGameLoseShocker+", hasGameWinShocker="+hasGameWinShocker);
                if(hasGameLoseShocker){
                    try{
                        logger.info(fName+"SHOCKERGAMELOSE="+cSHOCKERGAMELOSE.getJSON().toString());
                        desc+="\n**game lose**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKERGAMELOSE.getActionKeyAsJSON(nPishockAction_main);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nAction: "+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nAction: (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError game lose shocker!";
                    }
                }
                else{
                    desc+="\nHas no game lose shocker, go to shockers and select one. This is a required shocker!";
                }
                if(hasGameWinShocker){
                    try{
                        logger.info(fName+"SHOCKERGAMELOSE="+cSHOCKERGAMEWIN.getJSON().toString());
                        desc+="\n**game lose**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKERGAMEWIN.getActionKeyAsJSON(nPishockAction_main);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nAction: "+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nAction: (null)";
                        }
                        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace)+" to edit.";
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError game win shocker!";
                    }
                }
                else{
                    desc+="\nHas no game win shocker. This is an optional shocker.";
                }
                embed.addField("Shockers",desc,false);
                desc=""+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace)+" set up game lose shocker";
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace)+" set up game win shocker";
                embed.addField("Options",desc,false);
                desc="`"+llPrefixStr+gCommand+" rps <@Player2>`";
                embed.addField("Rock paper scissors",desc,false);
                desc="\nThis games also support punishing via Pishock but they not required in order to play.";
                desc+="\n`"+llPrefixStr+"minesweeper`";
                desc+="\n`"+llPrefixStr+"hangman`";
                embed.addField("Other games",desc,false);
                Message message=llSendMessageResponse(gUser,embed);
                if(hasGameLoseShocker)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace));
                if(hasGameWinShocker)lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(null);

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFirstPlace))){
                                    SHOCKER_TYPE=4;
                                    dmInputAction(0);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThirdPlace))){
                                    SHOCKER_TYPE=3;
                                    dmInputAction(0);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void menuGame_Somebody(){
            String fName="[menuGame_Somebody]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();
                embed.setTitle(sRTitle);
                getShockers(); isMenuLevel=true;indexMenuLevel=6;
                isPatreon=lsPatreon.hasPatreonTierOrAbove(gGlobal,gMember,2);isPatreonGuild=lsPatreon.hasPatreonTier(gGlobal,gGuild,69);
                logger.info(fName + ".isPatreon=" + isPatreon+", isPatreonGuild="+isPatreonGuild);
                if(isPatreon){
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2True));
                }
                else if (isPatreonGuild) {
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonGuild));
                }
                else{
                    embed.setTitle(sRTitle+" "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPatreonTier2False));
                }
                desc2="";desc="";
                logger.info(fName + ".hasGameLoseShocker=" + hasGameLoseShocker+", hasGameWinShocker="+hasGameWinShocker);
                if(hasGameLoseShocker){
                    try{
                        logger.info(fName+"SHOCKERGAMELOSE="+cSHOCKERGAMELOSE.getJSON().toString());
                        desc+="\n**game lose**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKERGAMELOSE.getActionKeyAsJSON(nPishockAction_main);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nAction: "+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nAction: (null)";
                        }
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError game lose shocker!";
                    }
                }
                else{
                    desc+="\nHas no game lose shocker. This is a required shocker!";
                }
                if(hasGameWinShocker){
                    try{
                        logger.info(fName+"SHOCKERGAMELOSE="+cSHOCKERGAMEWIN.getJSON().toString());
                        desc+="\n**game lose**";
                        desc+="\ncode: (hidden)";
                        JSONArray tasks=new JSONArray();
                        try{
                            desc3= new StringBuilder();
                            tasks=cSHOCKERGAMEWIN.getActionKeyAsJSON(nPishockAction_main);
                            for(int i=0;i<tasks.length();i++){
                                JSONObject task=tasks.getJSONObject(i);
                                String  mode=task.getString(nPishockMode).toLowerCase();
                                int duration=0;
                                int itensity=0;
                                if(mode.equalsIgnoreCase(vPishockMode_Beep)){
                                    duration=task.getInt(nPishockDuration);
                                }
                                if(mode.equalsIgnoreCase(vPishockMode_Vibrate)||mode.equalsIgnoreCase(vPishockMode_Shock)){
                                    duration=task.getInt(nPishockDuration);
                                    itensity=task.getInt(nPishockItensity);
                                }
                                desc3.append("\n").append(mode).append(",").append(duration).append(",").append(itensity);
                            }
                            desc+="\nAction: "+desc3.toString();
                        } catch (Exception e) {
                            logger.error(fName+".exception=" + e);
                            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                            desc+="\nAction: (null)";
                        }
                    } catch (Exception e) {
                        logger.error(fName+".exception=" + e);
                        logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                        desc+="\nError game win shocker!";
                    }
                }
                else{
                    desc+="\nHas no game win shocker. This is an optional shocker.";
                }
                embed.addField("Shockers",desc,false);
                desc="`"+llPrefixStr+gCommand+" rps <@Player2>`";
                embed.addField("Rock paper scissors",desc,false);
                desc="\nThis games also support punishing via Pishock but they not required in order to play.";
                desc+="\n`"+llPrefixStr+"minesweeper`";
                desc+="\n`"+llPrefixStr+"hangman`";
                embed.addField("Other games",desc,false);
                Message message=llSendMessageResponse(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuPiShock(null);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doGameShocker(int action){
            String fName="[doGameShocker]";
            logger.info(fName);
            try{
                logger.info(fName+"action="+action);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}

                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    return;
                }
                if(!(gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.warn(fName + ".can't use do to white&black list");
                    return;
                }
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                JSONObject jsonObject=new JSONObject();
                a.config().verifySsl(false);
                entityShocker shocker=new entityShocker();
                getShockers();getDefaultShoker();
                if(action==1){
                    if(!cSHOCKERGAMELOSE.hasCode()){
                        logger.info(fName + "has no such game shocker");
                        return;
                    }
                    shocker=cSHOCKERGAMELOSE;
                }
                else if(action==2){
                    if(!cSHOCKERGAMEWIN.hasCode()){
                        logger.info(fName + "has no such game shocker");
                        return;
                    }
                    shocker=cSHOCKERGAMEWIN;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null >use main");
                    shocker=cSHOCKER;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null , attempt 2");
                    return;
                }
                logger.info(fName + "shocker=" + shocker.getJSON().toString());
                entityShockerActions tasks=new entityShockerActions();
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code > use main");
                    shocker=cSHOCKER;
                }
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code , attempt 2");
                    return;
                }
                tasks =shocker.getActions(nPishockAction_main);
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty > use main");
                    tasks =cSHOCKER.getActions(iRestraints.nPishockAction_main);
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty attempt 2");
                    return;
                }
                logger.info(fName + "tasks=" + tasks.toString());
                if(doHttp(shocker,tasks)){
                    logger.info(fName + "success");
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void doGameShocker(Member target,int action){
            String fName="[doGameShocker]";
            logger.info(fName);
            try{
                logger.info(fName+"action="+action);
                if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}


                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    return;
                }
                if(!(gTarget==null||gTarget.getIdLong()==gMember.getIdLong())&&!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.warn(fName + ".can't use do to white&black list");
                    return;
                }
                getShockers();getDefaultShoker();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                Unirest a= new Unirest();
                JSONObject jsonObject=new JSONObject();
                a.config().verifySsl(false);
                entityShocker shocker=new entityShocker();
                if(action==1){
                    if(!cSHOCKERGAMELOSE.hasCode()){
                        logger.info(fName + "has no such game shocker");
                        return;
                    }
                    shocker=cSHOCKERGAMELOSE;
                }
                else if(action==2){
                    if(!cSHOCKERGAMEWIN.hasCode()){
                        logger.info(fName + "has no such game shocker");
                        return;
                    }
                    shocker=cSHOCKERGAMEWIN;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null >use main");
                    shocker=cSHOCKER;
                }
                if(shocker==null){
                    logger.warn(fName + "shocker is null , attempt 2");
                    return;
                }
                logger.info(fName + "shocker=" + shocker.getJSON().toString());
                entityShockerActions tasks=new entityShockerActions();
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code > use main");
                    shocker=cSHOCKER;
                }
                if(!shocker.hasCode()){
                    logger.warn(fName + "nPishock has no code , attempt 2");
                    return;
                }
                tasks =shocker.getActions(nPishockAction_main);
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty > use main");
                    tasks =cSHOCKER.getActions(iRestraints.nPishockAction_main);
                }
                if(tasks==null||tasks.isEmpty()||tasks.length()==0){
                    logger.warn(fName + "tasks is null or empty attempt 2");
                    return;
                }
                logger.info(fName + "tasks=" + tasks.toString());
                if(doHttp(shocker,tasks)){
                    logger.info(fName + "success");
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        String titleHiLow="PiShock Hi/Low Number";
        private void gameHiLow_1(){
            String fName="[gameHiLow_1]";
            logger.info(fName);
            try{
                getProfile(gMember);
                getShockers();getJSON();
                if(!hasGameLoseShocker
                        ||!gNewUserProfile.cPISHOCK.isEnabled()
                        ||!cSHOCKERGAMELOSE.hasCode()
                ){
                    logger.warn(fName+"main player has no shocker or disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRPS,"Invalid player "+gMember.getAsMention()+"!", llColors_Red.llColorRed_Barn);
                    return;
                }
                gameHiLow_2();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameHiLow_2(){
            String fName="[gameHiLow_2]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(titleHiLow);embedBuilder.setColor(llColorYellow_Canary);
                embedBuilder.setDescription("Say a number. That number used as reference number.\nThe random number can be bigger or smaller than the refrence number.");
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embedBuilder);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    ;return;
                                }
                                int value=0;
                                try {
                                   value=Integer.parseInt(content);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Is not a number!");
                                    return;
                                }
                                if(value<3){
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Needs to be above 2!");
                                    return;
                                }
                                gameHiLow_3(value);
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameHiLow_3(int refrence){
            String fName="[gameHiLow_3]";
            logger.info(fName);
            try{
                int random=lsUsefullFunctions.getRandomToMax(refrence*2);
                logger.info(fName+".refrence="+refrence+", random="+random);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(titleHiLow);embedBuilder.setColor(llColorYellow_Canary);
                embedBuilder.setDescription("Say a number. That number will be used as reference point.");
                Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embedBuilder);
                gWaiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    //nothing or canceled >return
                                    ;return;
                                }
                                int value=0;
                                try {
                                    value=Integer.parseInt(content);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,"Is not a number!");
                                    return;
                                }
                                if(random<refrence&&value<refrence){
                                    //low
                                    gameHiLow_4(refrence,random, value,true);
                                }
                                else if(random>refrence&&value>refrence){
                                    //high
                                    gameHiLow_4(refrence,random, value,true);
                                }
                                else if(random==refrence&&value==refrence){
                                    //equal
                                    gameHiLow_4(refrence,random, value,true);
                                }else{
                                    gameHiLow_4(refrence,random, value,false);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, sRTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameHiLow_4(int refrence,int random, int selected,boolean correct){
            String fName="[gameHiLow_4]";
            logger.info(fName);
            try{
                logger.info(fName+".refrence="+refrence+", random="+random+", selected="+selected+", correct="+correct);
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(titleHiLow);embedBuilder.setColor(llColorYellow_Canary);
                if(correct){
                    embedBuilder.setDescription(gMember.getAsMention()+" was correct and avoided to get shocked.\nChoice number: "+selected+"\nRandom number: "+random+"\nReference number: "+refrence);
                }else{
                    embedBuilder.setDescription(gMember.getAsMention()+" was wrong and now they going to get shocked.\nChoice number: "+selected+"\nRandom number: "+random+"\nReference number: "+refrence);
                }
                Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                if(!correct){
                    doGameShocker(1);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        String titleRPS="PiShock Rock paper scissors";
        private void gameRockPaperScisors_1(){
            String fName="[gameRockPaperScisors]";
            logger.info(fName);
            try{
                List<Member>members=gCommandEvent.getMessage().getMentionedMembers();
                if(members.isEmpty()){
                    logger.warn(fName+"one member needs to be mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRPS,"One extra player you need! Please mention them.", llColors_Red.llColorRed_Barn);
                    return;
                }
                if(members.size()>1){
                    logger.warn(fName+"only one member can be mentioned");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRPS,"Only two players can play this!", llColors_Red.llColorRed_Barn);
                    return;
                }
                Member member=members.get(0);
                if(member.getIdLong()==gMember.getIdLong()){
                    logger.warn(fName+"cant be same member");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRPS,"Needs to be a different member!", llColors_Red.llColorRed_Barn);
                    return;
                }
                lcJSONUserProfile gOtherPlayerProfile=iRestraints.getThisPersonProfile(gGlobal,member);
                if(gOtherPlayerProfile==null
                        ||!gOtherPlayerProfile.jsonObject.has(nPishock)
                        ||!gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).has(nPishockGameLoseShocker)
                        ||!gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getJSONObject(nPishockGameLoseShocker).has(nPishockCode)
                        ||gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getJSONObject(nPishockGameLoseShocker).getString(nPishockCode).isBlank()
                        ||!gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).has(nEnabled)
                        ||!gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getBoolean(nEnabled)

                ){
                    logger.warn(fName+"other player has no profile or shocker or disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRPS,"Invalid player "+member.getAsMention()+"!", llColors_Red.llColorRed_Barn);
                    return;
                }
                getProfile(gMember);
                getShockers();getJSON();
                if(!hasGameLoseShocker
                        ||!gNewUserProfile.cPISHOCK.isEnabled()
                        ||!cSHOCKERGAMELOSE.hasCode()
                ){
                    logger.warn(fName+"main player has no shocker or disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRPS,"Invalid player "+gMember.getAsMention()+"!", llColors_Red.llColorRed_Barn);
                    return;
                }
                gameRockPaperScisors_2(gOtherPlayerProfile);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameRockPaperScisors_2(lcJSONUserProfile gOtherPlayerProfile){
            String fName="[gameRockPaperScisors]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1); embed.setTitle(titleRPS);
                embed.setDescription("Please select an option.\nAfter that "+gOtherPlayerProfile.getMember().getAsMention()+" will select theirs.");
                Message messagePlayer1=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
                lsMessageHelper.lsMessageAddReactions(messagePlayer1,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRock));
                lsMessageHelper.lsMessageAddReactions(messagePlayer1,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRollOfPaper));
                lsMessageHelper.lsMessageAddReactions(messagePlayer1,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors));
                embed.setDescription("Please wait while"+gMember.getAsMention()+" performs their selection.");
                Message messagePlayer2a=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gOtherPlayerProfile.getMember().getUser(),embed);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==messagePlayer1.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+asCodepoints);
                                llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messagePlayer2a);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRock))){
                                    gameRockPaperScisors_2(gOtherPlayerProfile,1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRollOfPaper))){
                                    gameRockPaperScisors_2(gOtherPlayerProfile,2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors))){
                                    gameRockPaperScisors_2(gOtherPlayerProfile,3);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(messagePlayer1);
                                llMessageDelete(messagePlayer2a);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(messagePlayer1);
                            llMessageDelete(messagePlayer2a);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameRockPaperScisors_2(lcJSONUserProfile gOtherPlayerProfile,int option1){
            String fName="[gameRockPaperScisors]";
            logger.info(fName);
            try{
                logger.info(fName+"option1="+option1);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1); embed.setTitle(titleRPS);
                embed.setDescription("Please select an option.");
                Message messagePlayer1=lsMessageHelper.lsSendMessageResponse(gOtherPlayerProfile.getMember().getUser(),embed);
                lsMessageHelper.lsMessageAddReactions(messagePlayer1,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRock));
                lsMessageHelper.lsMessageAddReactions(messagePlayer1,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRollOfPaper));
                lsMessageHelper.lsMessageAddReactions(messagePlayer1,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors));
                embed.setDescription("Please wait while"+gOtherPlayerProfile.getMember().getAsMention()+" performs their selection.");
                Message messagePlayer2a=lsMessageHelper.lsSendMessageResponse(gUser,embed);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==messagePlayer1.getIdLong()&&e.getUserIdLong()==gOtherPlayerProfile.getMember().getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+asCodepoints);
                                llMessageDelete(e.getChannel(),e.getMessageId());llMessageDelete(messagePlayer2a);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRock))){
                                    gameRockPaperScisors_2(gOtherPlayerProfile,option1,1);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRollOfPaper))){
                                    gameRockPaperScisors_2(gOtherPlayerProfile,option1,2);
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasScissors))){
                                    gameRockPaperScisors_2(gOtherPlayerProfile,option1,3);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(messagePlayer1);
                                llMessageDelete(messagePlayer2a);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(messagePlayer1);
                            llMessageDelete(messagePlayer2a);
                        });
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameRockPaperScisors_2(lcJSONUserProfile gOtherPlayerProfile,int option1,int option2){
            String fName="[gameRockPaperScisors]";
            logger.info(fName);
            try{
                logger.info(fName+"option1="+option1+", option2="+option2);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1); embed.setTitle(titleRPS);
                entityShocker shocker=new entityShocker();
                entityShockerActions tasks=new entityShockerActions();
                //1==rock 2==paper 3==scrissors
                //rock vs paper == paper
                //rock vs scrissors == rock
                //rock vs rock == draw
                //paper vs scrissors == scrissors
                //papper vs papper == draw
                //scissors vs scissors == draw
                boolean lose1=false, lose2=false;
                if(option1==option2){
                    lose1=true;lose2=true;

                }else{
                    switch (option1){
                        case 1://rock
                            if(option2==2)lose1=true;
                            else lose2=true;
                            break;
                        case 2://papper
                            if(option2==3)lose1=true;
                            else lose2=true;
                            break;
                        case 3://scissors
                            if(option2==1)lose1=true;
                            else lose2=true;
                            break;
                    }
                }
                logger.info(fName+"lose1="+lose1+", lose2="+lose2);
                if(lose1&&lose2){
                    llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), titleRPS, stringReplacer(gOtherPlayerProfile.getMember(),"Both !WEARER and !USER lose."), llColorYellow_Cream);
                }else
                if(lose1){
                    llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), titleRPS, stringReplacer(gOtherPlayerProfile.getMember(),"!WEARER loses againts !USER."), llColorYellow_Cream);

                }else
                if(lose2){
                    llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), titleRPS, stringReplacer(gOtherPlayerProfile.getMember(),"!USER loses againts !WEARER."), llColorYellow_Cream);

                }else{
                    llSendQuickEmbedMessage(gBDSMCommands.restraints.doRedirectNotificationChanel(gTextChannel), titleRPS, stringReplacer(gOtherPlayerProfile.getMember(),"Nobody loses, !WEARER and !USER are lucky."), llColorYellow_Cream);

                }

                if(lose1){
                    shocker=cSHOCKERGAMELOSE;
                    logger.info(fName+"shocker="+shocker.toString());
                    tasks=shocker.getActions(nPishockAction_main);
                    logger.info(fName+"tasks="+tasks.toString());
                    doHttp(shocker,tasks);
                    postHTTPErrorMessages(gTextChannel,gMember);
                }else{
                    if(hasGameWinShocker){
                        shocker=cSHOCKERGAMEWIN;
                        logger.info(fName+"shocker="+shocker.toString());
                        tasks=shocker.getActions(nPishockAction_main);
                        logger.info(fName+"tasks="+tasks.toString());
                        doHttp(shocker,tasks);
                        postHTTPErrorMessages(gTextChannel,gMember);
                    }
                }
                if(lose2){
                    shocker.set(gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getJSONObject(nPishockGameLoseShocker));
                    logger.info(fName+"shocker="+shocker.toString());
                    tasks=shocker.getActions(nPishockAction_main);
                    logger.info(fName+"tasks="+tasks.toString());
                    doHttp(shocker,tasks);
                    postHTTPErrorMessages(gTextChannel,gOtherPlayerProfile.getMember());
                }else{
                    if(gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).has(nPishockGameWinShocker)
                            &&gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getJSONObject(nPishockGameWinShocker).has(nPishockCode)
                            &&!gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getJSONObject(nPishockGameWinShocker).getString(nPishockCode).isBlank()
                    ){
                        shocker.set(gOtherPlayerProfile.jsonObject.getJSONObject(nPishock).getJSONObject(nPishockGameWinShocker));
                        logger.info(fName+"shocker="+shocker.toString());
                        tasks=shocker.getActions(nPishockAction_main);
                        logger.info(fName+"tasks="+tasks.toString());
                        doHttp(shocker,tasks);
                        postHTTPErrorMessages(gTextChannel,gOtherPlayerProfile.getMember());
                    }
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        class Player{
            public JSONObject jsonPlayer=new JSONObject();
            public Player(){ }
            public Player(JSONObject jsonObject){
                String fName="[Player]";
                try {
                    jsonPlayer=jsonObject;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Player(Member member){
                String fName="[Player]";
                try {
                    jsonPlayer.put(llCommonKeys.keyUserId,member.getIdLong());
                    jsonPlayer.put(keyGame_Won,0);
                    jsonPlayer.put(keyGame_lost,0);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Player(Member member,JSONObject shockerLost){
                String fName="[Player]";
                try {
                    jsonPlayer.put(llCommonKeys.keyUserId,member.getIdLong());
                    jsonPlayer.put(keyGame_Won,0);
                    jsonPlayer.put(keyGame_lost,0);
                    jsonPlayer.put(nPishockGameLoseShocker,shockerLost);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public Player(Member member,JSONObject shockerLost,JSONObject shockerWon){
                String fName="[Player]";
                try {
                    jsonPlayer.put(llCommonKeys.keyUserId,member.getIdLong());
                    jsonPlayer.put(keyGame_Won,0);
                    jsonPlayer.put(keyGame_lost,0);
                    jsonPlayer.put(nPishockGameLoseShocker,shockerLost);
                    jsonPlayer.put(nPishockGameWinShocker,shockerWon);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }

            }
            public long getId(){
                String fName="[getId]";
                try {
                    long result=jsonPlayer.getLong(llCommonKeys.keyUserId);
                    logger.info(fName + ".result=" + result);
                    return  result;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  -1;
                }
            }
            public Member getMember(){
                String fName="[getMember]";
                try {
                    Member result=lsMemberHelper.lsGetMemberById(gGuild,getId());
                    if(result==null){
                        logger.info(fName + ".result is null");
                        return  null;
                    }
                    logger.info(fName + ".result=" + result.getUser().getName());
                    return  result;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  null;
                }
            }
            public int getLost(){
                String fName="[getLost]";
                try {
                    int result=jsonPlayer.getInt(keyGame_lost);
                    logger.info(fName + ".result=" + result);
                    return  result;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  -1;
                }
            }
            public int getWon(){
                String fName="[getWon]";
                try {
                    int result=jsonPlayer.getInt(keyGame_Won);
                    logger.info(fName + ".result=" + result);
                    return  result;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  -1;
                }
            }
            public boolean lost(){
                String fName="[lost]";
                try {
                    jsonPlayer.put(keyGame_lost,getLost()+1);
                    entityShocker shocker=new entityShocker(jsonPlayer.getJSONObject(nPishockGameLoseShocker));
                    if(!doHttp(shocker,shocker.getActions(nPishockAction_main))){
                        postHTTPErrorMessages(gTextChannel);
                        return  false;
                    }
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  false;
                }
            }
            public boolean won(){
                String fName="[won]";
                try {
                    jsonPlayer.put(keyGame_Won,getWon()+1);
                    if(jsonPlayer.has(nPishockGameWinShocker)&&!jsonPlayer.isNull(nPishockGameWinShocker)){
                        entityShocker shocker=new entityShocker(jsonPlayer.getJSONObject(nPishockGameWinShocker));
                        if(!doHttp(shocker,shocker.getActions(nPishockAction_main))){
                            postHTTPErrorMessages(gTextChannel);
                            return  false;
                        }
                    }
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  false;
                }
            }
        }
        class Roulette{
            public JSONObject jsonRoulette=new JSONObject();
            public JSONArray playersArray=new JSONArray();
            public Roulette(){ }
            public Roulette(JSONObject jsonObject){
                String fName="[Roulette]";
                try {
                    setJSON(jsonObject);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            public boolean setJSON(JSONObject jsonObject){
                String fName="[setJSON]";
                try {
                    if(jsonObject.has(keyGame_Roulette)){
                        jsonRoulette=jsonObject.getJSONObject(keyGame_Roulette);
                    }else{
                        jsonRoulette=jsonObject;
                    }
                    playersArray=jsonRoulette.getJSONArray(keyGame_PlayersAray);
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  false;
                }
            }
            public JSONObject getJSON(){
                String fName="[getJSON]";
                try {
                    jsonRoulette.put(keyGame_PlayersAray,playersArray);
                    return  jsonRoulette;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  null;
                }
            }
            public boolean reset(){
                String fName="[reset]";
                try {
                    jsonRoulette=new JSONObject();
                    playersArray=new JSONArray();
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  false;
                }
            }
            public boolean clear(){
                String fName="[clear]";
                try {
                    for(int i=0;i<playersArray.length();i++){
                        playersArray.getJSONObject(i).put(keyGame_Won,0);
                        playersArray.getJSONObject(i).put(keyGame_lost,0);
                    }
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return  false;
                }
            }
            public int getSize(){
                String fName="[getSize]";
                try {
                    int result=playersArray.length();
                    logger.info(fName+"result="+result);
                    return result;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return -1;
                }
            }
            public List<Player> getPlayers(){
                String fName="[getPlayers]";
                try {
                    List<Player>listPlayers=new ArrayList<>();
                    for(int i=0;i<playersArray.length();i++){
                        JSONObject playerJson=playersArray.getJSONObject(i);
                        Player player=new Player(playerJson);
                        listPlayers.add(player);
                    }
                    logger.info(fName+"listPlayers.size="+listPlayers.size());
                    return listPlayers;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public List<Member> getPlayersAsMember(){
                String fName="[getPlayersAsMember]";
                try {
                    List<Member>listPlayers=new ArrayList<>();
                    for(int i=0;i<playersArray.length();i++){
                        JSONObject playerJson=playersArray.getJSONObject(i);
                        Player player=new Player(playerJson);
                        listPlayers.add(player.getMember());
                    }
                    logger.info(fName+"listPlayers.size="+listPlayers.size());
                    return listPlayers;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Player getPlayer(int index){
                String fName="[getPlayer]";
                try {
                    logger.info(fName+"index="+index);
                    if(index<0){
                        return null;
                    }
                    if(index>=playersArray.length()){
                        return null;
                    }
                    JSONObject playerJson=playersArray.getJSONObject(index);
                    return new Player(playerJson);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Player getPlayer(Member member){
                String fName="[getPlayer]";
                try {
                    logger.info(fName+"member="+member.getUser().getName()+"("+member.getId()+")");
                    for(int i=0;i<playersArray.length();i++){
                        JSONObject playerJson=playersArray.getJSONObject(i);
                        if(playerJson.getLong(llCommonKeys.keyUserId)==member.getIdLong()){
                            logger.info(fName+"found player");
                            return new Player(playerJson);
                        }
                    }
                    logger.info(fName+"not found player");
                    return null;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public Player addPlayer(Member member,JSONObject shockerLost,JSONObject shockerWon){
                String fName="[addPlayer]";
                try {
                    logger.info(fName+"member="+member.getUser().getName()+"("+member.getId()+")");
                    Player player=null;
                    if(shockerLost!=null&&!shockerLost.isEmpty()&&shockerWon!=null&&!shockerWon.isEmpty()){
                        player=new Player(member,shockerLost,shockerWon);
                    }
                    else if(shockerLost!=null&&!shockerLost.isEmpty()){
                        player=new Player(member,shockerLost);
                    }
                    if(player==null){
                        logger.info(fName+"failed to create player");
                        return null;
                    }
                    playersArray.put(player.jsonPlayer);
                    logger.info(fName+"player added");
                    return player;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean updatePlayer(Player player,JSONObject shockerLost,JSONObject shockerWon){
                String fName="[updatePlayer]";
                try {
                    if(shockerLost!=null&&!shockerLost.isEmpty()&&shockerWon!=null&&!shockerWon.isEmpty()){
                        player.jsonPlayer.put(nPishockGameLoseShocker,shockerLost);
                        player.jsonPlayer.put(nPishockGameWinShocker,shockerWon);
                    }
                    else if(shockerLost!=null&&!shockerLost.isEmpty()){
                        player.jsonPlayer.put(nPishockGameLoseShocker,shockerLost);
                    }
                    return  updatePlayer(player);
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean updatePlayer(Player player){
                String fName="[updatePlayer]";
                try {
                    long playerId=player.getId();
                    logger.info(fName+"playerId="+playerId);
                    Member member=lsMemberHelper.lsGetMemberById(gGuild,playerId);
                    for(int i=0;i<playersArray.length();i++){
                        JSONObject playerJson=playersArray.getJSONObject(i);
                        if(playerJson.getLong(llCommonKeys.keyUserId)==member.getIdLong()){
                            playersArray.put(i,player.jsonPlayer);
                            logger.info(fName+"updated player");
                            return  true;
                        }
                    }
                    logger.info(fName+"found no player to update");
                    return  false;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean updatePlayer(int index,Player player){
                String fName="[updatePlayer]";
                try {
                    logger.info(fName+"index="+index);
                    if(index<0){
                        return false;
                    }
                    if(index>=playersArray.length()){
                        return false;
                    }
                    playersArray.put(index,player.jsonPlayer);
                    logger.info(fName+"updated player");
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean deletePlayer(int index){
                String fName="[deletePlayer]";
                try {
                    logger.info(fName+"index="+index);
                    if(index<0){
                        return false;
                    }
                    if(index>=playersArray.length()){
                        return false;
                    }
                    playersArray.remove(index);
                    logger.info(fName+"removed player");
                    return  true;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean deletePlayer(Player player){
                String fName="[deletePlayer]";
                try {
                    long playerId=player.getId();
                    logger.info(fName+"playerId="+playerId);
                    Member member=lsMemberHelper.lsGetMemberById(gGuild,playerId);
                    for(int i=0;i<playersArray.length();i++){
                        JSONObject playerJson=playersArray.getJSONObject(i);
                        if(playerJson.getLong(llCommonKeys.keyUserId)==member.getIdLong()){
                            playersArray.remove(i);
                            logger.info(fName+"removed player");
                            return  true;
                        }
                    }
                    logger.info(fName+"found no player to remove");
                    return  false;
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
        }
        String titleRoulette="PiShock Roulette";
        private void menuGame_Roulette(){
            String fName="[menuGame_Roulette]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    getProfile(gMember);
                }
                getShockers();getJSON();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);String desc="",desc2="";
                StringBuilder desc3= new StringBuilder();isMenuLevel=true;
                embed.setTitle(titleRoulette);
                if(!getGameObject()){
                    logger.warn(fName+"no game entry");
                    if(!createGameObject()){
                        logger.warn(fName+"failed to create game object");
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors_Red.llColorRed_Barn);
                        return;
                    }
                }
                Roulette roulette=new Roulette(GAME);
                Player player=roulette.getPlayer(gMember);
                String commandQ2=llPrefixStr+gCommand+" "+keyGame_Roulette+" ";
                int sizePlayers=roulette.playersArray.length();
                List<Player>players=roulette.getPlayers();
                for(int i=0;i<players.size();i++){
                    Player playeri=players.get(i);
                    desc2+="\n"+lsMemberHelper.lsGetMemberMention(gGuild,playeri.getId())+" lost: "+playeri.getLost();
                }
                if(sizePlayers<3){
                    desc2+="\nRequire 3 players!";
                }
                embed.addField("Players [3-25]",desc2,false);
                if(player==null)desc=""+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" join";
                if(player!=null){
                    desc=""+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" leave";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" draw";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" clear";
                    desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" reset";
                }
                embed.addField("Commands",desc,false);
                if(lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)||lsGlobalHelper.slMemberIsBotOwner(gMember)){
                    desc="`"+commandQ2+"clear`";
                    desc+="\n`"+commandQ2+"reset`";
                    desc+="\n`"+commandQ2+"status`";
                    embed.addField("Commands+",desc,false);
                }
                Message message=llSendMessageResponse(gUser,embed);
                if(player==null) lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
                if(player!=null){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
                lsMessageHelper.lsMessageAddReactions(message,lsUnicodeEmotes.unicode_WhiteCheckMark);
                gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                isMenuLevel=true;
                                llMessageDelete(message);
                                if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                    menuGame();

                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                  gameRoulette_join();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                    gameRoulette_leave();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                    gameRoulette_draw();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))){
                                    gameRoulette_clear();
                                }
                                else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))){
                                    gameRoulette_reset();
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            //llSendQuickEmbedMessage(gUser,sRTitle, "Timeout", llColorRed);
                            llMessageDelete(message);
                        });

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }
        private void gameRoulette_join(){
            String fName="[gameRoulette_join]";
            logger.info(fName);
            try{
                if(gNewUserProfile.gUserProfile==null||!gNewUserProfile.gUserProfile.isProfile()){
                    getProfile(gMember);
                }
                getShockers();getJSON();
                if(!hasGameLoseShocker
                        ||!gNewUserProfile.cPISHOCK.isEnabled()
                        ||!cSHOCKERGAMELOSE.hasCode()
                ){
                    logger.warn(fName+"main player has no shocker or disabled");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"Invalid player "+gMember.getAsMention()+"!", llColors_Red.llColorRed_Barn);
                    return;
                }
                if(!getGameObject()){
                    if(!createGameObject()){
                        logger.warn(fName+"failed to create game object");
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette,"Failed to create game entry!");
                        return;
                    }
                }
                Roulette roulette=new Roulette(GAME);
                Player player=roulette.getPlayer(gMember);
                if(player==null){
                    roulette.addPlayer(gMember,cSHOCKERGAMELOSE.getJSON(),cSHOCKERGAMEWIN.getJSON());
                    logger.info(fName+"player joined");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,gMember.getAsMention()+" joined.", llColors.llColorGreen1);
                }else{
                    roulette.updatePlayer(player,cSHOCKERGAMELOSE.getJSON(),cSHOCKERGAMEWIN.getJSON());
                    logger.info(fName+"player exist> update shockers and warn they already joined");
                    lsMessageHelper.lsSendQuickEmbedMessage(gUser,titleRoulette,"You already joined!", llColors_Red.llColorRed_Barn);
                }
                GAME.put(keyGame_Roulette,roulette.getJSON());
                if(!putGameObject()){
                    logger.warn(fName+"failed to save game object");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette,"Failed to save game entry!");
                    return;
                }
                if(isMenuLevel){
                    menuGame_Roulette();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette, e.toString());
            }
        }
        private void gameRoulette_leave(){
            String fName="[gameRoulette_leave]";
            logger.info(fName);
            try{
                if(!getGameObject()){
                    logger.warn(fName+"no game entry");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors_Red.llColorRed_Barn);
                    return;
                }
                Roulette roulette=new Roulette(GAME);
                Player player=roulette.getPlayer(gMember);
                if(player==null){
                    logger.info(fName+"no player");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors.llColorGreen1);
                }else{
                    roulette.deletePlayer(player);
                    logger.info(fName+"player exist> delete");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,gMember.getAsMention()+" left the game.", llColors_Red.llColorRed_Barn);
                }
                GAME.put(keyGame_Roulette,roulette.getJSON());
                if(!putGameObject()){
                    logger.warn(fName+"failed to save game object");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette,"Failed to save game entry!");
                    return;
                }
                if(isMenuLevel){
                    menuGame_Roulette();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette, e.toString());
            }
        }
        private void gameRoulette_status(){
            String fName="[gameRoulette_status]";
            logger.info(fName);
            try{
                if(!getGameObject()){
                    logger.warn(fName+"no game entry");
                    if(!createGameObject()){
                        logger.warn(fName+"failed to create game object");
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors_Red.llColorRed_Barn);
                        return;
                    }
                }
                Roulette roulette=new Roulette(GAME);
                int sizePlayers=roulette.playersArray.length();
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(titleRoulette);embedBuilder.setColor(llColorGreen1);
                String desc="",desc2="";
                List<Player>players=roulette.getPlayers();
                for(int i=0;i<players.size();i++){
                    Player player=players.get(i);
                    desc2+="\n"+lsMemberHelper.lsGetMemberMention(gGuild,player.getId())+" lost: "+player.getLost();
                }
                if(sizePlayers<3){
                    desc2+="\nRequire 3 players!";
                }
                embedBuilder.addField("Players [3-25]",desc2,false);
                String commandQ2=llPrefixStr+gCommand+" "+keyGame_Roulette+" ";
                /*desc+="\n`"+commandQ2+"join`";
                desc+="\n`"+commandQ2+"leave`";
                desc+="\n`"+commandQ2+"draw`";
                desc+="\n`"+commandQ2+"clear`";
                desc+="\n`"+commandQ2+"reset`";
                desc+="\n`"+commandQ2+"status`";
                embedBuilder.addField("Commands",desc,false);*/
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette, e.toString());
            }
        }
        private void gameRoulette_reset(){
            String fName="[gameRoulette_reset]";
            logger.info(fName);
            try{
                if(!getGameObject()){
                    logger.warn(fName+"no game entry");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors_Red.llColorRed_Barn);
                    return;
                }
                Roulette roulette=new Roulette(GAME);
                roulette.reset();
                GAME.put(keyGame_Roulette,roulette.getJSON());
                if(!putGameObject()){
                    logger.warn(fName+"failed to save game object");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette,"Failed to save game entry!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"Reseted.", llColors.llColorBlue1);
                if(isMenuLevel){
                    menuGame_Roulette();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette, e.toString());
            }
        }
        private void gameRoulette_clear(){
            String fName="[gameRoulette_clear]";
            logger.info(fName);
            try{
                if(!getGameObject()){
                    logger.warn(fName+"no game entry");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors_Red.llColorRed_Barn);
                    return;
                }
                Roulette roulette=new Roulette(GAME);
                roulette.reset();
                GAME.put(keyGame_Roulette,roulette.getJSON());
                if(!putGameObject()){
                    logger.warn(fName+"failed to save game object");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette,"Failed to save game entry!");
                    return;
                }
                lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"Score cleared.", llColors.llColorBlue1);
                if(isMenuLevel){
                    menuGame_Roulette();
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette, e.toString());
            }
        }
        private void gameRoulette_draw(){
            String fName="[gameRoulette_draw]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                if(!getGameObject()){
                    logger.warn(fName+"no game entry");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"No game entry!", llColors_Red.llColorRed_Barn);
                    return;
                }
                Roulette roulette=new Roulette(GAME);
                int size=roulette.getSize();
                if(size<3){
                    logger.warn(fName+"too few players");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"Require minimum 3 players", llColors_Red.llColorRed_Barn);
                    return;
                }
                if(size>25){
                    logger.warn(fName+"too much players");
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,titleRoulette,"Can't be above 25 players.", llColors_Red.llColorRed_Barn);
                    return;
                }
                int random=lsUsefullFunctions.getRandom(size);
                logger.info(fName+"random="+random);
                Player player=roulette.getPlayer(random);
                if(player.lost()){
                    logger.info(fName+"player shocked with id="+player.getId());
                    embedBuilder.setTitle(titleRoulette);embedBuilder.setColor(llColorYellow_Canary);
                    embedBuilder.setDescription("The roulete spinned and landed on "+lsMemberHelper.lsGetMemberMention(gGuild,player.getId())+". Hope the shock was not to bad.");
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                }else{
                    logger.warn(fName+"failed to shock player with id="+player.getId());
                    postHTTPErrorMessages(gTextChannel,player.getMember(),gGuild.getSelfMember());
                }
                roulette.updatePlayer(random,player);
                GAME.put(keyGame_Roulette,roulette.getJSON());
                if(!putGameObject()){
                    logger.warn(fName+"failed to save game object");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette,"Failed to save game entry!");
                    return;
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,titleRoulette, e.toString());
            }
        }


        private void slashSelf(String action,String duration,String itensity,String code){
            String fName="[slashSelf]";
            logger.info(fName);
            try{
                logger.info(fName+"action="+action+strDuration45+duration+strItensity45+itensity+", code="+code);
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strCantDueNotenabled), llColorRed);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasPetGotAccess2Restrain()){
                    logger.info(fName + ".can't use do to access owner");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Pet), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                    logger.info(fName + ".can't use do to access public");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantSet2Public), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                    logger.info(fName + ".can't while the jacket is on and hands strapped");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantJacket1), llColorRed);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantJacket2), llColorRed);
                    return;
                }else
                if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                    logger.info(fName + ".can't while the mittens are on");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strCantMitts1), llColorRed);
                    llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(iPishock.strCantMitts2), llColorRed);
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                getShockers(); int codeIndex=-1;
                logger.info(fName+"readFile="+ getConfigValues());
                jsonObject.put(fieldUsername,gPiUsername);
                jsonObject.put(fieldApikey,gPiApiKey);
                jsonObject=getProfileAuthofication(jsonObject);
                boolean usedShocked=false;
                if(code==null||code.isBlank()){
                    usedShocked=true;
                }else{
                    codeIndex=getCode2Index(code);
                    if(codeIndex<0){
                        jsonObject.put(fieldCode,code);
                    }else{
                        usedShocked=true;
                    }
                }
                if(usedShocked){
                    logger.info(fName + ".codeIndex="+codeIndex);
                    if(codeIndex>=0){
                        selectDefaultShoker(codeIndex);
                    }else{
                        getDefaultShoker();
                    }
                    int selected=SHOCKER_INDEX;
                    logger.info(fName + ".selected="+selected);
                    if(selected<0){
                        logger.info(fName + ".no shocker selected");
                        embed.setDescription("None selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }else if(selected>=gNewUserProfile.cPISHOCK.getShockersSize()){
                        logger.info(fName + ".Invalid shocker selected");
                        embed.setDescription("Invalid shocker selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }
                    else {
                        logger.info(fName + ".shocker selected");
                        logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                        logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    }
                    jsonObject.put(fieldCode,cSHOCKER.getCode());
                }
                logger.info(fName+"code="+jsonObject.getString(fieldCode));
                int iDuration=0,iItensity=0;
                if(duration!=null&&!duration.isBlank()){
                    iDuration=Integer.valueOf(duration);
                }
                logger.info(fName+"iDuration="+iDuration);
                if(itensity!=null&&!itensity.isBlank()){
                    iItensity=Integer.valueOf(itensity);
                }
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                logger.info(fName+"url="+gUrl);
                jsonObject.put(fieldName,gUser.getName());
                switch (action){
                    case strBeep:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        jsonObject.put(fieldOp,2);
                        break;
                    case strVibrate:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,1);
                        break;
                    case strShock:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,0);
                        break;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                if(gDebugSend){
                    logger.info(fName + ".debug");
                    embed.setTitle(sRTitle+"(debug)");
                    embed.setDescription(jsonObject.toString());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                HttpResponse<String> jsonResponse =a.post(gUrl)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                logger.info(fName+".body ="+ jsonResponse.getBody());
                if(jsonResponse.getStatus()!=200){
                    desc.append("\n").append(jsonResponse.getBody());
                }
                else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                    desc.append("\n").append(jsonResponse.getBody());
                }else{
                    //success
                    //llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(strDoneBeep2,iDuration), llColorYellow_Cream);
                    switch (action){
                        case strBeep:
                            slashResponse(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep2,iDuration),16448204,false);
                            break;
                        case strVibrate:
                            slashResponse(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate2,iDuration,iItensity), 16448204,false);
                            break;
                        case strShock:
                            slashResponse(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock2,iDuration,iItensity), 16448204,false);
                            break;
                    }
                    loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setDescription(desc.toString());embed.setColor(llColorRed_Barn);
                    //lsMessageHelper.lsSendMessage(gUser,embed);
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                    slashResponse(desc.toString(),7866882,true);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void slashTargeted(Member member,String action,String duration,String itensity,String code, boolean isBypass){
            String fName="[slashTargeted]";
            logger.info(fName);
            try{
                logger.info(fName+"member="+member.getIdLong()+", action="+action+strDuration45+duration+strItensity45+itensity+", code="+code);
                if(!getProfile(member)){logger.error(fName + ".can't get profile"); return;}
                if(!gNewUserProfile.cPISHOCK.isEnabled()){
                    logger.info(fName+"not enabled>return");
                    llSendQuickEmbedMessage(gUser, sRTitle, stringReplacer(strCantDueNotenabled), llColorRed);
                    return;
                }
                if(!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                    logger.info(fName + ".can't use do to white&black list");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock. strCantUseDueList), llColorRed);
                    return;
                }
                if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                    logger.info(fName + ".can't use do to access protected");
                    llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(iPishock.strTargetCantDueAccess), llColorRed);
                    return;
                }
                JSONObject jsonObject=new JSONObject();
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1);
                StringBuilder desc= new StringBuilder();
                getShockers(); int codeIndex=-1;
                logger.info(fName+"readFile="+ getConfigValues());
                jsonObject.put(fieldUsername,gPiUsername);
                jsonObject.put(fieldApikey,gPiApiKey);
                jsonObject=getProfileAuthofication(jsonObject);
                boolean usedShocked=false;
                if(code==null||code.isBlank()){
                    usedShocked=true;
                }else{
                    codeIndex=getCode2Index(code);
                    if(codeIndex<0){
                        jsonObject.put(fieldCode,code);
                    }else{
                        usedShocked=true;
                    }
                }
                if(usedShocked){
                    logger.info(fName + ".codeIndex="+codeIndex);
                    if(codeIndex>=0){
                        selectDefaultShoker(codeIndex);
                    }else{
                        getDefaultShoker();
                    }
                    int selected=SHOCKER_INDEX;
                    logger.info(fName + ".selected="+selected);
                    if(selected<0){
                        logger.info(fName + ".no shocker selected");
                        embed.setDescription("None selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }else if(selected>=gNewUserProfile.cPISHOCK.getShockersSize()){
                        logger.info(fName + ".Invalid shocker selected");
                        embed.setDescription("Invalid shocker selected!");embed.setColor(llColorRed_Barn);
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        return;
                    }
                    else {
                        logger.info(fName + ".shocker selected");
                        logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                        logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                    }
                    jsonObject.put(fieldCode,cSHOCKER.getCode());
                }
                logger.info(fName+"code="+jsonObject.getString(fieldCode));
                int iDuration=0,iItensity=0;
                if(duration!=null&&!duration.isBlank()){
                    iDuration=Integer.valueOf(duration);
                }
                logger.info(fName+"iDuration="+iDuration);
                if(itensity!=null&&!itensity.isBlank()){
                    iItensity=Integer.valueOf(itensity);
                }
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                logger.info(fName+"url="+gUrl);
                jsonObject.put(fieldName,gUser.getName());
                switch (action){
                    case strBeep:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        jsonObject.put(fieldOp,2);
                        break;
                    case strVibrate:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,1);
                        break;
                    case strShock:
                        if(iDuration<=0){
                            iDuration=defaultDuration;
                        }else if(iDuration>maxDuration){
                            iDuration=maxDuration;
                        }
                        jsonObject.put(fieldDuration,iDuration);
                        logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                        if(iItensity<=0){
                            iItensity=defaultItensity;
                        }else if(iItensity>maxItensity){
                            iItensity=maxItensity;
                        }
                        jsonObject.put(fieldIntensity,iItensity);
                        logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                        jsonObject.put(fieldOp,0);
                        break;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                if(!isBypass&&gNewUserProfile.isAccessAsk()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)&&!gIsOverride){
                    logger.info(fName + ".ask mode");
                    messageUserInfoAsk=llSendQuickEmbedMessageResponse(gUser,sRTitle,stringReplacer("!TARGET is in ask modde, please wait till they confirm your command.."), llColorPurple2);
                    Message message=null;
                    switch (action){
                        case strBeep:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a beep command to your shocker for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                        case strVibrate:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a vibrate command to your shocker with "+iItensity+" itensity for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                        case strShock:
                            message=llSendQuickEmbedMessageResponse(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("!USER is attempting to send a shock command to your shocker with "+iItensity+" itensity for "+iDuration+" seconds. Do you accept it?"), llColorPurple2);
                            break;
                    }
                    Message finalMessage = message;
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple));
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple));
                    if(gNewUserProfile.isOwnerAskedAsWell()){
                        messageOwnerUser=gNewUserProfile.cAUTH.getOwnerAsUser(gGuild);
                        messageOwner=llSendQuickEmbedMessageResponse(messageOwnerUser,sRTitle,stringReplacer("!USER wants to send a Pishock command. Do you accept it?"), llColorPurple2);
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple));
                        lsMessageHelper.lsMessageAddReactions(messageOwner,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple));
                    }
                    gWaiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (
                                    (e.getMessageId().equalsIgnoreCase(finalMessage.getId())&&e.getUserId().equalsIgnoreCase(member.getId()))
                                            ||
                                            (messageOwner!=null&&messageOwnerUser!=null&&e.getMessageId().equalsIgnoreCase(messageOwner.getId())&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId()))
                            ),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                                        logger.info(fName + ".approved");
                                        slashTargeted(member,action,duration,itensity,code,true);
                                        if(messageOwnerUser!=null&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId())){
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET's owner approved your command."),llColorGreen1);
                                            llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("You approved !USER's command for !TARGET."),llColorGreen1);
                                        }else{
                                            if(messageOwner!=null&&messageOwnerUser!=null)llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("!TARGET approved !USER's command."),llColorGreen1);
                                        }

                                    }
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))){
                                        logger.info(fName + ".reject");
                                        if(messageOwnerUser!=null&&e.getUserId().equalsIgnoreCase(messageOwnerUser.getId())){
                                            logger.info(fName + ".reject");
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET's owner rejected your command."), llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("Your owner rejected !USER's command."),llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("You rejected !USER's command for !TARGET."),llColorRed_Cinnabar);

                                        }else{
                                            llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer("!TARGET rejected your command."), llColorRed_Cinnabar);
                                            llSendQuickEmbedMessage(gNewUserProfile.gUserProfile.getUser(),sRTitle,stringReplacer("You rejected !USER's command."),llColorRed_Cinnabar);
                                            if(messageOwner !=null&&messageOwnerUser!=null)llSendQuickEmbedMessage(messageOwnerUser,sRTitle,stringReplacer("!TARGET rejected !USER's command."),llColorRed_Cinnabar);
                                        }
                                    }
                                    llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                    if(messageOwner !=null)llMessageDelete(messageOwner);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sRTitle,e3.toString());
                                    llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                    if(messageOwner !=null)llMessageDelete(messageOwner);
                                }
                            },intDefaultWaitingMinute, TimeUnit.MINUTES, () -> {
                                logger.info(fName + ".timeout");
                                llSendQuickEmbedMessage(gUser,sRTitle,stringReplacer(strWaitingPeriodImeout), llColorRed_Cinnabar);
                                llMessageDelete(finalMessage);llMessageDelete(messageUserInfoAsk);
                                if(messageOwner !=null)llMessageDelete(messageOwner);
                            });
                    return;
                }
                if(gDebugSend){
                    logger.info(fName + ".debug");
                    embed.setTitle(sRTitle+"(debug)");
                    embed.setDescription(jsonObject.toString());
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    return;
                }
                HttpResponse<String> jsonResponse =a.post(gUrl)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asString();
                logger.info(fName+".status ="+jsonResponse.getStatus());
                logger.info(fName+".body ="+ jsonResponse.getBody());
                if(jsonResponse.getStatus()!=200){
                    desc.append("\n").append(jsonResponse.getBody());
                }
                else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                    desc.append("\n").append(jsonResponse.getBody());
                }else{
                    //success
                    //llSendQuickEmbedMessage(gTextChannel,sRTitle,stringReplacer(strDoneBeep2,iDuration), llColorYellow_Cream);
                    switch (action){
                        case strBeep:
                            slashResponse(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneBeep3,iDuration), 16448204,false);
                            break;
                        case strVibrate:
                            slashResponse(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneVibrate3,iDuration,iItensity), 16448204,false);
                            break;
                        case strShock:
                            slashResponse(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strTargetDoneShock3,iDuration,iItensity), 16448204,false);
                            break;
                    }
                    loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                }
                if(desc.length()>0){
                    desc.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                    if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())desc.append("\nBy:"+gMember.getAsMention());
                    embed.setDescription(desc.toString());embed.setColor(llColorRed_Barn);
                    //lsMessageHelper.lsSendMessage(gUser,embed);
                    loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+desc.toString());
                    slashResponse(desc.toString(),7866882,true);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private void slashResponse(String message, int color,boolean flag){
            String fName="[slashResponse]";
            logger.info(fName);
            try{
                logger.info(fName+"message="+message+", color="+color+", flag="+flag);
                JSONObject response=new JSONObject();
                lcMyEmbedBuilder embedBuilder=new lcMyEmbedBuilder();
                embedBuilder.setDescription(message).setColor(color);
                gInteractionCreate.respondMessage(embedBuilder,flag);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private int getCode2Index(String text){
            String fName="[getCode2Index]";
            try{
                logger.info(fName+"text="+text);
                text=text.replaceAll(" ","");
                int index=Integer.parseInt(text);
                logger.info(fName+"index="+index);
                if(index<0||index>9){
                    logger.info(fName+"invalid index");
                    return  -1;
                }
                return  index;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return  -1;
            }
        }
        InteractionHook gInteractionHook=null;
        public void rSlashNT() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
                User reqUser=null;
                boolean actionProvided=false,durationProvided=false,itensityProvided=false,shareCodeProvided=false;
                boolean subdirProvided=false;
                String actionValue="",shareCodeValue="";
                long durationValue=0,itensityValue=0;
                String subcommand=gSlashCommandEvent.getSubcommandName();
                if(subcommand==null)subcommand="";
                logger.info(".subcommand="+subcommand);
                slashReplyPleaseWait();
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case  llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;
                        case llCommonKeys.SlashCommandReceive.user:
                        case "member":
                            if(option.getType()== OptionType.USER){
                                reqUser=option.getAsUser();
                            }
                            break;
                        case "action":
                        case "op":
                            if(option.getType()== OptionType.STRING){
                                actionValue=option.getAsString();
                                actionProvided=true;
                            }
                            break;
                        case "duration":
                            if(option.getType()== OptionType.INTEGER){
                                durationValue=option.getAsLong();
                                durationProvided=true;
                            }
                            break;
                        case "intensity":
                            if(option.getType()== OptionType.INTEGER){
                                itensityValue=option.getAsLong();
                                itensityProvided=true;
                            }
                            break;
                        case "code":
                            if(option.getType()== OptionType.STRING){
                               shareCodeValue=option.getAsString();
                               shareCodeProvided=true;
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
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setColor(llColorRed_Barn).setTitle(sRTitle);
                if(subdirProvided||subcommand.equalsIgnoreCase("menu")){
                    embedBuilder.setDescription(iRdStr.strOpeningMenu).setColor(llColorBlue1);
                    sendOrUpdatePrivateEmbed(embedBuilder);
                    menuPiShock(gTarget);
                    return;
                }
                boolean valid=false,denied=false;
                if(!subcommand.equalsIgnoreCase("send")){
                    embedBuilder.setDescription("Invalid command!");
                    sendOrUpdatePrivateEmbed(embedBuilder);
                    return;
                }
                if(gTarget!=null){
                    if(!gNewUserProfile.cPISHOCK.isEnabled()){
                        logger.info(fName+"not enabled>return");
                        embedBuilder.setDescription(stringReplacer(strCantDueNotenabled));
                        denied=true;
                    }
                    if(!gNewUserProfile.hasPetGotAccess2Restrain()){
                        logger.info(fName + ".can't use do to access owner");
                        embedBuilder.setDescription(stringReplacer(iPishock.strCantSet2Pet));
                        denied=true;
                    }else
                    if(!gIsOverride&&gNewUserProfile.cAUTH.getAccessAsString().equals(ACCESSLEVELS.Public.getName())){
                        logger.info(fName + ".can't use do to access public");
                        embedBuilder.setDescription(stringReplacer(iPishock.strCantSet2Public));
                        denied=true;
                    }else
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nOn)&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nStraitjacket).getBoolean(nStrapArms)){
                        logger.info(fName + ".can't while the jacket is on and hands strapped");
                        embedBuilder.setDescription(stringReplacer(iPishock.strCantJacket1));
                        denied=true;
                    }else
                    if(!gIsOverride&&gNewUserProfile.gUserProfile.jsonObject.getJSONObject(nMitts).getBoolean(nOn)){
                        logger.info(fName + ".can't while the mittens are on");
                        embedBuilder.setDescription(stringReplacer(iPishock.strCantMitts1));
                        denied=true;
                    }
                    if(denied){
                        sendOrUpdatePrivateEmbed(embedBuilder);
                    }
                    else{
                        JSONObject jsonObject=new JSONObject();
                        embedBuilder.setColor(llColorBlue1);
                        embedBuilder.setDescription("Prpcessing...");
                        gInteractionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                        StringBuilder descBuilder= new StringBuilder();
                        getShockers(); int codeIndex=-1;
                        logger.info(fName+"readFile="+ getConfigValues());
                        jsonObject.put(fieldUsername,gPiUsername);
                        jsonObject.put(fieldApikey,gPiApiKey);
                        jsonObject=getProfileAuthofication(jsonObject);
                        boolean usedShocked=false;
                        if(!shareCodeProvided||shareCodeValue==null||shareCodeValue.isBlank()){
                            usedShocked=true;
                        }else{
                            codeIndex=getCode2Index(shareCodeValue);
                            if(codeIndex<0){
                                jsonObject.put(fieldCode,shareCodeValue);
                            }else{
                                usedShocked=true;
                            }
                        }
                        if(usedShocked){
                            logger.info(fName + ".codeIndex="+codeIndex);
                            if(codeIndex>=0){
                                selectDefaultShoker(codeIndex);
                            }else{
                                getDefaultShoker();
                            }
                            int selected=SHOCKER_INDEX;
                            logger.info(fName + ".selected="+selected);
                            if(selected<0){
                                logger.info(fName + ".no shocker selected");
                                embedBuilder.setDescription("None selected!");embedBuilder.setColor(llColorRed_Barn);
                                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                                return;
                            }else if(selected>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                logger.info(fName + ".Invalid shocker selected");
                                embedBuilder.setDescription("Invalid shocker selected!");embedBuilder.setColor(llColorRed_Barn);
                                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                                return;
                            }
                            else {
                                logger.info(fName + ".shocker selected");
                                logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                                logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            }
                            jsonObject.put(fieldCode,cSHOCKER.getCode());
                        }
                        logger.info(fName+"code="+jsonObject.getString(fieldCode));
                        long iDuration=0,iItensity=0;
                        if(durationProvided){
                            iDuration=durationValue;
                        }
                        logger.info(fName+"iDuration="+iDuration);
                        if(itensityProvided){
                            iItensity=itensityValue;
                        }
                        Unirest a= new Unirest();
                        a.config().verifySsl(false);
                        logger.info(fName+"url="+gUrl);
                        jsonObject.put(fieldName,gUser.getName());
                        switch (actionValue){
                            case strBeep:
                                if(iDuration<=0){
                                    iDuration=defaultDuration;
                                }else if(iDuration>maxDuration){
                                    iDuration=maxDuration;
                                }
                                jsonObject.put(fieldDuration,iDuration);
                                logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                                jsonObject.put(fieldOp,2);
                                break;
                            case strVibrate:
                                if(iDuration<=0){
                                    iDuration=defaultDuration;
                                }else if(iDuration>maxDuration){
                                    iDuration=maxDuration;
                                }
                                jsonObject.put(fieldDuration,iDuration);
                                logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                                if(iItensity<=0){
                                    iItensity=defaultItensity;
                                }else if(iItensity>maxItensity){
                                    iItensity=maxItensity;
                                }
                                jsonObject.put(fieldIntensity,iItensity);
                                logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                                jsonObject.put(fieldOp,1);
                                break;
                            case strShock:
                                if(iDuration<=0){
                                    iDuration=defaultDuration;
                                }else if(iDuration>maxDuration){
                                    iDuration=maxDuration;
                                }
                                jsonObject.put(fieldDuration,iDuration);
                                logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                                if(iItensity<=0){
                                    iItensity=defaultItensity;
                                }else if(iItensity>maxItensity){
                                    iItensity=maxItensity;
                                }
                                jsonObject.put(fieldIntensity,iItensity);
                                logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                                jsonObject.put(fieldOp,0);
                                break;
                        }
                        logger.info(fName+"jsonObject="+jsonObject.toString());
                        if(gDebugSend){
                            logger.info(fName + ".debug");
                            embedBuilder.setTitle(sRTitle+"(debug)");
                            embedBuilder.setDescription(jsonObject.toString());
                            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                            return;
                        }
                        HttpResponse<String> jsonResponse =a.post(gUrl)
                                .header("accept", "application/json")
                                .header("Content-Type", "application/json")
                                .body(jsonObject.toString())
                                .asString();
                        logger.info(fName+".status ="+jsonResponse.getStatus());
                        logger.info(fName+".body ="+ jsonResponse.getBody());
                        if(jsonResponse.getStatus()!=200){
                            descBuilder.append("\n").append(jsonResponse.getBody());
                        }
                        else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                            descBuilder.append("\n").append(jsonResponse.getBody());
                        }else{
                            loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                            switch (actionValue){
                                case strBeep:
                                    embedBuilder.setDescription(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep2,(int)iDuration));
                                    embedBuilder.setColor(16448204);
                                    break;
                                case strVibrate:
                                    embedBuilder.setDescription(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate2,(int)iDuration,(int)iItensity));
                                    embedBuilder.setColor(16448204);
                                    break;
                                case strShock:
                                    embedBuilder.setDescription(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock2,(int)iDuration,(int)iItensity));
                                    embedBuilder.setColor(16448204);
                                    break;
                            }
                            sendOrUpdatePrivateEmbed(sRTitle,"Success",llColorGreen_Jungle);
                            lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,embedBuilder);
                            return;
                        }
                        if(descBuilder.length()>0){
                            descBuilder.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                            if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())descBuilder.append("\nBy:"+gMember.getAsMention());
                            loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+descBuilder.toString());
                            embedBuilder.setDescription(descBuilder.toString());embedBuilder.setColor(llColorRed_Barn);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                        }
                    }
                }else{
                    if(!gNewUserProfile.cPISHOCK.isEnabled()){
                        logger.info(fName+"not enabled>return");
                        embedBuilder.setDescription(stringReplacer(strCantDueNotenabled));
                        denied=true;
                    }else
                    if(!gIsOverride&&!allowedToUse()&&!gNewUserProfile.cAUTH.hasUserOwnerAccess(gUser)&&!gNewUserProfile.cAUTH.hasUserSecOwnerAccess(gUser)){
                        logger.info(fName + ".can't use do to white&black list");
                        embedBuilder.setDescription(stringReplacer(iPishock. strCantUseDueList));
                        denied=true;
                    }else
                    if(!gIsOverride&&!gNewUserProfile.hasUserGotAccess2Restrain(gUser)){
                        logger.info(fName + ".can't use do to access protected");
                        embedBuilder.setDescription(stringReplacer(iPishock.strTargetCantDueAccess));
                        denied=true;
                    }
                    if(denied){
                        gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    }
                    else {
                        JSONObject jsonObject=new JSONObject();
                        embedBuilder.setColor(llColorBlue1);
                        embedBuilder.setDescription("Prpcessing...");
                        gInteractionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
                        StringBuilder descBuilder= new StringBuilder();
                        getShockers(); int codeIndex=-1;
                        logger.info(fName+"readFile="+ getConfigValues());
                        jsonObject.put(fieldUsername,gPiUsername);
                        jsonObject.put(fieldApikey,gPiApiKey);
                        jsonObject=getProfileAuthofication(jsonObject);
                        boolean usedShocked=false;
                        if(!shareCodeProvided||shareCodeValue==null||shareCodeValue.isBlank()){
                            usedShocked=true;
                        }else{
                            codeIndex=getCode2Index(shareCodeValue);
                            if(codeIndex<0){
                                jsonObject.put(fieldCode,shareCodeValue);
                            }else{
                                usedShocked=true;
                            }
                        }
                        if(usedShocked){
                            logger.info(fName + ".codeIndex="+codeIndex);
                            if(codeIndex>=0){
                                selectDefaultShoker(codeIndex);
                            }else{
                                getDefaultShoker();
                            }
                            int selected=SHOCKER_INDEX;
                            logger.info(fName + ".selected="+selected);
                            if(selected<0){
                                logger.info(fName + ".no shocker selected");
                                embedBuilder.setDescription("None selected!");embedBuilder.setColor(llColorRed_Barn);
                                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                                return;
                            }else if(selected>=gNewUserProfile.cPISHOCK.getShockersSize()){
                                logger.info(fName + ".Invalid shocker selected");
                                embedBuilder.setDescription("Invalid shocker selected!");embedBuilder.setColor(llColorRed_Barn);
                                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                                return;
                            }
                            else {
                                logger.info(fName + ".shocker selected");
                                logger.info(fName+"SHOCKER_INDEX="+SHOCKER_INDEX);
                                logger.info(fName+"SHOCKER="+cSHOCKER.getJSON().toString());
                            }
                            jsonObject.put(fieldCode,cSHOCKER.getCode());
                        }
                        logger.info(fName+"code="+jsonObject.getString(fieldCode));
                        long iDuration=0,iItensity=0;
                        if(durationProvided){
                            iDuration=durationValue;
                        }
                        logger.info(fName+"iDuration="+iDuration);
                        if(itensityProvided){
                            iItensity=itensityValue;
                        }
                        Unirest a= new Unirest();
                        a.config().verifySsl(false);
                        logger.info(fName+"url="+gUrl);
                        jsonObject.put(fieldName,gUser.getName());
                        switch (actionValue){
                            case strBeep:
                                if(iDuration<=0){
                                    iDuration=defaultDuration;
                                }else if(iDuration>maxDuration){
                                    iDuration=maxDuration;
                                }
                                jsonObject.put(fieldDuration,iDuration);
                                logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                                jsonObject.put(fieldOp,2);
                                break;
                            case strVibrate:
                                if(iDuration<=0){
                                    iDuration=defaultDuration;
                                }else if(iDuration>maxDuration){
                                    iDuration=maxDuration;
                                }
                                jsonObject.put(fieldDuration,iDuration);
                                logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                                if(iItensity<=0){
                                    iItensity=defaultItensity;
                                }else if(iItensity>maxItensity){
                                    iItensity=maxItensity;
                                }
                                jsonObject.put(fieldIntensity,iItensity);
                                logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                                jsonObject.put(fieldOp,1);
                                break;
                            case strShock:
                                if(iDuration<=0){
                                    iDuration=defaultDuration;
                                }else if(iDuration>maxDuration){
                                    iDuration=maxDuration;
                                }
                                jsonObject.put(fieldDuration,iDuration);
                                logger.info(fName+"duration="+jsonObject.getString(fieldDuration));
                                if(iItensity<=0){
                                    iItensity=defaultItensity;
                                }else if(iItensity>maxItensity){
                                    iItensity=maxItensity;
                                }
                                jsonObject.put(fieldIntensity,iItensity);
                                logger.info(fName+"itensity="+jsonObject.getString(fieldIntensity));
                                jsonObject.put(fieldOp,0);
                                break;
                        }
                        logger.info(fName+"jsonObject="+jsonObject.toString());
                        if(gDebugSend){
                            logger.info(fName + ".debug");
                            embedBuilder.setTitle(sRTitle+"(debug)");
                            embedBuilder.setDescription(jsonObject.toString());
                            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                            return;
                        }
                        HttpResponse<String> jsonResponse =a.post(gUrl)
                                .header("accept", "application/json")
                                .header("Content-Type", "application/json")
                                .body(jsonObject.toString())
                                .asString();
                        logger.info(fName+".status ="+jsonResponse.getStatus());
                        logger.info(fName+".body ="+ jsonResponse.getBody());
                        if(jsonResponse.getStatus()!=200){
                            descBuilder.append("\n").append(jsonResponse.getBody());
                        }
                        else if(!(jsonResponse.getBody().contains("Operation Succeeded")||jsonResponse.getBody().equalsIgnoreCase("Operation Succeeded"))){
                            descBuilder.append("\n").append(jsonResponse.getBody());
                        }else{
                            loggerSuccess.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString());
                            switch (actionValue){
                                case strBeep:
                                    embedBuilder.setDescription(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneBeep2,(int)iDuration));
                                    embedBuilder.setColor(16448204);
                                    break;
                                case strVibrate:
                                    embedBuilder.setDescription(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneVibrate2,(int)iDuration,(int)iItensity));
                                    embedBuilder.setColor(16448204);
                                    break;
                                case strShock:
                                    embedBuilder.setDescription(iPishock.sStringReplacer(gNewUserProfile.gUserProfile,gMember,strDoneShock2,(int)iDuration,(int)iItensity));
                                    embedBuilder.setColor(16448204);
                                    break;
                            }
                            sendOrUpdatePrivateEmbed(sRTitle,"Success",llColorGreen_Jungle);
                            lsMessageHelper.lsSendEmbed(gCurrentInteractionHook,embedBuilder);
                            return;
                        }
                        if(descBuilder.length()>0){
                            descBuilder.append("\nTarget:"+gNewUserProfile.getMember().getAsMention());
                            if(gMember.getIdLong()!=gNewUserProfile.getMember().getIdLong())descBuilder.append("\nBy:"+gMember.getAsMention());
                            loggerFail.info(logstrAuthor+"="+gMember.getId()+", "+logstrTarget+"="+gNewUserProfile.getMember().getId()+", "+logstrGuild+"="+gGuild.getId()+", "+logstrChannl+"="+gTextChannel.getId()+", "+logstrJson+"="+jsonObject.toString()+", "+logstrReason+"="+descBuilder.toString());
                            embedBuilder.setDescription(descBuilder.toString());embedBuilder.setColor(llColorRed_Barn);
                            sendOrUpdatePrivateEmbed(embedBuilder);
                        }
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sRTitle, e.toString());
            }
        }

        String gUrlMainSoloPath="resources/json/rd";
        String gPiUsername="",gPiApiKey="";
        lcText2Json text2Json=null;
        int SHOCKER_TYPE=0;
        //JSONObject SHOCKER=new JSONObject();
        int SHOCKER_MAIN_INDEX=-1,SHOCKER_INDEX=-1, SHOCKERS_COUNT=0;
        private void getDefaultShoker() {
            String fName="[getDefaultShoker]";
            logger.info(fName);
            try {
                try {
                    cSHOCKER=new entityShocker(); SHOCKER_MAIN_INDEX=-1;
                    int selected=gNewUserProfile.cPISHOCK.getMainShockerIndex();
                    if(selected<gNewUserProfile.cPISHOCK.getShockersSize()&&selected>=0){
                        SHOCKER_INDEX = selected;
                        cSHOCKER=gNewUserProfile.cPISHOCK.getMainShocker();
                    }else
                    if(selected==-1){
                        SHOCKER_INDEX=-1;
                    }else{
                        SHOCKER_INDEX=-2;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    SHOCKER_INDEX=-2;
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        entityShocker cSHOCKER=new entityShocker(),cSHOCKER_COLLAR=new entityShocker(),cSHOCKER_CHASTITY=new entityShocker(),cSHOCKERGAMELOSE=new entityShocker(),cSHOCKERGAMEWIN=new entityShocker();
        boolean hasCollarShocker, hasChastityShocker,hasGameWinShocker,hasGameLoseShocker;
        private void getShockers() {
            String fName="[getShockers]";
            logger.info(fName);
            try {
                logger.info(fName + ".gNewUserProfile.cPISHOCK="+gNewUserProfile.cPISHOCK.getJSON().toString());
                try {
                    SHOCKERS_COUNT=gNewUserProfile.cPISHOCK.getShockersSize();
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    SHOCKER_INDEX=-2;
                }
                try {
                    hasCollarShocker=false;
                    cSHOCKER_COLLAR=gNewUserProfile.cPISHOCK.getCollarShocker();
                    logger.info(fName + ".SHOCKER_COLLAR=" +cSHOCKER_COLLAR.getJSON().toString());
                    if(gNewUserProfile.cPISHOCK.getCollarShocker().hasCode()){
                        logger.info(fName + ".SHOCKER_COLLAR has code");
                        hasCollarShocker=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    hasChastityShocker=false;
                    cSHOCKER_CHASTITY=gNewUserProfile.cPISHOCK.getChastityShocker();
                    logger.info(fName + ".SHOCKER_CHASTITY=" +cSHOCKER_CHASTITY.getJSON().toString());
                    if(cSHOCKER_CHASTITY.hasCode()){
                        logger.info(fName + ".SHOCKER_CHASTITY has code");
                        hasChastityShocker=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    hasGameLoseShocker=false;
                    cSHOCKERGAMELOSE=gNewUserProfile.cPISHOCK.getGameLoseShocker();
                    logger.info(fName + ". SHOCKERGAMELOSE=" + cSHOCKERGAMELOSE.getJSON().toString());
                    if(cSHOCKERGAMELOSE.hasCode()){
                        logger.info(fName + ".SHOCKERGAMELOSE has code");
                        hasGameLoseShocker=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                try {
                    hasGameWinShocker=false;
                    cSHOCKERGAMEWIN=gNewUserProfile.cPISHOCK.getGameWinShocker();
                    logger.info(fName + ". SHOCKERGAMEWIN=" + cSHOCKERGAMEWIN.getJSON().toString());
                    if(cSHOCKERGAMEWIN.hasCode()){
                        logger.info(fName + ".SHOCKERGAMEWIN has code");
                        hasGameWinShocker=true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private boolean getConfigValues() {
            //changed from read file
            String fName="[getConfigValues]";
            logger.info(fName);
            try {
                gPiUsername=gGlobal.pishockConfig.getApiUserName();
                gPiApiKey=gGlobal.pishockConfig.getApiKey();
                logger.info(fName+"gPiUsername="+gPiUsername+", gPiApiKey="+gPiApiKey);
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private JSONObject getProfileAuthofication(JSONObject jsonObject) {
            String fName="[getProfileAuthofication]";
            logger.info(fName);
            try {
                 String username="",apikey="";
                 username=gNewUserProfile.cPISHOCK.getUsername();
                 apikey=gNewUserProfile.cPISHOCK.getApiKey();
                 logger.info(fName + ".username=" + username);
                 logger.info(fName + ".apikey=" + apikey);
                 if(!username.isBlank()&&!apikey.isBlank()){
                    jsonObject.put(fieldUsername,username);
                    jsonObject.put(fieldApikey,apikey);
                 }
                 return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return jsonObject;
            }
        }
        private Boolean getJSON(){
            String fName="[getJSON]";
            logger.info(fName);
            try {
                WHITELISTMMEMBERS=gNewUserProfile.cPISHOCK.geMembersWhiteList();
                BLACKLISTMEMBERS=gNewUserProfile.cPISHOCK.geMembersBlackList();
                return true;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }


}}

