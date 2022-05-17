package util;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.helper.lcSendMessageHelper;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.ls.*;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HelpCommand extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper
{
    Logger logger = Logger.getLogger(getClass()); String cName="[HelpCommand]";
    lcGlobalHelper gGlobal;
    public HelpCommand(lcGlobalHelper g) {
        gGlobal=g;
        String fName="constructor";
        logger.info(fName);
        this.name = "Help-Menu";
        this.help = "Showing command list";
        this.aliases = new String[]{"help","?","commands"};
        this.hidden=true;
        this.guildOnly = true;
    }
    public HelpCommand(lcGlobalHelper g, SlashCommandEvent event){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }

    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]";logger.info(cName + fName);
        Runnable r = new runLocal(event);
        new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;
        private User gUser;private Member gMember;
        SlashCommandEvent gSlashCommandEvent;
        private Guild gGuild;private TextChannel gTextChannel; private Message gMessage;
        public runLocal(CommandEvent ev) {
            String fName = "[runLocal]";
            logger.info(cName + ".run build");
            gCommandEvent = ev;
            gUser = gCommandEvent.getAuthor();gMember = gCommandEvent.getMember();
            gGuild = gCommandEvent.getGuild();
            logger.info(cName + fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(cName + fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = gCommandEvent.getTextChannel();
            logger.info(cName + fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= gCommandEvent.getMessage();
            logger.info(fName + ".gMessage:" + gMessage.getId() + "|" + gMessage.getContentRaw());
        }
        public runLocal(SlashCommandEvent ev) {
            String fName="runLocal";
            logger.info(cName + ".run build SlashCommandEvent");
            gSlashCommandEvent = ev;
            gUser=ev.getUser();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            if(ev.isFromGuild()){
                gGuild=ev.getGuild();
                logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
                gMember=ev.getMember();
                gTextChannel=ev.getTextChannel();
                logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            }
        }
        String gTitle="Commands";
        @Override
        public void run() {
            String fName = "[run]";
            logger.info(cName + ".run start");
            try {
                messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                if(gSlashCommandEvent!=null) {
                    slash();
                }else{
                    boolean isInvalidCommand = true;
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(cName + fName + ".Args=0");
                        menuLevels(null);
                        isInvalidCommand = false;
                    } else {
                        logger.info(cName + fName + ".Args");
                        String[] items = gCommandEvent.getArgs().split("\\s+");
                /*if(items[0].equalsIgnoreCase("social")){
                    listCommands(llCommandCategory_SocialSFW);listCommands(llCommandCategory_SocialNSFW);isInvalidCommand=false;
                }*/
                        if(items[0].equalsIgnoreCase("between")||items[0].equalsIgnoreCase("social")){
                            menuLevels(llCommandCategory_Between);isInvalidCommand=false;//listCommands(llCommandCategory_SocialNSFW);

                        }
                        if(items[0].equalsIgnoreCase("sfw")){
                            menuLevels(llCommandCategory_SFW);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("nsfw")){
                            menuLevels(llCommandCategory_NSFW);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("utility")&&llMemberIsStaff(gMember)){
                            menuLevels(llCommandCategory_Utility);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("utilityinhouse")&&llMemberIsStaff(gMember)){
                            menuLevels(llCommandCategory_UtilityInHouse);isInvalidCommand=false;
                        }
                        if(items[0].equalsIgnoreCase("alpha")&&llMemberIsStaff(gMember)){
                            menuLevels(llCommandCategory_BuildAlpha);isInvalidCommand=false;
                        }
                    }
                    logger.info(cName + fName + ".deleting op message");
                    llMessageDelete(gMessage);
                    if (isInvalidCommand) {
                        llSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColorRed);
                    }
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }


            logger.info(cName + ".run ended");
        }



        List<Command>commands=new ArrayList<>();

        String keyName="name",keyAlias="alias",keyHelp="help";



        lcSendMessageHelper messageHelper=new lcSendMessageHelper(); //class to help managing sending editing messages
        public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager(); //class to help managing components for message, loads the components from json file
        String gCommandFileMainPath ="resources/json/utility/commandsMenu.json";
        public InteractionHook gCurrentInteractionHook,gComponentInteractionHook;//slash and button interaction hook
        public void slash() {
            String fName="[slash]";
            logger.info(".start");
            try{
                gCurrentInteractionHook=lsMessageHelper.lsDeferReply(gSlashCommandEvent,true);
                String subcommand=gSlashCommandEvent.getSubcommandName(), subcommandGroup=gSlashCommandEvent.getSubcommandGroup();
                /*for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){

                    }
                }*/
                if(subcommandGroup==null)subcommandGroup="";
                if(subcommand==null)subcommand="";
                logger.info(fName+"subcommandGroup="+subcommandGroup+", subcommand="+subcommand);
                menuLevels(null);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void menuLevels(Category category){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();String desc="";
                embed.setColor(llColorPurple2).setTitle(gTitle);
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                Message message=null;
                if(commands==null||commands.isEmpty()){
                    commands=gGlobal.commandClientBuilt.getCommands();
                }
                JSONArray elements=new JSONArray();
                if(category==null){
                    desc="sfw, nsfw, between, utility";
                    if(gGlobal.isPartOfTeam(gMember)){
                        desc+=", utilityinhouse,  alpha";
                    }
                    embed.addField("Categories","Commands are categorized:"+desc,false);
                    embed.addField("Links", lsUsefullFunctions.getUrlTextString("Invite",lsAssets.urlBotInvite)+lsUsefullFunctions.getUrlTextString("Support",lsAssets.urlLink2BotSerer)+"\n"+lsUsefullFunctions.getUrlTextString("Site",lsAssets.urlLink2Site)+", "+lsUsefullFunctions.getUrlTextString("Forum",lsAssets.urlLink2Forum)+"\n"+lsUsefullFunctions.getUrlTextString("PayPal4Donation",lsAssets.urlLink2PayPal)+", "+lsUsefullFunctions.getUrlTextString("Patreon",lsAssets.urlLink2Patreon)+" for helping out the cost of hosting and showing your love to the bot.",false);
                    try {
                        logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                        lcMessageBuildComponent componentNavigator=messageComponentManager.messageBuildComponents.getComponent(0);
                        lcMessageBuildComponent componentCategory=messageComponentManager.messageBuildComponents.getComponent(1);
                        componentNavigator.setIgnored();
                        logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                        List<ActionRow> actionrow=messageComponentManager.messageBuildComponents.getAsActionRows();
                        messageHelper.clear().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser);
                        if(actionrow!=null&&!actionrow.isEmpty()){
                            logger.info(fName+"has action ");
                            messageHelper.setActionRows(actionrow);
                        }
                        if(gCurrentInteractionHook==null)message=messageHelper.send();
                        else message=messageHelper.editOriginalInteractionHookOrBackupSend();

                    }catch (Exception e3){
                        logger.error(fName + ".exception=" + e3);
                        logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                        messageHelper.clear().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser);
                        if(gCurrentInteractionHook==null)message=messageHelper.send();
                        else message=messageHelper.editOriginalInteractionHookOrBackupSend();
                    }
                    if(message==null)lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, "Message is null");
                    menuLevelsListener(category,elements,-1,message);
                }else
                if(category == llCommandCategory_SFW || category == llCommandCategory_NSFW || category == llCommandCategory_Between || category == llCommandCategory_Utility || category == llCommandCategory_BuildAlpha || category == llCommandCategory_UtilityInHouse){
                    for(Command command: commands){
                        try {
                            String name=command.getName();
                            boolean isHidden=command.isHidden();
                            boolean guildOnly=command.isGuildOnly();
                            String help="<null>";String alias="<null>";
                            logger.info(cName + fName+" command["+name+"] isHidden["+isHidden+"] guildOnly["+guildOnly+"]");
                            if(!isHidden){
                                Category commandCategory=command.getCategory();

                                if(!command.getHelp().isEmpty()){help=command.getHelp();}
                                String categoryName="n/a";
                                if(commandCategory!=null){
                                    categoryName=commandCategory.getName();
                                }
                                logger.info(cName + fName+" command["+name+"] category["+categoryName+"]");
                                if(category==commandCategory){
                                    logger.info(cName + fName+" command["+name+"] same category");
                                    String[] aliases=command.getAliases();
                                    alias=name;
                                    if(aliases.length>0){
                                        alias=aliases[0];
                                    }
                                    logger.info(cName + fName+" command["+name+"] hidden("+isHidden+")"+" guildOnly("+guildOnly+")");
                                    logger.info(cName + fName+" command["+name+"] alias["+alias+"]");
                                    logger.info(cName + fName+" command["+name+"] help["+help+"]");
                                    //embed.addField(name,"`"+llPrefixStr+alias+"` \n"+help,false);
                                    JSONObject element=new JSONObject();
                                    element.put(keyName,name);element.put(keyAlias,alias);element.put(keyHelp,help);
                                    elements.put(element);
                                }
                            }
                        }catch (Exception e){
                            logger.error(fName + ".exception=" + e);
                            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        }
                    }
                    menuLevels(category,elements,0);
                }else{
                    embed.setDescription("Error, unsupported option");
                }


            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void menuLevels(Category category, JSONArray elements, int page ){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                EmbedBuilder embed=new EmbedBuilder();String desc="";
                embed.setColor(llColorPurple2).setTitle(gTitle);
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                Message message=null;
                if(elements==null)elements=new JSONArray();
                if(elements.isEmpty()){
                    embed.setDescription("Error!");
                }
                else if(category==llCommandCategory_SFW){
                    embed.setTitle("SFW Commands");
                }
                else if(category==llCommandCategory_NSFW){
                    embed.setTitle("NSFW Commands");
                }
                else if(category==llCommandCategory_Between){
                    embed.setTitle("Between Commands");
                }
                else if(category==llCommandCategory_Utility){
                    embed.setTitle("Utility Commands");
                }
                else if(category==llCommandCategory_BuildAlpha){
                    embed.setTitle("BuildAlpha Commands");
                }
                else if(category==llCommandCategory_UtilityInHouse){
                    embed.setTitle("UtilityInHouse Commands");
                }
                int i=page,d=0;
                while(i<elements.length()&&d<10){
                    JSONObject element=elements.getJSONObject(i);
                    embed.addField(element.getString(keyName),"`"+llPrefixStr+element.getString(keyAlias)+"` \n"+element.getString(keyHelp),false);
                    d++;i++;
                }
                try {
                    logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent componentNavigator=messageComponentManager.messageBuildComponents.getComponent(0);
                    lcMessageBuildComponent componentCategory=messageComponentManager.messageBuildComponents.getComponent(1);
                    lcMessageBuildComponent.Button buttonBack=componentNavigator.getButtonAt4(0);
                    lcMessageBuildComponent.Button buttonUp=componentNavigator.getButtonAt4(1);
                    lcMessageBuildComponent.Button buttonNext=componentNavigator.getButtonAt4(2);
                    if(elements.isEmpty()){
                        buttonNext.setDisable();
                        buttonBack.setDisable();
                    }
                    if(!(i+1<elements.length())){
                        buttonNext.setDisable();
                    }
                    if(!(page>0)){
                       buttonBack.setDisable();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    List<ActionRow> actionrow=messageComponentManager.messageBuildComponents.getAsActionRows();
                    messageHelper.clear().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser);
                    if(actionrow!=null&&!actionrow.isEmpty()){
                        logger.info(fName+"has action ");
                        messageHelper.setActionRows(actionrow);
                    }
                    if(gCurrentInteractionHook==null)message=messageHelper.send();
                    else message=messageHelper.editOriginalInteractionHookOrBackupSend();
                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    messageHelper.clear().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser);
                    if(gCurrentInteractionHook==null)message=messageHelper.send();
                    else message=messageHelper.editOriginalInteractionHookOrBackupSend();
                }
                if(message==null)lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, "Message is null");
                menuLevelsListener(category,elements,page,message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void menuLevelsListener(Category category, JSONArray elements, int page, Message message){
            String fName="[menuLevelsWearerListener]";
            logger.info(fName);
            try{
                gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) {
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setColor(llColorPurple2).setTitle(gTitle).setDescription("Selected");
                                messageHelper.clear().clearActionRows().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setActionRowsClearFlag(true).editOriginalInteractionHook();
                                gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                                gCurrentInteractionHook=gComponentInteractionHook;
                            }
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                if(id.startsWith("gr_")){
                                    Category newCategory=null;
                                    switch (id){
                                        case "gr_sfw":
                                            newCategory=llCommandCategory_SFW;
                                            break;
                                        case "gr_nsfw":
                                            newCategory=llCommandCategory_NSFW;
                                            break;
                                        case "gr_between":
                                            newCategory=llCommandCategory_Between;
                                            break;
                                        case "gr_utility":
                                            newCategory=llCommandCategory_Utility;
                                            break;
                                    }
                                    menuLevels(newCategory);
                                }else
                                if(id.startsWith("arrow_")){
                                    switch (id){
                                        case "arrow_back":
                                            menuLevels(category,elements,page-10);
                                            break;
                                        case "arrow_up":
                                            menuLevels(null);
                                            break;
                                        case "arrow_next":
                                            menuLevels(category,elements,page+10);
                                            break;
                                    }
                                }

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));

                if(message.isFromGuild()){
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageId().equalsIgnoreCase(message.getId())&&!e.getUser().isBot()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    logger.info(fName+"user="+e.getUser().getName()+"("+e.getUser().getId()+"), isBot="+e.getUser().isBot());

                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        menuLevels(category,elements,page+10);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReversePlayButton))){
                                        menuLevels(category,elements,page-10);
                                    }else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                        menuLevels(null);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                       menuLevels(llCommandCategory_SFW);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        menuLevels(llCommandCategory_NSFW);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        menuLevels(llCommandCategory_Between);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        menuLevels(llCommandCategory_Utility);
                                    }
                                }catch (Exception e2) {
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                            },15, TimeUnit.MINUTES, () -> {
                                lsMessageHelper.lsMessageClearReactionsQueue(message);
                            });
                }else{
                    gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&!e.getUser().isBot()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton))){
                                        menuLevels(category,elements,page+10);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReversePlayButton))){
                                        menuLevels(category,elements,page-10);
                                    }else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp))){
                                        menuLevels(null);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasZero))){
                                        menuLevels(llCommandCategory_SFW);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasOne))){
                                        menuLevels(llCommandCategory_NSFW);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasTwo))){
                                        menuLevels(llCommandCategory_Between);
                                    }
                                    else if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasThree))){
                                        menuLevels(llCommandCategory_Utility);
                                    }
                                }catch (Exception e2) {
                                    logger.error(fName + ".exception=" + e2);
                                    logger.error(fName + ".exception:" + Arrays.toString(e2.getStackTrace()));
                                }
                            },15, TimeUnit.MINUTES, () -> {
                                lsMessageHelper.lsMessageRemoveReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowUp),gGuild.getSelfMember().getUser());
                                lsMessageHelper.lsMessageRemoveReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasArrowDoubleDown),gGuild.getSelfMember().getUser());
                                lsMessageHelper.lsMessageRemoveReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasPlayButton),gGuild.getSelfMember().getUser());
                                lsMessageHelper.lsMessageRemoveReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasReversePlayButton),gGuild.getSelfMember().getUser());
                            });
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
    }


}
