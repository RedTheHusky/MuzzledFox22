package nsfw.lovense;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import express.http.response.Response;
import kong.unirest.FailedResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.CrunchifyLog4jLevel;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
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
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static nsfw.lovense.iLovense.*;

public class Lovense extends Command implements llMessageHelper, llGlobalHelper,  llMemberHelper, llNetworkHelper {
        Logger logger = Logger.getLogger(getClass());
       
        lcGlobalHelper gGlobal;
        String gTitle="Lovense[WIP]",gCommand="lovense";
        Logger loggerReq = Logger.getLogger(CrunchifyLog4jLevel.LovenseReq_STR);
    public Lovense(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Controlling Lovense's toys.";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;

    }
    public Lovense(lcGlobalHelper global,Response response,Member member,TextChannel textChannel,String command){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(response,member,textChannel,command);
        new Thread(r).start();

    }
    public Lovense(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
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
    CommandEvent gCommandEvent;
    SlashCommandEvent gSlashCommandEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel gTextChannel;
    lcJSONUserProfile gUserProfile;
    entityLovensense gLovense=new entityLovensense();
    Member gTarget;
    boolean gIsOverride=false;
    public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
    String gForward=null;
    Response gHttpResponse=null;
    String[] gItems;
    public runLocal(CommandEvent ev) {
        String fName="build";logger.info(".run build");
        gCommandEvent = ev;
        gUser = gCommandEvent.getAuthor();gMember= gCommandEvent.getMember();
        gGuild = gCommandEvent.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = gCommandEvent.getTextChannel();
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
    }
    public runLocal(Response res, Member member, TextChannel textChannel,String command) {
        String fName="build";logger.info(".run build");
        gHttpResponse=res;
        gUser = member.getUser();gMember= member;
        gGuild = member.getGuild();
        logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
        logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
        gTextChannel = textChannel;
        logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        gForward=command;
        logger.info(fName + ".gForward:" + gForward);
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
            gTextChannel =ev.getTextChannel();
            logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        }
    }
    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");

        Boolean isInvalidCommand = true;
        try{
            gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"Lovense",gGlobal);
            gBasicFeatureControl.initProfile();
            gLovense=new entityLovensense(gGlobal);
            if(gHttpResponse!=null){
                if(gForward.equalsIgnoreCase("main")){
                    delReq2Que();
                    menuMain(null);
                }
            }else
            if(gSlashCommandEvent!=null){
                if(!isNSFW()){
                    blocked();return;
                }
                SlashNT();
            }else
            {
                logger.info(fName + "basic@");
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    if(!isNSFW()){
                        blocked();return;
                    }
                    menuMain(null);
                    isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    gItems = gCommandEvent.getArgs().split("\\s+");
                    if(gCommandEvent.getArgs().contains(llOverride)&&llMemberIsStaff(gMember)){ gIsOverride =true;}
                    logger.info(fName + ".gItems.size=" + gItems.length);
                    logger.info(fName + ".gItems[0]=" + gItems[0]);

                    if(gItems[0].equalsIgnoreCase("help")){
                        help("main");isInvalidCommand=false;
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
                                    setChannel(type,action, gCommandEvent.getMessage());
                                }
                            }
                            else if(group==2){
                                if(action==0){
                                    getRoles(type,false);isInvalidCommand=false;
                                }else{
                                    setRole(type,action, gCommandEvent.getMessage());
                                }
                            }
                        }else{
                            menuGuild();isInvalidCommand=false;
                        }
                    }
                    else if(!isNSFW()){
                        blocked();
                        return;
                    }
                    else if(!gBasicFeatureControl.getEnable()){
                        logger.info(fName+"its disabled");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's disabled in "+gGuild.getName()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                        return;
                    }
                    else if(!gBasicFeatureControl.isChannelAllowed(gTextChannel)){
                        logger.info(fName+"its not allowed by channel");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed in channel "+gTextChannel.getAsMention()+"!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                        return;
                    }
                    else if(!gBasicFeatureControl.isRoleAllowed(gMember)){
                        logger.info(fName+"its not allowed by roles");
                        lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"It's not allowed as you roles prevent it!", lsMessageHelper.llColorRed_Cardinal);
                        isInvalidCommand=false;
                        return;
                    }
                    if(isInvalidCommand&&(gItems[0].contains("<!@")||gItems[0].contains("<@"))&&gItems[0].contains(">")){
                        logger.info(fName+".detect mention characters");
                        Member target;
                        List<Member>mentions= gCommandEvent.getMessage().getMentionedMembers();
                        if(mentions.isEmpty()){
                            logger.warn(fName+".zero member mentions in message>check itemns[0]");
                            target=llGetMember(gGuild,gItems[0]);
                        }else{
                            logger.info(fName+".member mentions in message");
                            target=mentions.get(0);

                        }
                        if(target==null){
                            logger.warn(fName+".zero member mentions");
                        }
                        else if(target.getIdLong()==gUser.getIdLong()){
                            logger.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                            target=null;
                            gTarget=null;
                            //llSendQuickEmbedMessage(gEvent.getAuthor(),sRTitle,dontMentionYourselfWhenTrying2UseCommand4Yourself, llColorRed);
                        }else{
                            logger.info(fName+".user mentioned");
                            gTarget=target;
                        }
                    }
                    if(gTarget!=null){
                        if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
                        if(gItems[1].equalsIgnoreCase(iLovense.commandMenu)){
                            menuMainSomebody();isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase(iLovense.commandToy)&&gItems.length>=3){
                            rLovense(gTarget,gItems[1], gItems[2]);isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase(iLovense.commandStop)&&gItems.length>=3){
                            rLovense(gTarget,gItems[1], gItems[2]);isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase(iLovense.commandFunction)){
                            String tmp="";
                            for(int i=2;i<gItems.length;i++){
                                if(!tmp.isBlank())tmp+="~";
                                tmp+=gItems[i];
                            }
                            rLovense(gTarget,gItems[1], tmp);isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase(iLovense.commandPreset)){
                            String tmp="";
                            for(int i=2;i<gItems.length;i++){
                                if(!tmp.isBlank())tmp+="~";
                                tmp+=gItems[i];
                            }
                            rLovense(gTarget,gItems[1], tmp);isInvalidCommand=false;
                        }
                        else if(gItems[1].equalsIgnoreCase(iLovense.commandFunctions)){
                            String tmp="";
                            for(int i=2;i<gItems.length;i++){
                                if(!tmp.isBlank())tmp+="~";
                                tmp+=gItems[i];
                            }
                            rLovense(gTarget,gItems[1], tmp);isInvalidCommand=false;
                        }
                    }else{
                        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                        if(gItems[0].equalsIgnoreCase(iLovense.commandqr)){
                            dmGetQrCode();isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase(iLovense.commandMenu)){
                            menuMainWearer();isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase(iLovense.commandToy)&&gItems.length>=2){
                            rLovense(gItems[0], gItems[1]);isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase(iLovense.commandStop)&&gItems.length>=2){
                            rLovense(gItems[0], gItems[1]);isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase(iLovense.commandFunction)){
                            String tmp="";
                            for(int i=1;i<gItems.length;i++){
                                if(!tmp.isBlank())tmp+="~";
                                tmp+=gItems[i];
                            }
                            rLovense(gItems[0], tmp);isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase(iLovense.commandPreset)){
                            String tmp="";
                            for(int i=1;i<gItems.length;i++){
                                if(!tmp.isBlank())tmp+="~";
                                tmp+=gItems[i];
                            }
                            rLovense(gItems[0], tmp);isInvalidCommand=false;
                        }
                        else if(gItems[0].equalsIgnoreCase(iLovense.commandFunctions)){
                            String tmp="";
                            for(int i=1;i<gItems.length;i++){
                                if(!tmp.isBlank())tmp+="~";
                                tmp+=gItems[i];
                            }
                            rLovense(gItems[0], tmp);isInvalidCommand=false;
                        }
                        else if((gItems[0].equalsIgnoreCase(iLovense.commandDisable)||gItems[0].equalsIgnoreCase(iLovense.commandEnable)
                                ||gItems[0].equalsIgnoreCase(iLovense.commandPrivate)||gItems[0].equalsIgnoreCase(iLovense.commandPublic))&&gItems.length>=2){
                            rLovense(gItems[0],gItems[1]);isInvalidCommand=false;
                        }
                    }
                }
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
        logger.info(fName);
        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setTitle(gTitle).setDescription("Require NSFW channel or server.").setColor(llColorRed);
        if(gSlashCommandEvent!=null){
            gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true);
        }else{
            lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
        }
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
        embed.addField(strDisclaim,strDisclaimMore,false);

        desc="`"+quickSummonWithSpace+"<@OptionalMentionUser> menu` to access the dm menu";
        desc+="\n`"+quickSummonWithSpace+"<@OptionalMentionUser> toy <index>` to access the toy # menu";
        embed.addField("Menu Commands",desc,false);desc="";
        desc+="`"+quickSummonWithSpace+"<@OptionalMentionUser> functions <index> <actions> <total time> <loop running time *> <loop pause time *>` to send function command to toy with more than one action";
        desc+="\n`"+quickSummonWithSpace+"<@OptionalMentionUser> function <index> <action> <strength> <total time> <loop running time *> <loop pause time *>` to send function command to toy with only 1 action";
        desc+="\n • `actions` at least 1 action needs to be provided, following actions: vibrate, rotate, pump, stop.";
        desc+="\n • actions format: `<action>:<strength>,<action>:<strength>` for actions: vibrate, rotate, pump.";
        desc+="\n • `total time`, is required (except for stop), is a numeric value the total time the toy runs in seconds";
        desc+="\n • `loop running time` and `loop pause time` are optional numeric values, they not required";
        desc+="\n • example `vibrate:20,rotate:2 25`";
        embed.addField("Function Command",desc,false);desc="";
        desc+="`"+quickSummonWithSpace+"<@OptionalMentionUser> preset <index> <name> <total time>` to send preset command to toy";
        desc+="\n • `name` is required, following names: pulse, wave, fireworks, earthquake";
        desc+="\n • `total time`, is required, is a numeric value the total time the toy runs in seconds";
        desc+="\n • example `pulse 5`";
        embed.addField("Preset Command",desc,false);desc="";
        desc+="`"+quickSummonWithSpace+"<@OptionalMentionUser> stop <index>` to stop the toy";
        embed.addField("Stop Command",desc,false);desc="";
        embed.addField("First step","In order to connect and get the list of toys, its required to scan the qr code.\nUse `"+quickSummonWithSpace+"qr`, only can be used on self.",false);
        embed.addField("WIP","WORK IN PROGRESS",false);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            if(gSlashCommandEvent==null&&!isMenu)lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }

    private void rLovense(String command, String value) {
        String fName = "[rLovense]";
        logger.info(fName);
        logger.info(fName + ".command=" + command+", value="+value);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        if(command.equalsIgnoreCase(iLovense.commandToy)&&!value.isBlank()){
            int index=lsStringUsefullFunctions.String2Int(value,-1);
            if(!gLovense.hasToyLoc(index)){
                logger.warn(fName + ".invalid index");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                return;
            }
            menuToyWearer(index);
        }
        else if(command.equalsIgnoreCase(iLovense.commandPublic)&&!value.isBlank()){
            int index=lsStringUsefullFunctions.String2Int(value,-1);
            if(!gLovense.hasToyLoc(index)){
                logger.warn(fName + ".invalid index");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                return;
            }
            entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
            toyLoc.setPublic(true);
            if(!saveProfile()){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Toy "+index+" set to public.",llColors.llColorGreen1);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandPrivate)&&!value.isBlank()){
            int index=lsStringUsefullFunctions.String2Int(value,-1);
            if(!gLovense.hasToyLoc(index)){
                logger.warn(fName + ".invalid index");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                return;
            }
            entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
            toyLoc.setPublic(false);
            if(!saveProfile()){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Toy "+index+" set to private.",llColors.llColorGreen1);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandEnable)&&!value.isBlank()){
            int index=lsStringUsefullFunctions.String2Int(value,-1);
            if(!gLovense.hasToyLoc(index)){
                logger.warn(fName + ".invalid index");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                return;
            }
            entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
            toyLoc.setEnabled(true);
            if(!saveProfile()){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Toy "+index+" enable.",llColors.llColorGreen1);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandDisable)&&!value.isBlank()){
            int index=lsStringUsefullFunctions.String2Int(value,-1);
            if(!gLovense.hasToyLoc(index)){
                logger.warn(fName + ".invalid index");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                return;
            }
            entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
            toyLoc.setEnabled(false);
            if(!saveProfile()){
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                return;
            }
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Toy "+index+" disabled.",llColors.llColorGreen1);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandFunction)){
            String[] items= value.split("~");
            String reqToy="",reqAction="",reqActionLevel="",reqTimeSec="",reqLoopRunningSec="",reqLoopPauseSec="";
            if(items.length>=1)reqToy=items[0];
            if(items.length>=2)reqAction=items[1];
            if(items.length>=3)reqActionLevel=items[2];
            if(items.length>=4)reqTimeSec=items[3];
            if(items.length>=5)reqLoopRunningSec=items[4];
            if(items.length>=6)reqLoopPauseSec=items[5];
            logger.info(fName + ".reqToy=" + reqToy+", reqAction="+reqAction+", reqActionLevel="+reqActionLevel+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
            rToyFunction(reqToy, reqAction,reqActionLevel,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandFunctions)){
            String[] items= value.split("~");
            String reqToy="",reqActions="",reqTimeSec="",reqLoopRunningSec="",reqLoopPauseSec="";
            if(items.length>=1)reqToy=items[0];
            if(items.length>=2)reqActions=items[1];
            if(items.length>=3)reqTimeSec=items[2];
            if(items.length>=4)reqLoopRunningSec=items[3];
            if(items.length>=5)reqLoopPauseSec=items[4];
            rToyFunction(reqToy,reqActions,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandPreset)){
            String[] items= value.split("~");
            String reqToy="",reqAction="",reqTimeSec="";
            if(items.length>=1)reqToy=items[0];
            if(items.length>=2)reqAction=items[1];
            if(items.length>=3)reqTimeSec=items[2];
            logger.info(fName + ".reqToy=" + reqToy+", reqAction="+reqAction+", reqTimeSec="+reqTimeSec);
            rToyPreset(reqToy, reqAction,reqTimeSec);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandStop)){
            rToyFunction(value, "0","0","0","0");
            selectMenu();
        }

    }
    private void rToyFunction(String reqToy,  String reqAction, String reqActionLevel, String reqTimeSec, String reqLoopRunningSec, String reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".reqToy=" + reqToy+", reqAction="+reqAction+", reqActionLevel="+reqActionLevel+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        int index=lsStringUsefullFunctions.String2Int(reqToy,-1);
        if(!gLovense.hasToyLoc(index)){
            logger.warn(fName + ".invalid index");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
            return;
        }
        if(gLovense.getToyLocStatus(index)<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!");
            return;
        }
        entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
        rToyFunction(toyLoc,  reqAction+":"+reqActionLevel, reqTimeSec, reqLoopRunningSec, reqLoopPauseSec);
    }
    private void rToyFunction(String reqToy,  String reqActions, String reqTimeSec, String reqLoopRunningSec, String reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".reqToy=" + reqToy+",  reqActions="+reqActions+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        int index=lsStringUsefullFunctions.String2Int(reqToy,-1);
        if(!gLovense.hasToyLoc(index)){
            logger.warn(fName + ".invalid index");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
            return;
        }
        if(gLovense.getToyLocStatus(index)<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!");
            return;
        }
        entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
        rToyFunction(toyLoc, reqActions, reqTimeSec, reqLoopRunningSec, reqLoopPauseSec);
    }
    private void rToyFunction(entityLovensenseToyLoc toyLoc, String reqActions, String reqTimeSec, String reqLoopRunningSec, String reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", reqActions="+reqActions+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        List<entityLovensenseAction>actions=new ArrayList<>();
        String[] itemsActions=reqActions.split(",");
        boolean isstope=false;
        for(int i=0;i<itemsActions.length;i++){
            String[] itemsAction=itemsActions[i].split(":");
            Actions4Function actions4Function=null;int stregth=0;
            switch (itemsAction[0].toLowerCase()) {
                case "0":
                case "stop":
                    actions4Function = Actions4Function.Stop;isstope=true;
                    break;
                case "1":
                case "vibrate":
                    actions4Function = Actions4Function.Vibrate;
                    break;
                case "2":
                case "rotate":
                    actions4Function = Actions4Function.Rotate;
                    break;
                case "3":
                case "pump":
                    actions4Function = Actions4Function.Pump;
                    break;
            }
            if(actions4Function==null){
                logger.warn(fName + ".invalid action");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!");
                return;
            }
            if(actions4Function!=Actions4Function.Stop){
                if(itemsAction.length>=2)stregth=lsStringUsefullFunctions.String2Int(itemsAction[1]);
                if(stregth<=0){
                    logger.warn(fName + ".invalid stregth");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid stregth 4 action!");
                    return;
                }
            }
            entityLovensenseAction action=new entityLovensenseAction(actions4Function,stregth);
            actions.add(action);
            if(isstope){break;}
        }
        int  valTimeSec=0, valLoopRunningSec=0, valLoopPauseSec=0;
        if(!isstope){
            valTimeSec=lsStringUsefullFunctions.String2Int(reqTimeSec);
            if(valTimeSec<2){
                logger.warn(fName + ".invalid valTimeSec");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Time running should be above 1.");
                return;
            }
            valLoopPauseSec=lsStringUsefullFunctions.String2Int(reqLoopPauseSec);
            valLoopRunningSec=lsStringUsefullFunctions.String2Int(reqLoopRunningSec);
        }
        rToyFunction(toyLoc,actions,valTimeSec,valLoopRunningSec,valLoopPauseSec);
    }
    private void rToyFunction(entityLovensenseToyLoc toyLoc,  List<entityLovensenseAction>actions, int reqTimeSec, int reqLoopRunningSec, int reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", reqActions="+actions.size()+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        if(gLovense.getToyLocStatus(toyLoc.getID())<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!");
            return;
        }
        if(!toyLoc.isEnabled()){
            logger.warn(fName + ".not enabled");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Toy is not enabled!");
            return;
        }
        if(actions.isEmpty()){
            logger.warn(fName + ".invalid action");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!");
            return;
        }
        HttpResponse<JsonNode>response=gLovense.doFunction(toyLoc,actions,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
        if(response==null){
            logger.warn(fName + "respose is null");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action.");
            return;
        }
        if(!response.isSuccess()){
            logger.warn(fName + "respose is not success");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action.");
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        if(response.getStatus()>299){
            logger.warn(fName + "respose invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action.");
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        JSONObject jsonObject=response.getBody().getObject();
        if(jsonObject.getBoolean("result")){
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Successfully performed action.",llColors.llColorGreen1);
            loggerReq.info("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+",, status:true, result:success");
        }else{
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,jsonObject.getString("message"));
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:failed");
        }
    }
    private void rToyPreset(String reqToy,  String  reqPreset, String reqTimeSec) {
        String fName = "[rToyPreset]";
        logger.info(fName);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        logger.info(fName + ".reqToy=" + reqToy+", reqPreset="+reqPreset+", reqTimeSec="+reqTimeSec);
        int index=lsStringUsefullFunctions.String2Int(reqToy,-1);
        if(!gLovense.hasToyLoc(index)){
            logger.warn(fName + ".invalid index");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
            return;
        }
        if(gLovense.getToyLocStatus(index)<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!");
            return;
        }
        entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
        rToyPreset(toyLoc,  reqPreset, reqTimeSec);
    }
    private void rToyPreset(entityLovensenseToyLoc toyLoc,  String  reqPreset, String reqTimeSec) {
        String fName = "[rToyPreset]";
        logger.info(fName);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", reqPreset="+reqPreset+", reqTimeSec="+reqTimeSec);
        Actions4Preset preset=null;
        switch (reqPreset.toLowerCase()) {
            case "0":
            case "pulse":
                preset=Actions4Preset.Pulse;
                break;
            case "1":
            case "wave":
                preset=Actions4Preset.Wave;
                break;
            case "2":
            case "firework":
            case "fireworks":
                preset=Actions4Preset.Fireworks;
                break;
            case "3":
            case "earthquake":
                preset=Actions4Preset.Earthquake;
                break;
        }
        if(preset==null){
            logger.warn(fName + ".invalid preset");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid preset!");
            return;
        }
        int valTimeSec=lsStringUsefullFunctions.String2Int(reqTimeSec);
        if(valTimeSec<2){
            logger.warn(fName + ".invalid valTimeSec");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Time running should be above 1!");
            return;
        }
        rToyPreset(toyLoc,  preset, valTimeSec);
    }
    private void rToyPreset(entityLovensenseToyLoc toyLoc,  Actions4Preset preset, int reqTimeSec) {
        String fName = "[rToyPreset]";
        logger.info(fName);
        if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        if(preset==null||preset==Actions4Preset.Stop){
            logger.warn(fName + ".invalid action");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid preset!");
            return;
        }
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", presets="+preset.getName()+", reqTimeSec="+reqTimeSec);
        if(gLovense.getToyLocStatus(toyLoc.getID())<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!");
            return;
        }
        if(!toyLoc.isEnabled()){
            logger.warn(fName + ".not enabled");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Toy is not enabled!");
            return;
        }

        HttpResponse<JsonNode>response=gLovense.doPreset(toyLoc,preset,reqTimeSec);
        if(response==null){
            logger.warn(fName + "respose is null");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action.");
            return;
        }
        if(!response.isSuccess()){
            logger.warn(fName + "respose is not success");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action.");
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        if(response.getStatus()>299){
            logger.warn(fName + "respose invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action.");
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        JSONObject jsonObject=response.getBody().getObject();
        if(jsonObject.getBoolean("result")){
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Successfully performed action.",llColors.llColorGreen1);
            loggerReq.info("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:success");
        }else{
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,jsonObject.getString("message"));
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:failed");
        }
    }
    private void rLovense(Member target,String command, String value) {
        String fName = "[rLovense]";
        logger.info(fName);
        logger.info(fName + ".command=" + command+", value="+value);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        if(command.equalsIgnoreCase(iLovense.commandToy)&&!value.isBlank()){
            int index=lsStringUsefullFunctions.String2Int(value,-1);
            if(!gLovense.hasToyLoc(index)){
                logger.warn(fName + ".invalid index");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                return;
            }
            menuToySomebody(index);
        }
        else if(command.equalsIgnoreCase(iLovense.commandFunction)){
            String[] items= value.split("~");
            String reqToy="",reqAction="",reqActionLevel="",reqTimeSec="",reqLoopRunningSec="",reqLoopPauseSec="";
            if(items.length>=1)reqToy=items[0];
            if(items.length>=2)reqAction=items[1];
            if(items.length>=3)reqActionLevel=items[2];
            if(items.length>=4)reqTimeSec=items[3];
            if(items.length>=5)reqLoopRunningSec=items[4];
            if(items.length>=6)reqLoopPauseSec=items[5];
            logger.info(fName + ".reqToy=" + reqToy+", reqAction="+reqAction+", reqActionLevel="+reqActionLevel+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
            rToyFunction(target,reqToy,  reqAction,reqActionLevel,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandFunctions)){
            String[] items= value.split("~");
            String reqToy="",reqActions="",reqTimeSec="",reqLoopRunningSec="",reqLoopPauseSec="";
            if(items.length>=1)reqToy=items[0];
            if(items.length>=2)reqActions=items[1];
            if(items.length>=3)reqTimeSec=items[2];
            if(items.length>=4)reqLoopRunningSec=items[3];
            if(items.length>=5)reqLoopPauseSec=items[4];
            rToyFunction(target,reqToy,reqActions,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandPreset)){
            String[] items= value.split("~");
            String reqToy="",reqAction="",reqTimeSec="";
            if(items.length>=1)reqToy=items[0];
            if(items.length>=2)reqAction=items[1];
            if(items.length>=3)reqTimeSec=items[2];
            logger.info(fName + ".reqToy=" + reqToy+", reqAction="+reqAction+", reqTimeSec="+reqTimeSec);
            rToyPreset(target,reqToy, reqAction,reqTimeSec);
            selectMenu();
        }
        else if(command.equalsIgnoreCase(iLovense.commandStop)){
            rToyFunction(target,value, "0","0","0","0");
            selectMenu();
        }
    }
    private void rToyFunction(Member target, String reqToy,  String reqAction, String reqActionLevel, String reqTimeSec, String reqLoopRunningSec, String reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".reqToy=" + reqToy+", reqAction="+reqAction+", reqActionLevel="+reqActionLevel+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        int index=lsStringUsefullFunctions.String2Int(reqToy,-1);
        if(!gLovense.hasToyLoc(index)){
            logger.warn(fName + ".invalid index");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(gLovense.getToyLocStatus(index)<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!"+"\nTarget:"+target.getAsMention());
            return;
        }
        entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
        rToyFunction(target,toyLoc,  reqAction+":"+reqActionLevel, reqTimeSec, reqLoopRunningSec, reqLoopPauseSec);
    }
    private void rToyFunction(Member target, String reqToy,  String reqActions, String reqTimeSec, String reqLoopRunningSec, String reqLoopPauseSec) {
        String fName = "[rToyFunctionFunction]";
        logger.info(fName);
        logger.info(fName + ".reqToy=" + reqToy+", reqActions="+reqActions+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        int index=lsStringUsefullFunctions.String2Int(reqToy,-1);
        if(!gLovense.hasToyLoc(index)){
            logger.warn(fName + ".invalid index");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(gLovense.getToyLocStatus(index)<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!"+"\nTarget:"+target.getAsMention());
            return;
        }
        entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
        rToyFunction(target,toyLoc, reqActions, reqTimeSec, reqLoopRunningSec, reqLoopPauseSec);
    }
    private void rToyFunction(Member target, entityLovensenseToyLoc toyLoc,  String reqActions, String reqTimeSec, String reqLoopRunningSec, String reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", reqActions="+reqActions+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        List<entityLovensenseAction>actions=new ArrayList<>();
        String[] itemsActions=reqActions.split(",");
        boolean isstope=false;
        for(int i=0;i<itemsActions.length;i++){
            String[] itemsAction=itemsActions[i].split(":");
            Actions4Function actions4Function=null;int stregth=0;
            switch (itemsAction[0]) {
                case "0":
                case "stop":
                    actions4Function = Actions4Function.Stop;isstope=true;
                    break;
                case "1":
                case "vibrate":
                    actions4Function = Actions4Function.Vibrate;
                    break;
                case "2":
                case "rotate":
                    actions4Function = Actions4Function.Rotate;
                    break;
                case "3":
                case "pump":
                    actions4Function = Actions4Function.Pump;
                    break;
            }
            if(actions4Function==null){
                logger.warn(fName + ".invalid action");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!"+"\nTarget:"+target.getAsMention());
                return;
            }
            if(actions4Function!=Actions4Function.Stop){
                if(itemsAction.length>=2)stregth=lsStringUsefullFunctions.String2Int(itemsAction[1]);
                if(stregth<=0){
                    logger.warn(fName + ".invalid stregth");
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid stregth 4 action!"+"\nTarget:"+target.getAsMention());
                    return;
                }
            }
            entityLovensenseAction action=new entityLovensenseAction(actions4Function,stregth);
            actions.add(action);
            if(isstope){break;}
        }

        int  valTimeSec=0, valLoopRunningSec=0, valLoopPauseSec=0;
        if(!isstope){
            valTimeSec=lsStringUsefullFunctions.String2Int(reqTimeSec);
            if(valTimeSec<2){
                logger.warn(fName + ".invalid valTimeSec");
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Time running should be above 1.");
                return;
            }
            valLoopPauseSec=lsStringUsefullFunctions.String2Int(reqLoopPauseSec);
            valLoopRunningSec=lsStringUsefullFunctions.String2Int(reqLoopRunningSec);
        }
        rToyFunction(target,toyLoc,actions,valTimeSec,valLoopRunningSec,valLoopPauseSec);
    }
    private void rToyFunction(Member target, entityLovensenseToyLoc toyLoc,  List<entityLovensenseAction>actions, int reqTimeSec, int reqLoopRunningSec, int reqLoopPauseSec) {
        String fName = "[rToyFunction]";
        logger.info(fName);
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", reqActions="+actions.toString()+", reqTimeSec="+reqTimeSec+" reqLoopRunningSec="+reqLoopRunningSec+", reqLoopPauseSec="+reqLoopPauseSec);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        if(gLovense.getToyLocStatus(toyLoc.getID())<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(!toyLoc.isPublic()){
            logger.warn(fName + ".not public");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Toy  is not public!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(!toyLoc.isEnabled()){
            logger.warn(fName + ".not enabled");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Toy is not enabled!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(actions.isEmpty()){
            logger.warn(fName + ".invalid action");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!"+"\nTarget:"+target.getAsMention());
            return;
        }
        HttpResponse<JsonNode>response=gLovense.doFunction(toyLoc,actions,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
        if(response==null){
            logger.warn(fName + "respose is null");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action."+"\nTarget:"+target.getAsMention());
            return;
        }
        if(!response.isSuccess()){
            logger.warn(fName + "respose is not success");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action."+"\nTarget:"+target.getAsMention());
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        if(response.getStatus()>299){
            logger.warn(fName + "respose invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action."+"\nTarget:"+target.getAsMention());
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        JSONObject jsonObject=response.getBody().getObject();
        if(jsonObject.getBoolean("result")){
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Successfully performed action."+"\nTarget:"+target.getAsMention(),llColors.llColorGreen1);
            loggerReq.info("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:success");
        }else{
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,jsonObject.getString("message")+"\nTarget:"+target.getAsMention());
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:failed");
        }
    }
    private void rToyPreset(Member target,String reqToy,  String  reqPreset, String reqTimeSec) {
        String fName = "[rToyPreset]";
        logger.info(fName);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        logger.info(fName + ".reqToy=" + reqToy+", reqPreset="+reqPreset+", reqTimeSec="+reqTimeSec);
        int index=lsStringUsefullFunctions.String2Int(reqToy,-1);
        if(!gLovense.hasToyLoc(index)){
            logger.warn(fName + ".invalid index");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(gLovense.getToyLocStatus(index)<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!"+"\nTarget:"+target.getAsMention());
            return;
        }
        entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
        rToyPreset(target,toyLoc,  reqPreset, reqTimeSec);
    }
    private void rToyPreset(Member target,entityLovensenseToyLoc toyLoc,  String  reqPreset, String reqTimeSec) {
        String fName = "[rToyPreset]";
        logger.info(fName);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", reqPreset="+reqPreset+", reqTimeSec="+reqTimeSec);
        Actions4Preset preset=null;
        switch (reqPreset.toLowerCase()) {
            case "0":
            case "pulse":
                preset=Actions4Preset.Pulse;
                break;
            case "1":
            case "wave":
                preset=Actions4Preset.Wave;
                break;
            case "2":
            case "firework":
            case "fireworks":
                preset=Actions4Preset.Fireworks;
                break;
            case "3":
            case "earthquake":
                preset=Actions4Preset.Earthquake;
                break;
        }
        if(preset==null){
            logger.warn(fName + ".invalid preset");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid preset!"+"\nTarget:"+target.getAsMention());
            return;
        }
        int valTimeSec=lsStringUsefullFunctions.String2Int(reqTimeSec);
        if(valTimeSec<2){
            logger.warn(fName + ".invalid valTimeSec");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Time running should be above 1!");
            return;
        }
        rToyPreset(target,toyLoc,  preset, valTimeSec);
    }
    private void rToyPreset(Member target,entityLovensenseToyLoc toyLoc,  Actions4Preset preset, int reqTimeSec) {
        String fName = "[rToyPreset]";
        logger.info(fName);
        if(!getProfile(target)){logger.error(fName + ".can't get profile"); return;}
        if(preset==null||preset==Actions4Preset.Stop){
            logger.warn(fName + ".invalid action");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid preset!");
            return;
        }
        logger.info(fName + ".toyLoc=" + toyLoc.getID()+", presets="+preset.getName()+", reqTimeSec="+reqTimeSec);
        if(gLovense.getToyLocStatus(toyLoc.getID())<=0){
            logger.warn(fName + ".invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!"+"\nTarget:"+target.getAsMention());
            return;
        }
        if(!toyLoc.isEnabled()){
            logger.warn(fName + ".not enabled");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Toy is not enabled!"+"\nTarget:"+target.getAsMention());
            return;
        }

        HttpResponse<JsonNode>response=gLovense.doPreset(toyLoc,preset,reqTimeSec);
        if(response==null){
            logger.warn(fName + "respose is null");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action."+"\nTarget:"+target.getAsMention());
            return;
        }
        if(!response.isSuccess()){
            logger.warn(fName + "respose is not success");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action."+"\nTarget:"+target.getAsMention());
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        if(response.getStatus()>299){
            logger.warn(fName + "respose invalid status");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to perform action."+"\nTarget:"+target.getAsMention());
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:false, result:n/a");
            return;
        }
        JSONObject jsonObject=response.getBody().getObject();
        if(jsonObject.getBoolean("result")){
            lsMessageHelper.lsSendQuickEmbedMessage(gUser,gTitle,"Successfully performed action."+"\nTarget:"+target.getAsMention(),llColors.llColorGreen1);
            loggerReq.info("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:success");
        }else{
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,jsonObject.getString("message")+"\nTarget:"+target.getAsMention());
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:failed");
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
        int optionIndex=-1;boolean gotIndex=false;
        String optionAction="";boolean gotAction=false;
        String optionName="";boolean gotName=false;
        int optionStrength=0; boolean gotStrength=false;
        int optionDuration=0;boolean gotDuration=false;
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
                case "index":
                    if(option.getType()== OptionType.INTEGER){
                        optionIndex=Long.valueOf(option.getAsLong()).intValue();
                        gotIndex=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "strength":
                    if(option.getType()== OptionType.NUMBER){
                        optionStrength=Long.valueOf(option.getAsLong()).intValue();
                        gotStrength=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "duration":
                    if(option.getType()== OptionType.NUMBER){
                        optionDuration=Long.valueOf(option.getAsLong()).intValue();
                        gotDuration=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "action":
                    if(option.getType()== OptionType.STRING){
                        optionAction=option.getAsString();
                        gotAction=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "name":
                    if(option.getType()== OptionType.STRING){
                        optionName=option.getAsString();
                        gotName=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
            }

        }
        if(member!=null&&member.getIdLong()!=gMember.getIdLong()){
            gTarget=member;
        }
        EmbedBuilder embedBuilder=new EmbedBuilder();
        if(gTarget!=null){
            if(!getProfile(gTarget)){logger.error(fName + ".can't get profile"); return;}
        }else{
            if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
        }
        logger.info(fName+".got profile");
        Actions4Function lovenseActions4Function=null;
        Actions4Preset lovenseActions4Preset=null;
        HttpResponse<JsonNode>response=new FailedResponse(null);
        entityLovensenseToyLoc toyLoc=new entityLovensenseToyLoc(gGlobal);
        logger.info(fName+"subcommand="+subcommand);
        switch (subcommand){
            case commandFunction:case commandPreset:case commandStop: case commandMenu:
                logger.info(fName+".gotIndex="+gotIndex+", optionIndex="+optionIndex);
                if(subcommand.equalsIgnoreCase(commandMenu)&&!gotIndex){
                    logger.info(fName+".ignore checking index");
                    break;
                }
                if(!gotIndex){
                    logger.info(fName+".invalidindex:0, missing index");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotIndex&&optionIndex<0){
                    logger.info(fName+".invalidindex:1, index is bellow 0");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(!gLovense.hasToyLoc(optionIndex)){
                    logger.info(fName+".invalidindex:2, has no such toy");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                toyLoc=gLovense.getToyLoc(optionIndex);
                break;
        }
        switch (subcommand){
            case commandFunction:case commandPreset:case commandStop:
                int status=gLovense.getToyLocStatus(optionIndex);
                logger.info(fName+".getToyLocStatus="+status);
                if(status<=0){
                    logger.info(fName+".invalidindex:3, invalid status");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid status for toy!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                logger.info(fName+".toyLoc.isEnabled()="+toyLoc.isEnabled());
                if(!toyLoc.isEnabled()){
                    logger.warn(fName + ".not enabled");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Toy is not enabled!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                logger.info(fName+".toyLoc.isPublic="+toyLoc.isPublic());
                if(gTarget!=null&&!toyLoc.isPublic()){
                    logger.warn(fName + ".not public");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Toy is not public!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                break;
            default:
                logger.info(fName+".skip checking toy status");
                break;
        }
        switch (subcommand){
            case commandFunction:
                logger.info(fName+".gotStrength="+gotStrength+", optionStrength="+optionStrength);
                if(!gotStrength){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotStrength&&optionStrength<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid strength!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }

            case commandPreset:
                logger.info(fName+".gotDuration="+gotDuration+", optionDuration="+optionDuration);
                if(!gotDuration){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotDuration&&optionDuration<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                break;
        }
        switch (subcommand){
            case commandMenu:
                interactionHook=gSlashCommandEvent.reply("Opening menu").setEphemeral(true).complete();
                if(gotIndex){
                    menuToy(gTarget,optionIndex);
                }else{
                    menuMain(gTarget);
                }
                return;
            case commandFunction:
                logger.info(fName+".optionAction="+optionAction);
                switch (optionAction) {
                    case "1":
                    case "vibrate":
                        lovenseActions4Function = Actions4Function.Vibrate;
                        break;
                    case "2":
                    case "rotate":
                        lovenseActions4Function = Actions4Function.Rotate;
                        break;
                    case "3":
                    case "pump":
                        lovenseActions4Function = Actions4Function.Pump;
                        break;
                    default:
                        //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!");
                        embedBuilder.setColor(iLovense.intColorRed);
                        embedBuilder.setDescription("Invalid action!");
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                }
                int timeLoopRunningSec=0,timeLoopPauseSec=0;
                response=gLovense.doFunction(toyLoc,lovenseActions4Function,optionStrength,optionDuration,timeLoopRunningSec,timeLoopPauseSec);
                break;
            case commandPreset:
                logger.info(fName+".optionName="+optionName);
                switch (optionName) {
                    case "0":
                    case "pulse":
                        lovenseActions4Preset=Actions4Preset.Pulse;
                        break;
                    case "1":
                    case "wave":
                        lovenseActions4Preset=Actions4Preset.Wave;
                        break;
                    case "2":
                    case "firework":
                    case "fireworks":
                        lovenseActions4Preset=Actions4Preset.Fireworks;
                        break;
                    case "3":
                    case "earthquake":
                        lovenseActions4Preset=Actions4Preset.Earthquake;
                        break;
                    default:
                        //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!");
                        embedBuilder.setColor(iLovense.intColorRed);
                        embedBuilder.setDescription("Invalid preset!");
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                }
                response=gLovense.doPreset(toyLoc,lovenseActions4Preset,optionDuration);
                break;
            case commandStop:
                response=gLovense.doStop(toyLoc);
                break;
        }
        /*
        switch (subcommand){
            case commandFunction:
                if(!gotIndex){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotIndex&&optionIndex<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                if(!gLovense.hasToyLoc(index)){
                    logger.warn(fName + ".invalid index");
                    //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid index!");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    gInteractionCreate.respondMessage(embedBuilder,false);
                    return;
                }
                if(gLovense.getToyLocStatus(index)<=0){
                    logger.warn(fName + ".invalid status");
                    //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid status for toy!");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid status for toy!");
                    gInteractionCreate.respondMessage(embedBuilder,false);
                    return;
                }
                entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(index);
                if(!toyLoc.isEnabled()){
                    logger.warn(fName + ".not enabled");
                    //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Toy is not enabled!");
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Toy is not enabled!");
                    gInteractionCreate.respondMessage(embedBuilder,false);
                    return;
                }

                if(!gotStrength){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid strength!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotStrength&&optionStrength<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid strength!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }

                if(!gotDuration){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotDuration&&optionDuration<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }

                switch (optionAction) {
                    case "1":
                    case "vibrate":
                        lovenseActions4Function = Actions4Function.Vibrate;
                        break;
                    case "2":
                    case "rotate":
                        lovenseActions4Function = Actions4Function.Rotate;
                        break;
                    case "3":
                    case "pump":
                        lovenseActions4Function = Actions4Function.Pump;
                        break;
                    default:
                        //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!");
                        embedBuilder.setColor(iLovense.intColorRed);
                        embedBuilder.setDescription("Invalid action!");
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                }
                break;
            case commandPreset:
                if(!gotIndex){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotIndex&&optionIndex<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid index!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }

                if(!gotDuration){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }
                else if(gotDuration&&optionDuration<=0){
                    embedBuilder.setColor(iLovense.intColorRed);
                    embedBuilder.setDescription("Invalid duration!");
                    interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                    return;
                }

                switch (optionName) {
                    case "0":
                    case "pulse":
                        lovenseActions4Preset=Actions4Preset.Pulse;
                        break;
                    case "1":
                    case "wave":
                        lovenseActions4Preset=Actions4Preset.Wave;
                        break;
                    case "2":
                    case "firework":
                    case "fireworks":
                        lovenseActions4Preset=Actions4Preset.Fireworks;
                        break;
                    case "3":
                    case "earthquake":
                        lovenseActions4Preset=Actions4Preset.Earthquake;
                        break;
                    default:
                        //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Invalid action!");
                        embedBuilder.setColor(iLovense.intColorRed);
                        embedBuilder.setDescription("Invalid preset!");
                        interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
                        return;
                }
                break;
            case commandStop:
                response=gLovense.doStop(toyLoc);
                break;
        }
        */
        if(response==null||!response.isSuccess()||response.getStatus()>299){
            logger.warn(fName + "respose is invalid");
            embedBuilder.setColor(iLovense.intColorRed);
            embedBuilder.setDescription("Failed to perform action.");
            interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).setEphemeral(true).complete();
            return;
        }
        JSONObject jsonObject=response.getBody().getObject();
        if(jsonObject.getBoolean("result")){
            embedBuilder.setColor(iLovense.intColorGreen);
            embedBuilder.setDescription("Successfully performed action.");
            interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
            loggerReq.info("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:success");
        }else{
            embedBuilder.setColor(iLovense.intColorRed);
            embedBuilder.setDescription(jsonObject.getString("message"));
            interactionHook=gSlashCommandEvent.replyEmbeds(embedBuilder.build()).complete();
            loggerReq.warn("author="+gMember.getUser().getName()+"("+gMember.getId()+"), target="+gUserProfile.getMember().getUser().getName()+"("+gUserProfile.getMember().getId()+"), guild="+gGuild.getName()+"("+gGuild.getId()+"), channel="+gTextChannel.getName()+"("+gTextChannel.getId()+"), toyid="+toyLoc.getID()+", status:true, result:failed");
        }
    }

    private void selectMenu(){
        String fName="[selectMenu]";
        logger.info(fName);
        try{
          if(isMenu){
              switch (menuLevel){
                  case 0:menuMain(gTarget);
                  case 1:menuToy(gTarget, selectedToyIndex);
              }
          }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
        }
    }
    private void menuMain(Member mtarget){
        String fName="[menuMain]";
        logger.info(fName);
        try{
            if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
                menuMainSomebody();
            }else{
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                menuMainWearer();
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
        }
    }
    boolean isMenu=false;int menuLevel=0;

    private void menuMainWearer(){
        String fName="[menuMainWearer]";
        logger.info(fName);
        isMenu=true;menuLevel=0;
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        StringBuilder desc3=new StringBuilder();
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
        boolean isSessionValid=gLovense.isNetLastGotTimestampValid();
        logger.info(fName+"isSessionValid="+isSessionValid);
        if(!isSessionValid){
            embed.addField("QRCode","You need to scan a qr code to able to gain control!\nUse "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCamera),false);
        }
        if(gLovense.getToysLocSize()>0){
            for(int i=0;i<gLovense.getToysLocSize();i++){
                entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(i);
                String toyId=toyLoc.getID();
                logger.info(fName+"toyId["+i+"]="+toyId);
                entityLovensenseToyNet toyNet=gLovense.getToyNet(toyId);
                if(toyNet==null||toyNet.getStatus()==0){
                    logger.info(fName+"null or status 0");
                    if(desc3.length()>0)desc3.append("\n");
                    desc3.append("[~~"+i+"~~] "+toyLoc.getName()+" #"+toyLoc.getName()+" ("+toyLoc.getID()+")");
                }else{
                    logger.info(fName+"status not 0");
                    if(desc3.length()>0)desc3.append("\n");
                    desc3.append("["+i+"] "+toyLoc.getName()+" #"+toyLoc.getName()+" ("+toyLoc.getID()+")");
                }
            }
            embed.addField("Toys",desc3.toString(),false);
        }else{
            embed.addField("Toys","n/a",false);
        }
        embed.addField(strDisclaim,strDisclaimMore,false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        if(!isSessionValid){
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCamera));
        }
        if(gLovense.getToysLocSize()>0){
            for(int i=0;i<gLovense.getToysLocSize()&&i<10;i++){
                entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(i);
                String toyId=toyLoc.getID();
                logger.info(fName+"toyId["+i+"]="+toyId);
                switch (i){
                    case 0: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));break;
                    case 1: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));break;
                    case 2: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));break;
                    case 3: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));break;
                    case 4: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));break;
                    case 5: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));break;
                    case 6: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));break;
                    case 7: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));break;
                    case 8: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));break;
                    case 9: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));break;
                }
            }
        }
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent.SelectMenu selectMenu0=messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            if(isSessionValid)component1.getButtonAt4(0).setDisable();
            if(gLovense.getToysLocSize()<1){
                selectMenu0.setDisabled().setPlaceholder("Please get&scan QR Code");
            }else
            for(int i=0;i<10;i++){
                if(i>=gLovense.getToysLocSize()){
                    selectMenu0.getOptionAt(i).setIgnored();
                }else{
                    entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(i);
                    lcMessageBuildComponent.SelectMenu.Option option=selectMenu0.getOptionAt(i);
                    String name="";
                    if(toyLoc.hasNickname()){
                        name=toyLoc.getNickname();
                    }else{
                        name=toyLoc.getName();
                    }
                    option.setLabel(option.getLabel()+" "+name);
                    switch (toyLoc.getType()){
                        case Ambi:
                            option.setEmoji(Emojis.Ambi.getPartialEmoji());
                            break;
                        case Max: case Max1: case Max2:
                            option.setEmoji(Emojis.Max2.getPartialEmoji());
                            break;
                        case Domi: case Domi1: case Domi2:
                            option.setEmoji(Emojis.Domi2.getPartialEmoji());
                            break;
                        case Edge:case Edge1:case Edge2:
                            option.setEmoji(Emojis.Edge2.getPartialEmoji());
                            break;
                        case Hush:
                            option.setEmoji(Emojis.Hush.getPartialEmoji());
                            break;
                        case Lush:case Lush1: case Lush2: case Lush3:
                            option.setEmoji(Emojis.Lush3.getPartialEmoji());
                            break;
                        case Nora:
                            option.setEmoji(Emojis.Nora.getPartialEmoji());
                            break;
                        case Osci: case Osci1: case Osci2:
                            option.setEmoji(Emojis.Osci.getPartialEmoji());
                            break;
                        case Diamo:
                            option.setEmoji(Emojis.Diamo.getPartialEmoji());
                            break;
                        case Ferri:
                            option.setEmoji(Emojis.Ferri.getPartialEmoji());
                            break;
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
        menuMainWearerListener(message);

    }
    private void menuMainWearerListener(Message message){
        String fName="[menuMainWearerListener]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        logger.warn(fName+"value="+value);
                        llMessageDelete(message);
                        int index=-1;
                        switch (value){
                            case lsUnicodeEmotes.alias0:
                                index=0;
                                break;
                            case lsUnicodeEmotes.alias1:
                                index=1;
                                break;
                            case lsUnicodeEmotes.alias2:
                                index=2;
                                break;
                            case lsUnicodeEmotes.alias3:
                                index=3;
                                break;
                            case lsUnicodeEmotes.alias4:
                                index=4;
                                break;
                            case lsUnicodeEmotes.alias5:
                                index=5;
                                break;
                            case lsUnicodeEmotes.alias6:
                                index=6;
                                break;
                            case lsUnicodeEmotes.alias7:
                                index=7;
                                break;
                            case lsUnicodeEmotes.alias8:
                                index=8;
                                break;
                            case lsUnicodeEmotes.alias9:
                                index=9;
                                break;
                        }
                        if(index!=-1){
                            menuToyWearer(index);
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
                        if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                            help("main");
                            return;
                        }
                        switch (id){
                            case lsUnicodeEmotes.aliasCamera:
                                dmGetQrCode();
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
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCamera))){dmGetQrCode();}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){menuToyWearer(0);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){menuToyWearer(1);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){menuToyWearer(2);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){menuToyWearer(3);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){menuToyWearer(4);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){menuToyWearer(5);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){menuToyWearer(6);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){menuToyWearer(7);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){menuToyWearer(8);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){menuToyWearer(9);}
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuMainSomebody(){
        String fName="[menuMainSomebody]";
        logger.info(fName);
        isMenu=true;menuLevel=0;
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="";
        StringBuilder desc3=new StringBuilder();
        embed.setColor(llColorOrange);embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle+" Options");
        boolean isSessionValid=gLovense.isNetLastGotTimestampValid();
        logger.info(fName+"isSessionValid="+isSessionValid);
        if(!isSessionValid){
            embed.addField("QRCode","They need to scan a qr code to able to gain control!",false);
        }
        if(gLovense.getToysLocSize()>0){
            for(int i=0;i<gLovense.getToysLocSize();i++){
                entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(i);
                String toyId=toyLoc.getID();
                logger.info(fName+"toyId["+i+"]="+toyId);
                entityLovensenseToyNet toyNet=gLovense.getToyNet(toyId);
                if(toyNet==null||toyNet.getStatus()==0){
                    logger.info(fName+"null or status 0");
                    if(desc3.length()>0)desc3.append("\n");
                    desc3.append("[~~"+i+"~~] "+toyLoc.getName()+" #"+toyLoc.getName()+" (hidden)");
                }else{
                    logger.info(fName+"status not 0");
                    if(desc3.length()>0)desc3.append("\n");
                    desc3.append("["+i+"] "+toyLoc.getName()+" #"+toyLoc.getName()+" (hidden)");
                }
            }
            embed.addField("Toys",desc3.toString(),false);
        }else{
            embed.addField("Toys","n/a",false);
        }
        embed.addField(strDisclaim,strDisclaimMore,false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        if(!isSessionValid){
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCamera));
        }
        if(gLovense.getToysLocSize()>0){
            for(int i=0;i<gLovense.getToysLocSize()&&i<10;i++){
                entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(i);
                String toyId=toyLoc.getID();
                logger.info(fName+"toyId["+i+"]="+toyId);
                switch (i){
                    case 0: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0));break;
                    case 1: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1));break;
                    case 2: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2));break;
                    case 3: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3));break;
                    case 4: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4));break;
                    case 5: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5));break;
                    case 6: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6));break;
                    case 7: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7));break;
                    case 8: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8));break;
                    case 9: lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9));break;
                }
            }
        }
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent.SelectMenu selectMenu0=messageComponentManager.messageBuildComponents.getComponent(0).getSelect();
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            if(isSessionValid)component1.getButtonAt4(0).setDisable();
            if(gLovense.getToysLocSize()<1){
                selectMenu0.setDisabled().setPlaceholder("No toys");
            }else
            for(int i=0;i<10;i++){
                if(i>=gLovense.getToysLocSize()){
                    selectMenu0.getOptionAt(i).setIgnored();
                }else{
                    entityLovensenseToyLoc toyLoc=gLovense.getToyLoc(i);
                    lcMessageBuildComponent.SelectMenu.Option option=selectMenu0.getOptionAt(i);
                    String name="";
                    if(toyLoc.hasNickname()){
                        name=toyLoc.getNickname();
                    }else{
                        name=toyLoc.getName();
                    }
                    option.setLabel(option.getLabel()+" "+name);
                    switch (toyLoc.getType()){
                        case Ambi:
                            option.setEmoji(Emojis.Ambi.getPartialEmoji());
                            break;
                        case Max: case Max1: case Max2:
                            option.setEmoji(Emojis.Max2.getPartialEmoji());
                            break;
                        case Domi: case Domi1: case Domi2:
                            option.setEmoji(Emojis.Domi2.getPartialEmoji());
                            break;
                        case Edge:case Edge1:case Edge2:
                            option.setEmoji(Emojis.Edge2.getPartialEmoji());
                            break;
                        case Hush:
                            option.setEmoji(Emojis.Hush.getPartialEmoji());
                            break;
                        case Lush:case Lush1: case Lush2: case Lush3:
                            option.setEmoji(Emojis.Lush3.getPartialEmoji());
                            break;
                        case Nora:
                            option.setEmoji(Emojis.Nora.getPartialEmoji());
                            break;
                        case Osci: case Osci1: case Osci2:
                            option.setEmoji(Emojis.Osci.getPartialEmoji());
                            break;
                        case Diamo:
                            option.setEmoji(Emojis.Diamo.getPartialEmoji());
                            break;
                        case Ferri:
                            option.setEmoji(Emojis.Ferri.getPartialEmoji());
                            break;
                        default:
                            option.setLabel(option.getLabel()+" "+name);
                            break;
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
        menuMainSomebodyListener(message);
    }
    private void menuMainSomebodyListener(Message message){
        String fName="[menuMainSomebodyListener]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        logger.warn(fName+"value="+value);
                        llMessageDelete(message);
                        int index=-1;
                        switch (value){
                            case lsUnicodeEmotes.alias0:
                                index=0;
                                break;
                            case lsUnicodeEmotes.alias1:
                                index=1;
                                break;
                            case lsUnicodeEmotes.alias2:
                                index=2;
                                break;
                            case lsUnicodeEmotes.alias3:
                                index=3;
                                break;
                            case lsUnicodeEmotes.alias4:
                                index=4;
                                break;
                            case lsUnicodeEmotes.alias5:
                                index=5;
                                break;
                            case lsUnicodeEmotes.alias6:
                                index=6;
                                break;
                            case lsUnicodeEmotes.alias7:
                                index=7;
                                break;
                            case lsUnicodeEmotes.alias8:
                                index=8;
                                break;
                            case lsUnicodeEmotes.alias9:
                                index=9;
                                break;
                        }
                        if(index!=-1){
                            menuToyWearer(index);
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
                        if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource)){
                            help("main");
                            return;
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
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias0))){menuToySomebody(0);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){menuToySomebody(1);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){menuToySomebody(2);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){menuToySomebody(3);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){menuToySomebody(4);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){menuToySomebody(5);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias6))){menuToySomebody(6);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias7))){menuToySomebody(7);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias8))){menuToySomebody(8);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias9))){menuToySomebody(9);}
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    int selectedToyIndex =0; entityLovensenseToyLoc selectedToy=new entityLovensenseToyLoc(gGlobal);
    private void menuToy(Member mtarget,int index){
        String fName="[menuMain]";
        logger.info(fName);
        try{
            if(mtarget!=null&&mtarget.getIdLong()!=gMember.getIdLong()){
                if(!getProfile(mtarget)){logger.error(fName + ".can't get profile"); return;}
                menuToySomebody(index);
            }else{
                if(!getProfile(gMember)){logger.error(fName + ".can't get profile"); return;}
                menuToyWearer(index);
            }
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
        }
    }
    private void menuToyWearer(int index){
        String fName="[menuToyWearer]";
        logger.info(fName+"index="+index);
        isMenu=true;menuLevel=1;
        selectedToyIndex =index;
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="",desc2="";
        StringBuilder desc3=new StringBuilder();
        embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
        selectedToyIndex =0;selectedToy=new entityLovensenseToyLoc(gGlobal);
        if(gLovense.hasToyLoc(index))selectedToy=gLovense.getToyLoc(index);
        int toyStatus=0;
        boolean toyValid=false,toyEnabled=false,toyPublic=false;
        if(!selectedToy.hasID()){
            embed.addField("Toy","n/a",false);
        }else{
            toyValid=true;
            desc="id: "+selectedToy.getID();
            desc+="\nname: "+selectedToy.getName();
            desc+="\nnickname: "+selectedToy.getNickname();
            toyEnabled=selectedToy.isEnabled();
            toyPublic=selectedToy.isPublic();
            if(toyEnabled){
                desc+="\nenabled: true";
            }else{
                desc+="\nenabled: false";
            }
            if(toyPublic){
                desc+="\npublic: true";
            }else{
                desc+="\npublic: false";
            }
            entityLovensenseToyNet toyNet=gLovense.getToyNet(selectedToy.getID());
            if(toyNet!=null){
                toyStatus=toyNet.getStatus();
                if(toyStatus!=0){
                    desc+="\nstatus: online ("+toyStatus+")";
                }else{
                    desc+="\nstatus: offline ("+toyStatus+")";
                }
            }else{
                desc+="\nstatus: not present";
            }
            embed.addField("Toy",desc,false);
        }
        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" send function";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" send preset";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStopSign)+" stop action";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+"/"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" for enable/disable send commands to toy";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+"/"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" for allow public access or restrict to private";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark)+" remove";
        embed.addField("Commands",desc,false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        if(toyValid){
            if(toyStatus>0){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStopSign));
            }
            if(toyEnabled){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle));
            }
            if(toyPublic){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock));
            }else{
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock));
            }
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
        }
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gToyFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            lcMessageBuildComponent.Button buttonPublicPrivate=component1.getButtonById("public/private");
            lcMessageBuildComponent.Button buttonOnOff=component1.getButtonById("on/off");
            lcMessageBuildComponent.Button buttonRemove=component1.getButtonById("x");
            if(toyStatus==0){
                component0.getButtonAt4(0).setDisable();
                component0.getButtonAt4(1).setDisable();
                component0.getButtonAt4(2).setDisable();
            }
            if(toyPublic){
                buttonPublicPrivate.setCustomId(lsUnicodeEmotes.aliasLock).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock)+" Lock");
            }else{
                buttonPublicPrivate.setCustomId(lsUnicodeEmotes.aliasUnlock).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock)+" Unlock");
            }
            if(toyEnabled){
                buttonOnOff.setCustomId(lsUnicodeEmotes.aliasRedCircle).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle)+" Disable");
            }else{
                buttonOnOff.setCustomId(lsUnicodeEmotes.aliasGreenCircle).setLabel(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle)+" Enable");
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuToyWearerListener(message,index);
    }
    private void menuToySomebody(int index){
        String fName="[menuToySomebody]";
        logger.info(fName+"index="+index);
        isMenu=true;menuLevel=1;
        selectedToyIndex =index;
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);
        String desc="",desc2="";
        StringBuilder desc3=new StringBuilder();
        embed.setColor(llColorOrange);embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle+" Options");
        selectedToyIndex =0;selectedToy=new entityLovensenseToyLoc(gGlobal);
        if(gLovense.hasToyLoc(index))selectedToy=gLovense.getToyLoc(index);
        int toyStatus=0;
        boolean toyValid=false,toyEnabled=false,toyPublic=false;
        if(!selectedToy.hasID()){
            embed.addField("Toy","n/a",false);
        }else{
            toyValid=true;
            desc="id: (hidden)";
            desc+="\nname: "+selectedToy.getName();
            desc+="\nnickname: "+selectedToy.getNickname();
            toyEnabled=selectedToy.isEnabled();
            toyPublic=selectedToy.isPublic();
            if(toyEnabled){
                desc+="\nenabled: true";
            }else{
                desc+="\nenabled: false";
            }
            if(toyPublic){
                desc+="\npublic: true";
            }else{
                desc+="\npublic: false";
            }
            entityLovensenseToyNet toyNet=gLovense.getToyNet(selectedToy.getID());
            if(toyNet!=null){
                toyStatus=toyNet.getStatus();
                if(toyStatus!=0){
                    desc+="\nstatus: online ("+toyStatus+")";
                }else{
                    desc+="\nstatus: offline ("+toyStatus+")";
                }
            }else{
                desc+="\nstatus: not present";
            }
            embed.addField("Toy",desc,false);
        }
        desc=gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF)+" send function";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP)+" send preset";
        desc+="\n"+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStopSign)+" stop action";
        embed.addField("Commands",desc,false);
        /*Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
        if(toyValid){
            if(toyStatus>0&&toyPublic){
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP));
                lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStopSign));
            }
        }
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward));
        lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));*/
        Message message=null;
        messageComponentManager.loadMessageComponents(gToyFilePath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            if(toyStatus==0||!toyPublic){
                component0.getButtonAt4(0).setDisable();
                component0.getButtonAt4(1).setDisable();
                component0.getButtonAt4(2).setDisable();
            }
            component1.setIgnored();
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuToySomebodyListener(message,index);
    }
    private void menuToyWearerListener(Message message,int index){
        String fName="[menuToyWearerListener]";
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
                                menuMain(gTarget);
                                return;
                            case lsUnicodeEmotes.aliasGreenCircle:
                                rToy(commandEnable);
                                break;
                            case lsUnicodeEmotes.aliasRedCircle:
                                rToy(commandDisable);
                                break;
                            case lsUnicodeEmotes.aliasUnlock:
                                rToy(commandPublic);
                                break;
                            case lsUnicodeEmotes.aliasLock:
                                rToy(commandPrivate);
                                break;
                            case lsUnicodeEmotes.aliasCrossMark:
                                rToy(commandRemove);
                                break;
                            case lsUnicodeEmotes.aliasStopSign:
                                rToyFunction(selectedToy,"0","","","");
                                break;
                            case lsUnicodeEmotes.aliasSymbolF:
                                dmFunctionWearer();
                                break;
                            case lsUnicodeEmotes.aliasSymbolP:
                                dmPresetWearer();
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
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){menuMain(gTarget);}
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                            selectedToy.setEnabled(true);
                            gLovense.updateToyLoc(selectedToy);
                            if(!saveProfile()){
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                                return;
                            }
                            menuToy(gTarget,selectedToyIndex);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasRedCircle))){
                            selectedToy.setEnabled(false);
                            gLovense.updateToyLoc(selectedToy);
                            if(!saveProfile()){
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                                return;
                            }
                            menuToy(gTarget,selectedToyIndex);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasUnlock))){
                            selectedToy.setPublic(true);
                            gLovense.updateToyLoc(selectedToy);
                            if(!saveProfile()){
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                                return;
                            }
                            menuToy(gTarget,selectedToyIndex);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasLock))){
                            selectedToy.setPublic(false);
                            gLovense.updateToyLoc(selectedToy);
                            if(!saveProfile()){
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                                return;
                            }
                            menuToy(gTarget,selectedToyIndex);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark))){
                            gLovense.remToyLoc(selectedToy);
                            if(!saveProfile()){
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                                return;
                            }
                            menuMain(gTarget);
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStopSign))){
                            rToyFunction(selectedToy,"0","","","");
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))){
                            dmFunctionWearer();
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))){
                            dmPresetWearer();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void menuToySomebodyListener(Message message,int index){
        String fName="[menuToySomebodyListener]";
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
                                menuMain(gTarget);
                                return;
                            case lsUnicodeEmotes.aliasStopSign:
                                rToyFunction(selectedToy,"0","","","");
                                break;
                            case lsUnicodeEmotes.aliasSymbolF:
                                dmFunctionWearer();
                                break;
                            case lsUnicodeEmotes.aliasSymbolP:
                                dmPresetWearer();
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
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){menuMain(gTarget);}
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasStopSign))){
                            rToyFunction(gTarget,selectedToy,  "0","0","0","0");
                        }
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolF))){
                            dmFunctionSomebody();
                        }
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasSymbolP))){
                            dmPresetSomebody();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
    }
    private void rToy(String command) {
        String fName = "[rToy]";
        logger.info(fName);
        logger.info(fName + ".command=" + command);
        switch(command.toLowerCase()){
            case iLovense.commandPublic:
                selectedToy.setPublic(true);
                gLovense.updateToyLoc(selectedToy);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                    return;
                }
                if(isMenu)menuToy(gTarget,selectedToyIndex);
                break;
            case iLovense.commandPrivate:
                selectedToy.setPublic(false);
                gLovense.updateToyLoc(selectedToy);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                    return;
                }
                if(isMenu)menuToy(gTarget,selectedToyIndex);
                break;
            case iLovense.commandEnable:
                selectedToy.setEnabled(true);
                gLovense.updateToyLoc(selectedToy);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                    return;
                }
                if(isMenu)menuToy(gTarget,selectedToyIndex);
                break;
            case iLovense.commandDisable:
                selectedToy.setEnabled(false);
                gLovense.updateToyLoc(selectedToy);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                    return;
                }
                if(isMenu)menuToy(gTarget,selectedToyIndex);
                break;
            case iLovense.commandRemove:
                gLovense.remToyLoc(selectedToy);
                if(!saveProfile()){
                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gUser,gTitle,"Failed to save!");
                    return;
                }
                if(isMenu)menuMain(gTarget);
                break;
        }
    }
    private void dmGetQrCode(){
        String fName="[dmGetQrCode]";
        logger.info(fName);
        try{
            isMenu=true;
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="",desc2="";
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" QR Code");
            HttpResponse<JsonNode> response=gLovense.reqGetQrCode(gGuild,gMember);
            if(!response.isSuccess()){
                embed.setDescription("Failed to get qr code!");
            }else{
                JSONObject jsonObject=response.getBody().getObject();
                logger.info(fName+"jsonObject="+jsonObject.toString());
                if(jsonObject.has("result")&&jsonObject.getBoolean("result")){
                    embed.setImage(jsonObject.getString("message"));
                    embed.setDescription("Please scan the qr code and once done click "+gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark)+".\n**You may need to re-open the menu once you scanned it!**");
                }else{
                    embed.setDescription("Failed to get qr code!");
                }
            }
            Message message=llSendMessageResponse_withReactionNotification(gUser,embed);
            lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark));
            //lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasCrossMark));
            saveProfile();
            addReq2Que(message);
            gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                    e -> {
                        try {
                            String name=e.getReactionEmote().getName();
                            logger.warn(fName+"name="+name);
                            String asCodepoints=e.getReactionEmote().getAsCodepoints();
                            logger.warn(fName+"asCodepoints="+name);
                            String level="";
                            llMessageDelete(message);
                            /*if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasWhiteCheckMark))){
                                getProfile(gMember, false, false, true,true);
                                menuMain(null);
                            }*/
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
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
        }
    }
    private void dmFunctionWearer(){
        String fName="[dmFunctionWearer]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
            if(selectedToy.isVibratingType()){
                desc+="vibrate";
            }
            if(selectedToy.isRotateType()){
                if(desc.length()>0)desc+=", ";
                desc+="rotate";
            }
            if(selectedToy.isPumpType()){
                if(desc.length()>0)desc+=", ";
                desc+="pump";
            }
            if(!desc.isBlank())embed.addField("Recommended actions",desc,false);
            desc="Please enter toy ["+selectedToyIndex+"]";
            if(selectedToy.hasNickname())desc+=" "+selectedToy.getNickname();
            if(selectedToy.hasName())desc+=" #"+selectedToy.getName();
            if(selectedToy.hasID())desc+=" ("+selectedToy.getID()+")";
            desc+=" function(s) to send. Or type `!cancel` to abort.";
            desc+="\nfunction format: `<actions><space><total time><space><loop running time *><space><loop pause time *>`";
            desc+="\n`actions` at least 1 action needs to be provided, following actions: vibrate, rotate, pump, stop.";
            desc+="\nactions format: `<action>:<strength>,<action>:<strength>` for actions: vibrate, rotate, pump.";
            desc+="\n`total time`, is required (except for stop), is a numeric value the total time the toy runs in seconds";
            desc+="\n`loop running time` and `loop pause time` are optional numeric values, they not required";
            desc+="\nexample `vibrate:20,rotate:2 25`";
            embed.setDescription(desc);
            Message message=lsMessageHelper.lsSendMessageResponse(gUser,embed);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuToy(null,selectedToyIndex);return;
                            }
                            else{
                                String[] items= content.split("\\s+");
                                String reqActions="",reqTimeSec="",reqLoopRunningSec="",reqLoopPauseSec="";
                                if(items.length>=1)reqActions=items[0];
                                if(items.length>=2)reqTimeSec=items[1];
                                if(items.length>=3)reqLoopRunningSec=items[2];
                                if(items.length>=4)reqLoopPauseSec=items[3];
                                rToyFunction(selectedToy,  reqActions,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
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

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void dmFunctionSomebody(){
        String fName="[dmFunctionSomebody]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle+" Options");
            if(selectedToy.isVibratingType()){
                desc+="vibrate";
            }
            if(selectedToy.isRotateType()){
                if(desc.length()>0)desc+=", ";
                desc+="rotate";
            }
            if(selectedToy.isPumpType()){
                if(desc.length()>0)desc+=", ";
                desc+="pump";
            }
            if(!desc.isBlank())embed.addField("Recommended actions",desc,false);
            desc="Please enter toy ["+selectedToyIndex+"]";
            if(selectedToy.hasNickname())desc+=" "+selectedToy.getNickname();
            if(selectedToy.hasName())desc+=" #"+selectedToy.getName();
            desc+=" function(s) to send. Or type `!cancel` to abort.";
            desc+="\nfunction format: `<actions><space><total time><space><loop running time *><space><loop pause time *>`";
            desc+="\n`actions` at least 1 action needs to be provided, following actions: vibrate, rotate, pump, stop.";
            desc+="\nactions format: `<action>:<strength>,<action>:<strength>` for actions: vibrate, rotate, pump.";
            desc+="\n`total time`, is required (except for stop), is a numeric value the total time the toy runs in seconds";
            desc+="\n`loop running time` and `loop pause time` are optional numeric values, they not required";
            desc+="\nexample `vibrate:20,rotate:2 25`";
            embed.setDescription(desc);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuToy(gTarget,selectedToyIndex);return;
                            }
                            else{
                                String[] items= content.split("\\s+");
                                String reqActions="",reqTimeSec="",reqLoopRunningSec="",reqLoopPauseSec="";
                                if(items.length>=1)reqActions=items[0];
                                if(items.length>=2)reqTimeSec=items[1];
                                if(items.length>=3)reqLoopRunningSec=items[2];
                                if(items.length>=4)reqLoopPauseSec=items[3];
                                rToyFunction(gTarget,selectedToy,  reqActions,reqTimeSec,reqLoopRunningSec,reqLoopPauseSec);
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

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void dmPresetWearer(){
        String fName="[dmPresetWearer]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");
            desc="Please enter toy ["+selectedToyIndex+"]";
            if(selectedToy.hasNickname())desc+=" "+selectedToy.getNickname();
            if(selectedToy.hasName())desc+=" #"+selectedToy.getName();
            if(selectedToy.hasID())desc+=" ("+selectedToy.getID()+")";
            desc+=" preset(s) to send. Or type `!cancel` to abort.";
            desc+="\npreset format: `<name><space><total time>`";
            desc+="\n`name` is required, following names: pulse, wave, fireworks, earthquake";;
            desc+="\n`total time`, is required, is a numeric value the total time the toy runs in seconds";
            desc+="\nexample `pulse 5`";
            embed.setDescription(desc);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuToy(null,selectedToyIndex);return;
                            }
                            else{
                                String[] items= content.split("\\s+");
                                String reqActions="",reqTimeSec="";
                                if(items.length>=1)reqActions=items[0];
                                if(items.length>=2)reqTimeSec=items[1];
                                rToyPreset(selectedToy,  reqActions,reqTimeSec);
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

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private void dmPresetSomebody(){
        String fName="[dmPresetSomebody]";
        logger.info(fName);
        try{
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(gTarget.getUser().getName()+"'s "+gTitle+" Options");
            desc="Please enter toy ["+selectedToyIndex+"]";
            if(selectedToy.hasNickname())desc+=" "+selectedToy.getNickname();
            if(selectedToy.hasName())desc+=" #"+selectedToy.getName();
            desc+=" preset(s) to send. Or type `!cancel` to abort.";
            desc+="\npreset format: `<name><space><total time>`";
            desc+="\n`name` is required, following names: pulse, wave, fireworks, earthquake";;
            desc+="\n`total time`, is required, is a numeric value the total time the toy runs in seconds";
            desc+="\nexample `pulse 5`";
            embed.setDescription(desc);
            Message message=lsMessageHelper.lsSendMessageResponse_withReactionNotification(gUser,embed);
            gGlobal.waiter.waitForEvent(PrivateMessageReceivedEvent.class,
                    e -> e.getAuthor().equals(gUser),
                    e -> {
                        try {
                            String content = e.getMessage().getContentStripped();
                            logger.info(fName+".content="+content);
                            if(content.isBlank()||content.equalsIgnoreCase("!cancel")){
                                //nothing or canceled >return
                                menuToy(gTarget,selectedToyIndex);return;
                            }
                            else{
                                String[] items= content.split("\\s+");
                                String reqActions="",reqTimeSec="";
                                if(items.length>=1)reqActions=items[0];
                                if(items.length>=2)reqTimeSec=items[1];
                                rToyPreset(gTarget,selectedToy,  reqActions,reqTimeSec);
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

        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    private Boolean getProfile(Member member){
        String fName="[getProfile]";
        return getProfile(member, true, true, false,false);
    }
    private Boolean getProfile(Member member, boolean checkIfCache, boolean checkIFLoaded, boolean skipSave2Cache, boolean skipSave2Db){
        String fName="[getProfile]";
        logger.info(fName + "checkIfCache="+checkIfCache+", checkIFLoaded="+checkIFLoaded);
        logger.info(fName + "skipSave2Cache="+skipSave2Cache+", skipSave2Db="+skipSave2Db);
        logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
        if(checkIFLoaded){
            if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.getMember()!=null&&gUserProfile.getMember().getIdLong()==member.getIdLong()){
                logger.info(fName + ".already present>skip");
                logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
                return true;
            }
        }else{
            logger.info(fName + ".skip checking if loaded");
        }
        if(checkIfCache){
            gUserProfile=gGlobal.getUserProfile(iLovense.profileName,member);
        }else{
            logger.info(fName + ".skip checking if cached");
        }

        if(gUserProfile!=null&&gUserProfile.isProfile()){
            logger.info(fName + ".is locally cached");
        }else{
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(gGlobal,member,iLovense.profileName);
            if(gUserProfile.getProfile(iLovense.table)){
                logger.info(fName + ".has sql entry");
            }
        }
        logger.info(fName + "gUserProfile.jsonUser.beforeupdate:"+ gUserProfile.jsonObject.toString());
        gUserProfile= iLovense.sUserInit(gUserProfile);
        logger.info(fName + "gUserProfile.jsonUser.afterupdate:"+ gUserProfile.jsonObject.toString());
        if(!skipSave2Cache){gGlobal.putUserProfile(gUserProfile,iLovense.profileName);}else{
            logger.info(fName + ".skip save to cached");
        }
        gLovense.set(gUserProfile.jsonObject);
        if(!skipSave2Db){
            if(!saveProfile()){ logger.error(fName+".failed to write in Db");
                llSendQuickEmbedMessageWithDelete(gGlobal,true,gTextChannel,gTitle,"Failed to write in Db!", llColorRed);
            }
        }else{
            logger.info(fName + ".skip save to db");
        }
        return true;
    }
    private Boolean saveProfile(){
        String fName="[saveProfile]";
        logger.info(fName);
        gUserProfile.jsonObject =gLovense.getJSON();
        gGlobal.putUserProfile(gUserProfile,iLovense.profileName);
        if(gUserProfile.saveProfile(iLovense.table)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }
    public void addReq2Que(Message message) {
        String fName = "[addReq2Que]";
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(llCommonKeys.keyMessage_id,message.getId());
            jsonObject.put(llCommonKeys.keyPrivateChannel,message.getChannel().getId());
            jsonObject.put(llCommonKeys.keyTextChannel,gTextChannel.getId());
            jsonObject.put("status",1);
            if(!gGlobal.jsonObject.has(keyLovense)){
                gGlobal.jsonObject.put(keyLovense,new JSONObject());
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).has(llCommonKeys.keyMembers)){
                gGlobal.jsonObject.getJSONObject(keyLovense).put(llCommonKeys.keyMembers,new JSONObject());
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).has(gMember.getId())){
                gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).put(gMember.getId(),new JSONObject());
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).has(llCommonKeys.keyGuilds)){
                gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).put(llCommonKeys.keyGuilds,new JSONObject());
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).has(gGuild.getId())){
                gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).put(gGuild.getId(),new JSONObject());
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(gGuild.getId()).has(llCommonKeys.keyMessages)){
                gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(gGuild.getId()).put(llCommonKeys.keyMessages,new JSONArray());
            }
            gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(gGuild.getId()).getJSONArray(llCommonKeys.keyMessages).put(jsonObject);
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed);
        }

    }
    public void delReq2Que() {
        String fName = "[delReq2Que]";
        try{

            if(!gGlobal.jsonObject.has(keyLovense)){
                logger.info("no such key "+keyLovense);
                return;
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).has(llCommonKeys.keyMembers)){
                logger.info("no such key "+keyLovense+"/"+llCommonKeys.keyMembers);
                return;
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).has(gMember.getId())){
                logger.info("no such key "+keyLovense+"/"+llCommonKeys.keyMembers+"/"+gMember.getId());
                return;
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).has(llCommonKeys.keyGuilds)){
                logger.info("no such key "+keyLovense+"/"+llCommonKeys.keyMembers+"/"+llCommonKeys.keyMembers+"/"+llCommonKeys.keyGuilds);
                return;
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).has(gGuild.getId())){
                logger.info("no such key "+keyLovense+"/"+llCommonKeys.keyMembers+"/"+llCommonKeys.keyMembers+"/"+llCommonKeys.keyGuilds+"/"+gGuild.getId());
                return;
            }
            if(!gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(gGuild.getId()).has("messages")){
                logger.info("no such key "+keyLovense+"/"+llCommonKeys.keyMembers+"/"+llCommonKeys.keyMembers+"/"+llCommonKeys.keyGuilds+"/"+gGuild.getId()+"/messages");
                return;
            }
            JSONArray jsonArray=gGlobal.jsonObject.getJSONObject(keyLovense).getJSONObject(llCommonKeys.keyMembers).getJSONObject(gMember.getId()).getJSONObject(llCommonKeys.keyGuilds).getJSONObject(gGuild.getId()).getJSONArray("messages");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if(jsonObject.optInt("status",0)==1){
                    PrivateChannel privateChannel=lsMessageHelper.lsOpenPrivateChannel(gUser);
                    boolean response=lsMessageHelper.lsMessageDeleteStatus(privateChannel,jsonObject.optString(llCommonKeys.keyMessage_id,""));
                    jsonObject.put("status",2);
                }
            }
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));
            llSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColorRed);
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
                    },5, TimeUnit.MINUTES, () -> {
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
