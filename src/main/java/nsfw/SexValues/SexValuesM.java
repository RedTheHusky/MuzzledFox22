package nsfw.SexValues;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
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
import nsfw.chastity.lockedmen.iLockedmen;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static models.ls.lsMemberHelper.lsMemberIsBotOwner;

public class SexValuesM extends Command implements  llColors{
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    public static String quickAlias="SexValues",gCommand="sexvalues";
    public static String sMainRTitle ="SexValues";
    public SexValuesM(lcGlobalHelper global){
        String fName="[constructor]";
        logger.info(fName);
        gGlobal=global;
        this.name = "SexValues";
        this.help = "SexValues";
        this.aliases = new String[]{"sex_values"};
        this.guildOnly = true;this.category= llGlobalHelper.llCommandCategory_NSFW;
        this.hidden=true;
    }
    public SexValuesM(lcGlobalHelper global, SlashCommandEvent ev){
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
    protected class runLocal extends sexvaluesExtension implements Runnable {
        String cName="[runLocal]";

        public runLocal(CommandEvent ev){
            launch(SexValuesM.this.gGlobal,ev);
        }
        public runLocal(SlashCommandEvent ev) {
            logger.info(cName + ".run build");
            launch(SexValuesM.this.gGlobal,ev);
        }


        @Override
        public void run() {
            String fName="[run]";
            logger.info(".run start");
            try{
                setTitleStr(SexValuesM.sMainRTitle);setCommandStr(SexValuesM.gCommand);
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
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorRed_Barn).setTitle(sMainRTitle);
            if(!loadDefaultSettings()){
                embed.setDescription("Failed to load settings!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            if(!getProfile()){
                embed.setDescription("Failed to load user!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            menuMain_Guest();
        }
        private void menuMain_Guest(){
            String fName="[menuMain_Guest]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1).setTitle(sMainRTitle+" Options");

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
                                case "show_score":
                                    showScoreInChat();
                                    menuMain();
                                    break;
                                case "quiz":
                                    quiz();
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
        private void quiz(){
            String fName="[quiz]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1).setTitle(gUserProfile.getMember().getUser().getName()+"'s "+sMainRTitle);
            if(!loadDefaultSettings()){
                embed.setDescription("Failed to load settings!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            if(!getProfile()){
                embed.setDescription("Failed to load user!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
        }
        private void showScoreInChat(){
            String fName="[showScoreInChat]";
            logger.info(fName);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setColor(llColorBlue1).setTitle(gUserProfile.getMember().getUser().getName()+"'s "+sMainRTitle);
            if(!loadDefaultSettings()){
                embed.setDescription("Failed to load settings!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            if(!getProfile()){
                embed.setDescription("Failed to load user!");
                sendOrUpdatePrivateEmbed(embed);;
                return;
            }
            if(gUserProfile.isStarted()){
                embed.setDescription("Have not completed the quiz!");
                sendOrUpdatePrivateEmbed(embed);
                return;
            }
            double affect_Affection=gUserProfile.score.getAffect_Affection();
            double affect_Hedonism=gUserProfile.score.getAffect_Hedonism();
            double attract_Male=gUserProfile.score.getAttract_Male();
            double attract_Female=gUserProfile.score.getAttract_Female();
            Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embed);
        }


    }}
