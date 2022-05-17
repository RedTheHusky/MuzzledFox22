package util;
//implemented Runnable

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import models.lc.CrunchifyLog4jLevel;
import models.lc.helper.lcSendMessageHelper;
import models.lc.interaction.messagecomponent.lcSharedMessageComponentManager;
import models.lcGlobalHelper;
import models.ll.colors.llColors;
import models.ll.colors.llColors_Red;
import models.ll.llCommonKeys;
import models.ll.llCommonVariables;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsChannelHelper;
import models.ls.lsMemberHelper;
import models.ls.lsMessageHelper;
import models.ls.lsUnicodeEmotes;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.apache.log4j.Logger;
import util.entity.GuildNotificationProfile;
import util.entity.GuildNotificationReader;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class utilityUpdateNotification extends Command implements  llGlobalHelper, llMessageHelper, llMemberHelper  {
    String cName="[utilityUpdateNotification]";
    lcGlobalHelper gGlobal;
    Logger logger = Logger.getLogger(getClass());
    String commandPrefix="news";
    public utilityUpdateNotification(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(fName);
        gGlobal =g;
        this.name = "News/Update";
        this.help = "News/Update info";
        this.aliases = new String[]{commandPrefix,"update","utilityUpdateNotification"};
        this.guildOnly = true;
        this.category=llCommandCategory_Utility;
    }
    public utilityUpdateNotification(lcGlobalHelper g, SlashCommandEvent event){
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
        private Guild gGuild;private TextChannel gTextChannel; private Message gMessage;String gTitle="Update/News";

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
                guildNotificationProfile=new GuildNotificationProfile(gGlobal,gGuild);
                if(gTextChannel!=null)guildNotificationProfile=new GuildNotificationProfile(gGlobal,gTextChannel);
                else  if(gGuild!=null)guildNotificationProfile=new GuildNotificationProfile(gGlobal,gGuild);
                if(gSlashCommandEvent!=null){
                    messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                    slash();
                }else{
                    boolean isInvalidCommand=true;
                    messageComponentManager.set(gGlobal,gTextChannel,gTitle,gUser);
                    if (gCommandEvent.getArgs().isEmpty()) {
                        logger.info(fName + ".Args=0");
                        posttriggernewsHere();
                        isInvalidCommand = false;
                    } else {
                        logger.info(fName + ".Args");

                        String[] items = gCommandEvent.getArgs().split("\\s+");
                        switch (items[0].toLowerCase()){
                            case "help":
                                if(items.length>=2){
                                    help(items[1]);
                                }else{
                                    help("main");
                                }
                                isInvalidCommand = false;
                                break;
                            case "post2private": case "post2dm":
                                post2Private();
                                isInvalidCommand = false;
                                break;
                            case "post2public":
                                post2Text();
                                isInvalidCommand = false;
                                break;
                            case "set":
                                if(items.length>=2){
                                    switch (items[1].toLowerCase()){
                                        case "all":
                                            if(items.length>=3)
                                                switch (items[2].toLowerCase()){
                                                    case "channel":
                                                        if(items.length>=4){
                                                            allnewsconfigSetChannel(items[3]);
                                                        }else{
                                                            allnewsconfigSetChannel("clear");
                                                        }
                                                        isInvalidCommand = false;
                                                        break;
                                                }
                                            break;
                                        case "general":
                                            if(items.length>=3)
                                            switch (items[2].toLowerCase()){
                                                case "channel":
                                                    if(items.length>=4){
                                                        generalnewsconfigSetChannel(items[3]);
                                                    }else{
                                                        manualnewsconfigSetChannel("clear");
                                                    }
                                                    isInvalidCommand = false;
                                                    break;
                                            }
                                            break;
                                        case "auto":
                                            if(items.length>=3)
                                            switch (items[2].toLowerCase()){
                                                case "disable":
                                                    if(items.length>=4){
                                                        spamnewsconfigSetDisable(items[3]);
                                                        isInvalidCommand = false;
                                                    }
                                                    break;
                                                case "channel":
                                                    if(items.length>=4){
                                                        spamnewsconfigSetChannel(items[3]);
                                                    }else{
                                                        manualnewsconfigSetChannel("clear");
                                                    }
                                                    isInvalidCommand = false;
                                                    break;
                                            }
                                            break;
                                        case "manual":
                                            if(items.length>=3)
                                            switch (items[2].toLowerCase()){
                                                case "disable":
                                                    if(items.length>=4){
                                                        manualnewsconfigSetDisable(items[3]);
                                                        isInvalidCommand = false;
                                                    }
                                                    break;
                                                case "channel":
                                                    if(items.length>=4){
                                                        manualnewsconfigSetChannel(items[3]);
                                                    }else{
                                                        manualnewsconfigSetChannel("clear");
                                                    }
                                                    isInvalidCommand = false;
                                                    break;
                                            }
                                            break;
                                    }
                                }
                                break;
                            case "reset":
                                if(items.length>=2){
                                    newsconfigReset(items[1].toLowerCase());
                                }else{
                                    newsconfigReset("all");
                                }
                                isInvalidCommand = false;
                                break;
                            case "get":
                                newsconfigGet();
                                isInvalidCommand = false;
                             break;
                            case "admin":
                                if(items.length>=2){
                                   switch (items[1].toLowerCase()){
                                       case "reload":
                                           reload();
                                           isInvalidCommand = false;
                                           break;
                                       case "post2all":
                                           postgeneralnews();
                                           isInvalidCommand = false;
                                           break;
                                       case "post":
                                           postgeneralnewsHere();
                                           isInvalidCommand = false;
                                           break;
                                   }
                                }
                            break;
                        }
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
            //"[general/auto/manual]"
            embed.setTitle(gTitle);embed.setColor(llColors.llColorBlue1);
            embed.addField("News options","`get`, view the settings\n`set [all/general/auto/manual] channel [@channel]`, redirects all news to @channel\n`set [all/general/auto/manual] channel`, clears channel restriction, allowing to be sent to any channel\n`set [auto/manual] disable [true/false]`, sets to disable to send news to guild's text channels\n`reset [all/general/auto/manual]`, the command is obvious",false);
            embed.addField("Definition of [general/auto/manual]","`general`, is the news thats sent to all guilds, while it can't be disabled it can be redirected\n`auto`, news that's triggered by any commands to the bot or gag instance, there is a timeout between posts\n`manual`, news that needs to be triggered by user",false);
            embed.addField("Send news","private:post2private, post2dm\npublic: post2public",false);
            if(gGlobal.isPartOfTeam(gMember)){
                embed.addField("Bot command","`admin reload|post|post2all`",false);
            }
            lsMessageHelper.lsSendMessage(gUser,embed);
        }
        lcSendMessageHelper messageHelper=new lcSendMessageHelper();
        public lcSharedMessageComponentManager messageComponentManager=new lcSharedMessageComponentManager();
        private void reload(){
            String fName="[reload]";
            logger.info(fName);
            try{
                if(!gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".denied");
                    lsMessageHelper.lsSendMessage(gTextChannel,lsMessageHelper.lsErrorEmbed(gTitle,"Denied!", llColors_Red.llColorRed_Barn));
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorBlue3);
                String desc="";
                if(!gGlobal.generalGuildNotification.init()){
                    embedBuilder.setDescription("Error reading file").setColor(llColorOrange);
                }else{
                    embedBuilder.setDescription("Realoaded").setColor(llColorOrange);
                }
                lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }
        private void post2Text(){
            String fName="[ post2Text]";
            logger.info(fName);
            try{
                GuildNotificationReader._NEWS triggerNews=gGlobal.generalGuildNotification.getTriggerNews();
                GuildNotificationProfile._CONFIG._NEWS trigeredNewsConfig=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                Message message=null;
                TextChannel selectedChannel=gTextChannel;
                if(trigeredNewsConfig.hasChannel()){
                    TextChannel tmpChannel=lsChannelHelper.lsGetTextChannelById(gGuild,trigeredNewsConfig.getChannelId());
                    if(tmpChannel!=null&&tmpChannel.canTalk()){
                        selectedChannel=tmpChannel;
                    }
                }
                message=triggerNews.send(selectedChannel);
                post2TextListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void post2TextListener(Message message){
            String fName="[post2TextListener]";
            logger.info(fName);
            try{
                TimeUnit timeUnit=TimeUnit.MINUTES;long timeValue=5;
                long autoDelete=gGlobal.generalGuildNotification.getTriggerNews().getAutoDelete();
                boolean isAutoDelete=false;
                if(autoDelete>= llCommonVariables.milliseconds_second*15){
                    isAutoDelete=true;
                    timeUnit=TimeUnit.SECONDS;timeValue=autoDelete;
                }
                boolean finalIsAutoDelete = isAutoDelete;
                if(message.isFromGuild()){
                    gGlobal.waiter.waitForEvent(GuildMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                        llMessageDelete(message);
                                    }else{
                                       post2TextListener(message);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    llMessageDelete(message);
                                }
                            },timeValue, timeUnit, () -> {
                                if(finalIsAutoDelete)llMessageDelete(message);
                                logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }else{

                    gGlobal.waiter.waitForEvent(PrivateMessageReactionAddEvent.class,
                            e -> (e.getMessageIdLong()==message.getIdLong()&&e.getUserIdLong()==gUser.getIdLong()),
                            e -> {
                                try {
                                    String name=e.getReactionEmote().getName();
                                    logger.warn(fName+"name="+name);
                                    if(name.equalsIgnoreCase(gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb))){
                                        llMessageDelete(message);
                                    }else{
                                        post2TextListener(message);
                                    }
                                }catch (Exception e3){
                                    logger.error(fName + ".exception=" + e3);
                                    logger.error(fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                                    llMessageDelete(message);
                                }
                            },timeValue,timeUnit, () -> {
                                if(finalIsAutoDelete)llMessageDelete(message);
                                logger.info(fName+lsGlobalHelper.timeout_reaction_add);});
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void post2Private(){
            String fName="[ post2Text]";
            logger.info(fName);
            try{
                GuildNotificationReader._NEWS news=gGlobal.generalGuildNotification.getTriggerNews();
                Message message=news.send(gUser);
                post2TextListener(message);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String strInsufficientPermission="Insufficient permission!";
        private void allnewsconfigSetChannel(String value){
            String fName="[allnewsconfigSetChannel]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                GuildNotificationProfile._CONFIG._NEWS news=guildNotificationProfile.getConfig().getGeneralNewsConfig();
                GuildNotificationProfile._CONFIG._NEWS spam=guildNotificationProfile.getConfig().getSpamNewsConfig();
                GuildNotificationProfile._CONFIG._NEWS trigger=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                TextChannel textChannel=null;
                if(value!=null&&!value.isBlank()){
                    if(value.equalsIgnoreCase("clear")){
                        if(news.clearChannel()==null||trigger.clearChannel()==null||spam.clearChannel()==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        if(!guildNotificationProfile.saveProfile()){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        embedBuilder.setDescription("Updated, cleared channel restriction");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }else{
                        textChannel=lsChannelHelper.lsGetTextChannelById(gGuild,value);
                    }
                }else{
                    List<TextChannel>textChannels=gMessage.getMentionedChannels();
                    if(textChannels.isEmpty()){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid option!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    textChannel=textChannels.get(0);
                }
                if(textChannel==null){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"No text channel mentioned!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(!textChannel.canTalk()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Cant talk in this channel, failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(news.setChannel(textChannel.getIdLong())==null||spam.setChannel(textChannel.getIdLong())==null||trigger.setChannel(textChannel.getIdLong())==null){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                    lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                    return;
                }
                if(!guildNotificationProfile.saveProfile()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                embedBuilder.setDescription("Updated, added channel restriction");
                //lsMessageHelper.lsSendMessage(gTextChannel,embedBuilder);
                messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void generalnewsconfigSetChannel(String value){
            String fName="[mgeneralnewsconfigSetChannel]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                GuildNotificationProfile._CONFIG._NEWS news=guildNotificationProfile.getConfig().getGeneralNewsConfig();
                if(value!=null&&value.equalsIgnoreCase("clear")){
                    if(news.clearChannel()==null){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    if(!guildNotificationProfile.saveProfile()){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    embedBuilder.setDescription("Updated, cleared channel restriction");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                List<TextChannel>textChannels=gMessage.getMentionedChannels();
                if(textChannels.isEmpty()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid option!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                TextChannel textChannel=textChannels.get(0);
                if(!textChannel.canTalk()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Cant talk in this channel, failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(news.setChannel(textChannel.getIdLong())==null){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(!guildNotificationProfile.saveProfile()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                embedBuilder.setDescription("Updated, added channel restriction");
                messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void manualnewsconfigSetChannel(String value){
            String fName="[manualnewsconfigSetChannel]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                GuildNotificationProfile._CONFIG._NEWS triggeredNews=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                if(value!=null&&value.equalsIgnoreCase("clear")){
                    if(triggeredNews.clearChannel()==null){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    if(!guildNotificationProfile.saveProfile()){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    embedBuilder.setDescription("Updated, cleared channel restriction");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                List<TextChannel>textChannels=gMessage.getMentionedChannels();
                if(textChannels.isEmpty()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid option!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                TextChannel textChannel=textChannels.get(0);
                if(!textChannel.canTalk()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Cant talk in this channel, failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(triggeredNews.setChannel(textChannel.getIdLong())==null){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(!guildNotificationProfile.saveProfile()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                embedBuilder.setDescription("Updated, added channel restriction");
                messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void manualnewsconfigSetDisable(String value){
            String fName="[manualnewsconfigSetDisable]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(value==null||value.isEmpty()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Did not provided option (true, 1 /false, 0)!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                GuildNotificationProfile._CONFIG._NEWS triggeredNews=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                switch (value.toLowerCase()){
                    case "1":
                    case "true":
                        if(triggeredNews.setDisabled(true)==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        if(!guildNotificationProfile.saveProfile()){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        embedBuilder.setDescription("Updated, set to disabled.");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        break;
                    case "0":
                    case "false":
                        if(triggeredNews.setDisabled(false)==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        if(!guildNotificationProfile.saveProfile()){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        embedBuilder.setDescription("Updated, set to enable.");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        break;
                    default:
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid optin, Please provide: true, 1, false, 0!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();

                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void spamnewsconfigSetChannel(String value){
            String fName="[spamnewsconfigSetChannel]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                GuildNotificationProfile._CONFIG._NEWS spamNews=guildNotificationProfile.getConfig().getSpamNewsConfig();
                if(value!=null&&value.equalsIgnoreCase("clear")){
                    if(spamNews.clearChannel()==null){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    if(!guildNotificationProfile.saveProfile()){
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    embedBuilder.setDescription("Updated, cleared channel restriction");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                List<TextChannel>textChannels=gMessage.getMentionedChannels();
                if(textChannels.isEmpty()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid option!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                TextChannel textChannel=textChannels.get(0);
                if(!textChannel.canTalk()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Cant talk in this channel, failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(spamNews.setChannel(textChannel.getIdLong())==null){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(!guildNotificationProfile.saveProfile()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                embedBuilder.setDescription("Updated, added channel restriction");
                messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void spamnewsconfigSetDisable(String value){
            String fName="[spamnewsconfigSetDisable]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(value==null||value.isEmpty()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Did not provided option (true, 1 /false, 0)!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                GuildNotificationProfile._CONFIG._NEWS spamNews=guildNotificationProfile.getConfig().getSpamNewsConfig();
                switch (value.toLowerCase()){
                    case "1":
                    case "true":
                        if(spamNews.setDisabled(true)==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        if(!guildNotificationProfile.saveProfile()){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        embedBuilder.setDescription("Updated, set to disabled.");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        break;
                    case "0":
                    case "false":
                        if(spamNews.setDisabled(false)==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        if(!guildNotificationProfile.saveProfile()){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        embedBuilder.setDescription("Updated, set to enable.");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        break;
                    default:
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid optin, Please provide: true, 1, false, 0!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();

                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void newsconfigReset(String option){
            String fName="[newsconfigReset]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                if(gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".bypass isPartOfTeam");
                }else
                if(!lsMemberHelper.lsMemberIsManager(gMember)){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,strInsufficientPermission);
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                if(option==null)option="";
                GuildNotificationProfile._CONFIG._NEWS generalNewsConfig=guildNotificationProfile.getConfig().getGeneralNewsConfig();
                GuildNotificationProfile._CONFIG._NEWS trigeredNewsConfig=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                GuildNotificationProfile._CONFIG._NEWS spamNewsConfig=guildNotificationProfile.getConfig().getSpamNewsConfig();

                switch (option.toLowerCase()){
                    case "all":
                        if(generalNewsConfig.setDisabled(false)==null||generalNewsConfig.clearChannel()==null||spamNewsConfig.setDisabled(false)==null||spamNewsConfig.clearChannel()==null||trigeredNewsConfig.setDisabled(false)==null||trigeredNewsConfig.clearChannel()==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        break;
                    case "general":
                        if(generalNewsConfig.setDisabled(false)==null||generalNewsConfig.clearChannel()==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        break;
                    case "auto":
                        if(spamNewsConfig.setDisabled(false)==null||spamNewsConfig.clearChannel()==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        break;
                    case "manual":
                        if(trigeredNewsConfig.setDisabled(false)==null||trigeredNewsConfig.clearChannel()==null){
                            embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to set!");
                            messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                            return;
                        }
                        break;
                    default:
                        embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Invalid option!");
                        messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                }
               if(!guildNotificationProfile.saveProfile()){
                    embedBuilder=lsMessageHelper.lsErrorEmbed(gTitle,"Failed to update!");
                    messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                embedBuilder.setDescription("Updated settings reset.");
                messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void newsconfigGet(){
            String fName="[newsconfigGet]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorPurple1);
                String desc="";
                GuildNotificationProfile._CONFIG._NEWS generalNewsConfig=guildNotificationProfile.getConfig().getGeneralNewsConfig();
                GuildNotificationProfile._CONFIG._NEWS triggeredNews=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                GuildNotificationProfile._CONFIG._NEWS spamNews=guildNotificationProfile.getConfig().getSpamNewsConfig();
                desc+="\ndisabled:"+generalNewsConfig.isDisabled();
                desc+="\nchannel: ";
                if(!generalNewsConfig.hasChannel()){
                    desc+=" (no channel set, free to channel)";
                }else{
                    TextChannel textChannel1=lsChannelHelper.lsGetTextChannelById(gGuild,generalNewsConfig.getChannelIdAsLong());
                    if(textChannel1==null){
                        desc+=" "+generalNewsConfig.getChannelIdAsLong()+" (cant find channel)";
                    }else{
                        if(!textChannel1.canTalk()){
                            desc+=" "+textChannel1.getAsMention()+" (cant talk here)";
                        }else{
                            desc+=" "+textChannel1.getAsMention();
                        }
                    }
                }
                embedBuilder.addField("General News",desc,false);
                desc="\ndisabled:"+spamNews.isDisabled();
                desc+="\nchannel: ";
                if(!spamNews.hasChannel()){
                    desc+=" (no channel set, free to channel)";
                }else{
                    TextChannel textChannel2=lsChannelHelper.lsGetTextChannelById(gGuild,spamNews.getChannelIdAsLong());
                    if(textChannel2==null){
                        desc+=" "+spamNews.getChannelIdAsLong()+" (cant find channel)";
                    }else{
                        if(!textChannel2.canTalk()){
                            desc+=" "+textChannel2.getAsMention()+" (cant talk here)";
                        }else{
                            desc+=" "+textChannel2.getAsMention();
                        }
                    }
                }
                embedBuilder.addField("Auto Trigger News",desc,false);
                desc="\ndisabled:"+triggeredNews.isDisabled();
                desc+="\nchannel: ";
                if(!triggeredNews.hasChannel()){
                    desc+=" (no channel set, free to channel)";
                }else{
                    TextChannel textChannel3=lsChannelHelper.lsGetTextChannelById(gGuild,triggeredNews.getChannelIdAsLong());
                    if(textChannel3==null){
                        desc+=" "+spamNews.getChannelIdAsLong()+" (cant find channel)";
                    }else{
                        if(!textChannel3.canTalk()){
                            desc+=" "+textChannel3.getAsMention()+" (cant talk here)";
                        }else{
                            desc+=" "+textChannel3.getAsMention();
                        }
                    }
                }
                embedBuilder.addField("Manual Trigger News",desc,false);
                messageHelper.setEmbed(embedBuilder.build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        String strDisabled2Post="Disabled to post, have server manager enable it.",
        strNoNews="(No news)",
        strBlocked4BotManager="Blocked from bot managment!";
        public void slash() {
            String fName="[slash]";
            logger.info(".start");
            try{
                boolean optChannel=false;
                GuildChannel optChannelValue=null;
                ChannelType optChannelType=null;
                gCurrentInteractionHook=lsMessageHelper.lsDeferReply(gSlashCommandEvent,true);
                String subcommand=gSlashCommandEvent.getSubcommandName(), subcommandGroup=gSlashCommandEvent.getSubcommandGroup();
                for(OptionMapping option:gSlashCommandEvent.getOptions()){
                    switch (option.getName()){
                        case "channel":
                            optChannel=true;
                            optChannelType=option.getChannelType();
                            if(optChannelType!=ChannelType.TEXT){
                                logger.warn(fName+"optChannel is not a text channel");
                                break;
                            }
                            optChannelValue=option.getAsGuildChannel();
                            break;
                    }
                }
                if(subcommandGroup==null)subcommandGroup="";
                if(subcommand==null)subcommand="";
                logger.info(fName+"subcommandGroup="+subcommandGroup+", subcommand="+subcommand);
                if(subcommandGroup.equalsIgnoreCase("config")){
                    switch (subcommand){
                        case "get":
                            newsconfigGet();
                            break;
                        case "channel":case "set_channel":
                            if(optChannel){
                                if(optChannelValue!=null){
                                    allnewsconfigSetChannel(optChannelValue.getId());
                                }
                                else if(optChannelType!=ChannelType.TEXT){
                                    messageHelper.setEmbed(lsMessageHelper.lsErrorEmbed(gTitle,"Invalid channel type provided!").build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                                }else {
                                    messageHelper.setEmbed(lsMessageHelper.lsErrorEmbed(gTitle, "Failed to get channel!").build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                                }
                            }else{
                                allnewsconfigSetChannel("clear");
                            }
                            break;
                        case "reset":
                            newsconfigReset("all");
                            break;
                    }
                }else
                if(subcommand.equalsIgnoreCase("get")){
                    posttriggernewsHere();
                    /*GuildNotificationReader._NEWS triggerNews=gGlobal.generalGuildNotification.getTriggerNews();
                    GuildNotificationProfile._CONFIG._NEWS triggerNewsConfig=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                    EmbedBuilder embedBuilder=new EmbedBuilder();
                    embedBuilder.setTitle(gTitle).setColor(llColorOrange);
                    if(!triggerNews.isEnabled()){
                        logger.info(fName+"triggerNews.isEnabled()=false->abort");
                        logger2.info(fName+"[get] triggerNews.isEnabled()=false->abort");
                        messageHelper.setEmbed(embedBuilder.setDescription(strNoNews).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    if(triggerNewsConfig.isDisabled()){
                        logger.info(fName+"generalNewsConfig.isDisabled()=true->abort");
                        logger2.info(fName+"[get] generalNewsConfig.isDisabled()=true->abort");
                        messageHelper.setEmbed(embedBuilder.setDescription(strDisabled2Post).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return;
                    }
                    if(triggerNews.hasBlockedGuilds()){
                        logger.info(fName+"generalNews.hasBlockedGuilds()=true");
                        if(triggerNews.isGuildBlocked(gGuild)){
                            logger.info(fName+"generalNews.isGuildBlocked()=true->abort");
                            logger2.info(fName+"[get] generalNews.isGuildBlocked()=true->abort");
                            messageHelper.setEmbed(embedBuilder.setDescription(strBlocked4BotManager).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        }
                    }
                    if(triggerNews.hasAllowedGuilds()){
                        logger.info(fName+"generalNews.hasAllowedGuilds()=true");
                        if(!triggerNews.isGuildAllowed(gGuild)){
                            logger.info(fName+"generalNews.isGuildAllowed()=false->abort");
                            logger2.info(fName+"[get] generalNews.isGuildAllowed()=false->abort");
                            messageHelper.setEmbed(embedBuilder.setDescription(strBlocked4BotManager).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        }
                    }
                    TextChannel textChannel=null;
                    if(triggerNewsConfig.hasChannel()){
                        logger.info(fName+"generalNewsConfig.hasChannel()");
                        TextChannel textChannel2=lsChannelHelper.lsGetTextChannelById(gGuild,triggerNewsConfig.getChannelIdAsLong());
                        if(textChannel2!=null&&textChannel2.canTalk()){
                            logger.info(fName+"textChannel2 can talk");
                            textChannel=textChannel2;
                        }else
                        if(textChannel2!=null){
                            logger.info(fName+"textChannel2 can't talk");
                        }else{
                            logger.info(fName+"textChannel2 is invalid");
                        }
                    }
                    if(textChannel==null){
                        logger.info(fName+"textChannel is null");
                        List<TextChannel>textChannels=gGuild.getTextChannels();
                        logger.info(fName+"textChannels.size="+textChannels.size());
                        for(TextChannel textChannel1:textChannels){
                            if(textChannel1.canTalk()){
                                logger.info(fName+"set a new textchannel");
                                textChannel=textChannel1;
                                break;
                            }
                        }
                    }
                    Message message=messageHelper.setEmbed(triggerNews.getEmbedMessage()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    if(message!=null){
                        logger.info(fName+"guildNotificationProfile.send()="+message.getId());
                        logger2.info(fName+"[get] posted to channel "+textChannel.getId()+", nessage="+message.getId());
                    }else{
                        logger.info(fName+"guildNotificationProfile.send()->failed");
                        logger2.warn(fName+"[get] failed to post to channel "+textChannel.getId()+".");
                        messageHelper.setEmbed(embedBuilder.setDescription("Something went wrong").build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    }*/
                }else{
                    messageHelper.setEmbed(lsMessageHelper.lsErrorEmbed(gTitle, "Invalid command!").build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();

                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
            }
        }

        public InteractionHook gCurrentInteractionHook;
        public InteractionHook gComponentInteractionHook;
        GuildNotificationProfile guildNotificationProfile=null;
        Logger logger2 = Logger.getLogger(CrunchifyLog4jLevel.GuildNewsPost_STR);
        private void  postgeneralnews(){
            String fName="[postgeneralnews_All]";
            logger.info(fName);
            try{
                if(!gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".denied");
                    lsMessageHelper.lsSendMessage(gTextChannel,lsMessageHelper.lsErrorEmbed(gTitle,"Denied!", llColors_Red.llColorRed_Barn));
                }
                String guildPostReport="";
                GuildNotificationReader._NEWS generalNews=gGlobal.generalGuildNotification.getGeneralNews();
                if(!generalNews.isEnabled()){
                    logger.info(fName+"generalNews.isEnabled()=false->abort");
                    logger2.info("[post2All] generalNews.isEnabled()=false");
                    lsMessageHelper.lsSendMessage(gTextChannel,lsMessageHelper.lsErrorEmbed(gTitle,"Is enable set to false!", llColors_Red.llColorRed_Barn));
                    return;
                }
                EmbedBuilder embedBuilder=new EmbedBuilder();
                String desc="";
                List<Guild>guilds=gGlobal.getGuildList();
                embedBuilder.setDescription("Its going to take some time to do for all "+guilds.size()+" guilds.").setTitle(gTitle).setColor(llColorPurple1);
                Message message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                int count_run=0,count_updatedelay=0,count_success=0,count_failed=0,count_failed_other=0,count_disabledAtGuild=0,count_failed_notextchannel=0,count_failed_tosend=0,count_guild_blocked=0,count_guild_notpartofallowedguilds=0;
                logger.info(fName+".guilds.size="+guilds.size());
                logger2.info("[post2All] guilds.size="+guilds.size());
                for(Guild guild:guilds){
                    int tmpI=postgeneralnews(generalNews, guild);
                    logger.info(fName+".guild="+guild.getId()+", response="+tmpI);
                    switch (tmpI){
                        case 1:count_success++;break;
                        case 2:count_guild_notpartofallowedguilds++;count_failed++;break;
                        case 3:count_guild_blocked++;count_failed++;break;
                        case 4:count_disabledAtGuild++;count_failed++;break;
                        case -3:count_failed_notextchannel++;count_failed++;break;
                        case -1:count_failed_tosend++;count_failed++;break;
                        default:count_failed++;count_failed_other++;
                    }
                    count_run++;count_updatedelay++;
                    if(count_updatedelay>=500){
                        count_updatedelay=0;
                        embedBuilder.setDescription("Its going to take some time to do for all "+guilds.size()+" guilds. Currently at "+count_run+".");
                        if(message==null){
                            message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                        }else {
                            message = lsMessageHelper.lsEditMessageResponse(message, embedBuilder);
                        }
                    }
                }
                logger.info(fName+".exited for");
                logger2.info("[post2All] done");
                desc="Done. From "+guilds.size()+":";
                if(count_success>0)desc+="\n"+count_success+" success";
                if(count_failed>0){
                    desc+="\n"+count_failed+" failed due to:";
                    if(count_disabledAtGuild>0)desc+="\n"+count_disabledAtGuild+" disabled at guild";
                    if(generalNews.hasBlockedGuilds()) desc+="\n"+count_guild_blocked+" blocked guilds";
                    if(generalNews.hasAllowedGuilds())desc+="\n"+count_guild_notpartofallowedguilds+" not part of allowed guilds";
                    if(count_failed_notextchannel>0)desc+="\n"+count_failed_notextchannel+" no channel";
                    if(count_failed_tosend>0)desc+="\n"+count_failed_tosend+" send exception";
                    if(count_failed_other>0)desc+="\n"+count_failed_other+" other";
                }
                embedBuilder.setDescription(desc);
                if(message==null){
                    message=lsMessageHelper.lsSendMessageResponse(gTextChannel,embedBuilder);
                }else {
                    message = lsMessageHelper.lsEditMessageResponse(message, embedBuilder);
                }
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private void  postgeneralnewsHere(){
            String fName="[postgeneralnewsHere]";
            logger.info(fName);
            try{
                if(!gGlobal.isPartOfTeam(gMember)){
                    logger.warn(fName+".denied");
                    lsMessageHelper.lsSendMessage(gTextChannel,lsMessageHelper.lsErrorEmbed(gTitle,"Denied!", llColors_Red.llColorRed_Barn));
                }
                GuildNotificationReader._NEWS generalNews=gGlobal.generalGuildNotification.getGeneralNews();
                if(!generalNews.isEnabled()){
                    logger.info(fName+"generalNews.isEnabled()=false->abort");
                    return;
                }
                postgeneralnews(generalNews, gGuild);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private int  postgeneralnews(GuildNotificationReader._NEWS generalNews, Guild guild){
            String fName="[postgeneralnews]";
            logger.info(fName);
            try{
                if(guild==null)throw  new Exception("Guild is NULL!");
                fName+="["+guild.getId()+"]";
                GuildNotificationProfile._CONFIG._NEWS generalNewsConfig=guildNotificationProfile.getConfig().getGeneralNewsConfig();
                if(!generalNews.isEnabled()){
                    logger.info(fName+"generalNews.isEnabled()=false->abort");
                    logger2.info(fName+"generalNews.isEnabled()=false->abort");
                    return 5;
                }
                if(generalNewsConfig.isDisabled()){
                    logger.info(fName+"generalNewsConfig.isDisabled()=true->abort");
                    logger2.info(fName+"generalNewsConfig.isDisabled()=true->abort");
                    return 4;
                }
                if(generalNews.hasBlockedGuilds()){
                    logger.info(fName+"generalNews.hasBlockedGuilds()=true");
                    if(generalNews.isGuildBlocked(guild)){
                        logger.info(fName+"generalNews.isGuildBlocked()=true->abort");
                        logger2.info(fName+"generalNews.isGuildBlocked()=true->abort");
                        return 3;
                    }
                }
                if(generalNews.hasAllowedGuilds()){
                    logger.info(fName+"generalNews.hasAllowedGuilds()=true");
                    if(!generalNews.isGuildAllowed(guild)){
                        logger.info(fName+"generalNews.isGuildAllowed()=false->abort");
                        logger2.info(fName+"generalNews.isGuildAllowed()=false->abort");
                        return 2;
                    }
                }
                Message message=null;boolean hadTextChannels=false;TextChannel textChannel=null;
                if(generalNewsConfig.hasChannel()){
                    logger.info(fName+"generalNewsConfig.hasChannel()");
                    TextChannel textChannel1=lsChannelHelper.lsGetTextChannelById(gGuild,generalNewsConfig.getChannelIdAsLong());
                    if(textChannel1!=null&&textChannel1.canTalk()){
                        logger.info(fName+"textChannel1 can talk");
                        message=generalNews.send(textChannel1);
                        hadTextChannels=true;
                        textChannel=textChannel1;
                    }else
                    if(textChannel1!=null){
                        logger.info(fName+"textChannel1 can't talk");
                    }else{
                        logger.info(fName+"textChannel1 is invalid");
                    }
                }
                if(message==null){
                    logger.info(fName+"search textchannel to post to");
                    List<TextChannel>textChannels=guild.getTextChannels();
                    logger.info(fName+"textChannels.size="+textChannels.size());
                    for(TextChannel textChannel2:textChannels){
                        if(textChannel2.canTalk()){
                            logger.info(fName+"try textchannel2="+textChannel2.getId());
                            message=generalNews.send(textChannel2); hadTextChannels=true;
                            textChannel=textChannel2;
                            if(message!=null){
                                logger.info(fName+"sent to textchannel="+textChannel2.getId());
                                break;
                            }
                            break;
                        }
                    }
                }
                if(message!=null){
                    logger.info(fName+"guildNotificationProfile.send()="+message.getId());
                    logger2.info(fName+" posted to channel "+textChannel.getId()+", nessage="+message.getId());
                    return 1;
                }if(hadTextChannels){
                    logger.info(fName+"guildNotificationProfile.send()->failed");
                    logger2.warn(fName+" failed to post to channel "+textChannel.getId()+".");
                    return -1;
                }
                else{
                    logger.info(fName+"no textchannel found");
                    logger2.warn(fName+" failed to post as found no textchannel");
                    return -3;
                }

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                logger2.error(fName+" exception=" + e);
                return -4;
            }
        }
        private void  posttriggernewsHere(){
            String fName="[posttriggernewsHere]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorOrange);
                GuildNotificationReader._NEWS triggerNews=gGlobal.generalGuildNotification.getTriggerNews();
                if(!triggerNews.isEnabled()){
                    logger.info(fName+"triggerNews.isEnabled()=false->abort");
                    messageHelper.setEmbed(embedBuilder.setDescription("(No news)").build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return;
                }
                posttriggernews(triggerNews, gGuild,gTextChannel);
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private int  posttriggernews(GuildNotificationReader._NEWS triggernews, Guild guild, TextChannel textChannel){
            String fName="[posttriggernews]";
            logger.info(fName);
            try{
                EmbedBuilder embedBuilder=new EmbedBuilder();
                embedBuilder.setTitle(gTitle).setColor(llColorOrange);
                if(guild==null)throw  new Exception("Guild is NULL!");
                fName+="["+guild.getId()+"]";
                GuildNotificationProfile._CONFIG._NEWS trigeredNewsConfig=guildNotificationProfile.getConfig().getTrigeredNewsConfig();
                if(!triggernews.isEnabled()){
                    logger.info(fName+"triggernews.isEnabled()=false->abort");
                    logger2.info(fName+"triggernews.isEnabled()=false->abort");
                    messageHelper.setEmbed(embedBuilder.setDescription(strNoNews).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return 5;
                }
                if(trigeredNewsConfig.isDisabled()){
                    logger.info(fName+"trigeredNewsConfig.isDisabled()=true->abort");
                    logger2.info(fName+"trigeredNewsConfig.isDisabled()=true->abort");
                    messageHelper.setEmbed(embedBuilder.setDescription(strDisabled2Post).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                    return 4;
                }
                if(triggernews.hasBlockedGuilds()){
                    logger.info(fName+"triggernews.hasBlockedGuilds()=true");
                    if(triggernews.isGuildBlocked(guild)){
                        logger.info(fName+"triggernews.isGuildBlocked()=true->abort");
                        logger2.info(fName+"triggernews.isGuildBlocked()=true->abort");
                        messageHelper.setEmbed(embedBuilder.setDescription(strBlocked4BotManager).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return 3;
                    }
                }
                if(triggernews.hasAllowedGuilds()){
                    logger.info(fName+"triggernews.hasAllowedGuilds()=true");
                    if(!triggernews.isGuildAllowed(guild)){
                        logger.info(fName+"triggernews.isGuildAllowed()=false->abort");
                        logger2.info(fName+"triggernews.isGuildAllowed()=false->abort");
                        messageHelper.setEmbed(embedBuilder.setDescription(strBlocked4BotManager).build()).setTextChannel(gTextChannel).setInteractionHook(gCurrentInteractionHook).send();
                        return 2;
                    }
                }
                Message message=null;

                if(gCurrentInteractionHook!=null) {
                    logger.info(fName+"gCurrentInteractionHook is not null");
                    message=messageHelper.setEmbed(triggernews.getEmbedMessage()).setInteractionHook(gCurrentInteractionHook).send();
                }
                if(message==null){
                    logger.info(fName+"i nteraction message =failed-> do textchannel method");
                    if(trigeredNewsConfig.hasChannel()){
                        TextChannel textChannel2=null;
                        logger.info(fName+"trigeredNewsConfig.hasChannel()");
                        textChannel2=lsChannelHelper.lsGetTextChannelById(gGuild,trigeredNewsConfig.getChannelIdAsLong());
                        if(textChannel2!=null&&textChannel2.canTalk()){
                            logger.info(fName+"textChannelConfig can talk, id="+textChannel2.getId());
                            message=messageHelper.setEmbed(triggernews.getEmbedMessage()).setTextChannel(textChannel2).send();
                        }else
                        if(textChannel2!=null){
                            logger.info(fName+"textChannelConfig can't talk");
                        }else{
                            logger.info(fName+"textChannelConfig is invalid");
                        }
                    }
                    if(message==null){
                        logger.info(fName+"message is null-> try provided textchannel, id="+textChannel.getId());
                        message=messageHelper.setEmbed(triggernews.getEmbedMessage()).setTextChannel(textChannel).send();
                    }
                    if(message==null){
                        logger.info(fName+"search textchannel to post to");
                        List<TextChannel>textChannels=guild.getTextChannels();
                        logger.info(fName+"textChannels.size="+textChannels.size());
                        for(TextChannel textChannel3:textChannels){
                            if(textChannel3.canTalk()){
                                logger.info(fName+"try textchannel="+textChannel3.getId());
                                message=messageHelper.setEmbed(triggernews.getEmbedMessage()).setTextChannel(textChannel3).send();
                                if(message!=null){
                                    logger.info(fName+"sent to textchannel="+textChannel3.getId());
                                    break;
                                }
                            }
                        }
                    }
                    if(message==null){
                        logger.info(fName+"guildNotificationProfile.send()->failed");
                        logger2.warn(fName+" failed to post to channel "+textChannel.getId()+".");
                        return -1;
                    }
                }
                logger.info(fName+"guildNotificationProfile.send()="+message.getId());
                logger2.info(fName+" posted to channel "+textChannel.getId()+", nessage="+message.getId());

                long autoDelete=triggernews.getAutoDelete();
                if(triggernews.isDeleteOption()){
                    logger.info(fName + ".add delete option");
                    lsMessageHelper.lsMessageAddReactions(message,gGlobal.emojis.getEmoji(lsUnicodeEmotes.aliasBomb));
                }
                if(autoDelete>0){
                    logger.info(fName + ".autoDelete="+autoDelete);
                    lsMessageHelper.lsSetAutoDelete(gGlobal,message,TimeUnit.SECONDS,autoDelete);
                }
                return  1;

            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                logger2.error(fName+" exception=" + e);
                return -4;
            }
        }

       
    }
    
}
