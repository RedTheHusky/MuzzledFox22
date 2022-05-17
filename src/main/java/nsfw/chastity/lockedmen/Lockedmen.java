package nsfw.chastity.lockedmen;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.llGlobalHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class Lockedmen extends Command implements  llColors{
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public static String quickAlias="lockedmen",gCommand="lockedmen";
    public static String sMainRTitle ="Lockedmen";
    public Lockedmen(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "LockedMen";
        this.help = "lockedmen";
        this.aliases = new String[]{"locked_men"};
        this.guildOnly = true;this.category= llGlobalHelper.llCommandCategory_NSFW;
        this.hidden=true;
    }
    public Lockedmen(lcGlobalHelper global, SlashCommandEvent ev){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        Runnable r = new runLocal(ev);
        new Thread(r).start();
    }

    @Override
    protected void execute(CommandEvent gEvent) {
        String fName="[execute]";
        logger.info(fName);
        Runnable r = new runLocal(gEvent);
        new Thread(r).start();
    }
    protected class runLocal extends lockedmenExtension implements Runnable {
        String cName="[runLocal]";

        public runLocal(CommandEvent ev){
            launch(Lockedmen.this.gGlobal,ev);
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(Lockedmen.this.gGlobal,ev);
        }


        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try{
                setTitleStr(Lockedmen.sMainRTitle);setCommandStr(Lockedmen.gCommand);
                messageComponentManager.set(global,gTextChannel,sMainRTitle,gUser);
                loadBasic("rdAuth_commands");
                lsUsefullFunctions.setThreadName4Display("rdAuth");
                if(gSlashCommandEvent!=null) {
                    logger.info(cName + fName + "slash@");
                    if(!checkIFAllowed2UseCommand1_slash()){ return; }
                    if(!isAdult){blocked();return;}
                    rSlashNT();
                }
                else{
                    logger.info(cName + fName + "basic@");
                    if(!isAdult){blocked();return;}
                    boolean isInvalidCommand=true;
                    if(gArgs.isEmpty()){
                        logger.info(fName+".Args=0");
                        menuMain();isInvalidCommand=false;
                    }else {
                        logger.info(fName + ".Args");
                        if(gArgs!=null&&gArgs.contains(iLockedmen.llOverride)&&(lsMemberHelper.lsMemberIsAdministrator(gMember)||lsMemberHelper.lsMemberIsManager(gMember)||lsMemberHelper.lsMemberIsModerator(gMember)||lsMemberIsBotOwner(gMember))){
                            gIsOverride =true;
                            gArgs=gArgs.replaceAll(iLockedmen.llOverride,"");
                        }
                        gItems = gArgs.split("\\s+");
                        logger.info(fName + ".items.size=" + gItems.length);
                        logger.info(fName + ".items[0]=" + gItems[0]);

                        if(gItems[0].equalsIgnoreCase("help")){
                            rHelp("main");isInvalidCommand=false;
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

                        ///TARGETED
                        if(isInvalidCommand&&check4TargetinItems()){
                            logger.info(fName+".detect mention characters");
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
                            }else{

                            }
                        }
                        if(isInvalidCommand){
                            if(gItems==null||gItems.length==0){
                                menuMain();isInvalidCommand=false;
                            }


                        }
                    }
                    if(isInvalidCommand){
                        lsMessageHelper.lsSendQuickEmbedMessage(gCommandEvent.getAuthor(),sMainRTitle,"You provided an incorrect command!", llColors.llColorRed);
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
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorOrange);embed.setTitle(sRTitle+" Helper");
            switch (command) {
                default:
                    break;
            }
            if(gCurrentInteractionHook!=null&&lsMessageHelper.lsEditOriginEmbed(gCurrentInteractionHook,embed)!=null){
                logger.info(fName+"sent as slash");
            }
            else if(lsMessageHelper.lsSendMessageStatus(gUser,embed)){
                lsMessageHelper.lsSendMessageWithDeleteAndAutoDelete(global,gTextChannel," I sent you a list of commands in DMs");
            }else{
                lsMessageHelper.lsSendMessageStatus(gTextChannel,embed);
            }
        }
        public void rSlashNT() {
            String fName="[rSlashNT]";
            logger.info(".start");
            try{
                String subcommand="",subcommandgroup="";
                subcommand=gSlashCommandEvent.getSubcommandName();subcommandgroup=gSlashCommandEvent.getSubcommandGroup();
                if(subcommand==null)subcommand="";if(subcommandgroup==null)subcommandgroup=null;
                logger.info(fName + ".subcommand="+subcommand+", subcommandgroup="+subcommandgroup);

                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case  llCommonKeys.SlashCommandReceive.subdir:

                            break;
                    }
                }
                slashReplyPleaseWait();
                gCurrentInteractionHook=gSlashInteractionHook;
                menuMain();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,sMainRTitle, e.toString());
            }
        }
        private void menuMain(){
            String fName="[menuMain]";
            logger.info(fName);
            menuMain_Guest();
        }
        private void menuMain_Guest(){
            String fName="[menuMain_Guest]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1).setTitle(sMainRTitle+" Options");
            if(!loadDefaultSettings()){
                embed.setDescription("Failed to load settings!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            cLockedmenHomepage.SiteStats sitestats=lockedmenHomepage.getSiteStats();
            if(sitestats.result()){
                embed.addField("Site Status","Profiles: "+sitestats.getProfiles()+"\n"+"Members:"+sitestats.getMembers(),false);
            }

            Message message=null;
            messageComponentManager.loadMessageComponents(iLockedmen.gMainMenuFilePath);
            logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
            try {
                logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);;
            }
            menuMain_Guest_Listener(message);
        }
        private void menuMain_Guest_Listener(Message message){
            String fName="[menuMainListener_Wearer]";
            logger.info(fName);
            global.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            lsMessageHelper.lsMessageDelete(message);
                            switch (id){
                                case "images_new":
                                    menuImagesNew();
                                    break;
                                case "images_random":
                                    menuImagesRandom();
                                    break;
                            }
                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }
                    },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    rHelp("main");return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    rHelp("main");return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }


        }
        private void menuImagesNew(){
            String fName="[menuImagesNew]";
            logger.info(fName);
            List<cLockedmenHomepage.ImagePost> imagePostList=lockedmenHomepage.getHomepageNewImages();
            if(imagePostList==null)imagePostList=new ArrayList<>();
            menuImagesNew(0,imagePostList);
        }
        private void menuImagesNew(int index,List<cLockedmenHomepage.ImagePost> imagePostList){
            String fName="[menuImagesNew]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            int size=imagePostList.size();
            embed.setColor(llColorOrange);embed.setTitle(sMainRTitle+"Image "+(index+1)+"/"+size);
            desc="";InputStream ip=null;
            if(size==0){
               embed.setDescription("No images to display!");
            }else if(index<0||index>=size){
                embed.setDescription("Invalid index!");
            }else{
                cLockedmenHomepage.ImagePost imagePost=imagePostList.get(index);
                String imgUlr=imagePost.getImageUrl();
                logger.info(fName+"url="+imgUlr);
                String caption=imagePost.getImageCaption();
                String nick=imagePost.getUserNick();
                if(caption!=null&&!caption.isBlank()) desc+="\nCaption:"+caption;
                if(nick!=null&&!nick.isBlank()) {
                    desc += "\nUser:" + nick;
                    cLockedmenMember lmMember=imagePost.getMember();
                    if(lmMember==null){
                        lmMember=new cLockedmenMember();
                        logger.warn(fName+"could not load member");
                    }
                }
                if(imgUlr!=null&&!imgUlr.isBlank()){
                    desc+="\n"+lsUsefullFunctions.getUrlTextString("[source]",imgUlr);
                    ip=lsStreamHelper.llGetInputStream4WebFile(imagePost.getImageUrl());
                    if(ip!=null)embed.setImage(imagePost.getImageUrl());
                }
                embed.setDescription(desc);
            }

            Message message=null;
            messageComponentManager.loadMessageComponents(iLockedmen.gImageMenuFilePath);
            logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
            try {
                lcMessageBuildComponent.Button buttonFirst=messageComponentManager.messageBuildComponents.getButtonAt(0,0);
                lcMessageBuildComponent.Button buttonBack=messageComponentManager.messageBuildComponents.getButtonAt(0,1);
                lcMessageBuildComponent.Button buttonPost=messageComponentManager.messageBuildComponents.getButtonAt(0,2);
                lcMessageBuildComponent.Button buttonNext=messageComponentManager.messageBuildComponents.getButtonAt(0,3);
                lcMessageBuildComponent.Button buttonLast=messageComponentManager.messageBuildComponents.getButtonAt(0,4);
                if(index<=0){
                    buttonBack.setDisable();
                }
                if(index<=1){
                   buttonFirst.setDisable();
                }
                if(ip==null){
                    buttonPost.setDisable();
                }
                if(index>=size-1){
                    buttonNext.setDisable();
                }
                if(index>=size-2){
                    buttonLast.setDisable();
                }

                logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);;
            }
            menuImagesNew_Listener(message,index,imagePostList);
        }
        private void menuImagesNew_Listener(Message message, int index,List<cLockedmenHomepage.ImagePost>imagePostList){
            String fName="[ menuImagesNew_Wearer]";
            logger.info(fName);
            global.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            switch (id){
                                case "first":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesNew(0,imagePostList);
                                break;
                                case "back":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesNew(index-1,imagePostList);
                                    break;
                                case "post":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesNewPost(index,imagePostList);
                                    break;
                                case "next":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesNew(index+1,imagePostList);
                                    break;
                                case "last":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesNew(imagePostList.size()-1,imagePostList);
                                    break;
                                case "white_check_mark":
                                    lsMessageHelper.lsMessageDelete(message);
                                    break;
                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }
                    },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    rHelp("main");return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    rHelp("main");return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }


        }
        private void menuImagesNewPost(int index,List<cLockedmenHomepage.ImagePost> imagePostList){
            String fName="[menuImagesNew]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(sMainRTitle+" Options");
            desc="";InputStream ip=null;
            if(imagePostList.isEmpty()){
                embed.setDescription("No images to display!");
                sendOrUpdatePrivateEmbed(embed);
                return;
            }else if(index<0||index>=imagePostList.size()){
                embed.setDescription("Invalid index!");
                sendOrUpdatePrivateEmbed(embed);
                return;
            }else{
                cLockedmenHomepage.ImagePost imagePost=imagePostList.get(index);
                String imgUlr=imagePost.getImageUrl();
                logger.info(fName+"url="+imgUlr);
                String caption=imagePost.getImageCaption();
                String nick=imagePost.getUserNick();
                if(caption!=null&&!caption.isBlank()) desc+="\nCaption:"+caption;
                if(nick!=null&&!nick.isBlank()) desc+="\nUser:"+nick;
                if(imgUlr!=null&&!imgUlr.isBlank()){
                    desc+="\n"+lsUsefullFunctions.getUrlTextString("[source]",imgUlr);
                    ip=lsStreamHelper.llGetInputStream4WebFile(imagePost.getImageUrl());
                    if(ip!=null)embed.setImage(imagePost.getImageUrl());
                }
                embed.setTitle(null);
                embed.setDescription(desc);
            }
            lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
        }
        private void menuImagesRandom(){
            String fName="[menuImagesRandom]";
            logger.info(fName);
            List<cLockedmenHomepage.ImagePost> imagePostList=lockedmenHomepage.getHomepageRandomImages();
            if(imagePostList==null)imagePostList=new ArrayList<>();
            menuImagesNew(0,imagePostList);
        }
        private void menuImagesRandom(int index,List<cLockedmenHomepage.ImagePost> imagePostList){
            String fName="[menuImagesRandom]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            int size=imagePostList.size();
            embed.setColor(llColorOrange);embed.setTitle(sMainRTitle+"Image "+(index+1)+"/"+size);
            desc="";InputStream ip=null;
            if(size==0){
                embed.setDescription("No images to display!");
            }else if(index<0||index>=size){
                embed.setDescription("Invalid index!");
            }else{
                cLockedmenHomepage.ImagePost imagePost=imagePostList.get(index);
                String imgUlr=imagePost.getImageUrl();
                logger.info(fName+"url="+imgUlr);
                String caption=imagePost.getImageCaption();
                String nick=imagePost.getUserNick();
                if(caption!=null&&!caption.isBlank()) desc+="\nCaption:"+caption;
                if(nick!=null&&!nick.isBlank()) desc+="\nUser:"+nick;
                if(imgUlr!=null&&!imgUlr.isBlank()){
                    desc+="\n"+lsUsefullFunctions.getUrlTextString("[source]",imgUlr);
                    ip=lsStreamHelper.llGetInputStream4WebFile(imagePost.getImageUrl());
                    if(ip!=null)embed.setImage(imagePost.getImageUrl());
                }
                embed.setDescription(desc);
            }

            Message message=null;
            messageComponentManager.loadMessageComponents(iLockedmen.gImageMenuFilePath);
            logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
            try {
                lcMessageBuildComponent.Button buttonFirst=messageComponentManager.messageBuildComponents.getButtonAt(0,0);
                lcMessageBuildComponent.Button buttonBack=messageComponentManager.messageBuildComponents.getButtonAt(0,1);
                lcMessageBuildComponent.Button buttonPost=messageComponentManager.messageBuildComponents.getButtonAt(0,2);
                lcMessageBuildComponent.Button buttonNext=messageComponentManager.messageBuildComponents.getButtonAt(0,3);
                lcMessageBuildComponent.Button buttonLast=messageComponentManager.messageBuildComponents.getButtonAt(0,4);
                if(index<=0){
                    buttonBack.setDisable();
                }
                if(index<=1){
                    buttonFirst.setDisable();
                }
                if(ip==null){
                    buttonPost.setDisable();
                }
                if(index>=size-1){
                    buttonNext.setDisable();
                }
                if(index>=size-2){
                    buttonLast.setDisable();
                }

                logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                message=sendOrUpdatePrivateEmbed(embed,messageComponentManager.messageBuildComponents.getAsActionRows());
            }catch (Exception e3){
                logger.error(fName + ".exception=" + e3);
                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                message=sendOrUpdatePrivateEmbed(embed);;
            }
            menuImagesRandom_Listener(message,index,imagePostList);
        }
        private void menuImagesRandom_Listener(Message message, int index,List<cLockedmenHomepage.ImagePost>imagePostList){
            String fName="[ menuImagesRandom_Listener]";
            logger.info(fName);
            global.waiter.waitForEvent(ButtonClickEvent.class,
                    e -> (e.getMessageIdLong()==message.getIdLong()),
                    e -> {
                        try {
                            String id=e.getButton().getId();
                            logger.warn(fName+"id="+id);
                            switch (id){
                                case "first":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesRandom(0,imagePostList);
                                    break;
                                case "back":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesRandom(index-1,imagePostList);
                                    break;
                                case "post":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesRandomPost(index,imagePostList);
                                    break;
                                case "next":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesRandom(index+1,imagePostList);
                                    break;
                                case "last":
                                    lsMessageHelper.lsMessageDelete(message);
                                    menuImagesRandom(imagePostList.size()-1,imagePostList);
                                    break;
                                case "white_check_mark":
                                    lsMessageHelper.lsMessageDelete(message);
                                    break;
                            }

                        }catch (Exception e3){
                            logger.error(fName + ".exception=" + e3);
                            logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                            lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                            lsMessageHelper.lsMessageDelete(message);
                        }
                    },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
            if(message.isFromGuild()){
                global.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    rHelp("main");return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }else{
                global.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                        e -> {
                            try {
                                String name=e.getReactionEmote().getName();
                                logger.warn(fName+"name="+name);
                                //String asCodepoints=e.getReactionEmote().getAsCodepoints();
                                logger.warn(fName+"asCodepoints="+name);
                                String level="";
                                lsMessageHelper.lsMessageDelete(message);
                                if(name.equalsIgnoreCase(global.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource))){
                                    rHelp("main");return;
                                }
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,sMainRTitle,e3.toString());
                                lsMessageHelper.lsMessageDelete(message);
                            }
                        },iLockedmen.intDefaultMinutes, TimeUnit.MINUTES, () -> {lsMessageHelper.lsMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
            }


        }
        private void menuImagesRandomPost(int index,List<cLockedmenHomepage.ImagePost> imagePostList){
            String fName="[menuImagesRandomPost]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1);
            String desc="";
            embed.setColor(llColorOrange);embed.setTitle(sMainRTitle+" Options");
            desc="";InputStream ip=null;
            if(imagePostList.isEmpty()){
                embed.setDescription("No images to display!");
                sendOrUpdatePrivateEmbed(embed);
                return;
            }else if(index<0||index>=imagePostList.size()){
                embed.setDescription("Invalid index!");
                sendOrUpdatePrivateEmbed(embed);
                return;
            }else{
                cLockedmenHomepage.ImagePost imagePost=imagePostList.get(index);
                String imgUlr=imagePost.getImageUrl();
                logger.info(fName+"url="+imgUlr);
                String caption=imagePost.getImageCaption();
                String nick=imagePost.getUserNick();
                if(caption!=null&&!caption.isBlank()) desc+="\nCaption:"+caption;
                if(nick!=null&&!nick.isBlank()) desc+="\nUser:"+nick;
                if(imgUlr!=null&&!imgUlr.isBlank()){
                    desc+="\n"+lsUsefullFunctions.getUrlTextString("[source]",imgUlr);
                    ip=lsStreamHelper.llGetInputStream4WebFile(imagePost.getImageUrl());
                    if(ip!=null)embed.setImage(imagePost.getImageUrl());
                }
                embed.setTitle(null);
                embed.setDescription(desc);
            }
            lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
        }
}}
