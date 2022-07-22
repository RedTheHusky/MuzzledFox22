package forRemoval.textadventure.spooktober;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import express.http.response.Response;
import forRemoval.textadventure.PLAYER;
import forRemoval.textadventure.TEXTADVENTURE;
import models.lc.discordentities.lcEmbedBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponents;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
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
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.apache.log4j.Logger;
import restraints.models.entity.entityRDUserProfile;
import restraints.models.lcBDSMGuildProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Spooktober extends Command implements llMessageHelper, llGlobalHelper, iSpooktober {
        Logger logger = Logger.getLogger(getClass());

        lcGlobalHelper gGlobal;
        String gTitle="Spooktober[WIP]",gCommand="spooktober";
    public Spooktober(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = gTitle;
        this.help = "Number Generator";
        this.aliases = new String[]{gCommand};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;this.category= llCommandCategory_NSFW;
        this.hidden=true;

    }
    public Spooktober(lcGlobalHelper global, Response response, Member member, TextChannel textChannel, String command){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(response,member,textChannel,command);
        new Thread(r).start();

    }
    public Spooktober(lcGlobalHelper global, SlashCommandEvent ev){
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
    Member gTarget;
    boolean gIsOverride=false;
    public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
    String gForward=null;
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


            if(gSlashCommandEvent!=null){
                SlashNT();
            }else
            {
                logger.info(fName + "basic@");
                if(gCommandEvent.getArgs().isEmpty()){
                    logger.info(fName+".Args=0");
                    initGame();
                    isInvalidCommand=false;
                }else {
                    logger.info(fName + ".Args");
                    gItems = gCommandEvent.getArgs().split("\\s+");
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
                    else if(gItems[0].equalsIgnoreCase("menu")){
                        menuMain();
                        isInvalidCommand=false;
                    }
                    else if(gItems[0].equalsIgnoreCase("new")){
                        newGame();
                        isInvalidCommand=false;
                    }
                    else if(gItems[0].equalsIgnoreCase("reset")){
                        resetGame();
                        isInvalidCommand=false;
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
    private void help( String command) {
        String fName = "[help]";
        logger.info(fName);
        logger.info(fName + "command=" + command);
        String desc="N/a";
        String quickSummonWithSpace=llPrefixStr+gCommand+"/number ";
        EmbedBuilder embed=new EmbedBuilder();
        embed.setColor(llColorBlue1);embed.setTitle(gTitle);
        embed.setDescription("`"+quickSummonWithSpace+"` to generate 7 digit number.\n"+"`"+quickSummonWithSpace+" [a]` to generate a digit number, where a is an integer");
        if(lsMemberHelper.lsMemberIsManager(gMember))embed.addField("Server options","Type `"+quickSummonWithSpace+"guild|server` for managing this command server side.",false);
        if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
            if(gSlashCommandEvent==null&&!isMenu)lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(gGlobal,gTextChannel," I sent you a list of commands in DMs");
        }else{
            lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
        }
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
    lcText2Json  text2Json=new lcText2Json();
    TEXTADVENTURE textadventure=new TEXTADVENTURE();
    private boolean readFile() {
        String fName="[readFile]";
        logger.info(fName);
        try {
            File file1;
            InputStream fileStream=null;
            try {
                logger.info(fName+"path="+gChaptersFilePath);
                file1=new File(gChaptersFilePath);
                if(file1.exists()){
                    logger.info(fName+".file1 exists");
                    fileStream = new FileInputStream(file1);
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
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
            textadventure.set(text2Json.jsonObject);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    lcJSONUserProfile gUserProfile;
    PLAYER player=new PLAYER();
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
        if(gUserProfile!=null&&gUserProfile.isProfile()&&gUserProfile.getMember().getIdLong()==member.getIdLong()){
            logger.info(fName + ".already present>skip");
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
            player.set(gUserProfile.jsonObject);
            return true;
        }
        gUserProfile=gGlobal.getUserProfile(profileName,member,gGuild);
        if(gUserProfile!=null&&gUserProfile.isProfile()){
            logger.info(fName + ".is locally cached");
        }else{
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(gGlobal,member,gGuild,profileName);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");
            }
        }
        gUserProfile= iSpooktober.sUserInit(gUserProfile);
        gGlobal.putUserProfile(gUserProfile,profileName);
        player.set(gUserProfile.jsonObject);
        if(!gUserProfile.isUpdated){
            logger.info(fName + ".no update>ignore");
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
            return true;
        }
        if(!saveProfile()){ logger.error(fName+".failed to write in Db");
            llSendQuickEmbedMessageWithDelete(gGlobal,true,gTextChannel,gTitle,"Failed to write in Db!", llColorRed);}
        try {
            logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        return true;
    }
    private Boolean saveProfile(){
        String fName="[saveProfile]";
        logger.info(fName);
        gUserProfile.jsonObject =player.getJSON();
        logger.info(fName + ".gUserProfile="+gUserProfile.jsonObject.toString());
        gGlobal.putUserProfile(gUserProfile,profileName);
        if(gUserProfile.saveProfile(table)){
            logger.info(fName + ".success");return  true;
        }
        logger.warn(fName + ".failed");return false;
    }
    entityRDUserProfile gNewUserBDSMProfile=new entityRDUserProfile();
    lcBDSMGuildProfiles gBDSMCommands=null;
    private Boolean getBDSMProfile(Member member){
        String fName="[getBDSMProfile]";
        try {
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            gBDSMCommands =new lcBDSMGuildProfiles(gGlobal,gGuild);
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
        int valueDigit=7;boolean gotDigit=false;
        int valueLow=0;boolean gotLow=false;
        int valueHigh=0;boolean gotHigh=false;
        for(OptionMapping option:options){
            String name=option.getName();
            logger.info(fName+"option.name="+name);
            switch (name){
                case "digit":
                    if(option.getType()== OptionType.INTEGER){
                        valueDigit=Long.valueOf(option.getAsLong()).intValue();
                        gotDigit=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "low":
                    if(option.getType()== OptionType.INTEGER){
                        valueLow=Long.valueOf(option.getAsLong()).intValue();
                        gotLow=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
                case "high":
                    if(option.getType()== OptionType.INTEGER){
                        valueHigh=Long.valueOf(option.getAsLong()).intValue();
                        gotHigh=true;
                    }else{
                        logger.warn(fName+"invalid option.type");
                    }
                    break;
            }
        }
        switch (subcommand){

        }
        EmbedBuilder embedBuilder=new EmbedBuilder();

    }


    boolean isMenu=false;int menuLevel=0;
    private void menuMain(){
        String fName="[menuMain]";
        logger.info(fName);
        try{
            isMenu=true;menuLevel=0;
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            StringBuilder desc3=new StringBuilder();
            embed.setColor(llColorOrange);embed.setTitle(gTitle+" Options");

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
                logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                message=gUser.openPrivateChannel().complete().sendMessageEmbeds(embed.build()).append(lsGlobalHelper.strPrivateMessageReactionNotificationOptional).setActionRows(messageComponentManager.messageBuildComponents.getAsActionRows()).complete();

            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=llSendMessageResponse_withReactionNotificationOptional(gUser,embed);
            }
            menuMainWearerListener(message);
        } catch (Exception e) {
            logger.error(fName+".exception=" + e);
            logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
        }
    }
    private void menuMainWearerListener(Message message){
        String fName="[menuMainWearerListener]";
        logger.info(fName);
        /*gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String value=e.getValues().get(0);
                        logger.warn(fName+"value="+value);
                        llMessageDelete(message);
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, null);
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
                },intDefaultMinutes, TimeUnit.MINUTES, null);*/
        gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                e -> {
                    try {
                        String name=e.getReactionEmote().getName();
                        logger.warn(fName+"name="+name);
                        String level="";
                        llMessageDelete(message);
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){help("main");}
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

    TEXTADVENTURE.GROUP selectedGroup=new TEXTADVENTURE.GROUP();
    TEXTADVENTURE.GROUP.SUBGROUP selectedSubGroup=new TEXTADVENTURE.GROUP.SUBGROUP();
    TEXTADVENTURE.GROUP.PAGE selectedPage=new TEXTADVENTURE.GROUP.PAGE();
    private void initGame() {
        String fName = "[initGame]";
        logger.info(fName);
        if(!readFile()){
            logger.error(fName+"failed to read file");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to read file");
            return;
        }
        if(!getProfile()){
            logger.error(fName+"failed to get profile");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to get user profile");
            return;
        }
        if(!getBDSMProfile(gMember)){
            logger.error(fName+"failed to get bdsm profile");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to get bdsm profile");
        }
        getSelection();
        postMessage();
    }
    private void getSelection() {
        String fName = "[getSelection]";
        logger.info(fName);
        selectedGroup=textadventure.getChapter(player.getGroupIndex());
        if(player.hasSubGroupIndex()){
            selectedSubGroup=selectedGroup.getSubGroup(player.getSubGroupIndex());
            selectedPage= selectedSubGroup.getPage(0);
        }else{
            selectedPage=selectedGroup.getPage(0);
        }
        postMessage();
    }
    private void resetGame() {
        String fName = "[resetGame]";
        logger.info(fName);
        if(!readFile()){
            logger.error(fName+"failed to read file");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to read file");
            return;
        }
        if(!getProfile()){
            logger.error(fName+"failed to get profile");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to get user profile");
            return;
        }
        if(!getBDSMProfile(gMember)){
            logger.error(fName+"failed to get bdsm profile");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to get bdsm profile");
        }
        player.clear();
        saveProfile();
        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel,gTitle,stringReplacer("Reset !USER player."),llColors.llColorGreen1);
    }
    private void newGame() {
        String fName = "[newGame]";
        logger.info(fName);
        if(!readFile()){
            logger.error(fName+"failed to read file");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to read file");
            return;
        }
        if(!getProfile()){
            logger.error(fName+"failed to get profile");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to get user profile");
            return;
        }
        if(!getBDSMProfile(gMember)){
            logger.error(fName+"failed to get bdsm profile");
            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gTitle,"Failed to get bdsm profile");
        }
        player.setGroupIndex(0).setSubGroupIndex(0).setPageIndex(0);
        saveProfile();
        getSelection();
        postMessage();
    }
    private void jumpGroupGame(int index) {
        String fName = "[jumpGroupGame]";
        logger.info(fName+" index="+index);
        player.setGroupIndex(index).setSubGroupIndex(0).setPageIndex(0);
        saveProfile();
        getSelection();
        postMessage();
    }
    private void jumpSubGroupGame(int index) {
        String fName = "[jumpSubGroupGame]";
        logger.info(fName+" index="+index);
        player.setGroupIndex(index).setSubGroupIndex(0).setPageIndex(0);
        saveProfile();
        getSelection();
        postMessage();
    }
    private void jumpPageGame(int index) {
        String fName = "[jumpPageGame]";
        logger.info(fName+" index="+index);
        player.setPageIndex(index);
        saveProfile();
        getSelection();
        postMessage();
    }
    private void postMessage() {
        String fName = "[postMessage]";
        logger.info(fName);
        lcEmbedBuilder embedBuilder=selectedPage.embedBuilder;
        embedBuilder.setDescription(stringReplacer(embedBuilder.getDescription()));
        embedBuilder.setTitle(gMember.getEffectiveName()+"'s adventure");
        lcMessageBuildComponents messageBuildComponents=selectedPage.messageBuildComponents;
        Message message=gTextChannel.sendMessageEmbeds(embedBuilder.build()).setActionRows(messageBuildComponents.getAsActionRows()).complete();
        messageListener(message);
    }
    private void postMessageWithoutComponents() {
        String fName = "[postMessageWithoutComponents]";
        logger.info(fName);
        lcEmbedBuilder embedBuilder=selectedPage.embedBuilder;
        embedBuilder.setDescription(stringReplacer(embedBuilder.getDescription()));
        embedBuilder.setTitle(gMember.getEffectiveName()+"'s adventure");
        Message message=gTextChannel.sendMessageEmbeds(embedBuilder.build()).complete();
        messageListener(message);
    }
    private void messageListener(Message message) {
        String fName = "[messageListener]";
        logger.info(fName);
        gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                e -> (e.getMessageIdLong()==message.getIdLong()),
                e -> {
                    try {
                        String option=e.getValues().get(0);
                        logger.info(fName+"option="+option);
                        llMessageDelete(message);
                        String[] items=option.split(":");
                        String name="",value="";
                        if(items.length>=1)name=items[0];
                        if(items.length>=2)value=items[1];
                        logger.info(fName+"name="+name+", value="+value);
                        if(name.equalsIgnoreCase(TEXTADVENTURE.COMPONENT_COMMAND.GroupJump.getName())||name.equalsIgnoreCase("chapter_jump")){
                            jumpGroupGame(Integer.parseInt(value));
                        }
                        else if(name.equalsIgnoreCase(TEXTADVENTURE.COMPONENT_COMMAND.SubGroupJump.getName())||name.equalsIgnoreCase("subchapter_jump")){
                            jumpSubGroupGame(Integer.parseInt(value));
                        }
                        else if(name.equalsIgnoreCase(TEXTADVENTURE.COMPONENT_COMMAND.PageJump.getName())){
                            jumpPageGame(Integer.parseInt(value));
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
                        String option=e.getButton().getId();
                        logger.info(fName+"option="+option);
                        llMessageDelete(message);
                        String[] items=option.split(":");
                        String name="",value="";
                        if(items.length>=1)name=items[0];
                        if(items.length>=2)value=items[1];
                        logger.info(fName+"name="+name+", value="+value);
                        if(name.equalsIgnoreCase(TEXTADVENTURE.COMPONENT_COMMAND.GroupJump.getName())||name.equalsIgnoreCase("chapter_jump")){
                            jumpGroupGame(Integer.parseInt(value));
                        }
                        else if(name.equalsIgnoreCase(TEXTADVENTURE.COMPONENT_COMMAND.SubGroupJump.getName())||name.equalsIgnoreCase("subchapter_jump")){
                            jumpSubGroupGame(Integer.parseInt(value));
                        }
                        else if(name.equalsIgnoreCase(TEXTADVENTURE.COMPONENT_COMMAND.PageJump.getName())){
                            jumpPageGame(Integer.parseInt(value));
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
                        if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){help("main");}
                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                        llMessageDelete(message);
                    }
                },intDefaultMinutes, TimeUnit.MINUTES, () -> {
                    llMessageDelete(message);
                    postMessageWithoutComponents();
                });
    }
    private String stringReplacer(String source){
        String fName="[stringReplacer]";
        try {
            source=source.replaceAll("!USER_name",gMember.getUser().getName()).replaceAll("!USER",gMember.getAsMention());
            source=source.replaceAll("!MEMBER_name",gMember.getEffectiveName());
            return  source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return source;
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
