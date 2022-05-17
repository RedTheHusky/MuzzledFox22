package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lc.helper.lcSendMessageHelper;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.llCommonKeys;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import org.apache.log4j.Logger;
import restraints.in.iRdStr;
import util.entity.UserPrivacy;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class utilityPrivacy extends Command implements  llGlobalHelper, llMessageHelper, llMemberHelper  {
    String cName="[privacy]";
    lcGlobalHelper gGlobal;EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="privacy";
    public utilityPrivacy(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g; gWaiter=g.waiter;
        this.name = "Privacy";
        this.help = "Privacy settings";
        this.aliases = new String[]{commandPrefix};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    public utilityPrivacy(lcGlobalHelper g, SlashCommandEvent event){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g;
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    @Override
    protected void execute(CommandEvent event) {
        String fName="[execute]"; logger.info(fName);
        Runnable r = new runLocal(event);new Thread(r).start();
    }
    protected class runLocal implements Runnable {
        String cName = "[runLocal]";
        CommandEvent gCommandEvent;GuildMessageReactionAddEvent guildMessageReactionAddEvent;SlashCommandEvent gSlashCommandEvent;
        private User gUser;private Member gMember;
        private Guild gGuild;private TextChannel gTextChannel; private Message gMessage;String gTitle="Privacy";

        public runLocal(CommandEvent ev) {
            String fName="runLocal";
            logger.info(".run build");
            gCommandEvent = ev;
            gUser = ev.getAuthor();gMember =ev.getMember();
            gGuild = ev.getGuild();
            logger.info(fName + ".gUser:" + gUser.getId() + "|" + gUser.getName());
            logger.info(fName + ".gGuild:" + gGuild.getId() + "|" + gGuild.getName());
            gTextChannel = ev.getTextChannel();
            logger.info(fName + ".gTextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
            gMessage= ev.getMessage();
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

        @Override
        public void run() {
            String fName = "[run]";
            logger.info(fName);
            try {
                if(gSlashCommandEvent!=null){
                    messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                    slash();
                }else{
                    boolean isInvalidCommand=true;
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(fName + ".Args=0");
                        messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                        menuLevelsWearer();
                        isInvalidCommand = false;
                    } else {
                        logger.info(fName + ".Args");

                        String[] items = gCommandEvent.getArgs().split("\\s+");
                        if (items[0].equalsIgnoreCase("help")) {
                            if(items.length>=2){
                                help(items[1]);
                            }else{
                                help("main");
                            }
                            isInvalidCommand = false;
                        }else
                            messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                            menuLevelsWearer();

                    }
                    logger.info(fName + ".deleting op message");
                    lsMessageHelper.lsMessageDelete(gCommandEvent);
                    if (isInvalidCommand) {
                        lsMessageHelper.lsSendQuickEmbedMessage(gTextChannel, gTitle,"Provided an incorrect command!",llColors.llColorRed);
                    }
                }


            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
       
        private void help(String command) {
            String fName = "help";
            logger.info(fName + ".command:" + command);
            EmbedBuilder embed=new EmbedBuilder();
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
        String gCommandFileMainPath ="resources/json/utility/guild/menuPrivacy.json";
        lcSendMessageHelper messageHelper=new lcSendMessageHelper();
        private void menuLevelsWearer(){
            String fName="[menuLevels]";
            logger.info(fName);
            try{
                getProfile(gMember);
                EmbedBuilder embed=new EmbedBuilder();
                embed.setColor(llColorBlue1).setTitle(gTitle);
                String desc="";
                desc="By using bot, you agree to the data outlined in this policy being collected&stored in order for bot to provide full functionality.";
                embed.addField("Privacy Policy",desc,false);
                desc="for configuration: guild id, user id, message content (collects or/and stores for configuration)";
                desc+="\nfor main functionality: guild id, user id, message content (collects, does not stores)";
                embed.addField("Data collected",desc,false);
                desc="The bot stores data for the sole purpose of providing the advertised functionality within Discord. We don't not share data with any third party services. All data is stored on not publicly accessible database.";
                embed.addField("Usage of data",desc,false);
                desc="Enable event to read your messages posted to the guild without mentioning the bot.\nThis needs to be enabled for the main functions (gag) to work.";
                if(!gNewUserProfile.hasGuildMessageRead()) {
                    desc += "\nStatus: not set";
                }else if(gNewUserProfile.isGuildMessageRead()){
                    desc+="\nStatus: enabled";
                }else{
                    desc+="\nStatus: disabled";
                }
                embed.addField("Read Guild Messages",desc,false);
                desc="By the end of April, the prefix \'!>\' will become obsolete, you will need to mention the bot as prefix.";
                embed.addField("Important",desc,false);
                embed.addField("Site","https://redhusky.hostingerapp.com/muzzledfox/#privacy",false);
                Message message=null;
                messageComponentManager.loadMessageComponents(gCommandFileMainPath);
                try {
                    logger.info(fName+"component_before="+messageComponentManager.messageBuildComponents.getJson());
                    lcMessageBuildComponent.SelectMenu selectMessagePrivacy=messageComponentManager.messageBuildComponents.getSelectMenuAt(0);
                    if(gNewUserProfile.hasGuildMessageRead()){
                        if(selectMessagePrivacy==null)logger.error("selectMessagePrivacy is null!");
                        else if(gNewUserProfile.isGuildMessageRead()){
                            selectMessagePrivacy.setDefaultOptionAt(0);
                        }else{
                            selectMessagePrivacy.setDefaultOptionAt(1);
                        }
                    }

                    lcMessageBuildComponent.Button buttonClose=messageComponentManager.messageBuildComponents.getButtonAt(1,0);
                    if(gCurrentInteractionHook!=null){
                        if(buttonClose==null)logger.error("buttonClose is null!");
                        else buttonClose.setDisable();
                    }
                    logger.info(fName+"component_after="+messageComponentManager.messageBuildComponents.getJson());
                    List<ActionRow> actionrow=messageComponentManager.messageBuildComponents.getAsActionRows();
                    messageHelper.clear().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser);
                    if(actionrow!=null&&!actionrow.isEmpty()){
                        logger.info(fName+"has action ");
                        messageHelper.setActionRows(actionrow);
                    }
                    message=messageHelper.editOriginalInteractionHookOrBackupSend();

                }catch (Exception e3){
                    logger.error(fName + ".exception=" + e3);
                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                    message=messageHelper.clear().setEmbed(embed.build()).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser).editOriginalInteractionHookOrBackupSend();
                }
                menuLevelsWearerListener(message);

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void menuLevelsWearerListener(Message message){
            String fName="[menuLevelsWearerListener]";
            logger.info(fName);
            try{
                gGlobal.waiter.waitForEvent(ButtonClickEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                            try {
                                String id=e.getButton().getId();
                                logger.warn(fName+"id="+id);
                                String level="";
                                llMessageDelete(message);
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasWhiteCheckMark)){
                                    logger.info(fName + "close");
                                    return;
                                }
                                if(id.equalsIgnoreCase(lsUnicodeEmotes.aliasInformationSource))level="information_source";

                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+ lsGlobalHelper.timeout_button));
                gGlobal.waiter.waitForEvent(SelectionMenuEvent.class,
                        e -> (e.getMessageIdLong()==message.getIdLong()),
                        e -> {
                            if(gCurrentInteractionHook!=null) gComponentInteractionHook=lsMessageHelper.lsDeferReply(e,true);
                            try {
                                List<String> values=e.getValues();
                                String id=e.getComponent().getId();
                                String value=values.get(0);
                                logger.warn(fName+"value="+value+", id="+id);
                                llMessageDelete(message);
                                EmbedBuilder embed=new EmbedBuilder();
                                embed.setColor(llColorBlue1).setTitle(gTitle);
                                switch (Objects.requireNonNull(id).toLowerCase()){
                                    case "guild_allow_read":
                                        messageHelper.clear().setActionRowsClearFlag(true).setInteractionHook(gCurrentInteractionHook).setPrivateChannel(gUser);
                                        switch (value.toLowerCase()){
                                            case "yes": case "true": case "1":
                                                gNewUserProfile.setGuildMessageRead(true);
                                                embed.setDescription("Set read guild Messages to true.");
                                                break;
                                            case "no": case "false": case "0":
                                                gNewUserProfile.setGuildMessageRead(false);
                                                embed.setDescription("Set read guild Messages to false.");
                                                break;
                                        }
                                        messageHelper.setEmbed(embed.build()).editOriginalInteractionHookOrBackupSend();
                                        gNewUserProfile.saveProfile();
                                        break;
                                }
                                if(gCurrentInteractionHook!=null){
                                    if(gComponentInteractionHook==null)throw  new Exception("component interaction_hook is NULL!");
                                }
                                gCurrentInteractionHook=gComponentInteractionHook;
                                menuLevelsWearer();
                            }catch (Exception e3){
                                logger.error(fName + ".exception=" + e3);
                                logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                llMessageDelete(message);
                            }
                        },5, TimeUnit.MINUTES, () -> logger.info(fName+lsGlobalHelper.timeout_selectmenu));
                if(message.isFromGuild()){
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";

                                    if(!level.isBlank()){

                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }else{
                    gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    String level="";
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasInformationSource)))level="information_source";
                                    if(!level.isBlank()){

                                    }
                                    llMessageDelete(message);
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel,gUser,gTitle,e3.toString());
                                    llMessageDelete(message);
                                }
                            },5, TimeUnit.MINUTES, () -> {llMessageDelete(message);logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        public void slash() {
            String fName="[slash]";
            logger.info(".start");
            try{
                User user=null;
                boolean subdirProvided=false;
                gCurrentInteractionHook=lsMessageHelper.lsDeferReply(gSlashCommandEvent,true);
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case llCommonKeys.SlashCommandReceive.user:
                            if(option.getType()== OptionType.USER){
                                user=option.getAsUser();
                            }
                            break;
                        case llCommonKeys.SlashCommandReceive.subdir:
                            subdirProvided=true;
                            break;

                    }
                }
                menuLevelsWearer();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        public InteractionHook gComponentInteractionHook;
        public InteractionHook gCurrentInteractionHook;

        public UserPrivacy gNewUserProfile=new UserPrivacy();
        public Boolean getProfile(Member member){
            String fName="[getProfile]";
            logger.info(fName);
            if(member==null)logger.error("member is null!");
            logger.info(fName + ".member:"+ member.getId());
            if(gNewUserProfile!=null&&gNewUserProfile.getMember()==member&&gNewUserProfile.gUserProfile.isProfile()){
                logger.info(fName + ".same member>skip");
                logger.info(fName + ".json="+gNewUserProfile.gUserProfile.jsonObject.toString());
                return true;
            }
            if(gNewUserProfile.build(gGlobal,member)){
                logger.info(fName + ".json="+gNewUserProfile.gUserProfile.jsonObject.toString());
                return true;
            }
            return false;
        }
        public Boolean saveProfile(){
            String fName="[saveProfile]";
            logger.info(fName);
            boolean result=gNewUserProfile.saveProfile();
            if(result)logger.info(fName + ".result=true");
            else logger.warn(fName + ".result=false");
            return result;
        }

       
    }
    
}
