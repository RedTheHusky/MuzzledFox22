package nsfw.faprulette;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONObject;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lc.json.lcText2Json;
import models.lc.lcBasicFeatureControl;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import org.jsoup.internal.StringUtil;
import restraints.models.entityRDUserProfile;
import restraints.models.lcBDSMGuildProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FapRoulette extends Command implements  iFapRoulette, llMessageHelper {
        Logger logger = Logger.getLogger(getClass());


        lcGlobalHelper gGlobal;
        String gTitle="FapRoulette [WIP]",gCommand="faproulette";
        JSONObject rfEntries;
    public FapRoulette(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "FapRoulette";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        rfEntries=new JSONObject();
        this.guildOnly = true;this.category= llGlobalHelper.llCommandCategory_NSFW;
        this.hidden=true;
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    public FapRoulette(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }

protected class runLocal implements Runnable {
    CommandEvent gEvent;
    User gUser;Member gMember;
    Guild gGuild;
    TextChannel gTextChannel;
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
        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
    }
    public runLocal(SlashCommandEvent ev) {
        String fName="runLoccal";
        logger.info(".run build");
        gSlashCommandEvent = ev;
        gUser = ev.getUser();
        logger.info(fName + ".user:" + gUser.getId() + "|" + gUser.getName());
        if(ev.isFromGuild()){
            gMember=ev.getMember();
            gGuild = ev.getGuild();
            gTextChannel =ev.getTextChannel();
            logger.info(fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
            logger.info(fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
        }
    }
    lcBDSMGuildProfiles gBDSMCommands;
    @Override
    public void run() {
        String fName = "[run]";
        logger.info(".run start");

        Boolean isInvalidCommand = true;
        try{
            gBasicFeatureControl=new lcBasicFeatureControl(gGuild,"FapRoulette",gGlobal);
            gBasicFeatureControl.initProfile();
            selelectedgame=GUILD.getNullGame();
            if(gSlashCommandEvent!=null) {
                logger.info(fName + "slash@");
                SlashNT();
            }else
            if(gEvent!=null){
                logger.info(fName + "basic@");
                if(!isNSFW()){
                    blocked();return;
                }
                if(gArgs.isEmpty()){
                    logger.info(fName+".Args=0");
                /*help("main");
                gTextChannel.sendMessage(" I sent you a list of commands in DMs").queue();*/
                    menuMain(0);
                    isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    /*if(gArgs.contains(llOverride)&&(lsMemberHelper.lsMemberIsAdministrator(gMember)||lsMemberHelper.lsMemberIsManager(gMember)||lsMemberHelper.lsMemberIsModerator(gMember)||lsMemberHelper.lsMemberIsBotOwner(gMember))){
                        gIsOverride =true;
                        gArgs=gArgs.replaceAll(llOverride,"");
                    }*/
                    if(gArgs.contains(llGlobalHelper.llOverride)&&lsMemberHelper.lsMemberIsBotOwner(gMember)){
                        gIsOverride =true;
                        gArgs=gArgs.replaceAll(llGlobalHelper.llOverride,"");
                    }
                    gItems = gArgs.split("\\s+");
                    logger.info(fName + ".gItems.size=" + gItems.length);
                    logger.info(fName + ".gItems[0]=" + gItems[0]);
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
                            target=lsMemberHelper.lsGetMember(gGuild,gItems[0]);
                        }else{
                            logger.info(fName+".member mentions in message");
                            target=mentions.get(0);
                        }
                        if(target==null){
                            logger.warn(fName+".zero member mentions");
                        }
                        else if(target.getId().equalsIgnoreCase(gEvent.getAuthor().getId())){
                            logger.warn(fName+".target cant be the gUser");gItems= lsUsefullFunctions.RemoveFirstElement4ItemsArg(gItems);
                         
                        }else {
                            gTarget=target;
                            if(gItems.length<2){
                                logger.warn(fName+".invalid args length");
                                menuMain(0);isInvalidCommand=false;
                            }else{
                                gTarget=target;
                            }
                        }
                        
                    }
                    if(isInvalidCommand){
                        gTarget=null;
                        switch (gItems[0].toLowerCase()){
                            case "game0":case "0":
                                globalGameBase_Ask(0);
                                isInvalidCommand=false;
                                break;
                            case "game1":case "1":
                                globalGameBase_Ask(1);
                                isInvalidCommand=false;
                                break;
                            case "game2":case "2":
                                globalGameBase_Ask(2);
                                isInvalidCommand=false;
                                break;
                            case "game3":case "3":
                                globalGameBase_Ask(3);
                                isInvalidCommand=false;
                                break;
                            case "game4":case "4":
                                globalGameBase_Ask(4);
                                isInvalidCommand=false;
                                break;
                            case "game5":case "5":
                                globalGameBase_Ask(5);
                                isInvalidCommand=false;
                                break;
                        }
                    }


                }
        /*logger.info(fName+".deleting op message");
        llQuckCommandMessageDelete(gEvent);*/
                if(isInvalidCommand){
                    lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"You provided an incorrect command!", llColors.llColorRed);
                }
            }


        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);logger.error(".exception:" + Arrays.toString(ex.getStackTrace()));lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Exception:"+ex, llColors.llColorRed); }
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
        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,"Require NSFW channel or server.",llColors.llColorRed);
        logger.info(fName);
    }
    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llGlobalHelper.llPrefixStr+gCommand+" ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColors.llColorBlue1);embed.setTitle(gTitle);
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
    }
    boolean isManage=false,isMenu=false;int menuIndex=-1;
    private void menuMain(int index){
        String fName="[menuMain]";
        logger.info(fName+"index="+index);
        getGuildProfile();
        getMemberProfile();
        if(!readFile()){
            logger.warn(fName+"failed to read file");
        }
        if(lsMemberHelper.lsMemberHasPermission_MANAGESERVER(gMember)||lsMemberHelper.lsMemberIsAdministrator(gMember)||lsMemberHelper.lsMemberIsBotOwner(gMember)){
            menuMain_Admin(index);
        }else{
            menuMain_User(index);
        }
    }
    private void menuMain_Admin(int index){
        String fName="[menuMain_Admin]";
        logger.info(fName+"index="+index);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle).setColor(llColorPink2);
        String desc="";
        int gamesSize=GUILD.global.gamesSize();
        logger.info(fName+"gamesSize="+gamesSize);
        for(int i=0;i<5;i++){
            logger.info(fName+"(index+i)<gamesSize="+(index+i)+"<"+gamesSize);
            if((index+i)<gamesSize){
               entityGuild.GAME game=GUILD.global.getGameAt(index+i);
               if(game!=null){
                   logger.info(fName+"game(index+i) is not null");
                   embed.addField(String.valueOf(i+1)+" "+game.getName(),game.getDescription(),false);
               }else{
                   logger.info(fName+"game(index+i) is null");
               }
            }
        }
        embed.addField("Comming Soon","Guilds can upload custom json to define guild side roulettes.",false);
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainMenuPath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            for(int i=0;i<5;i++){
                logger.info(fName+"(index+i)>=gamesSize="+(index+i)+">="+gamesSize);
                if((index+i)>=gamesSize){
                    logger.info(fName+"(index+i)"+(index+i)+" button disable");
                    component0.getButtonAt4(i).setDisable();
                }
            }
            if(index<5){
                logger.info(fName+"index not past first level");
            }
            if(index<5){
                logger.info(fName+"set backward button disable");
                component1.getButtonAt4(0).setDisable();
            }
            if(gamesSize<5){
                logger.info(fName+"insufficient gamesize");
            }
            if(index+5>=gamesSize){
                logger.info(fName+"index at end");
            }
            if(gamesSize<5||index+5>=gamesSize){
                logger.info(fName+"set froward button disable");
                component1.getButtonAt4(1).setDisable();
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuMainListener_Admin(message,index);
    }
    private void menuMain_User(int index){
        String fName="[menuMain_User]";
        logger.info(fName+"index="+index);
        EmbedBuilder embed=new EmbedBuilder();
        embed.setTitle(gTitle).setColor(llColorPink2);
        String desc="";
        int gamesSize=GUILD.global.gamesSize();
        logger.info(fName+"gamesSize="+gamesSize);
        for(int i=0;i<5;i++){
            logger.info(fName+"(index+i)<gamesSize="+(index+i)+"<"+gamesSize);
            if((index+i)<gamesSize){
                entityGuild.GAME game=GUILD.global.getGameAt(index+i);
                if(game!=null){
                    logger.info(fName+"game(index+i) is not null");
                    embed.addField(String.valueOf(i+1)+" "+game.getName(),game.getDescription(),false);
                }else{
                    logger.info(fName+"game(index+i) is null");
                }
            }
        }
        embed.addField("Comming Soon","Guilds can upload custom json to define guild side roulettes.",false);
        Message message=null;
        messageComponentManager.loadMessageComponents(gMainMenuPath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        try {
            lcMessageBuildComponent component0=messageComponentManager.messageBuildComponents.getComponent(0);
            lcMessageBuildComponent component1=messageComponentManager.messageBuildComponents.getComponent(1);
            for(int i=0;i<5;i++){
                logger.info(fName+"(index+i)>=gamesSize="+(index+i)+">="+gamesSize);
                if((index+i)>=gamesSize){
                    logger.info(fName+"(index+i)"+(index+i)+" button disable");
                    component0.getButtonAt4(i).setDisable();
                }
            }
            if(index<5){
                logger.info(fName+"index not past first level");
            }
            if(index<5){
                logger.info(fName+"set backward button disable");
                component1.getButtonAt4(0).setDisable();
            }
            if(gamesSize<5){
                logger.info(fName+"insufficient gamesize");
            }
            if(index+5>=gamesSize){
                logger.info(fName+"index at end");
            }
            if(gamesSize<5||index+5>=gamesSize){
                logger.info(fName+"set froward button disable");
                component1.getButtonAt4(1).setDisable();
            }
            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
        }
        menuMainListener_User(message,index);
    }
    private void menuMainListener_Admin(Message message, int index){
        String fName="[menuMainListener_Admin]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        lsMessageHelper.lsMessageDelete(message);
                        int getGame=-1;
                        switch (id){
                            case "one":
                                getGame=0;
                                break;
                            case "two":
                                getGame=1;
                                break;
                            case "three":
                                getGame=2;
                                break;
                            case "four":
                                getGame=3;
                                break;
                            case "five":
                                getGame=4;
                                break;
                            case "arrow_backward":
                                menuMain(index-5);
                                break;
                            case "arrow_forward":
                                menuMain(index+5);
                                break;
                        }
                        if(getGame>=0){
                            logger.info(fName+"getGame="+getGame+", index="+index+getGame);
                            globalGameBase_Ask(index+getGame);
                        }
                        
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        lsMessageHelper.lsMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, null);
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
                            help("main");return;
                        }
                        int getGame=-1;
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                            getGame=0;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                            getGame=1;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                            getGame=2;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                            getGame=3;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                            getGame=4;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                            menuMain(index+5);return;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                            menuMain(index-5);return;
                        }
                        if(getGame>=0){
                            logger.info(fName+"getGame="+getGame+", index="+index+getGame);
                            globalGameBase_Ask(index+getGame);
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

    }
    private void menuMainListener_User(Message message, int index){
        String fName="[menuMainListener_User]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        llMessageDelete(message);
                        int getGame=-1;
                        switch (id){
                            case "one":
                                getGame=0;
                                break;
                            case "two":
                                getGame=1;
                                break;
                            case "three":
                                getGame=2;
                                break;
                            case "four":
                                getGame=3;
                                break;
                            case "five":
                                getGame=4;
                                break;
                            case "arrow_backward":
                                menuMain(index-5);
                                break;
                            case "arrow_forward":
                                menuMain(index+5);
                                break;
                        }
                        if(getGame>=0){
                            logger.info(fName+"getGame="+getGame+", index="+index+getGame);
                            globalGameBase_Ask(index+getGame);
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, null);
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
                            help("main");return;
                        }
                        int getGame=-1;
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias1))){
                            getGame=0;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias2))){
                            getGame=1;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias3))){
                            getGame=2;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias4))){
                            getGame=3;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.alias5))){
                            getGame=4;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowForward))){
                            menuMain(index+5);return;
                        }
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowBackward))){
                            menuMain(index-5);return;
                        }
                        if(getGame>=0){
                            logger.info(fName+"getGame="+getGame+", index="+index+getGame);
                            globalGameBase_Ask(index+getGame);
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
    }

    entityGuild.GAME selelectedgame=null;
    private void globalGameBase_Ask(int index){
        String fName = "[globalGameBase_Ask]";
        logger.info(fName+" index="+index);
        getGuildProfile();
        getMemberProfile();
        if(!readFile()){
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to read file!");
            return;
        }
        int size=GUILD.global.gamesSize();
        if(size==0){
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"No games!");
            return;
        }
        if(index<0){
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Invalid index, minimum is 0!");
            return;
        }
        if(index>=size){
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Invalid index, maximum is "+(size-1)+"!");
            return;
        }
        selelectedgame=GUILD.global.getGameAt(index);
        EmbedBuilder embedBuilder=new EmbedBuilder();
        String desc="";StringBuilder desc2=new StringBuilder();
        embedBuilder.setTitle(selelectedgame.getName());
        embedBuilder.setColor(selelectedgame.getDefaultStyle().getColor());
        if(!selelectedgame.getDefaultStyle().isThumbnailUrlBlank())embedBuilder.setThumbnail(selelectedgame.getDefaultStyle().getThumbnailUrl());
        if(!selelectedgame.getDescription().isBlank())embedBuilder.setDescription(selelectedgame.getDescription().replaceAll("!USER",gMember.getAsMention()));
        messageComponentManager.loadMessageComponents(gAskPath);
        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
        Message message=null;
        try {

            logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
            message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();
        }catch (Exception e3){
            logger.error(fName + ".exception=" + e3);
            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
            message=llSendMessageResponse_withReactionNotificationOptional(gUser,embedBuilder);
        }
        globalGameBase_AskListener(message);
    }
    private void globalGameBase_AskListener(Message message){
        String fName="[globalGameBase_AskListener]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String id=e.getButton().getId();
                        logger.warn(fName+"id="+id);
                        lsMessageHelper.lsMessageDelete(message);
                        if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasGreenCircle)){
                            globalGameBase_Do();
                        }
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        lsMessageHelper.lsMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, null);
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
                        else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasGreenCircle))){
                            globalGameBase_Do();
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

    }
    private void globalGameBase_Do(){
        String fName = "[globalGameBase_Do]";
        logger.info(fName);
        EmbedBuilder embedBuilder=new EmbedBuilder();
        String desc="";StringBuilder desc2=new StringBuilder();
        embedBuilder.setTitle(selelectedgame.getName());
        embedBuilder.setColor(selelectedgame.getDefaultStyle().getColor());
        if(!selelectedgame.getDefaultStyle().isThumbnailUrlBlank())embedBuilder.setThumbnail(selelectedgame.getDefaultStyle().getThumbnailUrl());
        List<Integer>rolls=selelectedgame.rollTheDice();
        embedBuilder.addField("Rolls", StringUtil.join(selelectedgame.getRollsPrint(true),", "),false);
        List<MessageEmbed.Field> fields=selelectedgame.getEmbedFields();
        for(MessageEmbed.Field field:fields){
            embedBuilder.addField(field);
        }
        embedBuilder.setAuthor(gUser.getName(),null,gUser.getEffectiveAvatarUrl());
        lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
    }
    
    lcJSONUserProfile gUserProfile=new lcJSONUserProfile();
    entityUser PLAYER=new entityUser();
    private Boolean getMemberProfile(){
        String fName="[getMemberProfile]";
        logger.info(fName);
        if(gTarget==null||gTarget.getIdLong()==gMember.getIdLong()){
            return getMemberProfile(gMember);
        }else{
            return getMemberProfile(gTarget);
        }
    }
    private Boolean getMemberProfile(Member member){
        String fName="[getMemberProfile]";
        logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
        /*if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.gMember.getIdLong()==member.getIdLong()){
            logger.info(fName + ".already present>skip");
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonUser.toString());
            PLAYER.set(gUserProfile.jsonUser);
            return true;
        }
        gUserProfile=gGlobal.getUserProfile(profileName,member);
        if(gUserProfile!=null&&gUserProfile.isProfile()){
            logger.info(fName + ".is locally cached");
        }else{
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(gGlobal,member,profileName);
            if(gUserProfile.getProfile()){
                logger.info(fName + ".has sql entry");
            }
        }
        gUserProfile= iFapRoulette.sUserInit(gUserProfile);
        gGlobal.putUserProfile(gUserProfile,profileName);
        PLAYER.set(gUserProfile.jsonUser);
        */
        return true;
    }
    private Boolean saveMemberProfile(){
        String fName="[saveMemberProfile]";
        logger.info(fName);
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile()){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
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
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
            gNewUserBDSMProfile.build(gGlobal,member,gBDSMCommands);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    lcJSONGuildProfile gGuildProfile;
    entityGuild GUILD=new entityGuild();
    private Boolean getGuildProfile(){
        String fName="[getProfile]";
        logger.info(fName);
        if(gGuildProfile!=null&&gGuildProfile.isProfile()){
            logger.info(fName + ".already present>skip");
            logger.info(fName + ".gGuildProfile="+gGuildProfile.jsonObject.toString());
            GUILD.custom.set(gGuildProfile.jsonObject);
            return true;
        }
        gGuildProfile=gGlobal.getGuildSettings(gGuild,guildprofileName);
        if(gGuildProfile==null||gGuildProfile.isProfile()){
            logger.info(fName + ".failed to get");
            return false;
        }
        gGuildProfile= iFapRoulette.sGuildInit(gGuildProfile);
        if(gGuildProfile.isUpdated){
            gGuildProfile.saveProfile();
        }
        logger.info(fName + ".gGuildProfile="+gGuildProfile.jsonObject.toString());
        GUILD.custom.set(gGuildProfile.jsonObject);
        return true;
    }
    lcText2Json text2Json=null;
    private boolean readFile() {
        String fName="[readFile]";
        logger.info(fName);
        try {
            File file1;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+ gGlobalGamesPath);
                file1=new File(gGlobalGamesPath);
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
                if(!text2Json.isInputStream2Json(fileStream)){
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
                GUILD.global.set(text2Json.jsonObject);
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
        long durationValue=0;boolean durationProvided=false;
        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setTitle(gTitle);embedBuilder.setColor(llColors.llColorBlue3);
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
            }
        }
        if(member!=null&&member.getIdLong()!=gMember.getIdLong()){
            gTarget=member;
            getMemberProfile(member);
        }else{
            getMemberProfile(gMember);
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
            logger.error(fName+"exception:"+e);
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
            logger.error(fName+"exception:"+e);
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
            logger.error(fName+"exception:"+e);
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
            logger.error(fName+"exception:"+e);
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
            logger.error(fName+"exception:"+e);
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
            logger.error(fName+"exception:"+e);
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
            embed.addField("Allowed channels","Commands:`"+llGlobalHelper.llPrefixStr+gCommand+" server allowchannels  :one:/list|add|rem|set|clear`",false);
            embed.addField("Blocked channels","Commands:`"+llGlobalHelper.llPrefixStr+gCommand+" server blockchannels :two:/list|add|rem|set|clear`",false);
            embed.addField("Allowed roles","Commands:`"+llGlobalHelper.llPrefixStr+gCommand+" server allowroles :three:/list|add|rem|set|clear`",false);
            embed.addField("Blocked roles","Commands:`"+llGlobalHelper.llPrefixStr+gCommand+" server blockroles :four:/list|add|rem|set|clear`",false);
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
                    e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&e.getUserId().equalsIgnoreCase(gUser.getId())),
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
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e.toString());
        }
    }

}
}
