package nsfw.chastity.emlalock;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.*;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.lcJSONandSqlUserProfile;
import models.lc.json.lcText2Json;
import models.lc.lcBasicFeatureControl;
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
import restraints.in.iRestraints;
import restraints.models.lcBDSMGuildProfiles;
import restraints.models.entity.entityRDUserProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class ChastityEmlalock extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper, iChastityEmlalock {
        Logger logger = Logger.getLogger(getClass());
        Logger loggerGetSuccess = Logger.getLogger(CrunchifyLog4jLevel.EmlalockUseSuccess_STR);
        Logger loggerUpdateSuccess = Logger.getLogger(CrunchifyLog4jLevel.EmlalockUpdateSuccess_STR);
        Logger loggerFail = Logger.getLogger(CrunchifyLog4jLevel.EmlalockUseFail_STR);
        
        lcGlobalHelper gGlobal;
        String gTitle="Emlalock",gCommand="emlalock";
        JSONObject rfEntries;
    public ChastityEmlalock(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Accessing information from Emlalock.";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
    }
    public ChastityEmlalock(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }
    public ChastityEmlalock(lcGlobalHelper global, CommandEvent ev, boolean isForward, String formawrd,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev,isForward,formawrd,target);
        new Thread(r).start();
    }
    public ChastityEmlalock(lcGlobalHelper global, String formawrd,Guild guild,TextChannel textChannel,Member author,Member target){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(formawrd,guild,textChannel,author,target);
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

protected class runLocal implements Runnable {
    CommandEvent gEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel gTextChannel;
    lcJSONandSqlUserProfile gUserProfile;
    Member gTarget;
    boolean gIsOverride=false;
    boolean gIsForward=false;String gRawForward="";String gArgs="";
    public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
    SlashCommandEvent gSlashCommandEvent;
    String[] gItems;
    public runLocal(CommandEvent ev) {
        String fName="build";logger.info(".run build");
        gEvent = ev;
        gUser = gEvent.getAuthor();gMember=gEvent.getMember();
        gGuild = gEvent.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gEvent.getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gArgs=gEvent.getArgs();
        gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
    }
    public runLocal(SlashCommandEvent ev) {
        String fName="runLoccal";
        logger.info(cName + ".run build");
        gSlashCommandEvent = ev;
        gUser = ev.getUser();
        logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
        if(ev.isFromGuild()){
            gMember=ev.getMember();
            gGuild = ev.getGuild();
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            gTextChannel =ev.getTextChannel();
            logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        }
    }
    public runLocal(CommandEvent ev,boolean isForward,String forward,Member target){
        String fName="[run]";
        logger.info(".run build");
        gEvent =ev;gIsForward=isForward;gRawForward=forward;
        gGuild = gEvent.getGuild();
        gUser = gEvent.getAuthor();gMember= gEvent.getMember();
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gEvent.getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        if(target!=null){
            gTarget=target;
            logger.info(fName + ". gTarget:" +  gTarget.getUser().getId() + "|" +  gTarget.getUser().getName());
        }
        gArgs=gEvent.getArgs();
        gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
    }
    public runLocal(String forward,Guild guild,TextChannel textChannel,Member author, Member target){
        String fName="[run]";
        logger.info(".run build");
        gRawForward=forward;gIsForward=true;
        gUser = author.getUser();gMember= author;
        gGuild = guild;
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = textChannel;
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        gTarget=target;
        if(gTarget!=null)logger.info(fName + ".gTarget:" + gTarget.getId() + "|" + gTarget.getUser().getName());
        gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
    }
    lcBDSMGuildProfiles gBDSMCommands;
    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");

        Boolean isInvalidCommand = true;
        try{
            gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"ChastityEmlalock",gGlobal);
            gBasicFeatureControl.initProfile();
            gSessionId=Config.getSessionID(gGlobal);
            if(gSlashCommandEvent!=null) {
                logger.info(cName + fName + "slash@");
                SlashNT();
            }else
            if(gIsForward&&gRawForward!=null&&!gRawForward.isBlank()){
                logger.info(cName + fName + "forward@");
                if(!isNSFW()){
                    return;
                }
                gItems = gRawForward.split("\\s+");
                if(gRawForward.contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                getProfile();
                getMainEntries();
                if(gItems[0].equalsIgnoreCase(commandPunish)){
                    doRdChastity(1);
                }else
                if(gItems[0].equalsIgnoreCase(commandReward)){
                    doRdChastity(2);
                }else
                if(gItems[0].equalsIgnoreCase(commandTimeout)){
                    doRdTimeout();
                }
            }else
            if(gEvent!=null){
                logger.info(cName + fName + "basic@");
                if(!isNSFW()){
                    blocked();return;
                }
                if(gArgs.isEmpty()){
                    logger.info(fName+".Args=0");
                /*help("main");
                gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();*/
                    menuMain();
                    isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    /*if(gArgs.contains(llOverride)&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                        gIsOverride =true;
                        gArgs=gArgs.replaceAll(llOverride,"");
                    }*/
                    if(gArgs.contains(llOverride)&&lsMemberIsBotOwner(gMember)){
                        gIsOverride =true;
                        gArgs=gArgs.replaceAll(llOverride,"");
                    }
                    gItems = gArgs.split("\\s+");
                    logger.info(fName + ".gItems.size=" + gItems.length);
                    logger.info(fName + ".gItems[0]=" + gItems[0]);
                    defaultVariables();
                    if(gItems[0].equalsIgnoreCase("help")){
                        help("main");
                        gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();isInvalidCommand=true;
                        isInvalidCommand=false;
                    }
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
                    if(isInvalidCommand&&(gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")){
                        logger.info(fName+".detect mention characters");
                        Member target;
                        List<Member>mentions= gEvent.getMessage().getMentionedMembers();
                        if(mentions.isEmpty()){
                            logger.warn(fName+".zero member mentions in message>check itemns[0]");
                            target=llGetMember(gGuild,gItems[0]);
                        }else{
                            logger.info(fName+".member mentions in message");
                            target=mentions.get(0);
                        }
                        readFile();
                        if(target==null){
                            logger.warn(fName+".zero member mentions");
                        }
                        else if(target.getId().equalsIgnoreCase(gEvent.getAuthor().getId())){
                            logger.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                            //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                        }
                        else if(gItems.length<2){
                            logger.warn(fName+".invalid args length");
                            gTarget=target;
                            menuMain();isInvalidCommand=false;
                        }else{
                            gTarget=target;
                            if(gItems[1].equalsIgnoreCase("help")){
                                help("main");isInvalidCommand=false;
                            }else
                            if(gItems[1].equalsIgnoreCase("punish")){
                                doRdChastity(1);isInvalidCommand=false;
                            }else
                            if(gItems[1].equalsIgnoreCase("reward")){
                                doRdChastity(2);isInvalidCommand=false;
                            }else
                            if(isInvalidCommand&&gItems[0].equalsIgnoreCase("menu")){
                                menuMain();isInvalidCommand=false;
                            }else
                            if(gItems[1].equalsIgnoreCase("api")&&gItems.length>2){
                                getProfile(gTarget);
                                getMainEntries();
                                long durationA=0,durationB=0;int countA=0,countB=0;
                                if(gItems.length>=4){
                                    durationA=StringShortTerms2Timeset4Duration(gItems[3]);
                                    countA=String2Int(gItems[3]);
                                }
                                if(gItems.length>=5){
                                    durationB=StringShortTerms2Timeset4Duration(gItems[4]);
                                    countB=String2Int(gItems[4]);
                                }
                                switch (gItems[2].toLowerCase()){
                                    case apiOptions_Add:
                                        doAPI_Add(durationA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddMaximum:
                                        doAPI_AddMaximum(durationA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddMaximumRandom:
                                        doAPI_AddMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddMinimum:
                                        doAPI_AddMinimum(durationA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddMinimumRandom:
                                        doAPI_AddMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddRandom:
                                        doAPI_AddRandom(durationA,durationB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddRequirement:
                                        doAPI_AddRequirement(countA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_AddRequirementRandom:
                                        doAPI_AddRequirementRandom(countA,countB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_Sub:
                                        doAPI_Sub(durationA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubMaximum:
                                        doAPI_SubMaximum(durationA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubMaximumRandom:
                                        doAPI_SubMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubMinimum:
                                        doAPI_SubMinimum(durationA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubMinimumRandom:
                                        doAPI_SubMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubRandom:
                                        doAPI_SubRandom(durationA,durationB);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubRequirement:
                                        doAPI_SubRequirement(countA);isInvalidCommand=false;
                                        break;
                                    case apiOptions_SubRequirementRandom:
                                        doAPI_SubRequirementRandom(countA,countB);isInvalidCommand=false;
                                        break;
                                }
                            }

                        }
                    }
                    if(isInvalidCommand){
                        gTarget=null;
                        readFile();
                        if(gItems[0].isBlank()&&gIsOverride){
                            menuMain();isInvalidCommand=false;
                        }else
                        if(gItems[0].equalsIgnoreCase("api")&&gItems.length>1){
                            getProfile(gMember);
                            getMainEntries();
                            long durationA=0,durationB=0;int countA=0,countB=0;
                            if(gItems.length>=4){
                                durationA=StringShortTerms2Timeset4Duration(gItems[3]);
                                countA=String2Int(gItems[3]);
                            }
                            if(gItems.length>=5){
                                durationB=StringShortTerms2Timeset4Duration(gItems[4]);
                                countB=String2Int(gItems[4]);
                            }
                            switch (gItems[1].toLowerCase()){
                                case apiOptions_Add:
                                    doAPI_Add(durationA);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddMaximum:
                                    doAPI_AddMaximum(durationA);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddMaximumRandom:
                                    doAPI_AddMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddMinimum:
                                    doAPI_AddMinimum(durationA);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddMinimumRandom:
                                    doAPI_AddMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddRandom:
                                    doAPI_AddRandom(durationA,durationB);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddRequirement:
                                    doAPI_AddRequirement(countA);isInvalidCommand=false;
                                    break;
                                case apiOptions_AddRequirementRandom:
                                    doAPI_AddRequirementRandom(countA,countB);isInvalidCommand=false;
                                    break;
                                case apiOptions_Sub:
                                    doAPI_Sub(durationA);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubMaximum:
                                    doAPI_SubMaximum(durationA);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubMaximumRandom:
                                    doAPI_SubMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubMinimum:
                                    doAPI_SubMinimum(durationA);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubMinimumRandom:
                                    doAPI_SubMinimumRandom(durationA,durationB);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubRandom:
                                    doAPI_SubRandom(durationA,durationB);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubRequirement:
                                    doAPI_SubRequirement(countA);isInvalidCommand=false;
                                    break;
                                case apiOptions_SubRequirementRandom:
                                    doAPI_SubRequirementRandom(countA,countB);isInvalidCommand=false;
                                    break;
                            }
                        }else
                        if(gItems[0].equalsIgnoreCase("set")&&gItems.length>1){
                            getProfile(gMember);
                            if(gItems[1].equalsIgnoreCase("userid")){
                                dmInputUserId();
                                isInvalidCommand=false;
                            }else
                            if(gItems[1].equalsIgnoreCase("apikey")){
                                dmInputApiKey();
                                isInvalidCommand=false;
                            }
                        }else
                        if(gItems[0].equalsIgnoreCase("punish")){
                            doRdChastity(1);isInvalidCommand=false;
                        }else
                        if(gItems[0].equalsIgnoreCase("reward")){
                            doRdChastity(2);isInvalidCommand=false;
                        }else
                        if(gItems[0].equalsIgnoreCase("menu")){
                            menuMain();isInvalidCommand=false;
                        }
                        if(isInvalidCommand&&gItems.length>1){
                            if(gItems[0].equalsIgnoreCase("profile")){
                                getUserProfile(gItems[1]);
                                isInvalidCommand=false;
                            }else
                            if(gItems[0].equalsIgnoreCase("session")){
                                getSessionProfile(gItems[1]);
                                isInvalidCommand=false;
                            }else
                            if(gItems[0].equalsIgnoreCase("verification")){
                                getVerification(gItems[1]);
                                isInvalidCommand=false;
                            }
                        }
                        if(isInvalidCommand){
                            getProfile(gMember);
                            if(!gUserProfile.isProfile()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"No profile for user!", llColorRed);
                                return;
                            }
                            if(WEARER.profile.isEmptyUserId()){
                                llSendQuickEmbedMessage(gTextChannel,gTitle,"No Emlalock user id recorded!", llColorRed);
                                return;
                            }
                            //"6k12qw7btcoveh5"
                            if(gItems[0].equalsIgnoreCase("profile")){
                                getUserProfile(WEARER.profile.getUserId());
                                //need2ProvideUserId();
                                isInvalidCommand=false;
                            }else
                            if(gItems[0].equalsIgnoreCase("session")){
                                getSessionProfile(WEARER.profile.getUserId());
                                //need2ProvideUserId();
                                isInvalidCommand=false;
                            }else
                            if(gItems[0].equalsIgnoreCase("verification")){
                                getVerification(WEARER.profile.getUserId());
                                //need2ProvideUserId();
                                isInvalidCommand=false;
                            }
                        }
                    }


                }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gEvent);*/
                if(isInvalidCommand){
                    llSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColorRed);
                }
            }


        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed); }
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
    private void need2ProvideUserId(){
        String fName = "[need2ProvideUserId]";
        llSendQuickEmbedMessage(gTextChannel,gTitle,"Need to provide emlalock user id.",llColorRed);
        logger.info(fName);
    }
    private Boolean isTargeted(String command){
        String fName = "[isTargeted]";
        logger.info(fName);
        try{
            logger.info(fName + ".command=" + command);
            if((command.contains("<@")&&command.contains(">"))||(command.contains("<@!")&&command.contains(">"))){
                String tmp=command.replace("<@!","").replace("<@","").replace(">","");
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

    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+gCommand+" ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);embed.setTitle(gTitle);
        embed.addField("What is Emlalock?","Is an online chastity keyholding web service.\nGo to their [site](https://www.emlalock.com), [discord](https://discord.gg/xGWZNtv) or [wiki](https://wiki.emlalock.com/doku.php?id=start).",false);
        embed.addField("Profile","Retrieve that user profile information.\nUse `"+quickSummonWithSpace+"profile [EmlalockUserId/@MentioUser]`",false);
        embed.addField("Session","Retrieve that user session information.\nUse `"+quickSummonWithSpace+"session [EmlalockUserId/@MentioUser]` ",false);
        embed.addField("Verification","Retrieve that user verification picture.\nUse `"+quickSummonWithSpace+"verification [EmlalockUserId/@MentioUser]`",false);
        embed.addField("Registration","You can register your Emlalock User id and API key to the bot in order the bot to use the Emlalock API to access the session. \nUse`"+quickSummonWithSpace+"menu`",false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    private void defaultVariables() {
        String fName = "defaultVariables]";
        logger.info(fName);
        try {
            gCalendarCurent=Calendar.getInstance();gCalendarLastVerification=Calendar.getInstance(); gCalendarStartDate=Calendar.getInstance();gCalendarEndDate=Calendar.getInstance();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    boolean sendGetError=true;
    private void getErrorJSON(HttpResponse<JsonNode> jsonResponse) {
        String fName = "[getError]";
        logger.info(fName);
        try {
            EmbedBuilder embedBuilder=new EmbedBuilder();
            //Its empty...The given data returned invalid response from the ChastiKey Server
            //embedBuilder.setFooter(String.valueOf(jsonResponse.getStatus()));
            if(jsonResponse!=null&&jsonResponse.getBody()!=null){
                logger.warn(fName+".jsonResponse="+jsonResponse.getBody().toString());
                //loggerFail.info("author="+gMember.getIdLong()+", target="+gUserProfile.gUser.getIdLong()+", reason="+jsonResponse.getBody().toString());
                if(!jsonResponse.getBody().isArray()){
                    logger.warn(fName+".body is an array");
                    JSONObject jsonObject=jsonResponse.getBody().getObject();
                    if(jsonObject.has("response")&&!jsonObject.isNull("response")&&jsonObject.getJSONObject("response").has("message")&&!jsonObject.getJSONObject("response").isNull("message")){
                        embedBuilder.setDescription(iReturnedValue4ApiIsInvalid+"\n"+jsonObject.getJSONObject("response").getString("message"));
                    }else{
                        embedBuilder.setDescription(iReturnedValue4ApiIsInvalid);
                    }
                }else{
                    embedBuilder.setDescription(iReturnedValue4ApiIsInvalid);
                }
            }else{
                //loggerFail.info("author="+gMember.getIdLong()+", target="+gUserProfile.gUser.getIdLong()+", reason=unspecified");
                embedBuilder.setDescription(iReturnedValue4ApiIsInvalid);
            }
            embedBuilder.setColor(llColorRed_Cornell);
            //Message message=llSendMessageResponse(gTextChannel,embedBuilder);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
            if(interactionHook!=null)interactionHook.setEphemeral(true).editOriginalEmbeds(embedBuilder.build()).complete();
            else if(sendGetError)lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel,embedBuilder,30);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    private void getErrorText(HttpResponse<String> jsonResponse) {
        String fName = "[getError]";
        logger.info(fName);
        try {
            EmbedBuilder embedBuilder=new EmbedBuilder();
            //Its empty...The given data returned invalid response from the ChastiKey Server
            //embedBuilder.setFooter(String.valueOf(jsonResponse.getStatus()));
            if(jsonResponse!=null&&jsonResponse.getBody()!=null){
                logger.warn(fName+".jsonResponse="+jsonResponse.getBody());
                //loggerFail.info("author="+gMember.getIdLong()+", target="+gUserProfile.gUser.getIdLong()+", reason="+jsonResponse.getBody());
                embedBuilder.setDescription("The returned value from emlalock api is invalid.\n"+jsonResponse.getBody());
            }else{
                //loggerFail.info("author="+gMember.getIdLong()+", target="+gUserProfile.gUser.getIdLong()+", reason=unspecified");
                embedBuilder.setDescription("The returned value from emlalock api is empty.");
            }
            embedBuilder.setColor(llColorRed_Cornell);
            //llSendMessage(gTextChannel,embedBuilder);
            if(sendGetError)lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel,embedBuilder,30);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }

    private JSONObject getUrlAsJson(String url,String id) {
        String fName = "[getUrlAsJson]";
        logger.info(fName);
        try {
            Member member=gMember;
            if(gTarget!=null){member=gTarget;}
            logger.info(fName + "id="+id);
            url=url.replaceAll("!id",id);
            logger.info(fName + "url="+url);
            Unirest a= new Unirest();
            HttpRequestWithBody b; HttpResponse<JsonNode> jsonResponse;JsonNode body;
            //b=a.get(url).header("sessionid",gSessionId);
            //jsonResponse=b.asJson();
            jsonResponse=a.get(url).header("sessionid",gSessionId).asJson();
            if(jsonResponse.getStatus()>200){
                getErrorJSON(jsonResponse);
                return null;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body.toString());
            if(body.isArray()){
                logger.error(fName+".body is an array");
                getErrorJSON(jsonResponse);
                return null;
            }
            JSONObject objects=new JSONObject();
            if(!body.getObject().isEmpty()){
                objects =body.getObject();
            }
            if(objects.isEmpty()){
                logger.error(fName+".array is empty");
                getErrorJSON(jsonResponse);
                return null;
            }
          return objects;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    private String getUrlAsText(String url,String id) {
        String fName = "[getUrlAsJson]";
        logger.info(fName);
        try {
            Member member=gMember;
            if(gTarget!=null){member=gTarget;}
            logger.info(fName + "id="+id);
            url=url.replaceAll("!id",id);
            logger.info(fName + "url="+url);
            Unirest a= new Unirest();
            HttpRequestWithBody b; HttpResponse<String> jsonResponse;String body;
            //b=a.get(url).header("sessionid",gSessionId);
            //jsonResponse=b.asJson();
            jsonResponse=a.get(url).header("sessionid",gSessionId).asString();
            if(jsonResponse.getStatus()>200){
                getErrorText(jsonResponse);
                return null;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body);
            return body;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    JSONObject Method2_ProfileJsonResponse =new JSONObject();
    private void getUserProfile(String id) {
        String fName = "[getUserProfile]";
        logger.info(fName);
        try {
            JSONObject profile=getUrlAsJson(gURLUserProfile,id);
            logger.info(cName+fName+".profile="+profile.toString());
            if(profile==null||profile.isEmpty()){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid id!",llColorRed);
                return;
            }
            WEARER.user.set(profile);
            reactionMenuUserProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return ;
        }

    }
    private void getSessionProfile(String id) {
        String fName = "[getUserVerification]";
        logger.info(fName);
        try {
            JSONObject session=getUrlAsJson(gUrlSessionProfile,id);
            logger.info(cName+fName+".session="+session.toString());
            if(session==null||session.isEmpty()){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid id!",llColorRed);
                return;
            }
            WEARER.session.set(session);
            JSONObject wearer,holder;
            wearer=getUrlAsJson(gURLUserProfile,session.getString(keywearerid));
            WEARER.session.set(wearer);
            holder=getUrlAsJson(gURLUserProfile,session.getString(keyholderid));
            WEARER.holder.set(holder);
            reactionMenuSessionProfile();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return ;
        }

    }
    private void getVerification(String id) {
        String fName = "[getUserVerification]";
        logger.info(fName);
        try {
            JSONObject session=getUrlAsJson(gUrlSessionProfile,id);
            logger.info(cName+fName+".session="+session.toString());
            if(session==null||session.isEmpty()){
                llSendQuickEmbedMessage(gTextChannel,gTitle,"Invalid id!",llColorRed);
                return;
            }
            JSONObject picture=new JSONObject();
            JSONObject wearer=new JSONObject();
            if(session.getInt(keysessiontype)==2){
                picture=getUrlAsJson(gUrlSessionPicture,session.getString(keychastitysessionid));
            }
            wearer=getUrlAsJson(gURLUserProfile,session.getString(keywearerid));
            if(picture==null){
                picture=new JSONObject();
            }
            if(wearer==null){
                wearer=new JSONObject();
            }

            reactionMenuVerificationPicture(wearer,picture,session);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return ;
        }

    }
    String gSessionId="";
    lcText2Json text2Json=null;
    private boolean readFile() {
        String fName="[readFile]";
        logger.info(fName);
        try {
            File file1;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+gValueFilePath);
                file1=new File(gValueFilePath);
                if(file1.exists()){
                    logger.info(fName+".file1 exists");
                    fileStream = new FileInputStream(file1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }

            text2Json=new lcText2Json();
            if(fileStream!=null){
                if(!text2Json.isInputStream2JsonObject(fileStream)){
                    logger.warn(fName+".failed to load");
                    return false;
                }else{
                    logger.info(fName+".loaded from file");
                }
            }else{
                logger.warn(fName+".no input stream");
            }
            if(text2Json.jsonObject.isEmpty()){
                logger.warn(fName+".isEmpty");return false;
            }
            logger.info(fName + ".text2Json.jsonObject=" + text2Json.jsonObject.toString());
            try {
                if(text2Json.jsonObject.has(keySessionID)&&!text2Json.jsonObject.isNull(keySessionID)&&!text2Json.jsonObject.getString(keySessionID).isBlank()){
                   gSessionId=text2Json.jsonObject.getString(keySessionID);
                    logger.info(fName + ".gSessionId=" + gSessionId);
                    WEARER.setSessionId(gSessionId);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    private void reactionMenuUserProfile(){
        String fName="[reactionMenuUserProfile]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String userid="";
            String profilepicture="";
            String username="";
            long maxsession=0;
            long shortsession=0;
            long sumsession=0;
            int irole=0;int iage=0;int isessions=0;
            int igender=0;
            String role="",gender="";
            try{
                logger.info(cName+fName+".json extracting");
                userid=WEARER.user.getUserId();
                username=WEARER.user.getUserName();
                maxsession= WEARER.user.getMaxSessionDuration(); //seconds
                shortsession= WEARER.user.getMinSessionDuration();
                sumsession= WEARER.user.getSumSessionDuration();
                irole= WEARER.user.getChastityRoleAsInt();
                igender= WEARER.user.getGenderAsInt();
                isessions=WEARER.user.getSessionsCount();
                role= Roles.valueByCode(irole).getName();
                gender= Genders.valueByCode(igender).getName();
                logger.info(cName+fName+".json extract done");
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            int mweeks = (int) (maxsession / 604800);int mdays = (int) (maxsession % 604800) / 86400;int mhours = (int) ((maxsession % 604800) % 86400) / 3600;
            int sweeks = (int) (shortsession / 604800);int sdays = (int) (shortsession % 604800) / 86400;int shours = (int) ((shortsession % 604800) % 86400) / 3600;
            int oweeks = (int) (sumsession / 604800);int odays = (int) (sumsession % 604800) / 86400;int ohours = (int) ((sumsession % 604800) % 86400) / 3600;

            embed.setTitle(username+" profile",gUrlPage2Profile.replaceAll("!id",WEARER.user.getUserId()));
            embed.addField("Gender",gender,true);embed.addField("Role",role,true);;embed.addField("Sessions", String.valueOf(isessions),true);
            embed.addBlankField(false);
            String duration="";
            duration="Max: ";
            if(mweeks>1){
                duration+=mweeks+" weeks ";
            }else
            if(mweeks==1){
                duration+="1 week ";
            }
            if(mdays>1){
                duration+=mdays+" days ";
            }else
            if(mdays==1){
                duration+="1 day ";
            }
            if(mhours>1){
                duration+=mhours+" hours ";
            }else
            if(mhours==1){
                duration+="1 hour ";
            }
            duration+="\nMin: ";
            if(sweeks>1){
                duration+=sweeks+" weeks ";
            }else
            if(sweeks==1){
                duration+="1 week ";
            }
            if(sdays>1){
                duration+=sdays+" days ";
            }else
            if(sdays==1){
                duration+="1 day ";
            }
            if(shours>1){
                duration+=shours+" hours ";
            }else
            if(shours==1){
                duration+="1 hour ";
            }
            duration+="\nAverage: ";
            if(oweeks>1){
                duration+=oweeks+" weeks ";
            }else
            if(oweeks==1){
                duration+="1 week ";
            }
            if(odays>1){
                duration+=odays+" days ";
            }else
            if(odays==1){
                duration+="1 day ";
            }
            if(ohours>1){
                duration+=ohours+" hours ";
            }else
            if(ohours==1){
                duration+="1 hour ";
            }
            embed.addField("Locked",duration,false);

            if(!WEARER.user.isProfilePicBlank()){
                embed.setThumbnail(WEARER.user.getProfilePicUrl());
            }
            Message message=llSendMessageResponse(gTextChannel,embed);
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
            message.addReaction(gGlobal.emojis.getEmoji("one")).queue();
            message.addReaction(gGlobal.emojis.getEmoji("two")).queue();
            String finalUserid = userid;
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(cName+fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            //logger.warn(cName+fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("one"))){
                                llMessageDelete(message);
                                getSessionProfile(finalUserid);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("two"))){
                                llMessageDelete(message);
                                getVerification(finalUserid);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                llMessageClearReactions(message);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                llMessageDelete(message);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private void reactionMenuSessionProfile(){
        String fName="[reactionMenuSessionProfile]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);

            try{
                logger.info(cName+fName+".json extracting");
                if(WEARER.user.hasKey(keyUserName)){
                    embed.setTitle(WEARER.user.getUserName()+" session",gUrlPage2Profile.replaceAll("!id",WEARER.user.getUserId()));
                }
                if(WEARER.holder.hasKey(keyUserName)){
                    lsUsefullFunctions.getUrlTextString(WEARER.holder.getUserName(),gUrlPage2Profile.replaceAll("!id",WEARER.holder.getUserName()));
                    embed.addField("Holder",WEARER.holder.getUserName(),true);
                }
                if(WEARER.session.hasKey(keysessiontype)){
                    embed.addField("Type", WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString(),true);
                }
                if(WEARER.session.hasKey(keystartduration)){
                    embed.addField("Start",convertDuration2Human(WEARER.session.getStarDurationAsLong()),true);
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        embed.addField("Maximum","disabled!",true);
                    }else{
                        embed.addField("Maximum",convertDuration2Human(WEARER.session.getMaxDurationAsLong()),true);
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        embed.addField("Minimum","disabled",true);
                    }else{
                        embed.addField("Minimum",convertDuration2Human(WEARER.session.getMinDurationAsLong()),true);
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    embed.addField("Started", lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate),true);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    embed.addField("Ends", lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate),true);
                }
                if(WEARER.session.hasDuration()){
                    embed.addField("Duration",convertDuration2Human(WEARER.session.getDurationAsLong()),true);
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    embed.addField("Remaining",convertDuration2Human(remaning),true);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    embed.addField("Done",convertDuration2Human(done),true);
                }
                if(WEARER.session.hasKey(keyinterval)){
                    String str="Interval: "+convertDuration2Human(WEARER.session.getInterval());
                    if(WEARER.session.hasLastVerification()){
                        gCalendarLastVerification.setTimeInMillis(WEARER.session.getLastVerification()* 1000);
                        str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                        long remaning=(WEARER.session.getInterval()* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                        logger.info(fName + ".remaning="+remaning);
                        remaning=remaning/1000;
                        if(remaning<0){
                            str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                        }else{
                            str+="\nRemaining: "+convertDuration2Human(remaning);
                        }
                    }
                    embed.addField("Verification", str,true);
                }
                if(WEARER.session.hasHygieneOpening()){
                    embed.addField("Hygiene Opening", WEARER.session.getCleaningsPerDay()+" "+WEARER.session.getCleaningPeriodAsString()+"\n"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt()),true);
                }
                if(WEARER.session.hasKey(keyrequirements)){
                    embed.addField("Requirements", WEARER.session.getRequirementsAsStr(),true);
                }
                logger.info(cName+fName+".json extract done");
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }

            Message message=llSendMessageResponse(gTextChannel,embed);
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
            message.addReaction(gGlobal.emojis.getEmoji("one")).queue();
            message.addReaction(gGlobal.emojis.getEmoji("two")).queue();
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(cName+fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            //logger.warn(cName+fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("one"))){
                                llMessageDelete(message);
                                getUserProfile(WEARER.session.getWearerId());
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji("two"))){
                                llMessageDelete(message);
                                getVerification(WEARER.session.getWearerId());
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                llMessageClearReactions(message);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                llMessageDelete(message);
                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    Calendar gCalendarLastVerification , gCalendarStartDate,gCalendarEndDate,gCalendarCurent;
    private void reactionMenuVerificationPicture(JSONObject wearer,JSONObject picture,JSONObject session){
        String fName="[reactionMenuSessionProfile]";
        logger.info(cName+fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);embed.setTitle(gTitle);

            try{
                logger.info(cName+fName+".json extracting");
                if(wearer.has(keyUserName)){
                    embed.setTitle(wearer.getString(keyUserName)+" verification picture",gUrlPage2Profile.replaceAll("!id",wearer.getString(keyUserId)));
                }
                if(picture.has(keyCode)){
                    embed.addField("Code",picture.getString(keyCode),false);
                }
                if(picture.has(keyFile)){
                    embed.setImage(gUrlSessionFile.replaceAll("!id",picture.getString(keyFile)));
                }
                if(session.has(keylastverification)) {
                    gCalendarLastVerification.setTimeInMillis(session.getLong(keylastverification)* 1000);
                    logger.info(cName+fName+".timestamp of lastverification="+session.getLong(keylastverification)* 1000);
                    embed.addField("Last verified", lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarLastVerification),false);
                }
                logger.info(cName+fName+".json extract done");
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }


            Message message=llSendMessageResponse(gTextChannel,embed);
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton)).queue();
            message.addReaction(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)).queue();
            gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(cName+fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            //logger.warn(cName+fName+"asCodepoints="+asCodepoints);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasDownwardsButton))){
                                llMessageClearReactions(message);
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                                llMessageDelete(message);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private String convertDuration2Human(long value) {
        String fName = "[convertDuration2Human]";
        logger.info(fName+"value="+value);
        try {
            int weeks = (int) (value / 604800);int days = (int) (value % 604800) / 86400;int hours = (int) ((value % 604800) % 86400) / 3600;
            String duration="";
            if(weeks>1){
                duration+=weeks+" weeks ";
            }else
            if(weeks==1){
                duration+="1 week ";
            }
            if(days>1){
                duration+=days+" days ";
            }else
            if(days==1){
                duration+="1 day ";
            }
            if(hours>1){
                duration+=hours+" hours ";
            }else
            if(hours==1){
                duration+="1 hour ";
            }
            logger.info(fName+"duration="+duration);
            return duration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    boolean isMenu=false;int menuIndex=-1;
    private void getMainEntries(){
        String fName="[getMainEntries";
        logger.info(fName);
        try{
            defaultVariables();
            if(WEARER.user.getJSON().isEmpty()){
                if(getWearerInfo()){
                    logger.info(fName+".managed to get user informatio");
                    if(WEARER.session.hasKeyHolder()){
                        if(WEARER.holder.isEmpty()){
                            if(getHolderInfo(WEARER.session.getHolderId())){
                                logger.info(fName+".managed to get holder information");
                            }
                        }
                    }
                }
            }
            getBDSMProfile(gUserProfile.gMember);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuMain(){
        String fName="[menuMain]";
        logger.info(fName);
        getProfile();
        getMainEntries();
        if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
            menuMain_Wearer();
        }else{
            if(isWearersHolder()||gIsOverride){
                menuMain_Holder();
            }else{
                if(iRestraints.lsHasUserOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)||iRestraints.lsHasUserSecOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)){
                    menuMain_RDOwner();
                }else{
                    menuMain_Somebody();
                }
            }
        }
    }
    private void menuMain_Wearer(){
        String fName="[menuMain_Wearer]";
        logger.info(fName);

        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
        desc=""; isMenu=true;menuIndex=0;
        embed.addField(strWhatIs,strWhatIsDetails,false);
        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
        embed.addField(strDisclaim,strDisclaimMore,false);
        try {
            if(!WEARER.profile.isEmptyUserId()){
                desc+="\nuser id:"+ WEARER.profile.getUserId();
            }else{
                desc+="\nuser id: (null)";
            }
            if(!WEARER.profile.isEmptyApi()){
                desc+="\napi key:"+ WEARER.profile.getApi();
            }else{
                desc+="\napi key: (null)";
            }
            embed.addField("User id& api key",desc,false);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            embed.addField("User id& api key","!error!",false);
        }
        if(!WEARER.user.getJSON().isEmpty()){
            desc="";
            try {
                desc+="\nname: "+WEARER.user.getUserName();
                desc+="\ngender: "+WEARER.user.getGenderAsString();
                desc+="\nrole: "+WEARER.user.getRoleAsString();
                embed.addField("Wearer",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: "+holderUser.getAsMention()+"";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                    String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                    if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                        gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                        str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                        long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                        logger.info(fName + ".remaning="+remaning);
                        remaning=remaning/1000;
                        if(remaning<0){
                            str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                        }else{
                            str+="\nRemaining: "+convertDuration2Human(remaning);
                        }
                    }
                    embed.addField("Verification", str,true);
                }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl)+" enter user id";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID)+" enter api key";
        embed.addField(" ", "Setup options :"+desc, false);
        embed=setupNotification(embed);
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" enter voting menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip)+" enter api calls menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" enter RD.Chastity punish/reward linking";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)+" enter RD.Timeout punish linking";
        embed.addField(" ", "Other options :"+desc, false);
        embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {


            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuMainListener_Wearer(message);
    }
    private void menuMain_Holder(){
        String fName="[menuMain_Holder]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
        desc="";isMenu=true;menuIndex=0;
        embed.addField(strWhatIs,strWhatIsDetails,false);
        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
        embed.addField(strDisclaim,strDisclaimMore,false);
        try {
            if(!WEARER.profile.isEmptyUserId()){
                desc+="\nuser id:"+ WEARER.profile.getUserId();
            }else{
                desc+="\nuser id: (null)";
            }
            if(HOLDERSqlJSON !=null&&!HOLDERSqlJSON.isEmpty()&&!HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                desc+="\nuser id:"+ HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId);
            }else{
                desc+="\nuser id: (null)";
            }
            embed.addField("Wearer & Holder id",desc,false);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            embed.addField("Wearer & Holder id","!error!",false);
        }
        desc="";
        if(!WEARER.user.getJSON().isEmpty()){
            desc="";
            try {
                desc+="\nname: "+WEARER.user.getUserName();
                desc+="\ngender: "+WEARER.user.getGenderAsString();
                desc+="\nrole: "+WEARER.user.getRoleAsString();
                embed.addField("Wearer",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: "+holderUser.getAsMention()+"";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                    String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                    if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                        gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                        str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                        long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                        logger.info(fName + ".remaning="+remaning);
                        remaning=remaning/1000;
                        if(remaning<0){
                            str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                        }else{
                            str+="\nRemaining: "+convertDuration2Human(remaning);
                        }
                    }
                    embed.addField("Verification", str,true);
                }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        embed=setupNotification(embed);
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" enter voting menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip)+" enter api calls menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" enter RD.Chastity punish/reward linking";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)+" enter RD.Timeout punish linking";
        embed.addField(" ", "Options :"+desc, false);
        embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            component0.setIgnored();

            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuMainListener_Holder(message);
    }
    private void menuMain_RDOwner(){
        String fName="[menuMain_RDOwner]";
        logger.info(fName);

        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
        desc="";isMenu=true;menuIndex=0;
        embed.addField(strWhatIs,strWhatIsDetails,false);
        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
        embed.addField(strDisclaim,strDisclaimMore,false);
        try {
            if(!WEARER.profile.isEmptyUserId()){
                desc+="\nuser id:"+ WEARER.profile.getUserId();
            }else{
                desc+="\nuser id: (null)";
            }
            if(HOLDERSqlJSON !=null&&!HOLDERSqlJSON.isEmpty()&&!HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                desc+="\nuser id:"+ HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId);
            }else{
                desc+="\nuser id: (null)";
            }
            embed.addField("Wearer & Holder id",desc,false);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            embed.addField("Wearer & Holder id","!error!",false);
        }
        desc="";
        if(!WEARER.user.getJSON().isEmpty()){
            desc="";
            try {
                desc+="\nname: "+WEARER.user.getUserName();
                desc+="\ngender: "+WEARER.user.getGenderAsString();
                desc+="\nrole: "+WEARER.user.getRoleAsString();
                embed.addField("Wearer",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: "+holderUser.getAsMention()+"";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                    String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                    if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                        gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                        str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                        long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                        logger.info(fName + ".remaning="+remaning);
                        remaning=remaning/1000;
                        if(remaning<0){
                            str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                        }else{
                            str+="\nRemaining: "+convertDuration2Human(remaning);
                        }
                    }
                    embed.addField("Verification", str,true);
                }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        embed=setupNotification(embed);
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" enter voting menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip)+" enter api calls menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" enter RD.Chastity punish/reward linking";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)+" enter RD.Timeout punish linking";
        embed.addField(" ", "Options :"+desc, false);
        embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {


            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuMainListener_RDOwner(message);
    }
    private void menuMain_Somebody(){
        String fName="[menuMain_Somebody]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
        desc="";
        embed.addField(strWhatIs,strWhatIsDetails,false);
        embed.addField(strSiteDiscord,strSiteDiscordMore,false);
        embed.addField(strDisclaim,strDisclaimMore,false);
        if(!WEARER.user.getJSON().isEmpty()){
            desc="";
            try {
                desc+="\nname: "+WEARER.user.getUserName();
                desc+="\ngender: "+WEARER.user.getGenderAsString();
                desc+="\nrole: "+WEARER.user.getRoleAsString();
                embed.addField("Wearer",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: "+holderUser.getAsMention()+"";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                    String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                    if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                        gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                        str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                        long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                        logger.info(fName + ".remaning="+remaning);
                        remaning=remaning/1000;
                        if(remaning<0){
                            str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                        }else{
                            str+="\nRemaining: "+convertDuration2Human(remaning);
                        }
                    }
                    embed.addField("Verification", str,true);
                }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        desc="";
        if(!WEARER.user.isValid()){
            desc+="\n**CAN'T RETREIVE USER INFO FROM EMLALOCK**";
        }else
        if(!WEARER.session.isInSession()){
            desc+="\n**USER IS NOT IN ANY ACTIVE SESSION**";
        }else
        if(!WEARER.session.isOwned()||!isWearerHolderRegistered()){
            desc+="\n**WEARER HAS NO HOLDER, SUBTRACTION IS IMPOSSIBLE**";
        }
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" enter voting menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip)+" enter api calls menu";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" enter RD.Chastity punish/reward linking";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT)+" enter RD.Timeout punish linking";
        embed.addField(" ", "Options :"+desc, false);
        embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {


            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuMainListener_Somebody(message);
    }
    private void menuMainListener_Wearer(Message message){
        String fName="[menuMainListener_Wearer]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasPassportControl:
                                dmInputUserId();
                                break;
                            case lsUnicodeEmotes.aliasID:
                                dmInputApiKey();
                                break;
                            case lsUnicodeEmotes.aliasGameDie:
                                menuVoting();
                                break;
                            case lsUnicodeEmotes.aliasPaperClip:
                                menuAPICalls();
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                menuRdChastity();
                                break;
                            case lsUnicodeEmotes.aliasSymbolT:
                                menuRdTimeout();
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                            dmInputApiKey();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl))){
                            dmInputUserId();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                            menuVoting();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip))){
                            menuAPICalls();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                            menuRdChastity();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                            menuRdTimeout();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

    }
    private void menuMainListener_Holder(Message message){
        String fName="[menuMainListener_Holder]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasGameDie:
                                menuVoting();
                                break;
                            case lsUnicodeEmotes.aliasPaperClip:
                                menuAPICalls();
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                menuRdChastity();
                                break;
                            case lsUnicodeEmotes.aliasSymbolT:
                                menuRdTimeout();
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }else
                            /*if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                                dmInputApiKey();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl))){
                                dmInputUserId();
                            }else*/
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                                menuVoting();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip))){
                                menuAPICalls();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                menuRdChastity();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                menuRdTimeout();
                            }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuMainListener_RDOwner(Message message){
        String fName="[menuMainListener_RDOwner]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasGameDie:
                                menuVoting();
                                break;
                            case lsUnicodeEmotes.aliasPaperClip:
                                menuAPICalls();
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                menuRdChastity();
                                break;
                            case lsUnicodeEmotes.aliasSymbolT:
                                menuRdTimeout();
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }else
                        /*if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasID))){
                            dmInputApiKey();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPassportControl))){
                            dmInputUserId();
                        }else*/
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                                menuVoting();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip))){
                                menuAPICalls();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                                menuRdChastity();
                            }else
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                                menuRdTimeout();
                            }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuMainListener_Somebody(Message message){
        String fName="[menuMainListener_Somebody]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasGameDie:
                                menuVoting();
                                break;
                            case lsUnicodeEmotes.aliasPaperClip:
                                menuAPICalls();
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                menuRdChastity();
                                break;
                            case lsUnicodeEmotes.aliasSymbolT:
                                menuRdTimeout();
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                            menuVoting();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPaperClip))){
                            menuAPICalls();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))){
                            menuRdChastity();
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolT))){
                            menuRdTimeout();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});

    }
    private void dmInputUserId(){
        String fName="[dmInputUserId]";
        logger.info(fName);
        try{
            String value=WEARER.profile.getUserId();Message message;
            if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current user id "+value+".\nPlease enter a new Emlalock user id.", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an Emlalock user id", llColorBlue1);
            }
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                if(isMenu)menuMain();
                                return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                WEARER.profile.setUserId("");
                            }
                            else{
                                WEARER.profile.setUserId(content);
                                checkInputValidity();
                            }
                            saveProfile();if(isMenu)menuMain();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void dmInputApiKey(){
        String fName="[dmInputApiKey]";
        logger.info(fName);
        try{
            String value=WEARER.profile.getApi();Message message;
            if(value!=null&&!value.isBlank()&&!value.isEmpty()){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current api key "+value+".\nPlease enter a new Emlalock api key associated with the user.", llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an Emlalockapi key associated with the user.", llColorBlue1);
            }
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                if(isMenu)menuMain();
                                return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                WEARER.profile.setApi("");
                            }
                            else{
                                WEARER.profile.setApi(content);
                                checkInputValidity();
                            }
                            saveProfile();if(isMenu)menuMain();
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuVoting(){
        String fName="[menuVoting]";
        logger.info(fName);
        getProfile();
        getMainEntries();
        if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
            menuVoting_Wearer();
        }else{
            if(isWearersHolder()||gIsOverride){
                menuVoting_Holder();
            }else{
                menuVoting_Somebody();
            }
        }
    }
    private void menuVoting_Wearer(){
        String fName="[menuVoting_Wearer]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Voting");
        isMenu=true;menuIndex=1;

        logger.info(fName+"VOTING="+WEARER.profile.voting.getJSON().toString());
        desc="";
        desc+="\nEnabled: "+WEARER.profile.voting.isEnable();
        long value=0L;
        if(WEARER.profile.voting.getTimeWait()<defaultMinTime){
            value=defaultWaitTIme;
        }else{
            value=WEARER.profile.voting.getTimeWait();
        }
        desc+="\nWait time: "+lsUsefullFunctions.displayDuration(value);
        if(WEARER.profile.voting.getTimeAdd()<defaultMinTime){
            value=defaultAddTime;
        }else{
            value=WEARER.profile.voting.getTimeAdd();
        }
        desc+="\nAdd time: "+lsUsefullFunctions.displayDuration(value);
        if(WEARER.profile.voting.getTimeSub()<defaultMinTime){
            value=defaultSubTime;
        }else{
            value=WEARER.profile.voting.getTimeSub();
        }
        desc+="\nSub time: "+lsUsefullFunctions.displayDuration(value);
        desc+="\nRandom enabled: "+WEARER.profile.voting.isAllowRandom();
        embed.addField("Voting", desc, false);
        embed=setupNotification(embed);
        desc="";
        if(WEARER.profile.voting.isEnable()){
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable";
        }else{
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable";
        }
        if(WEARER.profile.voting.isAllowRandom()){
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball)+" to disable random (only holder)";
        }else{
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball)+" to enable random (only holder)";
        }

        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" enter add time";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" enter sub time (only holder)";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" enter wait time";
        if(WEARER.session.isOwned()&&isWearerHolderRegistered()){
            desc+="\nYour keys are owned, you have no access to this options!";
        }

        embed.addField(" ", "Setup options :"+desc, false);
        desc="";
        if(WEARER.profile.voting.isEnable()){
            if(WEARER.profile.voting.isAllowAdd()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+" vote to add time";
            }
            if(WEARER.profile.voting.isAllowSub()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+" vote to sub time";
            }
            if(WEARER.profile.voting.isAllowAdd()&&WEARER.profile.voting.isAllowSub()&&WEARER.profile.voting.isAllowRandom()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" vote for random time";
            }
            desc+="\nOnly your holder & others are allowed!";
            embed.addField(" ", "Voting options :"+desc, false);
        }else{
            embed.addField(" ", "Voting disabled", false);
        }
        Message message=null;
        messageComponentManager.loadMessageComponents(gVotingFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent.Button buttonEnabled=component0.getButtonAt4(0);
            lcMessageBuildComponent.Button buttonEnableRandom=component0.getButtonAt4(1);
            lcMessageBuildComponent.Button buttonSetAdd=component0.getButtonAt4(2);
            lcMessageBuildComponent.Button buttonSetSub=component0.getButtonAt4(3);
            lcMessageBuildComponent.Button buttonSetWait=component0.getButtonAt4(4);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            lcMessageBuildComponent.Button buttonDoAdd=component1.getButtonAt4(0);
            lcMessageBuildComponent.Button buttonDoSub=component1.getButtonAt4(1);
            lcMessageBuildComponent.Button buttonDoRandom=component1.getButtonAt4(2);
            if(!gIsOverride&&WEARER.session.isOwned()&&isWearerHolderRegistered()){
                buttonEnabled.setDisable();
                buttonEnableRandom.setDisable();
                buttonSetAdd.setDisable();
                buttonSetSub.setDisable();
                buttonSetWait.setDisable();
            }
            if(!WEARER.profile.voting.isEnable()){
                buttonDoAdd.setDisable();
                buttonDoSub.setDisable();
                buttonDoRandom.setDisable();
            }else{
                if(!WEARER.profile.voting.isAllowAdd())buttonDoAdd.setDisable();
                if(!WEARER.profile.voting.isAllowSub())buttonDoSub.setDisable();
                if(!WEARER.profile.voting.isAllowRandom())buttonDoRandom.setDisable();
            }
            if(WEARER.profile.voting.isEnable()){
                buttonEnabled.setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel("🔴 Disable");
            }else{
                buttonEnabled.setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel("🟢 Enable");
            }
            if(WEARER.profile.voting.isAllowRandom()){
                buttonEnableRandom.setLabel("🎱 Random off");
            }else{
                buttonEnableRandom.setLabel("🎱 Random on");
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuVotingListener_Wearer(message);
    }
    private void menuVoting_Holder(){
        String fName="[menuVoting_Wearer]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" Voting");
        isMenu=true;menuIndex=1;
        logger.info(fName+"VOTING="+WEARER.profile.voting.getJSON());
        desc="";
        desc+="\nEnabled: "+WEARER.profile.voting.isEnable();
        long value=0L;
        if(WEARER.profile.voting.getTimeWait()<defaultMinTime){
            value=defaultWaitTIme;
        }else{
            value=WEARER.profile.voting.getTimeWait();
        }
        desc+="\nWait time: "+lsUsefullFunctions.displayDuration(value);
        if(WEARER.profile.voting.getTimeAdd()<defaultMinTime){
            value=defaultAddTime;
        }else{
            value=WEARER.profile.voting.getTimeAdd();
        }
        desc+="\nAdd time: "+lsUsefullFunctions.displayDuration(value);
        if(WEARER.profile.voting.getTimeSub()<defaultMinTime){
            value=defaultSubTime;
        }else{
            value=WEARER.profile.voting.getTimeSub();
        }
        desc+="\nSub time: "+lsUsefullFunctions.displayDuration(value);
        desc+="\nRandom enabled: "+WEARER.profile.voting.isAllowRandom();
        embed.addField("Voting", desc, false);
        embed=setupNotification(embed);
        desc="";
        if(WEARER.profile.voting.isEnable()){
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" to disable";
        }else{
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" to enable";
        }
        if(WEARER.profile.voting.isAllowRandom()){
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball)+" to disable random";
        }else{
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball)+" to enable random";
        }
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" enter add time";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" enter sub time";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" enter wait time";
        embed.addField(" ", "Setup options :"+desc, false);
        desc="";
        if(WEARER.profile.voting.isEnable()){
            if(WEARER.profile.voting.isAllowAdd()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+" vote to add time";
            }
            if(WEARER.profile.voting.isAllowSub()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+" vote to sub time";
            }
            if(WEARER.profile.voting.isAllowAdd()&&WEARER.profile.voting.isAllowSub()&&WEARER.profile.voting.isAllowRandom()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" vote for random time";
            }
            embed.addField(" ", "Voting options :"+desc, false);
        }else{
            embed.addField(" ", "Voting disabled", false);
        }
        Message message=null;
        messageComponentManager.loadMessageComponents(gVotingFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent.Button buttonEnabled=component0.getButtonAt4(0);
            lcMessageBuildComponent.Button buttonEnableRandom=component0.getButtonAt4(1);
            lcMessageBuildComponent.Button buttonSetAdd=component0.getButtonAt4(2);
            lcMessageBuildComponent.Button buttonSetSub=component0.getButtonAt4(3);
            lcMessageBuildComponent.Button buttonSetWait=component0.getButtonAt4(4);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            lcMessageBuildComponent.Button buttonDoAdd=component1.getButtonAt4(0);
            lcMessageBuildComponent.Button buttonDoSub=component1.getButtonAt4(1);
            lcMessageBuildComponent.Button buttonDoRandom=component1.getButtonAt4(2);
            if(!WEARER.profile.voting.isEnable()){
                buttonDoAdd.setDisable();
                buttonDoSub.setDisable();
                buttonDoRandom.setDisable();
            }else{
                if(!WEARER.profile.voting.isAllowAdd())buttonDoAdd.setDisable();
                if(!WEARER.profile.voting.isAllowSub())buttonDoSub.setDisable();
                if(!WEARER.profile.voting.isAllowRandom())buttonDoRandom.setDisable();
            }
            if(WEARER.profile.voting.isEnable()){
                buttonEnabled.setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel("🔴 Disable");
            }else{
                buttonEnabled.setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel("🟢 Enable");
            }
            if(WEARER.profile.voting.isAllowRandom()){
                buttonEnableRandom.setLabel("🎱 Random off");
            }else{
                buttonEnableRandom.setLabel("🎱 Random on");
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuVotingListener_Holder(message);
    }
    private void menuVoting_Somebody(){
        String fName="[menuVoting_Somebody]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" Voting");
        isMenu=true;menuIndex=1;
        logger.info(fName+"VOTING="+WEARER.profile.voting.getJSON());
        desc="";
        desc+="\nEnabled: "+WEARER.profile.voting.isEnable();
        long value=0L;
        if(WEARER.profile.voting.getTimeWait()<defaultMinTime){
            value=defaultWaitTIme;
        }else{
            value=WEARER.profile.voting.getTimeWait();
        }
        desc+="\nWait time: "+lsUsefullFunctions.displayDuration(value);
        if(WEARER.profile.voting.getTimeAdd()<defaultMinTime){
            value=defaultAddTime;
        }else{
            value=WEARER.profile.voting.getTimeAdd();
        }
        desc+="\nAdd time: "+lsUsefullFunctions.displayDuration(value);
        if(WEARER.profile.voting.getTimeSub()<defaultMinTime){
            value=defaultSubTime;
        }else{
            value=WEARER.profile.voting.getTimeSub();
        }
        desc+="\nSub time: "+lsUsefullFunctions.displayDuration(value);
        desc+="\nRandom enabled: "+WEARER.profile.voting.isAllowRandom();
        embed.addField("Voting", desc, false);
        embed=setupNotification(embed);
        desc="";
        if(WEARER.profile.voting.isEnable()){
            if(WEARER.profile.voting.isAllowAdd()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp)+" vote to add time";
            }
            if(WEARER.profile.voting.isAllowSub()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown)+" vote to sub time";
            }
            if(WEARER.profile.voting.isAllowAdd()&&WEARER.profile.voting.isAllowSub()&&WEARER.profile.voting.isAllowRandom()){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie)+" vote for random time";
            }
            embed.addField(" ", "Voting options :"+desc, false);
        }
        Message message=null;
        messageComponentManager.loadMessageComponents(gVotingFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent.Button buttonEnabled=component0.getButtonAt4(0);
            lcMessageBuildComponent.Button buttonEnableRandom=component0.getButtonAt4(1);
            lcMessageBuildComponent.Button buttonSetAdd=component0.getButtonAt4(2);
            lcMessageBuildComponent.Button buttonSetSub=component0.getButtonAt4(3);
            lcMessageBuildComponent.Button buttonSetWait=component0.getButtonAt4(4);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            lcMessageBuildComponent.Button buttonDoAdd=component1.getButtonAt4(0);
            lcMessageBuildComponent.Button buttonDoSub=component1.getButtonAt4(1);
            lcMessageBuildComponent.Button buttonDoRandom=component1.getButtonAt4(2);
            if(!gIsOverride){
                component0.setIgnored();
            }else{
                if(WEARER.profile.voting.isEnable()){
                    buttonEnabled.setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel("🔴 Disable");
                }else{
                    buttonEnabled.setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel("🟢 Enable");
                }
                if(WEARER.profile.voting.isAllowRandom()){
                    buttonEnableRandom.setLabel("🎱 Random off");
                }else{
                    buttonEnableRandom.setLabel("🎱 Random on");
                }
            }
            if(!WEARER.profile.voting.isEnable()){
                buttonDoAdd.setDisable();
                buttonDoSub.setDisable();
                buttonDoRandom.setDisable();
            }else{
                if(!WEARER.profile.voting.isAllowAdd())buttonDoAdd.setDisable();
                if(!WEARER.profile.voting.isAllowSub())buttonDoSub.setDisable();
                if(!WEARER.profile.voting.isAllowRandom())buttonDoRandom.setDisable();
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuVotingListener_Somebody(message);
    }
    private void menuVotingListener_Wearer(Message message){
        String fName="[menuVotingListener_Wearer]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                            case lsUnicodeEmotes.aliasGreenCircle:
                                setVotingEnable(0,true);
                                break;
                            case lsUnicodeEmotes.aliasRedCircle:
                                setVotingEnable(0,false);
                                break;
                            case lsUnicodeEmotes.alias8Ball:
                                setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                dmInputVotingDuration(1);
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                dmInputVotingDuration(2);
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                dmInputVotingDuration(3);
                                break;
                            case lsUnicodeEmotes.aliasArrowDoubleUp:
                                doVoting(1);
                                break;
                            case lsUnicodeEmotes.aliasArrowDoubleDown:
                                doVoting(2);
                                break;
                            case lsUnicodeEmotes.aliasGameDie:
                                doVoting(3);
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                            menuMain();
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())){
                            setVotingEnable(0,true);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())){
                            setVotingEnable(0,false);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())){
                            setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())){
                            dmInputVotingDuration(1);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())){
                            dmInputVotingDuration(2);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())){
                            dmInputVotingDuration(3);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){
                            doVoting(1);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){
                            doVoting(2);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                            doVoting(3);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                            setVotingEnable(1,!WEARER.profile.voting.isAllowAdd());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                            setVotingEnable(2,!WEARER.profile.voting.isAllowSub());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPineapple))) {
                            setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                        }


                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuVotingListener_Holder(Message message){
        String fName="[menuVotingListener_Wearer]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                            case lsUnicodeEmotes.aliasGreenCircle:
                                setVotingEnable(0,true);
                                break;
                            case lsUnicodeEmotes.aliasRedCircle:
                                setVotingEnable(0,false);
                                break;
                            case lsUnicodeEmotes.alias8Ball:
                                setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                dmInputVotingDuration(1);
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                dmInputVotingDuration(2);
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                dmInputVotingDuration(3);
                                break;
                            case lsUnicodeEmotes.aliasArrowDoubleUp:
                                doVoting(1);
                                break;
                            case lsUnicodeEmotes.aliasArrowDoubleDown:
                                doVoting(2);
                                break;
                            case lsUnicodeEmotes.aliasGameDie:
                                doVoting(3);
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                            menuMain();
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                            setVotingEnable(0,true);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                            setVotingEnable(0,false);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball))){
                            setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                            dmInputVotingDuration(1);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                            dmInputVotingDuration(2);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                            dmInputVotingDuration(3);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){
                            doVoting(1);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){
                            doVoting(2);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                            doVoting(3);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                            setVotingEnable(1,!WEARER.profile.voting.isAllowAdd());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                            setVotingEnable(2,!WEARER.profile.voting.isAllowSub());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPineapple))) {
                            setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuVotingListener_Somebody(Message message){
        String fName="[menuVotingListener_Somebody]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                            case lsUnicodeEmotes.aliasGreenCircle:
                                setVotingEnable(0,true);
                                break;
                            case lsUnicodeEmotes.aliasRedCircle:
                                setVotingEnable(0,false);
                                break;
                            case lsUnicodeEmotes.alias8Ball:
                                setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                dmInputVotingDuration(1);
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                dmInputVotingDuration(2);
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                dmInputVotingDuration(3);
                                break;
                            case lsUnicodeEmotes.aliasArrowDoubleUp:
                                doVoting(1);
                                break;
                            case lsUnicodeEmotes.aliasArrowDoubleDown:
                                doVoting(2);
                                break;
                            case lsUnicodeEmotes.aliasGameDie:
                                doVoting(3);
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");return;
                        }else
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                            menuMain();
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleUp))){
                            doVoting(1);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown))){
                            doVoting(2);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGameDie))){
                            doVoting(3);
                        }
                        else if((name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8Ball))||name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPineapple)))&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            setVotingEnable(3,!WEARER.profile.voting.isAllowRandom());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))) {
                            setVotingEnable(1,!WEARER.profile.voting.isAllowAdd());
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))) {
                            setVotingEnable(2,!WEARER.profile.voting.isAllowSub());
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void dmInputVotingDuration(int type){
        String fName="[dmInputVotingDuration]";
        try{
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName+"type="+type);
            long value=0;
            switch (type){
                case 1: value= WEARER.profile.voting.getTimeAdd();
                break;
                case 2: value= WEARER.profile.voting.getTimeSub();
                    break;
                case 3: value=WEARER.profile.voting.getTimeWait();
                    break;
            }
            Message message = null;
            if(value!=0){
                switch (type){
                    case 1:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current add time "+lsUsefullFunctions.displayDuration(value)+".\nPlease enter a new value for add time."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                    case 2:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current sub time "+lsUsefullFunctions.displayDuration(value)+".\nPlease enter a new value for sub time."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                    case 3:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current wait time "+lsUsefullFunctions.displayDuration(value)+".\nPlease enter a new value for wait time."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                }

            }else{
                switch (type){
                    case 1:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an value for add time."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                    case 2:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an value for sub time."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                    case 3:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an value for wait time."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                }

            }
            Message finalMessage = message;
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                if(isMenu)menuVoting();
                                return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                switch (type){
                                    case 1: WEARER.profile.voting.setTimeAdd(0L);
                                        break;
                                    case 2:  WEARER.profile.voting.setTimeSub(0L);
                                        break;
                                    case 3:  WEARER.profile.voting.setTimeWait(0L);
                                        break;
                                }
                                saveProfile();
                                if(isMenu)menuVoting();
                            }
                            else{
                                if(setVotingDuration(type,content)){
                                    saveProfile();
                                    if(isMenu)menuVoting();
                                }

                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(finalMessage);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(finalMessage);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private boolean setVotingDuration(int type,String message){
        String fName = "[setVotingDuration]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            logger.info(fName + ".type="+type+", message=" + message);
            long timeset = StringShortTerms2Timeset4Duration(message);
            logger.info(fName + ".timeset=" + timeset);
            if(type==3){
                if (timeset < milliseconds_hour) {
                    llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be minimum 1 hour.", llColorRed);
                    return false;
                }
                if (timeset >milliseconds_hour*48) {
                    llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be bellow 48 hours.", llColorRed);
                    return false;
                }
            }else{
                if (timeset < milliseconds_minute*15) {
                    llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be minimum 15 minutes.", llColorRed);
                    return false;
                }
                if (timeset >milliseconds_hour*24) {
                    llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be bellow 24 hours.", llColorRed);
                    return false;
                }
            }

            switch (type){
                case 1: WEARER.profile.voting.setTimeAdd(timeset);
                    llSendQuickEmbedMessage(gTextChannel, gTitle, "Add time voting for "+gUserProfile.gUser.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                    break;
                case 2: WEARER.profile.voting.setTimeSub(timeset);
                    llSendQuickEmbedMessage(gTextChannel, gTitle, "Sub time voting for "+gUserProfile.gUser.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                    break;
                case 3: WEARER.profile.voting.setTimeWait(timeset);
                    llSendQuickEmbedMessage(gTextChannel, gTitle, "Wait time voting for "+gUserProfile.gUser.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                    break;
            }
            return true;
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,ex.toString());
            return  false;
        }
    }
    private void setVotingEnable(int type,boolean value){
        String fName = "[setVotingDuration]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".type="+type+", value=" +value);
            switch (type){
                case 1: WEARER.profile.voting.setAllowAdd(value);
                    break;
                case 2: WEARER.profile.voting.setAllowSub(value);
                    break;
                case 3: WEARER.profile.voting.setAllowRandom(value);
                    if(value){
                        llSendQuickEmbedMessage(gUser, gTitle, "You enabled the random voting, be carefull.", llColorBlue1);
                    }else{
                        llSendQuickEmbedMessage(gUser, gTitle, "You disabed random voting.", llColorBlue1);
                    }
                    break;
                case 0: WEARER.profile.voting.setEnable(value);
                    if(value){
                        llSendQuickEmbedMessage(gUser, gTitle, "You enabled the voting system, be carefull.", llColorBlue1);
                    }else{
                        llSendQuickEmbedMessage(gUser, gTitle, "You disabed the voting system.", llColorBlue1);
                    }
                    break;
            }
            saveProfile();
            if(isMenu)menuVoting();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,ex.toString());
        }
    }
    HttpResponse<JsonNode> responseSessionUpdate;
    private void doVoting(int type){
        String fName = "[setVotingDuration]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".type="+type);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            boolean isEnabled=false,isEnabledOption=false;
            long time=0L;
            logger.info(fName + "VOTING="+WEARER.profile.voting.getJSON());
            isEnabled=WEARER.profile.voting.isEnable();
            int updatedtype=type;
            switch (type){
                case 1:
                    isEnabledOption=WEARER.profile.voting.isAllowAdd();
                    break;
                case 2:
                    isEnabledOption=WEARER.profile.voting.isAllowSub();
                    break;
                case 3:
                    isEnabledOption=WEARER.profile.voting.isAllowRandom();
                    int n=0;
                    while (updatedtype!=1&&updatedtype!=2&&n<100){
                        n++;
                        updatedtype=lsUsefullFunctions.getRandom(4);
                    }
                    break;
            }
            logger.info(fName + ".updatedtype="+updatedtype);
            logger.info(fName + ".isEnabled="+isEnabled+", isEnabledOption="+isEnabledOption);
            if(!isEnabled){
                logger.info(fName + "not enabled");
                embed.setDescription("Sorry, voting is not enabled for "+gUserProfile.gUser.getAsMention()+".");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(!isEnabledOption){
                logger.info(fName + "not enabledoption");
                embed.setDescription("Sorry,this voting option is not enabled for "+gUserProfile.gUser.getAsMention()+".");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            boolean isAllowed=false;
            Timestamp currenttime = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".currenttime="+currenttime.getTime());
            if(!WEARER.profile.voting.isMemberPresentInLogs(gMember)){
                isAllowed=true;
            }else{
                long lastTimeVoted=WEARER.profile.voting.getMemberTimeStamp4Logs(gMember);
                logger.info(fName+".lastTimeVoted="+lastTimeVoted);
                long wait=defaultWaitTIme;
                if(WEARER.profile.voting.getTimeWait()>defaultMinTime){
                    wait=WEARER.profile.voting.getTimeWait();
                }
                logger.info(fName+".wait="+wait);
                long lastTimeVoted2=lastTimeVoted+wait;
                logger.info(fName+".lastTimeVoted+wait="+lastTimeVoted2);
                if(currenttime.getTime()>=lastTimeVoted2){
                    logger.info(fName+".waited enough");
                    isAllowed=true;
                }
            }
            if(!isAllowed){
                logger.info(fName+".not allowed to vote");
                embed.setDescription("Sorry, you still need to wait before voting for "+gUserProfile.gUser.getAsMention()+".");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }else{
                logger.info(fName+".register");
                WEARER.profile.voting.setMemberTimeStamp4Logs(gMember,currenttime.getTime());
                saveProfile();
            }
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            long timeUpdater=0L;
            String url="";
            switch (updatedtype){
                case 1:
                    timeUpdater=WEARER.profile.voting.getTimeAdd();
                    url=gUrlApiAdd;
                    break;
                case 2:
                    timeUpdater=WEARER.profile.voting.getTimeSub();
                    url=gUrlApiSub;
                    break;
            }
            logger.info(fName+".timeUpdater="+timeUpdater);
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(timeUpdater);
            url=url.replaceAll("!VALUE", String.valueOf(time2Use));
            if(updatedtype==2){
                if(!WEARER.session.hasKeyHolder()){
                    embed.setDescription("No holder id found for "+gUserProfile.gUser.getAsMention()+".");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" holder info.");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }

                if(!HOLDERSqlEntry.has(sqlKeyEmUserId)||HOLDERSqlEntry.getString(sqlKeyEmUserId).isBlank()){
                    logger.warn(fName + ".no holder user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(!HOLDERSqlEntry.has(sqlKeyApi)||HOLDERSqlEntry.getString(sqlKeyApi).isBlank()){
                    logger.warn(fName + ".no holder api provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                url=url.replaceAll("!HOLDERAPIKEY",HOLDERSqlEntry.getString(sqlKeyApi));
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    switch (type){
                        case 1:
                            embed.setColor(llColorRed_Cinnabar);
                            embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration to their session.");
                            break;
                        case 2:
                            embed.setColor(llColorGreen_Mint);
                            embed.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration from their session.");
                            break;
                        case 3:
                            embed.setColor(llColorPurple2);
                            embed.setDescription(gMember.getAsMention()+" chose to play the dice with their session duration.");
                            break;
                    }
                }else{
                    switch (type){
                        case 1:
                            embed.setColor(llColorRed_Cinnabar);
                            embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            break;
                        case 2:
                            embed.setColor(llColorGreen_Mint);
                            embed.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration from "+gUserProfile.gUser.getAsMention()+"'s session.");
                            break;
                        case 3:
                            embed.setColor(llColorPurple2);
                            embed.setDescription(gMember.getAsMention()+" chose to play the dice with "+gUserProfile.gUser.getAsMention()+"'s session duration.");
                            break;
                    }
                }
                embed.setTitle(gTitle);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuVoting();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }

    private void menuAPICalls(){
        String fName="[menuAPICalls]";
        logger.info(fName);
        getProfile();
        getMainEntries();
        if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
            menuAPICalls_Wearer();
        }else{
            if(isWearersHolder()||gIsOverride){
                menuAPICalls_Holder();
            }else{
                menuAPICalls_Somebody();
            }
        }
    }
    private void menuAPICalls_Wearer(){
        String fName="[menuAPICalls_Weare]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" API Calls");
        desc=""; isMenu=true;menuIndex=0;

        if(WEARER.session.isEmpty()){
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: <@"+holderUser.getAsMention()+">";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                    /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                        String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                        if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                            gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                            str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                            long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                            logger.info(fName + ".remaning="+remaning);
                            remaning=remaning/1000;
                            if(remaning<0){
                                str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                            }else{
                                str+="\nRemaining: "+convertDuration2Human(remaning);
                            }
                        }
                        embed.addField("Verification", str,true);
                    }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Session","!error!",false);
            }
        }
        embed=setupNotification(embed);
        //desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" enter api key";
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Add, will raise session duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" AddMaximum, will raise session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" AddMaximumRandom, will raise session maximum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" AddMinimum, will raise session minimum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" AddMinimumRandom, will raise session minimum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" AddRandom, will raise session duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" AddRequirement, will raise session requirements.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" AddRequirementRandom, will raise session requirements randomly between two values.";
        embed.addField("Add Calls", desc, false);
        desc="\nSorry only your holder is allowed to subtract!";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" Sub, will lower session duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" SubMaximum, will lower session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" SubMaximumRandom, will lower session maximum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" SubMinimum, will lower session minimum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" SubMinimumRandom, will lower session minimum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" SubRandom, will lower session duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)+" SubRequirement, will lower session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)+" SubRequirementRandom, will lower session minimum duration randomly between two values.";
        embed.addField("Sub Calls", desc, false);
        Message message=null;
        messageComponentManager.loadMessageComponents(gcallFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent.SelectMenu select0=component0.getSelect();
            if(!gIsOverride){
                for(int i=8;i<16;i++){
                    select0.getOptionAt(i).setIgnored();
                }
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuAPICallsListener_Wearer(message);
    }
    private void menuAPICalls_Holder(){
        String fName="[menuAPICalls_Holder]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getAsMention()+"'s "+gTitle+" API Calls");
        desc=""; isMenu=true;menuIndex=0;
        if(WEARER.session.isEmpty()){
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: <@"+holderUser.getAsMention()+">";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                    /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                        String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                        if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                            gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                            str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                            long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                            logger.info(fName + ".remaning="+remaning);
                            remaning=remaning/1000;
                            if(remaning<0){
                                str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                            }else{
                                str+="\nRemaining: "+convertDuration2Human(remaning);
                            }
                        }
                        embed.addField("Verification", str,true);
                    }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Session","!error!",false);
            }
        }
        embed=setupNotification(embed);
        //desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" enter api key";
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Add, will raise session duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" AddMaximum, will raise session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" AddMaximumRandom, will raise session maximum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" AddMinimum, will raise session minimum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" AddMinimumRandom, will raise session minimum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" AddRandom, will raise session duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" AddRequirement, will raise session requirements.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" AddRequirementRandom, will raise session requirements randomly between two values.";
        embed.addField("Add Calls",desc, false);
        desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" Sub, will lower session duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" SubMaximum, will lower session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" SubMaximumRandom, will lower session maximum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" SubMinimum, will lower session minimum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" SubMinimumRandom, will lower session minimum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" SubRandom, will lower session duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)+" SubRequirement, will lower session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)+" SubRequirementRandom, will lower session minimum duration randomly between two values.";
        embed.addField("Sub Calls",desc, false);
        Message message=null;
        messageComponentManager.loadMessageComponents(gcallFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent.SelectMenu select0=component0.getSelect();
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuAPICallsListener_Holder(message);
    }
    private void menuAPICalls_Somebody(){
        String fName="[menuAPICalls_Somebody]";
        logger.info(fName);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getAsMention()+"'s "+gTitle+" API Calls");
        desc=""; isMenu=true;menuIndex=0;
        if(WEARER.session.isEmpty()){
            desc="";
            try {
                if(WEARER.session.hasKeyHolder()){
                    if(WEARER.holder.isEmpty()){
                        desc+="\nHolder: [Holder](https://www.emlalock.com/#/profile/"+WEARER.session.getHolderId()+") did not registered to the bot.";
                    }else{
                        String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
                        User holderUser=lsUserHelper.lsGetUser(gGuild,holderId);
                        if(holderUser==null){
                            desc+="\nHolder: <@"+holderId+">";
                        }else{
                            desc+="\nHolder: <@"+holderUser.getAsMention()+">";
                        }
                    }
                }
                desc+="\nType: "+WEARER.session.getSessionTypeAsString()+", "+WEARER.session.getDurationTypeAsString();
                if(WEARER.session.hasStartDuration()){
                    desc+="\nStart: "+convertDuration2Human(WEARER.session.getStartingDurationAsLong());
                }
                if(WEARER.session.hasMaxDuration()){
                    if(WEARER.session.getMaxDurationAsLong()==0){
                        desc+="\nMaximum: "+"disabled!";
                    }else{
                        desc+="\nMaximum: "+convertDuration2Human(WEARER.session.getMaxDurationAsLong());
                    }
                }
                if(WEARER.session.hasMinDuration()){
                    if(WEARER.session.getMinDurationAsLong()==0){
                        desc+="\nMinimum: "+"disabled";
                    }else{
                        desc+="\nMinimum: "+convertDuration2Human(WEARER.session.getMinDurationAsLong());
                    }
                }
                if(WEARER.session.hasStartDate()) {
                    gCalendarStartDate.setTimeInMillis(WEARER.session.getStartDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of start date="+WEARER.session.getStartDateAsLong()* 1000);
                    desc+="\nStarted: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarStartDate);
                }
                if(WEARER.session.hasEndDate()) {
                    gCalendarEndDate.setTimeInMillis(WEARER.session.getEndDateAsLong()* 1000);
                    logger.info(cName+fName+".timestamp of send date="+WEARER.session.getEndDateAsLong()* 1000);
                    desc+="\nEnds: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                }
                if(WEARER.session.hasDuration()){
                    desc+="\nDuration: "+convertDuration2Human(WEARER.session.getDurationAsLong());
                }
                if(WEARER.session.hasDuration()&&WEARER.session.hasEndDate()&&WEARER.session.hasStartDate()){
                    long remaning=gCalendarEndDate.getTimeInMillis()-gCalendarCurent.getTimeInMillis();
                    remaning=remaning/1000;
                    logger.info(fName + ".remaning="+remaning);
                    if(remaning<0)remaning=0;
                    desc+="\nRemaining: "+convertDuration2Human(remaning);
                    long done=gCalendarCurent.getTimeInMillis()-gCalendarStartDate.getTimeInMillis();
                    if(done<0){
                        done*=-1;
                    }
                    done=done/1000;
                    logger.info(fName + ".done="+done);
                    desc+="\nDone: "+convertDuration2Human(done);
                }
                    /*if(WEARERINFO_Session.has(keyinterval)&&WEARERINFO_Session.getLong(keyinterval)>0){
                        String str="Interval: "+convertDuration2Human(WEARERINFO_Session.getLong(keyinterval));
                        if(WEARERINFO_Session.has(keylastverification)&&WEARERINFO_Session.getLong(keylastverification)>0){
                            gCalendarLastVerification.setTimeInMillis(WEARERINFO_Session.getLong(keylastverification)* 1000);
                            str+="\nLast: "+lsUsefullFunctions.convertCalendar2HumanReadable(gCalendarEndDate);
                            long remaning=(WEARERINFO_Session.getLong(keyinterval)* 1000)-(gCalendarCurent.getTimeInMillis()-gCalendarLastVerification.getTimeInMillis());
                            logger.info(fName + ".remaning="+remaning);
                            remaning=remaning/1000;
                            if(remaning<0){
                                str+="\nRemaining: "+convertDuration2Human(remaning*-1);
                            }else{
                                str+="\nRemaining: "+convertDuration2Human(remaning);
                            }
                        }
                        embed.addField("Verification", str,true);
                    }*/
                if(WEARER.session.hasHygieneOpening()&&WEARER.session.getHygieneOpeningAsInt()>0){
                    desc+="\nHygiene Opening: "+WEARER.session.getCleaningsPerDay()+"|"+WEARER.session.getCleaningPeriodAsString()+"|"+convertDuration2Human(WEARER.session.getTimeforCleaningAsInt());
                }
                if(WEARER.session.hasRequirements()&&WEARER.session.getRequirementsAsInt()>0){
                    desc+="\nRequirements: "+String.valueOf(WEARER.session.getRequirementsAsInt());
                }
                embed.addField("Session",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Session","!error!",false);
            }
        }
        embed=setupNotification(embed);
        //desc="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero)+" enter api key";
        desc="\n This options are reserved for wearer and holder!";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Add, will raise session duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" AddMaximum, will raise session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree)+" AddMaximumRandom, will raise session maximum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour)+" AddMinimum, will raise session minimum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive)+" AddMinimumRandom, will raise session minimum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix)+" AddRandom, will raise session duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven)+" AddRequirement, will raise session requirements.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight)+" AddRequirementRandom, will raise session requirements randomly between two values.";
        embed.addField("Add Calls",desc, false);
        desc="\n This options are reserved for wearer's holder!";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA)+" Sub, will lower session duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB)+" SubMaximum, will lower session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC)+" SubMaximumRandom, will lower session maximum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD)+" SubMinimum, will lower session minimum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE)+" SubMinimumRandom, will lower session minimum duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" SubRandom, will lower session duration randomly between two values.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG)+" SubRequirement, will lower session maximum duration.";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH)+" SubRequirementRandom, will lower session minimum duration randomly between two values.";
        embed.addField("Sub Calls",desc, false);
        Message message=null;
        messageComponentManager.loadMessageComponents(gcallFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent.SelectMenu select0=component0.getSelect();
            if(!gIsOverride){
                select0.setDisabled();
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuAPICallsListener_Somebody(message);
    }
    private void menuAPICallsListener_Wearer(Message message){
        String fName="[menuAPICallsListener_Weare]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        logger.warn(fName+"value="+value);
                        llMessageDelete(message);
                        switch (value){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                doAPI_Add(0);
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                doAPI_AddMaximum(0);
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                doAPI_AddMaximumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasFour:
                                doAPI_AddMinimum(0);
                                break;
                            case lsUnicodeEmotes.aliasFive:
                                doAPI_AddMinimumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSix:
                                doAPI_AddRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSeven:
                                doAPI_AddRequirement(0);
                                break;
                            case lsUnicodeEmotes.aliasEight:
                                doAPI_AddRequirementRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolA:
                                doAPI_Sub(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolB:
                                doAPI_SubMaximum(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                doAPI_SubMaximumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolD:
                                doAPI_SubMinimum(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolE:
                                doAPI_SubMinimumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolF:
                                doAPI_SubRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolG:
                                doAPI_SubRequirement(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolH:
                                doAPI_SubRequirementRandom(0,0);
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                            menuMain();
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                            doAPI_Add(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                            doAPI_AddMaximum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                            doAPI_AddMaximumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                            doAPI_AddMinimum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))) {
                            doAPI_AddMinimumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))) {
                            doAPI_AddRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))) {
                            doAPI_AddRequirement(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))) {
                            doAPI_AddRequirementRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))&&gIsOverride) {
                            doAPI_Sub(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))&&gIsOverride) {
                            doAPI_SubMaximum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))&&gIsOverride) {
                            doAPI_SubMaximumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))&&gIsOverride) {
                            doAPI_SubMinimum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))&&gIsOverride) {
                            doAPI_SubMinimumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))&&gIsOverride) {
                            doAPI_SubRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG))&&gIsOverride) {
                            doAPI_SubRequirement(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH))&&gIsOverride) {
                            doAPI_SubRequirementRandom(0,0);
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuAPICallsListener_Holder(Message message){
        String fName="[menuAPICallsListener_Holder]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        logger.warn(fName+"value="+value);
                        llMessageDelete(message);
                        switch (value){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                doAPI_Add(0);
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                doAPI_AddMaximum(0);
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                doAPI_AddMaximumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasFour:
                                doAPI_AddMinimum(0);
                                break;
                            case lsUnicodeEmotes.aliasFive:
                                doAPI_AddMinimumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSix:
                                doAPI_AddRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSeven:
                                doAPI_AddRequirement(0);
                                break;
                            case lsUnicodeEmotes.aliasEight:
                                doAPI_AddRequirementRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolA:
                                doAPI_Sub(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolB:
                                doAPI_SubMaximum(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                doAPI_SubMaximumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolD:
                                doAPI_SubMinimum(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolE:
                                doAPI_SubMinimumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolF:
                                doAPI_SubRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolG:
                                doAPI_SubRequirement(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolH:
                                doAPI_SubRequirementRandom(0,0);
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                            menuMain();
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                            doAPI_Add(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                            doAPI_AddMaximum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))) {
                            doAPI_AddMaximumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFour))) {
                            doAPI_AddMinimum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasFive))) {
                            doAPI_AddMinimumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSix))) {
                            doAPI_AddRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSeven))) {
                            doAPI_AddRequirement(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasEight))) {
                            doAPI_AddRequirementRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolA))) {
                            doAPI_Sub(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolB))) {
                            doAPI_SubMaximum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolC))) {
                            doAPI_SubMaximumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolD))) {
                            doAPI_SubMinimum(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolE))) {
                            doAPI_SubMinimumRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))) {
                            doAPI_SubRandom(0,0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolG))) {
                            doAPI_SubRequirement(0);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolH))) {
                            doAPI_SubRequirementRandom(0,0);
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuAPICallsListener_Somebody(Message message){
        String fName="[menuAPICallsListener_Somebody]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        logger.warn(fName+"value="+value);
                        llMessageDelete(message);
                        switch (value){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                            case lsUnicodeEmotes.aliasOne:
                                doAPI_Add(0);
                                break;
                            case lsUnicodeEmotes.aliasTwo:
                                doAPI_AddMaximum(0);
                                break;
                            case lsUnicodeEmotes.aliasThree:
                                doAPI_AddMaximumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasFour:
                                doAPI_AddMinimum(0);
                                break;
                            case lsUnicodeEmotes.aliasFive:
                                doAPI_AddMinimumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSix:
                                doAPI_AddRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSeven:
                                doAPI_AddRequirement(0);
                                break;
                            case lsUnicodeEmotes.aliasEight:
                                doAPI_AddRequirementRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolA:
                                doAPI_Sub(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolB:
                                doAPI_SubMaximum(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolC:
                                doAPI_SubMaximumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolD:
                                doAPI_SubMinimum(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolE:
                                doAPI_SubMinimumRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolF:
                                doAPI_SubRandom(0,0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolG:
                                doAPI_SubRequirement(0);
                                break;
                            case lsUnicodeEmotes.aliasSymbolH:
                                doAPI_SubRequirementRandom(0,0);
                                break;

                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        switch (id){
                            case lsUnicodeEmotes.aliasInformationSource:
                                help("main");
                                return;
                            case lsUnicodeEmotes.aliasArrowUp:
                                menuMain();
                                break;
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_button));
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                        logger.warn(fName+"asCodepoints="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                            help("main");
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                            menuMain();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    boolean printApiCalls=false;

    private void doAPI_Add(long milliseconds){
        String fName = "[doAPI_Add]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".milliseconds="+milliseconds);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(milliseconds<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                     message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                 e -> e.getAuthor().equals(gUser),
                 e -> {
                     try {
                         String content = e.getMessage().getContentStripped();
                         logger.info(fName+".content="+content);
                         llMessageDelete(message);
                         if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                             logger.info(fName+".canceled");
                         }else if(content.equalsIgnoreCase("!back")){
                             logger.info(fName+".backed");
                             if(isMenu)menuAPICalls();
                         }
                         else{
                             doAPI_Add(StringShortTerms2Timeset4Duration(content));
                         }
                     }catch (Exception e3){
                         logger.error(fName + ".exception=" + e3);
                         logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                         lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                         llMessageDelete(message);
                     }
                 },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                     llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                 });
                logger.info(fName+".input asking");
                return;
            }
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(milliseconds);
            String url=gUrlApiAdd;
            url=url.replaceAll("!VALUE", String.valueOf(time2Use)); 
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(milliseconds)+" duration to their session.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(milliseconds)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddMaximum(long milliseconds){
        String fName = "[doAPI_AddMaximum]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".milliseconds="+milliseconds);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(milliseconds<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration for maximum you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration for maximum you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddMaximum(StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(milliseconds);
            String url=gUrlApiAddMaximum;
            url=url.replaceAll("!VALUE", String.valueOf(time2Use)); 
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(milliseconds)+" duration to their session maximum.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(milliseconds)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session maximum.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddMaximumRandom(long millisecondsFrom, long millisecondsTo){
        String fName = "[doAPI_AddMaximumRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".millisecondsFrom="+millisecondsFrom+", millisecondsTo="+millisecondsTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(millisecondsFrom<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` for maximum random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` for maximum random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddMaximumRandom(StringShortTerms2Timeset4Duration(content),millisecondsTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(millisecondsTo<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` for maximum random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` for maximum random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddMaximumRandom(millisecondsFrom,StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2UseFrom= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsFrom);
            long time2UseTo= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsTo);
            String url=gUrlApiAddMaximumRandom;
            url=url.replaceAll("!FROM", String.valueOf(time2UseFrom)).replaceAll("!TO", String.valueOf(time2UseTo)); 
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added random duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" to their session maximum.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added random duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" to "+gUserProfile.gUser.getAsMention()+"'s session maximum.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddMinimum(long milliseconds){
        String fName = "[doAPI_AddMinimum]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".milliseconds="+milliseconds);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(milliseconds<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration minimum you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration minimum you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddMinimum(StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(milliseconds);
            String url=gUrlApiAddMinimum;
            url=url.replaceAll("!VALUE", String.valueOf(time2Use));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(milliseconds)+" duration to their session minimum.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(milliseconds)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session minimum.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddMinimumRandom(long millisecondsFrom, long millisecondsTo){
        String fName = "[doAPI_AddMinimumRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".millisecondsFrom="+millisecondsFrom+", millisecondsTo="+millisecondsTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(millisecondsFrom<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` for minimum random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` for minimum random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddMinimumRandom(StringShortTerms2Timeset4Duration(content),millisecondsTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(millisecondsTo<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` for minimum random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` for minimum random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddMinimumRandom(millisecondsFrom,StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2UseFrom= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsFrom);
            long time2UseTo= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsTo);
            String url=gUrlApiAddMinimumRandom;
            url=url.replaceAll("!FROM", String.valueOf(time2UseFrom)).replaceAll("!TO", String.valueOf(time2UseTo));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added random duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" to their session minimum.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added random duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" to "+gUserProfile.gUser.getAsMention()+"'s session minimum.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddRandom(long millisecondsFrom, long millisecondsTo){
        String fName = "[doAPI_AddRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".millisecondsFrom="+millisecondsFrom+", millisecondsTo="+millisecondsTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(millisecondsFrom<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` for minimum random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` for minimum random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddRandom(StringShortTerms2Timeset4Duration(content),millisecondsTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(millisecondsTo<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` for minimum random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` for minimum random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddRandom(millisecondsFrom,StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2UseFrom= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsFrom);
            long time2UseTo= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsTo);
            String url=gUrlApiAddRandom;
            url=url.replaceAll("!FROM", String.valueOf(time2UseFrom)).replaceAll("!TO", String.valueOf(time2UseTo));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added random duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" to their session.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added random duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" to "+gUserProfile.gUser.getAsMention()+"'s session.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddRequirement(int count){
        String fName = "[doAPI_AddRequirement]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".count="+count);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(count<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) requirement you want to add for yourself." +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) requirement you want to add for "+gUserProfile.gUser.getAsMention()+"."+strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddRequirement(String2Int(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }

            String url=gUrlApiAddRequirement;
            url=url.replaceAll("!VALUE", String.valueOf(count));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added value "+count+" for their session requirements.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added value "+count+" for "+gUserProfile.gUser.getAsMention()+"'s session requirements.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_AddRequirementRandom(int countFrom, int countTo){
        String fName = "[doAPI_AddRequirementRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".countFrom="+countFrom+", countTo="+countTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(countFrom<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) `from` for requirement random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) `from` for requirement random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddRequirementRandom(String2Int(content),countTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(countTo<=0){
                Message message;
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) `to` for requirement random you want to add for yourself."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }else{
                    message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) `to` for requirement random you want to add for "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                }
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_AddRequirementRandom(countFrom,String2Int(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }

            String url=gUrlApiAddRequirementRandom;
            url=url.replaceAll("!FROM", String.valueOf(countFrom)).replaceAll("!TO", String.valueOf(countTo));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                    embed.setDescription(gMember.getAsMention()+" added random value between "+countFrom+"-"+countTo+" for their session requirements.");
                }else{
                    embed.setDescription(gMember.getAsMention()+" added random value between "+countFrom+"-"+countTo+" for "+gUserProfile.gUser.getAsMention()+"'s session requirements.");
                }
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_Sub(long milliseconds){
        String fName = "[doAPI_Sub]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".milliseconds="+milliseconds);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(milliseconds<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration you want to subtract from "+gUserProfile.gUser.getAsMention()+"."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_Sub(StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(milliseconds);
            String url=gUrlApiSub;
            url=url.replaceAll("!VALUE", String.valueOf(time2Use)); 
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, only holders can subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(milliseconds)+" duration from "+gUserProfile.gUser.getAsMention()+"'s session.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubMaximum(long milliseconds){
        String fName = "[doAPI_SubMaximum]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".milliseconds="+milliseconds);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(milliseconds<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration you want to subtract from "+gUserProfile.gUser.getAsMention()+"'s maximum."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubMaximum(StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(milliseconds);
            String url=gUrlApiSubMaximum;
            url=url.replaceAll("!VALUE", String.valueOf(time2Use)); 
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(milliseconds)+" duration from "+gUserProfile.gUser.getAsMention()+"'s maximum.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubMaximumRandom(long millisecondsFrom, long millisecondsTo){
        String fName = "[doAPI_SubMaximumRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".millisecondsFrom="+millisecondsFrom+", millisecondsTo="+millisecondsTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(millisecondsFrom<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` you want to subtract from "+gUserProfile.gUser.getAsMention()+" maximum."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubMaximumRandom(StringShortTerms2Timeset4Duration(content),millisecondsTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(millisecondsTo<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` you want to subtract from "+gUserProfile.gUser.getAsMention()+" maximum."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubMaximumRandom(millisecondsFrom,StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2UseFrom= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsFrom);
            long time2UseTo= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsTo);
            String url=gUrlApiSubMaximumRandom;
            url=url.replaceAll("!FROM", String.valueOf(time2UseFrom)).replaceAll("!TO", String.valueOf(time2UseTo)); 
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" from "+gUserProfile.gUser.getAsMention()+"'s maximum.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubMinimum(long milliseconds){
        String fName = "[doAPI_SubMinimum]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".milliseconds="+milliseconds);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(milliseconds<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration you want to subtract from "+gUserProfile.gUser.getAsMention()+"'s minimum."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubMinimum(StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(milliseconds);
            String url=gUrlApiSubMinimum;
            url=url.replaceAll("!VALUE", String.valueOf(time2Use));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(milliseconds)+" duration from "+gUserProfile.gUser.getAsMention()+"'s minimum.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubMinimumRandom(long millisecondsFrom, long millisecondsTo){
        String fName = "[doAPI_SubMinimumRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".millisecondsFrom="+millisecondsFrom+", millisecondsTo="+millisecondsTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(millisecondsFrom<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` you want to subtract from "+gUserProfile.gUser.getAsMention()+" minimum."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubMinimumRandom(StringShortTerms2Timeset4Duration(content),millisecondsTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(millisecondsTo<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` you want to subtract from "+gUserProfile.gUser.getAsMention()+" minimum."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubMinimumRandom(millisecondsFrom,StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2UseFrom= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsFrom);
            long time2UseTo= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsTo);
            String url=gUrlApiSubMinimumRandom;
            url=url.replaceAll("!FROM", String.valueOf(time2UseFrom)).replaceAll("!TO", String.valueOf(time2UseTo));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" from "+gUserProfile.gUser.getAsMention()+"'s minimum.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubRandom(long millisecondsFrom, long millisecondsTo){
        String fName = "[doAPI_SubRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".millisecondsFrom="+millisecondsFrom+", millisecondsTo="+millisecondsTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(millisecondsFrom<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `from` you want to subtract from "+gUserProfile.gUser.getAsMention()+" session."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubRandom(StringShortTerms2Timeset4Duration(content),millisecondsTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(millisecondsTo<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the duration `to` you want to subtract from "+gUserProfile.gUser.getAsMention()+" session."+ strInputDurationFormate4ApiMenu +strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubRandom(millisecondsFrom,StringShortTerms2Timeset4Duration(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            long time2UseFrom= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsFrom);
            long time2UseTo= lsNumbersUsefullFunctions.milliseconds2seconds(millisecondsTo);
            String url=gUrlApiSubRandom;
            url=url.replaceAll("!FROM", String.valueOf(time2UseFrom)).replaceAll("!TO", String.valueOf(time2UseTo));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted duration between "+lsUsefullFunctions.displayDuration(millisecondsFrom)+"-"+lsUsefullFunctions.displayDuration(millisecondsTo)+" from "+gUserProfile.gUser.getAsMention()+"'s session.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubRequirement(int count){
        String fName = "[doAPI_SubRequirement]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".count="+count);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(count<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) you want to subtract from "+gUserProfile.gUser.getAsMention()+" requirements."+ strInputMessageGoBack, llColorBlue1);
                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubRequirement(String2Int(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }

            String url=gUrlApiSubRequirement;
            url=url.replaceAll("!VALUE", String.valueOf(count));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted "+count+" value from "+gUserProfile.gUser.getAsMention()+"'s requirements.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void doAPI_SubRequirementRandom(int countFrom, int countTo){
        String fName = "[doAPI_SubRequirementRandom]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".countFrom="+countFrom+", countTo="+countTo);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no user id.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                embed.setDescription(gUserProfile.gUser.getAsMention()+" provided no api key.");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" session info");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(gUserProfile.gUser.getIdLong()!=gMember.getIdLong()){
                logger.info(fName+".different user is requesting this command, check if holder");
                if(!isWearersHolder()&&!gIsOverride){
                    logger.info(fName+".not wearer holder");
                    embed.setDescription("Failed to send command to "+gUserProfile.gUser.getAsMention()+" session as you're not their holder!");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
            }
            if(!WEARER.session.isOwned()){
                logger.warn(fName + ".has no holder");
                embed.setDescription(gUserProfile.gUser.getAsMention()+"'s has no holder, holder is required in order to subtract!");
                lsMessageHelper.lsSendMessage(gUser,embed);
                return;
            }
            if(WEARER.session.isOwned()){
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                    logger.warn(fName + ".no user id provided");
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                    embed.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                    lsMessageHelper.lsSendMessage(gUser,embed);
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        embed.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+"'s holder");
                        lsMessageHelper.lsSendMessage(gUser,embed);
                        return;
                    }
                }
            }
            if(countFrom<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) `from` you want to subtract from "+gUserProfile.gUser.getAsMention()+" requirements."+ strInputMessageGoBack, llColorBlue1);

                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubRequirementRandom(String2Int(content),countTo);
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            if(countTo<=0){
                Message message;
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Please enter the value(int) `to` you want to subtract from "+gUserProfile.gUser.getAsMention()+" requirements."+ strInputMessageGoBack, llColorBlue1);

                gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                        e -> e.getAuthor().equals(gUser),
                        e -> {
                            try {
                                String content = e.getMessage().getContentStripped();
                                logger.info(fName+".content="+content);
                                llMessageDelete(message);
                                if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                    logger.info(fName+".canceled");
                                }else if(content.equalsIgnoreCase("!back")){
                                    logger.info(fName+".backed");
                                    if(isMenu)menuAPICalls();
                                }
                                else{
                                    doAPI_SubRequirementRandom(countFrom,String2Int(content));
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                            llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(message);
                        });
                logger.info(fName+".input asking");
                return;
            }
            String url=gUrlApiSubRequirementRandom;
            url=url.replaceAll("!FROM", String.valueOf(countFrom)).replaceAll("!TO", String.valueOf(countTo));
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            if(WEARER.session.isOwned()){
                url=url.replaceAll("!HOLDERAPIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            }else{
                url=url.replaceAll("!HOLDERAPIKEY",WEARER.profile.getApi());
            }
            logger.info(fName+"url="+url);
           
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription(gMember.getAsMention()+" subtracted value between "+countFrom+"-"+countTo+" from "+gUserProfile.gUser.getAsMention()+"'s requirements.");
                embed.setTitle(gTitle);embed.setColor(llColorGreen_Mint);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }
            if(isMenu)menuAPICalls();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gUser, gTitle,"Failed to set duration!", llColorRed); logger.error(fName+"exception="+ex);
        }
    }
    private void menuRdChastity(){
        String fName="[menuRdChastity]";
        logger.info(fName);
        try{
            getProfile();
            getMainEntries();
            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                menuRdChastity_Wearer();
            }else{
                if(isWearersHolder()||gIsOverride){
                    menuRdChastity_Holder();
                }else{
                    if(iRestraints.lsHasUserOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)||iRestraints.lsHasUserSecOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)){
                        menuRdChastity_RDOwner();
                    }else{
                        menuRdChastity_Somebody();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdChastity_Wearer(){
        String fName="[menuRdChastity_Weare]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDCHASTITY=new JSONObject(),PUNISH=new JSONObject(),REWARD=new JSONObject();
            boolean isEnabled=false,isPunishEnabled=false,isRewardEnabled=false;
            long punishDuration=0L, rewardDuration=0L;
            try {
                desc="";
                RDCHASTITY=WEARER.profile.getJSONObject(keyRDChastity);
                isEnabled=RDCHASTITY.getBoolean(fieldEnable);
                PUNISH=RDCHASTITY.getJSONObject(keyPunish);
                REWARD=RDCHASTITY.getJSONObject(keyReward);
                isPunishEnabled=PUNISH.getBoolean(fieldEnable);
                isRewardEnabled=REWARD.getBoolean(fieldEnable);
                punishDuration=PUNISH.getLong(fieldDuration);
                rewardDuration=REWARD.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\n**Punish**";
                desc+="\n•  enabled: "+isPunishEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(punishDuration);
                desc+="\n**Reward**";
                desc+="\n•  enabled: "+isRewardEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(rewardDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(WEARER.session.isOwned()&&isWearerHolderRegistered()){
                desc+="\nSorry only your holder is allowed to chaneg the settings!";
            }
            if(isEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" disable";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" enable";
            }
            if(isPunishEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" disable punish";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" enable punish";
            }
            if(isRewardEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" disable reward (only holder)";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" enable reward  (only holder)";
            }
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Update punish";
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" Update reward  (only holder)";
            embed.addField(" ", "Options :"+desc, false);
            desc="";
            if(gNewUserBDSMProfile.cCHASTITY.isOn()&&gNewUserBDSMProfile.cCHASTITY.isShockEnabled()){
                desc+= strChastityOnShockOn;
            }
            else if(gNewUserBDSMProfile.cCHASTITY.isOn()){
                desc+= strChastityOnShockOff;
            }else{
                desc+=strChastityOff;
            }
            desc+=strChastityRdCommand;

            if(gNewUserBDSMProfile.cCOLLAR.isOn()&&gNewUserBDSMProfile.cCOLLAR.isShockeEnabled()){
                desc+=strCollarOnShockOn;
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyBadWords()){
                    desc+=strCollarNoBadwords;
                }
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyEnforcedWords()){
                    desc+=strCollarNoEnforcedWords;
                }
            }
            else if(gNewUserBDSMProfile.cCOLLAR.isOn()){
                desc+=strCollarOnShockOff;
            }else{
                desc+=strCollarOff;
            }
            desc+=strCollarRdCommand;
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDChastity,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));

            if(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered()){
                if(isEnabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            }
            if(gIsOverride){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsPunishEnabled = isPunishEnabled;
            boolean finalIsRewardEnabled = isRewardEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                dmInputRdChastityDuration(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                setRdChastityEnable(0,false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                setRdChastityEnable(0,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                setRdChastityEnable(1,!finalIsPunishEnabled);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))&&gIsOverride) {
                                dmInputRdChastityDuration(2);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))&&gIsOverride) {
                                setRdChastityEnable(2,!finalIsRewardEnabled);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                                doRdChastity(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                                doRdChastity(2);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdChastity_Holder(){
        String fName="[menuRdChastity_Holder]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDCHASTITY=new JSONObject(),PUNISH=new JSONObject(),REWARD=new JSONObject();
            boolean isEnabled=false,isPunishEnabled=false,isRewardEnabled=false;
            long punishDuration=0L, rewardDuration=0L;
            try {
                desc="";
                RDCHASTITY=WEARER.profile.getJSONObject(keyRDChastity);
                isEnabled=RDCHASTITY.getBoolean(fieldEnable);
                PUNISH=RDCHASTITY.getJSONObject(keyPunish);
                REWARD=RDCHASTITY.getJSONObject(keyReward);
                isPunishEnabled=PUNISH.getBoolean(fieldEnable);
                isRewardEnabled=REWARD.getBoolean(fieldEnable);
                punishDuration=PUNISH.getLong(fieldDuration);
                rewardDuration=REWARD.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\n**Punish**";
                desc+="\n•  enabled: "+isPunishEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(punishDuration);
                desc+="\n**Reward**";
                desc+="\n•  enabled: "+isRewardEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(rewardDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(isEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" disable";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" enable";
            }
            if(isPunishEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" disable punish";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" enable punish";
            }
            if(isRewardEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" disable reward";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" enable reward";
            }
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Update punish";
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" Update reward";
            embed.addField(" ", "Options :"+desc, false);
            desc="";
            if(gNewUserBDSMProfile.cCHASTITY.isOn()&&gNewUserBDSMProfile.cCHASTITY.isShockEnabled()){
                desc+= strChastityOnShockOn;
            }
            else if(gNewUserBDSMProfile.cCHASTITY.isOn()){
                desc+= strChastityOnShockOff;
            }else{
                desc+=strChastityOff;
            }
            desc+=strChastityRdCommand;

            if(gNewUserBDSMProfile.cCOLLAR.isOn()&&gNewUserBDSMProfile.cCOLLAR.isShockeEnabled()){
                desc+=strCollarOnShockOn;
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyBadWords()){
                    desc+=strCollarNoBadwords;
                }
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyEnforcedWords()){
                    desc+=strCollarNoEnforcedWords;
                }
            }
            else if(gNewUserBDSMProfile.cCOLLAR.isOn()){
                desc+=strCollarOnShockOff;
            }else{
                desc+=strCollarOff;
            }
            desc+=strCollarRdCommand;
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDChastity,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(isEnabled){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsPunishEnabled = isPunishEnabled;
            boolean finalIsRewardEnabled = isRewardEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                dmInputRdChastityDuration(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                dmInputRdChastityDuration(2);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setRdChastityEnable(0,false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setRdChastityEnable(0,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))) {
                                setRdChastityEnable(1,!finalIsPunishEnabled);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))) {
                                setRdChastityEnable(2,!finalIsRewardEnabled);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                                doRdChastity(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                                doRdChastity(2);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });


        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdChastity_RDOwner(){
        String fName="[menuRdChastity_RDOwner]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDCHASTITY=new JSONObject(),PUNISH=new JSONObject(),REWARD=new JSONObject();
            boolean isEnabled=false,isPunishEnabled=false,isRewardEnabled=false;
            long punishDuration=0L, rewardDuration=0L;
            try {
                desc="";
                RDCHASTITY=WEARER.profile.getJSONObject(keyRDChastity);
                isEnabled=RDCHASTITY.getBoolean(fieldEnable);
                PUNISH=RDCHASTITY.getJSONObject(keyPunish);
                REWARD=RDCHASTITY.getJSONObject(keyReward);
                isPunishEnabled=PUNISH.getBoolean(fieldEnable);
                isRewardEnabled=REWARD.getBoolean(fieldEnable);
                punishDuration=PUNISH.getLong(fieldDuration);
                rewardDuration=REWARD.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\n**Punish**";
                desc+="\n•  enabled: "+isPunishEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(punishDuration);
                desc+="\n**Reward**";
                desc+="\n•  enabled: "+isRewardEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(rewardDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(isEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" disable";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" enable";
            }
            if(isPunishEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" disable punish";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" enable punish";
            }
            if(isRewardEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" disable reward";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" enable reward";
            }
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne)+" Update punish";
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo)+" Update reward";
            embed.addField(" ", "Options :"+desc, false);
            desc="";
            if(gNewUserBDSMProfile.cCHASTITY.isOn()&&gNewUserBDSMProfile.cCHASTITY.isShockEnabled()){
                desc+= strChastityOnShockOn;
            }
            else if(gNewUserBDSMProfile.cCHASTITY.isOn()){
                desc+= strChastityOnShockOff;
            }else{
                desc+=strChastityOff;
            }
            desc+=strChastityRdCommand;

            if(gNewUserBDSMProfile.cCOLLAR.isOn()&&gNewUserBDSMProfile.cCOLLAR.isShockeEnabled()){
                desc+=strCollarOnShockOn;
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyBadWords()){
                    desc+=strCollarNoBadwords;
                }
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyEnforcedWords()){
                    desc+=strCollarNoEnforcedWords;
                }
            }
            else if(gNewUserBDSMProfile.cCOLLAR.isOn()){
                desc+=strCollarOnShockOff;
            }else{
                desc+=strCollarOff;
            }
            desc+=strCollarRdCommand;
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDChastity,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(isEnabled){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsPunishEnabled = isPunishEnabled;
            boolean finalIsRewardEnabled = isRewardEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))) {
                                dmInputRdChastityDuration(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))) {
                                dmInputRdChastityDuration(2);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setRdChastityEnable(0,false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setRdChastityEnable(0,true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))) {
                                setRdChastityEnable(1,!finalIsPunishEnabled);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolR))) {
                                setRdChastityEnable(2,!finalIsRewardEnabled);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                                doRdChastity(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))) {
                                doRdChastity(2);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });


        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void setRdChastityEnable(int type,boolean value){
        String fName = "[setRdChastityEnable]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".type="+type+", value=" +value);
            switch (type){
                case 1: gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyPunish).put(fieldEnable,value);
                    break;
                case 2: gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyReward).put(fieldEnable,value);
                    break;
                case 0: gUserProfile.jsonObject.getJSONObject(keyRDChastity).put(fieldEnable,value);
                    if(value){
                        llSendQuickEmbedMessage(gUser, gTitle, "You enabled the fictional chastity punish/reward, be carefull.", llColorBlue1);
                    }else{
                        llSendQuickEmbedMessage(gUser, gTitle, "You disabed the fictional chastity punish/reward.", llColorBlue1);
                    }
                    break;
            }
            saveProfile();
            if(isMenu)menuRdChastity();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,ex.toString());
        }
    }
    private void dmInputRdChastityDuration(int type){
        String fName="[dmInputRdChastityDuration]";
        try{
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName+"type="+type);
            long value=0;
            switch (type){
                case 1: value= gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyPunish).getLong(fieldDuration);
                    break;
                case 2: value= gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyReward).getLong(fieldDuration);
                    break;
            }
            Message message = null;
            if(value!=0){
                switch (type){
                    case 1:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current punishment time "+lsUsefullFunctions.displayDuration(value)+".\nPlease enter a new value for punishment duration."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                    case 2:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current reward time "+lsUsefullFunctions.displayDuration(value)+".\nPlease enter a new value for reward duration."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                }

            }else{
                switch (type){
                    case 1:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an value for punishment duration."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                    case 2:  message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an value for reward duration."+strInputDurationFormate4ApiMenu, llColorBlue1);
                        break;
                }

            }
            Message finalMessage = message;
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                if(isMenu)menuRdChastity();
                                return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                switch (type){
                                    case 1: gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyPunish).put(fieldDuration,0L);
                                        break;
                                    case 2: gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyReward).put(fieldDuration,0L);
                                        break;
                                }
                                saveProfile();
                                if(isMenu)menuRdChastity();
                            }
                            else{
                                if(setRdChastityDuration(type,content)){
                                    saveProfile();
                                    if(isMenu)menuRdChastity();
                                }

                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(finalMessage);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(finalMessage);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private boolean setRdChastityDuration(int type,String message){
        String fName = "[setRdChastityDuration]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            logger.info(fName + ".type="+type+", message=" + message);
            long timeset = StringShortTerms2Timeset4Duration(message);
            logger.info(fName + ".timeset=" + timeset);

           if (timeset < milliseconds_minute*15) {
               llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be minimum 15 minutes.", llColorRed);
               return false;
           }
           if (timeset >milliseconds_hour*24) {
               llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be bellow 24 hours.", llColorRed);
               return false;
           }

            switch (type){
                case 1: gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyPunish).put(fieldDuration,timeset);
                    llSendQuickEmbedMessage(gTextChannel, gTitle, "Chastity punishment for "+gUserProfile.gUser.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                    break;
                case 2: gUserProfile.jsonObject.getJSONObject(keyRDChastity).getJSONObject(keyReward).put(fieldDuration,timeset);
                    llSendQuickEmbedMessage(gTextChannel, gTitle, "Chastity reward for "+gUserProfile.gUser.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
                    break;
            }
            return true;
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            return  false;
        }
    }
    private void doRdChastity(int type){
        String fName = "[doRdChastity]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".type="+type);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            boolean isEnabled=false,isEnabledOption=false;
            JSONObject RDCHASTITY=gUserProfile.jsonObject.getJSONObject(keyRDChastity);
            logger.info(fName + "RDCHASTITY="+RDCHASTITY.toString());
            isEnabled=RDCHASTITY.getBoolean(fieldEnable);
            switch (type){
                case 1:
                    isEnabledOption=RDCHASTITY.getJSONObject(keyPunish).getBoolean(fieldEnable);
                    break;
                case 2:
                    isEnabledOption=RDCHASTITY.getJSONObject(keyReward).getBoolean(fieldEnable);
                    break;
            }
            logger.info(fName + ".isEnabled="+isEnabled+", isEnabledOption="+isEnabledOption);
            if(!isEnabled){
                logger.info(fName + "not enabled");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("Not enabled chastity punish/reward!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            if(!isEnabledOption){
                logger.info(fName + "not enabledoption");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("Not enabled chastity punish/reward!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }

            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("No wearer id porvided!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                logger.warn(fName + ".no user api key provided");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("No wearer api key provided!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    logger.warn(fName + ".failed to get wearer info");
                    if(!gIsForward){
                        embed.setTitle(gTitle);
                        embed.setDescription("Failed to get wearer information!");
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    }
                    return;
                }
            }
            long timeUpdater=0L;
            String url="";
            switch (type){
                case 1:
                    timeUpdater=RDCHASTITY.getJSONObject(keyPunish).getLong(fieldDuration);
                    url=gUrlApiAdd;
                    break;
                case 2:
                    timeUpdater=RDCHASTITY.getJSONObject(keyReward).getLong(fieldDuration);
                    url=gUrlApiSub;
                    break;
            }
            logger.info(fName+".timeUpdater="+timeUpdater);
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(timeUpdater);
            if(time2Use<=0){
                logger.warn(fName + ".time2Use is <=0");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("Duration is too smaller or equal to 0!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            url=url.replaceAll("!VALUE", String.valueOf(time2Use));
            if(type==2){
                if(!WEARER.session.hasKeyHolder()){
                    logger.warn(fName + ".No holder id found");
                    if(!gIsForward){
                        embed.setTitle(gTitle);
                        embed.setDescription("No holder id found!");
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    }
                    return;
                }
                if(WEARER.holder.isEmpty()){
                    if(!getHolderInfo(WEARER.session.getHolderId())){
                        logger.warn(fName + ".Failed to get holder info");
                        if(!gIsForward){
                            embed.setTitle(gTitle);
                            embed.setDescription("Failed to get holder!");
                            lsMessageHelper.lsSendMessage(gTextChannel,embed);
                        }
                        return;
                    }
                }

                if(!HOLDERSqlEntry.has(sqlKeyEmUserId)||HOLDERSqlEntry.getString(sqlKeyEmUserId).isBlank()){
                    logger.warn(fName + ".no holder user id provided");
                    if(!gIsForward){
                        embed.setTitle(gTitle);
                        embed.setDescription("Failed no holder id provided!");
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    }
                    return;
                }
                if(!HOLDERSqlEntry.has(sqlKeyApi)||HOLDERSqlEntry.getString(sqlKeyApi).isBlank()){
                    logger.warn(fName + ".no holder api provided");
                    if(!gIsForward){
                        embed.setTitle(gTitle);
                        embed.setDescription("Failed no holder api provided!");
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    }
                    return;
                }
                url=url.replaceAll("!HOLDERAPIKEY",HOLDERSqlEntry.getString(sqlKeyApi));
            }
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                switch (type){
                    case 1:
                        embed.setDescription(""+lsUsefullFunctions.displayDuration(timeUpdater)+" duration is added to "+gUserProfile.gUser.getAsMention()+"'s session as punishment.");
                        embed.setColor(llColorRed_Cinnabar);
                        break;
                    case 2:
                        embed.setDescription(""+lsUsefullFunctions.displayDuration(timeUpdater)+" duration is subtracted from "+gUserProfile.gUser.getAsMention()+"'s session as reward.");
                        embed.setColor(llColorGreen_Mint);
                        break;

                }
                embed.setTitle(gTitle);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }

        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
        }
    }
    private void menuRdChastity_Somebody(){
        String fName="[menuRdChastity_Somebody]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDCHASTITY=new JSONObject(),PUNISH=new JSONObject(),REWARD=new JSONObject();
            boolean isEnabled=false,isPunishEnabled=false,isRewardEnabled=false;
            long punishDuration=0L, rewardDuration=0L;
            try {
                desc="";
                RDCHASTITY=WEARER.profile.getJSONObject(keyRDChastity);
                isEnabled=RDCHASTITY.getBoolean(fieldEnable);
                PUNISH=RDCHASTITY.getJSONObject(keyPunish);
                REWARD=RDCHASTITY.getJSONObject(keyReward);
                isPunishEnabled=PUNISH.getBoolean(fieldEnable);
                isRewardEnabled=REWARD.getBoolean(fieldEnable);
                punishDuration=PUNISH.getLong(fieldDuration);
                rewardDuration=REWARD.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\n**Punish**";
                desc+="\n•  enabled: "+isPunishEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(punishDuration);
                desc+="\n**Reward**";
                desc+="\n•  enabled: "+isRewardEnabled;
                desc+="\n•  duration: "+lsUsefullFunctions.displayDuration(rewardDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(gNewUserBDSMProfile.cCHASTITY.isOn()&&gNewUserBDSMProfile.cCHASTITY.isShockEnabled()){
                desc+= strChastityOnShockOn;
            }
            else if(gNewUserBDSMProfile.cCHASTITY.isOn()){
                desc+= strChastityOnShockOff;
            }else{
                desc+=strChastityOff;
            }
            desc+=strChastityRdCommand;

            if(gNewUserBDSMProfile.cCOLLAR.isOn()&&gNewUserBDSMProfile.cCOLLAR.isShockeEnabled()){
                desc+=strCollarOnShockOn;
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyBadWords()){
                    desc+=strCollarNoBadwords;
                }
                if(gNewUserBDSMProfile.cCOLLAR.isEmptyEnforcedWords()){
                    desc+=strCollarNoEnforcedWords;
                }
            }
            else if(gNewUserBDSMProfile.cCOLLAR.isOn()){
                desc+=strCollarOnShockOff;
            }else{
                desc+=strCollarOff;
            }
            desc+=strCollarRdCommand;
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDChastity,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsPunishEnabled = isPunishEnabled;
            boolean finalIsRewardEnabled = isRewardEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))) {
                                doRdChastity(1);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenApple))&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))) {
                                doRdChastity(2);
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdTimeout(){
        String fName="[menuRdTimeout]";
        logger.info(fName);
        try{
            getProfile();
            getMainEntries();
            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                menuRdTimeout_Wearer();
            }else{
                if(isWearersHolder()||gIsOverride){
                    menuRdTimeout_Holder();
                }else{
                    if(iRestraints.lsHasUserOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)||iRestraints.lsHasUserSecOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)){
                        menuRdTimeout_RDOwner();
                    }else{
                        menuRdTimeout_Somebody();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void setRdTimeoutEnable(boolean value){
        String fName = "[setRdTimeoutEnable]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".value=" +value);
            gUserProfile.jsonObject.getJSONObject(keyRDTimeout).put(fieldEnable,value);
            saveProfile();
            if(isMenu)menuRdTimeout();
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,ex.toString());
        }
    }
    private void dmInputRdTimeoutDuration(){
        String fName="[dmInputRdChastityDuration]";
        try{
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            long value=0;
            value= gUserProfile.jsonObject.getJSONObject(keyRDTimeout).getLong(fieldDuration);
            Message message = null;
            if(value!=0){
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"Current punishment time "+lsUsefullFunctions.displayDuration(value)+".\nPlease enter a new value for punishment duration."+strInputDurationFormate4ApiMenu, llColorBlue1);
            }else{
                message=llSendQuickEmbedMessageResponse(gUser,gTitle,"\nPlease enter an value for punishment duration."+strInputDurationFormate4ApiMenu, llColorBlue1);
            }
            Message finalMessage = message;
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                if(isMenu)menuRdTimeout();
                                return;
                            }else if(content.equalsIgnoreCase("!clear")){
                                gUserProfile.jsonObject.getJSONObject(keyRDTimeout).put(fieldDuration,0L);
                                saveProfile();
                                if(isMenu)menuRdChastity();
                            }
                            else{
                                if(setRdTimeoutDuration(content)){
                                    saveProfile();
                                    if(isMenu)menuRdTimeout();
                                }

                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(finalMessage);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llSendQuickEmbedMessage(gUser, gTitle, "Timeout", llColorRed);llMessageDelete(finalMessage);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private boolean setRdTimeoutDuration(String message){
        String fName = "[setRdChastityDuration]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return false;}
            logger.info(fName + ".message=" + message);
            long timeset = StringShortTerms2Timeset4Duration(message);
            logger.info(fName + ".timeset=" + timeset);

            if (timeset < milliseconds_minute*15) {
                llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be minimum 15 minutes.", llColorRed);
                return false;
            }
            if (timeset >milliseconds_hour*24) {
                llSendQuickEmbedMessage(gUser, gTitle, "Failed to set duration! Duration needs to be bellow 24 hours.", llColorRed);
                return false;
            }

            gUserProfile.jsonObject.getJSONObject(keyRDTimeout).put(fieldDuration,timeset);
            llSendQuickEmbedMessage(gTextChannel, gTitle, "Timeout punishment for "+gUserProfile.gUser.getAsMention()+" set to:"+lsUsefullFunctions.displayDuration(timeset), llColorPurple1);
            return true;
        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            return  false;
        }
    }
    private void doRdTimeout(){
        String fName = "[doRdTimeout]";
        try {
            if(!getProfile()){logger.error(fName + ".can't get profile"); return;}
            logger.info(fName + ".");
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            boolean isEnabled=false;
            JSONObject RDTIMEOUT=gUserProfile.jsonObject.getJSONObject(keyRDTimeout);
            logger.info(fName + "RDTIMEOUT="+RDTIMEOUT.toString());
            isEnabled=RDTIMEOUT.getBoolean(fieldEnable);
            logger.info(fName + ".isEnabled="+isEnabled);
            if(!isEnabled){
                logger.info(fName + "not enabled");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("Not enabled chastity punish/reward!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }


            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("No wearer id porvided!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                logger.warn(fName + ".no user api key provided");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("No wearer api key provided!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            if(WEARER.user.getJSON().isEmpty()){
                if(!getWearerInfo()){
                    logger.warn(fName + ".failed to get wearer info");
                    if(!gIsForward){
                        embed.setTitle(gTitle);
                        embed.setDescription("Failed to get wearer information!");
                        lsMessageHelper.lsSendMessage(gTextChannel,embed);
                    }
                    return;
                }
            }
            long timeUpdater=0L;
            String url="";
            timeUpdater=RDTIMEOUT.getLong(fieldDuration);
            url=gUrlApiAdd;
            logger.info(fName+".timeUpdater="+timeUpdater);
            long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(timeUpdater);
            if(time2Use<=0){
                logger.warn(fName + ".time2Use is <=0");
                if(!gIsForward){
                    embed.setTitle(gTitle);
                    embed.setDescription("Duration is too smaller or equal to 0!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embed);
                }
                return;
            }
            url=url.replaceAll("!VALUE", String.valueOf(time2Use));
            url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            if(printApiCalls){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            responseSessionUpdate =getApiEmlalock(url);
            logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
            logger.info(fName+".body ="+ responseSessionUpdate.getBody());
            if(responseSessionUpdate.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(responseSessionUpdate);
                loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                return;
            }else{
                embed.setDescription("Due to timout violation, extra "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration is added to "+gUserProfile.gUser.getAsMention()+" session.");
                embed.setTitle(gTitle);embed.setColor(llColorRed_Cinnabar);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
                loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
            }

        } catch(Exception ex){
            logger.error(".exception=" + ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
        }
    }
    private void menuRdTimeout_Wearer(){
        String fName="[menuRdTimeout_Wearer]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDTIMEOUT=new JSONObject();
            boolean isEnabled=false;
            long punishDuration=0L;
            try {
                desc="";
                RDTIMEOUT=WEARER.profile.getJSONObject(keyRDTimeout);
                isEnabled=RDTIMEOUT.getBoolean(fieldEnable);
                punishDuration=RDTIMEOUT.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\nDuration: "+lsUsefullFunctions.displayDuration(punishDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(WEARER.session.isOwned()&&isWearerHolderRegistered()){
                desc+="\nSorry only your holder is allowed to chaneg the settings!";
            }
            if(isEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" disable";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" enable";
            }
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" Update duration to add.";
            embed.addField(" ", "Options :"+desc, false);
            desc="";
            if(gNewUserBDSMProfile.cTIMEOUT.isOn()){
                desc+="\ntimeout: on";
            }else{
                desc+="\ntimeout: off";
            }
            desc+="\n`!>timeout` to change";
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDTimeout,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered()){
                if(isEnabled){
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
                }else{
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
                }
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsEnabled = isEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                setRdTimeoutEnable(false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                setRdTimeoutEnable(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))&&(gIsOverride||!WEARER.session.isOwned()||!isWearerHolderRegistered())) {
                                dmInputRdTimeoutDuration();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                                doRdTimeout();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });


        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdTimeout_Holder(){
        String fName="[menuRdTimeout_Holder]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDTIMEOUT=new JSONObject();
            boolean isEnabled=false;
            long punishDuration=0L;
            try {
                desc="";
                RDTIMEOUT=WEARER.profile.getJSONObject(keyRDTimeout);
                isEnabled=RDTIMEOUT.getBoolean(fieldEnable);
                punishDuration=RDTIMEOUT.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\nDuration: "+lsUsefullFunctions.displayDuration(punishDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(isEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" disable";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" enable";
            }
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" Update duration to add.";
            embed.addField(" ", "Options :"+desc, false);
            desc="";
            if(gNewUserBDSMProfile.cTIMEOUT.isOn()){
                desc+="\ntimeout: on";
            }else{
                desc+="\ntimeout: off";
            }
            desc+="\n`!>timeout` to change";
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDTimeout,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(isEnabled){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsEnabled = isEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setRdTimeoutEnable(false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setRdTimeoutEnable(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))) {
                                dmInputRdTimeoutDuration();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                                doRdTimeout();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });


        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdTimeout_RDOwner(){
        String fName="[menuRdTimeout_RDOwner]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDTIMEOUT=new JSONObject();
            boolean isEnabled=false;
            long punishDuration=0L;
            try {
                desc="";
                RDTIMEOUT=WEARER.profile.getJSONObject(keyRDTimeout);
                isEnabled=RDTIMEOUT.getBoolean(fieldEnable);
                punishDuration=RDTIMEOUT.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\nDuration: "+lsUsefullFunctions.displayDuration(punishDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(isEnabled){
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" disable";
            }else{
                desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" enable";
            }
            desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" Update duration to add.";
            embed.addField(" ", "Options :"+desc, false);
            desc="";
            if(gNewUserBDSMProfile.cTIMEOUT.isOn()){
                desc+="\ntimeout: on";
            }else{
                desc+="\ntimeout: off";
            }
            desc+="\n`!>timeout` to change";
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDTimeout,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            if(isEnabled){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsEnabled = isEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))) {
                                setRdTimeoutEnable(false);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))) {
                                setRdTimeoutEnable(true);
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))) {
                                dmInputRdTimeoutDuration();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))) {
                                doRdTimeout();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });


        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void menuRdTimeout_Somebody(){
        String fName="[menuRdTimeout_Somebody]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gUserProfile.gUser.getName()+"'s "+gTitle+" RD Chastity");
            desc=""; isMenu=true;menuIndex=0;
            JSONObject RDTIMEOUT=new JSONObject();
            boolean isEnabled=false;
            long punishDuration=0L;
            try {
                desc="";
                RDTIMEOUT=WEARER.profile.getJSONObject(keyRDTimeout);
                isEnabled=RDTIMEOUT.getBoolean(fieldEnable);
                punishDuration=RDTIMEOUT.getLong(fieldDuration);
                desc+="\nEnabled: "+isEnabled;
                desc+="\nDuration: "+lsUsefullFunctions.displayDuration(punishDuration);
                embed.addField("Status",desc,false);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                embed.addField("Status","!error!",false);
            }
            embed=setupNotification(embed);
            desc="";
            if(gNewUserBDSMProfile.cTIMEOUT.isOn()){
                desc+="\ntimeout: on";
            }else{
                desc+="\ntimeout: off";
            }
            desc+="\n`!>timeout` to change";
            embed.addField(strTitleRDStatus,desc, false);
            embed.addField(strTitleNote,strNoteRDTimeout,false);
            //embed.addField("Help","Select "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)+" for more commands & help",false);
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource));
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp));
            boolean finalIsEnabled = isEnabled;
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                help("main");
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                menuMain();
                            }
                            else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasApple))&&(llMemberIsAdministrator(gMember)||llMemberIsManager(gMember)||llMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))) {
                                doRdTimeout();
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                            llMessageDelete(message);
                        }
                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        llMessageDelete(message);
                    });


        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private long String2Timeset4Duration(String str){
        String fName = "[String2Timeset4Duration]";
        try{
            logger.info(fName+"str="+str);
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
    private long StringShortTerms2Timeset4Duration(String str){
        String fName = "[StringShort2Timeset4Duration]";
        try{
            logger.info(fName+"str="+str);
            long timeset = 0;
            str=str.toLowerCase();
            int i=0;
            String key= "",value="",tmp="";
            while(i<str.length()){
                try {
                    tmp=String.valueOf(str.charAt(i));
                    if(tmp.equals("w")||tmp.equals("d")||tmp.equals("h")||tmp.equals("m")){
                        if(!key.isBlank()&&!value.isBlank()){
                            if(key.equals("w")){
                                timeset+=milliseconds_week*Integer.parseInt(value);
                            }else
                            if(key.equals("d")){
                                timeset+=milliseconds_day*Integer.parseInt(value);
                            }else
                            if(key.equals("h")){
                                timeset+=milliseconds_hour*Integer.parseInt(value);
                            }else
                            if(key.equals("m")){
                                timeset+=milliseconds_minute*Integer.parseInt(value);
                            }
                        }
                        key=tmp;
                        value="";
                    }else
                    if(tmp.equals("0")||tmp.equals("1")||tmp.equals("2")||tmp.equals("3")||tmp.equals("4")||tmp.equals("5")
                        ||tmp.equals("6")||tmp.equals("7")||tmp.equals("8")||tmp.equals("9")){
                        if(!key.isBlank()){
                            value+=tmp;
                        }

                    }else{
                        if(!key.isBlank()&&!value.isBlank()){
                            if(key.equals("w")){
                                timeset+=milliseconds_week*Integer.parseInt(value);
                            }else
                            if(key.equals("d")){
                                timeset+=milliseconds_day*Integer.parseInt(value);
                            }else
                            if(key.equals("h")){
                                timeset+=milliseconds_hour*Integer.parseInt(value);
                            }else
                            if(key.equals("m")){
                                timeset+=milliseconds_minute*Integer.parseInt(value);
                            }
                        }
                        key="";value="";
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                i++;
            }
            if(!key.isBlank()&&!value.isBlank()){
                if(key.equals("w")){
                    timeset+=milliseconds_week*Integer.parseInt(value);
                }else
                if(key.equals("d")){
                    timeset+=milliseconds_day*Integer.parseInt(value);
                }else
                if(key.equals("h")){
                    timeset+=milliseconds_hour*Integer.parseInt(value);
                }else
                if(key.equals("m")){
                    timeset+=milliseconds_minute*Integer.parseInt(value);
                }
            }
            logger.info(fName + ".timeset=" +timeset);
            return  timeset;
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return 0;
        }
    }
    private int String2Int(String str){
        String fName = "[String2Int]";
        try{
            logger.info(fName+"str="+str);
            int result = 0;
            result=Integer.parseInt(str);
            logger.info(fName+"result="+result);
            return  result;
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return 0;
        }
    }
    JSONObject WEARERJSONRESPONSE =new JSONObject();
    JSONObject HOLDERSqlEntry=new JSONObject(), HOLDERSqlJSON =new JSONObject(), HOLDERJSONRESPONSE =new JSONObject();
    boolean gDebugSend=false;
    entityUserEmlalock WEARER=new entityUserEmlalock();
    private boolean getWearerInfo(){
        String fName="[getWearerInfo]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String url=gUrlApiInfo;
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                return false;
            }
            if(WEARER.profile.isEmptyApi()){
                logger.warn(fName + ".no api key provided");
                return false;
            }
            url=url.replaceAll("!USERID", WEARER.profile.getUserId()).replaceAll("!APIKEY", WEARER.profile.getApi());
            logger.info(fName+"url="+url);
            logger.info(fName + ".ready to send");
            HttpResponse<JsonNode> jsonResponse=getApiEmlalock(url);
            if(gDebugSend){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+ jsonResponse.getBody());
            if(jsonResponse.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(jsonResponse);
                return false;
            }
            WEARERJSONRESPONSE =jsonResponse.getBody().getObject();
            WEARER.setEmlalock4Wearer(WEARERJSONRESPONSE);
            logger.info(fName+".return true");
            return  true;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            return  false;
        }
    }
    private boolean getHolderInfo(String userid){
        String fName="[getHolderInfo]";
        try{
            logger.info(fName+"userid="+userid);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String url=gUrlApiInfo;
            if(userid==null||userid.isBlank()){
                logger.warn(fName + ".no user id provided");
                return false;
            }
            HOLDERSqlEntry=getUserSQLEntry(sqlKeyEmUserId,WEARER.session.getHolderId());
            logger.info(fName+".HOLDERSqlEntry ="+HOLDERSqlEntry.toString());
            if(HOLDERSqlEntry.has(keyjson)){
                HOLDERSqlJSON =HOLDERSqlEntry.getJSONObject(keyjson);
                logger.info(fName+".HOLDERJSON ="+ HOLDERSqlJSON.toString());
            }
            if(HOLDERSqlJSON ==null|| HOLDERSqlJSON.isEmpty()){
                logger.warn(fName + ".no json");
                return false;
            }
            if(!HOLDERSqlJSON.getJSONObject(keyUser).has(fieldUserId)|| HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId).isBlank()){
                logger.warn(fName + ".no user id provided");
                return false;
            }
            if(!HOLDERSqlJSON.getJSONObject(keyUser).has(fieldApiKey)|| HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey).isBlank()){
                logger.warn(fName + ".no api key provided");
                return false;
            }
            url=url.replaceAll("!USERID", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldUserId)).replaceAll("!APIKEY", HOLDERSqlJSON.getJSONObject(keyUser).getString(fieldApiKey));
            logger.info(fName+"url="+url);
            logger.info(fName + ".ready to send");
            HttpResponse<JsonNode> jsonResponse=getApiEmlalock(url);
            if(gDebugSend){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+ jsonResponse.getBody());
            if(jsonResponse.getStatus()!=200){
                logger.error(fName+".error status");
                getErrorJSON(jsonResponse);
                return false;
            }
            HOLDERJSONRESPONSE =jsonResponse.getBody().getObject();
            WEARER.setEmlalock4Holder(HOLDERJSONRESPONSE);
            return  true;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            return  false;
        }
    }
    private void checkInputValidity(){
        String fName="[checkInputValidity]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorRed_Barn);embed.setTitle("Emlalock User id&api key validity");
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=gUrlApiInfo;
            if(WEARER.profile.isEmptyUserId()){
                logger.warn(fName + ".no user id provided");
                return;
            }
            if(WEARER.profile.isEmptyApi()){
                logger.warn(fName + ".no api key provided");
                return;
            }
            if(!isApiValid(WEARER.profile.getUserId(),WEARER.profile.getApi())){
                if(responseApiValid!=null){
                    if(responseApiValid.getBody().getObject().has(keyerror)){
                        embed.setDescription(responseApiValid.getBody().getObject().getString(keyerror));
                    }else{
                        embed.setDescription("Failed to verify user id and api key!");
                    }
                }else{
                    embed.setDescription("Failed to verify user id and api key!");
                }
                lsMessageHelper.lsSendMessage(gUser,embed);
            }

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
        }
    }
    private EmbedBuilder setupNotification(EmbedBuilder embed){
        String fName="[setupNotification]";
        logger.info(fName);
        try{
            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                if(!WEARER.user.isValid()){
                    embed.addField("IMPORTANT","CAN'T RETREIVE USER INFO FROM EMLALOCK",false);
                }else
                if(!WEARER.session.isInSession()){
                    embed.addField("IMPORTANT","YOU'RE NOT IN ANY ACTIVE SESSION",false);
                }else
                if(!WEARER.session.isOwned()||!isWearerHolderRegistered()){
                    embed.addField("IMPORTANT","YOU HAVE NO HOLDER, SUBTRACTION IS IMPOSSIBLE",false);
                }
            }else{
                if(!WEARER.user.isValid()){
                    embed.addField("IMPORTANT","CAN'T RETREIVE USER INFO FROM EMLALOCK",false);
                }else
                if(!WEARER.session.isInSession()){
                    embed.addField("IMPORTANT","USER IS NOT IN ANY ACTIVE SESSION",false);
                }else
                if(!WEARER.session.isOwned()||!isWearerHolderRegistered()){
                    embed.addField("IMPORTANT","WEARER HAS NO HOLDER, SUBTRACTION IS IMPOSSIBLE",false);
                }
            }
            return  embed;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  embed;
        }
    }

    private boolean isWearerHolderRegistered(){
        String fName="[isWearerHolderRegistered]";
        logger.info(fName);
        try{
            if(!WEARER.session.isOwned()) {
                logger.info(fName + "not owned>false");
                return  false;
            }
            if(WEARER.holder.isEmpty()){
                logger.info(fName + ".HOLDERINFO_User.isEmpty>false");
                return  false;
                /*if(!getHolderInfo(WEARER.session.getKeyHolderId())){
                    logger.info(fName + ".failed to getholder info>false");
                    return  false;
                }*/
            }
            logger.info(fName + ".got holder info>true");
            return  true;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            return false;
        }
    }
    private boolean isWearersHolder(){
        String fName="[isWearersHolder]";
        logger.info(fName);
        try{
            if(!WEARER.session.hasKeyHolder()) {
                logger.info(fName + ".has no holder id>false");
                return  false;
            }
            if(WEARER.session.getHolderId().isBlank()) {
                logger.info(fName + ".holder id is blank>false");
                return  false;
            }
            if(WEARER.session.getHolderId().equalsIgnoreCase(WEARER.user.getUserId())&&gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                logger.info(fName + ".same id&member>false");
                return  false;
            }
            if(WEARER.holder.isEmpty()){
                logger.info(fName + ".HOLDERINFO_User.isEmpty>false");
                return  false;
            }
            String holderId=HOLDERSqlEntry.getString(sqlKeyUserID);
            if(holderId.equalsIgnoreCase(gMember.getId())){
                logger.info(fName + ".id match>true");
                return  true;
            }
            logger.info(fName + ".default>false");
            return  false;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            return false;
        }
    }

    HttpResponse<JsonNode> responseApiValid;
    private boolean isApiValid(String userid,String apikey){
        String fName="[isApiValid]";
        try{
            logger.info(fName+"userid="+userid+", apikey="+apikey);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=gUrlApiInfo;
            if(userid==null||userid.isBlank()){
                logger.warn(fName + ".no user id provided");
                return false;
            }
            if(apikey==null||apikey.isBlank()){
                logger.warn(fName + ".no api key provided");
                return false;
            }
            url=url.replaceAll("!USERID",userid).replaceAll("!APIKEY",apikey);
            logger.info(fName+"url="+url);
            logger.info(fName + ".ready to send");
            responseApiValid=getApiEmlalock(url);
            if(gDebugSend){
                logger.info(fName + ".debug");
                embed.setTitle(gTitle+"(debug)");
                embed.setDescription("url="+url);
                lsMessageHelper.lsSendMessage(gTextChannel,embed);
            }
            logger.info(fName+".status ="+responseApiValid.getStatus());
            logger.info(fName+".body ="+ responseApiValid.getBody());
            if(responseApiValid.getStatus()!=200){
                logger.error(fName+".error status");
                //getErrorJSON(jsonResponse);
                return false;
            }
            if(!responseApiValid.getBody().getObject().has("user")){
                logger.info(fName+".json has no key user");
                return false;
            }
            return  true;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            return  false;
        }
    }
    private HttpResponse<JsonNode> getApiEmlalock(String url){
        String fName="[getApiEmlalock]";
        try{
            logger.info(fName+"url="+url);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            logger.info(fName + ".ready to send");
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            logger.info(fName+".body ="+ jsonResponse.getBody());
            return jsonResponse;
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            return new FailedResponse<JsonNode>(e);
        }
    }

    private Boolean getProfile(){
        String fName="[getProfile]";
        logger.info(fName);
        if(gTarget==null||gTarget.getIdLong()==gMember.getIdLong()){
            return getProfile(gMember);
        }else{
            return getProfile(gTarget);
        }
    }
    private Boolean getProfile(Member member){
        String fName="[getProfile]";
        logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
        if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.gMember.getIdLong()==member.getIdLong()){
            logger.info(fName + ".already present>skip");
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
            WEARER.setProfile(gUserProfile.jsonObject);
            return true;
        }
        gUserProfile=gGlobal.getUserProfileSql(profileName,member);
        if(gUserProfile!=null&&gUserProfile.isProfile()){
            logger.info(fName + ".is locally cached");
        }else{
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONandSqlUserProfile(gGlobal,member,profileName);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");
            }
        }
        gUserProfile= iChastityEmlalock.sUserInit(gUserProfile);
        gGlobal.putUserProfile(gUserProfile,profileName);
        WEARER.setProfile(gUserProfile.jsonObject);
        if(!gUserProfile.isUpdated){
            logger.info(fName + ".no update>ignore");
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
            return true;
        }
        if(!saveProfile()){ logger.error(fName+".failed to write in Db");
            llSendQuickEmbedMessageWithDelete(gGlobal,true,gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
        try {
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonSQL.toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        return true;
    }
    private Boolean saveProfile(){
        String fName="[saveProfile]";
        logger.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(!WEARER.profile.isEmptyUserId())gUserProfile.putSQLEntry(sqlKeyEmUserId,"string",WEARER.profile.getUserId());
        if(!WEARER.profile.isEmptyApi())gUserProfile.putSQLEntry(sqlKeyApi,"string",WEARER.profile.getApi());
        if(gUserProfile.saveProfile(table)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }
    public JSONObject getUserSQLEntry(String key, String value){
        String fName="[getUserSQLEntry]";
        try {
            JSONObject result=new JSONObject();
            logger.info(cName+fName+"key="+key+", value="+value);
            if (!gGlobal.sql.checkConnection()) {
                logger.error(cName + fName + ".no open connection");
                return result;
            }
            List<String> colums= new ArrayList<>();
            Map<String,Object> rows;
            colums.add("id");colums.add(sqlKeyUserID);colums.add("json");colums.add("emuserid");colums.add("emapikey");
            rows=gGlobal.sql.selectFirst(table,key+" = '"+value+"'",colums);
            if(rows==null){logger.info(cName+fName+".select null"); return result; }
            for(Map.Entry<String, Object> entry : rows.entrySet()) {
                String sqlkey = entry.getKey();
                Object sqlvalue = entry.getValue();
                logger.info(cName+fName+"sqlkey="+sqlkey+", sqlvalue="+sqlvalue);
                if(sqlkey.equalsIgnoreCase("json")&&sqlvalue!=null){
                    result.put("json",new JSONObject(sqlvalue.toString()));
                }else{
                    result.put(sqlkey,sqlvalue.toString());
                }
            }
            logger.info(cName+fName+". result="+ result.toString());
            logger.info(cName+fName+".success");
            return  result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }

    }

    entityRDUserProfile gNewUserBDSMProfile=new entityRDUserProfile();
    private Boolean getBDSMProfile(Member member){
        String fName="[getBDSMProfile]";
        try {
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            if(gNewUserBDSMProfile!=null&&gNewUserBDSMProfile.isProfile()&&gNewUserBDSMProfile.getMember().getIdLong()==member.getIdLong()){
                logger.info(fName + ".already present>skip");
                return true;
            }
            gNewUserBDSMProfile.build(gGlobal,member,gBDSMCommands);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }

    InteractionHook interactionHook;
    private void SlashNT() {
        String fName = "[SlashNT]";
        logger.info(fName);
        if(!gSlashCommandEvent.isFromGuild()){
            gSlashCommandEvent.replyEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Only available in guild!",null).build()).setEphemeral(true).complete();
            return;
        }
        if(!gTextChannel.isNSFW()){
            gSlashCommandEvent.replyEmbeds(lsMessageHelper.lsErrorEmbed(null,gTitle,"Require NSFW channel",null).build()).setEphemeral(true).complete();
            return;
        }
        String subcommand=gSlashCommandEvent.getSubcommandName();
        List<OptionMapping> options=gSlashCommandEvent.getOptions();
        /*if(options.isEmpty()){
            interactionHook=gSlashCommandEvent.reply("Opening menu").setEphemeral(true).complete();
            menuMain(gTarget);
            return;
        }*/
        User user=null;Member member=null;
        String optionValue="";boolean optionProvided=false;
        long daysValue=0;boolean daysProvided=false;
        long hoursValue=0;boolean hoursProvided=false;
        long secondsValue=0;boolean secondsProvided=false;
        long durationValue=0;boolean durationProvided=false;
        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColorBlue3);
        for(OptionMapping option:options){
            String name=option.getName();
            logger.info(fName+"option.name="+name);
            switch (name){
                case "user":
                    if(option.getType()== OptionType.USER){
                        user=option.getAsUser();
                        member=option.getAsMember();
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "option":
                    if(option.getType()== OptionType.STRING){
                       optionValue=option.getAsString();
                       optionProvided=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "duration":
                    if(option.getType()== OptionType.INTEGER){
                        durationValue=option.getAsLong();
                        durationProvided=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "days": case "day":
                    if(option.getType()== OptionType.INTEGER){
                        daysValue=option.getAsLong();
                        daysProvided=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "hours": case "hour":
                    if(option.getType()== OptionType.INTEGER){
                        hoursValue=option.getAsLong();
                        hoursProvided=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "seconds": case "second":
                    if(option.getType()== OptionType.INTEGER){
                        secondsValue=option.getAsLong();
                        secondsProvided=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
            }
        }
        if(member!=null&&member.getIdLong()!=gMember.getIdLong()){
            gTarget=member;
            getProfile(member);
        }else{
            getProfile(gMember);
        }
        getMainEntries();
        boolean isHolder=false,isOwner=false,isWearer=false,isGuest=false;
        if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
            isWearer=true;
        }
        else if(isWearersHolder()){
            isHolder=true;
        }
        else if(iRestraints.lsHasUserOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)||iRestraints.lsHasUserSecOwnerAccess(gNewUserBDSMProfile.gUserProfile,gUser)){
            isOwner=true;
        }else{
            isGuest=true;
        }
        logger.info(fName+"isWearer="+isWearer+", isHolder="+isHolder+", isOwner="+isOwner+", isGuest="+isGuest);
        String url="";
        switch (subcommand){
            case "menu":
                embedBuilder.setDescription("Opening menu");
                interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                menuMain();
                break;
            case "voting":
                if(!optionProvided){
                    embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not option provided!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                if(!WEARER.profile.voting.isEnable()){
                    embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not enabled to vote!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                if(isWearer){
                    embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not so fast! How do you think you can vote for yourself?! Better luck next time.");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                int type=0,updatedtype=0;
                switch (optionValue){
                    case "add":
                        type=1;updatedtype=1;
                        if(!WEARER.profile.voting.isAllowAdd()){
                            embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not so fast! Adding time is not allowed. Too bad we cant teas them with adding time.");
                            interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                            return;
                        }
                        break;
                    case "sub":
                        type=2;updatedtype=2;
                        if(!WEARER.profile.voting.isAllowSub()){
                            embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not so fast! Substracting time is not allowed. They must have been naughty to deserver this.");
                            interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                            return;
                        }
                        break;
                    case "random":
                        type=3;
                        if(!WEARER.profile.voting.isAllowRandom()){
                            embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not so fast! Random time is not allowd. Lucky or unlucky them.");
                            interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                            return;
                        }
                        int n=0;
                        while (updatedtype!=1&&updatedtype!=2&&n<100){
                            n++;
                            updatedtype=lsUsefullFunctions.getRandom(4);
                        }
                        break;
                    default:
                        embedBuilder.setColor(llColorRed_Cardinal).setDescription("Invalid option provided!");
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                }
                boolean isAllowed=false;
                Timestamp currenttime = new Timestamp(System.currentTimeMillis());
                logger.info(fName+".currenttime="+currenttime.getTime());
                if(!WEARER.profile.voting.isMemberPresentInLogs(gMember)){
                    isAllowed=true;
                }else{
                    long lastTimeVoted=WEARER.profile.voting.getMemberTimeStamp4Logs(gMember);
                    logger.info(fName+".lastTimeVoted="+lastTimeVoted);
                    long wait=defaultWaitTIme;
                    if(WEARER.profile.voting.getTimeWait()>defaultMinTime){
                        wait=WEARER.profile.voting.getTimeWait();
                    }
                    logger.info(fName+".wait="+wait);
                    long lastTimeVoted2=lastTimeVoted+wait;
                    logger.info(fName+".lastTimeVoted+wait="+lastTimeVoted2);
                    if(currenttime.getTime()>=lastTimeVoted2){
                        logger.info(fName+".waited enough");
                        isAllowed=true;
                    }
                }
                if(!isAllowed){
                    logger.info(fName+".not allowed to vote");
                    embedBuilder.setDescription("Sorry, you still need to wait before voting for "+gUserProfile.gUser.getAsMention()+".");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }else{
                    logger.info(fName+".register");
                    WEARER.profile.voting.setMemberTimeStamp4Logs(gMember,currenttime.getTime());
                }
                interactionHook=gSlashCommandEvent.deferReply().complete();

                long timeUpdater=0;
                logger.info(fName+".updatedtype="+updatedtype);
                switch (updatedtype){
                    case 1:
                        timeUpdater=WEARER.profile.voting.getTimeAdd();
                        url=gUrlApiAdd;
                        break;
                    case 2:
                        timeUpdater=WEARER.profile.voting.getTimeSub();
                        url=gUrlApiSub;
                        break;
                }
                logger.info(fName+".timeUpdater="+timeUpdater);
                long time2Use= lsNumbersUsefullFunctions.milliseconds2seconds(timeUpdater);
                url=url.replaceAll("!VALUE", String.valueOf(time2Use));
                if(updatedtype==2){
                    if(!WEARER.session.hasKeyHolder()){
                        embedBuilder.setDescription("No holder id found for "+gUserProfile.gUser.getAsMention()+".");
                        interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                        return;
                    }
                    if(WEARER.holder.isEmpty()){
                        if(!getHolderInfo(WEARER.session.getHolderId())){
                            embedBuilder.setDescription("Failed to get "+gUserProfile.gUser.getAsMention()+" holder info.");
                            interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                            return;
                        }
                    }

                    if(!HOLDERSqlEntry.has(sqlKeyEmUserId)||HOLDERSqlEntry.getString(sqlKeyEmUserId).isBlank()){
                        logger.warn(fName + ".no holder user id provided");
                        embedBuilder.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no user id.");
                        interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                        return;
                    }
                    if(!HOLDERSqlEntry.has(sqlKeyApi)||HOLDERSqlEntry.getString(sqlKeyApi).isBlank()){
                        logger.warn(fName + ".no holder api provided");
                        embedBuilder.setDescription(gUserProfile.gUser.getAsMention()+"'s holder provided no api key.");
                        interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                        return;
                    }
                    url=url.replaceAll("!HOLDERAPIKEY",HOLDERSqlEntry.getString(sqlKeyApi));
                }
                url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi());
                logger.info(fName+"url="+url);

                responseSessionUpdate =getApiEmlalock(url);
                logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
                logger.info(fName+".body ="+ responseSessionUpdate.getBody());
                if(responseSessionUpdate.getStatus()!=200){
                    logger.error(fName+".error status");
                    getErrorJSON(responseSessionUpdate);
                    loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                    return;
                }else{
                    if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                        switch (type){
                            case 1:
                                embedBuilder.setColor(llColorRed_Cinnabar);
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration to their session.");
                                break;
                            case 2:
                                embedBuilder.setColor(llColorGreen_Mint);
                                embedBuilder.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration from their session.");
                                break;
                            case 3:
                                embedBuilder.setColor(llColorPurple2);
                                embedBuilder.setDescription(gMember.getAsMention()+" chose to play the dice with their session duration.");
                                break;
                        }
                    }else{
                        switch (type){
                            case 1:
                                embedBuilder.setColor(llColorRed_Cinnabar);
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                                break;
                            case 2:
                                embedBuilder.setColor(llColorGreen_Mint);
                                embedBuilder.setDescription(gMember.getAsMention()+" subtracted "+lsUsefullFunctions.displayDuration(timeUpdater)+" duration from "+gUserProfile.gUser.getAsMention()+"'s session.");
                                break;
                            case 3:
                                embedBuilder.setColor(llColorPurple2);
                                embedBuilder.setDescription(gMember.getAsMention()+" chose to play the dice with "+gUserProfile.gUser.getAsMention()+"'s session duration.");
                                break;
                        }
                    }

                    interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                    loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
                    saveProfile();
                }
                break;
            case "calls":case "apit_time":
                long valueSet=0;
                if(!optionProvided){
                    embedBuilder.setDescription("Opening menu.");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    menuAPICalls();
                }
                if(!durationProvided&&!daysProvided&&!hoursProvided&&!secondsProvided){
                    embedBuilder.setColor(llColorRed_Cardinal).setDescription("Not duration provided!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                if(optionValue.contains("add")&&!isWearer&&!isHolder){
                    embedBuilder.setColor(llColorRed_Cardinal).setDescription("Only wearer and holder can access the API for adding!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                if(optionValue.contains("sub")&&!isHolder){
                    embedBuilder.setColor(llColorRed_Cardinal).setDescription("Only holder can access the API for substraction!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }

                switch (optionValue){
                    case "add":
                        url=gUrlApiAdd;
                        break;
                    case "addmaximum":
                        url=gUrlApiAddMaximum;
                        break;
                    case "addminimum":
                        url=gUrlApiAddMinimum;
                        break;
                    case "addrequirement":
                        url=gUrlApiAddRequirement;
                        break;
                    case "sub":
                        url=gUrlApiSub;
                        break;
                    case "submaximum":
                        url=gUrlApiSubMaximum;
                        break;
                    case "subminimum":
                        url=gUrlApiSubMinimum;
                        break;
                    case "subrequirement":
                        url=gUrlApiSubRequirement;
                        break;
                    default:
                        embedBuilder.setColor(llColorRed_Cardinal).setDescription("Invalid option provided!");
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                }
                if(!durationProvided)valueSet+=durationValue;
                if(daysProvided)valueSet+=daysValue*iChastityEmlalock.milliseconds_day;
                if(hoursProvided)valueSet+=hoursValue*iChastityEmlalock.milliseconds_hour;
                if(!secondsProvided)valueSet+=secondsValue;
                logger.info(fName+"valueSet="+valueSet);
                url=url.replaceAll("!USERID",WEARER.profile.getUserId()).replaceAll("!APIKEY",WEARER.profile.getApi()).replaceAll("!VALUE",String.valueOf(durationValue));
                logger.info(fName+"url="+url);
                interactionHook=gSlashCommandEvent.deferReply().complete();
                responseSessionUpdate =getApiEmlalock(url);
                logger.info(fName+".status ="+ responseSessionUpdate.getStatus());
                logger.info(fName+".body ="+ responseSessionUpdate.getBody());
                if(responseSessionUpdate.getStatus()!=200){
                    logger.error(fName+".error status");
                    getErrorJSON(responseSessionUpdate);
                    loggerFail.info("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), reason="+ responseSessionUpdate.getBody().getObject().toString());
                    return;
                }else{
                    switch (optionValue){
                        case "add":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(durationValue)+" duration to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(durationValue)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "addmaximum":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(durationValue)+" maximum duration to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(durationValue)+" maximum duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "addminimum":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(durationValue)+" minimum duration to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+lsUsefullFunctions.displayDuration(durationValue)+" minimum duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "addrequirement":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+durationValue+" requirement to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" added "+durationValue+" requirement to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "sub":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+lsUsefullFunctions.displayDuration(durationValue)+" duration to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+lsUsefullFunctions.displayDuration(durationValue)+" duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "submaximum":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+lsUsefullFunctions.displayDuration(durationValue)+" maximum duration to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+lsUsefullFunctions.displayDuration(durationValue)+" maximum duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "subminimum":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+lsUsefullFunctions.displayDuration(durationValue)+" minimum duration to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+lsUsefullFunctions.displayDuration(durationValue)+" minimum duration to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                        case "subrequirement":
                            if(gUserProfile.gUser.getIdLong()==gMember.getIdLong()){
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+durationValue+" requirement to their session.");
                            }else{
                                embedBuilder.setDescription(gMember.getAsMention()+" substracted "+durationValue+" requirement to "+gUserProfile.gUser.getAsMention()+"'s session.");
                            }
                            break;
                    }
                    interactionHook.editOriginalEmbeds(embedBuilder.build()).complete();
                    loggerUpdateSuccess.warn("author="+gUser.getName()+"#"+gUser.getDiscriminator()+"("+gMember.getIdLong()+"), target="+gUserProfile.gUser.getName()+"#"+gUserProfile.gUser.getDiscriminator()+"("+gUserProfile.gUser.getIdLong()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), url="+url);
                }
                break;
        }
    }

    lcBasicFeatureControl gBasicFeatureControl;
    private void setEnable(boolean enable) {
        String fName = "[setEnable]";
        try {
            logger.info(fName + "enable=" + enable);
            if(!lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)&&!lsGlobalHelper.slMemberIsBotOwner(gMember)){
                logger.info(fName+"denied");
                lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Denied. Require manage server.", llColors.llColorOrange_InternationalEngineering);
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
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Allowed channels list: "+lsChannelHelper.lsGetTextChannelsMentionAsString(list,", ",gGuild), llColors.llColorOrange_Bittersweet);
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
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
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

                    },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                        lsMessageHelper.lsMessageDelete(message);
                    });

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }

}
}
